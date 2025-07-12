package com.jaquadro.minecraft.gardentrees.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

import com.jaquadro.minecraft.gardentrees.block.BlockGTSapling;

public class ItemGTSapling extends ItemMultiTexture {

    public ItemGTSapling(Block block) {
        super(block, block, BlockGTSapling.types);
    }
}
