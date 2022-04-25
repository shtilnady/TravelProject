package com.example.travelproject.log;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelproject.MyTripsActivity;
import com.example.travelproject.R;

public class RegistryFragment extends Fragment {
    ImageButton back;
    Button create;
    Fragment registryFragment;
    EditText login;
    EditText createPassword;
    EditText repeatPassword;
    AccountsDatabase accountsDatabase;
    Account account;
    SharedPreferences settings;
    TextView textView;
    int flag;
//    private static final String key = "Gj11cnegfvDe16Cg17R19R20";
//    private static final String initVector = "yfFFbufrjptbayan";
//    String pass;
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
        login = getView().findViewById(R.id.reg_name);
        createPassword = getView().findViewById(R.id.et_create_password);
        repeatPassword = getView().findViewById(R.id.et_repeat_password);
        textView = getView().findViewById(R.id.text_toreg);
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
            @Override
            public void onClick(View v) {
                flag = 0;
                new Thread(){
                    @Override
                    public void run() {
                        if (accountsDatabase.getAccountDao().contains(login.getText().toString()))
                            flag = -1;
                        else
                            flag = 1;
                    }
                }.start();
                while (flag == 0){
                    System.out.println(0);
                }
                if (createPassword.getText().toString().length() < 7){
                    textView.setText("Пароль слишком короткий!");
                    textView.setTextColor(Color.RED);
                } else if (!createPassword.getText().toString().equals(repeatPassword.getText().toString())) {
                    textView.setText("Неверно введён пароль!");
                    textView.setTextColor(Color.RED);
                } else if (flag == -1){
                    textView.setText("Пользователь с таким логином уже есть");
                    textView.setTextColor(Color.RED);
                } else {
                    account = new Account(login.getText().toString(), createPassword.getText().toString());
                    new Thread() {
                        @Override
                        public void run() {
//                            accountsDatabase.getAccountDao().deleteAll();
                            accountsDatabase.getAccountDao().insertAccount(account);
                        }
                    }.start();
                    AccountManager.logIn(account);
                    settings = getActivity().getSharedPreferences("Authorisation", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("Registered", true).apply();
                    editor.putString("Account_ID", account.getLogin()).apply();
                    editor.commit();
                    Intent i = new Intent(getActivity(), MyTripsActivity.class);
                    startActivity(i);
//                    try {
//                        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());
//                        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
//                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//                        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//                        byte[] encrypted = cipher.doFinal(createPassword.getText().toString().getBytes());
//                        pass = encrypted.toString();
//                        account = new Account(name.getText().toString(), pass);
//                        new Thread() {
//                            @Override
//                            public void run() {
//                                accountsDatabase.getAccountDao().insertAccount(account);
//                                NotificationManagerCompat.from(getContext())
//                                        .notify(1, new NotificationCompat.Builder(getContext(), "User_ID")
//                                                .setSmallIcon(R.drawable.preview)
//                                                .setContentTitle("Ваш ID - " + account.getId())
//                                                .setContentText("При входе в приложение вам понадобиться ввести ID и пароль!")
//                                                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build());
//                            }
//                        }.start();
//                        AccountManager.logIn(account);
//                        NotificationManagerCompat.from(getContext())
//                                .notify(1, new NotificationCompat.Builder(getContext(), "User_ID")
//                                        .setSmallIcon(R.drawable.preview)
//                                        .setContentTitle("Ваш ID - " + account.getId())
//                                        .setContentText("При входе в приложение вам понадобится ввести ID и пароль!")
//                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).build());
//                        Intent i = new Intent(getActivity(), MyTripsActivity.class);
//                        startActivity(i);
//                    } catch (InvalidAlgorithmParameterException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getContext(), "InvalidAlgorithmParameterException", Toast.LENGTH_SHORT).show();
//                    } catch (NoSuchPaddingException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getContext(), "NoSuchPaddingException пароль", Toast.LENGTH_SHORT).show();
//                    } catch (IllegalBlockSizeException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getContext(), "IllegalBlockSizeException", Toast.LENGTH_SHORT).show();
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getContext(), "NoSuchAlgorithmException", Toast.LENGTH_SHORT).show();
//                    } catch (BadPaddingException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getContext(), "BadPaddingException", Toast.LENGTH_SHORT).show();
//                    } catch (InvalidKeyException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getContext(), "InvalidKeyException", Toast.LENGTH_SHORT).show();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Toast.makeText(getContext(), "Произошла ошибка при попытке зашифровать пароль", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
        super.onStart();
    }
}
