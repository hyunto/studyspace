package com.test.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class EmployeeSearchServletTest {

	@Test
	public void testSearchByEmpid() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.addParameter("empid", "5874");
		
		SearchBiz biz = mock(SearchBiz.class);
		Employee expectedEmployee = new Employee("박성철", "5874", "fupfin", "회장");
		when(biz.getEmployeeByEmpid(anyString())).thenReturn(expectedEmployee);
		
		EmployeeSearchSevlet searchServlet = new EmployeeSearchSevlet();
		searchServlet.setModel(biz);
		searchServlet.service(request, response);
		
		Employee employee = (Employee)request.getAttribute("employee");
		assertEquals("박성철", employee.getName());
		assertEquals("5874", employee.getEmpid());
		assertEquals("fupfin", employee.getId());
		assertEquals("회장", employee.getPosition());
		
		assertEquals("/SearchResult.jsp", response.getForwardedUrl());
	}
}
