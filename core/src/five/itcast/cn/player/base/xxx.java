package five.itcast.cn.player.base;

/**
 * Author by tony
 * Date on 2025/7/18.
 */

/* loaded from: classes2.dex */
public class Chessboard extends View implements IChessboard {
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
    private LinearLayout mainLLayout;
    private MediaPlayer mp;
    private final Paint paint;
    private IPlayer player1;
    private IPlayer player2;
    private Bitmap[] pointArray;
    private RefreshHandler refreshHandler;
    private SharedPreferences sp;
    private TextView textView;
    private int whoRun;

    public Chessboard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.currentMode = 1;
        Paint paint = new Paint();
        this.paint = paint;
        this.textView = null;
        this.pointArray = new Bitmap[4];
        this.player1 = new HumanPlayer();
        this.allFreePoints = new ArrayList();
        this.lines = new ArrayList();
        this.refreshHandler = new RefreshHandler();
        this.whoRun = 1;
        pointSize = Util.setIPx(context, 30);
        setFocusable(true);
        MediaPlayer create = MediaPlayer.create(context, R.raw.lose_music);
        this.mp = create;
        create.setLooping(false);
        SharedPreferences sharedPreferences = context.getSharedPreferences("wuziqi", 0);
        this.sp = sharedPreferences;
        this.isMusic = sharedPreferences.getBoolean("music", true);
        Resources resources = getContext().getResources();
        fillPointArrays(0, resources.getDrawable(R.drawable.green_point));
        fillPointArrays(1, resources.getDrawable(R.drawable.new_green_point));
        fillPointArrays(2, resources.getDrawable(R.drawable.red_point));
        fillPointArrays(3, resources.getDrawable(R.drawable.new_red_point));
        paint.setColor(Configuration.str_color[SharePref.getSpinnerIndex(getContext())]);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        maxX = (int) Math.floor(i / pointSize);
        int floor = (int) Math.floor(i2 / pointSize);
        maxY = floor;
        int i5 = pointSize;
        xOffset = (i - (maxX * i5)) / 2;
        yOffset = (i2 - (i5 * floor)) / 2;
        createLines();
        createPoints();
    }

    private void createLines() {
        for (int i = 0; i <= maxX; i++) {
            List<Line> list = this.lines;
            int i2 = xOffset;
            int i3 = pointSize;
            list.add(new Line(((i * i3) + i2) - (i3 / 2), yOffset, (i2 + (i * i3)) - (i3 / 2), r6 + (maxY * i3)));
        }
        for (int i4 = 0; i4 <= maxY; i4++) {
            List<Line> list2 = this.lines;
            int i5 = xOffset;
            int i6 = yOffset;
            int i7 = pointSize;
            list2.add(new Line(i5, ((i4 * i7) + i6) - (i7 / 2), i5 + (maxX * i7), (i6 + (i4 * i7)) - (i7 / 2)));
        }
    }

    private void drawChssboardLines(Canvas canvas) {
        for (Line line : this.lines) {
            canvas.drawLine(line.xStart, line.yStart, line.xStop, line.yStop, this.paint);
        }
    }

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

    private void drawPoint(Canvas canvas, Point point, int i) {
        canvas.drawBitmap(this.pointArray[i], (point.x * pointSize) + xOffset, (point.y * pointSize) + yOffset, this.paint);
    }

    public void setMode(int i) {
        this.currentMode = i;
        if (i == 3) {
            this.textView.setText(R.string.player_two_lost);
            this.mainLLayout.setVisibility(0);
            this.currentMode = 1;
            MainActivity.app.showChapingAD();
            return;
        }
        if (i == 2) {
            this.mainLLayout.setVisibility(8);
            return;
        }
        if (i == 1) {
            this.textView.setText(R.string.mode_ready);
            this.mainLLayout.setVisibility(0);
            MainActivity.app.showChapingAD();
        } else if (i == 4) {
            this.textView.setText(R.string.player_one_lost);
            this.mainLLayout.setVisibility(0);
            this.currentMode = 1;
            MainActivity.app.showChapingAD();
        }
    }

    public void setTextView(TextView textView, LinearLayout linearLayout) {
        this.textView = textView;
        this.mainLLayout = linearLayout;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        int i2 = this.currentMode;
        if (i2 == 1 && (i == 22 || i == 21)) {
            if (i == 22) {
                this.player2 = computer;
            } else if (i == 21) {
                this.player2 = human;
            }
            restart();
            setMode(2);
        } else {
            if (i2 != 2 || i != 20) {
                return false;
            }
            restart();
            setMode(1);
        }
        return true;
    }

    public void StartGame() {
        this.player2 = computer;
        restart();
        setMode(2);
    }

    public void StartInGame() {
        this.player2 = human;
        restart();
        setMode(2);
    }

    public void reStartGame() {
        this.player2 = computer;
        restart();
        setMode(1);
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
        createPoints();
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

    private void player2Run(MotionEvent motionEvent) {
        Point newPoint = newPoint(Float.valueOf(motionEvent.getX()), Float.valueOf(motionEvent.getY()));
        if (this.allFreePoints.contains(newPoint)) {
            setOnProcessing();
            this.player2.run(this.player1.getMyPoints(), newPoint);
            refressCanvas();
            if (!this.player2.hasWin()) {
                setPlayer1Run();
            } else {
                setMode(4);
            }
        }
    }

    class RefreshHandler extends Handler {
        RefreshHandler() {
        }

        public void computerRunAfter(long j) {
            removeMessages(0);
            sendMessageDelayed(obtainMessage(0), j);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Chessboard.this.player2.run(Chessboard.this.player1.getMyPoints(), null);
            Chessboard.this.refressCanvas();
            if (Chessboard.this.isMusic) {
                if (Chessboard.this.mp.isPlaying()) {
                    Chessboard.this.mp.seekTo(0);
                }
                Chessboard.this.mp.start();
            }
            if (!Chessboard.this.player2.hasWin()) {
                Chessboard.this.setPlayer1Run();
            } else {
                Chessboard.this.setMode(4);
            }
        }
    }

    private boolean onProcessing() {
        return this.whoRun == -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPlayer1Run() {
        this.whoRun = 1;
    }

    private void setOnProcessing() {
        this.whoRun = -1;
    }

    private boolean isPlayer1Run() {
        return this.whoRun == 1;
    }

    private boolean isPlayer2Run() {
        return this.whoRun == 2;
    }

    private void setPlayer2Run() {
        this.whoRun = 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refressCanvas() {
        invalidate();
    }

    private void drawPlayer1Point(Canvas canvas) {
        int size = this.player1.getMyPoints().size() - 1;
        if (size < 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            drawPoint(canvas, this.player1.getMyPoints().get(i), 0);
        }
        drawPoint(canvas, this.player1.getMyPoints().get(size), 1);
    }

    private void drawPlayer2Point(Canvas canvas) {
        int size;
        if (this.player2 != null && r0.getMyPoints().size() - 1 >= 0) {
            for (int i = 0; i < size; i++) {
                drawPoint(canvas, this.player2.getMyPoints().get(i), 2);
            }
            drawPoint(canvas, this.player2.getMyPoints().get(size), 3);
        }
    }

    public void fillPointArrays(int i, Drawable drawable) {
        int i2 = pointSize;
        Bitmap createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        int i3 = pointSize;
        drawable.setBounds(0, 0, i3, i3);
        drawable.draw(canvas);
        this.pointArray[i] = createBitmap;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        drawChssboardLines(canvas);
        drawPlayer1Point(canvas);
        drawPlayer2Point(canvas);
    }

    @Override // com.laodu.cn.qinghua.IChessboard
    public List<Point> getFreePoints() {
        return this.allFreePoints;
    }

    private void createPoints() {
        this.allFreePoints.clear();
        for (int i = 0; i < maxX; i++) {
            for (int i2 = 0; i2 < maxY; i2++) {
                this.allFreePoints.add(new Point(i, i2));
            }
        }
    }

    @Override // com.laodu.cn.qinghua.IChessboard
    public int getMaxX() {
        return maxX;
    }

    @Override // com.laodu.cn.qinghua.IChessboard
    public int getMaxY() {
        return maxY;
    }
}
