
/**
 * @author timheinsberg
 *	Ueber Klasse für die Klassen Line und Block
 *	enthält immer neun Felder
 */
public class neunerFeld {
	//Attribute
		protected Feld felder[];
		protected boolean enthalten[];//wenn Feld true --> Zahl(index+1) ist in dem neuner Feld enthalten 
		protected byte anzahlMoegliche[];
	//Konstuktor
		/**
		 * @param pFelder parm muss genau 9 Felder enthalten
		 */
		neunerFeld(Feld pFelder[]){
			felder = pFelder;
			enthalten = new boolean[9];
			anzahlMoegliche = new byte[9];
			aktualisieren();
			
		}
	
	//Methoden
		/**
		 * Aktualisiert das neuner Feld und überprüft ob durch ausschluss verfahren Felder identifiziert
		 * werden können
		 */
		protected void aktualisieren() {
			for(Feld p: felder) { // prüfen welche Zahlen schon in dem neuner Feld enthalten sind
				byte pZahl = p.getZahl();
				if(pZahl>0) {
					enthalten[pZahl-1] = true;
				}
			}
			for(int i = 0; i<9;i++) {
				anzahlMoegliche[i] = 0;
			}
			for(Feld p: felder) {
				p.ausschliesen(enthalten);
				byte pMoegliche[] = p.getMoegliche();
				//Counten wieviele Felder es gibt für die die Zahlen 1-9 möglich sind
				for(int i = 0; i<pMoegliche.length;i++) {
					anzahlMoegliche[pMoegliche[i]-1] ++;
				}
			}
			moeglicheUeberpruefen();
			
		}
		/**
		 * überprüft ob es eine Ziffer gibt für die nur ein Feld in Frage kommt
		 * wenn ja wird dieses Feld mit der Ziffer besetzt
		 */
		private void moeglicheUeberpruefen() {
			for(int i = 0; i<anzahlMoegliche.length ; i++) {
				if(anzahlMoegliche[i] == 1) {//Zahlgefunden für die nur ein Feld in frage kommt
					//Feld raussuchen
					for(Feld p: felder) {
						if(p.istMoeglich(i+1)) {
							p.setZahl(i+1);
						}
					}
				}
			}
		}
		
		public void print() {
			for(Feld p : felder)
				p.print();
		}
}
