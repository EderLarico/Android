package la.hackspace.pomodoro;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Eder on 01/02/2018.
 */

public class Alarma extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "¡Hora de un descanso!", Toast.LENGTH_SHORT).show();

        Intent intent2 = new Intent();
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent2, 0);
        Notification noti = new Notification.Builder(context)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Descanso")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("Pomodoro")
                .setContentText("Es hora de descansar 5 minutos")
                .setContentInfo("Recuerda")
                .setSmallIcon(R.drawable.tomateicono)
                .setContentIntent(pIntent).getNotification();
        noti.flags=Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager2 = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager2.notify(1, noti);
    }

}
