package com.zkp.fsh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public PreviewAdapter(Context mContext, List<PreviewItemBean> previewItemBeans) {
        this.mContext = mContext;
        this.mPreviewItemBeans = previewItemBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_preview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.ivPreview.setImageBitmap(getItem(position).getBitmap());
        viewHolder.tvName.setText(getItem(position).getName());
    }

    @Override
    public int getItemCount() {
        return mPreviewItemBeans.size();
    }

    public PreviewItemBean getItem(int position) {
        return mPreviewItemBeans.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPreview;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPreview = itemView.findViewById(R.id.ivPreview);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }

}
