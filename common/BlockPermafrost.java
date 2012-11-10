package net.minecraft.src;
//all java files for minecraft should have this.

import java.util.Random;
//because we are using random values below.
public class BlockPermafrost extends BlockBreakable{

	public BlockPermafrost(int i,int j){
		super(i,j,Material.ice, false);
		setTickRandomly(true);
	}
    public String getTextureFile()
    {
       return "/skull/Fos_terrian.png";
    }
    public int getRenderBlockPass()
    {
        return 1;
    }
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return super.shouldSideBeRendered(iblockaccess, i, j, k, 1 - l);
    }
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if((world.getSavedLightValue(EnumSkyBlock.Block, i, j, k) > 11 - Block.lightOpacity[blockID])||
            	(world.canBlockSeeTheSky(i, j+1, k)&&world.isDaytime() /*&& !(world.worldProvider.worldChunkMgr.getBiomeGenAt(i, k) instanceof BiomeGenSnow)*/))
            {
                world.setBlock(i, j, k, Block.dirt.blockID);
                return;
            }
        int targetX;
        int targetY;
        int targetZ;
    	for (targetX=-1;targetX<=1;targetX++){
        	for (targetY=-1;targetY<=1;targetY++){
            	for (targetZ=-1;targetZ<=1;targetZ++){
			        if ((world.getBlockId(i+targetX, j+targetY, k+targetZ)==Block.waterMoving.blockID)||
			        	(world.getBlockId(i+targetX, j+targetY, k+targetZ)==Block.waterStill.blockID)
			        		){
			        	world.setBlock(i+targetX, j+targetY, k+targetZ, Block.ice.blockID);
			        }
			        if ((world.getBlockId(i+targetX, j+targetY, k+targetZ)==Block.lavaMoving.blockID)||
				        	(world.getBlockId(i+targetX, j+targetY, k+targetZ)==Block.lavaStill.blockID)||
				        	(world.getBlockId(i+targetX, j+targetY, k+targetZ)==Block.fire.blockID)
				        		){
			        		world.setBlock(i, j, k, Block.dirt.blockID);
				        	return;
				        }
			        if (world.getBlockId(i+targetX, j+targetY, k+targetZ)==Block.stone.blockID){
			        	world.setBlock(i+targetX, j+targetY, k+targetZ, mod_Fossil.blockIcedStone.blockID);
			        	world.setBlockMetadata(i+targetX, j+targetY, k+targetZ,1);
			        }
            	}
        	}
        }
    }
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if((world.getSavedLightValue(EnumSkyBlock.Block, i, j, k) > 11 - Block.lightOpacity[blockID])||
        	(world.canBlockSeeTheSky(i, j+1, k)&&world.isDaytime() /*&& !(world.worldProvider.worldChunkMgr.getBiomeGenAt(i, k) instanceof BiomeGenSnow)*/))
        {
            world.setBlockWithNotify(i, j, k, Block.dirt.blockID);
            return;
        }
    	for (int i2=0;i2<5;i2++){
	        int targetX=new Random().nextInt(3)-1;
	        int targetY=new Random().nextInt(3)-1;
	        int targetZ=new Random().nextInt(3)-1;
	        if ((world.getBlockId(i+targetX, j+targetY, k+targetZ)==Block.waterMoving.blockID)||
	        	(world.getBlockId(i+targetX, j+targetY, k+targetZ)==Block.waterStill.blockID)
	        		){
	        	world.setBlockWithNotify(i+targetX, j+targetY, k+targetZ, Block.ice.blockID);
	        	return;
	        }
	        if ((world.getBlockId(i+targetX, j+targetY, k+targetZ)==Block.lavaMoving.blockID)||
		        	(world.getBlockId(i+targetX, j+targetY, k+targetZ)==Block.lavaStill.blockID)||
		        	(world.getBlockId(i+targetX, j+targetY, k+targetZ)==Block.fire.blockID)
		        		){
	        		world.setBlockWithNotify(i, j, k, Block.stone.blockID);
		        	return;
		        }
        }
        
    }
	public int idDropped(int i, Random random,int j)
	//when calling this function independently,it can drop 1 item with returned ID value.
    {
		int chance=(int)(new Random().nextInt(20000));
			if (chance>=0 && chance<4000) return mod_Fossil.FernSeed.shiftedIndex;
			if (chance>=4000 && chance<8000) return mod_Fossil.blockSkull.blockID;
			if (chance>=8000 && chance<12000) return mod_Fossil.IcedMeat.shiftedIndex;
			if (chance>=12000 && chance<16000) return Item.bone.shiftedIndex;
			if (chance>=16000 && chance<20000) return Item.book.shiftedIndex;
        return Block.dirt.blockID;
    }
}