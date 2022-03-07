package com.company;

import java.awt.*;
import java.util.Random;
import javax.swing.ImageIcon;

class Rabbit extends Animal
{   
    static Image img = new ImageIcon("Rabbit.jpg").getImage();
    int healthy=10;
    
    Rabbit(int x, int y)
    {
        super(x, y);
        Habitat.tabHelp[x][y][0]=1;
    }
    
    @Override
    public void move(long time) 
    {
        Habitat.tabHelp[this.getX()][this.getY()][0]=0;
        
        this.healthy-=1;
        
        if((new Random().nextInt(9)==1))
        {
            int x1 =(this.getX() + ((new Random().nextInt(3))-1));
            int y1 =(this.getY() + ((new Random().nextInt(3))-1));
                   
            if(x1<(20) && x1>=0 && y1<(20) && y1>=0)            
            {               
                this.setX(x1);
                this.setY(y1);
            }
        }
        
        Habitat.tabHelp[this.getX()][this.getY()][0]=1;
    }
}

