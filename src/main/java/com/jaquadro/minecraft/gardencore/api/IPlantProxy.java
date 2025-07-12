package com.jaquadro.minecraft.gardencore.api;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;

/**
 * Created by Justin on 11/30/2014.
 */
public interface IPlantProxy {
    // BlockGarden getGardenBlock (IBlockAccess blockAccess, int x, int y, int z);

    TileEntityGarden getGardenEntity(IBlockAccess blockAccess, int x, int y, int z);

    boolean applyBonemeal(World world, int x, int y, int z);
}
