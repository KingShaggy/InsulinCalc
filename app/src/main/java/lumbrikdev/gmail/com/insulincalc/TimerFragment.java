package lumbrikdev.gmail.com.insulincalc;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import static lumbrikdev.gmail.com.insulincalc.Base_application.ins_alert_ID;

public class TimerFragment extends Fragment {
    TextView mTextViewCountDown;
    Button mStartButton;
    EditText mEditTextLongLasting;

    CountDownTimer mCountDownTimer;

    long mTimeLeftInMillis;
    long longInsTime;
    long insTimeInMillis;
    long START_TIME_IN_MILLIS;
    long mEndTime;
    private boolean mTimerRunning;
    String mLeft = "millisLeft";
    String eTime = "endTime";
    String timeRun = "timerRunning";
    SwitchCompat ins_alert_switch;
    public static final String SHARED_PREFS = "sharedPrefs";

    public static final String INS_TIME = "insTime";

    private String ins_time;

    private NotificationManagerCompat notManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_timer, container, false);

        notManager = NotificationManagerCompat.from(requireContext());

        mStartButton = view.findViewById(R.id.countdownStartButton);

        mTextViewCountDown = view.findViewById(R.id.countdownTextView);

        mEditTextLongLasting = view.findViewById(R.id.longLastingEditText);

        ins_alert_switch = view.findViewById(R.id.alert_switch);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp_longLasting = mEditTextLongLasting.getText().toString();

                if (temp_longLasting.matches("")) {
                    Toast.makeText(getActivity(),"Please fill in your insulin time", Toast.LENGTH_LONG).show();
                }
                else if ((Double.parseDouble(temp_longLasting) != Math.floor(Double.parseDouble(temp_longLasting)))) {
                    Toast.makeText(getActivity(),"Please enter a whole number", Toast.LENGTH_LONG).show();
                }
                else {
                    longInsTime = Long.parseLong(temp_longLasting);
                    insTimeInMillis = longInsTime * 3600000;
                    START_TIME_IN_MILLIS = insTimeInMillis;

                    mTimeLeftInMillis = START_TIME_IN_MILLIS;

                    startTimer();
                }
                saveData();
            }
        });
        loadData();
        updateViews();
        return view;
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 60000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                if (ins_alert_switch.isChecked()) {
                    sendNotification();
                }
            }
        }.start();

        mTimerRunning = true;
    }
    private void updateCountDownText() {
        int hours   = (int) ((mTimeLeftInMillis / (1000*60*60)) % 24);
        int minutes = (int) ((mTimeLeftInMillis / (1000*60)) % 60);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", hours, minutes);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong(mLeft, mTimeLeftInMillis);
        editor.putBoolean(timeRun, mTimerRunning);
        editor.putLong(eTime, mEndTime);

        editor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong(mLeft, START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean(timeRun, false);

        if (mTimerRunning) {
            mEndTime = prefs.getLong(eTime, 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
            } else {
                startTimer();
            }
        }
    }
    public void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(INS_TIME, mEditTextLongLasting.getText().toString());

        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        ins_time = sharedPreferences.getString(INS_TIME, "");
    }

    public void updateViews() {
        mEditTextLongLasting.setText(ins_time);
    }
    public void sendNotification() {
        Notification notification = new NotificationCompat.Builder(requireContext(), ins_alert_ID)
            .setSmallIcon(R.drawable.ic_alert)
            .setContentTitle("It's time to set insulin!")
            .setContentText("Your basal countdown has finished")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .build();

        notManager.notify(1, notification);
    }
}
