package com.zkp.fsh;

import android.graphics.Bitmap;

/**
 * @author: zkp
 * @project: fsh
 * @package: com.zkp.fsh
 * @time: 2019/3/29 15:47
 * @description:
 */
public class PreviewItemBean {

    private Bitmap bitmap;
    private String name;

    public PreviewItemBean(Bitmap bitmap, String name) {
        this.bitmap = bitmap;
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PreviewItemBean{" +
                "bitmap=" + bitmap +
                ", name='" + name + '\'' +
                '}';
    }
}
