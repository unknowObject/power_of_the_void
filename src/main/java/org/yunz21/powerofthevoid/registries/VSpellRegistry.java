package org.yunz21.powerofthevoid.registries;


import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.yunz21.powerofthevoid.PowerOfTheVoid;
import org.yunz21.powerofthevoid.spells.orokin.*;


public class VSpellRegistry {
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

    public static final RegistryObject<AbstractSpell> THERMAL_THUNDER_ICE_SPELL = registerSpell(new ThermalSunderIceSpell());
    public static final RegistryObject<AbstractSpell> THERMAL_THUNDER_FIRE_SPELL = registerSpell(new ThermalSunderFireSpell());
    public static final RegistryObject<AbstractSpell> THERMAL_THUNDER_NEW_SPELL = registerSpell(new ThermalSunderNewSpell());



}