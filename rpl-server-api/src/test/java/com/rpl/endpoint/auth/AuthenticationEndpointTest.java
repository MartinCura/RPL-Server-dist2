package com.rpl.endpoint.auth;

import javax.ws.rs.core.Response;

import org.junit.Test;

import io.jsonwebtoken.lang.Assert;

public class AuthenticationEndpointTest {
	
	@Test
	public void testToken(){
		AuthenticationEndpoint ae = new AuthenticationEndpoint();
		Response r = ae.authenticateUser("dada", "adad");
		Assert.notNull(r);
	}

}
