package feon.wirelessredstone.objects.blocks;

import feon.wirelessredstone.init.ModTileEntityTypes;
import feon.wirelessredstone.objects.items.LinkerItem;
import feon.wirelessredstone.tileentity.RedstoneTransmitterTileEntity;
import feon.wirelessredstone.world.RedstoneNetwork;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RedstoneTransmitter extends HorizontalBlock {

  public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

  public RedstoneTransmitter(final Properties properties) {
    super(properties);
    this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(POWERED, false));
  }

  protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

  // This block is a 2 voxel tall slab-like shape.
  @Override
  public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos,
      final ISelectionContext context) {
    return SHAPE;
  }

  // This block can only be placed on top of blocks with a solid top.
  @Override
  public boolean isValidPosition(final BlockState state, final IWorldReader worldIn, final BlockPos pos) {
    return hasSolidSideOnTop(worldIn, pos.down());
  }

  @Override
  public ActionResultType onBlockActivated(BlockState state, final World worldIn, final BlockPos pos,
      final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
    if (!worldIn.isRemote) {

      TileEntity entity = worldIn.getTileEntity(pos);
      // Verify that the tile entity at this position is the transmitter
      if (entity instanceof RedstoneTransmitterTileEntity) {
        RedstoneTransmitterTileEntity transmitterTileEntity = (RedstoneTransmitterTileEntity) entity;
        Integer transmitterFrequency = transmitterTileEntity.getFrequency();

        ItemStack stack = player.getHeldItem(handIn);

        if (stack.isEmpty() && handIn == Hand.MAIN_HAND) {
          String message = "Transmitter frequency: " + transmitterFrequency + ".";
          if (transmitterFrequency == null) {
            message = "Frequency not set";
          }
          player.sendStatusMessage(new StringTextComponent(message), false);
        }

        // Is the user clicking on this with the linker?
        if (stack.getItem() instanceof LinkerItem) {
          LinkerItem linker = (LinkerItem) stack.getItem();

          // get the linker's frequency
          Integer linkerFrequency = linker.getFrequency(stack);
          String message;

          if (linkerFrequency == null) {
            if (transmitterFrequency == null) {
              // If neither have a frequency set, get a new frequency and set them both to it
              RedstoneNetwork network = RedstoneNetwork.getNetwork(worldIn);
              int newFrequency = network.getNewFrequency();
              network.save();

              linker.setFrequency(stack, newFrequency);
              transmitterTileEntity.setFrequency(newFrequency);
              message = "Transmitter and Linker set to frequency " + newFrequency + ".";
            } else {
              // If the linker doesn't have a frequency but the transmitter does, set the
              // linker to the value
              linker.setFrequency(stack, transmitterFrequency);
              message = "Linker set to frequency " + transmitterFrequency + ".";
            }
          } else {
            // If the linker has a frequency, set the transmitter to it no matter what
            transmitterTileEntity.setFrequency(linkerFrequency);
            message = "Transmitter set to frequency " + linkerFrequency + ".";
          }

          player.sendStatusMessage(new StringTextComponent(message), false);
          return ActionResultType.SUCCESS;
        }
      }
    }
    return ActionResultType.PASS;
  }

  // Allows redstone wire to connect to it
  @Override
  public boolean canProvidePower(final BlockState state) {
    return true;
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
      BlockPos currentPos, BlockPos facingPos) {
    return facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState()
        : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
  }

  // neighborChanged is called when the power of neighbors changes (duh).
  @Override
  public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
      boolean isMoving) {
    if (!worldIn.isRemote) {
      TileEntity entity = worldIn.getTileEntity(pos);
      if (entity instanceof RedstoneTransmitterTileEntity) {
        RedstoneTransmitterTileEntity transmitterTileEntity = (RedstoneTransmitterTileEntity) entity;
        transmitterTileEntity.setPowerLevel(getPower(worldIn, pos));
      }
    }
  }

  private int getPower(World worldIn, BlockPos pos) {
    int max = 0;

    for (Direction direction : Direction.values()) {
      // We ignore the power from blocks above the transmitter
      if (direction != Direction.UP) {
        int val = worldIn.getRedstonePower(pos.offset(direction), direction);
        if (val < 15) {
          BlockState blockState = worldIn.getBlockState(pos.offset(direction));
          Block block = blockState.getBlock();
          // Checking for redstone wire since bends in the wire the value isn't
          // correct when using world.getRedstonePower so we'll get the power
          // from the wire's state.
          // Not pretty but it's how redstone repeaters do it.
          // Credit to https://github.com/McJty for this algorithm, I probably would have
          // torn my hair
          // out trying to figure out what was going on.
          if (block == Blocks.REDSTONE_WIRE) {
            val = Math.max(val, blockState.get(RedstoneWireBlock.POWER));
          }
        }
        if (val > max) {
          max = val;
        }
      }
    }
    return max;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return ModTileEntityTypes.REDSTONE_TRANSMITTER.get().create();
  }

  @Override
  protected void fillStateContainer(final StateContainer.Builder<Block, BlockState> builder) {
    builder.add(HORIZONTAL_FACING, POWERED);
  }

}