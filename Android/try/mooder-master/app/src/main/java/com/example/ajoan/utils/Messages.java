package com.example.ajoan.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joan on 01/04/2017.
 */

public class Messages {

    public static final String msgOnNetworkError= "Impossible de joindre le serveur!\n " +
            "- Merci de vérifier votre connexion internet \n" +
            "- Ou essayez plus tard";

    public static final String msgOnError= "Une erreur vient de se produire -_-";



    public static final Map<Integer,String> remote = new HashMap<Integer, String>();
    static
    {
        /* unavailable resources -1* */
        remote.put(-11,"Identifiant déjà pris");
        remote.put(-12,"Adresse email déjà prise");
        remote.put(-13,"numéro de téléphone déjà pris");

	/* unknown resources -2* */
        remote.put(-20,"Oups! Nous n'avons pas trouvé ce que vous cherchez");
        remote.put(-21,"Adresse email inconnue");
        remote.put(-22,"Identifiant ou mot de passe erroné");
        remote.put(-23,"Utilisateur inconnue");

	/*bad context -3* */
        remote.put(-33,"Merci de confirmer votre compte puis rééssayez");
        int USER_NOT_CONFIRMED = -33;

	/* invalid formats -4* */
        remote.put(-41,"Identifiant invalide");
        remote.put(-42,"Adresse email invalide");
        remote.put(-43,"Mot de passe invalide");
    }


    public static void displayMSGOnNetworkError(Context context,Exception e){
        Log.e("MSGOnNetworkError" , context+" encountred network issues : "+e, e);
        displayMSG(context, msgOnNetworkError);
    }

    public static void displayMSGOnError(Context context,Exception e){
        Log.e("MSGOnError" , context+" encountred issues : "+e, e);
        displayMSG(context, msgOnError);
    }

    public static void displayMSG(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
