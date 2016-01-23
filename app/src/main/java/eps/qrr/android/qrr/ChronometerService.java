package eps.qrr.android.qrr;

/**
 * Created by Llore on 18/12/2015.
 */

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class ChronometerService extends Service {

    private Timer t = new Timer();
    private static final long INTERVALO_ACTUALIZACION = 10; // En ms
    public static PostSendActivity UPDATE_LISTENER;
    private double chronometer = 0;
    private Handler handler;

    public static void setUpdateListener(PostSendActivity poiService) {
        UPDATE_LISTENER = poiService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startChronometer();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                UPDATE_LISTENER.actualizarCronometro(chronometer);
            }
        };
    }

    @Override
    public void onDestroy() {
        stopChronometer();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void startChronometer() {
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                chronometer += 0.01;
                handler.sendEmptyMessage(0);
            }
        }, 0, INTERVALO_ACTUALIZACION);
    }

    private void stopChronometer() {
        if (t != null)
            t.cancel();
    }

}