package com.example.jumper.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.jumper.MainActivity;
import com.example.jumper.R;
import com.example.jumper.helpers.BaseView;
import com.example.jumper.model.BrokenPlatform;
import com.example.jumper.model.Castle;
import com.example.jumper.model.Coin;
import com.example.jumper.model.GameCharacter;
import com.example.jumper.model.MovingPlatform;
import com.example.jumper.model.Platform;
import com.example.jumper.model.Player;
import com.example.jumper.model.Spring;
import com.example.jumper.model.Ufo;
import com.example.jumper.model.World;

import java.util.LinkedList;

public class MainView extends BaseView {

    ConstraintLayout constraintLayout;
    Context context;
    MainActivity mainActivity;

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
    // 画像
    // ----------
    Bitmap playerRightImage;
    Bitmap playerLeftImage;
    Bitmap platformImage;
    Bitmap coinImage;
    Bitmap castleImage;
    Bitmap movingPlatformImage;
    Bitmap ufoRightImage;
    Bitmap ufoLeftImage;
    Bitmap brokenPlatform0Image;
    Bitmap brokenPlatform1Image;
    Bitmap brokenPlatform2Image;
    Bitmap brokenPlatform3Image;
    Bitmap coin0Image;
    Bitmap coin1Image;
    Bitmap coin2Image;
    Bitmap coin3Image;
    Bitmap springImage;

    // ----------
    // ビュー
    // ----------
    ImageViewBuilder imageViewBuilder;

    TextView gameEndTextView;
    TextView scoreTextView;

    Button retryButton;


    public MainView(Context context) {
        super(context);
        this.context = context;
        this.mainActivity=(MainActivity)context;
        this.constraintLayout = baseActivity.findViewById(R.id.main);

        // ----------
        // 画像の読み込み
        // ----------
        playerRightImage = loadImage(R.drawable.player_right);
        playerLeftImage = loadImage(R.drawable.player_left);
        platformImage = loadImage(R.drawable.platform);
        coinImage = loadImage(R.drawable.coin);
        castleImage = loadImage(R.drawable.castle);
        movingPlatformImage = loadImage(R.drawable.platform);
        ufoRightImage = loadImage(R.drawable.ufo_right);
        ufoLeftImage = loadImage(R.drawable.ufo_left);
        brokenPlatform0Image = loadImage(R.drawable.platform);
        brokenPlatform1Image = loadImage(R.drawable.platform1);
        brokenPlatform2Image = loadImage(R.drawable.platform2);
        brokenPlatform3Image = loadImage(R.drawable.platform3);
        coin0Image = loadImage(R.drawable.coin);
        coin1Image = loadImage(R.drawable.coin1);
        coin2Image = loadImage(R.drawable.coin2);
        coin3Image = loadImage(R.drawable.coin3);
        springImage = loadImage(R.drawable.spring);


        // ----------
        // ビューの取得生成
        // ----------
        // ImageView
        imageViewBuilder = new ImageViewBuilder(constraintLayout, context);

        // GameEnd
        gameEndTextView = new TextView(context);
        constraintLayout.addView(gameEndTextView);
        gameEndTextView.setVisibility(View.GONE);

        // Point
        scoreTextView = new TextView(context);
        constraintLayout.addView(scoreTextView);
        scoreTextView.setVisibility(View.GONE);

        // RetryButton
        retryButton = new Button(context);
        constraintLayout.addView(retryButton);
        retryButton.setVisibility(View.GONE);
        retryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.retry();
            }
        });

    }

    public void draw(World world) {
        // ----------
        // モデルの表示
        // ----------

        // スクロール
        int yMax = world.getPlayer().getyMax();
        if (yMax < 750) {
            canvasBaseY = 0;
        } else {
            canvasBaseY = yMax - 750;
        }

        // ImageBuilderのリセット
        imageViewBuilder.reset();


        // キャラクターの表示
        Player player = world.getPlayer();

        for(GameCharacter gameCharacter : world.getAllCharacters()){
            if(gameCharacter instanceof Player) {
                drawPlayer((Player) gameCharacter);
            }else if (gameCharacter instanceof Castle) {
                drawCharacter(gameCharacter, castleImage);
            }else if (gameCharacter instanceof Ufo) {
                drawUfo((Ufo) gameCharacter);
            }else if (gameCharacter instanceof Coin) {
                drawCoin((Coin) gameCharacter);
            }else if (gameCharacter instanceof Spring) {
                drawCharacter(gameCharacter, springImage);
            }else if (gameCharacter instanceof BrokenPlatform) {
                drawBrokenPlatform((BrokenPlatform) gameCharacter);
            }else if (gameCharacter instanceof MovingPlatform) {
                drawCharacter(gameCharacter, movingPlatformImage);
            }else if (gameCharacter instanceof Platform) {
                drawCharacter(gameCharacter, platformImage);
            }
        }

        // Scoreの表示
        int score = player.getPoint();
        drawScore(score);

        // gameOver->Clearをなくしたいならelifを使う　テスト用にそれはしてない
        // GameEndの表示
        if (player.isDead()) {
            drawGameOver();
            drawRetryButton();
        } else if (player.isClear()) {
            drawGameClear();
            eraseRetryButton();
        }else {
            gameEndTextView.setVisibility(GONE);
            eraseRetryButton();
        }

    }

    // ====================
    // リトライボタン表示用の関数
    // ====================
    public void drawRetryButton() {
        retryButton.setVisibility(View.VISIBLE);
        retryButton.setTextSize(24);
        retryButton.setText("もう一回！");
        drawViewCenter(350, canvasBaseY + 100, retryButton);
    }

    public void eraseRetryButton() {
        retryButton.setVisibility(View.GONE);
    }


    //======================
    // テキスト表示用の関数
    //======================
    public void drawScore(int score) {
        scoreTextView.setText("" + score);
        scoreTextView.setTextColor(Color.BLUE);
        scoreTextView.setTextSize(32);
        scoreTextView.setVisibility(View.VISIBLE);
        drawTextViewCenter(700, 1400 + canvasBaseY, scoreTextView);
    }

    public void drawGameOver() {
        gameEndTextView.setText("Game Over !!");
        gameEndTextView.setTextSize(32);
        gameEndTextView.setTextColor(Color.RED);
        gameEndTextView.setVisibility(View.VISIBLE);
        drawTextViewCenter(350, 750 + canvasBaseY, gameEndTextView);
    }

    public void drawGameClear() {
        gameEndTextView.setText("Game Clear !!");
        gameEndTextView.setTextSize(32);
        gameEndTextView.setTextColor(Color.BLUE);
        gameEndTextView.setVisibility(View.VISIBLE);
        drawTextViewCenter(350, 750 + canvasBaseY, gameEndTextView);
    }




    //======================
    // キャラクター表示用の関数
    //======================
    // 画像が変化しないキャラクターの表示
    public void drawCharacter(GameCharacter gameCharacter, Bitmap image) {
        ImageView imageView = imageViewBuilder.getImageView();
        int x = gameCharacter.getX();
        int y = gameCharacter.getY();
        int xSize = gameCharacter.getxSize();
        int ySize = gameCharacter.getySize();
        drawImage(x, y, xSize, ySize, image, imageView);
    }

    public void drawPlayer(Player player) {
        if (player.getX() > 0){
            drawCharacter(player, playerRightImage);
        }else {
            drawCharacter(player, playerLeftImage);
        }
    }

    public void drawCoin(Coin coin){
        switch(coin.getState()){
            case 1:
                drawCharacter(coin, coin1Image);
                break;
            case 2:
                drawCharacter(coin, coin2Image);
                break;
            case 3:
                drawCharacter(coin, coin3Image);
                break;
        }

    }

    public void drawUfo(Ufo ufo) {
        if (ufo.getX() > 0){
            drawCharacter(ufo, ufoRightImage);
        }else {
            drawCharacter(ufo, ufoLeftImage);
        }
    }

    public void drawBrokenPlatform(BrokenPlatform brokenPlatform){
        switch(brokenPlatform.getState()) {
            case 0:
                drawCharacter(brokenPlatform, platformImage);
                break;
            case 1:
                drawCharacter(brokenPlatform, brokenPlatform1Image);
                break;
            case 2:
                drawCharacter(brokenPlatform, brokenPlatform2Image);
                break;
            case 3:
                drawCharacter(brokenPlatform, brokenPlatform3Image);
                break;
        }
    }
}

