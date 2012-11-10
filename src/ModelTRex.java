package net.minecraft.src;
//Exported java file
//Keep in mind that you still need to fill in some blanks
// - ZeuX

public class ModelTRex extends ModelDinosaurs
{
final float SwingAngle=0.174533F;
public ModelTRex()
{
	noumenon_1 = new ModelRenderer(this,4, 2);
	noumenon_1.addBox(-3, 0, -10, 6, 8, 10, 0F);
	noumenon_1.setRotationPoint(0, 9, -1);

	noumenon_1.rotateAngleX = -0.9948377F;
	noumenon_1.rotateAngleY = 0F;
	noumenon_1.rotateAngleZ = 0F;
	noumenon_1.mirror = false;

	noumenon_2 = new ModelRenderer(this,1, 0);
	noumenon_2.addBox(-5, 0, -6, 8, 11, 12, 0F);
	noumenon_2.setRotationPoint(1, 6, 2);

	noumenon_2.rotateAngleX = -0.4068249F;
	noumenon_2.rotateAngleY = 0F;
	noumenon_2.rotateAngleZ = 0F;
	noumenon_2.mirror = false;

	hand_R = new ModelRenderer(this,34, 0);
	hand_R.addBox(-2, -1, -3, 2, 2, 3, 0F);
	hand_R.setRotationPoint(-2, 10, -9);

	hand_R.rotateAngleX = 0.6328388F;
	hand_R.rotateAngleY = 0F;
	hand_R.rotateAngleZ = 0F;
	hand_R.mirror = false;

	hand_L = new ModelRenderer(this,34, 0);
	hand_L.addBox(0, -1, -3, 2, 2, 3, 0F);
	hand_L.setRotationPoint(2, 10, -9);

	hand_L.rotateAngleX = 0.6328388F;
	hand_L.rotateAngleY = 0F;
	hand_L.rotateAngleZ = 0F;
	hand_L.mirror = false;

	foot_R_1 = new ModelRenderer(this,40, 2);
	foot_R_1.addBox(-4, -4, -4, 4, 8, 8, 0F);
	foot_R_1.setRotationPoint(-4, 14, 2);

	foot_R_1.rotateAngleX = 0F;
	foot_R_1.rotateAngleY = 0F;
	foot_R_1.rotateAngleZ = 0F;
	foot_R_1.mirror = false;

	noumenon_3 = new ModelRenderer(this,4, 4);
	noumenon_3.addBox(-3, 0, 0, 6, 5, 10, 0F);
	noumenon_3.setRotationPoint(0, 8, 6);

	noumenon_3.rotateAngleX = -0.7684471F;
	noumenon_3.rotateAngleY = 0F;
	noumenon_3.rotateAngleZ = 0F;
	noumenon_3.mirror = false;

	noumenon_4 = new ModelRenderer(this,5, 2);
	noumenon_4.addBox(-2, 0, 0, 4, 3, 10, 0F);
	noumenon_4.setRotationPoint(0, 15, 12);

	noumenon_4.rotateAngleX = -0.5424333F;
	noumenon_4.rotateAngleY = 0F;
	noumenon_4.rotateAngleZ = 0F;
	noumenon_4.mirror = false;

	foot_R_2 = new ModelRenderer(this,0, 9);
foot_R_2.addBox(-2, 0, 3, 2, 8, 3, 0F);
foot_R_2.setRotationPoint(-4, 14, 2);

	foot_R_2.rotateAngleX = -0.6108652F;
	foot_R_2.rotateAngleY = 0F;
	foot_R_2.rotateAngleZ = 0F;
	foot_R_2.mirror = false;

	foot_R_3 = new ModelRenderer(this,36, 0);
foot_R_3.addBox(-3, 8, -5, 3, 2, 8, 0F);
foot_R_3.setRotationPoint(-4, 14, 2);

	foot_R_3.rotateAngleX = 0F;
	foot_R_3.rotateAngleY = 0F;
	foot_R_3.rotateAngleZ = 0F;
	foot_R_3.mirror = false;

	foot_L_1 = new ModelRenderer(this,40, 2);
	foot_L_1.addBox(0, -4, -4, 4, 8, 8, 0F);
foot_L_1.setRotationPoint(4, 14, 2);

	foot_L_1.rotateAngleX = 0F;
	foot_L_1.rotateAngleY = 0F;
	foot_L_1.rotateAngleZ = 0F;
	foot_L_1.mirror = false;

	foot_L_2 = new ModelRenderer(this,0, 9);
foot_L_2.addBox(0, 0, 3, 2, 8, 3, 0F);
foot_L_2.setRotationPoint(4, 14, 2);

	foot_L_2.rotateAngleX = -0.6108652F;
	foot_L_2.rotateAngleY = 0F;
	foot_L_2.rotateAngleZ = 0F;
	foot_L_2.mirror = false;

	foot_L_3 = new ModelRenderer(this,36, 0);
foot_L_3.addBox(0, 8, -5, 3, 2, 8, 0F);
foot_L_3.setRotationPoint(4, 14, 2);

	foot_L_3.rotateAngleX = 0F;
	foot_L_3.rotateAngleY = 0F;
	foot_L_3.rotateAngleZ = 0F;
	foot_L_3.mirror = false;

	noumenon_5 = new ModelRenderer(this,10, 6);
	noumenon_5.addBox(-1, 0, 0, 2, 2, 6, 0F);
	noumenon_5.setRotationPoint(0, 20, 19);

	noumenon_5.rotateAngleX = -0.3616222F;
	noumenon_5.rotateAngleY = 0F;
	noumenon_5.rotateAngleZ = 0F;
	noumenon_5.mirror = false;

	head_1 = new ModelRenderer(this,0, 16);
	head_1.addBox(-4, 0, -6, 8, 8, 6, 0F);
	head_1.setRotationPoint(0, 0, -8);

	head_1.rotateAngleX = -7.905835E-16F;
	head_1.rotateAngleY = 0F;
	head_1.rotateAngleZ = 0F;
	head_1.mirror = false;

	head_2 = new ModelRenderer(this,34, 18);
	head_2.addBox(-4, 1, -11, 6, 6, 8, 0F);
	head_2.setRotationPoint(1, 0, -8);

	head_2.rotateAngleX = 0F;
	head_2.rotateAngleY = 0F;
	head_2.rotateAngleZ = 0F;
	head_2.mirror = false;

	head_3 = new ModelRenderer(this,13, 23);
	head_3.addBox(-4, 6, -10, 4, 2, 7, 0F);
	head_3.setRotationPoint(2, 1, -8);

	head_3.rotateAngleX = 0F;
	head_3.rotateAngleY = 0F;
	head_3.rotateAngleZ = 0F;
	head_3.mirror = false;

	New_Shape10 = new ModelRenderer(this,0, 0);
	New_Shape10.addBox(-1, -2, -2, 2, 2, 3, 0F);
	New_Shape10.setRotationPoint(0, 10, -8);

	New_Shape10.rotateAngleX = 2.181662F;
	New_Shape10.rotateAngleY = 0F;
	New_Shape10.rotateAngleZ = 0F;
	New_Shape10.mirror = false;


}
public void render(Entity entity,float f, float f1, float f2, float f3, float f4, float f5)
{
	//super.render(f,f1,f2, f3, float f4, float f5)
	setRotationAngles(f, f1, f2, f3, f4, f5,((EntityDinosaurce)entity).isModelized());
	noumenon_1.render(f5);
	noumenon_2.render(f5);
	hand_R.render(f5);
	hand_L.render(f5);
	foot_R_1.render(f5);
	noumenon_3.render(f5);
	noumenon_4.render(f5);
	foot_R_2.render(f5);
	foot_R_3.render(f5);
	foot_L_1.render(f5);
	foot_L_2.render(f5);
	foot_L_3.render(f5);
	noumenon_5.render(f5);
	head_1.render(f5);
	head_2.render(f5);
	head_3.render(f5);
	New_Shape10.render(f5);

}


private double GetDistance(ModelRenderer Box1,ModelRenderer Box2){
	return Math.sqrt(Math.pow((Box1.rotationPointX-Box2.rotationPointX),2)+Math.pow((Box1.rotationPointY-Box2.rotationPointY),2)+Math.pow((Box1.rotationPointZ-Box2.rotationPointZ),2));
}
/*private void SetRotateNewPoint(ModelRenderer Master,ModelRenderer Target,double MoffsetAngleX,double MoffsetAngleY,double MoffsetAngleZ){
	mod_Fossil.ShowMessage(new StringBuilder().append(Target.rotationPointX).append(",").append(Target.rotationPointY).append(",").append(Target.rotationPointZ).toString());
	double ResultX=Target.rotationPointX-Master.rotationPointX;
	double ResultY=Target.rotationPointY-Master.rotationPointY;
	double ResultZ=Target.rotationPointZ-Master.rotationPointZ;
	double tempX=ResultX;
	double tempY=ResultY;
	double tempZ=ResultZ;
	ResultX=ResultX*Math.cos(Master.rotateAngleY-MoffsetAngleY)+ResultZ*Math.sin(Master.rotateAngleY-MoffsetAngleY);
	ResultZ=(-tempX)*Math.sin(Master.rotateAngleY-MoffsetAngleY)+ResultZ*Math.cos(Master.rotateAngleY-MoffsetAngleY);
	tempX=ResultX;
	tempZ=ResultZ;
	ResultY=ResultY*Math.cos(Master.rotateAngleX-MoffsetAngleX)-ResultZ*Math.sin(Master.rotateAngleX-MoffsetAngleX);
	ResultZ=tempY*Math.sin(Master.rotateAngleX-MoffsetAngleX)+ResultZ*Math.cos(Master.rotateAngleX-MoffsetAngleX);
	tempY=ResultY;
	tempZ=ResultZ;
	ResultX=ResultX*Math.cos(Master.rotateAngleZ-MoffsetAngleZ)-ResultY*Math.sin(Master.rotateAngleZ-MoffsetAngleZ);
	ResultY=tempX*Math.sin(Master.rotateAngleZ-MoffsetAngleZ)+ResultY*Math.cos(Master.rotateAngleZ-MoffsetAngleZ);
	Target.setRotationPoint((float)(ResultX+Master.rotationPointX),(float)(ResultY+Master.rotationPointY),(float)(ResultZ+Master.rotationPointZ));
	mod_Fossil.ShowMessage(new StringBuilder().append(Target.rotationPointX).append(",").append(Target.rotationPointY).append(",").append(Target.rotationPointZ).toString());
	
}*/
public void RunPose(){
	RunPose(20.0F);
}
public void RunPose(float moves){
	if (noumenon_1.rotationPointY>7) noumenon_1.rotationPointY-=(2.0F/moves);
	else noumenon_1.rotationPointY=7;
	if (noumenon_1.rotationPointZ>-4) noumenon_1.rotationPointZ-=(3.0F/moves);
	else noumenon_1.rotationPointZ=-4;
	if (noumenon_1.rotateAngleX<0) noumenon_1.rotateAngleX+=(0.9948377F/moves);
	else noumenon_1.rotateAngleX=0;
	
	if (noumenon_2.rotationPointZ>0) noumenon_2.rotationPointZ-=(2.0F/moves);
	else noumenon_2.rotationPointZ=0;
	if (noumenon_2.rotateAngleX<0) noumenon_2.rotateAngleX+=(0.4068249F/moves);
	else noumenon_2.rotateAngleX=0;
	
	if (hand_R.rotationPointY<14) hand_R.rotationPointY+=(4.0F/moves);
	else hand_R.rotationPointY=14;
	
	if (hand_L.rotationPointY<14) hand_L.rotationPointY+=(4.0F/moves);
	else hand_L.rotationPointY=14;
	
	if (noumenon_3.rotateAngleX<-0.2260139F) noumenon_3.rotateAngleX+=((0.7684471F-0.2260139F)/moves);
	else noumenon_3.rotateAngleX=-0.2260139F;
	
	if (noumenon_4.rotationPointY>11) noumenon_4.rotationPointY-=(4.0F/moves);
	else noumenon_4.rotationPointY=11;
	if (noumenon_4.rotationPointZ<14) noumenon_4.rotationPointZ+=(2.0F/moves);
	else noumenon_4.rotationPointZ=14;
	if (noumenon_4.rotateAngleX<-0.1356083F) noumenon_4.rotateAngleX+=((0.5424333F-0.1356083F)/moves);
	else noumenon_4.rotateAngleX=-0.1356083F;
	
	if (noumenon_5.rotationPointY>13) noumenon_5.rotationPointY-=(7.0F/moves);
	else noumenon_5.rotationPointY=13;
	if (noumenon_5.rotationPointZ<22) noumenon_5.rotationPointZ+=(3.0F/moves);
	else noumenon_5.rotationPointZ=22;
	if (noumenon_5.rotateAngleX<0) noumenon_5.rotateAngleX+=(0.3616222F/moves);
	else noumenon_5.rotateAngleX=0;
	
	if (head_1.rotationPointY<7) head_1.rotationPointY+=(7.0F/moves);
	else head_1.rotationPointY=7;
	if (head_1.rotationPointZ>-14) head_1.rotationPointZ-=(6.0F/moves);
	else head_1.rotationPointZ=-14;
	//if (head_1.rotateAngleX<0.2260139F) head_1.rotateAngleX+=(0.2260139F/moves);
	//else head_1.rotateAngleX=0.2260139F;
	//mod_Fossil.ShowMessage(new StringBuilder().append(head_1.rotationPointX).append(",").append(head_1.rotationPointY).append(",").append(head_1.rotationPointZ).append(",").append(head_1.rotateAngleX).toString());
	
	if (head_2.rotationPointY<7) head_2.rotationPointY+=(7.0F/moves);
	else head_2.rotationPointY=7;
	if (head_2.rotationPointZ>-14) head_2.rotationPointZ-=(6.0F/moves);
	else head_2.rotationPointZ=-14;
	//if (head_2.rotateAngleX>-0.1356083F) head_2.rotateAngleX-=(0.1356083F/moves);
	//else head_2.rotateAngleX=-0.1356083F;
	
	
	if (head_3.rotationPointY<7) head_3.rotationPointY+=(6.0F/moves);
	else head_3.rotationPointY=7;
	if (head_3.rotationPointZ>-14) head_3.rotationPointZ-=(6.0F/moves);
	else head_3.rotationPointZ=-14;
	//if (head_3.rotateAngleX<0.4520277F) head_3.rotateAngleX+=(0.4520277F/moves);
	//else head_3.rotateAngleX=0.4520277F;
}
public void StandPose(){
	StandPose(20.0F);
}
public void StandPose(float moves){
	if (noumenon_1.rotationPointY<9) noumenon_1.rotationPointY+=(2.0F/moves);
	else noumenon_1.rotationPointY=9;
	if (noumenon_1.rotationPointZ<-1) noumenon_1.rotationPointZ+=(3.0F/moves);
	else noumenon_1.rotationPointZ=-1;
	if (noumenon_1.rotateAngleX>-0.9948377F) noumenon_1.rotateAngleX-=(0.9948377F/moves);
	else noumenon_1.rotateAngleX=-0.9948377F;
	
	if (noumenon_2.rotationPointZ<2) noumenon_2.rotationPointZ+=(2.0F/moves);
	else noumenon_2.rotationPointZ=2;
	if (noumenon_2.rotateAngleX>-0.4068249F) noumenon_2.rotateAngleX-=(0.4068249F/moves);
	else noumenon_2.rotateAngleX=-0.4068249F;
	
	if (hand_R.rotationPointY>10) hand_R.rotationPointY-=(4.0F/moves);
	else hand_R.rotationPointY=10;
	
	if (hand_L.rotationPointY>10) hand_L.rotationPointY-=(4.0F/moves);
	else hand_L.rotationPointY=10;
	
	if (noumenon_3.rotateAngleX>-0.7684471F) noumenon_3.rotateAngleX-=((0.7684471F-0.2260139F)/moves);
	else noumenon_3.rotateAngleX=-0.7684471F;
	
	if (noumenon_4.rotationPointY<15) noumenon_4.rotationPointY+=(4.0F/moves);
	else noumenon_4.rotationPointY=15;
	if (noumenon_4.rotationPointZ>12) noumenon_4.rotationPointZ-=(2.0F/moves);
	else noumenon_4.rotationPointZ=12;
	if (noumenon_4.rotateAngleX>-0.5424333F) noumenon_4.rotateAngleX-=((0.5424333F-0.1356083F)/moves);
	else noumenon_4.rotateAngleX=-0.5424333F;
	
	if (noumenon_5.rotationPointY<20) noumenon_5.rotationPointY+=(7.0F/moves);
	else noumenon_5.rotationPointY=20;
	if (noumenon_5.rotationPointZ>19) noumenon_5.rotationPointZ-=(3.0F/moves);
	else noumenon_5.rotationPointZ=19;
	if (noumenon_5.rotateAngleX<-0.3616222F) noumenon_5.rotateAngleX+=((0.3616222F-2.8368E-15F)/moves);
	else noumenon_5.rotateAngleX=-0.3616222F;
	
	if (head_1.rotationPointY>0) head_1.rotationPointY-=(7.0F/moves);
	else head_1.rotationPointY=0;
	if (head_1.rotationPointZ<-8) head_1.rotationPointZ+=(6.0F/moves);
	else head_1.rotationPointZ=-8;
	//if (head_1.rotateAngleX>0) head_1.rotateAngleX-=(0.2260139F/moves);
	//else head_1.rotateAngleX=0;
	
	if (head_2.rotationPointY>1) head_2.rotationPointY-=(7.0F/moves);
	else head_2.rotationPointY=1;
	if (head_2.rotationPointZ<-8) head_2.rotationPointZ+=(6.0F/moves);
	else head_2.rotationPointZ=-8;
	//if (head_2.rotateAngleX<0) head_2.rotateAngleX+=(0.1356083F/moves);
	//else head_2.rotateAngleX=0;
	
	if (head_3.rotationPointY>1) head_3.rotationPointY-=(6.0F/moves);
	else head_3.rotationPointY=1;
	if (head_3.rotationPointZ<-8) head_3.rotationPointZ+=(6.0F/moves);
	else head_3.rotationPointZ=-8;
	//if (head_3.rotateAngleX>0) head_3.rotateAngleX-=(0.4520277F/moves);
	//else head_3.rotateAngleX=0;
}
public void OpenMouth(float moves){
	if (head_1.rotateAngleX<0.2260139F) head_1.rotateAngleX+=(0.2260139F/moves);
	else head_1.rotateAngleX=0.2260139F;
	
	if (head_2.rotateAngleX>-0.1356083F) head_2.rotateAngleX-=(0.1356083F/moves);
	else head_2.rotateAngleX=-0.1356083F;
	
	if (head_3.rotateAngleX<0.4520277F) head_3.rotateAngleX+=(0.4520277F/moves);
	else head_3.rotateAngleX=0.4520277F;
}
public void CloseMouth(float moves){
	if (head_1.rotateAngleX>0) head_1.rotateAngleX-=(0.2260139F/moves);
	else head_1.rotateAngleX=0;
	
	if (head_2.rotateAngleX<0) head_2.rotateAngleX+=(0.1356083F/moves);
	else head_2.rotateAngleX=0;
	
	if (head_3.rotateAngleX>0) head_3.rotateAngleX-=(0.4520277F/moves);
	else head_3.rotateAngleX=0;
}
public boolean SwingHeadLeft(int step){
	int StepCount=0;
	if (head_3.rotateAngleZ>(-this.SwingAngle)) head_3.rotateAngleZ-=(this.SwingAngle/step);		
	else{
		StepCount++;
		head_3.rotateAngleZ=(-this.SwingAngle);
	}
	if (head_2.rotateAngleZ>(-this.SwingAngle)) head_2.rotateAngleZ-=(this.SwingAngle/step);		
	else{
		StepCount++;
		head_2.rotateAngleZ=(-this.SwingAngle);
	}
	if (head_1.rotateAngleZ>(-this.SwingAngle)) head_1.rotateAngleZ-=(this.SwingAngle/step);		
	else{
		StepCount++;
		head_1.rotateAngleZ=(-this.SwingAngle);
	}
	return (StepCount==3);
}
public boolean SwingHeadRight(int step){
	int StepCount=0;
	if (head_3.rotateAngleZ<this.SwingAngle) head_3.rotateAngleZ+=(this.SwingAngle/step);		
	else{
		StepCount++;
		head_3.rotateAngleZ=this.SwingAngle;
	}
	if (head_2.rotateAngleZ>this.SwingAngle) head_2.rotateAngleZ+=(this.SwingAngle/step);		
	else{
		StepCount++;
		head_2.rotateAngleZ=this.SwingAngle;
	}
	if (head_1.rotateAngleZ>this.SwingAngle) head_1.rotateAngleZ+=(this.SwingAngle/step);		
	else{
		StepCount++;
		head_1.rotateAngleZ=this.SwingAngle;
	}
	return (StepCount==3);
}
public void SwingHeadBack(){
	head_1.rotateAngleZ=0;
	head_2.rotateAngleZ=0;
	head_3.rotateAngleZ=0;
}
//fields
public ModelRenderer noumenon_1;
public ModelRenderer noumenon_2;
public ModelRenderer hand_R;
public ModelRenderer hand_L;
public ModelRenderer foot_R_1;
public ModelRenderer noumenon_3;
public ModelRenderer noumenon_4;
public ModelRenderer foot_R_2;
public ModelRenderer foot_R_3;
public ModelRenderer foot_L_1;
public ModelRenderer foot_L_2;
public ModelRenderer foot_L_3;
public ModelRenderer noumenon_5;
public ModelRenderer head_1;
public ModelRenderer head_2;
public ModelRenderer head_3;
public ModelRenderer New_Shape10;
@Override
protected void setRotationAngles(float f, float f1, float f2, float f3,
		float f4, float f5, boolean modelized) {
	if (modelized) return;
	//HeadYaw
	head_1.rotateAngleY = -f3 / 57.29578F;
	head_2.rotateAngleY = -f3 / 57.29578F;
	head_3.rotateAngleY = -f3 / 57.29578F;
	//right leg
	foot_R_1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
	foot_R_2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1-0.6108652F;
	foot_R_3.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
	//mod_Fossil.ShowMessage(new StringBuilder().append(f).append(",").append(f1).toString());
	//left leg
	foot_L_1.rotateAngleX = MathHelper.cos(f * 0.6662F+ 3.141593F) * 1.4F * f1;
	foot_L_2.rotateAngleX = MathHelper.cos(f * 0.6662F+ 3.141593F) * 1.4F * f1-0.6108652F;
	foot_L_3.rotateAngleX = MathHelper.cos(f * 0.6662F+ 3.141593F) * 1.4F * f1;
	if (Math.abs(foot_R_1.rotateAngleX)>=0.174532F){
		RunPose();
	}else{
		StandPose();
	}
	
}

}
