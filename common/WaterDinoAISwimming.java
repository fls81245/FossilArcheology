package net.minecraft.src;

public class WaterDinoAISwimming extends EntityAIBase
{
    protected EntityDinosaurce entity;
    protected IWaterDino entityInterface=null;
    protected boolean usuallySurface=true;
    protected final float FLOAT_SPEED;
    protected final float SINK_SPEED;
    protected final float FAST_FLOAT_SPEED;
    protected final float FAST_SINK_SPEED;
    protected final float FOLLOW_RANGE=1.5F;
    protected boolean fastFlag;
    protected boolean diveAtNight=false;
    public WaterDinoAISwimming(EntityDinosaurce par1EntityDinosaurce,boolean isUsuallySurface,float floatSpeed,float sinkSpeed)
    {
        this.entity = par1EntityDinosaurce;
        if (entity instanceof IWaterDino) entityInterface=(IWaterDino)entity;
        this.usuallySurface=isUsuallySurface;
        this.setMutexBits(4);
        //par1EntityDinosaurce.getNavigator().setCanSwim(true);
        FLOAT_SPEED=(floatSpeed>1.0F)?1.0F:floatSpeed;
        SINK_SPEED=-sinkSpeed;
        FAST_FLOAT_SPEED=FLOAT_SPEED*50;
        FAST_SINK_SPEED=-FAST_FLOAT_SPEED;
    }
    public WaterDinoAISwimming(EntityDinosaurce par1EntityDinosaurce,boolean isUsuallySurface,float floatSpeed)
    {
    	this(par1EntityDinosaurce,isUsuallySurface,floatSpeed,floatSpeed);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	if (this.entityInterface==null) return false;
        return this.entity.isInWater() || this.entity.handleLavaMovement();
    }
    public EntityAIBase setDiveAtNight(){
    	this.diveAtNight=true;
    	return this;
    }
    /**
     * Updates the task
     */
    public void updateTask()
    {
		EntityPlayer ownerTmp=(EntityPlayer)(entity.getOwner());
		if (entity.getOrderType()==EnumOrderType.Follow && ownerTmp!=null && ownerTmp.isInWater()){
			if (Math.abs(this.entity.posY-ownerTmp.posY)>this.FOLLOW_RANGE)
				entity.motionY=(ownerTmp.posY < entity.posY)?FAST_SINK_SPEED:FAST_FLOAT_SPEED;
			else entity.motionY=(ownerTmp.posY < entity.posY)?SINK_SPEED:FLOAT_SPEED;
			return;
		}
		if (this.entity.getNavigator().noPath() && (!this.diveAtNight||(this.diveAtNight && this.entity.worldObj.isDaytime()))){
			this.entity.motionY=this.FLOAT_SPEED;
		}
		//controlSurfacing();

    }
    protected void controlSurfacing(){
    	byte var1 = 5;
        double var2 = 0.0D;
        double var6;
        for (int var4 = 0; var4 < var1; ++var4)
        {
            double var5 = this.entity.boundingBox.minY + (this.entity.boundingBox.maxY - this.entity.boundingBox.minY) * (double)(var4 + 0) / (double)var1 - 0.125D;
            double var7 = this.entity.boundingBox.minY + (this.entity.boundingBox.maxY - this.entity.boundingBox.minY) * (double)(var4 + 1) / (double)var1 - 0.125D;
            AxisAlignedBB var9 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(this.entity.boundingBox.minX, var5, this.entity.boundingBox.minZ, this.entity.boundingBox.maxX, var7, this.entity.boundingBox.maxZ);

            if (this.entity.worldObj.isAABBInMaterial(var9, Material.water))
            {
                var2 += 1.0D / (double)var1;
            }
        }
        if (var2 < 1.0D)
        {
            var6 = var2 * 2.0D - 1.0D;
            this.entity.motionY += 0.03999999910593033D * var6;
        }
        else
        {
            if (this.entity.motionY < 0.0D)
            {
                this.entity.motionY =0.0D;
            }

            this.entity.motionY += (double)this.FAST_FLOAT_SPEED;
        }
    }
}
