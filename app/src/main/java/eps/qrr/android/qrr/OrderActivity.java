package eps.qrr.android.qrr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Llore on 17/11/2015.
 */
public class OrderActivity extends AppCompatActivity {

    private String  code_Text = "";
    private boolean incorrectCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        final String[] datos =
                new String[]{"Tomate aliñado","Macarrones con salsa carbonara","Esparragos con jamon","Torrada con escalivada"};

        final ListView lstOpciones;



        lstOpciones = (ListView)findViewById(R.id.order_list_view);

        lstOpciones.setAdapter(OrderContainer.getInstances().getAdapter(this));

//        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
//                builder.setMessage("Ingredientes:\n" +
//                        "   - ing1, ing2, ing3, ... , ingN\n" +
//                        "\nAlergenos:\n" +
//                        "   - alg1, alg2, alg3, ... algN\n");
//                builder.setCancelable(false);
//
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        //doSomething
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
//            }
//        };
//
//        lstOpciones.setOnItemClickListener(itemClickListener);
    }

    public void addProduct(View view) {
        Intent i = new Intent(this, MainActivity.class );
        //i.putExtra("clave", "valor");
        startActivity(i);

    }

    //BEGIN: PopUps, POL - 19/11/2015
    public void sendOrder(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
        TextView            message;

        // Set up the input
        final EditText input = new EditText(OrderActivity.this);


        builder.setMessage("Tu pedido será enviado a cocina.\n" +
                "Introduce tu código de validación.\n\n" +
                "Gracias por tu confianza.\n");
        builder.setCancelable(false);

        // Specify the type of input expected;
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                code_Text = input.getText().toString();
                if (code_Text.matches("abcd")) {
                    startActivity(new Intent(getBaseContext(), PostSendActivity.class));
                } else {
                    //dialog.cancel();
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                    builder.setMessage("Error al introducir el código.\n\n" +
                            "Si el problema persiste ponte en contacto" +
                            "con uno de nuestros camareros.\n");
                    builder.setCancelable(false);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //doSomething
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        message = (TextView)alert.findViewById(android.R.id.message);
        message.setGravity(Gravity.LEFT);
        alert.show();
    }
    //END: PopUps, POL - 19/11/2015
}