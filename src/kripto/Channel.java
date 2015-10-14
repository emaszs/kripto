package kripto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Channel {
	public double errorProbability = 0.2;
	
	public int[] sentCodeword;
	
	public int[] receivedCodeword;
	
	public void sendCodeword(int[] c) {
		this.sentCodeword = c;
	}
	
	public List<Integer> errorPositions = new ArrayList<Integer>();
	
	public void receiveCodeword() {
		int[] result = new int[sentCodeword.length];
		for (int i = 0; i < sentCodeword.length; i++) {
			if (new Random().nextDouble() < errorProbability) {
				result[i] = (sentCodeword[i] + 1) % 2;
				errorPositions.add(i+1);
			} else {
				result[i] = sentCodeword[i];
			}
		}
		receivedCodeword = result;
	}
	
	public int[] getReceivedCodeword() {
		return receivedCodeword;
	}
	
	public void setReceivedCodeword(int [] c) {
		this.receivedCodeword = c;
	}
	
	public void setErrorProbability(double p) {
		this.errorProbability = p;
	}
}
