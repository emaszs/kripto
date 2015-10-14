package kripto;

import java.util.Arrays;

public class GolayCodec {
	public final int[][] B12 = 
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
	
	public final int[][] B11 = 
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
	
	public final int[][] I = makeIdentityMatrix(12);
	
	public final int[][] G = getHMatrix();
	
	public int[][] makeIdentityMatrix(int n) {
		int[][] identity = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				identity[i][j] = 0;
			}
			identity[i][i] = 1;
		}
		return identity;
	}
	
	public void print2dArray(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}

	public int[][] multiplyMatrices(int[][] m1, int[][] m2) {
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
	
	
	public int[][] getG23Matrix() {
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
	
	public int[][] getHMatrix() {
		int[][] res = new int[24][12];
		for (int i = 0; i < 12; i++) {
			res[i] = I[i];
			res[i+12] = B12[i];
		}
		return res;
	}
	
	public int[] vectorStringToArray(String v) {
		int[] result = new int[v.length()];
		for (int i = 0; i < v.length(); i++) {
			result[i] = Integer.parseInt(String.valueOf(v.charAt(i)));
		}
		return result;
	}
	
	public int calculateVectorWeight(int[] v) {
		int weight = 0;
		for (int i = 0; i < v.length; i++) {
			if (v[i] == 1) {
				weight++;
			}
		}
		return weight;
	}
	
	public int[] completeVectorBasedOnWeight(int[] v) {
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
	
	public int[] encodeC23(int[] vectorToEncode) {
		int[][] modifiedVector = new int[1][24];
		modifiedVector[0] = vectorToEncode;
		return this.multiplyMatrices(modifiedVector, this.getG23Matrix())[0];
	}
	
	public int[] decodeC23(int[] vectorToDecode) {
		int[] wi = completeVectorBasedOnWeight(vectorToDecode);
		int[] u = calculateErrorVector(wi);
		if (u != null) {
			int[] res = sumVectors(u, wi);
			return Arrays.copyOfRange(res, 0, 12);
		} else {
			return null;
		}
	}
	
	public int[] calculateErrorVector(int[] v) {
		int[] errorVector = new int[24];
		int[] syndrome = new int[12];
		int[][] temp2dVector = new int[1][12];
		temp2dVector[0] = v; 
		syndrome = multiplyMatrices(temp2dVector, G)[0];		
		int weight = calculateVectorWeight(syndrome);
		if (weight <= 3) {
			// u=[s, 0]
			for (int i = 0; i < 12; i++) {
				errorVector[i] = syndrome[i];
				errorVector[i+12] = 0;
			}
			return errorVector;
		} else {
			int[] testVector = new int[12];
			for (int i = 0; i < 12; i++) {
				testVector = sumVectors(syndrome, B12[i]);
				if (calculateVectorWeight(testVector) <= 2) {
//					System.out.println("found s + bi vector with i=" + i + Arrays.toString(testVector));
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
			for (int i = 0; i < 12; i++) {
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
	
	public int[] sumVectors(int [] v1, int[] v2) {
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
}
