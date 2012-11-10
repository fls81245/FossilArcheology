package net.minecraft.src;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class FossilKeyHandler extends KeyHandler {
	public static KeyBinding jumpKeyBinding=new KeyBinding("jumpKey",Keyboard.KEY_O);
	public static KeyBinding forwardKeyBinding=new KeyBinding("forwardKey",Keyboard.KEY_I);
	public static KeyBinding backwardKeyBinding=new KeyBinding("backwardKey",Keyboard.KEY_K);
	public static KeyBinding rightturnKeyBinding=new KeyBinding("rightturnKey",Keyboard.KEY_L);
	public static KeyBinding leftturnKeyBinding=new KeyBinding("leftturnKey",Keyboard.KEY_J);
	public static boolean[] pressedKey=new boolean[]{false,false,false,false,false};
	public FossilKeyHandler() {
		super(new KeyBinding[]{jumpKeyBinding,forwardKeyBinding,backwardKeyBinding,rightturnKeyBinding,leftturnKeyBinding}, new boolean[]{true,true,true,true,true});
	}
	public boolean isJumpPressed(){
		return pressedKey[0];
	}
	public boolean isForwardPressed(){
		return pressedKey[1];
	}
	public boolean isBackwardPressed(){
		return pressedKey[2];
	}
	public boolean isRightTurnPressed(){
		return pressedKey[3];
	}
	public boolean isLeftTurnPressed(){
		return pressedKey[4];
	}
	@Override
	public String getLabel() {
		return "FossilMotionHandler";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		if (kb.keyCode==jumpKeyBinding.keyCode) pressedKey[0]=true;
		if (kb.keyCode==forwardKeyBinding.keyCode) pressedKey[1]=true;
		if (kb.keyCode==backwardKeyBinding.keyCode) pressedKey[2]=true;
		if (kb.keyCode==rightturnKeyBinding.keyCode) pressedKey[3]=true;
		if (kb.keyCode==leftturnKeyBinding.keyCode) pressedKey[4]=true;
			/*switch(kb.keyCode){
			case Keyboard.KEY_SPACE:
				pressedKey[0]=true;
				break;
			case Keyboard.KEY_W:
				pressedKey[1]=true;
				break;
			case Keyboard.KEY_S:
				pressedKey[2]=true;
				break;
			case Keyboard.KEY_D:
				pressedKey[3]=true;
				break;
			case Keyboard.KEY_A:
				pressedKey[4]=true;
				break;

			}*/

	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		if (kb.keyCode==jumpKeyBinding.keyCode) pressedKey[0]=false;
		if (kb.keyCode==forwardKeyBinding.keyCode) pressedKey[1]=false;
		if (kb.keyCode==backwardKeyBinding.keyCode) pressedKey[2]=false;
		if (kb.keyCode==rightturnKeyBinding.keyCode) pressedKey[3]=false;
		if (kb.keyCode==leftturnKeyBinding.keyCode) pressedKey[4]=false;

	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

}
