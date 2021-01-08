package Abalon.AI.GA;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Author: Ivan Poliakov
 * This is the file that I use in majority of my projects involving vector output
 * IO only
 */ 
public class Log {
	private static SecureRandom r;
	public static String filename, resultFilename, readFileName;

	public static void init(int n) throws IOException {
		filename = "Abalon/res/genalgo/population-" + Integer.toString(n) + ".log";
		PrintWriter log = new PrintWriter(filename, "UTF-8");
		log.close();

		resultFilename = "Abalon/res/genalgo/population-final-" + Integer.toString(n) + ".log";
		PrintWriter result = new PrintWriter(resultFilename, "UTF-8");
		result.close();

		readFileName = "Abalon/res/genalgo/population-final-" + Integer.toString(n - 1) + ".log";

		try {
			r = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static Vec[] read() throws IOException {
		File input = new File(readFileName);
        Scanner scanner = new Scanner(input);
        ArrayList<Vec> list = new ArrayList<>();
        for (int j = 0; j < 100; j++) {
        	String trash = scanner.next();
        	trash = scanner.next();
	        Vec current = new Vec(6);	
	        for (int i = 0; i < 6; i++) {
	        	current.v[i] = Double.parseDouble(scanner.next());
	        }
	        list.add(current);
	        scanner.nextLine();
	    }
	    Vec[] result = new Vec[list.size()];
	    for (int i = 0; i < list.size(); i++)
	    	result[i] = list.get(i);
	    return result;
	}

	public static void generateLog(int population) throws IOException {
		PrintWriter init = new PrintWriter("Abalon/res/genalgo/population-final-0.log", "UTF-8");

		for (int i = 0; i < population; i++) {
			Vec current = new Vec(6);
			for (int j = 0; j < 6; j++) {
				current.v[j] = r.nextDouble();
				current.v[j] -= 0.5;
				if (current.v[j] == 0) 
					current.v[j] = 0.5;
			}
			current.normalize();

			init.printf("%-4s: ", Integer.toString(i));
			for (int j = 0; j < 6; j++)
				init.printf("%-50s ", Double.toString(current.v[j]));
			init.println();	
		}
		init.close();
	}

	public static void writeResultPopulation(Vec[] population) throws IOException {
		FileWriter fw = new FileWriter(resultFilename, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter log = new PrintWriter(bw);

		for (int i = 0; i < population.length; i++) {
			log.printf("%-4s: ", Integer.toString(i));
			for (int j = 0; j < 6; j++) {
				log.printf("%-50s ", Double.toString(population[i].v[j]));
			}
			log.println();
		}

		log.close();
		bw.close();
		fw.close();
	}
}