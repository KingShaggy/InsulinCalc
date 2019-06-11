package funkyduck.protonmail.com.insulincalc;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TimerFragment extends Fragment {
    TextView mTextViewCountDown;
    Button mStartButton;
    EditText mEditTextLongLasting;

    CountDownTimer mCountDownTimer;

    long mTimeLeftInMillis;
    double longInsTime;
    double insTimeInMillis;
    double START_TIME_IN_MILLIS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_timer, container, false);

        mStartButton = view.findViewById(R.id.countdownStartButton);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextLongLasting = getActivity().findViewById(R.id.longLastingEditText);
                String temp_longLasting = mEditTextLongLasting.getText().toString();

                if (temp_longLasting.matches("")) {
                    Toast.makeText(getActivity(),"Please fill out the field", Toast.LENGTH_LONG).show();
                }
                else {
                    longInsTime = Double.parseDouble(temp_longLasting);
                    insTimeInMillis = longInsTime * 3600000;
                    START_TIME_IN_MILLIS = insTimeInMillis;

                    mTimeLeftInMillis = (long) START_TIME_IN_MILLIS;

                    mTextViewCountDown = view.findViewById(R.id.countdownTextView);

                    startTimer();
                }
            }
        });
        return view;
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 60000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    private void updateCountDownText() {
        int hours   = (int) ((mTimeLeftInMillis / (1000*60*60)) % 24);
        int minutes = (int) ((mTimeLeftInMillis / (1000*60)) % 60);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", hours, minutes);

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}
