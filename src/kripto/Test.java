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
	
	public static int[][] vectorStringToArray(String v) {
		int[][] result = new int[1][v.length()];
		for (int i = 0; i < v.length(); i++) {
			result[0][i] = Integer.parseInt(String.valueOf(v.charAt(i)));
		}
		return result;
	}
	
	public static int getVectorWeight(int[] v) {
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
		
		if (getVectorWeight(v) % 2 == 0) {
			newVector[23] = 1;
		} else {
			newVector[23] = 0;
		}
		
		return newVector;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] array = 
				{{1, 2, 3},
				 {3, 2, 1},
				 {1, 1, 1}};
		int[][] array2 = 
				{{1, 2, 0, 2},
				 {3, 2, 1, 2},
				 {1, 1, 1, 2}};
//		
//		Matrix a = new Matrix(array);
//		Matrix b = new Matrix(array2);
		
		print2dArray(multiplyMatrices(array, array2));
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
		
	}
}

