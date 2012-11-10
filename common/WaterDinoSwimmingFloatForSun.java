package net.minecraft.src;

public class WaterDinoSwimmingFloatForSun extends WaterDinoAISwimming {

	public WaterDinoSwimmingFloatForSun(EntityDinosaurce par1EntityDinosaurce,
			boolean isUsuallySurface, float floatSpeed) {
		super(par1EntityDinosaurce, isUsuallySurface, floatSpeed);
	}
    public void updateTask()
    {
		/*boolean isMorning=this.entity.worldObj.isDaytime();
		if (isMorning){
			if (!this.entityInterface.isOnSurface()){
				this.entity.motionY=this.FLOAT_SPEED;
			}
		}else{
			if (this.entityInterface.isOnSurface() || !this.entity.isCollidedVertically){
				this.entity.motionY=this.SINK_SPEED;
			}else this.entity.motionY=0.0D;
		}*/

		//controlSurfacing();

    }
}
