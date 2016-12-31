package com.rpl.endpoint.auth;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.rpl.model.Credentials;
import com.rpl.model.Role;

import io.jsonwebtoken.lang.Assert;

public class AuthenticationEndpointTest {
	
	@Test
	public void testToken(){
		AuthenticationEndpoint ae = new AuthenticationEndpoint();
		Response r = ae.authenticateUser(new Credentials("dada", "adad", Role.ADMIN));
		Assert.notNull(r);
	}

}
