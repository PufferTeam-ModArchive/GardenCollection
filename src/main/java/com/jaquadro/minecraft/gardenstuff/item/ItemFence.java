package com.jaquadro.minecraft.gardenstuff.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

import com.jaquadro.minecraft.gardenstuff.block.BlockFence;

public class ItemFence extends ItemMultiTexture {

    public ItemFence(Block block) {
        super(block, block, BlockFence.subNames);
    }
}
