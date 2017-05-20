package com.example.danil.newmanager.control;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import com.example.danil.newmanager.model.DBActions;
import com.example.danil.newmanager.model.Task;
import com.example.danil.newmanager.model.TaskHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    private Timer mTimer;
    public NotificationManager nm;
    private final static int NOTIFI_ID = 128;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            try {
                ArrayList<Task> tasks = DBActions.getInstans(getApplicationContext()).getListTasks();
                for(Task task :tasks)
                    if(task.isNotification()){
                        notifiy();
                        break;
                    }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    };

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mTimer = new Timer();
        mTimer.schedule(timerTask, 600*1000, 600*1000);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return super.onStartCommand(intent, flags, startId);
    }

    public void notifiy(){
        nm = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(getApplicationContext(), 0, new Intent[]{intent}, PendingIntent.FLAG_CANCEL_CURRENT);
        builder .setContentIntent(pendingIntent)
                .setSmallIcon(TaskHelper.getImageById((byte)10))
                .setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(), TaskHelper.getImageById((byte)10)))
                .setTicker("Task Manager")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Task Manager")
                .setContentText("Сегодня у вас запланированы задачи");
        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_ALL;

        nm.notify(NOTIFI_ID, notification);
    }
}
