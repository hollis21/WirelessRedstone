package feon.wirelessredstone.init;

import feon.wirelessredstone.Main;
import feon.wirelessredstone.objects.items.LinkerItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
  public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Main.MODID);

  public static final RegistryObject<Item> LINKER = ITEMS.register("linker", () -> new LinkerItem(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP).maxStackSize(1)));

  public static final RegistryObject<Item> REDSTONE_TRANSMITTER_ITEM = ITEMS.register("redstone_transmitter", () -> new BlockItem(ModBlocks.REDSTONE_TRANSMITTER.get(), new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
  public static final RegistryObject<Item> REDSTONE_RECEIVER_ITEM = ITEMS.register("redstone_receiver", () -> new BlockItem(ModBlocks.REDSTONE_RECEIVER.get(), new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
}