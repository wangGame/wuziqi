package kw.wzq.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer {
    public char marker;
    private List<Long> times;
    private List<Pos> path;
    private Random RANDOM;
    private int depth = 4;
    private int[][] history;
    private Move best;

    public ComputerPlayer(char marker) {
        this.marker = marker;
        this.times = new ArrayList<>();
        this.path = new ArrayList<>();
        this.RANDOM = new Random();
        this.history = buildHistory();
    }

    private int[][] buildHistory() {
        int[][] history = new int[Constant.N_ROW][Constant.N_COL];
        for (int i = 0; i < Constant.N_ROW; i++) {
            for (int j = 0; j < Constant.N_COL; j++) {
                history[i][j] = 0;
            }
        }
        return history;
    }

    private Move first() {
        return new Move(0, new Pos(
                Constant.N_ROW / 4 + RANDOM.nextInt(Constant.N_ROW) / 2,
                Constant.N_COL / 4 + RANDOM.nextInt(Constant.N_COL) / 2
        ));
    }

    protected Move decide(Board board) {
        if (this.step() <= 0 && board.getEnemy(this).step() <= 0) {
            return first();
        } else {
            alphaBeta(board, this.depth, Integer.MIN_VALUE, Integer.MAX_VALUE, this);
            return this.best;
        }
    }

    public int step() {
        return this.path.size();
    }

    public Pos getLastPos() {
        if (this.path.size() <= 0) {
            return null;
        }
        return this.path.get(this.path.size() - 1);
    }

    public Pos next(Board board) {
        long start = System.nanoTime();
        Move move = decide(board);
        while (!board.mark(move.getNext(), this)) {
            move = decide(board);
        }
        long end = System.nanoTime();
        this.times.add(end - start);
        this.path.add(move.getNext());
        return move.getNext();
    }

    private int alphaBeta(Board board, int depth, int alpha, int beta, ComputerPlayer player) {
        if (board.status().isGameOver() || depth <= 0) {
            return board.evaluate(this, this.depth - depth);
        }

        Pos bestPos = null;
        int v = (this == player) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        List<Pos> childPos = sortChildPos(board);
        for (Pos pos : childPos) {
            Board bd = new Board(board);
            bd.mark(pos, player);
            int w = alphaBeta(bd, depth - 1, alpha, beta, bd.getEnemy(player));
            if (this == player) {
                if (v < w) {
                    v = w;
                    bestPos = pos;
                    if (depth == this.depth) {
                        this.best = new Move(v, pos);
                    }
                }
                alpha = Integer.max(alpha, w);
            } else {
                if (v > w) {
                    v = w;
                    bestPos = pos;
                }
                beta = Integer.min(beta, w);
            }

            if (beta <= alpha) {
                this.history[pos.getRow()][pos.getCol()] += 2 << depth;
                break;
            }
        }
        if (bestPos != null) {
            this.history[bestPos.getRow()][bestPos.getCol()] += 2 << depth;
        }
        return v;
    }

    private List<Pos> sortChildPos(Board board) {
//        return board.getChildPos()
//                .stream()
//                .sorted(
//                        new Comparator<Pos>() {
//                            @Override
//                            public int compare(Pos o1, Pos o2) {
//                                return Integer.compare(
//                                        ComputerPlayer.this.history[o2.getRow()][o2.getCol()],
//                                        ComputerPlayer.this.history[o1.getRow()][o1.getCol()]);
//                            }
//                        })
//                .collect(Collectors.toList());
        return null;
    }

}
