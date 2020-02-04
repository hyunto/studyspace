package com.test.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeSearchSevlet extends HttpServlet {
	
	private SearchBiz searchBiz;
	
	public void setModel(SearchBiz searchBiz) {
		this.searchBiz = searchBiz;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Employee employee = searchBiz.getEmployeeByEmpid(request.getParameter("empid"));
		
		request.setAttribute("employee", employee);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/SearchResult.jsp");
		dispatcher.forward(request, response);
	}
}
