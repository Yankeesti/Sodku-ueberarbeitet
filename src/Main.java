import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Sodoku spielFeld;
		Feld[][] feld = new Feld[9][9];
		String[] dataString = {	"  4 13  5",
								"7      2 ",
								"   6     ",
								"  3 95  1",
								"     8   ",
								" 4    3  ",
								"   4    6",
								"  98     ",
								" 2  69 5 "};
//		String[] dataString = {	"95 7  3  ",
//								"  8   5  ",
//								" 1 56  4 ",
//								" 4 61    ",
//								" 2     3 ",
//								"  7 53 1 ",
//								" 6  25 7 ",
//								"7 4   8  ",
//								"2 5  7  6"};
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
		System.out.println("hi");
	}

}
