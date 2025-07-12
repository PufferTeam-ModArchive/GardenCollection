package com.jaquadro.minecraft.gardenstuff.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

import com.jaquadro.minecraft.gardenstuff.block.BlockLatticeWood;

public class ItemLatticeWood extends ItemMultiTexture {

    public ItemLatticeWood(Block block) {
        super(block, block, BlockLatticeWood.subNames);
    }
}
