package five.itcast.cn.player.base;

/**
 * Author by tony
 * Date on 2025/7/18.
 */

/* loaded from: classes2.dex */
public class SunWuKongAi extends BaseComputerAi {
    @Override // five.itcast.cn.player.base.BaseComputerAi
    protected Point getBestPoint() {
        Point bestPoint = getBestPoint(this.computerDouble3Alives, this.humanSencodResults);
        if (bestPoint != null) {
            return bestPoint;
        }
        Point bestPoint2 = getBestPoint(this.computer3Alives, this.humanSencodResults);
        if (bestPoint2 != null) {
            return bestPoint2;
        }
        Point bestPoint3 = getBestPoint(this.humanDouble3Alives, this.computerSencodResults);
        if (bestPoint3 != null) {
            return bestPoint3;
        }
        Point bestPoint4 = getBestPoint(this.human3Alives, this.computerSencodResults);
        if (bestPoint4 != null) {
            return bestPoint4;
        }
        Point bestPoint5 = getBestPoint(this.human4HalfAlives, this.computerSencodResults);
        if (bestPoint5 != null) {
            return bestPoint5;
        }
        Point bestPoint6 = getBestPoint(this.computerDouble2Alives, this.humanSencodResults);
        if (bestPoint6 != null) {
            return bestPoint6;
        }
        Point bestPoint7 = getBestPoint(this.computer2Alives, this.humanSencodResults);
        if (bestPoint7 != null) {
            return bestPoint7;
        }
        Point bestPoint8 = getBestPoint(this.computer3HalfAlives, this.humanSencodResults);
        if (bestPoint8 != null) {
            return bestPoint8;
        }
        Point bestPoint9 = getBestPoint(this.humanDouble2Alives, this.computerSencodResults);
        if (bestPoint9 != null) {
            return bestPoint9;
        }
        Point bestPoint10 = getBestPoint(this.human2Alives, this.computerSencodResults);
        return bestPoint10 != null ? bestPoint10 : getBestPoint(this.human3HalfAlives, this.computerSencodResults);
    }
}