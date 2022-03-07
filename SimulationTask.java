package com.company;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

class SimulationTask extends TimerTask 
{
    static long time;
    static AtomicLong timeSum = new AtomicLong(0);
    private Habitat simulation;
    
    static long GetTime() 
    {
        return time;
    }
    
    SimulationTask(Habitat simulation, long time) 
    {
        this.simulation = simulation;
        this.time = time;
    }
    
    @Override
    public void run() 
    {
        simulation.update(time);
        ++time;
        timeSum.set(timeSum.get() + 1);
    }

    public AtomicLong getTimeSum() 
    {
        return timeSum;
    }
}