package com.jaquadro.minecraft.gardentrees.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.jaquadro.minecraft.gardentrees.block.BlockThinLogFence;

public class ItemThinLogFence extends ItemBlock {

    String[] woodNames;
    String modName;
    String prefix;

    public ItemThinLogFence(Block block) {
        super(block);
        setHasSubtypes(true);

        woodNames = ((BlockThinLogFence) block).getWoods();
        modName = ((BlockThinLogFence) block).getMod();
        if (((BlockThinLogFence) block).isStripped()) {
            prefix = "stripped_";
        } else {
            prefix = "";
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= woodNames.length) return super.getUnlocalizedName();

        if (modName.equals("vanilla")) {
            return "tile.gardentrees." + prefix + woodNames[meta] + "_thin_log_fence";
        }
        return "tile.gardentrees." + modName + "_" + prefix + woodNames[meta] + "_thin_log_fence";
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
