package qibo.map;

import util.ExcelUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangjie-mac
 */

public class Main {
    private final static String file_lm_words = "/Users/yangjie/数据分析/祁博/地图/global.xlsx";

    public static void main(String[] args) {
        getList(0);

    }

    public static void getList(int sheetIndex) {
        List<List<String>> dataList = ExcelUtils.readFromXLSX(file_lm_words, sheetIndex);
        for (List<String> strList : dataList) {
            if (strList != null && strList.size() > 0) {
                System.out.println(strList);
            }
        }
    }

}
