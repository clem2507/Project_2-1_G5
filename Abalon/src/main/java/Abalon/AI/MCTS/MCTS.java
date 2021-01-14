package Abalon.AI.MCTS;

import Abalon.AI.EvaluationFunction.*;
import Abalon.AI.Output.Test;
import Abalon.AI.Tree.Edge;
import Abalon.AI.Tree.GetPossibleMoves;
import Abalon.AI.Tree.Node;

import java.util.ArrayList;

public class MCTS {

    private EvaluationFunction evaluationFunctionRoot;
    private EvaluationFunction evaluationFunction;

    private int[][] bestMove;
    private int currentPlayer;
    private GetPossibleMoves getPossibleMoves = new GetPossibleMoves();

    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();

    private Node root;

    private double rootScore;

    private int count = 0;

    private int timer;
    private int sampleSize;
    private int numOfPlays;
    private int strategy;

    public MCTS(int[][] rootState, int currentPlayer, int timer, int sampleSize, int numOfPlays, int strategy) {

        this.strategy = strategy;
        this.currentPlayer = currentPlayer;
        if (strategy == 1) {
            this.evaluationFunctionRoot = new NeutralEvalFunct(currentPlayer, rootState, rootState);
            this.rootScore = evaluationFunctionRoot.evaluate();
        }
        else if (strategy == 2) {
            this.evaluationFunctionRoot = new OffensiveEvalFunct(currentPlayer, rootState, rootState);
            this.rootScore = evaluationFunctionRoot.evaluate();
        }
        else if(strategy == 3){
            this.evaluationFunctionRoot = new DefensiveEvalFunct(currentPlayer, rootState, rootState);
            this.rootScore = evaluationFunctionRoot.evaluate();
        }else{
            //this.evaluationFunctionRoot = new MixEvalFunct(currentPlayer, rootState, rootState);
            //this.rootScore = evaluationFunctionRoot.evaluate();
        }
        this.root = new Node(rootState, 0, 0);
        this.nodes.add(root);
        this.timer = timer;
        this.sampleSize = sampleSize;
        this.numOfPlays = numOfPlays;
    }

    public void start() {

        long b_time = System.currentTimeMillis();
        int stopCondition = timer;
        while ((System.currentTimeMillis() - b_time) < stopCondition) {
            Selection();
            //for (Node n : nodes) {
              //  System.out.print(n.getTotalSimulation() + ", ");
            //}
            //System.out.println();
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
        //System.out.println("Simulations = " + nodes.get(0).getTotalSimulation());
        //System.out.println();
    }

    public double uctValue(Node n) {

        double c = 2;
        int nodeVisits = n.getTotalSimulation();
        int parentVisits = getParent(n).getTotalSimulation();
        double nodeWin = n.getTotalScore();
        double meanScore;
        if (nodeVisits == 0) {
            return Double.POSITIVE_INFINITY;
        }
        else {
            meanScore = nodeWin / nodeVisits;
        }
        return meanScore + (c * Math.sqrt(Math.log(parentVisits)/nodeVisits));
    }

    public Node findBestNodeWithUCT(Node n) {

        Node bestNode = new Node(null, 0, 0);
        double max = Double.NEGATIVE_INFINITY;
        for (Node node : getChildren(n)) {
            if (uctValue(node) > max) {
                max = uctValue(node);
                bestNode = node;
            }
        }
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
        if (this.currentPlayer == currentPlayer) {
            int max = children.size();
            int randomIndex = (int)(Math.random() * ((max)));
            Simulation(getChildren(n).get(randomIndex), currentPlayer);
        }
        else {
            Selection();
        }
    }

    public void Simulation(Node n, int currentPlayer) {

        double simulationScore = 0;
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

            if (strategy == 1) {
                evaluationFunction = new NeutralEvalFunct(currentPlayer, actualBoard, root.getBoardState());
                simulationScore += ponderationFunction(rootScore, evaluationFunction.evaluate());
            }
            else if (strategy == 2) {
                evaluationFunction = new OffensiveEvalFunct(currentPlayer, actualBoard, root.getBoardState());
                simulationScore += ponderationFunction(rootScore, evaluationFunction.evaluate());
            }
            else if (strategy == 3) {
                evaluationFunction = new DefensiveEvalFunct(currentPlayer, actualBoard, root.getBoardState());
                simulationScore += ponderationFunction(rootScore, evaluationFunction.evaluate());
            }
            else {
                //evaluationFunction = new MixEvalFunct(currentPlayer, actualBoard, root.getBoardState());
                //simulationScore += ponderationFunction(rootScore, evaluationFunction.evaluate());
            }
        }
        n.setTotalSimulation(n.getTotalSimulation() + 1);
        n.setTotalWin(n.getTotalScore() + simulationScore);

        Backpropagation(n, simulationScore);
    }

    public void Backpropagation(Node n, double simulationScore) {

        while (getParent(n) != null) {
            n = getParent(n);
            n.setTotalSimulation(n.getTotalSimulation() + 1);
            n.setTotalWin(n.getTotalScore() + simulationScore);
        }

        ArrayList<Node> rootChildren = getChildren(root);
        double sumScore = 0;
        int sumSimulation = 0;
        for (Node child : rootChildren) {
            sumScore += child.getTotalScore();
            sumSimulation += child.getTotalSimulation();
        }
        root.setTotalWin(sumScore);
        root.setTotalSimulation(sumSimulation);
    }

    public double ponderationFunction(double rootScore, double currentScore) {

        double score = 0;
        if (currentScore > rootScore) {
            //score = Math.log((Math.abs(currentScore-rootScore)) + 1) * scale;
            score = Math.sqrt(Math.abs(currentScore-rootScore))/5;
            //score = Math.pow(Math.abs(currentScore-rootScore), (double) 2/3)/10;
        }
        return score;
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

    public Node getParent(Node n) {

        Node parent = null;
        for (Edge e : edges) {
            if (e.getDestination() == n) {
                parent = e.getSource();
            }
        }
        return parent;
    }

    public int[][] getBestMove() {
        return bestMove;
    }
}

