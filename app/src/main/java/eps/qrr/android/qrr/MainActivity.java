package eps.qrr.android.qrr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // public static productObject productObjectMeal = new productObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureButtonReader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT).show();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void readQR(View view) {
        Intent i = new Intent(this, ProductActivity.class );
        //i.putExtra("clave", "valor");
        startActivity(i);
    }


    public void readQR(String scan_result, String scan_result_format) {
        if (scan_result_format.equals("QR_CODE")) {
            Intent i = new Intent(this, ProductActivity.class );
            // i.putExtra("key", "value");
            i.putExtra("scan_result", scan_result);
            startActivity(i);
        }
    }

    public void goToMaps(View view) {
        Intent i = new Intent(this, GoogleMapsActivity.class );
        startActivity(i);
    }

    /**
     * - - - - - - - - - - - - - - - - QrR Reader management - - - - - - - - - - - - - - - - - - -
     */

    private void configureButtonReader() {
        final Button buttonReader = (Button)findViewById(R.id.btReader);
        buttonReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(MainActivity.this).initiateScan();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        final IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        handleResult(scanResult);
    }

    private void handleResult(IntentResult scanResult) {
        if (scanResult != null) {
            // updateUITextViews(scanResult.getContents(), scanResult.getFormatName());
            readQR(scanResult.getContents(), scanResult.getFormatName());
        } else {
            Toast.makeText(this, "It has not readen anything :(", Toast.LENGTH_SHORT).show();
        }
    }

//    private void updateUITextViews(String scan_result, String scan_result_format) {
//        ((TextView)findViewById(R.id.tvFormat)).setText(scan_result_format);
//        final TextView tvResult = (TextView)findViewById(R.id.tvResult);
//        tvResult.setText(scan_result);
//        Linkify.addLinks(tvResult, Linkify.ALL);
//    }

    /**
     * - - - - - - - - - - - - - - - - QrR Reader management - - - - - - - - - - - - - - - - - - - /
     */
}
