
package org.fhi360.ddd.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.FacilityHome;
import org.fhi360.ddd.InventorySetup;
import org.fhi360.ddd.OutletHome;
import org.fhi360.ddd.PatientList;
import org.fhi360.ddd.R;
import org.fhi360.ddd.RegisterOutLet;
import org.fhi360.ddd.ReportHomeOption;
import org.fhi360.ddd.ReportHomeOptionFacility;
import org.fhi360.ddd.ReportingPeriod;
import org.fhi360.ddd.ReportingPeriod1;
import org.fhi360.ddd.domain.CardItem;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.Response;
import org.fhi360.ddd.domain.User;
import org.fhi360.ddd.util.PrefManager;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;
import org.fhi360.ddd.webservice.HttpGetRequest;
import org.fhi360.ddd.webservice.HttpPostRequest;
import org.fhi360.ddd.webservice.WebserviceResponseHandler;
import org.fhi360.ddd.webservice.WebserviceResponseServerHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FacilityCardPagerAdapter extends PagerAdapter implements CardAdapter {
    private ProgressDialog progressDialog;
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private Context context;
    private EditText otp_view;

    String serverUrl = null;

    public FacilityCardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(Context context, CardItem item) {
        this.context = context;
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @SuppressLint("SetTextI18n")
    @NotNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        ImageView imageView = view.findViewById(R.id.imageView);

        Button assigined = view.findViewById(R.id.assigined);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        if (position == 0) {
            imageView.setImageResource(R.drawable.clientregistrationicon);
            assigined.setBackgroundResource(R.drawable.background_button_accent);
            assigined.setText("Click More");
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RegisterOutLet.class);
                    context.startActivity(intent);
                }
            });
        }
        if (position == 1) {
            assigined.setText("Click More");
            imageView.setImageResource(R.drawable.inventorymgticon);
            assigined.setBackgroundResource(R.drawable.background_button_accent1);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, InventorySetup.class);
                    context.startActivity(intent);
                }
            });
        }

        if (position == 2) {
            assigined.setText("Click More");
            imageView.setImageResource(R.drawable.reporticon);
            assigined.setBackgroundResource(R.drawable.background_button_accent2);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ReportHomeOptionFacility.class);
                    context.startActivity(intent);
                }
            });
        }
        if (position == 3) {
            assigined.setText("Click More");
            imageView.setImageResource(R.drawable.synchronizeicon);
            assigined.setBackgroundResource(R.drawable.background_button_accent3);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Patient> patients = DDDDb.getInstance(context).patientRepository().findByAll();
                    sync(patients);
                }
            });
        }


        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }




    private void bind(CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
    }


    private void sync(final List<Patient> patientList) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("DDD outlet saving...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.show();
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Response> objectCall = clientAPI.sync(patientList);
        objectCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    FancyToast.makeText(context, "A sincronização foi bem sucedida", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<org.fhi360.ddd.domain.Response> call, Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(context, "Sem conexão com a Internet", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressDialog.dismiss();
            }


        });

    }

    public HashMap<String, String> get() {
        HashMap<String, String> name = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("name", Context.MODE_PRIVATE);
        name.put("name", sharedPreferences.getString("name", null));
        return name;

    }


}