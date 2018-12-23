package com.lace.aj.lacer.welcome.activties;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.lace.aj.lacer.R;

public class SigninActivity extends AppCompatActivity {

    private Context meGod = this;
    private RequestQueue mQueue;

    // UI references.
    private View rootLayout;
    private TextView mTitleTV;
    private AutoCompleteTextView mEmailInput;
    private EditText mPasswordInput;
    private Button mSignInButton;
    private View mProgressView;
    private View mLoginFormLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

       /* mQueue = ((App) getApplication()).appQueue;

        rootLayout = findViewById(R.id.rootLayout);
        mTitleTV = (TextView) findViewById(R.id.signin_form_title);
        mEmailInput = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordInput = (EditText) findViewById(R.id.password);
        mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mLoginFormLayout = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mTitleTV.setText(getString(R.string.signin_title));

        mEmailInput.addTextChangedListener(
                TextWatchers.birthEmailWatcher(meGod, mSignInButton));

        mPasswordInput.addTextChangedListener(
                TextWatchers.birthPasswordWatcher(meGod, mSignInButton));

        mSignInButton.setText(getString(R.string.signin_form_submit_text));
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
   */
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made. */
    private void attemptLogin() {

        // Reset errors.
        mEmailInput.setError(null);
        mPasswordInput.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailInput.getText().toString();
        String password = mPasswordInput.getText().toString();

        boolean cancel = false;
        View focusView = null;



        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordInput.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordInput;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailInput.setError(getString(R.string.error_field_required));
            focusView = mEmailInput;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailInput.setError(getString(R.string.error_invalid_email));
            focusView = mEmailInput;
            cancel = true;
        }





        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            //shout request todo
        }
    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     * Shows the progress UI and hides the login form. */
    private void showProgress(final boolean show) {
        /*TypedArray array = getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.colorBackground, android.R.attr.textColorPrimary
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormLayout.setVisibility(show ? View.GONE : View.VISIBLE);

        //https://stackoverflow.com/questions/3656586/android-how-to-get-background-color-of-activity-in-java
        if (show)
            rootLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        else
            rootLayout.setBackgroundColor(Utils.getThemeInitialBackgroundColor(meGod));
   */
    }

}

