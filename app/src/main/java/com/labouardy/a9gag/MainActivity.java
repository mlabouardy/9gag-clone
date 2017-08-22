package com.labouardy.a9gag;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.labouardy.a9gag.adapter.MemesAdapter;
import com.labouardy.a9gag.model.Meme;
import com.labouardy.a9gag.service.MemeService;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<List<Meme>>, MaterialSearchView.OnQueryTextListener{

    @BindView(R.id.memesLV)
    ListView memesLV;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    List<Meme> memes;

    MemesAdapter adapter;

    Retrofit retrofit;
    MemeService memeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeStatusBarColor();

        ButterKnife.bind(this);

        retrofit = new Retrofit.Builder()
                   .baseUrl(getResources().getString(R.string.api_url))
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();

        memeService = retrofit.create(MemeService.class);

        memes = new ArrayList<Meme>();
        adapter = new MemesAdapter(this, memes);
        memesLV.setAdapter(adapter);

        Call<List<Meme>> call = memeService.findByTag("geek");
        call.enqueue(this);

        searchView.setVoiceSearch(false);
        searchView.setSuggestions(getResources().getStringArray(R.array.tags_suggestions));
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onResponse(Call<List<Meme>> call, Response<List<Meme>> response) {
        if(response.isSuccessful()){
            memes = response.body();
            adapter = new MemesAdapter(this, memes);
            memesLV.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(Call<List<Meme>> call, Throwable t) {
        Log.e("onFailure", t.getMessage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.about){
            SweetAlertDialog aboutDialog = new SweetAlertDialog(this);
            aboutDialog.setTitleText(getResources().getString(R.string.dialog_title));
            aboutDialog.setContentText(getResources().getString(R.string.dialog_description));
            aboutDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeStatusBarColor(){
        int color = getResources().getColor(R.color.colorPrimary);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Call<List<Meme>> call = memeService.findByTag(query);
        call.enqueue(this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
