package kw.maj.newai;

import java.util.ArrayList;

public class Point implements Comparable {
    private int x,y;
    private int role;
    private int scoreCom,scoreHum;
    private int score;
    private int step;
    private ArrayList<Point> steps;
    public Point(int x, int y, int role){
        if (x>16||y>16){
            System.out.println("0000000000000000000000000000000");
        }
        this.x=x;
        this.y=y;
        this.role=role;
    }
    public Point(int x, int y){
        this.x=x;
        this.y=y;
        if (x>15||y>15){
            System.out.println("0000000000000000000000000000000");
        }

    }

    @Override
    public int compareTo(Object o) {
        Point xx=(Point) o;
        if (score==xx.score) {
            // 大于零是优势，尽快获胜，因此取步数短的
            // 小于0是劣势，尽量拖延，因此取步数长的
            if (score >= 0) {
                if (step != xx.step)
                    return step - xx.step;
                else
                    return xx.score - score; // 否则 选取当前分最高的（直接评分)
            } else {
                if (step != xx.step)
                    return xx.step - step;
                else
                    return xx.score - score; // 否则 选取当前分最高的（直接评分)
            }
        }
        else
            return (xx.score - score);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getScoreCom() {
        return scoreCom;
    }

    public void setScoreCom(int scoreCom) {
        this.scoreCom = scoreCom;
    }

    public int getScoreHum() {
        return scoreHum;
    }

    public void setScoreHum(int scoreHum) {
        this.scoreHum = scoreHum;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public ArrayList<Point> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Point> steps) {
        this.steps = steps;
    }
}
