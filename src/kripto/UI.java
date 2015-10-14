package kripto;

import java.util.Scanner;

public class UI {
	
	public static int[] vectorStringToArray(String v) {
		int[] result = new int[v.length()];
		for (int i = 0; i < v.length(); i++) {
			result[i] = Integer.parseInt(String.valueOf(v.charAt(i)));
		}
		return result;
	}
	
	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]);
			if (i == 11) {
				System.out.print(" ");
			}
		}
		System.out.println();
	}
	
	public static boolean enteredVectorIsCorrect(String v, int length) {
		if (v.length() != length) {
			return false;
		}
		
		for (char ch : v.toCharArray()) {
			if (Character.isDigit(ch)) {
				int value = Character.getNumericValue(ch);
				if (value != 0 && value != 1) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
	
	public static void initUI() {
		GolayCodec codec = new GolayCodec();
		Channel channel = new Channel();
		Scanner scan = new Scanner(System.in);
		
		boolean exit = false;
		double probability = 0;
		System.out.println("Norint uzdaryti programa iveskite zodi \"quit\" ir spauskite Enter.");
		
		while (exit == false) {
			System.out.println("Iveskite binarini (0 arba 1) vektoriu, kurio ilgis 12 ir spauskite Enter. "
					+ "\nVektorius gali tureti tarpus.");
			String enteredData = scan.nextLine().trim().replaceAll("\\s", "");
			
			if (enteredData.toLowerCase().equals("quit")) {
				exit = true;
			} else {
				if (enteredVectorIsCorrect(enteredData, 12)) {
					boolean probabilityEntered = false;
						System.out.println("Iveskite klaidos siunciant kanalu tikimybe tarp 0 ir 1 (pvz 0.12):");
					while (probabilityEntered == false && exit == false) {
						String s = scan.nextLine().trim();
						if (s.toLowerCase().equals("quit")) {
							exit = true;
						} else {
							try {
								probability = Double.parseDouble(s);
								if (probability <= 1 && probability >= 0) {
									probabilityEntered = true;
								} else {
									System.out.println("Ivesta tikimybe nera tarp 0 ir 1.");
								}
							} catch (NumberFormatException e) {
								System.out.println("Ivesta tikimybe yra klaidingo formato.");
							}
						}
 					}
					
					if (exit == false) {
						int[] codeword = codec.encodeC23(codec.encodeC23(vectorStringToArray(enteredData)));
						System.out.println("Gautas kodas is ivesto vektoriaus:");
						printArray(codeword);
						System.out.println("Kodas siunciamas.");
						channel.setErrorProbability(probability);
						channel.sendCodeword(codeword);
						channel.receiveCodeword();
						int[] receivedCodeword = channel.getReceivedCodeword();
						System.out.println("Per kanala gautas kodas:");
						printArray(receivedCodeword);
						System.out.println("Siunciant is viso ivyko " + channel.errorPositions.size() + " klaidos. Klaidu pozicijos (1-23):");
						System.out.println(channel.errorPositions.toString());
						System.out.println("Jei norite pakeisti gauta vektoriu, iveskite ji. Jei ne, spauskite Enter.");
						enteredData = scan.nextLine().trim().replaceAll("\\s", "");
						if (enteredData.toLowerCase().equals("quit")) {
							exit = true;
						} else if (enteredVectorIsCorrect(enteredData, 23)) {
							System.out.println("Nustatytas naujas kodas. Dekoduotas vektorius:");
							channel.setReceivedCodeword(vectorStringToArray(enteredData));
							printArray(codec.decodeC23(channel.getReceivedCodeword()));
						} else {
							System.out.println("Naudojamas is kanalo gautas kodas. Dekoduotas vektorius:");
							printArray(codec.decodeC23(channel.getReceivedCodeword()));
						}
					}
				} else {
					System.out.println("Klaida: ivestas klaidingas vektorius.");
				}
				
			}			
		}
		scan.close();
		System.out.println("Uzdaroma...");
	}
	
	public static void main(String[] args) {
		initUI();
		
	}
}
