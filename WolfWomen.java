package com.company;

import java.awt.*;
import javax.swing.ImageIcon;
import java.util.Random;

class WolfsWomen extends Wolfs
{    
    static Image img = new ImageIcon("WolfWomen.jpg").getImage();//сделать в наследниках
    boolean pregrance = false; 
   
    WolfsWomen(int x, int y)
    {
        super(x, y);
        Habitat.tabHelp[x][y][1]=1;
    }
     
    public void moveW(long time) 
    {
        this.healthy = this.healthy - 1;
        this.pregrance = false; 
                
        Habitat.tabHelp[this.getX()][this.getY()][1]=0;
        
        int rab_x=0, rab_y=0, check_r=-1;
 
        for(int i=0; Habitat.array_w.size()>i ;i++) //проверяем окружение волка в 8 клетках, check_w - наличие рядом кролика, check_r - наличие рядом волчихи
        {
                for(int x1=-1; x1<2; x1++)
                {       
                    for(int y1=-1; y1<2; y1++)
                    {    
                        if((this.x+x1)>-1 && (this.y+y1)>-1 && (this.x+x1)<20 && (this.y+y1)<20)
                            if(Habitat.tabHelp[this.x+x1][this.y+y1][0]==1)//если рядом есть кролик
                            {
                                rab_x=this.x+x1;
                                rab_y=this.y+y1;
                                check_r=1;
                            }
                    }
                }
        }
        
        if (check_r==1)
        {
            this.setX(rab_x);
            this.setY(rab_y);
        }
        else
            {    
                move(time);
            }
        

        Habitat.tabHelp[this.getX()][this.getY()][1]=1;
    }

}
