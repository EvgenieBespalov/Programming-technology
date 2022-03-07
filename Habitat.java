package com.company;

import java.util.LinkedList;
import java.util.Random;

class Habitat
{
    static int ManagerTimeOfLife = 30;
    static int DeveloperTimeOfLife = 30;
    
    static LinkedList<Wolfs> array_w;
    static LinkedList<Rabbit> array_r;
    
   
    static int CoW = 0; //Менеджера время генерации
    static int CoR = 0; //Менеджера время генерации


    static int i = 0;
    //static int sizeOfArray;
    static int AmountOfWolfes=0;
    static int AmountOfRabbits=0;
    static int[][][] tabHelp = new int[20][20][2];
    
    static private int areaSizeX, areaSizeY;
    
    Habitat(int x, int y)
    {
        areaSizeX = x;
        areaSizeY = y;
        clear_field();
        array_w = new LinkedList<>();
        array_r = new LinkedList<>();
    }
       
    synchronized void update(long time)
    {
        int x, y;
     
        for(int i=0; Habitat.array_w.size()>i ;i++)//берем волка
        {
                for(int j=0; Habitat.array_r.size()>j ;j++)//и ищем кролика
                {       
                        if(Habitat.array_w.get(i).x==Habitat.array_r.get(j).x && Habitat.array_w.get(i).y==Habitat.array_r.get(j).y)
                        {
                            Habitat.array_w.get(i).healthy+=10;
                            Habitat.tabHelp[Habitat.array_r.get(j).x][Habitat.array_r.get(j).y][0]=0;
                            Habitat.array_r.remove(j);
                            --j;
                            --AmountOfRabbits;
                            //System.out.println("Омномном");
                        }
                }
        }
              


        for(int i=0, k=Habitat.array_r.size(); i<k; i++)//размножение кроликов
        {
            if ((new Random().nextInt(5)==1))
            {
                x = Habitat.array_r.get(i).x;
                y = Habitat.array_r.get(i).y;
                Habitat.tabHelp[x][y][0]=1;
                Rabbit Mngr = new Rabbit(x, y);
                //System.out.println("Кролик отпочковался");
                array_r.add(Mngr);
                AmountOfRabbits++;
            }
        }
        for(int i=0, k=Habitat.array_w.size(); i<k; i++)//размножение волков
        {
            if (Habitat.array_w.get(i) instanceof WolfsMen)
            {
                for(int j=0; j<k; j++)
                {
                    if (Habitat.array_w.get(j) instanceof WolfsWomen && ((WolfsWomen)Habitat.array_w.get(j)).pregrance==false && Habitat.array_w.get(i).x==Habitat.array_w.get(j).x && Habitat.array_w.get(i).y==Habitat.array_w.get(j).y)
                    {
                        x = Habitat.array_w.get(j).x;
                        y = Habitat.array_w.get(j).y;
                        ((WolfsWomen)Habitat.array_w.get(j)).pregrance=true;
                        
                        if ((new Random().nextInt(2))==0)
                        {       
                            WolfsMen Wlf = new WolfsMen(x, y);                       
                            array_w.add(Wlf);
                        }
                        else
                        {       
                            WolfsWomen Wlf = new WolfsWomen(x, y);                       
                            array_w.add(Wlf);
                        } 
                        //System.out.println("Волк родился");
                        AmountOfWolfes++;
                    }
                }
            }
        }

        for(int i=0; Habitat.array_w.size()>i;i++)
        {
            if(Habitat.array_w.get(i).healthy<=0)
            {
                array_w.remove(i);
                --i;
                --AmountOfWolfes;
                //System.out.println("Волк умер");
            }
        }
        for(int i=0; Habitat.array_r.size()>i;i++)
        {
            if(Habitat.array_r.get(i).healthy<=0)
            {
                array_r.remove(i);
                --i;
                --AmountOfRabbits;
                //System.out.println("Кролик умер");
            }
        }
             
    }
   
    static public void generateWolf()
    {
        int x, y;
        int N=new Random().nextInt(CoW+1);
        for(int j=0;j<N;j++)
        {    
            x = (new Random().nextInt(20));
            y = (new Random().nextInt(20));
            if ((new Random().nextInt(2))==0)
            {       
                WolfsMen Wlf = new WolfsMen(x, y);                       
                array_w.add(Wlf);
            }
            else
            {       
                WolfsWomen Wlf = new WolfsWomen(x, y);                       
                array_w.add(Wlf);
            }  
        }
        AmountOfWolfes = N;
    }
    static public void generateRabbit()
    {
        int x, y;        
        int N=new Random().nextInt(CoR+1);
        for(int j=0;j<N;j++)
        {           
            x = (new Random().nextInt(20));
            y = (new Random().nextInt(20));
            Rabbit Rbt = new Rabbit(x, y);
                         
            array_r.add(Rbt);
        }
        AmountOfRabbits = N;
    }
    
    public void upd()
    {
        clear_field();
        generateRabbit();
        generateWolf();
    }
    
    static public void clear_field()
    {
        for(int i=0;i<20;i++)
            for(int j=0;j<20;j++)
            {
                tabHelp[i][j][0]=0; //зайцев на поле нет
                tabHelp[i][j][1]=0; //волков на поле нет
            }
    }
    
    public void reset()
    {
    }
    
    public void setX(int x)
    { 
        this.areaSizeX = x;
    }
    public void setY(int y)
    { 
        this.areaSizeY = y;
    }
    
    static public int getAreaSizeY()
    { 
        return areaSizeY;
    }
    static public int getAreaSizeX()
    { 
        return areaSizeX;
    }
}