package kripto;

import java.util.Arrays;

public class Test {
	public final static int[][] B = 
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
		int [][] res = new int[m1.length][m2[0].length];
		for (int i = 0; i < m1.length; i++) {
			for (int j = 0; j < m2[0].length; j++) {
				int sum = 0;
				for (int k = 0; k < m2.length; k++) {
					sum += m1[i][k] * m2[k][j];
				}
				res[i][j] = sum;
			}
		}
		return res;
	}
	
//	public static Matrix getHMatrix() {
//		double[][] h = new double[12][24];
//		for (int i = 0; i < bArr.length; i++) {
//			double[] rowB = bArr[i];
//			double[] rowI = I.getArray()[i];
//			double[] newArr = new double[rowB.length*2];
//			for (int j = 0; j < rowB.length; j++) {
//				newArr[j] = rowI[j];
//				newArr[j+rowB.length] = rowB[j];
//			}
//			h[i] = newArr;
//		}
//		
//		return new Matrix(h);
//	}

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
		
//		a.times(b).print(0, 0);
//		
//		B.print(0, 0);
//		I.print(0, 0);
// 		getHMatrix().print(0, 0);
		
	}
}

