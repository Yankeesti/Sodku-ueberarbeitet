import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Sodoku spielFeld;
		Feld[][] feld = new Feld[9][9];
//		String[] dataString = {	"8 5      ",
//								" 4      9",
//								"9   3 6 8",
//								"  98 1  6",
//								"41 2     ",
//								"  8   1  ",
//								"1   4 395",
//								"58       ",
//								"  4  7   ",};
//		String[] dataString = {	"  1      ",
//								"4  65 971",
//								"8  3   45",
//								"2   4 83 ",
//								"         ",
//								" 34 6   7",
//								"61   3  2",
//								"548 21  3",
//								"     64  "};
//		String[] dataString = {	"  1      ",
//								"4  65 971",
//								"8  3   45",
//								"2   4 83 ",
//								"         ",
//								" 34 6   7",
//								"61   3  2",
//								"548 21  3",
//								"     64  "};
		String[] dataString = {	"    1 42 ",
								"7  2  9 1",
								"   34    ",
								"3  6     ",
								"  29536  ",
								"     1  3",
								"    34   ",
								"6 8  2  7",
								" 74 6    "};
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
		System.out.println(spielFeld.loesen());
		spielFeld.print();
	}

}
