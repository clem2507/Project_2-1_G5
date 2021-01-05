package Abalon.AI;

public class Entry {

    private double score;
    private int depth;

    public Entry(double score, int depth) {

        this.score = score;
        this.depth = depth;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
