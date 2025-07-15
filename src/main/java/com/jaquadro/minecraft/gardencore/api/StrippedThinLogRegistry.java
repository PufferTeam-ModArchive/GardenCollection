package com.jaquadro.minecraft.gardencore.api;

import com.jaquadro.minecraft.gardencore.Constants;
import com.jaquadro.minecraft.gardentrees.core.ModBlocks;

import cpw.mods.fml.common.Loader;
import ganymedes01.etfuturum.api.StrippedLogRegistry;

public class StrippedThinLogRegistry extends StrippedLogRegistry {

    public static void init() {
        for (int i = 0; i < Constants.woodTypes.length; i++) addLog(ModBlocks.thinLog, i, ModBlocks.strippedThinLog, i);
        for (int i = 0; i < Constants.woodTypes.length; i++)
            addLog(ModBlocks.thinLogFence, i, ModBlocks.strippedThinLogFence, i);
        if (Loader.isModLoaded("BiomesOPlenty")) {
            for (int i = 0; i < Constants.bopWoodTypes.length; i++)
                addLog(ModBlocks.thinLogBop, i, ModBlocks.strippedThinLogBop, i);
            for (int i = 0; i < Constants.bopWoodTypes.length; i++)
                addLog(ModBlocks.thinLogFenceBop, i, ModBlocks.strippedThinLogFenceBop, i);
        }
        if (Loader.isModLoaded("Thaumcraft")) {
            for (int i = 0; i < Constants.thaumcraftWoodTypes.length; i++)
                addLog(ModBlocks.thinLogThaumcraft, i, ModBlocks.strippedThinLogThaumcraft, i);
            for (int i = 0; i < Constants.thaumcraftWoodTypes.length; i++)
                addLog(ModBlocks.thinLogFenceThaumcraft, i, ModBlocks.strippedThinLogFenceThaumcraft, i);
        }
    }
}
