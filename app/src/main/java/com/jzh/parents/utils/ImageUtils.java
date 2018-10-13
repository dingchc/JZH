package com.jzh.parents.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author ding
 *         图片获取路径在4.4以上跟更早版本不一样。
 */
public class ImageUtils {

    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        // other
        else {
            return getDataColumn(context, uri, null, null);
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    public static boolean checkValidImage(Context context, Uri contentURI) {

        boolean ret = true;

        InputStream imageStream = null;
        Bitmap selectedImage = null;
        try {
            imageStream = context.getContentResolver()
                    .openInputStream(contentURI);
            Rect rect = new Rect(0, 0, 20, 20);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 20;

            selectedImage = BitmapFactory.decodeStream(imageStream, rect,
                    options);
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
        }

        if (selectedImage != null) {
            if (!selectedImage.isRecycled()) {
                selectedImage.recycle();
            }

        } else {
            ret = false;
        }

        return ret;
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url 路径
     * @return 图片
     */
    public static Bitmap getBitmap(String url) {

        Bitmap bitmap = null;

        try {
            URL fileUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    /**
     * 读入路径图片，根据目前的宽、高，加载新的图片，并存储文件
     *
     * @param path       路径
     * @param viewWidth  宽度
     * @param viewHeight 高度
     * @return 图片
     */
    public static String saveBitmapToFile(String path, int viewWidth, int viewHeight) {

        if (viewWidth <= 0 || viewHeight <= 0 || TextUtils.isEmpty(path)) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = computeSampleSize(options, viewWidth, viewHeight);

        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        OutputStream os = null;

        try {
            os = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {

                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }

        return path;
    }

    /**
     * 图片存储文件
     *
     * @param path   路径
     * @param bitmap 图片
     * @return 图片
     */
    public static String saveBitmapToFile(String path, Bitmap bitmap) {

        if (TextUtils.isEmpty(path)) {
            return null;
        }

        OutputStream os = null;

        try {
            os = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {

                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }

        return path;
    }

    /**
     * 读入路径图片，根据目前的宽、高，加载新的图片，并返回Bitmap
     *
     * @param path       图片路径
     * @param viewWidth  宽度
     * @param viewHeight 高度
     * @return 图片
     */
    public static Bitmap getBitmapFromFile(String path, int viewWidth, int viewHeight) {

        if (viewWidth <= 0 || viewHeight <= 0) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = computeSampleSize(options, viewWidth, viewHeight);

        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        return bitmap;
    }

    /**
     * 计算压缩比
     *
     * @param options    选择项
     * @param viewWidth  目标宽度
     * @param viewHeight 目标高度
     * @return sampleSize
     */
    public static int computeSampleSize(BitmapFactory.Options options, int viewWidth, int viewHeight) {
        int inSampleSize = 1;
        if (viewWidth == 0 || viewHeight == 0) {
            return inSampleSize;
        }
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

        if (bitmapWidth > viewWidth || bitmapHeight > viewHeight) {
            int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
            int heightScale = Math.round((float) bitmapHeight / (float) viewHeight);

            inSampleSize = widthScale < heightScale ? widthScale : heightScale;
        }
        return inSampleSize;
    }
}
