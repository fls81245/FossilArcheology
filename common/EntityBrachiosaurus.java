package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;

public class EntityBrachiosaurus extends EntityDinosaurce  {
	public boolean isTamed=false;
	public final float HuntLimit=getHungerLimit()*4/5;
	public String OwnerName;
	final float PUSHDOWN_HARDNESS=5.0F;
	protected final int AGE_LIMIT=36;
	public EntityBrachiosaurus(World world) {
		super(world);
		texture="/skull/Brachiosaurus.png";
		this.SelfType=EnumDinoType.Brachiosaurus;
		this.setSize(1.5F, 1.5F);
		this.moveSpeed=0.3F;
        health = 8;
		this.setHunger(getHungerLimit());
        this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new DinoAIGrowup(this, AGE_LIMIT));
		this.tasks.addTask(0, new DinoAIStarvation(this));
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.ridingHandler=new DinoAIControlledByPlayer(this, 0.34F));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        this.tasks.addTask(5, new DinoAIFollowOwner(this, this.moveSpeed, 5F, 2.0F));
        this.tasks.addTask(6, new DinoAIEatLeavesWithHeight(this,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(6, new DinoAIUseFeederWithHeight(this,this.moveSpeed,24,this.HuntLimit));
        this.tasks.addTask(7, new DinoAIWander(this, this.moveSpeed));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
		
	}
	public int getHungerLimit(){
		return 500;
	}
    public boolean isAIEnabled()
    {
        return (!this.isModelized());
    }
	public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
		if (this.modelizedDrop()) return true;
		return super.attackEntityFrom(damagesource, i);
    }
	 protected String getLivingSound()
	    {
			if (worldObj.getClosestPlayerToEntity(this, 16D)!=null) return "Brach_living";
			else return null;

	    }
		protected String getHurtSound()
	    {
	        return "mob.cowhurt";
	    }

	    protected String getDeathSound()
	    {
	        return "Brach_death";
	    }
	@Override
	public String getOwnerName() {
		return this.OwnerName;
	}

	@Override
	public void setOwner(String s) {
		this.OwnerName=s.toString();

	}

	@Override
	public boolean isTamed() {
		return this.isTamed; 
	}

	@Override
	public void setTamed(boolean flag) {
		this.isTamed=flag;

	}

	public void SetOrder(EnumOrderType input) {
		this.OrderStatus=input;

	}

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
		if (this.getHunger()>=getHungerLimit()) this.setHunger(getHungerLimit());
		return true;
	}

	public boolean CheckEatable(int itemIndex) {
		Item tmp=Item.itemsList[itemIndex];
		boolean result=false;
		result=
				(tmp==Item.wheat)||
				(tmp==Item.melon);
		return result;
}


	@Override
	public void ShowPedia(EntityPlayer checker) {
		//if (worldObj.isRemote) return;
		PediaTextCorrection(SelfType,checker);
		if (this.isTamed()){
				mod_Fossil.ShowMessage(new StringBuilder().append(OwnerText).append(this.getOwnerName()).toString(),checker);
				mod_Fossil.ShowMessage(new StringBuilder().append(AgeText).append(this.getDinoAge()).toString(),checker);
				mod_Fossil.ShowMessage(new StringBuilder().append(HelthText).append(this.health).append("/").append(getMaxHealth()).toString(),checker);
				mod_Fossil.ShowMessage(new StringBuilder().append(HungerText).append(this.getHunger()).append("/").append(this.getHungerLimit()).toString(),checker);

			//if ((this.isSelfTamed() && this.age>4 &&!worldObj.multiplayerWorld && riddenByEntity == null)) mod_Fossil.ShowMessage(RidiableText);
		}else{
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
			if ((this.isTamed() && this.getDinoAge() > 4 && riddenByEntity == null))
				resultList.add(RidiableText);
			if (!resultList.isEmpty()) {
				result=new String[1];
				result=resultList.toArray(result);
			}
		}
		return result;
	}
	



	

	/*@Override
	protected void GrowUp() {
		if (age<=AGE_LIMIT && this.riddenByEntity==null){
		AgeTick++;
			if(AgeTick>=GROW_TIME_COUNT){
				//mod_Fossil.ShowMessage((new StringBuilder()).append((int)Math.round(1.0+age*0.3)).toString());

					AgeTick=0;
					age++;
					if (health<20) health+=1;
					updateSize(false);
					setPosition(posX,posY,posZ);
				if (!CheckSpace()){	
					age--;
					if (health>1) health-=1;
					updateSize(false);
					setPosition(posX,posY,posZ);
					if (isTamed()) SendStatusMessage(EnumSituation.NoSpace,this.SelfType); 
				}
				this.moveSpeed=0.3F+this.age*0.05f;
				this.heal(this.getMaxHealth()/AGE_LIMIT);
			}
		}

	}*/
	public boolean interact(EntityPlayer entityplayer)
    {
		if (this.isModelized()) return modelizedInteract(entityplayer);
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
		if (itemstack!=null && itemstack.itemID==mod_Fossil.ChickenEss.shiftedIndex){
			if (this.getDinoAge()>=12 || this.getHunger()<=0)return false;
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
        if(itemstack != null && itemstack.itemID==Item.stick.shiftedIndex){
        	if(entityplayer.username.equalsIgnoreCase(getOwnerName())){				
				 if(!worldObj.isRemote)
				{
					isJumping = false;
					setPathToEntity(null);
					OrderStatus=EnumOrderType.values()[(mod_Fossil.EnumToInt(OrderStatus)+1)%3];
					SendOrderMessage(OrderStatus);
				}
				return true;
				
			}
        }
		if (this.isTamed() && this.getDinoAge()>=2 &&!worldObj.isRemote&& (riddenByEntity == null || riddenByEntity == entityplayer)){
		 	entityplayer.rotationYaw=this.rotationYaw;
            entityplayer.mountEntity(this);
            this.setPathToEntity(null);
            this.renderYawOffset=this.rotationYaw;
            return true;
		}
        return false;
    }
	@Override
	public EntityAnimal spawnBabyAnimal(EntityAnimal entityanimal) {
		return null;
	}

	@Override
	public int getMaxHealth() {
		return 20+10*this.getDinoAge();
	}
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setByte("OrderStatus", (byte)(mod_Fossil.EnumToInt(this.OrderStatus)));
    }
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
		InitSize();
		OrderStatus=EnumOrderType.values()[nbttagcompound.getByte("OrderStatus")];
    }
	private void InitSize(){
		updateSize(false);
		setPosition(posX,posY,posZ);
		this.moveSpeed=0.3F+this.getDinoAge()*0.05f;
	}
	public void onUpdate(){
		super.onUpdate();
		if (this.getDinoAge()>=4) BlockInteractive();		
	}
	protected boolean isMovementCeased()
	    {
	        return isSelfSitting();
	    }

	/*public void onLivingUpdate(){
		if (this.isModelized()) {
			super.onLivingUpdate();
			return;
		}
		if (this.riddenByEntity==null){
			super.onLivingUpdate();

			if (Hunger<=this.HuntLimit && !isSelfSitting()&& !hasPath()){
				//mod_Fossil.ShowMessage(new StringBuilder().append("Feeding Boolean:").append(FindWheats(24)).append(FindFeeder(24)).append(FindFren(24)).toString());
				if (!FindWheats(24)) {
					if (!FindFeeder(24)){
						FindLeave(24);
					}
				}
				return;
			}

			if(!hasAttacked && !hasPath() && isTamed() && ridingEntity == null)
	        {
	            EntityPlayer entityplayer = worldObj.getPlayerEntityByName(getOwnerName());
	            if(entityplayer != null)
	            {
					if (OrderStatus==EnumOrderType.Follow){
						float f = entityplayer.getDistanceToEntity(this);
						if(f > 6F)
						{
							getPathOrWalkableBlock(entityplayer, f);
						}
					}
	            } else{
	            	OrderStatus=EnumOrderType.FreeMove;
	            }
	            return;
	        }
			
		}else{
            HandleRiding();
			super.onLivingUpdate();
		}
	}*/
    public void applyEntityCollision(Entity entity){
    	if (this.isModelized()) return;
		if (entity instanceof EntityLiving && !(entity instanceof EntityPlayer)){
			if (this.onGround && ((EntityLiving)entity).getEyeHeight()<this.getHalfHeight()){
				this.onKillEntity((EntityLiving)entity);
				((EntityLiving)entity).attackEntityFrom(DamageSource.causeMobDamage(this), 10);
				return;
			}
		}
		super.applyEntityCollision(entity);
	}
	/*protected void updateEntityActionState()
    {
		if (this.isModelized()) return;
		if (this.riddenByEntity==null){
			super.updateEntityActionState();
	        if(isInWater())
	        {
	            setSelfSitting(false);
	        }
			if (!isSelfSitting()&& !hasPath()&&(new Random().nextInt(1000)==5)){
				if (!FindWheats(6)) FindLeave(6);
			}
		}
    }*/
	public void setSelfSitting(boolean b) {
		if (b) this.OrderStatus=EnumOrderType.Stay;
		else this.OrderStatus=EnumOrderType.FreeMove;
		
	}

	/*private boolean FindLeave(int range) {
		float TempDis=range*2;
		int targetX=0;
		int targetY=0;
		int targetZ=0;
		int EyeHeight=(int)Math.round(this.getEyeHeight());
		int LowerBound=EyeHeight-2;
		if (LowerBound<0)LowerBound=0;
		for (int j=LowerBound;j<EyeHeight+2;j++){
				for (int i=-range;i<=range;i++){
						for (int k=-range;k<=range;k++){
							if (worldObj.getBlockId((int)Math.round(posX+i), (int)Math.round(posY+j),(int)Math.round(posZ+ k))==Block.leaves.blockID){
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
				if (this.getHungerTick()==0){
					FaceToCoord((int)-(posX+targetX),(int)(posY+targetY),(int)-(posZ+targetZ));
					HandleEating(20,true);
					worldObj.playAuxSFX(2001, (int)Math.round(posX+targetX), (int)Math.round(posY+targetY), (int)Math.round(posZ+targetZ), Block.tallGrass.blockID);
					worldObj.setBlockWithNotify((int)Math.round(posX+targetX),(int)Math.round(posY+targetY),(int)Math.round(posZ+targetZ),0);
					this.heal(3);
					EatTick=100;
					this.setPathToEntity(null);
				}
				return true;
			}
		}else return false;
		
	}*/

	/*private boolean FindFeeder(int range){ 
		if(range>6) range=6;
		TileEntityFeeder target=null;
		if (GetNearestTileEntity(2,range)!=null){
			target=(TileEntityFeeder)GetNearestTileEntity(2,range);
			if (GetDistanceWithTileEntity((TileEntity)target)>3){
				//mod_Fossil.ShowMessage(new StringBuilder().append("Setting Path:").append(target.xCoord).append(",").append(target.yCoord).append(",").append(target.zCoord).toString());
				setPathToEntity(worldObj.getEntityPathToXYZ((Entity)this,(int)Math.round(target.xCoord),(int)Math.round(target.yCoord),(int)Math.round(target.zCoord),(float)range,true,false,true,false));
				
			}else{
				if (target.CheckIsEmpty(this))return false;
				FaceToCoord(target.xCoord,target.yCoord,target.zCoord);
				target.Feed(this);
				this.setTarget(null);
			}
			return true;
		}else return false;
	}*/

	/*private boolean FindWheats(int range) {
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
			List searchlist=worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityItem.class, AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(range, 4D, range));
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
	}*/

	public boolean isSelfSitting() {
		return (this.OrderStatus==EnumOrderType.Stay);
	}
	/*private TileEntity GetNearestTileEntity(int targetType,int range){
		final int Chest=0;
		final int Furance=1;
		final int Feeder=2;
		TileEntity result=null;
		TileEntity tmp=null;
		if (range>6) range=6;
		float Distance=range+1;
		float EyeHeight=this.getEyeHeight();
		int initHeight=(int)Math.round(posY)-(int)EyeHeight;
		if (initHeight<0)initHeight=0;
		for (int i=(int)Math.round(posX)-range;i<=(int)Math.round(posX)+range;i++){
			for (int j=initHeight;j<=(int)Math.round(posY)+(int)EyeHeight;j++){
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
	public float getEyeHeight()
    {
		return (4+this.getDinoAge()/1.8F);
    }
	public float getHalfHeight(){
		return getEyeHeight()/2;
	}
	public float getSpeedModifier()
    {
		float f=1.0F+(float)Math.floor((float)this.getDinoAge()/5F);
		return f;
    }
	public void BlockInteractive(){
		if (!FossilOptions.TRexBreakingBlocks) return;
		if (this.isModelized()) return;
		int ObjectTall=0;
		int TmpID=0;
		int HeightCount=0;
		int k=0;
		for (int j=(int)Math.round(boundingBox.minX)-1;j<=(int)Math.round(boundingBox.maxX)+1;j++){
			for (HeightCount=0;HeightCount<=(int)getHalfHeight();HeightCount++){
				for (int l=(int)Math.round(boundingBox.minZ)-1;l<=(int)Math.round(boundingBox.maxZ)+1;l++){
					k=(int)Math.round(boundingBox.minY)+HeightCount;
					TmpID=worldObj.getBlockId(j, k, l);
					if (Block.blocksList[TmpID]!=null && Block.blocksList[TmpID].getBlockHardness(worldObj,(int)posX,(int)posY,(int)posZ)<this.PUSHDOWN_HARDNESS){
						ObjectTall=GetObjectTall(j,k,l);
						if (ObjectTall>0 && !isObjectTooTall(ObjectTall+HeightCount)) DestroyTower(j,k,l,ObjectTall);
					}
				}
			}
		}
	}
	private boolean isObjectTooTall(int targetX,int targetY,int targetZ){
		return (GetObjectTall(targetX,targetY,targetZ)>getHalfHeight());
	}
	private boolean isObjectTooTall(int ObjectTall){
		float HalfHeight=getHalfHeight();
		return ((float)ObjectTall>HalfHeight);
	}
	private int GetObjectTall(int targetX,int targetY,int targetZ){
		int count=0;
		while(!worldObj.isAirBlock(targetX, targetY+count, targetZ)){
			count++;
		}
		return count;
	}
	private void DestroyTower(int targetX,int bottomY,int targetZ,int ObjectTall){
		int TmpID=0;
		for (int y=bottomY;y<=bottomY+ObjectTall;y++){
			TmpID=worldObj.getBlockId(targetX, y, targetZ);
			worldObj.playAuxSFX(2001, targetX, y, targetZ, TmpID);
			worldObj.setBlockWithNotify(targetX, y, targetZ,0);
		}
	}
	public void updateRiderPosition()
    {
        if(riddenByEntity == null)
        {
            return;
        } else
        {
            riddenByEntity.setPosition(posX, posY+getHalfHeight()*1.2, posZ);
            return;
        }
    }
	protected void updateWanderPath()
    {
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999F;
		if (OrderStatus==EnumOrderType.FreeMove || !isTamed()){
			for(int l = 0; l < 10+this.getDinoAge(); l++)
			{
				int i1 = MathHelper.floor_double((posX + (double)rand.nextInt(24+(int)(width*width*4))) - (12D+(width*width*2)));
				int j1 = MathHelper.floor_double((posY + (double)rand.nextInt(7)) - 3D);
				int k1 = MathHelper.floor_double((posZ + (double)rand.nextInt(24+(int)(width*width*4))) - (12D+(width*width*2)));
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
    }
	private void HandleRiding(){

			if(riddenByEntity != null)
	        {

				if (this.riddenByEntity instanceof EntityPlayerSP){
					if (this.onGround){
						if (((EntityPlayerSP)this.riddenByEntity).movementInput.jump){
							this.jump();
							((EntityPlayerSP)this.riddenByEntity).movementInput.jump=false;
						}							
							this.rotationYaw-=(float) ((float)(((EntityPlayerSP) this.riddenByEntity).moveStrafing)*5);
				            for(; this.rotationYaw < -180F; this.rotationYaw += 360F) { }
				            for(; this.rotationYaw >= 180F; this.rotationYaw -= 360F) { }
							this.moveForward=((EntityPlayerSP) this.riddenByEntity).moveForward*this.moveSpeed;
							//if (this.SneakScream) this.SneakScream=false;
					}
				}
	        }

	}
	@Override
	protected void updateSize(boolean shouldAddAge) {
		if (shouldAddAge && this.getDinoAge()<this.AGE_LIMIT) this.increaseDinoAge();
		setSize((float)(1.5F+0.3*(float)this.getDinoAge()),(float)(1.5F+0.3*(float)this.getDinoAge()));
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
		return (float)(1.5F+0.3*(float)this.getDinoAge());
	}
	@Override
	public float getGLY() {
		return (float)(1.5F+0.3*(float)this.getDinoAge());
	}
}
