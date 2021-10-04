package com.example.zhihu.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class GetRealUriUtil {

    public static String getImageRealPath(Context context, Uri uri){
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)){
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaUri(uri)){
                String id = documentId.split(":")[1];
                String type = documentId.split(":")[0];
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                if (type.equals("image"))
                    filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
                else if (type.equals("video"))
                    filePath = getDataColumn(context, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            }else if (isDownloadDocument(uri)){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            filePath = getDataColumn(context, uri, null, null);
        }else if ("file".equals(uri.getScheme())){
            filePath = uri.getPath();
        }
        return filePath;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    private static boolean isMediaUri(Uri uri){
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadDocument(Uri uri){
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
