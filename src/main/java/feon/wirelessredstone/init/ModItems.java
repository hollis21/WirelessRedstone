package feon.wirelessredstone.init;

import feon.wirelessredstone.Main;
import feon.wirelessredstone.objects.items.LinkerItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Bus.MOD)
@ObjectHolder(Main.MODID)
public class ModItems extends EventBusSubscriberBase {

  public static final Item example_item = null;
  public static final Item special_item = null;
  public static final LinkerItem linker = null;

  // Tools
  public static final Item example_sword = null;
  public static final Item example_pickaxe = null;
  public static final Item example_shovel = null;
  public static final Item example_hoe = null;
  public static final Item example_axe = null;

  @SubscribeEvent()
  public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
    event.getRegistry().registerAll(
      setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "example_item"),
      setup(new SwordItem(ModItemTier.EXAMPLE, 7, 5.0F, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "example_sword"),
      setup(new PickaxeItem(ModItemTier.EXAMPLE, 3, 5.0F, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "example_pickaxe"),
      setup(new ShovelItem(ModItemTier.EXAMPLE, 3, 5.0F, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "example_shovel"),
      setup(new AxeItem(ModItemTier.EXAMPLE, 3, 5.0F, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "example_axe"),
      setup(new HoeItem(ModItemTier.EXAMPLE, 5.0F, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "example_hoe"),

      setup(new LinkerItem(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP).maxStackSize(1)), "linker")
    );
  }

  public enum ModItemTier implements IItemTier {
    EXAMPLE(3, 1500, 15.0F, 7.0F, 250, () -> {
      return Ingredient.fromItems(ModItems.example_item);
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    private ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability,
        Supplier<Ingredient> repairMaterial) {
      this.harvestLevel = harvestLevel;
      this.maxUses = maxUses;
      this.efficiency = efficiency;
      this.attackDamage = attackDamage;
      this.enchantability = enchantability;
      this.repairMaterial = new LazyValue<Ingredient>(repairMaterial);
    }

    @Override
    public int getMaxUses() {
      return this.maxUses;
    }

    @Override
    public float getEfficiency() {
      return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
      return this.attackDamage;
    }

    @Override
    public int getHarvestLevel() {
      return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
      return this.enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
      return this.repairMaterial.getValue();
    }
  }

}