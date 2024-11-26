package kw.wzq.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Board {
    //大小15x15
    public int N_ROW = 15;
    public int N_COL = 15;
    private char EMPTY_CHAR = '-';
    private int AVAILABLE_DISTANCE = 2;
    private Random RANDOM;
    private List<Pos> ALL_POS;
    private List<List<Pos>> BANDS;
    private long[][] RANDOM_TABLE;
    private Map<Player, Set<Set<Pos>>> GROUPS_CACHE;
    private int[][] SCORE_TABLE;
    private GameStatus status;
    private char[][] grid;

    private long hash;
    private Player player1;
    private Player player2;

    public Board(Player player1,Player player2){
        this.SCORE_TABLE = new int[][]{
                {1, 1, 1},
                {5, 10, 20},
                {10, 500, 1000},
                {25, 5000, 10000},
                {1000000, 1000000, 1000000}
        };
        this.player1 = player1;
        this.player2 = player2;
        this.status = new GameStatus(Status.ONGOING,null,Collections.emptySet());
        this.RANDOM_TABLE = buildRandomTable();
        this.hash = buildHash();
        this.ALL_POS = buildAllPos();
    }

    public Board(Board other){
        this.player1 = other.player1;
        this.player2 = other.player2;
        this.grid =  copyOf(other.grid);
        this.status = new GameStatus(other.status.getStatus(),other.status.getWinner(),this.status.getWinningSet());
        this.hash = other.hash;
    }

    private long buildHash() {
        long hash = RANDOM.nextLong();
        for (long[] longs : RANDOM_TABLE) {
            for (long aLong : longs) {
                hash ^= aLong;
            }
        }
        return hash;
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

    private long[][] buildRandomTable() {
        long[][] hashTable = new long[Constant.N_ROW * Constant.N_COL][3];
        /**
         * 三种状态  黑白无
         */
        for (int i = 0; i < Constant.N_ROW * Constant.N_COL; i++) {
            for (int i1 = 0; i1 < 3; i1++) {
                hashTable[i][i1] = RANDOM.nextLong();
            }
        }
        return hashTable;
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


    private List<Pos> buildAllPos() {
        List<Pos> list = new ArrayList<>();
        for (int i = 0; i < N_ROW; i++) {
            for (int i1 = 0; i1 < N_COL; i1++) {
                list.add(new Pos(i,i1));
            }
        }
        return list;
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
