package net.minecraft.src;

public class DinoAILearnChest extends EntityAIBase {
	IHighIntellegent learnerInterface=null;
	EntityDinosaurce learner=null;
	TileEntityChest nearbyChest=null;
	int learningTick=300;
	public DinoAILearnChest(EntityDinosaurce learnerPar){
		learner=learnerPar;
		if (learner instanceof IHighIntellegent)learnerInterface=(IHighIntellegent)learner;
	}
	@Override
	public boolean shouldExecute() {
		if (learner==null || learnerInterface==null) return false;
		if (learnerInterface.isLeartChest()) return false;
		nearbyChest=getNearbyChest(15,3,15);
		if (nearbyChest==null) return false;
		return true;
	}
	private TileEntityChest getNearbyChest(int rangeX,int rangeY,int rangeZ) {
		World world=learner.worldObj;
		TileEntity tmp=null;
		if (world==null) return null;
		for (int Xoffset=-rangeX;Xoffset<=rangeX;Xoffset++){
			for (int Yoffset=-rangeY;Yoffset<=rangeY;Xoffset++){
				if (learner.posY+Yoffset<=0) continue;
				for (int Zoffset=-rangeZ;Zoffset<rangeZ;Zoffset++){
					tmp=world.getBlockTileEntity((int)(learner.posX+Xoffset), (int)(learner.posY+Yoffset), (int)(learner.posZ+Zoffset));
					if (tmp instanceof TileEntityChest) return (TileEntityChest) tmp;
				}
			}
		}
		return null;
	}
	public void updateTask() {
		learningTick--;
		if (learningTick<=0){
			learner.SendStatusMessage(EnumSituation.LearingChest,learner.SelfType);
			learnerInterface.setLearntChest(true);
		}
	}
}
