package feon.wirelessredstone.events;

import feon.wirelessredstone.Main;
import feon.wirelessredstone.objects.items.LinkerItem;
import feon.wirelessredstone.objects.items.capabilities.TargetBlockCapability;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Bus.FORGE)
public class CapabilityAttachHandlers {

  @SubscribeEvent
  public static void CapabilityAttachItemStack(AttachCapabilitiesEvent<ItemStack> event){
      if(event.getObject().getItem() instanceof LinkerItem){
          event.addCapability(new ResourceLocation(Main.MODID, "targetblockposition"), new TargetBlockCapability());
      }
  }
}