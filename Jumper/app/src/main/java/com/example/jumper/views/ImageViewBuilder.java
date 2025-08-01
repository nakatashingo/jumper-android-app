package com.example.jumper.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.LinkedList;

public class ImageViewBuilder {
    private ConstraintLayout constraintLayout;
    private Context context;
    private LinkedList<ImageView> imageViews = new LinkedList<ImageView>();
    private int pos=-1;

    public ImageViewBuilder(ConstraintLayout constraintLayout , Context context){
        this.constraintLayout=constraintLayout;
        this.context=context;
    }

    public void reset(){
        pos=-1;
        imageViews.forEach(x->x.setVisibility(View.GONE));
    }

    public ImageView getImageView(){
        pos++;

        ImageView imageView;
        if(imageViews.size()-1 < pos){
            imageView= createImageView();
        }else{
            imageView= imageViews.get(pos);
        }

        imageView.setVisibility(View.VISIBLE);
        return imageView;
    }

    private ImageView createImageView(){
        ImageView imageView = new ImageView(context);
        constraintLayout.addView(imageView);
        imageViews.add(imageView);
        return imageView;
    }
}
