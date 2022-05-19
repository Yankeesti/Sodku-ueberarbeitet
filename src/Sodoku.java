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
	Sodoku(Feld[][] pSpielfeld){
		spielfeld = pSpielfeld;
		
		horizontal = new Linie[9];
		vertikal = new Linie[9];
		bloecke = new Block[9];
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
	/**
	 * loest das Sodoku
	 */
	public void loesen() {
		for(int i = 0; i<9; i++) {
			aktualisieren();
			blockFindLocked();
			}
		//print();
		aktualisieren();
		skyscraper();
		aktualisieren();
		skyscraper();
		aktualisieren();
	}
	
	/**
	 * arbeitet nach dem Prizip in diesem Video : https://www.youtube.com/watch?v=QTlmYXAMgLE
	 */
	public void skyscraper() {
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
						eckPunkte = sykscraperMoeglich(felderMoeglichA,felderMoeglichB);
						if(eckPunkte != null) {//Skyscraper gefunden
							bloecke[eckPunkte[2].getBlock()].ausschliessenWoY(durchZahl,eckPunkte[3].getY());
							bloecke[eckPunkte[3].getBlock()].ausschliessenWoY(durchZahl,eckPunkte[2].getY());
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
							eckPunkte = sykscraperMoeglich(felderMoeglichA,felderMoeglichB);
							if(eckPunkte != null) {//Skyscraper gefunden
								bloecke[eckPunkte[2].getBlock()].ausschliessenWoX(durchZahl,eckPunkte[3].getX());
								bloecke[eckPunkte[3].getBlock()].ausschliessenWoX(durchZahl,eckPunkte[2].getX());
								continue nextLoop;
							}
						}
					}
				}
			}
		}
	//überprüfen ob Säulen gleiche basis Linie haben
	
	//überprüfen ob "Dach" auf verschiedenn Höhen ist
	
	//Ausschliesen
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
	 */
	private void blockFindLocked() {
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
						vertikal[x].ausschliesenAusser(i+1, moeglich[i]);
					}else if(y != -1) {//Felder befinden sich in einer Reihe
						horizontal[y].ausschliesenAusser(i+1, moeglich[i]);
					}
				}
			}
		}
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
	 */
	private void aktualisieren() {
		for(int i = 0; i<9;i++) {
			horizontal[i].aktualisieren();
			vertikal[i].aktualisieren();
			bloecke[i].aktualisieren();
		}
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

