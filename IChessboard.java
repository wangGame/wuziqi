package five.itcast.cn.player.base;

import java.util.List;

/**
 * Author by tony
 * Date on 2025/7/18.
 */
public interface IChessboard {
    List<Point> getFreePoints();

    int getMaxX();

    int getMaxY();
}