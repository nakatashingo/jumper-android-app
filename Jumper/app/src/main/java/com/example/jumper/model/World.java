package com.example.jumper.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class World {
    // ----------
    // 定数宣言
    // ----------
    public static final int PLATFORM_COUNTS = 5;
    public static final int MOVING_PLATFORM_COUNTS = 5;
    public static final int BROKEN_PLATFORM_COUNTS = 5;
    public static final int UFO_COUNTS = 6;
    public static final int COIN_COUNTS = 7;
    public static final int SPRING_COUNTS = 3;

    // ----------
    // モデル
    // ----------
    // ----------
    // モデル
    // ----------
    private Player player;
    private Castle castle;
    private List<Platform> platforms;
    private List<MovingPlatform> movingPlatforms;
    private List<BrokenPlatform> brokenPlatforms;
    private List<Ufo> ufos;
    private List<Coin> coins;
    private List<Spring> springs;

    private List<GameCharacter> allCharacters;

    public World() {
        // ----------
        // モデルの生成
        // ----------
        player = new Player();
        castle = new Castle();

        platforms = IntStream.range(0, PLATFORM_COUNTS)
                .map(n->400+n*1200)
                .mapToObj(y-> new Platform(y))
                .collect(Collectors.toList());

        movingPlatforms = IntStream.range(0, MOVING_PLATFORM_COUNTS)
                .map(n->800+n*1200)
                .mapToObj(y-> new MovingPlatform(y))
                .collect(Collectors.toList());

        brokenPlatforms = IntStream.range(0, BROKEN_PLATFORM_COUNTS)
                .map(n->1200+n*1200)
                .mapToObj(y-> new BrokenPlatform(y))
                .collect(Collectors.toList());

        ufos = IntStream.range(0, UFO_COUNTS)
                .map(n->1100+n*1000)
                .mapToObj(y-> new Ufo(y))
                .collect(Collectors.toList());

        coins = IntStream.range(0, COIN_COUNTS)
                .map(n->450+n*800)
                .mapToObj(y-> new Coin(y))
                .collect(Collectors.toList());

        springs = IntStream.range(0, SPRING_COUNTS)
                .map(n->1800+n*1800)
                .mapToObj(y-> new Spring(y))
                .collect(Collectors.toList());

        allCharacters = new LinkedList<GameCharacter>();
        allCharacters.add(player);
        allCharacters.add(castle);
        allCharacters.addAll(platforms);
        allCharacters.addAll(movingPlatforms);
        allCharacters.addAll(brokenPlatforms);
        allCharacters.addAll(ufos);
        allCharacters.addAll(coins);
        allCharacters.addAll(springs);



        // ----------
        // モデルの接続
        // ----------
        castle.setPlayer(player);

        platforms.forEach(x->x.setPlayer(player));
        movingPlatforms.forEach(x->x.setPlayer(player));
        brokenPlatforms.forEach(x->x.setPlayer(player));
        ufos.forEach(x->x.setPlayer(player));
        coins.forEach(x->x.setPlayer(player));
        springs.forEach(x->x.setPlayer(player));
    }

    public void move(){
        // ----------
        // モデルの更新
        // ----------
        allCharacters.forEach(x->x.move());
        /*
        // プレイヤー
        player.move();
        castle.hit();
        platforms.forEach(x->x.move());
        movingPlatforms.forEach(x->x.move());
        brokenPlatforms.forEach(x->x.move());
        ufos.forEach(x->x.move());
        coins.forEach(x->x.move());
        springs.forEach(x->x.move());
        */


    }

    public Player getPlayer() {
        return player;
    }

    public List<GameCharacter> getAllCharacters() {
        return allCharacters;
    }


    /*
    public Castle getCastle() {
        return castle;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public List<MovingPlatform> getMovingPlatforms() {
        return movingPlatforms;
    }

    public List<BrokenPlatform> getBrokenPlatforms() {
        return brokenPlatforms;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public List<Ufo> getUfos() {
        return ufos;
    }

    public List<Spring> getSprings() {
        return springs;
    }
    */
}
