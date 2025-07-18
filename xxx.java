package five.itcast.cn.player.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by tony
 * Date on 2025/7/18.
 */
public class Chessboard implements IChessboard {
    private static final int GREEN = 0;
    private static final int NEW_GREEN = 1;
    private static final int NEW_RED = 3;
    private static final int PLAYER_ONE_LOST = 4;
    private static final int PLAYER_TWO_LOST = 3;
    private static final int READY = 1;
    private static final int RED = 2;
    private static final int RUNNING = 2;
    private static final IPlayer computer = AiFactory.getInstance(2);
    private static final IPlayer human = new HumanPlayer();
    private static int maxX;
    private static int maxY;
    private static int pointSize;
    private static int xOffset;
    private static int yOffset;
    private final List<Point> allFreePoints;
    private int currentMode;
    private boolean isMusic;
    private List<Line> lines;


    private IPlayer player1;
    private IPlayer player2;

    private RefreshHandler refreshHandler;


    private int whoRun;

    public Chessboard() {
        this.currentMode = 1;
        this.player1 = new HumanPlayer();
        this.allFreePoints = new ArrayList();
        this.lines = new ArrayList();
        this.refreshHandler = new RefreshHandler();
        this.whoRun = 1;
        pointSize = 30;
    }
//
//    @Override // android.view.View
//    protected void onSizeChanged(int i, int i2, int i3, int i4) {
//        maxX = (int) Math.floor(i / pointSize);
//        int floor = (int) Math.floor(i2 / pointSize);
//        maxY = floor;
//        int i5 = pointSize;
//        xOffset = (i - (maxX * i5)) / 2;
//        yOffset = (i2 - (i5 * floor)) / 2;
//        createLines();
//        createPoints();
//    }

//    private void createLines() {
//        for (int i = 0; i <= maxX; i++) {
//            List<Line> list = this.lines;
//            int i2 = xOffset;
//            int i3 = pointSize;
//            list.add(new Line(((i * i3) + i2) - (i3 / 2), yOffset, (i2 + (i * i3)) - (i3 / 2), r6 + (maxY * i3)));
//        }
//        for (int i4 = 0; i4 <= maxY; i4++) {
//            List<Line> list2 = this.lines;
//            int i5 = xOffset;
//            int i6 = yOffset;
//            int i7 = pointSize;
//            list2.add(new Line(i5, ((i4 * i7) + i6) - (i7 / 2), i5 + (maxX * i7), (i6 + (i4 * i7)) - (i7 / 2)));
//        }
//    }
//
//    private void drawChssboardLines(Canvas canvas) {
//        for (Line line : this.lines) {
//            canvas.drawLine(line.xStart, line.yStart, line.xStop, line.yStop, this.paint);
//        }
//    }

    class Line {
        float xStart;
        float xStop;
        float yStart;
        float yStop;

        public Line(float f, float f2, float f3, float f4) {
            this.xStart = f;
            this.yStart = f2;
            this.xStop = f3;
            this.yStop = f4;
        }
    }


    public void StartGame() {
        this.player2 = computer;
        restart();
    }

    public void StartInGame() {
        this.player2 = human;
        restart();
    }

    public void reStartGame() {
        this.player2 = computer;
        restart();
    }

    private Point newPoint(Float f, Float f2) {
        Point point = new Point(0, 0);
        for (int i = 0; i < maxX; i++) {
            if ((pointSize * i) + xOffset <= f.floatValue() && f.floatValue() < ((i + 1) * pointSize) + xOffset) {
                point.setX(i);
            }
        }
        for (int i2 = 0; i2 < maxY; i2++) {
            if ((pointSize * i2) + yOffset <= f2.floatValue() && f2.floatValue() < ((i2 + 1) * pointSize) + yOffset) {
                point.setY(i2);
            }
        }
        return point;
    }

    private void restart() {

        this.player1.setChessboard(this);
        this.player2.setChessboard(this);
        setPlayer1Run();
        refressCanvas();
    }

    private boolean hasStart() {
        return this.currentMode == 2;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!hasStart() || motionEvent.getAction() != 1 || onProcessing()) {
            return true;
        }
        playerRun(motionEvent);
        return true;
    }

    private synchronized void playerRun(MotionEvent motionEvent) {
        Log.e("hel", "playerRun: ");
        if (this.isMusic) {
            if (this.mp.isPlaying()) {
                this.mp.seekTo(0);
            }
            this.mp.start();
        }
        if (isPlayer1Run()) {
            player1Run(motionEvent);
        } else if (isPlayer2Run()) {
            player2Run(motionEvent);
        }
    }

    private void player1Run(MotionEvent motionEvent) {
        Point newPoint = newPoint(Float.valueOf(motionEvent.getX()), Float.valueOf(motionEvent.getY()));
        if (this.allFreePoints.contains(newPoint)) {
            setOnProcessing();
            this.player1.run(this.player2.getMyPoints(), newPoint);
            refressCanvas();
            if (!this.player1.hasWin()) {
                if (this.player2 == computer) {
                    this.refreshHandler.computerRunAfter(10L);
                    return;
                } else {
                    setPlayer2Run();
                    return;
                }
            }
            setMode(3);
        }
    }
//
//    private void player2Run(MotionEvent motionEvent) {
//        Point newPoint = newPoint(Float.valueOf(motionEvent.getX()), Float.valueOf(motionEvent.getY()));
//        if (this.allFreePoints.contains(newPoint)) {
//            setOnProcessing();
//            this.player2.run(this.player1.getMyPoints(), newPoint);
//            refressCanvas();
//            if (!this.player2.hasWin()) {
//                setPlayer1Run();
//            } else {
//                setMode(4);
//            }
//        }
//    }
//
//    class RefreshHandler extends Handler {
//        RefreshHandler() {
//        }

//        public void computerRunAfter(long j) {
//            removeMessages(0);
//            sendMessageDelayed(obtainMessage(0), j);
//        }

//        @Override // android.os.Handler
//        public void handleMessage(Message message) {
//            Chessboard.this.player2.run(Chessboard.this.player1.getMyPoints(), null);
//
//    }


    /* JADX INFO: Access modifiers changed from: private */


//    private void drawPlayer1Point(Canvas canvas) {
//        int size = this.player1.getMyPoints().size() - 1;
//        if (size < 0) {
//            return;
//        }
//        for (int i = 0; i < size; i++) {
//            drawPoint(canvas, this.player1.getMyPoints().get(i), 0);
//        }
//        drawPoint(canvas, this.player1.getMyPoints().get(size), 1);
//    }

//    private void drawPlayer2Point() {
//        int size;
//        if (this.player2 != null && r0.getMyPoints().size() - 1 >= 0) {
//            for (int i = 0; i < size; i++) {
//                drawPoint(canvas, this.player2.getMyPoints().get(i), 2);
//            }
//            drawPoint(canvas, this.player2.getMyPoints().get(size), 3);
//        }
//    }

    protected void onDraw() {
//        drawPlayer1Point();
//        drawPlayer2Point();
    }

    @Override // com.laodu.cn.qinghua.IChessboard
    public List<Point> getFreePoints() {
        return this.allFreePoints;
    }

    private ArrayList<Point> allFreePoints = new ArrayList<>();
    private void createPoints() {
        this.allFreePoints.clear();
        for (int i = 0; i < maxX; i++) {
            for (int i2 = 0; i2 < maxY; i2++) {
                this.allFreePoints.add(new Point(i, i2));
            }
        }
    }

}
