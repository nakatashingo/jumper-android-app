package com.example.jumper.model;

public class Coin extends GameCharacter {
    // 属性
    private int count = 0;
    private int state = 0;
    private Player player;
    private boolean disappearFlag = false;

    // コンストラクタ
    public Coin(int inity) {
        x = (int)(Math.random() * 600);
        y = inity + (int)(Math.random() * 100  - 300);
        xSize = 75;
        ySize = 75;
    }

    // メソッド
    public int getState() {
        return state;
    }

    public void setPlayer(Player player) { this.player = player; }

    public void move() {
        count += 1;
        if (disappearFlag == true) {
            return;
        }
        if (count % 10 == 0){
            state++;
            if (state == 4){
                state = 1;
            }
        }
        if (overlap(player)){
            if (disappearFlag == false) {
                onOverlapListener.overlap();
                player.addPoint(10);
                disappearFlag = true;
                state = -1;
            }
        }
    }

}
