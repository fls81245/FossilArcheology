package net.minecraft.src;
import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;
public class EntityStegosaurus extends EntityDinosaurce{
    private boolean looksWithInterest;
	public final float HuntLimit=getHungerLimit()*0.9F;
    private float field_25048_b;
    private float field_25054_c;
    private boolean isWolfShaking;
    private boolean field_25052_g;
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;
	public int SubSpecies=1;
	public boolean isBaby=true;
	public int RushTick=0;
	public int BreedTick=3000;
	public boolean Running=false;
	public EntityStegosaurus(World world)
    {
        super(world);
        SelfType=EnumDinoType.Stegosaurus;
        looksWithInterest = false;
		SubSpecies=new Random().nextInt(3)+1;
		texture = "/skull/Stegosaurus_Baby.png";
		CheckSkin();
        setSize(1.0F, 1.0F);
		//this.moveSpeed=0.5F+this.age*3;
        this.moveSpeed = 0.3F;
        health = 8;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new DinoAIStarvation(this));
		this.tasks.addTask(0, new DinoAIGrowup(this, 12));
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        this.tasks.addTask(4, new DinoAIFollowOwner(this, this.moveSpeed, 5F, 2.0F));
        this.tasks.addTask(5, new DinoAIEatFerns(this,this.HuntLimit));
        this.tasks.addTask(6, new DinoAIUseFeeder(this,this.moveSpeed,24,this.HuntLimit,EnumDinoEating.Herbivorous));
        this.tasks.addTask(6, new DinoAIPickItem(this,Item.wheat,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(6, new DinoAIPickItem(this,Item.appleRed,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(7, new DinoAIWander(this, this.moveSpeed));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
    }
	public int getHungerLimit(){
		return 500;
	}
    public boolean isAIEnabled()
    {
        return (!this.isModelized() && this.riddenByEntity==null);
    }

	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setInteger("SubSpecies",SubSpecies);
		nbttagcompound.setBoolean("Angry", isSelfAngry());
        nbttagcompound.setBoolean("isBaby", isBaby);

    }
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
		this.SubSpecies=nbttagcompound.getInteger("SubSpecies");
		this.isBaby=nbttagcompound.getBoolean("isBaby");
		CheckSkin();
        setSelfAngry(nbttagcompound.getBoolean("Angry"));
        setSelfSitting(nbttagcompound.getBoolean("Sitting"));
		InitSize();
    }
	protected boolean canDespawn()
    {
        return false;
    }
	protected String getLivingSound()
    {

		if (worldObj.getClosestPlayerToEntity(this, 8D)!=null) return "Steg_living";
		else return null;
 
    }
	protected String getHurtSound()
    {
        return "Steg_Hurt";
    }

    protected String getDeathSound()
    {
        return "Steg_death";
    }
	protected void updateEntityActionState()
    {

		if (this.riddenByEntity==null){
			super.updateEntityActionState();
			//mod_Fossil.ShowMessage(new StringBuilder().append(Hunger).toString());
	
	
			//else
	        /*if(playerToAttack == null && !hasPath() && !isWolfTamed() && worldObj.rand.nextInt(100) == 0)
	        {
	            List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntitySheep.class, AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
	            if(!list.isEmpty())
	            {
	                setTarget((Entity)list.get(worldObj.rand.nextInt(list.size())));
	            }
	        }*/
	       /* if(isInWater())
	        {
	            setSelfSitting(false);
	        }
			if (!isSelfSitting()&& !hasPath()&&(new Random().nextInt(1000)==5)){
				if (!FindWheats(6)) FindFren(6);
			}
	        if(!worldObj.isRemote)
	        {
	            this.dataWatcher.updateObject(18, Integer.valueOf(this.getHealth()));
	        }*/
		}
    }
	

	public void onUpdate()
    {
        super.onUpdate();

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
	
    public boolean getSelfShaking()
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
    }
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
	public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
		Entity entity = damagesource.getEntity();
        setSelfSitting(false);
        if(entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
        {
            i = (i + 1) / 2;
        }
        if(super.attackEntityFrom(damagesource, i))
        {
            if(!isTamed() && !isSelfAngry())
            {
                if(entity instanceof EntityPlayer)
                {
                    setSelfAngry(true);
                    entityToAttack = entity;
                }
                if((entity instanceof EntityArrow) && ((EntityArrow)entity).shootingEntity != null)
                {
                    entity = ((EntityArrow)entity).shootingEntity;
                }
                if(entity instanceof EntityLiving)
                {
                    /*List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityWolf.class, AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
                    Iterator iterator = list.iterator();
                    do
                    {
                        if(!iterator.hasNext())
                        {
                            break;
                        }
                        Entity entity1 = (Entity)iterator.next();
                        EntityWolf entitywolf = (EntityWolf)entity1;
                        if(!entitywolf.isWolfTamed() && entitywolf.playerToAttack == null)
                        {
                            entitywolf.playerToAttack = entity;
                            if(entity instanceof EntityPlayer)
                            {
                                entitywolf.setWolfAngry(true);
                            }
                        }
                    } while(true);*/
                }
            } else
            if(entity != this && entity != null)
            {
                if(isTamed() && (entity instanceof EntityPlayer) && ((EntityPlayer)entity).username.equalsIgnoreCase(getOwnerName()))
                {
                    return true;
                }
                entityToAttack = entity;
            }
            return true;
        } else
        {
            return false;
        }
    }
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
	/*protected void attackEntity(Entity entity, float f)
    {
		if (this.getEntityToAttack()==this){
			this.setTarget(null);
			return;
		}
        if(f > GLsizeX && f < GLsizeX*3 && rand.nextInt(10) == 0)
        {
            if(onGround)
            {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
                motionX = (d / (double)f1) * 0.5D * 0.80000001192092896D + motionX * 0.20000000298023224D;
                motionZ = (d1 / (double)f1) * 0.5D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
                //motionY = 0.40000000596046448D;
            }
        } else
        if((double)f < GLsizeX && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3+this.getDinoAge());
        }
    }*/
	public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if(itemstack != null && itemstack.itemID==Item.wheat.shiftedIndex)
            {
				//if (EatTick<=0){
					if(this.CheckEatable(itemstack.itemID) && HandleEating(10))
					{
						itemstack.stackSize--;
						if(itemstack.stackSize <= 0)
						{
							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
						}
						heal(3);

					}
				/*}else{
					SendStatusMessage(EnumSituation.ChewTime,this.SelfType);
				}*/
				return true;
		}
        if (FMLCommonHandler.instance().getSide().isClient()){
        	        if (itemstack!=null && itemstack.itemID==mod_Fossil.DinoPedia.shiftedIndex){
        	        	//this.ShowPedia(entityplayer);			//old function
        	        	EntityDinosaurce.pediaingDino=this;		//new function
        	        	mod_Fossil.callGUI(entityplayer, FossilGuiHandler.DINOPEDIA_GUI_ID, worldObj, (int)(this.posX), (int)(this.posY), (int)(this.posZ));
        	return true;
        }
        }

        if (this.EOCInteract(itemstack, entityplayer)) return true;
        if(itemstack != null && itemstack.itemID==Item.stick.shiftedIndex){
        	if(entityplayer.username.equalsIgnoreCase(getOwnerName())){				
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
		/*if (this.isSelfTamed() && this.age>4 &&!worldObj.multiplayerWorld && (riddenByEntity == null || riddenByEntity == entityplayer)){
		 	entityplayer.rotationYaw=this.rotationYaw;
            entityplayer.mountEntity(this);
            this.setPathToEntity(null);
            this.renderYawOffset=this.rotationYaw;
            return true;
		}*/
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
            timeWolfIsShaking = 0.0F;
            prevTimeWolfIsShaking = 0.0F;
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
			moveSpeed=0.5F;
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
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -5)));
        }
    }
    public boolean getCanSpawnHere()
    {
        return worldObj.checkIfAABBIsClear(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.isAnyLiquid(boundingBox);
    }
	/*protected void GrowUp(){
	
		if (age<=12 && this.riddenByEntity==null){
		AgeTick++;
			if(AgeTick>=GROW_TIME_COUNT){
				//mod_Fossil.ShowMessage((new StringBuilder()).append((int)Math.round(1.0+age*0.3)).toString());

					AgeTick=0;
					age++;
					if (age>4 && isBaby) ChangeTexture();
					if (health<20) health+=1;
					setSize((float)(1.5F+0.3*(float)age),(float)(1.5F+0.3*(float)age));
					setPosition(posX,posY,posZ);
				if (!CheckSpace()){	
					age--;
					isBaby=(age<=4);
					CheckSkin();
					if (health>1) health-=1;
					setSize((float)(1.5F+0.3*(float)age),(float)(1.5F+0.3*(float)age));
					setPosition(posX,posY,posZ);
					if (isTamed()) SendStatusMessage(EnumSituation.NoSpace,this.SelfType); 
				}
				this.moveSpeed=0.5F+this.age*3;
			}
		}
		
	}*/
	private void InitSize(){
		if (this.getDinoAge()>4 && isBaby) ChangeTexture();
		setSize((float)(1.5F+0.3*(float)getDinoAge()),(float)(1.5F+0.3*(float)getDinoAge()));
		setPosition(posX,posY,posZ);
		this.moveSpeed=0.5F+this.getDinoAge()*3;
	}
	public boolean CheckSpace(){
		return !isEntityInsideOpaqueBlock();
	}
	private void ChangeTexture(){
			isBaby=false;
			CheckSkin();
	}
	public void CheckSkin(){
		if (isBaby){
			texture="/skull/Stegosaurus_Baby.png";		
		}else{

			texture="/skull/Stegosaurus_Adult.png";		
		}
	}
	public void updateRiderPosition()
    {
        if(riddenByEntity == null)
        {
            return;
        } else
        {
            riddenByEntity.setPosition(posX, posY+this.getGLY()*0.65+0.07*(12-this.getDinoAge()), posZ);
           //riddenByEntity.setRotation(this.rotationYaw,riddenByEntity.rotationPitch);
           //((EntityLiving)riddenByEntity).renderYawOffset=this.rotationYaw;
            return;
        }
    }
	/*private boolean FindFren(int range){
		float TempDis=range*2;
		int targetX=0;
		int targetY=0;
		int targetZ=0;
				for (int i=-range;i<=range;i++){
					for (int j=-2;j<=2;j++){
						for (int k=-range;k<=range;k++){
							if (worldObj.getBlockId((int)Math.round(posX+i), (int)Math.round(posY+j),(int)Math.round(posZ+ k))==mod_Fossil.Ferns.blockID){
								if (TempDis> GetDistanceWithXYZ(posX+i,posY+j,posZ+k)){
									TempDis=GetDistanceWithXYZ(posX+i,posY+j,posZ+k);
									targetX=i;
									targetY=j;
									targetZ=k;
								}
							}
						}
					}
				}
		if (TempDis!=range*2){
			if (Math.sqrt((targetX)^2+(targetY)^2+(targetZ)^2)>=2){
				setPathToEntity(worldObj.getEntityPathToXYZ((Entity)this,(int)Math.round(posX+targetX),(int)Math.round(posY+targetY),(int)Math.round(posZ+targetZ),10F, true, false, true, false));
				return true;
			}else{
				if (EatTick==0){
					FaceToCoord((int)-(posX+targetX),(int)(posY+targetY),(int)-(posZ+targetZ));
					HandleEating(10,true);
					for(int i=-1;i<=1;i++){
						for (int j=-1;j<=1;j++){
							if (worldObj.getBlockId((int)Math.round(posX+targetX+i),(int)Math.round(posY+targetY),(int)Math.round(posZ+targetZ+j))==mod_Fossil.Ferns.blockID){
								worldObj.playAuxSFX(2001, (int)Math.round(posX+targetX+i), (int)Math.round(posY+targetY), (int)Math.round(posZ+targetZ+j), Block.tallGrass.blockID);
								worldObj.setBlockWithNotify((int)Math.round(posX+targetX+i),(int)Math.round(posY+targetY),(int)Math.round(posZ+targetZ+j),0);
								if (worldObj.getBlockId((int)Math.round(posX+targetX+i),(int)Math.round(posY+targetY)+1,(int)Math.round(posZ+targetZ+j))==mod_Fossil.FernUpper.blockID) worldObj.setBlockWithNotify((int)Math.round(posX+targetX+i),(int)Math.round(posY+targetY)+1,(int)Math.round(posZ+targetZ+j),0);
								if (worldObj.getBlockId((int)Math.round(posX+targetX+i),(int)Math.round(posY+targetY)-1,(int)Math.round(posZ+targetZ+j))==Block.grass.blockID) worldObj.setBlockWithNotify((int)Math.round(posX+targetX+i),(int)Math.round(posY+targetY)-1,(int)Math.round(posZ+targetZ+j),Block.dirt.blockID);
							}
						}
					}

					this.heal(3);
					EatTick=100;
					this.setPathToEntity(null);
				}
				return true;
			}
		}else return false;
	}*/
	/*protected boolean HandleHunger(){
		if (EatTick>0) EatTick--;
		if (worldObj.difficultySetting > 0 && this.riddenByEntity==null){
			//mod_Fossil.ShowMessage(new StringBuilder().append(Hunger).toString());
			HungerTick--;
			if (HungerTick<=0){
				Hunger--;
				HungerTick=HungerTickLimit;
				HandleStarvation();
				return true;
			}
			HandleStarvation();
			return true;
		}else return true;
	}*/
	public boolean HandleEating(int FoodValue){
		return HandleEating(FoodValue,false);
	}
	public boolean HandleEating(int FoodValue,boolean FernFlag){
		if (this.getHunger()>=getHungerLimit()) {
			if (isTamed() && !FernFlag) SendStatusMessage(EnumSituation.Full,this.SelfType);
			return false;
		}
		this.increaseHunger(FoodValue);
		showHeartsOrSmokeFX(false);
		if (getHunger()>=getHungerLimit()) this.setHunger(getHungerLimit());
		return true;
	}
	/*protected void HandleStarvation(){
		if (this.isDead) return;
		if (Hunger<=0) {
			if (isTamed()) SendStatusMessage(EnumSituation.Starve,SelfType);;
			attackEntityFrom(DamageSource.starve, 20);
			return;
		}
		if (Hunger==HungerLimit/2 && HungerTick==HungerTickLimit/10) {
			if (isTamed()) SendStatusMessage(EnumSituation.Hungry,SelfType);
			return;
		}
		
	}*/
	private boolean FindWheats(int range){
		if (!isSelfSitting()){
			List NearBy=worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.0D, 0.0D, 1.0D));
			if (NearBy!=null){
				for(int i = 0; i < NearBy.size(); i++)
				{	
					if (NearBy.get(i) instanceof EntityItem){
						EntityItem ItemInRange=(EntityItem)NearBy.get(i);
						if (ItemInRange.item.itemID==Item.wheat.shiftedIndex){
							HandleEating(10);
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
				if (entity1 instanceof EntityItem){
					EntityItem Tmp=(EntityItem)entity1;
					if (Tmp.item.getItem().shiftedIndex==Item.wheat.shiftedIndex){
						if (TargetItem!=null){
							if (GetDistanceWithEntity(Tmp)<GetDistanceWithEntity(TargetItem)){
								TargetItem	= Tmp;
							}
						}else{
							TargetItem	= Tmp;
						}
					}
				}
			} while(true);
			if (TargetItem!=null){
				setPathToEntity(worldObj.getPathEntityToEntity(this,TargetItem,(float)range, true, false, true, false));
				return true;
			}else return false;
		}else return false;	
	}

	/*private boolean FindFeeder(int range){ if(range>6) range=6;
		TileEntityFeeder target=null;
		if (GetNearestTileEntity(2,range)!=null){
			target=(TileEntityFeeder)GetNearestTileEntity(2,range);
			if (GetDistanceWithTileEntity((TileEntity)target)>3){
				//mod_Fossil.ShowMessage(new StringBuilder().append("Setting Path:").append(target.xCoord).append(",").append(target.yCoord).append(",").append(target.zCoord).toString());
				setPathToEntity(worldObj.getEntityPathToXYZ((Entity)this,(int)Math.round(target.xCoord),(int)Math.round(target.yCoord),(int)Math.round(target.zCoord),(float)range, true, false, true, false));
				
			}else{
				if (target.CheckIsEmpty(this))return false;
				FaceToCoord(target.xCoord,target.yCoord,target.zCoord);
				target.Feed(this);
				this.setTarget(null);
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

		public void FaceToCoord(int targetX,int targetY,int targetZ){
	        double d = targetX;
	        double d2 = targetZ;
	        float f2 = (float)((Math.atan2(d2, d) * 180D) / 3.1415927410125732D) - 90F;
	        rotationYaw = updateRotation(rotationYaw, f2, 360.0f);
		}
	    private float updateRotation(float f, float f1, float f2)
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
	    }
		private void HandleRiding(){
			if (this.RushTick>0) this.RushTick--;
				if(riddenByEntity != null)
		        {

					if (this.riddenByEntity instanceof EntityPlayerSP){
						if (this.onGround){
							if (((EntityPlayerSP)this.riddenByEntity).movementInput.jump){
								this.jump();
								((EntityPlayerSP)this.riddenByEntity).movementInput.jump=false;
							}
							this.Running=(((EntityPlayerSP)this.riddenByEntity).movementInput.sneak && this.RushTick==0);
							if (((EntityPlayerSP)this.riddenByEntity).movementInput.sneak){
								if (this.getDinoAge()>4 && (this.motionX!=0 && this.motionZ!=0)) BlockInteractive();
								if(this.RushTick==0){
									this.rotationYaw-=(float) ((float)(((EntityPlayerSP) this.riddenByEntity).moveStrafing)*5);
						            for(; this.rotationYaw < -180F; this.rotationYaw += 360F) { }
						            for(; this.rotationYaw >= 180F; this.rotationYaw -= 360F) { }
									this.moveForward=((EntityPlayerSP) this.riddenByEntity).moveForward*this.moveSpeed*8;
								}else{
									this.rotationYaw-=(float) ((float)(((EntityPlayerSP) this.riddenByEntity).moveStrafing)*5);
						            for(; this.rotationYaw < -180F; this.rotationYaw += 360F) { }
						            for(; this.rotationYaw >= 180F; this.rotationYaw -= 360F) { }
									this.moveForward=((EntityPlayerSP) this.riddenByEntity).moveForward*this.moveSpeed*(3/4);
									
								}
								/*if (!this.SneakScream){
									this.SneakScream=true;
									worldObj.playSoundAtEntity(this,"TRex_scream",getSoundVolume(), 1.0F);
								}*/
							}else{
								
								this.rotationYaw-=(float) ((float)(((EntityPlayerSP) this.riddenByEntity).moveStrafing)*5);
					            for(; this.rotationYaw < -180F; this.rotationYaw += 360F) { }
					            for(; this.rotationYaw >= 180F; this.rotationYaw -= 360F) { }
								this.moveForward=((EntityPlayerSP) this.riddenByEntity).moveForward*this.moveSpeed;
								//if (this.SneakScream) this.SneakScream=false;
							}
						}
					}
		        }

		}
		public void applyEntityCollision(Entity entity){
			if (entity instanceof EntityLiving && !(entity instanceof EntityPlayer)){
				if (this.riddenByEntity!=null && this.onGround){
					this.onKillEntity((EntityLiving)entity);
					((EntityLiving)entity).attackEntityFrom(DamageSource.causeMobDamage(this), 10);
					return;
				}
			}
			super.applyEntityCollision(entity);
		}
		public void BlockInteractive(){
			for (int j=(int)Math.round(boundingBox.minX)-1;j<=(int)Math.round(boundingBox.maxX)+1;j++){
				for (int k=(int)Math.round(boundingBox.minY);k<=(int)Math.round(boundingBox.maxY);k++){
					for (int l=(int)Math.round(boundingBox.minZ)-1;l<=(int)Math.round(boundingBox.maxZ)+1;l++){
						if(!worldObj.isAirBlock(j, k, l) /*&& new Random().nextInt(10)==5*/){
						int blockid=worldObj.getBlockId(j,k,l);
							if (!this.inWater){
								if (!this.isTamed() || this.riddenByEntity!=null){

										if (Block.blocksList[blockid].getBlockHardness(worldObj,(int)posX,(int)posY,(int)posZ)<5.0 || blockid==Block.wood.blockID){
											if (new Random().nextInt(10)==5)Block.blocksList[blockid].dropBlockAsItem(worldObj,j,k,l,1,0);
											worldObj.setBlockWithNotify(j,k,l,0);
											this.RushTick=10;
										}
									
								}else{
									if (blockid==Block.wood.blockID || blockid==Block.leaves.blockID){
										worldObj.setBlockWithNotify(j,k,l,0);
										this.RushTick=10;
									}
								}
							}
						
						
						}
					}
				}
			}
		}
		public void ShowPedia(EntityPlayer checker){
			//if (worldObj.isRemote) return;
			PediaTextCorrection(SelfType,checker);
			if (this.isTamed()){
					mod_Fossil.ShowMessage(new StringBuilder().append(OwnerText).append(this.getOwnerName()).toString(),checker);
					mod_Fossil.ShowMessage(new StringBuilder().append(AgeText).append(this.getDinoAge()).toString(),checker);
					mod_Fossil.ShowMessage(new StringBuilder().append(HelthText).append(this.health).append("/").append(20).toString(),checker);
					mod_Fossil.ShowMessage(new StringBuilder().append(HungerText).append(this.getHunger()).append("/").append(getHungerLimit()).toString(),checker);
				
			}else{
				mod_Fossil.ShowMessage(UntamedText,checker);
			}
			
		}
		/*public void HandleBreed(){
			if (this.age>4){
				this.BreedTick--;
				if (this.BreedTick==0){
					int GroupAmount=0;
					List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(32D, 32D, 32D));
					for (int i=0;i<list.size();i++){
						if (list.get(i) instanceof EntityStegosaurus) GroupAmount++;
					}
					if (GroupAmount>50) GroupAmount=50;
					if (GroupAmount>80) return;
					if (GroupAmount>50) GroupAmount=50;
					if (GroupAmount>80) return;
					if (new Random().nextInt(100)<GroupAmount){
						EntityDinoEgg entityegg=null;
						entityegg = (EntityDinoEgg)new EntityDinoEgg(worldObj,EnumDinoType.Stegosaurus);
						entityegg.setLocationAndAngles(this.posX+(new Random().nextInt(3)-1), this.posY, this.posZ+(new Random().nextInt(3)-1), worldObj.rand.nextFloat() * 360F, 0.0F);
						if(	worldObj.checkIfAABBIsClear(entityegg.boundingBox) && worldObj.getCollidingBoundingBoxes(entityegg, entityegg.boundingBox).size() == 0) worldObj.spawnEntityInWorld(entityegg);
						showHeartsOrSmokeFX(true);
					}
					this.BreedTick=3000;
				}
			}
		}*/
		public void SetOrder(EnumOrderType input){
			this.OrderStatus=input;
		}

		public boolean CheckEatable(int itemIndex) {
				//if (!(Item.itemsList[itemIndex] instanceof ItemFood))return false;
				Item tmp=Item.itemsList[itemIndex];
				boolean result=false;
				result=
						(tmp==Item.wheat)||
						(tmp==Item.melon);
				return result;
		}
		@Override
		public EntityAnimal spawnBabyAnimal(EntityAnimal entityanimal) {
			
			return new EntityStegosaurus(worldObj);
		}
		@Override
		public int getMaxHealth() {
			// TODO Auto-generated method stub
			return 20;
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
			return (float)(1.5F+0.3*this.getDinoAge());
		}

		@Override
		public float getGLY() {
			return (float)(1.5F+0.3*this.getDinoAge());
		}

}