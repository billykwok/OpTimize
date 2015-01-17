package com.optimize.optimize.activities;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.optimize.optimize.R;
import com.optimize.optimize.models.OTUserService;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    private boolean isEmailValid() {
        return actxtEmail.getText().toString().contains("@");
    }

    private boolean isPasswordValid() {
        return etxtPassword.getText().toString().length() > 7;
    }

    private boolean isUsernameValid() {
        return !etxtUsername.getText().toString().isEmpty();
    }

    private boolean isFormValid() {
        return isEmailValid()&& isPasswordValid() && isUsernameValid();
    }

    @OnClick(R.id.btnRegister)
    public void registerUser() {
        if (isFormValid()) {
            OTUserService otUserService = new OTUserService();
            String email = actxtEmail.getText().toString();
            String username = etxtUsername.getText().toString();
            String password = etxtPassword.getText().toString();

//            otUserService.setEmail(email);
//            otUserService.setUsername(username);
//            otUserService.setPassword(password);
//            blockForApi();
//            otUserService.signUpInBackground(new SignUpCallback() {
//                @Override
//                public void done(ParseException e) {
//                    if (e == null) {
//                        dismissBlockForApi();
//                        Log.d(TAG_OT, "hooray user create success");
//                    } else {
//                        dismissBlockForApi();
//                        ToTo.show(e.getMessage(), LoginActivity.this);
//                    }
//                }
//            });
        }
    }

}
