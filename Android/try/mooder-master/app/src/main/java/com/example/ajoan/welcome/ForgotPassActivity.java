package com.example.ajoan.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ajoan.MyApp;
import com.example.ajoan.maps.R;
import com.example.ajoan.utils.WebAppDirectory;
import com.example.ajoan.utils.FormManager;
import com.example.ajoan.utils.Messages;
import com.example.ajoan.utils.Rules;
import com.example.ajoan.utils.Utils;
import com.example.ajoan.utils.jeez.reqstr.ReQstr;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;

public class ForgotPassActivity extends AppCompatActivity {

    private Context meGod = this;

    private RequestQueue queue;
    private ReQstr reqstr;

    private boolean onTheFly = false;

    //external inputs
    public final static String USERMAIL = "usermail";

    private Map<String, JSONObject> inputsMap = new HashMap<>();
    private Map<String, Boolean> formValidationMap = new HashMap<>();
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        queue = ((MyApp)getApplication()).queue;
        reqstr = ((MyApp)getApplication()).reqstr;

        RelativeLayout title = (RelativeLayout)findViewById(R.id.title);
        RelativeLayout input1 = (RelativeLayout)findViewById(R.id.input1);
        RelativeLayout submit = (RelativeLayout)findViewById(R.id.submit);

        FormManager.initTextView((TextView) title.findViewById(R.id.pageTitle),"Récupération de compte");

        (submitBtn = FormManager.initButton((Button) submit.findViewById(R.id.submitBtn),"Envoyer un email",false)
        ).setOnClickListener(new View.OnClickListener() {
            @Override  public void onClick(View v) { submit(); }
        });

        try {
            inputsMap.put(USERMAIL, FormManager.initInput(
                    (TextView) input1.findViewById(R.id.inputTitle),
                    "Email",
                    (EditText) input1.findViewById(R.id.inputET),
                    "Email utilisateur",
                    TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                    (ProgressBar) input1.findViewById(R.id.inputPB),
                    (TextView) input1.findViewById(R.id.inputMSG)
                    ).put("rule", Rules.USERNMAIL_RULE)
            );

            ((EditText) inputsMap.get(USERMAIL).get("input")).addTextChangedListener(
                    new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            try {
                                if (!FormManager.validInput(formValidationMap, USERMAIL, inputsMap.get(USERMAIL), meGod, submitBtn))
                                    return; //warning message already displayed, cant go further

                                FormManager.validFormOnInputChange(formValidationMap, USERMAIL,submitBtn);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });

        } catch (JSONException e) { throw new RuntimeException(e); }
    }



    private void submit() {
        if (!onTheFly){
            FormManager.disableButton(submitBtn);

            try {

                Log.i("ForgotPassActivity", "/submit : Sending request toserver");

                Response.Listener<String> mc =new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onTheFly = false;
                        Log.i("ForgotPassActivity","onResponse : "+response);
                        startActivity(
                                Utils.intent(new Intent(meGod, LoginActivity.class), null, null)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        );

                        //todo verifier qu il clear bien les previous task ::later
                        //todo store user id and response content username

                        //todo finish all prev
                        FormManager.enableButton(submitBtn);
                    }
                };

                Response.ErrorListener err=new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ForgotPassActivity", "onErrorResponse : '" + error + "'", error);
                        onTheFly = false;
                        Messages.displayMSGOnNetworkError(meGod,error);
                        FormManager.enableButton(submitBtn);
                    }
                };

                reqstr.send(
                        WebAppDirectory.forgotPass,
                        mc,err,
                        null,
                        USERMAIL +"->"+((EditText)inputsMap.get(USERMAIL).get("input"))
                );

                onTheFly = true;
            } catch (Exception e) {
                Messages.displayMSGOnError(meGod,e);
            }
        }
    }
}
