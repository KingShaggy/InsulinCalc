package funkyduck.protonmail.com.insulincalc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText KarbFaktorEditText;
        KarbFaktorEditText = (EditText) findViewById(R.id.KarbFaktorEditText);
        final EditText InsulinFølsomhetEditText;
        InsulinFølsomhetEditText = (EditText) findViewById(R.id.InsulinFølsomhetEditText);
        final EditText InsuEditText;


        Button CalculateButton = (Button) findViewById(R.id.CalculateButton);
        CalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText KarbEditText = (EditText) findViewById(R.id.KarbEditText);
                EditText BlodsukkerEditText = (EditText) findViewById(R.id.BlodsukkerEditText);
                final TextView KarbFaktorEditText = (EditText) findViewById(R.id.KarbFaktorEditText);
                final TextView InsulinFølsomhetEditText = (EditText) findViewById(R.id.InsulinFølsomhetEditText);
                TextView InsulindoseTextView = (TextView) findViewById(R.id.InsulindoseTextView);
                TextView KorreksjonTextView = (TextView) findViewById(R.id.KorreksjonTextView);
                TextView KarbTextView = (TextView) findViewById(R.id.KarbTextView);
                int zero = 0;


                //REGNER UT DOSEN

                double karb = Double.parseDouble(KarbEditText.getText().toString());
                double blodsukker = Double.parseDouble(BlodsukkerEditText.getText().toString());
                double karbfaktor = Double.parseDouble(KarbFaktorEditText.getText().toString());
                double insulinfølsomhet = Double.parseDouble(InsulinFølsomhetEditText.getText().toString());
                double korre;

                double kar = karb / karbfaktor;
                String karbdose = String.format("%.1f", kar);
                double KorrStart = blodsukker - 5;
                double korr = KorrStart / insulinfølsomhet;
                korre = korr;
                String korreksjon = String.format("%.1f", korre);
                double ins = korre + kar;
                final String insulindose = String.format("%.1f", ins);
                final double insulinDoseValue = Double.valueOf(insulindose);

                InsulindoseTextView.setText("Den totale dosen er " + insulindose + " enheter");
                KorreksjonTextView.setText("Korreksjonen tilsvarer " + korreksjon + " enheter av dosen");
                KarbTextView.setText("Karbohydratene tilsvarer " + karbdose + " enheter av dosen");
            }
        });
    }
}
