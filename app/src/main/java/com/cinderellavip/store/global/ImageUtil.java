package com.cinderellavip.store.global;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.cinderellavip.store.R;
import com.cinderellavip.store.http.HttpUrl;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 目前为测试，当路径为空时才向控件imageView中加载图片
 * Created by 32672 on 2019/1/7.
 */

public class ImageUtil {


    public static  void loadNetRound(Context mContext, ImageView imageView, String path){
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(10);
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .error(R.drawable.shape_gray5);
        Glide.with(mContext).load(path).apply(options).into(imageView);


    }
    public static  void loadNet(Context mContext, ImageView imageView, String path){
        RequestOptions options = new RequestOptions()
                .error(R.mipmap.logo1);//图片加载失败后，显示的图片
        Glide.with(mContext).load(path).apply(options).into(imageView);
    }
    public static  void loadGauss(Context mContext, ImageView imageView, String path){

        RequestOptions requestOptions = RequestOptions.bitmapTransform(new BlurTransformation(mContext, 12, 1));
        Glide.with(mContext).load(path).apply(requestOptions).into(imageView);


    }

    public static  void loadProduct(Context mContext, ImageView imageView, String path){
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(10);
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .error(R.drawable.shape_white5);
        Glide.with(mContext).load(path).apply(options).into(imageView);
    }

    public static  void load(Context mContext, ImageView imageView, String path){
        RequestOptions options = new RequestOptions()
                .error(R.drawable.shape_gray5);//图片加载失败后，显示的图片
        Glide.with(mContext).load(path).apply(options).into(imageView);

    }
    public static  void loadNetTest(Context mContext, ImageView imageView, String path){
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能
        RequestOptions options = new RequestOptions().override(300, 300)
                .error(R.drawable.shape_gray5);
        Glide.with(mContext).load("http://192.168.1.212:9000/FileUpLoad//Image/20186/c96bf354-3243-4f4c-b4e9-dd89a98f2ce6.png").apply(options).into(imageView);
    }

    public static  void loadNetAllAddress(Context mContext, ImageView imageView, String path){
        Glide.with(mContext).load(path).into(imageView);
    }

    public static  void loadLocal(Context mContext, ImageView imageView, String path){
        Glide.with(mContext).load(path).into(imageView);
    }
}
