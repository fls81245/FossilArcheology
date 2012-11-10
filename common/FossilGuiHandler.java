package net.minecraft.src;

import static cpw.mods.fml.common.Side.CLIENT;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.Side;
/**
 * 
 * @author FlammaRilva
 *
 */
public class FossilGuiHandler implements IGuiHandler {
	public static final int ANALYZER_GUI_ID=0;
	public static final int CULTIVE_GUI_ID=1;
	public static final int FEEDER_GUI_ID=2;
	public static final int WORKTABLE_GUI_ID=3;
	public static final int DINOPEDIA_GUI_ID=4;
	@Override

	public Container getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {

		TileEntity workTarget=world.getBlockTileEntity(x, y, z);
		switch(ID){
		case DINOPEDIA_GUI_ID:
			return new ContainerPedia();
		case ANALYZER_GUI_ID:
			if (workTarget==null) return null;
			return new ContainerAnalyzer(player.inventory,  workTarget);
		case CULTIVE_GUI_ID:
			if (workTarget==null) return null;
			return new ContainerCultivate(player.inventory,workTarget);
		case FEEDER_GUI_ID:
			if (workTarget==null) return null;
			return new ContainerFeeder(player.inventory,workTarget);
		case WORKTABLE_GUI_ID:
			if (workTarget==null) return null;
			return new ContainerWorktable(player.inventory,workTarget);
		}
		return null;
	}
    @SideOnly(CLIENT)
	@Override
	public GuiScreen getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
			TileEntity workTarget=world.getBlockTileEntity(x, y, z);
			switch(ID){
			case DINOPEDIA_GUI_ID:
				if (EntityDinosaurce.pediaingDino==null) return null;
				return new GuiPedia(player.inventory,EntityDinosaurce.pediaingDino,world);
			case ANALYZER_GUI_ID:
				if (workTarget==null) return null;
				return new GuiAnalyzer(player.inventory, workTarget);
			case CULTIVE_GUI_ID:
				if (workTarget==null) return null;
				return new GuiCultivate(player.inventory,workTarget);
			case FEEDER_GUI_ID:
				if (workTarget==null) return null;
				return new GuiFeeder(player.inventory,workTarget);
			case WORKTABLE_GUI_ID:
				if (workTarget==null) return null;
				return new GuiWorktable(player.inventory,workTarget);
			}
			return null;
	}

}
