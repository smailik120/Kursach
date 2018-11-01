import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import WorkWithBd.Database;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.awt.event.ActionEvent;
public class GameTable extends BaseFrame implements InterfaceObject{
	private String name;
	private TreeSet<String> strategiesDefense;
	private TreeSet<String> strategiesAttack;
	private String [] FieldAttack;
	private String [] FieldDefense;
	private String [] priceDefense;
	private String [] priceAttack;
	private String[][] meansGame;
	private JTable tableAttacks;
	private JTable tableDefense;
	private JTable game;
	private HashMap map;
	private StringWorker worker;
	private Mathematics mat;
	CheckModel modelGame;
	private DefaultTableModel tableModel;
	private JPanel buttons;
	private JLabel message;
	public GameTable(int coordinateX,int coordinateY,int sizeX,int sizeY, String name) throws SQLException
	{
		super(coordinateX, coordinateY, sizeX, sizeY);
		this.worker = new StringWorker();
		this.mat = new Mathematics();
		this.name = name;
		this.map = new HashMap<String, ArrayList<Integer>>();
		build();
        CreateFrame();
	}
	
	@Override
	public void CreateFrame()  
	{
  		String nameForChooseTableAttack[] = {"№", "attack name", "Choose"};
  		String [][] meansAttack = new String[FieldAttack.length][3];
  		String nameForChooseTableDefense[] = {"№", "defense name", "Choose"};
  		String [][] meansDefense = new String[FieldDefense.length][3];
  		String[] nameForGameTable = {"Таблица в которой отображаются все стратегии"};
		JTable chooseAttack = buildCheckTable(nameForChooseTableAttack, meansAttack, FieldAttack);
		game = new JTable(meansGame, nameForGameTable);
  		JTable chooseDefense = buildCheckTable(nameForChooseTableDefense, meansDefense, FieldDefense);
		Box contents = new Box(BoxLayout.X_AXIS);
        contents.add(new JScrollPane(chooseAttack));
        contents.add(new JScrollPane(game, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
        contents.add(new JScrollPane(chooseDefense));
        setContentPane(contents);
        createPanel();
	}
	
	void build() throws SQLException {
		FieldAttack = Database.getInstance().getFieldFromTable("attacks", "unicname");
		FieldDefense = Database.getInstance().getFieldFromTable("defense", "unicname");
		priceDefense = Database.getInstance().getFieldFromTable("defense", "cost");
		priceAttack = Database.getInstance().getFieldFromTable("attacks", "damage");
		strategiesDefense = new TreeSet<String>();
		strategiesDefense = worker.work(FieldDefense.length);
		strategiesAttack = new TreeSet<String>();
		strategiesAttack = worker.work(FieldAttack.length);
		meansGame = new String[0][1];
	}
	
	public JTable buildCheckTable(String[] nameForChooseTable, String[][] means, String[] FieldFromTable) {
		modelGame= new CheckModel(nameForChooseTable);
		for(int i = 1; i <= FieldFromTable.length; i++) {
  			modelGame.addRow(new Object[] {i, FieldFromTable[i - 1], false});
  		}
		return new JTable(modelGame);
	}
	
	
	public final static void setColumnsWidth(JTable table) {
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    JTableHeader th = table.getTableHeader();
	    for (int i = 0; i < table.getColumnCount(); i++) {
	        TableColumn column = table.getColumnModel().getColumn(i);
	        int prefWidth = 
	            Math.round(
	                (float) th.getFontMetrics(
	                    th.getFont()).getStringBounds(th.getTable().getColumnName(i),
	                    th.getGraphics()
	                ).getWidth()
	            );
	        column.setPreferredWidth(prefWidth + 100);
	    }
	}
	
	public void buildGameTable() throws SQLException {
		int sizeX = mat.sumOfCombinations(FieldAttack.length) + 1;
		int sizeY = mat.sumOfCombinations(FieldDefense.length) + 1;
		String[] defenderNames = new String[sizeY];
		String[] attackerNames = new String[sizeX];
		meansGame = new String[sizeX][sizeY];
		defenderNames[0] = "";
		for(int i = 2; i <= sizeY; i++) {
			defenderNames[i - 1] = "Defender " + Integer.toString(i - 1);
			meansGame[0][i - 1] = defenderNames[i - 1];
		}
		
		for(int i = 2; i <= sizeX; i++) {
			attackerNames[i - 1] = "Attacker " + Integer.toString(i - 1);
			meansGame[i - 1][0] = attackerNames[i - 1];
		}
		int i = 0;
		int j = 0;
		for(String at:strategiesAttack) {
			i++;
			for(String def:strategiesDefense) {
				j++;
				int sum = 0;
				System.out.println(def);
				for(int d = 0; d < def.length(); d++) {
					sum += Integer.parseInt(priceDefense[Character.getNumericValue(def.charAt(d)) - 1]);
				}
				for(int a = 0; a < at.length(); a++) {
					sum += Integer.parseInt(priceAttack[Character.getNumericValue(at.charAt(a)) - 1]);
				}
				meansGame[i][j] = Integer.toString(sum);
				System.out.println(meansGame[i][j]);
			}
			j = 0;
		}
		DefaultTableModel tableModel = new DefaultTableModel(meansGame, defenderNames);
		game.setModel(tableModel); 
		game.getTableHeader().setUI(null);
		game.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	void createButton() 
	{
		JButton create = new JButton("Create Game");
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					buildGameTable();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        buttons.add(create);
	}
	
	public void createPanel() 
	{
		buttons = new JPanel();
		createMessageError();
		createButton();
		getContentPane().add(buttons, "South");
	}
	
	
	
	void createMessageError() 
	{
		message = new JLabel("");
		buttons.add(message);
	}
}