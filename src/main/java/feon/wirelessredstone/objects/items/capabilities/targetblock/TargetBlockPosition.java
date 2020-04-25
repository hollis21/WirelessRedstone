package feon.wirelessredstone.objects.items.capabilities.targetblock;

import net.minecraft.util.math.BlockPos;

public class TargetBlockPosition implements ITargetBlockPosition {

  private BlockPos blockPos = null;
  @Override
  public BlockPos getBlockPosition() {
    return this.blockPos;
  }

  @Override
  public void setBlockPosition(BlockPos blockPos) {
    this.blockPos = blockPos;
  }

}