import java.sql.SQLException;

import WorkWithBd.Database;

public class Strategy {
	private String[] strategy;
	int ozu = 0;
	public Strategy(String[] strategy) throws SQLException {
		this.strategy = strategy;
	}
	public String[] getStrategy() {
		return strategy;
	}
	
	public int getOzu() {
		return ozu;
	}
}
