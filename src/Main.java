import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Sodoku spielFeld;
		Feld[][] feld = new Feld[9][9];
//		String[] dataString = {	"  4 13  5",
//								"7      2 ",
//								"   6     ",
//								"  3 95  1",
//								"     8   ",
//								" 4    3  ",
//								"   4    6",
//								"  98     ",
//								" 2  69 5 "};
//		String[] dataString = {	"  1      ",
//								"4  65 971",
//								"8  3   45",
//								"2   4 83 ",
//								"         ",
//								" 34 6   7",
//								"61   3  2",
//								"548 21  3",
//								"     64  "};
		String[] dataString = {	"  1      ",
								"4  65 971",
								"8  3   45",
								"2   4 83 ",
								"         ",
								" 34 6   7",
								"61   3  2",
								"548 21  3",
								"     64  "};
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
