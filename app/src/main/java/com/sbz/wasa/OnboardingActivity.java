package com.sbz.wasa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class OnboardingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_main_layout);

        if (onboardingOccured()) {
            openMainActivity();
        } else {
            SharedPreferences.Editor editor =
                    getSharedPreferences(getString(R.string.sharedPrefOnboardingKey), MODE_PRIVATE).edit();
            editor.putBoolean(getString(R.string.sharedPrefOnboardingKey), true);
            editor.commit();
        }

        PaperOnboardingEngineExtended engine =
                new PaperOnboardingEngineExtended(findViewById(R.id.onboardingRootView),
                        getDataForOnboarding(),
                        getApplicationContext());

        // When swiping between pages in the onboarding screen
//        engine.setOnChangeListener(new PaperOnboardingOnChangeListener() {
//            @Override
//            public void onPageChanged(int oldElementIndex, int newElementIndex) {
//                Toast.makeText(getApplicationContext(), "Swiped from " + oldElementIndex + " to " + newElementIndex, Toast.LENGTH_SHORT).show();
//            }
//        });

        // When moving all the way to the end and exiting the onboarding
        engine.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                openMainActivity();
            }
        });
    }

    private boolean onboardingOccured() {
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.sharedPrefOnboardingKey), MODE_PRIVATE);
        return sharedPreferences.getBoolean(
                getString(R.string.sharedPrefOnboardingKey),
                false);
    }

    private void openMainActivity() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(intent);
    }

    // Data for Onboarding
    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 =
                new PaperOnboardingPage(
                        getString(R.string.onboard_guide_one_title),
                        getString(R.string.onboard_guide_one_text),
                        Color.parseColor(stringFormatColor(R.color.onboard_bg_one)),
                        R.drawable.logo_onboard, R.drawable.ic_one);
        PaperOnboardingPage scr2 =
                new PaperOnboardingPage(
                        getString(R.string.onboard_guide_two_title),
                        getString(R.string.onboard_guide_two_text),
                        Color.parseColor(stringFormatColor(R.color.onboard_bg_two)),
                        R.drawable.world, R.drawable.ic_two);
        PaperOnboardingPage scr3 =
                new PaperOnboardingPage(
                        getString(R.string.onboard_guide_three_title),
                        getString(R.string.onboard_guide_three_text),
                        Color.parseColor(stringFormatColor(R.color.onboard_bg_three)),
                        R.drawable.numbers, R.drawable.ic_three);
        PaperOnboardingPage scr4 =
                new PaperOnboardingPage(
                        getString(R.string.onboard_guide_four_title),
                        getString(R.string.onboard_guide_four_text),
                        Color.parseColor(stringFormatColor(R.color.onboard_bg_four)),
                        R.drawable.message, R.drawable.ic_four);
        PaperOnboardingPage scr5 =
                new PaperOnboardingPage(
                        getString(R.string.onboard_guide_five_title),
                        getString(R.string.onboard_guide_five_text),
                        Color.parseColor(stringFormatColor(R.color.onboard_bg_five)),
                        R.drawable.send, R.drawable.ic_five);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        elements.add(scr4);
        elements.add(scr5);
        return elements;
    }

    private String stringFormatColor(Integer color) {
        return String.format("#%06x", ContextCompat.getColor(this, color) & 0xffffff);
    }
}

class PaperOnboardingEngineExtended extends PaperOnboardingEngine {
    public PaperOnboardingEngineExtended(View rootLayout, ArrayList<PaperOnboardingPage> contentElements, Context appContext) {
        super(rootLayout, contentElements, appContext);
    }

    @Override
    protected ViewGroup createContentTextView(PaperOnboardingPage PaperOnboardingPage) {
        ViewGroup viewGroup = super.createContentTextView(PaperOnboardingPage);
        TextView tvOne = (TextView) viewGroup.getChildAt(0);
        tvOne.setTextColor(Color.WHITE);
        TextView tvTwo = (TextView) viewGroup.getChildAt(1);
        tvTwo.setTextColor(Color.WHITE);

        return viewGroup;
    }
}
