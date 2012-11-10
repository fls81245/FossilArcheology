package net.minecraft.src;
import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;

public class EntityPterosaur extends EntityDinosaurce{
	protected  final int AGE_LIMIT = 8;
	public final float HuntLimit=getHungerLimit()*4/5;
    private boolean looksWithInterest;
    private float field_25048_b;
    private float field_25054_c;
    private boolean isWolfShaking;
    private boolean field_25052_g;
	public ItemStack ItemInMouth=null;
	public int LearningChestTick=900;
	public int SubType=0;
	public int BreedTick=3000;
	public float AirSpeed=0.0F;
	public float AirAngle=0.0F;
	public float AirPitch=0.0F;
	public float LastAirPitch=0.0F;
	public boolean Landing=false;
	public EntityPterosaur(World world) 
    {
        super(world);
        SelfType=EnumDinoType.Pterosaur;
        looksWithInterest = false;
        this.CheckSkin();
        setSize(0.8F, 0.8F);
        moveSpeed = 2.0F;
        health = 10;
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new DinoAIGrowup(this, AGE_LIMIT));
		this.tasks.addTask(0, new DinoAIStarvation(this));
		// TODO:Breeding
		this.tasks.addTask(1, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityTRex.class,
				8.0F, 0.3F, 0.35F));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityBrachiosaurus.class,
				8.0F, 0.3F, 0.35F));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, this.moveSpeed,
				true));
		this.tasks.addTask(5, new DinoAIFollowOwner(this, this.moveSpeed, 5F,
				2.0F));
		this.tasks.addTask(6, new DinoAIUseFeeder(this, this.moveSpeed, 24,
				this.HuntLimit, EnumDinoEating.Carnivorous));
        this.tasks.addTask(6, new DinoAIPickItem(this,Item.fishRaw,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(6, new DinoAIPickItem(this,Item.fishCooked,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(6, new DinoAIPickItem(this,mod_Fossil.SJL,this.moveSpeed*2,24,this.HuntLimit));
		this.tasks.addTask(7, new EntityAIWander(this, 0.3F));
		//this.tasks.addTask(8, new EntityAIWatchClosest(this,
				//EntityPlayer.class, 8.0F));
		//this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
    }

	protected boolean canTriggerWalking()
    {
        return false;
    }

    public boolean isAIEnabled()
    {
    	if (this.isModelized()) return false;
    	if (this.riddenByEntity!=null) return false;
        return true;
    }

	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Angry", isSelfAngry());


        nbttagcompound.setFloat("Airspeed",this.AirSpeed);
        nbttagcompound.setFloat("AirAngle",this.AirAngle);
        nbttagcompound.setFloat("AirPitch",this.AirPitch);
    }
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);

		
        setSelfAngry(nbttagcompound.getBoolean("Angry"));
        setSelfSitting(nbttagcompound.getBoolean("Sitting"));
		
        if (nbttagcompound.hasKey("SubType")){
        	this.SubType=nbttagcompound.getInteger("SubType");    	
        }
		InitSize();
        this.AirSpeed=nbttagcompound.getFloat("Airspeed");
        this.AirAngle=nbttagcompound.getFloat("AirAngle");
        this.AirPitch=nbttagcompound.getFloat("AirPitch");
    }
	protected boolean canDespawn()
    {
        return false;
    }
	protected String getLivingSound()
    {
            //return "mob.wolf.growl";
		if (worldObj.getClosestPlayerToEntity(this, 8D)!=null) return "PTS_living";
		else return null;
    }
	protected String getHurtSound()
    {
        return "PTS_hurt";
		//return "raptor_living";
    }

    protected String getDeathSound()
    {
        return "raptor_death";
    }
	public boolean getCanSpawnHere()
    {
        return worldObj.checkIfAABBIsClear(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.isAnyLiquid(boundingBox);
    }
	
	
	public void onLivingUpdate()
    {
		this.HandleRiding();
		super.onLivingUpdate();
		
		
        //chicken code
        /*if(!onGround && motionY < 0.0D && this.moveForward>=0.1F && this.getDinoAge()>=5)
        {
            motionY *= 0.1D;
        }*/
        


        
	
    }

	public void onUpdate()
    {
        super.onUpdate();
		//HangleChestLearning();
        field_25054_c = field_25048_b;
        if(looksWithInterest)
        {
            field_25048_b = field_25048_b + (1.0F - field_25048_b) * 0.4F;
        } else
        {
            field_25048_b = field_25048_b + (0.0F - field_25048_b) * 0.4F;
        }
        if(looksWithInterest)
        {
            numTicksToChaseTarget = 10;
        }
    }
    /*public boolean getSelfShaking()
    {
        return false;
    }
	public float getShadingWhileShaking(float f)
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
    }
	public float getInterestedAngle(float f)
    {
        return (field_25054_c + (field_25048_b - field_25054_c) * f) * 0.15F * 3.141593F;
    }*/
	public float getEyeHeight()
    {
        return height * 0.8F;
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
	protected boolean isMovementCeased()
    {
        return isSelfSitting() || field_25052_g;
    }
	/*public boolean attackEntityFrom(Entity entity, int i)//being attack
    {
		boolean isPlayerAttack=false;
        setSelfSitting(false);//stand up(dog)
        if(entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))//(if attacker not player or arrow)
        {
            i = (i + 1) / 2;
        }
        if(super.attackEntityFrom(entity, i))
        {
            if(!isSelfAngry())
            {
                if(entity instanceof EntityPlayer)
                {
					setSelfTamed(false);
					setSelfOwner("");
					ItemInMouth=null;
                    setSelfAngry(true);
                    playerToAttack = entity;
					isPlayerAttack=true;
                }
                if((entity instanceof EntityArrow) && ((EntityArrow)entity).owner != null)
                {
                    entity = ((EntityArrow)entity).owner;
                }
                if(entity instanceof EntityLiving)
                {
                    List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityRaptor.class, AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(32D, 4D, 32D));
                    Iterator iterator = list.iterator();
                    do
                    {
                        if(!iterator.hasNext())
                        {
                            break;
                        }
                        Entity entity1 = (Entity)iterator.next();
                        EntityRaptor entityraptor = (EntityRaptor)entity1;
						if (entityraptor!=this && entityraptor.getTarget()==null){
							setTarget(entity1);
							if (isPlayerAttack){
								entityraptor.setSelfTamed(false);
								entityraptor.setSelfOwner("");
								entityraptor.ItemInMouth=null;
								entityraptor.setSelfAngry(true);
								entityraptor.playerToAttack = entity;
							}
						}
                    } while(true);
                }
            } else
            if(entity != this && entity != null)
            {
                playerToAttack = entity;
            }
            return true;
        } else
        {
            return false;
        }
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
	public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
		if (this.modelizedDrop()) return true;
		return super.attackEntityFrom(damagesource, i);
    }
	protected void attackEntity(Entity entity, float f)
    {
		//mod_Fossil.ShowMessage(new StringBuilder().append("Range:").append(f).toString());
		//mod_Fossil.ShowMessage(new StringBuilder().append("GLsize:").append(GLsizeX).toString());
		//mod_Fossil.ShowMessage(new StringBuilder().append("BondingX:").append(boundingBox.minX).append(",").append(boundingBox.maxX).toString());
        if(f > 2.0F && f < 5.0F && rand.nextInt(10) == 0)
        {
            if(onGround)
            {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
                motionX = (d / (double)f1) * 0.5D * 0.80000001192092896D + motionX * 0.20000000298023224D;
                motionZ = (d1 / (double)f1) * 0.5D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
                jump();
            }
        } else
        if((double)f < 1.9F && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2+this.getDinoAge());
        }
    }
	public void updateRiderPosition()
    {
		float YAxieOffset=-this.getGLY();
        if(riddenByEntity == null)
        {
            return;
        } else
        {
            if (this.onGround)riddenByEntity.setPosition(posX, posY-YAxieOffset*1.1, posZ);
            else if (this.Landing) riddenByEntity.setPosition(posX, posY-YAxieOffset, posZ);
            else riddenByEntity.setPosition(posX, posY-YAxieOffset*0.6, posZ);
           //riddenByEntity.setRotation(this.rotationYaw,riddenByEntity.rotationPitch);
           //((EntityLiving)riddenByEntity).renderYawOffset=this.rotationYaw;
            return;
        }
    }
	public boolean interact(EntityPlayer entityplayer)
    {
		if (this.isModelized()) return modelizedInteract(entityplayer);
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (FMLCommonHandler.instance().getSide().isClient()){
        			if (itemstack!=null && itemstack.itemID==mod_Fossil.DinoPedia.shiftedIndex){
        	        	//this.ShowPedia(entityplayer);			//old function
        	        	EntityDinosaurce.pediaingDino=this;		//new function
        	        	mod_Fossil.callGUI(entityplayer, FossilGuiHandler.DINOPEDIA_GUI_ID, worldObj, (int)(this.posX), (int)(this.posY), (int)(this.posZ));
			return true;
		}
        }

		if (isTamed()){
			if (itemstack != null){
				if (itemstack.itemID==Item.arrow.shiftedIndex){
					if(entityplayer.username.equalsIgnoreCase(getOwnerName()))
					{
						if(!worldObj.isRemote)
						{
							isJumping = false;
							setPathToEntity(null);
							OrderStatus=EnumOrderType.values()[(mod_Fossil.EnumToInt(OrderStatus)+1)%3];
							SendOrderMessage(OrderStatus);
							switch (OrderStatus){
								case Stay:

									setSelfSitting(true);
									break;
								case Follow:

									setSelfSitting(false);
									break;
								case FreeMove:

									setSelfSitting(false);
							}
						}
						return true;
					}
				}
				if (EOCInteract(itemstack,entityplayer))return true;
		        if(itemstack != null && (itemstack.itemID==Item.fishRaw.shiftedIndex || itemstack.itemID==Item.fishCooked.shiftedIndex||itemstack.itemID==mod_Fossil.SJL.shiftedIndex))
	            {

						if(HandleEating(30))
						{
							itemstack.stackSize--;
							if(itemstack.stackSize <= 0)
							{
								entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
							}
							heal(3);

						}
					return true;
	            }else return false;
				/*if (ItemInMouth==null){
					ItemInMouth=itemstack;
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
					return true;
				}*/
			}else{
				if (ItemInMouth!=null){
					if(worldObj.isRemote)
			        {
			            return false;
			        }
			        int i = ItemInMouth.stackSize;
			        if(entityplayer.inventory.addItemStackToInventory(ItemInMouth))
			        {
			            ModLoader.onItemPickup(entityplayer, ItemInMouth);
			            worldObj.playSoundAtEntity(entityplayer, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			        }else return false;
					ItemInMouth=null;
					return true;
				}else{
					if (this.isTamed() && this.getDinoAge()>4 &&!worldObj.isRemote && (riddenByEntity == null || riddenByEntity == entityplayer)){
							entityplayer.moveForward=0;
							entityplayer.rotationYaw=this.rotationYaw;
				            entityplayer.mountEntity(this);
				            this.setPathToEntity(null);
				            this.renderYawOffset=this.rotationYaw;
				            return true;
					}

				}
			}
		}
		return false;

    }

	public void handleHealthUpdate(byte byte0)
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
        } else
        {
            super.handleHealthUpdate(byte0);
        }
    }
	public int getMaxSpawnedInChunk()
    {
        return 100;
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
        if(flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 2)));
			moveSpeed=2.0F;
        } else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -3)));
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

	public void setTamed(boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);
        if(flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 4)));
        } else
        {
			ItemInMouth=null;
			SendStatusMessage(EnumSituation.Bytreate);
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -5)));
        }
    }
	protected void fall(float f)
    {
        //super.fall(f);
		 if(riddenByEntity != null && !this.Landing)
        {
            riddenByEntity.fall(f);
        }
		int i = (int)Math.ceil(f - 3F);
		if (worldObj.isRemote) return;
        if(i > 0)
        {
            //attackEntityFrom(DamageSource.fall, 0);
            int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(posY - 0.20000000298023224D - (double)yOffset), MathHelper.floor_double(posZ));
            if(j > 0)
            {
                StepSound stepsound = Block.blocksList[j].stepSound;
                worldObj.playSoundAtEntity(this, stepsound.getBreakSound(), stepsound.getVolume() * 0.5F, stepsound.getPitch() * 0.75F);
            }
        }
    }
	/*protected void updateWanderPath()
    {
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999F;
		if (OrderStatus==OrderStatus.FreeMove || !isTamed()){
			for(int l = 0; l < 10; l++)
			{
				int i1 = MathHelper.floor_double((posX + (double)rand.nextInt(13)) - 6D);
				int j1 = MathHelper.floor_double((posY + (double)rand.nextInt(7)) - 3D);
				int k1 = MathHelper.floor_double((posZ + (double)rand.nextInt(13)) - 6D);
				float f1 = getBlockPathWeight(i1, j1, k1);
				if(f1 > f)
				{
					f = f1;
					i = i1;
					j = j1;
					k = k1;
					flag = true;
				}
			}

			if(flag)
			{
				setPathToEntity(worldObj.getEntityPathToXYZ(this, i, j, k, 10F, true, false, true, false));
			}
		}
    }*/
	/*protected void GrowUp(){
		int preage=age;
		if (age<=AGE_LIMIT){
		AgeTick++;
			if(AgeTick>=GROW_TIME_COUNT){
				//mod_Fossil.ShowMessage((new StringBuilder()).append((int)Math.round(0.1*age+0.3)).toString());
					AgeTick=0;
					age++;
					this.CheckSkin();
					if (health<20) health+=1;
					updateSize(false);
					setPosition(posX,posY,posZ);
				if (!CheckSpace()){	
					age--;
					this.CheckSkin();
					if (health>1) health-=1;
					updateSize(false);
					setPosition(posX,posY,posZ);
					if (isTamed()) SendStatusMessage(EnumSituation.NoSpace);
				}
			}
		}
		
	}*/
	private void InitSize(){
		this.CheckSkin();
		updateSize(false);
		setPosition(posX,posY,posZ);
	}
	public void CheckSkin(){
		if (!this.isModelized()) texture="/skull/Pterosaur.png";
		else texture=getModelTexture();
	}
	public boolean CheckSpace(){
		return !isEntityInsideOpaqueBlock();
	}
	
	/*private boolean HelpPlayer(EntityPlayer entityplayer){
		List list=worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(32D, 4D, 32D));
		if (list!=null){
			Iterator iterator = list.iterator();
                do
                {
                    if(!iterator.hasNext())
                    {
                        return false;
                    }
                    Entity entity1 = (Entity)iterator.next();
					if (entity1 instanceof EntityMob && entity1.isEntityAlive()){
						if (!(entity1 instanceof EntityRaptor)){
							if(((EntityMob)entity1).getTarget() != null)
							{
								if (((EntityMob)entity1).getTarget()==(Entity)entityplayer){
									setTarget(entity1);
									return true;
								}else{
									if ((((EntityMob)entity1).getTarget() instanceof EntityRaptor)){
										if (((EntityRaptor)(((EntityMob)entity1).getTarget())).getSelfOwner()==getSelfOwner())
										setTarget(entity1);
										return true;
									}
								}
							}
						}
						/*else{
							if (((EntityRaptor)entity1).isSelfTamed()==false){
								if(((EntityCreature)entity1).playerToAttack != null)
								{
									if (((EntityCreature)entity1).playerToAttack==(Entity)entityplayer){
										setTarget(entity1);
										return;
									}
								}
							}
						}
					}
                } while(true);
		}else return false;
	}*/
	/*private boolean FindAndHoldItems(int ItemID,int range){
		if (!isSelfSitting()){
			List NearBy=worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.0D, 0.0D, 1.0D));
			if (NearBy!=null){
				for(int i = 0; i < NearBy.size(); i++)
				{	
					if (NearBy.get(i) instanceof EntityItem){
						EntityItem ItemInRange=(EntityItem)NearBy.get(i);
						if (ItemID==-1 || ItemInRange.item.itemID==ItemID){
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
				Entity entity1 = (Entity)iterator.next();
				if (TargetItem!=null){
					if (GetDistanceWithEntity(entity1)<GetDistanceWithEntity(TargetItem)){
						TargetItem	= (EntityItem)entity1;
					}
				}else{
					TargetItem	= (EntityItem)entity1;
				}		
			} while(true);
			if (TargetItem!=null){
				setPathToEntity(worldObj.getPathEntityToEntity(this,TargetItem,(float)range, true, false, true, false));
				return true;
			}else return false;
		}else return false;	
	}*/
	/*protected boolean HandleHunger(){
		if (EatTick>0) EatTick--;
		if (worldObj.difficultySetting > 0 && worldObj.getClosestPlayerToEntity(this, 24D)!=null){
			//mod_Fossil.ShowMessage(new StringBuilder().append(Hunger).toString());
			HungerTick--;

			if (HungerTick<=0){
				Hunger--;
				HungerTick=HungerTickLimit;
				HandleStarvation();
				return true;
			}
			HandleStarvation();
			return false;
		}else return true;
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
		if (this.isDead) return;
		if (Hunger<=0) {
			if (isTamed()) SendStatusMessage(EnumSituation.Starve);
			attackEntityFrom(DamageSource.starve, 20);
			return;
		}
		if (Hunger==HungerLimit/2 && HungerTick==HungerTickLimit/10) {
			if (isTamed()) SendStatusMessage(EnumSituation.Hungry);
			return;
		}
	}*/
	/*public void Flee(Entity EscapeFrom,int Mutplier){
		int TargetX=(int)Math.round(posX-(EscapeFrom.posX-posX)*Mutplier);
		int TargetZ=(int)Math.round(posZ-(EscapeFrom.posZ-posZ)*Mutplier);
		int TargetY=0;
		for(int i=128;i>0;i--){
			if (!worldObj.isAirBlock(TargetX, i,TargetZ)){
				TargetY=i;
				break;
			}
		}
		setTamed(false);
		setSelfSitting(false);	
		setPathToEntity(worldObj.getEntityPathToXYZ((Entity)this,TargetX,TargetY,TargetZ,(float)(10*Mutplier), true, false, true, false));

	}*/
	/*private boolean HuntForFish(double range){
		return false;
	}*/

	/*private TileEntity GetNearestTileEntity(int targetType,int range){
		final int Chest=0;
		final int Furance=1;
		final int Feeder=2;
		TileEntity result=null;
		TileEntity tmp=null;
		if (range>6) range=6;
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
	/*private boolean FindChest(int range){
		TileEntityChest target=null;
		if (GetNearestTileEntity(0,range)!=null){
			target=(TileEntityChest)GetNearestTileEntity(0,range);
			if (GetDistanceWithTileEntity((TileEntity)target)>1){
				//mod_Fossil.ShowMessage(new StringBuilder().append("Setting Path:").append(target.xCoord).append(",").append(target.yCoord).append(",").append(target.zCoord).toString());
				setPathToEntity(worldObj.getEntityPathToXYZ((Entity)this,(int)Math.round(target.xCoord),(int)Math.round(target.yCoord),(int)Math.round(target.zCoord),(float)range));
				
			}
			return true;
		}else return false;
	}*/
	/*private boolean FindFeeder(int range){ 
		if(range>6) range=6;
		TileEntityFeeder target=null;
		if (GetNearestTileEntity(2,range)!=null){
			target=(TileEntityFeeder)GetNearestTileEntity(2,range);
			if (GetDistanceWithTileEntity((TileEntity)target)>4){
				//mod_Fossil.ShowMessage(new StringBuilder().append("Setting Path:").append(target.xCoord).append(",").append(target.yCoord).append(",").append(target.zCoord).toString());
				setPathToEntity(worldObj.getEntityPathToXYZ((Entity)this,(int)Math.round(target.xCoord),(int)Math.round(target.yCoord),(int)Math.round(target.zCoord),(float)range, true, false, true, false));
				
			}else{
				if (target.CheckIsEmpty(this))return false;
				FaceToCoord(target.xCoord,target.yCoord,target.zCoord);
				target.Feed(this);
			}
			return true;
		}else return false;
	}*/
	/*public void FaceToCoord(int targetX,int targetY,int targetZ){
        double d = targetX;
        double d2 = targetZ;
        float f2 = (float)((Math.atan2(d2, d) * 180D) / 3.1415927410125732D) - 90F;
        rotationYaw = updateRotation(rotationYaw, f2, 360.0f);
	}*/
    /*private float updateRotation(float f, float f1, float f2)
    {
        float f3;
        for(f3 = f1 - f; f3 < -180F; f3 += 360F) { }
        for(; f3 >= 180F; f3 -= 360F) { }
        if(f3 > f2)
        {
            f3 = f2;
        }
        if(f3 < -f2)
        {
            f3 = -f2;
        }
        return f + f3;
    }*/
	public void ChangeSubType(int type){
		if (type<=2 && type>=0){
			this.SubType=type;
			this.CheckSkin();
		}
	}
	public void ShowPedia(EntityPlayer checker){
		PediaTextCorrection(SelfType,checker);
		if (this.isTamed()){
				mod_Fossil.ShowMessage(new StringBuilder().append(OwnerText).append(this.getOwnerName()).toString(),checker);
				mod_Fossil.ShowMessage(new StringBuilder().append(AgeText).append(this.getDinoAge()).toString(),checker);
				mod_Fossil.ShowMessage(new StringBuilder().append(HelthText).append(this.health).append("/").append(20).toString(),checker);
				mod_Fossil.ShowMessage(new StringBuilder().append(HungerText).append(this.getHunger()).append("/").append(this.getHungerLimit()).toString(),checker);
			

			if (this.getDinoAge()>=5) mod_Fossil.ShowMessage(new StringBuilder().append(FlyText).toString(),checker);
			if (this.getDinoAge()>=8) mod_Fossil.ShowMessage(new StringBuilder().append(RidiableText).toString(),checker);
		}else{
			mod_Fossil.ShowMessage(UntamedText,checker);
		}
		
	}
	/*public void HandleBreed(){
		if (this.age>3){
			this.BreedTick--;
			if (this.BreedTick==0){
				int GroupAmount=0;
				List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(32D, 32D, 32D));
				for (int i=0;i<list.size();i++){
					if (list.get(i) instanceof EntityPterosaur) GroupAmount++;
				}
				if (GroupAmount>50) GroupAmount=50;
				if (GroupAmount>80) return;
				if (new Random().nextInt(100)<GroupAmount){
					EntityDinoEgg entityegg=null;
					entityegg = (EntityDinoEgg)new EntityDinoEgg(worldObj,EnumDinoType.Pterosaur);
					entityegg.setLocationAndAngles(this.posX+(new Random().nextInt(3)-1), this.posY, this.posZ+(new Random().nextInt(3)-1), worldObj.rand.nextFloat() * 360F, 0.0F);
					if(	worldObj.checkIfAABBIsClear(entityegg.boundingBox) && worldObj.getCollidingBoundingBoxes(entityegg, entityegg.boundingBox).size() == 0) worldObj.spawnEntityInWorld(entityegg);
					showHeartsOrSmokeFX(true);
				}
				this.BreedTick=3000;
			}
		}
	}*/
	public void jump(){
		this.motionY=0.8D;
	}

	private void HandleRiding(){
		
		if (this.riddenByEntity==null || !(this.riddenByEntity instanceof EntityClientPlayerMP)) return;
		EntityClientPlayerMP ridder=(EntityClientPlayerMP)this.riddenByEntity;
			this.HandleLanding();
			if (this.onGround || this.inWater){
				//land movement
	    		if (this.AirSpeed!=0.0F) this.AirSpeed=0.0F;
	    		if (this.AirAngle!=0.0F) this.AirAngle=0.0F;
	    		if (this.AirPitch!=0.0F) this.AirPitch=0.0F;
				this.rotationYaw-=(float) ((float)(ridder.moveStrafing)*5);
	            for(; this.rotationYaw < -180F; this.rotationYaw += 360F) { }
	            for(; this.rotationYaw >= 180F; this.rotationYaw -= 360F) { }
				this.setMoveForward(ridder.moveForward*this.moveSpeed);
				
			}else{				
	    		this.AirAngle-=ridder.moveStrafing;
	            if (this.AirAngle>30F) this.AirAngle=30F;
	            if (this.AirAngle<-30F) this.AirAngle=-30F;
	            if (Math.abs(this.AirAngle)>10) this.rotationYaw+=(this.AirAngle>0? 1:-1);
				//if (Math.abs(this.AirAngle)!=30)this.rotationYaw-=ridder.moveStrafing;
	            for(; this.rotationYaw < -180F; this.rotationYaw += 360F) { }
	            for(; this.rotationYaw >= 180F; this.rotationYaw -= 360F) { }
				if (this.Landing){
					AirPitch=0;
					if (!this.isCollidedVertically)this.motionY = -0.2D;
					else this.motionY=0;
					this.setMoveForward(this.AirSpeed);
				}else{
					if ((this.isCollidedHorizontally || this.isCollidedVertically)&& this.AirSpeed!=0){
						this.AirSpeed=0.0F;
						this.setMoveForward(0.0F);
						return;
					}
					//air movement
		    		if (this.AirSpeed==0 && this.moveForward!=0){
		    			this.AirSpeed=this.moveForward*this.moveSpeed;
		    		}
		    		this.AirAngle-=ridder.moveStrafing;
		            if (this.AirAngle>30F) this.AirAngle=30F;
		            if (this.AirAngle<-30F) this.AirAngle=-30F;

					//this.moveForward=ridder.moveForward*this.moveSpeed*100;
		            this.AirPitch-=ridder.moveForward*2;
		            if (this.AirPitch>90) this.AirPitch=90;
		            if (this.AirPitch<-60) this.AirPitch=-60;
		            float RandPitch=(float)(this.AirPitch*(Math.PI/180));
		            if (LastAirPitch>=AirPitch){
			            double SpeedOffset=Math.cos(RandPitch);
			            if (RandPitch<0)SpeedOffset+=1;
			            this.setMoveForward(this.AirSpeed*(float)SpeedOffset);
			            if (this.AirPitch<60 && this.moveForward>0.1F){
			            	this.motionY=Math.sin(RandPitch)*0.4;
			            }
		            }
		            LastAirPitch=AirPitch;
				}
				
			}
	}
	public void SetOrder(EnumOrderType input){
		this.OrderStatus=input;
	}

	@Override
	public EntityAnimal spawnBabyAnimal(EntityAnimal entityanimal) {
		return new EntityPterosaur(worldObj);
	}
	@Override
	public int getMaxHealth() {
		return 20;
	}
	public void HandleLanding(){
		if (this.riddenByEntity!=null && !this.isCollidedVertically && !this.onGround){
			if (!Landing){
				if (this.AirPitch>60) this.Landing=true;
			}
		}else{
			this.Landing=false;
		}
	}

	@Override
	protected void updateSize(boolean shouldAddAge) {
		if (shouldAddAge && this.getDinoAge()<this.AGE_LIMIT) this.increaseDinoAge();
		setSize((float)(0.8F+0.2*(float)this.getDinoAge()),(float)(0.8F+0.2*(float)this.getDinoAge()));
		
	}
	public EnumOrderType getOrderType() {
		return this.OrderStatus;
	}

	@Override
	protected int foodValue(Item asked) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void HandleEating(Item food) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HoldItem(Item itemGot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getGLX() {
		return (float)(0.8F+0.2*(float)this.getDinoAge());
	}

	@Override
	public float getGLY() {
		return (float)(0.8F+0.2*(float)this.getDinoAge());
	}
}