package com.labouardy.a9gag.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.labouardy.a9gag.R;
import com.labouardy.a9gag.model.Meme;
import com.labouardy.a9gag.utils.BitmapLruCache;

import java.util.List;

/**
 * Created by Mohamed on 22/08/2017.
 */

public class MemesAdapter extends ArrayAdapter<Meme> {

    private final Context context;
    private final List<Meme> memes;
    private ImageLoader imageLoader;

    public MemesAdapter(Context context, List<Meme> memes){
        super(context, R.layout.meme, memes);
        this.context = context;
        this.memes = memes;
        this.imageLoader = new ImageLoader(Volley.newRequestQueue(context), new BitmapLruCache());
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.meme, parent, false);
        }
        Meme meme = getItem(position);

        TextView descriptionTV = (TextView)convertView.findViewById(R.id.descriptionTV);
        NetworkImageView imageNIV = (NetworkImageView)convertView.findViewById(R.id.imageNIV);

        descriptionTV.setText(meme.getDescription());
        imageNIV.setImageUrl(meme.getImage(), this.imageLoader);

        return convertView;
    }
}
