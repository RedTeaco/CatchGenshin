package org.redtea.resourceUpdate;

import org.redtea.Utils.progressCount;

public class resourcesCounts implements progressCount {

    @Override
    public void setCount(int count) {
        Main.setCount(count);
    }

    @Override
    public int getCount() {
        return Main.getCount();
    }

    @Override
    public int getCountEnd() {
        return Main.getCountEnd();
    }

    @Override
    public void setCountEnd(int countEnd) {
        Main.setCountEnd(countEnd);
    }
}
