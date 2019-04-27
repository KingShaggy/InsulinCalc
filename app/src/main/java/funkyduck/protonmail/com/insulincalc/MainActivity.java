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

        Button CalculateButton = findViewById(R.id.calculateButton);
        CalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText carbEditText = findViewById(R.id.carbEditText);
                EditText bloodsugarEditText = findViewById(R.id.bloodsugarEditText);
                final TextView carbRatioEditText = findViewById(R.id.carbRatioEditText);
                final TextView insulinSensitivityEditText = findViewById(R.id.sensitivityEditText);
                TextView insulinDoseTextView = findViewById(R.id.insulinDoseTextView);
                TextView correctionTextView = findViewById(R.id.correctionTextView);
                TextView carbTextView = findViewById(R.id.carbTextView);
                EditText goalBGeditText = findViewById(R.id.goalBGeditText);


                //Calculating the dose

                double carb = Double.parseDouble(carbEditText.getText().toString());
                double bloodsugar = Double.parseDouble(bloodsugarEditText.getText().toString());
                double carbRatio = Double.parseDouble(carbRatioEditText.getText().toString());
                double insulinSensitivity = Double.parseDouble(insulinSensitivityEditText.getText().toString());
                double goalBG = Double.parseDouble(goalBGeditText.getText().toString());

                double kar = carb / carbRatio;
                String carbDose = String.format("%.1f", kar);
                double corrStart = bloodsugar - goalBG;
                double corre = corrStart / insulinSensitivity;
                String correction = String.format("%.1f", corre);
                double ins = corre + kar;
                final String insulinDose = String.format("%.1f", ins);

                insulinDoseTextView.setText("The total dosage is " + insulinDose + " units");
                correctionTextView.setText("The correction accounts for " + correction + " units");
                carbTextView.setText("The carbs account for " + carbDose + " units");
            }
        });
    }
}
