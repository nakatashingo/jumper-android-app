package com.example.jumper;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.jumper.helpers.BaseActivity;
import com.example.jumper.model.BrokenPlatform;
import com.example.jumper.model.Coin;
import com.example.jumper.model.MovingPlatform;
import com.example.jumper.model.OnOverlapListener;
import com.example.jumper.model.Platform;
import com.example.jumper.model.Spring;
import com.example.jumper.model.Ufo;
import com.example.jumper.model.World;
import com.example.jumper.views.MainView;

public class MainActivity extends BaseActivity {

    // ----------
    // 定数宣言
    // ----------
    public final int PLATFORM_COUNTS = 5;
    public final int MOVING_PLATFORM_COUNTS = 5;
    public final int BROKEN_PLATFORM_COUNTS = 5;
    public final int UFO_COUNTS = 6;
    public final int COIN_COUNTS = 7;
    public final int SPRING_COUNTS = 3;



    // ----------
    // ビュー
    // ----------
    MainView mainView;

    // ----------
    // モデル
    // ----------
    World world;

    // ----------
    // モデル
    // ----------
    int platform_sound;
    int movingPlatform_sound;
    int brokenPlatform_sound;
    int coin_sound;
    int ufo_sound;
    int spring_sound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // 加速度センサーを有効化
        gravityEnabled = true;

        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        // 画面の向きを固定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // ----------
        // ビューの取得・生成
        // ----------
        mainView = new MainView(this);


        // ----------
        // モデルの生成
        // ----------
        world = new World();

        // ----------
        // BGM
        // ----------
        loadMusic(R.raw.bgm);
        setMusicVolume(0.5f);
        startMusic();

        //----------
        // 効果音
        // ---------
        platform_sound = loadSound(R.raw.platform);
        movingPlatform_sound = loadSound(R.raw.platform);
        brokenPlatform_sound = loadSound(R.raw.broken);
        coin_sound = loadSound(R.raw.coin);
        ufo_sound = loadSound(R.raw.ufo);
        spring_sound = loadSound(R.raw.spring);


        connectEffectSound(world);
    }

    @Override
    public void update(){
        // ----------
        // モデルへの更新
        // ----------
        int accelx = (int) accelerationController.x;
        world.getPlayer().setxSpeed(-accelx);


        // ---------
        // モデルの更新
        // ---------
        world.move();

        // ----------
        // モデルの表示
        // ----------
        mainView.draw(world);

    }

    // ----------
    // 効果音の接続
    // ----------
    public void connectEffectSound(World world){
        world.getAllCharacters().stream()
                .filter((x -> x instanceof Platform))
                .forEach(x->x.setOnOverlapListener(new PlatformOnOverlapListener()));
        world.getAllCharacters().stream()
                .filter((x -> x instanceof MovingPlatform))
                .forEach(x->x.setOnOverlapListener(new MovingPlatformOnOverlapListener()));
        world.getAllCharacters().stream()
                .filter((x -> x instanceof BrokenPlatform))
                .forEach(x->x.setOnOverlapListener(new BrokenPlatformOnOverlapListener()));
        world.getAllCharacters().stream()
                .filter((x -> x instanceof Coin))
                .forEach(x->x.setOnOverlapListener(new CoinOnOverLapListener()));
        world.getAllCharacters().stream()
                .filter((x -> x instanceof Ufo))
                .forEach(x->x.setOnOverlapListener(new UfoOnOverLapListener()));
        world.getAllCharacters().stream()
                .filter((x -> x instanceof Spring))
                .forEach(x->x.setOnOverlapListener(new SpringOnOverLapListener()));


    }

    // ----------
    // Retry処理
    // ----------

    public void retry() {
        stopMusic();

        world = new World();
        connectEffectSound(world);

        loadMusic(R.raw.bgm);
        setMusicVolume(0.5f);
        startMusic();
    }


    // ----------
    // イベントリスナー
    // ----------
    public class PlatformOnOverlapListener implements OnOverlapListener {

        @Override
        public void overlap() {
            playSound(platform_sound, 1.0f);
        }
    }

    public class MovingPlatformOnOverlapListener implements OnOverlapListener {

        @Override
        public void overlap() {
            playSound(movingPlatform_sound, 1.0f);
        }
    }

    public class BrokenPlatformOnOverlapListener implements OnOverlapListener{

        @Override
        public void overlap() {
            playSound(brokenPlatform_sound, 1.0f);
        }
    }

    public class CoinOnOverLapListener implements  OnOverlapListener{

        @Override
        public void overlap() {
            playSound(coin_sound, 1.0f);
        }
    }

    public class UfoOnOverLapListener implements OnOverlapListener{

        @Override
        public void overlap() {
            playSound(ufo_sound, 1.0f);;
        }
    }

    public class SpringOnOverLapListener implements OnOverlapListener{

        @Override
        public void overlap() {
            playSound(spring_sound, 1.0f);
        }
    }
}