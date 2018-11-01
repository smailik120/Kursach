import java.sql.SQLException;

import WorkWithBd.Database;

public class Test {
	public static void main(String[] args) throws SQLException {
		String[] names = {"unicname", "damage", "info", "unicgroup"};
		String[] result = Database.getInstance().getFieldFromTable("attacks", "unicname");
		//String[][] result = Database.getInstance().getTable("attacks", 4);
		System.out.println(result);
	}
}
