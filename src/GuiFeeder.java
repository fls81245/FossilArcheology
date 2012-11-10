package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
@SideOnly(Side.CLIENT)
public class GuiFeeder extends GuiContainer
{
    private TileEntityFeeder FeederInventory;
	public GuiFeeder(InventoryPlayer inventoryplayer, TileEntity tileentityfeeder)
    {
        super(new ContainerFeeder(inventoryplayer, tileentityfeeder));
        FeederInventory = (TileEntityFeeder) tileentityfeeder;
    }
	protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(mod_Fossil.GetLangTextByKey("block.Feeder.Name"), 8, 6, 0x404040);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 2, 0x404040);

    }
	public void drawScreen(int par1, int par2, float par3){
		this.drawDefaultBackground();
        int var4 = this.guiLeft;
        int var5 = this.guiTop;
        this.drawGuiContainerBackgroundLayer(par3, par1, par2);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        super.drawScreen(par1, par2, par3);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var4, (float)var5, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        Slot var6 = null;
        short var7 = 240;
        short var8 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var7 / 1.0F, (float)var8 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        for (int var11 = 0; var11 < this.inventorySlots.inventorySlots.size(); ++var11)
        {
            Slot var14 = (Slot)this.inventorySlots.inventorySlots.get(var11);
            this.drawSlotInventory(var14);

            if (this.isMouseOverSlot(var14, par1, par2))
            {
                var6 = var14;
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                int var9 = var14.xDisplayPosition;
                int var10 = var14.yDisplayPosition;
                this.drawGradientRect(var9, var10, var9 + 16, var10 + 16, -2130706433, -2130706433);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
        }

        this.drawGuiContainerForegroundLayer();
        InventoryPlayer var12 = this.mc.thePlayer.inventory;

        if (var12.getItemStack() != null)
        {
            GL11.glTranslatef(0.0F, 0.0F, 32.0F);
            this.zLevel = 200.0F;
            itemRenderer.zLevel = 200.0F;
            itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, var12.getItemStack(), par1 - var4 - 8, par2 - var5 - 8);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var12.getItemStack(), par1 - var4 - 8, par2 - var5 - 8);
            this.zLevel = 0.0F;
            itemRenderer.zLevel = 0.0F;
        }

        if (var12.getItemStack() == null && var6 != null && var6.getHasStack())
        {
            ItemStack var13 = var6.getStack();
            this.func_74184_a(var13, par1 - var4, par2 - var5);
        }
		fontRenderer.drawString(new StringBuilder().append(FeederInventory.getCurrentMeat()).toString(),23,32,0xFF0000);
		fontRenderer.drawString(new StringBuilder().append(FeederInventory.getCurreentVeg()).toString(),120,32,0x03b703);
        
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
	}
    private boolean isMouseOverSlot(Slot par1Slot, int par2, int par3)
    {
        return this.func_74188_c(par1Slot.xDisplayPosition, par1Slot.yDisplayPosition, 16, 16, par2, par3);
    }
	 private void drawSlotInventory(Slot par1Slot)
	    {
	        int var2 = par1Slot.xDisplayPosition;
	        int var3 = par1Slot.yDisplayPosition;
	        ItemStack var4 = par1Slot.getStack();
	        boolean var5 = false;
	        this.zLevel = 100.0F;
	        itemRenderer.zLevel = 100.0F;

	        if (var4 == null)
	        {
	            int var6 = par1Slot.getBackgroundIconIndex();

	            if (var6 >= 0)
	            {
	                GL11.glDisable(GL11.GL_LIGHTING);
	                this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/gui/items.png"));
	                this.drawTexturedModalRect(var2, var3, var6 % 16 * 16, var6 / 16 * 16, 16, 16);
	                GL11.glEnable(GL11.GL_LIGHTING);
	                var5 = true;
	            }
	        }

	        if (!var5)
	        {
	            GL11.glEnable(GL11.GL_DEPTH_TEST);
	            itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, var4, var2, var3);
	            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var4, var2, var3);
	        }

	        itemRenderer.zLevel = 0.0F;
	        this.zLevel = 0.0F;
	    }
	protected void drawGuiContainerBackgroundLayer(float f,int unusedi, int unusedj)
    {
        int i = mc.renderEngine.getTexture("/skull/UIFeeder.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

        int l = FeederInventory.getMeatBarScaled(46);
        drawTexturedModalRect(j + 66, (k + 55) - l, 176, 46 - l, 3, l );

        int i1 = FeederInventory.getVegBarScaled(46);
        drawTexturedModalRect(j + 110, (k + 55)-i1, 176, 46-i1, 3, i1);

    }
}