package com.jaquadro.minecraft.gardentrees.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardentrees.GardenTrees;
import com.jaquadro.minecraft.gardentrees.core.ClientProxy;
import com.jaquadro.minecraft.gardentrees.core.ModCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockThinLogFence extends Block {

    String[] woodNames;
    String modName;
    boolean woodStripped;

    @SideOnly(Side.CLIENT)
    private IIcon[] tree;

    @SideOnly(Side.CLIENT)
    private IIcon[] tree_top;

    IIcon sideIcon;

    public BlockThinLogFence(String[] woods, String mod, boolean stripped) {
        super(Material.wood);

        setCreativeTab(ModCreativeTabs.tabGardenTrees);
        setHardness(1.5f);
        setResistance(5f);
        setLightOpacity(0);
        setStepSound(Block.soundTypeWood);

        woodNames = woods;
        modName = mod;
        woodStripped = stripped;

        setBlockBoundsForItemRender();
    }

    public float getMargin() {
        return 0.25f;
    }

    @Override
    public void setBlockBoundsForItemRender() {
        float margin = getMargin();
        setBlockBounds(margin, 0, margin, 1 - margin, 1, 1 - margin);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list,
        Entity colliding) {
        boolean connectedZNeg = canConnectFenceTo(world, x, y, z - 1);
        boolean connectedZPos = canConnectFenceTo(world, x, y, z + 1);
        boolean connectedXNeg = canConnectFenceTo(world, x - 1, y, z);
        boolean connectedXPos = canConnectFenceTo(world, x + 1, y, z);

        float margin = getMargin();
        float xs = margin;
        float xe = 1 - margin;
        float zs = margin;
        float ze = 1 - margin;

        if (connectedZNeg) zs = 0;
        if (connectedZPos) ze = 1;
        if (connectedZNeg || connectedZPos) {
            setBlockBounds(xs, 0, zs, xe, 1.5f, ze);
            super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
        }

        zs = margin;
        ze = 1 - margin;

        if (connectedXNeg) xs = 0;
        if (connectedXPos) xe = 1;
        if (connectedXNeg || connectedXPos || (!connectedZNeg && !connectedZPos)) {
            setBlockBounds(xs, 0, zs, xe, 1.5f, ze);
            super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
        }

        if (connectedZNeg) zs = 0;
        if (connectedZPos) ze = 1;

        setBlockBounds(xs, 0, zs, xe, 1, ze);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        boolean connectedZNeg = canConnectFenceTo(world, x, y, z - 1);
        boolean connectedZPos = canConnectFenceTo(world, x, y, z + 1);
        boolean connectedXNeg = canConnectFenceTo(world, x - 1, y, z);
        boolean connectedXPos = canConnectFenceTo(world, x + 1, y, z);

        float margin = getMargin();
        float xs = margin;
        float xe = 1 - margin;
        float zs = margin;
        float ze = 1 - margin;

        if (connectedZNeg) zs = 0;
        if (connectedZPos) ze = 1;
        if (connectedXNeg) xs = 0;
        if (connectedXPos) xe = 1;

        setBlockBounds(xs, 0, zs, xe, 1, ze);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public int getRenderType() {
        return ClientProxy.thinLogFenceRenderID;
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        return true;
    }

    public boolean canConnectFenceTo(IBlockAccess world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block != this) return (block.getMaterial()
            .isOpaque() && block.renderAsNormalBlock()) ? block.getMaterial() != Material.gourd : false;

        return true;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        int meta2 = meta % 4;

        int k = meta2 & 12;
        int i = meta2 & 3;
        int l = i;
        if (meta < 4) {
            l = i;
        } else if (meta < 8) {
            l = i + 4;
        } else if (meta < 12) {
            l = i + 8;
        } else if (meta < 16) {
            l = i + 12;
        }
        return k == 0 && (side == 1 || side == 0) ? tree_top[l]
            : (k == 4 && (side == 5 || side == 4) ? tree_top[l]
                : (k == 8 && (side == 2 || side == 3) ? tree_top[l] : tree[l]));
    }

    @SideOnly(Side.CLIENT)
    public IIcon getSideIcon() {
        return sideIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List<ItemStack> list) {
        for (int i = 0; i < woodNames.length; i++) list.add(new ItemStack(this, 1, i));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        sideIcon = iconRegister.registerIcon(GardenTrees.MOD_ID + ":thinlog_fence_side");
        tree = new IIcon[woodNames.length];
        tree_top = new IIcon[woodNames.length];
        for (int i = 0; i < woodNames.length; i++) {
            if (!woodStripped) {
                if (modName.equals("vanilla")) {
                    tree[i] = iconRegister.registerIcon("minecraft:log_" + woodNames[i]);
                    tree_top[i] = iconRegister.registerIcon("minecraft:log_" + woodNames[i] + "_top");
                } else if (modName.equals("bop")) {
                    tree[i] = iconRegister.registerIcon("biomesoplenty:log_" + woodNames[i] + "_side");
                    tree_top[i] = iconRegister.registerIcon("biomesoplenty:log_" + woodNames[i] + "_heart");
                } else if (modName.equals("thaumcraft")) {
                    tree[i] = iconRegister.registerIcon("thaumcraft:" + woodNames[i] + "side");
                    tree_top[i] = iconRegister.registerIcon("thaumcraft:" + woodNames[i] + "top");
                } else if (modName.equals("witchery")) {
                    tree[i] = iconRegister.registerIcon("witchery:log_" + woodNames[i]);
                    tree_top[i] = iconRegister.registerIcon("witchery:log_" + woodNames[i] + "_top");
                }
            } else {
                String actualModName = modName;
                if (modName.equals("vanilla")) {
                    actualModName = "minecraft";
                } else if (modName.equals("bop")) {
                    actualModName = "biomesoplenty";
                }
                tree[i] = iconRegister.registerIcon(actualModName + ":stripped_" + woodNames[i] + "_log");
                tree_top[i] = iconRegister.registerIcon(actualModName + ":stripped_" + woodNames[i] + "_log_top");
            }
        }
    }

    public String[] getWoods() {
        return woodNames;
    }

    public String getMod() {
        return modName;
    }

    public boolean isStripped() {
        return woodStripped;
    }
}
