package com.example.jumper.model;

public class MovingPlatform extends GameCharacter {
    // 属性
    private int xSpeed = 3;
    private Player player;

    // コンストラクタ
    public MovingPlatform(int inity) {
        x = (int)(Math.random() * 500);
        y = inity + (int)(Math.random() * 100 -50);
        xSize = 192;
        ySize = 42;
    }

    // メソッド

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }
    public void setPlayer(Player player) { this.player = player; }

    public void move() {
        if (x <= 50 ) {
            xSpeed = 2;
        }
        if (x >= 368 ) {
            xSpeed = -2;
        }
        x += xSpeed;
        if (player.getySpeed()<=0 && overlap(player) == true) {
            player.jump();
            onOverlapListener.overlap();
        }
    }
}
