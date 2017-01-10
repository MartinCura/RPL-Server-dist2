package com.rpl.security;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.rpl.annotation.Secured;
import com.rpl.exception.RplNotAuthorizedException;
import com.rpl.exception.RplRoleException;
import com.rpl.model.Person;
import com.rpl.model.Role;
import com.rpl.service.SecurityService;
import com.rpl.service.UserService;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityInterceptor implements ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;

	@Inject
	private SecurityService securityService;
	
	@Inject
	private UserService userService;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		try {
			Person p = checkAuth(requestContext);
			checkRoles(requestContext, p);
			userService.setCurrentUser(p);
		} catch (ForbiddenException e) {
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	private Person checkAuth(ContainerRequestContext requestContext) throws NotAuthorizedException {
		// Get the HTTP Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Check if the HTTP Authorization header is present and formatted
		// correctly
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new NotAuthorizedException("Authorization header must be provided");
		}

		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.substring("Bearer".length()).trim();

		// Validate the token
		try {
			return securityService.validateToken(token);
		} catch (RplNotAuthorizedException e) {
			throw new NotAuthorizedException("Failed to authorize");
		}

	}

	private void checkRoles(ContainerRequestContext requestContext, Person p) throws ForbiddenException {
		List<Role> classRoles = extractRoles(resourceInfo.getResourceClass());
		List<Role> methodRoles = extractRoles(resourceInfo.getResourceMethod());
		try {
			if (methodRoles.isEmpty())
				securityService.checkPermissions(classRoles, p);
			else
				securityService.checkPermissions(methodRoles, p);
		} catch (RplRoleException e) {
			throw new ForbiddenException();
		}

	}

	private List<Role> extractRoles(AnnotatedElement annotatedElement) {
		if (annotatedElement == null) {
			return new ArrayList<Role>();
		} else {
			Secured secured = annotatedElement.getAnnotation(Secured.class);
			if (secured == null) {
				return new ArrayList<Role>();
			} else {
				Role[] allowedRoles = secured.value();
				return Arrays.asList(allowedRoles);
			}
		}
	}

}
