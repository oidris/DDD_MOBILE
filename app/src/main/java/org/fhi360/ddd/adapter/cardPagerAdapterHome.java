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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.OutletHome;
import org.fhi360.ddd.OutletRecievedAndRequest;
import org.fhi360.ddd.PatientList;
import org.fhi360.ddd.R;
import org.fhi360.ddd.ReportHomeOption;
import org.fhi360.ddd.domain.CardItem;
import org.fhi360.ddd.domain.User;
import org.fhi360.ddd.util.DateUtil;
import org.fhi360.ddd.util.PrefManager;
import org.fhi360.ddd.webservice.HttpGetRequest;
import org.fhi360.ddd.webservice.HttpPostRequest;
import org.fhi360.ddd.webservice.WebserviceResponseHandler;
import org.fhi360.ddd.webservice.WebserviceResponseServerHandler;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class cardPagerAdapterHome extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private Context context;
    private EditText otp_view;
    private ProgressDialog progressDialog;
    String serverUrl = null;

    public cardPagerAdapterHome() {
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
        CardView cardView = view.findViewById(R.id.cardView);
        ImageView imageView = view.findViewById(R.id.imageView);

        Button assigined = view.findViewById(R.id.assigined);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        if (position == 0) {
            imageView.setImageResource(R.drawable.firsttimevisit);
            assigined.setBackgroundResource(R.drawable.background_button_accent);
            assigined.setText("Check Assigned Clients");
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlert();
                }
            });
        }
        if (position == 1) {
            assigined.setText("Click More");
            imageView.setImageResource(R.drawable.revisit);
            assigined.setBackgroundResource(R.drawable.background_button_accent1);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PatientList.class);
                    context.startActivity(intent);
                }
            });
        }

        if (position == 2) {
            imageView.setImageResource(R.drawable.inventorymgticon);
            assigined.setText("Click More");
            assigined.setBackgroundResource(R.drawable.background_button_accent2);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OutletRecievedAndRequest.class);
                    context.startActivity(intent);
                }
            });
        }
        if (position == 3) {
            imageView.setImageResource(R.drawable.reporticon);
            assigined.setText("Click More");
            assigined.setBackgroundResource(R.drawable.background_button_accent3);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ReportHomeOption.class);
                    context.startActivity(intent);
                }
            });

        }
        if (position == 4) {
            assigined.setText("Click More");
            imageView.setImageResource(R.drawable.synchronizeicon);
            assigined.setBackgroundResource(R.drawable.background_button_accent3);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("App Synchronization with Server in Progress...");
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(false);
                    progressDialog.setMax(100);
                    progressDialog.show();
                    countDownTimer.start();

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

    private CountDownTimer countDownTimer = new CountDownTimer(10000, 100) {
        public void onTick(long millisUntilFinished) {
            progressDialog.setProgress(Math.abs((int) millisUntilFinished / 100 - 100));
        }

        @Override
        public void onFinish() {
            FancyToast.makeText(context, "Sync Was successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            progressDialog.dismiss();
        }
    };

    public HashMap<String, String> get() {
        HashMap<String, String> name = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("name", Context.MODE_PRIVATE);
        name.put("name", sharedPreferences.getString("name", null));
        return name;
    }


    private void bind(CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);

        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
    }


    public void showAlert() {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.pop_up_activation, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(promptsView);
        final EditText notitoptxt;
        final TextView cancel_action;
        final Button activate;
        notitoptxt = promptsView.findViewById(R.id.notitoptxt);
        otp_view = promptsView.findViewById(R.id.otp_view);
        activate = promptsView.findViewById(R.id.activate_button);
        cancel_action = promptsView.findViewById(R.id.cancel_action);
        serverUrl = context.getResources().getString(R.string.server_url);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput(otp_view.getText().toString())) {
                    String pin = "la0019F2228";
                    otp_view.getText().toString();
                    @SuppressLint("HardwareIds")
                    String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    String accountUserName = "";
                    String accountPassword = "";
                    User usernameAndPasswords = DDDDb.getInstance(context).userRepository().findByOne();
                    if (usernameAndPasswords != null) {
                        accountUserName = usernameAndPasswords.getUsername();
                        accountPassword = usernameAndPasswords.getPassword();
                    }
                    if (pin.contains("F")) {
                        String url = "resources/webservice/mobile/activate/" + "fhi360" + "/" + pin + "/" + deviceId + "/" + accountUserName + "/" + accountPassword;
                        activate1("GET", url);
                        Intent intent = new Intent(context, PatientList.class);
                        context.startActivity(intent);
                    } else if (pin.contains("DD")) {
                        String url = "api/hts/mobile/dd/activate/" + pin + "/" + deviceId + "/" + accountUserName + "/" + accountPassword;
                        activate("GET", url);

                    }

                }
                new PrefManager(context).saveIpAddress(notitoptxt.getText().toString());
                dialog.dismiss();
            }


        });

        dialog.setCancelable(false);
        dialog.show();
    }


    public void activate1(String method, String url) {
        //Create a progress dialog window
        progressDialog = new ProgressDialog(context);
        //Close window on pressing the back button
        progressDialog.setCancelable(false);
        //Set a message
        progressDialog.setMessage("Please wait activation in progress.....");
        progressDialog.show();
        url = serverUrl + url;
        new WebserviceInvocator1(context).execute(method, url);
    }

    public void activate(String method, String url) {
        //Create a progress dialog window
        progressDialog = new ProgressDialog(context);
        //Close window on pressing the back button
        progressDialog.setCancelable(false);
        //Set a message
        progressDialog.setMessage("Please wait activation in progress.....");
        progressDialog.show();
        url = serverUrl + url;
        Log.v("URL", url);
        new WebserviceInvocator(context).execute(method, url);
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
                context.startActivity(intent);
                ;
                progressDialog.dismiss();
            } else {
                FancyToast.makeText(context, "Error while activating, Check your internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
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
                context.startActivity(intent);
                progressDialog.dismiss();
            } else {
                FancyToast.makeText(context, "Error while activating, Check your internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressDialog.dismiss();
            }
        }

    }


    private boolean validateInput(String pin) {
        if (pin.isEmpty()) {
            otp_view.setError("Enter Activation code");
            return false;
        }
        return true;


    }


}