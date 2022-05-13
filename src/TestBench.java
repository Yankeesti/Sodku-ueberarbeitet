
public class TestBench {

	public static void main(String[] args) {
		neunerFeld spalte;
		Feld a,b,c,d,e,f,g,h,i;
		byte pos[] = {0,2};
		
		a = new Feld(pos);
		byte moeg[] = {1,2,6};
		a.setMoegliche(moeg);
		
		b = new Feld(pos);
		b.setMoegliche(moeg);
		
		c = new Feld(pos);
		byte moeg1[] = {1,7};
		c.setMoegliche(moeg1);
		
		d = new Feld(pos);
		byte moeg2[] = {2,5,8,9};
		d.setMoegliche(moeg2);
		
		e = new Feld((byte)4,pos);
		
		f = new Feld(pos);
		byte moeg3[] = {1,8,9};
		f.setMoegliche(moeg3);
		
		g = new Feld(pos);
		byte moeg4[] = {1,3,7};
		g.setMoegliche(moeg4);
		
		h = new Feld(pos);
		byte moeg5[] = {2,3,5};
		h.setMoegliche(moeg5);
		
		
		i = new Feld(pos);
		byte moeg6[] = {1,2};
		i.setMoegliche(moeg6);
		
		Feld komb[] = {a,b,c,d,e,f,g,h,i};
		spalte = new neunerFeld(komb);
		
		for(int z = 0; z<5; z++)
		spalte.aktualisieren();
		
		spalte.print();
	}

}
