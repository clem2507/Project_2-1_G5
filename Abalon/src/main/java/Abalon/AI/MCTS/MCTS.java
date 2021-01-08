package Abalon.AI.MCTS;

import Abalon.AI.EvaluationFunction.NeutralEvalFunct;
import Abalon.AI.Output.Test;
import Abalon.AI.Tree.Edge;
import Abalon.AI.Tree.GetPossibleMoves;
import Abalon.AI.Tree.Node;

import java.util.ArrayList;

public class MCTS {

    private int[][] bestMove;
    private int currentPlayer;
    private GetPossibleMoves getPossibleMoves = new GetPossibleMoves();

    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();

    private Node root;

    private NeutralEvalFunct rootEvaluation;
    private double rootScore;

    private int count = 0;

    private int countForMean = 0;
    private double sumForMean = 0;

    private double mean = 0;

    private int timer;
    private int eval;
    private int sampleSize;
    private int numOfPlays;

    public MCTS(int[][] rootState, int currentPlayer, int timer, int eval, int sampleSize, int numOfPlays) {

        this.currentPlayer = currentPlayer;
        this.rootEvaluation = new NeutralEvalFunct(currentPlayer, rootState, rootState);
        this.rootScore = rootEvaluation.evaluate();
        this.root = new Node(rootState, 0, 0);
        this.nodes.add(root);
        this.timer = timer;
        // eval variable changes the final simulation board evaluation technique
        // 1 = compared to the root score
        // 2 = compared to the mean score so far
        this.eval = eval;
        this.sampleSize = sampleSize;
        this.numOfPlays = numOfPlays;
    }

    public void start() {

        long b_time = System.currentTimeMillis();
        int stopCondition = timer;
        while ((System.currentTimeMillis() - b_time) < stopCondition) {
            Selection();
            for (Node n : nodes) {
                System.out.print(n.getTotalSimulation() + ", ");
            }
            System.out.println();
            count++;
        }
        ArrayList<Node> rootChildren = getChildren(root);
        double maxSimulation = 0;
        for (Node child : rootChildren) {
            if (child.getTotalSimulation() > maxSimulation) {
                maxSimulation = child.getTotalSimulation();
                bestMove = child.getBoardState();
            }
        }
        System.out.println();
        Test.printBoard(bestMove);
    }

    public double uctValue(Node n) {

        int c = 2;
        int nodeWin = n.getTotalWin();
        int nodeVisits = n.getTotalSimulation();
        int parentVisits = getParent(n).getTotalSimulation();
        double meanScore;
        if (nodeVisits == 0) {
            return Double.POSITIVE_INFINITY;
        }
        else {
            meanScore = (double) nodeWin / nodeVisits;
        }
        return meanScore + (c * Math.sqrt(Math.log(parentVisits)/nodeVisits));
    }

    public Node findBestNodeWithUCT(Node n) {

        Node bestNode = new Node(null, 0, 0);
        double max = -1;
        for (Node node : getChildren(n)) {
            if (uctValue(node) > max) {
                max = uctValue(node);
                bestNode = node;
            }
        }
        //System.out.println("max = " + max);
        return bestNode;
    }

    public void Selection() {

        Node n = root;
        int actualPlayer = currentPlayer;
        if (count > 0) {
            while (getChildren(n).size() > 0) {
                n = findBestNodeWithUCT(n);
                if (actualPlayer == 1) {
                    actualPlayer = 2;
                } else {
                    actualPlayer = 1;
                }
            }
        }
        Expansion(n, actualPlayer);
    }

    public void Expansion(Node n, int currentPlayer) {

        ArrayList<int[][]> children = getPossibleMoves.getPossibleMoves(n.getBoardState(), currentPlayer);
        for (int[][] child : children) {
            Node childNode = new Node(child, 0, 0);
            nodes.add(childNode);
            Edge edge = new Edge(n, childNode);
            edges.add(edge);
        }
        int max = children.size();
        int randomIndex = (int)(Math.random() * ((max)));
        Simulation(getChildren(n).get(randomIndex), currentPlayer);
    }

    public void Simulation(Node n, int currentPlayer) {

        int simulationScore = 0;
        int numberOfSample = sampleSize;
        for (int i = 0; i < numberOfSample; i++) {
            int actualPlayer = currentPlayer;
            int[][] actualBoard = n.getBoardState();
            int numberOfPlays = numOfPlays;
            int countMoves = 0;
            while (countMoves < numberOfPlays) {
                if (actualPlayer == 1) {
                    actualPlayer = 2;
                }
                else {
                    actualPlayer = 1;
                }

                ArrayList<int[][]> children = getPossibleMoves.getPossibleMoves(actualBoard, actualPlayer);
                int max = children.size();
                int randomIndex = (int)(Math.random() * ((max)));

                if (children.size() > 0) {
                    actualBoard = children.get(randomIndex);
                }

                countMoves++;
            }

            NeutralEvalFunct evaluation = new NeutralEvalFunct(currentPlayer, actualBoard, n.getBoardState());

            if (eval == 1) {
                if (evaluation.evaluate() >= rootScore) {
                    simulationScore++;
                }
            }
            else {
                if (BoardEvaluation(evaluation.evaluate())) {
                    simulationScore++;
                }
            }
        }
        n.setTotalSimulation(n.getTotalSimulation() + 1);
        n.setTotalWin(n.getTotalWin() + simulationScore);

        Backpropagation(n, simulationScore);
    }

    public void Backpropagation(Node n, int simulationScore) {

        while (getParent(n) != null) {
            n = getParent(n);
            n.setTotalSimulation(n.getTotalSimulation() + 1);
            n.setTotalWin(n.getTotalWin() + simulationScore);
        }
        ArrayList<Node> rootChildren = getChildren(root);
        int sumWin = 0;
        int sumSimulation = 0;
        for (Node child : rootChildren) {
            sumWin += child.getTotalWin();
            sumSimulation += child.getTotalSimulation();
        }
        root.setTotalWin(sumWin);
        root.setTotalSimulation(sumSimulation);
    }

    public ArrayList<Node> getChildren(Node n) {

        ArrayList<Node> children = new ArrayList<>();
        for (Edge e : edges) {
            if (e.getSource() == n) {
                children.add(e.getDestination());
            }
        }
        return children;
    }

    public boolean BoardEvaluation(double score) {

        countForMean++;
        boolean result = false;
        if (score > mean) {
            result = true;
        }
        sumForMean += score;
        mean = sumForMean/countForMean;
        //System.out.println("mean = " + mean);
        return result;
    }

    public Node getParent(Node n) {

        Node parent = null;
        for (Edge e : edges) {
            if (e.getDestination() == n) {
                parent = e.getSource();
            }
        }
        return parent;
    }

    //    public ArrayList<Node> getNeighbours(Node n) {
//
//        ArrayList<Node> neighbours = new ArrayList<>();
//        for(Edge e : edges) {
//            if(e.getSource().equals(getParent(n))) {
//                neighbours.add(e.getDestination());
//            }
//        }
//        return neighbours;
//    }

//    public MCTS(int[][] boardState, int currentPlayer, int depth, int strategy){
//
//        this.boardState = boardState;
//        this.currentPlayer = currentPlayer;
//        this.rootEvaluation = new NeutralEvalFunct(currentPlayer, boardState, boardState);
//        this.rootScore = rootEvaluation.evaluate();
//        this.depth = depth;
//        this.strategy = strategy;
//    }
//
//    public void start(){
//
//        ArrayList<int[][]> rootChildren = getPossibleMoves.getPossibleMoves(boardState, currentPlayer);
//
//        int childrenSize = rootChildren.size();
//        int bestIndex = 0;
//        int maxScore = Integer.MIN_VALUE;
//        bestMove = new int[9][9];
//
//        for(int i = 0; i < childrenSize; i++) {
//            rootChildren = getPossibleMoves.getPossibleMoves(boardState, currentPlayer);
//            int score = simulation(rootChildren.get(i));
//            if (maxScore < score) {
//                maxScore = score;
//                bestIndex = i;
//            }
//        }
//        rootChildren = getPossibleMoves.getPossibleMoves(boardState, currentPlayer);
//        bestMove = rootChildren.get(bestIndex);
//    }
//
//    public int simulation(int[][] currentBoard){
//
//        int simulationScore = 0;
//
//        for (int i = 0; i < 100; i++) {
//            int actualPlayer = currentPlayer;
//            int[][] actualBoard = currentBoard;
//            int countMoves = 0;
//            while (countMoves <= depth) {
//                ArrayList<int[][]> children = getPossibleMoves.getPossibleMoves(actualBoard, actualPlayer);
//
//                int max = children.size();
//                int randomIndex = (int)(Math.random() * ((max)));
//
//                if (children.size() > 0) {
//                    actualBoard = children.get(randomIndex);
//                }
//
//                if (actualPlayer == 1) {
//                    actualPlayer = 2;
//                }
//                else {
//                    actualPlayer = 1;
//                }
//                countMoves++;
//            }
//
//            NeutralEvalFunct evaluation = new NeutralEvalFunct(currentPlayer, actualBoard, boardState);
//
//            if (evaluation.evaluate() >= rootScore) {
//                simulationScore++;
//            }
//            else {
//                simulationScore--;
//            }
//        }
//        return simulationScore;
//    }

    public int[][] getBestMove() {
        return bestMove;
    }
}
