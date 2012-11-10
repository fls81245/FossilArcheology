





package net.minecraft.src;

public class Modeldil extends ModelDinosaurs
{
      //fields
    ModelRenderer Head;
    ModelRenderer Jaw1;
    ModelRenderer Jaw2;
    ModelRenderer Crest;
    ModelRenderer Crest2;
    ModelRenderer Crest3;
    ModelRenderer Crest4;
    ModelRenderer Neck;
    ModelRenderer UpperBody;
    ModelRenderer LowerBody;
    ModelRenderer Thigh1;
    ModelRenderer Thigh2;
    ModelRenderer Tail1;
    ModelRenderer Tail2;
    ModelRenderer Heel1;
    ModelRenderer Heel2;
    ModelRenderer Feet1;
    ModelRenderer Feet2;
    ModelRenderer UpperArm1;
    ModelRenderer UpperArm2;
    ModelRenderer LowerArm1;
    ModelRenderer LowerArm2;
    ModelRenderer Frill2;
    ModelRenderer Frill1;
      
      public Modeldil()
      {
        Head = new ModelRenderer(this, 0, 20).setTextureSize(64, 32);;
        Head.addBox(-3F, 0F, -6F, 6, 6, 6);
        Head.setRotationPoint(0F, 4F, -10F);
        setRotation(Head, 0F, 0F, 0F);
        Head.mirror = true;
        Jaw1 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);;
        Jaw1.addBox(-2F, 0F, -12F, 4, 4, 6);
        Jaw1.setRotationPoint(0F, 4F, -10F);
        setRotation(Jaw1, 0F, 0F, 0F);
        Jaw1.mirror = true;
        Jaw2 = new ModelRenderer(this, 1, 10).setTextureSize(64, 32);;
        Jaw2.addBox(-1.5F, 4F, -11F, 3, 1, 7);
        Jaw2.setRotationPoint(0F, 4F, -10F);
        setRotation(Jaw2, 0F, 0F, 0F);
        Jaw2.mirror = true;
        Crest = new ModelRenderer(this, 18, 11).setTextureSize(64, 32);;
        Crest.addBox(-2F, -4F, -10F, 0, 4, 10);
        Crest.setRotationPoint(0F, 4F, -10F);
        setRotation(Crest, 0F, 0F, 0F);
        Crest.mirror = true;
        Crest2 = new ModelRenderer(this, 18, 11).setTextureSize(64, 32);;
        Crest2.addBox(2F, -4F, -10F, 0, 4, 10);
        Crest2.setRotationPoint(0F, 4F, -10F);
        setRotation(Crest2, 0F, 0F, 0F);
        Crest2.mirror = true;
        Crest3 = new ModelRenderer(this, 16, -5).setTextureSize(64, 32);;
        Crest3.addBox(0F, 0F, 0F, 0, 6, 5);
        Crest3.setRotationPoint(-3F, 4F, -10F);
        setRotation(Crest3, 0F, -0.5235988F, 0F);
        Crest3.mirror = true;
        Crest4 = new ModelRenderer(this, 16, -5).setTextureSize(64, 32);;
        Crest4.addBox(0F, 0F, 0F, 0, 6, 5);
        Crest4.setRotationPoint(3F, 4F, -10F);
        setRotation(Crest4, 0F, 0.5235988F, 0F);
        Crest4.mirror = true;
        Neck = new ModelRenderer(this, 42, 21).setTextureSize(64, 32);;
        Neck.addBox(-2F, -1.5F, -7F, 4, 4, 7);
        Neck.setRotationPoint(0F, 10F, -6F);
        setRotation(Neck, -0.7063936F, 0F, 0F);
        Neck.mirror = true;
        UpperBody = new ModelRenderer(this, 40, 0).setTextureSize(64, 32);;
        UpperBody.addBox(-3F, -3F, -6.5F, 6, 6, 6);
        UpperBody.setRotationPoint(0F, 11.5F, -1F);
        setRotation(UpperBody, -0.2602438F, 0F, 0F);
        UpperBody.mirror = true;
        LowerBody = new ModelRenderer(this, 32, 5).setTextureSize(64, 32);;
        LowerBody.addBox(-4F, -0.5F, -4.5F, 8, 8, 8);
        LowerBody.setRotationPoint(0F, 9F, 2F);
        setRotation(LowerBody, 0F, 0F, 0F);
        LowerBody.mirror = true;
        Thigh1 = new ModelRenderer(this, 24, 2).setTextureSize(64, 32);;
        Thigh1.addBox(0F, -1.5F, -2.5F, 3, 5, 5);
        Thigh1.setRotationPoint(4F, 13F, 3F);
        setRotation(Thigh1, 0F, 0F, 0F);
        Thigh1.mirror = true;        
        Thigh2 = new ModelRenderer(this, 24, 2).setTextureSize(64, 32);;
        Thigh2.addBox(-3F, -1.5F, -2.5F, 3, 5, 5);
        Thigh2.setRotationPoint(-4F, 13F, 3F);
        setRotation(Thigh2, 0F, 0F, 0F);
        Thigh2.mirror = true;
        Tail1 = new ModelRenderer(this, 44, 0).setTextureSize(64, 32);;
        Tail1.addBox(-2F, -0.5F, 0F, 4, 4, 6);
        Tail1.setRotationPoint(0F, 9F, 5.5F);
        setRotation(Tail1, 0F, 0F, 0F);
        Tail1.mirror = true;
        Tail2 = new ModelRenderer(this, 36, 0).setTextureSize(64, 32);;
        Tail2.addBox(-1F, -0.5F, 0F, 2, 2, 12);
        Tail2.setRotationPoint(0F, 10F, 11.5F);
        setRotation(Tail2, 0F, 0F, 0F);
        Tail2.mirror = true;
        
        /*Heel1 = new ModelRenderer(this, 24, 12).setTextureSize(64, 32);
        Heel1.addBox(-1F, 0F, -1F, 2, 7, 2);
        Heel1.setRotationPoint(5F, 16F, 5F);
        setRotation(Heel1, -0.3717861F, 0F, 0F);
        Heel1.mirror = true;        
        Heel2 = new ModelRenderer(this, 24, 12).setTextureSize(64, 32);
        Heel2.addBox(-1F, 0F, -1F, 2, 7, 2);
        Heel2.setRotationPoint(-5F, 16F, 5F);
        setRotation(Heel2, -0.3717861F, 0F, 0F);
        Heel2.mirror = true;
        Feet1 = new ModelRenderer(this, 35, 21).setTextureSize(64, 32);
        Feet1.addBox(-1F, 0F, -3F, 3, 2, 4);
        Feet1.setRotationPoint(5F, 22F, 3F);
        setRotation(Feet1, 0F, 0F, 0F);
        Feet1.mirror = true;
        Feet2 = new ModelRenderer(this, 35, 21).setTextureSize(64, 32);
        Feet2.addBox(-2F, 0F, -3F, 3, 2, 4);
        Feet2.setRotationPoint(-5F, 22F, 3F);
        setRotation(Feet2, 0F, 0F, 0F);
        Feet2.mirror = true;*/
        
        Heel1 = new ModelRenderer(this, 24, 12).setTextureSize(64, 32);
        Heel1.addBox(0F, 2F, 2F, 2, 7, 2);
        Heel1.setRotationPoint(4F, 13F, 3F);
        setRotation(Heel1, -0.3717861F, 0F, 0F);
        Heel1.mirror = true;        
        Heel2 = new ModelRenderer(this, 24, 12).setTextureSize(64, 32);
        Heel2.addBox(-2F, 2F, 2F, 2, 7, 2);
        Heel2.setRotationPoint(-4F, 13F, 3F);
        setRotation(Heel2, -0.3717861F, 0F, 0F);
        Heel2.mirror = true;
        Feet1 = new ModelRenderer(this, 35, 21).setTextureSize(64, 32);
        Feet1.addBox(0F, 9F, -3F, 3, 2, 4);
        Feet1.setRotationPoint(4F, 13F, 3F);
        setRotation(Feet1, 0F, 0F, 0F);
        Feet1.mirror = true;
        Feet2 = new ModelRenderer(this, 35, 21).setTextureSize(64, 32);
        Feet2.addBox(-3F, 9F, -3F, 3, 2, 4);
        Feet2.setRotationPoint(-4F, 13F, 3F);
        setRotation(Feet2, 0F, 0F, 0F);
        Feet2.mirror = true;
        
        UpperArm1 = new ModelRenderer(this, 14, 10).setTextureSize(64, 32);
        UpperArm1.addBox(0F, -1F, -2F, 2, 3, 3);
        UpperArm1.setRotationPoint(3F, 11F, -5.5F);
        setRotation(UpperArm1, 0F, 0F, 0F);
        UpperArm1.mirror = true;
        UpperArm2 = new ModelRenderer(this, 14, 10).setTextureSize(64, 32);;
        UpperArm2.addBox(-2F, -1F, -2F, 2, 3, 3);
        UpperArm2.setRotationPoint(-3F, 11F, -5.5F);
        setRotation(UpperArm2, 0F, 0F, 0F);
        UpperArm2.mirror = true;
        LowerArm1 = new ModelRenderer(this, 0, 10).setTextureSize(64, 32);;
        LowerArm1.addBox(-1F, 0F, 0F, 2, 4, 2);
        LowerArm1.setRotationPoint(4F, 11.5F, -5.5F);
        setRotation(LowerArm1, -0.2602503F, 0F, 0F);
        LowerArm1.mirror = true;
        LowerArm2 = new ModelRenderer(this, 0, 10).setTextureSize(64, 32);;
        LowerArm2.addBox(-1F, 0F, 0F, 2, 4, 2);
        LowerArm2.setRotationPoint(-4F, 11.5F, -5.5F);
        setRotation(LowerArm2, -0.2602503F, 0F, 0F);
        LowerArm2.mirror = true;
        Frill2 = new ModelRenderer(this, 24, 27).setTextureSize(64, 32);;
        Frill2.addBox(-1F, 3F, 0F, 9, 5, 0);
        Frill2.setRotationPoint(0F, 4F, -10F);
        setRotation(Frill2, 0F, 3.141593F, -1.570796F);
        Frill2.mirror = true;
        Frill1 = new ModelRenderer(this, 24, 27).setTextureSize(64, 32);;
        Frill1.addBox(-1F, 3F, 0F, 9, 5, 0);
        Frill1.setRotationPoint(0F, 4F, -10F);
        setRotation(Frill1, 0F, 0F, 1.570796F);
        Frill1.mirror = true;
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
      {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5,((EntityDinosaurce)entity).isModelized());
        Head.render(f5);
        Jaw1.render(f5);
        Jaw2.render(f5);
        Crest.render(f5);
        Crest2.render(f5);
        Crest3.render(f5);
        Crest4.render(f5);
        Neck.render(f5);
        UpperBody.render(f5);
        LowerBody.render(f5);
        Thigh1.render(f5);
        Thigh2.render(f5);
        Tail1.render(f5);
        Tail2.render(f5);
        Heel1.render(f5);
        Heel2.render(f5);
        Feet1.render(f5);
        Feet2.render(f5);
        UpperArm1.render(f5);
        UpperArm2.render(f5);
        LowerArm1.render(f5);
        LowerArm2.render(f5);
        Frill2.render(f5);
        Frill1.render(f5);
      }


      
      private void setRotation(ModelRenderer model, float x, float y, float z)
      {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
      }
  	public void OpenMouth(int Steps){
		if (Jaw2.rotateAngleX<0.4363323F) Jaw2.rotateAngleX+=(0.4363323F/Steps);
		else Jaw2.rotateAngleX=0.4363323F;
		//mod_Fossil.ShowMessage(new StringBuilder().append(head3_down.rotateAngleX).toString());
	}
	public void CloseMouth(int Steps){
		if (Jaw2.rotateAngleX>0) Jaw2.rotateAngleX-=(0.4363323F/Steps);
		else Jaw2.rotateAngleX=0;
		//mod_Fossil.ShowMessage(new StringBuilder().append(head3_down.rotateAngleX).toString());
	}

	@Override
	protected void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, boolean modelized) {
		if (modelized)return;
			//right leg
			Thigh2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
			Heel2.rotateAngleX=MathHelper.cos(f * 0.6662F) * 1.4F * f1-0.372F;
			Feet2.rotateAngleX=MathHelper.cos(f * 0.6662F) * 1.4F * f1;

			//left leg
			Thigh1.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
			Heel1.rotateAngleX =MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1-0.372F;
			Feet1.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		
	}
}
