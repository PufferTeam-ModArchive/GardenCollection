package com.jaquadro.minecraft.gardenstuff.integration.nei;

import com.jaquadro.minecraft.gardenstuff.client.gui.GuiBloomeryFurnace;

import codechicken.nei.api.API;

public class NEIHelper {

    public static void registerNEI() {
        API.registerRecipeHandler(new BloomeryFurnaceRecipeHandler());
        API.registerUsageHandler(new BloomeryFurnaceRecipeHandler());
        API.registerGuiOverlay(GuiBloomeryFurnace.class, "bloomerySmelting");
    }
}
