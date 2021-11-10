package com.boughaba.abnormaldetection.ui.home;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.boughaba.abnormaldetection.R;
import com.boughaba.abnormaldetection.service.SensorService;
import com.boughaba.abnormaldetection.ui.contact.AddContactActivity;
import com.boughaba.abnormaldetection.ui.register.RegisterUseActivity;
import com.boughaba.abnormaldetection.utils.Constants;
import com.boughaba.abnormaldetection.utils.ServicesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Unbinder unbinder;
    @BindView(R.id.tv_detection)
    TextView tvDetection;
    @BindView(R.id.fl_detection)
    ImageButton imageButton;
    @BindView(R.id.fl_disable)
    ImageButton imageButtonDisable;
    private boolean serviceStarted;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        serviceStarted = ServicesUtils.isMyServiceRunning(SensorService.class, getActivity().getApplicationContext());
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @OnClick(R.id.fl_detection)
    void activate(){
        startSensorService();
        imageButtonDisable.setVisibility(View.VISIBLE);
        imageButton.setVisibility(View.INVISIBLE);
        tvDetection.setText("Appuyer pour désactiver la détection");

    }

    @OnClick(R.id.fl_disable)
    void disable(){
        stopSensorService();
        imageButton.setVisibility(View.VISIBLE);
        imageButtonDisable.setVisibility(View.INVISIBLE);
        tvDetection.setText("Appuyer pour activer la détection");
    }

    private void startSensorService() {
        if (!ServicesUtils.isMyServiceRunning(SensorService.class, getActivity().getApplicationContext())) {
            Intent startIntent = new Intent(getActivity(), SensorService.class);
            startIntent.setAction(Constants.START_FOREGROUND_ACTION);
            getActivity().startService(startIntent);
            serviceStarted = true;
        }
    }

    private void stopSensorService() {
        if (ServicesUtils.isMyServiceRunning(SensorService.class, getActivity().getApplicationContext())) {
            Intent stopIntent = new Intent(getActivity(), SensorService.class);
            stopIntent.setAction(Constants.STOP_FOREGROUND_ACTION);
            getActivity().startService(stopIntent);
            serviceStarted = false;
        }
    }
}