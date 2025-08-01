package com.example.jumper.model;

public class Ufo extends GameCharacter {
    // 属性
    private int xSpeed = 2;
    private Player player;

    // コンストラクタ
    public Ufo(int inity) {
        x = (int)(Math.random() * 600);
        y = inity + (int)(Math.random() * 100 - 200);
        xSize = 96;
        ySize = 66;
    }

    // メソッド

    public int getxSpeed() {
        return xSpeed;
    }

    public void setPlayer(Player player) { this.player = player; }

    public void move() {
        if (x >= 604 ) {
            xSpeed = -2;
        }
        if (x <= 96 ) {
            xSpeed = 2;
        }
        x += xSpeed;

        //hit()のやつを移動させた
        if(overlap(player)) {
            onOverlapListener.overlap();
            player.dead();
        }
    }

    public void hit() {
        if (overlap(player) == true){
            player.dead();
        }
    }

}
