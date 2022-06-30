package GUI;

import java.awt.*;

import javax.swing.plaf.DimensionUIResource;
import javax.swing.*;
public class MenueBar extends JPanel{
	JButton loesen,einstellungen;
	JPanel buttonGroup;
	MenueBar(){
		//Button Einstellungen
		loesen = new JButton("Lösen");
		loesen.setPreferredSize(new Dimension(250,100));
		loesen.setFont(new Font("",Font.PLAIN,30));
		
		
		einstellungen = new JButton("Einstellungen");
		einstellungen.setPreferredSize(new Dimension(250,100));
		einstellungen.setFont(new Font("",Font.PLAIN,30));
		
		//buttonGroup
		buttonGroup = new JPanel();
		buttonGroup.add(loesen);
		buttonGroup.add(einstellungen);
		buttonGroup.setBackground(Color.white);;
		
		
		//MenueBar Einstellungen
		setPreferredSize(new Dimension(150,150));
		setBackground(Color.white);
		add(buttonGroup);
		
	}
	public JButton getLoesen() {
		return loesen;
	}
	
}
