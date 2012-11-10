package net.minecraft.src;

public class DinoAIUseFeeder extends EntityAIBase
{
    protected EntityDinosaurce entityVar;
    private double destX;
    private double destY;
    private double destZ;
    private float field_48317_e;
    protected final int SEARCH_RANGE;
    private final float HUNT_LIMIT;
    private final int USE_RANGE=3;
    protected TileEntityFeeder targetFeeder=null;
    protected EnumDinoEating eating;

    public DinoAIUseFeeder(EntityDinosaurce par1EntityCreature, float par2,int rangePar,float HuntLimitPar,EnumDinoEating eating)
    {
        this.entityVar = par1EntityCreature;
        this.field_48317_e = par2;
        this.setMutexBits(1);
        this.SEARCH_RANGE=rangePar;
        HUNT_LIMIT=HuntLimitPar;
        this.eating=eating;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    		if (entityVar.getHunger()>HUNT_LIMIT) return false;
            Vec3 var1 = GetNearestFeeder();

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
    protected Vec3 GetNearestFeeder(){
    	World worldTmp=this.entityVar.worldObj;
    	Vec3 result=null;
    	double xCord=entityVar.posX,yCord=entityVar.posY,zCord=entityVar.posZ;
    	final int HEIGHT_RANGE=SEARCH_RANGE/2;
    	double distanceSqr=0.0F;
    	double nearestDistanceSqr=(SEARCH_RANGE*SEARCH_RANGE)*2;
    	TileEntity Tmp;
    	for (int secX=(int)(xCord-this.SEARCH_RANGE);secX<(int)(xCord+this.SEARCH_RANGE);secX++){
        	for (int secY=(int)(yCord-HEIGHT_RANGE);secY<(int)(yCord+HEIGHT_RANGE);secY++){
            	for (int secZ=(int)(zCord-this.SEARCH_RANGE);secZ<(int)(zCord+this.SEARCH_RANGE);secZ++){
            		if (secY<0||secY>worldTmp.getHeight()) continue; 
            		Tmp=worldTmp.getBlockTileEntity(secX, secY, secZ);
            		if (Tmp!=null && Tmp instanceof TileEntityFeeder && ((TileEntityFeeder)Tmp).isFilled()){
            			distanceSqr=(secX-xCord)*(secX-xCord)+(secZ-zCord)*(secZ-zCord);
            			if (distanceSqr<nearestDistanceSqr){
            				nearestDistanceSqr=distanceSqr;
            				targetFeeder=(TileEntityFeeder)Tmp;
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
        return !this.entityVar.getNavigator().noPath() && !targetFeeder.tileEntityInvalid;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    	if ((Math.pow((entityVar.posX-destX),2)+Math.pow((entityVar.posZ-destZ), 2))<Math.pow(this.USE_RANGE,2)){
        	targetFeeder.Feed(entityVar,this.eating);
    		if (entityVar.getHunger()>HUNT_LIMIT) this.entityVar.getNavigator().clearPathEntity();
    	}
    	else this.entityVar.getNavigator().tryMoveToXYZ(this.destX, this.destY, this.destZ, this.field_48317_e);
    }
}
