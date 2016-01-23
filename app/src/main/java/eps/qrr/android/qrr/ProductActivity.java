package eps.qrr.android.qrr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by Llore on 17/11/2015.
 */
public class ProductActivity extends AppCompatActivity {

    private String qrUrlCode = "";
    private String responsedJSON;
    private String responsedJSON2;
    private String productId, productName, productDescription, productprice, productImage, productIngredients = "";

    TextView titleTextView;
    TextView ingredientsTextView;
    TextView priceTextView;
    ProductModel product;

    private String meal = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);

        titleTextView = (TextView) findViewById(R.id.TextProduct);
        ingredientsTextView = (TextView) findViewById(R.id.Ingredients);
        priceTextView = (TextView) findViewById(R.id.Price);

        Bundle bundle = getIntent().getExtras();
        qrUrlCode = bundle.getString("scan_result");

        // Toast.makeText(ProductActivity.this, qrUrlCode, Toast.LENGTH_SHORT).show();

        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute(qrUrlCode);

        try {
            responsedJSON = asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // responsedJSON = "{\"id\":1,\"name\":\"test-product-1\",\"description\":\"test-description-1\",\"price\":2.00,\"image\":null,\"restaurant\":1,\"ingredients\":[{\"id\":2,\"name\":\"Tomato\"},{\"id\":1,\"name\":\"Cheese\"}]}";
        // responsedJSON = responsedJSON2;

        try {
            product = new ProductModel(new JSONObject(responsedJSON));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        titleTextView.setText(product.getName());
        ingredientsTextView.setText(product.getIngredients().toString());
        priceTextView.setText(String.format("%.2f", product.getPrice().floatValue()));

    }

    public void addProduct(View view) {

        /**
         *
         */

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Product quantity:");

        final EditText qtyBox = new EditText(this);
        qtyBox.setHint("Quantity");
        qtyBox.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setView(qtyBox);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("INTERRUPT 1");

                String quantity = qtyBox.getText().toString();
                OrderContainer.getInstances().addOrder(new ProductOrderModel(product, Integer.parseInt(quantity)));

                System.out.println("INTERRUPT 2");
                Intent i = new Intent(ProductActivity.this, OrderActivity.class);
                System.out.println("INTERRUPT 3");
                startActivity(i);
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
    }

    // BEGIN: PopUps, POL - 19/11/2015
    public void moreInfo(View view) {
        TextView            message;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Información adicional");
//        builder.setMessage("- Masa sin gluten\n" +
//                           "- Puede contener trazas de frutos   secos\n" +
//                           "- ...\n");
            builder.setMessage(product.getDescription());
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do things
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        message = (TextView)alert.findViewById(android.R.id.message);
        message.setGravity(Gravity.LEFT);
        alert.show();
    }
    // END: PopUps, POL - 19/11/2015

//    private List<String> convertJSONintoArray(String responsedJSON) throws JSONException {
//
//        JSONObject jsonObject = new JSONObject(responsedJSON);
//        List<String> list = new ArrayList<String>();
//        String ingredientsString = "";
//
//        list.add(jsonObject.getString("id"));
//        list.add(jsonObject.getString("name"));
//        list.add(jsonObject.getString("description"));
//        list.add(jsonObject.getString("image"));
//        list.add(String.valueOf(jsonObject.getDouble("price")));
//        list.add(jsonObject.getString("restaurant"));
//
//        JSONArray ingredients = new JSONArray(jsonObject.getString("ingredients"));
//
//        for (int i = 0; i<ingredients.length(); i++) {
//            JSONObject ingredientName = new JSONObject(ingredients.getString(i));
//            if (i < ingredients.length()-1)
//                ingredientsString += ingredientName.getString("name") + ", ";
//            else
//                ingredientsString += ingredientName.getString("name");
//        }
//
//        list.add(ingredientsString);
//
//        return list;
//    }

    // This needs three types:
    // - Params: The type of the params
    // If "MyAsyncTask" is going to download something then it's going to need an URL,
    // so that URL is a type string, so that is the param type
    // - Progress: The type of the progress
    // The progress is I want to show the progress on the download,
    // we can have a progress bar and I can show the progress from 1 to 100, that is going to be an interject
    // - Result: The type of the result
    // Finally the result is going to be a JSON which will be a string
    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String urlString = params[0];
            InputStream is = null;
            String result = "";

            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(1000);
                conn.setConnectTimeout(1500);
                conn.setRequestMethod("GET");

                conn.connect();

                int response = conn.getResponseCode();

                switch (response) {
                    case 200:
                    case 201:
                        is = conn.getInputStream();
                        result = inputStreamToString(is);
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null)
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            return result;
        }

        private String inputStreamToString(InputStream is) {
            String line = "";
            StringBuilder total = new StringBuilder();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((line = rd.readLine()) != null) {
                    total.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return total.toString();

        }

        // This method is called when our background work has finished
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
