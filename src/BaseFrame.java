import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
public class BaseFrame extends JFrame implements InterfaceObject{
	private int coordinateX;
	private int coordinateY;
	private int sizeX;
	private int sizeY;
	BaseFrame(int coordinateX,int coordinateY,int sizeX,int sizeY)
	{
		super();
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		initialize();
	}
	public void initialize() 
	{
		this.setBounds(coordinateX, coordinateY, sizeX, sizeY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
	}
	public void CreateFrame()  {
	}
}
