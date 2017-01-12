package com.ducetech.util;

import java.util.Calendar;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Kerby on 2015/2/10.
 */
public class IDGenerator {
    private static Hashtable<String, Integer> count = new Hashtable<>();
    private static Hashtable<String, Integer> billCount = new Hashtable<>();

    public static String generateFlowID(String dpmtNo, String lastId) {
        if (Integer.valueOf(dpmtNo) < 10) {
            dpmtNo = "0" + dpmtNo;
        }
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String yearStr = String.valueOf(year).substring(2);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
        String monthStr = String.valueOf(month);
        String dayStr = String.valueOf(day);
        if (month < 10) {
            monthStr = "0" + String.valueOf(month);
        }

        if (day < 10) {
            dayStr = "0" + String.valueOf(day);
        }

        String index = yearStr + monthStr + dayStr;
        int seq = getSeq(index);
        String seqStr = String.valueOf(seq);
        if (lastId.contains(index)) {
            seq = Integer.valueOf(lastId.substring(index.length() + 2)) + 1;
        }
        if (seq < 10) {
            seqStr = "000" + String.valueOf(seq);
        } else if (seq < 100) {
            seqStr = "00" + String.valueOf(seq);
        } else if (seq < 1000) {
            seqStr = "0" + String.valueOf(seq);
        }

        return dpmtNo + yearStr + monthStr + dayStr + seqStr;
    }

    public static int getSeq(String key) {
        if (count.containsKey(key)) {
            int no = count.get(key);
            count.put(key, ++no);
        } else {
            count.put(key, 1);
        }
        return count.get(key);
    }

    public static String generateBillID(String key) {
        if (billCount.containsKey(key)) {
            int no = billCount.get(key);
            billCount.put(key, ++no);
        } else {
            billCount.put(key, 1);
        }
        String seq = "";
        int seqValue = billCount.get(key);
        if (seqValue < 10) {
            seq = "0" + String.valueOf(seqValue);
        } else {
            seq = String.valueOf(seqValue);
        }
        return key + seq;

    }
    public static String generateApplicationID(String flowId, String lastFlowId) {
        String str = generateFlowID(StringUtils.substring(flowId, 1, 2), StringUtils.substring(lastFlowId, 2));
        return "SQ" + str;
    }

}
