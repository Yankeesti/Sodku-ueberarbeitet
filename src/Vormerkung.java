/**
 * Objekte dieser Klasse enthalten vormerkungen von insgesamt 4 Feldern 
 * vormerkungen werden durch die 2 String Kette und turbot Fish hervorgerufen.
 * diese 4 Felder werden in 2 Paare unterteilt ist wird einem der 4 Felder eine Nummer zugewiesen kann daraus für alle anderen Felder etwas abggeleitet werden
 * @author timheinsberg
 *
 */
public class Vormerkung {
	private Feld[][] feldPaare;
	private int zahl; //die Zahl für für die die Vormerkung gilt
	
	public Vormerkung(int zahl,Feld[] feldPaar1, Feld[] feldPaar2) {
		this.zahl = zahl;
		feldPaare = new Feld[2][];
		feldPaare[0] = feldPaar1;
		feldPaare[1] = feldPaar2;
	}
	
	/**
	 * überprüft ob sich bei einem der Felder etwas bezüglich zahl veraendert hat und zieht daraus rückschlüsse
	 * sollte sich die Vormerkung auflösen(es steht fest welches Paar die Zahl enthält) gibt die Methode true zurück sonst false
	 * @return
	 */
	public boolean aktualisieren() {
		for(int i = 0; i<2; i++) {
			for(int i2 = 0; i2<2; i2++) {
				Feld aktFeld = feldPaare[i][i2];
				//ueberprufen aktFeld Zahl nicht mehr moeglich ist
				if(!aktFeld.istMoeglich((byte)zahl)) {// die Zahl ist in diesem Feld nichtmehr möglich --> zahl kann bei Partner feld gelöscht werden und Zahl kann bei anderem Paar fest gesetzt werden
					//Zahl bei anderem Paar setzen
					int anderesPaar =0;
					if(i == 0)
						anderesPaar = 1;
					for(int ip = 0; ip<2; ip++)
						feldPaare[anderesPaar][ip].setZahl(zahl);
					//zahl bei partner feld ausschliesen
					if(i2 == 0)
						feldPaare[i][1].ausschliesen((byte)zahl);
					else
						feldPaare[i][0].ausschliesen((byte)zahl);
					return true;
				}
				//überprüfen ob zahl bei einem feld feststeht
				if(aktFeld.getZahl() == zahl) {
					//zahl bei Partner Feld festsetzen
					if(i2 == 0)
						feldPaare[i][1].setZahl(zahl);
					else
						feldPaare[i][0].setZahl(zahl);
					//zahl bei anderem paar ausschliesen
					int anderesPaar =0;
					if(i == 0)
						anderesPaar = 1;
					for(int ip = 0; ip<2; ip++)
						feldPaare[anderesPaar][ip].ausschliesen((byte)zahl);
				}
			}
		}
		return false;
	}
	
	public boolean isSame(Vormerkung b) {
		if(zahl != b.getZahl())
			return false;
		Feld[][] feldPaareB = b.getPaare();
		for(int i = 0; i<2;i++) {
			if(!paarEnthalten(feldPaareB[i]))
				return false;
		}
		return true;
	}
	
	/**
	 * Hilfs Methode für isSame
	 * @param paar
	 * @return truen wenn paar als paar vorhanden ist und false wenn nicht
	 */
	public boolean paarEnthalten(Feld paar[]) {
		boolean paar1gleich = false;
		boolean paar2gleich = false;
		
		next:
		for(int iA = 0; iA<2 ;iA++) {
			for(int iB = 0; iB<2; iB++) {
				if(iA == 0 || paar1gleich)
				if(paar[iA] == feldPaare[0][iB]) {
					paar1gleich = true;
					continue next;
				}
				if(iA == 0 || paar2gleich)
				if(paar[iA] == feldPaare[1][iB]) {
					paar2gleich = true;
					continue next;
				}
				if(iB == 1)
				paar1gleich = false;
				if(iB == 1)
				paar2gleich = false;
			}
		}
		
		return paar1gleich || paar2gleich;
	}
	//Getter/ Setter
	public int getZahl() {
		return zahl;
	}
	
	public Feld[][] getPaare(){
		return feldPaare;
	}
	
	public void print() {
		System.out.println("Zahl: " + zahl+
				" P1 Feld 1: "+feldPaare[0][0].getPosString()+
				" P1 Feld 2: "+feldPaare[0][1].getPosString()+
				" P2 Feld 1: "+feldPaare[1][0].getPosString()+
				" P2 Feld 1: "+feldPaare[1][1].getPosString());
	}
}
