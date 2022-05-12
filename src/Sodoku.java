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
		aktualisieren();
		aktualisieren();
		aktualisieren();
		aktualisieren();
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
}
