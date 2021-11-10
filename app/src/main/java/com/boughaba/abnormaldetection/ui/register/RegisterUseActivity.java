package com.boughaba.abnormaldetection.ui.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.boughaba.abnormaldetection.MainActivity;
import com.boughaba.abnormaldetection.R;
import com.boughaba.abnormaldetection.model.User;
import com.boughaba.abnormaldetection.utils.Constants;
import com.boughaba.abnormaldetection.utils.SharedPrefsUtils;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.basgeekball.awesomevalidation.ValidationStyle.TEXT_INPUT_LAYOUT;

public class RegisterUseActivity extends AppCompatActivity {

    private boolean firstTimeRegistration;
    private User loggedUser;

    private AwesomeValidation mAwesomeValidation;
    private SharedPrefsUtils sharedPrefsUtils;

    @BindView(R.id.name_login)
    TextInputLayout nameLogin;

    @BindView(R.id.first_name_login)
    TextInputLayout firstNameLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        disableDarkMode();
        firstTimeRegistration = getIntent().getBooleanExtra(Constants.FIRST_TIME_REGISTRATION, false);
        init();
    }

    //Disable Dark Mode
    public void disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void init() {
        sharedPrefsUtils = SharedPrefsUtils.getInstance(this);
        loggedUser = sharedPrefsUtils.getUser();
        if (loggedUser != null) {
            nameLogin.getEditText().setText(loggedUser.getName());
            firstNameLogin.getEditText().setText(loggedUser.getFirstName());
        }
        mAwesomeValidation = new AwesomeValidation(TEXT_INPUT_LAYOUT);
        mAwesomeValidation.addValidation(nameLogin, "[a-zA-Z\\s]+", "Le nom est obligatoire");
        mAwesomeValidation.addValidation(firstNameLogin, "[a-zA-Z\\s]+", "Le prenom est obligatoire");
    }

    @OnClick(R.id.bt_save_user)
    void onSaveClicked() {
        if (mAwesomeValidation.validate()) {
            sharedPrefsUtils.saveUser(new User(
                    nameLogin.getEditText().getText().toString(),
                    firstNameLogin.getEditText().getText().toString()
            ));
            if (firstTimeRegistration) {
                Intent intent = new Intent(RegisterUseActivity.this, MainActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }
}
