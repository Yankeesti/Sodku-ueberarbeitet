
public class Block extends neunerFeld{

	Block(Feld[] pFelder) {
		super(pFelder);
		
	}
	
	public void print() {
		for(int i = 0; i <9;i++) {
			felder[i].print();
			if(i == 2 || i == 5)
				System.out.println();
		}
	}

}
