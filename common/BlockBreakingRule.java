package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockBreakingRule {
	World worldObj=null;
	EntityDinosaurce destroyer=null;
	int ageLimit;
	Block[] targetBlocks=null;
	public BlockBreakingRule(World world,EntityDinosaurce destroyer,int destroyAge,Block[] targetBlocks){
		this.worldObj=world;
		this.destroyer=destroyer;
		this.ageLimit=destroyAge;
		this.targetBlocks=targetBlocks;
	}
	public BlockBreakingRule(World world,EntityDinosaurce destroyer,int destroyAge,float BlockHardness){
		super();
		ArrayList<Block> targetBlockList=new ArrayList<Block>();
		Block[] dataList=Block.blocksList;
		if (dataList!=null){
			for (int i=0;i<dataList.length;i++){
				if (dataList[i]==null) continue;
				if (dataList[i].getBlockHardness(world, 0, 0, 0)<BlockHardness) targetBlockList.add(dataList[i]);
			}
			targetBlocks=new Block[targetBlockList.size()];
			for (int i=0;i<targetBlockList.size();i++){
				targetBlocks[i]=targetBlockList.get(i);
			}
		}else{
			this.targetBlocks=new Block[]{};
		}
		this.worldObj=world;
		this.destroyer=destroyer;
		this.ageLimit=destroyAge;
		
	}
	public void execute() {
		if (!FossilOptions.TRexBreakingBlocks) return;
		if (destroyer.getDinoAge()<this.ageLimit) return;
		for (int j=(int)Math.round(destroyer.boundingBox.minX)-1;j<=(int)Math.round(destroyer.boundingBox.maxX)+1;j++){
			for (int k=(int)Math.round(destroyer.boundingBox.minY);(k<=(int)Math.round(destroyer.boundingBox.maxY)+3) &&(k<=127);k++){
				for (int l=(int)Math.round(destroyer.boundingBox.minZ)-1;l<=(int)Math.round(destroyer.boundingBox.maxZ)+1;l++){
					if (k<0) return;
					if (k<destroyer.posY) continue;
					int tmpID=worldObj.getBlockId(j, k, l);
					for (int i=0;i<this.targetBlocks.length;i++){
						if (targetBlocks[i]==Block.blocksList[tmpID]){
							worldObj.playAuxSFX(2001, j, k, l, tmpID);
							worldObj.setBlockWithNotify(j,k,l,0);
						}
					}
				}
			}
		}
		
	}

}
