package com.lace.aj.lacer.traffic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lace.aj.lacer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Listener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {
    private Button badd;
    private EditText etxt;
    private Listener mListener;

    public ProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /* badd = (Button) view.findViewById(R.id.badd);
        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"You just added 1 TODO", Toast.LENGTH_SHORT).show();
                Log.i("debug_badd", "badd's click with text :"+etxt.getText());
                mListener.getUserContext(etxt.getText().toString());
                etxt.setText("");//sert aussi d'event pour refresh l'adapter (implicit notifyDataSetChanged)
            }
        });

        etxt = (EditText) view.findViewById(R.id.etxt);
        etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                badd.setEnabled(s.length() > 0);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });*/
    }


    public interface Listener { //todo modify
        void getUserContext(String text);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener)
            mListener = (Listener) context;
        else
            throw new RuntimeException(context.toString()
                    + " must implement Listener");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
