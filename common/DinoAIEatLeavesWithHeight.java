package net.minecraft.src;

public class DinoAIEatLeavesWithHeight extends EntityAIBase {
	 protected EntityDinosaurce entityVar;
	 private double destX;
	 private double destY;
	 private double destZ;
	 private float field_48317_e;
	 protected final int SEARCH_RANGE;
	 private final float HUNT_LIMIT;
	 private final int USE_RANGE=3;
	 protected float height;
	    public DinoAIEatLeavesWithHeight(EntityDinosaurce par1EntityCreature, float par2,int rangePar,float HuntLimitPar)
	    {
	        this.entityVar = par1EntityCreature;
	        this.field_48317_e = par2;
	        this.setMutexBits(1);
	        this.SEARCH_RANGE=rangePar;
	        HUNT_LIMIT=HuntLimitPar;
	    }
		@Override
		 public boolean shouldExecute()
	    {
			height=this.entityVar.getEyeHeight();
	    	if (entityVar.getHunger()>HUNT_LIMIT) return false;
	    	Vec3 var1 = GetNearestLeave();
	        if (var1 == null)
	        {
	            return false;
	        }
	        else
	        {
	            this.destX = var1.xCoord;
	            this.destY = var1.yCoord;
	            this.destZ = var1.zCoord;
	            return true;
	        }
	    }
		private Vec3 GetNearestLeave() {
			World worldTmp=this.entityVar.worldObj;
	    	Vec3 result=null;
	    	double xCord=entityVar.posX,yCord=entityVar.posY,zCord=entityVar.posZ;
	    	final int HEIGHT_RANGE=2;
	    	double distanceSqr=0.0F;
	    	double nearestDistanceSqr=(SEARCH_RANGE*SEARCH_RANGE)*2;
	    	int Tmp;
	    	for (int secX=(int)(xCord-this.SEARCH_RANGE);secX<(int)(xCord+this.SEARCH_RANGE);secX++){
	        	for (int secY=(int)(yCord+height-HEIGHT_RANGE);secY<(int)(yCord+height+HEIGHT_RANGE);secY++){
	            	for (int secZ=(int)(zCord-this.SEARCH_RANGE);secZ<(int)(zCord+this.SEARCH_RANGE);secZ++){
	            		if (secY<0||secY>worldTmp.getHeight()) continue; 
	            		Tmp=worldTmp.getBlockId(secX, secY, secZ);
	            		if (Tmp==Block.leaves.blockID){
	            			distanceSqr=(secX-xCord)*(secX-xCord)+(secZ-zCord)*(secZ-zCord);
	            			if (distanceSqr<nearestDistanceSqr){
	            				nearestDistanceSqr=distanceSqr;
	            				result=Vec3.createVectorHelper(secX, secY, secZ);
	            			}
	            		}
	            	}
	        	}
	    	}
	    	return result;
		}
		 /**
	     * Returns whether an in-progress EntityAIBase should continue executing
	     */
	    public boolean continueExecuting()
	    {
	        return !this.entityVar.getNavigator().noPath() ;
	    }

	    /**
	     * Execute a one shot task or start executing a continuous task
	     */
	    public void startExecuting()
	    {
	    	World worldTmp=this.entityVar.worldObj;
	    	if (worldTmp.getBlockId((int)destX, (int)destY, (int)destZ)==Block.leaves.blockID && (Math.pow((entityVar.posX-destX),2)+Math.pow((entityVar.posZ-destZ), 2))<Math.pow(this.USE_RANGE,2)){
	    		worldTmp.playAuxSFX(2001, (int)destX, (int)destY, (int)destZ, Block.leaves.blockID + 4096);
	    		worldTmp.setBlockWithNotify((int)destX, (int)destY, (int)destZ, 0);
	    		this.entityVar.HandleEating(30);
	    		if (entityVar.getHunger()>HUNT_LIMIT) this.entityVar.getNavigator().clearPathEntity();
	    		}
	    	else this.entityVar.getNavigator().tryMoveToXYZ(this.destX, this.destY, this.destZ, this.field_48317_e);
	    }
}
