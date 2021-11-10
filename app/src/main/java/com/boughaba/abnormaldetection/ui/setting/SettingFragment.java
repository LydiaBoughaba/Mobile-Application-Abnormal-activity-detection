package com.boughaba.abnormaldetection.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.boughaba.abnormaldetection.R;
import com.boughaba.abnormaldetection.ui.alarm.EditAlarmActivity;
import com.boughaba.abnormaldetection.ui.contact.AddContactActivity;
import com.boughaba.abnormaldetection.ui.user.DetailsUserActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingFragment extends Fragment {

    private SettingViewModel settingViewModel;
    private Unbinder unbinder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.cardView)
    void goToDetailsActivity(){
        Intent intent = new Intent(getActivity(), DetailsUserActivity.class);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.cardView1)
    void goToAlarmActivity(){
        Intent intent = new Intent(getActivity(), EditAlarmActivity.class);
        getActivity().startActivity(intent);
    }
}