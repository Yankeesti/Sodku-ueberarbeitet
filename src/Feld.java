
/**
 * @author timheinsberg
 * Objekte dieser Klasse sind die Felder des Sodokus
 */
public class Feld {
	//Atribute
		private byte zahl; // wenn -1 ist zahl noch unbekannt
		private boolean zahlMoeglich[]; // ein Feld mit insgesamt 9 Felderen welche abspeichern ob die zahl noch moeglich ist,
										// felder sind um 1 nach links verschoben(z.b. wenn zahlMoeglich[0] == true -> 1 ist moeglich).
		private byte zahlenMoeglich;
		private byte pos[]; // {y,x}
		
		Feld(byte pPos[]){
			zahl = -1;
			zahlMoeglich = new boolean[9];
			for(int i = 0; i<9; i++)
				zahlMoeglich[i] = true;
			zahlenMoeglich = 9;
			pos = pPos;
		}
		Feld(byte pZahl,byte pos[]){
			this(pos);
			if(pZahl>0 && pZahl <=9) {
				zahl = pZahl;
				zahlMoeglich = new boolean[9]; // alle zahlen auf false setzen.
				zahlMoeglich[pZahl-1] = true;
				zahlenMoeglich = 1;
				}
		}
		
		/**
		 * Schliest zahl von den Moeglichen Zahlen aus,
		 * wenn nur noch eine Zahl übrig ist wird diese in zahl abgespeichert
		 * @param zahl
		 */
		public void ausschliesen(byte pZahl) {
			if(zahl == -1) { // verhindert das schon identifiziertes Feld erneut veraendert wird
				if(zahlMoeglich[pZahl-1]) {
					zahlMoeglich[pZahl-1] = false;
					zahlenMoeglich --;
				}
				if(zahlenMoeglich == 1)
				{
					for(byte i = 0;i<zahlMoeglich.length;i++) {
						if(zahlMoeglich[i])
							zahl = (byte)(i+1);
					}
				}
			}
		}
		/**
		 * schliest alle werte aus welche in auschliesen true sind
		 * @param auszuschliesen
		 */
		public void ausschliesen(boolean auszuschliesen[]) {
			for(byte i = 0; i<auszuschliesen.length ; i++) {
				if(auszuschliesen[i]) {
					ausschliesen((byte)(i+1));
				}
			}
		}
		
		/**
		 * printet das Feld wenn eindeutig die Zahl sonst ein leerzeichen
		 */
		public void print() {
			if(zahl == -1)
				System.out.print(" ");
			else
				System.out.print(zahl);
		}
		/**
		 * 
		 * @param pZahl
		 * @return wenn pZahl moeglich true sonst false
		 */
		public boolean istMoeglich(int pZahl) {
			return zahlMoeglich[pZahl-1];
		}
		
		
	//Getter und Setter methoden
		/**
		 * setzt zahl auf pZahl und identifiziert damit eindeutig
		 * welche Zahl zu diesem Feld gehört
		 * 
		 * @param pZahl
		 */
		public void setZahl(int pZahl) {
			zahl = (byte)pZahl;
			zahlenMoeglich = 1;
			zahlMoeglich = new boolean[9];
			zahlMoeglich[pZahl -1] = true;
		}
		
		/**
		 * @return zahl
		 */
		public byte getZahl() {
			return zahl;
		}
		
		/**
		 * 
		 * @return ein byte Array mit moeglichen Zahlen für dieses Feld
		 */
		public byte[] getMoegliche() {
			byte outPut[] = new byte[zahlenMoeglich];
			int index = 0;
			for(int i = 0; i<outPut.length;i++) {
				for(int p = index; p<9;p++) {
					if(zahlMoeglich[p]) {
						index = p+1;
						outPut[i] = (byte)index;
						break;
					}
				}
			}
			return outPut;
		}
		

}
