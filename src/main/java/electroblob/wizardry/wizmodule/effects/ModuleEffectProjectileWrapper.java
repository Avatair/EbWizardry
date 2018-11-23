package electroblob.wizardry.wizmodule.effects;

import com.teamwizardry.wizardry.api.spell.SpellData;
import com.teamwizardry.wizardry.api.spell.SpellRing;
import com.teamwizardry.wizardry.api.spell.annotation.ModuleOverride;
import com.teamwizardry.wizardry.api.spell.annotation.ModuleParameter;
import com.teamwizardry.wizardry.api.spell.annotation.RegisterModule;
import com.teamwizardry.wizardry.api.spell.module.IModuleEffect;
import com.teamwizardry.wizardry.api.spell.module.ModuleInstanceEffect;

import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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
		
		// Modifiers 
		SpellModifiers modifiers = new SpellModifiers();
//		double dist = shape.getAttributeValue(AttributeRegistry.RANGE, data);
//		if( dist > 0 )
//			modifiers.set(WizardryItems.range_upgrade, 1.0f + (float)dist, true);
		// ... convert from applied modifiers
		
		// Hand
		EnumHand hand = EnumHand.MAIN_HAND; // TODO: from parameters

		// TODO: Unsafe cast to player. Fix it!
		return spell.cast(world, (EntityPlayer)data.getCaster(), hand, 0, modifiers);
	}

}
