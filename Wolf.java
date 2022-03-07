package com.company;

import java.util.Random;

class Wolfs extends Animal
{    
    int healthy=10;   
    
    Wolfs(int x, int y)
    {
        super(x, y);
    }
    
    @Override
    public void move(long time) 
    {     
        int x1 =(this.getX() + ((new Random().nextInt(3))-1));
        int y1 =(this.getY() + ((new Random().nextInt(3))-1));
       
        if(x1<(20) && x1>=0 && y1<(20) && y1>=0)            
        {    
            this.setX(x1);
            this.setY(y1);
        }
    }
    
    public void moveW(long time) 
    {

    }
}