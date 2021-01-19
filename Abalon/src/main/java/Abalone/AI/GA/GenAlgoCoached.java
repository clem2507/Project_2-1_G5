package Abalon.AI.GA;

import Abalone.AI.EvaluationFunction.NeutralEvalFunct;
import Abalone.AI.EvaluationFunction.OffensiveEvalFunct;
import Abalone.AI.Tree.GetPossibleMoves;
import Abalone.UI.BoardUI;
import Abalone.AI.MCTS.MCTS;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.application.Platform;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.File;
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

public class GenAlgoCoached {// extends Application {

	public GetPossibleMoves getPossibleMoves = new GetPossibleMoves();
	//public BorderPane pane = new BorderPane();
	//public Stage primaryStage;
	private final double WIDTH = 1270;
    private final double HEIGHT = 700;

    public Log logger = new Log();

    //public BoardUI board;

	/*public static void main(String[] args) {
		launch();
	}

	@Override 
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {
            BufferedImage buffer = ImageIO.read(new File("./res/grey2.jpg"));
            Image background = SwingFXUtils.toFXImage(buffer, null);
            ImageView view = new ImageView(background);
            pane.getChildren().addAll(view);
        } catch (Exception e) {
            System.out.println("file not found");
            System.exit(0);
        }

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        this.primaryStage.setResizable(false);
        this.primaryStage.setTitle("Abalone");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        this.primaryStage.requestFocus();

        board = new BoardUI();
        pane.setCenter(board.hexagon);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                if (board.circles[i][j] != null)
                    pane.getChildren().add(board.circles[i][j]);
        }

        for (int i = 0; i < 6; i++){
            pane.getChildren().add(board.scoredCircles[i][0]);
            pane.getChildren().add(board.scoredCircles[i][1]);
        }

        Thread gaThread = new Thread(new Runnable() {
        	@Override
            public void run() {
            	GenAlgoCoached obj = new GenAlgoCoached();
            	obj.runGA(1, 0, board);
            }
        });
        gaThread.setDaemon(false);
        gaThread.start();
		

		//System.out.println();
		//for (int i = 0; i < 100; i++) {
		//	obj.GenAlgoCoached(i + 1);
		//	System.out.println("population " + (i + 1) + " produced");
		//}
	}*/

	public static void main(String[] args) {
		GenAlgoCoached obj = new GenAlgoCoached();
		obj.runGA(1, 1);
	}

	public void runGA(int gen_number, int postfix){//, BoardUI board) {
		//this.board = board;
		Vec[] population = new Vec[100];
		try {
			logger.init(gen_number, postfix);
			population = logger.read();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		int[] scoreSum = new int[population.length];
		for (int i = 0; i < population.length; i++) {
			System.out.println(String.format("game #%d", i));
			population[i].normalize();
			scoreSum[i] = playGame(population[i]);	
			System.out.println(scoreSum[i]);
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
			logger.writeResultPopulation(population);
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
			//if (val < 0)
			//	val = 0;
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

		//System.out.println(p.toString());
		int[][] currentBoardState = getRootBoardState();
		int[][] rootBoardState = getRootBoardState();
		int currentPlayer = 1;

		int move_cnt = 0;
		boolean break_flag = false;
		
		/*int[][] effectiveBoardState = new int[9][9];
		for (int i = 0; i < 9; i++) 
			for (int j = 0; j < 9; j++)
				effectiveBoardState[i][j] = currentBoardState[i][j];
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				final int[][] boardCopy = effectiveBoardState;

				//if (board == null)	
				//	System.out.println("bad copy");

				board.setBoard(boardCopy);
				board.createScoredCirclesColors();
				board.drawAllCells();
			}
		});*/

		while (move_cnt < 200) {	
			move_cnt++;
			//System.out.println("move " + move_cnt);

			int[][] nextBoardState = null;

			if (currentPlayer == 1) {
				ArrayList<int[][]> childrenStates = getPossibleMoves.getPossibleMoves(currentBoardState, currentPlayer);

				double max_score = Double.NEGATIVE_INFINITY;
				for (int[][] child : childrenStates) {
					OffensiveEvalFunct func = new OffensiveEvalFunct(currentPlayer, child, rootBoardState);
					func.setUnifiedWeights(p.toDouble());
					//Vec to_print = func.getModus();
					//System.out.println("modus " + to_print.toString());

					/*if (move_cnt > 300) {
						int[][] effectiveBoardState2 = new int[9][9];
						for (int i = 0; i < 9; i++) 
							for (int j = 0; j < 9; j++)
								effectiveBoardState2[i][j] = child[i][j];
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								final int[][] boardCopy = effectiveBoardState2;
								//if (board == null)	
								//	System.out.println("bad copy");
								board.setBoard(boardCopy);
								board.drawAllCells();
							}
						});

						try {
						    Thread.sleep(1000);
						} catch(InterruptedException ex) {
						    Thread.currentThread().interrupt();
						}
					}*/
					if (NeutralEvalFunct.isWin(child)) {
						nextBoardState = child;
						break_flag = true;
						break;
					}

					double score = func.evaluate();
					//System.out.println(score);
					if (score > max_score) {
						nextBoardState = child;
						max_score = score;
					}
				}

				/*MCTS monteCarlo = new MCTS(currentBoardState, currentPlayer, 100, 10, 1);
				monteCarlo.setUnifiedWeights(p.v);
				monteCarlo.start();
				nextBoardState = monteCarlo.getBestMove();
				*/
				//System.out.println("ga just made a move");
			} else {
				// here we dont use MiniMax explicitly since with its current speed we would be able to use it for subtrees of depth 2 maximum anyways
				// hence, for optimization reasons we use default values in NeutralEvalFunct that are also used by default in MiniMax

				ArrayList<int[][]> childrenStates = getPossibleMoves.getPossibleMoves(currentBoardState, currentPlayer);

				double max_score = Double.NEGATIVE_INFINITY;
				for (int[][] child : childrenStates) {
					OffensiveEvalFunct func = new OffensiveEvalFunct(currentPlayer, child, rootBoardState);
					//System.out.println("modus " + func.getModus().toString());

					if (NeutralEvalFunct.isWin(child)) {
						nextBoardState = child;
						break_flag = true;
						break;
					}

					double score = func.evaluate();
					if (score > max_score) {
						nextBoardState = child;
						max_score = score;
					}
				}

				/*MCTS monteCarlo = new MCTS(currentBoardState, currentPlayer, 100, 10, 1);
				monteCarlo.start();
				nextBoardState = monteCarlo.getBestMove();
				*/
			}

			currentBoardState = nextBoardState;
			currentPlayer = (currentPlayer == 1 ? 2 : 1);

			if (break_flag) {
				//System.out.println(move_cnt);
				break;
			}
			
			/*int[][] effectiveBoardState1 = new int[9][9];
			for (int i = 0; i < 9; i++) 
				for (int j = 0; j < 9; j++)
					effectiveBoardState1[i][j] = currentBoardState[i][j];

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					final int[][] boardCopy = effectiveBoardState1;
					//if (board == null)	
					//	System.out.println("bad copy");
					board.setBoard(boardCopy);
					board.drawAllCells();
				}
			});*/

			try {
			    Thread.sleep(100);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
/*
		MCTS monteCarlo = new MCTS(board.getBoard(), currentPlayer,timer,10, strategy2);
					monteCarlo.start();
					board.setBoard(monteCarlo.getBestMove());
					currentPlayer = 1;
*/
					/*
		GameTree gameTree = new GameTree(strategy2, 1, true);
					gameTree.createTree(board.getBoard(), currentPlayer, 3);
					AlphaBetaSearch algo = new AlphaBetaSearch(gameTree);
					algo.start(true);
					board.setBoard(algo.getBestMove());
					currentPlayer = 1;
					*/

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

        /*if (score1 >= score2) {
        	System.out.println("first won");
        } else {
        	System.out.println("second won");
        }*/

        if (score1 >= score2)
        	return score1;
        return -score2;
	}
}