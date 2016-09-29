package com.example.zane.imageloader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zane.easyimageprovider.builder.ImageLoadBuidler;
import com.example.zane.easyimageprovider.builder.core.EasyImage;
import com.example.zane.easyimageprovider.builder.factory.EasyImageFactory;
import com.example.zane.easyimageprovider.builder.factory.LoadFactory;

/**
 * Created by Zane on 16/9/29.
 * Email: zanebot96@gmail.com
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private EasyImage imageLoader;
    private Context context;

    public MyAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_layout, null ,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageLoadBuidler buidler = new ImageLoadBuidler().with(context)
                                           .load("https://avatars.githubusercontent.com/u/12124524?v=3")
                                           .setError(R.drawable.ic_launcher)
                                           .setHolderPlace(R.drawable.ic_launcher)
                                           .useNoCache()
                                           .into(holder.mImageView);
        EasyImageFactory factory = new LoadFactory(buidler);
        imageLoader = factory.init();
        imageLoader.execute();
    }

    @Override
    public int getItemCount() {
        return 40;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageview_item);
        }
    }
}
