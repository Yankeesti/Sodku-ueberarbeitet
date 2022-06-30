package GUI;

import javax.swing.*;
import java.awt.*;

public class Block extends JPanel{
	public Feld felder[];
	Block(JButton loesen){
		felder = new Feld[9];
		for(int i = 0;i<felder.length;i++) {
			felder[i] = new Feld(loesen);
			add(felder[i]);
		}
		setBackground(Color.white);
		setLayout(new GridLayout(3,3,0,0));
	}
}
