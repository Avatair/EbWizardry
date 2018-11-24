package electroblob.wizardry.wizmodule.effects;

import com.teamwizardry.wizardry.api.spell.SpellData;
import com.teamwizardry.wizardry.api.spell.SpellRing;
import com.teamwizardry.wizardry.api.spell.annotation.ModuleOverride;
import com.teamwizardry.wizardry.api.spell.annotation.ModuleParameter;
import com.teamwizardry.wizardry.api.spell.annotation.RegisterModule;
import com.teamwizardry.wizardry.api.spell.module.IModuleEffect;
import com.teamwizardry.wizardry.api.spell.module.ModuleInstanceEffect;

import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.event.SpellCastEvent.Source;
import electroblob.wizardry.packet.PacketCastSpell;
import electroblob.wizardry.packet.WizardryPacketHandler;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

@RegisterModule(ID="effect_ebw_projectile_wrapper")
public class ModuleEffectProjectileWrapper implements IModuleEffect {
	
	@ModuleParameter("ebwspell")
	public String spellName;
	
	private Spell lazy_spell;

	@Override
	public String[] compatibleModifierClasses() {
		return new String[]{ };
	}

	@Override
	public boolean run(ModuleInstanceEffect instance, SpellData spell, SpellRing spellRing) {
		// Do nothing here
		return true;
	}
	
	public Spell getSpell() {
		if( lazy_spell == null ) {
			lazy_spell = Spell.registry.getValue(new ResourceLocation(spellName));
			if( lazy_spell == null )
				throw new IllegalStateException("Spell '" + spellName + "' is not a valid Electrobob's Wizardry spell.");
		}
		return lazy_spell;
	}
	
	//////////////////
	
	@ModuleOverride("shape_projectile_launch")
	public boolean launchProjectile(SpellData data, SpellRing shape, World world, Vec3d origin, boolean runResult) {
		Spell spell = getSpell();

		// Player
		EntityPlayer player;
		{
			Entity caster = data.getCaster();
			if( caster == null )
				return false;	// TODO: Test with pearl holders.
			if( !(caster instanceof EntityPlayer) )
				return false;	// TODO: Generalize for any kind of entity. Not only player
			player = (EntityPlayer)data.getCaster();
		}
		
		// Hand
		EnumHand hand = data.getHand();
		if( hand == null )
			hand = EnumHand.MAIN_HAND; // Fallback

		// Modifiers
		SpellModifiers modifiers = new SpellModifiers();
//		double dist = shape.getAttributeValue(AttributeRegistry.RANGE, data);
//		if( dist > 0 )
//			modifiers.set(WizardryItems.range_upgrade, 1.0f + (float)dist, true);
		// ... convert from applied modifiers
		
		// If anything stops the spell working at this point, nothing else happens.
		if(MinecraftForge.EVENT_BUS.post(new SpellCastEvent.Pre(player, spell, modifiers, Source.OTHER))){
			return false;
		}

		if( spell.cast(world, player, hand, 0, modifiers) ) {
			MinecraftForge.EVENT_BUS.post(new SpellCastEvent.Post(player, spell, modifiers, Source.OTHER));

			if(spell.doesSpellRequirePacket()){
				// Sends a packet to all players in dimension to tell them to spawn particles.
				IMessage msg = new PacketCastSpell.Message(player.getEntityId(), hand, spell.id(), modifiers);
				WizardryPacketHandler.net.sendToDimension(msg, world.provider.getDimension());
			}
		}
		else
			return false;
		
		return true;
	}

}
