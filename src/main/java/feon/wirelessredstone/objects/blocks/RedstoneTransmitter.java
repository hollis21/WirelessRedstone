package feon.wirelessredstone.objects.blocks;

import feon.wirelessredstone.Main;
import feon.wirelessredstone.objects.items.LinkerItem;
import feon.wirelessredstone.objects.items.capabilities.TargetBlockCapability;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RedstoneTransmitter extends HorizontalBlock {

  public RedstoneTransmitter(Properties properties) {
    super(properties);
    this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(POWERED,
        Boolean.valueOf(true)));
  }

  public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

  protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

  // This block is a 2 voxel tall slab-like shape.
  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return SHAPE;
  }

  // This block can only be placed on top of blocks with a solid top.
  @Override
  public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
    return hasSolidSideOnTop(worldIn, pos.down());
  }

  @Override
  public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
      Hand handIn, BlockRayTraceResult hit) {
    // super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    if (!worldIn.isRemote) {
      doStuff("!isRemote", pos, player, handIn);
      boolean currPowered = state.get(POWERED);
      log("onBlockActivated", "Setting POWERED to " + (!currPowered));
      BlockState newState = state.with(POWERED, Boolean.valueOf(!currPowered));
      log("onBlockActivated", "POWERED set to " + (newState.get(POWERED)));
      log("onBlockActivated", "Setting Block State");
      worldIn.setBlockState(pos, newState, 2);
      log("onBlockActivated", "Block State set");
      return ActionResultType.SUCCESS;
    } else {
      doStuff("isRemote", pos, player, handIn);
      return ActionResultType.SUCCESS;
    }
  }

  // Breaks block if position is no longer valid (ie the block under it broke)
  @Override
  public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
      boolean isMoving) {
    if (!state.isValidPosition(worldIn, pos)) {
      TileEntity tileentity = state.hasTileEntity() ? worldIn.getTileEntity(pos) : null;
      spawnDrops(state, worldIn, pos, tileentity);
      worldIn.removeBlock(pos, false);

      for (Direction direction : Direction.values()) {
        worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
      }
    }
  }

  // Allows redstone wire to connect to it
  @Override
  public boolean canProvidePower(BlockState state) {
    return true;
  }

  @Override
  public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
    log("getWeakPower", String.format("Entered for pos %1$s %2$s %3$s, side %4$s", pos.getX(), pos.getY(), pos.getZ(), side.toString()));
    int result = super.getWeakPower(blockState, blockAccess, pos, side);
    log("getWeakPower", "Exiting, returning " + result);
    return result;
  }

  @Override
  public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
    log("getStrongPower", "Entered, POWERED is " + blockState.get(POWERED));
    int result = blockState.get(POWERED) ? 15 : 0;
    log("getStrongPower", "Exiting, returning " + result);
    return result;
  }

  private void doStuff(String prefix, BlockPos pos, PlayerEntity player, Hand handIn) {
    Main.LOGGER.info(prefix);
    if (player.getHeldItem(handIn).getItem() instanceof LinkerItem) {
      Main.LOGGER.info(prefix + " - Holding LinkerItem");
      player.getHeldItem(handIn).getCapability(TargetBlockCapability.TARGET_BLOCK_CAPABILITY).ifPresent(cap -> {
        Main.LOGGER.info(prefix + " - cap present");
        BlockPos currentPos = cap.getBlockPosition();
        String message = prefix + "Linker position not set";
        if (currentPos != null) {
          message = String.format("Old Linker Position - x:%1$s y:%2$s z:%3$s", pos.getX(), pos.getY(), pos.getZ());
        }
        Main.LOGGER.info(message);
      });
    } else {
      Main.LOGGER.info(prefix + " - Not holding LinkerItem");
    }
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(HORIZONTAL_FACING, POWERED);
  }

  private String className = "RedstoneTransmitter";

  private void log(String methodName, String message) {
    Main.LOGGER.info(className + "." + methodName + ":" + message);
  }
}