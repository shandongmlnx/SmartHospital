package com.mlnx.agv.tp;

import java.nio.ByteBuffer;
import java.text.ParseException;

public interface Codec {

	void decode(ByteBuffer buf) throws ParseException;

	byte[] encode();

	void init();
}
