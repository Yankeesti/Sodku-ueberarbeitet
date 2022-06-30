package GUI;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;

public class Feld extends JTextField{
	boolean textRichtig = true;
	JButton loesen;
	Feld(JButton loesen){
		this.loesen =loesen;
		setBackground(Color.LIGHT_GRAY);
		setFont(new Font("Coic Sans",Font.PLAIN,50));
		setHorizontalAlignment(JTextField.CENTER); 
		getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(getText().length() == 0) {
					feldRichtig();
				}else
					ueberpruefen();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				ueberpruefen();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(getText().length() == 0) {
					feldRichtig();
				}else
					ueberpruefen();
			}
			
			//ueberprueft ob die Eingabe noch eine Zahl zwischen 1 und 9 ist
			private void ueberpruefen() {
				String text = getText();
				int nummer;
				try {
					nummer = Integer.parseInt(text);
					if(nummer < 1 || nummer >9 )
					{
						feldFalsch();
					}else
						feldRichtig();
					
				}catch(Exception e) {
					feldFalsch();
				}
			}
		});
		
	}
	
	/**
	 * wird aufgerufen wenn das Feld einen Falschen Input enthält z.b. keine Zahl oder nicht zwischen 1 und 9
	 */
	public void feldFalsch() {
		setBackground(Color.red);
		loesen.setEnabled(false);
	}
	
	public void feldRichtig() {
		setBackground(Color.LIGHT_GRAY);
		loesen.setEnabled(true);
	}
	
}
