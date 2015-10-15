package kripto;

import java.util.Arrays;

/**
 * 
 * @author Emilis Antanas Rupeika
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
	 * Grąžina matricų daugybos rezultatą, sveikų skaičių dvimatį masyvą (NxP)
	 */
	public int[][] multiplyMatrices(int[][] m1, int[][] m2) {
		int[][] res = new int[m1.length][m2[0].length];
		for (int i = 0; i < m1.length; i++) {
			for (int j = 0; j < m2[0].length; j++) {
				int sum = 0;
				for (int k = 0; k < m2.length; k++) {
					sum += m1[i][k] * m2[k][j];
				}
				// q = 2, todėl reikia skaičiuoti liekanas dalinant iš 2.
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
		// u gali įgyti null reikšmę, todėl tą reikia patikrinti.
		if (u != null) {
			int[] res = sumVectors(u, wi);
			return Arrays.copyOfRange(res, 0, 12);
		} else {
			return null;
		}
	}
	
	/**
	 * Apskaičiuoja klaidų vektorių u.
	 * Jis naudojamas mėginant ištaisyti klaidas, esančias iš kanalo gautame kode.
	 * 
	 * Parametras v - dekoduojamas ilgio 24 vektorius sveikų skaičių masyve
	 * 
	 * Grąžina vektorių u - ilgio 24 sveikų skaičių masyvą.
	 */
	public int[] calculateErrorVector(int[] v) {
		int[] errorVector = new int[24];
		int[] syndrome = new int[12];
		
		// vektorius v pakeičiamas į dvimatį masyvą tam, kad jį būtų galima sudauginti su kita matrica
		int[][] temp2dVector = new int[1][12];
		temp2dVector[0] = v;
		
		// apskaičiuojamas sindromas dauginant dekoduojamą vektorių su generuojančia matrica G.
		syndrome = multiplyMatrices(temp2dVector, G)[0];
		
		int weight = calculateVectorWeight(syndrome);
		if (weight <= 3) {
			// šiuo atveju vektoriaus u apskaičiavimas paprastas - u = [s, 0]
			// tai yra 12-os bitų sindromas kartu su 12-a bitų lygių 0.
			for (int i = 0; i < 12; i++) {
				errorVector[i] = syndrome[i];
				errorVector[i+12] = 0;
			}
			// vektorius apskaičiuotas, galima užbaigti darbą
			return errorVector;
		} else {
			// jei nepavyko u gauti pirmu būdu, mėginama rasti tokį vektorių, kurio
			// svoris(sindromas + bi) <= 2.
			int[] testVector = new int[12];
			for (int i = 0; i < 12; i++) {
				testVector = sumVectors(syndrome, B12[i]);
				if (calculateVectorWeight(testVector) <= 2) {
					// suradus tokį vektorių, u = [s + bi, ei]
					// tai yra 12-os bitų ilgio vektorius s + bi 
					// kartu su 11-a nulių ir vienu vienetu, esančiu i-ojoje pozicijoje
					for (int j = 0; j < 12; j++) {
						errorVector[j] = testVector[j];
						errorVector[j+12] = 0;
					}
					errorVector[12 + i] = 1;
					// darbas baigiamas
					return errorVector;
				}
			}
		}
		
		// jei pirmame etape nepavyko rasti klaidų vektoriaus, pereinama į antrąjį.
		// apskaičiuojamas naujas sindromas sB - senasis sindromas dauginamas iš matricos B.
		int[][] firstSyndrome = new int[1][12];
		firstSyndrome[0] = syndrome;
		int[] secondSyndrome = multiplyMatrices(firstSyndrome, B12)[0];
		weight = calculateVectorWeight(secondSyndrome);
		if (weight <= 3) {
			// šiuo atveju vektoriaus u apskaičiavimas paprastas - u = [0, sB + bi].
			for (int i = 0; i < 12; i++) {
				errorVector[i] = 0;
				errorVector[i+12] = secondSyndrome[i];
			}
			// darbas baigiamas
			return errorVector;
		} else {
			// priešingu atveju, mėginamas rasti toks vektorius sB + bi, kad
			// svoris(sB + bi) <= 2.
			int[] testVector = new int[12];
			for (int i = 0; i < 12; i++) {
				testVector = sumVectors(secondSyndrome, B12[i]);
				if (calculateVectorWeight(testVector) <= 2) {
					// jį suradus, u = [ei, sB + bi].
					for (int j = 0; j < 12; j++) {
						errorVector[j] = 0;
						errorVector[j+12] = testVector[j];
					}
					errorVector[i] = 1;
					// darbas baigiamas
					return errorVector;
				}
			}
		}
		// jei abu etapai nepavyko, vektoriaus apskaičiavimas neįmanomas, todėl grąžinama null reikšmė.
		return null;
	}
	
	/**
	 * Susumuoja du vienodo ilgio vektorius, q = 2. 
	 * Sumuojamas kiekvienas elementas, grąžinamas rezultatas yra tokio pačio ilgio vektorius
	 * 
	 * Parametrai v1 ir v2 - vektoriai, kurie bus sumuojami
	 * 
	 * Grąžina susumuotą vektorių, sudarytą iš 0 ir 1.
	 */
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
