

/**
 * Author by tony
 * Date on 2025/7/18.
 */
package five.itcast.cn.player.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class BaseComputerAi extends BasePlayer {
    private static final int ALIVE = 1;
    private static final boolean BACKWARD = false;
    private static final int FAN_XIE = 3;
    private static final boolean FORWARD = true;
    private static final int HALF_ALIVE = 0;
    private static final int HENG = 0;
    private static final int RANGE_STEP = 1;
    private static final int ZHENG_XIE = 2;
    private static final int ZHONG = 1;
    CalcuteRange currentRange;
    private final FirstAnalysisResult far;
    private final Map<Point, List<FirstAnalysisResult>> computerFirstResults = new HashMap();
    private final Map<Point, List<FirstAnalysisResult>> humanFirstResults = new HashMap();
    protected final List<SencondAnalysisResult> computerSencodResults = new ArrayList();
    protected final List<SencondAnalysisResult> humanSencodResults = new ArrayList();
    protected final List<SencondAnalysisResult> computer4HalfAlives = new ArrayList(2);
    protected final List<SencondAnalysisResult> computerDouble3Alives = new ArrayList(4);
    protected final List<SencondAnalysisResult> computer3Alives = new ArrayList(5);
    protected final List<SencondAnalysisResult> computerDouble2Alives = new ArrayList();
    protected final List<SencondAnalysisResult> computer2Alives = new ArrayList();
    protected final List<SencondAnalysisResult> computer3HalfAlives = new ArrayList();
    protected final List<SencondAnalysisResult> human4Alives = new ArrayList(2);
    protected final List<SencondAnalysisResult> human4HalfAlives = new ArrayList(5);
    protected final List<SencondAnalysisResult> humanDouble3Alives = new ArrayList(2);
    protected final List<SencondAnalysisResult> human3Alives = new ArrayList(10);
    protected final List<SencondAnalysisResult> humanDouble2Alives = new ArrayList(3);
    protected final List<SencondAnalysisResult> human2Alives = new ArrayList();
    protected final List<SencondAnalysisResult> human3HalfAlives = new ArrayList();

    public BaseComputerAi() {
        int i = 0;
        this.currentRange = new CalcuteRange(0, 0, 0, i);
        this.far = new FirstAnalysisResult(1, (Point) null, i,0);
    }

    private class CalcuteRange {
        int xStart;
        int xStop;
        int yStart;
        int yStop;

        private CalcuteRange(int i, int i2, int i3, int i4) {
            this.xStart = i;
            this.yStart = i2;
            this.xStop = i3;
            this.yStop = i4;
        }
    }

    private void initRange(List<Point> list, List<Point> list2) {
        this.currentRange.xStart = list2.get(0).getX() - 1;
        this.currentRange.yStart = list2.get(0).getY() - 1;
        this.currentRange.xStop = list2.get(0).getX() + 1;
        this.currentRange.yStop = list2.get(0).getY() + 1;
        for (Point point : list2) {
            if (point.getX() - 1 < this.currentRange.xStart) {
                this.currentRange.xStart = point.getX() - 1;
            } else if (point.getX() + 1 > this.currentRange.xStop) {
                this.currentRange.xStop = point.getX() + 1;
            }
            if (point.getY() - 1 < this.currentRange.yStart) {
                this.currentRange.yStart = point.getY() - 1;
            } else if (point.getY() + 1 > this.currentRange.yStop) {
                this.currentRange.yStop = point.getY() + 1;
            }
        }
        for (Point point2 : list) {
            if (point2.getX() - 1 < this.currentRange.xStart) {
                this.currentRange.xStart = point2.getX() - 1;
            } else if (point2.getX() + 1 > this.currentRange.xStop) {
                this.currentRange.xStop = point2.getX() + 1;
            }
            if (point2.getY() - 1 < this.currentRange.yStart) {
                this.currentRange.yStart = point2.getY() - 1;
            } else if (point2.getY() + 1 > this.currentRange.yStop) {
                this.currentRange.yStop = point2.getY() + 1;
            }
        }
        CalcuteRange calcuteRange = this.currentRange;
        calcuteRange.xStart = calcuteRange.xStart < 0 ? 0 : this.currentRange.xStart;
        CalcuteRange calcuteRange2 = this.currentRange;
        calcuteRange2.yStart = calcuteRange2.yStart >= 0 ? this.currentRange.yStart : 0;
        CalcuteRange calcuteRange3 = this.currentRange;
        calcuteRange3.xStop = calcuteRange3.xStop >= this.maxX ? this.maxX - 1 : this.currentRange.xStop;
        CalcuteRange calcuteRange4 = this.currentRange;
        calcuteRange4.yStop = calcuteRange4.yStop >= this.maxY ? this.maxY - 1 : this.currentRange.yStop;
    }

    private Point doAnalysis(List<Point> list, List<Point> list2) {
        if (list2.size() == 1) {
            return getFirstPoint(list2);
        }
        initRange(list, list2);
        initAnalysisResults();
        Point doFirstAnalysis = doFirstAnalysis(list, list2);
        if (doFirstAnalysis != null) {
            return doFirstAnalysis;
        }
        Point doComputerSencondAnalysis = doComputerSencondAnalysis(this.computerFirstResults, this.computerSencodResults);
        if (doComputerSencondAnalysis != null) {
            return doComputerSencondAnalysis;
        }
        this.computerFirstResults.clear();
        System.gc();
        Point doHumanSencondAnalysis = doHumanSencondAnalysis(this.humanFirstResults, this.humanSencodResults);
        if (doHumanSencondAnalysis != null) {
            return doHumanSencondAnalysis;
        }
        this.humanFirstResults.clear();
        System.gc();
        return doThirdAnalysis();
    }

    private Point getFirstPoint(List<Point> list) {
        Point point = list.get(0);
        if (point.getX() == 0 || point.getY() == 0 || (point.getX() == this.maxX && point.getY() == this.maxY)) {
            return new Point(this.maxX / 2, this.maxY / 2);
        }
        return new Point(point.getX() - 1, point.getY());
    }

    private Point doFirstAnalysis(List<Point> list, List<Point> list2) {
        int size = this.allFreePoints.size();
        Point point = null;
        for (int i = 0; i < size; i++) {
            Point point2 = this.allFreePoints.get(i);
            int x = point2.getX();
            int y = point2.getY();
            if (x >= this.currentRange.xStart && x <= this.currentRange.xStop && y >= this.currentRange.yStart && y <= this.currentRange.yStop) {
                FirstAnalysisResult tryAndCountResult = tryAndCountResult(list, list2, point2, 0);
                point2.setX(x).setY(y);
                if (tryAndCountResult != null) {
                    if (tryAndCountResult.count == 5) {
                        return point2;
                    }
                    addToFirstAnalysisResult(tryAndCountResult, this.computerFirstResults);
                }
                FirstAnalysisResult tryAndCountResult2 = tryAndCountResult(list, list2, point2, 1);
                point2.setX(x).setY(y);
                if (tryAndCountResult2 != null) {
                    if (tryAndCountResult2.count == 5) {
                        return point2;
                    }
                    addToFirstAnalysisResult(tryAndCountResult2, this.computerFirstResults);
                }
                FirstAnalysisResult tryAndCountResult3 = tryAndCountResult(list, list2, point2, 2);
                point2.setX(x).setY(y);
                if (tryAndCountResult3 != null) {
                    if (tryAndCountResult3.count == 5) {
                        return point2;
                    }
                    addToFirstAnalysisResult(tryAndCountResult3, this.computerFirstResults);
                }
                FirstAnalysisResult tryAndCountResult4 = tryAndCountResult(list, list2, point2, 3);
                point2.setX(x).setY(y);
                if (tryAndCountResult4 != null) {
                    if (tryAndCountResult4.count == 5) {
                        return point2;
                    }
                    addToFirstAnalysisResult(tryAndCountResult4, this.computerFirstResults);
                }
                FirstAnalysisResult tryAndCountResult5 = tryAndCountResult(list2, list, point2, 0);
                point2.setX(x).setY(y);
                if (tryAndCountResult5 != null) {
                    if (tryAndCountResult5.count == 5) {
                        point = point2;
                    }
                    addToFirstAnalysisResult(tryAndCountResult5, this.humanFirstResults);
                }
                FirstAnalysisResult tryAndCountResult6 = tryAndCountResult(list2, list, point2, 1);
                point2.setX(x).setY(y);
                if (tryAndCountResult6 != null) {
                    if (tryAndCountResult6.count == 5) {
                        point = point2;
                    }
                    addToFirstAnalysisResult(tryAndCountResult6, this.humanFirstResults);
                }
                FirstAnalysisResult tryAndCountResult7 = tryAndCountResult(list2, list, point2, 2);
                point2.setX(x).setY(y);
                if (tryAndCountResult7 != null) {
                    if (tryAndCountResult7.count == 5) {
                        point = point2;
                    }
                    addToFirstAnalysisResult(tryAndCountResult7, this.humanFirstResults);
                }
                FirstAnalysisResult tryAndCountResult8 = tryAndCountResult(list2, list, point2, 3);
                point2.setX(x).setY(y);
                if (tryAndCountResult8 != null) {
                    if (tryAndCountResult8.count != 5) {
                        point2 = point;
                    }
                    addToFirstAnalysisResult(tryAndCountResult8, this.humanFirstResults);
                    point = point2;
                }
            }
        }
        return point;
    }

    private Point doComputerSencondAnalysis(Map<Point, List<FirstAnalysisResult>> map, List<SencondAnalysisResult> list) {
        Iterator<Point> it = map.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                return null;
            }
            Point next = it.next();
            SencondAnalysisResult sencondAnalysisResult = new SencondAnalysisResult(next);
            for (FirstAnalysisResult firstAnalysisResult : map.get(next)) {
                if (firstAnalysisResult.count == 4) {
                    if (firstAnalysisResult.aliveState == 1) {
                        return firstAnalysisResult.point;
                    }
                    sencondAnalysisResult.halfAlive4++;
                    this.computer4HalfAlives.add(sencondAnalysisResult);
                } else if (firstAnalysisResult.count == 3) {
                    if (firstAnalysisResult.aliveState == 1) {
                        sencondAnalysisResult.alive3++;
                        if (sencondAnalysisResult.alive3 == 1) {
                            this.computer3Alives.add(sencondAnalysisResult);
                        } else {
                            this.computerDouble3Alives.add(sencondAnalysisResult);
                        }
                    } else {
                        sencondAnalysisResult.halfAlive3++;
                        this.computer3HalfAlives.add(sencondAnalysisResult);
                    }
                } else {
                    sencondAnalysisResult.alive2++;
                    if (sencondAnalysisResult.alive2 == 1) {
                        this.computer2Alives.add(sencondAnalysisResult);
                    } else {
                        this.computerDouble2Alives.add(sencondAnalysisResult);
                    }
                }
            }
            list.add(sencondAnalysisResult);
        }
    }

    private Point doHumanSencondAnalysis(Map<Point, List<FirstAnalysisResult>> map, List<SencondAnalysisResult> list) {
        Iterator<Point> it = map.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                return null;
            }
            Point next = it.next();
            SencondAnalysisResult sencondAnalysisResult = new SencondAnalysisResult(next);
            for (FirstAnalysisResult firstAnalysisResult : map.get(next)) {
                if (firstAnalysisResult.count == 4) {
                    if (firstAnalysisResult.aliveState == 1) {
                        this.human4Alives.add(sencondAnalysisResult);
                    } else {
                        sencondAnalysisResult.halfAlive4++;
                        this.human4HalfAlives.add(sencondAnalysisResult);
                    }
                } else if (firstAnalysisResult.count == 3) {
                    if (firstAnalysisResult.aliveState == 1) {
                        sencondAnalysisResult.alive3++;
                        if (sencondAnalysisResult.alive3 == 1) {
                            this.human3Alives.add(sencondAnalysisResult);
                        } else {
                            this.humanDouble3Alives.add(sencondAnalysisResult);
                        }
                    } else {
                        sencondAnalysisResult.halfAlive3++;
                        this.human3HalfAlives.add(sencondAnalysisResult);
                    }
                } else {
                    sencondAnalysisResult.alive2++;
                    if (sencondAnalysisResult.alive2 == 1) {
                        this.human2Alives.add(sencondAnalysisResult);
                    } else {
                        this.humanDouble2Alives.add(sencondAnalysisResult);
                    }
                }
            }
            list.add(sencondAnalysisResult);
        }
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException unused) {
        }
    }

    private Point doThirdAnalysis() {
        if (!this.computer4HalfAlives.isEmpty()) {
            return this.computer4HalfAlives.get(0).point;
        }
        System.gc();
        sleep(300);
        Collections.sort(this.computerSencodResults);
        System.gc();
        Point bestPoint = getBestPoint(this.human4Alives, this.computerSencodResults);
        if (bestPoint != null) {
            return bestPoint;
        }
        Collections.sort(this.humanSencodResults);
        System.gc();
        Point bestPoint2 = getBestPoint();
        return bestPoint2 != null ? bestPoint2 : this.computerSencodResults.get(0).point;
    }

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
        Point bestPoint5 = getBestPoint(this.computerDouble2Alives, this.humanSencodResults);
        if (bestPoint5 != null) {
            return bestPoint5;
        }
        Point bestPoint6 = getBestPoint(this.computer2Alives, this.humanSencodResults);
        if (bestPoint6 != null) {
            return bestPoint6;
        }
        Point bestPoint7 = getBestPoint(this.computer3HalfAlives, this.humanSencodResults);
        if (bestPoint7 != null) {
            return bestPoint7;
        }
        Point bestPoint8 = getBestPoint(this.human4HalfAlives, this.computerSencodResults);
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

    protected Point getBestPoint(List<SencondAnalysisResult> list, List<SencondAnalysisResult> list2) {
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() > 1) {
            for (SencondAnalysisResult sencondAnalysisResult : list2) {
                if (list.contains(sencondAnalysisResult)) {
                    return sencondAnalysisResult.point;
                }
            }
            return list.get(0).point;
        }
        return list.get(0).point;
    }

    private void initAnalysisResults() {
        this.computerFirstResults.clear();
        this.humanFirstResults.clear();
        this.computerSencodResults.clear();
        this.humanSencodResults.clear();
        this.computer4HalfAlives.clear();
        this.computerDouble3Alives.clear();
        this.computer3Alives.clear();
        this.computerDouble2Alives.clear();
        this.computer2Alives.clear();
        this.computer3HalfAlives.clear();
        this.human4Alives.clear();
        this.human4HalfAlives.clear();
        this.humanDouble3Alives.clear();
        this.human3Alives.clear();
        this.humanDouble2Alives.clear();
        this.human2Alives.clear();
        this.human3HalfAlives.clear();
        System.gc();
    }

    private void addToFirstAnalysisResult(FirstAnalysisResult firstAnalysisResult, Map<Point, List<FirstAnalysisResult>> map) {
        if (map.containsKey(firstAnalysisResult.point)) {
            map.get(firstAnalysisResult.point).add(firstAnalysisResult);
            return;
        }
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(firstAnalysisResult);
        map.put(firstAnalysisResult.point, arrayList);
    }

    private class FirstAnalysisResult {
        int aliveState;
        int count;
        int direction;
        Point point;

        private FirstAnalysisResult(BaseComputerAi baseComputerAi, int i, Point point, int i2) {
            this(i, point, i2, 1);
        }

        private FirstAnalysisResult(int i, Point point, int i2, int i3) {
            this.count = i;
            this.point = point;
            this.direction = i2;
            this.aliveState = i3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FirstAnalysisResult init(Point point, int i, int i2) {
            this.count = 1;
            this.point = point;
            this.direction = i;
            this.aliveState = i2;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FirstAnalysisResult cloneMe() {
            return BaseComputerAi.this.new FirstAnalysisResult(this.count, this.point, this.direction, this.aliveState);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    protected class SencondAnalysisResult implements Comparable<SencondAnalysisResult> {
        int alive2;
        int alive3;
        int alive4;
        int halfAlive3;
        int halfAlive4;
        Point point;

        public int hashCode() {
            Point point = this.point;
            return 31 + (point == null ? 0 : point.hashCode());
        }

        public boolean equals(Object obj) {
            SencondAnalysisResult sencondAnalysisResult = (SencondAnalysisResult) obj;
            Point point = this.point;
            if (point == null) {
                if (sencondAnalysisResult.point != null) {
                    return false;
                }
                return BaseComputerAi.FORWARD;
            }
            if (point.equals(sencondAnalysisResult.point)) {
                return BaseComputerAi.FORWARD;
            }
            return false;
        }

        private SencondAnalysisResult(Point point) {
            this.alive4 = 0;
            this.alive3 = 0;
            this.halfAlive4 = 0;
            this.halfAlive3 = 0;
            this.alive2 = 0;
            this.point = point;
        }

        @Override // java.lang.Comparable
        public int compareTo(SencondAnalysisResult sencondAnalysisResult) {
            return BaseComputerAi.this.compareTowResult(this, sencondAnalysisResult);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int compareTowResult(SencondAnalysisResult sencondAnalysisResult, SencondAnalysisResult sencondAnalysisResult2) {
        if (sencondAnalysisResult.alive4 > sencondAnalysisResult2.alive4) {
            return -1;
        }
        if (sencondAnalysisResult.alive4 < sencondAnalysisResult2.alive4) {
            return 1;
        }
        if (sencondAnalysisResult.halfAlive4 > sencondAnalysisResult2.halfAlive4) {
            return -1;
        }
        if (sencondAnalysisResult.halfAlive4 < sencondAnalysisResult2.halfAlive4) {
            return 1;
        }
        if (sencondAnalysisResult.alive3 > sencondAnalysisResult2.alive3) {
            return -1;
        }
        if (sencondAnalysisResult.alive3 < sencondAnalysisResult2.alive3) {
            return 1;
        }
        if (sencondAnalysisResult.alive2 > sencondAnalysisResult2.alive2) {
            return -1;
        }
        if (sencondAnalysisResult.alive2 < sencondAnalysisResult2.alive2) {
            return 1;
        }
        if (sencondAnalysisResult.halfAlive3 > sencondAnalysisResult2.halfAlive3) {
            return -1;
        }
        return sencondAnalysisResult.halfAlive3 > sencondAnalysisResult2.halfAlive3 ? 1 : 0;
    }

    private FirstAnalysisResult tryAndCountResult(List<Point> list, List<Point> list2, Point point, int i) {
        int x = point.getX();
        int y = point.getY();
        int maxCountOnThisDirection = maxCountOnThisDirection(point, list2, i, 1);
        if (maxCountOnThisDirection < 5) {
            return null;
        }
        FirstAnalysisResult init = maxCountOnThisDirection == 5 ? this.far.init(point, i, 0) : this.far.init(point, i, 1);
        countPoint(list, list2, point.setX(x).setY(y), init, i, FORWARD);
        countPoint(list, list2, point.setX(x).setY(y), init, i, false);
        if (init.count <= 1 || (init.count == 2 && init.aliveState == 0)) {
            return null;
        }
        return init.cloneMe();
    }

    private boolean isOutSideOfWall(Point point, int i) {
        if (i == 0) {
            if (point.getX() < 0 || point.getX() >= this.maxX) {
                return FORWARD;
            }
            return false;
        }
        if (i == 1) {
            if (point.getY() < 0 || point.getY() >= this.maxY) {
                return FORWARD;
            }
            return false;
        }
        if (point.getX() < 0 || point.getY() < 0 || point.getX() >= this.maxX || point.getY() >= this.maxY) {
            return FORWARD;
        }
        return false;
    }

    private Point pointToNext(Point point, int i, boolean z) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        if (z) {
                            point.x++;
                            point.y++;
                        } else {
                            point.x--;
                            point.y--;
                        }
                    }
                } else if (z) {
                    point.x++;
                    point.y--;
                } else {
                    point.x--;
                    point.y++;
                }
            } else if (z) {
                point.y++;
            } else {
                point.y--;
            }
        } else if (z) {
            point.x++;
        } else {
            point.x--;
        }
        return point;
    }

    private void countPoint(List<Point> list, List<Point> list2, Point point, FirstAnalysisResult firstAnalysisResult, int i, boolean z) {
        if (list.contains(pointToNext(point, i, z))) {
            firstAnalysisResult.count++;
            if (list.contains(pointToNext(point, i, z))) {
                firstAnalysisResult.count++;
                if (list.contains(pointToNext(point, i, z))) {
                    firstAnalysisResult.count++;
                    if (list.contains(pointToNext(point, i, z))) {
                        firstAnalysisResult.count++;
                        return;
                    } else {
                        if (list2.contains(point) || isOutSideOfWall(point, i)) {
                            firstAnalysisResult.aliveState = 0;
                            return;
                        }
                        return;
                    }
                }
                if (list2.contains(point) || isOutSideOfWall(point, i)) {
                    firstAnalysisResult.aliveState = 0;
                    return;
                }
                return;
            }
            if (list2.contains(point) || isOutSideOfWall(point, i)) {
                firstAnalysisResult.aliveState = 0;
                return;
            }
            return;
        }
        if (list2.contains(point) || isOutSideOfWall(point, i)) {
            firstAnalysisResult.aliveState = 0;
        }
    }

    private int maxCountOnThisDirection(Point point, List<Point> list, int i, int i2) {
        int x = point.getX();
        int y = point.getY();
        if (i == 0) {
            while (!list.contains(point.setX(point.getX() - 1)) && point.getX() >= 0 && i2 < 6) {
                i2++;
            }
            point.setX(x);
            while (!list.contains(point.setX(point.getX() + 1)) && point.getX() < this.maxX && i2 < 6) {
                i2++;
            }
        } else if (i == 1) {
            while (!list.contains(point.setY(point.getY() - 1)) && point.getY() >= 0) {
                i2++;
            }
            point.setY(y);
            while (!list.contains(point.setY(point.getY() + 1)) && point.getY() < this.maxY && i2 < 6) {
                i2++;
            }
        } else if (i == 2) {
            while (!list.contains(point.setX(point.getX() - 1).setY(point.getY() + 1)) && point.getX() >= 0 && point.getY() < this.maxY) {
                i2++;
            }
            point.setX(x).setY(y);
            while (!list.contains(point.setX(point.getX() + 1).setY(point.getY() - 1)) && point.getX() < this.maxX && point.getY() >= 0 && i2 < 6) {
                i2++;
            }
        } else if (i == 3) {
            while (!list.contains(point.setX(point.getX() - 1).setY(point.getY() - 1)) && point.getX() >= 0 && point.getY() >= 0) {
                i2++;
            }
            point.setX(x).setY(y);
            while (!list.contains(point.setX(point.getX() + 1).setY(point.getY() + 1)) && point.getX() < this.maxX && point.getY() < this.maxY && i2 < 6) {
                i2++;
            }
        }
        return i2;
    }

    @Override
    public boolean hasWin() {
        return false;
    }

    @Override // five.itcast.cn.player.interfaces.IPlayer
    public void run(List<Point> list, Point point) {
        this.allFreePoints.remove(list.get(list.size() - 1));
        Point doAnalysis = doAnalysis(this.myPoints, list);
        this.allFreePoints.remove(doAnalysis);
        this.myPoints.add(doAnalysis);
    }
}