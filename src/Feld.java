import java.util.Arrays;

/**
 * @author timheinsberg
 * Objekte dieser Klasse sind die Felder des Sodokus
 */
public class Feld {
	
	//Atribute
		private byte zahl; 				// wenn -1 ist zahl noch unbekannt
		private boolean zahlMoeglich[]; // ein Feld mit insgesamt 9 Felderen welche abspeichern ob die zahl noch möglich ist,
		private byte zahlenMoeglich;	// Speichert wie viele zahlen noch möglich sind
		private byte pos[]; 			// {y,x}
		private int block;				// speichert in welchem Block sich das Feld befindet

	//Konstruktoren
		Feld(byte pPos[]){
			block = -1;
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
				zahlMoeglich = new boolean[9]; // alle Zahlen auf false setzen.
				zahlMoeglich[pZahl-1] = true;
				zahlenMoeglich = 1;
				}
		}
		
		/**
		 * 
		 * @return Kopie von augerufenem Feld
		 */
		public Feld copy() {
			Feld outPut = new Feld(zahl, pos);
			outPut.setMoegliche(zahlMoeglich);
			outPut.setBlock(block);
			return outPut;
		}
		
		/**
		 * Schliest pZahl von den Moeglichen Zahlen aus,
		 * wenn nur noch eine Zahl übrig ist wird diese in zahl abgespeichert
		 * @param auszuschliessende Zahl
		 * @return true wenn ein neuer Wert ausgeschlosen wurde der vorher noch moeglich war und false wenn nicht
		 */
		public boolean ausschliessen(byte pZahl) {
			boolean outPut = false;
			if(zahl == -1) { 									// verhindert das schon identifiziertes Feld erneut verändert wird
				if(zahlMoeglich[pZahl-1]) {
					zahlMoeglich[pZahl-1] = false;
					zahlenMoeglich --;
					outPut = true;
				}
				if(zahlenMoeglich == 1)
				{
					for(byte i = 0;i<zahlMoeglich.length;i++) {
						if(zahlMoeglich[i])
							zahl = (byte)(i+1);
					}
				}
			}
			return outPut;
		}
		
		/**
		 * Schliest alle Werte aus welche in auschliessen true sind
		 * @return true wenn ein neuer Wert ausgeschlosen wurde der vorher noch moeglich war und false wenn nicht
		 */
		public boolean ausschliessen(boolean auszuschliessen[]) {
			boolean outPut = false;
			for(byte i = 0; i<auszuschliessen.length ; i++) {
				if(auszuschliessen[i]) {
					if(!outPut)
					outPut = ausschliessen((byte)(i+1));
					else
					ausschliessen((byte)(i+1));
				}
			}
			return outPut;
		}
		
		/**
		 * Schliest alle Werte aus welche in auschliessen true sind
		 * @param auszuschliessende Zahlen
		 * @return true wenn ein neuer Wert ausgeschlossen wurde der vorher noch möglich war und false wenn nicht
		 */
		public boolean ausschliessen(byte[] auszuschliessen) {
			boolean outPut = false;
			for(byte i = 0; i<auszuschliessen.length ; i++) {
				if(!outPut)
					outPut = ausschliessen(auszuschliessen[i]);
				else
					ausschliessen(auszuschliessen[i]);
			}
			return outPut;
		}
		
		/**
		 * Printet das Feld, wenn die Zahl eindutig ist wird diese in einer Zeile ausgeprintet sonst ein leerzeichen
		 */
		public void print() {
			if(zahl == -1)
				System.out.print(" ");
			else
				System.out.print(zahl);
		}
		
		/**
		 * 
		 * @param Zahl die auf möglich überprüft wird
		 * @return wenn pZahl möglich true sonst false
		 */
		public boolean istMoeglich(int pZahl) {
			return zahlMoeglich[pZahl-1];
		}
		
		/**
		 * gibt true zurück wenn alle Zahlen Möglich sind
		 * @param zu überprüfende Zahlen
		 * @return true wenn alle Zahlen möglich sind, sonst false
		 */
		public boolean istMoeglich(byte pZahlen[]) {
			for(byte i : pZahlen) {
				if(!istMoeglich(i))
					return false;
			}
			return true;
		}
		
		/**
		 * 
		 * @param b
		 * @param c
		 * @return true wenn die beiden Felder gemeinsam mit dem Objekt über welches die Methode aufgeruffen wird einen Drilling bilden
		 */
		public boolean isDrilling(Feld b,Feld c) {
			byte aMoegliche[] = getMoegliche();
			byte bMoegliche[] = b.getMoegliche();
			byte cMoegliche[] = c.getMoegliche();
			
			byte pool[] =MengenOperationen.vereinigen(MengenOperationen.vereinigen(aMoegliche, bMoegliche), cMoegliche);//hier werden jene Zahlen abgespeichert welche in dem drilling enthalten sein dürfen(die von a,b,c moegliche mit laenge 3
				
			if(pool.length == 3)
				return true;
			
			return false;
		}
		
		/**
		 * 
		 * @param a
		 * @param b
		 * @param c
		 * @param anzahlMoegliche
		 * @return true wenn ein hidden Drilling vorliegt, sonst false
		 */
		public static boolean hiddenDrilling(Feld a,Feld b, Feld c, byte[] anzahlMoegliche) {
			byte aMoegliche[] = a.getMoegliche();
			byte bMoegliche[] = b.getMoegliche();
			byte cMoegliche[] = c.getMoegliche();
			
			byte pool[] = MengenOperationen.vereinigen(MengenOperationen.vereinigen(aMoegliche, bMoegliche), cMoegliche); 
			byte count[] = new byte[pool.length]; // hier wird gespeichert wie oft die Zahlen in dem 3er Paar vorkommen
			
			for(int i = 0; i<pool.length;i++) {
				if(a.istMoeglich(pool[i]))
					count[i] ++;
				if(b.istMoeglich(pool[i]))
					count[i] ++;
				if(c.istMoeglich(pool[i]))
					count[i] ++;
			}
			int abgedektCount = 0; // speichert ab wiele der Zahlen aus dem pool nur in der dreier konstelation vorzufinden sind (wenn es 3 sind ist ein hidden driling gefunden)
			int index = 0;
			byte abgedeckt[] = new byte[3];
			for(int i = 0; i<pool.length;i++) {
				if(count[i] == anzahlMoegliche[pool[i]-1]) {
					abgedektCount ++;
					if(abgedektCount > 3)
						return false;
					abgedeckt[index] = pool[i];
					index ++;
				}
			}
			
			if(abgedektCount == 3) {
				return(a.setMoegliche(abgedeckt)|b.setMoegliche(abgedeckt)|c.setMoegliche(abgedeckt));
			}
			return false;
		}
		
		/**
		 * 
		 * @param b
		 * @return true wenn beide Felder die gleichen möglichen haben sonst false
		 */
		public boolean identisch(Feld b) {
			byte aMoeglich[] = getMoegliche();
			byte bMoeglich[] = b.getMoegliche();
			
			if(aMoeglich.length == bMoeglich.length) {
				for(int i = 0; i< aMoeglich.length;i++)
					if(aMoeglich[i] != bMoeglich[i])
						return false;
				return true;
			}
			return false;
		}
		
	//Getter und Setter methoden
		
		/**
		 * setzt zahl auf pZahl und identifiziert damit eindeutig
		 * welche Zahl zu diesem Feld gehört
		 * 
		 * @param pZahl
		 * @retun true wenn pZahl nicht schon vorhereindeutig war(neue informationen) false wenn pZahl schon vorher eindeutig war
		 */
		public boolean setZahl(int pZahl) {
			boolean outPut = false;
			if(zahl == -1)
				outPut = true;
			zahl = (byte)pZahl;
			zahlenMoeglich = 1;
			zahlMoeglich = new boolean[9];
			zahlMoeglich[pZahl -1] = true;
			return outPut;
		}
		
		public byte getZahl() {return zahl;}
		public int getX(){return pos[1];}
		public int getY(){return pos[0];}
		public byte getZahlenMoeglich() {return zahlenMoeglich;}
		public void setBlock(int block) {if(this.block == -1)this.block = block;}
		public int getBlock() {return block;}
		
		/**
		 * 
		 * @return ein byte Array mit möglichen Zahlen für dieses Feld
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
		
		/**
		 * Setzt nur die zahlen welche in p enthalten sind und vor aufruf möglich sind auf möglich
		 * @param p
		 * @return true wenn neue informationen vorliegen (mindestens eine Zahl wurde von den möglichen gestrichen) false wenn nicht
		 */
		public boolean setMoegliche(byte[] p) {
			int temp = zahlenMoeglich;//speichert den allten wert von Zahlen moeglich ab
			zahlenMoeglich = 0;
			for(int i = 0; i<9; i++) {
				if(zahlMoeglich[i] && isIn((byte)(i+1),p))
						zahlenMoeglich ++;
				else
					zahlMoeglich[i] = false;
			}
			return temp != zahlenMoeglich;
		}
		
		public void setMoegliche(boolean[] p) {
			if(zahl == -1) {
			zahlMoeglich = Arrays.copyOf(p, p.length);
			zahlenMoeglich = 0;
			for(int i = 0; i<9; i++)
				if(zahlMoeglich[i])
					zahlenMoeglich ++;}
		}
		/**
		 * 
		 * @param zahl
		 * @param p
		 * @return true wenn zahl in p enthalten
		 */
		private boolean isIn(byte zahl,byte[] p) {
			for(byte pZahl : p) {
				if(zahl == pZahl)
					return true;
			}
			return false;
		}
}
