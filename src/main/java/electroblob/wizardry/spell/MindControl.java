package electroblob.wizardry.spell;

import java.util.Arrays;
import java.util.List;

import electroblob.wizardry.Wizardry;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.constants.SpellType;
import electroblob.wizardry.constants.Tier;
import electroblob.wizardry.entity.living.EntityEvilWizard;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.util.SpellModifiers;
import electroblob.wizardry.util.WizardryParticleType;
import electroblob.wizardry.util.WizardryUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class MindControl extends Spell {

	/**
	 * The NBT tag name for storing the controlling entity's UUID in the target's tag compound. Defined here in case it
	 * changes.
	 */
	public static final String NBT_KEY = "controllingEntity";

	public MindControl(int id){
		super(id, Tier.ADVANCED, 40, Element.NECROMANCY, "mind_control", SpellType.ATTACK, 150, EnumAction.NONE, false);
	}

	@Override
	public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers){

		RayTraceResult rayTrace = WizardryUtilities.standardEntityRayTrace(world, caster,
				8 * modifiers.get(WizardryItems.range_upgrade));

		if(rayTrace != null && rayTrace.entityHit != null && WizardryUtilities.isLiving(rayTrace.entityHit)){

			EntityLivingBase target = (EntityLivingBase)rayTrace.entityHit;

			if(!world.isRemote){
				if(!canControl(target)){
					// Adds a message saying that the player/boss entity/wizard resisted mind control
					caster.sendMessage(new TextComponentTranslation("spell.resist", target.getName(),
							this.getNameForTranslationFormatted()));

				}else if(target instanceof EntityLiving){

					if(!MindControl.findMindControlTarget((EntityLiving)target, caster, world)){
						// If no valid target was found, this just acts like mind trick.
						((EntityLiving)target).setAttackTarget(null);
					}

					NBTTagCompound entityNBT = target.getEntityData();
					if(entityNBT != null) entityNBT.setUniqueId(NBT_KEY, caster.getUniqueID());

					((EntityLiving)target).addPotionEffect(new PotionEffect(WizardryPotions.mind_control,
							(int)(600 * modifiers.get(WizardryItems.duration_upgrade)), 0));
				}
			}else{
				for(int i = 0; i < 10; i++){
					Wizardry.proxy.spawnParticle(WizardryParticleType.DARK_MAGIC, world,
							target.posX - 0.25 + world.rand.nextDouble() * 0.5,
							target.getEntityBoundingBox().minY + target.getEyeHeight() - 0.25
									+ world.rand.nextDouble() * 0.5,
							target.posZ - 0.25 + world.rand.nextDouble() * 0.5, 0, 0, 0, 0, 0.8f, 0.2f, 1.0f);
					Wizardry.proxy.spawnParticle(WizardryParticleType.DARK_MAGIC, world,
							target.posX - 0.25 + world.rand.nextDouble() * 0.5,
							target.getEntityBoundingBox().minY + target.getEyeHeight() - 0.25
									+ world.rand.nextDouble() * 0.5,
							target.posZ - 0.25 + world.rand.nextDouble() * 0.5, 0, 0, 0, 0, 0.2f, 0.04f, 0.25f);
				}
			}
			target.playSound(WizardrySounds.SPELL_SUMMONING, 1.0f, 1.0f);
			caster.swingArm(hand);
			return true;
		}
		return false;
	}

	@Override
	public boolean cast(World world, EntityLiving caster, EnumHand hand, int ticksInUse, EntityLivingBase target,
			SpellModifiers modifiers){

		if(target != null){
			if(!world.isRemote){
				if(canControl(target)){

					if(!MindControl.findMindControlTarget((EntityLiving)target, caster, world)){
						// If no valid target was found, this just acts like mind trick.
						((EntityLiving)target).setAttackTarget(null);
					}

					NBTTagCompound entityNBT = target.getEntityData();
					if(entityNBT != null) entityNBT.setUniqueId(NBT_KEY, caster.getUniqueID());

					((EntityLiving)target).addPotionEffect(new PotionEffect(WizardryPotions.mind_control,
							(int)(600 * modifiers.get(WizardryItems.duration_upgrade)), 0));
				}
			}else{
				for(int i = 0; i < 10; i++){
					Wizardry.proxy.spawnParticle(WizardryParticleType.DARK_MAGIC, world,
							target.posX - 0.25 + world.rand.nextDouble() * 0.5,
							target.getEntityBoundingBox().minY + target.getEyeHeight() - 0.25
									+ world.rand.nextDouble() * 0.5,
							target.posZ - 0.25 + world.rand.nextDouble() * 0.5, 0, 0, 0, 0, 0.8f, 0.2f, 1.0f);
					Wizardry.proxy.spawnParticle(WizardryParticleType.DARK_MAGIC, world,
							target.posX - 0.25 + world.rand.nextDouble() * 0.5,
							target.getEntityBoundingBox().minY + target.getEyeHeight() - 0.25
									+ world.rand.nextDouble() * 0.5,
							target.posZ - 0.25 + world.rand.nextDouble() * 0.5, 0, 0, 0, 0, 0.2f, 0.04f, 0.25f);
				}
			}
			target.playSound(WizardrySounds.SPELL_SUMMONING, 1.0f, 1.0f);
			caster.swingArm(hand);
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeCastByNPCs(){
		return true;
	}

	/** Returns true if the given entity can be mind controlled (i.e. is not a player, npc, evil wizard or boss). */
	public static boolean canControl(EntityLivingBase target){
		return target instanceof EntityLiving && target.isNonBoss() && !(target instanceof INpc)
				&& !(target instanceof EntityEvilWizard) && !Arrays.asList(Wizardry.settings.mindControlTargetsBlacklist)
				.contains(EntityList.getKey(target.getClass()));
	}

	/**
	 * Finds the nearest creature to the given target which it is allowed to attack according to the given caster and
	 * sets it as the target's attack target. Handles both new and old AI and takes follow range into account. Defined
	 * here so it can be used both in the spell itself and in the potion effect (event handler).
	 * 
	 * @param target The entity being mind controlled
	 * @param caster The entity doing the controlling
	 * @param world The world to look for targets in
	 * @return True if a new target was found and set, false if not.
	 */
	public static boolean findMindControlTarget(EntityLiving target, EntityLivingBase caster, World world){

		// As of 1.1, this now uses the creature's follow range, like normal targeting. It also
		// no longer lasts until the creature dies; instead it is a potion effect which continues to
		// set the target until it wears off.
		List<EntityLivingBase> possibleTargets = WizardryUtilities.getEntitiesWithinRadius(
				((EntityLiving)target).getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue(),
				target.posX, target.posY, target.posZ, world);

		possibleTargets.remove(target);
		possibleTargets.removeIf(e -> e instanceof EntityArmorStand);

		EntityLivingBase newAITarget = null;

		for(EntityLivingBase possibleTarget : possibleTargets){
			if(WizardryUtilities.isValidTarget(caster, possibleTarget) && (newAITarget == null
					|| target.getDistance(possibleTarget) < target.getDistance(newAITarget))){
				newAITarget = possibleTarget;
			}
		}

		if(newAITarget != null){
			// From 1.7.10 - this seems not to work quite right; the entity appears to continue attacking this target
			// after it gets killed (not noticeable in survival since it will target the player again immediately.)
			((EntityLiving)target).setAttackTarget(newAITarget);

			return true;
		}

		return false;

	}
	
	/** Retrieves the given entity's controller and decides whether it needs to search for a target. */
	private static void processTargeting(World world, EntityLiving entity, EntityLivingBase currentTarget){
		
		if(entity.isPotionActive(WizardryPotions.mind_control) && MindControl.canControl(entity)){

			NBTTagCompound entityNBT = entity.getEntityData();

			if(entityNBT != null && entityNBT.hasUniqueId(MindControl.NBT_KEY)){

				Entity caster = WizardryUtilities.getEntityByUUID(world, entityNBT.getUniqueId(MindControl.NBT_KEY));

				if(caster instanceof EntityLivingBase){

					// If the current target is already a valid mind control target, nothing happens.
					if(WizardryUtilities.isValidTarget(caster, currentTarget)) return;

					if(MindControl.findMindControlTarget(entity, (EntityLivingBase)caster, world)){
						// If it worked, skip setting the target to null.
						return;
					}
				}
			}
			// If the caster couldn't be found or no valid target was found, this just acts like mind trick.
			entity.setAttackTarget(null);
		}
	}

	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingUpdateEvent event){
		// Tries to find a new target for mind-controlled creatures that do not currently have one
		// When the mind-controlled creature does have a target, LivingSetAttackTargetEvent is used instead since it is
		// more efficient (because it only fires when the entity tries to set a target)
		// Of course, in survival this code is unlikely to be used much because the entity will always try to target the
		// player and hence will rarely have no target.
		if(event.getEntityLiving().isPotionActive(WizardryPotions.mind_control) && event.getEntityLiving() instanceof EntityLiving){
			
			EntityLiving entity = (EntityLiving)event.getEntityLiving();
			
			// Processes targeting if the current target is null or has died
			if(((EntityLiving)event.getEntityLiving()).getAttackTarget() == null
				|| !((EntityLiving)event.getEntityLiving()).getAttackTarget().isEntityAlive()){
			
				processTargeting(entity.world, entity, entity.getAttackTarget());
			}
		}
	}

	@SubscribeEvent
	public static void onLivingSetAttackTargetEvent(LivingSetAttackTargetEvent event){
		// The != null check prevents infinite loops with mind trick
		if(event.getTarget() != null && event.getEntityLiving() instanceof EntityLiving)
			processTargeting(event.getEntity().world, (EntityLiving)event.getEntityLiving(), event.getTarget());
	}

}