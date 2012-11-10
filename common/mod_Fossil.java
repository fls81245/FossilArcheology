package net.minecraft.src;
//all java files for minecraft should have this.
import static cpw.mods.fml.common.Side.CLIENT;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.IChatListener;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

//because we are using random values below.
@Mod(modid = "633",name="Fossil/Archeology",version="v7.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class mod_Fossil {

    static {
    	
        PigbossOnEarth = (new Achievement(18000, "PigbossOnEarth", 0,0, new ItemStack(Item.dyePowder, 1, 4), null)).registerAchievement();
    }
    public static int blockRendererID=0;
    public static final String DEFAULT_LANG="en_US";    
    public static String LastLangSetting=DEFAULT_LANG; 
    public static Properties LangProps=new Properties(); 
	private static final File Langdir=new File("/Fossillang/");
    private static File Langfile=new File(Langdir,LastLangSetting+".lang");
    public static FossilGuiHandler GH=new FossilGuiHandler();
    public static Object INSTANCE;
    //public static FossilKeyHandler keyBindingService=new FossilKeyHandler();
    public static boolean DebugMode = true;
    public static IChatListener messagerHandler=new FossilMessageHandler();
    private static int[] BlockIDs = {1137,1138,1139,1140,1141,1142,1143,1144,1145,1146,1147,1148,1149,1151,1152,1153};
    
    private static String[] BlockName = {
        "block Fossil",
        "block Skull",
        "block SkullLantern",
        "block analyzerIdle",
        "block analyzerActive",
        "block cultivateIdle",
        "block cultivateActive",
        "block worktableIdle",
        "block worktableActive",
        "block fern",
        "block fernUpper",
        "block Drum",
        "block FeederIdle",
        "block FeederActive",
        "Block Permafrost",
        "Block IcedStone"
    };
    //110,111 for ferns
    //112 for dumps
    //113,114 for feeder
    private static int[] ItemIDs = {600, 601, 602, 603, 604, 605, 606, 607, 608, 609, 610, 611, 612, 613, 614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 624, 625, 626, 627, 628,629,630,631,632,633,634,635,636};
    private static String[] ItemName = {
        "Item biofossil",
        "Item relic",
        "Item stoneboard",
        "Item DNA",
        "Item Ancientegg",
        "Item AncientSword",
        "Item BrokenSword",
        "Item FernSeed",
        "Item AncientHelmet",
        "Item BrokenHelmet",
        "Item SkullStick",
        "Item Gen",
        "Item GenAxe",
        "Item GenPickaxe",
        "Item GenSword",
        "Item GenHoe",
        "Item GenShovel",
        "Item DinoPedia",
        "Item TRexTooth",
        "Item ToothDagger",
        "Item RawChickenSoup",
        "Item ChickenEss",
        "Item EmptyShell",
        "Item Magic conch",
        "Item SJL",
        "Item RawDinoMeat",
        "Item CookedDinoMeat",
        "Item EmbyoSyringe",
        "Item AnimalDNA",
        "Item IcedMeat",
        "Item WoodJavelin",
        "Item StonrJavelin",
        "Item IronJavelin",
        "Item GoldJavelin",
        "Item DiamondJavelin",
        "Item AncientJavelin",
        "Item Whip"
    };
    //607 for fern seed
    //608 for ancient helmet
    //609 for ancient broken helmet
    //610 for skull stick
    public static int[] FernPics = {38,
        39,
        40,
        41,
        42,
        43,
        26,
        27,
        71, 72, 73, 74, 58};
    public static final int MutiCount = EnumDinoType.values().length * 3 + EnumAnimalType.values().length * 2 - 1+2;
    public static final int BlockCount = BlockIDs.length - 5;
    public static final int skull_side = 65;
    public static final int skull_top = 64;
    public static final int skull_face_off = 48;
    public static final int skull_face_on = 49;
    public static final int dump_side = 4;
    public static final int dump_top_follow = 2;
    public static final int dump_top_freemove = 3;
    public static final int dump_top_stay = 1;
    public static final int feeder_side = 35;
    public static final int feeder_front = 34;
    public static final int feeder_top_on = 19;
    public static final int feeder_top_off = 18;
    public static Block blockFossil;
    public static Block blockSkull;
    public static Block SkullLantern;
    //3.0 contents
    public static Item biofossil;
    public static Item relic;
    public static Item stoneboard;
    public static Item DNA;
    public static Item Ancientegg;
    public static Item AncientSword;
    public static Item BrokenSword;
    public static Item FernSeed;
    public static Item Ancienthelmet;
    public static Item Brokenhelmet;
    public static Block blockanalyzerIdle;
    public static Block blockanalyzerActive;
    public static Block blockcultivateIdle;
    public static Block blockcultivateActive;
    public static Block blockworktableIdle;
    public static Block blockworktableActive;
    public static Block Ferns;
    public static Block FernUpper;
    //4.0 contents
    public static Block Dump;
    public static Block FeederIdle;
    public static Block FeederActive;
    public static Item SkullStick;
    public static Item Gen;
    public static Item GenAxe;
    public static Item GenPickaxe;
    public static Item GenSword;
    public static Item GenHoe;
    public static Item GenShovel;
    public static Item DinoPedia;
    //5.0 contents
    public static Block blockPermafrost;
    public static Block blockIcedStone;
    public static Item ChickenSoup;
    public static Item ChickenEss;
    public static Item EmptyShell;
    public static Item SJL;
    public static Item MagicConch;
    public static Item RawDinoMeat;
    public static Item CookedDinoMeat;
    public static Item EmbyoSyringe;
    public static Item AnimalDNA;
    public static Item IcedMeat;
    public static Item Woodjavelin;
    public static Item Stonejavelin;
    public static Item Ironjavelin;
    public static Item Goldjavelin;
    public static Item Diamondjavelin;
    public static Item AncientJavelin;
    public static Item Whip;
    public static Achievement PigbossOnEarth;
    public static AchievementPage selfArcPage=new AchievementPage("FOSSIL / ARCHEOLOGY",PigbossOnEarth);
    public static final int analyzer_side = 67;
    public static final int analyzer_top = 66;
    public static final int analyzer_on = 51;
    public static final int analyzer_off = 50;
    public static final int cultivate_top = 36;
    public static int cultivate_side_on = 21;
    public static final int cultivate_side_off = 20;
    public static final int worktable_top_on = 17;
    public static final int worktable_top_off = 16;
    public static final int worktable_side1 = 32;
    public static final int worktable_side2 = 33;
	public static final double MESSAGE_DISTANCE = 25.0d;
    public static ItemStack[] SingleTotalList;
    public static ItemStack[] MutiTotalList = new ItemStack[MutiCount];
    public static WorldGenWeaponShopA WeaponShopA=new WorldGenWeaponShopA();
   public static WorldGenShipWreck ShipA=new WorldGenShipWreck();
    public static WorldGenAcademy AcademyA=new WorldGenAcademy();
	private IWorldGenerator FossilGenerator=new FossilGenerator();
    public mod_Fossil() {
    }

    public static void fillCreativeList() {
        int i, j, pt = 0;
        for (i = 0; i < EnumDinoType.values().length; i++) {
            MutiTotalList[pt++] = new ItemStack(DNA, 1, i);
        }
        for (i = 0; i < EnumDinoType.values().length; i++) {
            MutiTotalList[pt++] = new ItemStack(Ancientegg, 1, i);
        }
        for (i = 0; i < EnumDinoType.values().length; i++) {
            MutiTotalList[pt++] = new ItemStack(RawDinoMeat, 1, i);
        }
        for (j = 0; j < EnumAnimalType.values().length; j++) {
            MutiTotalList[pt++] = new ItemStack(AnimalDNA, 1, j);
        }
        for (j = 0; j < EnumEmbyos.values().length; j++) {
            MutiTotalList[pt++] = new ItemStack(EmbyoSyringe, 1, j);
        }
        for (j = 0; j < 2; j++) {
            MutiTotalList[pt++] = new ItemStack(ChickenSoup, 1, j);
        }
        SingleTotalList = new ItemStack[]{
                new ItemStack(blockFossil, 1, 0),
                new ItemStack(blockSkull, 1, 0),
                new ItemStack(SkullLantern, 1, 0),
                new ItemStack(blockanalyzerIdle, 1, 0),
                new ItemStack(blockcultivateIdle, 1, 0),
                new ItemStack(blockworktableIdle, 1, 0),
                new ItemStack(Ferns, 1, 0),
                new ItemStack(Dump, 1, 0),
                new ItemStack(FeederIdle, 1, 0),
                new ItemStack(blockPermafrost, 1, 0),
                new ItemStack(blockIcedStone, 1, 0),};
    }

    public String getVersion() //every mod for modloader need this function,too.
    //or modloader will refuse to load this mod.
    {
        return "v6.8 Patch-1";
    }
    @SideOnly(CLIENT)
    public void registingRenderer(){  
    	RenderingRegistry.instance().registerEntityRenderingHandler(EntityStoneboard.class, new RenderStoneboard());
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityTriceratops.class, new RenderTriceratops(new ModelTriceratops(), new ModelTriceratops(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityRaptor.class, new RenderRaptor(new ModelRaptor(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityTRex.class, new RenderTRex(new ModelTRex(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityFailuresaurus.class, new RenderFailuresaurus(new ModelFailuresaurus(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityPigBoss.class, new RenderPigBoss(new ModelPigBoss(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityFriendlyPigZombie.class, new RenderBiped(new ModelZombie(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityPterosaur.class, new RenderPterosaur(new ModelPterosaurGround(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityNautilus.class, new RenderNautilus(new ModelNautilus(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityPlesiosaur.class, new RenderPlesiosaur(0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityMosasaurus.class, new RenderMosasaurus(0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityStegosaurus.class, new RenderStegosaurus(new ModelStegosaurus(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityDinoEgg.class, new RenderDinoEgg(1.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityPregnantPig.class, new RenderPig(new ModelPig(), new ModelPig(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(Entitydil.class, new Renderdil(new Modeldil(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntitySaberCat.class, new RenderSaberCat(new ModelSaberCat(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityJavelin.class, new RenderJavelin());
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityAncientJavelin.class, new RenderJavelin());
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityBones.class, new RenderBiped(new ModelBiped(),0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityBrachiosaurus.class, new RenderBrachiosaurus(0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(EntityMammoth.class, new RenderMammoth(new ModelMammoth(), 0.5F));
    }
    @SideOnly(CLIENT)
    public void addRenderer(Map map) {
    	RenderingRegistry.instance().registerBlockHandler(new FossilBlockRenderHandler());
    	
        map.put(EntityStoneboard.class, new RenderStoneboard());
        map.put(EntityTriceratops.class, new RenderTriceratops(new ModelTriceratops(), new ModelTriceratops(), 0.5F));
        map.put(EntityRaptor.class, new RenderRaptor(new ModelRaptor(), 0.5F));
        map.put(EntityTRex.class, new RenderTRex(new ModelTRex(), 0.5F));
        map.put(EntityFailuresaurus.class, new RenderFailuresaurus(new ModelFailuresaurus(), 0.5F));
        map.put(EntityPigBoss.class, new RenderPigBoss(new ModelPigBoss(), 0.5F));
        map.put(EntityFriendlyPigZombie.class, new RenderBiped(new ModelZombie(), 0.5F));
        map.put(EntityPterosaur.class, new RenderPterosaur(new ModelPterosaurGround(), 0.5F));
        map.put(EntityNautilus.class, new RenderNautilus(new ModelNautilus(), 0.5F));
        map.put(EntityPlesiosaur.class, new RenderPlesiosaur(0.5F));
        map.put(EntityMosasaurus.class, new RenderMosasaurus(0.5F));
        map.put(EntityStegosaurus.class, new RenderStegosaurus(new ModelStegosaurus(), 0.5F));
        map.put(EntityDinoEgg.class, new RenderDinoEgg(1.5F));
        map.put(EntityPregnantPig.class, new RenderPig(new ModelPig(), new ModelPig(), 0.5F));
        map.put(Entitydil.class, new Renderdil(new Modeldil(), 0.5F));
        map.put(EntitySaberCat.class, new RenderSaberCat(new ModelSaberCat(), 0.5F));
        map.put(EntityJavelin.class, new RenderJavelin());
        map.put(EntityAncientJavelin.class, new RenderJavelin());
        map.put(EntityBones.class, new RenderBiped(new ModelBiped(),0.5F));
        map.put(EntityBrachiosaurus.class, new RenderBrachiosaurus(0.5F));
        map.put(EntityMammoth.class, new RenderMammoth(new ModelMammoth(), 0.5F));
    }

    /*public void generateSurface(World world, Random rand, int chunkX, int chunkZ) //this override function can make the custom block "blockFossil" can be added in new chunks automatically.
    {
        for (int i = 0; i < (20); i++) 
        {
            int randPosX = chunkX + rand.nextInt(16);

            int randPosY = rand.nextInt(50);

            int randPosZ = chunkZ + rand.nextInt(16);

            int OreSelection=rand.nextInt(100);
            if (OreSelection>50) 
            	(new WorldGenMinable(blockFossil.blockID, 10)).generate(world, rand, randPosX, rand.nextInt(20)+40, randPosZ);
            else (new WorldGenMinable(blockPermafrost.blockID, 5)).generate(world, rand, randPosX, randPosY, randPosZ);
            if(!FossilOptions.SpawnShipwrecks || !ShipA.generate(world, rand, chunkX, 60, chunkZ))
            	if (!FossilOptions.SpawnAcademy || !AcademyA.generate(world, rand, chunkX, 50+rand.nextInt(20), chunkZ))
            		if (FossilOptions.SpawnWeaponShop) WeaponShopA.generate(world, rand, chunkX, 50, chunkZ);
            
        }
    }*/
    private void SetupOptions(){
        boolean OldFileDetected = false;
        Properties props;
		try {
			props = FossilCfgLoader.loadOptionConfig();
	        Properties Storeback = new Properties();
	        if (props!=null){
		        if (props.containsKey("AnuSpawn") && !OldFileDetected) {
		        	String SpawnTmp=props.getProperty("AnuSpawn");
		        	if (FossilOptions.isNegtiveWord(SpawnTmp)) FossilOptions.ShouldAnuSpawn=false;                	
		        }
		        if (props.containsKey("SpawnShipWrecks") && !OldFileDetected) {
		        	String SpawnTmp=props.getProperty("SpawnShipWrecks");
		        	if (FossilOptions.isNegtiveWord(SpawnTmp)) FossilOptions.SpawnShipwrecks=false;                	
		        }
		        if (props.containsKey("SpawnWeaponShop") && !OldFileDetected) {
		        	String SpawnTmp=props.getProperty("SpawnWeaponShop");
		        	if (FossilOptions.isNegtiveWord(SpawnTmp)) FossilOptions.SpawnWeaponShop=false;                	
		        }
		        if (props.containsKey("SpawnAcademy") && !OldFileDetected) {
		        	String SpawnTmp=props.getProperty("SpawnAcademy");
		        	if (FossilOptions.isNegtiveWord(SpawnTmp)) FossilOptions.SpawnAcademy=false;                	
		        }
		        if (props.containsKey("DinoGrows") && !OldFileDetected) {
		        	String SpawnTmp=props.getProperty("DinoGrows");
		        	if (FossilOptions.isNegtiveWord(SpawnTmp)) FossilOptions.DinoGrows=false;                	
		        }
		        if (props.containsKey("DinoHunger") && !OldFileDetected) {
		        	String SpawnTmp=props.getProperty("DinoHunger");
		        	if (FossilOptions.isNegtiveWord(SpawnTmp)) FossilOptions.DinoHunger=false;                	
		        }
		        if (props.containsKey("TRexBreakingBlocks") && !OldFileDetected) {
		        	String SpawnTmp=props.getProperty("TRexBreakingBlocks");
		        	if (FossilOptions.isNegtiveWord(SpawnTmp)) FossilOptions.TRexBreakingBlocks=false;                	
		        }
		        if (props.containsKey("BraBreakingBlocks") && !OldFileDetected) {
		        	String SpawnTmp=props.getProperty("BraBreakingBlocks");
		        	if (FossilOptions.isNegtiveWord(SpawnTmp)) FossilOptions.BraBreakingBlocks=false;                	
		        }
	        }
	        
            Storeback.setProperty("AnuSpawn", (FossilOptions.ShouldAnuSpawn?"true":"false"));
            Storeback.setProperty("SpawnShipWrecks", (FossilOptions.SpawnShipwrecks?"true":"false"));
            Storeback.setProperty("SpawnWeaponShop", (FossilOptions.SpawnWeaponShop?"true":"false"));
            Storeback.setProperty("SpawnAcademy", (FossilOptions.SpawnAcademy?"true":"false"));
            Storeback.setProperty("DinoGrows", (FossilOptions.DinoGrows?"true":"false"));
            Storeback.setProperty("DinoHunger", (FossilOptions.DinoHunger?"true":"false"));
            Storeback.setProperty("TRexBreakingBlocks", (FossilOptions.TRexBreakingBlocks?"true":"false"));
            Storeback.setProperty("BraBreakingBlocks", (FossilOptions.BraBreakingBlocks?"true":"false"));
            FossilCfgLoader.saveOptionConfig(Storeback);
		} catch (IOException e) {
			return;
		}

    }
    private void SetupID() {
        try {
            boolean OldFileDetected = false;
            Properties props = FossilCfgLoader.loadIDConfig();
            Properties Storeback = new Properties();
            if (props != null) {
                for (int i = 0; i < BlockIDs.length; i++) {
                    if (props.containsKey(BlockName[i]) && !OldFileDetected) {
                        int temp = Integer.parseInt(props.getProperty(BlockName[i]));
                        if (temp <= 153) {
                        	System.out.println("Old Cfg file detected!ignore custom ID setting");
                            OldFileDetected = true;
                        } else {
                            BlockIDs[i] = temp;
                        }
                    }
                    Storeback.setProperty(BlockName[i], Integer.toString(BlockIDs[i]));
                }
                for (int i = 0; i < ItemIDs.length; i++) {
                    if (props.containsKey(ItemName[i]) && !OldFileDetected) {
                        ItemIDs[i] = Integer.parseInt(props.getProperty(ItemName[i]));
                    }
                    Storeback.setProperty(ItemName[i], Integer.toString(ItemIDs[i]));
                }

                //if (props.containsKey("Mode Debug") && !OldFileDetected) DebugMode=Boolean.getBoolean(props.getProperty("Mode Debug"));
                //Storeback.setProperty("Mode Debug", Boolean.toString(DebugMode));
                System.out.println(new StringBuilder().append("Anu Spawn:").append(FossilOptions.ShouldAnuSpawn ? "Engaged" : "Disabled"));
                System.out.println(new StringBuilder().append("Debug Mode:").append(DebugMode ? "Engaged" : "Disabled"));
            }
            FossilCfgLoader.saveIDConfig(Storeback);
        } catch (Throwable throwable) {
            return;
        }
    }

    /*public static void ShowMessage(String s) {
    	if(FMLCommonHandler.instance().getSide().isClient()){
            ModLoader.getMinecraftInstance().ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(s,0);    		
    	}

    }*/
    public static void ShowMessage(String s,EntityPlayer targetPlayer) {
    	if (targetPlayer==null) return;
    	targetPlayer.addChatMessage(s);
    }

    public static void DebugMessage(String s) {
        //logger.fine((new StringBuilder(s);
        if (DebugMode) {
            System.out.println(s);
        }
    }

    public static int EnumToInt(EnumOrderType input) {
        return input.ToInt();
    }
    private void registBlocks(){
    	 GameRegistry.registerBlock(blockFossil);
         GameRegistry.registerBlock(blockSkull);
         GameRegistry.registerBlock(SkullLantern);
         GameRegistry.registerBlock(blockanalyzerIdle);
         GameRegistry.registerBlock(blockanalyzerActive);
         GameRegistry.registerBlock(blockcultivateIdle);
         GameRegistry.registerBlock(blockcultivateActive);
         GameRegistry.registerBlock(blockworktableIdle);
         GameRegistry.registerBlock(blockworktableActive);
         GameRegistry.registerBlock(Ferns);
         GameRegistry.registerBlock(FernUpper);
         GameRegistry.registerBlock(Dump);
         GameRegistry.registerBlock(FeederIdle);
         GameRegistry.registerBlock(FeederActive);
         GameRegistry.registerBlock(blockPermafrost);
         GameRegistry.registerBlock(blockIcedStone);
    }
    private void initBlockAndItems() throws IDException{
    	IDException preChkResult = new IDException().setLoc(FossilCfgLoader.IDcfgfile);
    	for (int i=0;i<BlockIDs.length;i++){
    		if (BlockIDs[i]>=Block.blocksList.length || Block.blocksList[BlockIDs[i]]!=null) 
    			preChkResult.add(BlockIDs[i]);
    	}
    	for (int i=0;i<ItemIDs.length;i++){
    		if (ItemIDs[i]>=Item.itemsList.length || Item.itemsList[ItemIDs[i]]!=null) preChkResult.add(ItemIDs[i]);
    	}
    	if (preChkResult.IsConflicted()) throw preChkResult;
    	blockFossil = new BlockFossil(BlockIDs[0], 1).setHardness(3F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setBlockName("fossil").setCreativeTab(CreativeTabs.tabBlock);
        blockSkull = new BlockFossilSkull(BlockIDs[1], 0, false).setHardness(1.0F).setStepSound(Block.soundStoneFootstep).setBlockName("Skull").setCreativeTab(CreativeTabs.tabBlock);
        SkullLantern = new BlockFossilSkull(BlockIDs[2], 0, true).setHardness(1.0F).setLightValue(0.9375F).setStepSound(Block.soundStoneFootstep).setBlockName("SkullLantern").setCreativeTab(CreativeTabs.tabBlock);
        blockanalyzerIdle = new BlockAnalyzer(BlockIDs[3], false).setHardness(3F).setStepSound(Block.soundMetalFootstep).setBlockName("analyzerIdle").setCreativeTab(CreativeTabs.tabDecorations);
        blockanalyzerActive = new BlockAnalyzer(BlockIDs[4], true).setLightValue(0.9375F).setHardness(3F).setStepSound(Block.soundMetalFootstep).setBlockName("analyzerActive");
        blockcultivateIdle = new BlockCultivate(BlockIDs[5], false).setLightValue(0.9375F).setHardness(0.3F).setStepSound(Block.soundGlassFootstep).setBlockName("cultivateIdle").setCreativeTab(CreativeTabs.tabDecorations);
        blockcultivateActive = new BlockCultivate(BlockIDs[6], true).setLightValue(0.9375F).setHardness(0.3F).setStepSound(Block.soundGlassFootstep).setBlockName("cultivateActive");
        blockworktableIdle = new BlockWorktable(BlockIDs[7], false).setHardness(2.5F).setStepSound(Block.soundWoodFootstep).setBlockName("worktableIdle").setCreativeTab(CreativeTabs.tabDecorations);
        blockworktableActive = new BlockWorktable(BlockIDs[8], true).setHardness(2.5F).setStepSound(Block.soundWoodFootstep).setBlockName("worktableActive");
        Ferns = new BlockFern(BlockIDs[9], 0, false).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).disableStats().setRequiresSelfNotify().setCreativeTab(null);
        FernUpper = new BlockFern(BlockIDs[10], 0, true).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).disableStats().setRequiresSelfNotify().setCreativeTab(null);
        Dump = new BlockDrum(BlockIDs[11]).setHardness(0.8F).setBlockName("drum").setRequiresSelfNotify().setCreativeTab(CreativeTabs.tabDecorations);
        FeederIdle = new BlockFeeder(BlockIDs[12], false).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setBlockName("Feeder").setRequiresSelfNotify().setCreativeTab(CreativeTabs.tabDecorations);
        FeederActive = new BlockFeeder(BlockIDs[13], false).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setBlockName("Feeder").setRequiresSelfNotify();
        biofossil = new ItemBioFossil(ItemIDs[0]).setItemName("biofossil").setCreativeTab(CreativeTabs.tabMisc);
        relic = new ItemRelic(ItemIDs[1]).setItemName("relic").setCreativeTab(CreativeTabs.tabMisc);
        stoneboard = new ItemStoneBoard(ItemIDs[2]).setItemName("stoneboard").setCreativeTab(CreativeTabs.tabDecorations);
        DNA = new ItemDNA(ItemIDs[3]).setItemName("DNA").setCreativeTab(CreativeTabs.tabMaterials);
        Ancientegg = new ItemAncientEgg(ItemIDs[4]).setItemName("TriceratopsEgg").setCreativeTab(CreativeTabs.tabMaterials);
        AncientSword = new ItemAncientsword(ItemIDs[5]).setItemName("ancientsword").setCreativeTab(CreativeTabs.tabCombat);
        BrokenSword = new ItemBrokenSword(ItemIDs[6]).setItemName("Brokensword").setCreativeTab(CreativeTabs.tabMaterials);
        FernSeed = new ItemSeeds(ItemIDs[7], Ferns.blockID,Block.grass.blockID).setItemName("FernSeed").setCreativeTab(CreativeTabs.tabMaterials);
        FernSeed.setTextureFile("/skull/Fos_items.png");
        if(FMLCommonHandler.instance().getSide().isClient()){
        	Ancienthelmet = new ForgeItemArmor(ItemIDs[8], EnumArmorMaterial.IRON, ModLoader.addArmor("Ancient"), 0).setItemName("ancientHelmet").setCreativeTab(CreativeTabs.tabCombat);
        }else{
        	Ancienthelmet = new ForgeItemArmor(ItemIDs[8], EnumArmorMaterial.IRON, 0, 0).setItemName("ancientHelmet").setCreativeTab(CreativeTabs.tabCombat);
        }
        Brokenhelmet = new ItemBrokenHelmet(ItemIDs[9]).setItemName("BrokenHelmet").setCreativeTab(CreativeTabs.tabMaterials);
        SkullStick = new ForgeItem(ItemIDs[10]).setItemName("SkullStick").setCreativeTab(CreativeTabs.tabMisc);
        Gen = new ItemGen(ItemIDs[11]).setItemName("Gen").setCreativeTab(CreativeTabs.tabMisc);
        GenAxe = new ForgeItemAxe(ItemIDs[12], EnumToolMaterial.EMERALD).setItemName("GenAxe");
        GenPickaxe = new ForgeItemPickaxe(ItemIDs[13], EnumToolMaterial.EMERALD).setItemName("GenPickaxe");
        GenSword = new ForgeItemSword(ItemIDs[14], EnumToolMaterial.EMERALD).setItemName("GenSword");
        GenHoe = new ForgeItemHoe(ItemIDs[15], EnumToolMaterial.EMERALD).setItemName("GenHoe");
        GenShovel = new ForgeItemSpade(ItemIDs[16], EnumToolMaterial.EMERALD).setItemName("GenShovel");

        DinoPedia = new ForgeItem(ItemIDs[17]).setItemName("dinopedia").setCreativeTab(CreativeTabs.tabTools);
        ChickenSoup = new ItemChickenSoup(ItemIDs[20]).setItemName("ChickenSoup").setMaxStackSize(1).setContainerItem(Item.bucketEmpty).setCreativeTab(CreativeTabs.tabFood);
        
        ChickenEss = new ForgeItemFood(ItemIDs[21], 10, 0, false).setItemName("ChickenEss").setContainerItem(Item.glassBottle).setCreativeTab(CreativeTabs.tabFood);
        EmptyShell = new ForgeItem(ItemIDs[22]).setItemName("EmptyShell").setCreativeTab(CreativeTabs.tabMisc);
        SJL = new ForgeItemFood(ItemIDs[23], 8, 2.0F, false).setItemName("SioChiuLe").setCreativeTab(CreativeTabs.tabFood);
        MagicConch = new ItemMagicConch(ItemIDs[24]).setItemName("MagicConch").setCreativeTab(CreativeTabs.tabTools);
        RawDinoMeat = new ItemDinoMeat(ItemIDs[25], 3, 0.3F, true).setItemName("DinoMeat").setCreativeTab(CreativeTabs.tabFood);
        CookedDinoMeat = new ForgeItemFood(ItemIDs[26], 8, 0.8F, true).setItemName("CookedDinoMeat").setCreativeTab(CreativeTabs.tabFood);
        EmbyoSyringe = new ItemEmbryoSyringe(ItemIDs[27]).setItemName("EmbryoSyringe").setCreativeTab(CreativeTabs.tabMisc);
        AnimalDNA = new ItemNonDinoDNA(ItemIDs[28]).setItemName("AnimalDNA").setCreativeTab(CreativeTabs.tabMisc);
        IcedMeat=new ItemIcedMeat(ItemIDs[29], EnumToolMaterial.EMERALD).setItemName("IcedMeat").setCreativeTab(CreativeTabs.tabCombat);
        Woodjavelin=new ItemJavelin(ItemIDs[30],EnumToolMaterial.WOOD).setItemName("WoodJavelin").setCreativeTab(CreativeTabs.tabCombat);
        Stonejavelin=new ItemJavelin(ItemIDs[31],EnumToolMaterial.STONE).setItemName("StoneJavelin").setCreativeTab(CreativeTabs.tabCombat);
        Ironjavelin=new ItemJavelin(ItemIDs[32],EnumToolMaterial.IRON).setItemName("IronJavelin").setCreativeTab(CreativeTabs.tabCombat);
        Goldjavelin=new ItemJavelin(ItemIDs[33],EnumToolMaterial.GOLD).setItemName("GoldJavelin").setCreativeTab(CreativeTabs.tabCombat);
        Diamondjavelin=new ItemJavelin(ItemIDs[34],EnumToolMaterial.EMERALD).setItemName("DiamondJavelin").setCreativeTab(CreativeTabs.tabCombat);
        AncientJavelin= ((ItemJavelin) new ItemJavelin(ItemIDs[35],EnumToolMaterial.IRON).setItemName("AncientJavelin")).setAncient(true).setCreativeTab(CreativeTabs.tabCombat);
        Whip=new ItemWhip(ItemIDs[36]).setItemName("FossilWhip").setCreativeTab(CreativeTabs.tabTools);
        blockPermafrost = new BlockPermafrost(BlockIDs[14], 5).setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundGrassFootstep).setBlockName("Permafrost").setRequiresSelfNotify().setCreativeTab(CreativeTabs.tabBlock);
        blockIcedStone = new BlockIcedStone(BlockIDs[15], 6).setHardness(1.5F).setResistance(10F).setStepSound(Block.soundStoneFootstep).setBlockName("IcedStone").setRequiresSelfNotify();
    }
    @SideOnly(CLIENT)
    private void forgeTextureSetUp(){
    	if(FMLCommonHandler.instance().getSide().isServer()) return;
        MinecraftForgeClient.preloadTexture("/skull/Fos_terrian.png");
        MinecraftForgeClient.preloadTexture("/skull/Fos_items.png");
        MinecraftForgeClient.preloadTexture("/skull/needle.png");
    }
    private void forgeHarvestLevelSetUp(){
        MinecraftForge.setBlockHarvestLevel(blockFossil, 0, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(blockPermafrost, 0, "shovel", 2);
        MinecraftForge.setBlockHarvestLevel(blockIcedStone, 0, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(blockIcedStone, 1, "pickaxe", 1);
    }
    private void registTileEntitys(){
        GameRegistry.registerTileEntity(net.minecraft.src.TileEntityCultivate.class, "Cultivate");
        GameRegistry.registerTileEntity(net.minecraft.src.TileEntityAnalyzer.class, "Analyzer");
        GameRegistry.registerTileEntity(net.minecraft.src.TileEntityWorktable.class, "Worktable");
        GameRegistry.registerTileEntity(net.minecraft.src.TileEntityDrum.class, "Dump");
        GameRegistry.registerTileEntity(net.minecraft.src.TileEntityFeeder.class, "Feeder");
    }
    private void registEntitys(){
    	int modEntityID=1;
    	EntityRegistry.registerModEntity(EntityStoneboard.class, "StoneBoard", modEntityID++,this,250,5,false);
    	EntityRegistry.registerModEntity(EntityJavelin.class, "Javelin", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityAncientJavelin.class, "AncientJavelin", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityMLighting.class, "FriendlyLighting", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityFailuresaurus.class, "Failuresaurus", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityBones.class, "Bones", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityNautilus.class, "Nautilus", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityDinoEgg.class, "DinoEgg", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityRaptor.class, "Raptor", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityTriceratops.class, "Triceratops", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityTRex.class, "Tyrannosaurus", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityFriendlyPigZombie.class, "FriendlyPigZombie", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityPigBoss.class, "PigBoss", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityPterosaur.class, "Pterosaur", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityPlesiosaur.class, "Plesiosaur", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityMosasaurus.class, "Mosasaurus", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityStegosaurus.class, "Stegosaurus", modEntityID++,this,250,5,true);

        EntityRegistry.registerModEntity(Entitydil.class, "Utahraptor", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityPregnantSheep.class, "PregnantSheep", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityPregnantCow.class, "PregnantCow", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityPregnantPig.class, "PregnantPig", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntitySaberCat.class, "SaberCat", modEntityID++,this,250,5,true);

        EntityRegistry.registerModEntity(EntityBrachiosaurus.class, "Brachiosaurus", modEntityID++,this,250,5,true);
        EntityRegistry.registerModEntity(EntityMammoth.class, "Mammoth", modEntityID++,this,250,5,true);
    	
    	/*EntityRegistry.registerGlobalEntityID(EntityStoneboard.class, "StoneBoard", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityTriceratops.class, "Triceratops", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityRaptor.class, "Raptor", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityTRex.class, "Tyrannosaurus", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityFriendlyPigZombie.class, "FriendlyPigZombie", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityFailuresaurus.class, "Failuresaurus", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityPigBoss.class, "PigBoss", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityPterosaur.class, "Pterosaur", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityNautilus.class, "Nautilus", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityPlesiosaur.class, "Plesiosaur", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityMosasaurus.class, "Mosasaurus", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityStegosaurus.class, "Stegosaurus", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityDinoEgg.class, "DinoEgg", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityMLighting.class, "FriendlyLighting", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(Entitydil.class, "Utahraptor", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityPregnantSheep.class, "PregnantSheep", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityPregnantCow.class, "PregnantCow", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityPregnantPig.class, "PregnantPig", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntitySaberCat.class, "SaberCat", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityJavelin.class, "Javelin", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityAncientJavelin.class, "AncientJavelin", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityBones.class, "Bones", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityBrachiosaurus.class, "Brachiosaurus", ModLoader.getUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntityMammoth.class, "Mammoth", ModLoader.getUniqueEntityId());*/
    }
    private void iconIndexSetup(){
    	 biofossil.iconIndex = 38;
         relic.iconIndex = 39;
         stoneboard.iconIndex = 44;
         BrokenSword.iconIndex = 0;
         AncientSword.iconIndex = 1;
         FernSeed.iconIndex = 40;
         Brokenhelmet.iconIndex = 2;
         Ancienthelmet.iconIndex = 3;
         SkullStick.iconIndex = 5;
         Gen.iconIndex = 4;
         GenAxe.iconIndex = 19;
         GenPickaxe.iconIndex = 18;
         GenSword.iconIndex = 16;
         GenHoe.iconIndex = 20;
         GenShovel.iconIndex = 17;

         DinoPedia.iconIndex = 53;
         ChickenSoup.iconIndex = 58;
         ChickenEss.iconIndex = 59;
         EmptyShell.iconIndex = 42;
         SJL.iconIndex = 43;
         MagicConch.iconIndex = 21;
         CookedDinoMeat.iconIndex = 56;
         EmbyoSyringe.iconIndex = 0;
         IcedMeat.iconIndex=57;
    }
    private void testingReciptSetup(){
        GameRegistry.addRecipe(new ItemStack(this.AncientSword, 1), new Object[]{
        "X", Character.valueOf('X'), Block.dirt });
       
        GameRegistry.addRecipe(new ItemStack(this.Ancienthelmet,1), new
        Object[] { "X",Character.valueOf('X'), Block.sand });
       
       
        GameRegistry.addRecipe(new ItemStack(FeederIdle, 64), new Object[] {
        "XY",Character.valueOf('X'), Block.sand,Character.valueOf('Y'),
        Block.dirt
		});
    }
    private void reciptsSetup(){
    	GameRegistry.addRecipe(new ItemStack(SkullLantern, 1), new Object[]{
            "X", "Y", Character.valueOf('X'), blockSkull, Character.valueOf('Y'), Block.torchWood
        });
GameRegistry.addRecipe(new ItemStack(Item.dyePowder, 5, 15), new Object[]{
            "X", Character.valueOf('X'), blockSkull
        });
GameRegistry.addRecipe(new ItemStack(Item.dyePowder, 5, 15), new Object[]{
            "X", Character.valueOf('X'), SkullLantern
        });
//3.0 Contects
GameRegistry.addRecipe(new ItemStack(blockcultivateIdle, 1), new Object[]{
            "XYX", "XWX", "ZZZ", Character.valueOf('X'), Block.glass, Character.valueOf('Y'), new ItemStack(Item.dyePowder, 1, 2), Character.valueOf('W'), Item.bucketWater, Character.valueOf('Z'), Item.ingotIron
        });
GameRegistry.addRecipe(new ItemStack(blockanalyzerIdle, 1), new Object[]{
            "XYX", "XWX", Character.valueOf('X'), Item.ingotIron, Character.valueOf('Y'), relic, Character.valueOf('W'), biofossil
        });
GameRegistry.addRecipe(new ItemStack(blockworktableIdle, 1), new Object[]{
            "X", "Y", Character.valueOf('X'), Item.paper, Character.valueOf('Y'), Block.workbench
        });
GameRegistry.addRecipe(new ItemStack(Item.cake, 1), new Object[]{
            "AAA", "BEB", "CCC", Character.valueOf('A'), Item.bucketMilk, Character.valueOf('B'), Item.sugar, Character.valueOf('C'), Item.wheat, Character.valueOf('E'), new ItemStack(Ancientegg, 1, 0)
        });
GameRegistry.addRecipe(new ItemStack(Item.cake, 1), new Object[]{
            "AAA", "BEB", "CCC", Character.valueOf('A'), Item.bucketMilk, Character.valueOf('B'), Item.sugar, Character.valueOf('C'), Item.wheat, Character.valueOf('E'), new ItemStack(Ancientegg, 1, 1)
        });
GameRegistry.addRecipe(new ItemStack(Item.cake, 1), new Object[]{
            "AAA", "BEB", "CCC", Character.valueOf('A'), Item.bucketMilk, Character.valueOf('B'), Item.sugar, Character.valueOf('C'), Item.wheat, Character.valueOf('E'), new ItemStack(Ancientegg, 1, 2)
        });
GameRegistry.addRecipe(new ItemStack(Item.cake, 1), new Object[]{
            "AAA", "BEB", "CCC", Character.valueOf('A'), Item.bucketMilk, Character.valueOf('B'), Item.sugar, Character.valueOf('C'), Item.wheat, Character.valueOf('E'), new ItemStack(Ancientegg, 1, 3)
        });
GameRegistry.addRecipe(new ItemStack(Item.cake, 1), new Object[]{
            "AAA", "BEB", "CCC", Character.valueOf('A'), Item.bucketMilk, Character.valueOf('B'), Item.sugar, Character.valueOf('C'), Item.wheat, Character.valueOf('E'), new ItemStack(Ancientegg, 1, 5)
        });
GameRegistry.addRecipe(new ItemStack(Item.cake, 1), new Object[]{
            "AAA", "BEB", "CCC", Character.valueOf('A'), Item.bucketMilk, Character.valueOf('B'), Item.sugar, Character.valueOf('C'), Item.wheat, Character.valueOf('E'), new ItemStack(Ancientegg, 1, 6)
        });
GameRegistry.addRecipe(new ItemStack(Item.cake, 1), new Object[]{
            "AAA", "BEB", "CCC", Character.valueOf('A'), Item.bucketMilk, Character.valueOf('B'), Item.sugar, Character.valueOf('C'), Item.wheat, Character.valueOf('E'), new ItemStack(Ancientegg, 1, 7)
        });
GameRegistry.addRecipe(new ItemStack(Item.cake, 1), new Object[]{
            "AAA", "BEB", "CCC", Character.valueOf('A'), Item.bucketMilk, Character.valueOf('B'), Item.sugar, Character.valueOf('C'), Item.wheat, Character.valueOf('E'), new ItemStack(Ancientegg, 1, 8)
        });
//4.0 contents
GameRegistry.addRecipe(new ItemStack(SkullStick, 1), new Object[]{
            "X", "Y", Character.valueOf('X'), blockSkull, Character.valueOf('Y'), Item.stick
        });
GameRegistry.addRecipe(new ItemStack(Dump, 1), new Object[]{
            "ZZZ", "XYX", "XXX", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.redstone, Character.valueOf('Z'), Item.leather
        });
GameRegistry.addRecipe(new ItemStack(FeederIdle, 1), new Object[]{
            "XYX", "ZAB", "BBB", Character.valueOf('X'), Item.ingotIron, Character.valueOf('Y'), Block.glass, Character.valueOf('Z'), Block.button, Character.valueOf('A'), Item.bucketEmpty, Character.valueOf('B'), Block.stone
        });
ModLoader.addShapelessRecipe(new ItemStack(GenAxe), new Object[]{
            Item.axeWood, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenAxe), new Object[]{
            Item.axeStone, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenAxe), new Object[]{
            Item.axeSteel, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenAxe), new Object[]{
            Item.axeGold, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenAxe), new Object[]{
            Item.axeDiamond, Gen
        });


ModLoader.addShapelessRecipe(new ItemStack(GenPickaxe), new Object[]{
            Item.pickaxeWood, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenPickaxe), new Object[]{
            Item.pickaxeStone, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenPickaxe), new Object[]{
            Item.pickaxeSteel, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenPickaxe), new Object[]{
            Item.pickaxeGold, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenPickaxe), new Object[]{
            Item.pickaxeDiamond, Gen
        });


ModLoader.addShapelessRecipe(new ItemStack(GenHoe), new Object[]{
            Item.hoeWood, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenHoe), new Object[]{
            Item.hoeStone, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenHoe), new Object[]{
            Item.hoeSteel, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenHoe), new Object[]{
            Item.hoeGold, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenHoe), new Object[]{
            Item.hoeDiamond, Gen
        });


ModLoader.addShapelessRecipe(new ItemStack(GenSword), new Object[]{
            Item.swordWood, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenSword), new Object[]{
            Item.swordStone, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenSword), new Object[]{
            Item.swordSteel, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenSword), new Object[]{
            Item.swordGold, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenSword), new Object[]{
            Item.swordDiamond, Gen
        });

ModLoader.addShapelessRecipe(new ItemStack(GenShovel), new Object[]{
            Item.shovelWood, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenShovel), new Object[]{
            Item.shovelStone, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenShovel), new Object[]{
            Item.shovelSteel, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenShovel), new Object[]{
            Item.shovelGold, Gen
        });
ModLoader.addShapelessRecipe(new ItemStack(GenShovel), new Object[]{
            Item.shovelDiamond, Gen
        });

ModLoader.addShapelessRecipe(new ItemStack(DinoPedia), new Object[]{
            Item.book, new ItemStack(DNA, 1, 0)
        });
ModLoader.addShapelessRecipe(new ItemStack(DinoPedia), new Object[]{
            Item.book, new ItemStack(DNA, 1, 1)
        });
ModLoader.addShapelessRecipe(new ItemStack(DinoPedia), new Object[]{
            Item.book, new ItemStack(DNA, 1, 2)
        });
ModLoader.addShapelessRecipe(new ItemStack(DinoPedia), new Object[]{
            Item.book, new ItemStack(DNA, 1, 3)
        });
ModLoader.addShapelessRecipe(new ItemStack(DinoPedia), new Object[]{
            Item.book, new ItemStack(DNA, 1, 4)
        });
ModLoader.addShapelessRecipe(new ItemStack(DinoPedia), new Object[]{
            Item.book, new ItemStack(DNA, 1, 5)
        });
ModLoader.addShapelessRecipe(new ItemStack(DinoPedia), new Object[]{
            Item.book, new ItemStack(DNA, 1, 6)
        });
ModLoader.addShapelessRecipe(new ItemStack(DinoPedia), new Object[]{
            Item.book, new ItemStack(DNA, 1, 7)
        });
ModLoader.addShapelessRecipe(new ItemStack(DinoPedia), new Object[]{
            Item.book, new ItemStack(DNA, 1, 8)
        });
ModLoader.addShapelessRecipe(new ItemStack(ChickenSoup,1,0), new Object[]{
            Item.bucketEmpty, Item.chickenRaw
        });
ModLoader.addShapelessRecipe(new ItemStack(MagicConch, 1, 1), new Object[]{
            new ItemStack(MagicConch, 1, 0)
        });
ModLoader.addShapelessRecipe(new ItemStack(MagicConch, 1, 2), new Object[]{
            new ItemStack(MagicConch, 1, 1)
        });
ModLoader.addShapelessRecipe(new ItemStack(MagicConch, 1, 0), new Object[]{
            new ItemStack(MagicConch, 1, 2)
        });
GameRegistry.addRecipe(new ItemStack(ChickenEss, 8), new Object[]{
    "XXX", "XYX", "XXX", Character.valueOf('X'), Item.glassBottle, Character.valueOf('Y'), new ItemStack(ChickenSoup,1,1)
});
    }
    private void smeltingSetup(){
    	final float xp=3.0f;
        GameRegistry.addSmelting(ChickenSoup.shiftedIndex, new ItemStack(ChickenSoup,1,1),xp);
        
        GameRegistry.addSmelting(RawDinoMeat.shiftedIndex, new ItemStack(CookedDinoMeat),xp);
        GameRegistry.addSmelting(IcedMeat.shiftedIndex, new ItemStack(Item.beefCooked),xp);
        try{
            FurnaceRecipes.smelting().addSmelting(Ancientegg.shiftedIndex,EnumDinoType.Nautilus.ordinal(), new ItemStack(SJL));
        }catch(Throwable e){}

    }
    private void spawningSetup(){
    	final BiomeGenBase[] avaliableBiomes=new BiomeGenBase[22];
    	for (int i=0;i<avaliableBiomes.length;i++){
    		avaliableBiomes[i]=BiomeGenBase.biomeList[i];
    	}
        //EntityRegistry.addSpawn(EntityPigBoss.class, 1, 1, 1, EnumCreatureType.creature, new BiomeGenBase[]{BiomeGenBase.hell});
        EntityRegistry.addSpawn(EntityNautilus.class, 5, 4, 14, EnumCreatureType.waterCreature,avaliableBiomes);
    	//EntityRegistry.addSpawn(EntityFailuresaurus.class, 10, 4, 4, EnumCreatureType.monster,avaliableBiomes);
    }

    @PreInit
    public void PreLoad(FMLPreInitializationEvent event){
    	if(FMLCommonHandler.instance().getSide().isClient()){
    	MinecraftForge.EVENT_BUS.register(new DinoSoundHandler());
    	}
    }
    @Init
    public void load(FMLInitializationEvent event) {
    	if(FMLCommonHandler.instance().getSide().isClient()){
    		    		  LastLangSetting = ModLoader.getMinecraftInstance().gameSettings.language;
    		              registingRenderer();
    		              forgeTextureSetUp();
    		          	MinecraftForge.EVENT_BUS.register(new DinoSoundHandler()); 	
    		        	//KeyBindingRegistry.registerKeyBinding(keyBindingService);
    	}
    	NetworkRegistry.instance().registerChatListener(messagerHandler);
    	GameRegistry.registerWorldGenerator(FossilGenerator);
    	GameRegistry.registerWorldGenerator(AcademyA);
    	GameRegistry.registerWorldGenerator(ShipA);
    	GameRegistry.registerWorldGenerator(WeaponShopA);

          //MinecraftForge.registerAchievementPage(selfArcPage);
          SetupID();
          //SetupOptions();
          initBlockAndItems();
          registBlocks();       
          /*
           * MutiLanguage:Set names
           */
          UpdateName(true);
          /*
           * creative function
           */
          fillCreativeList();

          /*
           * Forge Function
           */
          //MinecraftForgeClient.registerSoundHandler(new DinoSoundHandler());

          forgeHarvestLevelSetUp();
          registTileEntitys();
          registEntitys();
          iconIndexSetup();
          //Quick recipe for testing
          //testingReciptSetup();
          //Actual using recipes
          reciptsSetup();
          smeltingSetup();
          spawningSetup();
          GUIHandlerSetup();

          INSTANCE=this;
    }

    private void GUIHandlerSetup() {
    	NetworkRegistry.instance().registerGuiHandler(this,GH);
	}

	/*@Override
    public boolean onTickInGame(float f, Minecraft minecraft) {
        if (minecraft.currentScreen == null) {
            lastGuiOpen = null;
        }
        UpdateName();
        return true;
    }

    @Override
    public boolean onTickInGUI(float f, Minecraft minecraft, GuiScreen guiscreen) {
        if ((guiscreen instanceof GuiContainerCreative) && !(lastGuiOpen instanceof GuiContainerCreative) && !minecraft.theWorld.isRemote) {
            Container container = ((GuiContainer) guiscreen).inventorySlots;
            ArrayList list = (ArrayList) ((ContainerCreative) container).itemList;
            for (int i = 0; i < BlockCount; i++) {
                list.add(SingleTotalList[i]);
            }
            ListIterator it;
            for (int j = 0; j < MutiCount; j++) {
                it=list.listIterator(200);
                while(it.hasNext()){
                	ItemStack isTmp=(ItemStack)(it.next());
                	if (isTmp.itemID==MutiTotalList[j].itemID && isTmp.getItemDamage()==MutiTotalList[j].getItemDamage()){
                		list.remove(it.nextIndex()-1);
                		break;
                	}
                }
                list.add(MutiTotalList[j]);
                
            }
        }
        lastGuiOpen = guiscreen;
        return true;
    }*/

    public static String GetEmbyoName(EnumEmbyos target) {
        String result = "";
        switch(target){
        case Cow:
        case Sheep:
        case Pig:
            result=" "+StringTranslate.getInstance().translateNamedKey("entity."+target.toString()).toString();
            break;
        default:
        	result=GetLangTextByKey("AnimalType."+target.toString());
        }

        return result;
    }

    public void UpdateName() {
        UpdateName(false);
    }
    public static String GetLangTextByKey(String key){
    	String result=LangProps.getProperty(key, " ");
    	return result;
    }
    public void UpdateName(boolean Init) {
    	if(FMLCommonHandler.instance().getSide().isServer()) return;
    	final String BLOCK="block.";
    	final String NAME=".Name";
    	final String ITEM="Item.";
    	final String HEAD="Head";
    	final String TAIL="Tail";
    	final String ORDER="Order.";
    	final String DNANAME="DNAName.";
    	final String DEGGNAME="DEggName.";
    	final String ACHIEVE="Achieve.";
    	final String PIGBOSSONEARTH="PigBossOnEarth";
    	final String DESC=".Desc";
    	String HeadTmp;
    	String TailTmp;
        if (Init) {
            this.LastLangSetting = ModLoader.getMinecraftInstance().gameSettings.language;
        } else {
            if (this.LastLangSetting.equals(ModLoader.getMinecraftInstance().gameSettings.language)) {
                return;
            } else {
                this.LastLangSetting = ModLoader.getMinecraftInstance().gameSettings.language;
            }
        }
        
        try {
        	UpdateLangProp();
        }
        catch (Throwable throwable){
        	return;
        }
        
        LanguageRegistry.addName(blockFossil, GetLangTextByKey(BLOCK+"Fossil"+NAME));
        LanguageRegistry.addName(blockSkull, GetLangTextByKey(BLOCK+"Skull"+NAME));
        LanguageRegistry.addName(SkullLantern, GetLangTextByKey(BLOCK+"SkullLantern"+NAME));
        //3.0 Contents
        LanguageRegistry.addName(biofossil, GetLangTextByKey(ITEM+"BioFossil"+NAME));
        LanguageRegistry.addName(relic, GetLangTextByKey(ITEM+"Relic"+NAME));
        LanguageRegistry.addName(stoneboard, GetLangTextByKey(ITEM+"Tablet"+NAME));
        LanguageRegistry.addName(blockanalyzerIdle, GetLangTextByKey(BLOCK+"Analyzer"+NAME));
        LanguageRegistry.addName(blockcultivateIdle, GetLangTextByKey(BLOCK+"CultureVat"+NAME));
        LanguageRegistry.addName(blockworktableIdle, GetLangTextByKey(BLOCK+"workbench"+NAME));
        LanguageRegistry.addName(BrokenSword, GetLangTextByKey(ITEM+"BrokenSword"+NAME));
        LanguageRegistry.addName(AncientSword, GetLangTextByKey(ITEM+"AncientSword"+NAME));
        LanguageRegistry.addName(FernSeed, GetLangTextByKey(ITEM+"FernSeeds"+NAME));
        LanguageRegistry.addName(Brokenhelmet, GetLangTextByKey(ITEM+"BrokenHelmet"+NAME));
        LanguageRegistry.addName(Ancienthelmet, GetLangTextByKey(ITEM+"AncientHelmet"+NAME));
        LanguageRegistry.addName(SkullStick, GetLangTextByKey(ITEM+"SkullStick"+NAME));
        short i=0;
        HeadTmp=GetLangTextByKey(DNANAME+HEAD);
        TailTmp=GetLangTextByKey(DNANAME+TAIL);
        for (i=0;i<EnumDinoType.values().length;i++){
        	String NameTmp=EntityDinosaurce.GetNameByEnum(EnumDinoType.values()[i],false);
            LanguageRegistry.addName(new ItemStack(DNA, 1, i), HeadTmp+NameTmp+TailTmp);
        }
        HeadTmp=GetLangTextByKey(DEGGNAME+HEAD);
        TailTmp=GetLangTextByKey(DEGGNAME+TAIL);
        for (i=0;i<EnumDinoType.values().length;i++){
        	String NameTmp;
        	if (i==4){
        		NameTmp=GetLangTextByKey("Item.LivingNautilus.Name");
        		LanguageRegistry.addName(new ItemStack(Ancientegg, 1, i), NameTmp);
        	}else{
	        	NameTmp=EntityDinosaurce.GetNameByEnum(EnumDinoType.values()[i],false);
	            LanguageRegistry.addName(new ItemStack(Ancientegg, 1, i), HeadTmp+NameTmp+TailTmp);
        	}
        }
        //4.0 Contents
        LanguageRegistry.addName(Dump, GetLangTextByKey(BLOCK+"Drum"+NAME));
        LanguageRegistry.addName(FeederIdle, GetLangTextByKey(BLOCK+"Feeder"+NAME));
        LanguageRegistry.addName(Gen, GetLangTextByKey(ITEM+"Gem"+NAME));
        LanguageRegistry.addName(GenAxe, GetLangTextByKey(ITEM+"ScarabAxe"+NAME));
        LanguageRegistry.addName(GenPickaxe, GetLangTextByKey(ITEM+"ScarabPickAxe"+NAME));
        LanguageRegistry.addName(GenSword, GetLangTextByKey(ITEM+"ScarabSword"+NAME));
        LanguageRegistry.addName(GenHoe, GetLangTextByKey(ITEM+"ScarabHoe"+NAME));
        LanguageRegistry.addName(GenShovel, GetLangTextByKey(ITEM+"ScarabShovel"+NAME));
        LanguageRegistry.addName(DinoPedia, GetLangTextByKey(ITEM+"DinoPedia"+NAME));
        //5.0 contents
        LanguageRegistry.addName(new ItemStack(ChickenSoup,1,0), GetLangTextByKey(ITEM+"RawChickenSoup"+NAME));
        LanguageRegistry.addName(new ItemStack(ChickenSoup,1,1), GetLangTextByKey(ITEM+"CookedChickenSoup"+NAME));
        LanguageRegistry.addName(ChickenEss, GetLangTextByKey(ITEM+"EOC"+NAME));
        LanguageRegistry.addName(EmptyShell, GetLangTextByKey(ITEM+"Shell"+NAME));
        LanguageRegistry.addName(SJL, GetLangTextByKey(ITEM+"SJL"+NAME));
        
        HeadTmp=GetLangTextByKey("MGCName."+HEAD);
        TailTmp=GetLangTextByKey("MGCName."+TAIL);
        for (i=0;i<EnumOrderType.values().length;i++){
        	String NameTmp=GetLangTextByKey("Order."+EnumOrderType.values()[i].toString());
            LanguageRegistry.addName(new ItemStack(MagicConch, 1, i), HeadTmp+NameTmp+TailTmp);
        }

        HeadTmp=GetLangTextByKey("MeatName."+HEAD);
        TailTmp=GetLangTextByKey("MeatName."+TAIL);
        for (i=0;i<EnumDinoType.values().length;i++){
        	String NameTmp=EntityDinosaurce.GetNameByEnum(EnumDinoType.values()[i],false);
            LanguageRegistry.addName(new ItemStack(RawDinoMeat, 1, i), HeadTmp+NameTmp+TailTmp);
        }
        
        LanguageRegistry.addName(CookedDinoMeat, GetLangTextByKey(ITEM+"DinoSteak"+NAME));
        
        HeadTmp=GetLangTextByKey("EmbyoName."+HEAD);
        TailTmp=GetLangTextByKey("EmbyoName."+TAIL);
        for (i=0;i<EnumEmbyos.values().length;i++){
        	String NameTmp=this.GetEmbyoName(EnumEmbyos.values()[i]);
            LanguageRegistry.addName(new ItemStack(EmbyoSyringe, 1, i), HeadTmp+NameTmp+TailTmp);
        }
        HeadTmp=GetLangTextByKey(DNANAME+HEAD);
        TailTmp=GetLangTextByKey(DNANAME+TAIL);
        for (i=0;i<EnumEmbyos.values().length+1/*chicken*/;i++){
        	String NameTmp;
        	if (i!=3){
	        	NameTmp=this.GetEmbyoName(EnumEmbyos.values()[i>3?i-1:i]);
        	}else{
        		NameTmp=StringTranslate.getInstance().translateNamedKey("entity.Chicken").toString();
        	}
            LanguageRegistry.addName(new ItemStack(AnimalDNA, 1, i), HeadTmp+NameTmp+TailTmp);
        }
        
        LanguageRegistry.addName(blockPermafrost, GetLangTextByKey(BLOCK+"Permafrost"+NAME));
        LanguageRegistry.addName(blockIcedStone, GetLangTextByKey(BLOCK+"IcedStone"+NAME));
        LanguageRegistry.addName(IcedMeat, GetLangTextByKey(ITEM+"IcedMeat"+NAME));
        LanguageRegistry.addName(Woodjavelin, GetLangTextByKey(ITEM+"WoodJavelin"+NAME));
        LanguageRegistry.addName(Stonejavelin, GetLangTextByKey(ITEM+"StoneJavelin"+NAME));
        LanguageRegistry.addName(Ironjavelin, GetLangTextByKey(ITEM+"IronJavelin"+NAME));
        LanguageRegistry.addName(Diamondjavelin, GetLangTextByKey(ITEM+"DiamondJavelin"+NAME));
        LanguageRegistry.addName(Goldjavelin, GetLangTextByKey(ITEM+"GoldJavelin"+NAME));
        LanguageRegistry.addName(AncientJavelin, GetLangTextByKey(ITEM+"AncientJavelin"+NAME));
        HeadTmp=GetLangTextByKey(ACHIEVE+PIGBOSSONEARTH+NAME);
        TailTmp=GetLangTextByKey(ACHIEVE+PIGBOSSONEARTH+DESC);
        ModLoader.addAchievementDesc(PigbossOnEarth, HeadTmp, TailTmp);
    }
    
    
    public static void UpdateLangProp()
    	throws IOException{
    	String loadLang=LastLangSetting;
    	try{
	    	Langfile=new File((net.minecraft.src.mod_Fossil.class).getResource("/Fossillang/"+LastLangSetting+".lang").getFile());
    	}
        catch(Throwable e){
        	loadLang=DEFAULT_LANG;
        }
    	finally{

        	UTF8Reader(LangProps,loadLang); 
        	//ChkRevLang(loadLang);
        }
    }
    private static void UTF8Reader(Properties properties,String LangName)
    	    throws IOException
    	    {
    	        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((net.minecraft.src.mod_Fossil.class).getResourceAsStream((new StringBuilder()).append("/Fossillang/").append(LangName).append(".lang").toString()), "UTF-8"));
    	        for (String s1 = bufferedreader.readLine(); s1 != null; s1 = bufferedreader.readLine())
    	        {
    	            s1 = s1.trim();
    	            if (s1.startsWith("#"))
    	            {
    	                continue;
    	            }
    	            String as[] = s1.split("=");
    	            if (as != null && as.length == 2)
    	            {
    	                properties.setProperty(as[0], as[1]);
    	            }
    	        }
    	    }

	public static void callGUI(EntityPlayer par5EntityPlayer,
			int GuiId, World par1World, int par2, int par3, int par4) {
		//FMLCommonHandler.instance().findContainerFor(mod)
    	//if(FMLCommonHandler.instance().getSide().isClient()){
		FMLNetworkHandler.openGui(par5EntityPlayer,INSTANCE , GuiId, par1World, par2,par3,par4);
    	//}
		
	}

	
}