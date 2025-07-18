package five.itcast.cn.player.base;

/**
 * Author by tony
 * Date on 2025/7/18.
 */
public class Point {
    public int x;

    public int y;

    public int getX() {
        return this.x;
    }

    public Point setX(int i) {
        this.x = i;
        return this;
    }

    public int getY() {
        return this.y;
    }

    public Point setY(int i) {
        this.y = i;
        return this;
    }

    public Point(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public int hashCode() {
        return this.x + this.y;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        Point point = (Point) obj;
        return this.x == point.x && this.y == point.y;
    }
}
