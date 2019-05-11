package funkyduck.protonmail.com.insulincalc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculateFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calculate, container, false);
        Button CalculateButton = view.findViewById(R.id.calculateButton);
        CalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText carbEditText = view.findViewById(R.id.carbEditText);
                EditText bloodsugarEditText = view.findViewById(R.id.bloodsugarEditText);
                final TextView carbRatioEditText = view.findViewById(R.id.carbRatioEditText);
                final TextView insulinSensitivityEditText = view.findViewById(R.id.sensitivityEditText);
                TextView insulinDoseTextView = view.findViewById(R.id.insulinDoseTextView);
                TextView correctionTextView = view.findViewById(R.id.correctionTextView);
                TextView carbTextView = view.findViewById(R.id.carbTextView);
                EditText goalBGeditText = view.findViewById(R.id.goalBGeditText);


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
        return view;

    }

}
