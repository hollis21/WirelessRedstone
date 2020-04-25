package feon.wirelessredstone.objects.items.capabilities.targetblock;

import net.minecraft.util.math.BlockPos;

public interface ITargetBlockPosition {
  public BlockPos getBlockPosition();
  public void setBlockPosition(BlockPos blockPos);
}