import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import WorkWithBd.Database;

public class InfoTable extends BaseFrame implements InterfaceObject{
	private String[][] arrayMean;
	private String[] columnsHeader;
	private String name;
	private JLabel label;
	private JTable table1;
	private DefaultTableModel tableModel;
	private JPanel buttons;
	public InfoTable(int coordinateX, int coordinateY, int sizeX, int sizeY, String[][] arrayMean, String[] columnsHeader,String name)
	{
		super(coordinateX, coordinateY, sizeX, sizeY);
		this.arrayMean = arrayMean;
		this.columnsHeader = columnsHeader;
		this.name = name;
        CreateFrame();
	}
	
	@Override
	public void CreateFrame() 
	{
		label = new JLabel();
		tableModel = new DefaultTableModel(arrayMean, columnsHeader);
		table1 = new JTable(tableModel);
		Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(table1));
        contents.add(label);
        setContentPane(contents);
        createPanel();
	}
	
	JTable getTable() 
	{
		return table1;
	}
	
	JLabel getLavel() {
		return label;
	}
	void createPanel() 
	{
		buttons = new JPanel();
		getContentPane().add(buttons, "South");
	}
	
}
