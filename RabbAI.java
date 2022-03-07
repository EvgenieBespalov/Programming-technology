package com.company;

public class RabbAI extends BaseAI 
{
    final Object nos = new Object();

    @Override
    public void run() 
    {
        while(move)
        {
            synchronized (nos)
            {
                if (GUI.stopRabAI)
                {
                    try
                    {
                        nos.wait();
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                for(int i=0;i<Habitat.array_r.size();i++) 
                {
                    long time = SimulationTask.GetTime();
                    Habitat.array_r.get(i).move(time);
                }
            }
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
