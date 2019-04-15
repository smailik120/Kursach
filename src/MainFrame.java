import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import WorkWithBd.Database;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class MainFrame {
	/**
	 * @wbp.nonvisual location=191,129
	 */
	/**
	 * Launch the application.
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws SQLException, IOException {
		InputStream is = Runtime.getRuntime().exec(new String[] {"src//ozu.bat"}).getInputStream();
	    int i;
	    while((i = is.read()) != -1)
	    {
	    }
	    is.close();
		BaseFrame frame = new BaseFrame(300,300,300,300);
		JButton attack = new JButton("Attacks");
		attack.setBounds(44, 36, 200, 50);
		frame.getContentPane().add(attack);
		JButton defense = new JButton("Defense");
		defense.setBounds(44, 129, 200, 50);
		frame.getContentPane().add(defense);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String nameTableAttacks = "attacks";
        String[] columnsHeaderAttacks = {"№", "Название", "Цена пропущенной атаки"};
        String[][] arrayAttacks = Database.getInstance().getTable(nameTableAttacks, columnsHeaderAttacks.length);
 		AddTable tableAttacks = new AddTable(100, 100, 800, 800, arrayAttacks, columnsHeaderAttacks,nameTableAttacks);
 		String nameTableDefense = "defense";
        String[] columnsHeaderDefense = {"№", "Название", "стоимость", "Дата", "Тип"};
        String[] namesDefense = {"№", "Название", "стоимость", "Дата", "Тип", "озу", "битность"};
        String[][] arrayDefense = Database.getInstance().getTable(nameTableDefense, columnsHeaderDefense.length);
        String[][] ozu = Database.getInstance().getTable("ozu", 3);
        String[][] bitDepth = Database.getInstance().getTable("bit_depth", 3);
        String[][] resultDefense = new String[arrayDefense.length][columnsHeaderDefense.length + 2]; 
        for(i = 0; i < arrayDefense.length; i++) {
        	for(int j = 0; j < columnsHeaderDefense.length + 2; j++) {
        		if(j < columnsHeaderDefense.length) {
        			resultDefense[i][j] = arrayDefense[i][j];
        		}
        		if(j == columnsHeaderDefense.length) {
        			resultDefense[i][j] = ozu[i][2];	
        		}
        		if(j == columnsHeaderDefense.length + 1) {
        			resultDefense[i][j] = bitDepth[i][1];	
        		}
            }
        }
  		AddTable tableDefense = new AddTable(100, 100, 800, 800, resultDefense, namesDefense,nameTableDefense);
  		GameTable game = new GameTable(300,300,700,700,"game");
  		game.setVisible(true);
  		tableAttacks.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  		tableDefense.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  		game.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  		attack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               tableAttacks.setVisible(true);
            }
        });
  		defense.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               tableDefense.setVisible(true);
            }
        });
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */

}
