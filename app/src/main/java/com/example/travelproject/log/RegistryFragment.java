package com.example.travelproject.log;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelproject.MyTripsActivity;
import com.example.travelproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class RegistryFragment extends Fragment {
    ImageButton back, createPassEye, repeatPassEye;
    Button create;
    Fragment entryFragment;
    EditText etEmail;
    EditText createPassword;
    EditText repeatPassword;
    SharedPreferences settings;
    TextView textView;
    FirebaseAuth auth;
    boolean createHide = true, repeatHide = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.registry_fragment, container, false);

        return root;
    }
    @Override
    public void onStart() {
        auth = FirebaseAuth.getInstance();
        back = getView().findViewById(R.id.b_backtoenter);
        entryFragment = new EntryFragment();
        etEmail = getView().findViewById(R.id.reg_name);
        createPassword = getView().findViewById(R.id.et_create_password);
        repeatPassword = getView().findViewById(R.id.et_repeat_password);
        textView = getView().findViewById(R.id.text_toreg);
        createPassEye = getView().findViewById(R.id.b_create_password_show);
        repeatPassEye = getView().findViewById(R.id.b_repeat_password_show);
        back.setOnClickListener(v -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.l_entry, entryFragment)
                .commit());
        createPassEye.setOnClickListener(v -> {
            if (createHide) {
                createPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                createHide = !createHide;
                createPassEye.setBackgroundResource(R.drawable.icon_opened_eye);
            } else {
                createPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                createHide = !createHide;
                createPassEye.setBackgroundResource(R.drawable.icon_closed_eye);
            }
        });
        repeatPassEye.setOnClickListener(v -> {
            if (repeatHide) {
                repeatPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                repeatHide = !repeatHide;
                repeatPassEye.setBackgroundResource(R.drawable.icon_opened_eye);
            } else {
                repeatPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                repeatHide = !repeatHide;
                repeatPassEye.setBackgroundResource(R.drawable.icon_closed_eye);
            }
        });
        create = getView().findViewById(R.id.b_create);
        create.setOnClickListener(v -> {
            createUser();
        });
        super.onStart();
    }

    private void createUser(){
        String email = etEmail.getText().toString();
        String password = createPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            etEmail.setError("Email не может быть пустым");
            etEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)){
            createPassword.setError("Пароль не может быть пустым");
            createPassword.requestFocus();
        } else if (TextUtils.isEmpty(repeatPassword.getText().toString())){
            repeatPassword.setError("Пароль не может быть пустым");
            repeatPassword.requestFocus();
        } else if (password.length() < 7){
            createPassword.setError("Пароль слишком короткий!");
            createPassword.requestFocus();
        } else if (!password.equals(repeatPassword.getText().toString())) {
            repeatPassword.setError("Неверно введён пароль!");
            repeatPassword.requestFocus();
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                        settings = getActivity().getSharedPreferences("Authorisation", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("Registered", true).apply();
                        editor.putString("Account_ID", email).apply();
                        editor.commit();
                        startActivity(new Intent(getActivity(), MyTripsActivity.class));
                    } else {
                        Toast.makeText(getContext(), "Регистрация не удалась: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
