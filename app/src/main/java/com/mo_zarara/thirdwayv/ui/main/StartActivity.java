package com.mo_zarara.thirdwayv.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Visibility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mo_zarara.thirdwayv.R;
import com.mo_zarara.thirdwayv.adapters.GameRecyclerAdapter;
import com.mo_zarara.thirdwayv.pojo.GamesModel;
import com.mo_zarara.thirdwayv.pojo.Root;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class StartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button refresh_btn;
    LinearLayout linearLayout_Internet_failed;
    RelativeLayout main_layout;
    FloatingActionButton floatingActionButton;
    ProgressBar progressBar;

    GamesViewModel gamesViewModel;
    GameRecyclerAdapter adapter;

    String target_dates_str;
    int target_dates_num;
    String[] mlistDates = {"None",
            "2020-01-01,2021-12-31",
            "2010-01-01,2019-12-31",
            "2000-01-01,2009-12-31",
            "1990-01-01,1999-12-31",
            "1980-01-01,1989-12-31",
            "1970-01-01,1979-12-31",
            "1960-01-01,1969-12-31",
            "1950-01-01,1959-12-31"};

    private static final String TAG = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Paper.init(this);

        progressBar = findViewById(R.id.progressBar_id);
        recyclerView = findViewById(R.id.start_recycler_view_id);
        refresh_btn = findViewById(R.id.btn_Refresh);
        linearLayout_Internet_failed = findViewById(R.id.Internet_failed_txt_id);
        main_layout = findViewById(R.id.main_id);
        floatingActionButton = findViewById(R.id.search_btn_id);


        gamesViewModel = new ViewModelProvider(this).get(GamesViewModel.class);
        adapter = new GameRecyclerAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        if (Paper.book().read("target_dates") != null) {
            target_dates_num = Paper.book().read("target_dates");
        } else {
            target_dates_num = 0;
        }




        target_dates_str = mlistDates[target_dates_num];

        if (Paper.book().read("first_visit_no_internet") == null) {
            if (!isNetworkAvailable() ) {
                Log.d(TAG, "mymy LoadingData: 8000");
                VisibilityItems(View.GONE, View.GONE, View.VISIBLE);
            } else {
                LoadingData(target_dates_str);
            }


        } else {
            LoadingData(target_dates_str);
        }




        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                target_dates_str = mlistDates[target_dates_num];
                if (Paper.book().read("first_visit_no_internet") == null) {
                    if (!isNetworkAvailable() ) {

                        VisibilityItems(View.GONE, View.GONE, View.VISIBLE);
                    } else {
                        LoadingData(target_dates_str);
                    }


                } else {
                    LoadingData(target_dates_str);
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Paper.book().read("target_dates") != null) {
                    target_dates_num = Paper.book().read("target_dates");
                } else {
                    target_dates_num = 0;
                }
                Search(target_dates_num);


            }
        });


    }


    private void LoadingData(String dates) {

            VisibilityItems(View.VISIBLE, View.GONE, View.GONE);

            if (isNetworkAvailable()) {
                gamesViewModel.getGamesData(dates);
            }

            gamesViewModel.getAllGames().observe(this, new Observer<List<GamesModel>>() {
                @Override
                public void onChanged(List<GamesModel> gamesModels) {
                    if (gamesModels != null) {

                        adapter.setList(gamesModels);

                        if (Paper.book().read("first_visit_no_internet") == null) {
                            Paper.book().write("first_visit_no_internet", "Ok");
                        }

                        VisibilityItems(View.GONE, View.VISIBLE, View.GONE);
                    } else {
                        VisibilityItems(View.GONE, View.GONE, View.VISIBLE);
                    }
                }
            });


    }

    private void VisibilityItems(int progressV, int main_layoutV, int Internet_failedV){
        progressBar.setVisibility(progressV);
        main_layout.setVisibility(main_layoutV);
        linearLayout_Internet_failed.setVisibility(Internet_failedV);
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void Search(final int checkItems) {

        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        builder.setTitle("Dates");

        builder.setSingleChoiceItems(mlistDates, checkItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (isNetworkAvailable()) {
                    Paper.book().write("target_dates", which);

                    target_dates_str = mlistDates[which];
                    LoadingData(target_dates_str);
                } else {
                    Toast.makeText(StartActivity.this, "Internet connection failed!!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        AlertDialog mDialog = builder.create();
        mDialog.show();
    }


}