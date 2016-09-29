package com.oubowu.jsoupdemo.utils;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by Oubowu on 2016/9/29 2:20.
 */
public class FileUtils {

    // 保存文件
    public static File saveFile(ResponseBody response, String destFileDir, String destFileName) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.byteStream();
            final long total = response.contentLength();

            final Boolean available = memoryAvailable(total);
            if (null == available) {
                return null;
            } else if (!available) {
                return null;
            }

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                // LogUtil.e("TAG", "DownloadManager-88行-saveFile(): " + 100 * finalSum * 1.0f / total);
                //                publishProgress((int) (100 * finalSum * 1.0f / total));
                Log.e("TAG", "PhotoModel-69行-saveFile(): " + (int) (100 * finalSum * 1.0f / total));
            }
            fos.flush();

            return file;

        } finally {
            response.close();
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }

    }

    /**
     * 删除文件
     *
     * @param destFileDir
     * @param destFileName
     */
    public static void deleteFile(String destFileDir, String destFileName) {
        File file = new File(destFileDir, destFileName);
        if (file.exists()) {
            Log.e("TAG", "DownloadManager-186行-deleteFile(): " + file.getAbsolutePath());
            if (file.delete()) {
                Log.e("TAG", "DownloadManager-188行-deleteFile(): " + "删除成功");
            }
        }
    }

    /**
     * 内存是否可用
     *
     * @param fileSize
     * @return
     */
    public static Boolean memoryAvailable(long fileSize) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // SD卡挂载了检测空间是否够用
            return getUsableSpace(new File(Environment.getExternalStorageDirectory().getPath())) > fileSize;
        } else {
            // SD卡没有挂载
            return null;
        }
    }

    /**
     * 获取可用内存
     *
     * @param path
     * @return
     */
    public static long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        final StatFs statFs = new StatFs(path.getPath());
        return statFs.getBlockSize() * statFs.getAvailableBlocks();
    }

}
