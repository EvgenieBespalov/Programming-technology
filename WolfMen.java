package com.company;

import java.awt.*;
import javax.swing.ImageIcon;
import java.util.Random;

class WolfsMen extends Wolfs
{
    static Image img = new ImageIcon("Wolf.jpg").getImage();
    //boolean fertility = false; 
    
    WolfsMen(int x, int y)
    {
        super(x, y);
    }
     
    public void moveW(long time) 
    {
        this.healthy = this.healthy - 1;
        
        int rab_x=0, rab_y=0, check_r=-1, check_w=-1, wol_x=0, wol_y=0;
 
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
                        else
                            {
                                if(Habitat.tabHelp[this.x+x1][this.y+y1][1]==1 && check_r==-1)//если рядом есть волчица, и текущий волк - волк
                                {
                                    wol_x=this.x+x1;
                                    wol_y=this.y+y1;
                                    check_w=1;
                                }
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
            if(check_w==1)
            {
                this.setX(wol_x);
                this.setY(wol_y);
            }
            else
            {    
                move(time);
            }
    }
   
}

