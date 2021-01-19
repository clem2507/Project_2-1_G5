package Abalon.AI.GA;

import Abalon.AI.GA.Vec;

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
	private SecureRandom r;
	public String resultFilename, readFileName;
	public final int GENE_SIZE = 5;

	public void init(int n, int postfix) throws IOException {

		resultFilename = "./res/genalgo/population-final-" + Integer.toString(n) + "-" + Integer.toString(postfix) + ".log";
		PrintWriter result = new PrintWriter(resultFilename, "UTF-8");
		result.close();

		readFileName = "./res/genalgo/population-final-" + Integer.toString(n - 1) + "-" + Integer.toString(postfix) + ".log";

		try {
			r = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public Vec[] read() throws IOException {
		File input = new File(readFileName);
        Scanner scanner = new Scanner(input);
        ArrayList<Vec> list = new ArrayList<>();
        for (int j = 0; j < 100; j++) {
        	String trash = scanner.next();
        	trash = scanner.next();
	        Vec current = new Vec(GENE_SIZE);	
	        for (int i = 0; i < GENE_SIZE; i++) {
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

	public void generateLog(int population, int postfix) throws IOException {
		try {
			r = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(0);
		}

		PrintWriter init = new PrintWriter("./res/genalgo/population-final-0-" + Integer.toString(postfix)  + ".log", "UTF-8");

		for (int i = 0; i < population; i++) {
			Vec current = new Vec(GENE_SIZE);
			for (int j = 0; j < GENE_SIZE; j++) {
				current.v[j] = r.nextDouble();
				current.v[j] -= 0.5;
				if (current.v[j] == 0) 
					current.v[j] = 1;
			}
			current.normalize();

			init.printf("%-4s: ", Integer.toString(i));
			for (int j = 0; j < GENE_SIZE; j++)
				init.printf("%-50s ", Double.toString(current.v[j]));
			init.println();	
		}
		init.close();
	}

	public void writeResultPopulation(Vec[] population) throws IOException {
		FileWriter fw = new FileWriter(resultFilename, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter log = new PrintWriter(bw);

		for (int i = 0; i < population.length; i++) {
			log.printf("%-4s: ", Integer.toString(i));
			for (int j = 0; j < GENE_SIZE; j++) {
				log.printf("%-50s ", Double.toString(population[i].v[j]));
			}
			log.println();
		}

		log.close();
		bw.close();
		fw.close();
	}

	public void mixPopulations(int n, int postfixes) throws IOException {
		Log[] loggers = new Log[postfixes];
		for (int i = 0; i < postfixes; i++) {
			loggers[i] = new Log();
			loggers[i].init(n, i);
		}

		Vec[][] populations = new Vec[postfixes][100];
		for (int i = 0; i < postfixes; i++) {	
			populations[i] = loggers[i].read();
		}
	
		List<Vec> offset = new ArrayList<>();
		List<Integer> order = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			order.add(i);
		}

		Vec[][] result = new Vec[postfixes][100];
		for (int i = 0; i < postfixes; i++) {
			Collections.shuffle(order);
			for (int j = 0; j < 100; j++) {
				if (j < 50)
					result[i][j] = new Vec(populations[i][order.get(j)]);
				else
					offset.add(populations[i][order.get(j)]);
			}
		}	

		for (int i = 0; i < offset.size(); i++) {
			int postfix_num = i / 50;
			int id = i % 50;

			result[postfix_num][50 + id] = new Vec(offset.get(i));
		}

		for (int i = 0; i < postfixes; i++) {
			loggers[i].writeResultPopulation(result[i]);
		}
	}

	public void generateLogs() {
		try { 
			for (int i = 0; i < 2; i++) {
				Log logger = new Log();
				logger.generateLog(100, i);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateCoachedPopulations(int gen_id) {
		long startTime = System.nanoTime();
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			int id = i;
			Thread gaThread = new Thread(new Runnable() {
	        	@Override
	            public void run() {
	            	GenAlgoCoached obj = new GenAlgoCoached();
					//obj.runGA(gen_id, id);
	            }
	        });
	        threads.add(gaThread);
	        gaThread.setDaemon(false);
	        gaThread.start();
	        /*try {
		        gaThread.join();
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }*/
		}

		threads.forEach(t -> {
		    try {
		        t.join();
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		});

		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);
	}

	public static void main(String[] args) {
		Log obj = new Log();	
		//obj.generateLogs();

		//for (int i = 1; i < 50; i += 2) {
			try {
				//System.out.println("generation #" + i);
				obj.generateCoachedPopulations(3);
				System.out.println("ga done");
				obj.mixPopulations(4, 2);	
				System.out.println("migration done");
			} catch (IOException e) {
				e.printStackTrace();
			}
		//}

		/*try {
			obj.mixPopulations(3, 2);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		/*for (int i = 1; i < 100; i++) {
			generateCoachedPopulations();
		}*/
	}
}