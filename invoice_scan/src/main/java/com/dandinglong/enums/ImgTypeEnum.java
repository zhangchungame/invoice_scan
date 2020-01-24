package com.dandinglong.enums;

import com.dandinglong.exception.WxException;
import com.dandinglong.model.ocrmodel.InvoiceOcrModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ImgTypeEnum {
    INVOICE("增值税发票","invoice",10, InvoiceOcrModel.class,"invoiceResultDealService","invoiceExcelDealService");

//    CAR_Ticket("车票","car_ticket",8);
    private String name;
    private String type;
    private int consumScore;
    private Class ocrModelClass;
    private String serviceName;
    private String excelServiceNmae;

    ImgTypeEnum(String name, String type, int consumScore, Class ocrModelClass, String serviceName, String excelServiceNmae) {
        this.name = name;
        this.type = type;
        this.consumScore = consumScore;
        this.ocrModelClass = ocrModelClass;
        this.serviceName = serviceName;
        this.excelServiceNmae = excelServiceNmae;
    }

    public String getExcelServiceNmae() {
        return excelServiceNmae;
    }

    public void setExcelServiceNmae(String excelServiceNmae) {
        this.excelServiceNmae = excelServiceNmae;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Class getOcrModelClass() {
        return ocrModelClass;
    }

    public void setOcrModelClass(Class ocrModelClass) {
        this.ocrModelClass = ocrModelClass;
    }

    public static boolean checkType(String type){
        ImgTypeEnum[] values = values();
        for(int i=0;i<values.length;i++){
            if(values[i].type.equals(type)){
                return true;
            }
        }
        throw new WxException("选择的图片类型错误");
    }
    public static ImgTypeEnum getEnumByType(String type){
        ImgTypeEnum[] values = values();
        for(int i=0;i<values.length;i++){
            if(values[i].type.equals(type)){
                return values[i];
            }
        }
        throw new WxException("选择的图片类型错误");
    }
    /**
     * 获取所有枚举返回list
     * @return
     */
    public static List<Map<String,String>> valueList(){
        ImgTypeEnum[] values = values();
        List<Map<String,String>> list=new ArrayList<>();
        for(int i=0;i<values.length;i++){
            Map<String,String> map=new HashMap<>();
            map.put("name",values[i].name);
            map.put("value",values[i].type);
            list.add(map);
        }
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getConsumScore() {
        return consumScore;
    }

    public void setConsumScore(int consumScore) {
        this.consumScore = consumScore;
    }
}
