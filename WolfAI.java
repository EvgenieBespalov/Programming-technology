package com.company;

public class WolfAI extends BaseAI 
{
    final Object nos = new Object();

    @Override
    public void run() 
    {
        while(move)
        {
            synchronized (nos)
            {
                if(GUI.stopWolfAI)
                {
                    try
                    {
                        nos.wait();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                for(int i = 0;i<Habitat.array_w.size();i++)
                {
                    if(Habitat.array_w.get(i) instanceof WolfsMen)
                    {
                        long time = SimulationTask.GetTime();
                        ((WolfsMen)Habitat.array_w.get(i)).moveW(time);
                    }
                    if(Habitat.array_w.get(i) instanceof WolfsWomen)
                    {
                        long time = SimulationTask.GetTime();
                        ((WolfsWomen)Habitat.array_w.get(i)).moveW(time);
                    }
                }
            }
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
