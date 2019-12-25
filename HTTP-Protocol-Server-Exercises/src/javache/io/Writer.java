package javache.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public final class Writer {
    private Writer () {}

    public static void writeBytes(byte[] byteData, OutputStream outputStream) throws IOException {
        DataOutputStream buffer = new DataOutputStream(outputStream);

        buffer.write(byteData);
        buffer.flush();
    }
}