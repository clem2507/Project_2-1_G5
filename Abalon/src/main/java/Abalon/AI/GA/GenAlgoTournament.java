package Abalon.AI.GA;

import Abalon.AI.EvaluationFunction.NeutralEvalFunct;
import Abalon.AI.Tree.GetPossibleMoves;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/** 
 * This class can produce new populations of evaluation function weights based on tournament selection.
 * population size: 100
 * tournament: 10 iterations, random 10% of population
 * fitness function: number of marbles scored against all opponents in the same tournament
 * selection: winner of each tournament -> winner[i]
 * offset: for all (i, j) winner[i] and winner[j] produce a son
 * mutation chance: 5%
 * replacement: 100% of population replaced with offset
 */
public class GenAlgoTournament {//extends Application {

	private GetPossibleMoves getPossibleMoves = new GetPossibleMoves();

	public static void main(String[] args) {
		GenAlgoTournament obj = new GenAlgoTournament();
		
		//System.out.println();
		//for (int i = 0; i < 10; i++) {
		//	obj.genAlgo(i + 1);
		//	System.out.println("population " + (i + 1) + " produced");
		//}

		obj.genAlgo(1);
	}

	public void genAlgo(int gen_number) {
		Vec[] population = new Vec[100];
		
		try {
			Log.init(gen_number);
			population = Log.read();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		Vec[] tournament_winners = new Vec[10];
		List<Integer> order = new ArrayList();
		for (int i = 0; i < population.length; i++)
			order.add(i);

		for (int i = 0; i < 10; i++) {
			Collections.shuffle(order);

			Vec[] tournament = new Vec[10];
			for (int j = 0; j < tournament.length; j++)
				tournament[j] = population[order.get(j)];

			Vec winner = playTournament(tournament, i, gen_number);
			tournament_winners[i] = winner;
		}

		Vec[] offset = new Vec[population.length];
		int son_id = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Vec son = Vec.add(tournament_winners[j], tournament_winners[i]);
				son.normalize();
				offset[son_id] = son;
				son_id++;

				Vec old_son = son;
				boolean result = mutate(offset[i]);
			}
		}

		for (int i = 0; i < population.length; i++)
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

	public Vec playTournament(Vec[] p, int tour_id_1, int tour_id_2) {
		System.out.println("entered tournament #" + Integer.toString(tour_id_2) + "-" + Integer.toString(tour_id_1));

		int[] scores = new int[p.length];
		int game_id = 0;
		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < i; j++) {
				Vec p1 = p[i];
				Vec p2 = p[j];

				System.out.println("game #" + Integer.toString(game_id));

				int score = playGame(p1, p2);

				if (score >= 0)
					scores[i] += score;
				else
					scores[j] += -score;

				game_id++;
			}
		}
		
		int mx = 0;
		for (int i = 0; i < p.length; i++) {
			if (scores[i] > scores[mx]) {
				mx = i;
			}
		}

		return p[mx];
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

	public int playGame(Vec p1, Vec p2) {

		int[][] currentBoardState = getRootBoardState();
		int[][] rootBoardState = getRootBoardState();
		int currentPlayer = 1;

		int move_cnt = 0;
		boolean break_flag = false;
		while (move_cnt < 4000) {	
			move_cnt++;

			ArrayList<int[][]> childrenStates = getPossibleMoves.getPossibleMoves(currentBoardState, currentPlayer);

			int[][] nextBoardState = null;
			double max_score = Double.NEGATIVE_INFINITY;
			for (int[][] child : childrenStates) {
				NeutralEvalFunct func = new NeutralEvalFunct(currentPlayer, child, rootBoardState);

				if (currentPlayer == 1) 
					func.changeModus(p1.toDouble());
				else
					func.changeModus(p2.toDouble());

				if (NeutralEvalFunct.isWin(child)) {
					break_flag = true;
					break;
				}

				double score = func.evaluate();
				if (score > max_score) {
					nextBoardState = child;
					max_score = score;
				}
			}

			currentBoardState = nextBoardState;
			currentPlayer = (currentPlayer == 1 ? 2 : 1);
			
			if (break_flag)
				break;
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