package lumbrikdev.gmail.com.insulincalc;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Base_application extends Application {
    public static final String ins_alert_ID = "ins_alert";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ins_alert = new NotificationChannel(
                    ins_alert_ID,
                    "Insulin alert",
                    NotificationManager.IMPORTANCE_HIGH
            );
            ins_alert.setDescription("This is the alert that reminds you about your long term inuslin");
            ins_alert.enableVibration(true);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(ins_alert);
        }
    }
}
