package net.minecraft.src;

import java.util.List;
import java.util.Random;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public abstract class EntityDinosaurce extends EntityTameable implements IEntityAdditionalSpawnData{
	


	public static final int HUNGER_TICK_DATA_INDEX=18;
	public static final int HUNGER_DATA_INDEX=19;
	public static final int AGE_TICK_DATA_INDEX=20;
	public static final int AGE_DATA_INDEX=21;
	public static final int SUBSPECIES_INDEX=22;
	public static final int MODELIZED_INDEX=23;
	public static final int GROW_TIME_COUNT = 12000;
	
	public int attackStrength=0;
	public static String SelfName = "";
	public static String OwnerText = "Owner:";
	public static String UntamedText = "Untamed";
	public static String EnableChestText = " * Has learnt about chests.";
	public static String AgeText = "Age:";
	public static String HelthText = "Health:";
	public static String HungerText = "Hunger:";
	public static String CautionText = "---Caution:Dangerous---";
	public static String RidiableText = " * Ride-able.";
	public static String WeakText = "Dying";
	public static String FlyText = " * Able to fly";
	public EnumDinoType SelfType = null;
	public final int BREED_LIMIT=3000;
	public int BreedTick=BREED_LIMIT;
	public static EntityDinosaurce pediaingDino=null;
	protected DinoAIControlledByPlayer ridingHandler;
	public DinoAIControlledByPlayer getRidingHandler() {
		return ridingHandler;
	}

	public boolean isModelized() {
		return this.dataWatcher.getWatchableObjectByte(MODELIZED_INDEX)>=0;
	}

	public void setModelized(boolean modelized) {
		if (!this.SelfType.isModelable()) return;
		this.dataWatcher.updateObject(MODELIZED_INDEX, (byte)(modelized?0:-1));
		if (modelized)this.texture=getModelTexture();
	}
	public int getHungerLimit(){
		return 100;
	}
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(this.AGE_DATA_INDEX, new Integer(0));
        this.dataWatcher.addObject(this.AGE_TICK_DATA_INDEX, new Integer(0));
        this.dataWatcher.addObject(this.HUNGER_DATA_INDEX, new Integer(100));
        this.dataWatcher.addObject(this.HUNGER_TICK_DATA_INDEX, new Integer(HungerTickLimit));
        this.dataWatcher.addObject(this.SUBSPECIES_INDEX, new Integer(1));
        this.dataWatcher.addObject(this.MODELIZED_INDEX, new Byte((byte) -1));

    }
    public int getSubSpecies(){
    	return this.dataWatcher.getWatchableObjectInt(SUBSPECIES_INDEX);
    }
    public void setSubSpecies(int value){
    	this.dataWatcher.updateObject(this.SUBSPECIES_INDEX, Integer.valueOf(value));
    }
	public int getDinoAge() {
		return this.dataWatcher.getWatchableObjectInt(this.AGE_DATA_INDEX);
	}
	public int getDinoAgeTick() {
		return this.dataWatcher.getWatchableObjectInt(this.AGE_TICK_DATA_INDEX);
	}
	public int getHunger() {
		return this.dataWatcher.getWatchableObjectInt(this.HUNGER_DATA_INDEX);
	}
	public int getHungerTick() {
		return this.dataWatcher.getWatchableObjectInt(this.HUNGER_TICK_DATA_INDEX);
	}
	public void increaseHunger(int value){
		this.setHunger(this.getHunger()+value);
	}
	public void increaseHungerTick(){
		this.setHungerTick(this.getHungerTick()+1);
	}
	public void setDinoAge(int value) {
		this.dataWatcher.updateObject(this.AGE_DATA_INDEX,Integer.valueOf(value));
	}
	public void increaseDinoAge(){
		this.setDinoAge(this.getDinoAge()+1);
	}
	public void increaseDinoAgeTick(){
		this.setDinoAgeTick(this.getDinoAgeTick()+1);
	}
	public void decreaseDinoAge(){
		if (this.getDinoAge()>0) this.setDinoAge(this.getDinoAge()-1);
	}
	public void decreaseHunger(){
		if(this.getHunger()>0) this.increaseHunger(-1);
	}
	public void decreaseHungerTick(){
		if (this.getHungerTick()>0) this.setHungerTick(this.getHungerTick()-1);
	}
	public void setDinoAgeTick(int value) {
		this.dataWatcher.updateObject(AGE_TICK_DATA_INDEX,Integer.valueOf(value));
	}
	public void setHunger(int value) {
		this.dataWatcher.updateObject(HUNGER_DATA_INDEX,Integer.valueOf(value));
	}
	public void setHungerTick(int value) {
		this.dataWatcher.updateObject(HUNGER_TICK_DATA_INDEX,Integer.valueOf(value));
	}
	protected String getModelTexture(){
		return "/skull/DinoModel"+this.SelfType.toString()+".png";
	}
	public String getTexture(){
		if (this.isModelized()) return getModelTexture();
		else return this.texture;
	}
    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval()
    {
        return 360;
    }
    @SideOnly(Side.CLIENT)
	public abstract void ShowPedia(EntityPlayer checker);

	//protected abstract void HandleStarvation();

	public abstract boolean HandleEating(int FoodValue);

	//protected abstract boolean HandleHunger();

	public static void UpdateGlobleText(){
		
	}
    public boolean isAIEnabled()
    {
        return false;
    }
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        int var2 = this.attackStrength;

        if (this.isPotionActive(Potion.damageBoost))
        {
            var2 += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }

        if (this.isPotionActive(Potion.weakness))
        {
            var2 -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
        }

        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
    }
	public void HandleBreed(){
		if (this.getDinoAge()>4){
			this.BreedTick--;
			if (this.BreedTick==0){
				int GroupAmount=0;
				List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(32D, 32D, 32D));
				for (int i=0;i<list.size();i++){
					if (list.get(i) instanceof EntityDinosaurce) {
						EntityDinosaurce Tmp=(EntityDinosaurce)list.get(i);
						if (Tmp.SelfType==this.SelfType) GroupAmount++;
					}
				}
				if (GroupAmount>50) GroupAmount=50;
				if (GroupAmount>80) return;
				if (new Random().nextInt(100)<GroupAmount){
					EntityDinoEgg entityegg=null;
					entityegg = (EntityDinoEgg)new EntityDinoEgg(worldObj,this.SelfType);
					entityegg.setLocationAndAngles(this.posX+(new Random().nextInt(3)-1), this.posY, this.posZ+(new Random().nextInt(3)-1), worldObj.rand.nextFloat() * 360F, 0.0F);
					if(	worldObj.checkIfAABBIsClear(entityegg.boundingBox) && worldObj.getCollidingBoundingBoxes(entityegg, entityegg.boundingBox).size() == 0) worldObj.spawnEntityInWorld(entityegg);
					showHeartsOrSmokeFX(true);
				}
				this.BreedTick=BREED_LIMIT;
			}
		}
	}


	public boolean CheckSpace(){
		return !isEntityInsideOpaqueBlock();
	}
	protected void getPathOrWalkableBlock(Entity entity, float f)
    {
        PathEntity pathentity = worldObj.getPathEntityToEntity(this, entity, 16F,true,false,true,false);
       
            setPathToEntity(pathentity);
    }
	public void setSize(float x, float y) {
		// float Tmp=width;
		float Tmp = x;
		/*if (Tmp > 2.5F)
			Tmp = 2.5F;*/
		/*GLsizeX = x;
		GLsizeY = -y;
		GLsizeZ = x;*/

		super.setSize(Tmp, y);
	}
	public void SendOrderMessage(EnumOrderType Order) {
		String result = "";	
		final String ORDERHEAD="Order.";
		result = mod_Fossil.GetLangTextByKey(ORDERHEAD+"Head");
		result += mod_Fossil.GetLangTextByKey(ORDERHEAD+Order.toString());
		mod_Fossil.ShowMessage(result,(EntityPlayer) this.getOwner());
	}

	public void SendStatusMessage(EnumSituation ST) {
		SendStatusMessage(ST, SelfType);
	}

	public void SendStatusMessage(EnumSituation ST, EnumDinoType target) {
		if (this.worldObj.getClosestPlayerToEntity(this, mod_Fossil.MESSAGE_DISTANCE)==null)return;
		String DinoName = GetNameByEnum(target,true);
		String result = "";
		final String STATUS="Status.";
		final String HEAD="Head.";
		String MsgHead="";
		switch (ST){
			case Hungry:
			case Starve:
			case LearingChest:
			case Bytreate:
			case NoSpace:
			case StarveEsc:
				MsgHead=mod_Fossil.GetLangTextByKey(STATUS+HEAD+"SomeOf");
				break;
			case SJLBite:
			case GemErrorYoung:
				MsgHead=mod_Fossil.GetLangTextByKey(STATUS+HEAD+"This");
				DinoName = GetNameByEnum(target,false);
				break;
			case Nervous:
				MsgHead=mod_Fossil.GetLangTextByKey(STATUS+HEAD+"Nervous");
				DinoName = GetNameByEnum(target,false);
				break;
			case ChewTime:
			case Full:
			case GemErrorHealth:
				DinoName="";				
		}

			result=MsgHead + DinoName + mod_Fossil.GetLangTextByKey(STATUS+ST.toString());			


		mod_Fossil.ShowMessage(result,(EntityPlayer) this.getOwner());
	}
	public void showHeartsOrSmokeFX(boolean flag)
    {
        String s = "heart";
        if(!flag)
        {
            s = "smoke";
        }
        for(int i = 0; i < 7; i++)
        {
            double d = rand.nextGaussian() * 0.02D;
            double d1 = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            worldObj.spawnParticle(s, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
        }

    }
	public static String GetNameByEnum(EnumDinoType target,boolean plural) {
		final String DINO="Dino.";
		final String PLURAL=".Plural";
		String resultSingle=mod_Fossil.GetLangTextByKey(DINO+target.toString());
		String resultPul=mod_Fossil.GetLangTextByKey(DINO+target.toString()+PLURAL);
		if (resultPul.equals(" "))resultPul=resultSingle;
		if (plural) return resultPul;
		else return resultSingle;
	}

	public void PediaTextCorrection(EnumDinoType Caller,EntityPlayer checker) {
		SelfName = GetNameByEnum(Caller,false);
		final String PEDIA="PediaText.";
		mod_Fossil.ShowMessage(SelfName,checker);
		OwnerText = mod_Fossil.GetLangTextByKey(PEDIA+"owner");
		UntamedText = mod_Fossil.GetLangTextByKey(PEDIA+"Untamed");
		EnableChestText = mod_Fossil.GetLangTextByKey(PEDIA+"EnableChest");
		AgeText = mod_Fossil.GetLangTextByKey(PEDIA+"Age");
		HelthText = mod_Fossil.GetLangTextByKey(PEDIA+"Health");
		HungerText = mod_Fossil.GetLangTextByKey(PEDIA+"Hunger");
		CautionText = mod_Fossil.GetLangTextByKey(PEDIA+"Caution");
		RidiableText = mod_Fossil.GetLangTextByKey(PEDIA+"Ridiable");
		WeakText = mod_Fossil.GetLangTextByKey(PEDIA+"Weak");
		FlyText = mod_Fossil.GetLangTextByKey(PEDIA+"Fly");
		
	}



	// protected abstract int AgeLimit();
	/*public int HungerTick = 300;
	public int EatTick = 100;
	public int AgeTick = 0;
	public int age = 0;*/
	/*public float GLsizeX = -1.0F;
	public float GLsizeY = -1.0F;
	public float GLsizeZ = 1.0F;*/
	public final int HungerTickLimit = 300;
	public EnumOrderType OrderStatus = EnumOrderType.Follow;

	public EntityDinosaurce(World world) {
		super(world);
	}
	public float GetDistanceWithXYZ(double inputX,double inputY,double inputZ){
		return (float)(Math.sqrt(Math.pow((double)(posX-inputX),2)+Math.pow((double)(posY-inputY),2)+Math.pow((double)(posZ-inputZ),2)));
	}
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
	public float GetDistanceWithTileEntity(TileEntity target){
		if (target!=null) return (float)(Math.sqrt(Math.pow((double)(posX-target.xCoord),2)+Math.pow((double)(posY-target.yCoord),2)+Math.pow((double)(posZ-target.zCoord),2)));
		else return -1;
	}
	public float GetDistanceWithEntity(Entity target){
		return (float)(Math.sqrt(Math.pow((double)(posX-target.posX),2)+Math.pow((double)(posY-target.posY),2)+Math.pow((double)(posZ-target.posZ),2)));
	}

	protected int getDropItemId() {
		if (this.isModelized()) return Item.bone.shiftedIndex;
		return mod_Fossil.RawDinoMeat.shiftedIndex;
	}

	protected void dropFewItems(boolean flag, int unusedi) {
	    int i = getDropItemId();
	    int meta=(this.isModelized())?0:this.SelfType.ordinal();
	    if(i > 0)
	    {
	    	int j=this.getDinoAge();
	        for(int k = 0; k < j; k++)
	        {
	        	entityDropItem(new ItemStack(i, 1, meta),0.0f);
	        }
	
	    }
	}
	protected abstract void updateSize(boolean shouldAddAge);
	public boolean isYoung(){
		return false;
	}
	public boolean isTamed() {
	    return (dataWatcher.getWatchableObjectByte(16) & 4) != 0;
	}

	public String getOwnerName() {
	    return dataWatcher.getWatchableObjectString(17);
	}

	public void setOwner(String s) {
	    dataWatcher.updateObject(17, s);
	}
	protected boolean modelizedInteract(EntityPlayer entityplayer) {
		faceEntity(entityplayer, 360F, 360F);
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack==null) return false;
		final int STICKID=Item.stick.shiftedIndex;
		final int BONEID=Item.bone.shiftedIndex;
		int hittingItemID=itemstack.itemID;
		if (hittingItemID==BONEID){
			this.increaseDinoAge();
			itemstack.stackSize--;
		}
		return false;
	}
	protected void updateEntityActionState()
    {
		if (this.isModelized()) return;
		else super.updateEntityActionState();
    }
	protected float getSoundVolume() {
		if (this.isModelized()) return 0.0F;
	    return 0.5F+0.1F*this.getDinoAge();
	}
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("isModelized", this.isModelized());
		nbttagcompound.setInteger("Hunger", this.getHunger());
		nbttagcompound.setInteger("HungerTick", this.getHungerTick());
		nbttagcompound.setInteger("DinoAge", this.getDinoAge());
		nbttagcompound.setInteger("AgeTick", this.getDinoAgeTick());
		nbttagcompound.setByte("OrderStatus",
				(byte) (mod_Fossil.EnumToInt(this.OrderStatus)));


		if (getOwnerName() == null) {
			nbttagcompound.setString("Owner", "");
		} else {
			nbttagcompound.setString("Owner", getOwnerName());
		}
    }
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        if (nbttagcompound.hasKey("isModelized")) this.setModelized(nbttagcompound.getBoolean("isModelized"));
		this.setDinoAge(nbttagcompound.getInteger("DinoAge"));
		this.setDinoAgeTick(nbttagcompound.getInteger("AgeTick"));
		this.setHunger(nbttagcompound.getInteger("Hunger"));
		if (this.getHunger() <= 0)
			this.setHunger(this.getHungerLimit());
		OrderStatus = EnumOrderType.values()[nbttagcompound
		                     				.getByte("OrderStatus")];
		String s = nbttagcompound.getString("Owner");

		if (s.length() > 0) {
			setOwner(s);
			setTamed(true);
		}
    }
	protected boolean modelizedDrop(){
		if (!this.isModelized()) return false;
		else{
			if (!worldObj.isRemote){
				this.entityDropItem(new ItemStack(mod_Fossil.biofossil, 1),0.0f);
				this.dropFewItems(false, 0);
				this.setDead();
			}
			return true;
		}
	}
	protected abstract int foodValue(Item asked);
	public abstract EnumOrderType getOrderType();
	public void HandleEating(Item food) {
		HandleEating(foodValue(food));
		
	}
	public abstract void HoldItem(Item itemGot);
	public void onUpdate(){
        super.onUpdate();
       /* if (!this.isModelized()){
        	HandleBreed();
    		if (FossilOptions.DinoHunger)HandleHunger();
        }*/

	}
	protected void PickUpItem(Item getItem){
		if (foodValue(getItem)>0){
			HandleEating(getItem);
		}else HoldItem(getItem);
	}

	public void CheckSkin() {}
	protected boolean EOCInteract(ItemStack itemstack,EntityPlayer entityplayer){
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
		}else return false;
	}
	public void BlockInteractive(){};
    public boolean func_82171_bF()
    {
        ItemStack var1 = ((EntityPlayer)this.riddenByEntity).getHeldItem();
        return var1 != null && var1.itemID == mod_Fossil.Whip.shiftedIndex;
    }
	public void SetOrder(EnumOrderType order) {
		OrderStatus=order;
		
	}
	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {


	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {


	}
	public abstract float getGLX();
	public abstract float getGLY();
	public float getGLZ(){
		return this.getGLX();
	}
}