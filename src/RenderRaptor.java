package net.minecraft.src;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
public class RenderRaptor extends RenderLiving
{

	final float SwingConst=0.261799F;
	final int SwingStep=15;
	public static final float SwingBackSignal=-1000F;
	public boolean HuntingPose=false;

	public RenderRaptor(ModelBase modelbase, float f)
    {
        super(modelbase, f); 
    }
    private float func_77034_a(float par1, float par2, float par3)
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
	public void renderCow(EntityRaptor entityRaptor, double d, double d1, double d2, 
            float f, float f1)
    {
		GL11.glPushMatrix();
        GL11.glDisable(2884 /*GL_CULL_FACE*/);
        mainModel.onGround = renderSwingProgress(entityRaptor, f1);
        if(renderPassModel != null)
        {
            renderPassModel.onGround = mainModel.onGround;
        }
        mainModel.isRiding = entityRaptor.isRiding();
        if(renderPassModel != null)
        {
            renderPassModel.isRiding = mainModel.isRiding;
        }
        try
        {
        	 float var10 = this.func_77034_a(entityRaptor.prevRenderYawOffset, entityRaptor.renderYawOffset, f1);
             float var11 = this.func_77034_a(entityRaptor.prevRotationYawHead, entityRaptor.rotationYawHead, f1);
             float var12 = entityRaptor.prevRotationPitch + (entityRaptor.rotationPitch - entityRaptor.prevRotationPitch) * f1;
             this.renderLivingAt(entityRaptor, d, d1, d2);
             float var13 = this.handleRotationFloat(entityRaptor, f1);
             this.rotateCorpse(entityRaptor, var13, var10, f1);
             float var14 = 0.0625F;
             GL11.glEnable(GL12.GL_RESCALE_NORMAL);
             GL11.glScalef(((EntityDinosaurce)entityRaptor).getGLX(), -((EntityDinosaurce)entityRaptor).getGLY(), ((EntityDinosaurce)entityRaptor).getGLZ());
             this.preRenderCallback(entityRaptor, f1);
             GL11.glTranslatef(0.0F, -24.0F * var14 - 0.0078125F, 0.0F);
             float var15 = entityRaptor.prevLegYaw + (entityRaptor.legYaw - entityRaptor.prevLegYaw) * f1;
             float var16 = entityRaptor.legSwing - entityRaptor.legYaw * (1.0F - f1);

             if (entityRaptor.isChild())
             {
                 var16 *= 3.0F;
             }

             if (var15 > 1.0F)
             {
                 var15 = 1.0F;
             }

             GL11.glEnable(GL11.GL_ALPHA_TEST);
             this.mainModel.setLivingAnimations(entityRaptor, var16, var15, f1);
             this.renderModel(entityRaptor, var16, var15, var13, var11 - var10, var12, var14);
             float var19;
             int var18;
             float var20;
             float var22;

             for (int var17 = 0; var17 < 4; ++var17)
             {
                 var18 = this.shouldRenderPass(entityRaptor, var17, f1);

                 if (var18 > 0)
                 {
                     this.renderPassModel.setLivingAnimations(entityRaptor, var16, var15, f1);
                     this.renderPassModel.render(entityRaptor, var16, var15, var13, var11 - var10, var12, var14);

                     if (var18 == 15)
                     {
                         var19 = (float)entityRaptor.ticksExisted + f1;
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
                             this.renderPassModel.render(entityRaptor, var16, var15, var13, var11 - var10, var12, var14);
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

             this.renderEquippedItems(entityRaptor, f1);
             float var26 = entityRaptor.getBrightness(f1);
             var18 = this.getColorMultiplier(entityRaptor, var26, f1);
             OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
             GL11.glDisable(GL11.GL_TEXTURE_2D);
             OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

             if ((var18 >> 24 & 255) > 0 || entityRaptor.hurtTime > 0 || entityRaptor.deathTime > 0)
             {
                 GL11.glDisable(GL11.GL_TEXTURE_2D);
                 GL11.glDisable(GL11.GL_ALPHA_TEST);
                 GL11.glEnable(GL11.GL_BLEND);
                 GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                 GL11.glDepthFunc(GL11.GL_EQUAL);

                 if (entityRaptor.hurtTime > 0 || entityRaptor.deathTime > 0)
                 {
                     GL11.glColor4f(var26, 0.0F, 0.0F, 0.4F);
                     this.mainModel.render(entityRaptor, var16, var15, var13, var11 - var10, var12, var14);

                     for (int var27 = 0; var27 < 4; ++var27)
                     {
                         if (this.inheritRenderPass(entityRaptor, var27, f1) >= 0)
                         {
                             GL11.glColor4f(var26, 0.0F, 0.0F, 0.4F);
                             this.renderPassModel.render(entityRaptor, var16, var15, var13, var11 - var10, var12, var14);
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
                     this.mainModel.render(entityRaptor, var16, var15, var13, var11 - var10, var12, var14);

                     for (int var28 = 0; var28 < 4; ++var28)
                     {
                         if (this.inheritRenderPass(entityRaptor, var28, f1) >= 0)
                         {
                             GL11.glColor4f(var19, var20, var29, var22);
                             this.renderPassModel.render(entityRaptor, var16, var15, var13, var11 - var10, var12, var14);
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
         this.passSpecialRender(entityRaptor, d, d1, d2);
        	
        	
            /*float f2 = entityTRex.prevRenderYawOffset + (entityTRex.renderYawOffset - entityTRex.prevRenderYawOffset) * f1;
            float f3 = entityTRex.prevRotationYaw + (entityTRex.rotationYaw - entityTRex.prevRotationYaw) * f1;
            float f4 = entityTRex.prevRotationPitch + (entityTRex.rotationPitch - entityTRex.prevRotationPitch) * f1;
            renderLivingAt(entityTRex, d, d1, d2);
            float f5 = handleRotationFloat(entityTRex, f1);
            rotateCorpse(entityTRex, f5, f2, f1);
            float f6 = 0.0625F;
            GL11.glEnable(32826);
            GL11.glScalef(entityTRex.getGLX(), -entityTRex.getGLY(), entityTRex.getGLZ());
            preRenderCallback(entityTRex, f1);
            GL11.glTranslatef(0.0F, -24F * f6 - 0.0078125F, 0.0F);
            float f7 = entityTRex.prevLegYaw+ (entityTRex.legYaw - entityTRex.prevLegYaw) * f1;
            float f8 = entityTRex.legSwing - entityTRex.legYaw * (1.0F - f1);
            if(f7 > 1.0F)
            {
                f7 = 1.0F;
            }
            loadDownloadableImageTexture(entityTRex.skinUrl, entityTRex.getTexture());
            GL11.glEnable(3008 );
            if (entityTRex.SkillTick>0){
				if (entityTRex.SkillTick<=30) ((ModelTRex)mainModel).OpenMouth(10);
				if (entityTRex.SkillTick>30) ((ModelTRex)mainModel).CloseMouth(10);
				if (entityTRex.SkillTick<20) ((ModelTRex)mainModel).RunPose();
				if (entityTRex.SkillTick>=20) ((ModelTRex)mainModel).StandPose();
			}else{
				if (entityTRex.Screaming){
					((ModelTRex)mainModel).OpenMouth(5);
					//entityTRex.ScreamTick--;
				}else{
					((ModelTRex)mainModel).CloseMouth(5);
				}
			}
            mainModel.setLivingAnimations(entityTRex, f8, f7, f1);            
			mainModel.render(entityTRex,f8, f7, f5, f3 - f2, f4, f6);
			
            for(int i = 0; i < 4; i++)
            {
                if(shouldRenderPass(entityTRex, i, f1)>=0)
                {
                    renderPassModel.render(entityTRex,f8, f7, f5, f3 - f2, f4, f6);
                    GL11.glDisable(3042);
                    GL11.glEnable(3008);
                }
            }
			
            renderEquippedItems(entityTRex, f1);
            float f9 = entityTRex.getBrightness(f1);
            int j = getColorMultiplier(entityTRex, f9, f1);
            if((j >> 24 & 0xff) > 0 || entityTRex.hurtTime > 0 || entityTRex.deathTime > 0)
            {
                GL11.glDisable(3553 );
                GL11.glDisable(3008 );
                GL11.glEnable(3042 );
                GL11.glBlendFunc(770, 771);
                GL11.glDepthFunc(514);
                if(entityTRex.hurtTime > 0 || entityTRex.deathTime > 0)
                {
                    GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
                    mainModel.render(entityTRex,f8, f7, f5, f3 - f2, f4, f6);
                    for(int k = 0; k < 4; k++)
                    {
                        if(inheritRenderPass(entityTRex, k, f1)>=0)
                        {
                            GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
                            renderPassModel.render(entityTRex,f8, f7, f5, f3 - f2, f4, f6);
                        }
                    }

                }
                if((j >> 24 & 0xff) > 0)
                {
                    float f10 = (float)(j >> 16 & 0xff) / 255F;
                    float f11 = (float)(j >> 8 & 0xff) / 255F;
                    float f12 = (float)(j & 0xff) / 255F;
                    float f13 = (float)(j >> 24 & 0xff) / 255F;
                    GL11.glColor4f(f10, f11, f12, f13);
                    mainModel.render(entityTRex,f8, f7, f5, f3 - f2, f4, f6);
                    for(int l = 0; l < 4; l++)
                    {
                        if(inheritRenderPass(entityTRex, l, f1)>=0)
                        {
                            GL11.glColor4f(f10, f11, f12, f13);
                            renderPassModel.render(entityTRex,f8, f7, f5, f3 - f2, f4, f6);
                        }
                    }

                }
                GL11.glDepthFunc(515);
                GL11.glDisable(3042 );
                GL11.glEnable(3008 );
                GL11.glEnable(3553);
            }
            GL11.glDisable(32826 );*/
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        renderCow((EntityRaptor)entityliving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        renderCow((EntityRaptor)entity, d, d1, d2, f, f1);
    }
	protected void renderEquippedItems(EntityLiving entityliving, float f)
    {
		float AgeOffset=(1.0F+0.0F*((float)((EntityDinosaurce)entityliving).getDinoAge()));
		ItemStack itemstack = ((EntityRaptor)entityliving).ItemInMouth;
        if(itemstack != null)
        {
            GL11.glPushMatrix();
            ((ModelRaptor)mainModel).head3_up.postRender(0.01F);
            if(itemstack.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
            {
                float f1 = 0.5F;
                GL11.glTranslatef(0.0F, 0.4F, -0.75F);
                f1 *= 0.75F;
                GL11.glRotatef(20F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f1*AgeOffset, -f1*AgeOffset, f1*AgeOffset);
            } else
            if(Item.itemsList[itemstack.itemID].isFull3D())
            {
                float f2 = 0.625F;
                GL11.glTranslatef(0.0F, 0.4F, -0.75F);
                GL11.glScalef(f2*AgeOffset, -f2*AgeOffset, f2*AgeOffset);
                GL11.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            } else
            {
                float f3 = 0.375F;
                GL11.glTranslatef(0.0F, 0.4F, -0.75F);
                GL11.glScalef(f3*AgeOffset, f3*AgeOffset, f3*AgeOffset);
                GL11.glRotatef(60F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
            }
            renderManager.itemRenderer.renderItem(entityliving, itemstack,1);
            GL11.glPopMatrix();
        }
	}
}