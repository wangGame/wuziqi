package kw.wzq.ai;

public class Pos {
    private int row;
    private int col;
    private int index;

    public Pos(int row, int col) {
        this.row = row;
        this.col = col;
        this.index = row * Constant.N_ROW + col;
    }

    public Pos(int index) {
        this.index = index;
        this.row = index / Constant.N_ROW;
        this.col = index - this.row * Constant.N_ROW;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "(" + (this.row + 1) + ", " + (this.col + 1) + ")";
    }
}
