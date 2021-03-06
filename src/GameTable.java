import java.awt.Dimension;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;
import java.awt.event.ActionEvent;

public class GameTable extends BaseFrame implements InterfaceObject {
	private String name;
	private String probs[][];
	private String viewProb[][];
	private String[][] meansAttack;
	private String[][] meansDefense;
	private String[][] comp;
	HashSet<Pair<String, String>> defendAgainst;
	private JButton info;
	private JLabel messageTime;
	private TreeSet<ArrayList<String>> strategiesDefense;
	private TreeSet<ArrayList<String>> strategiesAttack;
	private String[] FieldAttack;
	private String[] FieldDefense;
	private String[] priceDefense;
	private String[] priceAttack;
	private String[] damage;
	private long time;
	private ArrayList<Strategy> defense;
	private ArrayList<Strategy> attack;
	private String[][] meansGame;
	private String[][] meanOzu;
	private String[][] meanBit;
	private JTable tableAttacks;
	private JTextField cost;
	private JTextField ozu;
	private Box buttonsForGame;
	private JTextField bitDepth;
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
	private JTextField c = new JTextField();

	public GameTable(int coordinateX, int coordinateY, int sizeX, int sizeY, String name)
			throws SQLException, IOException {
		super(coordinateX, coordinateY, sizeX, sizeY);
		this.worker = new StringWorker();
		this.mat = new Mathematics();
		this.name = name;
		this.map = new HashMap<String, ArrayList<Integer>>();
		this.info = new JButton("����������");
		info.setMinimumSize(new Dimension(400, 40));
		info.setMaximumSize(new Dimension(400, 40));
		build();
		CreateFrame();
	}

	@Override
	public void CreateFrame() {
		try {
			init();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			createPanel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void init() throws SQLException {
		String nameForChooseTableAttack[] = { "�", "��������", "�����", "�����������"};
		meansAttack = new String[FieldAttack.length][4];
		String nameForChooseTableDefense[] = { "�", "��������", "�����" };
		meansDefense = new String[FieldDefense.length][3];
		String[] headerProb = { "�����������" };
		prob = new String[1000000][1];
		System.out.println("size" + prob.length);
		probs = Database.getInstance().getTable("prob", 2);
		meanOzu = Database.getInstance().getTable("ozu", 3);
		meanBit = Database.getInstance().getTable("bit_depth", 3);
		viewProb = new String[probs.length][1];
		for (int k = 0; k < viewProb.length; k++) {
			viewProb[k][0] = probs[k][1];
		}
		String[][] container = Database.getInstance().getTable("defense", 5);
		/*
		 * for(int i = 0; i < container.length; i++) { String[] record = new
		 * String[container[i].length]; for(int j = 0; j < container[i].length; j++) {
		 * record[j] = container[i][j]; } Strategy strategy = new Strategy(record);
		 * defense.add(strategy); } container =
		 * Database.getInstance().getTable("attacks", 5); for(int i = 0; i <
		 * container.length; i++) { String[] record = new String[container[i].length];
		 * for(int j = 0; j < container[i].length; j++) { record[j] = container[i][j]; }
		 * Strategy strategy = new Strategy(record); attack.add(strategy); }
		 */
		String[] nameForGameTable = { "����" };
		for (int i = 0; i < meansAttack.length; i++) {
			meansAttack[i][3] = viewProb[i][0];
		}
		chooseAttack = buildCheckTable(nameForChooseTableAttack, meansAttack, FieldAttack,"attack");
		JTable probabilities = new JTable(viewProb, headerProb);
		//getFrames().probabilities.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//probabilities.getColumnModel().getColumn(0).setHeaderValue("�����������");
		//probabilities.getColumnModel().getColumn(0).setMinWidth(80);
		//probabilities.getColumnModel().getColumn(0).setMaxWidth(80);
		game = new JTable(meansGame, nameForGameTable);
		chooseDefense = buildCheckTable(nameForChooseTableDefense, meansDefense, FieldDefense, "defense");
		chooseAttack.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//chooseAttack.getColumnModel().getColumn(1).setMinWidth(300);
		//chooseAttack.getColumnModel().getColumn(1).setMaxWidth(500);
		//chooseDefense.getColumnModel().getColumn(1).setMinWidth(500);
		//chooseDefense.getColumnModel().getColumn(1).setMaxWidth(500);
		Box contents = new Box(BoxLayout.X_AXIS);
		contents.add(new JScrollPane(chooseAttack, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
		JScrollPane pan = new JScrollPane(probabilities, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		//contents.add(pan);
		contents.add(new JScrollPane(chooseDefense, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
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

	public JTable buildCheckTable(String[] nameForChooseTable, String[][] means, String[] FieldFromTable, String name) {
		modelGame = new CheckModel(nameForChooseTable);
		for (int i = 1; i <= FieldFromTable.length; i++) {
			if(name.equals("attack")) {
				modelGame.addRow(new Object[] { i, FieldFromTable[i - 1], false , viewProb[i - 1][0]});
			}
			else {
				modelGame.addRow(new Object[] { i, FieldFromTable[i - 1], false});
			}
		}
		return new JTable(modelGame);
	}

	public void buildGameTable() throws SQLException, NumberFormatException, IOException {
		defense = new ArrayList<Strategy>();
		attack = new ArrayList<Strategy>();
		int size = 0;
		for (int i = 0; i < chooseDefense.getRowCount(); i++) {
			if (chooseDefense.getValueAt(i, 2).equals(true)) {
				size++;
			}
		}
		String[][] container = Database.getInstance().getTable("defense", 5);
		for (int i = 0; i < container.length; i++) {
			if (chooseDefense.getValueAt(i, 2).equals(true)) {
				String[] record = new String[container[i].length];
				for (int j = 0; j < container[i].length; j++) {
					record[j] = container[i][j];
				}
				Strategy strategy = new Strategy(record);
				defense.add(strategy);
			}
		}
		size = 0;
		for (int i = 0; i < chooseAttack.getRowCount(); i++) {
			if (chooseAttack.getValueAt(i, 2).equals(true)) {
				size++;
			}
		}
		container = Database.getInstance().getTable("attacks", 5);
		for (int i = 0; i < container.length; i++) {
			if (chooseAttack.getValueAt(i, 2).equals(true)) {
				String[] record = new String[container[i].length];
				for (int j = 0; j < container[i].length; j++) {
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
		comp = Database.getInstance().getTable("compability_defense", 3);
		for (int i = 0; i < comp.length; i++) {
			compability.add(new Pair<String, String>(comp[i][1], comp[i][2]));
		}
		int prov = 0;
		int sum = 0;
		int bit = 0;
		// delete not compability
		for (ArrayList<String> def : strategiesDefense) {
			prov = 0;
			String[] temp = def.get(0).split(" ");
			// System.out.println(meanOzu[Integer.parseInt(defense.get(Integer.parseInt(temp[d])
			// - 1).getStrategy()[0])]);
			for (int d = 0; d < temp.length; d++) {
				if (meanBit[Integer.parseInt(defense.get(Integer.parseInt(temp[d]) - 1).getStrategy()[0]) - 1][1]
						.equals(bitDepth.getText())) {
					bit++;
				}
				sum = sum + Integer.parseInt(
						meanOzu[Integer.parseInt(defense.get(Integer.parseInt(temp[d]) - 1).getStrategy()[0]) - 1][2]);
				for (int j = 0; j < temp.length; j++) {
					if (compability.contains(
							new Pair<String, String>(defense.get(Integer.parseInt(temp[d]) - 1).getStrategy()[0],
									defense.get(Integer.parseInt(temp[j]) - 1).getStrategy()[0]))) {
						// if(compability.containsKey(defense.get(Integer.parseInt(temp[d]) -
						// 1).getStrategy()[0]) && defense.get(Integer.parseInt(temp[j]) -
						// 1).getStrategy()[0].equals(compability.get(defense.get(Integer.parseInt(temp[d])
						// - 1).getStrategy()[0]))) {
						prov++;
					}
				}
			}
			if (bit != temp.length || sum > Integer.parseInt(ozu.getText()) || prov > 0) {
				array.add(def);
			}
			sum = 0;
			bit = 0;
			prov = 0;
		}
		for (ArrayList<String> def : array) {
			strategiesDefense.remove(def);
		}
		FillContentMainMatrix();
		DefaultTableModel tableModel = new DefaultTableModel(meansGame,
				new String[1000]);
		game.setModel(tableModel);
		game.getTableHeader().setUI(null);
		game.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	void FillContentMainMatrix() throws SQLException, NumberFormatException, IOException {
		int sizeX = mat.sumOfCombinations(attack.size()) + 1;
		int sizeY = strategiesDefense.size() + 1;
		String[] defenderNames = new String[sizeY];
		String[] attackerNames = new String[sizeX];
		meansGame = new String[sizeX][sizeY];
		defenderNames[0] = "";
		for (int i = 2; i <= sizeY; i++) {
			defenderNames[i - 1] = "Defender " + Integer.toString(i - 1);
			meansGame[0][i - 1] = defenderNames[i - 1];
		}

		for (int i = 2; i <= sizeX; i++) {
			attackerNames[i - 1] = "Attacker " + Integer.toString(i - 1);
			meansGame[i - 1][0] = attackerNames[i - 1];
		}
		fillMeansGame();
	}

	void fillMeansGame() throws SQLException, NumberFormatException, IOException {
		int i = 0;
		int j = 0;
		int tempSize = 0;
		String[] temp;
		String[] tempAt;
		defendAgainst = new HashSet<>();
		String[][] defAgainst = Database.getInstance().getTable("defend_against", 3);
		// fill defenedAgaints of mysqlDb
		for (int k = 0; k < defAgainst.length; k++) {
			defendAgainst.add(new Pair<String, String>(defAgainst[k][1], defAgainst[k][2]));
		}
		for (int k = 0; k < prob.length; k++) {
			prob[k][0] = "1.0";
		}
		double luck = 0;
		// protect exist for storage data about attacks that not realize for strategy
		HashSet<Integer> protect = new HashSet<Integer>();
		for (ArrayList<String> at : strategiesAttack) {
			i++;
			tempAt = at.get(0).split(" ");
			for (int a = 0; a < tempAt.length; a++) {
				//prob[i - 1][0] = Double.toString(Double.parseDouble((prob[i - 1][0])) * Double.parseDouble(
				//		viewProb[Integer.parseInt(attack.get(Integer.parseInt(tempAt[a]) - 1).getStrategy()[0]) - 1][0]));
				String p =  (String) chooseAttack.getValueAt(Integer.parseInt(attack.get(Integer.parseInt(tempAt[a])-1).getStrategy()[0]) - 1, 3);
				prob[i - 1][0] = Double.toString(Double.parseDouble((prob[i - 1][0])) * Double.parseDouble(p));
			}
		}
		i = 0;
		int attackDamage = 0;
		for (ArrayList<String> at : strategiesAttack) {
			i++;
			for (ArrayList<String> def : strategiesDefense) {
				j++;
				int sum = 0;
				temp = def.get(0).split(" ");
				tempAt = at.get(0).split(" ");
				for (int d = 0; d < temp.length; d++) {
					sum += Integer.parseInt(defense.get(Integer.parseInt(temp[d]) - 1).getStrategy()[2]);

					for (int a = 0; a < tempAt.length; a++) {
						if (defendAgainst.contains(
								new Pair<String, String>(defense.get(Integer.parseInt(temp[d]) - 1).getStrategy()[0],
										attack.get(Integer.parseInt(tempAt[a]) - 1).getStrategy()[0]))) {
							// if (defendAgainst.containsKey(temp[d]) &&
							// defendAgainst.get(temp[d]).equals(attack.get(Integer.parseInt(tempAt[a]) -
							// 1).getStrategy()[0])) {
							protect.add(Integer.parseInt(tempAt[a]) - 1);
						}
					}
					// sum +=
					// Integer.parseInt(priceDefense[Character.getNumericValue(def.get(0).charAt(d))
					// - 1]);
				}
				/*
				 * for(int d = 0; d < def.get(0).length(); d++) { sum +=
				 * Integer.parseInt(defense.get(Character.getNumericValue(def.get(0).charAt(d))
				 * - 1).getStrategy()[2]); //sum +=
				 * Integer.parseInt(priceDefense[Character.getNumericValue(def.get(0).charAt(d))
				 * - 1]); } for(int a = 0; a < at.get(0).length(); a++) { sum +=
				 * Integer.parseInt(attack.get(Character.getNumericValue(at.get(0).charAt(a)) -
				 * 1).getStrategy()[2]); //sum +=
				 * Integer.parseInt(priceAttack[Character.getNumericValue(at.get(0).charAt(a)) -
				 * 1]); }
				 */
				for (int a = 0; a < tempAt.length; a++) {
					if (!protect.contains(Integer.parseInt(tempAt[a]) - 1)) {
						sum += Integer.parseInt(attack.get(Integer.parseInt(tempAt[a]) - 1).getStrategy()[2]);
					}
				}  
				protect = new HashSet<Integer>();
				meansGame[i][j] = Integer.toString(sum);
			}
			j = 0;
		}
		matrix = new Matrix(meansGame);
		FileWriter writer = new FileWriter("src//java.txt");
		for (int k = 1; k < meansGame.length; k++) {
			for (int k1 = 1; k1 < meansGame[0].length; k1++) {
				writer.write(meansGame[k][k1]);
				writer.write(' ');
			}
			writer.write('\r');
			writer.write('\n');
		}
		writer.close();
	}

	void createButton() throws IOException {
		JButton create = new JButton("�������");
		create.setMinimumSize(new Dimension(200, 30));
		create.setMaximumSize(new Dimension(200, 30));
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					long start = System.currentTimeMillis();
					buildGameTable();
					long end = System.currentTimeMillis();
					time = end - start;
					messageTime.setText("����� ��������" + time + "ms");
					JFrame g = new JFrame();
					g.setVisible(false);
					Box b = new Box(BoxLayout.X_AXIS);
					b.add(new JScrollPane(game, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
							ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
					b.add(buttonsForGame);
					g.setSize(700, 700);
					g.add(b);
					g.setContentPane(b);
					//g.getContentPane().add(buttonsForGame, "North");
					g.setVisible(true);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttons.add(create);
	}

	void createFieldCost() {
		JLabel label = new JLabel();
		label.setText("how much money you want give");
		cost = new JTextField("0");
		buttons.add(label);
		buttons.add(cost);
	}

	void createFieldGurvic() throws IOException {
		JLabel label = new JLabel();
		label.setText("���������� ���������");
		buttonsForGame.add(label);
		c.setMinimumSize(new Dimension(50, 20));
		c.setMaximumSize(new Dimension(50, 20));
		buttonsForGame.add(c);
	}

	void createFieldBitDepth() throws IOException {
		JLabel label = new JLabel();
		label.setText("�����������");
		final int size = 15;
		FileReader reader = new FileReader("src//ozu.txt");
		Scanner sc = new Scanner(reader);
		int index = 0;
		String res = "";
		while (sc.hasNext()) {
			String t = sc.nextLine();
			index++;
			if (index == size) {
				for (int i = 0; i < t.length(); i++) {
					if (t.charAt(i) >= 48 && t.charAt(i) <= 57) {
						res = res + t.charAt(i);
					}
				}
				break;
			}
		}
		reader.close();
		bitDepth = new JTextField(res);
		bitDepth.setMinimumSize(new Dimension(80, 40));
		bitDepth.setMaximumSize(new Dimension(80, 40));
		buttons.add(label);
		buttons.add(bitDepth);
	}

	void createFieldOzu() throws IOException, InterruptedException {
		final int size = 25;
		JLabel label = new JLabel();
		label.setText("���");
		FileReader reader = new FileReader("src//ozu.txt");
		Scanner sc = new Scanner(reader);
		int index = 0;
		String res = "";
		while (sc.hasNext()) {
			String t = sc.nextLine();
			index++;
			if (index == size) {
				for (int i = 0; i < t.length(); i++) {
					if (t.charAt(i) >= 48 && t.charAt(i) <= 57) {
						res = res + t.charAt(i);
					}
				}
				break;
			}
		}
		reader.close();
		ozu = new JTextField(res);
		ozu.setMinimumSize(new Dimension(80, 40));
		ozu.setMaximumSize(new Dimension(80, 40));
		buttons.add(label);
		buttons.add(ozu);
	}

	void createSeddleButton() {
		JButton seddle = new JButton("�������� �������");
		seddle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long start = System.currentTimeMillis();
				optimal = Integer.toString(matrix.getSeddlePoint());
				message.setText("��������� �� �������:�������� �" + optimal);
				time = System.currentTimeMillis() - start;
				messageTime.setText("����� ���������� :" + time + "ms");
				for (ArrayList<String> def : strategiesDefense) {
				}
			}
		});
		buttonsForGame.add(seddle);
	}

	void createMonteButton() {
		JButton monte = new JButton("����� �����");
		monte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long start = System.currentTimeMillis();
				optimal = Integer.toString(matrix.getMonte(10000));
				time = System.currentTimeMillis() - start;
				message.setText("��������� �� ����� �����:�������� �" + optimal);
				messageTime.setText("����� ���������� :" + time + "ms");
			}
		});
		buttonsForGame.add(monte);
	}
	
	void createThompsonButton() {
		JButton monte = new JButton("�������");
		monte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long start = System.currentTimeMillis();
				try {
					optimal = Integer.toString(matrix.getTomphson());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				time = System.currentTimeMillis() - start;
				message.setText("��������� �� ��������:�������� �" + optimal);
				messageTime.setText("����� ���������� :" + time + "ms");
			}
		});
		buttonsForGame.add(monte);
	}

	void createGurvicButton() {
		JButton gurvic = new JButton("�������� �������");
		gurvic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long start = System.currentTimeMillis();
				optimal = Integer.toString(matrix.getGurvic(Double.parseDouble(c.getText())));
				message.setText("��������� �� �������:�������� �" + optimal);
				time = System.currentTimeMillis() - start;
				messageTime.setText("����� ���������� :" + time + "ms");
			}
		});
		buttonsForGame.add(gurvic);
	}

	void createBaesButton() {
		JButton baes = new JButton("�������� ������");
		baes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long start = System.currentTimeMillis();
				optimal = Integer.toString(matrix.getBaes(prob));
				message.setText("��������� �� ������:�������� �" + optimal);
				time = System.currentTimeMillis() - start;
				messageTime.setText("����� ���������� :" + time + "ms");
			}
		});
		buttonsForGame.add(baes);
	}

	void infoButton() {
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[][] info = null;
				int damage = 0;
				int proof[] = new int[priceAttack.length];
				for(int k1 = 0; k1 < priceAttack.length; k1++) {
					proof[k1] = 0;
				}
				String[] Defend = { "�", "��������", "���������", "����", "���", "���", "��������" };
				int counter = 0;
				int i = 0;
				int j = 0;
				for (ArrayList<String> def : strategiesDefense) {
					i++;
					if (Integer.toString(i).equals(optimal)) {
						String[] temp;
						temp = def.get(0).split(" ");
						info = new String[temp.length][Defend.length];
						for (int k = 0; k < temp.length; k++) {
							for (Strategy strategy : defense) {
								j++;
								if (temp[k].equals(Integer.toString(j))) {
									for (int t = 0; t < Defend.length - 2; t++) {
										info[counter][t] = strategy.getStrategy()[t];
									}
									String[][] ozu = new String[100][100];
									try {
										ozu = Database.getInstance().getTable("ozu", 3);
									} catch (SQLException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
									for (int t = 0; t < ozu.length; t++) {
										if (ozu[t][1].equals(strategy.getStrategy()[0])) {
											info[counter][Defend.length - 2] = ozu[t][2];
										}
									}
									String[][] bitDepth = new String[100][100];
									try {
										bitDepth = Database.getInstance().getTable("bit_depth", 3);
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									for (int t = 0; t < bitDepth.length; t++) {
										if (bitDepth[t][2].equals(strategy.getStrategy()[0])) {
											info[counter][Defend.length - 1] = bitDepth[t][1];
										}
									}
									for (int t = 0; t < temp.length; t++) {
										for(int k1 = 0; k1 < priceAttack.length; k1++) {
											if(defendAgainst.contains(new Pair<String, String>(info[counter][0], Integer.toString(k1+1))) && chooseAttack.getValueAt(k1, 2).equals(true)) {
												proof[k1]++;
											}
											if(chooseAttack.getValueAt(k1, 2).equals(false)) {
												proof[k1]++;
											}
										}
									}
									counter++;
								}
							}
							j = 0;
						}
					}
				}

				/*
				 * String[] Defend = {"Defend", "cost"}; System.out.println("Length" +
				 * Integer.toString(optimal.length())); String[][] info = new
				 * String[optimal.length()][Defend.length]; String[] name = new
				 * String[optimal.length()]; String[] cost = new String[optimal.length()]; try {
				 * name = Database.getInstance().getFieldFromTable("defense", "unicname"); }
				 * catch (SQLException e1) { // TODO Auto-generated catch block
				 * e1.printStackTrace(); } try { cost =
				 * Database.getInstance().getFieldFromTable("defense", "cost"); } catch
				 * (SQLException e1) { // TODO Auto-generated catch block e1.printStackTrace();
				 * } for(int i = 0; i < optimal.length(); i++) { info[i][0] =
				 * name[optimal.charAt(i) - 48 - 1]; info[i][1] = cost[optimal.charAt(i) - 48 -
				 * 1]; }
				 */
				for(int k1 = 0; k1 < priceAttack.length; k1++) {
					if(proof[k1] == 0) {
						System.out.println("attack" + k1);
						damage = damage + Integer.parseInt(priceAttack[k1]);
					}
				}
				InfoTable infoTable = new InfoTable(100, 100, 800, 800, info, Defend, "info");
				infoTable.getLavel().setText("������������ ����� �� ���� =" + damage);
				infoTable.setVisible(true);
				infoTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		buttonsForGame.add(info);
	}

	public void createPanel() throws IOException {
		buttons = new Box(BoxLayout.Y_AXIS);
		buttonsForGame = new Box(BoxLayout.Y_AXIS);
		createFieldGurvic();
		createFieldBitDepth();
		try {
			createFieldOzu();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createButton();
		createSeddleButton();
		createBaesButton();
		createMonteButton();
		createGurvicButton();
		createThompsonButton();
		createMessageError();
		infoButton();
		getContentPane().add(buttons, "North");
	}

	void createMessageError() {
		message = new JLabel("");
		message.setMinimumSize(new Dimension(500, 100));
		message.setMaximumSize(new Dimension(500, 100));
		messageTime = new JLabel("");
		buttonsForGame.add(message);
		buttonsForGame.add(messageTime);
		// buttons.add(message);
		// buttons.add(messageTime);
	}
}