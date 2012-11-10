package net.minecraft.src;
import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;
public class EntityPlesiosaur extends EntityDinosaurce implements IWaterDino{
    private boolean looksWithInterest;
	public final float HuntLimit=this.getHungerLimit()*4/5;
    private float field_25048_b;
    private float field_25054_c;
    private boolean isWolfShaking;
    private boolean field_25052_g;
	public int SubSpecies=1;
	public boolean isBaby=true;
	public int RushTick=0;
	public int BreedTick=3000;
	public float TargetY=0;
	private float randomMotionSpeed;
	private float randomMotionVecX=0.0F;
	private float randomMotionVecY=0.0F;
	private float randomMotionVecZ=0.0F;
	protected final int AGE_LIMIT=18;

	public EntityPlesiosaur(World world)
    {
        super(world);
        SelfType=EnumDinoType.Plesiosaur;
        looksWithInterest = false;
		SubSpecies=new Random().nextInt(3)+1;
		texture = "/skull/Plesiosaur_adult.png";
		//CheckSkin();
        setSize(1.0F, 1.0F);
		this.moveSpeed = 0.7F;
        this.getNavigator().setCanSwim(true);
		this.tasks.addTask(0, new DinoAIGrowup(this, 12));
		this.tasks.addTask(0, new DinoAIStarvation(this));
        this.tasks.addTask(1, new WaterDinoAISwimming(this,true,FLOAT_SPEED,-SINK_SPEED));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        this.tasks.addTask(4, new DinoAIFollowOwner(this, this.moveSpeed, 5F, 2.0F));
        this.tasks.addTask(6, new DinoAIUseFeeder(this,this.moveSpeed,24,this.HuntLimit, EnumDinoEating.Carnivorous));
        this.tasks.addTask(7, new DinoAIPickItem(this,Item.fishRaw,this.moveSpeed*2,24,this.HuntLimit));
        this.tasks.addTask(7, new DinoAIPickItem(this,Item.fishCooked,this.moveSpeed*2,24,this.HuntLimit));
        this.tasks.addTask(7, new DinoAIPickItem(this,mod_Fossil.SJL,this.moveSpeed*2,24,this.HuntLimit));
        this.tasks.addTask(8, new DinoAIFishing(this,this.HuntLimit,1));
        this.tasks.addTask(9, new DinoAIWander(this, this.moveSpeed));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(11, new EntityAILookIdle(this));
        //this.targetTasks.addTask(1, new WaterDinoAINearestAttackableTarget(this, EntityNautilus.class, 16.0F, 0, true));
        health = 8;
    }
	public int getHungerLimit(){
		return 500;
	}
    public boolean isAIEnabled()
    {
        return (!this.isModelized());
    }
	protected boolean canTriggerWalking()
    {
        return false;
    }
    public boolean canBreatheUnderwater()
    {
        return true;
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
		//CheckSkin();
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
		if (worldObj.getClosestPlayerToEntity(this, 8D)!=null) return "Pls_Living";
		else return null;  
    }
	protected String getHurtSound()
    {
        return "Pls_hurt";
    }

    protected String getDeathSound()
    {
        return "tri_death";
    }
	protected void updateEntityActionState()
    {
		if (this.isModelized()) return;
		if (this.riddenByEntity==null){
			super.updateEntityActionState();
			if (!this.isOnSurface() && this.TargetY<this.posY) this.TargetY=(float) this.posY++;
			if (!isSelfSitting()&& !hasPath()&&(new Random().nextInt(1000)==5)){
				FindFish(6);
			}
	        if(!worldObj.isRemote)
	        {
	            this.dataWatcher.updateObject(18, Integer.valueOf(this.getHealth()));
	        }
		}
    }
	
	public void onLivingUpdate()
    {
		
		if (this.riddenByEntity!=null)HandleRiding();
		if (this.isInWater()  && this.motionY<=0.0f) {
			if(this.getNavigator().noPath())	//Idle sinking
				this.motionY=this.IDLE_SINK_SPEED;	//In Water and sinking by gravity,so changing sinking speed.
			else
				this.motionY=this.SINK_SPEED;
		}
        super.onLivingUpdate();
    }
	public boolean isOnSurface(){
		return (worldObj.isAirBlock((int)Math.floor(this.posX), (int)Math.floor(posY+this.getEyeHeight()/2),(int)Math.floor(posZ)));
		
	}
	
	public void onUpdate()
    {

        super.onUpdate();


		if (this.isInWater()){
			if (!this.hasPath() && (this.riddenByEntity==null)){
				motionX*=0.8;
				motionZ*=0.8;
			}
			if (this.riddenByEntity!=null){
				EntityPlayerSP rider=(EntityPlayerSP)this.riddenByEntity;
				if (rider.movementInput.moveForward+rider.movementInput.moveStrafe==0.0F){
					motionX*=0.8;
					motionZ*=0.8;
				}
			}
			if (((!this.isOnSurface()&&TargetY>this.posY)||TargetY<this.posY)&& !this.isCollidedVertically) {
				if (TargetY>this.posY)this.motionY+=0.02;
				else this.motionY-=0.1;
				if (this.motionY>0.4) this.motionY=0.4;
				if (this.motionY<-2) this.motionY=-2;
			}
			if ((Math.abs(this.posY-this.TargetY)<=0.125)) this.motionY=0;
		}
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
            if(isCollidedHorizontally && isOffsetPositionInLiquid(motionX, ((motionY + 0.60000002384185791D) - posY) + d, motionZ))
            {
                motionY = 0.30000001192092896D;
            }
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
    public float getBlockPathWeight(int i, int j, int k)
    {
        return 0.5F - worldObj.getLightBrightness(i, j, k);
    }
    
    
	public float getInterestedAngle(float f)
    {
        return (field_25054_c + (field_25048_b - field_25054_c) * f) * 0.15F * 3.141593F;
    }
	public float getEyeHeight()
    {
        return height*(0.8F);
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
	protected void getPathOrWalkableBlock(Entity entity, float f)
    {
        PathEntity pathentity = worldObj.getPathEntityToEntity(this, entity, 16F, true, false, true, false);
        /*if(pathentity == null && f > 12F)
        {
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
        {*/
            setPathToEntity(pathentity);
            //TargetY=(float)entity.posY;
        //}
    }
	protected boolean isMovementCeased()
    {
        return isSelfSitting() || field_25052_g;
    }
	public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
		if (this.modelizedDrop()) return true;
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
		if (this.isModelized()) return modelizedInteract(entityplayer);
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if(itemstack != null && (itemstack.itemID==Item.fishRaw.shiftedIndex || itemstack.itemID==Item.fishCooked.shiftedIndex||itemstack.itemID==mod_Fossil.SJL.shiftedIndex))
            {
				//if (EatTick<=0){
					if((itemstack.itemID==Item.fishRaw.shiftedIndex || itemstack.itemID==Item.fishCooked.shiftedIndex) && HandleEating(45))
					{
						itemstack.stackSize--;
						if(itemstack.stackSize <= 0)
						{
							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
						}
						heal(3);

					}
				/*}else{
					SendStatusMessage(EnumSituation.ChewTime);
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

		if (itemstack!=null && itemstack.itemID==mod_Fossil.ChickenEss.shiftedIndex){
			if (this.getDinoAge()>=18 || this.getHunger()<=0)return false;
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
        if(itemstack != null && itemstack.itemID==mod_Fossil.EmptyShell.shiftedIndex){
        	//need change
        	if(entityplayer.username.equalsIgnoreCase(getOwnerName())){				
				 if(!worldObj.isRemote)
				{
					isJumping = false;
					setPathToEntity(null);
					int TypeSwitch=(mod_Fossil.EnumToInt(OrderStatus)+1)%3;
					OrderStatus=EnumOrderType.values()[TypeSwitch];
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
		if (this.isTamed() && this.getDinoAge()>4 &&!worldObj.isRemote && (riddenByEntity == null || riddenByEntity == entityplayer)){
		 	entityplayer.rotationYaw=this.rotationYaw;
            entityplayer.mountEntity(this);
            this.setPathToEntity(null);
            this.renderYawOffset=this.rotationYaw;
            return true;
		}
        return false;
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
	
		if (age<=this.AGE_LIMIT && this.riddenByEntity==null){
		AgeTick++;
			if(AgeTick>=GROW_TIME_COUNT){
				//mod_Fossil.ShowMessage((new StringBuilder()).append((int)Math.round(1.0+age*0.3)).toString());

					AgeTick=0;
					age++;
					//if (age>4 && isBaby) ChangeTexture();
					if (health<20) health+=1;
					this.updateSize(false);
					setPosition(posX,posY,posZ);
				if (!CheckSpace()){	
					age--;
					isBaby=(age<=4);
					//CheckSkin();
					if (health>1) health-=1;
					this.updateSize(false);
					setPosition(posX,posY,posZ);
					if (isTamed()) SendStatusMessage(EnumSituation.NoSpace); 
				}
				this.moveSpeed=0.5F+this.age*3;
			}
		}
		
	}*/
	private void InitSize(){
		//if (age>4 && isBaby) ChangeTexture();
		this.updateSize(false);
		setPosition(posX,posY,posZ);
		this.moveSpeed=0.5F+this.getDinoAge()*3;
	}
	public boolean CheckSpace(){
		if (!this.isInWater()) return !isEntityInsideOpaqueBlock();
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
	/*private void ChangeTexture(){
			isBaby=false;
			CheckSkin();
	}
	private void CheckSkin(){
		if (isBaby){
			switch (SubSpecies){
			case 1:
				texture = "/skull/Triceratops_Baby_1.png";
				break;
			case 2:
				texture = "/skull/Triceratops_Baby_2.png";
				break;
			case 3:
				texture = "/skull/Triceratops_Baby_3.png";
				break;
			default:
				texture = "/skull/Triceratops_Baby_1.png";
		}
		}else{
			switch (SubSpecies){
				case 1:
					texture = "/skull/Triceratops_Adult_1.png";
					break;
				case 2:
					texture = "/skull/Triceratops_Adult_2.png";
					break;
				case 3:
					texture = "/skull/Triceratops_Adult_3.png";
					break;
				default:
					texture = "/skull/Triceratops_Adult_1.png";
			}
		}
	}*/
	public void updateRiderPosition()
    {
        if(riddenByEntity == null)
        {
            return;
        } else
        {
            riddenByEntity.setPosition(posX, posY+this.getGLY()*0.75+0.07*(18-this.getDinoAge()), posZ);
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
				setPathToEntity(worldObj.getEntityPathToXYZ((Entity)this,(int)Math.round(posX+targetX),(int)Math.round(posY+targetY),(int)Math.round(posZ+targetZ),10F));
				return true;
			}else{
				if (EatTick==0){
					FaceToCoord((int)-(posX+targetX),(int)(posY+targetY),(int)-(posZ+targetZ));
					HandleEating(10,true);
					worldObj.setBlockWithNotify((int)Math.round(posX+targetX),(int)Math.round(posY+targetY),(int)Math.round(posZ+targetZ),0);
					if (worldObj.getBlockId((int)Math.round(posX+targetX),(int)Math.round(posY+targetY)+1,(int)Math.round(posZ+targetZ))==mod_Fossil.FernUpper.blockID) worldObj.setBlockWithNotify((int)Math.round(posX+targetX),(int)Math.round(posY+targetY)+1,(int)Math.round(posZ+targetZ),0);
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
	private boolean FindFish(int range){
		if (!isSelfSitting()){
			List NearBy=worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.0D, 0.0D, 1.0D));
			if (NearBy!=null){
				for(int i = 0; i < NearBy.size(); i++)
				{	
					if (NearBy.get(i) instanceof EntityItem){
						EntityItem ItemInRange=(EntityItem)NearBy.get(i);
						if (ItemInRange.item.itemID==Item.fishRaw.shiftedIndex || ItemInRange.item.itemID==Item.fishCooked.shiftedIndex){
							HandleEating(10);
							worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((new Random().nextFloat() - new Random().nextFloat()) * 0.7F + 1.0F) * 2.0F);
							ItemInRange.setDead();
							return true;
						}
					}
				}		
			}
			return false;
		}else return false;	
	}
	/*private boolean FindFeeder(int range){ 
		if(range>6) range=6;
		if (this.isInWater()) return false;
		TileEntityFeeder target=null;

		if (GetNearestTileEntity(2,range)!=null){
			target=(TileEntityFeeder)GetNearestTileEntity(2,range);
			if (GetDistanceWithTileEntity((TileEntity)target)>5){
				//mod_Fossil.ShowMessage(new StringBuilder().append("Setting Path:").append(target.xCoord).append(",").append(target.yCoord).append(",").append(target.zCoord).toString());
				if (this.OrderStatus!=EnumOrderType.Stay)setPathToEntity(worldObj.getEntityPathToXYZ((Entity)this,(int)Math.round(target.xCoord),(int)Math.round(target.yCoord),(int)Math.round(target.zCoord),(float)range, true, false, true, false));
				
			}else{
				if (target.CheckIsEmpty(this))return false;
				FaceToCoord(target.xCoord,target.yCoord,target.zCoord);
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
		if (range>6) range=6;
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
	    protected void jump()
	    {
	    	if (this.isInWater() && isCollidedHorizontally && this.riddenByEntity==null) {
	    		super.jump();
	    	}
	    }
	    public boolean isInWater()
	    {
	        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
	    }
		private void HandleRiding(){
			if (this.RushTick>0) this.RushTick--;
				if(riddenByEntity != null)
		        {

					if (this.riddenByEntity instanceof EntityPlayerSP){
						if (this.onGround || this.isInWater()){
							if (((EntityPlayerSP)this.riddenByEntity).movementInput.jump){
								this.jump();
								((EntityPlayerSP)this.riddenByEntity).movementInput.jump=false;
							}							
								this.rotationYaw-=(float) ((float)(((EntityPlayerSP) this.riddenByEntity).moveStrafing)*5);
					            for(; this.rotationYaw < -180F; this.rotationYaw += 360F) { }
					            for(; this.rotationYaw >= 180F; this.rotationYaw -= 360F) { }
								this.moveForward=((EntityPlayerSP) this.riddenByEntity).moveForward*this.moveSpeed;
						}
						if (((EntityPlayerSP)this.riddenByEntity).movementInput.sneak){
							this.TargetY=(float)this.posY-0.5F;
						}else{
							if (this.isOnSurface())this.TargetY=(float) this.posY;
							else if (((EntityPlayerSP) this.riddenByEntity).moveForward==0)this.TargetY=(float)this.posY+0.2F;
							else this.TargetY=(float)this.posY;
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
			if (!this.isInWater())super.applyEntityCollision(entity);
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
			//PediaTextCorrection(SelfType,checker);
			if (this.isTamed()){
					mod_Fossil.ShowMessage(new StringBuilder().append(OwnerText).append(this.getOwnerName()).toString(),checker);
					mod_Fossil.ShowMessage(new StringBuilder().append(AgeText).append(this.getDinoAge()).toString(),checker);
					mod_Fossil.ShowMessage(new StringBuilder().append(HelthText).append(this.health).append("/").append(20).toString(),checker);
					mod_Fossil.ShowMessage(new StringBuilder().append(HungerText).append(this.getHunger()).append("/").append(this.getHungerLimit()).toString(),checker);


				//if ((this.isSelfTamed() && this.age>4 &&!worldObj.multiplayerWorld && riddenByEntity == null)) mod_Fossil.ShowMessage(" * Ride-able.");
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
						if (list.get(i) instanceof EntityPlesiosaur) GroupAmount++;
					}
					if (GroupAmount>50) GroupAmount=50;
					if (GroupAmount>80) return;
					if (new Random().nextInt(100)<GroupAmount){
						EntityDinoEgg entityegg=null;
						entityegg = (EntityDinoEgg)new EntityDinoEgg(worldObj,EnumDinoType.Plesiosaur);
						entityegg.setLocationAndAngles(this.posX+(new Random().nextInt(3)-1), this.posY, this.posZ+(new Random().nextInt(3)-1), worldObj.rand.nextFloat() * 360F, 0.0F);
						if(	worldObj.checkIfAABBIsClear(entityegg.boundingBox) && worldObj.getCollidingBoundingBoxes(entityegg, entityegg.boundingBox).size() == 0) worldObj.spawnEntityInWorld(entityegg);
						showHeartsOrSmokeFX(true);
					}
					this.BreedTick=3000;
				}
			}
		}*/
		public void onKillEntity(EntityLiving entityliving)
	    {
			super.onKillEntity(entityliving);
			if (entityliving instanceof EntityNautilus){
				HandleEating(100);
			}
			heal(5);			
	    }
		public void SetOrder(EnumOrderType input){
			this.setPathToEntity(null);
			this.setTarget(null);
			this.OrderStatus=input;
		}
		@Override
		public EntityAnimal spawnBabyAnimal(EntityAnimal entityanimal) {
			return new EntityPlesiosaur(worldObj);
		}
		@Override
		public int getMaxHealth() {
			return 200;
		}

		@Override
		protected void updateSize(boolean shouldAddAge) {
			if (shouldAddAge && this.getDinoAge()<this.AGE_LIMIT) this.increaseDinoAge();
			setSize((float)(1.0F+0.3*(float)this.getDinoAge()),(float)(1.0F+0.3*(float)this.getDinoAge()));
			
		}
		public EnumOrderType getOrderType() {
			return this.OrderStatus;
		}
		@Override
		protected int foodValue(Item asked) {
			if (asked==Item.fishRaw || asked==Item.fishCooked || asked==mod_Fossil.SJL) return 10;
			return 0;
		}

		@Override
		public void HoldItem(Item itemGot) {
			return;
			
		}
		@Override
		public float getGLX() {
			return (float)(1.0F+0.3*(float)this.getDinoAge());
		}
		@Override
		public float getGLY() {
			return (float) (1.0F+0.3*(float)this.getDinoAge());
		}

}