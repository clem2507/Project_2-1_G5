package Abalon.AI.Output;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Deals with the simulations' results and outputs them to a CSV file
 */
public class OutputCSV {

//    private int totalSimulation;
//
//    private float[] ab_time_tab;
//    private float[] mcts_time_tab;
//    private double[] gt_nodes_tab;
//    private int[] numb_turn_tab;
//    private String[] first_marble_tab;
//    private String[] winner_tab;
//    private String[] winning_strategy_tab;
//
//    private int[] time_tab;
//    private int[] sample_size_tab;
//    private int[] plays_tab;
//    private int[] win_rate_tab;

    private String fileName;
    private String stringHeaderResume;

    private String filePath;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;


    public OutputCSV(String fileName, String headerResume) {

//        this.ab_time_tab = new float[totalSimulation];
//        this.mcts_time_tab = new float[totalSimulation];
//        this.gt_nodes_tab = new double[totalSimulation];
//        this.numb_turn_tab = new int[totalSimulation];
//        this.first_marble_tab = new String[totalSimulation];
//        this.winner_tab = new String[totalSimulation];
//        this.winning_strategy_tab = new String[totalSimulation];
//
//        this.time_tab = new int[totalSimulation];
//        this.sample_size_tab = new int[totalSimulation];
//        this.plays_tab = new int[totalSimulation];
//
//        this.totalSimulation = totalSimulation;
//        this.fileName = fileName;

        //if (choice == 1) {
            //stringHeaderResume = "Alpha-Beta(time_avg), MCTS(time_avg), gt_nodes(avg), #turn, first_marble, winner, winning_strategy";
        //}
        //else {
        this.fileName = fileName;
        this.stringHeaderResume = headerResume;

        this.filePath = fileName;
        try {
            this.fileWriter = new FileWriter(filePath,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.bufferedWriter = new BufferedWriter(fileWriter);
        this.printWriter = new PrintWriter(bufferedWriter);
        //}
    }

//    public void add(int index, float ab_time, float mcts_time, double gt_nodes, int numb_turn, String first_marble, String winner, String winning_strategy){
//
//        ab_time_tab[index] = ab_time;
//        mcts_time_tab[index] = mcts_time;
//        gt_nodes_tab[index] = gt_nodes;
//        numb_turn_tab[index] = numb_turn;
//        first_marble_tab[index] = first_marble;
//        winner_tab[index] = winner;
//        winning_strategy_tab[index] = winning_strategy;
//    }
//
//    public void add1(int index, int time, int sampleSize, int plays, int numb_turn, String winner, int win_rate, String winning_strategy){
//
//        time_tab[index] = time;
//        sample_size_tab[index] = sampleSize;
//        plays_tab[index] = plays;
//        numb_turn_tab[index] = numb_turn;
//        winner_tab[index] = winner;
//        win_rate_tab[index] = win_rate;
//        winning_strategy_tab[index] = winning_strategy;
//    }

    public void writeResume(boolean open, boolean close, String[] data) {

        try {
            if (open) {
                printWriter.println(stringHeaderResume);
                printWriter.println();
            }

            for (int i = 0; i < data.length; i++) {
                printWriter.print(data[i] + ", ");
            }
            printWriter.println();
            printWriter.flush();

            if (close) {
                printWriter.close();
                System.out.println();
                System.out.println("Data is save in: " + fileName);
            }
        }
        catch (Exception e) {
            System.out.println("Recorded not save");
        }
    }

    /*
    public void writeResume() {

        String[][] data = new String[totalSimulation][7];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (j == 0) {
                    data[i][j] = Float.toString(ab_time_tab[i]);
                }
                else if (j == 1) {
                    data[i][j] = Float.toString(mcts_time_tab[i]);
                }
                else if (j == 2) {
                    data[i][j] = Double.toString(gt_nodes_tab[i]);
                }
                else if (j == 3) {
                    data[i][j] = Integer.toString(numb_turn_tab[i]);
                }
                else if (j == 4) {
                    data[i][j] = first_marble_tab[i];
                }
                else if (j == 5){
                    data[i][j] = winner_tab[i];
                }
                else {
                    data[i][j] = winning_strategy_tab[i];
                }
            }
        }

        try {
            String filepath = fileName;
            FileWriter fileWriter = new FileWriter(filepath,false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            printWriter.println(stringHeaderResume);

            printWriter.println();

            for (int i = 0; i < data.length; i++) {
                printWriter.println(data[i][0]+","+data[i][1]+","+data[i][2]+","+data[i][3]+","+data[i][4]+","+data[i][5]+","+data[i][6]);
            }

            printWriter.flush();
            printWriter.close();
            System.out.println();
            System.out.println("Data is save in: " + fileName);
        }
        catch (Exception e) {
            System.out.println("Recorded not save");
        }
    }
    */
}

