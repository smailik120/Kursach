import javax.swing.table.DefaultTableModel;

public class CheckModel extends DefaultTableModel {
	
	public CheckModel(String[] names) {
		super(names, 0);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class my = String.class;
		if(columnIndex == 0) {
			my = Integer.class;
		}
		else if (columnIndex == 2) {
			my = Boolean.class;
		}
		return my;
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return column == 2 || column == 1 || column == 0;
	}
	
}
