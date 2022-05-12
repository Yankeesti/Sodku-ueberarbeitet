
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
		String[] dataString = {	"6  47 1 2",
								"7  1   9 ",
								" 1 5 87 4",
								" 2    8  ",
								" 5 726  9",
								"47       ",
								"  7 5  8 ",
								" 4     15",
								"   9   2 ",
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
