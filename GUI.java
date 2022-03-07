package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;


class GUI extends JFrame
{
    private java.util.Timer timer;
    private long time;
    private boolean isTimerRunning = false,
            isTimeVisible = false,
            isStatisticsVisible = false,
            isShowModalDialog = false;
    static boolean stopRabAI = true,
            stopWolfAI = true;
    private RabbAI RabbitMoving;
    private WolfAI WolfMoving;

    private void start()
    {
        Habitat.i = 0;
        Habitat.array_w.clear(); //очищаем список объектов класса
        Habitat.array_r.clear(); //очищаем список объектов класса
        Habitat.clear_field();
        Habitat.AmountOfWolfes = 0;   //количество менеджеров
        Habitat.AmountOfRabbits = 0; //количество разработчиков
        timer = new Timer();
        isTimerRunning = true;
        time = 0; // аннулируем время которое передаем в update()
        SimulationTask.timeSum.set(0);// аннулируем время которое отображается на экране
        RabbitMoving = new RabbAI();
        WolfMoving = new WolfAI();
        Thread WolfThread = new Thread(WolfMoving);
        Thread RabThread = new Thread(RabbitMoving);
        WolfThread.start();
        RabThread.start();
        BaseAI.move = true;
        
        /*synchronized (RabbitMoving.nos) 
            {
                if (!stopRabAI) {
                    stopRabAI = true;
                } else {
                    stopRabAI = false;
                    RabbitMoving.nos.notify();
                }
            }  */ 
    }
    private void stop()
    {
        BaseAI.move = false;
    }
    
    private void _continue()
    {
        timer = new java.util.Timer();
        isTimerRunning = true;
    }

    GUI()
    {
        super("Остров");
        final int DEFAULT_X = 1167;                          //размеры фрейма при запуске
        final int DEFAULT_Y = 1000;
        this.setSize(DEFAULT_X, DEFAULT_Y);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // завершение программы при закрытии фрейма
        this.setMinimumSize(new Dimension(1167,900));         //минимальный размер фрейма
        this.setIconImage(new ImageIcon("Island.jpg").getImage());
        this.setLocationRelativeTo(null); //фрейм в центре экрана
        this.setFocusable(true);          //фокус на фрейме при запуске
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

        // глобальный контейнер
        JPanel scene = new JPanel();
        add(scene);
        scene.setBackground(new Color(34, 139, 0));
        scene.setLayout(new BorderLayout());

        // панель управления симуляцией 
        JPanel control = new JPanel();
        Dimension controlSize = new Dimension(250, getHeight());//размер правого окна
        control.setPreferredSize(controlSize);
        control.setLayout(new FlowLayout(FlowLayout.CENTER));
        control.setBackground(new Color(100, 150, 150));
        scene.add(control, BorderLayout.EAST);

        // панель визуализации объектов
        int visualX = getWidth()-controlSize.width-17;
        int visualY = getHeight();
        VPanel visual = new VPanel(visualX, visualY);
        visual.setDoubleBuffered(true);
        visual.setBackground(new Color(34, 139, 0));
        scene.add(visual, BorderLayout.WEST);


        //статистика симуляции из левого окна
        JLabel statistics = new JLabel();
        statistics.setFont(new Font("Arial", Font.PLAIN, 25));
        statistics.setBackground(visual.getBackground());
        visual.add(statistics);

        // создание рабочей области
        Habitat habitat = new Habitat(visualX, visualY);
        
        
        //кнопка старт
        Dimension buttonSize = new Dimension(150,40);
        JButton startButton = new JButton("Старт");
        startButton.setPreferredSize(buttonSize);
        startButton.addActionListener(new ActionListener() 
        {
            SimulationTask updating = null;
                          
            @Override      
            public void actionPerformed(ActionEvent e)
            {
                if (!isTimerRunning)
                { 
                    if (isStatisticsVisible)
                    {
                        statistics.setVisible(false);
                        isStatisticsVisible = false; 
                    }
                    stopRabAI=false;
                    stopWolfAI=false;
                    start();
                    habitat.upd();
                    updating = new SimulationTask(habitat, time);
                    timer.schedule(updating, 0, 1000);
                    RepaintTask repainting = new RepaintTask(visual);
                    timer.schedule(repainting, 0, 20);
                }
                requestFocus();
            }
        }); 
        control.add(startButton);
     
        //кнопка стоп
        JButton stopButton = new JButton("Стоп");
        stopButton.setPreferredSize(buttonSize);
        stopButton.addActionListener(new ActionListener()
        {
            SimulationTask updating = null;
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (isTimerRunning) 
                {
                    timer.cancel();
                    isTimerRunning = false;
                }
                synchronized (RabbitMoving.nos)
                {stopRabAI=true;
                RabbitMoving.nos.notify();}
                synchronized (WolfMoving.nos)
                {stopWolfAI=true;
                WolfMoving.nos.notify();}
                repaint();
                requestFocus();
            }
        });
        control.add(stopButton);


        ////////////////////////////////////////////////////////////
        // панель периода рождения объектов
        JPanel periodPanel = new JPanel();
        periodPanel.setLayout(new GridLayout(3,1));
        periodPanel.setBackground(control.getBackground());
        control.add(periodPanel);

        JLabel periodHint = new JLabel("Количество животных:");
        periodPanel.add(periodHint);

        
        // панель для размещения текстовых полей
        JPanel periodEditPanel = new JPanel();
        periodEditPanel.setLayout(new BoxLayout(periodEditPanel, BoxLayout.X_AXIS));
        periodEditPanel.setBackground(control.getBackground());
        periodPanel.add(periodEditPanel);

        JPanel ManagerPeriodPanel = new JPanel(new GridLayout(2,1));
        ManagerPeriodPanel.setBackground(control.getBackground());
        periodEditPanel.add(ManagerPeriodPanel);

        JLabel ManagerHint = new JLabel("Кроликов:");
        ManagerPeriodPanel.add(ManagerHint);

        // поля ввода пользователем периода генерации
        JTextField PeriodOfManager = new JTextField(Integer.toString(Habitat.CoR));
        PeriodOfManager.addActionListener(e ->
        {
            try {
                Habitat.CoR = Integer.valueOf(PeriodOfManager.getText());
                System.out.println("Новое число для кроликов: " + Habitat.CoR);
                if (Habitat.CoR < 0)
                    throw new Exception();
            } 
            catch (Exception e1)
            {
                Habitat.CoR = 4;
                PeriodOfManager.setText(Integer.toString(habitat.CoR));
                JOptionPane.showMessageDialog(null,
                        "Введено неверное значение. Число не может быть ниже нуля.",
                        "Ошибка", JOptionPane.ERROR_MESSAGE); 
            }
        });
        ManagerPeriodPanel.add(PeriodOfManager);
  
       JPanel DeveloperPeriodPanel = new JPanel(new GridLayout(2,1));
        DeveloperPeriodPanel.setBackground(control.getBackground());
        periodEditPanel.add(DeveloperPeriodPanel);

        JLabel DevHint = new JLabel("Волков:");
        DeveloperPeriodPanel.add(DevHint);

        JTextField PeriodOfDeveloper = new JTextField(Integer.toString(Habitat.CoW));
        PeriodOfDeveloper.addActionListener(e -> 
        {
            try 
            {
                Habitat.CoW = Integer.valueOf(PeriodOfDeveloper.getText());
                System.out.println("Новое число для волков: " + Habitat.CoW);
                if (Habitat.CoW < 0)
                    throw new Exception();
            } 
            catch (Exception e1)
            {
                Habitat.CoW = 3;
                PeriodOfDeveloper.setText(Integer.toString(Habitat.CoW));
                JOptionPane.showMessageDialog(null,
                        "Введено неверное значение. Число не может быть ниже нуля.",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        DeveloperPeriodPanel.add(PeriodOfDeveloper);
        

        JMenuBar menuBar = new JMenuBar();
        {
            JMenu menuSimulation = new JMenu("Управление");
            menuBar.add(menuSimulation);

            JMenuItem startMenuItem = new JMenuItem("Старт");
            startMenuItem.addActionListener(new ActionListener()
            {
                SimulationTask updating = null;
                public void actionPerformed(ActionEvent e) 
                {
                    if (!isTimerRunning)
                    { // без условия таймер бы ускорялся
                        if (isStatisticsVisible)
                        {
                            statistics.setVisible(false);
                            isStatisticsVisible = false;
                        }
                        start();
                        updating = new SimulationTask(habitat, time);
                        timer.schedule(updating, 0, 1000);
                    }
                    requestFocus();
                }
            });
            menuSimulation.add(startMenuItem);

            JMenuItem stopMenuItem = new JMenuItem("Стоп");
            stopMenuItem.addActionListener(new ActionListener()
            {
                SimulationTask updating = null;
                
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (isTimerRunning) 
                    {
                        timer.cancel();
                        isTimerRunning = false;
                    }
                    repaint();
                    requestFocus();
                }
            });
            menuSimulation.add(stopMenuItem);



        }
        setJMenuBar(menuBar);

        this.setVisible(true);
    }
}