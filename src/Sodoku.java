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
		vertikal[4].drilling();
	}
	
	/**
	 * Ueberprueft alle Blöcke ob es Zahlen gibt für die alle moegöichen Felder in einer Reihe/Spalte liegen
	 * ist dies der Fall wird diese Zahl aus allen anderen Feldern der Reihe/Spalte gelöscht.
	 */
	private void blockFindLocked() {
//		for(int bI = 0; bI<9; bI++) {
//			Block akt = bloecke[bI];
//			for(int i = 1 ;i<=9; i++) {
//				Feld felder[] = akt.getFelderAufLinie(i);
//				if(felder != null) {//alle moeglichen Felder für i liegen auf einer Zeile/Spalte
//					int x = felder[0].getX(); // wenn nach durchlauf der for schleife nicht minus eins drin steht liegen alle Felder in der Spalte(vertikal) x
//					for(int fI = 1; fI<felder.length;fI++)
//						if(felder[fI].getX() != x)
//						{
//							x = -1;
//							break;
//						}
//					if(x != -1)
//						vertikal[x].ausschliesenAusser(new byte[] {(byte)i}, felder);
//					else
//						horizontal[felder[0].getY()].ausschliesenAusser(new byte[] {(byte)i}, felder);
//				}
//			}
//		}
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

