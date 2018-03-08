package fr.tgoncalves.webservices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by lp on 08/03/2018.
 */

public class AdapterAd extends ArrayAdapter<Ad> {
    private Activity activity;
    private ArrayList<Ad> listAd;
    private static LayoutInflater inflater = null;

    public AdapterAd (Activity activity, int textViewResourceId,ArrayList<Ad> listAd) {
        super(activity, textViewResourceId, listAd);
        try {
            this.activity = activity;
            this.listAd = listAd;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public static class ViewHolder {
        public TextView display_title;
        public TextView display_desc;
        public TextView display_price;
        public Button delete_ad;
        public Button edit_ad;

    }

    public View getView(int position, final View convertView, final ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.ad_list_item, null);
                holder = new ViewHolder();

                holder.display_title = (TextView) vi.findViewById(R.id.text_view_ad_title);
                holder.display_desc = (TextView) vi.findViewById(R.id.text_view_ad_desc);
                holder.display_price= (TextView) vi.findViewById(R.id.text_view_ad_price);
                holder.edit_ad= (Button) vi.findViewById(R.id.btnEdit);
                holder.delete_ad= (Button) vi.findViewById(R.id.btnDelete);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.display_title.setText(listAd.get(position).getTitle());
            holder.display_desc.setText(listAd.get(position).getDesc());
            holder.display_price.setText(listAd.get(position).getPrice()+"€");

            holder.delete_ad.setTag(listAd.get(position).getId().toString());
            holder.edit_ad.setTag(listAd.get(position).getId_user().toString());
            holder.delete_ad.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v) {

                    String id =  holder.delete_ad.getTag().toString();
                    String requestUrl = "http://android.tgoncalves.fr/deleteAd.php?id=";
                    requestUrl += id;
                    RequestQueue requestQueue;
                    requestQueue = Volley.newRequestQueue(v.getContext());
                    StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, requestUrl,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Toast.makeText(v.getContext(),"Annonce supprimée",Toast.LENGTH_SHORT);
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error.
                                    Toast.makeText(v.getContext(),"Erreur lors de la suppression de l'annonce",Toast.LENGTH_SHORT);

                                }
                            }
                    );
                    requestQueue.add(deleteRequest);
                }
            });

        } catch (Exception e) {


        }
        return vi;
    }




}