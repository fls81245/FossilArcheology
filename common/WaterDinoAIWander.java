package net.minecraft.src;

public class WaterDinoAIWander extends EntityAIBase
{
    private EntityDinosaurce entity;
    protected IWaterDino entityInterface=null;
    private double targetX;
    private double targetZ;
    private float speed;
    private final float FLOAT_SPEED;
    public WaterDinoAIWander(EntityDinosaurce par1EntityDinosaurce, float par2,float floatSpeed)
    {
        this.entity = par1EntityDinosaurce;
        if (entity instanceof IWaterDino) entityInterface=(IWaterDino)entity;
        this.speed = par2;
        this.FLOAT_SPEED=floatSpeed;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.entity.getAge() >= 100)
        {
            return false;
        }
        else if (this.entity.getRNG().nextInt(30) != 0)
        {
            return false;
        }
        else
        {
            Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.targetX = var1.xCoord;
                this.targetZ = var1.zCoord;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.entity.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
		/*boolean isMorning=this.entity.worldObj.isDaytime();
		if (isMorning){
			if (!this.entityInterface.isOnSurface()){
				this.entity.motionY=this.FLOAT_SPEED;
			}
		}else{
			if (this.entityInterface.isOnSurface() || !this.entity.isCollidedVertically){
				this.entity.motionY=-this.FLOAT_SPEED;
			}
		}
        this.entity.getNavigator().tryMoveToXYZ(this.targetX, this.entity.posY, this.targetZ, this.speed);*/
    }
}
