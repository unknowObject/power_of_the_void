package org.yunz21.powerofthevoid.registries;


import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.attribute.MagicRangedAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.yunz21.powerofthevoid.PowerOfTheVoid;


@Mod.EventBusSubscriber(modid = PowerOfTheVoid.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VAttributeRegistry {

    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, PowerOfTheVoid.MODID);

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

//    public static final RegistryObject<Attribute> OROKIN_MAGIC_RESIST = newResistanceAttribute("orokin");
//    public static final RegistryObject<Attribute> OROKIN_SPELL_POWER = newPowerAttribute("orokin");

//    @SubscribeEvent
//    public static void modifyEntityAttributes(EntityAttributeModificationEvent e) {
//        e.getTypes().forEach(
//                entity -> ATTRIBUTES.getEntries().forEach(attribute -> e.add(entity, attribute.get())));
//    }
//
//    private static RegistryObject<Attribute> newResistanceAttribute(String id) {
//        return ATTRIBUTES.register(id + "_magic_resist", () -> (new MagicRangedAttribute("attribute.power_of_the_void." + id + "_magic_resist", 1.0D, -100, 100).setSyncable(true)));
//    }
//
//    private static RegistryObject<Attribute> newPowerAttribute(String id) {
//        return ATTRIBUTES.register(id + "_spell_power", () -> (new MagicRangedAttribute("attribute.power_of_the_void." + id + "_spell_power", 1.0D, -100, 100).setSyncable(true)));
//    }
}
