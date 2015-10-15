package kripto;

/**
 * Klasė atliekanti matricų daugybą ir tapatybės matricos generavimą.
 * 
 * @author Emilis Antanas Rupeika
 *
 */
public class MatrixOperations {
	
	/**
	 * Metodas sukuria tapatybės matricą NxN dydžio.
	 * 
	 * Parametras n - tapatybės matricos dydis sveiko skaičiaus formatu
	 * Grąžina dvimatį masyvą su tapatybės matrica
	 */
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

	/**
	 * Iteratyviai daugina dvi sveikų skaičių matricas, pateiktas per parametrus.
	 * 
	 * Parametras m1 - dvimatis sveikų skaičių (NxM) pirmos matricos masyvas
	 * Parametras m2 - dvimatis sveikų skaičių (MxP) antros matricos masyvas
	 * 
	 * Grąžina matricų daugybos rezultatą, sveikų skaičių dvimatį masyvą (NxP)
	 */
	public static int[][] multiplyMatrices(int[][] m1, int[][] m2) {
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
}
