package com.example.runic;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class TaskManager {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startJobScheduler(Context context){
        Log.v("TaskScheduler", "Started TaskManager");

        ComponentName serviceComponent = new ComponentName(context,JobSchedulerManager.class);
        JobInfo.Builder builder = new JobInfo.Builder
                (0, serviceComponent);

        builder.setPeriodic(0); //runs over time
        builder.setPersisted(true); // persists over reboot
        //builder.setMinimumLatency(1);
        //builder.setOverrideDeadline(1);
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        if (jobScheduler != null) {
            jobScheduler.schedule(builder.build());
        }
    }
}
