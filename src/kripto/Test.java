package kripto;
import Jama.*;

public class Test {
	public final static double[][] bArr = 
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
	
	public final static Matrix B = new Matrix(bArr);
	
	public final static Matrix I = Matrix.identity(12, 12);
	
	public static Matrix getHMatrix() {
		double[][] h = new double[12][24];
		for (int i = 0; i < bArr.length; i++) {
			double[] rowB = bArr[i];
			double[] rowI = I.getArray()[i];
			double[] newArr = new double[rowB.length*2];
			for (int j = 0; j < rowB.length; j++) {
				newArr[j] = rowI[j];
				newArr[j+rowB.length] = rowB[j];
			}
			h[i] = newArr;
		}
		
		return new Matrix(h);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] array = 
				{{1, 2, 3},
				 {3, 2, 1},
				 {1, 1, 1}};
		double[][] array2 = 
				{{1, 2, 0},
				 {3, 2, 1},
				 {1, 1, 1}};
		
		Matrix a = new Matrix(array);
		Matrix b = new Matrix(array2);
//		a.times(b).print(0, 0);
//		
//		B.print(0, 0);
//		I.print(0, 0);
 		getHMatrix().print(0, 0);
		
	}
}

