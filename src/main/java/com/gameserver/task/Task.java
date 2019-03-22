package com.gameserver.task;


import java.util.Timer;
import java.util.TimerTask;

public class Task {
    public Timer timer;

    public Task(TimerTask task, int delay)
    {
        timer = new Timer();
        timer.schedule(task, delay);
    }
}
