package org.fhi360.ddd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.provider.Settings;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.User;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;

import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private TextView createUser, text_forget_password;
    private Button login;
    private ProgressDialog progressdialog;
    private String deviceconfigId;
    private Locale myLocale;
    private Spinner localeSpinner;
    private Locale mCurrentLocale;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.login);
        DDDDb.getInstance(getApplicationContext()).patientRepository().delete();
        deviceconfigId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        createUser = findViewById(R.id.text_create_account);
        login = findViewById(R.id.sign_in_button);
        text_forget_password = findViewById(R.id.text_forget_password1);
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
        createUser.setMovementMethod(LinkMovementMethod.getInstance());
        text_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(deviceconfigId);
            }
        });
        User id = DDDDb.getInstance(this).userRepository().findByOne();
        if (id == null) {
            createUser.setVisibility(View.VISIBLE);
        } else {
            createUser.setVisibility(View.VISIBLE);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput(username.getText().toString(), password.getText().toString())) {
                    User user = DDDDb.getInstance(LoginActivity.this).userRepository().findByUsernameAndPassword(username.getText().toString(), password.getText().toString());
                    if (user != null && user.getRole().equalsIgnoreCase("DDD outlet")) {
                        Intent intent = new Intent(LoginActivity.this, OutletWelcome.class);
                        save(user.getName());
                        startActivity(intent);
                    } else if (user != null && user.getRole().equalsIgnoreCase("Facility")) {
                        Intent intent = new Intent(LoginActivity.this, FacilityWelcome.class);
                        save(user.getName());
                        startActivity(intent);
                    } else {
                        FancyToast.makeText(getApplicationContext(), "Wrong username or password or role", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }

                }
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        mCurrentLocale = getResources().getConfiguration().locale;
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Locale locale = getLocale(this);
//
//        if (!locale.equals(mCurrentLocale)) {
//
//            mCurrentLocale = locale;
//            recreate();
//        }
//    }
//
//    public static Locale getLocale(Context context){
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//
//        String lang = sharedPreferences.getString("language", "es");
//        switch (lang) {
//            case "English":
//                lang = "en";
//                break;
//            case "Spanish":
//                lang = "es";
//                break;
//        }
//        return new Locale(lang);
//    }

    private void create() {
        Intent intent = new Intent(this, Account.class);
        startActivity(intent);
    }


    private boolean validateInput(String username1, String password1) {
        if (username1.isEmpty()) {
            username.setError("username can not be empty");
            return false;
        } else if (password1.isEmpty()) {
            password.setError("password can not be empty");
            return false;
        }
        return true;


    }

    private void showAlert(final String deviceconfigId) {
        LayoutInflater li = LayoutInflater.from(LoginActivity.this);
        View promptsView = li.inflate(R.layout.forget_pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).create();
        dialog.setView(promptsView);
        final TextView notitopOk, notitopNotnow;
        final EditText notitoptxt;
        notitopOk = promptsView.findViewById(R.id.notitopOk);
        notitopNotnow = promptsView.findViewById(R.id.notitopNotnow);
        notitoptxt = promptsView.findViewById(R.id.notitoptxt);
        notitopNotnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        notitopOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notitoptxt.getText().toString().isEmpty()) {
                    notitoptxt.setError("Acivation code can't empty");
                } else {
                    User usernameAndPassword = DDDDb.getInstance(LoginActivity.this).userRepository().findByOne();
                    if (usernameAndPassword != null) {
                        username.setText(usernameAndPassword.getUsername());
                        password.setText(usernameAndPassword.getPassword());
                    }
                    dialog.dismiss();
                }

            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }


    private void getPassworFromLamis(String deviceId, String pin) {
        progressdialog = new ProgressDialog(LoginActivity.this);
        progressdialog.setMessage("App Loading credential please wait...");
        progressdialog.setCancelable(false);
        progressdialog.setIndeterminate(false);
        progressdialog.setMax(100);
        progressdialog.show();
        APIService apiService = new APIService();
        ClientAPI clientAPI = apiService.createService(ClientAPI.class);

        Call<User> objectCall = clientAPI.getUsernamePasswordFromLamis(deviceId, pin);

        objectCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User dataObject = response.body();
                    username.setText(dataObject.getUsername());
                    password.setText(dataObject.getPassword());
                    if (dataObject.getUsername() == null && dataObject.getPassword() == null || dataObject.getUsername() == "" && dataObject.getPassword() == "") {
                        FancyToast.makeText(getApplicationContext(), "Your account does not exist on the server", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        progressdialog.dismiss();
                    }
                    progressdialog.dismiss();
                } else if (response.code() == 500) {
                    FancyToast.makeText(getApplicationContext(), "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                } else if (response.code() == 400) {
                    FancyToast.makeText(getApplicationContext(), "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                } else if (response.code() == 404) {
                    FancyToast.makeText(getApplicationContext(), "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(getApplicationContext(), "No Internet Connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressdialog.dismiss();
            }
        });

    }


    public void save(String name) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("name", name);
        editor.apply();
    }

    public HashMap<String, String> get() {
        HashMap<String, String> name = new HashMap<>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        name.put("name", sharedPreferences.getString("name", null));
        return name;
    }

    public void setLocale() {
        myLocale = new Locale("fr");
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
//        Intent refresh = new Intent(this, LoginActivity.class);
//        startActivity(refresh);
//        finish();
    }

}
