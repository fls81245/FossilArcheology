package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
public class GuiCultivate extends GuiContainer
{
    private TileEntityCultivate furnaceInventory;
	public GuiCultivate(InventoryPlayer inventoryplayer, TileEntity tileentityfurnace)
    {
        super(new ContainerCultivate(inventoryplayer, tileentityfurnace));
        furnaceInventory = (TileEntityCultivate) tileentityfurnace;
    }
	protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(mod_Fossil.GetLangTextByKey("block.CultureVat.Name"), 60, 6, 0x404040);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 2, 0x404040);
    }
	protected void drawGuiContainerBackgroundLayer(float f,int unusedi, int unusedj)
    {
        int i = mc.renderEngine.getTexture("/skull/UICultivate.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        if(furnaceInventory.isBurning())
        {
            int l = furnaceInventory.getBurnTimeRemainingScaled(12);
            drawTexturedModalRect(j + 82, (k + 36 + 12) - l, 176, 12 - l, 14, l + 2);
        }
        int i1 = furnaceInventory.getCookProgressScaled(24);
        drawTexturedModalRect(j + 79, k + 18, 176, 14, i1 + 1, 16);
    }
}