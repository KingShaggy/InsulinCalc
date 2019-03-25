package funkyduck.protonmail.com.insulincalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText KarbFaktorEditText;
        KarbFaktorEditText = (EditText) findViewById(R.id.carbRatioEditText);
        final EditText InsulinFølsomhetEditText;
        InsulinFølsomhetEditText = (EditText) findViewById(R.id.sensitivityEditText);
        final EditText InsuEditText;


        Button CalculateButton = (Button) findViewById(R.id.calculateButton);
        CalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText KarbEditText = (EditText) findViewById(R.id.carbEditText);
                EditText BlodsukkerEditText = (EditText) findViewById(R.id.bloodsugarEditText);
                final TextView KarbFaktorEditText = (EditText) findViewById(R.id.carbRatioEditText);
                final TextView InsulinFølsomhetEditText = (EditText) findViewById(R.id.sensitivityEditText);
                TextView InsulindoseTextView = (TextView) findViewById(R.id.insulinDoseTextView);
                TextView KorreksjonTextView = (TextView) findViewById(R.id.correctionTextView);
                TextView KarbTextView = (TextView) findViewById(R.id.carbTextView);
                TextView goalBGtextView = (TextView) findViewById(R.id.goalBGtextView);
                EditText goalBGeditText = (EditText) findViewById(R.id.goalBGeditText);


                //Calculating the dose

                double karb = Double.parseDouble(KarbEditText.getText().toString());
                double blodsukker = Double.parseDouble(BlodsukkerEditText.getText().toString());
                double karbfaktor = Double.parseDouble(KarbFaktorEditText.getText().toString());
                double insulinfølsomhet = Double.parseDouble(InsulinFølsomhetEditText.getText().toString());
                double korre;
                double goalBG = Double.parseDouble(goalBGeditText.getText().toString());

                double kar = karb / karbfaktor;
                String karbdose = String.format("%.1f", kar);
                double KorrStart = blodsukker - goalBG;
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
