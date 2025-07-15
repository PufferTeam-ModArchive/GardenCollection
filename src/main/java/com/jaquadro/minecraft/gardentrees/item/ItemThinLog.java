package com.jaquadro.minecraft.gardentrees.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.jaquadro.minecraft.gardentrees.block.BlockThinLog;

public class ItemThinLog extends ItemBlock {

    String[] woodNames;
    String modName;
    String prefix;

    public ItemThinLog(Block block) {
        super(block);
        setHasSubtypes(true);

        woodNames = ((BlockThinLog) block).getWoods();
        modName = ((BlockThinLog) block).getMod();
        if (((BlockThinLog) block).isStripped()) {
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
            return "tile.gardentrees." + prefix + woodNames[meta] + "_thin_log";
        }
        return "tile.gardentrees." + modName + "_" + prefix + woodNames[meta] + "_thin_log";
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

}
