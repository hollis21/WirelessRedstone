package feon.wirelessredstone.objects.blocks;

import feon.wirelessredstone.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RedstoneReceiver extends RedstoneDiodeBlock {

  public RedstoneReceiver(Properties properties) {
    super(properties);
    this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(POWERED, Boolean.valueOf(false)));
  }

  @Override
  protected int getDelay(BlockState blockState) {
    return 2;
  }
  
  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
     builder.add(HORIZONTAL_FACING, POWERED);
  }

  @Override
  public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
    Main.LOGGER.info("neighborChanged");
    if (state.isValidPosition(worldIn, pos)) {
       this.updateState(worldIn, pos, state);
    } else {
       Main.LOGGER.info("!state.isValidPosition");
       TileEntity tileentity = state.hasTileEntity() ? worldIn.getTileEntity(pos) : null;
       spawnDrops(state, worldIn, pos, tileentity);
       worldIn.removeBlock(pos, false);

       for(Direction direction : Direction.values()) {
          worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
       }

       Main.LOGGER.info("End !state.isValidPosition");
    }
    Main.LOGGER.info("End neighborChanged");
 }

}