package com.example.runic;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceFragmentCompat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingsActivity extends AppCompatActivity {

    public static void createNotificationChannel(Context context, String ID, String name, String desc) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ID, name, importance);
            channel.setDescription(desc);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel
                        (channel);
            }
        }
    }

    public void getDataFromServer(Context context) throws IOException {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Context appContext = getApplicationContext();
                    GetData getData = new GetData();
                    String allData = getData.getAllData(appContext);
                    RequestBody body = RequestBody.create(
                            MediaType.parse("application/json"), allData);

                    Request request = new Request.Builder()
                            .url("https://user1342-runic.hf.space" + "/run/submit")
                            .post(body)
                            .build();
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(600, TimeUnit.SECONDS)
                            .writeTimeout(600, TimeUnit.SECONDS)
                            .readTimeout(600, TimeUnit.SECONDS)
                            .build();
                    Call call = client.newCall(request);
                    Response response = call.execute();
                    String responseString = response.body().string();

                    String respVal = responseString.substring(39, 43);

                    float responseVal = Float.valueOf(respVal);
                    String SummaryString = "";

                    if (responseVal > 80){
                        SummaryString = "Device integrity is high. Scored " + responseVal + "% out of 100%.";
                    }else{
                        SummaryString = "Device integrity is low. Scored " + responseVal + "% out of 100%.";

                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "124")
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle("Runic Integrity Survey Result")
                            .setContentText(SummaryString)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(1, builder.build());


                    Log.v("TaskScheduler", responseString);




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Log.v("TaskScheduler", "Started Receiver ");
        TaskManager taskManager = new TaskManager();
        taskManager.startJobScheduler(getApplicationContext());
        JobSchedulerManager manager = new JobSchedulerManager();
        Log.v("TaskScheduler", "Started Job Scheduler with tid ");

        createNotificationChannel(getApplicationContext(),"123","Runic Survey","Surveying Device Integrity");
        Intent notificationIntent = new Intent(this, SettingsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder
                (getApplicationContext(), "123")
                .setContentTitle("RUNIC Integrity")
                .setContentText("Runic Integrity is scanning your device")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();

        createNotificationChannel(getApplicationContext(),"124","Runic Result","Survey Results From Runic Integrity");

        try {
            getDataFromServer(this);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}