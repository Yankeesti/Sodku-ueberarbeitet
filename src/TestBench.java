
public class TestBench {

	public static void main(String[] args) {
		
		Feld a = new Feld(new byte[] {0,2});
		Feld b = new Feld(new byte[] {4,2});
		Feld c = new Feld(new byte[] {1,2});
		Feld d = new Feld(new byte[] {7,2});
		
		Feld e = new Feld(new byte[] {3,2});
		
		Vormerkung aV = new Vormerkung(0, new Feld[] {a,b}, new Feld[] {c,d});
		Vormerkung bV = new Vormerkung(0, new Feld[] {a,b}, new Feld[] {c,d});
		
		System.out.println(aV.isSame(bV));
	}

}
