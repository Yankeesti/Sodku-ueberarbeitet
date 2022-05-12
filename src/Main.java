
public class Main {

	public static void main(String[] args) {
		Sodoku spielFeld;
		Feld[][] feld = new Feld[9][9];
//		String[] dataString = {	"     5   ",
//								"7  3  158",
//								"5 1 8 3  ",
//								" 5 8    2",
//								"  3   7  ",
//								"8    6 9 ",
//								"  4 7 2  ",
//								"962  3  7",
//								"   2     "};
		String[] dataString = {	"2  8   4 ",
								"9    7  1",
								"   59    ",
								" 874   6 ",
								" 92  6   ",
								"5 6  23  ",
								" 2     96",
								"   3   1 ",
								"    4 753"
								};
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
