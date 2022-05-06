package com.example.travelproject.log;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;

import com.example.travelproject.MyTripsActivity;
import com.example.travelproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EntryFragment extends Fragment {
    TextView wantToReg;
    Button enter;
    Fragment registryFragment;
    EditText etEmail;
    EditText etPassword;
    SharedPreferences settings;
    FirebaseAuth auth;
    ImageButton passwordEye;
    boolean hide = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.entry_fragment, container, false);
        return root;
    }

    @Override
    public void onStart() {
        auth = FirebaseAuth.getInstance();
        wantToReg = getView().findViewById(R.id.wanttoreg);
        registryFragment = new RegistryFragment();
        etEmail = getView().findViewById(R.id.et_login);
        etPassword = getView().findViewById(R.id.et_entry_password);
        passwordEye = getView().findViewById(R.id.b_entry_show_password);
        wantToReg.setOnClickListener(v -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.l_entry, registryFragment)
                .commit());
        enter = getView().findViewById(R.id.b_enter);
        enter.setOnClickListener(v -> {
            enterUser();
        });
        passwordEye.setOnClickListener(v -> {
            if (hide) {
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hide = !hide;
                passwordEye.setBackgroundResource(R.drawable.icon_opened_eye);
            } else {
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                hide = !hide;
                passwordEye.setBackgroundResource(R.drawable.icon_closed_eye);
            }
        });
        super.onStart();
    }

    private void enterUser(){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            etEmail.setError("Email не может быть пустым");
            etEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)){
            etPassword.setError("Пароль не может быть пустым");
            etPassword.requestFocus();
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Вы успешно вошли", Toast.LENGTH_SHORT).show();
                        settings = getActivity().getSharedPreferences("Authorisation", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("Registered", true).apply();
                        editor.putString("Account_ID", email).apply();
                        editor.commit();
                        startActivity(new Intent(getActivity(), MyTripsActivity.class));
                    } else {
                        Toast.makeText(getContext(), "Вход не удался: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}