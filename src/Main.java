
public class Main {

	public static void main(String[] args) {
		Sodoku spielFeld;
		Feld[][] feld = new Feld[9][9];
		String[] dataString = {	" 973     ",
								"  89   3 ",
								"6   2 7  ",
								"2 6  9 8 ",
								"8  27    ",
								" 5  64   ",
								" 65      ",
								"9     27 ",
								"    5   9",};

		//Daten von dataString in feld laden
		for(int y = 0; y<9;y++) {
			for(int x = 0; x<9;x++) {
				byte pos[] = {(byte)y,(byte)x};
				if(dataString[y].charAt(x) == ' ') {
					feld[y][x] = new Feld(pos);
				}else
					feld[y][x] = new Feld(Byte.parseByte(Character.toString(dataString[y].charAt(x))),pos);
			}
		}
		spielFeld = new Sodoku(feld);
		spielFeld.loesen();
		spielFeld.print();
	}

}
