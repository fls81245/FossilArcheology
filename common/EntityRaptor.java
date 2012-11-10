package net.minecraft.src;

import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;

public class EntityRaptor extends EntityDinosaurce implements IHighIntellegent {
	private boolean looksWithInterest;

	public final float HuntLimit = getHungerLimit() * 4 / 5;
	private float field_25048_b;
	private float field_25054_c;
	private boolean isWolfShaking;
	private boolean field_25052_g;
	private float timeWolfIsShaking;
	private float prevTimeWolfIsShaking;
	public ItemStack ItemInMouth = null;

	public int BreedTick = 3000;
	public boolean PreyChecked = false;
	public boolean SupportChecked = false;
	public Vector MemberList = new Vector();
	public float SwingAngle = RenderRaptor.SwingBackSignal;
	public int FleeingTick = 0;
	public int DoorOpeningTick = 0;

	public EntityRaptor(World world) {
		super(world);
		SelfType = EnumDinoType.Raptor;
		looksWithInterest = false;
		this.CheckSkin();
		setSize(0.3F, 0.3F);
		moveSpeed = 0.3F;
		health = 10;
		this.setHunger(getHungerLimit());
		this.attackStrength = 2 + this.getDinoAge();
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new DinoAIGrowup(this, 8));
		this.tasks.addTask(0, new DinoAIStarvation(this));
		// TODO:Breeding
		this.tasks.addTask(1, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityTRex.class,
				8.0F, 0.3F, 0.35F));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityBrachiosaurus.class,
				8.0F, 0.3F, 0.35F));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, this.moveSpeed,
				true));
		this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(5, new DinoAIFollowOwner(this, this.moveSpeed, 5F,
				2.0F));
		this.tasks.addTask(6, new DinoAIUseFeeder(this, this.moveSpeed, 24,
				this.HuntLimit, EnumDinoEating.Carnivorous));
        this.tasks.addTask(6, new DinoAIPickItem(this,Item.porkRaw,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(6, new DinoAIPickItem(this,Item.beefRaw,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(6, new DinoAIPickItem(this,Item.chickenRaw,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(6, new DinoAIPickItem(this,Item.porkCooked,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(6, new DinoAIPickItem(this,Item.beefCooked,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(6, new DinoAIPickItem(this,Item.chickenCooked,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(6, new DinoAIPickItem(this,mod_Fossil.RawDinoMeat,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(6, new DinoAIPickItem(this,mod_Fossil.CookedDinoMeat,this.moveSpeed,24,this.HuntLimit));
		this.tasks.addTask(7, new EntityAIWander(this, 0.3F));
		this.tasks.addTask(7, new DinoAILearnChest(this));
		// TODO:chestOpening
		this.tasks.addTask(8, new EntityAIWatchClosest(this,
				EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(4, new DinoAITargetNonTamedExceptSelfClass(
				this, EntityLiving.class, 16.0F, 50, false));

	}

	public boolean isAIEnabled() {
		return true;
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(this.CHEST_LEARNING_PROGRESS_DATA_INDEX,
				new Byte((byte) 0));
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	public String getTexture() {
		if (this.isModelized()) return super.getTexture();
		if (this.getAge() > 3) {
			switch (this.getSubSpecies()) {
			case 1:
				return "/skull/raptor_blue_adult.png";
			case 2:
				return "/skull/raptor_green_adult.png";
			default:
				return "/skull/Raptor_Adult.png";
			}
		} else {
			switch (this.getSubSpecies()) {
			case 1:
				return "/skull/raptor_blue_Baby.png";
			case 2:
				return "/skull/raptor_green_Baby.png";
			default:
				return "/skull/Raptor_Baby.png";
			}
		}
	}

	protected void jump() {
		motionY = 0.41999998688697815D * (1 + this.getDinoAge() / 16);
		;
		if (isSprinting()) {
			float f = rotationYaw * 0.01745329F;
			motionX -= MathHelper.sin(f) * 0.2F;
			motionZ += MathHelper.cos(f) * 0.2F;
		}
		isAirBorne = true;
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		if (ItemInMouth != null) {
			nbttagcompound.setShort("Itemid", (short) ItemInMouth.itemID);
			nbttagcompound.setByte("ItemCount", (byte) ItemInMouth.stackSize);
			nbttagcompound.setShort("ItemDamage",
					(short) ItemInMouth.getItemDamage());
		} else {
			nbttagcompound.setShort("Itemid", (short) -1);
			nbttagcompound.setByte("ItemCount", (byte) 0);
			nbttagcompound.setShort("ItemDamage", (short) 0);
		}
		nbttagcompound.setBoolean("LearntChest", this.isLeartChest());
		nbttagcompound.setBoolean("Angry", isSelfAngry());
		nbttagcompound.setBoolean("Sitting", isSelfSitting());
		nbttagcompound.setInteger("SubType", this.getSubSpecies());
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		short Itemid = nbttagcompound.getShort("Itemid");
		byte ItemCount = nbttagcompound.getByte("ItemCount");
		short ItemDamage = nbttagcompound.getShort("ItemDamage");
		if (Itemid != -1)
			ItemInMouth = new ItemStack((int) Itemid, (int) ItemCount,
					(int) ItemDamage);
		else
			ItemInMouth = null;

		this.setHungerTick(nbttagcompound.getInteger("HungerTick"));

		this.setLearntChest(nbttagcompound.getBoolean("LearntChest"));
		setSelfAngry(nbttagcompound.getBoolean("Angry"));
		setSelfSitting(nbttagcompound.getBoolean("Sitting"));

		if (nbttagcompound.hasKey("SubType")) {
			this.setSubSpecies(nbttagcompound.getInteger("SubType"));
		}
		InitSize();


	}

	protected boolean canDespawn() {
		return false;
	}

	protected String getLivingSound() {
		// return "mob.wolf.growl";
		if (this.isTamed())
			return "raptor_living_friendly";
		else
			return "raptor_living_wild";
	}

	protected String getHurtSound() {
		return "Raptor_hurt";
		// return "raptor_living";
	}

	protected String getDeathSound() {
		return "raptor_death";
	}

	public boolean getCanSpawnHere() {
		return worldObj.checkIfAABBIsClear(boundingBox)
				&& worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0
				&& !worldObj.isAnyLiquid(boundingBox);
	}

	/*
	 * public void onLivingUpdate() { super.onLivingUpdate(); //this.OpenDoor();
	 * //1.If something in mouth,check if it's eat-able. if (ItemInMouth!=null){
	 * if (this.CheckEatable(ItemInMouth.itemID)){ ItemFood
	 * tmp=(ItemFood)ItemInMouth.getItem(); if (EatTick<=0){ if
	 * (HandleEating(30)){
	 * heal(((ItemFood)(ItemInMouth.getItem())).getHealAmount());
	 * ItemInMouth=null; } } } }else{ //2.If nothing in mouth,check if there are
	 * something pick-able around. if (!hasPath() &&
	 * OrderStatus==EnumOrderType.FreeMove && isTamed()){
	 * FindAndHoldItems(-1,6); } //3.If nothing around or self is not
	 * tamed,check if self is able to hunt or use feeder if (!hasPath() &&
	 * (OrderStatus
	 * ==EnumOrderType.FreeMove||OrderStatus==EnumOrderType.Follow)){ if
	 * (HuntForPrey(16D)){ if (!FindAndHoldItems(Item.porkRaw.shiftedIndex,32))
	 * FindAndHoldItems(Item.porkCooked.shiftedIndex,32); }else{ FindFeeder(16);
	 * } } }
	 * 
	 * }
	 */
	public void onUpdate() {
		super.onUpdate();
		/*
		 * Level 1 AI seq.
		 */
		// HangleChestLearning();
		HandleBreed();
		field_25054_c = field_25048_b;
		if (looksWithInterest) {
			field_25048_b = field_25048_b + (1.0F - field_25048_b) * 0.4F;
		} else {
			field_25048_b = field_25048_b + (0.0F - field_25048_b) * 0.4F;
		}
		if (looksWithInterest) {
			numTicksToChaseTarget = 10;
		}
	}

	public boolean getSelfShaking() {
		return false;
	}

	public float getShadingWhileShaking(float f) {
		return 0.75F + ((prevTimeWolfIsShaking + (timeWolfIsShaking - prevTimeWolfIsShaking)
				* f) / 2.0F) * 0.25F;
	}

	public float getShakeAngle(float f, float f1) {
		float f2 = (prevTimeWolfIsShaking
				+ (timeWolfIsShaking - prevTimeWolfIsShaking) * f + f1) / 1.8F;
		if (f2 < 0.0F) {
			f2 = 0.0F;
		} else if (f2 > 1.0F) {
			f2 = 1.0F;
		}
		return MathHelper.sin(f2 * 3.141593F)
				* MathHelper.sin(f2 * 3.141593F * 11F) * 0.15F * 3.141593F;
	}

	public float getEyeHeight() {
		return height * 0.8F;
	}

	public int getVerticalFaceSpeed() {
		if (isSelfSitting()) {
			return 20;
		} else {
			return super.getVerticalFaceSpeed();
		}
	}

	protected boolean isMovementCeased() {
		return isSelfSitting() || field_25052_g;
	}

	public boolean attackEntityFrom(DamageSource damagesource, int i)// being
																		// attack
	{
		Entity entity = damagesource.getEntity();
		boolean isPlayerAttack = false;
		setSelfSitting(false);// stand up(dog)
		if (entity != null && !(entity instanceof EntityPlayer)
				&& !(entity instanceof EntityArrow))// (if attacker not player
													// or arrow)
		{
			i = (i + 1) / 2;
		}
		if (super.attackEntityFrom(damagesource, i)) {
			if (!isSelfAngry()) {
				if (entity instanceof EntityPlayer) {
					setTamed(false);
					setOwner("");
					ItemInMouth = null;
					// setSelfAngry(true);
					this.PreyChecked = true;
					isPlayerAttack = true;
				}
				if ((entity instanceof EntityArrow)
						&& ((EntityArrow) entity).shootingEntity != null) {
					entity = ((EntityArrow) entity).shootingEntity;
				}
				if (entity instanceof EntityLiving) {
					this.setAttackTarget((EntityLiving) entity);
				}
			} else if (entity != this && entity != null) {
				entityToAttack = entity;
			}
			return true;
		} else {
			return false;
		}
	}

	protected Entity findPlayerToAttack() {
		/*
		 * if(isSelfAngry()) { return worldObj.getClosestPlayerToEntity(this,
		 * 16D); } else {
		 */
		return null;
		// }
	}

	protected void attackEntity(Entity entity, float f) {
		// mod_Fossil.ShowMessage(new
		// StringBuilder().append("Range:").append(f).toString());
		// mod_Fossil.ShowMessage(new
		// StringBuilder().append("GLsize:").append(GLsizeX).toString());
		// mod_Fossil.ShowMessage(new
		// StringBuilder().append("BondingX:").append(boundingBox.minX).append(",").append(boundingBox.maxX).toString());
		if (entity.isDead)
			this.setAttackTarget(null);
		if (f > 2.0F && f < 5.0F && rand.nextInt(10) == 0) {
			if (onGround) {
				double d = entity.posX - posX;
				double d1 = entity.posZ - posZ;
				float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
				motionX = (d / (double) f1) * 0.5D * 0.80000001192092896D
						+ motionX * 0.20000000298023224D;
				motionZ = (d1 / (double) f1) * 0.5D * 0.80000001192092896D
						+ motionZ * 0.20000000298023224D;
				worldObj.playSoundAtEntity(this, "Raptor_attack",
						getSoundVolume() * 2.0F, 1.0F);
				jump();
			}
		} else if ((double) f < 1.9F
				&& entity.boundingBox.maxY > boundingBox.minY
				&& entity.boundingBox.minY < boundingBox.maxY) {
			attackTime = 20;
			entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2 + this.getDinoAge());
		}
	}

	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null) {
			if (itemstack.getItem().getItemUseAction(itemstack) == EnumAction.bow)
				return false;
			if (FMLCommonHandler.instance().getSide().isClient()){
				if (itemstack.itemID == mod_Fossil.DinoPedia.shiftedIndex) {
		        	//this.ShowPedia(entityplayer);			//old function
		        	EntityDinosaurce.pediaingDino=this;		//new function
		        	mod_Fossil.callGUI(entityplayer, FossilGuiHandler.DINOPEDIA_GUI_ID, worldObj, (int)(this.posX), (int)(this.posY), (int)(this.posZ));
				return true;
			}
			}

			if (isTamed()) {
				if (itemstack.itemID == mod_Fossil.ChickenEss.shiftedIndex) {
					if (this.getDinoAge() >= 8 || this.getHunger() <= 0)
						return false;
					itemstack.stackSize--;
					if (itemstack.stackSize <= 0) {
						entityplayer.inventory.setInventorySlotContents(
								entityplayer.inventory.currentItem, null);
					}
					entityplayer.inventory
							.addItemStackToInventory(new ItemStack(
									Item.glassBottle, 1));
					this.setDinoAgeTick(GROW_TIME_COUNT);
					this.setHunger(1 + new Random().nextInt(this.getHunger()));
					return true;
				}
				if (ItemInMouth == null) {
					ItemInMouth = new ItemStack(itemstack.getItem(), 1,
							itemstack.getItemDamage());
					itemstack.stackSize--;
					if (itemstack.stackSize <= 0) {
						entityplayer.inventory.setInventorySlotContents(
								entityplayer.inventory.currentItem, null);
					}
					return true;
				}

			}
		} else {
			if (ItemInMouth != null) {
				/*
				 * if(worldObj.multiplayerWorld) { return false; }
				 */
				int i = ItemInMouth.stackSize;
				if (entityplayer.inventory.addItemStackToInventory(ItemInMouth)) {
					ModLoader.onItemPickup(entityplayer, ItemInMouth);
					worldObj.playSoundAtEntity(
							entityplayer,
							"random.pop",
							0.2F,
							((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				} else
					return false;
				ItemInMouth = null;
				return true;
			} else {
				if (entityplayer.username.equalsIgnoreCase(getOwnerName())) {
					if (!worldObj.isRemote) {
						isJumping = false;
						setPathToEntity(null);
						this.SetOrder(EnumOrderType.values()[(mod_Fossil
								.EnumToInt(OrderStatus) + 1) % 3]);
						SendOrderMessage(OrderStatus);
						switch (OrderStatus) {
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
		}
		return false;

	}

	public void handleHealthUpdate(byte byte0) {
		if (byte0 == 7) {
			showHeartsOrSmokeFX(true);
		} else if (byte0 == 6) {
			showHeartsOrSmokeFX(false);
		} else if (byte0 == 8) {
			field_25052_g = true;
			timeWolfIsShaking = 0.0F;
			prevTimeWolfIsShaking = 0.0F;
		} else {
			super.handleHealthUpdate(byte0);
		}
	}

	public int getMaxSpawnedInChunk() {
		return 100;
	}

	public boolean isSelfAngry() {
		return (dataWatcher.getWatchableObjectByte(16) & 2) != 0;
	}

	public boolean isSelfSitting() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setSelfAngry(boolean flag) {
		byte byte0 = dataWatcher.getWatchableObjectByte(16);
		if (flag) {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 | 2)));
		} else {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 & -3)));
		}
	}

	public void setSelfSitting(boolean flag) {
		byte byte0 = dataWatcher.getWatchableObjectByte(16);
		if (flag) {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 | 1)));
		} else {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 & -2)));
		}
	}

	public void setTamed(boolean flag) {
		byte byte0 = dataWatcher.getWatchableObjectByte(16);
		if (flag) {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 | 4)));
		} else {
			ItemInMouth = null;
			// mod_Fossil.ShowMessage("A velociraptor won't trust mankind anymore.");
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 & -5)));
		}
	}

	protected void fall(float f) {
		// super.fall(f);
		if (riddenByEntity != null) {
			riddenByEntity.fall(f);
		}
		int i = (int) Math.ceil(f - 3F);
		if (i > 0) {
			attackEntityFrom(DamageSource.fall, 0);
			int j = worldObj.getBlockId(
					MathHelper.floor_double(posX),
					MathHelper.floor_double(posY - 0.20000000298023224D
							- (double) yOffset), MathHelper.floor_double(posZ));
			if (worldObj.isRemote) return;
			if (j > 0) {
				StepSound stepsound = Block.blocksList[j].stepSound;
				worldObj.playSoundAtEntity(this, stepsound.getBreakSound(),
						stepsound.getVolume() * 0.5F,
						stepsound.getPitch() * 0.75F);
			}
		}
	}

	protected void updateWanderPath() {
		boolean flag = false;
		int i = -1;
		int j = -1;
		int k = -1;
		float f = -99999F;
		if (OrderStatus == OrderStatus.FreeMove || !isTamed()) {
			for (int l = 0; l < 10; l++) {
				int i1 = MathHelper.floor_double((posX + (double) rand
						.nextInt(13)) - 6D);
				int j1 = MathHelper.floor_double((posY + (double) rand
						.nextInt(7)) - 3D);
				int k1 = MathHelper.floor_double((posZ + (double) rand
						.nextInt(13)) - 6D);
				float f1 = getBlockPathWeight(i1, j1, k1);
				if (f1 > f) {
					f = f1;
					i = i1;
					j = j1;
					k = k1;
					flag = true;
				}
			}

			if (flag) {
				setPathToEntity(worldObj.getEntityPathToXYZ(this, i, j, k, 10F,
						true, false, true, false));
			}
		}
	}

	private void InitSize() {
		this.CheckSkin();
		setSize((float) (0.3F + 0.1 * (float) this.getAge()),
				(float) (0.3F + 0.1 * (float) this.getAge()));
		setPosition(posX, posY, posZ);
	}

	/*public void CheckSkin() {
		if (this.getAge() > 3) {
			switch (this.getSubSpecies()) {
			case 1:
				texture = "/skull/raptor_blue_adult.png";
				break;
			case 2:
				texture = "/skull/raptor_green_adult.png";
				break;
			default:
				texture = "/skull/Raptor_Adult.png";
			}
		} else {
			switch (this.getSubSpecies()) {
			case 1:
				texture = "/skull/raptor_blue_Baby.png";
				break;
			case 2:
				texture = "/skull/raptor_green_Baby.png";
				break;
			default:
				texture = "/skull/Raptor_Baby.png";
			}
		}
	}*/

	/*
	 * private boolean FindAndHoldItems(int ItemID,int range){ if
	 * (!isSelfSitting()){ if (ItemID!=-1 && isLeartChest() &&
	 * GetNearestTileEntity(0,range)!=null){ TileEntity
	 * target=GetNearestTileEntity(0,range); if
	 * (GetDistanceWithTileEntity(target)>5){ FindChest(range); return true;
	 * }else{ if (TakeItemFromChest((TileEntityChest)target,ItemID)){
	 * ItemInMouth=new ItemStack(ItemID,1,0); return true; } } } List
	 * NearBy=worldObj.getEntitiesWithinAABBExcludingEntity(this,
	 * boundingBox.expand(1.0D, 0.0D, 1.0D)); if (NearBy!=null){ for(int i = 0;
	 * i < NearBy.size(); i++) { if (NearBy.get(i) instanceof EntityItem){
	 * EntityItem ItemInRange=(EntityItem)NearBy.get(i); if (ItemID==-1 ||
	 * ItemInRange.item.itemID==ItemID){ ItemInMouth=ItemInRange.item;
	 * worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((new
	 * Random().nextFloat() - new Random().nextFloat()) * 0.7F + 1.0F) * 2.0F);
	 * ItemInRange.setDead(); return true; } } } } EntityItem TargetItem=null;
	 * List
	 * searchlist=worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityItem
	 * .class, AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX +
	 * 1.0D, posY + 1.0D, posZ + 1.0D).expand(range, 4D, range)); Iterator
	 * iterator = searchlist.iterator(); do { if(!iterator.hasNext()) { break; }
	 * Entity entity1 = (Entity)iterator.next(); if (TargetItem!=null){ if
	 * (GetDistanceWithEntity(entity1)<GetDistanceWithEntity(TargetItem)){
	 * TargetItem = (EntityItem)entity1; } }else{ TargetItem =
	 * (EntityItem)entity1; } } while(true); if (TargetItem!=null){
	 * setPathToEntity
	 * (worldObj.getPathEntityToEntity(this,TargetItem,(float)range
	 * ,true,false,true,false)); return true; }else return false; }else return
	 * false; }
	 */
	/*@Override
	protected boolean HandleHunger() {
		if (this.ItemInMouth != null)
			return true;
		if (EatTick > 0)
			EatTick--;
		if (worldObj.difficultySetting > 0
				&& worldObj.getClosestPlayerToEntity(this, 24D) != null) {
			// mod_Fossil.ShowMessage(new
			// StringBuilder().append(Hunger).toString());
			HungerTick--;

			if (HungerTick <= 0) {
				Hunger--;
				HungerTick = HungerTickLimit;
				HandleStarvation();
				return true;
			}
			HandleStarvation();
			return false;
		} else
			return true;
	}*/

	@Override
	public boolean HandleEating(int FoodValue) {
		if (this.getHunger() >= getHungerLimit()) {
			return false;
		}
		this.increaseHunger(FoodValue);
		showHeartsOrSmokeFX(false);
		if (this.getHunger() >= getHungerLimit())
			this.setHunger(getHungerLimit());
		return true;
	}

	/*@Override
	protected void HandleStarvation() {
		if (Hunger <= 0) {
			if (!isTamed()) {
				attackEntityFrom(DamageSource.starve, 20);
				return;
			} else {
				SendStatusMessage(EnumSituation.StarveEsc);
				Hunger = HungerLimit / 2;
				this.setTamed(false);
				// if (worldObj.getPlayerEntityByName(getOwnerName())!=null)
				// Flee(worldObj.getPlayerEntityByName(getOwnerName()),4);
				return;
			}
		}
		if (Hunger == HungerLimit / 2 && HungerTick == HungerTickLimit / 10) {
			if (isTamed())
				SendStatusMessage(EnumSituation.Hungry);
			return;
		}
	}*/

	/*
	 * public void Flee(Entity EscapeFrom,int Mutplier){
	 * mod_Fossil.DebugMessage(new
	 * StringBuilder().append(this.toString()).append
	 * (":Fleeing from ").append(EscapeFrom.toString()).toString()); int
	 * TargetX=(int)Math.round(posX-(EscapeFrom.posX-posX)*Mutplier); int
	 * TargetZ=(int)Math.round(posZ-(EscapeFrom.posZ-posZ)*Mutplier); int
	 * TargetY=0; for(int i=128;i>0;i--){ if (!worldObj.isAirBlock(TargetX,
	 * i,TargetZ)){ TargetY=i; break; } } //setSelfTamed(false);
	 * setSelfSitting(false);
	 * setPathToEntity(worldObj.getEntityPathToXYZ((Entity
	 * )this,TargetX,TargetY,TargetZ
	 * ,(float)(10*Mutplier),true,false,true,false)); }
	 */

	/*
	 * public boolean HuntForPrey(double range){ if (this.PreyChecked) return
	 * false; if (this.FleeingTick>0){ FleeingTick--; return true; } if
	 * (this.getEntityToAttack()==null){ EntityLiving targetPrey=null;
	 * EntityLiving TempPrey=null; float NearestDistance=(float)range; List list
	 * = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class,
	 * AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX + 1.0D, posY
	 * + 1.0D, posZ + 1.0D).expand(range, 4D, range)); Iterator iterator =
	 * list.iterator(); do{ if(!iterator.hasNext()) { break; } TempPrey =
	 * (EntityLiving)iterator.next(); if (!(TempPrey instanceof
	 * EntityRaptor)&&!((TempPrey instanceof EntityPlayer)&&(this.isTamed()))){
	 * if (GetDistanceWithEntity((Entity)TempPrey)<NearestDistance){
	 * NearestDistance=GetDistanceWithEntity((Entity)TempPrey);
	 * targetPrey=TempPrey; } } } while(true); this.PreyChecked=true; if
	 * (targetPrey!=null){ mod_Fossil.DebugMessage(new
	 * StringBuilder().append("Nearest Target:"
	 * ).append(targetPrey.toString()).toString()); return true; }else return
	 * false; }else { this.PreyChecked=true; return false; } }
	 */

	/*
	 * private TileEntity GetNearestTileEntity(int targetType,int range){ final
	 * int Chest=0; final int Furance=1; final int Feeder=2; TileEntity
	 * result=null; TileEntity tmp=null; if (range>6) range=6; float
	 * Distance=range+1; for (int
	 * i=(int)Math.round(posX)-range;i<=(int)Math.round(posX)+range;i++){ for
	 * (int
	 * j=(int)Math.round(posY)-range/2;j<=(int)Math.round(posY)+range/2;j++){
	 * for (int
	 * k=(int)Math.round(posZ)-range;k<=(int)Math.round(posZ)+range;k++){
	 * //mod_Fossil.ShowMessage(new
	 * StringBuilder().append(worldObj.getBlockTileEntity(i,j,k)).toString());
	 * //mod_Fossil.ShowMessage(new
	 * StringBuilder().append(i).append(",").append(
	 * j).append(",").append(k).toString()); if (j<0) continue;
	 * tmp=worldObj.getBlockTileEntity(i,j,k); if (tmp!=null){ if(((tmp
	 * instanceof TileEntityChest)&&(targetType==Chest))|| ((tmp instanceof
	 * TileEntityFurnace)&&(targetType==Furance))|| ((tmp instanceof
	 * TileEntityFeeder
	 * )&&(targetType==Feeder)&&(!((TileEntityFeeder)tmp).CheckIsEmpty(this)))){
	 * float TempDis=GetDistanceWithTileEntity(tmp);
	 * //mod_Fossil.ShowMessage(new StringBuilder().append(TempDis).toString());
	 * if (TempDis<Distance) { Distance=TempDis; result=tmp; } } } } } } return
	 * result; }
	 */
	/*
	 * private void OpenDoor(){ if (this.DoorOpeningTick>0){
	 * this.DoorOpeningTick--; return; } int TmpX=(int)Math.round(posX); int
	 * TmpZ=(int)Math.round(posZ); int TmpY=(int)Math.round(posY); int MayDoor;
	 * for (int i=-1;i<=1;i++){ for (int j=-1;j<=1;j++){
	 * MayDoor=worldObj.getBlockId(i+TmpX, TmpY, j+TmpZ); if
	 * (MayDoor==Block.doorWood.blockID){ int l1 =
	 * worldObj.getBlockMetadata(i+TmpX, TmpY, j+TmpZ); if
	 * (!BlockDoor.isOpen(l1)) {
	 * Block.blocksList[MayDoor].blockActivated(worldObj, i+TmpX, TmpY, j+TmpZ,
	 * null); this.DoorOpeningTick=300; return; } } } } }
	 */
	/*
	 * private boolean FindChest(int range){ TileEntityChest target=null; if
	 * (GetNearestTileEntity(0,range)!=null){
	 * target=(TileEntityChest)GetNearestTileEntity(0,range); if
	 * (GetDistanceWithTileEntity((TileEntity)target)>1){
	 * //mod_Fossil.ShowMessage(new
	 * StringBuilder().append("Setting Path:").append
	 * (target.xCoord).append(",").
	 * append(target.yCoord).append(",").append(target.zCoord).toString());
	 * setPathToEntity
	 * (worldObj.getEntityPathToXYZ((Entity)this,(int)Math.round(target
	 * .xCoord),(
	 * int)Math.round(target.yCoord),(int)Math.round(target.zCoord),(float
	 * )range,true,false,true,false));
	 * 
	 * } return true; }else return false; }
	 */
	/*
	 * private boolean FindFeeder(int range){ if(range>6) range=6;
	 * TileEntityFeeder target=null; if (GetNearestTileEntity(2,range)!=null){
	 * target=(TileEntityFeeder)GetNearestTileEntity(2,range); if
	 * (GetDistanceWithTileEntity((TileEntity)target)>3){
	 * //mod_Fossil.ShowMessage(new
	 * StringBuilder().append("Setting Path:").append
	 * (target.xCoord).append(",").
	 * append(target.yCoord).append(",").append(target.zCoord).toString());
	 * setPathToEntity
	 * (worldObj.getEntityPathToXYZ((Entity)this,(int)Math.round(target
	 * .xCoord),(
	 * int)Math.round(target.yCoord),(int)Math.round(target.zCoord),(float
	 * )range,true,false,true,false));
	 * 
	 * }else{ if (target.CheckIsEmpty(this))return false;
	 * //FaceToCoord(target.xCoord,target.yCoord,target.zCoord);
	 * target.Feed(this); showHeartsOrSmokeFX(false); } return true; }else
	 * return false; }
	 */
	/*
	 * private void HangleChestLearning(){ //mod_Fossil.ShowMessage(new
	 * StringBuilder().append(LearningChestTick).toString()); if
	 * (!isLeartChest()){ float
	 * Dis=GetDistanceWithTileEntity(GetNearestTileEntity(0,16)); if (Dis<=5 &&
	 * Dis>0) { //mod_Fossil.ShowMessage("Start Learning.");
	 * LearningChestTick--; if (LearningChestTick==0){
	 * SendStatusMessage(EnumSituation.LearingChest,SelfType); } }
	 * 
	 * } }
	 */
	/*
	 * private boolean TakeItemFromChest(TileEntityChest TargetChest,int
	 * Itemid){ //TargetChest.openChest();
	 * worldObj.sendClientEvent(TargetChest.xCoord,TargetChest.yCoord
	 * ,TargetChest.zCoord , 1, TargetChest.numUsingPlayers+1); for (int
	 * i=0;i<TargetChest.getSizeInventory();i++){ if
	 * (TargetChest.getStackInSlot(i)!=null){ ItemStack
	 * target=TargetChest.getStackInSlot(i); if (target.itemID==Itemid){
	 * target.stackSize--; return true; } } } return false; }
	 */
	public boolean isLeartChest() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 16) == 0;
	}

	public void ChangeSubType(int type) {
		if (type <= 2 && type >= 0) {
			this.setSubSpecies(type);
			this.CheckSkin();
		}
	}

	@Override
	public void ShowPedia(EntityPlayer checker) {
		//if (worldObj.isRemote) return;
		PediaTextCorrection(SelfType,checker);
		if (this.isTamed()) {

				mod_Fossil.ShowMessage(new StringBuilder().append(OwnerText)
						.append(this.getOwnerName()).toString(),checker);
				mod_Fossil.ShowMessage(new StringBuilder().append(AgeText)
						.append(this.getDinoAge()).toString(),checker);
				mod_Fossil.ShowMessage(new StringBuilder().append(HelthText)
						.append(this.health).append("/").append(20).toString(),checker);
				mod_Fossil.ShowMessage(new StringBuilder().append(HungerText)
						.append(this.getHunger()).append("/")
						.append(this.getHungerLimit()).toString(),checker);

			if (this.isLeartChest()) {
				mod_Fossil.ShowMessage(EnableChestText,checker);
			}
		} else {

			mod_Fossil.ShowMessage(UntamedText,checker);
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
			if (this.isLeartChest())
				resultList.add(EnableChestText);
			if (!resultList.isEmpty()) {
				result=new String[1];
				result=resultList.toArray(result);
			}
		}
		return result;
	}
	/*
	 * public void HandleBreed(){ if (this.age>3){ this.BreedTick--; if
	 * (this.BreedTick==0){ int GroupAmount=0; List list =
	 * worldObj.getEntitiesWithinAABBExcludingEntity(this,
	 * boundingBox.expand(32D, 32D, 32D)); for (int i=0;i<list.size();i++){ if
	 * (list.get(i) instanceof EntityRaptor) GroupAmount++; } if
	 * (GroupAmount>50) GroupAmount=50; if (GroupAmount>80) return; if (new
	 * Random().nextInt(100)<GroupAmount){ EntityDinoEgg entityegg=null; if
	 * (this.isTamed()) entityegg = (EntityDinoEgg)new
	 * EntityDinoEgg(worldObj,SelfType,(IDino)this); else entityegg =
	 * (EntityDinoEgg)new EntityDinoEgg(worldObj,SelfType);
	 * entityegg.setLocationAndAngles(this.posX+(new Random().nextInt(3)-1),
	 * this.posY, this.posZ+(new Random().nextInt(3)-1),
	 * worldObj.rand.nextFloat() * 360F, 0.0F); if(
	 * worldObj.checkIfAABBIsClear(entityegg.boundingBox) &&
	 * worldObj.getCollidingBoundingBoxes(entityegg,
	 * entityegg.boundingBox).size() == 0)
	 * worldObj.spawnEntityInWorld(entityegg); showHeartsOrSmokeFX(true); }
	 * this.BreedTick=3000; } } }
	 */
	public void SetOrder(EnumOrderType input) {
		this.OrderStatus = input;
	}

	public boolean HandleEating(int FoodValue, boolean FernFlag) {
		return this.HandleEating(FoodValue);
	}

	public boolean CheckEatable(int itemIndex) {
		if (!(Item.itemsList[itemIndex] instanceof ItemFood))
			return false;
		Item tmp = Item.itemsList[itemIndex];
		boolean result = false;
		result = (tmp == Item.beefCooked) || (tmp == Item.beefRaw)
				|| (tmp == Item.fishCooked) || (tmp == Item.fishRaw)
				|| (tmp == Item.chickenCooked) || (tmp == Item.chickenRaw)
				|| (tmp == Item.porkRaw) || (tmp == Item.porkCooked);
		return result;
	}

	public EntityAnimal spawnBabyAnimal(EntityAnimal entityanimal) {
		return new EntityRaptor(worldObj);
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	public boolean IsIdle() {
		return (this.motionX < 0.03125F) && (this.motionY < 0.03125F)
				&& (this.motionZ < 0.03125F);
	}

	protected void updateSize(boolean shouldAddAge) {

	}

	public EnumOrderType getOrderType() {
		return this.OrderStatus;
	}

	@Override
	protected int foodValue(Item asked) {

		return 0;
	}

	@Override
	public void HandleEating(Item food) {

	}

	@Override
	public void HoldItem(Item itemGot) {

	}

	@Override
	public void setLearntChest(boolean var) {
		byte var2 = this.dataWatcher
				.getWatchableObjectByte(this.CHEST_LEARNING_PROGRESS_DATA_INDEX);

		if (var) {
			this.dataWatcher.updateObject(
					this.CHEST_LEARNING_PROGRESS_DATA_INDEX,
					Byte.valueOf((byte) (var2 & -17)));
		} else {
			this.dataWatcher.updateObject(
					this.CHEST_LEARNING_PROGRESS_DATA_INDEX,
					Byte.valueOf((byte) (var2 | 16)));
		}

	}

	@Override
	public float getGLX() {
		return (float) (0.2F+0.1*this.getDinoAge());
	}
	@Override
	public float getGLY() {
		return (float)(0.32F+0.1*this.getDinoAge());
	}

}