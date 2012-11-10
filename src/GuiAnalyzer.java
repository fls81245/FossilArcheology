package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiAnalyzer extends GuiContainer{
    private TileEntityAnalyzer analyzerInventory;
	public GuiAnalyzer(InventoryPlayer inventoryplayer, TileEntity tileentityanalyzer)
    {
		super(new ContainerAnalyzer(inventoryplayer, tileentityanalyzer));
		analyzerInventory = (TileEntityAnalyzer) tileentityanalyzer;
	}
	protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(mod_Fossil.GetLangTextByKey("block.Analyzer.Name"), 19, 6, 0x404040);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 2, 0x404040);
    }
	protected void drawGuiContainerBackgroundLayer(float f,int i, int j)
    {
        int k = mc.renderEngine.getTexture("/skull/UIAnalyzer.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(k);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
		
        int k1 = analyzerInventory.getCookProgressScaled(21);
        drawTexturedModalRect(l + 80, i1 + 22, 177, 18, k1 + 1, 9);
    }

}