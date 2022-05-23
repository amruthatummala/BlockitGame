package com.example.objectanimator;

import java.util.ArrayList;

public class BlockList {
    private static ArrayList<Block> blocks = new ArrayList<Block>();

    public BlockList(ArrayList<Block> b) {
        for (int i = 0; i < b.size(); i++) {
            blocks.add(b.get(i));
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }
}
