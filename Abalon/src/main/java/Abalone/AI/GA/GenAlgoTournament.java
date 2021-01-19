package Abalon.AI.GA;

import Abalone.AI.EvaluationFunction.NeutralEvalFunct;
import Abalone.AI.Tree.GetPossibleMoves;
import Abalone.UI.BoardUI;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

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
 * This class can produce new populations of evaluation function weights based on tournament selection.
 * population size: 100
 * tournament: 10 iterations, random 10% of population
 * fitness function: number of marbles scored against all opponents in the same tournament
 * selection: winner of each tournament -> winner[i]
 * offset: for all (i, j) winner[i] and winner[j] produce a son
 * mutation chance: 5%
 * replacement: 100% of population replaced with offset
 */
public class GenAlgoTournament extends Application {

	private GetPossibleMoves getPossibleMoves = new GetPossibleMoves();
	public Log logger = new Log();

	public BorderPane pane = new BorderPane();
	public Stage primaryStage;
	private final double WIDTH = 1270;
    private final double HEIGHT = 700;

    public BoardUI board;

	public static void main(String[] args) {
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
            	GenAlgoTournament obj = new GenAlgoTournament();
            	obj.runGA(51, 0, board);
            }
        });
        gaThread.setDaemon(false);
        gaThread.start();
		

		//System.out.println();
		//for (int i = 0; i < 100; i++) {
		//	obj.GenAlgoCoached(i + 1);
		//	System.out.println("population " + (i + 1) + " produced");
		//}
	}	

	private int score1, score2;

	public void runGA(int gen_number, int postfix, BoardUI board) {
		this.board = board;
		Vec[] population = new Vec[100];

		try {
			logger.init(gen_number, postfix);
			population = logger.read();
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

	public Vec playTournament(Vec[] p, int tour_id_1, int tour_id_2) {
		System.out.println("entered tournament #" + Integer.toString(tour_id_2) + "-" + Integer.toString(tour_id_1));

		int[] scores = new int[p.length];
		int game_id = 0;
		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < i; j++) {
				Vec p1 = p[i];
				Vec p2 = p[j];

				System.out.println("game #" + Integer.toString(game_id));

				playGame(p1, p2);

				if (score1 == score2) {
					scores[i] += score1 / 2;
					scores[j] += score2 / 2;
				} else if (score1 > score2)
					scores[i] += score1;
				else
					scores[j] += score2;

				System.out.println(score1 + " " + score2);

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

	public void playGame(Vec p1, Vec p2) {

		int[][] currentBoardState = getRootBoardState();
		int[][] rootBoardState = getRootBoardState();
		int currentPlayer = 1;

		int move_cnt = 0;
		boolean break_flag = false;

		int[][] effectiveBoardState = new int[9][9];
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
		});

		while (move_cnt < 1000) {	
			move_cnt++;
			System.out.println("move " + move_cnt);


			ArrayList<int[][]> childrenStates = getPossibleMoves.getPossibleMoves(currentBoardState, currentPlayer);

			int[][] nextBoardState = null;
			double max_score = Double.NEGATIVE_INFINITY;
			for (int[][] child : childrenStates) {
				NeutralEvalFunct func = new NeutralEvalFunct(currentPlayer, child, rootBoardState);

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

				//if (currentPlayer == 1) 
				//	func.setUnifiedWeights(p1.v);
				//else
				//	func.setUnifiedWeights(p2.v);

				if (NeutralEvalFunct.isWin(child)) {
					break_flag = true;
					break;
				}

				double score = func.evaluate();
				System.out.println("score " + score);

				if (score > max_score) {
					nextBoardState = child;
					max_score = score;
				}
			}

			currentBoardState = nextBoardState;
			currentPlayer = (currentPlayer == 1 ? 2 : 1);
			
			if (break_flag)
				break;

			int[][] effectiveBoardState1 = new int[9][9];
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
			});

			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}

		score1 = 14;
		for (int i = 0; i < currentBoardState.length; i++) {
            for (int j = 0; j < currentBoardState[0].length; j++) {
                if (currentBoardState[i][j] == 2) {
                    score1--;
                }
            }
        }

		score2 = 14;
		for (int i = 0; i < currentBoardState.length; i++) {
            for (int j = 0; j < currentBoardState[0].length; j++) {
                if (currentBoardState[i][j] == 1) {
                    score2--;
                }
            }
        }       
	}
}