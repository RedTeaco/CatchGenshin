package org.redtea.Threads;

import org.redtea.GUI.MainWindow;
import org.redtea.Genshin.miHoYoSoup;
import org.redtea.Utils.progressCount;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.function.Function;


public class WorkThread extends Thread{
    private JProgressBar jProgressBar;
    private progressCount ps;

    private int sleepTime;
    public void run(){
        ps.setCount(0);
        ps.setCountEnd(10);
        //int count = miHoYoSoup.getCount();
        int count = ps.getCount();
        int countEnd = ps.getCountEnd();
        int progress = 0;
        jProgressBar.setValue(0);
        while (count < 100){
/*            if (Thread.currentThread().isInterrupted()){
                jProgressBar.setValue(0);
                break;
            }*/
            try {
                Thread.sleep(sleepTime);
            }catch (InterruptedException e){
                //e.printStackTrace();
                jProgressBar.setValue(jProgressBar.getMaximum());
                break;
            }
            if (progress >= count && progress < countEnd){
                progress++;

            } else if (progress < count){
                progress = count;
            } else if (progress >= countEnd){
                progress = countEnd;
            }
            int finalProgress = progress;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    jProgressBar.setValue(finalProgress);
                }
            });
            count = ps.getCount();
            countEnd = ps.getCountEnd();
        }
    }
    public WorkThread(){}
    public WorkThread(JProgressBar jProgressBar,int sleepTime,progressCount ps){
        this.ps = ps;
        this.sleepTime = sleepTime;
        this.jProgressBar = jProgressBar;

    }
}
