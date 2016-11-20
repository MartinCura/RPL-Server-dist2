package com.rpl.persistence;

import org.junit.Assert;
import org.junit.Test;

public class ResultDAOTest {
	
	@Test
	public void testPrepareCommand() {
		ResultDAO r = new ResultDAO();
		Assert.assertNotNull(r.find(1l));
	}
	
}
