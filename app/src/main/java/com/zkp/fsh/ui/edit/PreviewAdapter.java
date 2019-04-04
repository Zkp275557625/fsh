package com.zkp.fsh.ui.edit;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zkp.fsh.R;

import java.util.List;

/**
 * @author: zkp
 * @project: fsh
 * @package: com.zkp.fsh
 * @time: 2019/3/29 13:53
 * @description:
 */
public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.ViewHolder> {

    private Context mContext;
    private List<PreviewItemBean> mPreviewItemBeans;

    private OnItemClickListener onItemClickListener;

    public PreviewAdapter(Context mContext, List<PreviewItemBean> previewItemBeans) {
        this.mContext = mContext;
        this.mPreviewItemBeans = previewItemBeans;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_preview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (getItem(position).isShowSelected()) {
            viewHolder.ivPreview.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.preview_selected));
        } else {
            viewHolder.ivPreview.setImageBitmap(null);
        }
        viewHolder.ivPreview.setBackground(new BitmapDrawable(getItem(position).getBitmap()));
        viewHolder.tvName.setText(getItem(position).getName());
    }

    @Override
    public int getItemCount() {
        return mPreviewItemBeans.size();
    }

    public PreviewItemBean getItem(int position) {
        return mPreviewItemBeans.get(position);
    }

    /**
     * 点击某个item的监听接口
     */
    public interface OnItemClickListener {
        /**
         * 某个item被点击的回调
         *
         * @param view     view
         * @param position position
         */
        void OnItemClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPreview;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPreview = itemView.findViewById(R.id.ivPreview);
            tvName = itemView.findViewById(R.id.tvName);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(view, getLayoutPosition());
                    }

                }
            });
        }
    }

}
