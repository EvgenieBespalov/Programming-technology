package com.company;

import java.util.TimerTask;

public class RepaintTask extends TimerTask {
    
    private VPanel visual;

    RepaintTask(VPanel visual) {
        this.visual = visual;
    }

    @Override
    public void run() {
        visual.repaint();
    }
}
