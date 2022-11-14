package org.redtea.Genshin;

import org.redtea.Utils.progressCount;

public class miHoYoCounts implements progressCount {
    @Override
    public void setCount(int count) {
        miHoYoSoup.setCount(count);
    }

    @Override
    public int getCount() {
        return miHoYoSoup.getCount();
    }

    @Override
    public int getCountEnd(){
        return miHoYoSoup.getCountEnd();
    }

    @Override
    public void setCountEnd(int countEnd){
        miHoYoSoup.setCountEnd(countEnd);
    }
}
