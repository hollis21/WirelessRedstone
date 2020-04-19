package feon.wirelessredstone.objects.items;

import java.util.List;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class LinkerItem extends Item {

  public LinkerItem(Properties properties) {
    super(properties);
  }

  @Override
  public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    if (Screen.hasShiftDown()) {
      tooltip.add(new StringTextComponent("Test Information"));
      tooltip.add(new StringTextComponent("More Information"));
    } else {
      tooltip.add(new StringTextComponent("A little test Information"));
    }
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    // if (!worldIn.isRemote) {
    //   Main.LOGGER.info("!worldIn.isRemote");
    //   playerIn.getHeldItem(handIn)
    //     .getStack()
    //     .getCapability(TargetBlockCapability.TARGET_BLOCK_CAPABILITY)
    //     .ifPresent(tbp -> {
    //       if (Screen.hasShiftDown()) {
    //         Main.LOGGER.info("!worldIn.isRemote - Screen.hasShiftDown()");

    //         BlockPos pos = playerIn.getPosition();
    //         playerIn.sendStatusMessage(new StringTextComponent(
    //             String.format("Setting Linker Position - x:%1$s y:%2$s z:%3$s", pos.getX(), pos.getY(), pos.getZ())), false);
    //         tbp.setBlockPosition(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
    //       } else {
    //         Main.LOGGER.info("!worldIn.isRemote - !Screen.hasShiftDown()");
    //         BlockPos pos = tbp.getBlockPosition();
    //         String message = "Linker Position not set";
    //         if (pos != null) {
    //           message = String.format("Linker Position - x:%1$s y:%2$s z:%3$s", pos.getX(), pos.getY(), pos.getZ());
    //         }
            
    //         playerIn.sendStatusMessage(new StringTextComponent(message), false);
    //       }
    //     });
    // } else {
    //   Main.LOGGER.info("worldIn.isRemote");
    // }
    return super.onItemRightClick(worldIn, playerIn, handIn);
  }
}