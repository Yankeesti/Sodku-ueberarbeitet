package GUI;

import javax.swing.*;
import java.awt.*;

public class SpielFeld extends JPanel{
	public Block blocks[];
	SpielFeld(JButton loesen){
		//Blöcke erstellen
		blocks = new Block[9];
		for(int i = 0; i<9 ; i++) {
			blocks[i] = new Block(loesen);
			add(blocks[i]);
		}
		setBackground(Color.black);
		
		setLayout(new GridLayout(3,3,10,10));
	}
}
