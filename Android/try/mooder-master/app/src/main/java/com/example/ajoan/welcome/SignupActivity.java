package com.example.ajoan.welcome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ajoan.MyApp;
import com.example.ajoan.maps.R;
import com.example.ajoan.utils.blackbutler.BlackButler;
import com.example.ajoan.utils.WebAppDirectory;
import com.example.ajoan.utils.FormManager;
import com.example.ajoan.utils.Messages;
import com.example.ajoan.utils.MoodClient;
import com.example.ajoan.utils.Rules;
import com.example.ajoan.utils.Utils;
import com.example.ajoan.utils.jeez.reqstr.ReQstr;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
import static android.text.InputType.TYPE_TEXT_VARIATION_PERSON_NAME;

public class SignupActivity extends AppCompatActivity {

    private Context meGod = this;

    private final static String USERMAIL = "email";
    private final static String USERNAME = "username";

    private Map<String, JSONObject> inputsMap = new HashMap<>();
    private Map<String, Boolean> formValidationMap = new HashMap<>();
    private Button submitBtn;

    private RequestQueue queue;
    private ReQstr reqstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        queue = ((MyApp)getApplication()).queue;

        reqstr = ((MyApp)getApplication()).reqstr;

        RelativeLayout title = (RelativeLayout)findViewById(R.id.title);
        RelativeLayout input1 = (RelativeLayout)findViewById(R.id.input1);
        RelativeLayout input2 = (RelativeLayout)findViewById(R.id.input2);
        RelativeLayout submit = (RelativeLayout)findViewById(R.id.submit);
        LinearLayout linksLayout = (LinearLayout)findViewById(R.id.linksLayout);

        FormManager.initTextView((TextView) title.findViewById(R.id.pageTitle),"Nouveau sur Mood");

        (submitBtn = FormManager.initButton((Button) submit.findViewById(R.id.submitBtn),"Suivant",false)
        ).setOnClickListener(new View.OnClickListener() {
            @Override  public void onClick(View v) { submit(); }
        });

        TextView link1 = new TextView(meGod);
        link1.setText("J'ai déjà un compte");
        link1.setTextColor(ContextCompat.getColor(meGod, R.color.colorAccent));
        link1.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
        link1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(
                                Utils.intent(new Intent(meGod, LoginActivity.class), null, null)
                        );
                        finish();
                    }
                }
        );
        linksLayout.addView(link1);

        try {
            inputsMap.put(USERMAIL, FormManager.initInput(
                    (TextView) input1.findViewById(R.id.inputTitle),
                    "Email",
                    (EditText) input1.findViewById(R.id.inputET),
                    "Entre ton adresse email",
                    TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                    (ProgressBar) input1.findViewById(R.id.inputPB),
                    (TextView) input1.findViewById(R.id.inputMSG)
                    ).put("url", WebAppDirectory.emailChk)
                            .put("rule", Rules.USERNMAIL_RULE)
            );

            inputsMap.put(USERNAME, FormManager.initInput(
                    (TextView) input2.findViewById(R.id.inputTitle),
                    "Nom d'utilisateur",
                    (EditText) input2.findViewById(R.id.inputET),
                    "Choisis ton nom sur Mood",
                    TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PERSON_NAME,
                    (ProgressBar) input2.findViewById(R.id.inputPB),
                    (TextView) input2.findViewById(R.id.inputMSG)
                    ).put("url", WebAppDirectory.usernameChk)
                            .put("rule", Rules.USERNAME_RULE)
                            .put("manual", "Un nom d'utilisateur contient au moins 3 caractères et commence par une lettre")
            );

            for(final Map.Entry<String,JSONObject> entry : inputsMap.entrySet() ) {

                final EditText inputET=((EditText) entry.getValue().get("input"));
                final TextView msgTV=((TextView) entry.getValue().get("msg"));
                final ProgressBar checkingPB=((ProgressBar) entry.getValue().get("pb"));

                formValidationMap.put(entry.getKey(),false);

                inputET.addTextChangedListener(
                        new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                try {
                                    queue.cancelAll(entry.getKey()); //cancel all the previous this input related requests

                                    if (!FormManager.validInput(formValidationMap,entry.getKey(),entry.getValue(),meGod,submitBtn))
                                        return; //warning message already displayed, cant go further

                                    FormManager.showProgressBar(checkingPB);
                                    FormManager.showMsgTV(msgTV,"Vérification en cours",getResources());

                                    Log.i("SignupActivity", "/onTextChanged : Sending request to server");

                                    MoodClient mc = new MoodClient() {
                                        @Override
                                        public void onReply(int rpcode, String message, JSONObject result) {
                                            Log.i("SignupActivity", "onReply : result:'" + result + "' - message :'"+message+"'");
                                            FormManager.validFormOnInputChange(formValidationMap,entry.getKey(),submitBtn);
                                        }

                                        @Override
                                        public void onIssue(int iscode) {
                                            Log.i("SignupActivity", "onIssue : '" + iscode + "'");
                                            FormManager.showMsgTV(msgTV,Messages.remote.get(iscode),getResources());
                                        }

                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                Log.i("SignupActivity", "onResponse : '" + response + "'");
                                                FormManager.dropProgressBar(checkingPB);
                                                FormManager.dropMsgTV(msgTV);
                                                BlackButler.popNserve(meGod,this,response);
                                            } catch (Exception e) {
                                                Messages.displayMSGOnError(meGod,e);
                                            }
                                        }
                                    };

                                    Response.ErrorListener err = new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("SignupActivity", "onErrorResponse : '" + error + "'", error);
                                            FormManager.dropProgressBar(checkingPB);
                                            FormManager.dropMsgTV(msgTV);
                                            Messages.displayMSGOnNetworkError(meGod,error);
                                        }
                                    };

                                    reqstr.send(
                                            entry.getValue().getString("url"),
                                            mc,err,
                                            entry.getKey(),//tag
                                            entry.getKey() + "->" + s.toString()
                                    );

                                } catch (Exception e) {
                                    Messages.displayMSGOnError(meGod,e);
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });
            }
        } catch (JSONException e) { throw new RuntimeException(e); }
    }



    private void submit() {
        try {
            Bundle b = new Bundle();
            b.putString(ChoosePassActivity.USERMAIL,
                    ((EditText) inputsMap.get("email").get("input")).getText().toString());
            b.putString(ChoosePassActivity.USERNAME,
                    ((EditText) inputsMap.get("username").get("input")).getText().toString());

            startActivity(
                    Utils.intent(new Intent(meGod, ChoosePassActivity.class), null, null)
                            .putExtras(b)
            );
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
