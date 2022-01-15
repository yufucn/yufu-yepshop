package com.yufu.yepshop.persistence.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/9 23:22
 */
public class MapStruct {
    public static String[] strToList(String hand) {
        return hand.split(",");
    }

    public static String listToStr(String[] hands) {
        return String.join(",", hands);
    }
}
