import java.util.ArrayList;
import java.util.Collections;
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
	public static byte[] vereinigen(byte[] a,byte[] b) {
		List<Byte> elements = new ArrayList<Byte>();
			for(byte bA : a)
				elements.add(bA);
			
			enthalten:
				for(byte bB:b) {
					for(byte bA:a)
					{
						if(bB == bA)
							continue enthalten;
					}
					elements.add(bB);
				}
			Collections.sort(elements);;
		byte outPut[] = new byte[elements.size()];
		for(int i = 0; i<outPut.length;i++)
			outPut[i] = elements.get(i);
		return outPut;
	}
}
