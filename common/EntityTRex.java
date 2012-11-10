package net.minecraft.src;
import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;
public class EntityTRex extends EntityDinosaurce{
	
	public final int Areas=15;
	public final float HuntLimit=getHungerLimit()*(4F/5F);
    private boolean looksWithInterest;
    private float field_25048_b;
    private float field_25054_c;
    private boolean field_25052_g;
	//public int ScreamTick=0;
	public boolean Screaming=false;
	public int SkillTick=0;
	public int WeakToDeath=0;
	public int TooNearMessageTick=0;
	public boolean SneakScream=false;
	private final BlockBreakingRule blockBreakingBehavior=new BlockBreakingRule(worldObj,this,3,5.0f);
	
	public EntityTRex(World world)
    {
        super(world);
        SelfType=EnumDinoType.TRex;
        looksWithInterest = false;
        texture = "/skull/TRex.png";
        setSize(0.5F, 0.5F);
        this.moveSpeed = 0.3F;
        health = 10;     
		attackStrength=4+this.getDinoAge();
        //this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new DinoAIGrowup(this, 8,23));
		this.tasks.addTask(0, new DinoAIStarvation(this));

        this.tasks.addTask(1, new DinoAIAvoidEntityWhenYoung(this, EntityPlayer.class, 8.0F, 0.3F, 0.35F));
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        this.tasks.addTask(4, new DinoAIFollowOwner(this, this.moveSpeed, 5F, 2.0F));
        this.tasks.addTask(5, new DinoAIUseFeeder(this,this.moveSpeed,24,this.HuntLimit,EnumDinoEating.Carnivorous));
        this.tasks.addTask(6, new DinoAIWander(this, this.moveSpeed));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new DinoAIPickItem(this,Item.porkRaw,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this,Item.beefRaw,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this,Item.chickenRaw,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this,Item.porkCooked,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this,Item.beefCooked,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this,Item.chickenCooked,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this,mod_Fossil.RawDinoMeat,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this,mod_Fossil.CookedDinoMeat,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new DinoAITargetNonTamedExceptSelfClass(this, EntityLiving.class, 16.0F, 50, false));
    }
	public int getHungerLimit(){
		return 500;
	}
    public boolean isAIEnabled()
    {
        return (!this.isModelized() && this.riddenByEntity==null && !this.isWeak());
    }
	protected boolean canTriggerWalking()
    {
        return false;
    }
	public boolean isYoung(){
		return this.getDinoAge()<=3;
	}
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Angry", isSelfAngry());
        nbttagcompound.setBoolean("Sitting", isSelfSitting());
		nbttagcompound.setInteger("WeakToDeath", WeakToDeath);
    }
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);

        setSelfAngry(nbttagcompound.getBoolean("Angry"));
        setSelfSitting(nbttagcompound.getBoolean("Sitting"));
		InitSize();
		WeakToDeath=nbttagcompound.getInteger("WeakToDeath");

    }
	protected boolean canDespawn()
    {
        return false;
    }
	protected String getLivingSound()
    {
		if (worldObj.getClosestPlayerToEntity(this, 8D)!=null) return "Trex_Living";
		else return null;
    }
	protected String getHurtSound()
    {
        return "TRex_hit";
    }

    protected String getDeathSound()
    {
        return "Trex_Death";
    }
	protected void updateEntityActionState()
    {

		//super.updateEntityActionState();

    }
    public boolean getCanSpawnHere()
    {
        return worldObj.checkIfAABBIsClear(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.isAnyLiquid(boundingBox);
    }
    public boolean isInWater()
    {
    	if (this.onGround) return false;
    	else return super.isInWater();
    }
	public void onUpdate()
    {
		//fleeingTick=0;
        super.onUpdate();
        blockBreakingBehavior.execute();
        if (!(this.health<=0)){	
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
    }
    public void applyEntityCollision(Entity entity){
		if (entity instanceof EntityLiving && !(entity instanceof EntityPlayer)){
			if ((this.riddenByEntity!=null || this.isSelfAngry()) && this.onGround && this.getDinoAge()>3){
				this.onKillEntity((EntityLiving)entity);
				((EntityLiving)entity).attackEntityFrom(DamageSource.causeMobDamage(this), 10);
				return;
			}
		}
		super.applyEntityCollision(entity);
	}
	public boolean getSelfShaking()
    {
        return false;
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
	private void handleScream(){
		Entity target=this.getAttackTarget();
		if (target==null) {
			this.Screaming=false;
			return;
		}
		double distance=this.getDistanceSqToEntity(target);
		if (distance<=(double)(this.width * 4.0F * this.width * 4.0F)){
			this.Screaming=true;
		}else this.Screaming=false;
	}
	protected boolean isMovementCeased()
    {
        return isSelfSitting() || field_25052_g;
    }
	public boolean attackEntityFrom(DamageSource damagesource, int i)//being attack
    {
		if (damagesource.getEntity()==this) return false;
		Entity entity = damagesource.getEntity();
		if (damagesource.damageType.equals("arrow") && this.getDinoAge()>=3) return false;
		//if (damagesource.damageType.equals("arrow") && this.age>=3) return super.attackEntityFrom(damagesource, i/2);
		if (i<6 && entity!=null && this.getDinoAge()>=3) return false;
		if ((i==20) && (entity==null)) return super.attackEntityFrom(damagesource, 200);
		this.setTarget((EntityLiving)entity);
		return super.attackEntityFrom(damagesource, i);
		
    }
    protected void damageEntity(DamageSource damagesource, int i)
    {
        i = applyArmorCalculations(damagesource, i);
        i = applyPotionDamageCalculations(damagesource, i);
        health -= i;
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
    protected void attackEntity(Entity entity, float f)
    {
		//mod_Fossil.ShowMessage(new StringBuilder().append("Start attack").append(entity.toString()).append(",").append(f).append(",").append(width*1.6).toString());
        this.faceEntity(entity, 30F, 30F);
    	if (!hasPath()){
        	this.setPathToEntity(worldObj.getPathEntityToEntity(this, this.getEntityToAttack(), f, true, false, true, false));
        }
    	if(f > width*1.6 /*&& f < Areas && rand.nextInt(10) == 0*/)
        {
    		
            if(onGround)
            {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
                motionX = (d / (double)f1) * 0.5D * 0.80000001192092896D + motionX * 0.20000000298023224D;
                motionZ = (d1 / (double)f1) * 0.5D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
                if (getDinoAge()<=3) motionY = 0.40000000596046448D;
				//this.ScreamTick=15;
				if (f<(Areas/3)){
					if (!Screaming){
						if (getDinoAge()>=3) worldObj.playSoundAtEntity(this,"TRex_scream",getSoundVolume() * 2.0F, 1.0F);
						Screaming=true;
					}
				}
            }
        } else
        /*if((double)f <= width*1.6 && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)*/
        {
        	//if (this.Prey==null) this.Bite(entity);
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), attackStrength);
        	//entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
            //if (entity.isDead && this.Prey==entity) this.Prey=null;
        }
    }
	public void onKillEntity(EntityLiving entityliving)
    {
		super.onKillEntity(entityliving);
		if (getDinoAge()>=3) worldObj.playSoundAtEntity(this,"TRex_scream",getSoundVolume() * 2.0F, 1.0F);
        if (!isWeak()){
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
			heal(5);
        }
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

		if (this.EOCInteract(itemstack, entityplayer)) return true;
		if (!this.isTamed()){
			if (itemstack!=null){
				
				if (itemstack.itemID==mod_Fossil.Gen.shiftedIndex){
			       if (this.isWeak() && !this.isTamed() && getDinoAge()>3){    		   
			    			   this.heal(200);
			    			   this.HandleEating(500);
			    			   this.WeakToDeath=0;
			    			   this.setTamed(true);
			    			   this.setOwner(entityplayer.username);
			    			   itemstack.stackSize--;
			    			   return true;	
			       }else{
			    	   if (!this.isWeak()){
			    		   SendStatusMessage(EnumSituation.GemErrorHealth,this.SelfType);
			    		   itemstack.stackSize--;
			    	   }
			    	   if (this.getDinoAge()<=3) SendStatusMessage(EnumSituation.GemErrorYoung,this.SelfType);
			    	   return false;
			       }
		 	   }else return false;
			}else return false;
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

	public void setTamed(boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);
        if(flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 4)));
            this.texture="/skull/TRex.png";
        } else
        {
			//mod_Fossil.ShowMessage("A raptor won't trust mankind anymore.");
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -5)));
        }
    }
	/*protected void GrowUp(){
		if (this.isWeak()) return;
		int preage=age;
		if (this.riddenByEntity==null){
			if (age<=8){
			AgeTick++;
				if(AgeTick>=GROW_TIME_COUNT){
					//mod_Fossil.ShowMessage((new StringBuilder()).append((int)Math.round(0.1*age+0.3)).toString());
						AgeTick=0;
						age++;
						if (health<200 && !isWeak()) health+=23;
						setSize((float)(0.5F+0.5125*(float)age),(float)(0.5F+0.5125*(float)age));
						setPosition(posX,posY,posZ);
					if (!CheckSpace()){	
						age--;
						if (health>23) health-=23;
						setSize((float)(0.5F+0.5125*(float)age),(float)(0.5F+0.5125*(float)age));
						setPosition(posX,posY,posZ);
						if (isTamed()) SendStatusMessage(EnumSituation.NoSpace,this.SelfType); 
					}
					if (age>=3 && !this.isTamed()) texture="/skull/TRex_Adult.png";
					else texture="/skull/TRex.png";
					attackStrength=4+age;
				}
			}
			if (preage!=age && preage==2 && this.age==3) this.setPathToEntity(null);
		}
	}*/
	private void InitSize(){
		setSize((float)(0.5F+0.5125*(float)getDinoAge()),(float)(0.5F+0.5125*(float)getDinoAge()));
		setPosition(posX,posY,posZ);
		if (getDinoAge()>3 && !this.isTamed()) texture="/skull/TRex_Adult.png";
		else texture="/skull/TRex.png";
		attackStrength=4+getDinoAge();
	}
	public boolean CheckSpace(){
		/*range=Math.abs(range);
		for (int x=(int)Math.round(-range+posX);x<=(int)Math.round(range+posX);x++){
			for (int y=(int)Math.round(posY+1);y<=(int)Math.round(range+posY+1);y++){
				for (int z=(int)Math.round(-range+posZ);z<=(int)Math.round(range+posZ);z++){
					if (!worldObj.isAirBlock(x,y,z)) {
						//mod_Fossil.ShowMessage((new StringBuilder()).append(x).append(",").append(y).append(",").append(z).toString());
						Block Blocking=Block.blocksList[worldObj.getBlockId(x, y, z)];
						if (!(Blocking instanceof BlockFlower))	return false;
					}
				}
			}
		}
		return true;*/
		return !isEntityInsideOpaqueBlock();
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
		if (this.isDead) return;
		if (Hunger<=0) {
			if (!isTamed()) SendStatusMessage(EnumSituation.Starve,this.SelfType);
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
        } 
        if (this.riddenByEntity!=null)
        {
            riddenByEntity.setPosition(posX, posY+this.getGLY()*1.5, posZ);
        }

    }

	private void Flee(Entity EscapeFrom,int range){
		int DistanceX=new Random().nextInt(range)+1;
		int DistanceZ=(int)Math.round(Math.sqrt(Math.pow(range,2)-Math.pow(DistanceX,2)));
		int TargetX=0;
		int TargetY=0;
		int TargetZ=0;
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999F;
		if (EscapeFrom.posX<=posX) TargetX=(int)Math.round(posX)+DistanceX;
		else TargetX=(int)Math.round(posX)-DistanceX;
		if (EscapeFrom.posZ<=posZ) TargetZ=(int)Math.round(posZ)+DistanceZ;
		else TargetZ=(int)Math.round(posZ)-DistanceZ;
		for(int ic=128;ic>0;ic--){
			if (!worldObj.isAirBlock(TargetX, ic,TargetZ)){
				TargetY=ic;
				break;
			}
		}
		setTamed(false);
		setSelfSitting(false);
		this.setPathToEntity(worldObj.getEntityPathToXYZ(this, TargetX, TargetY, TargetZ, range, true, false, true, false));

		//mod_Fossil.ShowMessage(new StringBuilder().append("Escaping to:").append(TargetX).append(",").append(TargetY).append(",").append(TargetZ).toString());

	}
	/*private boolean HuntForPrey(int range){
		float NearestDistance=(float)range*2;
		if (this.getEntityToAttack()!=null && getEntityToAttack().isEntityAlive()){
			NearestDistance=GetDistanceWithEntity(getEntityToAttack());
		}
				//mod_Fossil.ShowMessage("Hunt Time!!"); 
				EntityLiving targetLiving=null;
				EntityLiving TempLiving=null;
				List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(range, 4D, range));
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
						if ((!(TempLiving instanceof EntityPlayer)||(this.age>=3)&&!(this.isTamed()))&&TempLiving!=this&&!TempLiving.isDead){	
						if (GetDistanceWithEntity((Entity)TempLiving)<NearestDistance){
								NearestDistance=GetDistanceWithEntity((Entity)TempLiving);
								targetLiving=TempLiving;
							}
						}
						//}
				} while(true);
				if (targetLiving!=null){
					this.setTarget(targetLiving);
					//mod_Fossil.ShowMessage(new StringBuilder().append("Target set at").append(targetLiving.toString()).toString()); 
					return true;
				}else return false;

		
	}*/
	public void onLivingUpdate()
	{
		if (!this.isWeak())handleScream();
		else HandleWeak();
		super.onLivingUpdate();
	}
	/*private void BlockInteractive(){
		if (!FossilOptions.TRexBreakingBlocks) return;
		float TargetHardness=0.0F;
		if (this.isTamed()) TargetHardness=1.0F;
		else TargetHardness=5.0F;
		
		for (int j=(int)Math.round(boundingBox.minX)-1;j<=(int)Math.round(boundingBox.maxX)+1;j++){
			for (int k=(int)Math.round(boundingBox.minY)-1;(k<=(int)Math.round(boundingBox.maxY)+3) &&(k<=127);k++){
				for (int l=(int)Math.round(boundingBox.minZ)-1;l<=(int)Math.round(boundingBox.maxZ)+1;l++){
					if (k<posY && this.isCollidedVertically){
						if(!worldObj.isAirBlock(j, k, l)){
							int blockid=worldObj.getBlockId(j,k,l);
							if (blockid==Block.grass.blockID && !this.isTamed()){
								worldObj.setBlockWithNotify(j,k,l,Block.dirt.blockID);
							}
							if (blockid==Block.ice.blockID){
								worldObj.playAuxSFX(2001, j, k, l, Block.ice.blockID);
								worldObj.setBlockWithNotify(j,k,l,0);
								worldObj.setBlockWithNotify(j, k, l, Block.waterMoving.blockID);
							}
							if (blockid==Block.glass.blockID){
								worldObj.playAuxSFX(2001, j, k, l, Block.ice.blockID);
								worldObj.setBlockWithNotify(j,k,l,0);
							}
						}
					}else{
						if (this.isCollidedHorizontally){
							if(!worldObj.isAirBlock(j, k, l)){
								if (!this.inWater){
									int blockid=worldObj.getBlockId(j,k,l);
									if (!this.isTamed() || this.riddenByEntity!=null){
										if (new Random().nextInt(5)==3){
											if (Block.blocksList[blockid].getBlockHardness(worldObj,(int)posX,(int)posY,(int)posZ)<TargetHardness || blockid==Block.wood.blockID){
												worldObj.playAuxSFX(2001, j, k, l, blockid);
												worldObj.setBlockWithNotify(j,k,l,0);
											}
										}
									}else{
										if (blockid==Block.wood.blockID || blockid==Block.leaves.blockID){
											worldObj.playAuxSFX(2001, j, k, l, blockid);
											worldObj.setBlockWithNotify(j,k,l,0);
										}
									}
								}
							}
						}
					}
					
				}
			}
		}
	}*/
	public String getTexture(){
		if (this.isModelized()) return super.getTexture();
		if (this.isBaby() || this.isTamed()) return "/skull/TRex.png";
		return "/skull/TRex_Adult.png";
	}
    private boolean isBaby() {
		return this.getDinoAge()<3;
	}
	protected void jump()
    {
    	if (!this.isInWater())
    	{
            if (this.riddenByEntity!=null) this.motionY+=0.41999998688697815D*1.5;
            else super.jump();
    	}
    	else
    		if (!this.onGround)this.motionY-=0.1D;
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
	public boolean isWeak(){
		return false;
		//return ((this.getHunger()==1 || this.getHealth()<=20) && this.getDinoAge()>3 && !this.isTamed());
	}
	private void HandleWeak(){
		if (worldObj.isRemote) return;
		if (texture!="/skull/TRexWeak.png") texture="/skull/TRexWeak.png";
		WeakToDeath++;
		if (WeakToDeath>=200) {
			this.attackEntityFrom(DamageSource.generic, 20);
			//mod_Fossil.ShowMessage("Died:Weak");
			return;
		}
		this.setTarget(null);
		this.setPathToEntity(null);
		this.setSelfAngry(false);
	}
	/*private boolean FindFeeder(int range){ if(range>6) range=6;
		TileEntityFeeder target=null;
		if (GetNearestTileEntity(2,range)!=null){
			target=(TileEntityFeeder)GetNearestTileEntity(2,range);
			if (GetDistanceWithTileEntity((TileEntity)target)>5){
				//mod_Fossil.ShowMessage(new StringBuilder().append("Setting Path:").append(target.xCoord).append(",").append(target.yCoord).append(",").append(target.zCoord).toString());
				setPathToEntity(worldObj.getEntityPathToXYZ((Entity)this,(int)Math.round(target.xCoord),(int)Math.round(target.yCoord),(int)Math.round(target.zCoord),(float)range, true, false, true, false));
				
			}else{
				if (target.CheckIsEmpty(this))return false;
				//FaceToCoord(target.xCoord,target.yCoord,target.zCoord);
				target.Feed(this);
			}
			return true;
		}else return false;
	}*/
	/*private TileEntity GetNearestTileEntity(int targetType,int range){
		if (posY<0) return null;
		final int Chest=0;
		final int Furance=1;
		final int Feeder=2;
		TileEntity result=null;
		TileEntity tmp=null;
		if (range>6) range=6;
		float Distance=range+1;
		for (int i=(int)Math.round(posX)-range;i<=(int)Math.round(posX)+range;i++){
			for (int j=(int)Math.round(posY)-range/2;j<=(int)Math.round(posY)+range/2;j++){
				if (j<0)j=0;
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

		/*private void HandleRiding(){
			EntityPlayerSP Rider=((EntityPlayerSP)this.riddenByEntity);
			MovementInputFromOptions PlayerMov=(MovementInputFromOptions)Rider.movementInput;
			//if (Math.abs(this.rotationYaw-riddenByEntity.rotationYaw)>5){
				//if (this.rotationYaw<riddenByEntity.rotationYaw) 	this.rotationYaw+=5;
				//if (this.rotationYaw>riddenByEntity.rotationYaw)	this.rotationYaw-=5;
			//}else{
				//this.rotationYaw=riddenByEntity.rotationYaw;
				if(riddenByEntity != null)
		        {

					if (this.riddenByEntity instanceof EntityPlayerSP){
						if (this.onGround){
							if (Rider.isJumping){
								this.jump();
								Rider.movementInput.jump=false;
							}
							if (((EntityPlayerSP)this.riddenByEntity).movementInput.sneak){
								this.rotationYaw-=(float) ((float)(((EntityPlayerSP) this.riddenByEntity).moveStrafing)*5);
					            for(; this.rotationYaw < -180F; this.rotationYaw += 360F) { }
					            for(; this.rotationYaw >= 180F; this.rotationYaw -= 360F) { }
								if (!this.SneakScream){
									this.SneakScream=true;
									worldObj.playSoundAtEntity(this,"TRex_scream",getSoundVolume(), 1.0F);
								}
							}else{
								if (this.getDinoAge()>3) BlockInteractive();
								this.rotationYaw-=(float) ((float)(Rider.moveStrafing)*5);
					            for(; this.rotationYaw < -180F; this.rotationYaw += 360F) { }
					            for(; this.rotationYaw >= 180F; this.rotationYaw -= 360F) { }
								this.moveForward=Rider.moveForward*this.moveSpeed;								
								if (this.SneakScream) this.SneakScream=false;
							}
						}
		        }
			}*/

			/*motionX += riddenByEntity.motionX*moveSpeed; 
            motionZ += riddenByEntity.motionZ*moveSpeed;
			//this.moveForward=((EntityPlayer)this.riddenByEntity).moveForward*moveSpeed;
			//this.moveStrafing=((EntityPlayer)this.riddenByEntity).moveStrafing*moveSpeed;
            //this.rotationPitch=riddenByEntity.rotationPitch;
			if (Math.abs(this.riddenByEntity.rotationYaw-this.rotationYaw)>=30)this.rotationYaw=this.riddenByEntity.rotationYaw;

            //moveEntity(motionX, motionY, motionZ);*/
		//}
		public void ShowPedia(EntityPlayer checker){
			//if (worldObj.isRemote) return;
			PediaTextCorrection(SelfType,checker);
			if (this.isTamed()){

					mod_Fossil.ShowMessage(new StringBuilder().append(OwnerText).append(this.getOwnerName()).toString(),checker);
					mod_Fossil.ShowMessage(new StringBuilder().append(AgeText).append(this.getDinoAge()).toString(),checker);
					mod_Fossil.ShowMessage(new StringBuilder().append(HelthText).append(this.health).append("/").append(20).toString(),checker);
					mod_Fossil.ShowMessage(new StringBuilder().append(HungerText).append(this.getHunger()).append("/").append(this.getHungerLimit()).toString(),checker);


				if (this.getDinoAge()>=4 && this.isTamed()) mod_Fossil.ShowMessage(RidiableText,checker);
			}else{
				if (!this.isWeak())mod_Fossil.ShowMessage(CautionText,checker);
				else mod_Fossil.ShowMessage(WeakText,checker);
			}
			
		}
		@Override
		public String[] additionalPediaMessage(){
			String[] result=null;
			if (!this.isTamed()){
				result=new String[1];
				result[0]=UntamedText;
			}else{
				ArrayList<String> resultList=new ArrayList<String>();
				if (this.getDinoAge()>=4 && this.isTamed())
					resultList.add(RidiableText);
				if (!this.isWeak() && !this.isTamed()) resultList.add(CautionText);
				if (!resultList.isEmpty()) {
					result=new String[1];
					result=resultList.toArray(result);
				}
			}
			return result;
		}
		public void SetOrder(EnumOrderType input){
			this.OrderStatus=input;
		}

		public boolean HandleEating(int FoodValue, boolean FernFlag) {
			return HandleEating(FoodValue);
		}

		public boolean CheckEatable(int itemIndex) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public EntityAnimal spawnBabyAnimal(EntityAnimal entityanimal) {
			return new EntityTRex(worldObj);
		}
		@Override
		public int getMaxHealth() {
			return 200;
		}
	    public float getSpeedModifier()
	    {
	    	float f = 1.0F;
	        if (this.getDinoAge()<3){
		        f = super.getSpeedModifier();
		        if (fleeingTick > 0)
		        {
		            f *= 3.0F;
		        }
	        }else{
	        	if (this.riddenByEntity!=null && this.riddenByEntity instanceof EntityPlayerSP){
	        		EntityPlayerSP Rider=(EntityPlayerSP)this.riddenByEntity;
	        		if (Rider.movementInput.sneak) f=5f;
	        	}
	        }
	        return f;
	    }

		@Override
		protected void updateSize(boolean shouldAddAge) {
			// TODO Auto-generated method stub
			
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
			return (float)(0.5F+0.5125*this.getDinoAge());
		}
		@Override
		public float getGLY() {
			return (float)(0.5F+0.5125*this.getDinoAge());
		}
}