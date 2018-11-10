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
	private String [][] meansAttack;
	private String [][] meansDefense;
	private JButton info;
	private TreeSet<ArrayList<String>> strategiesDefense;
	private TreeSet<ArrayList<String>> strategiesAttack;
	private String [] FieldAttack;
	private String [] FieldDefense;
	private String [] priceDefense;
	private String [] priceAttack;
	private ArrayList<Strategy> defense;
	private ArrayList<Strategy> attack;
	private String[][] meansGame;
	private JTable tableAttacks;
	private JTable probabilities;
	private JTable chooseDefense;
	private JTable chooseAttack;
	private String optimal;
	private Matrix matrix;
	private String[][] prob;
	private String[] headerProb;
	private JTable tableDefense;
	private JTable game;
	private HashMap map;
	private StringWorker worker;
	private Mathematics mat;
	CheckModel modelGame;
	private DefaultTableModel tableModel;
	private Box buttons;
	private JLabel message;
	public GameTable(int coordinateX,int coordinateY,int sizeX,int sizeY, String name) throws SQLException
	{
		super(coordinateX, coordinateY, sizeX, sizeY);
		this.worker = new StringWorker();
		this.mat = new Mathematics();
		this.name = name;
		this.map = new HashMap<String, ArrayList<Integer>>();
		this.info = new JButton("Info");
		build();
        CreateFrame();
	}
	
	@Override
	public void CreateFrame()  
	{
  		try {
			init();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        createPanel();
	}
	
	public void init() throws SQLException {
		String nameForChooseTableAttack[] = {"�", "attack name", "Choose"};
  		meansAttack = new String[FieldAttack.length][3];
  		String nameForChooseTableDefense[] = {"�", "defense name", "Choose"};
  		meansDefense = new String[FieldDefense.length][3];
  		String[] headerProb = {"�����������"};
  		prob =  new String[mat.sumOfCombinations(FieldAttack.length)][1];
  		for(int i = 0; i < prob.length; i++)
  		{
  			prob[i][0] = "0.5";
  		}
  		String[][] container = Database.getInstance().getTable("defense", 5);
  		/*
		for(int i = 0; i < container.length; i++)
		{
			String[] record = new String[container[i].length];
			for(int j = 0; j < container[i].length; j++)
			{
				record[j] = container[i][j];
			}
			Strategy strategy = new Strategy(record);
			defense.add(strategy);
		}
		container = Database.getInstance().getTable("attacks", 5);
		for(int i = 0; i < container.length; i++)
		{
			String[] record = new String[container[i].length];
			for(int j = 0; j < container[i].length; j++)
			{
				record[j] = container[i][j];
			}
			Strategy strategy = new Strategy(record);
			attack.add(strategy);
		}
		*/
  		String[] nameForGameTable = {"������� � ������� ������������ ��� ���������"};
		chooseAttack = buildCheckTable(nameForChooseTableAttack, meansAttack, FieldAttack);
		JTable probabilities = new JTable(prob, headerProb);
		game = new JTable(meansGame, nameForGameTable);
  		chooseDefense = buildCheckTable(nameForChooseTableDefense, meansDefense, FieldDefense);
		Box contents = new Box(BoxLayout.X_AXIS);
        contents.add(new JScrollPane(chooseAttack));
        contents.add(new JScrollPane(probabilities, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
        contents.add(new JScrollPane(game, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
        contents.add(new JScrollPane(chooseDefense));
        setContentPane(contents);
	}
	void build() throws SQLException {
		defense = new ArrayList<Strategy>();
		attack = new ArrayList<Strategy>();
		FieldAttack = Database.getInstance().getFieldFromTable("attacks", "unicname");
		FieldDefense = Database.getInstance().getFieldFromTable("defense", "unicname");
		priceDefense = Database.getInstance().getFieldFromTable("defense", "cost");
		priceAttack = Database.getInstance().getFieldFromTable("attacks", "damage");
		meansGame = new String[0][1];
	}
	
	public JTable buildCheckTable(String[] nameForChooseTable, String[][] means, String[] FieldFromTable) {
		modelGame= new CheckModel(nameForChooseTable);
		for(int i = 1; i <= FieldFromTable.length; i++) {
  			modelGame.addRow(new Object[] {i, FieldFromTable[i - 1], false});
  		}
		return new JTable(modelGame);
	}
	
	public void buildGameTable() throws SQLException {
		defense = new ArrayList<Strategy>();
		attack = new ArrayList<Strategy>();
		int size = 0;
		for(int i = 0; i < chooseDefense.getRowCount(); i++)
		{
			if(chooseDefense.getValueAt(i, 2).equals(true)) {
				size++;
			}
		}
		String[][] container = Database.getInstance().getTable("defense", 5);
		for(int i = 0; i < container.length; i++)
		{
			if(chooseDefense.getValueAt(i, 2).equals(true)) {
				String[] record = new String[container[i].length];
				for(int j = 0; j < container[i].length; j++)
				{
					record[j] = container[i][j];
				}
			Strategy strategy = new Strategy(record);
			defense.add(strategy);
		}
		}
		size = 0;
		for(int i = 0; i < chooseAttack.getRowCount(); i++)
		{
			if(chooseAttack.getValueAt(i, 2).equals(true)) {
				size++;
			}
		}
		container = Database.getInstance().getTable("attacks", 5);
		for(int i = 0; i < container.length; i++)
		{
			if(chooseAttack.getValueAt(i, 2).equals(true)) {
				String[] record = new String[container[i].length];
				for(int j = 0; j < container[i].length; j++)
				{
					record[j] = container[i][j];
				}
			Strategy strategy = new Strategy(record);
			attack.add(strategy);
		}
		}
		strategiesDefense = new TreeSet<ArrayList<String>>();
		strategiesDefense = worker.work(defense);
		strategiesAttack = new TreeSet<ArrayList<String>>();
		strategiesAttack = worker.work(attack);
		HashSet<Pair<String, String>> compability = new HashSet<Pair<String, String>>();
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		String[][] comp = Database.getInstance().getTable("compability_defense", 3);
		for(int i = 0; i < comp.length; i++) {
			compability.add(new Pair<String, String>(comp[i][1], comp[i][2]));
		}
		int prov = 0;
		//delete not compability
		for(ArrayList<String> def:strategiesDefense) {
			prov = 0;
			String[] temp = def.get(0).split(" ");
			for(int d = 0; d < temp.length; d++) {
				for(int j = 0; j < temp.length; j++) {
					if(compability.contains(new Pair<String, String> (defense.get(Integer.parseInt(temp[d]) - 1).getStrategy()[0], defense.get(Integer.parseInt(temp[j]) - 1).getStrategy()[0]))) {
					//if(compability.containsKey(defense.get(Integer.parseInt(temp[d]) - 1).getStrategy()[0]) && defense.get(Integer.parseInt(temp[j]) - 1).getStrategy()[0].equals(compability.get(defense.get(Integer.parseInt(temp[d]) - 1).getStrategy()[0]))) {
						System.out.println("keyyy"+ temp[j]);
						prov++;
					}
			}
			}
			if(prov > 0) {
				array.add(def);
			}
		}
		for(ArrayList<String> def:array) {
			strategiesDefense.remove(def);
		}
		FillContentMainMatrix();
		DefaultTableModel tableModel = new DefaultTableModel(meansGame, new String[mat.sumOfCombinations(FieldDefense.length) + 1]);
		game.setModel(tableModel); 
		game.getTableHeader().setUI(null);
		game.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
		
	
	void FillContentMainMatrix() throws SQLException {
		int sizeX = mat.sumOfCombinations(attack.size()) + 1;
		int sizeY = strategiesDefense.size() + 1;
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
		fillMeansGame();
	}
	
	void fillMeansGame() throws SQLException {
		int i = 0;
		int j = 0;
		int tempSize = 0;
		String[] temp;
		String[] tempAt;
		HashSet<Pair<String, String>> defendAgainst = new HashSet<>();
		String[][] defAgainst = Database.getInstance().getTable("defend_against", 3);
		//fill defenedAgaints of mysqlDb
		for(int k = 0; k < defAgainst.length; k++) {
			defendAgainst.add(new Pair<String, String>(defAgainst[k][1], defAgainst[k][2]));
		}
		System.out.println(defendAgainst.toString());
		//protect exist for storage data about attacks that not realize for strategy
		HashSet<Integer> protect = new HashSet<Integer>();
		for(ArrayList<String> at:strategiesAttack) {
			i++;
			for(ArrayList<String> def:strategiesDefense) {
				j++;
				int sum = 0;
				temp = def.get(0).split(" ");
				tempAt = at.get(0).split(" ");
				for (int d = 0; d < temp.length; d++) {
					sum += Integer.parseInt(defense.get(Integer.parseInt(temp[d]) - 1).getStrategy()[2]);
					for (int a = 0; a < tempAt.length; a++) {
						System.out.println(defendAgainst.contains(new Pair<String, String> ("1","2")));
						if (defendAgainst.contains(new Pair<String,String>(temp[d], attack.get(Integer.parseInt(tempAt[a]) - 1).getStrategy()[0]))) {
						//if (defendAgainst.containsKey(temp[d]) && defendAgainst.get(temp[d]).equals(attack.get(Integer.parseInt(tempAt[a]) - 1).getStrategy()[0])) {
							protect.add(Integer.parseInt(tempAt[a]) - 1);
						}
					}
					//sum += Integer.parseInt(priceDefense[Character.getNumericValue(def.get(0).charAt(d)) - 1]);
				}
				/*
				for(int d = 0; d < def.get(0).length(); d++) {
					sum += Integer.parseInt(defense.get(Character.getNumericValue(def.get(0).charAt(d)) - 1).getStrategy()[2]);
					//sum += Integer.parseInt(priceDefense[Character.getNumericValue(def.get(0).charAt(d)) - 1]);
				}
				for(int a = 0; a < at.get(0).length(); a++) {
					sum += Integer.parseInt(attack.get(Character.getNumericValue(at.get(0).charAt(a)) - 1).getStrategy()[2]);
					//sum += Integer.parseInt(priceAttack[Character.getNumericValue(at.get(0).charAt(a)) - 1]);
				}
				*/
				for (int a = 0; a < tempAt.length; a++) {
					if(!protect.contains(Integer.parseInt(tempAt[a]) - 1)) {
						sum += Integer.parseInt(attack.get(Integer.parseInt(tempAt[a]) - 1).getStrategy()[2]);
					}
				}
				protect = new HashSet<Integer>();
				meansGame[i][j] = Integer.toString(sum);
			}
			j = 0;
		}
		matrix = new Matrix(meansGame);
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
	
	void createSeddleButton() 
	{
		JButton seddle = new JButton("Minimax");
        seddle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		optimal = Integer.toString(matrix.getSeddlePoint());
					message.setText("result with minimax: Defender" + Integer.toString(matrix.getSeddlePoint()));
					infoButton();
            }
        });
        buttons.add(seddle);
	}
	
	void createBaesButton() 
	{
		JButton baes = new JButton("Baes");
        baes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		optimal = Integer.toString(matrix.getBaes(prob));
					message.setText("result with baes: Defender" + Integer.toString(matrix.getBaes(prob)));
					infoButton();
            }
        });
        buttons.add(baes);
	}
	
	void infoButton() 
	{
        info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println(optimal);
            	String[][] info = null;
            	String[] Defend = {"�", "unicname","cost","info","unicgroup"};
            	int counter = 0;
            	int i = 0;
            	int j = 0;
            	for(ArrayList<String> def:strategiesDefense) {
            		i++;
            		if(Integer.toString(i).equals(optimal)) {
            			String[] temp;
            			temp = def.get(0).split(" ");
            			info = new String[temp.length][Defend.length];
            			for(int k = 0; k < temp.length; k++) {
            			for(Strategy strategy:defense) {
                        		j++;
                        		if (temp[k].equals(Integer.toString(j))) {
                        			for(int t = 0; t < Defend.length; t++) {
                        				info[counter][t] = strategy.getStrategy()[t];
                        			}
                        			counter++;
                        		}
            			}
            			j = 0;
            		}
            	}
            	}
            
            	/*
            	String[] Defend = {"Defend", "cost"};
            	System.out.println("Length" + Integer.toString(optimal.length()));
            	String[][] info = new String[optimal.length()][Defend.length];
            	String[] name = new String[optimal.length()];
            	String[] cost = new String[optimal.length()]; 
            	try {
					name = Database.getInstance().getFieldFromTable("defense", "unicname");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	try {
					cost = Database.getInstance().getFieldFromTable("defense", "cost");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	for(int i = 0; i < optimal.length(); i++) {
                	info[i][0] = name[optimal.charAt(i) - 48 - 1];
                	info[i][1] = cost[optimal.charAt(i) - 48 - 1];
            	}
            	*/
            	InfoTable infoTable = new InfoTable(100, 100, 800, 800, info, Defend,"info");
            	infoTable.setVisible(true);
            	infoTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        buttons.add(info);
	}
	
	public void createPanel() 
	{
		buttons = new Box(BoxLayout.Y_AXIS);
		createButton();
		createSeddleButton();
		createBaesButton();
		createMessageError();
		getContentPane().add(buttons, "North");
	}
	
	
	
	void createMessageError() 
	{
		message = new JLabel("");
		buttons.add(message);
	}
}