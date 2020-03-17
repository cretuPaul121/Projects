import java.sql.*;


public class GetDbConnection {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3307/";
	
	
	public GetDbConnection(String databaseName) {
		createDatabase(databaseName);
	}
	
	public Connection getConnection() {
		
		try {
			Class.forName(DRIVER);
			Connection conn = DriverManager.getConnection(
					   URL,"root","root");
			
			if(conn != null) {
				System.out.println("Conection established with the MYSQL database...");	
				return conn;
			}
			
		}
		catch(SQLException ex) {
			System.out.println(ex);
		}
		catch(ClassNotFoundException ex) {
			System.out.println("Class not found " + ex);
		}
		
		return null;
	}
	
	private void createDatabase(String dbName) {
		
		try {
			Connection  conn = getConnection();
			Statement st = conn.createStatement();
		
			String createTable = "CREATE DATABASE IF NOT EXISTS " + dbName;
			
			if(st.executeUpdate(createTable) > 0) {
				System.out.println("Database - " + dbName + " created succesfuly!");
			}else {
				System.out.println("Database cannot be created...");
			}
			
			st.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
