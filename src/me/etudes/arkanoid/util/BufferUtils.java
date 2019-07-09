package me.etudes.arkanoid.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferUtils {

    private BufferUtils() {
    }

    public static FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static ByteBuffer createByteBuffer(byte[] data) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(data.length)
                .order(ByteOrder.nativeOrder());
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}
