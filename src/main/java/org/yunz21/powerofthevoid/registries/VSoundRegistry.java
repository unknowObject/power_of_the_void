package org.yunz21.powerofthevoid.registries;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.yunz21.powerofthevoid.PowerOfTheVoid;

@Mod.EventBusSubscriber(modid = PowerOfTheVoid.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VSoundRegistry {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PowerOfTheVoid.MODID);

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    public static RegistryObject<SoundEvent> THERMAL_SUNDER_ICE = registerSoundEvent("thermal_sunder_ice");
    public static RegistryObject<SoundEvent> THERMAL_SUNDER_FIRE = registerSoundEvent("thermal_sunder_fire");
    public static RegistryObject<SoundEvent> REDLINE_START = registerSoundEvent("redline_start");
    public static RegistryObject<SoundEvent> REDLINE_END = registerSoundEvent("redline_end");
    public static RegistryObject<SoundEvent> MESMER_SKIN_START = registerSoundEvent("mesmer_skin_start");
    public static RegistryObject<SoundEvent> MESMER_SKIN_END = registerSoundEvent("mesmer_skin_end");
    public static RegistryObject<SoundEvent> BLOODLETTING = registerSoundEvent("bloodletting");
    public static RegistryObject<SoundEvent> ROAR = registerSoundEvent("roar");
    public static RegistryObject<SoundEvent> MACH_RUSH = registerSoundEvent("mach_rush");
    public static RegistryObject<SoundEvent> KINETIC_PLATING = registerSoundEvent("kinetic_plating");


    public static RegistryObject<SoundEvent> HIT_WALL = registerSoundEvent("hit_wall");
    public static RegistryObject<SoundEvent> FULL_CHARGE = registerSoundEvent("full_charge");
    public static RegistryObject<SoundEvent> REDLINE_EFFECT_END = registerSoundEvent("redline_effect_end");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(PowerOfTheVoid.MODID, name)));
    }
}
