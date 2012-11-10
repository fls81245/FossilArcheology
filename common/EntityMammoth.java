package net.minecraft.src;

import java.util.ArrayList;

import net.minecraftforge.common.IShearable;


public class EntityMammoth extends EntityTameable implements IShearable {
	private static final int SIZE_MULTIFER = 5;
	private static final int EATING_TIMES_TO_GROW_FUR = 5;
	private static final float CHILD_SIZE_Y = 1.3F;
	private static final float CHILD_SIZE_X = 0.9F;
	private static final float ADULT_SIZE_Y = CHILD_SIZE_Y * SIZE_MULTIFER;
	private static final float ADULT_SIZE_X = CHILD_SIZE_X * SIZE_MULTIFER;
	private static final Potion BIOME_SICK=Potion.weakness;
	private static final PotionEffect BIOME_EFFECT = new PotionEffect(Potion.weakness.id,60,1);
	private static final BiomeGenBase[] COLD_BIOMES = {
			BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver,
			BiomeGenBase.iceMountains, BiomeGenBase.icePlains,
			BiomeGenBase.taiga, BiomeGenBase.taigaHills };
	private static final BiomeGenBase[] HOT_BIOMES = { BiomeGenBase.desert,
			BiomeGenBase.swampland, BiomeGenBase.jungle,
			BiomeGenBase.jungleHills, BiomeGenBase.hell,
			BiomeGenBase.desertHills };
	private EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);
	private int eatGrassTimes = 0;
	private int swingTick;
	public EntityMammoth(World par1World) {
		super(par1World);
		this.texture = "/skull/MammothAdult.png";
		this.setSize(CHILD_SIZE_X, CHILD_SIZE_Y);
		float var2 = 0.23F;
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 0.25F, true));
		this.tasks.addTask(3, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(4, new EntityAIMate(this, var2));
		this.tasks.addTask(5, new EntityAITempt(this, 0.25F,
				Item.wheat.shiftedIndex, false));
		this.tasks.addTask(6, new EntityAIFollowParent(this, 0.25F));
		this.tasks.addTask(7, this.aiEatGrass);
		this.tasks.addTask(8, new EntityAIWander(this, var2));
		this.tasks.addTask(9, new EntityAIWatchClosest(this,
				EntityPlayer.class, 6.0F));
		this.tasks.addTask(10, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	public boolean isAIEnabled() {
		return true;
	}
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        this.swingTick = 10;
        this.worldObj.setEntityState(this, (byte)4);
        boolean var2 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), isChild()?2:7);

        if (var2)
        {
            par1Entity.motionY += 0.4000000059604645D;
        }

        this.worldObj.playSoundAtEntity(this, "mob.irongolem.throw", 1.0F, 1.0F);
        return var2;
    }
	public int getMaxHealth() {
		return 24;
	}
    public int getTalkInterval()
    {
        return 360;
    }
	public void onLivingUpdate() {
		super.onLivingUpdate();
		updateSize();
        if (this.swingTick > 0)
        {
            --this.swingTick;
        }
		if (!this.isPotionActive(BIOME_SICK) && this.checkBiomeAndWeakness()){
			this.addPotionEffect(BIOME_EFFECT);
		}
	}

	public String getTexture() {
		if (this.isChild())
			return "/skull/MammothYoung.png";
		else {
			if (!this.getSheared()) {
				return "/skull/MammothAdult.png";
			} else {
				return "/skull/MammothFurless.png";
			}
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Sheared", this.getSheared());
		par1NBTTagCompound.setByte("Color", (byte) this.getFleeceColor());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
		this.setSheared(par1NBTTagCompound.getBoolean("Sheared"));
		this.setFleeceColor(par1NBTTagCompound.getByte("Color"));
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound() {
		return "Mammoth_living";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound() {
		return "Mammoth_hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound() {
		return "Mammoth_death";
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume() {
		return 0.4F;
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	protected int getDropItemId() {
		return Item.leather.shiftedIndex;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean par1, int par2) {
		int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
		int var4;

		for (var4 = 0; var4 < var3; ++var4) {
			this.dropItem(Item.leather.shiftedIndex, 1);
		}

		var3 = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);

		for (var4 = 0; var4 < var3; ++var4) {
			if (this.isBurning()) {
				this.dropItem(Item.beefCooked.shiftedIndex, 1);
			} else {
				this.dropItem(Item.beefRaw.shiftedIndex, 1);
			}
		}
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(18, new Byte((byte) 3));
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	public boolean interact(EntityPlayer par1EntityPlayer) {
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		if (var2!=null && var2.getItem().equals(mod_Fossil.ChickenEss)) {
			this.setGrowingAge(this.getGrowingAge() + 2000);
		}
		return super.interact(par1EntityPlayer);
	}

	/**
	 * This function is used when two same-species animals in 'love mode' breed
	 * to generate the new baby animal.
	 */
	public EntityAnimal spawnBabyAnimal(EntityAnimal par1EntityAnimal) {
		EntityMammoth baby=new EntityMammoth(this.worldObj);
		if (this.isTamed()){
			baby.setOwner(this.getOwnerName());
			baby.setTamed(true);
		}
		return baby;
	}

	@Override
	public boolean isShearable(ItemStack item, World world, int x, int y, int z) {
		this.eatGrassTimes = 0;
		return !getSheared() && !isChild();
	}

	public void setSheared(boolean par1) {
		byte var2 = this.dataWatcher.getWatchableObjectByte(18);

		if (par1) {
			this.dataWatcher.updateObject(18, Byte.valueOf((byte) (var2 | 16)));
		} else {
			this.dataWatcher
					.updateObject(18, Byte.valueOf((byte) (var2 & -17)));
		}
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x,
			int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int i = 1 + rand.nextInt(20);
		for (int j = 0; j < i; j++) {
			ret.add(new ItemStack(Block.cloth.blockID, 1, 12)); // Brown color
																// as 12
		}
		this.setSheared(true);
		return ret;
	}

	public int getFleeceColor() {
		return this.dataWatcher.getWatchableObjectByte(18) & 15;
	}

	public void setFleeceColor(int par1) {
		byte var2 = this.dataWatcher.getWatchableObjectByte(18);
		this.dataWatcher.updateObject(18,
				Byte.valueOf((byte) (var2 & 240 | par1 & 15)));
	}

	public boolean getSheared() {
		return (this.dataWatcher.getWatchableObjectByte(18) & 16) != 0;
	}

	public void updateSize() {
		if (this.isChild())
			return;
		if (this.width != this.ADULT_SIZE_X || this.height != this.ADULT_SIZE_Y) {
			this.setSize(ADULT_SIZE_X, ADULT_SIZE_Y);
			setPosition(this.posX, this.posY, this.posZ);
		}
	}

	/**
	 * This function applies the benefits of growing back wool and faster
	 * growing up to the acting entity. (This function is used in the
	 * AIEatGrass)
	 */
	public void eatGrassBonus() {
		if (this.getSheared()) {
			eatGrassTimes++;
			if (this.eatGrassTimes >= this.EATING_TIMES_TO_GROW_FUR) {
				this.setSheared(false);
				this.eatGrassTimes = 0;
			}
		}

		if (this.isChild()) {
			int var1 = this.getGrowingAge() + 1200;

			if (var1 > 0) {
				var1 = 0;
			}

			this.setGrowingAge(var1);
		}
	}


	private boolean checkBiomeAndWeakness() {
		if (this.isChild())
			return false;
		BiomeGenBase locatBiome=this.worldObj.getBiomeGenForCoords((int)posX, (int)posZ);
		boolean inCold=isBiomeCold(locatBiome);
		boolean inHot=isBiomeHot(locatBiome);
		if (this.getSheared())
			return inCold;
		else
			return inHot;
	}

	private boolean isBiomeHot(BiomeGenBase locatBiome) {
		return isBiomeInList(this.HOT_BIOMES,locatBiome);
	}

	private boolean isBiomeCold(BiomeGenBase locatBiome) {
		return isBiomeInList(this.COLD_BIOMES,locatBiome);
	}
	private boolean isBiomeInList(BiomeGenBase[] biomeList,
			BiomeGenBase locatBiome) {
		for (int i=0;i<biomeList.length;i++)
			if (biomeList[i].equals(locatBiome)) return true;
		return false;
	}
	public EntityMammoth Imprinting(double posXparm,double posYparm,double posZparm){
		EntityPlayer nearest=worldObj.getClosestPlayer(posXparm, posYparm, posZparm, 50);
		if (nearest==null) return this;
		this.setOwner(nearest.username);
		this.setTamed(true);
		return this;
	}

	public int getSwingTick() {
		return swingTick;
	}
	
}
