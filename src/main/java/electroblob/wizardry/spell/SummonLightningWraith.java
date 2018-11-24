package electroblob.wizardry.spell;

import electroblob.wizardry.constants.Element;
import electroblob.wizardry.constants.SpellType;
import electroblob.wizardry.constants.Tier;
import electroblob.wizardry.entity.living.EntityLightningWraith;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.util.SpellModifiers;
import electroblob.wizardry.util.WizardryUtilities;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SummonLightningWraith extends Spell {

	public SummonLightningWraith(int id){
		super(id, Tier.ADVANCED, 40, Element.LIGHTNING, "summon_lightning_wraith", SpellType.MINION, 200, EnumAction.BOW,
				false);
	}

	@Override
	public boolean doesSpellRequirePacket(){
		return false;
	}

	@Override
	public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers){

		if(!world.isRemote){

			BlockPos pos = WizardryUtilities.findNearbyFloorSpace(caster, 2, 4);
			if(pos == null) return false;

			EntityLightningWraith lightningWraith = new EntityLightningWraith(world, pos.getX() + 0.5, pos.getY(),
					pos.getZ() + 0.5, caster, (int)(600 * modifiers.get(WizardryItems.duration_upgrade)));
			world.spawnEntity(lightningWraith);
		}
		WizardryUtilities.playSoundAtPlayer(caster, SoundEvents.ENTITY_WITHER_AMBIENT, 1.0F,
				world.rand.nextFloat() * 0.2F + 1.0F);
		return true;
	}

	@Override
	public boolean cast(World world, EntityLiving caster, EnumHand hand, int ticksInUse, EntityLivingBase target,
			SpellModifiers modifiers){

		if(!world.isRemote){

			BlockPos pos = WizardryUtilities.findNearbyFloorSpace(caster, 2, 4);
			if(pos == null) return false;

			EntityLightningWraith lightningWraith = new EntityLightningWraith(world, pos.getX() + 0.5, pos.getY(),
					pos.getZ() + 0.5, caster, (int)(600 * modifiers.get(WizardryItems.duration_upgrade)));
			world.spawnEntity(lightningWraith);
		}
		caster.playSound(SoundEvents.ENTITY_WITHER_AMBIENT, 1.0F, world.rand.nextFloat() * 0.2F + 1.0F);
		return true;
	}

	@Override
	public boolean canBeCastByNPCs(){
		return true;
	}

}
