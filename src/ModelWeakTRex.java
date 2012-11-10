package net.minecraft.src;
//Exported java file
//Keep in mind that you still need to fill in some blanks
// - ZeuX

public class ModelWeakTRex extends ModelTRex
{

public ModelWeakTRex()
{
foot_R_1 = new ModelRenderer(this,40, 2);
foot_R_1.addBox(-4, -4, -4, 4, 8, 8, 0F);
foot_R_1.setRotationPoint(-2, 20, 5);

foot_R_1.rotateAngleX = 0F;
foot_R_1.rotateAngleY = 0F;
foot_R_1.rotateAngleZ = 0F;
foot_R_1.mirror = false;

head_1 = new ModelRenderer(this,0, 16);
head_1.addBox(-4, 0, -6, 8, 8, 6, 0F);
head_1.setRotationPoint(0, 16, -8);

head_1.rotateAngleX = -7.905835E-16F;
head_1.rotateAngleY = 0F;
head_1.rotateAngleZ = 0F;
head_1.mirror = false;

head_2 = new ModelRenderer(this,34, 18);
head_2.addBox(-4, 1, -11, 6, 6, 8, 0F);
head_2.setRotationPoint(1, 16, -8);

head_2.rotateAngleX = 0F;
head_2.rotateAngleY = 0F;
head_2.rotateAngleZ = 0F;
head_2.mirror = false;

head_3 = new ModelRenderer(this,13, 23);
head_3.addBox(-4, 6, -10, 4, 2, 7, 0F);
head_3.setRotationPoint(2, 16, -8);

head_3.rotateAngleX = 0F;
head_3.rotateAngleY = 0F;
head_3.rotateAngleZ = 0F;
head_3.mirror = false;

noumenon_1 = new ModelRenderer(this,4, 2);
noumenon_1.addBox(-3, 0, -10, 6, 8, 10, 0F);
noumenon_1.setRotationPoint(0, 13, -1);

noumenon_1.rotateAngleX = 0.3164194F;
noumenon_1.rotateAngleY = 0F;
noumenon_1.rotateAngleZ = 0F;
noumenon_1.mirror = false;

hand_L = new ModelRenderer(this,34, 0);
hand_L.addBox(0, -1, -3, 2, 2, 3, 0F);
hand_L.setRotationPoint(2, 22, -4);

hand_L.rotateAngleX = 1.039664F;
hand_L.rotateAngleY = 0F;
hand_L.rotateAngleZ = 0F;
hand_L.mirror = false;

hand_R = new ModelRenderer(this,34, 0);
hand_R.addBox(-2, -1, -3, 2, 2, 3, 0F);
hand_R.setRotationPoint(-2, 22, -4);

hand_R.rotateAngleX = 0.994461F;
hand_R.rotateAngleY = 0F;
hand_R.rotateAngleZ = 0F;
hand_R.mirror = false;

foot_L_1 = new ModelRenderer(this,40, 2);
foot_L_1.addBox(0, -4, -4, 4, 8, 8, 0F);
foot_L_1.setRotationPoint(2, 19, 5);

foot_L_1.rotateAngleX = 0F;
foot_L_1.rotateAngleY = 0F;
foot_L_1.rotateAngleZ = 0F;
foot_L_1.mirror = false;

noumenon_2 = new ModelRenderer(this,1, 0);
noumenon_2.addBox(-5, 0, -6, 8, 11, 12, 0F);
noumenon_2.setRotationPoint(1, 13, 4);

noumenon_2.rotateAngleX = 0F;
noumenon_2.rotateAngleY = 0F;
noumenon_2.rotateAngleZ = 0F;
noumenon_2.mirror = false;

foot_R_3 = new ModelRenderer(this,36, 0);
foot_R_3.addBox(-2, 0, -6, 3, 2, 8, 0F);
foot_R_3.setRotationPoint(-6, 23, 4);

foot_R_3.rotateAngleX = 0F;
foot_R_3.rotateAngleY = 0F;
foot_R_3.rotateAngleZ = 0F;
foot_R_3.mirror = false;

foot_L_3 = new ModelRenderer(this,36, 0);
foot_L_3.addBox(-1, 0, -6, 3, 2, 8, 0F);
foot_L_3.setRotationPoint(6, 23, 4);

foot_L_3.rotateAngleX = 0F;
foot_L_3.rotateAngleY = 0F;
foot_L_3.rotateAngleZ = 0F;
foot_L_3.mirror = false;

foot_L_2 = new ModelRenderer(this,0, 9);
foot_L_2.addBox(-1, -3, 0, 2, 8, 3, 0F);
foot_L_2.setRotationPoint(4, 25, 4);

foot_L_2.rotateAngleX = 1.570796F;
foot_L_2.rotateAngleY = 0F;
foot_L_2.rotateAngleZ = 0F;
foot_L_2.mirror = false;

foot_R_2 = new ModelRenderer(this,0, 9);
foot_R_2.addBox(-1, -3, 0, 2, 8, 3, 0F);
foot_R_2.setRotationPoint(-4, 25, 4);

foot_R_2.rotateAngleX = 1.570796F;
foot_R_2.rotateAngleY = 0F;
foot_R_2.rotateAngleZ = 0F;
foot_R_2.mirror = false;

noumenon_3 = new ModelRenderer(this,4, 4);
noumenon_3.addBox(-3, 0, 0, 6, 5, 10, 0F);
noumenon_3.setRotationPoint(0, 14, 9);

noumenon_3.rotateAngleX = -0.3616222F;
noumenon_3.rotateAngleY = 0.4972305F;
noumenon_3.rotateAngleZ = 0F;
noumenon_3.mirror = false;

noumenon_4 = new ModelRenderer(this,5, 2);
noumenon_4.addBox(-2, 0, 0, 4, 3, 10, 0F);
noumenon_4.setRotationPoint(2, 17, 16);

noumenon_4.rotateAngleX = -0.4520277F;
noumenon_4.rotateAngleY = 1.6273F;
noumenon_4.rotateAngleZ = 0F;
noumenon_4.mirror = false;

noumenon_5 = new ModelRenderer(this,10, 6);
noumenon_5.addBox(-1, 0, 0, 2, 2, 6, 0F);
noumenon_5.setRotationPoint(12, 22, 11);

noumenon_5.rotateAngleX = 0F;
noumenon_5.rotateAngleY = -0.4520277F;
noumenon_5.rotateAngleZ = 0F;
noumenon_5.mirror = false;


}
public void render(Entity entity,float f, float f1, float f2, float f3, float f4, float f5)
{
//super.render(float f, float f1, float f2, float f3, float f4, float f5)
setRotationAngles(f, f1, f2, f3, f4, f5,((EntityDinosaurce)entity).isModelized());
foot_R_1.render(f5);
head_1.render(f5);
head_2.render(f5);
head_3.render(f5);
noumenon_1.render(f5);
hand_L.render(f5);
hand_R.render(f5);
foot_L_1.render(f5);
noumenon_2.render(f5);
foot_R_3.render(f5);
foot_L_3.render(f5);
foot_L_2.render(f5);
foot_R_2.render(f5);
noumenon_3.render(f5);
noumenon_4.render(f5);
noumenon_5.render(f5);

}



//fields
public ModelRenderer foot_R_1;
public ModelRenderer head_1;
public ModelRenderer head_2;
public ModelRenderer head_3;
public ModelRenderer noumenon_1;
public ModelRenderer hand_L;
public ModelRenderer hand_R;
public ModelRenderer foot_L_1;
public ModelRenderer noumenon_2;
public ModelRenderer foot_R_3;
public ModelRenderer foot_L_3;
public ModelRenderer foot_L_2;
public ModelRenderer foot_R_2;
public ModelRenderer noumenon_3;
public ModelRenderer noumenon_4;
public ModelRenderer noumenon_5;

}
