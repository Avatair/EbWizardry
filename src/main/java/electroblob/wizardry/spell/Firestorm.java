package electroblob.wizardry.spell;

import electroblob.wizardry.Wizardry;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.constants.SpellType;
import electroblob.wizardry.constants.Tier;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.MagicDamage.DamageType;
import electroblob.wizardry.util.SpellModifiers;
import electroblob.wizardry.util.WizardryParticleType;
import electroblob.wizardry.util.WizardryUtilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class Firestorm extends Spell {
	// SHAPE: RAY

	public Firestorm(int id){
		super(id, Tier.MASTER, 15, Element.FIRE, "firestorm", SpellType.ATTACK, 0, EnumAction.NONE, true);
	}

	@Override
	public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers){

		Vec3d look = caster.getLookVec();

		RayTraceResult rayTrace = WizardryUtilities.standardEntityRayTrace(world, caster,
				10 * modifiers.get(WizardryItems.range_upgrade));

		// Fire can damage armour stands.
		if(rayTrace != null && rayTrace.typeOfHit == RayTraceResult.Type.ENTITY && rayTrace.entityHit instanceof EntityLivingBase){

			EntityLivingBase target = (EntityLivingBase)rayTrace.entityHit;

			if(!MagicDamage.isEntityImmune(DamageType.FIRE, target)){
				target.setFire(10);
				WizardryUtilities.attackEntityWithoutKnockback(target,
						MagicDamage.causeDirectMagicDamage(caster, DamageType.FIRE),
						6.0f * modifiers.get(SpellModifiers.DAMAGE));
			}else{
				if(!world.isRemote && ticksInUse == 1) caster.sendMessage(new TextComponentTranslation("spell.resist",
						target.getName(), this.getNameForTranslationFormatted()));
			}

		}else if(rayTrace != null && rayTrace.typeOfHit == RayTraceResult.Type.BLOCK){

			BlockPos pos = rayTrace.getBlockPos().offset(rayTrace.sideHit);

			if(world.isAirBlock(pos)){
				if(!world.isRemote){
					world.setBlockState(pos, Blocks.FIRE.getDefaultState());
				}
			}
		}

		if(world.isRemote){
			for(int i = 0; i < 40; i++){
				// I figured it out! when on client side, entityplayer.posY is at the eyes, not the feet!
				double x1 = caster.posX + look.x * i / 2 + world.rand.nextFloat() * 0.6f - 0.3f;
				double y1 = WizardryUtilities.getPlayerEyesPos(caster) - 0.4f + look.y * i / 2
						+ world.rand.nextFloat() * 0.4f - 0.2f;
				double z1 = caster.posZ + look.z * i / 2 + world.rand.nextFloat() * 0.6f - 0.3f;
				Wizardry.proxy.spawnParticle(WizardryParticleType.MAGIC_FIRE, world, x1, y1, z1,
						look.x * modifiers.get(WizardryItems.range_upgrade),
						look.y * modifiers.get(WizardryItems.range_upgrade),
						look.z * modifiers.get(WizardryItems.range_upgrade), 0, 3 + world.rand.nextFloat(), 0, 0);
				Wizardry.proxy.spawnParticle(WizardryParticleType.MAGIC_FIRE, world, x1, y1, z1,
						look.x * modifiers.get(WizardryItems.range_upgrade),
						look.y * modifiers.get(WizardryItems.range_upgrade),
						look.z * modifiers.get(WizardryItems.range_upgrade), 0, 3 + world.rand.nextFloat(), 0, 0);
			}
		}
		if(ticksInUse % 16 == 0){
			if(ticksInUse == 0) WizardryUtilities.playSoundAtPlayer(caster, SoundEvents.ENTITY_BLAZE_SHOOT, 1, 1);
			WizardryUtilities.playSoundAtPlayer(caster, WizardrySounds.SPELL_LOOP_FIRE, 0.5F, 1.0f);
		}
		return true;
	}

}
