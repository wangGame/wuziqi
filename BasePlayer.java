package five.itcast.cn.player.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by tony
 * Date on 2025/7/18.
 */

/* loaded from: classes2.dex */
public abstract class BasePlayer implements IPlayer {
    protected List<Point> allFreePoints;
    protected IChessboard chessboard;
    protected int maxX;
    protected int maxY;
    protected List<Point> myPoints = new ArrayList(200);
    private final Point temp = new Point(0, 0);

    @Override // five.itcast.cn.player.interfaces.IPlayer
    public final List<Point> getMyPoints() {
        return this.myPoints;
    }

    @Override // five.itcast.cn.player.interfaces.IPlayer
    public void setChessboard(IChessboard iChessboard) {
        this.chessboard = iChessboard;
        this.allFreePoints = iChessboard.getFreePoints();
        this.maxX = iChessboard.getMaxX();
        this.maxY = iChessboard.getMaxY();
        this.myPoints.clear();
    }
}