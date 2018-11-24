package electroblob.wizardry.registry;

import electroblob.wizardry.Wizardry;
import electroblob.wizardry.spell.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * Class responsible for defining, storing and registering all of wizardry's spells.
 * 
 * @author Electroblob
 * @since Wizardry 2.1
 */
// In case anyone was wondering, the reason @ObjectHolder is useful within one mod is that it allows you to initialise
// stuff during the registry events (or whenever), whilst still having a final field (which is important, not only
// because it makes the text go bold, but also because it stops anyone fiddling with your fields). "Why would I want to
// initialise things within the registry events?", I hear you ask - well, for one, custom registries don't like it if
// you haven't created the registry before you start calling constructors of classes extending IForgeRegistryEntry.Impl,
// and secondly, you might want to initialise objects based on certain conditions - perhaps a config option, or whether
// another mod is installed. This, presumably, is why everyone at forge is encouraging us to use @ObjectHolder.
@ObjectHolder(Wizardry.MODID)
@Mod.EventBusSubscriber
public final class Spells {

	// This is here because this class is already an event handler.
	@SubscribeEvent
	public static void createRegistry(RegistryEvent.NewRegistry event){

		// Beats me why we need both of these. Surely the type parameter means it already knows?
		RegistryBuilder<Spell> builder = new RegistryBuilder<Spell>();
		builder.setType(Spell.class);
		builder.setName(new ResourceLocation(Wizardry.MODID, "spells"));
		builder.setIDRange(0, 5000); // Is there any penalty for using a larger number?

		Spell.registry = builder.create();
	}

	// Wizardry 1.0 spells

	public static final Spell none = null;
	public static final Spell magic_missile = null;
	public static final Spell ignite = null;
	public static final Spell freeze = null;
	public static final Spell snowball = null;
	public static final Spell arc = null;
	public static final Spell thunderbolt = null;
	public static final Spell summon_zombie = null;
	public static final Spell snare = null;
	public static final Spell dart = null;
	public static final Spell light = null;
	public static final Spell telekinesis = null;
	public static final Spell heal = null;

	public static final Spell fireball = null;
	public static final Spell flame_ray = null;
	public static final Spell firebomb = null;
	public static final Spell fire_sigil = null;
	public static final Spell firebolt = null;
	public static final Spell frost_ray = null;
	public static final Spell summon_snow_golem = null;
	public static final Spell ice_shard = null;
	public static final Spell ice_statue = null;
	public static final Spell frost_sigil = null;
	public static final Spell lightning_ray = null;
	public static final Spell spark_bomb = null;
	public static final Spell homing_spark = null;
	public static final Spell lightning_sigil = null;
	public static final Spell lightning_arrow = null;
	public static final Spell life_drain = null;
	public static final Spell summon_skeleton = null;
	public static final Spell metamorphosis = null;
	public static final Spell wither = null;
	public static final Spell poison = null;
	public static final Spell growth_aura = null;
	public static final Spell bubble = null;
	public static final Spell whirlwind = null;
	public static final Spell poison_bomb = null;
	public static final Spell summon_spirit_wolf = null;
	public static final Spell blink = null;
	public static final Spell agility = null;
	public static final Spell conjure_sword = null;
	public static final Spell conjure_pickaxe = null;
	public static final Spell conjure_bow = null;
	public static final Spell force_arrow = null;
	public static final Spell shield = null;
	public static final Spell replenish_hunger = null;
	public static final Spell cure_effects = null;
	public static final Spell heal_ally = null;

	public static final Spell summon_blaze = null;
	public static final Spell ring_of_fire = null;
	public static final Spell detonate = null;
	public static final Spell fire_resistance = null;
	public static final Spell fireskin = null;
	public static final Spell flaming_axe = null;
	public static final Spell blizzard = null;
	public static final Spell summon_ice_wraith = null;
	public static final Spell ice_shroud = null;
	public static final Spell ice_charge = null;
	public static final Spell frost_axe = null;
	public static final Spell invoke_weather = null;
	public static final Spell chain_lightning = null;
	public static final Spell lightning_bolt = null;
	public static final Spell summon_lightning_wraith = null;
	public static final Spell static_aura = null;
	public static final Spell lightning_disc = null;
	public static final Spell mind_control = null;
	public static final Spell summon_wither_skeleton = null;
	public static final Spell entrapment = null;
	public static final Spell wither_skull = null;
	public static final Spell darkness_orb = null;
	public static final Spell shadow_ward = null;
	public static final Spell decay = null;
	public static final Spell water_breathing = null;
	public static final Spell tornado = null;
	public static final Spell glide = null;
	public static final Spell summon_spirit_horse = null;
	public static final Spell spider_swarm = null;
	public static final Spell slime = null;
	public static final Spell petrify = null;
	public static final Spell invisibility = null;
	public static final Spell levitation = null;
	public static final Spell force_orb = null;
	public static final Spell transportation = null;
	public static final Spell spectral_pathway = null;
	public static final Spell phase_step = null;
	public static final Spell vanishing_box = null;
	public static final Spell greater_heal = null;
	public static final Spell healing_aura = null;
	public static final Spell forcefield = null;
	public static final Spell ironflesh = null;
	public static final Spell transience = null;

	public static final Spell meteor = null;
	public static final Spell firestorm = null;
	public static final Spell summon_phoenix = null;
	public static final Spell ice_age = null;
	public static final Spell wall_of_frost = null;
	public static final Spell summon_ice_giant = null;
	public static final Spell thunderstorm = null;
	public static final Spell lightning_hammer = null;
	public static final Spell plague_of_darkness = null;
	public static final Spell summon_skeleton_legion = null;
	public static final Spell summon_shadow_wraith = null;
	public static final Spell forests_curse = null;
	public static final Spell flight = null;
	public static final Spell silverfish_swarm = null;
	public static final Spell black_hole = null;
	public static final Spell shockwave = null;
	public static final Spell summon_iron_golem = null;
	public static final Spell arrow_rain = null;
	public static final Spell diamondflesh = null;
	public static final Spell font_of_vitality = null;

	// Wizardry 1.1 spells

	public static final Spell smoke_bomb = null;
	public static final Spell mind_trick = null;
	public static final Spell leap = null;

	public static final Spell pocket_furnace = null;
	public static final Spell intimidate = null;
	public static final Spell banish = null;
	public static final Spell sixth_sense = null;
	public static final Spell darkvision = null;
	public static final Spell clairvoyance = null;
	public static final Spell pocket_workbench = null;
	public static final Spell imbue_weapon = null;
	public static final Spell invigorating_presence = null;
	public static final Spell oakflesh = null;

	public static final Spell greater_fireball = null;
	public static final Spell flaming_weapon = null;
	public static final Spell ice_lance = null;
	public static final Spell freezing_weapon = null;
	public static final Spell ice_spikes = null;
	public static final Spell lightning_pulse = null;
	public static final Spell curse_of_soulbinding = null;
	public static final Spell cobwebs = null;
	public static final Spell decoy = null;
	public static final Spell arcane_jammer = null;
	public static final Spell conjure_armour = null;
	public static final Spell group_heal = null;

	public static final Spell hailstorm = null;
	public static final Spell lightning_web = null;
	public static final Spell summon_storm_elemental = null;
	public static final Spell earthquake = null;
	public static final Spell font_of_mana = null;

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Spell> event){

		IForgeRegistry<Spell> registry = event.getRegistry();

		// event.getRegistry should always equal Spell.registry.
		registry.register(new None(0));
		registry.register(new MagicMissile(1));
		registry.register(new Ignite(2));
		registry.register(new Freeze(3));
		registry.register(new Snowball(4));
		registry.register(new Arc(5));
		registry.register(new Thunderbolt(6));
		registry.register(new SummonZombie(7));
		registry.register(new Snare(8));
		registry.register(new Dart(9));
		registry.register(new Light(10));
		registry.register(new Telekinesis(11));
		registry.register(new Heal(12));

		registry.register(new Fireball(13));
		registry.register(new FlameRay(14));
		registry.register(new Firebomb(15));
		registry.register(new FireSigil(16));
		registry.register(new Firebolt(17));
		registry.register(new FrostRay(18));
		registry.register(new SummonSnowGolem(19));
		registry.register(new IceShard(20));
		registry.register(new IceStatue(21));
		registry.register(new FrostSigil(22));
		registry.register(new LightningRay(23));
		registry.register(new SparkBomb(24));
		registry.register(new HomingSpark(25));
		registry.register(new LightningSigil(26));
		registry.register(new LightningArrow(27));
		registry.register(new LifeDrain(28));
		registry.register(new SummonSkeleton(29));
		registry.register(new Metamorphosis(30));
		registry.register(new Wither(31));
		registry.register(new Poison(32));
		registry.register(new GrowthAura(33));
		registry.register(new Bubble(34));
		registry.register(new Whirlwind(35));
		registry.register(new PoisonBomb(36));
		registry.register(new SummonSpiritWolf(37));
		registry.register(new Blink(38));
		registry.register(new Agility(39));
		registry.register(new ConjureSword(40));
		registry.register(new ConjurePickaxe(41));
		registry.register(new ConjureBow(42));
		registry.register(new ForceArrow(43));
		registry.register(new Shield(44));
		registry.register(new ReplenishHunger(45));
		registry.register(new CureEffects(46));
		registry.register(new HealAlly(47));

		registry.register(new SummonBlaze(48));
		registry.register(new RingOfFire(49));
		registry.register(new Detonate(50));
		registry.register(new FireResistance(51));
		registry.register(new Fireskin(52));
		registry.register(new FlamingAxe(53));
		registry.register(new Blizzard(54));
		registry.register(new SummonIceWraith(55));
		registry.register(new IceShroud(56));
		registry.register(new IceCharge(57));
		registry.register(new FrostAxe(58));
		registry.register(new InvokeWeather(59));
		registry.register(new ChainLightning(60));
		registry.register(new LightningBolt(61));
		registry.register(new SummonLightningWraith(62));
		registry.register(new StaticAura(63));
		registry.register(new LightningDisc(64));
		registry.register(new MindControl(65));
		registry.register(new SummonWitherSkeleton(66));
		registry.register(new Entrapment(67));
		registry.register(new WitherSkull(68));
		registry.register(new DarknessOrb(69));
		registry.register(new ShadowWard(70));
		registry.register(new Decay(71));
		registry.register(new WaterBreathing(72));
		registry.register(new Tornado(73));
		registry.register(new Glide(74));
		registry.register(new SummonSpiritHorse(75));
		registry.register(new SpiderSwarm(76));
		registry.register(new Slime(77));
		registry.register(new Petrify(78));
		registry.register(new Invisibility(79));
		registry.register(new Levitation(80));
		registry.register(new ForceOrb(81));
		registry.register(new Transportation(82));
		registry.register(new SpectralPathway(83));
		registry.register(new PhaseStep(84));
		registry.register(new VanishingBox(85));
		registry.register(new GreaterHeal(86));
		registry.register(new HealingAura(87));
		registry.register(new Forcefield(88));
		registry.register(new Ironflesh(89));
		registry.register(new Transience(90));

		registry.register(new Meteor(91));
		registry.register(new Firestorm(92));
		registry.register(new SummonPhoenix(93));
		registry.register(new IceAge(94));
		registry.register(new WallOfFrost(95));
		registry.register(new SummonIceGiant(96));
		registry.register(new Thunderstorm(97));
		registry.register(new LightningHammer(98));
		registry.register(new PlagueOfDarkness(99));
		registry.register(new SummonSkeletonLegion(100));
		registry.register(new SummonShadowWraith(101));
		registry.register(new ForestsCurse(102));
		registry.register(new Flight(103));
		registry.register(new SilverfishSwarm(104));
		registry.register(new BlackHole(105));
		registry.register(new Shockwave(106));
		registry.register(new SummonIronGolem(107));
		registry.register(new ArrowRain(108));
		registry.register(new Diamondflesh(109));
		registry.register(new FontOfVitality(110));

		// Wizardry 1.1 spells

		registry.register(new SmokeBomb(111));
		registry.register(new MindTrick(112));
		registry.register(new Leap(113));

		registry.register(new PocketFurnace(114));
		registry.register(new Intimidate(115));
		registry.register(new Banish(116));
		registry.register(new SixthSense(117));
		registry.register(new Darkvision(118));
		registry.register(new Clairvoyance(119));
		registry.register(new PocketWorkbench(120));
		registry.register(new ImbueWeapon(121));
		registry.register(new InvigoratingPresence(122));
		registry.register(new Oakflesh(123));

		registry.register(new GreaterFireball(124));
		registry.register(new FlamingWeapon(125));
		registry.register(new IceLance(126));
		registry.register(new FreezingWeapon(127));
		registry.register(new IceSpikes(128));
		registry.register(new LightningPulse(129));
		registry.register(new CurseOfSoulbinding(130));
		registry.register(new Cobwebs(131));
		registry.register(new Decoy(132));
		registry.register(new ArcaneJammer(133));
		registry.register(new ConjureArmour(134));
		registry.register(new GroupHeal(135));

		registry.register(new Hailstorm(136));
		registry.register(new LightningWeb(137));
		registry.register(new SummonStormElemental(138));
		registry.register(new Earthquake(139));
		registry.register(new FontOfMana(140));
	}

}