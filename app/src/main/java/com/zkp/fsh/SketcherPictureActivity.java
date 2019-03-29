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
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
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
    private PreviewItemBean mBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketcher_picture);

        mBitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("imgPath"));

        mBitmapSketcher = BitmapFactory.decodeResource(getResources(), R.drawable.pencil_texture);

        initView();

        mPreviewItemBeans = new ArrayList<>();
        mBean = new PreviewItemBean();
        mBean.setName("彩色铅笔画");

        ;
        new PreviewTask().execute(new Bitmap[]{ImageHelper.zoomImageToFixedSize(mBitmap, 200, 200)});

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

    private class PreviewTask extends AsyncTask<Bitmap, Integer, Bitmap> {

        private SmartDialog mLargeLoadingDialog;

        /**
         * 接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
         *
         * @param bitmaps Voids
         * @return bitmap
         */
        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            return ImageHelperJNI.Carving(bitmaps[0]);
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
            mBean.setBitmap(bitmap);
            for (int i = 0; i < 10; i++) {
                mPreviewItemBeans.add(i, mBean);
            }

            Logger.d("mPreviewItemBeans.size()==" + mPreviewItemBeans.size());

            mAdapter = new PreviewAdapter(SketcherPictureActivity.this, mPreviewItemBeans);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
