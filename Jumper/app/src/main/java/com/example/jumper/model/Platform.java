package com.example.jumper.model;

public class Platform extends GameCharacter {
    // 属性
    private Player player;

    // コンストラクタ
    public Platform(int inity) {
        x = (int)(Math.random() * 500);
        y = inity + (int)(Math.random()*100 - 50);
        xSize = 192;
        ySize = 42;
    }

    // メソッド
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void move() {
        if (player.getySpeed()<=0 && overlap(player) == true) {
            player.jump();
            onOverlapListener.overlap();
        }
    }
}
