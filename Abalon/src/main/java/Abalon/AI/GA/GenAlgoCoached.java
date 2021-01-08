package Abalon.AI.GA;

import Abalon.AI.EvaluationFunction.NeutralEvalFunct;
import Abalon.AI.Tree.GetPossibleMoves;

import java.util.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/** 
 * This class can produce new populations of evaluation function weights based on supervised learning.
 * As a supervisor this version uses MiniMax algorirthm with Alpha Beta optimization.
 * In principle, MiniMax algorithm here can be replaced with any other AI that produces one-move decisions given the current state of the game only.
 * population size: 100
 * scoreSum function: number of marbles scored against MiniMax
 * selection: 70 iterations, random 10% of population
 * offset: for each selection iteration 2 members with max scoreSum function value produce a son
 * mutation chance: 5%
 * replacement: 70% of population with worst scoreSum function
 */
public class GenAlgoCoached {//extends Application {

	GetPossibleMoves getPossibleMoves = new GetPossibleMoves();

	public static void main(String[] args) {
		GenAlgoCoached obj = new GenAlgoCoached();
		
		//System.out.println();
		//for (int i = 0; i < 100; i++) {
		//	obj.GenAlgoCoached(i + 1);
		//	System.out.println("population " + (i + 1) + " produced");
		//}

		obj.GenAlgoCoached(1);
	}

	public void GenAlgoCoached(int gen_number) {
		Vec[] population = new Vec[100];
		try {
			Log.init(gen_number);
			population = Log.read();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		int[] scoreSum = new int[population.length];
		for (int i = 0; i < population.length; i++) {
			System.out.println(String.format("game #%d", i));
			population[i].normalize();
			scoreSum[i] = playGame(population[i]);	
		}

		ArrayList<Integer> order = new ArrayList<>();
		for (int i = 0; i < population.length; i++)
			order.add(i);

		order.sort(new Comparator<Integer>() {
			public int compare(Integer p1, Integer p2) {
				if (scoreSum[p1] < scoreSum[p2])
					return -1;
				else if (scoreSum[p1] == scoreSum[p2])
					return 0;
				else
					return 1;
			}
		});

		Vec[] temp_population = new Vec[population.length];
		int[] temp_scoreSum = new int[population.length];

		for (int i = 0; i < population.length; i++) {
			temp_population[i] = new Vec(population[i]);
			temp_scoreSum[i] = new Integer(scoreSum[i]);
		}

		for (int i = 0; i < population.length; i++) {
			population[i] = temp_population[order.get(i)];
			scoreSum[i] = temp_scoreSum[order.get(i)];
		}

		Vec[] offset = new Vec[population.length * 75 / 10];
		for (int i = 0; i < population.length * 75 / 10; i++) {
			Collections.shuffle(order);

			int mx1 = -1, mx2 = -1;
			for (int j = 0; j < population.length / 10; j++) {
				int currentID = order.get(j);
				if (mx1 == -1) {
					mx1 = currentID;
				} else if (mx2 == -1) {
					mx2 = currentID;
				} else if (scoreSum[currentID] > scoreSum[mx1]) {
					mx2 = mx1;
					mx1 = currentID;
				} else if (scoreSum[currentID] > scoreSum[mx2]) {
					mx2 = currentID;
				}
			}

			Vec son = Vec.add(Vec.multiply(population[mx1], scoreSum[mx1]), 
				              Vec.multiply(population[mx2], scoreSum[mx2]));
			son.normalize();
			offset[i] = son;

			Vec old_son = son;
			boolean result = mutate(offset[i]);
		}

		for (int i = 0; i < population.length * 75 / 100; i++) 
			population[i] = offset[i];

		try {
			Log.writeResultPopulation(population);
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static boolean mutate(Vec v) {
		try {
			SecureRandom r = SecureRandom.getInstance("SHA1PRNG");
			double prob = r.nextDouble();
			if (prob >= 0.05)
				return false;

			double val = r.nextDouble() * 2 / 5;
			val -= 0.2;
			if (val == 0)
				val = 0.2;
			int id = r.nextInt(v.length);
			v.v[id] += val;
			v.normalize();
			return true;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return false;
	}

	private int[][] getRootBoardState() {
		int[][] rootCellColor = new int[][]{
            {2, 2, 2, 2, 2, -1, -1, -1, -1},
            {2, 2, 2, 2, 2,  2, -1, -1, -1},
            {0, 0, 2, 2, 2,  0,  0, -1, -1},
            {0, 0, 0, 0, 0,  0,  0,  0, -1},
            {0, 0, 0, 0, 0,  0,  0,  0,  0},
            {0, 0, 0, 0, 0,  0,  0,  0, -1},
            {0, 0, 1, 1, 1,  0,  0, -1, -1},
            {1, 1, 1, 1, 1,  1, -1, -1, -1},
            {1, 1, 1, 1, 1, -1, -1, -1, -1}};
    	return rootCellColor;
	}

	public int playGame(Vec p) {

		int[][] currentBoardState = getRootBoardState();
		int[][] rootBoardState = getRootBoardState();
		int currentPlayer = 1;

		int move_cnt = 0;
		boolean break_flag = false;
		while (move_cnt < 1000) {	
			move_cnt++;

			int[][] nextBoardState = null;

			if (currentPlayer == 1) {
				ArrayList<int[][]> childrenStates = getPossibleMoves.getPossibleMoves(currentBoardState, currentPlayer);

				double max_score = Double.NEGATIVE_INFINITY;
				for (int[][] child : childrenStates) {
					NeutralEvalFunct func = new NeutralEvalFunct(currentPlayer, child, rootBoardState);
					func.changeModus(p.toDouble());

					if (func.isWin()) {
						break_flag = true;
						break;
					}

					double score = func.evaluate();
					if (score > max_score) {
						nextBoardState = child;
						max_score = score;
					}
				}
			} else {
				// here we dont use MiniMax explicitly since with its current speed we would be able to use it for subtrees of depth 2 maximum anyways
				// hence, for optimization reasons we use default values in NeutralEvalFunct that are also used by default in MiniMax

				ArrayList<int[][]> childrenStates = getPossibleMoves.getPossibleMoves(currentBoardState, currentPlayer);

				double max_score = Double.NEGATIVE_INFINITY;
				for (int[][] child : childrenStates) {
					NeutralEvalFunct func = new NeutralEvalFunct(currentPlayer, child, rootBoardState);
					
					if (func.isWin()) {
						break_flag = true;
						break;
					}

					double score = func.evaluate();
					if (score > max_score) {
						nextBoardState = child;
						max_score = score;
					}
				}
			}

			if (break_flag)
				break;

			currentBoardState = nextBoardState;
			currentPlayer = (currentPlayer == 1 ? 2 : 1);
		}

		int score1 = 14;
		for (int i = 0; i < currentBoardState.length; i++) {
            for (int j = 0; j < currentBoardState[0].length; j++) {
                if (currentBoardState[i][j] == 2) {
                    score1--;
                }
            }
        }

		int score2 = 14;
		for (int i = 0; i < currentBoardState.length; i++) {
            for (int j = 0; j < currentBoardState[0].length; j++) {
                if (currentBoardState[i][j] == 1) {
                    score2--;
                }
            }
        }  

        if (score1 >= score2)
        	return score1;
        return -score2;
	}
}