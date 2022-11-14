package org.redtea.Threads;

import org.redtea.GUI.MainWindow;
import org.redtea.Genshin.miHoYoSoup;

import javax.swing.*;


public class WorkThread extends Thread{
    private JProgressBar jProgressBar;
    public void run(){
        int count = miHoYoSoup.getCount();
        int progress = 0;
        while (count < 100){
            if (Thread.currentThread().isInterrupted()){
                jProgressBar.setValue(0);
                break;
            }
            try {
                Thread.sleep(150);
            }catch (InterruptedException e){
                //e.printStackTrace();
                jProgressBar.setValue(0);
                break;
            }
            if (progress >= count){
                progress++;

            } else {
                progress = count;
            }
            int finalProgress = progress;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    jProgressBar.setValue(finalProgress);
                }
            });
            count = miHoYoSoup.getCount();
        }
    }
    public WorkThread(){}
    public WorkThread(JProgressBar jProgressBar){
        this.jProgressBar = jProgressBar;
    }
}
