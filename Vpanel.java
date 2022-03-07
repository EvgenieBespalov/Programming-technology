package com.company;

import javax.swing.*;
import java.awt.*;

class VPanel extends JPanel
{
    VPanel(int x, int y) 
    {
        setPreferredSize(new Dimension(x, y));
        setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    
        for (int i = 0; i < Habitat.array_r.size(); i++)
        { 
                g.drawImage(Habitat.array_r.get(i).img, Habitat.array_r.get(i).getX()*45, Habitat.array_r.get(i).getY()*45, null);
        }    
        
        for (int i = 0; i < Habitat.array_w.size(); i++)
        { 
            if(Habitat.array_w.get(i) instanceof WolfsWomen)
                g.drawImage(((WolfsWomen)Habitat.array_w.get(i)).img, Habitat.array_w.get(i).getX()*45, Habitat.array_w.get(i).getY()*45, null);
            else
                g.drawImage(((WolfsMen)Habitat.array_w.get(i)).img, Habitat.array_w.get(i).getX()*45, Habitat.array_w.get(i).getY()*45, null);
                
        }
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        drawWeb(g2d);
        g2d.setColor(Color.BLUE);
    }

    
    private void drawWeb (Graphics2D g2d)    
    {
        int iSizeX = 20;
        int iSizeY = 20;

        int X = 900/iSizeX;
        int Y = 900/iSizeY;
        
        for (int i = 0; i < iSizeX; i++) 
        {
            g2d.drawLine(X * (i + 1), 0 , X * (i + 1),900);
        }

        for (int i = 0; i < iSizeY; i++) 
        {
            g2d.drawLine(0, Y * (i + 1), 900, Y * (i + 1));
        }
    }
}
