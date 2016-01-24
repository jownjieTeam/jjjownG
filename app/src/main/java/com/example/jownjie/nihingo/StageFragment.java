package com.example.jownjie.nihingo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hebi5 on 1/24/2016.
 * //TODO: this fragment is for the 5, 7, 9 - letter word screens.
 */
public class StageFragment extends Fragment {
    View rootView;

    @OnClick(R.id.five_button_back_menu)
    public void back(){
        getActivity().finish();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_5letterword, container, false);
        ButterKnife.bind(this, rootView);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
