package com.hqu.cst.sketcher;

import android.graphics.Bitmap;


/**
 * @author: zkp
 * @project: fsh
 * @package: com.hqu.cst.sketcher
 * @time: 2019/3/28 17:18
 * @description:
 */

public class ImageHelperJNI {

    /**
     * 铅笔画
     *
     * @param bitmap         原图片
     * @param pencil_texture 纹理图
     * @return 处理结果
     */
    public static Bitmap Pencil(Bitmap bitmap, Bitmap pencil_texture) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int w1 = pencil_texture.getWidth();
        int h1 = pencil_texture.getHeight();

        int[] pix = new int[w * h];
        int[] bac = new int[w1 * h1];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        pencil_texture.getPixels(bac, 0, w1, 0, 0, w1, h1);

        int[] resultPixes = OpenCVCPP.Pencil(pix, w, h, bac, h1, h1);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * 彩色铅笔画
     *
     * @param bitmap         原图片
     * @param pencil_texture 纹理图
     * @return 处理结果
     */
    public static Bitmap ColorPencil(Bitmap bitmap, Bitmap pencil_texture) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int w1 = pencil_texture.getWidth();
        int h1 = pencil_texture.getHeight();

        int[] pix = new int[w * h];
        int[] bac = new int[w1 * h1];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        pencil_texture.getPixels(bac, 0, w1, 0, 0, w1, h1);

        int[] resultPixes = OpenCVCPP.ColorPencil(pix, w, h, bac, h1, h1);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * 抽象画
     *
     * @param bitmap 原图片
     * @return 处理结果
     */
    public static Bitmap CoherenceFilter(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int[] resultPixes = OpenCVCPP.CoherenceFilter(pix, w, h);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * 低面多边形
     *
     * @param bitmap 原图片
     * @return 处理结果
     */
    public static Bitmap Lowpoly(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int[] resultPixes = OpenCVCPP.Lowpoly(pix, w, h, 1);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * 水彩画
     *
     * @param bitmap             原图片
     * @param watercolor_texture 纹理图
     * @return 处理结果
     */
    public static Bitmap WaterColor(Bitmap bitmap, Bitmap watercolor_texture) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int w1 = watercolor_texture.getWidth();
        int h1 = watercolor_texture.getHeight();

        int[] pix = new int[w * h];
        int[] bac = new int[w1 * h1];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        watercolor_texture.getPixels(bac, 0, w1, 0, 0, w1, h1);

        int[] resultPixes = OpenCVCPP.Watercolor(pix, w, h, 2);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }


    /**
     * 浮雕
     *
     * @param bitmap 原图片
     * @return 处理结果
     */
    public static Bitmap Carving(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int[] resultPixes = OpenCVCPP.Carving(pix, w, h);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * 毛玻璃
     *
     * @param bitmap 原图片
     * @return 处理结果
     */
    public static Bitmap Maoboli(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int[] resultPixes = OpenCVCPP.Maoboli(pix, w, h);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * 马赛克
     *
     * @param bitmap 原图片
     * @return 处理结果
     */
    public static Bitmap Mosaic(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int[] resultPixes = OpenCVCPP.Mosaic(pix, w, h, 2);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * ascii马赛克
     *
     * @param bitmap 原图片
     * @return 处理结果
     */
    public static Bitmap ascii(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int[] resultPixes = OpenCVCPP.ascii(pix, w, h);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * 边缘保持
     *
     * @param bitmap 原图片
     * @return 处理结果
     */
    public static Bitmap edgePreservingFilter(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int[] resultPixes = OpenCVCPP.edgePreservingFilter(pix, w, h);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * 风格模仿
     *
     * @param bitmap 原图片
     * @return 处理结果
     */
    public static Bitmap stylization(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int[] resultPixes = OpenCVCPP.stylization(pix, w, h);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * 细节增强
     *
     * @param bitmap 原图片
     * @return 处理结果
     */
    public static Bitmap detailEnhance(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int[] resultPixes = OpenCVCPP.detailEnhance(pix, w, h);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * 粉笔画
     *
     * @param bitmap 原图片
     * @return 处理结果
     */
    public static Bitmap Fenbi(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int[] resultPixes = OpenCVCPP.Fenbi(pix, w, h);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * 线条画
     *
     * @param bitmap 原图片
     * @return 处理结果
     */
    public static Bitmap Line(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];

        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int[] resultPixes = OpenCVCPP.Line(pix, w, h);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixes, 0, w, 0, 0, w, h);

        return result;
    }

    /**
     * 多色调
     *
     * @param bitmap 原图片
     * @param div    div可选2的倍数 越大颜色越少
     * @return 处理结果
     */
    public static Bitmap ColorReduce(Bitmap bitmap, int div) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);

        int[] buf = new int[w * h];
        bitmap.getPixels(buf, 0, w, 0, 0, w, h);

        int[] resultPixes = OpenCVCPP.ColorReduce(buf, w, h, div);

        result.setPixels(resultPixes, 0, w, 0, 0, w, h);
        return result;
    }
}
