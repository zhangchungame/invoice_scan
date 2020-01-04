package com.dandinglong.task;

import com.dandinglong.entity.ScanImageByDayDetailEntity;

public class ExcelGenerateRunnable implements Runnable {
    private ScanImageByDayDetailEntity scanImageByDayDetailEntity;

    public ExcelGenerateRunnable(ScanImageByDayDetailEntity scanImageByDayDetailEntity) {
        this.scanImageByDayDetailEntity = scanImageByDayDetailEntity;
    }

    @Override
    public void run() {

    }
}
