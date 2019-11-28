package com.gameserver.task;


import java.util.Timer;
import java.util.TimerTask;

public class Task {

    private final TimerTask task;

    public Task(TimerTask task, int delay)
    {
        Timer timer = new Timer();
        timer.schedule(task, delay);
        this.task = task;
    }

    public TimerTask getTask() {
        return task;
    }
}
