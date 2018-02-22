package la.hackspace.pomodoro;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Eder on 02/02/2018.
 */

public class Empezar extends BroadcastReceiver {

    /**
     * Creación de alarma de descanso
     * @param context Contexto actual
     * @param intent Intent contenedor de alarma
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        //Mensaje de alarma
        Toast.makeText(context, "¡De vuelta al trabajo!", Toast.LENGTH_SHORT).show();

        //region Creación de alarma
        Intent intent2 = new Intent();
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent2, 0);
        Notification noti = new Notification.Builder(context)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Trabajo")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("Pomodoro")
                .setContentText("Vuelve al trabajo")
                .setContentInfo("Recuerda")
                .setSmallIcon(R.drawable.tomateicono)
                .setContentIntent(pIntent).getNotification();
        noti.flags=Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager2 = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager2.notify(1, noti);
        //endregion Creación de alarma
    }
}
