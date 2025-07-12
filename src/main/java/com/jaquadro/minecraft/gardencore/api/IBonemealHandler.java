package com.jaquadro.minecraft.gardencore.api;

import net.minecraft.world.World;

import com.jaquadro.minecraft.gardencore.block.BlockGarden;

public interface IBonemealHandler {

    public boolean applyBonemeal(World world, int x, int y, int z, BlockGarden hostBlock, int slot);
}
