package net.minecraft.src;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
public class RenderPterosaur extends RenderLiving
{
	public RenderPterosaur(ModelBase modelbase, float f)
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
	public void renderCow(EntityPterosaur entityPterosaur, double d, double d1, double d2, 
            float f, float f1)
    {
		 //super.doRenderLiving(entityPterosaur, d, d1, d2, f, f1);
		GL11.glPushMatrix();
        GL11.glDisable(2884 /*GL_CULL_FACE*/);
        mainModel.onGround = renderSwingProgress(entityPterosaur, f1);
        if(renderPassModel != null)
        {
            renderPassModel.onGround = mainModel.onGround;
        }
        mainModel.isRiding = entityPterosaur.isRiding();
        if(renderPassModel != null)
        {
            renderPassModel.isRiding = mainModel.isRiding;
        }
        try
        {
            float f2 = entityPterosaur.prevRenderYawOffset + (entityPterosaur.renderYawOffset - entityPterosaur.prevRenderYawOffset) * f1;
            float f3 = entityPterosaur.prevRotationYaw + (entityPterosaur.rotationYaw - entityPterosaur.prevRotationYaw) * f1;
            float f4 = entityPterosaur.prevRotationPitch + (entityPterosaur.rotationPitch - entityPterosaur.prevRotationPitch) * f1;
            renderLivingAt(entityPterosaur, d, d1, d2);
            float f5 = handleRotationFloat(entityPterosaur, f1);
            rotateCorpse(entityPterosaur, f5, f2, f1);
            float f6 = 0.0625F;
            GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
            GL11.glScalef(entityPterosaur.getGLX(), -entityPterosaur.getGLY(), entityPterosaur.getGLZ());
            preRenderCallback(entityPterosaur, f1);
            GL11.glTranslatef(0.0F, -24F * f6 - 0.0078125F, 0.0F);
            float f7 = entityPterosaur.prevLegYaw+ (entityPterosaur.legYaw - entityPterosaur.prevLegYaw) * f1;
            float f8 = entityPterosaur.legSwing - entityPterosaur.legYaw * (1.0F - f1);
            if(f7 > 1.0F)
            {
                f7 = 1.0F;
            }
            loadDownloadableImageTexture(entityPterosaur.skinUrl, entityPterosaur.getTexture());
            GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
            
            if (this.FlyingModel){
            	/*ModelPterosaurFlying ModelLink=(ModelPterosaurFlying)this.mainModel;
            	float LeftWingFlap=new Random().nextFloat()*10-5.0F;
            	if (LeftWingFlap>5.0F) LeftWingFlap=5.0F;
            	if (LeftWingFlap<-5.0F) LeftWingFlap=-5.0F;
            	float RightWingFlap=new Random().nextFloat()*10-5.0F;
            	if (RightWingFlap>5.0F) RightWingFlap=5.0F;
            	if (RightWingFlap<-5.0F) RightWingFlap=-5.0F;
            	ModelLink.LeftWingRandomFlap=LeftWingFlap;
            	ModelLink.RightWingRandomFlap=RightWingFlap;*/
            	
            }
            if (this.LandingModel){
            	this.LandModel.setLivingAnimations(entityPterosaur, f8, f7, f1);
            	this.LandModel.render(entityPterosaur,f8, f7, f5, f3 - f2, f4, f6);
            }else{
	            if (this.FlyingModel){
	            	((ModelPterosaurFlying)this.FlyModel).AirPitch=-(float)(entityPterosaur.AirPitch*(Math.PI/180));
	            	((ModelPterosaurFlying)this.FlyModel).AirRoll=(float)(entityPterosaur.AirAngle*(Math.PI/180));
	            	this.FlyModel.setLivingAnimations(entityPterosaur, f8, f7, f1);
	            	this.FlyModel.render(entityPterosaur,f8, f7, f5, f3 - f2, f4, f6);
	            }else{
	            	this.GroundModel.setLivingAnimations(entityPterosaur, f8, f7, f1);
	            	this.GroundModel.render(entityPterosaur,f8, f7, f5, f3 - f2, f4, f6);
	            }
            }
            //mainModel.setLivingAnimations(entityPterosaur, f8, f7, f1);
            //mainModel.render(f8, f7, f5, f3 - f2, f4, f6);
			/*if (entityPterosaur.isSelfAngry() || entityPterosaur.ItemInMouth!=null){
				((ModelPterosaurGround)mainModel).OpenMouth(5);
			}else{
				((ModelPterosaurGround)mainModel).CloseMouth(10);
			}*/
            for(int i = 0; i < 4; i++)
            {
                if(shouldRenderPass(entityPterosaur, i, f1)>=0)
                {
                    renderPassModel.render(entityPterosaur,f8, f7, f5, f3 - f2, f4, f6);
                    GL11.glDisable(3042 /*GL_BLEND*/);
                    GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
                }
            }

            renderEquippedItems(entityPterosaur, f1);
            float f9 = entityPterosaur.getBrightness(f1);
            int j = getColorMultiplier(entityPterosaur, f9, f1);
            if((j >> 24 & 0xff) > 0 || entityPterosaur.hurtTime > 0 || entityPterosaur.deathTime > 0)
            {
                GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
                GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
                GL11.glEnable(3042 /*GL_BLEND*/);
                GL11.glBlendFunc(770, 771);
                GL11.glDepthFunc(514);
                if(entityPterosaur.hurtTime > 0 || entityPterosaur.deathTime > 0)
                {
                    GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
                    if (this.LandingModel) this.LandModel.render(entityPterosaur,f8, f7, f5, f3 - f2, f4, f6);
                    else if (this.FlyingModel) this.FlyModel.render(entityPterosaur,f8, f7, f5, f3 - f2, f4, f6);
                    else this.GroundModel.render(entityPterosaur,f8, f7, f5, f3 - f2, f4, f6);
                    //mainModel.render(f8, f7, f5, f3 - f2, f4, f6);
                    for(int k = 0; k < 4; k++)
                    {
                        if(inheritRenderPass(entityPterosaur, k, f1)>=0)
                        {
                            GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
                            renderPassModel.render(entityPterosaur,f8, f7, f5, f3 - f2, f4, f6);
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
                    if (this.LandingModel) this.LandModel.render(entityPterosaur,f8, f7, f5, f3 - f2, f4, f6);
                    else if (this.FlyingModel) this.FlyModel.render(entityPterosaur,f8, f7, f5, f3 - f2, f4, f6);
                    else this.GroundModel.render(entityPterosaur,f8, f7, f5, f3 - f2, f4, f6);
                    //mainModel.render(f8, f7, f5, f3 - f2, f4, f6);

                    for(int l = 0; l < 4; l++)
                    {
                        if(inheritRenderPass(entityPterosaur, l, f1)>=0)
                        {
                            GL11.glColor4f(f10, f11, f12, f13);
                            renderPassModel.render(entityPterosaur,f8, f7, f5, f3 - f2, f4, f6);
                        }
                    }

                }
                GL11.glDepthFunc(515);
                GL11.glDisable(3042 /*GL_BLEND*/);
                GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
                GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
            }
            GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        GL11.glEnable(2884 /*GL_CULL_FACE*/);
        GL11.glPopMatrix();
        passSpecialRender(entityPterosaur, d, d1, d2);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        renderCow((EntityPterosaur)entityliving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
    	if (((EntityPterosaur)entity).getDinoAge()<5 || entity.onGround || entity.inWater){
    		if (this.FlyingModel){
    			this.FlyingModel=false;
    			//this.renderPassModel=this.mainModel=new ModelPterosaurGround();
    			//this.renderPassModel=this.mainModel=new ModelPterosaurFlying();
    		}
    	}else{
    		if (!this.FlyingModel){
    			this.FlyingModel=true;
    			//this.renderPassModel=this.mainModel=new ModelPterosaurFlying();
    		}
    	}
		this.LandingModel=((EntityPterosaur)entity).Landing;
		renderCow((EntityPterosaur)entity, d, d1, d2, f, f1);
    }
	/*protected void renderEquippedItems(EntityLiving entityliving, float f)
    {
		float AgeOffset=(1.0F+0.0F*((float)((EntityPterosaur)entityliving).age));
		ItemStack itemstack = ((EntityPterosaur)entityliving).ItemInMouth;
        if(itemstack != null)
        {
            GL11.glPushMatrix();
            ((ModelPterosaurGround)mainModel).upper_mouth.postRender(0.04F);
            if(itemstack.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
            {
                float f1 = 0.5F;
                GL11.glTranslatef(0.0F, 0.4F, -0.3125F);
                f1 *= 0.75F;
                GL11.glRotatef(20F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f1*AgeOffset, -f1*AgeOffset, f1*AgeOffset);
            } else
            if(Item.itemsList[itemstack.itemID].isFull3D())
            {
                float f2 = 0.625F;
                GL11.glTranslatef(0.0F, 0.4F, 0.0F);
                GL11.glScalef(f2*AgeOffset, -f2*AgeOffset, f2*AgeOffset);
                GL11.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            } else
            {
                float f3 = 0.375F;
                GL11.glTranslatef(0.25F, 0.4F, -0.1875F);
                GL11.glScalef(f3*AgeOffset, f3*AgeOffset, f3*AgeOffset);
                GL11.glRotatef(60F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
            }
            //renderManager.itemRenderer.renderItem(entityliving, itemstack);
            GL11.glPopMatrix();
        }
	}*/
	public boolean FlyingModel=false;
	public boolean LandingModel=false;
	public float RollAngle=0.0F;
	public ModelBase GroundModel=new ModelPterosaurGround();
	public ModelBase FlyModel=new ModelPterosaurFlying();
	public ModelBase LandModel=new ModelPTSLanding();
}