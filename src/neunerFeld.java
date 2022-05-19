import java.util.ArrayList;
import java.util.List;

/**
 * @author timheinsberg
 *	Ueber Klasse für die Klassen Line und Block
 *	enthält immer neun Felder
 */
public class neunerFeld {
	//Attribute
		protected Feld felder[];
		protected boolean enthalten[];//wenn Feld true --> Zahl(index+1) ist in dem neuner Feld enthalten 
		protected byte anzahlMoegliche[];
		protected List<Feld>[] moeglich; //speichert ab welche Felder für die Zahl noch moeglich sind, Zahl wird angegeben durch position im array
	//Konstuktor
		/**
		 * @param pFelder parm muss genau 9 Felder enthalten
		 */
		neunerFeld(Feld pFelder[]){
			felder = pFelder;
			enthalten = new boolean[9];
			anzahlMoegliche = new byte[9];
			moeglich = new List[9];
			for(int i = 0; i<9;i++) {
				moeglich[i] = new ArrayList<Feld>();
			}
			
			for(Feld p: felder) {//moeglich das erste mal füllen
				byte pMoeglich[] = p.getMoegliche();
				for(byte m:pMoeglich) {
					if(!moeglich[m-1].contains(p))
						moeglich[m-1].add(p);
				}
			}
			aktualisieren();
			
		}
	
	//Methoden
		/**
		 * Aktualisiert das neuner Feld und überprüft ob durch ausschluss verfahren Felder identifiziert
		 * werden können
		 */
		protected void aktualisieren() {
			for(Feld p: felder) { // prüfen welche Zahlen schon in dem neuner Feld enthalten sind
				byte pZahl = p.getZahl();
				if(pZahl>0) {
					enthalten[pZahl-1] = true;
				}
				
			}
			for(Feld p: felder) 
				p.ausschliesen(enthalten);
			
			zaehleMoegliche();
			zwilling();
			drilling();
			moeglicheUeberpruefen();
			aktMoeglicheList();
			
		}
		/**
		 * Zaehl wie viele Felder für jede Zahl moeglich sind und speichert das Ergebniss in anzahl moegliche ab
		 */
		private void zaehleMoegliche() {
			for(int i = 0; i<9 ; i++) {
				anzahlMoegliche[i] = 0;
			}
			for(Feld p:felder) {
				byte pMoegliche[] = p.getMoegliche();
				//Counten wieviele Felder es gibt für die die Zahlen 1-9 möglich sind
				for(int i = 0; i<pMoegliche.length;i++) {
					anzahlMoegliche[pMoegliche[i]-1] ++;
				}
			}
		}
		/**
		 * überprüft ob es eine Ziffer gibt für die nur ein Feld in Frage kommt
		 * wenn ja wird dieses Feld mit der Ziffer besetzt
		 */
		private void moeglicheUeberpruefen() {
			for(int i = 0; i<anzahlMoegliche.length ; i++) {
				if(anzahlMoegliche[i] == 1) {//Zahlgefunden für die nur ein Feld in frage kommt
					//Feld raussuchen
					for(Feld p: felder) {
						if(p.istMoeglich(i+1)) {
							p.setZahl(i+1);
						}
					}
				}
			}
		}
		
		public void print() {
			for(Feld p : felder)
				p.print();
		}
		
		/**
		 * wenn ein Zwilling in dem neuner Feld vorhanden ist wird dieser ausgegeben
		 */
		protected void zwilling() {
			//ueberpruefen ob es 2 Zahlen in dem neuener Feld gibt welche nur an 2 Feldern moeglich sind
			//naked Pair (Felder werden gesucht die 2 zahlen enthalten von dennen es nur 2 moeglichkeiten in dem Feld gibt)
			byte zweimal[] = get2MalMoeglich();
			for(byte a = 0; a < zweimal.length; a++) {
				for(byte b = (byte)(a+1); b < zweimal.length; b++) {
					byte aktPaar[] = {zweimal[a],zweimal[b]};
					for(int feldA = 0; feldA <9; feldA++) {
						if(felder[feldA].istMoeglich(aktPaar))// true --> erstes mögliches Paar für zwilling gefunden
						for(int feldB = feldA+1; feldB <9; feldB++) {
							if(felder[feldB].istMoeglich(aktPaar)){// true --> zwilings Paar gefunden
								felder[feldA].setMoegliche(aktPaar);
								felder[feldB].setMoegliche(aktPaar);
							}
						}
					}
				}
			}
			//Paar gesucht welches 2 moegliche hat und diese 2 moeglichen identisch sind --> die zwei Zahlen können aus allen anderen der neuner Kombination gestrichen werden
			for(int i = 0; i<9; i++) {
				if(felder[i].getZahlenMoeglich() == 2) 
					for(int b = i+1;b<9;b++) {
						if(felder[i].identisch(felder[b]))//zwilling gefunden
							ausschliesenAusser(felder[i].getMoegliche(), new Feld[] {felder[i],felder[b]});
					}
			}
		}
		
		/**
		 * Wenn ein Drilling in dem neuner Feld vorhanden ist werden alle drei drillings felder ausgegen
		 */
		public void drilling() {
			for(int feldA = 0; feldA<9; feldA++) {
				if(felder[feldA].getZahl() == -1)
					for(int feldB = feldA+1; feldB<9; feldB++) {
						if(felder[feldB].getZahl() == -1)
							for(int feldC = feldB+1; feldC<9; feldC++) {
								if(felder[feldC].getZahl() == -1) {
								if(felder[feldA].isDrilling(felder[feldB], felder[feldC]))
									{//Drilling gefunden
										byte auszuschliessen[];
										if(felder[feldA].getZahlenMoeglich() == 3)
											auszuschliessen = felder[feldA].getMoegliche();
										else if(felder[feldB].getZahlenMoeglich() == 3)
											auszuschliessen = felder[feldB].getMoegliche();
										else
											auszuschliessen = felder[feldC].getMoegliche();
										
										Feld[] auslassen = {felder[feldA],felder[feldB],felder[feldC]};
										ausschliesenAusser(auszuschliessen, auslassen);
									}else {//nach Hidden Drilling suchen (3 Felder enthalten alle möglichkeiten für 3 Zahlen)
										Feld.hiddenDrilling(felder[feldA],felder[feldB],felder[feldC],anzahlMoegliche);
									}
								}
							}
					}
			}
		}
		
		/**
		 * schliest die zahlen in auszuschliessen aus allen Feldern der neuner Kombination aus
		 * und lässt die Felder aus auslassen aus.
		 * @param auszuschliessen
		 * @param auslassen
		 */
		public void ausschliesenAusser(byte[] auszuschliessen, Feld[] auslassen) {
			nextFeld:
			for(Feld f:felder) {
				for(Feld ausla: auslassen)
					if(f == ausla)
						continue nextFeld;
				f.ausschliesen(auszuschliessen);
			}
		}
		public void ausschliesenAusser(int zahl, List<Feld> auslassen) {
			nextFeld:
				for(Feld f: felder) {
					for(int i = 0; i<auslassen.size();i++) 
						if(f == auslassen.get(i))
							continue nextFeld;
					f.ausschliesen((byte) zahl);
					
				}
		}
		
		public void ausschliesenAusser(int zahl,Feld[] auslassen) {
			nextFeld:
				for(Feld f: felder) {
					for(Feld ausla: auslassen)
						if(f == ausla)
							continue nextFeld;
					f.ausschliesen((byte) zahl);
					
				}
		}
		/**
		 *aktualisiert die Liste welche alle möglichen Felder für jede Zahl enthält
		 */
		protected void aktMoeglicheList() {
			for(int i = 0; i<9; i++) {
				for(int indexInList = 0; indexInList<moeglich[i].size();indexInList++) {
					if(!moeglich[i].get(indexInList).istMoeglich(i+1))
						moeglich[i].remove(indexInList);
				}
			}
		}
		
		

	//Getter und Setter
		/**
		 *
		 * @param zahl
		 * @return alle Felder bei denen Zahl moeglich ist
		 */
		public Feld[] getFelder(int zahl) {
			List<Feld> moegliche = new ArrayList<Feld>();
			for(Feld f: felder) {
				if(f.istMoeglich(zahl))
					moegliche.add(f);
			}
			Feld outPut[] = new Feld[moegliche.size()];
			for(int i = 0;i<outPut.length;i++) {
				outPut[i] = moegliche.get(i);
			}
			return outPut;
		}
		/**
		 * @return gibt alle Zahle an welche nur 2 mal möglich sind
		 */
		public byte[] get2MalMoeglich() {
			zaehleMoegliche();
			List<Byte> moegliche = new ArrayList<Byte>();
			for(int i = 0; i<9; i++) {
				if(anzahlMoegliche[i] == 2) {
					moegliche.add((byte)(i+1));
				}
			}
			byte outPut[] = new byte[moegliche.size()]; //List in Array umwandeln | alle Werte abgespeichert die genau zweimal vorkommen
			for(int i = 0; i<outPut.length;i++) {
				outPut[i] =(moegliche.get(i));
			}
			return outPut;
		}
		public List<Feld>[] getMoeglich(){return moeglich;}
}
