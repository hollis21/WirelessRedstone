package feon.wirelessredstone.objects.items.capabilities;

import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.math.BlockPos;

public interface ITargetBlockPosition {
  public BlockPos getBlockPosition();
  public void setBlockPosition(BlockPos blockPos);
}