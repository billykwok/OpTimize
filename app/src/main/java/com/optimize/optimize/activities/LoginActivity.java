package com.optimize.optimize.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.optimize.optimize.R;
import com.optimize.optimize.utilities.FastToast;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends OTActionBarActivity {

    @InjectView(R.id.etUsername)
    EditText etUsername;

    @InjectView(R.id.etEmail)
    EditText etEmail;

    @InjectView(R.id.etPassword)
    EditText etPassword;

    @InjectView(R.id.btnSignIn)
    Button btnSignIn;

    @InjectView(R.id.btnRegister)
    Button btnRegister;

    @InjectView(R.id.btnFacebook)
    Button btnFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        // showHashKey(this);
    }


    private boolean isEmailValid() {
        return etEmail.getText().toString().trim().contains("@");
    }

    private boolean isPasswordValid() {
        return etPassword.getText().toString().trim().length() > 7;
    }

    private boolean isUsernameValid() {
        return !etUsername.getText().toString().trim().isEmpty();
    }

    private boolean isFormValid() {
        return isEmailValid() && isPasswordValid() && isUsernameValid();
    }

    @OnClick(R.id.btnRegister)
    public void onClickBtnRegister() {
        if (isFormValid()) {
            String email = etEmail.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            ParseUser parseUser = new ParseUser();
            parseUser.setEmail(email);
            parseUser.setUsername(username);
            parseUser.setPassword(password);
            parseUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    afterSigIn(e);
                }
            });
        } else {
            FastToast.show("Some fields are wrong", this);
        }
    }

    @OnClick(R.id.btnSignIn)
    public void onClickBtnSignIn() {
        if (isFormValid()) {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    afterSignIn(parseUser, e);
                }
            });
        } else {
            FastToast.show("Some fields are wrong", this);
        }
    }

    @OnClick(R.id.btnFacebook)
    public void onClickBtnFacebook() {
        List<String> permissions = Arrays.asList("public_profile", "user_friends", "user_about_me",
                "user_relationships", "user_birthday", "user_location");
        ParseFacebookUtils.logIn(permissions, this , new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                afterSignIn(parseUser, e);
            }
        });
    }

    void afterSignIn(ParseUser parseUser, ParseException e) {
        if (parseUser == null) {
            FastToast.show("No such user", this);
            return;
        }
        afterSigIn(e);
    }

    void afterSigIn(ParseException e) {
        if (e == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            String errMsg = e.getMessage();
            FastToast.show(errMsg, LoginActivity.this);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

}
