package kripto;

import java.util.Arrays;

public class Test {
	public final static int[][] B12 = 
		{{1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1},
	     {1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1},
	     {0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1},
	     {1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
	     {1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1},
	     {1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1},
	     {0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1},
	     {0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1},
	     {0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1},
	     {1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1},
	     {0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1},
	     {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}};
	
	public final static int[][] B11 = 
		{{1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0},
	     {1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1},
	     {0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1},
	     {1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0},
	     {1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
	     {1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1},
	     {0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1},
	     {0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0},
	     {0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0},
	     {1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0},
	     {0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1},
	     {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
	
	public final static int[][] I = makeIdentityMatrix(12);
	
	public final static int[][] G = getHMatrix();
	
	public static int[][] makeIdentityMatrix(int n) {
		int[][] identity = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				identity[i][j] = 0;
			}
			identity[i][i] = 1;
		}
		return identity;
	}
	
	public static void print2dArray(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static int[][] multiplyMatrices(int[][] m1, int[][] m2) {
		int[][] res = new int[m1.length][m2[0].length];
		for (int i = 0; i < m1.length; i++) {
			for (int j = 0; j < m2[0].length; j++) {
				int sum = 0;
				for (int k = 0; k < m2.length; k++) {
					sum += m1[i][k] * m2[k][j];
				}
				res[i][j] = sum % 2;
			}
		}
		return res;
	}
	
	
	public static int[][] getG23Matrix() {
		int[][] h = new int[12][23];
		for (int i = 0; i < 12; i++) {
			int[] rowB = B11[i];
			int[] rowI = I[i];
			int[] newArr = new int[23];
			for (int j = 0; j < 12; j++) {
				newArr[j] = rowI[j];
			}
			for (int j = 0; j < 11; j++) {
				newArr[j+12] = rowB[j];
			}
			h[i] = newArr;
		}
		
		return h;
	}
	
	public static int[][] getHMatrix() {
		int[][] res = new int[24][12];
		for (int i = 0; i < 12; i++) {
			res[i] = I[i];
			res[i+12] = B12[i];
		}
		return res;
	}
	
	public static int[][] vectorStringToArray(String v) {
		int[][] result = new int[1][v.length()];
		for (int i = 0; i < v.length(); i++) {
			result[0][i] = Integer.parseInt(String.valueOf(v.charAt(i)));
		}
		return result;
	}
	
	public static int calculateVectorWeight(int[] v) {
		int weight = 0;
		for (int i = 0; i < v.length; i++) {
			if (v[i] == 1) {
				weight++;
			}
		}
		return weight;
	}
	
	public static int[] completeVectorBasedOnWeight(int[] v) {
		int[] newVector = new int[24];
		for (int i = 0; i < v.length; i++) {
			newVector[i] = v[i];
		}
		
		if (calculateVectorWeight(v) % 2 == 0) {
			newVector[23] = 1;
		} else {
			newVector[23] = 0;
		}
		
		return newVector;
	}
	
	public static int[] calculateErrorVector(int[][] v) {
		int[] errorVector = new int[24];
		int[] syndrome = new int[12];
		syndrome = multiplyMatrices(v, G)[0];		
		int weight = calculateVectorWeight(syndrome);
		if (weight <= 3) {
			// u=[s, 0]
			for (int i = 0; i < 24; i++) {
				errorVector[i] = syndrome[i];
				errorVector[i+12] = 0;
			}
			return errorVector;
		} else {
			int[] testVector = new int[12];
			for (int i = 0; i < 12; i++) {
				testVector = sumVectors(syndrome, B12[i]);
				if (calculateVectorWeight(testVector) <= 2) {
					System.out.println("found s + bi vector with i=" + i + Arrays.toString(testVector));
					for (int j = 0; j < 12; j++) {
						errorVector[j] = testVector[j];
						errorVector[j+12] = 0;
					}
					errorVector[12 + i] = 1;
					return errorVector;
				}
			}
		}
		
		int[][] firstSyndrome = new int[1][12];
		firstSyndrome[0] = syndrome;
		int[] secondSyndrome = multiplyMatrices(firstSyndrome, B12)[0];
		weight = calculateVectorWeight(secondSyndrome);
		if (weight <= 3) {
			//u=[0, sB]
			for (int i = 0; i < 24; i++) {
				errorVector[i] = 0;
				errorVector[i+12] = secondSyndrome[i];
			}
			return errorVector;
		} else {
			int[] testVector = new int[12];
			for (int i = 0; i < 12; i++) {
				testVector = sumVectors(secondSyndrome, B12[i]);
				if (calculateVectorWeight(testVector) <= 2) {
					System.out.println("found sB + bi vector with i=" + i + Arrays.toString(testVector));
					for (int j = 0; j < 12; j++) {
						errorVector[j] = 0;
						errorVector[j+12] = testVector[j];
					}
					errorVector[i] = 1;
					return errorVector;
				}
			}
		}
		
		return null;
	}
	
	public static int[] sumVectors(int [] v1, int[] v2) {
		if (v1.length == v2.length) {
			int[] result = new int[v1.length];
			for (int i = 0; i < v1.length; i++) {
				result[i] = (v1[i] + v2[i]) % 2;
			}
			return result;
		} else {
			return null;
		}
	}

	public static void main(String[] args) {	
		print2dArray(getG23Matrix());
		int[][] G23 = new int[12][23];
		G23 = getG23Matrix();
		
//		a.times(b).print(0, 0);
//		
//		B.print(0, 0);
//		I.print(0, 0);
// 		getHMatrix().print(0, 0);
		// #TODO CHECK LENGTH
		String enteredVector = "100010001101";
		System.out.println(Arrays.toString(vectorStringToArray(enteredVector)));
		int[][] enteredArray = vectorStringToArray(enteredVector);
		//print2dArray(multiplyMatrices(vectorStringToArray(enteredVector), G23));
		int[][] encodedArray = multiplyMatrices(vectorStringToArray(enteredVector), G23);
		System.out.println(Arrays.toString(encodedArray[0]));
		System.out.println(Arrays.toString(completeVectorBasedOnWeight(encodedArray[0])));
		
		String encodedVector = "101111101111010010010010";
		encodedArray = vectorStringToArray(encodedVector);
		
		int[][] syndrome = multiplyMatrices(encodedArray, G);
		
		System.out.println("Syndrome: " + Arrays.toString(syndrome[0]));
		String v1 = "110001001001";
		String b1 = "110111000101";
		System.out.println(Arrays.toString(sumVectors(vectorStringToArray(v1)[0], vectorStringToArray(b1)[0])));
		
		String anotherVectorToDecode = "001001001101101000101000";
		int[][] anotherArray = vectorStringToArray(anotherVectorToDecode);
		int[] u = new int[24];
		u = calculateErrorVector(anotherArray);
		System.out.println(Arrays.toString(u));
	}
}

