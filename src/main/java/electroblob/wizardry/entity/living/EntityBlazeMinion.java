package electroblob.wizardry.entity.living;

import java.lang.ref.WeakReference;
import java.util.UUID;

import electroblob.wizardry.Wizardry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class EntityBlazeMinion extends EntityBlaze implements ISummonedCreature {

	// Field implementations
	private int lifetime = 600;
	private WeakReference<EntityLivingBase> casterReference;
	private UUID casterUUID;

	// Setter + getter implementations
	@Override
	public int getLifetime(){
		return lifetime;
	}

	@Override
	public void setLifetime(int lifetime){
		this.lifetime = lifetime;
	}

	@Override
	public WeakReference<EntityLivingBase> getCasterReference(){
		return casterReference;
	}

	@Override
	public void setCasterReference(WeakReference<EntityLivingBase> reference){
		casterReference = reference;
	}

	@Override
	public UUID getCasterUUID(){
		return casterUUID;
	}

	@Override
	public void setCasterUUID(UUID uuid){
		this.casterUUID = uuid;
	}

	/**
	 * Default shell constructor, only used by client. Lifetime defaults arbitrarily to 600, but this doesn't matter
	 * because the client side entity immediately gets the lifetime value copied over to it by this class anyway. When
	 * extending this class, you must override this constructor or Minecraft won't like it, but there's no need to do
	 * anything inside it other than call super().
	 */
	public EntityBlazeMinion(World world){
		super(world);
		this.experienceValue = 0;
	}

	/**
	 * Set lifetime to -1 to allow this creature to last forever. This constructor should be overridden when extending
	 * this class (be sure to call super()) so that AI and other things can be added.
	 */
	public EntityBlazeMinion(World world, double x, double y, double z, EntityLivingBase caster, int lifetime){
		super(world);
		this.setPosition(x, y, z);
		this.casterReference = new WeakReference<EntityLivingBase>(caster);
		this.experienceValue = 0;
		this.lifetime = lifetime;
	}

	// EntityBlaze overrides

	// This particular override is pretty standard: let the superclass handle basic AI like swimming, but replace its
	// targeting system with one that targets hostile mobs and takes the ADS into account.
	@Override
	protected void initEntityAI(){
		super.initEntityAI();
		this.targetTasks.taskEntries.clear();
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityLivingBase>(this, EntityLivingBase.class,
				0, false, true, this.getTargetSelector()));
	}

	// Implementations

	@Override
	public void setRevengeTarget(EntityLivingBase entity){
		if(this.shouldRevengeTarget(entity)) super.setRevengeTarget(entity);
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		this.updateDelegate();
	}

	@Override
	public void onSpawn(){
		this.spawnParticleEffect();
	}

	@Override
	public void onDespawn(){
		this.spawnParticleEffect();
	}

	/**
	 * Normally this would be private, but since this class has subclasses with different spawn/despawn particle
	 * effects, it makes sense to have them override this rather than both onSpawn() and onDespawn().
	 */
	protected void spawnParticleEffect(){
		if(this.world.isRemote){
			for(int i = 0; i < 15; i++){
				this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + this.rand.nextFloat(),
						this.posY + 1 + this.rand.nextFloat(), this.posZ + this.rand.nextFloat(), 0, 0, 0);
			}
		}
	}

	@Override
	public boolean hasParticleEffect(){
		return false;
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand){
		// In this case, the delegate method determines whether super is called.
		// Rather handily, we can make use of Java's 'stop as soon as you find true' method of evaluating OR statements.
		return this.interactDelegate(player, hand) || super.processInteract(player, hand);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound){
		super.writeEntityToNBT(nbttagcompound);
		this.writeNBTDelegate(nbttagcompound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound){
		super.readEntityFromNBT(nbttagcompound);
		this.readNBTDelegate(nbttagcompound);
	}

	// Recommended overrides

	@Override
	protected int getExperiencePoints(EntityPlayer player){
		return 0;
	}

	@Override
	protected boolean canDropLoot(){
		return false;
	}

	@Override
	protected Item getDropItem(){
		return null;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return null;
	}

	// This vanilla method has nothing to do with the custom despawn() method.
	@Override
	protected boolean canDespawn(){
		return false;
	}

	@Override
	public boolean canAttackClass(Class<? extends EntityLivingBase> entityType){
		return true;
	}

	@Override
	public ITextComponent getDisplayName(){
		if(getCaster() != null){
			return new TextComponentTranslation(NAMEPLATE_TRANSLATION_KEY, getCaster().getName(),
					new TextComponentTranslation("entity." + this.getEntityString() + ".name"));
		}else{
			return super.getDisplayName();
		}
	}

	@Override
	public boolean hasCustomName(){
		// If this returns true, the renderer will show the nameplate when looking directly at the entity
		return Wizardry.settings.showSummonedCreatureNames && getCaster() != null;
	}
}
