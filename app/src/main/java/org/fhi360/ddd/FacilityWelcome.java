package org.fhi360.ddd;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.Response;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FacilityWelcome extends AppCompatActivity {

    private ProgressDialog progressdialog;
    TextView pharmacy, next;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facility_welcome);
        pharmacy = findViewById(R.id.pharmacy);
        next = findViewById(R.id.next);
        HashMap<String, String> name = get();
        String userName = name.get("name");
        assert userName != null;
        String firstLettersurname = String.valueOf(userName.charAt(0));
        String fullSurname = firstLettersurname.toUpperCase() + userName.substring(1).toLowerCase();
        pharmacy.setText(fullSurname);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FacilityWelcome.this, FacilityHome.class);
                startActivity(intent);
            }
        });
//        LinearLayout registerDDDOutlet = findViewById(R.id.registerDDDOutlet);
//        inventory = findViewById(R.id.inventory);
//
//        registerClient = findViewById(R.id.clientRegistration);
//        summaryReport = findViewById(R.id.summaryReport);
//        synchronize = findViewById(R.id.synchronize);
//        HashMap<String, String> name = get();
//        String userName = name.get("name");
//        assert userName != null;
//        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome, " + userName.toUpperCase());
//        registerDDDOutlet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FacilityHome.this, RegisterOutLet.class);
//                startActivity(intent);
//            }
//        });
//
//        registerClient.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FacilityHome.this, NewVisit.class);
//                startActivity(intent);
//            }
//        });
//
//        summaryReport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FacilityHome.this, ReportHomeOptionFacility.class);
//                startActivity(intent);
//            }
//        });
//
//        synchronize.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<Patient> patients = DDDDb.getInstance(getApplicationContext()).patientRepository().findByAll();
//                sync(patients);
//            }
//        });
//
//        inventory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FacilityHome.this, InventorySetup.class);
//                startActivity(intent);
//            }
//        });
    }


    private void sync(final List<Patient> patientList) {
        progressdialog = new ProgressDialog(FacilityWelcome.this);
        progressdialog.setMessage("DDD outlet saving...");
        progressdialog.setCancelable(false);
        progressdialog.setIndeterminate(false);
        progressdialog.setMax(100);
        progressdialog.show();
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Response> objectCall = clientAPI.sync(patientList);
        objectCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<org.fhi360.ddd.domain.Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    FancyToast.makeText(getApplicationContext(), "Sync was successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    progressdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<org.fhi360.ddd.domain.Response> call, Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(getApplicationContext(), "No Internet Connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressdialog.dismiss();
            }


        });

    }

    public HashMap<String, String> get() {
        HashMap<String, String> name = new HashMap<>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        name.put("name", sharedPreferences.getString("name", null));
        return name;

    }
}

