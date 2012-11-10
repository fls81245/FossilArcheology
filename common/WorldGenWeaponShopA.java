package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.client.Minecraft;

public class WorldGenWeaponShopA implements IWorldGenerator
{	
	private File SchFile;
	private FileInputStream SchInp;
	private NBTInputStream SchSource;
	private ArrayList ModelTagList=new ArrayList();
	private int WidthX,Layers,WidthZ;
	private byte[] BlockArray,MDArray;
	private int ShopCount=0;
	private static final int ShopLimit=6250;
    public WorldGenWeaponShopA()
    {
		//SchFile=new File(Minecraft.getMinecraftDir(), "/resources/FossilStructers/WeaponShopA.schematic");
    	Class clsTmp=this.getClass();
    	URL urlTmp=clsTmp.getResource("/FossilStructers/");
    	String tmp=urlTmp.getFile();
    	
    	File dirTmp=new File(tmp);
    	SchFile=new File(dirTmp,"WeaponShopA.schematic");
    	mod_Fossil.DebugMessage("Model route:"+SchFile.getPath());
    	if (SchFile.exists()){
    		try{
    			SchInp=new FileInputStream(SchFile);
        		SchSource=new NBTInputStream(SchInp);
        		ModelTagList.add((CompoundTag)SchSource.readTag());
        		SchSource.close();
    		}catch(Throwable t){return;};
        	mod_Fossil.DebugMessage("WeaponShopA model loaded");
    	}
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
    	chunkX*=16;
    	chunkZ*=16;
    	int posY=50;
    	if (ModelTagList.isEmpty()) {
    		//mod_Fossil.DebugMessage("Ship model failed.");
    		return;
    	}
    	CompoundTag Tg=(CompoundTag)ModelTagList.get(random.nextInt(ModelTagList.size()));
    	WidthX=((ShortTag)(Tg.getValue().get("Width"))).getValue();
    	Layers=((ShortTag)(Tg.getValue().get("Height"))).getValue();
    	WidthZ=((ShortTag)(Tg.getValue().get("Length"))).getValue();
    	BlockArray=((ByteArrayTag)(Tg.getValue().get("Blocks"))).getValue();
    	MDArray=((ByteArrayTag)(Tg.getValue().get("Data"))).getValue();
    	if (ShopCount!=0){
    		ShopCount--;
    		return;
    	}else ShopCount=ShopLimit;
    	int ScanX,ScanY,ScanZ,MixedIndex;
    	int WaterLayerCount=0;
    	short TmpBlock,TmpMD;
    	
        byte Tall = 7;
        int WidthX = 12;
        int WidthZ = 11;
        int AirBlockCount = 0;
        //Space Checking Function
        for (int XScanning = chunkX; XScanning <= chunkX + WidthX; XScanning++)
        {
                for (int ZScanning = chunkZ; ZScanning <= chunkZ + WidthZ; ZScanning++)
                {
                    Material material = world.getBlockMaterial(XScanning, posY, ZScanning);
                    if (!material.isSolid())
                    {
                        return;
                    }
                    /*if ((XScanning == posX - WidthX - 1 || 
                    	XScanning == posX + WidthX + 1 || 
                    	ZScanning == posZ - WidthZ - 1 || 
                    	ZScanning == posZ + WidthZ + 1) 
                    	&& YScanning == posY 
                    	&& world.isAirBlock(XScanning, YScanning, ZScanning) 
                    	&& world.isAirBlock(XScanning, YScanning + 1, ZScanning))
                    {
                        AirBlockCount++;
                        if (AirBlockCount > 20)
                        {
                            return false;
                        }
                    }*/
                }
        }
        /*if (AirBlockCount < 1)
        {
            return false;
        }*/
         
        //Generate Function
        for (ScanY=0;ScanY<Layers;ScanY++){
        	for (ScanZ=0;ScanZ<WidthZ;ScanZ++){
        		for (ScanX=0;ScanX<WidthX;ScanX++){
        			MixedIndex=ScanY*WidthX*WidthZ+ScanZ*WidthX+ScanX;
        			TmpBlock=BlockArray[MixedIndex];
        			TmpMD=MDArray[MixedIndex];
        			if (TmpBlock==Block.torchWood.blockID) TmpBlock=0;
        				world.setBlockAndMetadata(chunkX-WidthX/2+ScanX, posY+ScanY, chunkZ-WidthZ/2+ScanZ, TmpBlock,TmpMD);
        				if (TmpBlock==Block.chest.blockID){
        					SetupTileEntitys(world,random,chunkX-WidthX/2+ScanX,posY+ScanY, chunkZ-WidthZ/2+ScanZ);
        				}
        		}
        	}
        }
        mod_Fossil.DebugMessage(new StringBuilder().append("Placing Weapon Shop-A at ").append(chunkX).append(',').append(posY).append(',').append(chunkZ).toString());
    }
    public ItemStack GetRandomContent(Random rand){
    	int Chance=rand.nextInt(1000);
    	if (Chance<=10) return new ItemStack(Item.swordDiamond);
    	if (Chance<20) return new ItemStack(mod_Fossil.Diamondjavelin,rand.nextInt(63)+1);
    	if (Chance<35) return new ItemStack(Item.swordGold);
    	if (Chance<50) return new ItemStack(mod_Fossil.Goldjavelin,rand.nextInt(63)+1);
    	if (Chance<70) return new ItemStack(Item.swordSteel);
    	if (Chance<90) return new ItemStack(mod_Fossil.Ironjavelin,rand.nextInt(63)+1);
    	if (Chance<105) return new ItemStack(Item.swordStone);
    	if (Chance<120) return new ItemStack(mod_Fossil.Stonejavelin,rand.nextInt(63)+1);
    	if (Chance<130) return new ItemStack(Item.swordWood);
    	if (Chance<140) return new ItemStack(mod_Fossil.Woodjavelin,rand.nextInt(63)+1);
    	if (Chance<240) return new ItemStack(Item.bow);
    	if (Chance<750) return new ItemStack(Item.arrow,rand.nextInt(63)+1);
    	return null;
    }
    public void SetupTileEntitys(World world,Random rand,int CordX,int CordY,int CordZ){
    	TileEntity Target=world.getBlockTileEntity(CordX, CordY, CordZ);
    	if (Target==null)return;
    	if (Target instanceof TileEntityFurnace) return;
    	else if (Target instanceof TileEntityChest){
    		TileEntityChest Chest=(TileEntityChest)Target;
            ItemStack Tmp=null;
            int Count=rand.nextInt(27);
            for (int i=1;i<=Count;i++){
            	Tmp=GetRandomContent(rand);
            	if (Tmp!=null){
            		Chest.setInventorySlotContents(rand.nextInt(Chest.getSizeInventory()), Tmp);
            	}
            }
    	}
    	else return;
    }
}
