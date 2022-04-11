package com.example.travelproject;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class RegistryFragment extends Fragment {
    ImageButton back;
    Button create;
    Fragment registryFragment;
    EditText name;
    EditText createPassword;
    EditText repeatPassword;
    AccountsDatabase accountsDatabase;
    Account account;
    private static final String key = "Gj11cnegfvDe16Cg17R19R20";
    private static final String initVector = "yfFFbufrjptbayan";
    String pass;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.registry_fragment, container, false);

        return root;
    }
    @Override
    public void onStart() {
        accountsDatabase = AccountManager.getInstance(getContext());
        back = getView().findViewById(R.id.b_backtoenter);
        registryFragment = new EntryFragment();
        name = getView().findViewById(R.id.reg_name);
        createPassword = getView().findViewById(R.id.et_create_password);
        repeatPassword = getView().findViewById(R.id.et_repeat_password);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.l_entry, registryFragment)
                        .commit();
            }
        });
        create = getView().findViewById(R.id.b_create);
        create.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (createPassword.getText().toString().length() < 7){
                    Toast.makeText(getContext(), "Пароль слишком короткий!", Toast.LENGTH_SHORT).show();
                } else if (!createPassword.getText().toString().equals(repeatPassword.getText().toString())) {
                    Toast.makeText(getContext(), "Неверно введён пароль!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());
                        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                        byte[] encrypted = cipher.doFinal(createPassword.getText().toString().getBytes());
                        pass = encrypted.toString();
                        account = new Account(name.getText().toString(), pass);
                        new Thread() {
                            @Override
                            public void run() {
                                accountsDatabase.getAccountDao().insertAccount(account);
                                NotificationManagerCompat.from(getContext())
                                        .notify(1, new NotificationCompat.Builder(getContext(), "User_ID")
                                                .setSmallIcon(R.drawable.preview)
                                                .setContentTitle("Ваш ID - " + account.getId())
                                                .setContentText("При входе в приложение вам понадобиться ввести ID и пароль!")
                                                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build());
                            }
                        }.start();
                        AccountManager.logIn(account);
                        NotificationManagerCompat.from(getContext())
                                .notify(1, new NotificationCompat.Builder(getContext(), "User_ID")
                                        .setSmallIcon(R.drawable.preview)
                                        .setContentTitle("Ваш ID - " + account.getId())
                                        .setContentText("При входе в приложение вам понадобится ввести ID и пароль!")
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).build());
                        Intent i = new Intent(getActivity(), MyTripsActivity.class);
                        startActivity(i);
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "InvalidAlgorithmParameterException", Toast.LENGTH_SHORT).show();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "NoSuchPaddingException пароль", Toast.LENGTH_SHORT).show();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "IllegalBlockSizeException", Toast.LENGTH_SHORT).show();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "NoSuchAlgorithmException", Toast.LENGTH_SHORT).show();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "BadPaddingException", Toast.LENGTH_SHORT).show();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "InvalidKeyException", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Произошла ошибка при попытке зашифровать пароль", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        super.onStart();
    }
}
