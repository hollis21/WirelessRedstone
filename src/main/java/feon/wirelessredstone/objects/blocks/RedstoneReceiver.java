package feon.wirelessredstone.objects.blocks;

import feon.wirelessredstone.init.ModTileEntityTypes;
import feon.wirelessredstone.objects.items.LinkerItem;
import feon.wirelessredstone.tileentity.RedstoneReceiverTileEntity;
import feon.wirelessredstone.world.RedstoneNetwork;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
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

public class RedstoneReceiver extends HorizontalBlock {

  public RedstoneReceiver(Properties properties) {
   super(properties);
   this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
  }

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
      ItemStack stack = player.getHeldItem(handIn);
      // Is the user clicking on this with the linker?
      if (stack.getItem() instanceof LinkerItem) {
        LinkerItem linker = (LinkerItem)stack.getItem();
        TileEntity entity = worldIn.getTileEntity(pos);
        // Verify that the tile entity at this position is the receiver
        if (entity instanceof RedstoneReceiverTileEntity) {
          RedstoneReceiverTileEntity receiverTileEntity = (RedstoneReceiverTileEntity) entity;
          // get the linker's frequency
          Integer linkerFrequency = linker.getFrequency(stack);
          Integer receiverFrequency = receiverTileEntity.getFrequency();
          String message;

          if (linkerFrequency == null) {
            if (receiverFrequency == null) {
              // If neither have a frequency set, get a new frequency and set them both to it
              RedstoneNetwork network = RedstoneNetwork.getNetwork(worldIn);
              int newFrequency = network.getNewFrequency();
              network.save();
              
              linker.setFrequency(stack, newFrequency);
              receiverTileEntity.setFrequency(newFrequency);
              message = "Receiver and Linker set to frequency " + newFrequency + ".";
            } else {
              // If the linker doesn't have a frequency but the receiver does, set the linker to the value
              linker.setFrequency(stack, receiverFrequency);
              message = "Linker set to frequency " + receiverFrequency + ".";
            }
          } else {
            // If the linker has a frequency, set the receiver to it no matter what
            receiverTileEntity.setFrequency(linkerFrequency);
            message = "Receiver set to frequency " + linkerFrequency + ".";
          }
          
          player.sendStatusMessage(new StringTextComponent(message), false);
          return ActionResultType.SUCCESS;
        }
      }
    }
    return ActionResultType.PASS;
  }

  // Grabs the power from the tile entity
  @Override
  public int getWeakPower(final BlockState blockState, final IBlockReader blockAccess, final BlockPos pos, final Direction side) {
    TileEntity entity = blockAccess.getTileEntity(pos);
    if (blockState.getBlock() instanceof RedstoneReceiver && entity instanceof RedstoneReceiverTileEntity) {
      RedstoneReceiverTileEntity receiverTileEntity = (RedstoneReceiverTileEntity) entity;
      return receiverTileEntity.getPowerLevel();
    }
    return 0;
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

  // For breaking the block if the block's position becomes invalid
  @SuppressWarnings("deprecation")
  @Override
  public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
      BlockPos currentPos, BlockPos facingPos) {
    return facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
     builder.add(HORIZONTAL_FACING);
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return ModTileEntityTypes.REDSTONE_RECEIVER.get().create();
  }
}