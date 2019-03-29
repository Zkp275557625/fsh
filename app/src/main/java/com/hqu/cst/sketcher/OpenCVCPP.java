package com.hqu.cst.sketcher;

/**
 * @author: zkp
 * @project: fsh
 * @package: com.hqu.cst.sketcher
 * @time: 2019/3/28 17:18
 * @description:
 */
public class OpenCVCPP {
    static {
        System.loadLibrary("OpenCV");
    }

    /**
     * 铅笔画
     *
     * @param buf  需要处理的图像
     * @param w    buf的宽
     * @param h    buf的高
     * @param buf2 和纹理图
     * @param w2   buf2的宽
     * @param h2   buf2的高
     * @return 处理结果
     */
    public static native int[] Pencil(int[] buf, int w, int h, int[] buf2, int w2, int h2);

    /**
     * 彩色铅笔画
     *
     * @param buf  需要处理的图像
     * @param w    buf的宽
     * @param h    buf的高
     * @param buf2 和纹理图
     * @param w2   buf2的宽
     * @param h2   buf2的高
     * @return 处理结果
     */
    public static native int[] ColorPencil(int[] buf, int w, int h, int[] buf2, int w2, int h2);

    /**
     * 抽象画
     *
     * @param buf 需要处理的图像
     * @param w   buf的宽
     * @param h   buf的高
     * @return 处理结果
     */
    public static native int[] CoherenceFilter(int[] buf, int w, int h);

    /**
     * LowPoly风格
     *
     * @param buf   需要处理的图像
     * @param w     buf的宽
     * @param h     buf的高
     * @param index 可选0和1，两种风格
     * @return 处理结果
     */
    public static native int[] Lowpoly(int[] buf, int w, int h, int index);

    /**
     * 水彩画
     *
     * @param buf  需要处理的图像
     * @param w    buf的宽
     * @param h    buf的高
     * @param size 水彩半径
     * @return 处理结果
     */
    public static native int[] Watercolor(int[] buf, int w, int h, int size);

    /**
     * 浮雕
     *
     * @param buf 需要处理的图像
     * @param w   buf的宽
     * @param h   buf的高
     * @return 处理结果
     */
    public static native int[] Carving(int[] buf, int w, int h);

    /**
     * 卡通
     *
     * @param buf  需要处理的图像
     * @param w    buf的宽
     * @param h    buf的高
     * @param buf2 和纹理图
     * @param w2   buf2的宽
     * @param h2   buf2的高
     * @return 处理结果
     */
    public static native int[] Cartoon(int[] buf, int w, int h, int[] buf2, int w2, int h2);

    /**
     * 毛玻璃
     *
     * @param buf 需要处理的图像
     * @param w   buf的宽
     * @param h   buf的高
     * @return 处理结果
     */
    public static native int[] Maoboli(int[] buf, int w, int h);

    /**
     * 马赛克
     *
     * @param buf   需要处理的图像
     * @param w     buf的宽
     * @param h     buf的高
     * @param size2 马赛克半径
     * @return 处理结果
     */
    public static native int[] Mosaic(int[] buf, int w, int h, int size2);

    /**
     * ascii马赛克
     *
     * @param buf 需要处理的图像
     * @param w   buf的宽
     * @param h   buf的高
     * @return 处理结果
     */
    public static native int[] ascii(int[] buf, int w, int h);

    //下面三个为opencv自带的效果
    public static native int[] edgePreservingFilter(int[] buf, int w, int h);

    public static native int[] stylization(int[] buf, int w, int h);

    public static native int[] detailEnhance(int[] buf, int w, int h);

    /**
     * 粉笔画
     *
     * @param buf 需要处理的图像
     * @param w   buf的宽
     * @param h   buf的高
     * @return 处理结果
     */
    public static native int[] Fenbi(int[] buf, int w, int h);

    /**
     * 线条画
     *
     * @param buf 需要处理的图像
     * @param w   buf的宽
     * @param h   buf的高
     * @return 处理结果
     */
    public static native int[] Line(int[] buf, int w, int h);

    /**
     * 多色调
     *
     * @param buf 需要处理的图像
     * @param w   buf的宽
     * @param h   buf的高
     * @param div 可选2的倍数 越大颜色越少
     * @return 处理结果
     */
    public static native int[] ColorReduce(int[] buf, int w, int h, int div);

    /**
     * 图片融合
     *
     * @param buf  图片1
     * @param w    buf的宽
     * @param h    buf的高
     * @param buf2 图片1
     * @param w2   buf2的宽
     * @param h2   buf2的高
     * @param Points  int[]
     * @param PointsSize   int
     * @return 处理结果
     */
    public static native int[] MultiStyle(int[] buf, int w, int h,
                                          int[] buf2, int w2, int h2, int[] Points, int PointsSize);
}
