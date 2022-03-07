package com.company;
import javax.swing.*;

public class Main 
{
    public static void main(String[] args)//создаем фрейм в потоке диспетчеризации событи
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        new GUI(); //Вызываем класс визуализации
    }
}