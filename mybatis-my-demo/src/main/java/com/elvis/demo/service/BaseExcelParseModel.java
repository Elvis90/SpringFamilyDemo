package com.elvis.demo.service;

import java.util.List;

/**
 * @author Elvis
 * @create 2019-11-26 14:31
 */
public interface BaseExcelParseModel {
    List<String> listColumnName();

    Integer getSheetNumber();

    Integer getBeginRowNumber();

}
