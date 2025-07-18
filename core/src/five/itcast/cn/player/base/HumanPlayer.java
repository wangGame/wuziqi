package five.itcast.cn.player.base;

/**
 * Author by tony
 * Date on 2025/7/18.
 */
public class HumanPlayer extends BasePlayer implements IPlayer {
    @Override // five.itcast.cn.player.interfaces.IPlayer
    public void run(List<Point> list, Point point) {
        getMyPoints().add(point);
        this.allFreePoints.remove(point);
    }
}