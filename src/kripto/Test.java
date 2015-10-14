package kripto;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		GolayCodec codec = new GolayCodec();
		
		codec.print2dArray(codec.getG23Matrix());
		int[][] G23 = new int[12][23];
		G23 = codec.getG23Matrix();
		
//		a.times(b).print(0, 0);
//		
//		B.print(0, 0);
//		I.print(0, 0);
// 		getHMatrix().print(0, 0);
		// #TODO CHECK LENGTH
		String enteredVector = "010010001101";
		System.out.println(Arrays.toString(codec.vectorStringToArray(enteredVector)));
		int[][] enteredArray = codec.vectorStringToArray(enteredVector);
		//print2dArray(multiplyMatrices(vectorStringToArray(enteredVector), G23));
		int[][] encodedArray = codec.multiplyMatrices(enteredArray, G23);
		System.out.println(Arrays.toString(encodedArray[0]));
		System.out.println(Arrays.toString(codec.completeVectorBasedOnWeight(encodedArray[0])));
		System.out.println("Decoded array:" + Arrays.toString(codec.decodeC23(encodedArray[0])));
		System.out.println("Entered array:" + Arrays.toString(enteredArray[0]));
		assert Arrays.equals(enteredArray[0], codec.decodeC23(encodedArray[0]));
		
		String encodedVector = "101111101111010010010010";
		encodedArray = codec.vectorStringToArray(encodedVector);
		
		int[][] syndrome = codec.multiplyMatrices(encodedArray, codec.G);
		
		System.out.println("Syndrome: " + Arrays.toString(syndrome[0]));
		String v1 = "110001001001";
		String b1 = "110111000101";
		System.out.println(Arrays.toString(codec.sumVectors(codec.vectorStringToArray(v1)[0],
				codec.vectorStringToArray(b1)[0])));
		
		String anotherVectorToDecode = "001001001101101000101000";
		int[][] anotherArray = codec.vectorStringToArray(anotherVectorToDecode);
		int[] u = new int[24];
		u = codec.calculateErrorVector(anotherArray[0]);
		System.out.println(Arrays.toString(u));
		assert Arrays.toString(u).equals("[0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0]");
		
		String vector364 = "000111000111011011010000";
		int[][] arr = codec.vectorStringToArray(vector364);
		int[] u2 = new int[24];
		u2 = codec.calculateErrorVector(arr[0]);
		System.out.println(Arrays.toString(u2));
		assert Arrays.toString(u2).equals("[0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0]");
	}
}

