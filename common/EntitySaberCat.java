package net.minecraft.src;

import java.util.*;

public class EntitySaberCat extends EntityTameable
{
    /**
     * This flag is set when the wolf is looking at a player with interest, i.e. with tilted head. This happens when
     * tamed wolf is wound and player holds porkchop (raw or cooked), or when wild wolf sees bone in player's hands.
     */
    private boolean looksWithInterest = false;
    private float field_25048_b;
    private float field_25054_c;
    private boolean isShaking;
    private boolean field_25052_g;

    /**
     * This time increases while wolf is shaking and emitting water particles.
     */
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;

    public EntitySaberCat(World world)
    {
        super(world);
        texture = "/skull/SaberCat_Adult.png";
        setSize(0.8F, 0.8F);
        moveSpeed = 0.3F;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIMate(this, this.moveSpeed));
        this.tasks.addTask(7, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(8, new EntityAIBegSC(this, 8.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 16.0F, 200, false));
        this.targetTasks.addTask(5, new EntityAITargetNonTamed(this, EntityPig.class, 16.0F, 200, false));
        this.targetTasks.addTask(6, new EntityAITargetNonTamed(this, EntityCow.class, 16.0F, 200, false));
        this.targetTasks.addTask(7, new EntityAITargetNonTamed(this, EntityChicken.class, 16.0F, 200, false));
        this.targetTasks.addTask(8, new EntityAITargetNonTamed(this, EntityVillager.class, 16.0F, 200, false));
    }

    public int getMaxHealth()
    {
        return !isTamed() ? 8 : 20;
    }
    public boolean isAIEnabled()
    {
        return true;
    }
    public void setAttackTarget(EntityLiving par1EntityLiving)
    {
        super.setAttackTarget(par1EntityLiving);

        if (par1EntityLiving instanceof EntityPlayer)
        {
            this.setAngry(true);
        }
    }
    protected void updateAITick()
    {
        this.dataWatcher.updateObject(18, Integer.valueOf(this.getHealth()));
    }
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(18, new Integer(this.getHealth()));
    }
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public String getTexture()
    {
        if (isChild())
        {
            return "/skull/SaberCat_Young.png";
        }
        else
        {
            return "/skull/SaberCat_Adult.png";
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Angry", this.isAngry());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setAngry(par1NBTTagCompound.getBoolean("Angry"));
    }

    protected boolean canDespawn()
    {
        return isAngry();
    }

    protected String getLivingSound()
    {
    	return "SaberCat_Living";
    }

    protected String getHurtSound()
    {
        return "SaberCat_Hurt";
    }

    protected String getDeathSound()
    {
        return "SaberCat_death";
    }

    protected float getSoundVolume()
    {
        return 0.4F;
    }

    protected int getDropItemId()
    {
        return -1;
    }

    protected void updateEntityActionState()
    {
        super.updateEntityActionState();
        if (!hasAttacked && !hasPath() && isTamed() && ridingEntity == null)
        {
            EntityPlayer entityplayer = worldObj.getPlayerEntityByName(getOwnerName());
            if (entityplayer != null)
            {
                float f = entityplayer.getDistanceToEntity(this);
                if (f > 5F)
                {
                    getPathOrWalkableBlock(entityplayer, f);
                }
            }
            else if (!isInWater())
            {
                setIsSitting(true);
            }
        }
        else if (entityToAttack == null && !hasPath() && !isTamed() && !isChild() && worldObj.rand.nextInt(100) == 0)
        {
        	
            List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityAnimal.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
            if (!list.isEmpty())
            {
            	EntityAnimal Tmp=(EntityAnimal)list.get(worldObj.rand.nextInt(list.size()));
                if (!(Tmp instanceof EntitySaberCat)) this.setAttackTarget(Tmp);
            }
        }else{
        	if (this.isChild() && !this.isTamed()){
        		Entity Tmp=this.getEntityToAttack();
        		if (!(Tmp instanceof EntityPlayer)) this.setAttackTarget(null);
        	}
        }
        if (isInWater())
        {
            setIsSitting(false);
        }
        if (!worldObj.isRemote)
        {
            dataWatcher.updateObject(18, Integer.valueOf(getHealth()));
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.worldObj.isRemote && this.isShaking && !this.field_25052_g && !this.hasPath() && this.onGround)
        {
            this.field_25052_g = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            this.worldObj.setEntityState(this, (byte)8);
        }
    }
    private boolean isInterest(int ItemID){
    	if (isChild()){
    		return ItemID == Item.bucketMilk.shiftedIndex;
    	}else{
    		return (ItemID == Item.beefRaw.shiftedIndex)||
    				(ItemID == Item.porkRaw.shiftedIndex)||
    				(ItemID == Item.chickenRaw.shiftedIndex)||
    				(ItemID == mod_Fossil.RawDinoMeat.shiftedIndex);
    	}
    }
    private boolean TamedInterest(int ItemID){
    	if (!this.isTamed()) return isInterest(ItemID);
    	else{
    		return (ItemID == Item.beefRaw.shiftedIndex)||
    				(ItemID == Item.porkRaw.shiftedIndex)||
    				(ItemID == Item.chickenRaw.shiftedIndex)||
    				(ItemID == mod_Fossil.RawDinoMeat.shiftedIndex);
    	}
    }
    public void onUpdate()
    {
        super.onUpdate();
        field_25054_c = field_25048_b;
        if (looksWithInterest)
        {
            field_25048_b = field_25048_b + (1.0F - field_25048_b) * 0.4F;
        }
        else
        {
            field_25048_b = field_25048_b + (0.0F - field_25048_b) * 0.4F;
        }
        if (looksWithInterest)
        {
            numTicksToChaseTarget = 10;
        }
        if (isWet())
        {
            isShaking = true;
            field_25052_g = false;
            timeWolfIsShaking = 0.0F;
            prevTimeWolfIsShaking = 0.0F;
        }
        else if ((isShaking || field_25052_g) && field_25052_g)
        {
            if (timeWolfIsShaking == 0.0F)
            {
                worldObj.playSoundAtEntity(this, "mob.wolf.shake", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }
            prevTimeWolfIsShaking = timeWolfIsShaking;
            timeWolfIsShaking += 0.05F;
            if (prevTimeWolfIsShaking >= 2.0F)
            {
                isShaking = false;
                field_25052_g = false;
                prevTimeWolfIsShaking = 0.0F;
                timeWolfIsShaking = 0.0F;
            }
            if (timeWolfIsShaking > 0.4F)
            {
                float f = (float)boundingBox.minY;
                int i = (int)(MathHelper.sin((timeWolfIsShaking - 0.4F) * 3.141593F) * 7F);
                for (int j = 0; j < i; j++)
                {
                    float f1 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
                    float f2 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
                    worldObj.spawnParticle("splash", posX + (double)f1, f + 0.8F, posZ + (double)f2, motionX, motionY, motionZ);
                }
            }
        }
    }

    public boolean getWolfShaking()
    {
        return isShaking;
    }
    /**
     * Used when calculating the amount of shading to apply while the wolf is shaking.
     */
    public float getShadingWhileShaking(float f)
    {
        return 0.75F + ((prevTimeWolfIsShaking + (timeWolfIsShaking - prevTimeWolfIsShaking) * f) / 2.0F) * 0.25F;
    }

    public float getShakeAngle(float f, float f1)
    {
        float f2 = (prevTimeWolfIsShaking + (timeWolfIsShaking - prevTimeWolfIsShaking) * f + f1) / 1.8F;
        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }
        else if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }
        return MathHelper.sin(f2 * 3.141593F) * MathHelper.sin(f2 * 3.141593F * 11F) * 0.15F * 3.141593F;
    }

    public float getInterestedAngle(float f)
    {
        return (field_25054_c + (field_25048_b - field_25054_c) * f) * 0.15F * 3.141593F;
    }

    public float getEyeHeight()
    {
        return height * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    private void getPathOrWalkableBlock(Entity entity, float f)
    {
        PathEntity pathentity = worldObj.getPathEntityToEntity(this, entity, 16F,true,false,true,false);
        if (pathentity == null && f > 12F)
        {
            int i = MathHelper.floor_double(entity.posX) - 2;
            int j = MathHelper.floor_double(entity.posZ) - 2;
            int k = MathHelper.floor_double(entity.boundingBox.minY);
            for (int l = 0; l <= 4; l++)
            {
                for (int i1 = 0; i1 <= 4; i1++)
                {
                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && worldObj.isBlockNormalCube(i + l, k - 1, j + i1) && !worldObj.isBlockNormalCube(i + l, k, j + i1) && !worldObj.isBlockNormalCube(i + l, k + 1, j + i1))
                    {
                        setLocationAndAngles((float)(i + l) + 0.5F, k, (float)(j + i1) + 0.5F, rotationYaw, rotationPitch);
                        return;
                    }
                }
            }
        }
        else
        {
            setPathToEntity(pathentity);
        }
    }

    protected boolean isMovementCeased()
    {
        return isSitting() || field_25052_g;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        Entity var3 = par1DamageSource.getEntity();
        this.aiSit.setSitting(false);

        if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow))
        {
            par2 = (par2 + 1) / 2;
        }

        return super.attackEntityFrom(par1DamageSource, par2);
    }
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        int var2 = this.isTamed() ? 4 : 2;
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
    }
    protected Entity findPlayerToAttack()
    {
        if (isAngry())
        {
            return worldObj.getClosestPlayerToEntity(this, 16D);
        }
        else
        {
            return null;
        }
    }
    public void onKillEntity(EntityLiving entityliving)
    {
    	if (entityliving instanceof EntityAnimal){
    		int i=this.getGrowingAge();
    		if (i<0) {
    			i+=3000;
    			if (i>0) i=0;
    			this.setGrowingAge(i);
    		}
    		
    		
    	}
    	super.onKillEntity(entityliving);
    }
    protected void attackEntity(Entity entity, float f)
    {
        if (f > 2.0F && f < 6F && rand.nextInt(10) == 0)
        {
            if (onGround)
            {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
                motionX = (d / (double)f1) * 0.5D * 0.80000001192092896D + motionX * 0.20000000298023224D;
                motionZ = (d1 / (double)f1) * 0.5D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
                motionY = 0.40000000596046448D;
            }
        }
        else if ((double)f < 1.5D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            attackTime = 20;
            byte byte0 = 4;
            if (!isChild())
            {
                byte0 = 8;
            }
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), byte0);
        }
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (!isTamed())
        {
            if (itemstack != null && this.isInterest(itemstack.itemID) && !isAngry())
            {
                itemstack.stackSize--;
                if (itemstack.stackSize <= 0)
                {
                	if (itemstack.getItem().hasContainerItem()){
                		entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(itemstack.getItem().getContainerItem()));
                	}
                	else entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
                if (!worldObj.isRemote)
                {
                    if (rand.nextInt(isChild()?1:3) == 0)
                    {
                        setIsTamed(true);
                        setPathToEntity(null);
                        this.aiSit.setSitting(true);
                        setEntityHealth(20);
                        setOwner(entityplayer.username);
                        showHeartsOrSmokeFX(true);
                        worldObj.setEntityState(this, (byte)7);
                    }
                    else
                    {
                        showHeartsOrSmokeFX(false);
                        worldObj.setEntityState(this, (byte)6);
                    }
                }
                return true;
            }
        }
        else
        {
            if (itemstack != null && (Item.itemsList[itemstack.itemID] instanceof ItemFood))
            {
                ItemFood itemfood = (ItemFood)Item.itemsList[itemstack.itemID];
                if (this.TamedInterest(itemstack.itemID) && dataWatcher.getWatchableObjectInt(18) < 20)
                {
                    itemstack.stackSize--;
                    heal(itemfood.getHealAmount());
                    if (itemstack.stackSize <= 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }
                    return true;
                }
                if (itemfood==Item.rottenFlesh && this.getGrowingAge()<0){
                	int i=this.getGrowingAge();
                	i-=3000;
                	this.setGrowingAge(i);
                	return true;
                }
            }
            if (entityplayer.username.equalsIgnoreCase(getOwnerName()))
            {
                if (!worldObj.isRemote)
                {
                    this.aiSit.setSitting(!this.isSitting());
                    isJumping = false;
                    setPathToEntity(null);
                }
                return true;
            }
        }
        return super.interact(entityplayer);
    }

    void showHeartsOrSmokeFX(boolean flag)
    {
        String s = "heart";
        if (!flag)
        {
            s = "smoke";
        }
        for (int i = 0; i < 7; i++)
        {
            double d = rand.nextGaussian() * 0.02D;
            double d1 = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            worldObj.spawnParticle(s, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
        }
    }

    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 8)
        {
            this.field_25052_g = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }

    public float setTailRotation()
    {
        return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.55F - (float)(20 - this.dataWatcher.getWatchableObjectInt(18)) * 0.02F) * (float)Math.PI : ((float)Math.PI / 5F));
    } 

    public int getMaxSpawnedInChunk()
    {
        return 8;
    }
    /**
     * Checks if the parameter is an wheat item.
     */
    public boolean isWheat(ItemStack par1ItemStack)
    {
        return par1ItemStack == null ? false : (!(Item.itemsList[par1ItemStack.itemID] instanceof ItemFood) ? false : ((ItemFood)Item.itemsList[par1ItemStack.itemID]).isWolfsFavoriteMeat());
    }




    public void setIsSitting(boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);
        if (flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 1)));
        }
        else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -2)));
        }
    }

    public boolean isAngry()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    public void setAngry(boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);
        if (flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 2)));
        }
        else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -3)));
        }
    }

    public boolean isTamed()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 4) != 0;
    }

    public void setIsTamed(boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);
        if (flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 4)));
        }
        else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -5)));
        }
    }

    public EntityAnimal spawnBabyAnimal(EntityAnimal par1EntityAnimal)
    {
        EntitySaberCat var2 = new EntitySaberCat(this.worldObj);
        var2.setOwner(this.getOwnerName());
        var2.setTamed(true);
        return var2;
    }
    public void func_48150_h(boolean par1)
    {
        this.looksWithInterest = par1;
    }
    public boolean func_48135_b(EntityAnimal par1EntityAnimal)
    {
        if (par1EntityAnimal == this)
        {
            return false;
        }
        else if (!this.isTamed())
        {
            return false;
        }
        else if (!(par1EntityAnimal instanceof EntityWolf))
        {
            return false;
        }
        else
        {
            EntityWolf var2 = (EntityWolf)par1EntityAnimal;
            return !var2.isTamed() ? false : (var2.isSitting() ? false : this.isInLove() && var2.isInLove());
        }
    }
}
