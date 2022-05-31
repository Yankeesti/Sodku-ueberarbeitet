import java.util.ArrayList;
import java.util.List;

/**
 * Spiegelt as Spielfeld mit allen Methoden zum lösen da
 * @author timheinsberg
 *
 */
public class Sodoku {
	private Feld[][] spielfeld;
	
	private Linie horizontal[],vertikal[]; //Linien sind von oben nach unten und von rechts nach links durchnummerriert (siehe Zeichung aufteilung.jpeg)
	private Block bloecke[];				//Blöcke sind von Links nach recht und oben nach unten durch nummerriert (siehe Zeichung aufteilung.jpeg )
	private List<Vormerkung> vormerkungen;
	Sodoku(Feld[][] pSpielfeld){
		horizontal = new Linie[9];
		vertikal = new Linie[9];
		bloecke = new Block[9];
		initialisieren(pSpielfeld);
		vormerkungen = new ArrayList<Vormerkung>();
	}
	
	private void initialisieren(Feld[][] spielfeld) {
		this.spielfeld = spielfeld;
		//linien initialisieren
				for(int i = 0; i<9;i++) {
					horizontal[i] = new Linie(spielfeld[i]);
					//Vertikale Linien initialisieren
					Feld vLinie[] = new Feld[9];
					for(int y = 0; y<9;y++){
						vLinie[y] = spielfeld[y][i];
					}
					vertikal[i] = new Linie(vLinie);
					//Bloecke initialisieren
				
					Feld[] block = new Feld[9];
						byte[] obenLinks = getEcke((byte)i);
						byte index =0;
						for(int reihe = obenLinks[0];reihe < obenLinks[0]+3;reihe++) {
							for(int zeile = obenLinks[1]; zeile < obenLinks[1]+3;zeile++) {
								block[index] = spielfeld[reihe][zeile];
								spielfeld[reihe][zeile].setBlock(i);
								index++;
							}
						}
					bloecke[i] = new Block(block);
				}
	}
//	/**
//	 * loest das Sodoku
//	 * @retu
//	 */
//	public void loesen() {
//		boolean veraenderung = false; // speichert ab ob sich auf dem spielfeld etwas veraendert hat
//		for(int i = 0; i<1000; i++){
//			veraenderung = false;
//			if(aktualisieren()) veraenderung = true;
//			if(linienUeberpruefung()) veraenderung = true;
//			if(aktualisieren()) veraenderung = true;
//			if(zweierKette()) veraenderung = true;
//			if(veraenderung == false)
//				//Feld bei dem am wenigsten zahlen moeglich sind eine Zahl raten 
//				break;
//		}
//		
//	}
	
	/**
	 * loest das Sodoku
	 * gibt die Methode false wieder heißt dies das eine Zahl falsch geraten wurde
	 * @return true wenn das sodoku geloest wurd und false wenn ein Fehler Sodoku vorliegt
	 */
	public boolean loesen() {
		boolean veraenderung = false; // speichert ab ob sich auf dem spielfeld etwas veraendert hat
		if(aktualisieren()) veraenderung = true;
		if(linienUeberpruefung()) veraenderung = true;
		if(aktualisieren()) veraenderung = true;
		if(zweierKette()) veraenderung = true;
		
		if(!richtig()) return false; // bei einer Raten Methode wurde falsch geraten
		
		if(veraenderung) return loesen();
		else { //entweder wurde das sodoku gelöst oder es muss geraten werden
			if(fertig())return true;
			return raten();
		}
	}
	/**
	 * speichert das alte spielfeld ab und raet bei einem Feld bei dem die wenigsten Zahlen moeglich sind eine Zahl und loest das Sodoku mit dem
	 * geratenen Feld weiter, sollte das Sodoku so Falsch sein kehrt die Methode zu dem alten spielFeld zurück und wählt die nächste Zahl bei dem Feld
	 * ist das Sodoku bei der letzten Zahl immernoch Falsch gibt die Methode False zurück, dies heist das eine geratene Zahl davor falsch war
	 * @return true wenn das Sodoku geloest wurde sonst false
	 */
	
	public boolean raten() {
		//altes spielfeld abspeichern
		Feld[][] altesFeld = copy(spielfeld);
		
		Feld kleinsteAnzahlMoegliche = spielfeld[0][0];
		int probiert = 0; //speichert ab welche moegliche zahl des feldes als letztes probiert wurde
		int anzahl = spielfeld[0][0].getZahlenMoeglich();
		if(spielfeld[0][0].getZahl() != -1)
			anzahl = 10;
		found:
		for(int y = 0; y<9;y++) {
			for(int x = 0; x<9;x++) {
				if(spielfeld[y][x].getZahlenMoeglich() == 2) {
					kleinsteAnzahlMoegliche = spielfeld[y][x];
					anzahl = spielfeld[y][x].getZahlenMoeglich();
					break found;		
				}
				else if(spielfeld[y][x].getZahlenMoeglich() < anzahl){
					kleinsteAnzahlMoegliche = spielfeld[y][x];
					anzahl = spielfeld[y][x].getZahlenMoeglich();
				}
			}
		}
		int pos[] = {kleinsteAnzahlMoegliche.getY(),kleinsteAnzahlMoegliche.getX()};// spichert die Position von dem zu ratenden Feld ab
		byte moegliche[] = kleinsteAnzahlMoegliche.getMoegliche();
		// Feld mit der Kleinsten anzahl moeglichen steht nun in kleinsteAnzahlMoegliche
		while(true) {
			this.spielfeld = copy(altesFeld);
			kleinsteAnzahlMoegliche = this.spielfeld[pos[0]][pos[1]];
			kleinsteAnzahlMoegliche.setZahl(moegliche[probiert]);
			initialisieren(this.spielfeld);
			boolean boolGeloest = loesen();
			if(boolGeloest)
				return true; // das sodoku wurde geloest
			else
				if(probiert >= anzahl-1) {
					this.spielfeld = altesFeld;
					return false; // bei einem raten vorher muss etwas schiefgelaufen sein
					}
			probiert ++;
		}
		
	}
	
	private Feld[][] copy(Feld[][] p){
		Feld outPut[][] = new Feld[p.length][];
		
		for(int i = 0; i<p.length;i++) {
			outPut[i] = new Feld[p[i].length];
			for(int i2 = 0;i2< outPut[i].length ;i2++) {
				outPut[i][i2] = p[i][i2].copy();
			}
		}
		
		return outPut;
	}
	
	/**
	 * 
	 * @return true wenn das Sodoku noch den Regeln entsricht, sonnst false
	 */
	private boolean richtig() {
		for(int i = 0; i<9; i++) {
			//alle linien und blöcke auf richtigkeit überprüfen
			if(!(horizontal[i].richtig() && vertikal[i].richtig() && bloecke[i].richtig())) { //ein neuner Feld ist nicht richtig
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @return true wenn das sodoku gelöst wurde und false wenn nicht
	 */
	private boolean fertig() {
		for(int i = 0; i<9; i++) {
			//alle linien und blöcke auf richtigkeit überprüfen
			if(!(horizontal[i].fertig() && vertikal[i].fertig() && bloecke[i].fertig())) { //ein neuner Feld ist nicht richtig
				return false;
			}
		}
		return richtig();
	}
	
	/**
	 * arbeitet nach dem Prizipen in diesem Video : https://www.youtube.com/watch?v=QTlmYXAMgLE
	 * verwendet die loesungs ansaetze der xWing und der scyscraper Methoden
	 * @return true wenn der aufruf etwas bei den Feldern des Feldes veraendert hat sonst false
	 */
	public boolean linienUeberpruefung() {
		boolean outPut = false;
		Feld eckPunkte[] = new Feld[4];
	//mögliche Säulen finden
		//Vertikalen Linien nach Säulen durchsuchen
		nextLoop:
		for(int a = 0;a<9;a++) {
			byte aZweiMoeglich[] = vertikal[a].get2MalMoeglich();
			for(int b = a+1;b<9;b++) {
				//suchen ob b eine Gleiche Zahl wie a 2 mal möglich hat 
				byte bZweiMoeglich[] = vertikal[b].get2MalMoeglich();
				byte[] durchschnitt = MengenOperationen.duchschnitt(aZweiMoeglich, bZweiMoeglich);
				if(durchschnitt != null) {//--> a und b haben eine oder mehr Zahlen gleich wo nur 2 Felder in Frage kommen
					//überprüfen ob es in a und b 2 Felder mit einer der Zahlen aus durschnitt und die Linear zur X/Y Achse verlaufen
					for(byte durchZahl : durchschnitt) {
						Feld felderMoeglichA[] = vertikal[a].getFelder(durchZahl);
						Feld felderMoeglichB[] = vertikal[b].getFelder(durchZahl);
						if(felderMoeglichA.length != 2 || felderMoeglichB.length != 2)
							continue nextLoop;
						if(xWing(felderMoeglichA, felderMoeglichB, durchZahl)) outPut = true;
						eckPunkte = sykscraperMoeglich(felderMoeglichA,felderMoeglichB);
						if(eckPunkte != null) {//Skyscraper gefunden
							if(bloecke[eckPunkte[2].getBlock()].ausschliessenWoY(durchZahl,eckPunkte[3].getY())) outPut = true;
							if(bloecke[eckPunkte[3].getBlock()].ausschliessenWoY(durchZahl,eckPunkte[2].getY())) outPut = true;
							if(aktualisieren()) outPut = true;
							continue nextLoop;
						}
					}
				}
			}
		}
		nextLoop:
		for(int a = 0;a<9;a++) {//Horizontale Linien nach Säulen durchsuchen
			byte aZweiMoeglich[] = horizontal[a].get2MalMoeglich();
			if(aZweiMoeglich != null)
			for(int b = a+1;b<9;b++) {
				//suchen ob b eine Gleiche Zahl wie a 2 mal möglich hat 
				byte bZweiMoeglich[] = horizontal[b].get2MalMoeglich();
				if(aZweiMoeglich != null) {
					byte[] durchschnitt = MengenOperationen.duchschnitt(aZweiMoeglich, bZweiMoeglich);
					if(durchschnitt != null) {//--> a und b haben eine oder mehr Zahlen gleich wo nur 2 Felder in Frage kommen
						//überprüfen ob es in a und b 2 Felder mit einer der Zahlen aus durschnitt und die Linear zur X/Y Achse verlaufen
						for(byte durchZahl : durchschnitt) {
							Feld felderMoeglichA[] = horizontal[a].getFelder(durchZahl);
							Feld felderMoeglichB[] = horizontal[b].getFelder(durchZahl);
							if(felderMoeglichA.length != 2 || felderMoeglichB.length != 2)
								continue nextLoop;
							if(xWing(felderMoeglichA, felderMoeglichB, durchZahl)) outPut = true;
							eckPunkte = sykscraperMoeglich(felderMoeglichA,felderMoeglichB);
							if(eckPunkte != null) {//Skyscraper gefunden
								if(bloecke[eckPunkte[2].getBlock()].ausschliessenWoX(durchZahl,eckPunkte[3].getX())) outPut = true;
								if(bloecke[eckPunkte[3].getBlock()].ausschliessenWoX(durchZahl,eckPunkte[2].getX())) outPut = true;
								if(aktualisieren()) outPut = true;
								continue nextLoop;
							}
						}
					}
				}
			}
		}
		return outPut;
	}
	
	/**
	 * ueberpruft ob die Felder aus a zusammen mit den FEldern aus b einen X Wing bilden, 
	 * wenn ja schliest die Methode daruas rueckschluesse und gibt true zurueck.
	 * sollte kein xWing vorliegen gibt die Methode False zurück.
	 * @return true wenn der aufruf etwas bei den Feldern des Blocks veraendert hat sonst false
	 */
	private boolean xWing(Feld a[], Feld b[], int zahl) {
		boolean outPut = false;
		if(a[0].getX() == b[0].getX() && a[1].getX() == b[1].getX()) {//Xwing gefunden --> auf allen beiden vetikalen linien ( a[0] und a[1]) kann zahl geloescht werden
			if(vertikal[a[0].getX()].ausschliesenAusser(zahl, new Feld[] {a[0],b[0]})) outPut = true;
			if(vertikal[a[1].getX()].ausschliesenAusser(zahl, new Feld[] {a[1],b[1]})) outPut = true;
			if(aktualisieren()) outPut = true;
		}else if(a[0].getY() == b[0].getY() && a[1].getY() == b[1].getY()) {//Xwing gefunden --> auf allen beiden horizontalen linien ( a[0] und a[1])kann zahl geloescht werden
			if(horizontal[a[0].getY()].ausschliesenAusser(zahl, new Feld[] {a[0],b[0]})) outPut = true;
			if(horizontal[a[1].getY()].ausschliesenAusser(zahl, new Feld[] {a[1],b[1]})) outPut = true;
			if(aktualisieren()) outPut = true;
		}
		return outPut;
	}
	
	/**
	 * überprüft das Spiel feld nach der 2-er Kette 
	 * erklärung: https://www.youtube.com/watch?v=QTlmYXAMgLE
	 * @return true wenn der aufruf etwas bei den Feldern des Blocks veraendert hat sonst false
	 */
	private boolean zweierKette() {
		boolean outPut = false;
		//2 String Kite
		for(int iV = 0; iV<9; iV++) {
			byte[] aZweiMoeglich = vertikal[iV].get2MalMoeglich();
			if(aZweiMoeglich != null) {
				for(int iH = 0; iH <9; iH++) {
					//Anfang 2 String Kite
					byte[] bZweiMoeglich = horizontal[iH].get2MalMoeglich();
					byte[] durchschnitt = MengenOperationen.duchschnitt(aZweiMoeglich, bZweiMoeglich);
					if(durchschnitt != null) {
						for(byte durchZahl : durchschnitt) {
							Feld felderMoeglichA[] = vertikal[iV].getFelder(durchZahl);
							Feld felderMoeglichB[] = horizontal[iH].getFelder(durchZahl);
							if(felderMoeglichA.length != 2 || felderMoeglichB.length != 2)
								continue;
							Feld nichtVerbunden[] = verbunden2String(durchZahl,felderMoeglichA, felderMoeglichB);
							if(nichtVerbunden != null) {
								// 2er String gefunden
								//schlusfolgerung aus 2er String kette ziehen
								if(spielfeld[nichtVerbunden[0].getY()][nichtVerbunden[1].getX()].ausschliesen(durchZahl)) outPut = true;
								if(aktualisieren()) outPut = true;
							}
						}
					}
				}
			}
		}
		//Turbot Fish
		
		for(int iL = 0; iL <9; iL++) {
			byte[] vZweiMoeglich = vertikal[iL].get2MalMoeglich();
			byte[] hZweiMoeglich = horizontal[iL].get2MalMoeglich();
			if(vZweiMoeglich != null && hZweiMoeglich != null) {
				for(int iB = 0; iB<9; iB++) {
					byte[] bZweiMoeglich = bloecke[iB].get2MalMoeglich();
					byte durchschnittV[] = MengenOperationen.duchschnitt(vZweiMoeglich, bZweiMoeglich);
					byte durchschnittH[] = MengenOperationen.duchschnitt(hZweiMoeglich, bZweiMoeglich);
					if(durchschnittV != null) {
						for(byte durchZahl : durchschnittV) {
							Feld felderMoeglichV[] = vertikal[iL].getFelder(durchZahl);
							Feld felderMoeglichB[] = bloecke[iB].getFelder(durchZahl);
							if(felderMoeglichV.length != 2 || felderMoeglichB.length != 2)
								continue;
							Feld nichtVerbunden[] = verbundenTurbot(durchZahl,felderMoeglichV, felderMoeglichB);
							if(nichtVerbunden != null) {
								if(spielfeld[nichtVerbunden[0].getY()][nichtVerbunden[1].getX()].ausschliesen(durchZahl)) outPut = true;
								if(aktualisieren()) outPut = true;
							}
						}
					}
					if(durchschnittH != null) {
						for(byte durchZahl : durchschnittH) {
							Feld felderMoeglichH[] = horizontal[iL].getFelder(durchZahl);
							Feld felderMoeglichB[] = bloecke[iB].getFelder(durchZahl);
							if(felderMoeglichH.length != 2 || felderMoeglichB.length != 2)
								continue;
							Feld nichtVerbunden[] = verbundenTurbot(durchZahl,felderMoeglichH, felderMoeglichB);
							if(nichtVerbunden != null) {
								if(spielfeld[nichtVerbunden[1].getY()][nichtVerbunden[0].getX()].ausschliesen(durchZahl)) outPut = true;
								if(aktualisieren()) outPut = true;
							}
						}
					}
				}
			}
		}
		return outPut;
	}
	/**
	 * hilfsmethode für zweier Kette
	 * @param a
	 * @param b
	 * @return wenn alle 4 Felder unterschiedlich sind und eine Verbindung(gleiche Block bei einem Feld aus a mit einem Feld aus b) werden die Felderwieder gegeben welche keine verbindung haben, sonst null
	 */
	private Feld[] verbunden2String(int zahl,Feld[] a, Feld[] b) {
		if(a[0] == b[0] || a[0] == b[1] || a[1] == b[0] || a[1] == b[1] || a[0].getBlock() == a[1].getBlock() || b[0].getBlock() == b[1].getBlock() )
			return null;
		 for(int iA = 0; iA <2 ; iA ++) {
			 for(int iB = 0; iB<2;iB++)
				 if(a[iA].getBlock() == b[iB].getBlock()) {//zweier kette gefunden
					 Feld[] feldPaar1 = new Feld[2]; //in FeldPaar eins wird das feld aus a gespeichert welches die verbindung zwischen a und b herstellt(gleicher block) und sein gegemstück
					 Feld[] feldPaar2 = new Feld[2];
					 feldPaar1[0] = a[iA];
					 feldPaar2[1] = b[iB];
					
					 
					 Feld outPut[] = new Feld[2];
					 if(iA == 0) 
						 outPut[0] = a[1];
					 else
						 outPut[0] = a[0];
					 
					 if(iB == 0)
						 outPut[1] = b[1];
					 else
						 outPut[1] = b[0];
					 feldPaar1[1] = outPut[1];
					 feldPaar2[0] = outPut[0];
					 Vormerkung temp = new Vormerkung(zahl, feldPaar1, feldPaar2);
					 
					 if(!vormerkungEnthalten(temp))
						 vormerkungen.add(temp);
					 return outPut;
				 }
		 }
		 return null;
		
	}
	/**
	 * 
	 * @param b
	 * @return true wenn b schon in vormerkungene enthalten ist
	 */
	private boolean vormerkungEnthalten(Vormerkung b) {
		for(int i = 0; i<vormerkungen.size();i++) {
			if(vormerkungen.get(i).isSame(b))
				return true;
		}
		return false;
	}
	/**
	 * 
	 * @param 2 Felder der Linie
	 * @param 2 Felder des Blocks
	 * @return Wenn eine Verbindung zwischen Linie und Block besteht werden die 2 Felder wieder gegeben welche keine Verbindung haben, sonst null
	 */
	private Feld[] verbundenTurbot(int zahl,Feld []a ,Feld[] b) {
		if(a[0] == b[0] || a[0] == b[1] || a[1] == b[0] || a[1] == b[1])
			return null;
		Feld outPut[] = new Feld[2];
		boolean vertikaleLinie = a[0].getX() == a[1].getX();
		for(int iL = 0; iL<2; iL ++) {
			for(int iB = 0; iB<2;iB++) {
				if(vertikaleLinie) {
					if(a[iL].getY() == b[iB].getY()) {
						Feld[] feldPaar1 = new Feld[2]; //in feldPaar1[0] wird das Feld aus der Linie abgespeichert welche die selbe y koordinate hat wie ein Feld aus einem Block
						Feld[] feldPaar2 = new Feld[2];
						feldPaar1[0] = a[iL];
						feldPaar2[1] = b[iB];
						if(iL == 0)
							outPut[0] = a[1];
						else
							outPut[0] = a[0];
						if(iB == 0)
							outPut[1] = b[1];
						else
							outPut[1] = b[0];
						//Verhindern das die beiden outPut punkte verbunden sind
						if(outPut[0].getY() == outPut[1].getY())
							return null;
						
						feldPaar1[1] = outPut[1];
						feldPaar2[0] = outPut[0];
						Vormerkung temp = new Vormerkung(zahl, feldPaar1, feldPaar2);
						if(!vormerkungEnthalten(temp))
							vormerkungen.add(temp);
						return outPut;
									
					}
					
				}else {
					if(a[iL].getX() == b[iB].getX()) {
						Feld[] feldPaar1 = new Feld[2]; //in feldPaar1[0] wird das Feld aus der Linie abgespeichert welche die selbe y koordinate hat wie ein Feld aus einem Block
						Feld[] feldPaar2 = new Feld[2];
						feldPaar1[0] = a[iL];
						feldPaar2[1] = b[iB];
						if(iL == 0)
							outPut[0] = a[1];
						else
							outPut[0] = a[0];
						if(iB == 0)
							outPut[1] = b[1];
						else
							outPut[1] = b[0];
						//Verhindern das die beiden outPut punkte verbunden sind
						if(outPut[0].getX() == outPut[1].getX())
							return null;
						feldPaar1[1] = outPut[1];
						feldPaar2[0] = outPut[0];
						Vormerkung temp = new Vormerkung(zahl, feldPaar1, feldPaar2);
						if(!vormerkungEnthalten(temp))
							vormerkungen.add(temp);
						return outPut;
									
					}
				}
			}
		}
		return null;
	}
	/**
	 * darf nur aufgeruden werden wen vorher geprueft wurde ob alle 4 Felder eine Zahl gemeinsam moeglich haben
	 * @param a
	 * @param b
	 * @return wenn die Arrays a und b skyscraper geignet sind gibt die methode an den 1. beiden stellen die 2 Basis Felder aus und an den letzen beiden die Dach Felder. sollten die Arrays nich skyscraper faehig sein gibt die Methode null zurueck
	 */
	private Feld[] sykscraperMoeglich(Feld a[],Feld b[]) {
		if(a[0].getBlock() == a[1].getBlock() || b[0].getBlock() == b[1].getBlock())
			return null;
		Feld[] outPut = new Feld[4];
		//suchen ob Basis vorhanden
		if(a[0].getX() == a[1].getX()){//Vertikale Linien
			if(a[0].getY() == b[0].getY() && a[0].getBlock() != b[0].getBlock())//Basis gefunden
			{
				outPut[0] = a[0];
				outPut[1] = b[0];
				outPut[2] = a[1];
				outPut[3] = b[1];
			}else if(a[1].getY() == b[1].getY()&& a[1].getBlock() != b[0].getBlock())//Basis gefunden
			{
				outPut[0] = a[1];
				outPut[1] = b[1];
				outPut[2] = a[0];
				outPut[3] = b[0];
			}else
				return null;//kann man hier sicher sagen da die moeglichen Felder sortiert sind --> a[0].Y == b[1].y  heist das a[1] und b[0] nicht auf einer seite von a[0] und b[1]sind
			if(outPut[2].getY() != outPut[3].getY() && outPut[2].getBlock() != outPut[3].getBlock()) {
				//überprüfen ob outPut[2] und outPut[3] in einer Blockreihe liegen
				if(	(outPut[2].getBlock() <= 2 && outPut[3].getBlock() <= 2) || 
					(outPut[2].getBlock() >= 6 && outPut[3].getBlock() >= 6) ||
					((outPut[2].getBlock() >=3 && outPut[2].getBlock() <=5) && (outPut[3].getBlock() >=3 && outPut[3].getBlock() <=5)))
				return outPut;
			}
			return null;
		}else {//horizontale Linie
			if(a[0].getX() == b[0].getX() && a[0].getBlock() != b[0].getBlock() )//Basis gefunden
			{
				outPut[0] = a[0];
				outPut[1] = b[0];
				outPut[2] = a[1];
				outPut[3] = b[1];
			}else if(a[1].getX() == b[1].getX() && a[1].getBlock() != b[0].getBlock())//Basis gefunden
			{
				outPut[0] = a[1];
				outPut[1] = b[1];
				outPut[2] = a[0];
				outPut[3] = b[0];
			}else
				return null;//kann man hier sicher sagen da die moeglichen Felder sortiert sind --> a[0].Y == b[1].y  heist das a[1] und b[0] nicht auf einer seite von a[0] und b[1]sind
			if(outPut[2].getX() != outPut[3].getX() && outPut[2].getBlock() != outPut[3].getBlock() && inBlockSpalte(outPut[2].getBlock(),outPut[3].getBlock())) {
				return outPut;
			}
			return null;
		}
	}
	
	private boolean inBlockSpalte(int blockA, int blockB) {
		if(blockA == 0 || blockA == 3 ||blockA == 6)
			if(blockB == 0 || blockB == 3 ||blockB == 6)
				return true;
		if(blockA == 1 || blockA == 4 ||blockA == 7)
			if(blockB == 1 || blockB == 4 ||blockB == 7)
				return true;
		if(blockA == 2 || blockA == 5 ||blockA == 8)
			if(blockB == 2 || blockB == 5 ||blockB == 8)
				return true;
		return false;
	}
	
	/**
	 * Ueberprueft alle Blöcke ob es Zahlen gibt für die alle moegöichen Felder in einer Reihe/Spalte liegen
	 * ist dies der Fall wird diese Zahl aus allen anderen Feldern der Reihe/Spalte gelöscht.
	 * @return true wenn der aufruf etwas bei den Feldern der neuner Reihe veraendert hat sonst false
	 */
	private boolean blockFindLocked() {
		boolean outPut = false;
		for(int bI = 0; bI<9; bI++) {
			List<Feld> moeglich[] = bloecke[bI].getMoeglich();
			for(int i = 0; i<9; i++) {
				if(moeglich[i].size() == 2 || moeglich[i].size() == 3 ) {//wenn mehr als 3 moeglich sind ist es unmoeglich das diese in einer Reihe/Spalte sind
					int x = moeglich[i].get(0).getX(); // wenn -1 sind die Felder nicht in einer Spalte
					int y = moeglich[i].get(0).getY(); // wenn -1 sind die Felder nicht in einer Reihe
					for(int mI =1; mI<moeglich[i].size();mI++) {
						if(moeglich[i].get(mI).getX() != x)
							x = -1;
						if(moeglich[i].get(mI).getY() != y)
							y= -1;
					}
					if(x != -1) {//Felder befinden sich in einer spalte
						if(vertikal[x].ausschliesenAusser(i+1, moeglich[i]))
							outPut = true;
					}else if(y != -1) {//Felder befinden sich in einer Reihe
						if(horizontal[y].ausschliesenAusser(i+1, moeglich[i]))
							outPut = true;
					}
				}
			}
		}
		return outPut;
	}
	
	
	public void printLinesAndBlocks() {
		System.out.println("\n\nHorizontale Linien:");
		for(int i = 0; i<9;i++) {
			System.out.print(i+" :  ");
			horizontal[i].print();
			System.out.println();
		}
		System.out.println("\n\nVertikale Linien:");
		for(int i = 0; i<9;i++) {
			System.out.print(i+" :  ");
			vertikal[i].print();
			System.out.println();
		}
		System.out.println("\n\nBlöcke:");
		for(int i = 0; i<9;i++) {
			System.out.println("\n"+i+" :  ");
			bloecke[i].print();
			System.out.println();
		}
	}
	public void print() {
		for(int y = 0; y<9; y++) {
			for(int x = 0; x<9;x++) {
				if(x%3 == 0 && x != 0)
					System.out.print("|");
				spielfeld[y][x].print();
			}
			if(y == 2 || y == 5)
				System.out.print("\n------------");
			System.out.println();
		}
	}
	/**
	 * aktualisiert alle Linien und Blöcke
	 * @return true wenn der aufruf etwas bei den Feldern veraendert hat sonst false
	 */
	private boolean aktualisieren() {
		boolean outPut = false;
		if(blockFindLocked()) //Für test ausgeklammert
			outPut = true;
		for(int i = 0; i<9;i++) {
			if(horizontal[i].aktualisieren())
				outPut = true;
			if(vertikal[i].aktualisieren())
				outPut = true;
			if(bloecke[i].aktualisieren())
				outPut = true;
		}
		//Vormerkungen nicht aktualisieren
//		if(!vormerkungen.isEmpty()) {
//			for(int i = 0; i< vormerkungen.size();i++) {
//			if(vormerkungen.get(i).aktualisieren()) {
//				vormerkungen.remove(i);
//			}
//			}
//		}
		
		if(blockFindLocked())
			outPut = true;
		return outPut;
	}
	
	/**
	 * gibt die oben links(pos[y|[x]) stehende Ecke des Blocks i wieder.
	 * 
	 */
	private byte[] getEcke(byte block) {
		byte[] outPut = new byte[2];
		
		if(block >=0 && block <=2)
			outPut[0] = 0;
		else if(block >=3 && block <=5)
			outPut[0] = 3;
		if(block >=6 && block <=8)
			outPut[0] = 6;
		
		if(block % 3 == 0)
			outPut[1] = 0;
		else if(block == 1 || block == 4 || block == 7)
			outPut[1] = 3;
		else
			outPut[1] = 6;
		
		return outPut;
	}
	//methoden zum weiteren loesen des Sodokus
}

