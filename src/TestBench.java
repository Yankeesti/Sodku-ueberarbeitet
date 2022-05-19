
public class TestBench {

	public static void main(String[] args) {
		Feld a[] = new Feld[2];
		Feld b[] = new Feld[2];
		
		a[0] = new Feld(new byte[] {3,3});
		a[0].setBlock(4);
		a[0].setMoeglicheHard(new byte[] {8});
		
		a[1] = new Feld(new byte[] {3,6});
		a[1].setBlock(5);
		a[1].setMoeglicheHard(new byte[] {8});
		
		b[0] = new Feld(new byte[] {5,3});
		b[0].setBlock(4);
		b[0].setMoeglicheHard(new byte[] {8});
		
		b[1] = new Feld(new byte[] {5,7});
		b[1].setBlock(5);
		b[1].setMoeglicheHard(new byte[] {8});
		
		System.out.println("\n\n Versuch 1:");
		if(Sodoku.sykscraperMoeglich(a, b) != null) {//sollte moeglich sein
			System.out.println("moeglich");
		}else {
			System.out.println("nicht moeglich");
		}
		
		
	}

}
