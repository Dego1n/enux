package com.gameserver.task;


import java.util.Timer;
import java.util.TimerTask;

public class Task {
    public TimerTask task;
    public Timer timer;

    public Task(TimerTask task, int delay, long period)
    {
        this.task = task;
        timer = new Timer();
        timer.schedule(task, delay,period);
    }
}
