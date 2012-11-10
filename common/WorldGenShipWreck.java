package net.minecraft.src;

import java.io.PrintStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import net.minecraft.client.Minecraft;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

public class WorldGenShipWreck implements IWorldGenerator{

	private File SchFile;
	private InputStream SchInp;
	private NBTInputStream SchSource=null;
	private ArrayList ModelTagList = new ArrayList();
	private int WidthX, Layers, WidthZ;
	private byte[] BlockArray, MDArray;
	private int ShipCount = 0;
	private static final int ShipLimit = 12500;
	private RelicHoleList Damages;
	public EnumShipTypes SelfType;
	private final int OCEAN_DEPTH = 14;
	private final String[] shipList = { "cheheWreck", "dukeWreck",
			"galleonWreck", "ShipWreck", "shortRangeWreck", "vikingWreck" };
	private boolean loaded=true;

	public WorldGenShipWreck() {
		/*File minecraftDir = FMLCommonHandler.instance()
				.getMinecraftRootDirectory();
		File modsDir = new File(minecraftDir, "mods");
		SourceType loadMode = checkModPro();*/
		// File listTmp=new File(Minecraft.getMinecraftDir(),
		// "/resources/FossilStructers/shipWrecks/");
		final String ZIP_LOC="FossilStructers/shipWrecks/";
		final String FILE_LOC = "/"+ZIP_LOC;
		final String SCH_NAME = ".schematic";
		for (int i = 0; i < shipList.length; i++) {
			for (int j = 0; j < 360; j += 90) {
				String jName = (j == 0) ? "" : "_" + String.valueOf(j);
				URL tester = this.getClass().getResource(
						FILE_LOC + shipList[i] + jName + SCH_NAME);
				if (tester == null)
					continue;
				try {
					/*if (loadMode == SourceType.JAR) {
						String targetTmp=ZIP_LOC
								+ shipList[i] + jName + SCH_NAME;
						ZipFile zipTmp = new ZipFile(new File(modsDir,
								"mod_Fossil.zip"));
						ZipEntry entryTmp=zipTmp.getEntry(targetTmp);
						
						SchInp = zipTmp.getInputStream(entryTmp);
						mod_Fossil.DebugMessage("Model route:"+ targetTmp);
					} else {*/
						SchFile = new File(tester.getFile());
						SchInp = new FileInputStream(SchFile);
						mod_Fossil.DebugMessage("Model route:" + SchFile.getPath());
					//}
					SchSource = new NBTInputStream(SchInp);
					ModelTagList.add((CompoundTag) SchSource.readTag());
					mod_Fossil.DebugMessage("Ship model " + SchFile.getName()
							+ " loaded");


				} catch (Throwable t) {
					loaded=false;
					continue;
				} finally {
					try {
						if (SchSource!=null)SchSource.close();
					} catch (IOException e) {
					}
				}
			}
		}

	}

	/*private SourceType checkModPro() {
		List<ModContainer> modList = Loader.getModList();
		ModContainer tmp;
		final String target = "mod_Fossil";
		Iterator<ModContainer> itr = modList.iterator();
		SourceType result = null;
		while (itr.hasNext()) {
			tmp = itr.next();
			if (tmp.getName().equalsIgnoreCase(target)) {
				result = tmp.getSourceType();
				break;
			}
		}
		return result;
	}*/

	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
		int posY=60;
    	chunkX*=16;
    	chunkZ*=16;
		if (!loaded) return;
		int heightRemind = 0; 
		if (ModelTagList.isEmpty()) {
			// mod_Fossil.DebugMessage("Ship model failed.");
			return;
		}
		CompoundTag Tg = (CompoundTag) ModelTagList.get(random
				.nextInt(ModelTagList.size()));
		WidthX = ((ShortTag) (Tg.getValue().get("Width"))).getValue();
		Layers = ((ShortTag) (Tg.getValue().get("Height"))).getValue();
		if (Layers > this.OCEAN_DEPTH)
			heightRemind = Layers - this.OCEAN_DEPTH;
		WidthZ = ((ShortTag) (Tg.getValue().get("Length"))).getValue();
		BlockArray = ((ByteArrayTag) (Tg.getValue().get("Blocks"))).getValue();
		MDArray = ((ByteArrayTag) (Tg.getValue().get("Data"))).getValue();
		if (ShipCount != 0) {
			ShipCount--;
			return;
		} else
			ShipCount = ShipLimit;
		int ScanX, ScanY, ScanZ, MixedIndex;
		int WaterLayerCount = 0;
		int TmpBlock;
		int TmpMD;
		SelfType = EnumShipTypes.GetRandom(random);
		Damages = new RelicHoleList(random, WidthX, Layers, WidthZ, BlockArray,
				-1, -1);

		while (!world.isBlockNormalCube(chunkX + WidthX / 2, posY, chunkZ + WidthZ
				/ 2)) {
			posY--;
		}

		for (ScanY = 0; ScanY < (Layers - heightRemind); ScanY++) {
			if (world.getBlockMaterial(chunkX - WidthX / 2, posY + ScanY, chunkZ
					- WidthZ / 2) == Material.water) {
				WaterLayerCount++;
				if (WaterLayerCount >= Layers / 2)
					break;
			}
		}
		if (WaterLayerCount < Layers / 2)
			return;
		posY -= heightRemind;

		for (ScanY = 0; ScanY < Layers; ScanY++) {
			for (ScanZ = 0; ScanZ < WidthZ; ScanZ++) {
				for (ScanX = 0; ScanX < WidthX; ScanX++) {
					MixedIndex = ScanY * WidthX * WidthZ + ScanZ * WidthX
							+ ScanX;
					if (MixedIndex >= BlockArray.length)
						return;
					TmpBlock = BlockArray[MixedIndex];
					TmpMD = MDArray[MixedIndex];
					if (TmpBlock == Block.torchWood.blockID)
						TmpBlock = 0;
					else if (Damages.isHole(ScanX, ScanY, ScanZ))
						TmpBlock = 0;
					if (TmpBlock != 0) {
						if (TmpBlock == Block.planks.blockID) {
							TmpMD = this.SelfType.getMetaData();
						}
						/*
						 * if (TmpBlock==Block.cloth.blockID && TmpMD==0){
						 * TmpBlock=SwapCargoBlock(random); if
						 * (TmpBlock!=0)TmpMD=SwapCargoMD(random); else break; }
						 */
						world.setBlockAndMetadata(chunkX - WidthX / 2 + ScanX,
								posY + ScanY, chunkZ - WidthZ / 2 + ScanZ,
								TmpBlock, TmpMD);
						if (Block.blocksList[TmpBlock].hasTileEntity(TmpMD)) {
							SetupTileEntitys(world, random, chunkX - WidthX / 2
									+ ScanX, posY + ScanY, chunkZ - WidthZ / 2
									+ ScanZ);
						}
					} else if (random.nextInt(10000) < 5) {
						EntityBones bones = new EntityBones(world);
						bones.setLocationAndAngles(chunkX - WidthX / 2 + ScanX,
								posY + ScanY, chunkZ - WidthZ / 2 + ScanZ,
								random.nextFloat() * 360F, 0.0F);
						if (world.checkIfAABBIsClear(bones.boundingBox)
								&& world.getCollidingBoundingBoxes(bones,
										bones.boundingBox).size() == 0) {
							world.spawnEntityInWorld(bones);
						}
					}
				}
			}
		}
		mod_Fossil.DebugMessage(new StringBuilder()
				.append("Placing shipwreck of ")
				.append(this.SelfType.toString()).append(" at ").append(chunkX)
				.append(',').append(posY).append(',').append(chunkZ).toString());
	}

	private short SwapCargoBlock(Random rand) {
		int Chance = rand.nextInt(1000);
		switch (this.SelfType) {
		case Science:
			if (Chance < 10)
				return (short) mod_Fossil.blockanalyzerIdle.blockID;
			if (Chance < 20)
				return (short) mod_Fossil.blockcultivateIdle.blockID;
			if (Chance < 30)
				return (short) mod_Fossil.blockworktableIdle.blockID;
			break;
		case MetalTrader:
			if (Chance < 500)
				return (short) Block.blockSteel.blockID;
			if (Chance < 600)
				return (short) Block.blockGold.blockID;
			break;
		case Battleship:
			if (Chance < 333)
				return (short) Block.tnt.blockID;
			break;
		case Cargo:
		default:
			if (Chance < 100)
				return (short) mod_Fossil.blockSkull.blockID;
		}
		return 0;
	}

	private byte SwapCargoMD(Random rand) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void SetupTileEntitys(World world, Random rand, int CordX,
			int CordY, int CordZ) {
		TileEntity Target = world.getBlockTileEntity(CordX, CordY, CordZ);
		if (Target == null)
			return;
		if (Target instanceof TileEntityFurnace)
			return;
		else if (Target instanceof TileEntityChest) {
			TileEntityChest Chest = (TileEntityChest) Target;
			ItemStack Tmp = null;
			int Count = rand.nextInt(27);
			for (int i = 1; i <= Count; i++) {
				Tmp = GetRandomContent(rand);
				if (Tmp != null) {
					Chest.setInventorySlotContents(
							rand.nextInt(Chest.getSizeInventory()), Tmp);
				}
			}
		} else if (Target instanceof TileEntityDispenser) {
			if (this.SelfType != EnumShipTypes.Battleship)
				return;
			else {
				TileEntityDispenser Disper = (TileEntityDispenser) Target;
				ItemStack Tmp;
				int Count = rand.nextInt(3);
				for (int i = 1; i <= Count; i++) {
					Tmp = new ItemStack(Item.arrow, rand.nextInt(63) + 1);
					if (Tmp != null) {
						Disper.setInventorySlotContents(i, Tmp);
					}
				}
			}
		} else
			return;
	}

	public ItemStack GetRandomContent(Random rand) {
		int Chance = rand.nextInt(1000);
		switch (SelfType) {
		case Science:
			if (Chance < 100)
				return new ItemStack(Item.potion, 1, 1 + rand.nextInt(19));
			if (Chance < 200)
				return new ItemStack(mod_Fossil.relic, rand.nextInt(63) + 1);
			if (Chance < 300)
				return new ItemStack(mod_Fossil.stoneboard);
			break;
		case Battleship:
			if (Chance < 2)
				return new ItemStack(mod_Fossil.AncientJavelin);
			if (Chance <= 35)
				return new ItemStack(Block.tnt, rand.nextInt(9) + 1);
			if (Chance < 70)
				return new ItemStack(Item.swordSteel);
			if (Chance < 90)
				return new ItemStack(mod_Fossil.Ironjavelin,
						rand.nextInt(63) + 1);
			if (Chance < 240)
				return new ItemStack(Item.bow);
			if (Chance < 750)
				return new ItemStack(Item.arrow, rand.nextInt(63) + 1);
			break;
		case MetalTrader:
			if (Chance == 0)
				return new ItemStack(mod_Fossil.Gen);
			if (Chance <= 10)
				return new ItemStack(Block.blockDiamond);
			if (Chance < 250)
				return new ItemStack(Block.blockSteel, rand.nextInt(14) + 1);
			if (Chance < 350)
				return new ItemStack(Item.ingotGold, rand.nextInt(31) + 1);

			break;
		case Cargo:
		default:
			if (Chance < 15)
				return new ItemStack(mod_Fossil.blockSkull);
			if (Chance < 100)
				return new ItemStack(Item.egg, rand.nextInt(63) + 1);
			if (Chance < 300)
				return new ItemStack(Item.fishRaw, rand.nextInt(63) + 1);
			if (Chance < 750)
				return new ItemStack(Item.rottenFlesh, rand.nextInt(63) + 1);
		}
		if (Chance == 900)
			return new ItemStack(Item.compass);
		return null;
	}
}
