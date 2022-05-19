import java.util.ArrayList;
import java.util.List;

public class MengenOperationen {
	public static byte[] duchschnitt(byte [] a, byte[] b) {
		List<Byte> durchschnitt = new ArrayList<Byte>();
		
		nextByte:
		for(byte zahlA : a) {
			for(byte zahlB : b) {
				if(zahlA == zahlB) {
					durchschnitt.add(zahlA);
					continue nextByte;
					}
			}
		}
		if(durchschnitt.isEmpty())
			return null;
		byte outPut[] = new byte[durchschnitt.size()];
		for(int i = 0; i<outPut.length;i++)
			outPut[i] = durchschnitt.get(i);
		return outPut;
	}
}
