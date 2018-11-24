package electroblob.wizardry.spell;

import electroblob.wizardry.constants.Element;
import electroblob.wizardry.constants.SpellType;
import electroblob.wizardry.constants.Tier;
import electroblob.wizardry.util.SpellModifiers;
import electroblob.wizardry.util.WizardryUtilities;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WitherSkull extends Spell {

	public WitherSkull(int id){
		super(id, Tier.ADVANCED, 20, Element.NECROMANCY, "wither_skull", SpellType.ATTACK, 30, EnumAction.NONE, false);
	}

	@Override
	public boolean doesSpellRequirePacket(){
		return false;
	}

	@Override
	public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers){

		Vec3d look = caster.getLookVec();

		if(!world.isRemote){
			EntityWitherSkull witherskull = new EntityWitherSkull(world, caster, 1, 1, 1);
			witherskull.setPosition(caster.posX + look.x, caster.posY + look.y + 1.3,
					caster.posZ + look.z);
			witherskull.accelerationX = look.x * 0.1;
			witherskull.accelerationY = look.y * 0.1;
			witherskull.accelerationZ = look.z * 0.1;
			world.spawnEntity(witherskull);
			WizardryUtilities.playSoundAtPlayer(caster, SoundEvents.ENTITY_WITHER_SHOOT, 1.0F,
					world.rand.nextFloat() * 0.2F + 1.0F);
		}
		caster.swingArm(hand);
		return true;
	}

	@Override
	public boolean cast(World world, EntityLiving caster, EnumHand hand, int ticksInUse, EntityLivingBase target,
			SpellModifiers modifiers){

		if(target != null){

			if(!world.isRemote){

				EntityWitherSkull witherskull = new EntityWitherSkull(world, caster, 1, 1, 1);

				double dx = target.posX - caster.posX;
				double dy = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F)
						- (caster.posY + (double)(caster.height / 2.0F));
				double dz = target.posZ - caster.posZ;

				witherskull.accelerationX = dx / caster.getDistance(target) * 0.1;
				witherskull.accelerationY = dy / caster.getDistance(target) * 0.1;
				witherskull.accelerationZ = dz / caster.getDistance(target) * 0.1;

				witherskull.setPosition(caster.posX, caster.posY + caster.getEyeHeight(), caster.posZ);

				world.spawnEntity(witherskull);
				caster.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 1.0F, world.rand.nextFloat() * 0.2F + 1.0F);
			}

			caster.swingArm(hand);
			return true;
		}

		return false;
	}

	@Override
	public boolean canBeCastByNPCs(){
		return true;
	}

}
