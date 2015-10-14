package kripto;

import java.util.Random;

public class Channel {
	public double errorProbability = 0.2;
	
	public int[] sentCodeword;
	
	public void sendCodeword(int[] c) {
		this.sentCodeword = c;
	}
	
	public int[] receiveCodeword() {
		int[] result = new int[sentCodeword.length];
		for (int i = 0; i < sentCodeword.length; i++) {
			if (new Random().nextDouble() < errorProbability) {
				result[i] = (sentCodeword[i] + 1) % 2;
			} else {
				result[i] = sentCodeword[i];
			}
		}
		return result;
	}
}
