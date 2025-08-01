package com.example.jumper.model;

public class Castle extends GameCharacter {
    // 属性
    private Player player;


    // コンストラクタ
    public Castle() {
        x = 500;
        y = 6500;
        xSize = 192;
        ySize = 192;
    }
    // メソッド
    public void setPlayer(Player player) { this.player = player; }
    public void move() {
        if (overlap(player) == true){
            player.clear();
        }
    }
}
