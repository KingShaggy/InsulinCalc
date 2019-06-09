package funkyduck.protonmail.com.insulincalc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

    private EditText bloodsugarEditText;
    private EditText carbRatioEditText;
    private EditText insulinSensitivityEditText;
    private EditText goalBGeditText;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String BG_TEXT = "BGtext";
    public static final String CARB_RATIO = "CarbRatio";
    public static final String INS_SENS = "InsSensitivity";
    public static final String GOAL_BG = "goalBG";

    private String bg_text;
    private String carb_ratio;
    private String ins_sens;
    private String goal_bg;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calculate, container, false);

        Button CalculateButton = view.findViewById(R.id.calculateButton);
        bloodsugarEditText = view.findViewById(R.id.bloodsugarEditText);
        carbRatioEditText = view.findViewById(R.id.carbRatioEditText);
        insulinSensitivityEditText = view.findViewById(R.id.sensitivityEditText);
        goalBGeditText = view.findViewById(R.id.goalBGeditText);

        CalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText carbEditText = view.findViewById(R.id.carbEditText);
                TextView insulinDoseTextView = view.findViewById(R.id.insulinDoseTextView);
                TextView correctionTextView = view.findViewById(R.id.correctionTextView);
                TextView carbTextView = view.findViewById(R.id.carbTextView);


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

                saveData();
            }
        });
        loadData();
        updateViews();
        return view;

    }

    public void saveData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(BG_TEXT, goalBGeditText.getText().toString());
        editor.putString(CARB_RATIO, carbRatioEditText.getText().toString());
        editor.putString(INS_SENS, insulinSensitivityEditText.getText().toString());
        editor.putString(GOAL_BG, goalBGeditText.getText().toString());

        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        bg_text = sharedPreferences.getString(BG_TEXT, "");
        carb_ratio = sharedPreferences.getString(CARB_RATIO, "");
        ins_sens = sharedPreferences.getString(INS_SENS, "");
        goal_bg = sharedPreferences.getString(GOAL_BG, "");
    }

    public void updateViews() {
        bloodsugarEditText.setText(bg_text);
        carbRatioEditText.setText(carb_ratio);
        insulinSensitivityEditText.setText(ins_sens);
        goalBGeditText.setText(goal_bg);
    }

}
