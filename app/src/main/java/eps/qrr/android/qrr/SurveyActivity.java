package eps.qrr.android.qrr;

//import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by Llore on 17/11/2015.
 */
public class SurveyActivity extends AppCompatActivity {

    Button btnPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);

        //BEGIN: PopUps, POL - 24/11/2015
        btnPayment =(Button) findViewById(R.id.payment);
        btnPayment.setEnabled(false);

        RadioGroup radGrp1 = (RadioGroup) findViewById(R.id.rdbGpVote1);
        RadioGroup radGrp2 = (RadioGroup)findViewById(R.id.rdbGpVote2);

        radGrp1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup _arg0, int _id)
            {
                RadioGroup radGrp2_aux = (RadioGroup)findViewById(R.id.rdbGpVote2);

                if (_id > -1 && radGrp2_aux.getCheckedRadioButtonId() > -1)
                    btnPayment.setEnabled(true);

                else
                    btnPayment.setEnabled(false);
            }
        });

        radGrp2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup _arg0, int _id) {
                RadioGroup radGrp1_aux = (RadioGroup)findViewById(R.id.rdbGpVote1);

                if (_id > -1 && radGrp1_aux.getCheckedRadioButtonId() > -1)
                    btnPayment.setEnabled(true);

                else
                    btnPayment.setEnabled(false);
            }
        });
        //END: PopUps, POL - 24/11/2015
    }

    public void payment(View view) {
        //Intent i = new Intent(this, SurveyActivity.class );
        //i.putExtra("clave", "valor");
        //startActivity(i);

        //BEGIN: PopUps, POL - 19/11/2015
        TextView            message;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tu petición de pago ha sido enviada.\n" +
                           "En breve un camarero te traerá la cuenta.\n\n" +
                           "         Muchas gracias por su visita.\n");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(getBaseContext(),MainActivity.class));
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        message = (TextView)alert.findViewById(android.R.id.message);
        message.setGravity(Gravity.LEFT);
        alert.show();
        //END: PopUps, POL - 19/11/2015
    }
}
