package com.gameserver.task;


import java.util.Timer;
import java.util.TimerTask;

public class Task {

    public Task(TimerTask task, int delay)
    {
        Timer timer = new Timer();
        timer.schedule(task, delay);
    }
}
