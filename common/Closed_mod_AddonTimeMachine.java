package net.minecraft.src;

//all java files for minecraft should have this.
import static cpw.mods.fml.common.Side.CLIENT;
import static cpw.mods.fml.common.Side.*;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.client.Minecraft;
import java.util.Random;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import cpw.mods.fml.common.asm.SideOnly;

public class Closed_mod_AddonTimeMachine /*extends BaseMod*/ {
	private static int[] BlockIDs = { 150 };
	private static String[] BlockName = { "Block TimeMachine" };
	private static String[] ItemName;
	private static int[] ItemIDs;
	public static Block blockTimeMachine;
	private static GuiScreen lastGuiOpen;
	public static String LastLangSetting;

	/*public Closed_mod_AddonTimeMachine() {
		ModLoader.setInGUIHook(this, true, true);
		ModLoader.setInGameHook(this, true, true);
	}*/

	//@Override
	public String getVersion() {
		return "Time Machine Add-on Engaged";
	}

	//@Override
	public void load() {
		SetupID();

		blockTimeMachine = new BlockTimeMachine(BlockIDs[0], 0, Material.glass)
				.setLightValue(0.9375F).setHardness(0.3F)
				.setStepSound(Block.soundGlassFootstep)
				.setBlockName("BlockTimeMachine");
		/* MutiLanguage:Set names */
		UpdateName(true);

		ModLoader.registerBlock(blockTimeMachine);
		try {
			LastLangSetting = ModLoader.getMinecraftInstance().gameSettings.language;
		} catch (Throwable e) {

		} finally {
			try {
				ModLoader.registerTileEntity(
						net.minecraft.src.TileEntityTimeMachine.class,
						"TimeMachine", new RenderTNClock());
			} catch (Throwable e) {
				ModLoader.registerTileEntity(
						net.minecraft.src.TileEntityTimeMachine.class,
						"TimeMachine");
			}
		}

	}

	private void SetupID() {
		try {
			boolean OldFileDetected = false;
			Properties props = TimeMachineCfgLoader.loadConfig();
			Properties Storeback = new Properties();
			if (props != null) {
				for (int i = 0; i < BlockIDs.length; i++) {
					if (props.containsKey(BlockName[i]) && !OldFileDetected) {
						int temp = Integer.parseInt(props
								.getProperty(BlockName[i]));
						if (temp <= 122) {
							OldFileDetected = true;
						} else {
							BlockIDs[i] = temp;
						}
					}
					Storeback.setProperty(BlockName[i],
							Integer.toString(BlockIDs[i]));
				}
				/*
				 * for (int i = 0; i < ItemIDs.length; i++) { if
				 * (props.containsKey(ItemName[i]) && !OldFileDetected) {
				 * ItemIDs[i] =
				 * Integer.parseInt(props.getProperty(ItemName[i])); }
				 * Storeback.setProperty(ItemName[i],
				 * Integer.toString(ItemIDs[i])); }
				 */
			}
			TimeMachineCfgLoader.saveConfig(Storeback);
		} catch (Throwable throwable) {
			return;
		}
	}

	public boolean OnTickInGame(float f, Minecraft minecraft) {
		if (minecraft.currentScreen == null) {
			lastGuiOpen = null;
		}
		UpdateName();
		return true;
	}

	//@Override
	public boolean onTickInGUI(float f, Minecraft minecraft, GuiScreen guiscreen) {
		if ((guiscreen instanceof GuiContainerCreative)
				&& !(lastGuiOpen instanceof GuiContainerCreative)
				&& !minecraft.theWorld.isRemote) {
			Container container = ((GuiContainer) guiscreen).inventorySlots;
			List list = ((ContainerCreative) container).itemList;
			list.add(new ItemStack(blockTimeMachine, 1, 0));
		}
		lastGuiOpen = guiscreen;
		return true;
	}

	public void UpdateName() {
		UpdateName(false);
	}

	public void UpdateName(boolean Init) {

		ModLoader.addName(blockTimeMachine, /*
											 * mod_Fossil.GetLangTextByKey(
											 * "block.TimeMachine.Name")
											 */"Time Machine");

	}
}
