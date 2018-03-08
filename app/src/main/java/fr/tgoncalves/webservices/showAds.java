package fr.tgoncalves.webservices;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;


import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import java.util.ArrayList;

public class showAds extends AppCompatActivity {

    // json array response url
    private String urlJsonArry = "http://android.tgoncalves.fr/adShow.php";

    private static String TAG = showAds.class.getSimpleName();

    private Button btnMakeArrayRequest;

    private Button btnMyAds;

    // Progress dialog
    private ProgressDialog pDialog;

    private ListView adsListView;

    private TextView emptyElem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ads);

        btnMakeArrayRequest = (Button) findViewById(R.id.btnArrayRequest);

        btnMyAds = (Button) findViewById(R.id.btnMyAds);

        adsListView = (ListView) findViewById(R.id.adsList);

        emptyElem = (TextView) findViewById(R.id.emptyElem);

        adsListView.setEmptyView(emptyElem);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btnMakeArrayRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json array request
                makeJsonArrayRequest();
            }
        });

        btnMyAds.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json array request
                makeJsonMyAdsRequest();
            }
        });

    }

    /**
     * Method to make json array request where response starts with [
     * */
    private void makeJsonArrayRequest() {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("RESPONSE", response.toString());

                        ArrayList<Ad> adList = new ArrayList<>();

                        AdapterAd adbAd;

                        try {
                            // Parsing json array response
                            // loop through each json object

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject ad = response.getJSONObject(i);

                                String id = ad.get("id_ad").toString();
                                String title = ad.get("title").toString();
                                String desc = ad.get("desc").toString();
                                String price = ad.get("price").toString();
                                String id_user = ad.get("id_user").toString();

                                Ad annonce = new Ad(id,title,desc,price,id_user);
                                adList.add(annonce);
                            }

                            adbAd= new AdapterAd (showAds.this, 0, adList);
                            adsListView.setAdapter(adbAd);
                            //txtResponse.setText(jsonResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }


    /**
     * Method to make json array request where response starts with [
     * */
        private void makeJsonMyAdsRequest() {

            showpDialog();

            JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("RESPONSE", response.toString());
                            ArrayList<Ad> adList = new ArrayList<>();

                            AdapterAd adbAd;
                            try {
                                // Parsing json array response
                                // loop through each json object
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject ad = response.getJSONObject(i);

                                    String id = ad.get("id_ad").toString();
                                    String title = ad.get("title").toString();
                                    String desc = ad.get("desc").toString();
                                    String price = ad.get("price").toString();
                                    String ad_id_user = ad.get("id_user").toString();

                                    Ad annonce = new Ad(id,title,desc,price,ad_id_user);

                                    if (ad_id_user.equals(getIntent().getStringExtra("USERID"))) {
                                        adList.add(annonce);
                                    }

                                adbAd= new AdapterAd (showAds.this, 0, adList);
                                adsListView.setAdapter(adbAd);

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),
                                        "Error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }

                            hidepDialog();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                    hidepDialog();
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(req);
        }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
