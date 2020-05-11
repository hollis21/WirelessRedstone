package feon.wirelessredstone.objects.items;

import java.util.List;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
    Integer frequency = this.getFrequency(stack);
    String message = frequency == null ? "No frequency set. <Hold shift for usage>" : ("Frequency: " + frequency + ". <Hold shift for usage>");
    tooltip.add(new StringTextComponent(message));

    if (Screen.hasShiftDown()) {
      if (frequency == null) {
        tooltip.add(new StringTextComponent("Right click on a Redstone Reciever or Transmitter to set the Linker's frequency."));
      } else {
        tooltip.add(new StringTextComponent("Right click on a Redstone Reciever or Transmitter to set their frequency to " + frequency));
        tooltip.add(new StringTextComponent("Sneak + Right Click to clear Linker's frequency."));
      }
    } 
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }
  private static final String FREQUENCY_KEY = "Frequency";

  public Integer getFrequency(ItemStack stack) {
    CompoundNBT tag = stack.getTag();
    if (tag == null || !tag.contains(FREQUENCY_KEY)) {
      return null;
    }
    return tag.getInt(FREQUENCY_KEY);
  }

  public void setFrequency(ItemStack stack, int frequency) {
    CompoundNBT tag = stack.getOrCreateTag();
    tag.remove(FREQUENCY_KEY);
    tag.putInt(FREQUENCY_KEY, frequency);
  }

  public void clearFrequency(ItemStack stack) {
    CompoundNBT tag = stack.getTag();
    if (tag == null || !tag.contains(FREQUENCY_KEY)) {
      return;
    }
    tag.remove(FREQUENCY_KEY);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    if (!worldIn.isRemote) {
      ItemStack stack = playerIn.getHeldItem(handIn);
      if (stack.getItem() instanceof LinkerItem) {
        LinkerItem linker = (LinkerItem)stack.getItem();
        
        if (playerIn.isSneaking() && linker.getFrequency(stack) != null) {
          linker.clearFrequency(stack);
          playerIn.sendStatusMessage(new StringTextComponent("Linker frequency cleared!"), false);
          return ActionResult.resultSuccess(stack);
        }
      }
    }

    return super.onItemRightClick(worldIn, playerIn, handIn);
  }
}