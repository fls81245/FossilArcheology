package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderMammoth extends RenderLiving
{
    public RenderMammoth(ModelBase par1ModelBase, float par2)
    {
        super(par1ModelBase, par2);
    }

    public void renderMammoth(EntityMammoth par1EntityMammoth, double par2, double par4, double par6, float par8, float par9)
    {
    	 GL11.glPushMatrix();
         GL11.glDisable(GL11.GL_CULL_FACE);
         this.mainModel.onGround = this.renderSwingProgress(par1EntityMammoth, par9);

         if (this.renderPassModel != null)
         {
             this.renderPassModel.onGround = this.mainModel.onGround;
         }

         this.mainModel.isRiding = par1EntityMammoth.isRiding();

         if (this.renderPassModel != null)
         {
             this.renderPassModel.isRiding = this.mainModel.isRiding;
         }

         this.mainModel.isChild = par1EntityMammoth.isChild();

         if (this.renderPassModel != null)
         {
             this.renderPassModel.isChild = this.mainModel.isChild;
         }

         try
         {
             float var10 = this.func_48418_a(par1EntityMammoth.prevRenderYawOffset, par1EntityMammoth.renderYawOffset, par9);
             float var11 = this.func_48418_a(par1EntityMammoth.prevRotationYawHead, par1EntityMammoth.rotationYawHead, par9);
             float var12 = par1EntityMammoth.prevRotationPitch + (par1EntityMammoth.rotationPitch - par1EntityMammoth.prevRotationPitch) * par9;
             this.renderLivingAt(par1EntityMammoth, par2, par4, par6);
             float var13 = this.handleRotationFloat(par1EntityMammoth, par9);
             this.rotateCorpse(par1EntityMammoth, var13, var10, par9);
             float var14 = 0.0625F;
             GL11.glEnable(GL12.GL_RESCALE_NORMAL);

             if(par1EntityMammoth.isChild())GL11.glScalef(-1.0F, -1.0F, 1.0F);
             else GL11.glScalef(-6.0F, -6.0F, 6.0F);

             this.preRenderCallback(par1EntityMammoth, par9);
             GL11.glTranslatef(0.0F, -24.0F * var14 - 0.0078125F, 0.0F);
             float var15 = par1EntityMammoth.prevLegYaw+ (par1EntityMammoth.legYaw - par1EntityMammoth.prevLegYaw) * par9;
             float var16 = par1EntityMammoth.legSwing - par1EntityMammoth.legYaw * (1.0F - par9);

             if (par1EntityMammoth.isChild())
             {
                 var16 *= 3.0F;
             }

             if (var15 > 1.0F)
             {
                 var15 = 1.0F;
             }

             GL11.glEnable(GL11.GL_ALPHA_TEST);
             this.mainModel.setLivingAnimations(par1EntityMammoth, var16, var15, par9);
             this.renderModel(par1EntityMammoth, var16, var15, var13, var11 - var10, var12, var14);
             float var19;
             int var18;
             float var20;
             float var22;

             for (int var17 = 0; var17 < 4; ++var17)
             {
                 var18 = this.shouldRenderPass(par1EntityMammoth, var17, par9);

                 if (var18 > 0)
                 {
                     this.renderPassModel.setLivingAnimations(par1EntityMammoth, var16, var15, par9);
                     this.renderPassModel.render(par1EntityMammoth, var16, var15, var13, var11 - var10, var12, var14);

                     if (var18 == 15)
                     {
                         var19 = (float)par1EntityMammoth.ticksExisted + par9;
                         this.loadTexture("%blur%/misc/glint.png");
                         GL11.glEnable(GL11.GL_BLEND);
                         var20 = 0.5F;
                         GL11.glColor4f(var20, var20, var20, 1.0F);
                         GL11.glDepthFunc(GL11.GL_EQUAL);
                         GL11.glDepthMask(false);

                         for (int var21 = 0; var21 < 2; ++var21)
                         {
                             GL11.glDisable(GL11.GL_LIGHTING);
                             var22 = 0.76F;
                             GL11.glColor4f(0.5F * var22, 0.25F * var22, 0.8F * var22, 1.0F);
                             GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                             GL11.glMatrixMode(GL11.GL_TEXTURE);
                             GL11.glLoadIdentity();
                             float var23 = var19 * (0.001F + (float)var21 * 0.003F) * 20.0F;
                             float var24 = 0.33333334F;
                             GL11.glScalef(var24, var24, var24);
                             GL11.glRotatef(30.0F - (float)var21 * 60.0F, 0.0F, 0.0F, 1.0F);
                             GL11.glTranslatef(0.0F, var23, 0.0F);
                             GL11.glMatrixMode(GL11.GL_MODELVIEW);
                             this.renderPassModel.render(par1EntityMammoth, var16, var15, var13, var11 - var10, var12, var14);
                         }

                         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                         GL11.glMatrixMode(GL11.GL_TEXTURE);
                         GL11.glDepthMask(true);
                         GL11.glLoadIdentity();
                         GL11.glMatrixMode(GL11.GL_MODELVIEW);
                         GL11.glEnable(GL11.GL_LIGHTING);
                         GL11.glDisable(GL11.GL_BLEND);
                         GL11.glDepthFunc(GL11.GL_LEQUAL);
                     }

                     GL11.glDisable(GL11.GL_BLEND);
                     GL11.glEnable(GL11.GL_ALPHA_TEST);
                 }
             }

             this.renderEquippedItems(par1EntityMammoth, par9);
             float var26 = par1EntityMammoth.getBrightness(par9);
             var18 = this.getColorMultiplier(par1EntityMammoth, var26, par9);
             OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
             GL11.glDisable(GL11.GL_TEXTURE_2D);
             OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

             if ((var18 >> 24 & 255) > 0 || par1EntityMammoth.hurtTime > 0 || par1EntityMammoth.deathTime > 0)
             {
                 GL11.glDisable(GL11.GL_TEXTURE_2D);
                 GL11.glDisable(GL11.GL_ALPHA_TEST);
                 GL11.glEnable(GL11.GL_BLEND);
                 GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                 GL11.glDepthFunc(GL11.GL_EQUAL);

                 if (par1EntityMammoth.hurtTime > 0 || par1EntityMammoth.deathTime > 0)
                 {
                     GL11.glColor4f(var26, 0.0F, 0.0F, 0.4F);
                     this.mainModel.render(par1EntityMammoth, var16, var15, var13, var11 - var10, var12, var14);

                     for (int var27 = 0; var27 < 4; ++var27)
                     {
                         if (this.inheritRenderPass(par1EntityMammoth, var27, par9) >= 0)
                         {
                             GL11.glColor4f(var26, 0.0F, 0.0F, 0.4F);
                             this.renderPassModel.render(par1EntityMammoth, var16, var15, var13, var11 - var10, var12, var14);
                         }
                     }
                 }

                 if ((var18 >> 24 & 255) > 0)
                 {
                     var19 = (float)(var18 >> 16 & 255) / 255.0F;
                     var20 = (float)(var18 >> 8 & 255) / 255.0F;
                     float var29 = (float)(var18 & 255) / 255.0F;
                     var22 = (float)(var18 >> 24 & 255) / 255.0F;
                     GL11.glColor4f(var19, var20, var29, var22);
                     this.mainModel.render(par1EntityMammoth, var16, var15, var13, var11 - var10, var12, var14);

                     for (int var28 = 0; var28 < 4; ++var28)
                     {
                         if (this.inheritRenderPass(par1EntityMammoth, var28, par9) >= 0)
                         {
                             GL11.glColor4f(var19, var20, var29, var22);
                             this.renderPassModel.render(par1EntityMammoth, var16, var15, var13, var11 - var10, var12, var14);
                         }
                     }
                 }

                 GL11.glDepthFunc(GL11.GL_LEQUAL);
                 GL11.glDisable(GL11.GL_BLEND);
                 GL11.glEnable(GL11.GL_ALPHA_TEST);
                 GL11.glEnable(GL11.GL_TEXTURE_2D);
             }

             GL11.glDisable(GL12.GL_RESCALE_NORMAL);
         }
         catch (Exception var25)
         {
             var25.printStackTrace();
         }

         OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
         GL11.glEnable(GL11.GL_TEXTURE_2D);
         OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
         GL11.glEnable(GL11.GL_CULL_FACE);
         GL11.glPopMatrix();
         this.passSpecialRender(par1EntityMammoth, par2, par4, par6);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderMammoth((EntityMammoth)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderMammoth((EntityMammoth)par1Entity, par2, par4, par6, par8, par9);
    }
    private float func_48418_a(float par1, float par2, float par3)
    {
        float var4;

        for (var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F)
        {
            ;
        }

        while (var4 >= 180.0F)
        {
            var4 -= 360.0F;
        }

        return par1 + par3 * var4;
    }
}
