package nl.roydewildt.whofirst;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.github.paolorotolo.appintro.AppIntro;

import java.util.ArrayList;
import java.util.HashSet;

import nl.roydewildt.whofirst.intro.FirstFragment;
import nl.roydewildt.whofirst.intro.SecondFragment;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(new FirstFragment());

        SecondFragment secondFragment = new SecondFragment();
        addSlide(secondFragment);

        setWizardColor();

        showSkipButton(false);
    }

    private void setWizardColor() {
        int color = ContextCompat.getColor(getApplicationContext(), R.color.textColor);
        int color2 = ContextCompat.getColor(getApplicationContext(), R.color.textColorLight);
        setSeparatorColor(color);
        setColorSkipButton(color);
        setColorDoneText(color);
        setNextArrowColor(color);
        setIndicatorColor(color, color2);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        this.finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        this.finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}

