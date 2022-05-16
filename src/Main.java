import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Sodoku spielFeld;
		Feld[][] feld = new Feld[9][9];
//		String[] dataString = {	" 8   1 69",
//								"         ",
//								" 164     ",
//								"  42 6   ",
//								"   13    ",
//								"      8 2",
//								" 38      ",
//								" 4   7 5 ",
//								"    29 47"};
		String[] dataString = {	" 16    8 ",
								"3    165 ",
								"  56   91",
								"4    65 2",
								"25  8 7 6",
								"6375  8 9",
								" 74  31  ",
								"   4  9 3",
								" 63   47 "
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
		System.out.println("hi");

	}

}
