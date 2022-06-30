package GUI;

import javax.swing.JFrame;
import java.awt.*;

public class MyFrame extends JFrame{
	public MyFrame(){ 
		MenueBar menue = new MenueBar();
		
		SpielFeld spielFeld = new SpielFeld(menue.getLoesen());
		setSize(1000,1178);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(menue,BorderLayout.SOUTH);
		add(spielFeld,BorderLayout.CENTER);
		setVisible(true);
		System.out.println(spielFeld.blocks[0].felder[0].getWidth());
		System.out.println(spielFeld.blocks[0].felder[0].getHeight());
		
		System.out.println();
		System.out.println(spielFeld.getWidth());
		System.out.println(spielFeld.getHeight());
	}
	
	public int[][] getFeld(){
		int[][] outPut = new int[9][9];
		
		return null;
	}
}
