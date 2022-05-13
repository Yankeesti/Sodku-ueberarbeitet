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
	//Konstuktor
		/**
		 * @param pFelder parm muss genau 9 Felder enthalten
		 */
		neunerFeld(Feld pFelder[]){
			felder = pFelder;
			enthalten = new boolean[9];
			anzahlMoegliche = new byte[9];
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
		
		private void zwilling() {
			//ueberpruefen ob es 2 Zahen in dem neuener Feld gibt welche nur an 2 Feldern moeglich sind
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
		
		private void drilling() {
			for(int feldA = 0; feldA<9; feldA++) {
				if(felder[feldA].getZahlenMoeglich() == 2 || felder[feldA].getZahlenMoeglich() == 3)
					for(int feldB = feldA+1; feldB<9; feldB++) {
						if(felder[feldA].isPossibleDrilling(felder[feldB]))
							for(int feldC = feldB+1; feldC<9; feldC++) {
								if(felder[feldA].isPossibleDrilling(felder[feldC]) && felder[feldB].isPossibleDrilling(felder[feldC]))
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
		private void ausschliesenAusser(byte[] auszuschliessen, Feld[] auslassen) {
			nextFeld:
			for(Feld f:felder) {
				for(Feld ausla: auslassen)
					if(f == ausla)
						continue nextFeld;
				f.ausschliesen(auszuschliessen);
			}
		}
	//Getter und Setter
		
		/**
		 * @return gibt alle moeglichen zwillings kombination an (alle kombination von zahlen welche nur 2 moeglichkeiten haben)
		 */
		private byte[] get2MalMoeglich() {
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
		
}
