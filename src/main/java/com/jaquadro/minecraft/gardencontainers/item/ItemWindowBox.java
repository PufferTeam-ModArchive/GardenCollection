package com.jaquadro.minecraft.gardencontainers.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardencontainers.block.BlockWindowBox;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityWindowBox;

public class ItemWindowBox extends ItemMultiTexture {

    public ItemWindowBox(Block block) {
        super(block, block, getSubTypes(block));
    }

    private static String[] getSubTypes(Block block) {
        if (block instanceof BlockWindowBox) return ((BlockWindowBox) block).getSubTypes();
        else return new String[0];
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        if (!super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata & 15)) return false;

        boolean isLower = side != 0 && (side == 1 || hitY <= 0.5);

        TileEntityWindowBox te = (TileEntityWindowBox) world.getTileEntity(x, y, z);
        if (te != null) {
            te.setUpper(!isLower);

            if (side != 0 && side != 1) te.setDirection((side % 2 == 0) ? side + 1 : side - 1);
        }

        return true;
    }

    @Override
    public void registerIcons(IIconRegister register) {}
}
