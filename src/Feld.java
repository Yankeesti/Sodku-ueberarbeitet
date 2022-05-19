import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		private int block;//speicher ab in welchem Block sich das Feld befindet
		
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
		 * schliest alle werte aus welche in auschliesen true sind
		 * @param auszuschliesen
		 */
		public void ausschliesen(byte[] auszuschliessen) {
			for(byte i = 0; i<auszuschliessen.length ; i++) {
				ausschliesen(auszuschliessen[i]);
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
		/**
		 * gibt true zurück wenn alle Zahlen Moeglich sind
		 * @param pZahlen
		 * @return
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
		 * @param a
		 * @return true wenn Feld a ein möglicher drillings Partner ist
		 */
		public boolean isDrilling(Feld b,Feld c) {
			byte aMoegliche[] = getMoegliche();
			byte bMoegliche[] = b.getMoegliche();
			byte cMoegliche[] = c.getMoegliche();
			
			byte pool[] =vereinigen(vereinigen(aMoegliche, bMoegliche), cMoegliche);//hier werden jene Zahlen abgespeichert welche in dem drilling enthalten sein dürfen(die von a,b,c moegliche mit laenge 3
				
			if(pool.length == 3)
				return true;
			
			return false;
		}
		
		public static void hiddenDrilling(Feld a,Feld b, Feld c, byte[] anzahlMoegliche) {
			byte aMoegliche[] = a.getMoegliche();
			byte bMoegliche[] = b.getMoegliche();
			byte cMoegliche[] = c.getMoegliche();
			
			byte pool[] = vereinigen(vereinigen(aMoegliche, bMoegliche), cMoegliche); 
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
					abgedeckt[index] = pool[i];
					index ++;
				}
			}
			if(abgedektCount == 3) {
				a.setMoegliche(abgedeckt);
				b.setMoegliche(abgedeckt);
				c.setMoegliche(abgedeckt);
			}
		}
		
		/**
		 * 
		 * @param b
		 * @return true wenn beide Felder die gleichen Moeglichenm haben sonst false
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
		
		public int getX(){return pos[1];}
		public int getY(){return pos[0];}
		
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
		/**
		 * setzt nur die zahlen welche in p enthalten sind und vor aufruf moeglich sind auf moeglich
		 * @param p
		 */
		public void setMoegliche(byte[] p) {
			zahlenMoeglich = 0;
			for(int i = 0; i<9; i++) {
				if(zahlMoeglich[i] && isIn((byte)(i+1),p))
						zahlenMoeglich ++;
				else
					zahlMoeglich[i] = false;
			}
			
		}
		
		public byte getZahlenMoeglich() {
			return zahlenMoeglich;
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
		
		public static byte[] vereinigen(byte[] a,byte[] b) {
			List<Byte> elements = new ArrayList<Byte>();
				for(byte bA : a)
					elements.add(bA);
				
				enthalten:
					for(byte bB:b) {
						for(byte bA:a)
						{
							if(bB == bA)
								continue enthalten;
						}
						elements.add(bB);
					}
				Collections.sort(elements);;
			byte outPut[] = new byte[elements.size()];
			for(int i = 0; i<outPut.length;i++)
				outPut[i] = elements.get(i);
			return outPut;
		}
		
		public void setBlock(int block) {if(this.block == -1)this.block = block;}
		public int getBlock() {return block;}
		
		//Methoden zum testen wen Projekt fertig ist löschen
		public void setPos(int y,int x) {
			pos[0] = (byte)y;
			pos[1] = (byte)x;
		}
		/**
		 * setzt die moeglichen unabhängig davon ob sie vorher moeglich waren
		 */
		public void setMoeglicheHard(byte[] p) {
			zahlenMoeglich = (byte)p.length;
			zahlMoeglich = new boolean[9];
			nextByte:
			for(int i = 0; i<9;i++) {
				for(byte pzahl: p) {
					if(pzahl == i+1) {
						zahlMoeglich[i] = true;
						continue nextByte;
					}
				}
			}
		}
		
}
