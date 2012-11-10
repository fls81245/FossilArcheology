package net.minecraft.src;

import cpw.mods.fml.common.network.IChatListener;

public class FossilMessageHandler implements IChatListener {

	@Override
	public Packet3Chat serverChat(NetHandler handler, Packet3Chat message) {
		return message;
	}

	@Override
	public Packet3Chat clientChat(NetHandler handler, Packet3Chat message) {
		return message;
	}

}
