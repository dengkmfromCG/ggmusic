package com.gdut.dkmfromcg.ggmusic.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by dkmFromCG on 2017/10/27.
 * function:
 */

public class RxFileTool {

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
