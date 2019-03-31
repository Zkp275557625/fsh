package com.zkp.fsh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.dialog.creator.type.impl.DialogCreatorFactory;
import com.hqu.cst.sketcher.ImageHelperJNI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: zkp
 * @project: fsh
 * @package: com.zkp.fsh
 * @time: 2019/3/29 13:26
 * @description:
 */
public class SketcherPictureActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageView mImageView;

    private Bitmap mBitmap;
    private Bitmap mBitmapSketcher;
    private SketcherTask mSketcherTask;
    private PreviewAdapter mAdapter;
    private List<PreviewItemBean> mPreviewItemBeans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketcher_picture);

        mBitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("imgPath"));

        mBitmapSketcher = BitmapFactory.decodeResource(getResources(), R.drawable.pencil_texture);

        initView();

        mPreviewItemBeans = new ArrayList<>();

        new PreviewTask().execute(new Bitmap[]{ImageHelper.zoomImageToFixedSize(mBitmap, 200, 200),
                ImageHelper.zoomImageToFixedSize(mBitmapSketcher, 200, 200)});

        mSketcherTask = new SketcherTask();
        mSketcherTask.execute(new Bitmap[]{mBitmap, mBitmapSketcher});
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mImageView = findViewById(R.id.imageView);
    }

    private class SketcherTask extends AsyncTask<Bitmap, Integer, Bitmap> {

        private SmartDialog mLargeLoadingDialog;

        /**
         * 接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
         *
         * @param bitmaps Voids
         * @return bitmap
         */
        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            return ImageHelperJNI.Carving(mBitmap);
        }

        /**
         * 线程任务前的操作
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mLargeLoadingDialog == null) {
                mLargeLoadingDialog = SmartDialog.newInstance(
                        DialogCreatorFactory
                                .loading()
                                .large()
                                .message("处理中...")
                )
                        .reuse(true);
            }
            mLargeLoadingDialog.show(SketcherPictureActivity.this);
        }

        /**
         * 接收线程任务执行结果、将执行结果显示到UI组件
         *
         * @param bitmap bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mLargeLoadingDialog.dismiss(SketcherPictureActivity.this);
            mImageView.setImageBitmap(bitmap);
        }
    }

    private class PreviewTask extends AsyncTask<Bitmap, Integer, List<Bitmap>> {

        private SmartDialog mLargeLoadingDialog;

        /**
         * 接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
         *
         * @param bitmaps Voids
         * @return bitmap
         */
        @Override
        protected List<Bitmap> doInBackground(Bitmap... bitmaps) {
            List<Bitmap> previewBitmaps = new ArrayList<>();
            previewBitmaps.add(ImageHelperJNI.CoherenceFilter(bitmaps[0]));
            previewBitmaps.add(ImageHelperJNI.Lowpoly(bitmaps[0]));
            previewBitmaps.add(ImageHelperJNI.WaterColor(bitmaps[0], bitmaps[1]));
            previewBitmaps.add(ImageHelperJNI.Carving(bitmaps[0]));
            previewBitmaps.add(ImageHelperJNI.Maoboli(bitmaps[0]));
            previewBitmaps.add(ImageHelperJNI.Mosaic(bitmaps[0]));
            previewBitmaps.add(ImageHelperJNI.ascii(bitmaps[0]));
            previewBitmaps.add(ImageHelperJNI.edgePreservingFilter(bitmaps[0]));
            previewBitmaps.add(ImageHelperJNI.stylization(bitmaps[0]));
            previewBitmaps.add(ImageHelperJNI.detailEnhance(bitmaps[0]));
            previewBitmaps.add(ImageHelperJNI.Fenbi(bitmaps[0]));
            previewBitmaps.add(ImageHelperJNI.Line(bitmaps[0]));
            previewBitmaps.add(ImageHelperJNI.ColorReduce(bitmaps[0], 64));
            previewBitmaps.add(ImageHelperJNI.Pencil(mBitmap, mBitmapSketcher));
            previewBitmaps.add(ImageHelperJNI.ColorPencil(mBitmap, mBitmapSketcher));
            return previewBitmaps;
        }

        /**
         * 线程任务前的操作
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mLargeLoadingDialog == null) {
                mLargeLoadingDialog = SmartDialog.newInstance(
                        DialogCreatorFactory
                                .loading()
                                .large()
                                .message("处理中...")
                )
                        .reuse(true);
            }
            mLargeLoadingDialog.show(SketcherPictureActivity.this);
        }

        /**
         * 接收线程任务执行结果、将执行结果显示到UI组件
         *
         * @param bitmaps bitmap
         */
        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            mLargeLoadingDialog.dismiss(SketcherPictureActivity.this);
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(0), "抽象画"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(1), "LowPoly"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(2), "水彩画"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(3), "浮雕"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(4), "毛玻璃"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(5), "马赛克"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(6), "ascii马赛克"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(7), "边缘保持"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(8), "风格模仿"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(9), "细节增强"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(10), "粉笔画"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(11), "线条画"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(12), "多色调"));
            mPreviewItemBeans.add(new PreviewItemBean(ImageHelper.zoomImageToFixedSize(bitmaps.get(13), 200, 200), "铅笔画"));
            mPreviewItemBeans.add(new PreviewItemBean(ImageHelper.zoomImageToFixedSize(bitmaps.get(14), 200, 200), "彩色铅笔画"));

            mAdapter = new PreviewAdapter(SketcherPictureActivity.this, mPreviewItemBeans);
            mRecyclerView.setAdapter(mAdapter);
        }

    }
}
