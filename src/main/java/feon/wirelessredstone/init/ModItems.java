package feon.wirelessredstone.init;

import feon.wirelessredstone.Main;
import feon.wirelessredstone.objects.items.LinkerItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Bus.MOD)
@ObjectHolder(Main.MODID)
public class ModItems extends EventBusSubscriberBase {

  public static final LinkerItem linker = null;

  @SubscribeEvent()
  public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
    event.getRegistry().registerAll(
      setup(new LinkerItem(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP).maxStackSize(1)), "linker")
    );
  }
}