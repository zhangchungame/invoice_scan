package com.dandinglong.model.excel;

import java.util.ArrayList;
import java.util.List;

public class ExcellRowMeaningConfigInvoice {
    private static volatile List<ExcellCellMeaning> excellCellMeanings=new ArrayList<>();
    private static volatile List<ExcellCellMeaning> excellCellMeaningsDetail=new ArrayList<>();

    private static void init(){
        excellCellMeanings.add(new ExcellCellMeaning(0,"InvoiceType","发票种类"));
        excellCellMeanings.add(new ExcellCellMeaning(1,"InvoiceTypeOrg","发票名称"));
        excellCellMeanings.add(new ExcellCellMeaning(2,"InvoiceCode","发票代码"));
        excellCellMeanings.add(new ExcellCellMeaning(3,"InvoiceNum","发票号码"));
        excellCellMeanings.add(new ExcellCellMeaning(4,"CheckCode","校验码"));
        excellCellMeanings.add(new ExcellCellMeaning(5,"InvoiceDate","开票日期"));
        excellCellMeanings.add(new ExcellCellMeaning(6,"PurchaserName","购方名称"));
        excellCellMeanings.add(new ExcellCellMeaning(7,"PurchaserRegisterNum","购方纳税人识别号"));
        excellCellMeanings.add(new ExcellCellMeaning(8,"PurchaserAddress","购方地址及电话"));
        excellCellMeanings.add(new ExcellCellMeaning(9,"PurchaserBank","购方开户行及账号"));
        excellCellMeanings.add(new ExcellCellMeaning(10,"Password","密码区"));
        excellCellMeanings.add(new ExcellCellMeaning(11,"SellerName","销售方名称"));
        excellCellMeanings.add(new ExcellCellMeaning(12,"SellerRegisterNum","销售方纳税人识别号"));
        excellCellMeanings.add(new ExcellCellMeaning(13,"SellerAddress","销售方地址及电话"));
        excellCellMeanings.add(new ExcellCellMeaning(14,"SellerBank","销售方开户行及账号"));
        excellCellMeanings.add(new ExcellCellMeaning(15,"TotalAmount","合计金额"));
        excellCellMeanings.add(new ExcellCellMeaning(16,"TotalTax","合计税额"));
        excellCellMeanings.add(new ExcellCellMeaning(17,"AmountInWords","价税合计(大写)"));
        excellCellMeanings.add(new ExcellCellMeaning(18,"AmountInFiguers","价税合计(小写)"));
        excellCellMeanings.add(new ExcellCellMeaning(19,"Payee","收款人"));
        excellCellMeanings.add(new ExcellCellMeaning(20,"Checker","复核"));
        excellCellMeanings.add(new ExcellCellMeaning(21,"NoteDrawer","开票人"));
        excellCellMeanings.add(new ExcellCellMeaning(22,"Remarks","备注"));

        excellCellMeaningsDetail.add(new ExcellCellMeaning(23,"CommodityName","货物名称"));
        excellCellMeaningsDetail.add(new ExcellCellMeaning(24,"CommodityType","规格型号"));
        excellCellMeaningsDetail.add(new ExcellCellMeaning(25,"CommodityUnit","单位"));
        excellCellMeaningsDetail.add(new ExcellCellMeaning(26,"CommodityNum","数量"));
        excellCellMeaningsDetail.add(new ExcellCellMeaning(27,"CommodityPrice","单价"));
        excellCellMeaningsDetail.add(new ExcellCellMeaning(28,"CommodityAmount","金额"));
        excellCellMeaningsDetail.add(new ExcellCellMeaning(29,"CommodityTaxRate","税率"));
        excellCellMeaningsDetail.add(new ExcellCellMeaning(30,"CommodityTax","税额"));
    }
    public static List<ExcellCellMeaning> getExcellCellMeanings() {
        if(excellCellMeanings.size()==0){
            init();
        }
        return excellCellMeanings;
    }
    public static List<ExcellCellMeaning> getExcellCellMeaningsDetail(){
        if(excellCellMeaningsDetail.size()==0){
            init();
        }
        return excellCellMeaningsDetail;

    }

}
