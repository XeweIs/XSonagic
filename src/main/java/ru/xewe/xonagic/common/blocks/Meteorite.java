package ru.xewe.xonagic.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.xewe.xonagic.common.data.ElementData;
import ru.xewe.xonagic.common.enums.ElementEnum;

public class Meteorite extends Block {
    private final AxisAlignedBB blockAABB = new AxisAlignedBB(0.77, 0.55, 0.77, 0.23, 0, 0.23);
    public Meteorite() {
        super(Material.ROCK);
        this.setRegistryName("meteorite");
        this.setUnlocalizedName("meteorite");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos position, IBlockState blockState, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
        ElementData.setElement(player, ElementEnum.Air);
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess source, BlockPos position) {
        return blockAABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
