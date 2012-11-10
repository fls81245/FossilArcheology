
package net.minecraft.src;

import java.util.List;
import java.util.Random;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

// Referenced classes of package net.minecraft.src:
//            Entity, World, Block, Item, 
//            AxisAlignedBB, Material, MathHelper, EntityPlayer, 
//            DamageSource, NBTTagCompound
public class EntityDinoEgg extends Entity implements IEntityAdditionalSpawnData{
	private static final String HEAD="Dinoegg.";
	private static final String MSGHEAD=HEAD+"msghead";
	private static final String MSGTAIL=".msgtail";
	private static final String COLD="cold";
	private static final String DRY="dry";
	private static final String PEDIA="PediaText.egg.";
	public EntityDinoEgg(World world, EnumDinoType Inside) {
		super(world);
		damageTaken = 0;
		timeSinceHit = 0;
		preventEntitySpawning = true;
		setSize(0.5F, 1.5F);
		yOffset = height;
		DinoInside = Inside;
	}

	public String getTexture() {
		int IntType = this.DinoInside.ordinal();
		if (IntType<4) return new StringBuilder().append("/skull/eggTexture").append(IntType + 1).append(".png").toString();
		else return new StringBuilder().append("/skull/eggTexture").append(IntType).append(".png").toString();
		//return "/skull/dinoegg.png";
	}

	public EntityDinoEgg(World world) {
		this(world, null);
	}

	public EntityDinoEgg(World world, EnumDinoType Inside, EntityDinosaurce parent) {
		this(world, Inside);
		this.ParentOwner = parent.getOwnerName();
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	protected void entityInit() {
	}

	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.boundingBox;
	}

	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	public boolean canBePushed() {
		return true;
	}

	public EntityDinoEgg(World world, double d, double d1, double d2, EnumDinoType Inside) {
		this(world, Inside);
		setPosition(d, d1 + (double) yOffset, d2);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = d;
		prevPosY = d1;
		prevPosZ = d2;
	}

	public double getMountedYOffset() {
		return (double) height * 0.0D - 0.30000001192092896D;
	}

	public boolean attackEntityFrom(DamageSource damagesource, int i) {
		if (worldObj.isRemote || isDead) {
			return true;
		}
		timeSinceHit = 10;
		damageTaken += i * 10;
		setBeenAttacked();
		if (damageTaken > 40) {
			if (riddenByEntity != null) {
				riddenByEntity.mountEntity(this);
			}

			/*for(int j = 0; j < 3; j++)
			{
			dropItemWithOffset(Block.planks.blockID, 1, 0.0F);
			}
			
			for(int k = 0; k < 2; k++)
			{
			dropItemWithOffset(Item.stick.shiftedIndex, 1, 0.0F);
			}*/

			setDead();
		}
		return true;
	}

	public void performHurtAnimation() {
		timeSinceHit = 10;
		damageTaken += damageTaken * 10;
	}

	public boolean canBeCollidedWith() {
		return !isDead;
	}

	public void onUpdate() {
		super.onUpdate();
		HandleHatching();
		super.onUpdate();
		if (timeSinceHit > 0) {
			timeSinceHit--;
		}
		if (damageTaken > 0) {
			damageTaken--;
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		int i = 5;
		double d = 0.0D;
		for (int j = 0; j < i; j++) {
			double d5 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double) (j + 0)) / (double) i) - 0.125D;
			double d9 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double) (j + 1)) / (double) i) - 0.125D;
			AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(boundingBox.minX, d5, boundingBox.minZ, boundingBox.maxX, d9, boundingBox.maxZ);
		}
		if (d < 1.0D) {
			double d3 = d * 2D - 1.0D;
			motionY += 0.039999999105930328D * d3;
		} else {
			if (motionY < 0.0D) {
				motionY /= 2D;
			}
			motionY += 0.0070000002160668373D;
		}
		if (riddenByEntity != null) {
			motionX += riddenByEntity.motionX * 0.20000000000000001D;
			motionZ += riddenByEntity.motionZ * 0.20000000000000001D;
		}
		double d4 = 0.40000000000000002D;
		if (motionX < -d4) {
			motionX = -d4;
		}
		if (motionX > d4) {
			motionX = d4;
		}
		if (motionZ < -d4) {
			motionZ = -d4;
		}
		if (motionZ > d4) {
			motionZ = d4;
		}
		if (onGround) {
			motionX *= 0.5D;
			motionY *= 0.5D;
			motionZ *= 0.5D;
		}
		moveEntity(motionX, motionY, motionZ);
		double d8 = Math.sqrt(motionX * motionX + motionZ * motionZ);
		if (d8 > 0.14999999999999999D) {
			double d12 = Math.cos(((double) rotationYaw * 3.1415926535897931D) / 180D);
			double d15 = Math.sin(((double) rotationYaw * 3.1415926535897931D) / 180D);
			for (int i1 = 0; (double) i1 < 1.0D + d8 * 60D; i1++) {
				double d18 = rand.nextFloat() * 2.0F - 1.0F;
				double d20 = (double) (rand.nextInt(2) * 2 - 1) * 0.69999999999999996D;
				if (rand.nextBoolean()) {
					double d21 = (posX - d12 * d18 * 0.80000000000000004D) + d15 * d20;
					double d23 = posZ - d15 * d18 * 0.80000000000000004D - d12 * d20;
					//worldObj.spawnParticle("splash", d21, posY - 0.125D, d23, motionX, motionY, motionZ);
				} else {
					double d22 = posX + d12 + d15 * d18 * 0.69999999999999996D;
					double d24 = (posZ + d15) - d12 * d18 * 0.69999999999999996D;
					//worldObj.spawnParticle("splash", d22, posY - 0.125D, d24, motionX, motionY, motionZ);
				}
			}

		}
		if (isCollidedHorizontally && d8 > 0.14999999999999999D) {
			if (!worldObj.isRemote) {
				setDead();

			}
		} else {
			motionX *= 0.99000000953674316D;
			motionY *= 0.94999998807907104D;
			motionZ *= 0.99000000953674316D;
		}
		rotationPitch = 0.0F;
		double d13 = rotationYaw;
		double d16 = prevPosX - posX;
		double d17 = prevPosZ - posZ;
		if (d16 * d16 + d17 * d17 > 0.001D) {
			d13 = (float) ((Math.atan2(d17, d16) * 180D) / 3.1415926535897931D);
		}
		double d19;
		for (d19 = d13 - (double) rotationYaw; d19 >= 180D; d19 -= 360D) {
		}
		for (; d19 < -180D; d19 += 360D) {
		}
		if (d19 > 20D) {
			d19 = 20D;
		}
		if (d19 < -20D) {
			d19 = -20D;
		}
		rotationYaw += d19;
		setRotation(rotationYaw, rotationPitch);
		//List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.0D, 0.0D, 0.0D));
		if (list != null && list.size() > 0) {
			for (int j1 = 0; j1 < list.size(); j1++) {
				Entity entity = (Entity) list.get(j1);
				if (entity != riddenByEntity && entity.canBePushed() && (entity instanceof EntityBoat)) {
					entity.applyEntityCollision(this);
				}
			}

		}
		for (int k1 = 0; k1 < 4; k1++) {
			int l1 = MathHelper.floor_double(posX + ((double) (k1 % 2) - 0.5D) * 0.80000000000000004D);
			int i2 = MathHelper.floor_double(posY);
			int j2 = MathHelper.floor_double(posZ + ((double) (k1 / 2) - 0.5D) * 0.80000000000000004D);
			if (worldObj.getBlockId(l1, i2, j2) == Block.snow.blockID) {
				worldObj.setBlockWithNotify(l1, i2, j2, 0);
			}
		}

		if (riddenByEntity != null && riddenByEntity.isDead) {
			riddenByEntity = null;
		}
	}

	public void updateRiderPosition() {
		if (riddenByEntity == null) {
			return;
		} else {
			double d = Math.cos(((double) rotationYaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D;
			double d1 = Math.sin(((double) rotationYaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D;
			riddenByEntity.setPosition(posX + d, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ + d1);
			return;
		}
	}

	private void HandleHatching() {
		String MsgHeadTmp;
		float light = getBrightness(1.0F);
		BiomeGenBase LocationBiome;
		EntityPlayer PlayerNearby=null;
		if (this.ParentOwner != "" && worldObj.getPlayerEntityByName(this.ParentOwner) != null) {
			//PlayerNearby = ParentOwner;
		} else if (worldObj.getClosestPlayerToEntity(this, 16D) != null) {
			PlayerNearby = worldObj.getClosestPlayerToEntity(this, 16D);
		}
		if (this.DinoInside == EnumDinoType.Mosasaurus) {
			if (this.inWater) {
				this.BirthTick++;
			} else {
				this.BirthTick--;
			}
		} else {
			if (light >= 0.5) {
				this.BirthTick++;
			} else {
				if (!worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))) {
					this.BirthTick--;
				}
			}
		}
		if (this.BirthTick <= -HatchingNeedTime) {
			String TailTmp;
			if (this.DinoInside == EnumDinoType.Mosasaurus) {
				TailTmp=mod_Fossil.GetLangTextByKey(HEAD+DRY+MSGTAIL);
			} else {
				TailTmp=mod_Fossil.GetLangTextByKey(HEAD+COLD+MSGTAIL);
			}
			MsgHeadTmp=mod_Fossil.GetLangTextByKey(MSGHEAD);
			mod_Fossil.ShowMessage(MsgHeadTmp+EntityDinosaurce.GetNameByEnum(DinoInside,false)+TailTmp,PlayerNearby);				

			this.setDead();
			return;
		}
		if (this.BirthTick >= HatchingNeedTime) {
			if (worldObj.isRemote) return;
			LocationBiome=worldObj.provider.worldChunkMgr.getBiomeGenAt((int)Math.floor(posX), (int)Math.floor(posZ));

			EntityLiving entityliving = null;
			switch (DinoInside) {
				case Triceratops:
					entityliving = (EntityAnimal) new EntityTriceratops(worldObj);
					((EntityTriceratops)entityliving).setSubSpecies(worldObj.rand.nextInt(3)+1);
					if (PlayerNearby != null) {
						((EntityDinosaurce) entityliving).setOwner(PlayerNearby.username);
						((EntityDinosaurce) entityliving).setTamed(true);
					}else ((EntityDinosaurce) entityliving).OrderStatus=EnumOrderType.FreeMove;
					break;
				case Raptor:
					entityliving = (EntityAnimal) new EntityRaptor(worldObj);
					if (LocationBiome instanceof BiomeGenForest) {
						((EntityRaptor) entityliving).ChangeSubType(2);
					}
					if (LocationBiome instanceof BiomeGenSnow||LocationBiome instanceof BiomeGenTaiga) {
						((EntityRaptor) entityliving).ChangeSubType(1);
					}
					if (PlayerNearby != null) {
						((EntityDinosaurce) entityliving).setOwner(PlayerNearby.username);
						((EntityDinosaurce) entityliving).setTamed(true);
					}else ((EntityDinosaurce) entityliving).OrderStatus=EnumOrderType.FreeMove;
					break;
				case TRex:
					entityliving = (EntityAnimal) new EntityTRex(worldObj);
					((EntityDinosaurce) entityliving).OrderStatus=EnumOrderType.FreeMove;
					//((EntityTRex)entityliving).setSelfOwner(entityplayer.username);
					//((EntityTRex)entityliving).setSelfTamed(true);
					break;
				case Pterosaur:
					//mod_Fossil.ShowMessage("Pterosaur not ready yet.");
					entityliving = (EntityAnimal) new EntityPterosaur(worldObj);
					if (PlayerNearby != null) {
						((EntityDinosaurce) entityliving).setOwner(PlayerNearby.username);
						((EntityDinosaurce) entityliving).setTamed(true);
					}else ((EntityDinosaurce) entityliving).OrderStatus=EnumOrderType.FreeMove;
					break;
				case Plesiosaur:
					entityliving = (EntityAnimal) new EntityPlesiosaur(worldObj);
					if (PlayerNearby != null) {
						((EntityDinosaurce) entityliving).setOwner(PlayerNearby.username);
						((EntityDinosaurce) entityliving).setTamed(true);
					}
					break;
				case Mosasaurus:
					entityliving = (EntityAnimal) new EntityMosasaurus(worldObj);
					((EntityDinosaurce) entityliving).OrderStatus=EnumOrderType.FreeMove;
					break;
				case Stegosaurus:
					entityliving=(EntityAnimal) new EntityStegosaurus(worldObj);
					if (PlayerNearby != null) {
						((EntityDinosaurce) entityliving).setOwner(PlayerNearby.username);
						((EntityDinosaurce) entityliving).setTamed(true);
					}else ((EntityDinosaurce) entityliving).OrderStatus=EnumOrderType.FreeMove;
					break;
				case dilphosaur:
					entityliving = (EntityAnimal) new Entitydil(worldObj);
					((EntityDinosaurce) entityliving).OrderStatus=EnumOrderType.FreeMove;
					break;
				case Brachiosaurus:
					entityliving=(EntityAnimal) new EntityBrachiosaurus(worldObj);
					if (PlayerNearby != null) {
						((EntityDinosaurce) entityliving).setOwner(PlayerNearby.username);
						((EntityDinosaurce) entityliving).setTamed(true);
					}else ((EntityDinosaurce) entityliving).OrderStatus=EnumOrderType.FreeMove;
					break;
				default:
					mod_Fossil.ShowMessage("Bug:Impossible result.",PlayerNearby);
					this.setDead();
					return;
			}
			entityliving.setLocationAndAngles((int) Math.floor(this.posX), (int) Math.floor(this.posY) + 1, (int) Math.floor(this.posZ), worldObj.rand.nextFloat() * 360F, 0.0F);
			if (worldObj.checkIfAABBIsClear(entityliving.boundingBox) && worldObj.getCollidingBoundingBoxes(entityliving, entityliving.boundingBox).size() == 0 && (!worldObj.isAnyLiquid(entityliving.boundingBox) ^ (DinoInside == EnumDinoType.Mosasaurus))) {
				if (!worldObj.isRemote)worldObj.spawnEntityInWorld(entityliving);
				if (PlayerNearby != null) {
					mod_Fossil.ShowMessage(mod_Fossil.GetLangTextByKey(HEAD+"Hatched"),PlayerNearby);
				}
				this.setDead();
			} else {
				mod_Fossil.ShowMessage(mod_Fossil.GetLangTextByKey(HEAD+"NoSpace"),PlayerNearby);
				this.BirthTick -= 500;
			}
		}
	}

	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		//super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("BirthTick", this.BirthTick);
		nbttagcompound.setInteger("DinoType", this.EnumToInt(DinoInside));
		nbttagcompound.setString("ParentOwner", this.ParentOwner);
	}

	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		//super.readFromNBT(nbttagcompound);
		EnumDinoType[] DinoChart = EnumDinoType.values();
		this.BirthTick = nbttagcompound.getInteger("BirthTick");
		this.DinoInside = DinoChart[nbttagcompound.getInteger("DinoType")];
		this.ParentOwner = nbttagcompound.getString("ParentOwner");
	}

	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack == null) {
			ItemStack shell = new ItemStack(mod_Fossil.Ancientegg, 1, EnumToInt(this.DinoInside));
			if (entityplayer.inventory.addItemStackToInventory(shell)) {
				ModLoader.onItemPickup(entityplayer, shell);
				worldObj.playSoundAtEntity(entityplayer, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				this.setDead();
			} else {
				return false;
			}
			return false;
		}
		if (FMLCommonHandler.instance().getSide().isClient()){
			if (itemstack.getItem().shiftedIndex == mod_Fossil.DinoPedia.shiftedIndex) {
				this.showpedia(entityplayer);
				return true;
			}
		}
		return false;
	}

	private int EnumToInt(EnumDinoType input) {
		return this.DinoInside.ordinal();
	}
	private void showpedia(EntityPlayer checker) {
		//if (worldObj.isRemote) return;
		String TempStatus = "";
		String self=mod_Fossil.GetLangTextByKey(PEDIA+"selfHead")+EntityDinosaurce.GetNameByEnum(DinoInside,false)+mod_Fossil.GetLangTextByKey(PEDIA+"selfTail");
		int Progress = (int) Math.floor(((float)this.BirthTick / HatchingNeedTime) * 100);
		/*if (Progress < 0) {
			Progress = 0;
		}*/
		mod_Fossil.ShowMessage(self,checker);
		if (this.DinoInside == EnumDinoType.Mosasaurus) {
			if (this.BirthTick >= 0) {
				TempStatus = mod_Fossil.GetLangTextByKey(PEDIA+"wet");
			} else {
				TempStatus = mod_Fossil.GetLangTextByKey(PEDIA+"dry");
			}
		} else {
			if (this.BirthTick >= 0) {
				TempStatus = mod_Fossil.GetLangTextByKey(PEDIA+"warm");
			} else {
				TempStatus = mod_Fossil.GetLangTextByKey(PEDIA+"cold");
			}
		}
		String StatusText = mod_Fossil.GetLangTextByKey(PEDIA+"Status");
		String HatchingText = mod_Fossil.GetLangTextByKey(PEDIA+"Progress");
		mod_Fossil.ShowMessage(new StringBuilder().append(StatusText).append(TempStatus).toString(),checker);			
		mod_Fossil.ShowMessage(new StringBuilder().append(HatchingText).append(Progress).append("/100").toString(),checker);


	}
	public int damageTaken;
	public int timeSinceHit;
	public EnumDinoType DinoInside;
	public int BirthTick = 0;
	public String ParentOwner = "";
	public final int HatchingNeedTime=3000;//Normal:3000
	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {

		data.writeInt(this.BirthTick);
		data.writeInt(this.EnumToInt(DinoInside));
		//data.writeChars(this.ParentOwner);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {

		this.BirthTick=data.readInt();
		this.DinoInside=EnumDinoType.values()[data.readInt()];
		/*String tmp=data.readLine();
		if (tmp==null ||tmp.isEmpty()){
			this.ParentOwner="";
			return;
		}
		StringBuilder dataResult=new StringBuilder();
		char readTmp;
		for (int i=0;i<tmp.length();i++){
			readTmp=tmp.charAt(i);
			if (readTmp!=0x20) dataResult.append(readTmp);
		}
		this.ParentOwner=dataResult.toString();*/
	}
}
