package com.example.travelproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EntryFragment extends Fragment {
    TextView wantToReg;
    Button enter;
    Fragment registryFragment;
    EditText userID;
    EditText password;
    AccountsDatabase accountsDatabase;
    String pass = "";
    public Account account;
    private static final String key = "Gj11cnegfvDe16Cg17R19R20";
    private static final String initVector = "yfFFbufrjptbayan";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.entry_fragment, container, false);
        return root;
    }

    @Override
    public void onStart() {
        wantToReg = getView().findViewById(R.id.wanttoreg);
        registryFragment = new RegistryFragment();
        userID = getView().findViewById(R.id.et_user_id);
        password = getView().findViewById(R.id.et_entry_password);
        accountsDatabase = AccountManager.getInstance(getContext());
        wantToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.l_entry, registryFragment)
                        .commit();
            }
        });
        enter = getView().findViewById(R.id.b_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());
                    SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
                    new Thread() {
                        @Override
                        public void run() {
                            synchronized (cipher){
                                pass = accountsDatabase.getAccountDao().getPasswordWithId(Integer.parseInt(userID.getText().toString()));
                            }
                        }
                    }.start();
                    byte[] original = cipher.update(pass.getBytes());
//                    pass = original.toString();
                    if (password.getText().toString().equals(pass)){
                        new Thread() {
                            @Override
                            public void run() {
                                account = accountsDatabase.getAccountDao().getAccount(Integer.parseInt(userID.getText().toString()));
                            }
                        }.start();
                        AccountManager.logIn(account);
                        Intent i = new Intent(getActivity(), MyTripsActivity.class);
                        startActivity(i);
                    } else {
                        System.out.println(original.toString());
                        Toast.makeText(getContext(), "Неверный пароль", Toast.LENGTH_SHORT).show();
                    }
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "InvalidAlgorithmParameterException", Toast.LENGTH_SHORT).show();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "NoSuchPaddingException", Toast.LENGTH_SHORT).show();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "NoSuchAlgorithmException", Toast.LENGTH_SHORT).show();
//                } catch (BadPaddingException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getContext(), "BadPaddingException", Toast.LENGTH_SHORT).show();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "InvalidKeyException", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Произошла ошибка при попытке расшифровать пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });
        super.onStart();
    }
}