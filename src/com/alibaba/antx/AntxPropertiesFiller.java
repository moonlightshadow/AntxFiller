package com.alibaba.antx;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1.先用AntxPropertiesCompare将待增加的配置项列表获得<br>
 * 2.找一个配置项较完整的应用的antx.properties文件（线上）作为配置仓库<br>
 * 3.用配置仓库的值填充第一步导出的配置项<br>
 * 
 * @author willy 2012-3-20 上午10:03:43
 */
public class AntxPropertiesFiller {

    // 待增加配置
    private final static String antxToFill     = "/home/willy/Desktop/transfer.more";
    // 配置仓库，配置全集
    private final static String antxRepository = "/home/willy/Desktop/antx.properties.automation.hz.ml";

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(antxRepository));
            Map<String, String> afterMap = getKV(br);
            //
            BufferedReader br2 = new BufferedReader(new FileReader(antxToFill));
            List<String> keyList = getKeys(br2);
            //
            int count = 0;
            for (String key : keyList) {
                count++;
                if (null != afterMap.get(key)) {
                    System.out.println(key + " = " + afterMap.get(key));
                } else {
                    System.out.println(key + "=");
                }
            }
            System.out.println(count);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }

    }

    private static List<String> getKeys(BufferedReader br) throws IOException {
        List<String> keyList = new ArrayList<String>();
        if (null == br) return keyList;
        String line;
        while ((line = br.readLine()) != null) {
            keyList.add(line.trim());
        }
        return keyList;
    }

    private static Map<String, String> getKV(BufferedReader br) throws IOException {
        Map<String, String> kvMap = new HashMap<String, String>();

        if (null == br) {
            return kvMap;
        }
        String line;

        while ((line = br.readLine()) != null) {
            if (null == line || "".equals(line)) {
                continue;
            }
            String key = line.split("=")[0].trim();
            String value = line.split("=")[1].trim();
            kvMap.put(key, value);
        }
        return kvMap;
    }
}
