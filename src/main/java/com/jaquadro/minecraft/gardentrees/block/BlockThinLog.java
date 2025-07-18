package com.jaquadro.minecraft.gardentrees.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.jaquadro.minecraft.gardenapi.api.connect.IChainSingleAttachable;
import com.jaquadro.minecraft.gardentrees.core.ClientProxy;
import com.jaquadro.minecraft.gardentrees.core.ModCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockThinLog extends Block implements IChainSingleAttachable {

    String[] woodNames;
    String modName;
    boolean woodStripped;

    @SideOnly(Side.CLIENT)
    private IIcon[] tree;

    @SideOnly(Side.CLIENT)
    private IIcon[] tree_top;

    // Scratch state variable for rendering purposes
    // 0 = Y, 1 = Z, 2 = X, 3 = BARK
    int orientation;

    public BlockThinLog(String[] woods, String mod, boolean stripped) {
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

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    @Override
    public void setBlockBoundsForItemRender() {
        float margin = getMargin();
        setBlockBounds(margin, 0, margin, 1 - margin, 1, 1 - margin);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list,
        Entity colliding) {
        int connectFlags = calcConnectionFlags(world, x, y, z);

        float margin = getMargin();
        float ys = (connectFlags & 1) != 0 ? 0 : margin;
        float ye = (connectFlags & 2) != 0 ? 1 : 1 - margin;
        float zs = (connectFlags & 4) != 0 ? 0 : margin;
        float ze = (connectFlags & 8) != 0 ? 1 : 1 - margin;
        float xs = (connectFlags & 16) != 0 ? 0 : margin;
        float xe = (connectFlags & 32) != 0 ? 1 : 1 - margin;

        setBlockBounds(xs, ys, zs, xe, ye, ze);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int connectFlags = calcConnectionFlags(world, x, y, z);

        float margin = getMargin();
        float ys = (connectFlags & 1) != 0 ? 0 : margin;
        float ye = (connectFlags & 2) != 0 ? 1 : 1 - margin;
        float zs = (connectFlags & 4) != 0 ? 0 : margin;
        float ze = (connectFlags & 8) != 0 ? 1 : 1 - margin;
        float xs = (connectFlags & 16) != 0 ? 0 : margin;
        float xe = (connectFlags & 32) != 0 ? 1 : 1 - margin;

        setBlockBounds(xs, ys, zs, xe, ye, ze);
    }

    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta) {
        byte b0 = 4;
        int i1 = b0 + 1;

        if (worldIn.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
            for (int j1 = -b0; j1 <= b0; ++j1) {
                for (int k1 = -b0; k1 <= b0; ++k1) {
                    for (int l1 = -b0; l1 <= b0; ++l1) {
                        Block block = worldIn.getBlock(x + j1, y + k1, z + l1);
                        if (block.isLeaves(worldIn, x + j1, y + k1, z + l1)) {
                            block.beginLeavesDecay(worldIn, x + j1, y + k1, z + l1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
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
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public int getRenderType() {
        return ClientProxy.thinLogRenderID;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    public int calcConnectionFlags(IBlockAccess world, int x, int y, int z) {
        int flagsY = calcConnectYFlags(world, x, y, z);
        int flagsZNeg = calcConnectYFlags(world, x, y, z - 1);
        int flagsZPos = calcConnectYFlags(world, x, y, z + 1);
        int flagsXNeg = calcConnectYFlags(world, x - 1, y, z);
        int flagsXPos = calcConnectYFlags(world, x + 1, y, z);

        int connectFlagsY = flagsY & 3;
        int connectFlagsZNeg = flagsZNeg & 3;
        int connectFlagsZPos = flagsZPos & 3;
        int connectFlagsXNeg = flagsXNeg & 3;
        int connectFlagsXPos = flagsXPos & 3;

        Block blockZNeg = world.getBlock(x, y, z - 1);
        Block blockZPos = world.getBlock(x, y, z + 1);
        Block blockXNeg = world.getBlock(x - 1, y, z);
        Block blockXPos = world.getBlock(x + 1, y, z);

        boolean hardZNeg = isNeighborHardConnection(world, x, y, z - 1, blockZNeg, ForgeDirection.NORTH)
            || blockZNeg instanceof BlockTorch;
        boolean hardZPos = isNeighborHardConnection(world, x, y, z + 1, blockZPos, ForgeDirection.SOUTH)
            || blockZPos instanceof BlockTorch;
        boolean hardXNeg = isNeighborHardConnection(world, x - 1, y, z, blockXNeg, ForgeDirection.WEST)
            || blockXNeg instanceof BlockTorch;
        boolean hardXPos = isNeighborHardConnection(world, x + 1, y, z, blockXPos, ForgeDirection.EAST)
            || blockXPos instanceof BlockTorch;

        boolean hardConnection = (flagsY & 4) != 0;
        boolean hardConnectionZNeg = hardConnection && (flagsZNeg & 4) != 0;
        boolean hardConnectionZPos = hardConnection && (flagsZPos & 4) != 0;
        boolean hardConnectionXNeg = hardConnection && (flagsXNeg & 4) != 0;
        boolean hardConnectionXPos = hardConnection && (flagsXPos & 4) != 0;

        boolean connectZNeg = (connectFlagsY == 0 && hardZNeg)
            || (blockZNeg instanceof BlockThinLog && !hardConnectionZNeg
                && (connectFlagsY != 3 || connectFlagsZNeg != 3));
        boolean connectZPos = (connectFlagsY == 0 && hardZPos)
            || (blockZPos instanceof BlockThinLog && !hardConnectionZPos
                && (connectFlagsY != 3 || connectFlagsZPos != 3));
        boolean connectXNeg = (connectFlagsY == 0 && hardXNeg)
            || (blockXNeg instanceof BlockThinLog && !hardConnectionXNeg
                && (connectFlagsY != 3 || connectFlagsXNeg != 3));
        boolean connectXPos = (connectFlagsY == 0 && hardXPos)
            || (blockXPos instanceof BlockThinLog && !hardConnectionXPos
                && (connectFlagsY != 3 || connectFlagsXPos != 3));

        boolean connectSide = connectZNeg | connectZPos | connectXNeg | connectXPos;
        if (!connectSide && (connectFlagsY & 1) == 0) {
            if (hardZNeg) connectZNeg = true;
            if (hardZPos) connectZPos = true;
            if (hardXNeg) connectXNeg = true;
            if (hardXPos) connectXPos = true;
        }

        if (!(connectZNeg | connectZPos | connectXNeg | connectXPos)) connectFlagsY = 3;

        if (connectFlagsY == 2 && hardZNeg) connectZNeg = true;
        if (connectFlagsY == 2 && hardZPos) connectZPos = true;
        if (connectFlagsY == 2 && hardXNeg) connectXNeg = true;
        if (connectFlagsY == 2 && hardXPos) connectXPos = true;

        return connectFlagsY | (connectZNeg ? 4 : 0)
            | (connectZPos ? 8 : 0)
            | (connectXNeg ? 16 : 0)
            | (connectXPos ? 32 : 0);
    }

    private int calcConnectYFlags(IBlockAccess world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (!(block instanceof BlockThinLog)) return 0;

        Block blockYNeg = world.getBlock(x, y - 1, z);
        boolean hardYNeg = isNeighborHardConnectionY(world, x, y - 1, z, blockYNeg, ForgeDirection.DOWN);
        boolean connectYNeg = hardYNeg || blockYNeg instanceof BlockThinLog;

        Block blockYPos = world.getBlock(x, y + 1, z);
        boolean hardYPos = isNeighborHardConnectionY(world, x, y + 1, z, blockYPos, ForgeDirection.UP);
        boolean connectYPos = hardYPos || blockYPos instanceof BlockThinLog || blockYPos instanceof BlockTorch;

        return (connectYNeg ? 1 : 0) | (connectYPos ? 2 : 0) | (hardYNeg ? 4 : 0) | (hardYPos ? 8 : 0);
    }

    private boolean isNeighborHardConnection(IBlockAccess world, int x, int y, int z, Block block,
        ForgeDirection side) {
        if (block.getMaterial()
            .isOpaque() && block.renderAsNormalBlock()) return true;

        if (block.isSideSolid(world, x, y, z, side.getOpposite())) return true;
        // if (block == ModBlocks.largePot)
        // return true;
        return false;
    }

    private boolean isNeighborHardConnectionY(IBlockAccess world, int x, int y, int z, Block block,
        ForgeDirection side) {
        if (isNeighborHardConnection(world, x, y, z, block, side)) return true;

        return block instanceof BlockThinLogFence;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List<ItemStack> list) {
        for (int i = 0; i < woodNames.length; i++) list.add(new ItemStack(this, 1, i));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
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

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        int orient = orientation;

        int ometa = 0;
        if (orient == 1) ometa |= 8;
        else if (orient == 2) ometa |= 4;
        else if (orient == 3) ometa |= 12;

        int meta2 = meta % 4 | ometa;

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

    public String[] getWoods() {
        return woodNames;
    }

    public String getMod() {
        return modName;
    }

    public boolean isStripped() {
        return woodStripped;
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    private final Vec3[] attachPoints = new Vec3[] { Vec3.createVectorHelper(.5, getMargin(), .5),
        Vec3.createVectorHelper(.5, 1 - getMargin(), .5), Vec3.createVectorHelper(.5, .5, getMargin()),
        Vec3.createVectorHelper(.5, .5, 1 - getMargin()), Vec3.createVectorHelper(getMargin(), .5, .5),
        Vec3.createVectorHelper(1 - getMargin(), .5, .5), };

    @Override
    public Vec3 getChainAttachPoint(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int connectFlags = calcConnectionFlags(blockAccess, x, y, z);

        switch (side) {
            case 0:
                return (connectFlags & 1) == 0 ? attachPoints[0] : null;
            case 1:
                return (connectFlags & 2) == 0 ? attachPoints[1] : null;
            case 2:
                return (connectFlags & 4) == 0 ? attachPoints[2] : null;
            case 3:
                return (connectFlags & 8) == 0 ? attachPoints[3] : null;
            case 4:
                return (connectFlags & 16) == 0 ? attachPoints[4] : null;
            case 5:
                return (connectFlags & 32) == 0 ? attachPoints[5] : null;
        }

        return null;
    }
}
