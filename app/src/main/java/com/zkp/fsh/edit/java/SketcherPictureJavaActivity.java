package com.zkp.fsh.edit.java;

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
import com.hqu.cst.sketcher.ImageHelper;
import com.zkp.fsh.R;
import com.zkp.fsh.edit.PreviewAdapter;
import com.zkp.fsh.edit.PreviewItemBean;
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
public class SketcherPictureJavaActivity extends AppCompatActivity {

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
                    return ImageHelper.IceImage(mBitmap);
                case 2:
                    return ImageHelper.FireImage(mBitmap);
                case 3:
                    return ImageHelper.Delusion(mBitmap);
                case 4:
                    return ImageHelper.Brushing(mBitmap);
                case 5:
                    return ImageHelper.Shutter(mBitmap);
                case 6:
                    return ImageHelper.WaterStreak(mBitmap);
                case 7:
                    return ImageHelper.PaintBrush(mBitmap);
                case 8:
                    return ImageHelper.EdgeDegradation(mBitmap);
                case 9:
                    return ImageHelper.Noise(mBitmap);
                case 10:
                    return ImageHelper.Polaroid(mBitmap);
                case 11:
                    return ImageHelper.Red(mBitmap);
                case 12:
                    return ImageHelper.Green(mBitmap);
                case 13:
                    return ImageHelper.Blue(mBitmap);
                case 14:
                    return ImageHelper.Yellow(mBitmap);
                case 15:
                    return ImageHelper.Lomo(mBitmap);
                case 16:
                    return ImageHelper.Neon(mBitmap);
                case 17:
                    return ImageHelper.BlackWhite(mBitmap);
                case 18:
                    return ImageHelper.OldPhoto(mBitmap);
                case 19:
                    return ImageHelper.InvertedImg(mBitmap);
                case 20:
                    return ImageHelper.SunshineImage(mBitmap);
                case 21:
                    return ImageHelper.Feather(mBitmap);
                case 22:
                    return ImageHelper.Magnifier(mBitmap, 50);
                case 23:
                    return ImageHelper.MagicMirror(mBitmap, 50);
                case 24:
                    return ImageHelper.Masic(mBitmap);
                case 25:
                    return ImageHelper.Cartoon(mBitmap);
                case 26:
                    return ImageHelper.RadialDistortion(mBitmap);
                case 27:
                    return ImageHelper.Bright(mBitmap);
                case 28:
                    return ImageHelper.OilPaint(mBitmap);
                case 29:
                    return ImageHelper.fastBuklr(mBitmap, 10);
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
            mLargeLoadingDialog.show(SketcherPictureJavaActivity.this);
        }

        /**
         * 接收线程任务执行结果、将执行结果显示到UI组件
         *
         * @param bitmap bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mLargeLoadingDialog.dismiss(SketcherPictureJavaActivity.this);
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
            previewBitmaps.add(ImageHelper.IceImage(bitmaps[0]));
            previewBitmaps.add(ImageHelper.FireImage(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Delusion(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Brushing(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Shutter(bitmaps[0]));
            previewBitmaps.add(ImageHelper.WaterStreak(bitmaps[0]));
            previewBitmaps.add(ImageHelper.PaintBrush(bitmaps[0]));
            previewBitmaps.add(ImageHelper.EdgeDegradation(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Noise(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Polaroid(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Red(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Green(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Blue(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Yellow(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Lomo(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Neon(bitmaps[0]));
            previewBitmaps.add(ImageHelper.BlackWhite(bitmaps[0]));
            previewBitmaps.add(ImageHelper.OldPhoto(bitmaps[0]));
            previewBitmaps.add(ImageHelper.InvertedImg(bitmaps[0]));
            previewBitmaps.add(ImageHelper.SunshineImage(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Feather(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Magnifier(bitmaps[0], 50));
            previewBitmaps.add(ImageHelper.MagicMirror(bitmaps[0], 50));
            previewBitmaps.add(ImageHelper.Masic(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Cartoon(bitmaps[0]));
            previewBitmaps.add(ImageHelper.RadialDistortion(bitmaps[0]));
            previewBitmaps.add(ImageHelper.Bright(bitmaps[0]));
            previewBitmaps.add(ImageHelper.OilPaint(bitmaps[0]));
            previewBitmaps.add(ImageHelper.fastBuklr(bitmaps[0], 10));
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
            mLargeLoadingDialog.show(SketcherPictureJavaActivity.this);
        }

        /**
         * 接收线程任务执行结果、将执行结果显示到UI组件
         *
         * @param bitmaps bitmap
         */
        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            mLargeLoadingDialog.dismiss(SketcherPictureJavaActivity.this);
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(0), "原图"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(1), "冰冻"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(2), "熔铸"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(3), "幻觉"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(4), "急速奔驰"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(5), "百叶窗"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(6), "水纹"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(7), "画笔"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(8), "边缘退化"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(9), "噪点"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(10), "宝丽来"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(11), "泛红"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(12), "荧光绿"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(13), "宝石蓝"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(14), "泛黄"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(15), "Lomo"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(16), "霓虹"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(17), "黑白"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(18), "老照片"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(19), "倒影"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(20), "光照"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(21), "羽化"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(22), "放大镜"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(23), "哈哈镜"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(24), "马赛克"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(25), "漫画"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(26), "扭曲"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(27), "明亮"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(28), "油画"));
            mPreviewItemBeans.add(new PreviewItemBean(bitmaps.get(29), "高斯模糊"));


            mAdapter = new PreviewAdapter(SketcherPictureJavaActivity.this, mPreviewItemBeans);
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(this);
        }
    }
}
