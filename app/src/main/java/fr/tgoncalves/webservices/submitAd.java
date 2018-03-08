package fr.tgoncalves.webservices;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class submitAd extends AppCompatActivity {

    EditText Title, Desc, Price;

    // Creating button;
    Button Submit;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String TitleHolder, DescHolder, PriceHolder ;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpUrl = "http://android.tgoncalves.fr/submitAd.php";

    Boolean CheckEditText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_ad);

        // Assigning ID's to EditText.
        Title = (EditText) findViewById(R.id.EditTextAdTitle);

        Desc = (EditText) findViewById(R.id.EditTextAdDesc);

        Price = (EditText) findViewById(R.id.EditTextAdPrice);

        // Assigning ID's to Button.
        Submit = (Button) findViewById(R.id.ButtonSubmitAd);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(submitAd.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(submitAd.this);

        // Adding click listener to button.
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){
                    SubmitAdd();

                }
                else {

                    Toast.makeText(submitAd.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    public void SubmitAdd(){

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(submitAd.this, ServerResponse, Toast.LENGTH_LONG).show();
                        Log.d("ERRORLOG", ServerResponse);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(submitAd.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                        Log.d("ERRORLOG", volleyError.toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("title", TitleHolder);
                params.put("desc", DescHolder);
                params.put("price", PriceHolder);
                params.put("id_user", getIntent().getStringExtra("USERID"));

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(submitAd.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    public void CheckEditTextIsEmptyOrNot(){

        // Getting values from EditText.
        TitleHolder = Title.getText().toString().trim();
        DescHolder = Desc.getText().toString().trim();
        PriceHolder = Price.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if(TextUtils.isEmpty(TitleHolder) || TextUtils.isEmpty(DescHolder) || TextUtils.isEmpty(PriceHolder))
        {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        }
        else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true ;
        }
    }

}