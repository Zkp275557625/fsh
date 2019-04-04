package com.zkp.fsh.ui.edit.jni;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.dialog.creator.type.impl.DialogCreatorFactory;
import com.hqu.cst.sketcher.ImageHelperJNI;
import com.hqu.cst.sketcher.ImageHelper;
import com.zkp.fsh.R;
import com.zkp.fsh.ui.edit.PreviewAdapter;
import com.zkp.fsh.ui.edit.PreviewItemBean;
import com.zkp.fsh.imageview.ImageViewTouch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zkp
 * @project: fsh
 * @package: com.zkp.fsh
 * @time: 2019/3/29 13:26
 * @description:
 */
public class SketcherPictureJNIActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageViewTouch mImageView;

    private Bitmap mBitmap;
    private Bitmap mBitmapSketcher;
    private SketcherTask mSketcherTask;
    private PreviewAdapter mAdapter;
    private List<PreviewItemBean> mPreviewItemBeans;

    private int mPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketcher_picture);

        mBitmap = ImageHelper.zoomImageToFixedSize(BitmapFactory.decodeFile(getIntent().getStringExtra("imgPath")), 960);

        mBitmapSketcher = BitmapFactory.decodeResource(getResources(), R.drawable.pencil_texture);

        mPosition = 0;

        initView();

        mPreviewItemBeans = new ArrayList<>();

        new PreviewTask().execute(new Bitmap[]{ImageHelper.zoomImageToFixedSize(mBitmap, 200, 200),
                ImageHelper.zoomImageToFixedSize(mBitmapSketcher, 200, 200)});
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mImageView = findViewById(R.id.imageView);

        mImageView.setImageBitmap(mBitmap);
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
            switch (mPosition) {
                case 0:
                    return mBitmap;
                case 1:
                    return ImageHelperJNI.CoherenceFilter(mBitmap);
                case 2:
                    return ImageHelperJNI.Lowpoly(mBitmap);
                case 3:
                    return ImageHelperJNI.WaterColor(mBitmap, mBitmapSketcher);
                case 4:
                    return ImageHelperJNI.Carving(mBitmap);
                case 5:
                    return ImageHelperJNI.Maoboli(mBitmap);
                case 6:
                    return ImageHelperJNI.Mosaic(mBitmap);
                case 7:
                    return ImageHelperJNI.ascii(mBitmap);
                case 8:
                    return ImageHelperJNI.edgePreservingFilter(mBitmap);
                case 9:
                    return ImageHelperJNI.stylization(mBitmap);
                case 10:
                    return ImageHelperJNI.detailEnhance(mBitmap);
                case 11:
                    return ImageHelperJNI.Fenbi(mBitmap);
                case 12:
                    return ImageHelperJNI.Line(mBitmap);
                case 13:
                    return ImageHelperJNI.ColorReduce(mBitmap, 64);
                case 14:
                    return ImageHelperJNI.Pencil(mBitmap, mBitmapSketcher);
                case 15:
                    return ImageHelperJNI.ColorPencil(mBitmap, mBitmapSketcher);
                case 16:
                    return ImageHelper.Ascii(mBitmap, SketcherPictureJNIActivity.this);
                default:
                    return mBitmap;
            }
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
            mLargeLoadingDialog.show(SketcherPictureJNIActivity.this);
        }

        /**
         * 接收线程任务执行结果、将执行结果显示到UI组件
         *
         * @param bitmap bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mLargeLoadingDialog.dismiss(SketcherPictureJNIActivity.this);
            mImageView.setImageBitmap(bitmap);
        }
    }

    private class PreviewTask extends AsyncTask<Bitmap, Integer, List<Bitmap>> implements PreviewAdapter.OnItemClickListener {

        private SmartDialog mLargeLoadingDialog;

        @Override
        public void OnItemClick(View view, int position) {
            mPosition = position;
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                mAdapter.getItem(i).setShowSelected(false);
            }
            mAdapter.getItem(position).setShowSelected(!mAdapter.getItem(position).isShowSelected());
            mAdapter.notifyDataSetChanged();
            mSketcherTask = new SketcherTask();
            mSketcherTask.execute(new Bitmap[]{mBitmap, mBitmapSketcher});
        }

        /**
         * 接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
         *
         * @param bitmaps Voids
         * @return bitmap
         */
        @Override
        protected List<Bitmap> doInBackground(Bitmap... bitmaps) {
            List<Bitmap> previewBitmaps = new ArrayList<>();
            previewBitmaps.add(bitmaps[0]);
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
            previewBitmaps.add(ImageHelper.Ascii(mBitmap, SketcherPictureJNIActivity.this));
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
            mLargeLoadingDialog.show(SketcherPictureJNIActivity.this);
        }

        /**
         * 接收线程任务执行结果、将执行结果显示到UI组件
         *
         * @param bitmaps bitmap
         */
        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            mLargeLoadingDialog.dismiss(SketcherPictureJNIActivity.this);
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(0), "原图"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(1), "抽象画"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(2), "LowPoly"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(3), "水彩画"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(4), "浮雕"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(5), "毛玻璃"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(6), "马赛克"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(7), "ascii马赛克"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(8), "边缘保持"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(9), "风格模仿"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(10), "细节增强"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(11), "粉笔画"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(12), "线条画"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(13), "多色调"));
            mPreviewItemBeans.add(new PreviewItemBean(ImageHelper.zoomImageToFixedSize(bitmaps.get(14), 200, 200), "铅笔画"));
            mPreviewItemBeans.add(new PreviewItemBean(ImageHelper.zoomImageToFixedSize(bitmaps.get(15), 200, 200), "彩色铅笔画"));
            mPreviewItemBeans.add(new PreviewItemBean(ImageHelper.zoomImageToFixedSize(bitmaps.get(16), 200, 200), "Ascii"));

            mAdapter = new PreviewAdapter(SketcherPictureJNIActivity.this, mPreviewItemBeans);
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(this);
        }
    }
}
