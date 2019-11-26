package com.example.eyeballinapp;

import androidx.fragment.app.Fragment;

public class ListenActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ListenFragment();
    }
}
