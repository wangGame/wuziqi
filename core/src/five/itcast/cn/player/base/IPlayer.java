package five.itcast.cn.player.base;

/**
 * Author by tony
 * Date on 2025/7/18.
 */
public class IPlayer {
    List<Point> getMyPoints();

    boolean hasWin();

    void run(List<Point> list, Point point);

    void setChessboard(IChessboard iChessboard);
}