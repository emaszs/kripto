package kripto;

import java.util.Arrays;


/**
 * 
 * @author Emilis
 * Klasė atsakinga už vektoriaus užkodavimą ir dekodavimą.
 *
 */
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
	
	
	/**
	 * Metodas sukuria tapatybės matricą NxN dydžio.
	 * 
	 * Parametras n - tapatybės matricos dydis sveiko skaičiaus formatu
	 * Grąžina dvimatį masyvą su tapatybės matrica
	 */
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
	
	/**
	 * Metodas skirtas spausdinti dvimačius masyvus
	 * 
	 * Parametras arr - dvimatis sveikų skaičių masyvas
	 */
	public void print2dArray(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}

	
	/**
	 * Iteratyviai daugina dvi sveikų skaičių matricas, pateiktas per parametrus.
	 * 
	 * Parametras m1 - dvimatis sveikų skaičių (NxM) pirmos matricos masyvas
	 * Parametras m2 - dvimatis sveikų skaičių (MxP) antros matricos masyvas
	 * 
	 * Grąžina matricų daugybos rezultatą, taip pat sveikų skaičių dvimatį masyvą (NxP)
	 */
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
	
	/**
	 * Sukuria generuojančią G matricą, naudojamą vektoriaus užkodavimui Golėjaus kodu.
	 * Tam padaryti sujungiamos matricų I ir B11 eilutės
	 * 
	 * Grąžina dvimatį sveikų skaičių G matricos masyvą (12x23)
	 */
	public int[][] getG23Matrix() {
		int[][] h = new int[12][23];
		for (int i = 0; i < 12; i++) {
			int[] rowB = B11[i];
			int[] rowI = I[i];
			int[] newArr = new int[23];
			// Pirmą pusę kiekvienos eilutės užpildome tapatybės matrica
			for (int j = 0; j < 12; j++) {
				newArr[j] = rowI[j];
			}
			// Antrąją pusę užpildome matrica B
			for (int j = 0; j < 11; j++) {
				newArr[j+12] = rowB[j];
			}
			h[i] = newArr;
		}
		
		return h;
	}
	
	/**
	 * Sukuria Golėjaus kodo kontrolinę matricą H
	 * 
	 * Grąžina dvimatį sveikų skaičių kontrolinės matricos masyvą (24x12)
	 */
	public int[][] getHMatrix() {
		int[][] res = new int[24][12];
		for (int i = 0; i < 12; i++) {
			res[i] = I[i];
			res[i+12] = B12[i];
		}
		return res;
	}
	
	/** 
	 * Konvertuoja eilutės tipo vektorių į sveikų skaičių masyvą, kadangi juos lengviau apdoroti.
	 * Programoje naudojami tik dvinariai vektoriai, taigi greičiausiai ši eilutė bus sudaryta iš 0 ir 1.
	 * 
	 * Parametras v - eilutės tipo vektorius.
	 * 
	 * Grąžina sveikų skaičių masyvą, atitinkantį duotą vektorių
	 */
	public int[] vectorStringToArray(String v) {
		int[] result = new int[v.length()];
		for (int i = 0; i < v.length(); i++) {
			result[i] = Integer.parseInt(String.valueOf(v.charAt(i)));
		}
		return result;
	}
	
	/**
	 * Apskaičiuoja vektoriaus svorį - skaičių bitų, kurie yra lugūs 1.
	 * 
	 * Parametras v - sveikų skaičių masyvas 
	 * 
	 * Grąžina sveiką skaičių, kuris yra parametro v svoris
	 */
	public int calculateVectorWeight(int[] v) {
		int weight = 0;
		for (int i = 0; i < v.length; i++) {
			if (v[i] == 1) {
				weight++;
			}
		}
		return weight;
	}
	
	/**
	 * Metodas naudojamas C23 dekodavimo algoritme. 
	 * Vektorių, kurio ilgis yra 23, metodas papildo vienu bitu, tam kad padaryti vektoriaus svorį nelyginį.
	 * Jei svoris lyginis, vektorius papildomas vienetu.
	 * Jei ne, vektorius papildomas 0.
	 * 
	 * Parametras v - 23 ilgio vektorius, atvaizduotas sveikų skaičių masyve.
	 * 
	 * Grąžina ilgio 24 sveikų skaičių masyvą, kurio svoris nelyginis. 
	 */
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
	
	/**
	 * Užkoduoja ilgio 12 vektorių Golėjaus kodu.
	 * Tai daroma sudauginant pateiktą vektorių su generuojančia matrica G.
	 * 
	 * Parametras vectorToEncode - sveikų skaičių masyvo vektorius, kuris bus užkoduotas
	 * 
	 * Grąžina sveikų skaičių ilgio 23 masyvą, kas yra užkoduotas Golėjaus kodu vektorius.
	 */
	public int[] encodeC23(int[] vectorToEncode) {
		int[][] modifiedVector = new int[1][];
		modifiedVector[0] = vectorToEncode;
		return this.multiplyMatrices(modifiedVector, this.getG23Matrix())[0];
	}
	
	/**
	 * Dekoduoja Golėjaus kodu užkoduotą vektorių.
	 * Didžioji dalis darbo atliekama kito metodo, calculateErrorVector.
	 * Turint klaidų vektorių, pakanka jį susumuoti su dekoduojamu vektoriu ir grąžinti pirmus 12 bitų.
	 * Tai ir bus dekoduotas rezultatas.
	 * 
	 * Parametras vectorToDecode - vectorius, kuris bus dekoduotas
	 *  
	 * Grąžina dekoduotą ilgio 12 vektorių, arba null, jei nepavyko rasti teisingo klaidų vektoriaus.
	 */
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
//					System.out.println("found sB + bi vector with i=" + i + Arrays.toString(testVector));
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
