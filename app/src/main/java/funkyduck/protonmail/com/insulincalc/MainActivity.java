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
        InsuEditText = (EditText) findViewById(R.id.InsuEditText);


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
                TextView InsTidTextView = (TextView) findViewById(R.id.InsTidTextView);
                Button InsulinButton = (Button) findViewById(R.id.InsulinButton);
                final TextView AktivInsulinTextView = (TextView) findViewById(R.id.AktivInsulinTextView);
                final TextView InsuEditText = (EditText) findViewById(R.id.InsuEditText);
                int zero = 0;
                AktivInsulinTextView.setText(zero);


                //REGNER UT DOSEN

                double karb = Double.parseDouble(KarbEditText.getText().toString());
                double blodsukker = Double.parseDouble(BlodsukkerEditText.getText().toString());
                double karbfaktor = Double.parseDouble(KarbFaktorEditText.getText().toString());
                double insulinfølsomhet = Double.parseDouble(InsulinFølsomhetEditText.getText().toString());
                double AktivIns = Double.parseDouble(AktivInsulinTextView.getText().toString());
                double korre;

                double kar = karb / karbfaktor;
                String karbdose = String.format("%.1f", kar);
                double KorrStart = blodsukker - 5;
                double korr = KorrStart / insulinfølsomhet;
                if (AktivIns > 0) {
                    korre = korr - AktivIns;                                            //KRASJER APPEN
                }
                else {
                    korre = korr;
                }
                String korreksjon = String.format("%.1f", korre);
                double ins = korre + kar;
                final String insulindose = String.format("%.1f", ins);
                final double insulinDoseValue = Double.valueOf(insulindose);

                InsulindoseTextView.setText("Den totale dosen er " + insulindose + " enheter");
                KorreksjonTextView.setText("Korreksjonen tilsvarer " + korreksjon + " enheter av dosen");
                KarbTextView.setText("Karbohydratene tilsvarer " + karbdose + " enheter av dosen");



                //AKTIV INSULIN



                InsulinButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = InsuEditText.getText().toString();
                        if (!text.equalsIgnoreCase("")) {
                            final int hours = Integer.valueOf(text);
                            final CountDownTimer TotalTid = new CountDownTimer(hours * 3600000, 60000) //Decides how many milliseconds the countdown will be (3600000 being an hour) and how often it will update (60000 is 1 minute)
                            {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    long start_time = System.currentTimeMillis();
                                    double TimePassed = System.currentTimeMillis() - start_time;
                                    int time = hours * 3600000;
                                    Log.d("test", "time: " + time + " , millisUntilFinished: " + millisUntilFinished); //Used this to log the values
                                    DecimalFormat numberFormat = new DecimalFormat("#.0"); //Limits the amount of decimals shown when outputting the active insulin
                                    AktivInsulinTextView.setText("Aktiv insulin: " + numberFormat.format(insulinDoseValue / time * millisUntilFinished)); //Calculates and outputs the active insulin
                                }

                                @Override
                                public void onFinish() {
                                    AktivInsulinTextView.setText("Ingen aktiv insulin"); //Text that is shown when the active insulin timer reached 0
                                }
                            }.start();
                        }
                    }
                });
            }
        });
    }
}
