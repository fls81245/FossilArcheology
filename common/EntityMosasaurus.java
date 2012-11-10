package net.minecraft.src;
import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;
public class EntityMosasaurus extends EntityDinosaurce implements IWaterDino{
	public final int Areas=60;
	public final float HuntLimit=getHungerLimit()*4/5;
    private boolean looksWithInterest;
    private float field_25048_b;
    private float field_25054_c;
    private boolean field_25052_g;
	public float TargetY=0;
	public EntityMosasaurus(World world)
    {
        super(world);
        SelfType=EnumDinoType.Mosasaurus;
        looksWithInterest = false;
        texture = "/skull/Mosasaurus.png";
        setSize(0.5F, 0.5F);
        moveSpeed =0.3F;
        health = 10;
		attackStrength=4+2*this.getDinoAge();
		this.moveSpeed = 0.3F;
		this.getNavigator().setCanSwim(true);
		this.tasks.addTask(0, new DinoAIGrowup(this, 8));
		this.tasks.addTask(0, new DinoAIStarvation(this));
        this.tasks.addTask(1, new WaterDinoAISwimming(this,true,FLOAT_SPEED,-SINK_SPEED).setDiveAtNight());
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        this.tasks.addTask(4, new WaterDinoAIWander(this, this.moveSpeed,0.003F));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new WaterDinoAINearestAttackableTarget(this, EntityNautilus.class, 16.0F, 0, true));
        this.targetTasks.addTask(3, new WaterDinoAINearestAttackableTarget(this, EntitySquid.class, 16.0F, 0, true));
        this.targetTasks.addTask(4, new WaterDinoAINearestAttackableTarget(this, EntityAnimal.class, 16.0F, 0, true));
        this.targetTasks.addTask(5, new WaterDinoAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, false));
    }
	public int getHungerLimit(){
		return 500;
	}
    public boolean isAIEnabled()
    {
        return (!this.isModelized() && this.riddenByEntity==null);
    }
	protected boolean canTriggerWalking()
    {
        return false;
    }

	public String getTexture()
    {
        //if(isSelfTamed())
        //{
            //return "/skull/Triceratops_Tamed.png";
        //}
        //if(isSelfAngry())
        //{
            //return "/skull/Triceratops_angry.png";
        //} else
        //{
            return super.getTexture();
        //}
    }

	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);

        nbttagcompound.setBoolean("Angry", isSelfAngry());

    }
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);

        setSelfAngry(nbttagcompound.getBoolean("Angry"));
		InitSize();


    }
	protected boolean canDespawn()
    {
        return false;
    }
	protected String getLivingSound()
    {
        //if(isWolfAngry())
        //{
            return "";
        //}
        //if(rand.nextInt(3) == 0)
        //{
            //if(isWolfTamed() && dataWatcher.getWatchableObjectInt(18) < 10)
            //{
                //return "mob.wolf.whine";
            //} else
            //{
              //  return "mob.wolf.panting";
            //}
        //} else
        //{
            //return "mob.wolf.bark";
        //}
    }
	protected String getHurtSound()
    {
        return "";
    }

    protected String getDeathSound()
    {
        return "";
    }
	protected void updateEntityActionState()
    {
		if (this.riddenByEntity==null) super.updateEntityActionState();

    }
    public boolean getCanSpawnHere()
    {
        return worldObj.checkIfAABBIsClear(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && worldObj.isAnyLiquid(boundingBox);
    }

    public boolean canBreatheUnderwater()
    {
        return true;
    }

	public boolean isOnSurface(){
		AxisAlignedBB boxTmp=this.boundingBox;
		AxisAlignedBB checkBoxLower=AxisAlignedBB.getBoundingBox(boxTmp.minX, boxTmp.minY, boxTmp.minZ, boxTmp.maxX, boxTmp.maxY/2F, boxTmp.maxZ);
		AxisAlignedBB checkBoxUpper=AxisAlignedBB.getBoundingBox(boxTmp.minX, boxTmp.minY+(boxTmp.maxY-boxTmp.minY)/4, boxTmp.minZ, boxTmp.maxX, boxTmp.maxY, boxTmp.maxZ);
		return (worldObj.isAABBInMaterial(checkBoxLower, Material.water) && (worldObj.isAABBInMaterial(checkBoxLower, Material.air)));
	}
	public void moveEntityWithHeading(float f, float f1)
    {
        if(isInWater())
        {
        	if (this.isOnSurface() && motionY>0) motionY=0;
            double d = posY;
            moveFlying(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            //motionX *= 0.80000001192092896D;
            //motionY *= 0.80000001192092896D;
            //motionZ *= 0.80000001192092896D;
            //motionY -= 0.02D;
            /*if(isCollidedHorizontally && isOffsetPositionInLiquid(motionX, ((motionY + 0.60000002384185791D) - posY) + d, motionZ))
            {
                motionY = 0.30000001192092896D;
            }*/
        } else
        if(handleLavaMovement())
        {
            double d1 = posY;
            moveFlying(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.5D;
            motionY *= 0.5D;
            motionZ *= 0.5D;
            motionY -= 0.02D;
            if(isCollidedHorizontally && isOffsetPositionInLiquid(motionX, ((motionY + 0.60000002384185791D) - posY) + d1, motionZ))
            {
                motionY = 0.30000001192092896D;
            }
        } else
        {
            motionX *= 0.80000001192092896D;
            motionY *= 0.80000001192092896D;
            motionZ *= 0.80000001192092896D;
            float f2 = 0.91F;
            if(onGround)
            {
                f2 = 0.5460001F;
                int i = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if(i > 0)
                {
                    f2 = Block.blocksList[i].slipperiness * 0.91F;
                }
            }
            float f3 = 0.1627714F / (f2 * f2 * f2);
            float f4 = onGround ? landMovementFactor * f3 : jumpMovementFactor;
            moveFlying(f, f1, f4);
            f2 = 0.91F;
            if(onGround)
            {
                f2 = 0.5460001F;
                int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if(j > 0)
                {
                    f2 = Block.blocksList[j].slipperiness * 0.91F;
                }
            }
            if(isOnLadder())
            {
                float f5 = 0.15F;
                if(motionX < (double)(-f5))
                {
                    motionX = -f5;
                }
                if(motionX > (double)f5)
                {
                    motionX = f5;
                }
                if(motionZ < (double)(-f5))
                {
                    motionZ = -f5;
                }
                if(motionZ > (double)f5)
                {
                    motionZ = f5;
                }
                fallDistance = 0.0F;
                if(motionY < -0.14999999999999999D)
                {
                    motionY = -0.14999999999999999D;
                }
                if(isSneaking() && motionY < 0.0D)
                {
                    motionY = 0.0D;
                }
            }
            moveEntity(motionX, motionY, motionZ);
            if(isCollidedHorizontally && isOnLadder())
            {
                motionY = 0.20000000000000001D;
            }
            if (!this.isInWater())motionY -= 0.080000000000000002D;
            motionY *= 0.98000001907348633D;
            motionX *= f2;
            motionZ *= f2;
        }
        prevLegYaw= legYaw;
        double d2 = posX - prevPosX;
        double d3 = posZ - prevPosZ;
        float f6 = MathHelper.sqrt_double(d2 * d2 + d3 * d3) * 4F;
        if(f6 > 1.0F)
        {
            f6 = 1.0F;
        }
        legYaw += (f6 - legYaw) * 0.4F;
        legSwing += legYaw;
    }

	public void onLivingUpdate()
    {
		if (this.motionY<=0.0f && this.isInWater()){
			if(this.getNavigator().noPath())	//Idle sinking
				this.motionY=this.IDLE_SINK_SPEED;	//In Water and sinking by gravity,so changing sinking speed.
			else
				this.motionY=this.SINK_SPEED;
		}
        super.onLivingUpdate();
    }
    public boolean getSelfShaking()
    {
        return false;
    }
	/*public float getShadingWhileShaking(float f)
    {
        return 0.75F + ((prevTimeWolfIsShaking + (timeWolfIsShaking - prevTimeWolfIsShaking) * f) / 2.0F) * 0.25F;
    }
	public float getShakeAngle(float f, float f1)
    {
        float f2 = (prevTimeWolfIsShaking + (timeWolfIsShaking - prevTimeWolfIsShaking) * f + f1) / 1.8F;
        if(f2 < 0.0F)
        {
            f2 = 0.0F;
        } else
        if(f2 > 1.0F)
        {
            f2 = 1.0F;
        }
        return MathHelper.sin(f2 * 3.141593F) * MathHelper.sin(f2 * 3.141593F * 11F) * 0.15F * 3.141593F;
    }*/



	public float getEyeHeight()
    {
        return height * 0.2F;
    }
	public int getVerticalFaceSpeed()
    {
        if(isSelfSitting())
        {
            return 20;
        } else
        {
            return super.getVerticalFaceSpeed();
        }
    }
	/*private boolean FindSJL(int range){
		if (!isSelfSitting()){
			List NearBy=worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.0D, 2.0D, 1.0D));
			if (NearBy!=null){
				for(int i = 0; i < NearBy.size(); i++)
				{	
					Entity Tmp=(Entity)NearBy.get(i);
					if (Tmp instanceof EntityItem && Tmp.isEntityAlive()){
						EntityItem ItemInRange=(EntityItem)Tmp;
						if (ItemInRange.item.itemID==mod_Fossil.SJL.shiftedIndex){
							ItemInMouth=ItemInRange.item;
							worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((new Random().nextFloat() - new Random().nextFloat()) * 0.7F + 1.0F) * 2.0F);
							ItemInRange.setDead();
							return true;
						}
					}
				}		
			}
			EntityItem TargetItem=null;
			List searchlist=worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityItem.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(range, 4D, range));
			Iterator iterator = searchlist.iterator();
			do
			{
				if(!iterator.hasNext())
				{
					break;
				}
				EntityItem entity1 = (EntityItem)iterator.next();
				if (entity1.item.itemID==mod_Fossil.SJL.shiftedIndex){
					if (TargetItem!=null){
						if (GetDistanceWithEntity(entity1)<GetDistanceWithEntity(TargetItem)){
							TargetItem	= (EntityItem)entity1;
						}
					}else{
						TargetItem	= (EntityItem)entity1;
					}
				}
			} while(true);
			if (TargetItem!=null){
				setPathToEntity(worldObj.getPathEntityToEntity(this,TargetItem,(float)range, true, false, true, false));
				TargetY=(float)TargetItem.posY;
				return true;
			}else return false;
		}else return false;	
	}*/
	public void getPathOrWalkableBlock(Entity entity, float f)
    {
        PathEntity pathentity = worldObj.getPathEntityToEntity(this, entity, 16F, true, false, true, false);
        if(pathentity == null && f > 12F)
        {
        	TargetY=(float)entity.posY;
            int i = MathHelper.floor_double(entity.posX) - 2;
            int j = MathHelper.floor_double(entity.posZ) - 2;
            int k = MathHelper.floor_double(entity.boundingBox.minY);
            for(int l = 0; l <= 4; l++)
            {
                for(int i1 = 0; i1 <= 4; i1++)
                {
                    if((l < 1 || i1 < 1 || l > 3 || i1 > 3) && worldObj.isBlockNormalCube(i + l, k - 1, j + i1) && !worldObj.isBlockNormalCube(i + l, k, j + i1) && !worldObj.isBlockNormalCube(i + l, k + 1, j + i1))
                    {
                        setLocationAndAngles((float)(i + l) + 0.5F, k, (float)(j + i1) + 0.5F, rotationYaw, rotationPitch);
                        return;
                    }
                }

            }

        } else
        {
            setPathToEntity(pathentity);
        }
    }
	protected boolean isMovementCeased()
    {
        return isSelfSitting() || field_25052_g;
    }
	/*public boolean attackEntityFrom(DamageSource damagesource, int i)//being attack
    {
		Entity entity = damagesource.getEntity();
		//if (i<8 && entity!=null) return false;
		if ((i==20)) return super.attackEntityFrom(damagesource, 200);
		if (entity!=null){
			this.ItemInMouth=null;
			//this.setSelfAngry(true);
			this.setTarget((EntityLiving)entity);
		}
		return super.attackEntityFrom(damagesource, i);
		
    }*/
	protected Entity findPlayerToAttack()
    {
        if(isSelfAngry())
        {
            return worldObj.getClosestPlayerToEntity(this, 16D);
        } else
        {
            return null;
        }
    }
	protected void attackEntity(Entity entity, float f)
    {
		//mod_Fossil.ShowMessage(new StringBuilder().append("Start attack").append(entity.toString()).append(",").append(f).append(",").append(width*1.6).toString());
        if(f > width*1.6 && f < Areas && entity.isInWater())
        {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
                motionX = (d / (double)f1) * 0.5D * 0.80000001192092896D + motionX * 0.20000000298023224D;
                motionZ = (d1 / (double)f1) * 0.5D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
                TargetY=(float) entity.posY;
        } else
        if((double)f <= width*1.6 && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), attackStrength);
        }
    }
	public void onKillEntity(EntityLiving entityliving)
    {
		super.onKillEntity(entityliving);
        if(entityliving instanceof EntityPig){
			HandleEating(30);
			//return;
		}
		if(entityliving instanceof EntitySheep){
			HandleEating(35);
			//return;
		}
		if(entityliving instanceof EntityCow){
			HandleEating(50);
			//return;
		}
		if(entityliving instanceof EntityChicken){
			HandleEating(20);
			//return;
		}
		if(entityliving instanceof EntityMob){
			HandleEating(20);
			//return;
		}
		if (entityliving instanceof EntityNautilus){
			HandleEating(100);
		}
		heal(5);
		
    }
	public boolean interact(EntityPlayer entityplayer)
    {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (FMLCommonHandler.instance().getSide().isClient()){
			if (itemstack!=null && itemstack.itemID==mod_Fossil.DinoPedia.shiftedIndex){
	        	//this.ShowPedia(entityplayer);			//old function
	        	EntityDinosaurce.pediaingDino=this;		//new function
	        	mod_Fossil.callGUI(entityplayer, FossilGuiHandler.DINOPEDIA_GUI_ID, worldObj, (int)(this.posX), (int)(this.posY), (int)(this.posZ));
			return true;
		}
		}

		if (itemstack!=null && itemstack.itemID==mod_Fossil.ChickenEss.shiftedIndex){
			if (this.getDinoAge()>=8|| this.getHunger()<=0)return false;
			itemstack.stackSize--;
            if(itemstack.stackSize <= 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle,1));
			this.setDinoAgeTick(GROW_TIME_COUNT);
			this.setHunger(1+new Random().nextInt(this.getHunger()));
			return true;
		}
		if (!this.isTamed()){
			return false;
		}else{
			 if(!worldObj.isRemote && (riddenByEntity == null || riddenByEntity == entityplayer))
		        {
				 	entityplayer.rotationYaw=this.rotationYaw;
		            entityplayer.mountEntity(this);
		            this.setPathToEntity(null);
		            this.renderYawOffset=this.rotationYaw;
		            return true;
		        } else
		        {
		            return false;
		        }
		}
    }

	/*public void handleHealthUpdate(byte byte0)
    {
        if(byte0 == 7)
        {
            showHeartsOrSmokeFX(true);
        } else
        if(byte0 == 6)
        {
            showHeartsOrSmokeFX(false);
        } else
        if(byte0 == 8)
        {
            field_25052_g = true;
            timeWolfIsShaking = 0.0F;
            prevTimeWolfIsShaking = 0.0F;
        } else
        {
            super.handleHealthUpdate(byte0);
        }
    }*/
	public int getMaxSpawnedInChunk()
    {
        return 200;
    }
	
	public boolean isSelfAngry()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }
	public boolean isSelfSitting()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }
	public void setSelfAngry(boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);
		if (flag!=isSelfAngry()){
			if(flag)
			{
				dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 2)));
				moveSpeed=2.0F;

			} else
			{
				dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -3)));
			}
		}
    }
	public void setSelfSitting(boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);
        if(flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 1)));
        } else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -2)));
        }
    }

	/*protected void GrowUp(){
		int preage=age;
		if (this.riddenByEntity==null){
			if (age<=8){
			AgeTick++;
				if(AgeTick>=GROW_TIME_COUNT){
					//mod_Fossil.ShowMessage((new StringBuilder()).append((int)Math.round(0.1*age+0.3)).toString());
						AgeTick=0;
						age++;
						if (health<200) health+=23;
						setSize((float)(0.5F+0.5125*(float)age),(float)(0.5F+0.5125*(float)age));
						setPosition(posX,posY,posZ);
					if (!CheckSpace()){	
						age--;
						if (health>23) health-=23;
						setSize((float)(0.5F+0.5125*(float)age),(float)(0.5F+0.5125*(float)age));
						setPosition(posX,posY,posZ);
						//if (isSelfTamed()) mod_Fossil.ShowMessage("A T-Rex hasn't enough space to grow."); 
					}
					//if (age>=3 && !this.isSelfTamed()) texture="/skull/TRex_Adult.png";
					//else texture="/skull/TRex.png";
					attackStrength=4+2*age;
					moveSpeed = 0.5F+0.4F*age;
				}
			}
			//if (preage!=age && preage==2 && this.age==3) this.setPathToEntity(null);
		}
	}*/
	private void InitSize(){
		setSize((float)(0.5F+0.5125*(float)this.getDinoAge()),(float)(0.5F+0.5125*(float)this.getDinoAge()));
		setPosition(posX,posY,posZ);
		//if (age>3 && !this.isSelfTamed()) texture="/skull/TRex_Adult.png";
		//else texture="/skull/TRex.png";
		attackStrength=4+2*this.getDinoAge();
		moveSpeed = 0.5F+0.4F*this.getDinoAge();
	}
	public boolean CheckSpace(){
		if (this.isCollidedHorizontally) return false;
		if (!this.isInWater()) return false;
		else{
	        for(int i = 0; i < 8; i++)
	        {
	            float f = ((float)((i >> 0) % 2) - 0.5F) * width * 0.9F;
	            float f1 = ((float)((i >> 1) % 2) - 0.5F) * 0.1F;
	            float f2 = ((float)((i >> 2) % 2) - 0.5F) * width * 0.9F;
	            int j = MathHelper.floor_double(posX + (double)f);
	            int k = MathHelper.floor_double(posY + (double)getEyeHeight() + (double)f1);
	            int l = MathHelper.floor_double(posZ + (double)f2);
	            Block block = Block.blocksList[worldObj.getBlockId(i, j, k)];
	            if(block!=null && (block!=block.waterStill && block!=block.waterMoving))
	            {
	                return false;
	            }
	        }
	        return true;
		}
	}
	
	/*protected boolean HandleHunger(){
		if (EatTick>0) EatTick--;
		if (worldObj.difficultySetting > 0 && riddenByEntity == null &&(this.isTamed() || worldObj.getClosestPlayerToEntity(this, (double)(this.Areas+24))!=null))
		{
			//mod_Fossil.ShowMessage(new StringBuilder().append(Hunger).toString());
			HungerTick--;
			if (HungerTick<=0){
				Hunger--;
				HungerTick=HungerTickLimit;
				HandleStarvation();
				return true;
			}
			//HandleStarvation();
			return false;
		}else{
			if (this.Hunger<HungerLimit) this.Hunger++;
			return true;
		}
	}*/
	public boolean HandleEating(int FoodValue){
		if (this.getHunger()>=getHungerLimit()) {
			return false;
		}
		this.increaseHunger(FoodValue);
		showHeartsOrSmokeFX(false);
		if (this.getHunger()>=getHungerLimit()) this.setHunger(getHungerLimit());
		return true;
	}
	/*protected void HandleStarvation(){
		//mod_Fossil.ShowMessage("Starvation");
		if (Hunger<=0) {
			if (!isTamed())  SendStatusMessage(EnumSituation.Starve,this.SelfType);
			attackEntityFrom(DamageSource.starve, 20);
			//mod_Fossil.ShowMessage("Died:Hungry");
			return;
		}
		if (Hunger==HungerLimit/2 && HungerTick==HungerTickLimit/10) {
			if (isTamed()) SendStatusMessage(EnumSituation.Hungry,this.SelfType);
			return;
		}
	}*/
	public void updateRiderPosition()
    {
        if(riddenByEntity == null)
        {
            return;
        } else
        {
            riddenByEntity.setPosition(posX, posY+this.getGLY()*1.5, posZ);
           //riddenByEntity.setRotation(this.rotationYaw,riddenByEntity.rotationPitch);
           //((EntityLiving)riddenByEntity).renderYawOffset=this.rotationYaw;
            return;
        }
    }
	private void Flee(Entity EscapeFrom,int range){
		int DistanceX=new Random().nextInt(range)+1;
		int DistanceZ=(int)Math.round(Math.sqrt(Math.pow(range,2)-Math.pow(DistanceX,2)));
		int TargetX=0;
		int TargetY=0;
		int TargetZ=0;
		if (EscapeFrom.posX<=posX) TargetX=(int)Math.round(posX)+DistanceX;
		else TargetX=(int)Math.round(posX)-DistanceX;
		if (EscapeFrom.posZ<=posZ) TargetZ=(int)Math.round(posZ)+DistanceZ;
		else TargetZ=(int)Math.round(posZ)-DistanceZ;
		for(int i=128;i>0;i--){
			if (!worldObj.isAirBlock(TargetX, i,TargetZ)){
				TargetY=i;
				break;
			}
		}
		setTamed(false);
		setSelfSitting(false);	
		setPathToEntity(worldObj.getEntityPathToXYZ((Entity)this,TargetX,TargetY,TargetZ,(float)(range), true, false, true, false));
		//mod_Fossil.ShowMessage(new StringBuilder().append("Escaping to:").append(TargetX).append(",").append(TargetY).append(",").append(TargetZ).toString());

	}
	private boolean HuntForPrey(int range){
		if ((this.getEntityToAttack()==null)||!(this.getEntityToAttack() instanceof EntityPlayer)){
				//mod_Fossil.ShowMessage("Hunt Time!!"); 
				EntityLiving targetLiving=null;
				EntityLiving TempLiving=null;
				float NearestDistance=(float)range*2;
				List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(range, range*2, range));
				Iterator iterator = list.iterator();
				do{
					if(!iterator.hasNext())
						{
							break;
						}
						TempLiving = (EntityLiving)iterator.next();
						//mod_Fossil.ShowMessage("Target sighted"); 
						//if (TempLiving instanceof EntityPig || TempLiving instanceof EntityCow || TempLiving instanceof EntitySheep||TempLiving instanceof EntityChicken){
							//mod_Fossil.ShowMessage(new StringBuilder().append("Target Distance:").append(GetDistanceWithEntity((Entity)TempLiving)).toString());
							//mod_Fossil.ShowMessage(new StringBuilder().append("Nearest Distance:").append(NearestDistance).toString());
						if (!(TempLiving instanceof EntityPlayer)){	
							if (TempLiving instanceof EntitySquid || TempLiving instanceof EntityNautilus){
							if (GetDistanceWithEntity((Entity)TempLiving)<NearestDistance){
									NearestDistance=GetDistanceWithEntity((Entity)TempLiving);
									targetLiving=TempLiving;
								}
							}
						}
						//}
				} while(true);
				if (targetLiving!=null){
					setTarget(targetLiving);
					//mod_Fossil.ShowMessage(new StringBuilder().append("Target set at").append(targetLiving.toString()).toString()); 
					return true;
				}else return false;
			}else return false;
		
	}
    protected void jump()
    {
    	this.isJumping=false;
    	return;
    }
	public void heal(int i)
    {
        if(health <= 0)
        {
            return;
        }
        health += i;
        if(health > 200)
        {
            health = 200;
        }
        //heartsLife = heartsHalvesLife / 2;
    }
	



	/*private boolean FindFeeder(int range){ if(range>6) range=6;
		TileEntityFeeder target=null;
		if (GetNearestTileEntity(2,range)!=null){
			target=(TileEntityFeeder)GetNearestTileEntity(2,range);
			if (GetDistanceWithTileEntity((TileEntity)target)>1){
				//mod_Fossil.ShowMessage(new StringBuilder().append("Setting Path:").append(target.xCoord).append(",").append(target.yCoord).append(",").append(target.zCoord).toString());
				setPathToEntity(worldObj.getEntityPathToXYZ((Entity)this,(int)Math.round(target.xCoord),(int)Math.round(target.yCoord),(int)Math.round(target.zCoord),(float)range, true, false, true, false));
				
			}else{
				if (target.CheckIsEmpty(this))return false;
				//FaceToCoord(target.xCoord,target.yCoord,target.zCoord);
				target.Feed(this);
			}
			return true;
		}else return false;
	}
	private TileEntity GetNearestTileEntity(int targetType,int range){
		final int Chest=0;
		final int Furance=1;
		final int Feeder=2;
		TileEntity result=null;
		TileEntity tmp=null;
		float Distance=range+1;
		for (int i=(int)Math.round(posX)-range;i<=(int)Math.round(posX)+range;i++){
			for (int j=(int)Math.round(posY)-range/2;j<=(int)Math.round(posY)+range/2;j++){
				for (int k=(int)Math.round(posZ)-range;k<=(int)Math.round(posZ)+range;k++){
					//mod_Fossil.ShowMessage(new StringBuilder().append(worldObj.getBlockTileEntity(i,j,k)).toString());
					//mod_Fossil.ShowMessage(new StringBuilder().append(i).append(",").append(j).append(",").append(k).toString());
					tmp=worldObj.getBlockTileEntity(i,j,k);
					if (tmp!=null){
						if((tmp instanceof TileEntityFeeder)&&(!((TileEntityFeeder)tmp).CheckIsEmpty(this))){
							float TempDis=GetDistanceWithTileEntity(tmp);
							//mod_Fossil.ShowMessage(new StringBuilder().append(TempDis).toString());
							if (TempDis<Distance) {
								Distance=TempDis;
								result=tmp;
							}
						}
					}
				}
			}
		}
		return result;
	}*/
	


		public void ShowPedia(EntityPlayer checker){
			//if (worldObj.isRemote) return;
			PediaTextCorrection(SelfType,checker);
			if (this.isTamed()){

					mod_Fossil.ShowMessage(new StringBuilder().append(OwnerText).append(this.getOwnerName()).toString(),checker);
					mod_Fossil.ShowMessage(new StringBuilder().append(AgeText).append(this.getDinoAge()).toString(),checker);
					mod_Fossil.ShowMessage(new StringBuilder().append(HelthText).append(this.health).append("/").append(20).toString(),checker);
					mod_Fossil.ShowMessage(new StringBuilder().append(HungerText).append(this.getHunger()).append("/").append(this.getHungerLimit()).toString(),checker);
				

				//if (this.age>=4 && this.isSelfTamed()) mod_Fossil.ShowMessage(" * Ride-able.");
			}else{
				mod_Fossil.ShowMessage(CautionText,checker);
			}
			
		}
		@Override
		public String[] additionalPediaMessage(){
			String[] result=null;
			if (!this.isTamed()){
				result=new String[1];
				result[0]=UntamedText;
			}
			return result;
		}
		/*public void HandleBreed(){
			if (this.age>4){
				this.BreedTick--;
				if (this.BreedTick==0){
					int GroupAmount=0;
					List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(32D, 32D, 32D));
					for (int i=0;i<list.size();i++){
						if (list.get(i) instanceof EntityMosasaurus) GroupAmount++;
					}
					if (GroupAmount>50) GroupAmount=50;
					if (GroupAmount>80) return;
					if (new Random().nextInt(100)<GroupAmount){
						EntityLiving entityliving=null;
						entityliving = (EntityAnimal)new EntityMosasaurus(worldObj);
						//if (new Random().nextInt(2)==0)	((EntityRaptor)entityliving).ChangeSubType(2);
						//if (worldObj.getBlockId(i, j, k)==Block.snow.blockID) ((EntityRaptor)entityliving).ChangeSubType(1);
						//((EntityPlesiosaur)entityliving).SubSpecies=this.SubSpecies;
						if (this.isTamed()){
							((EntityMosasaurus)entityliving).setOwner(this.getOwnerName());
							((EntityMosasaurus)entityliving).setTamed(true);
						}
						entityliving.setLocationAndAngles(this.posX+(new Random().nextInt(3)-1), this.posY, this.posZ+(new Random().nextInt(3)-1), worldObj.rand.nextFloat() * 360F, 0.0F);
						if(	worldObj.checkIfAABBIsClear(entityliving.boundingBox) && worldObj.getCollidingBoundingBoxes(entityliving, entityliving.boundingBox).size() == 0) worldObj.spawnEntityInWorld(entityliving);
						showHeartsOrSmokeFX(true);
					}
					this.BreedTick=3000;
				}
			}
		}*/
		public void HandleBoatSinking(){
			EntityBoat TargetBoat=null;
			List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityBoat.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(10, 4, 10));
			Iterator iterator = list.iterator();
			do{
				if(!iterator.hasNext())
					{
						break;
					}
					TargetBoat = (EntityBoat)iterator.next();
					if (TargetBoat.isInWater()) break;
			} while(true);
			if (TargetBoat!=null){
				if (TargetBoat.riddenByEntity!=null){
					TargetBoat.riddenByEntity.mountEntity(TargetBoat);
				}
				TargetBoat.attackEntityFrom(DamageSource.causeMobDamage(this), 50);
			}
			
		}
		public void SetOrder(EnumOrderType input){
			this.OrderStatus=input;
		
		}
		public boolean HandleEating(int FoodValue, boolean FernFlag) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public EntityAnimal spawnBabyAnimal(EntityAnimal entityanimal) {
			return new EntityMosasaurus(worldObj);
		}
		@Override
		public int getMaxHealth() {
			return 200;
		}
		@Override
		protected void updateSize(boolean shouldAddAge) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public EnumOrderType getOrderType() {
			return this.OrderStatus;
		}
		@Override
		protected int foodValue(Item asked) {
			if (asked==mod_Fossil.SJL) return 50;
			return 0;
		}
		@Override
		public void HoldItem(Item itemGot) {
			return;
			
		}
		@Override
		public float getGLX() {
			return (float)(0.5F+0.5125*this.getDinoAge());
		}
		@Override
		public float getGLY() {
			return (float)(0.5F+0.5125*this.getDinoAge());
		}
}