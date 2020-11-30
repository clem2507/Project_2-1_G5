package Abalon.AI;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Deals with the simulations' results and outputs them to a CSV file
 */
public class OutputCSV {

    private int totalSimulation;

    private float[] ab_time_tab;
    private float[] mcts_time_tab;
    private int[] numb_turn_tab;
    private String[] winner_tab;

    private String fileName;

    public OutputCSV(int totalSimulation, String fileName) {

        this.ab_time_tab = new float[totalSimulation];
        this.mcts_time_tab = new float[totalSimulation];
        this.numb_turn_tab = new int[totalSimulation];
        this.winner_tab = new String[totalSimulation];

        this.totalSimulation = totalSimulation;
        this.fileName = fileName;
    }

    public void set(int index, float ab_time, float mcts_time, int numb_turn, String winner){

        ab_time_tab[index] = ab_time;
        mcts_time_tab[index] = mcts_time;
        numb_turn_tab[index] = numb_turn;
        winner_tab[index] = winner;
    }

    private static String stringHeaderResume = "Alpha-Beta(time avg), MCTS(time avg), #turn, winner";

    public void writeResume() {

        String[][] data = new String[totalSimulation][4];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (j == 0) {
                    data[i][j] = Float.toString(ab_time_tab[i]);
                }
                else if (j == 1) {
                    data[i][j] = Float.toString(mcts_time_tab[i]);
                }
                else if (j == 2) {
                    data[i][j] = Integer.toString(numb_turn_tab[i]);
                }
                else {
                    data[i][j] = winner_tab[i];
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
                printWriter.println(data[i][0]+","+data[i][1]+","+data[i][2]+","+data[i][3]);
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
}
