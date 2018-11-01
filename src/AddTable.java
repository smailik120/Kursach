import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import WorkWithBd.Database;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
public class AddTable extends BaseFrame implements InterfaceObject{
	private String[][] arrayMean;
	private String[] columnsHeader;
	private int rows;
	private int columns;
	private String name;
	private JTable table1;
	private DefaultTableModel tableModel;
	private JPanel buttons;
	private JLabel message;
	public AddTable(int coordinateX, int coordinateY, int sizeX, int sizeY, String[][] arrayMean, String[] columnsHeader,String name)
	{
		super(coordinateX, coordinateY, sizeX, sizeY);
		this.arrayMean = arrayMean;
		this.columnsHeader = columnsHeader;
		this.rows = rows;
		this.columns = columns;
		this.name = name;
        CreateFrame();
	}
	
	@Override
	public void CreateFrame() 
	{
		tableModel = new DefaultTableModel(arrayMean, columnsHeader);
		table1 = new JTable(tableModel);
		Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(table1));
        setContentPane(contents);
        createPanel();
	}
	
	JTable getTable() 
	{
		return table1;
	}
	
	void createAddButton() 
	{
		JButton add = new JButton("Добавить");
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	boolean prove = false;
            	prove = true;
                int idx = table1.getRowCount() - 1;
                for(int i = 0; i < table1.getColumnCount(); i++) 
                {
                	if(table1.getValueAt(idx, i) == null) {
                		prove = false;
                		break;
                	}
                }
                if(prove == true) 
                {
                	tableModel.insertRow(idx + 1, new String[] {Integer.toString(idx+2)});
                }
                else
                {
                	message.setText("Вы добавили строку,но не заполнили ее,пожалуйста заполните все поля добавленной строки,а потом добавляйте новые строки");
                }
            }
        });
        buttons.add(add);
	}
	
	void createRemoveButton() 
	{
		JButton remove = new JButton("Удалить");
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Номер выделенной строки
                int idx = table1.getSelectedRow();
                // Удаление выделенной строки
                tableModel.removeRow(idx);
            }
        });
        buttons.add(remove);
	}
	
	void createPanel() 
	{
		buttons = new JPanel();
		createAddButton();
		createRemoveButton();
		createButtonSave();
		createMessageError();
		getContentPane().add(buttons, "South");
	}
	
	void createMessageError() 
	{
		message = new JLabel("");
		buttons.add(message);
	}
	
	void createButtonSave() 
	{
		JButton save = new JButton("Сохранить изменения");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		String[][] tempArray = new String[table1.getRowCount()][table1.getColumnCount()];
            		System.out.println(name);
            		System.out.println(columnsHeader.length);
					Database.getInstance().clearTable(name);
					for(int i = 0; i < table1.getRowCount(); i++) {
						for(int j = 0; j < table1.getColumnCount(); j++) {
							tempArray[i][j] = (String) table1.getValueAt(i, j);
						}
					}
					Database.getInstance().insertAllToTable(name, columnsHeader, tempArray);
            }
        });
        buttons.add(save);
	}
}

