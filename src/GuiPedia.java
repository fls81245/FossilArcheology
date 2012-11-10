package net.minecraft.src;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class GuiPedia extends GuiContainer
{
    private EntityDinosaurce dino;

    public GuiPedia(InventoryPlayer par1InventoryPlayer, EntityDinosaurce par2Dino, World par3World)
    {
        super(new ContainerPedia());
        this.dino = par2Dino;
        this.xSize=178;
        this.ySize=164;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        int var1 = (this.width - this.xSize) / 2;
        int var2 = (this.height - this.ySize) / 2;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(new StringBuilder().append(this.dino.getDinoAge()).append("days").toString(), 110, 31, 4210752);
        this.fontRenderer.drawString(new StringBuilder().append(this.dino.getHealth()).append('/').append(this.dino.getMaxHealth()).toString(), 110, 47, 4210752);
        this.fontRenderer.drawString(new StringBuilder().append(this.dino.getHunger()).append('/').append(this.dino.getHungerLimit()).toString(), 110, 62, 4210752);
        String[] additional=this.dino.additionalPediaMessage();
        if (additional==null) return;
        for (int i=0;i<additional.length && i<=6;i++){
        	this.fontRenderer.drawString(additional[i],104,80+(this.fontRenderer.FONT_HEIGHT*i),4210752);
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
    }



    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int var4 = this.mc.renderEngine.getTexture("/skull/UIPedia.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);


            int var5 = (this.width - this.xSize) / 2;
            int var6 = (this.height - this.ySize) / 2;
            GL11.glPushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
    }
    public void onGuiClosed()
    {
    	super.onGuiClosed();
    	EntityDinosaurce.pediaingDino=null;
    }
}
