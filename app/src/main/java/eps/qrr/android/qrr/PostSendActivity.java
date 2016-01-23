package eps.qrr.android.qrr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Llore on 17/11/2015.
 */
public class PostSendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_send);

        final String[] datos =
                new String[]{"Tomate aliñado","Macarrones con salsa carbonara","Esparragos con jamon","Torrada con escalivada"};

        final ListView lstOpciones;

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, datos);

        lstOpciones = (ListView)findViewById(R.id.ListProducts);

        lstOpciones.setAdapter(adaptador);

        //BEGIN: PopUps, POL - 24/11/2015
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PostSendActivity.this);
                builder.setMessage("Ingredientes:\n" +
                        "   - ing1, ing2, ing3, ... , ingN\n" +
                        "\nAlergenos:\n" +
                        "   - alg1, alg2, alg3, ... algN\n");
                builder.setCancelable(false);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //doSomething
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        };

        lstOpciones.setOnItemClickListener(itemClickListener);

        /**
         * = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
         */

        HttpURLConnection httpcon;
        String url = "alumnes-grp01.udl.cat/server-qrr-web/api/rest/order/3";
        String data = "[{\"dishId\":\"3\", \"quantity\":\"22\"}]";
        String result = null;
        try{
//Connect
            httpcon = (HttpURLConnection) ((new URL (url).openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Accept", "application/json");
            httpcon.setRequestMethod("POST");
            httpcon.connect();

//Write
            OutputStream os = httpcon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.close();
            os.close();

////Read
//            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));
//
//            String line = null;
//            StringBuilder sb = new StringBuilder();
//
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//
//            br.close();
//            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
         */

        Toast.makeText(this, "Su pedido ha sido enviado a cocina.", Toast.LENGTH_LONG).show();
        //END: PopUps, POL - 24/11/2015
    }

    public void addProduct(View view) {
        Intent i = new Intent(this, MainActivity.class );
        //i.putExtra("clave", "valor");
        startActivity(i);

    }

    public void paymentOrder(View view) {
        Intent i = new Intent(this, SurveyActivity.class );
        //i.putExtra("clave", "valor");
        startActivity(i);

    }

    public void actualizarCronometro(double chronometer) {  }

//    protected void trySendOrder() {
//
//        new Thread(new Runnable() {
//            JSONArray jarray;
//
//            String requestUrl = BASE_URL + "/order/" + orderId;
//
//            @Override
//            public void run() {
//                jarray = new JSONArray();
//                if(orders != null) {
//                    for(ProductOrderModel order : orders) {
//                        try {
//                            jarray.put(order.toJSON());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,
//                            requestUrl, jarray, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            showPOSTResults(response);
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//                    });
//
//                    MyRequestQueue.getInstance(MainMenu.this).addToRequestQueue(request);
//                }
//            }
//        }).start();
//    }
}