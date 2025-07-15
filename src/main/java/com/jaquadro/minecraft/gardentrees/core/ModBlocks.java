package com.jaquadro.minecraft.gardentrees.core;

import net.minecraft.block.Block;

import org.apache.logging.log4j.Level;

import com.jaquadro.minecraft.gardencore.Constants;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardentrees.GardenTrees;
import com.jaquadro.minecraft.gardentrees.block.*;
import com.jaquadro.minecraft.gardentrees.item.ItemGTSapling;
import com.jaquadro.minecraft.gardentrees.item.ItemIvy;
import com.jaquadro.minecraft.gardentrees.item.ItemThinLog;
import com.jaquadro.minecraft.gardentrees.item.ItemThinLogFence;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static BlockThinLog thinLog;
    public static BlockThinLogFence thinLogFence;
    public static BlockThinLog strippedThinLog;
    public static BlockThinLogFence strippedThinLogFence;
    public static BlockThinLog thinLogBop;
    public static BlockThinLogFence thinLogFenceBop;
    public static BlockThinLog strippedThinLogBop;
    public static BlockThinLogFence strippedThinLogFenceBop;
    public static BlockThinLog thinLogThaumcraft;
    public static BlockThinLogFence thinLogFenceThaumcraft;
    public static BlockThinLog strippedThinLogThaumcraft;
    public static BlockThinLogFence strippedThinLogFenceThaumcraft;
    public static BlockThinLog thinLogWitchery;
    public static BlockThinLogFence thinLogFenceWitchery;
    public static BlockFlowerLeaves flowerLeaves;
    public static BlockGTSapling sapling;
    public static BlockIvy ivy;
    public static BlockStrangePlant strangePlant;
    public static BlockCandelilla candelilla;

    public void init() {
        thinLog = new BlockThinLog(Constants.woodTypes, "vanilla", false);
        thinLogFence = new BlockThinLogFence(Constants.woodTypes, "vanilla", false);
        thinLogBop = new BlockThinLog(Constants.bopWoodTypes, "bop", false);
        thinLogFenceBop = new BlockThinLogFence(Constants.bopWoodTypes, "bop", false);
        thinLogThaumcraft = new BlockThinLog(Constants.thaumcraftWoodTypes, "thaumcraft", false);
        thinLogFenceThaumcraft = new BlockThinLogFence(Constants.thaumcraftWoodTypes, "thaumcraft", false);
        thinLogWitchery = new BlockThinLog(Constants.witcheryWoodTypes, "witchery", false);
        thinLogFenceWitchery = new BlockThinLogFence(Constants.witcheryWoodTypes, "witchery", false);

        strippedThinLog = new BlockThinLog(Constants.woodTypes2, "vanilla", true);
        strippedThinLogFence = new BlockThinLogFence(Constants.woodTypes2, "vanilla", true);
        strippedThinLogBop = new BlockThinLog(Constants.bopWoodTypes, "bop", true);
        strippedThinLogFenceBop = new BlockThinLogFence(Constants.bopWoodTypes, "bop", true);
        strippedThinLogThaumcraft = new BlockThinLog(Constants.thaumcraftWoodTypes, "thaumcraft", true);
        strippedThinLogFenceThaumcraft = new BlockThinLogFence(Constants.thaumcraftWoodTypes, "thaumcraft", true);

        flowerLeaves = new BlockFlowerLeaves(makeName("flowerLeaves"));
        sapling = new BlockGTSapling(makeName("sapling"));
        ivy = new BlockIvy(makeName("ivy"));
        strangePlant = new BlockStrangePlant(makeName("strangePlant"));
        candelilla = new BlockCandelilla(makeName("candelilla"));

        GameRegistry.registerBlock(sapling, ItemGTSapling.class, "sapling");

        GameRegistry.registerBlock(thinLog, ItemThinLog.class, "thin_log");
        if (Loader.isModLoaded("BiomesOPlenty")) {
            GameRegistry.registerBlock(thinLogBop, ItemThinLog.class, "bop_thin_log");
        }
        if (Loader.isModLoaded("Thaumcraft")) {
            GameRegistry.registerBlock(thinLogThaumcraft, ItemThinLog.class, "thaumcraft_thin_log");
        }
        if (Loader.isModLoaded("witchery")) {
            GameRegistry.registerBlock(thinLogWitchery, ItemThinLog.class, "witchery_thin_log");
        }
        GameRegistry.registerBlock(thinLogFence, ItemThinLogFence.class, "thin_log_fence");
        if (Loader.isModLoaded("BiomesOPlenty")) {
            GameRegistry.registerBlock(thinLogFenceBop, ItemThinLogFence.class, "bop_thin_log_fence");
        }
        if (Loader.isModLoaded("Thaumcraft")) {
            GameRegistry.registerBlock(thinLogFenceThaumcraft, ItemThinLogFence.class, "thaumcraft_thin_log_fence");
        }
        if (Loader.isModLoaded("witchery")) {
            GameRegistry.registerBlock(thinLogFenceWitchery, ItemThinLogFence.class, "witchery_thin_log_fence");
        }

        if (Loader.isModLoaded("etfuturum")) {
            GameRegistry.registerBlock(strippedThinLog, ItemThinLog.class, "thin_log_stripped");
            if (Loader.isModLoaded("BiomesOPlenty")) {
                GameRegistry.registerBlock(strippedThinLogBop, ItemThinLog.class, "bop_thin_log_stripped");
            }
            if (Loader.isModLoaded("Thaumcraft")) {
                GameRegistry
                    .registerBlock(strippedThinLogThaumcraft, ItemThinLog.class, "thaumcraft_thin_log_stripped");
            }
            GameRegistry.registerBlock(strippedThinLogFence, ItemThinLogFence.class, "thin_log_fence_stripped");
            if (Loader.isModLoaded("BiomesOPlenty")) {
                GameRegistry
                    .registerBlock(strippedThinLogFenceBop, ItemThinLogFence.class, "bop_thin_log_fence_stripped");
            }
            if (Loader.isModLoaded("Thaumcraft")) {
                GameRegistry.registerBlock(
                    strippedThinLogFenceThaumcraft,
                    ItemThinLogFence.class,
                    "thaumcraft_thin_log_fence_stripped");
            }
        }

        // GameRegistry.registerBlock(flowerLeaves, "flower_leaves");
        GameRegistry.registerBlock(ivy, ItemIvy.class, "ivy");
        GameRegistry.registerBlock(strangePlant, "strange_plant");
        GameRegistry.registerBlock(candelilla, "candelilla_bush");

        // GameRegistry.registerTileEntity(TileEntityWoodProxy.class, ModBlocks.getQualifiedName(thinLog));
    }

    public static String makeName(String name) {
        return GardenTrees.MOD_ID.toLowerCase() + "." + name;
    }

    public static Block get(String name) {
        return GameRegistry.findBlock(GardenTrees.MOD_ID, name);
    }

    public static String getQualifiedName(Block block) {
        return GameData.getBlockRegistry()
            .getNameForObject(block);
    }

    public static UniqueMetaIdentifier getUniqueMetaID(Block block, int meta) {
        String name = GameData.getBlockRegistry()
            .getNameForObject(block);
        if (name == null) {
            FMLLog.log(GardenTrees.MOD_ID, Level.WARN, "Tried to make a UniqueMetaIdentifier from an invalid block");
            return null;
        }

        return new UniqueMetaIdentifier(name, meta);
    }
}
