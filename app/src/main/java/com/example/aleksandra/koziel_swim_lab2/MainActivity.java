package com.example.aleksandra.koziel_swim_lab2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Float.parseFloat;

public class MainActivity extends Activity implements View.OnClickListener
{


    Typeface appTypeface;
    @BindView(R.id.mass) TextView massTextView;
    @BindView(R.id.myHeight) TextView heightTextView;
    @BindView(R.id.yourBmi) TextView yourBmiTextView;
    @BindView(R.id.bmiSignature) TextView bmiSignatureTextView;
    @BindView(R.id.massUnits) TextView unitsMassTextView;
    @BindView(R.id.heightUnits) TextView unitsHeightTextView;
    @BindView(R.id.yourMass)EditText userMassEditText;
    @BindView(R.id.yourHeight)EditText userHeightEditText;
    @BindView(R.id.countButton) Button countButton;
    @BindView(R.id.units) Button unitsButton;
    @BindView(R.id.menuHamburgerButton) Button menuButton;
    @BindView(R.id.moodLayout) RelativeLayout relativeLayout;
    @BindView(R.id. imageView) ImageView moodIcon;

    boolean isMetric;
    final float minGoodBmi = 18.5f;
    final float maxGoodBmi = 25.0f;
    final float maxOverweightBmi = 30.0f;
    final float centimetersToMeters = 100;
    final float startValue = 1.0f;
    public float memorizedBmi = 0;
    public boolean flagFirstCount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initializeAll();
        loadSavedPreferences();
        setBarsColorToBlack();
    }

    public void initializeAll()
    {
        ButterKnife.bind(this);
        //typefaces
        //activity layout
        appTypeface = Typeface.createFromAsset(getAssets(), "alcubierre.ttf");
        massTextView.setTypeface(appTypeface);
        heightTextView.setTypeface(appTypeface);
        yourBmiTextView.setTypeface(appTypeface);
        bmiSignatureTextView.setTypeface(appTypeface);
        unitsMassTextView.setTypeface(appTypeface);
        unitsHeightTextView.setTypeface(appTypeface);
        userMassEditText.setTypeface(appTypeface);
        userHeightEditText.setTypeface(appTypeface);
        countButton.setTypeface(appTypeface);
        unitsButton.setTypeface(appTypeface);


        isMetric=true;
        relativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.additionalColor));
        countButton.setOnClickListener(this);
        unitsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.countButton)
        {
            bmiDataCount();

        }
        else if(view.getId()==R.id.units)
        {
            unitsChanging();
        }
    }

    public void bmiDataCount()
    {
        CountBmiForKgM countUserBmi = new CountBmiForKgM();
        try
        {
            if (!TextUtils.isEmpty(userHeightEditText.getText()) && !TextUtils.isEmpty(userMassEditText.getText())) {

                String userHeight = userHeightEditText.getText().toString();
                String userMass = userMassEditText.getText().toString();
                Float numericUserMass = parseFloat(userMass);
                Float numericUserHeight = parseFloat(userHeight);

                if (!isMetric)
                {
                    numericUserHeight = countUserBmi.countHeightImperial(numericUserHeight);
                    numericUserMass = countUserBmi.countMassImperial(numericUserMass);
                }
                else
                {
                    numericUserHeight = numericUserHeight / centimetersToMeters;
                }

                if (countUserBmi.isMassValid(numericUserMass) && countUserBmi.isHeightValid(numericUserHeight)) {
                    float result = countUserBmi.countBmi(numericUserMass, numericUserHeight);
                    changeColors(result);
                    flagFirstCount=true;
                    memorizeBmi(result);
                    yourBmiTextView.setText((String.format(java.util.Locale.US, "%.2f", result)));
                } else {
                    wrongInputToast();
                }
            } else {
                emptyEditTextToast();
            }
        }
        catch(NumberFormatException e)
        {
            wrongInputToast();
        }

        setBarsColorToBlack();
    }

    public float memorizeBmi(float result)
    {
        memorizedBmi = result;
        return memorizedBmi;
    }

    public void unitsChanging()
    {
        isMetric=!isMetric;
        if(isMetric)
        {
            unitsButton.setText(R.string.imperialUnits);
            unitsMassTextView.setText(R.string.layoutKg);
            unitsHeightTextView.setText(R.string.layoutCm);
        }
        else
        {
            unitsButton.setText(R.string.metricUnits);
            unitsMassTextView.setText(R.string.layoutLb);
            unitsHeightTextView.setText(R.string.layoutFt);
        }
    }


    //pop-up menu
    public void onPopupButtonClick(View toPopupView)
    {
        ContextThemeWrapper wrapForPopupStyle = new ContextThemeWrapper(this, R.style.CustomPopupTheme);
        PopupMenu popup = new PopupMenu(wrapForPopupStyle, toPopupView);
        MenuInflater menuInflater = popup.getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu,popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                boolean flag = true;
                if(item.getItemId()==R.id.Save)
                {
                    onSaveButton();
                }
                else if(item.getItemId()==R.id.Share)
                {
                    onShareButton();
                }
                else if(item.getItemId()==R.id.About)
                {
                    onAboutButton();
                }
                else
                {
                    flag = false;
                }
                return flag;
            }
        });
    }

    //save button
    public void onSaveButton()
    {
        CountBmiForKgM checkValuesToSave = new CountBmiForKgM();
        String userHeightToSave = userHeightEditText.getText().toString();
        String userMassToSave = userMassEditText.getText().toString();
        Float numericUserMassToSave = parseFloat(userMassToSave);
        Float numericUserHeightToSave = parseFloat(userHeightToSave);

        if (!isMetric)
        {
            numericUserHeightToSave = checkValuesToSave.countHeightImperial(numericUserHeightToSave);
            numericUserMassToSave = checkValuesToSave.countMassImperial(numericUserMassToSave);
        }
        else
        {
            numericUserHeightToSave = numericUserHeightToSave/centimetersToMeters;
        }

        if(checkValuesToSave.isMassValid(numericUserMassToSave)&&checkValuesToSave.isHeightValid(numericUserHeightToSave))
        {
            savePreferences("savedMass",userMassToSave);
            savePreferences("savedHeight",userHeightToSave);

        }
        else
        {
            wrongInputToast();
        }

    }

    private void savePreferences(String key, String value)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String massToReload = sharedPreferences.getString("savedMass","");
        String heightToReload = sharedPreferences.getString("savedHeight", "");
        userMassEditText.setText(massToReload);
        userHeightEditText.setText(heightToReload);
    }


    //share button
    public void onShareButton()
    {
        try
        {
            if(memorizedBmi>startValue)
            {
                String message=createMessage();
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
            else
            {
                countEnterToast();

            }
        }
        catch (Exception e)
        {
            exportImageToast();
        }
    }

    public String createMessage()
    {
        String memorizedBmiString = String.format(java.util.Locale.US, "%.2f", memorizedBmi);
        String messageToShare=getString(R.string.monitShareStart)+memorizedBmiString;
        if (memorizedBmi < minGoodBmi)
        {
            messageToShare+=getString(R.string.monitShareUnderweight);
        }
        else if(memorizedBmi>=minGoodBmi&&memorizedBmi<maxGoodBmi)
        {
            messageToShare+=getString(R.string.monitShareNormal);
        }
        else if(memorizedBmi>=maxGoodBmi&&memorizedBmi<maxOverweightBmi)
        {
            messageToShare+=getString(R.string.monitShareOverweight);
        }
        if (memorizedBmi>=maxOverweightBmi)
        {
            messageToShare+=getString(R.string.monitShareObesity);
        }
        messageToShare+=getString(R.string.monitShareAppId);
        return messageToShare;
    }
    //about bmi button
    public void onAboutButton()
    {
        Intent toAboutActivity = new Intent(this, AboutActivity.class);
        Bundle resultBmi = new Bundle();

        resultBmi.putFloat("currentBmi", memorizedBmi);
        toAboutActivity.putExtras(resultBmi);
        startActivity(toAboutActivity);
    }


    //TOASTS
    public void wrongInputToast()
    {
        Toast.makeText(getApplicationContext(), R.string.toastWrongImput, Toast.LENGTH_SHORT).show();
    }
    public void emptyEditTextToast()
    {
        Toast.makeText(getApplicationContext(), R.string.toastAllData, Toast.LENGTH_SHORT).show();
    }
    public void exportImageToast()
    {
        Toast.makeText(getApplicationContext(), R.string.toastExport, Toast.LENGTH_SHORT).show();
    }
    public void countEnterToast()
    {
        Toast.makeText(getApplicationContext(), R.string.toastClick, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("savedHeight",userHeightEditText.getText().toString());
        savedInstanceState.putString("savedMass",userMassEditText.getText().toString());

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        userHeightEditText.setText(savedInstanceState.getString("savedHeight"));
        userMassEditText.setText(savedInstanceState.getString("savedMass"));
        bmiDataCount();
    }


    //colors
    public void changeColors(float result)
    {
        if (result < minGoodBmi)
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
        else if (result>=maxOverweightBmi)
        {
            changeStyleToObesity();
        }
    }

    public void changeStyleToUnderweight()
    {
        //main layout
        relativeLayout.setBackgroundColor((ContextCompat.getColor(this, R.color.redDark)));
        moodIcon.setImageDrawable(getResources().getDrawable(R.drawable.sad, getApplicationContext().getTheme()));
        countButton.setTextColor((ContextCompat.getColor(this, R.color.redDark)));
        getWindow().setStatusBarColor((ContextCompat.getColor(this, R.color.redDark)));

    }

    public void changeStyleToGood()
    {
        //main layout
        relativeLayout.setBackgroundColor((ContextCompat.getColor(this, R.color.greenDark)));
        moodIcon.setImageDrawable(getResources().getDrawable(R.drawable.smile, getApplicationContext().getTheme()));
        countButton.setTextColor((ContextCompat.getColor(this, R.color.greenDark)));
        getWindow().setStatusBarColor((ContextCompat.getColor(this, R.color.greenDark)));

    }

    public void changeStyleToOverweight()
    {
        //main layout
        relativeLayout.setBackgroundColor((ContextCompat.getColor(this, R.color.yellowDark)));
        moodIcon.setImageDrawable(getResources().getDrawable(R.drawable.neutral, getApplicationContext().getTheme()));
        countButton.setTextColor(ContextCompat.getColor(this, R.color.yellowDark));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.yellowDark));

    }

    public void changeStyleToObesity()
    {
        //main layout
        relativeLayout.setBackgroundColor((ContextCompat.getColor(this, R.color.redDark)));
        moodIcon.setImageDrawable(getResources().getDrawable(R.drawable.sad, getApplicationContext().getTheme()));
        countButton.setTextColor((ContextCompat.getColor(this, R.color.redDark)));
        getWindow().setStatusBarColor((ContextCompat.getColor(this, R.color.redDark)));

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
