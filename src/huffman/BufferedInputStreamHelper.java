package huffman;

import java.io.BufferedInputStream;
import java.io.IOException;

public class BufferedInputStreamHelper {

    private BufferedInputStream bufferedInputStream;
    private int cursor;
    private byte[] buffer;

    public BufferedInputStreamHelper(BufferedInputStream bufferedInputStream){
        this.bufferedInputStream = bufferedInputStream;
        cursor = 1024;
    }

    public int read() throws IOException {
        int ret = 0;
        if(cursor >= 1024) {
            buffer = new byte[1024];
            ret = this.bufferedInputStream.read(buffer);
            if (ret == -1) return -1;
            cursor = 0;
        }


        ret = buffer[cursor] & 0x000000FF;
        cursor++;
        return ret;
    }
}
