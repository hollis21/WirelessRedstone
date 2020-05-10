package feon.wirelessredstone.objects.blocks;

import feon.wirelessredstone.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RedstoneTransmitter extends HorizontalBlock {

  public RedstoneTransmitter(final Properties properties) {
    super(properties);
    this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(POWERED,
        Boolean.valueOf(true)));
  }

  public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

  protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

  // This block is a 2 voxel tall slab-like shape.
  @Override
  public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos, final ISelectionContext context) {
    return SHAPE;
  }

  // This block can only be placed on top of blocks with a solid top.
  @Override
  public boolean isValidPosition(final BlockState state, final IWorldReader worldIn, final BlockPos pos) {
    return hasSolidSideOnTop(worldIn, pos.down());
  }

  @Override
  public ActionResultType onBlockActivated(BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player,
      final Hand handIn, final BlockRayTraceResult hit) {
    if (!worldIn.isRemote) {
      state = state.cycle(POWERED);
      worldIn.setBlockState(pos, state, 3);
      this.updateNeighbors(state, worldIn, pos);
    }
    return ActionResultType.SUCCESS;
  }

  @SuppressWarnings("deprecation")
  @Override
  public void onReplaced(final BlockState state, final World worldIn, final BlockPos pos, final BlockState newState, final boolean isMoving) {
    if (!isMoving && state.getBlock() != newState.getBlock()) {
      if (state.get(POWERED)) {
        this.updateNeighbors(state, worldIn, pos);
      }

      super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
  }

  @Override
  public int getWeakPower(final BlockState blockState, final IBlockReader blockAccess, final BlockPos pos, final Direction side) {
    return blockState.get(POWERED) ? 15 : 0;
  }

  @Override
  public int getStrongPower(final BlockState blockState, final IBlockReader blockAccess, final BlockPos pos, final Direction side) {
    return 0;
  }

  // Allows redstone wire to connect to it
  @Override
  public boolean canProvidePower(final BlockState state) {
    return true;
  }

  private void updateNeighbors(final BlockState blockState, final World worldIn, final BlockPos pos) {
    log("updateNeightbors", "Entered");
    worldIn.notifyNeighborsOfStateChange(pos, this);
    log("updateNeightbors", "Exit");
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
      BlockPos currentPos, BlockPos facingPos) {
    return facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
  }

  // private void doStuff(String prefix, BlockPos pos, PlayerEntity player, Hand
  // handIn) {
  // Main.LOGGER.info(prefix);
  // if (player.getHeldItem(handIn).getItem() instanceof LinkerItem) {
  // Main.LOGGER.info(prefix + " - Holding LinkerItem");
  // player.getHeldItem(handIn).getCapability(TargetBlockCapability.TARGET_BLOCK_CAPABILITY).ifPresent(cap
  // -> {
  // Main.LOGGER.info(prefix + " - cap present");
  // BlockPos currentPos = cap.getBlockPosition();
  // String message = prefix + "Linker position not set";
  // if (currentPos != null) {
  // message = String.format("Old Linker Position - x:%1$s y:%2$s z:%3$s",
  // pos.getX(), pos.getY(), pos.getZ());
  // }
  // Main.LOGGER.info(message);
  // });
  // } else {
  // Main.LOGGER.info(prefix + " - Not holding LinkerItem");
  // }
  // }

  @Override
  protected void fillStateContainer(final StateContainer.Builder<Block, BlockState> builder) {
    builder.add(HORIZONTAL_FACING, POWERED);
  }

  private final String className = "RedstoneTransmitter";

  private void log(final String methodName, final String message) {
    Main.LOGGER.info(className + "." + methodName + ":" + message);
  }
}