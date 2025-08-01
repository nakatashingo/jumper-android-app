package com.example.jumper.model;

public class Spring extends GameCharacter {
    // 属性
    private Player player;

    // コンストラクタ
    public Spring(int inity) {
        x = (int)(Math.random() * 600);
        y = inity + (int)(Math.random()*100 - 200);
        xSize = 70;
        ySize = 120;
    }

    // メソッド
    public void setPlayer(Player player) { this.player = player; }
    public void move() {
        if (player.getySpeed()<=0 && overlap(player) == true) {
            player.highJump();
            onOverlapListener.overlap();
        }
    }
}
