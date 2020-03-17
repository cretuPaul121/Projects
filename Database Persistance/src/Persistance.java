import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

public class Persistance {
	private GetDbConnection connection;
	private String tableName;
	private String databaseName;
	// map contains variable name and variable type
	private Map<String, String> map;

	public Persistance(String databaseName) {
		this.databaseName = databaseName;
	}

	
	/*
	 * I used a map <String,String> to save the variable name and variable datatype
	 * to use later to create the tables
	 */
	
	public void saveToDb(Object o) {
		this.map = getInstanceVariables(o);

		tableName = o.getClass().getSimpleName();

		StringBuilder sb = new StringBuilder();

		sb.append("( ");

		//initial string with the java datatype
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey() + " " + entry.getValue() + " , ");
		}

		sb.deleteCharAt(sb.length() - 1);
		sb.deleteCharAt(sb.length() - 1);

		sb.append(" )");

		String[] changeTypes = sb.toString().split(" ");

		// change java datatype with the MYSQL dataype suitable him
		for (int i = 0; i < changeTypes.length; i++) {

			switch (changeTypes[i]) {
			case "double": {
				changeTypes[i] = "DOUBLE";
			}
				break;

			case "String": {
				changeTypes[i] = "VARCHAR(255)";
			}
				break;

			case "int": {
				changeTypes[i] = "INT";
			}
				break;

			case "boolean": {
				changeTypes[i] = "MEDIUMINT";
			}
				break;
			case "float": {
				changeTypes[i] = "FLOAT";
			}
				break;
			}

		}

		//final dataTypes
		String dataTypes = "";

		for (int i = 0; i < changeTypes.length; i++) {
			dataTypes += " " + changeTypes[i];
		}
		
		//I used the first sb instance to make a string like (variable - datatype...)

		// get the connection with mysql

		this.connection = new GetDbConnection(databaseName);

		Connection conn = this.connection.getConnection();

		// the final query to create the table
		String finalQuery = " CREATE TABLE IF NOT EXISTS " + tableName.toLowerCase() + dataTypes;

		try {
			Statement st = conn.createStatement();

			st.executeUpdate("USE " + databaseName);
			st.executeUpdate(finalQuery);

			String insertQuery = "INSERT INTO " + tableName.toLowerCase() + " VALUES(";
			StringBuilder sb2 = new StringBuilder(insertQuery);

			for (Map.Entry<String, String> entry : map.entrySet()) {

				Field f = o.getClass().getDeclaredField("" + entry.getKey());
				f.setAccessible(true);
				
				switch (map.get(entry.getKey())) {

					case "int": {
	
						Integer i = (Integer) f.get(o);
						sb2.append(i+ ",");
					} break;
	
					case "String":{
						String s = (String) f.get(o);
						sb2.append("'"+s+"',");
					} break;
					
					case "boolean":{
						Boolean b = (Boolean) f.get(o);
						sb2.append(b + ",");
					} break;
					
					case "float":{
						Float fl = (Float) f.get(o);
						sb2.append(fl+",");
					} break;
					
					case "double":{
						Double d = (Double)f.get(o);
						sb2.append(d+",");
					} break;
				
				}

			}
			
			sb2.deleteCharAt(sb2.length()-1);
			sb2.append(")");
			
			insertQuery = sb2.toString();
			
			System.out.println(insertQuery);
			st.executeUpdate(insertQuery);

			st.close();
			conn.close();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// function that returns a map that contains variable name as key and variable
	// type as value
	private Map<String, String> getInstanceVariables(Object o) {

		Map<String, String> map = new LinkedHashMap<>();

		Class<?> obj = o.getClass();
		Field[] fields = obj.getDeclaredFields();

		for (Field f : fields) {
			String variableName = f.getName();
			String variableType = f.getType().getSimpleName();

			map.put(variableName, variableType);
		}

		return map;
	}

}
