package com.example.ajoan.welcome;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.ajoan.utils.blackbutler.BlackButler;
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
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

public class ChoosePassActivity extends AppCompatActivity {

    private Context meGod = this;

    private RequestQueue queue;
    private ReQstr reqstr;

    private boolean onTheFly = false;
    private String submitListener = WebAppDirectory.serverUrl + WebAppDirectory.signup;

    //external inputs
    public final static String USERMAIL = "email";
    public final static String USERNAME = "username";
    //internal inputs
    private final static String USERPASS ="pass";
    private final static String USERPASS2 ="confirm";

    private Map<String, JSONObject> inputsMap = new HashMap<>();
    private TextView passMSGTV;
    private Button submitBtn;

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

        FormManager.initTextView((TextView) title.findViewById(R.id.pageTitle),"Choix du mot de passe");

        (submitBtn = FormManager.initButton((Button) submit.findViewById(R.id.submitBtn),"Suivant",false)
        ).setOnClickListener(new View.OnClickListener() {
            @Override  public void onClick(View v) { submit(); }
        });

        try {
            inputsMap.put(USERPASS, FormManager.initInput(
                    (TextView) input1.findViewById(R.id.inputTitle),
                    "Mot de passe",
                    (EditText) input1.findViewById(R.id.inputET),
                    "Mot de passe",
                    TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD,
                    (ProgressBar) input1.findViewById(R.id.inputPB),
                    (TextView) input1.findViewById(R.id.inputMSG)
                    ).put("rule", Rules.PASS_RULE)
                            .put("manual", "Il faut au moins 8 caractères dont au moins un chiffre, une Majuscule et une minuscule")
            );

            passMSGTV=((TextView) inputsMap.get(USERPASS).get("msg"));//convenience

            inputsMap.put(USERPASS2, FormManager.initInput(
                    (TextView) input2.findViewById(R.id.inputTitle),
                    "Vérification",
                    (EditText) input2.findViewById(R.id.inputET),
                    "Vérification du mot de passe",
                    TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD,
                    (ProgressBar) input2.findViewById(R.id.inputPB),
                    (TextView) input2.findViewById(R.id.inputMSG)
                    )
            );

            for(final Map.Entry<String,JSONObject> entry : inputsMap.entrySet() ) {

                ((EditText) entry.getValue().get("input")).addTextChangedListener(
                        new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                try {
                                    if (!FormManager.validInput(null, entry.getKey(), entry.getValue(), meGod, submitBtn))
                                        return; //warning message already displayed, cant go further

                                    if (!
                                            (((EditText) inputsMap.get(USERPASS).get("input")).getText().toString())
                                                    .equals
                                                            (((EditText) inputsMap.get(USERPASS2).get("input")).getText().toString())
                                            ) {
                                        FormManager.showMsgTV(((TextView) inputsMap.get(USERPASS2).get("msg")),
                                                "La vérification ne correspond pas au mot de passe", getResources());
                                        return;
                                    }

                                    FormManager.dropMsgTV(((TextView) inputsMap.get(USERPASS2).get("msg")));
                                    FormManager.enableButton(submitBtn);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
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
        if (!onTheFly){
            FormManager.disableButton(submitBtn);

            try {

                Log.i("ChoosePassActivity", "/submit : Sending this request to server" );

                MoodClient mc = new MoodClient() {
                    @Override
                    public void onReply(int rpcode, String message, JSONObject result) {
                        Log.i("ChoosePassActivity", "onReply : '"+rpcode+"'-> 'result:" + result + "' message :"+message+"'");
                        Bundle b = new Bundle();
                        b.putString(LoginActivity.USERNAME,getIntent().getStringExtra(USERNAME));
                        startActivity(
                                Utils.intent(new Intent(meGod, LoginActivity.class), null, null)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        .putExtras(b)
                        );
                    }

                    @Override
                    public void onIssue(int iscode) {
                        Log.i("ChoosePassActivity", "onIssue : '" + iscode + "'");
                        if(iscode==-43)
                            FormManager.showMsgTV(passMSGTV,Messages.remote.get(iscode),getResources());
                        else
                            Messages.displayMSG(meGod,Messages.remote.get(iscode));
                    }

                    @Override
                    public void onResponse(String response) {
                        try{
                            onTheFly = false;
                            Log.i("ChoosePassActivity","onResponse : "+response);
                            FormManager.enableButton(submitBtn);
                            BlackButler.popNserve(meGod,this,response);
                        } catch (Exception e) {
                            Messages.displayMSGOnError(meGod,e);
                        }
                    }
                };

                Response.ErrorListener err = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ChoosePassActivity", "onErrorResponse : '" + error + "'", error);
                        onTheFly = false;
                        Messages.displayMSGOnNetworkError(meGod,error);
                        FormManager.enableButton(submitBtn);
                    }
                };



                reqstr.send(
                        WebAppDirectory.signup,
                        mc,err,
                        null,
                        USERMAIL+"->"+getIntent().getStringExtra(USERMAIL),
                        USERNAME+"->"+getIntent().getStringExtra(USERNAME),
                        USERPASS+"->"+((EditText)inputsMap.get(USERPASS).get("input")).getText()
                );

                onTheFly = true;
            } catch (Exception e) {
                Messages.displayMSGOnError(meGod,e);
            }
        }
    }

    //class end
}
