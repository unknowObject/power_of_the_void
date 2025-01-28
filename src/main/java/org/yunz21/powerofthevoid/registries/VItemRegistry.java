package org.yunz21.powerofthevoid.registries;


import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.yunz21.powerofthevoid.PowerOfTheVoid;

import java.util.Collection;

public class VItemRegistry {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PowerOfTheVoid.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    //public static final RegistryObject<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBook::new);
    /**
     * Spell items
     */
//    public static final RegistryObject<Item> WIMPY_SPELL_BOOK = ITEMS.register("wimpy_spell_book", () -> new SpellBook(0, SpellRarity.LEGENDARY, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
//    public static final RegistryObject<Item> LEGENDARY_SPELL_BOOK = ITEMS.register("legendary_spell_book", () -> new SpellBook(12, SpellRarity.LEGENDARY, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
//    public static final RegistryObject<Item> NETHERITE_SPELL_BOOK = ITEMS.register("netherite_spell_book", () -> new SimpleAttributeSpellBook(12, SpellRarity.LEGENDARY, AttributeRegistry.COOLDOWN_REDUCTION.get(), .20));
//    public static final RegistryObject<Item> DIAMOND_SPELL_BOOK = ITEMS.register("diamond_spell_book", () -> new SpellBook(10, SpellRarity.EPIC));
//    public static final RegistryObject<Item> GOLD_SPELL_BOOK = ITEMS.register("gold_spell_book", () -> new SimpleAttributeSpellBook(8, SpellRarity.RARE, AttributeRegistry.CAST_TIME_REDUCTION.get(), .15));
//    public static final RegistryObject<Item> IRON_SPELL_BOOK = ITEMS.register("iron_spell_book", () -> new SpellBook(6, SpellRarity.UNCOMMON));
//    public static final RegistryObject<Item> COPPER_SPELL_BOOK = ITEMS.register("copper_spell_book", () -> new SpellBook(5, SpellRarity.COMMON));
//    public static final RegistryObject<Item> ROTTEN_SPELL_BOOK = ITEMS.register("rotten_spell_book", () -> new SimpleAttributeSpellBook(8, SpellRarity.RARE, AttributeRegistry.SPELL_RESIST.get(), -.15));
//    public static final RegistryObject<Item> BLAZE_SPELL_BOOK = ITEMS.register("blaze_spell_book", () -> new SimpleAttributeSpellBook(10, SpellRarity.LEGENDARY, AttributeRegistry.FIRE_SPELL_POWER.get(), .10));
//    public static final RegistryObject<Item> DRAGONSKIN_SPELL_BOOK = ITEMS.register("dragonskin_spell_book", () -> new SimpleAttributeSpellBook(12, SpellRarity.LEGENDARY, AttributeRegistry.ENDER_SPELL_POWER.get(), .10));
//    public static final RegistryObject<Item> DRUIDIC_SPELL_BOOK = ITEMS.register("druidic_spell_book", () -> new SimpleAttributeSpellBook(10, SpellRarity.LEGENDARY, AttributeRegistry.NATURE_SPELL_POWER.get(), .10));
//    public static final RegistryObject<Item> VILLAGER_SPELL_BOOK = ITEMS.register("villager_spell_book", () -> {
//        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
//        builder.put(AttributeRegistry.HOLY_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .08, AttributeModifier.Operation.MULTIPLY_BASE));
//        builder.put(AttributeRegistry.CAST_TIME_REDUCTION.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .08, AttributeModifier.Operation.MULTIPLY_BASE));
//        return new SimpleAttributeSpellBook(10, SpellRarity.LEGENDARY, builder.build());
//    });
//    public static final RegistryObject<Item> GRAYBEARD_STAFF = ITEMS.register("graybeard_staff", () -> new StaffItem(ItemPropertiesHelper.equipment().stacksTo(1), 2, -3, Map.of(AttributeRegistry.MANA_REGEN.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE), AttributeRegistry.SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .10, AttributeModifier.Operation.MULTIPLY_BASE))));
//    public static final RegistryObject<Item> ARTIFICER_STAFF = ITEMS.register("artificer_cane", () -> new StaffItem(ItemPropertiesHelper.equipment().stacksTo(1), 3, -3, Map.of(AttributeRegistry.CAST_TIME_REDUCTION.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .15, AttributeModifier.Operation.MULTIPLY_BASE), AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .15, AttributeModifier.Operation.MULTIPLY_BASE), AttributeRegistry.SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .10, AttributeModifier.Operation.MULTIPLY_BASE))));
//    public static final RegistryObject<Item> ICE_STAFF = ITEMS.register("ice_staff", () -> new StaffItem(ItemPropertiesHelper.equipment(1).rarity(Rarity.RARE), 3, -3, Map.of(AttributeRegistry.MANA_REGEN.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE), AttributeRegistry.ICE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .15, AttributeModifier.Operation.MULTIPLY_BASE), AttributeRegistry.SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .05, AttributeModifier.Operation.MULTIPLY_BASE))));
//
//    public static final RegistryObject<Item> BLOOD_STAFF = ITEMS.register("blood_staff", BloodStaffItem::new);
//
//    public static final RegistryObject<Item> EVOKER_SPELL_BOOK = ITEMS.register("evoker_spell_book", () -> new UniqueSpellBook(SpellRarity.LEGENDARY,
//            new SpellDataRegistryHolder[]{
//                    new SpellDataRegistryHolder(SpellRegistry.FANG_STRIKE_SPELL, 6),
//                    new SpellDataRegistryHolder(SpellRegistry.FANG_WARD_SPELL, 4),
//                    new SpellDataRegistryHolder(SpellRegistry.SUMMON_VEX_SPELL, 4)},
//            7,
//            () -> {
//                ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
//                builder.put(AttributeRegistry.EVOCATION_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .10, AttributeModifier.Operation.MULTIPLY_BASE));
//                return builder.build();
//            })
//    );
//    public static final RegistryObject<Item> NECRONOMICON = ITEMS.register("necronomicon_spell_book", NecronomiconSpellBook::new);
//
//    public static final RegistryObject<Item> MAGEHUNTER = ITEMS.register("magehunter", MagehunterItem::new);
//    public static final RegistryObject<Item> SPELLBREAKER = ITEMS.register("spellbreaker", () -> new SpellbreakerItem(SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.COUNTERSPELL_SPELL, 1))));
//    public static final RegistryObject<Item> TEST_CLAYMORE = ITEMS.register("claymore", TestClaymoreItem::new);
//    //    public static final RegistryObject<Item> TEST_RAPIER = ITEMS.register("amethyst_rapier", () -> new SpellbreakerItem(SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.STARFALL_SPELL, 8))));
////    public static final RegistryObject<Item> TEST_RAPIER2 = ITEMS.register("misery", () -> new SpellbreakerItem(SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.STARFALL_SPELL, 8))));
//    public static final RegistryObject<Item> KEEPER_FLAMBERGE = ITEMS.register("keeper_flamberge", KeeperFlambergeItem::new);
//    //public static final RegistryObject<Item> TRUTHSEEKER = ITEMS.register("truthseeker", TruthseekerItem::new);
//    //public static final RegistryObject<Item> FIREBRAND = ITEMS.register("firebrand", FirebrandItem::new);
//    public static final RegistryObject<Item> SCROLL = ITEMS.register("scroll", Scroll::new);
//    public static final RegistryObject<Item> AUTOLOADER_CROSSBOW = ITEMS.register("autoloader_crossbow", () -> new AutoloaderCrossbow(ItemPropertiesHelper.hidden(1).durability(465)));
//    public static final RegistryObject<Item> HITHER_THITHER_WAND = ITEMS.register("hither_thither_wand", () -> new HitherThitherWand(ItemPropertiesHelper.equipment(1).rarity(Rarity.EPIC)));
//    public static final RegistryObject<Item> STAFF_OF_THE_NINES = ITEMS.register("staff_of_the_nines", () -> new StaffOfTheNines(ItemPropertiesHelper.hidden(1).rarity(Rarity.EPIC)));
//
    /**
     * Generic Items
     */
    public static final RegistryObject<Item> DRIPPY = ITEMS.register("drippy", () -> new Item(ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON)));

//    public static final RegistryObject<Item> FROZEN_BONE_SHARD = ITEMS.register("frozen_bone", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> BLOOD_VIAL = ITEMS.register("blood_vial", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> DIVINE_PEARL = ITEMS.register("divine_pearl", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> FURLED_MAP = ITEMS.register("furled_map", () -> new FurledMapItem());
//    public static final RegistryObject<Item> HOGSKIN = ITEMS.register("hogskin", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> DRAGONSKIN = ITEMS.register("dragonskin", DragonskinItem::new);
//    public static final RegistryObject<Item> ARCANE_ESSENCE = ITEMS.register("arcane_essence", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> MAGIC_CLOTH = ITEMS.register("magic_cloth", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> BLANK_RUNE = ITEMS.register("blank_rune", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> FIRE_RUNE = ITEMS.register("fire_rune", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> ICE_RUNE = ITEMS.register("ice_rune", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> LIGHTNING_RUNE = ITEMS.register("lightning_rune", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> ENDER_RUNE = ITEMS.register("ender_rune", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> HOLY_RUNE = ITEMS.register("holy_rune", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> BLOOD_RUNE = ITEMS.register("blood_rune", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> EVOCATION_RUNE = ITEMS.register("evocation_rune", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> MANA_RUNE = ITEMS.register("arcane_rune", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> COOLDOWN_RUNE = ITEMS.register("cooldown_rune", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> PROTECTION_RUNE = ITEMS.register("protection_rune", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> NATURE_RUNE = ITEMS.register("nature_rune", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> WAYWARD_COMPASS = ITEMS.register("wayward_compass", WaywardCompass::new);
//    //    public static final RegistryObject<Item> ANTIQUATED_COMPASS = ITEMS.register("antiquated_compass", AntiquatedCompass::new);
//    public static final RegistryObject<Item> RUINED_BOOK = ITEMS.register("ruined_book", () -> new Item(ItemPropertiesHelper.material().rarity(Rarity.EPIC)));
//    public static final RegistryObject<Item> CINDER_ESSENCE = ITEMS.register("cinder_essence", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> ARCANE_SALVAGE = ITEMS.register("arcane_salvage", ArcaneSalvageItem::new);
//    public static final RegistryObject<Item> ARCANE_INGOT = ITEMS.register("arcane_ingot", () -> new Item(ItemPropertiesHelper.material()));
//    public static final RegistryObject<Item> SHRIVING_STONE = ITEMS.register("shriving_stone", ShrivingStoneItem::new);
//    public static final RegistryObject<Item> ELDRITCH_PAGE = ITEMS.register("eldritch_manuscript", () -> new EldritchManuscript(ItemPropertiesHelper.material().rarity(Rarity.EPIC)));
//    public static final RegistryObject<Item> LOST_KNOWLEDGE_FRAGMENT = ITEMS.register("ancient_knowledge_fragment", () -> new Item(ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON)));
//    public static final RegistryObject<Item> FROSTED_HELVE = ITEMS.register("frosted_helve", () -> new Item(ItemPropertiesHelper.material().rarity(Rarity.COMMON)));
//    public static final RegistryObject<Item> ICE_CRYSTAL = ITEMS.register("permafrost_shard", () -> new Item(ItemPropertiesHelper.material().rarity(Rarity.RARE)));

    public static Collection<RegistryObject<Item>> getIronsItems() {
        return ITEMS.getEntries();
    }
}
