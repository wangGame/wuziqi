package five.itcast.cn.player.base;


import java.util.List;

/**
 * Author by tony
 * Date on 2025/7/18.
 */
public interface IPlayer {
    List<Point> getMyPoints();

    boolean hasWin();

    void run(List<Point> list, Point point);

    void setChessboard(IChessboard iChessboard);
}