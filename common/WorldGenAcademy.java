package net.minecraft.src;

import java.io.PrintStream;
import java.util.Random;

import net.minecraft.client.Minecraft;
import java.io.FileInputStream;
import java.io.File;

import cpw.mods.fml.common.IWorldGenerator;
public class WorldGenAcademy implements IWorldGenerator
{	
	
	private File SchFile;
	private FileInputStream SchInp;
	private NBTInputStream SchSource;
	private CompoundTag Tg;
	private int WidthX,Layers,WidthZ;
	private byte[] BlockArray,MDArray;
	private int GenCount=0;
	private RelicHoleList Damages;
	private static final int GenLimit=500;
    public WorldGenAcademy()
    {
    	
    	//SchFile=new File(Minecraft.getMinecraftDir(), "/resources/FossilStructers/academy1.schematic");
    	SchFile=new File(this.getClass().getResource("/FossilStructers/").getFile(),"academy1.schematic");
    	mod_Fossil.DebugMessage("Model route:"+SchFile.getPath());
    	if (SchFile.exists()){
    		try{
    			SchInp=new FileInputStream(SchFile);
        		SchSource=new NBTInputStream(SchInp);
        		Tg=(CompoundTag)SchSource.readTag();
        		SchSource.close();
    		}catch(Throwable t){return;};
        	WidthX=((ShortTag)(Tg.getValue().get("Width"))).getValue();
        	Layers=((ShortTag)(Tg.getValue().get("Height"))).getValue();
        	WidthZ=((ShortTag)(Tg.getValue().get("Length"))).getValue();
        	BlockArray=((ByteArrayTag)(Tg.getValue().get("Blocks"))).getValue();
        	MDArray=((ByteArrayTag)(Tg.getValue().get("Data"))).getValue();
        	mod_Fossil.DebugMessage("Academy model loaded");
    	}
    	
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
    	chunkX*=16;
    	chunkZ*=16;
    	int posY=50+random.nextInt(20);
    	if (!SchFile.exists()) {
    		//mod_Fossil.DebugMessage("Academy model failed.");
    		return;
    	}
    	if (GenCount!=0){
    		GenCount--;
    		return;
    	}else GenCount=GenLimit;
    	int ScanX,ScanY,ScanZ,MixedIndex;
    	int SoildCount=0;
    	final int SoildLimit=(WidthX*WidthZ)/2;
    	short TmpBlock,TmpMD;
    	Damages=new RelicHoleList(random,WidthX,Layers,WidthZ,BlockArray,15,3);
 
    	
	    	for (ScanY=2;ScanY<Layers;ScanY++){
	    		if (ScanY==2){
	    			for (ScanX=0;ScanX<WidthX;ScanX++){
	    				for (ScanZ=0;ScanZ<WidthZ;ScanZ++){
	    	    			if (world.isBlockNormalCube(chunkX-WidthX/2+ScanX, posY+ScanY, chunkZ-WidthZ/2+ScanZ))SoildCount++;
	    	    			if (SoildCount>=SoildLimit) break;
	    				}
	    			}
	    			if (SoildCount<SoildLimit) return;
	    		}else{
	    			if (!world.isAirBlock(chunkX-WidthX/2, posY+ScanY, chunkZ-WidthZ/2)) return;
	    		}
	    	}

        for (ScanY=0;ScanY<Layers;ScanY++){
        	for (ScanZ=0;ScanZ<WidthZ;ScanZ++){
        		for (ScanX=0;ScanX<WidthX;ScanX++){
        			MixedIndex=ScanY*WidthX*WidthZ+ScanZ*WidthX+ScanX;
        			TmpBlock=BlockArray[MixedIndex];
        			TmpMD=MDArray[MixedIndex];

        			if (TmpBlock==Block.torchWood.blockID) TmpBlock=0;
        			if (TmpBlock==Block.glowStone.blockID && random.nextInt(100)<10) TmpBlock=0;
        			if (TmpBlock==Block.brick.blockID || TmpBlock==Block.blockClay.blockID){
        				if (TmpBlock==Block.blockClay.blockID) TmpMD=2;
        				TmpBlock=(short)Block.stoneBrick.blockID;
        			}else if (Damages.isHole(ScanX, ScanY, ScanZ)) TmpBlock=0;
        			if (TmpBlock!=0){
        				if (TmpBlock==Block.blockDiamond.blockID){
        					TmpBlock=(short)mod_Fossil.blockcultivateIdle.blockID;
        				}
            			world.setBlockAndMetadata(chunkX-WidthX/2+ScanX, posY+ScanY, chunkZ-WidthZ/2+ScanZ, TmpBlock,TmpMD);
            			if (Block.blocksList[TmpBlock]!=null && Block.blocksList[TmpBlock].hasTileEntity(TmpMD)){
            				SetupTileEntitys(world,random,chunkX-WidthX/2+ScanX,posY+ScanY, chunkZ-WidthZ/2+ScanZ,ScanY);
            			}
        			}else{
        				Material MatCheck=world.getBlockMaterial(chunkX-WidthX/2+ScanX, posY+ScanY, chunkZ-WidthZ/2+ScanZ);
        				if (MatCheck!=Material.water && MatCheck!=Material.sand){
        					world.setBlockAndMetadataWithNotify(chunkX-WidthX/2+ScanX, posY+ScanY, chunkZ-WidthZ/2+ScanZ, 0,0);
        				}
        			}
        		}
        	}
        }
        mod_Fossil.DebugMessage(new StringBuilder().append("Placing Academy of ").append(" at ").append(chunkX).append(',').append(posY).append(',').append(chunkZ).toString());
    }

	public void SetupTileEntitys(World world,Random rand,int CordX,int CordY,int CordZ,int ScanY){
    	TileEntity Target=world.getBlockTileEntity(CordX, CordY, CordZ);
    	boolean isDisc=false;
    	if (Target==null)return;
    	else if (Target instanceof TileEntityChest){
    		TileEntityChest Chest=(TileEntityChest)Target;
            ItemStack Tmp=null;
            isDisc=(ScanY>Layers/2);
	            int Count=(isDisc)?1:rand.nextInt(10);
	            for (int i=1;i<=Count;i++){
	            	Tmp=GetRandomContent(rand,isDisc);
	            	if (Tmp!=null){
	            		Chest.setInventorySlotContents(rand.nextInt(Chest.getSizeInventory()), Tmp);
	            	}
	            }
    	}
    	else return;
    }
    public ItemStack GetRandomContent(Random rand,boolean isDisc){
    	int Chance=rand.nextInt(1000);
		if (isDisc) return (new ItemStack(Item.itemsList[Item.record13.shiftedIndex+rand.nextInt(11)],1));
		else{
			if (Chance<10) return new ItemStack(mod_Fossil.AncientJavelin,rand.nextInt(4)+1);
			if (Chance<200) return new ItemStack(mod_Fossil.biofossil);
			if (Chance<300) return new ItemStack(mod_Fossil.DNA,rand.nextInt(2)+1,rand.nextInt(EnumDinoType.values().length));
			if (Chance<400) return new ItemStack(Item.potion,1,1+rand.nextInt(19));
		}
    	return null;
    }
}
