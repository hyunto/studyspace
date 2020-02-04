package com.dbunit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseRepository implements Repository {

	private final String driver = org.h2.Driver.class.getName();
	private final String protocol = "jdbc:h2:mem:";
	private final String dbName = "test";
	private Connection conn;
	
	public DatabaseRepository() throws Exception {
		Class.forName(driver).newInstance();
		conn = DriverManager.getConnection(protocol + dbName);
	}
	
	public Seller findById(String id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Seller seller = null;
		
		try {
			String query = "SELECT ID, NAME, EMAIL FROM seller WHERE ID = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			
			if (!rs.next()) {
				throw new SQLException("No Data Found!");
			}
			
			seller = new Seller(rs.getString(1), rs.getString(2), rs.getString(3));
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return seller;
	}

	public void add(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	public void update(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	public void remove(Seller seller) {
		// TODO Auto-generated method stub
		
	}

}
