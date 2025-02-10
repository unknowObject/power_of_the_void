package org.yunz21.powerofthevoid.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.yunz21.powerofthevoid.PowerOfTheVoid;
import org.yunz21.powerofthevoid.capabilities.BatteryCapability;
import org.yunz21.powerofthevoid.capabilities.BatteryCapabilityProvider;


@Mod.EventBusSubscriber(modid = PowerOfTheVoid.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VCreativeTabRegistry {

    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PowerOfTheVoid.MODID);

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }

    public static final RegistryObject<CreativeModeTab> POWER_OF_THE_VOID = TABS.register("power_of_the_void", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + PowerOfTheVoid.MODID + ".item_tab"))
            .icon(() -> new ItemStack(VItemRegistry.DRIPPY.get()))
            .displayItems((enabledFeatures, entries) -> {
                entries.accept(VItemRegistry.DRIPPY.get());
            })
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .build());


//    @SubscribeEvent
//    public static void fillCreativeTabs(final BuildCreativeModeTabContentsEvent event) {
//        if (event.getTab() == BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
//            event.accept(ItemRegistry.INSCRIPTION_TABLE_BLOCK_ITEM.get());
//            event.accept(ItemRegistry.SCROLL_FORGE_BLOCK.get());
//            event.accept(ItemRegistry.ACANE_ANVIL_BLOCK_ITEM.get());
//            event.accept(ItemRegistry.PEDESTAL_BLOCK_ITEM.get());
//            event.accept(ItemRegistry.ARMOR_PILE_BLOCK_ITEM.get());
//            event.accept(ItemRegistry.ALCHEMIST_CAULDRON_BLOCK_ITEM.get());
//            event.accept(ItemRegistry.FIREFLY_JAR_ITEM.get());
//        }
//
//        if (event.getTab() == CreativeModeTabs.searchTab() || event.getTab() == SCROLLS_TAB.get()) {
//            SpellRegistry.getEnabledSpells().stream()
//                    .filter(spellType -> spellType != SpellRegistry.none())
//                    .forEach(spell -> {
//                        for (int i = spell.getMinLevel(); i <= spell.getMaxLevel(); i++) {
//                            var itemstack = new ItemStack(ItemRegistry.SCROLL.get());
//                            var spellList = ISpellContainer.createScrollContainer(spell, i, itemstack);
//                            spellList.save(itemstack);
//                            event.accept(itemstack);
//                        }
//                    });
//        }
//
//        if (event.getTab() == BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.NATURAL_BLOCKS)) {
//            event.accept(ItemRegistry.ARCANE_DEBRIS_BLOCK_ITEM.get());
//        }
//    }
}
