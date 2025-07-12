package com.jaquadro.minecraft.gardentrees.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModCreativeTabs {

    private ModCreativeTabs() {}

    public static final CreativeTabs tabGardenTrees = new CreativeTabs("gardenTrees") {

        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.thinLogFence);
        }
    };
}
