package com.meson.imageplayer;

import android.graphics.Bitmap;

public class MyBitmapFactory {
    /*
     * ��ȡλͼ��RGB����
     */
    public static byte[]  getRGBByBitmap(Bitmap bitmap)
    {
        if (bitmap == null)
        {
            return null;
        }
        
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        
        int size = width * height;
        
        int pixels[] = new int[size];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        
        byte[]data = convertColorToByte(pixels);
        
        
        return data;
    }
    
    
    /*
     * ��������ת��ΪRGB����
     */
    public static byte[] convertColorToByte(int color[])
    {
        if (color == null)
        {
            return null;
        }
        
        byte[] data = new byte[color.length * 3];
        for(int i = 0; i < color.length; i++)
        {
            data[i * 3] = (byte) (color[i] >> 16 & 0xff);
            data[i * 3 + 1] = (byte) (color[i] >> 8 & 0xff);
            data[i * 3 + 2] =  (byte) (color[i] & 0xff);
        }
        
        return data;
        
    }
    
    /*
     * byte[] data������Ǵ�RGB�����ݣ�����������ͼƬ�ļ�����
     */
    static public Bitmap createMyBitmap(byte[] data, int width, int height){    
        int []colors = convertByteToColor(data);
        if (colors == null){
            return null;
        }
            
        Bitmap bmp = null;

        try {
            bmp = Bitmap.createBitmap(colors, 0, width, width, height, 
                    Bitmap.Config.ARGB_8888);
        } catch (Exception e) {
            // TODO: handle exception
    
            return null;
        }
                        
        return bmp;
    }

    
    /*
     * ��RGB����ת��Ϊ��������
     */
    private static int[] convertByteToColor(byte[] data){
        int size = data.length;
        if (size == 0){
            return null;
        }
        
        
        // ������data�ĳ���Ӧ����3�ı�����������������
        int arg = 0;
        if (size % 3 != 0){
            arg = 1;
        }
        
        int []color = new int[size / 3 + arg];
        int red, green, blue;
        
        
        if (arg == 0){                                  //  ������3�ı���
            for(int i = 0; i < color.length; ++i){
        
                color[i] = (data[i * 3] << 16 & 0x00FF0000) | 
                           (data[i * 3 + 1] << 8 & 0x0000FF00 ) | 
                           (data[i * 3 + 2] & 0x000000FF ) | 
                            0xFF000000;
            }
        }else{                                      // ����3�ı���
            for(int i = 0; i < color.length - 1; ++i){
                color[i] = (data[i * 3] << 16 & 0x00FF0000) | 
                   (data[i * 3 + 1] << 8 & 0x0000FF00 ) | 
                   (data[i * 3 + 2] & 0x000000FF ) | 
                    0xFF000000;
            }
            
            color[color.length - 1] = 0xFF000000;                   // ���һ�������ú�ɫ���
        }
    
        return color;
    }
}
