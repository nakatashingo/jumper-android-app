package com.example.jumper.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseView extends View {
    protected BaseActivity baseActivity;

    // 画面の基準座標
    protected int canvasBaseX = 0;
    protected int canvasBaseY = 0;

    // 画面の幅、高さ
    protected final int canvasWidth;
    protected final int canvasHeight;

    public BaseView(Context context) {
        super(context);

        baseActivity=(BaseActivity)context;
        this.canvasHeight=baseActivity.getCanvasHeight();
        this.canvasWidth=baseActivity.getCanvasWidth();
    }

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
    //  View用の関数
    //=========================
    public void drawView(int x, int y, View textView) {
        int canvasX = x - canvasBaseX;
        int canvasY = canvasHeight - textView.getHeight() - y + canvasBaseY;

        textView.setTranslationX(canvasX);
        textView.setTranslationY(canvasY);
        textView.requestLayout();
    }

    public void drawViewRight(int x, int y, View textView) {
        int canvasX = x - textView.getWidth() - canvasBaseX ;
        int canvasY = canvasHeight - textView.getHeight() - y + canvasBaseY;

        textView.setTranslationX(canvasX);
        textView.setTranslationY(canvasY);
        textView.requestLayout();
    }

    public void drawViewCenter(int x, int y, View textView) {
        int canvasX = x - textView.getWidth()/2 - canvasBaseX ;
        int canvasY = canvasHeight - textView.getHeight() - y + canvasBaseY;

        textView.setTranslationX(canvasX);
        textView.setTranslationY(canvasY);
        textView.requestLayout();
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



}
