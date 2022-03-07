package com.company;

import java.awt.*;
import java.util.Random;
import javax.swing.ImageIcon;

abstract class Animal implements IBehaviour
{
    int x, y;

    Animal(int x, int y)
    {
        this.x = x;
        this.y = y;

    }
    
    public void move(long time)
    {

    }  
    public void setX(int x)
    { 
        this.x = x;
    }
    public void setY(int y)
    { 
        this.y = y;
    }
    @Override
    public int getX()
    { 
        return x;
    }
    @Override
    public int getY()
    {
        return y; 
    }
}
