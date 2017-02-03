package com.dbunit.db;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class DatabaseRepositoryTest {
	
	private final String driver = org.h2.Driver.class.getName();
	private final String protocol = "jdbc:h2:mem:";
	private final String dbName = "test";
	
	private IDatabaseTester databaseTester;
	
	@BeforeClass
	public static void createTable() throws Exception {
		// RunScript.execute("jdbc:h2:mem:test", "sa", "", "src/test/resources/com/dbunit/db/seller.sql", null, false);
		Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
		String query = "CREATE TABLE IF NOT EXISTS seller ("
				+ "ID varchar identity primary key,"
				+ "NAME varchar,"
				+ "EMAIL varchar"
				+ ")";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.execute();
		stmt.close();
	}
	
	@Before
	public void setUp() throws Exception {
		databaseTester = new JdbcDatabaseTester(driver, protocol + dbName);
		
		try {
			databaseTester.getConnection().getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
			
			IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/com/dbunit/db/seller.xml"));
			databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
			databaseTester.setDataSet(dataSet);
			databaseTester.onSetup();
		} finally {
			databaseTester.getConnection().close();
		}
	}

	@Ignore
	@Test
	public void testFindById() throws Exception {
		Seller expectedSeller = new Seller("horichoi", "최승호", "megaseller@hatmail.com");
		Repository repository = new DatabaseRepository();
		Seller actualSeller = repository.findById("horichoi");
		
		assertEquals(expectedSeller.getId(), actualSeller.getId());
		assertEquals(expectedSeller.getName(), actualSeller.getName());
		assertEquals(expectedSeller.getEmail(), actualSeller.getEmail());
	}
}
