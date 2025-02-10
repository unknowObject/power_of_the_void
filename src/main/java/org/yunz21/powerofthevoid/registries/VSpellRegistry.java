package org.yunz21.powerofthevoid.registries;


import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.yunz21.powerofthevoid.PowerOfTheVoid;
import org.yunz21.powerofthevoid.spells.orokin.*;

@Mod.EventBusSubscriber(modid = PowerOfTheVoid.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VSpellRegistry {
    //public static final ResourceKey<Registry<AbstractSpell>> SPELL_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(PowerOfTheVoid.MODID, "spells"));

    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, PowerOfTheVoid.MODID);

    public static void register(IEventBus eventBus) {
        SPELLS.register(eventBus);
    }

    public static RegistryObject<AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }



    public static final RegistryObject<AbstractSpell> THERMAL_THUNDER_SPELL = registerSpell(new ThermalSunderSpell());
    public static final RegistryObject<AbstractSpell> REDLINE_SPELL = registerSpell(new RedlineSpell());
    public static final RegistryObject<AbstractSpell> MESMER_SKIN_SPELL = registerSpell(new MesmerSkinSpell());
    public static final RegistryObject<AbstractSpell> BLOODLETTING_SPELL = registerSpell(new BloodlettingSpell());
    public static final RegistryObject<AbstractSpell> ROAR_SPELL = registerSpell(new RoarSpell());
    public static final RegistryObject<AbstractSpell> MACH_RUSH_SPELL = registerSpell(new MachRushSpell());
    public static final RegistryObject<AbstractSpell> KINETIC_PLATING_SPELL = registerSpell(new KineticPlatingSpell());

    public static final RegistryObject<AbstractSpell> THERMAL_THUNDER_ICE_SPELL = registerSpell(new ThermalSunderIceSpell());
    public static final RegistryObject<AbstractSpell> THERMAL_THUNDER_FIRE_SPELL = registerSpell(new ThermalSunderFireSpell());
    public static final RegistryObject<AbstractSpell> THERMAL_THUNDER_NEW_SPELL = registerSpell(new ThermalSunderNewSpell());



}