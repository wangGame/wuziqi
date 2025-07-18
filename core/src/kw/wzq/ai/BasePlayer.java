package kw.wzq.ai;

/**
 * Author by tony
 * Date on 2025/7/18.
 */

import androidx.work.impl.Scheduler;
import com.laodu.cn.qinghua.IChessboard;
import com.laodu.cn.qinghua.Point;
import five.itcast.cn.player.interfaces.IPlayer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class BasePlayer implements IPlayer {
    protected List<Point> allFreePoints;
    protected IChessboard chessboard;
    protected int maxX;
    protected int maxY;
    protected List<Point> myPoints = new ArrayList(Scheduler.MAX_GREEDY_SCHEDULER_LIMIT);
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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004d, code lost:

        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x007b, code lost:

        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d4, code lost:

        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0114, code lost:

        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0156, code lost:

        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0194, code lost:

        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01d7, code lost:

        return true;
     */
    @Override // five.itcast.cn.player.interfaces.IPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean hasWin() {
        /*
            Method dump skipped, instructions count: 473
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: five.itcast.cn.player.base.BasePlayer.hasWin():boolean");
    }
}