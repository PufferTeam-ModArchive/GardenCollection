package com.jaquadro.minecraft.gardenstuff.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

import com.jaquadro.minecraft.gardenstuff.block.BlockMossBrick;

public class ItemMossBrick extends ItemMultiTexture {

    public ItemMossBrick(Block block) {
        super(block, block, BlockMossBrick.subNames);
    }
}
