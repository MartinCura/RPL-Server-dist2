package com.rpl.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rpl.exception.RplException;
import com.rpl.exception.RplQueueException;
import com.rpl.model.*;
import com.rpl.persistence.ActivityDAO;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.service.*;
import com.rpl.service.util.JsonUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Stateless
public class ActivitySubmissionServiceImpl implements ActivitySubmissionService {

    @Inject
    private QueueProducerService queueService;
    @Inject
    private UserService userService;
    @Inject
    private ActivitySubmissionDAO activitySubmissionDAO;
    @Inject
    private ActivityDAO activityDAO;
    @Inject
    private ActionLogService actionLogService;

    private boolean hasDefinitiveSubmission(Long personId, Long activityId) {
        try {
            activitySubmissionDAO.findDefinitiveByActivityAndPerson(activityId, personId);
        } catch (Exception e) {	// ToDo: specify
            return false;
        }
        return true;
    }

    public ActivitySubmission getSubmissionById(Long id) {
        return activitySubmissionDAO.find(id);
    }

    public ActivitySubmission submit(Long activityId, ActivitySubmission submission) throws RplException {

        if (hasDefinitiveSubmission(userService.getCurrentUser().getId(), activityId)) {
            throw RplException.of(MessageCodes.SERVER_ERROR, "");
        }

        Activity activity = activityDAO.find(activityId);
        submission.setPerson(userService.getCurrentUser());
        submission.setActivity(activity);
        submission.setSubmissionDate(new Date());
        submission.setStatus(Status.PENDING);
        submission = activitySubmissionDAO.save(submission);
        actionLogService.logActivitySubmission(submission.getId());
        return submission;

    }

    public void markAsSelected(Long submissionId) throws RplException{
        ActivitySubmission submission = activitySubmissionDAO.find(submissionId);
        if (! submission.getStatus().equals(Status.SUCCESS)) {
            throw RplException.of(MessageCodes.SERVER_ERROR, "");
        }
        if (hasDefinitiveSubmission(submission.getPerson().getId(), submission.getActivity().getId())) {
            throw RplException.of(MessageCodes.SERVER_ERROR, "");
        }
        submission.setSelected(true);
        activitySubmissionDAO.save(submission);
        activitySubmissionDAO.setUnselectedSubmissions(submissionId, submission.getPerson().getId(), submission.getActivity().getId());
        actionLogService.logMarkActivitySubmissionAsSelected(submissionId);
    }

    public List<ActivitySubmission> getSubmissionsByActivity(Long activityId) {
        return activitySubmissionDAO.findByPersonAndActivity(userService.getCurrentUser().getId(), activityId);
    }

    public List<ActivitySubmission> getSubmissionsByActivity(Long id, Long activityId) {
        return activitySubmissionDAO.findByPersonAndActivity(id, activityId);
    }

    @Override	// ToDo: revisar este método; especificar exception
    public List<ActivitySubmission> getDefinitiveSubmissionsByActivity(Long activityId) throws RplException {
        try {
            ActivitySubmission submission = activitySubmissionDAO.findDefinitiveByActivityAndPerson(activityId, userService.getCurrentUser().getId());
        } catch (Exception e) {
            throw RplException.of(MessageCodes.SERVER_ERROR, "");
        }
        return activitySubmissionDAO.findDefinitiveByActivity(activityId);
    }

    public void queueSubmission(ActivitySubmission submission) throws RplQueueException {
        QueueMessage qm;
        try {
            qm = new QueueMessage(JsonUtils.objectToJson(submission));
            queueService.send(qm);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RplQueueException(e);
        }
    }

    public void markAsDefinitive(Long submissionId) throws RplException {
        ActivitySubmission submission = activitySubmissionDAO.find(submissionId);
        if (! submission.getStatus().equals(Status.SUCCESS)) {
            throw RplException.of(MessageCodes.SERVER_ERROR, "");
        }
        if (hasDefinitiveSubmission(submission.getPerson().getId(), submission.getActivity().getId())) {
            throw RplException.of(MessageCodes.SERVER_ERROR, "");
        }
        submission.setSelected(true);
        submission.setDefinitive(true);
        activitySubmissionDAO.save(submission);
        activitySubmissionDAO.setUnselectedSubmissions(submissionId, submission.getPerson().getId(), submission.getActivity().getId());
        actionLogService.logMarkActivitySubmissionAsDefinitive(submissionId);
    }

}
