package com.optimize.optimize.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.optimize.optimize.R;
import com.optimize.optimize.models.OTUserService;
import com.optimize.optimize.utilities.FastToast;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends OTActionBarActivity {

    @InjectView(R.id.etxtUsername)
    EditText etxtUsername;
    @InjectView(R.id.actxtEmail)
    AutoCompleteTextView actxtEmail;
    @InjectView(R.id.etxtPassword)
    EditText etxtPassword;
    @InjectView(R.id.btnSignIn)
    Button btnSignIn;
    @InjectView(R.id.btnRegister)
    Button btnRegister;
    @InjectView(R.id.btnFB)
    Button btnFB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
//        showHashKey(this);
    }

//    public void showHashKey(Context context) {
//        try {
//            PackageInfo info = context.getPackageManager().getPackageInfo(
//                    "com.optimize.optimize", PackageManager.GET_SIGNATURES); //Your            package name here
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//        } catch (NoSuchAlgorithmException e) {
//        }
//    }
    private boolean isEmailValid() {
        return actxtEmail.getText().toString().trim().contains("@");
    }

    private boolean isPasswordValid() {
        return etxtPassword.getText().toString().trim().length() > 7;
    }

    private boolean isUsernameValid() {
        return !etxtUsername.getText().toString().trim().isEmpty();
    }

    private boolean isFormValid() {
        return isEmailValid()&& isPasswordValid() && isUsernameValid();
    }

    @OnClick(R.id.btnRegister)
    public void registerUser() {
        if (isFormValid()) {
            String email = actxtEmail.getText().toString().trim();
            String username = etxtUsername.getText().toString().trim();
            String password = etxtPassword.getText().toString().trim();
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
    public void signInUser() {
        if (isFormValid()) {
            String username = etxtUsername.getText().toString().trim();
            String password = etxtPassword.getText().toString().trim();
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

    @OnClick(R.id.btnFB)
    public void signInwithFb() {
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
