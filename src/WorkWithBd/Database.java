package WorkWithBd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
private static Database instance = new Database();
private Connection conn = null;
private Statement statement = null;
private Database() {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	try {
     	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginuser?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false","root","03091977s");
	}
	catch(Exception e1) {
     	System.out.print(e1.toString());
     }
}

public static Database getInstance() {
	return instance;
}

public String[] getFieldFromTable(String tableName, String name) throws SQLException {
	String[] array = null;
	String request = "select " + name + " from " + tableName;
 	if (conn != null) {
 		statement = conn.createStatement();
 		int size = this.getSize(tableName);
 		ResultSet resultSet;
 		System.out.println(size);
 		array = new String[size];
 		resultSet = statement.executeQuery(request);
 		int j = 0;
 		while(resultSet.next()) {
 			array[j] = resultSet.getString(1);
 			System.out.println(array[j]);
 			j++;
 		}
 	}
 	return array;
 }

public int getSize(String tableName) throws SQLException {
	String request = "select count(*) from " + tableName + ";";
	int size = 0;
 	if (conn != null) {
 		statement = conn.createStatement();
 		ResultSet resultSet = statement.executeQuery(request);
 		resultSet.next();
 		size = resultSet.getInt(1);
 	}
 	return size;
}
public void insertAllToTable(String tableName, String[] names, String[][] arrayMean) {
	for(int j = 0; j < arrayMean.length; j++) {
		String request = "insert into "+ tableName + "(";
		for(int i = 0; i < names.length - 1; i++) {
			request += names[i] + ", ";
		}
		request = request + names[names.length - 1];
		request = request + ") values (";
		if (conn != null) {
	 		try {
				statement = conn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		for(int i = 0; i < names.length - 1; i++) {
	 			request += (String)("'" + arrayMean[j][i] + "'" + ",");
	 		}
	 		request += "'" + (String)(arrayMean[j][names.length - 1]) + "'" + ");";
	 		try {
				statement.execute(request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	}
	 }
}

public void clearTable(String tableName) {
	String request = "delete from "+ tableName;
	if (conn != null) {
		try {
			statement = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			statement.execute(request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public String[][] getTable(String tableName, int columnNumber) throws SQLException {
	String[][] array = null;
	String request = "select * from " + tableName;
     	if (conn != null) {
     		statement = conn.createStatement();
     		int size = this.getSize(tableName);
     		int j = 0;
     		ResultSet resultSet;
     		array = new String[size][columnNumber];
     		resultSet = statement.executeQuery(request);
     		while(resultSet.next()) {
     			for(int i = 1; i <= columnNumber; i++) {
     				System.out.println(resultSet.getString(i));
     				array[j][i-1] = resultSet.getString(i);
     			}
     			j++;
     		}
     	}
     	return array;
     }
}
