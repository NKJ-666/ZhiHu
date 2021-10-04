package com.example.zhihu.util;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;

public class GetFirstImageUtil {
    public static Bitmap getFirstImage(String path){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        return retriever.getFrameAtTime();
    }
}
