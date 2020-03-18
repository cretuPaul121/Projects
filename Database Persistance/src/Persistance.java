import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * I used a map <String,String> to save the variable name and variable datatype
 * to use later to create the tables
 */

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
	 * save an object to the database
	 */
	
	public void saveToDb(Object o) {
		this.map = getInstanceVariables(o);

		tableName = o.getClass().getSimpleName();

		String datatypes = initializeDataype(map);

		String[] changeTypes = datatypes.split(" ");

		// change java datatype with the MYSQL dataype suitable him
		changesJavaDataTypesWithMysql(changeTypes);

		// final dataTypes
		String dataTypes = "";

		for (int i = 0; i < changeTypes.length; i++) {
			dataTypes += " " + changeTypes[i];
		}

		System.out.println(dataTypes);
		// I used the first sb instance to make a string like (variable - datatype...)

		// get the connection with mysql

		this.connection = new GetDbConnection(databaseName);

		Connection conn = this.connection.getConnection();

		// the final query to create the table
		String finalQuery = " CREATE TABLE IF NOT EXISTS " + tableName.toLowerCase() + dataTypes;

		try {
			Statement st = conn.createStatement();

			st.executeUpdate("USE " + databaseName);
			st.executeUpdate(finalQuery);

			String insertQuery = "INSERT INTO " + tableName.toLowerCase()+getColumns() + " VALUES";
			
			insertQuery = completeQuery(insertQuery,o);

			System.out.println(insertQuery);
			st.executeUpdate(insertQuery);

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	/*
	 *function that returns a map that contains variable name as key and variable
	 *type as value
	 */
	 
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

	/*
	 * In this function I used a string builder instance to make a string like
	 * (variableName variable dataType..) for query ... later java dataypes will be
	 * changed with MYSQL datatypes
	 */
	private String initializeDataype(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();

		sb.append("(id INTEGER AUTO_INCREMENT PRIMARY KEY, ");

		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey() + " " + entry.getValue() + " , ");
		}

		sb.delete(sb.length() - 2, sb.length() - 1);
		sb.append(")");

		return sb.toString();
	}

	
	/*
	 * I used this function to complet the insert query
	 * the insert query initialy is :  "INSERT INTO " + tableName.toLowerCase() + " VALUES(
	 * In this function I decide the dataype of the reference ,,o,, passed as argument
	 * I make a String which contains the values of the member variables of refference o
	 * and then concatenate it to the initial query
	 */
	private String completeQuery(String initialQuery, Object o) {
		StringBuilder sb2 = new StringBuilder(initialQuery);
		
		sb2.append("(");
		
		try {
			for (Map.Entry<String, String> entry : map.entrySet()) {

				// get the variable value
				Field f = o.getClass().getDeclaredField("" + entry.getKey());
				f.setAccessible(true);

				switch (map.get(entry.getKey())) {

				case "int": {

					Integer i = (Integer) f.get(o);
					sb2.append(i + ",");
				}
					break;

				case "String": {
					String s = (String) f.get(o);
					sb2.append("'" + s + "',");
				}
					break;

				case "boolean": {
					Boolean b = (Boolean) f.get(o);
					sb2.append(b + ",");
				}
					break;

				case "float": {
					Float fl = (Float) f.get(o);
					sb2.append(fl + ",");
				}
					break;

				case "double": {
					Double d = (Double) f.get(o);
					sb2.append(d + ",");
				}
					break;

				}

			}
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		sb2.deleteCharAt(sb2.length() - 1);
		sb2.append(")");

		return sb2.toString();
	}
	
	
	/*
	 * change java data types with mysql data types 
	 * ex double to DOUBLE, String to VARCHAR, int to INT, boolean to MEDIUMINT
	 */
	private void changesJavaDataTypesWithMysql(String[] initialDataypes) {
		for (int i = 0; i < initialDataypes.length; i++) {

			switch (initialDataypes[i]) {
			case "double": {
				initialDataypes[i] = "DOUBLE";
			}
				break;

			case "String": {
				initialDataypes[i] = "VARCHAR(255)";
			}
				break;

			case "int": {
				initialDataypes[i] = "INT";
			}
				break;

			case "boolean": {
				initialDataypes[i] = "MEDIUMINT";
			}
				break;
			case "float": {
				initialDataypes[i] = "FLOAT";
			}
				break;
			}

		}

	}

	/*
	 * I used this function to make a string with needed columns ()
	 */
	private String getColumns() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("(");
		for(Map.Entry<String,String> entry:map.entrySet()) {
			sb.append(entry.getKey()+",");
		}
		
		sb.deleteCharAt(sb.length() -1);
		sb.append(")");
		
		return sb.toString();
	}
	
}
