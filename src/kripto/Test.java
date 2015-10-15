package kripto;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		GolayCodec codec = new GolayCodec();
		
		codec.print2dArray(codec.getG23Matrix());
//		int[][] G23 = codec.getG23Matrix();
		
//		a.times(b).print(0, 0);
//		
//		B.print(0, 0);
//		I.print(0, 0);
// 		getHMatrix().print(0, 0);
		// #TODO CHECK LENGTH
		String enteredVector = "010010001101";
		System.out.println(Arrays.toString(UI.vectorStringToArray(enteredVector)));
		int[] enteredArray = UI.vectorStringToArray(enteredVector);
		int[] encodedArray = codec.encodeC23(enteredArray);
		System.out.println("Encoded: " + Arrays.toString(encodedArray));
		System.out.println(Arrays.toString(codec.completeVectorBasedOnWeight(encodedArray)));
		System.out.println("Decoded array:" + Arrays.toString(codec.decodeC23(encodedArray)));
		System.out.println("Entered array:" + Arrays.toString(enteredArray));
		assert Arrays.equals(enteredArray, codec.decodeC23(encodedArray));
		
		String encodedVector = "101111101111010010010010";
		encodedArray = UI.vectorStringToArray(encodedVector);
		
		
		String anotherVectorToDecode = "001001001101101000101000";
		int[] anotherArray = UI.vectorStringToArray(anotherVectorToDecode);
		int[] u = new int[24];
		u = codec.calculateErrorVector(anotherArray);
		System.out.println(Arrays.toString(u));
		assert Arrays.toString(u).equals("[0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0]");
		
		String vector364 = "000111000111011011010000";
		int[] arr = UI.vectorStringToArray(vector364);
		int[] u2 = new int[24];
		u2 = codec.calculateErrorVector(arr);
		System.out.println(Arrays.toString(u2));
		assert Arrays.toString(u2).equals("[0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0]");
		
		Channel ch = new Channel();
		ch.setErrorProbability(0.1);
		ch.sendCodeword(codec.encodeC23(arr));
		System.out.println("Sent codeword: " + Arrays.toString(ch.sentCodeword));
		ch.receiveCodeword();
		int[] receivedCodeword = ch.getReceivedCodeword();
				
		System.out.println("Received word: " + Arrays.toString(receivedCodeword));
		System.out.println("Decoded word:  " + Arrays.toString(codec.decodeC23(receivedCodeword)));
		
	}
}

