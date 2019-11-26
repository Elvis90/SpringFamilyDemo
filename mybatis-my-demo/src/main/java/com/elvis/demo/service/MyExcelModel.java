package com.elvis.demo.service;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elvis
 * @create 2019-11-26 14:52
 */
@Data
public class MyExcelModel implements BaseExcelParseModel {
    private String prove;
    private String city;
    private String town;
    private String orderType;
    private String orderNumber;
    private String creatDate;
    private String netId;
    private String netName;
    private String netType;
    private String probomclassify;
    private String endDate;
    private String price;
    @Override
    public List<String> listColumnName() {
        ArrayList<String> list = new ArrayList<>();
        list.add("prove");
        list.add("city");
        list.add("town");
        list.add("orderType");
        list.add("orderNumber");
        list.add("creatDate");
        list.add("netId");
        list.add("netName");
        list.add("netType");
        list.add("probomclassify");
        list.add("endDate");
        list.add("price");
        return list;
    }

    @Override
    public Integer getSheetNumber() {
        return 0;
    }

    @Override
    public Integer getBeginRowNumber() {
        return 4;
    }


}
