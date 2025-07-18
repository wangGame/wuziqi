package kw.wzq.player;


import java.util.ArrayList;
import java.util.List;

import kw.wzq.ai.Point;

public abstract class IPlayer {
    protected List<Point> handPoint = new ArrayList<>();

    public List<Point> getHandPoint() {
        return handPoint;
    }

    public void addPoint(int xx, int yy) {
        handPoint.add(new Point(xx,yy));
    }
}
