package net.minecraft.src;
//Exported java file
//Keep in mind that you still need to fill in some blanks
// - ZeuX

public class ModelNautilus extends ModelBase
{
	public ModelNautilus()
	{
		Shell = new ModelRenderer(this,0, 12);
		Shell.addBox(-2F, -5F, -5F, 4, 10, 10, 0F);
		Shell.setRotationPoint(2F, 1F, 1F);
		Shell.rotateAngleX = 0F;
		Shell.rotateAngleY = 0F;
		Shell.rotateAngleZ = 0F;
		Shell.mirror = false;
		Head = new ModelRenderer(this,0, 0);
		Head.addBox(-3F, 1F, -7F, 6, 6, 6, 0F);
		Head.setRotationPoint(2F, 1F, 1F);
		Head.rotateAngleX = -0.8588527F;
		Head.rotateAngleY = 0F;
		Head.rotateAngleZ = 0F;
		Head.mirror = false;
		Tech1 = new ModelRenderer(this,0, 12);
		Tech1.addBox(-1F, -1F, -2F, 1, 8, 1, 0F);
		Tech1.setRotationPoint(2F, 2F, -6F);
		Tech1.rotateAngleX = 0F;
		Tech1.rotateAngleY = 0F;
		Tech1.rotateAngleZ = 0F;
		Tech1.mirror = false;
		Tech2 = new ModelRenderer(this,0, 12);
		Tech2.addBox(-2F, 0F, -1F, 1, 8, 1, 0F);
		Tech2.setRotationPoint(2F, 2F, -6F);
		Tech2.rotateAngleX = 0F;
		Tech2.rotateAngleY = 0F;
		Tech2.rotateAngleZ = 0F;
		Tech2.mirror = false;
		Tech3 = new ModelRenderer(this,0, 12);
		Tech3.addBox(2F, 1F, 0F, 1, 8, 1, 0F);
		Tech3.setRotationPoint(1F, 2F, -6F);
		Tech3.rotateAngleX = 0F;
		Tech3.rotateAngleY = 0F;
		Tech3.rotateAngleZ = 0F;
		Tech3.mirror = false;
		Tech4 = new ModelRenderer(this,0, 12);
		Tech4.addBox(1F, 0F, -1F, 1, 8, 1, 0F);
		Tech4.setRotationPoint(2F, 2F, -6F);
		Tech4.rotateAngleX = 0F;
		Tech4.rotateAngleY = 0F;
		Tech4.rotateAngleZ = 0F;
		Tech4.mirror = false;
		Tech5 = new ModelRenderer(this,0, 12);
		Tech5.addBox(0F, -1F, -2F, 1, 8, 1, 0F);
		Tech5.setRotationPoint(2F, 2F, -6F);
		Tech5.rotateAngleX = 0F;
		Tech5.rotateAngleY = 0F;
		Tech5.rotateAngleZ = 0F;
		Tech5.mirror = false;
		Tech6 = new ModelRenderer(this,0, 12);
		Tech6.addBox(-2F, 1F, 0F, 1, 8, 1, 0F);
		Tech6.setRotationPoint(2F, 2F, -6F);
		Tech6.rotateAngleX = 0F;
		Tech6.rotateAngleY = 0F;
		Tech6.rotateAngleZ = 0F;
		Tech6.mirror = false;
	}
	
	public void render(Entity entity,float f, float f1, float f2, float f3, float f4, float f5)
	{
		setRotationAngles(f, f1, f2, f3, f4, f5,entity);
		Shell.render(f5);
		Head.render(f5);
		Tech1.render(f5);
		Tech2.render(f5);
		Tech3.render(f5);
		Tech4.render(f5);
		Tech5.render(f5);
		Tech6.render(f5);
	}
	
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity par7Entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5,par7Entity);
		Tech1.rotateAngleX = 0.2F * MathHelper.sin(f2 * 0.3F + (float)1) + 0.4F;
		Tech2.rotateAngleX = 0.2F * MathHelper.sin(f2 * 0.3F + (float)2) + 0.4F;
		Tech3.rotateAngleX = 0.2F * MathHelper.sin(f2 * 0.3F + (float)3) + 0.4F;
		Tech4.rotateAngleX = 0.2F * MathHelper.sin(f2 * 0.3F + (float)4) + 0.4F;
		Tech5.rotateAngleX = 0.2F * MathHelper.sin(f2 * 0.3F + (float)5) + 0.4F;
		Tech6.rotateAngleX = 0.2F * MathHelper.sin(f2 * 0.3F + (float)6) + 0.4F;
	}
	
	//fields
	public ModelRenderer Shell;
	public ModelRenderer Head;
	public ModelRenderer Tech1;
	public ModelRenderer Tech2;
	public ModelRenderer Tech3;
	public ModelRenderer Tech4;
	public ModelRenderer Tech5;
	public ModelRenderer Tech6;
}
