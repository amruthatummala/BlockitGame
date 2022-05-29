package com.example.objectanimator;

import java.util.ArrayList;

public class GameInfo {
    private static ArrayList<Block> blockPool = new ArrayList<Block>();
    //private static ArrayList<Block> backUpBlocks = new ArrayList<>();
    private static String difficulty = "";

    public void setBlocks(ArrayList<Block> b) {
        for (int i = 0; i < b.size(); i++) {
            blockPool.add(b.get(i));
            //backUpBlocks.add(b.get(i));
        }
    }


    public ArrayList<Block> getBlocks() {
//        ArrayList<Block> blocks = new ArrayList<>();
//        for (Block b : blockPool) {
//            blocks.add(b);
//        }
//        return blocks;
        return blockPool;
    }

    public void clearBlocks() {
        int i = 0;
        while (i < blockPool.size()) {
            blockPool.remove(i);
            //backUpBlocks.remove(i);
        }
    }

    public void restartGame() {
        int i = 0;
        while (i < blockPool.size()) {
            blockPool.remove(i);
            //backUpBlocks.remove(i);
        }
    }

    public void setDifficulty(String s) {
        difficulty = s;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
