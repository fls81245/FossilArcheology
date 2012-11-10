package net.minecraft.src;

public class DinoAIStarvation extends EntityAIBase {
	EntityDinosaurce mover=null;
	public DinoAIStarvation(EntityDinosaurce arg){
		mover=arg;
	}
	@Override
	public boolean shouldExecute() {
		if (!FossilOptions.DinoHunger) return false;
		mover.decreaseHungerTick();
		return (mover.getHungerTick()<=0)&&(mover.worldObj.difficultySetting > 0 && mover.worldObj.getClosestPlayerToEntity(mover, 24D)!=null);
	}
	public void startExecuting() {
		mover.setHungerTick(mover.HungerTickLimit);
		mover.decreaseHunger();
		if (mover.getHunger()<=0) handleStarvation();
	}
	private void handleStarvation() {
		mover.attackEntityFrom(DamageSource.starve, 20);
		
	}
}
