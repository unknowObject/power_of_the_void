package org.yunz21.powerofthevoid;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import org.yunz21.powerofthevoid.registries.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PowerOfTheVoid.MODID)
public class PowerOfTheVoid {

    public static final String MODID = "power_of_the_void";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final ResourceKey<CreativeModeTab> POWER_OF_THE_VOID = createKey("power_of_the_void");

    public PowerOfTheVoid() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Event Handlers
        MinecraftForge.EVENT_BUS.register(this);
        // Items
        VItemRegistry.register(modEventBus);
        // Schools
        VSchoolRegistry.register(modEventBus);
        // Attributes
        VAttributeRegistry.register(modEventBus);
        // Effects
        VMobEffectRegistry.register(modEventBus);
        // Entities
        VEntityRegistry.register(modEventBus);
        // Spells
        VSpellRegistry.register(modEventBus);
        // Creative tabs
        VCreativeTabRegistry.register(modEventBus);
        // Sound
        VSoundRegistry.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        // Rendering armor
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.AddLayers event)
        {
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
    }

    private static ResourceKey<CreativeModeTab> createKey(String key) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(key));
    }

    public static ResourceLocation id(@NotNull String path) {
        return new ResourceLocation(PowerOfTheVoid.MODID, path);
    }
}