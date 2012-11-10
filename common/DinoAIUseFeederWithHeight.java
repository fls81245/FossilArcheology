package net.minecraft.src;

public class DinoAIUseFeederWithHeight extends DinoAIUseFeeder {
	private float ownerHeight;
	public DinoAIUseFeederWithHeight(EntityDinosaurce par1EntityCreature,
			float par2, int rangePar, float HuntLimitPar) {
		super(par1EntityCreature, par2, rangePar, HuntLimitPar,EnumDinoEating.Herbivorous);
		// TODO Auto-generated constructor stub
	}
    public boolean shouldExecute()
    {
		ownerHeight=this.entityVar.getEyeHeight();
		return super.shouldExecute();
    }
    protected Vec3 GetNearestFeeder(){
    	World worldTmp=this.entityVar.worldObj;
    	Vec3 result=null;
    	double xCord=entityVar.posX,yCord=entityVar.posY,zCord=entityVar.posZ;
    	final int HEIGHT_RANGE=2;
    	double distanceSqr=0.0F;
    	double nearestDistanceSqr=(SEARCH_RANGE*SEARCH_RANGE)*2;
    	TileEntity Tmp;
    	for (int secX=(int)(xCord-this.SEARCH_RANGE);secX<(int)(xCord+this.SEARCH_RANGE);secX++){
        	for (int secY=(int)(yCord+ownerHeight-HEIGHT_RANGE);secY<(int)(yCord+ownerHeight+HEIGHT_RANGE);secY++){
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

}
