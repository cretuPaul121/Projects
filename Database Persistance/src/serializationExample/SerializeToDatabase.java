package serializationExample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SerializeToDatabase {
	
	public static final String SQL_SERIALIZE_OBJECT = "INSERT INTO blobs (name,object) VALUES(?,?)";
	
	
	public static long serializeDataObjectToDb(Connection connection, Object objectToSerialize) throws SQLException{
		
		PreparedStatement ps = connection.prepareStatement(SQL_SERIALIZE_OBJECT);
		
		ps.setString(1, objectToSerialize.getClass().getSimpleName());
		ps.setObject(2, objectToSerialize);
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		
		int serialized_id = -1;
		
		if(rs.next()) {
			serialized_id = rs.getInt(1);
		}
		rs.close();
		ps.close();
		
		return serialized_id;
	}
	
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3307/biblioteca";
		String user = "root";
		String pass = "root";
		
		Connection conn = DriverManager.getConnection(url,user,pass);
		
		Vector<String> v = new Vector<>();
		
		v.add("Java");
		v.add("papers");
		
		long serialized_id = serializeDataObjectToDb(conn,v);
		
		System.out.println(serialized_id);
		
		conn.close();
	}
	
}
