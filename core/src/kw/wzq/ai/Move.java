package kw.wzq.ai;

public class Move implements Comparable<Move> {
    private final Pos next;
    private final int score;

    public Move(int score, Pos next) {
        this.score = score;
        this.next = next;
    }

    @Override
    public int compareTo(Move o) {
        return Integer.compare(this.score, o.score);
    }

    @Override
    public String toString() {
        return this.score + ":-->" + this.next;
    }

    public Pos getNext() {
        return null;
    }
}
