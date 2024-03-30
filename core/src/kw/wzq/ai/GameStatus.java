package kw.wzq.ai;

import java.util.Set;

public class GameStatus {
    private Status status;
    private ComputerPlayer winner;
    private Set<Pos> winningSet;
    public GameStatus(Status status,ComputerPlayer player,Set<Pos> winningSet){
        this.status = status;
        this.winner  = player;
        this.winningSet = winningSet;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ComputerPlayer getWinner() {
        return winner;
    }

    public void setWinner(ComputerPlayer winner) {
        this.winner = winner;
    }

    public Set<Pos> getWinningSet() {
        return winningSet;
    }

    public void setWinningSet(Set<Pos> winningSet) {
        this.winningSet = winningSet;
    }

    public boolean isGameOver() {
        return this.status != Status.ONGOING;  //不在允许状态
    }

    public boolean isDraw() {
        return this.status == Status.DRAW; // 绘制
    }

    public boolean isWinning() {
        return this.status == Status.P1_WIN || this.status == Status.P2_WIN;
    }
}
