package com.jaquadro.minecraft.gardenstuff.block.tile;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import com.jaquadro.minecraft.gardencore.block.tile.TileEntityBlockMateralProxy;

public class TileEntityLatticeWood extends TileEntityLattice {

    public static final TileEntityLatticeWood instance = new TileEntityLatticeWood();

    @Override
    protected TileEntityBlockMateralProxy createTileEntity() {
        return new TileEntityLatticeWood();
    }

    @Override
    protected Block getBlockFromStandardMetadata(int metadata) {
        return Blocks.planks;
    }
}
