package com.example.jumper.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements Runnable, MediaPlayer.OnCompletionListener {
    //----------------
    // スレッド処理用
    //----------------
    Handler handler=null;
    long startTime = 0;

    //----------------
    // センサー
    //----------------
    // センサー制御用コントローラ
    protected AccelerationHelper accelerationController;
    protected GpsHelper gpsController;
    protected TouchHelper touchController;

    // センサー設定
    protected boolean gravityEnabled = false;
    protected boolean gpsEnabled = false;
    protected int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

    //----------------
    // BGM
    //----------------
    // 音楽設定
    boolean mediaPlayerIsInitialized = false;
    protected MediaPlayer mediaPlayer;
    protected SoundPool soundPool;

    //-------------
    // View用パラメータ
    //-------------
    // 減点の座標
    protected int canvasBaseX;
    protected int canvasBaseY;

    // デバイスの表示領域
    int canvasHeight;
    int canvasWidth;

    //=========================
    //  ライフサイクル用関数
    //=========================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 画面の向きを設定
        setRequestedOrientation(orientation);

        // ステータスバーを非表示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // タイトルを非表示
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 初期設定
        initializeWindow();
        initializeAccelerationController();
        initializeGpsController();
        initializeOrientation();
        initializeTouchController();
        initializeMusic();
     }

    //------------
    //  起動時の処理
    //------------
    @Override
    protected void onResume() {
        super.onResume();
        if (gravityEnabled && accelerationController != null) {
            accelerationController.start();
        }
        if (gpsEnabled && gpsController != null) {
            gpsController.start();
        }
        startThread();
        continueThread();
    }

    //------------
    //  一時停止時の処理
    //------------
    @Override
    protected void onPause() {
        if(mediaPlayer != null) {
            mediaPlayer.release();
        }
        if (gravityEnabled) {
            if(accelerationController!=null) {
                accelerationController.stop();
            }
        }
        if (gpsEnabled) {
            if(gpsController!=null){
                gpsController.stop();
            }
        }
        stopThread();
        super.onPause();
    }

    //------------
    //  終了時の処理
    //------------
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //------------
    //  更新時の処理
    //------------
    // コントローラの更新処理
    protected void update() {
    }

    //=========================
    //  スレッド処理用の関数
    //=========================
    @Override
    public void run() {
        float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
        update();
        continueThread();
    }

    private void startThread() {
        handler = new Handler();
        startTime = System.nanoTime();
    }

    private void continueThread() {
        handler.postDelayed(this, 10);
    }

    private void stopThread() {
        handler.removeCallbacks(this);
    }

    //=========================
    //  初期化用の関数
    //=========================
    // Windowsの初期化
    private void initializeWindow() {
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display dp = wm.getDefaultDisplay();
        Point point = new Point();
        dp.getSize(point);
        canvasWidth = point.x;
        canvasHeight = point.y;
    }

    // 加速度センサーの初期化
    protected void initializeAccelerationController() {
        if (gravityEnabled) {
            accelerationController = new AccelerationHelper(
                    (SensorManager) getSystemService(Context.SENSOR_SERVICE));
            accelerationController.start();
        } else {
            accelerationController = null;
        }
    }

    // GPSコントローラーの初期化
    protected void initializeGpsController() {
        if (gpsEnabled) {
            gpsController = new GpsHelper((LocationManager) getSystemService(Context.LOCATION_SERVICE),this);
        }
    }

    // タッチコントローラーの初期化
    protected void initializeTouchController() {
        touchController = new TouchHelper();
    }

    // 向きの初期化
    protected void initializeOrientation() {
        setRequestedOrientation(orientation);
    }

    protected void initializeMusic(){
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = buildSoundPool(20);
    }

    //=========================
    //  サウンド用の関数
    //=========================
    @Override
    public void onCompletion(MediaPlayer mp) {
        synchronized (this) {
            mediaPlayerIsInitialized = false;
        }
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SoundPool buildSoundPool(int poolMax) {
        SoundPool pool = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            pool = new SoundPool(poolMax, AudioManager.STREAM_MUSIC, 0);
        } else {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            pool = new SoundPool.Builder()
                    .setAudioAttributes(attr)
                    .setMaxStreams(poolMax)
                    .build();
        }
        return pool;
    }

    public void setMusicVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }


    //--------------
    // 効果音
    //--------------
    public void loadMusic(int id) {
        mediaPlayer = MediaPlayer.create(this, id);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setLooping(true);
    }

    public void startMusic() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    //--------------
    // BGM
    //--------------
    public int loadSound(int id) {
        return soundPool.load(this, id, 1);
    }

    public void playSound(int soundId, float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void stopMusic() {
        mediaPlayer.stop();
    }

    //=========================
    //  画像用の関数
    //=========================
    // 画像の読み込み
    public Bitmap loadImage(int id) {
        return BitmapFactory.decodeResource(getResources(), id);
    }

    // 画像の描画
    public void drawImage(int x, int y, int xSize, int ySize, Bitmap bitmap, ImageView imageView) {
        int canvasX = x - canvasBaseX;
        int canvasY = canvasHeight - ySize - y + canvasBaseY;

        imageView.setImageBitmap(bitmap);
        imageView.setTranslationX(canvasX);
        imageView.setTranslationY(canvasY);
        imageView.getLayoutParams().height = ySize;
        imageView.getLayoutParams().width = xSize;
        imageView.requestLayout();
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    // 画像サイズの変更
    protected Bitmap changeSize(Bitmap image, int x, int y) {
        Matrix m = new Matrix();
        m.postScale(x / (float) image.getWidth(), y / (float) image.getHeight());
        return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), m, true);
    }

    //=========================
    //  文字用の関数
    //=========================
    public void drawTextView(int x, int y, TextView textView) {
        int canvasX = x - canvasBaseX;
        int canvasY = canvasHeight - textView.getHeight() - y + canvasBaseY;

        textView.setTranslationX(canvasX);
        textView.setTranslationY(canvasY);
        textView.requestLayout();
    }

    public void drawTextViewRight(int x, int y, TextView textView) {
        int canvasX = x - textView.getWidth() - canvasBaseX ;
        int canvasY = canvasHeight - textView.getHeight() - y + canvasBaseY;

        textView.setTranslationX(canvasX);
        textView.setTranslationY(canvasY);
        textView.requestLayout();
    }

    public void drawTextViewCenter(int x, int y, TextView textView) {
        int canvasX = x - textView.getWidth()/2 - canvasBaseX ;
        int canvasY = canvasHeight - textView.getHeight() - y + canvasBaseY;

        textView.setTranslationX(canvasX);
        textView.setTranslationY(canvasY);
        textView.requestLayout();
    }
    public void drawString(int x, int y, String string, TextView textView) {
        textView.setText(string);
        drawTextView(x,y,textView);
    }

    public void drawStringRight(int x, int y, String string, TextView textView) {
        textView.setText(string);
        drawTextViewRight(x,y,textView);
    }

    public void drawStringCenter(int x, int y, String string, TextView textView) {
        textView.setText(string);
        drawTextViewCenter(x,y,textView);
    }

    //=========================
    //  画面の解像度取得
    //=========================
    public int getCanvasHeight() {
        return canvasHeight;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }
}
