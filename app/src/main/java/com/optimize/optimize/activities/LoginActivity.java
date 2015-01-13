package com.optimize.optimize.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.optimize.optimize.R;
import com.optimize.optimize.models.OTUserService;
import com.optimize.optimize.utilities.ToTo;
import com.parse.ParseException;
import com.parse.SignUpCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends OTActivity{
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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
