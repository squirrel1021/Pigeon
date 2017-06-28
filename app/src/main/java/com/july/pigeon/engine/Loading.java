package com.july.pigeon.engine;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.july.pigeon.R;


public class Loading extends Dialog {
    private String loadingMsg = "正在加载...";
    private Context context;

    public Loading(Context context) {
        super(context);
        this.context = context;
    }

    public Loading(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public Loading(Context context, int theme, String msg) {
        super(context, theme);
        this.context = context;

        this.loadingMsg = msg;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_style_layout, null);
        setContentView(view);
        this.setCancelable(false);

        ProgressBar pb = (ProgressBar) findViewById(R.id.loadingProgressBar);
        pb.setVisibility(View.VISIBLE);

        TextView tv = (TextView) findViewById(R.id.loadingText);
        tv.setText(loadingMsg);
        //tv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        Log.d("TAG", "+++++++++++++++++++++++++++");
    }


}
