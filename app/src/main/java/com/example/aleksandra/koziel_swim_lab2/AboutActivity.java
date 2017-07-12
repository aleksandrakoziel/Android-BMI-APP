package com.example.aleksandra.koziel_swim_lab2;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends Activity
{
    Typeface appTypeface;
    @BindView(R.id.aboutYourBmi)TextView bmiInfo;
    @BindView(R.id.aboutYourBmiDescription)TextView bmiInfoDescription;
    @BindView(R.id.aboutInfo) RelativeLayout aboutLayout;
    @BindView(R.id.imageViewAbout) ImageView moodIconAbout;
    final float minGoodBmi = 18.5f;
    final float maxGoodBmi = 25.0f;
    final float maxOverweightBmi = 30.0f;
    final float startValue = 1.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about_layout);

        initializeAll();
        Bundle bundle = getIntent().getExtras();
        float bmiResult = bundle.getFloat("currentBmi");
        changeColors(bmiResult);
        setBarsColorToBlack();
    }


    public void initializeAll()
    {
        ButterKnife.bind(this);
        //typefaces
        appTypeface = Typeface.createFromAsset(getAssets(), "alcubierre.ttf");

        //about layout

        aboutLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.additionalColor));
        bmiInfo.setTypeface(appTypeface);
        bmiInfoDescription.setTypeface(appTypeface);


    }

    public void changeColors(float result)
    {
        if (startValue < result && result < minGoodBmi)
        {
            changeStyleToUnderweight();
        }
        else if(result>=minGoodBmi&&result<maxGoodBmi)
        {
            changeStyleToGood();
        }
        else if(result>=maxGoodBmi&&result<maxOverweightBmi)
        {
            changeStyleToOverweight();
        }
        if (result>=maxOverweightBmi)
        {
            changeStyleToObesity();
        }
    }

    public void changeStyleToUnderweight()
    {
        //about layout
        aboutLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.greenDark));
        moodIconAbout.setImageDrawable(getResources().getDrawable(R.drawable.sad, getApplicationContext().getTheme()));
        bmiInfo.setText(R.string.underweight);
        bmiInfoDescription.setText(R.string.bmiUnderDes);
    }

    public void changeStyleToGood()
    {

        //about layout
        aboutLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.greenDark));
        moodIconAbout.setImageDrawable(getResources().getDrawable(R.drawable.smile, getApplicationContext().getTheme()));
        bmiInfo.setText(R.string.normal);
        bmiInfoDescription.setText(R.string.goodWeight);
    }

    public void changeStyleToOverweight()
    {


        //about layout
        aboutLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.yellowDark));
        moodIconAbout.setImageDrawable(getResources().getDrawable(R.drawable.neutral, getApplicationContext().getTheme()));
        bmiInfo.setText(R.string.overweightInfo);
        bmiInfoDescription.setText(R.string.overweight);
    }

    public void changeStyleToObesity()
    {
        //about layout
        aboutLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.redDark));
        moodIconAbout.setImageDrawable(getResources().getDrawable(R.drawable.sad, getApplicationContext().getTheme()));
        bmiInfo.setText(R.string.obesity);
        bmiInfoDescription.setText(R.string.obesityDes);
    }

    private void setBarsColorToBlack()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.myBlack));
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.myBlack));
        }
    }
}
