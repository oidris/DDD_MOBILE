package org.fhi360.ddd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.fhi360.ddd.R;
import com.shashank.sony.fancytoastlib.FancyToast;


import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.User;
import org.fhi360.ddd.util.PrefManager;
import org.fhi360.ddd.webservice.HttpGetRequest;
import org.fhi360.ddd.webservice.HttpPostRequest;
import org.fhi360.ddd.webservice.WebserviceResponseHandler;
import org.fhi360.ddd.webservice.WebserviceResponseServerHandler;

import java.util.HashMap;


public class Activation extends AppCompatActivity implements View.OnClickListener {
    private View layout;
    private Button activateButton;
    private EditText userNameEditText;
    private EditText pinEditText;
    private ProgressDialog progressDialog;
    private TextView serviceProvided;

    String serverUrl = null;
    String localIpAdress = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_activate);
        serverUrl = getResources().getString(R.string.server_url);
      //  serviceProvided = findViewById(R.id.serviceProvided);
        pinEditText = findViewById(R.id.pin);
        //Get reference to the Configure Button
        activateButton = findViewById(R.id.activate_button);
        activateButton.setOnClickListener(this);

        HashMap<String, String> ipAddress = new PrefManager(getApplicationContext()).getIpAddress();
        localIpAdress = ipAddress.get("ipAddress");
        serviceProvided.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();

            }
        });
    }


    @Override
    public void onClick(View view) {
        if (validateInput(pinEditText.getText().toString())) {
            String pin = pinEditText.getText().toString();
            @SuppressLint("HardwareIds")
            String deviceId = Settings.Secure.getString(Activation.this.getContentResolver(), Settings.Secure.ANDROID_ID);
            String accountUserName = "";
            String accountPassword = "";
            User usernameAndPasswords = DDDDb.getInstance(Activation.this).userRepository().findByOne();
            if (usernameAndPasswords != null) {
                accountUserName = usernameAndPasswords.getUsername();
                accountPassword = usernameAndPasswords.getPassword();
            }
            if (pin.contains("F")) {
                String url = "resources/webservice/mobile/activate/" + "fhi360" + "/" + pin + "/" + deviceId + "/" + accountUserName + "/" + accountPassword;
                activate1("GET", url);
            } else if (pin.contains("DD")) {
                String url = "api/hts/mobile/dd/activate/" + pin + "/" + deviceId + "/" + accountUserName + "/" + accountPassword;
                activate("GET", url);
            }

        }
    }

    public void activate1(String method, String url) {
        //Create a progress dialog window
        progressDialog = new ProgressDialog(this);
        //Close window on pressing the back button
        progressDialog.setCancelable(false);
        //Set a message
        progressDialog.setMessage("Please wait activation in progress.....");
        progressDialog.show();
        url = serverUrl + url;
        new WebserviceInvocator1(this).execute(method, url);
    }

    public void activate(String method, String url) {
        //Create a progress dialog window
        progressDialog = new ProgressDialog(this);
        //Close window on pressing the back button
        progressDialog.setCancelable(false);
        //Set a message
        progressDialog.setMessage("Please wait activation in progress.....");
        progressDialog.show();
        url = serverUrl + url;
        Log.v("URL", url);
        new WebserviceInvocator(this).execute(method, url);
    }

    //Perform network access on a different thread from the UI thread using AsyncTask`
    private class WebserviceInvocator extends AsyncTask<String, Void, String> {
        Context context;

        WebserviceInvocator(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String method = params[0];
            if (method.equalsIgnoreCase("POST"))
                result = new HttpPostRequest(context).postData(params[1], params[2]);

            if (method.equalsIgnoreCase("GET"))
                result = new HttpGetRequest(context).getData(params[1]);
            progressDialog.dismiss();
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (!result.isEmpty()) {
                new WebserviceResponseHandler(context).parseResult(result);
                Intent intent = new Intent(context, PatientList.class);
                startActivity(intent);
                finish();
                progressDialog.dismiss();
            } else {
                FancyToast.makeText(getApplicationContext(), "Error while activating, Check your internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressDialog.dismiss();
            }
        }
    }


    //Perform network access on a different thread from the UI thread using AsyncTask`
    private class WebserviceInvocator1 extends AsyncTask<String, Void, String> {
        Context context;

        WebserviceInvocator1(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String method = params[0];
            if (method.equalsIgnoreCase("POST"))
                result = new HttpPostRequest(context).postData(params[1], params[2]);

            if (method.equalsIgnoreCase("GET"))
                result = new HttpGetRequest(context).getData(params[1]);
            progressDialog.dismiss();
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (!result.isEmpty()) {
                new WebserviceResponseServerHandler(context).parseResult(result);
                Intent intent = new Intent(context, PatientList.class);
                startActivity(intent);
                finish();
                progressDialog.dismiss();
            } else {
                FancyToast.makeText(getApplicationContext(), "Error while activating, Check your internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private boolean validateInput(String pin) {
        if (pin.isEmpty()) {
            pinEditText.setError("Enter Activation code");
            return false;
        }
        return true;


    }


    private void showAlert() {
        LayoutInflater li = LayoutInflater.from(Activation.this);
        View promptsView = li.inflate(R.layout.forget_pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(Activation.this).create();
        dialog.setView(promptsView);
        final TextView notitopOk, notitopNotnow;
        final EditText notitoptxt;
        notitopOk = promptsView.findViewById(R.id.notitopOk);
        notitopNotnow = promptsView.findViewById(R.id.notitopNotnow);
        notitoptxt = promptsView.findViewById(R.id.notitoptxt);
        notitoptxt.setHint("Enter server URL");
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
                    notitoptxt.setError("Ip address  can't empty");
                } else {
                    new PrefManager(getApplicationContext()).saveIpAddress(notitoptxt.getText().toString());
                    FancyToast.makeText(getApplicationContext(), "Ip Address saved successfully ", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    dialog.dismiss();
                }

            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

}