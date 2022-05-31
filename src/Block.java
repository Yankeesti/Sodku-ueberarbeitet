
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
	
	/**
	 * wenn zahl nur bei Feldern in einer Spalte/Zeile moeglich ist gibt die Methode ein Array mit diesen Feldern wieder,
	 * ist dies nicht der Fall gibt die methode null zurück
	 */
	public Feld[] getFelderAufLinie(int zahl) {
		aktMoeglicheList();
		if(moeglich[zahl-1].size() >0) {
		int x = moeglich[zahl-1].get(0).getX();//Wenn -1 haben nicht alle Felder die gleiche X/Y Position
		int y = moeglich[zahl-1].get(0).getY();
		int anzahlFelder = moeglich[zahl-1].size();
		if(anzahlFelder >1 && anzahlFelder <4) {
			for(int i = 1; i<anzahlFelder;i++) {
				if(x != moeglich[zahl-1].get(i).getX())
					if(y == -1)
						return null;
					else
						x = -1;
				if(y != moeglich[zahl-1].get(i).getY())
					if(x == -1)
						return null;
						else
							y = -1;
			}
		}
		if(x == -1 && y == -1) return null;//weder x noch y sind bei allen Feldern gleich
		
		Feld outPut[] = new Feld[anzahlFelder];
		for(int i = 0; i<anzahlFelder;i++) {
			outPut[i] = moeglich[zahl-1].get(i);
		}
		return outPut;
		}
		return null;
	}
	/**
	 * lösche Zahl als moeglichkeit bei allen Feldern wo x = x ist
	 * @param zahl
	 * @param x
	 * @return true wenn der aufruf etwas bei den Feldern des Blocks veraendert hat sonst false
	 */
	public boolean ausschliessenWoX(int zahl, int x) {
		boolean outPut = false;
		for(Feld i: felder)
			if(i.getX() == x)
				if(i.ausschliessen((byte)zahl))
						outPut = true;
		return outPut;
	}
	
	/**
	 * lösche Zahl als moeglichkeit bei allen Feldern wo y = y ist
	 * @param zahl
	 * @param y
	 * @return true wenn der aufruf etwas bei den Feldern der neuner Reihe veraendert hat sonst false
	 */
	public boolean ausschliessenWoY(int zahl, int y) {
		boolean outPut = false;
		for(Feld i: felder)
			if(i.getY() == y)
				if(i.ausschliessen((byte)zahl))
					outPut = true;
		return outPut;
	}

}
