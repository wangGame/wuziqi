package kw.wzq.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import kw.wzq.newai.Board;

public class NewAi {
    private static final char EMPTY_CHAR = '-';
    private char[][] grid;
    private long[][] hashTable;
    private long hashValue;
    private Random RANDOM;
    private GameStatus status;

    public NewAi(){
        initRandom();
        init();
        initHash();
    }

    public NewAi(NewAi ai){
        this.grid = copyOf(ai.grid);
        this.status = new GameStatus(
                ai.status.getStatus(),
                ai.status.getWinner(),
                ai.status.getWinningSet());
        this.hashValue = ai.hashValue;
    }

    private void initRandom() {
        RANDOM = new Random();
    }

    private static char[][] copyOf(char[][] src) {
        int length = src.length;
        char[][] target = new char[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    private void initHash() {
        hashTable = new long[Constant.N_ROW * Constant.N_COL][3];
        /**
         * 三种状态  黑白无
         */
        for (int i = 0; i < Constant.N_ROW * Constant.N_COL; i++) {
            for (int i1 = 0; i1 < 3; i1++) {
                hashTable[i][i1] = RANDOM.nextLong();
            }
        }

        hashValue = RANDOM.nextLong();

        for (int i = 0; i < Constant.N_COL * Constant.N_ROW; i++) {
            //啥也没有
            hashValue ^= hashTable[i][2];
        }
    }

    public void init(){
        this.status = new GameStatus(Status.ONGOING, null, Collections.emptySet());
        //开始都为'-'
        grid = new char[Constant.N_ROW][Constant.N_COL];
        for (int i = 0; i < Constant.N_ROW; i++) {
            for (int i1 = 0; i1 < Constant.N_COL; i1++) {
                grid[i][i1] = EMPTY_CHAR;
            }
        }
    }

    public List<List<Pos>> buildBands(){
        Map<Integer, List<Pos>> basket = new HashMap<>();
        int offset = 2 * (Constant.N_ROW + Constant.N_COL);
        for (int i = 0; i < Constant.N_ROW; i++) {
            for (int j = 0; j < Constant.N_COL; j++) {
                // row
                load(basket, i, new Pos(i, j));
                // col
                load(basket, j + offset, new Pos(i, j));
                // diagonal
                load(basket, i + j + 2 * offset, new Pos(i, j));
                load(basket, i - j + 3 * offset, new Pos(i, j));
            }
        }
        return null;
    }

    public void load(Map<Integer, List<Pos>> basket, int key, Pos pos){
        List<Pos> band = basket.get(key);
        if (band == null) {
            basket.put(key, new ArrayList<>(Collections.singletonList(pos)));
        } else {
            band.add(pos);
        }
    }

    public ComputerPlayer getEnemy(ComputerPlayer player) {
        return null;
    }

    public boolean mark(Pos next, ComputerPlayer player) {
        return false;
    }

    public GameStatus status() {
        return null;
    }

    public int evaluate(ComputerPlayer player, int i) {
        return 0;
    }
}
