package com.rpl.persistence;


import com.rpl.model.Topic;

public class TopicDAO extends ApplicationDAO {

    public Topic find(long id) {
        return entityManager.find(Topic.class, id);
    }

    public Topic save(Topic topic){
        return this.merge(topic);
    }
}
