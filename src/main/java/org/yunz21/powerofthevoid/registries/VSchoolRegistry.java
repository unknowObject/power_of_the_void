package org.yunz21.powerofthevoid.registries;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.ChatFormatting;
import org.yunz21.powerofthevoid.PowerOfTheVoid;
import org.yunz21.powerofthevoid.util.VTags;

import static io.redspace.ironsspellbooks.api.registry.SchoolRegistry.*;

@Mod.EventBusSubscriber(modid = PowerOfTheVoid.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VSchoolRegistry {
    public static final ResourceLocation OROKIN_RESOURCE = PowerOfTheVoid.id("orokin");
    private static final DeferredRegister<SchoolType> OROKIN_SCHOOLS = DeferredRegister.create(SCHOOL_REGISTRY_KEY, PowerOfTheVoid.MODID);

    public static void register(IEventBus eventBus)
    {
        OROKIN_SCHOOLS.register(eventBus);
    }

    private static RegistryObject<SchoolType> registerSchool(SchoolType type)
    {
        return OROKIN_SCHOOLS.register(type.getId().getPath(), () -> type);
    }


    public static final RegistryObject<SchoolType> OROKIN = registerSchool(new SchoolType(
            OROKIN_RESOURCE,
            VTags.OROKIN_FOCUS,
            Component.translatable("school.power_of_the_void.orokin").withStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)),
            LazyOptional.of(AttributeRegistry.ELDRITCH_SPELL_POWER::get),
            LazyOptional.of(AttributeRegistry.ELDRITCH_MAGIC_RESIST::get),
            LazyOptional.of(SoundRegistry.BLOOD_EXPLOSION::get),
            ISSDamageTypes.ELDRITCH_MAGIC
    ));
}
