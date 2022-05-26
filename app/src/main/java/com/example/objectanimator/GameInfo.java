package com.example.objectanimator;

import java.util.ArrayList;

public class GameInfo {
    private static ArrayList<Block> blockPool = new ArrayList<Block>();
    private static String difficulty;

    public void setBlocks(ArrayList<Block> b) {
        for (int i = 0; i < b.size(); i++) {
            blockPool.add(b.get(i));
        }
    }

    public ArrayList<Block> getBlocks() {
        return blockPool;
    }

    public void setDifficulty(String s) {
        difficulty = s;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
