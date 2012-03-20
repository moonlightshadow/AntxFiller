package com.alibaba.antx;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 1.先将线上的配置cp到本地并复制两份为antx.properties和antx.properties.bak<br>
 * 2.用antx.properties在应用中autoconfig，填入任意值,这个时候antx.properties已经有了新的配置项<br>
 * 3.比较antx.properties和antx.properties.bak就能获得需新增的配置项的key列表了
 * 
 * @author willy 2012-3-20 上午9:58:12
 */
public class AntxPropertiesCompare {

    // 老的配置
    private final static String oldProperties = "/home/willy/Desktop/transfer.less";
    // 新autoconfig后产生的配置
    private final static String newProperties = "/home/willy/work/intl-standalone/srcrosstool/transfer/antx.properties.transfer.hz";

    /**
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(newProperties));
            Map<String, String> afterMap = getKV(br);
            //
            BufferedReader br2 = new BufferedReader(new FileReader(oldProperties));
            Map<String, String> beforeMap = getKV(br2);
            //
            compareDifference(afterMap, beforeMap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    private static void compareDifference(Map<String, String> afterMap, Map<String, String> beforeMap) {
        System.out.println(afterMap.size() + "============" + beforeMap.size());
        int count = 0;
        for (Map.Entry<String, String> entity : afterMap.entrySet()) {
            if (null == beforeMap.get(entity.getKey().trim())) {
                System.out.println(entity.getKey().trim());
                count++;
            }
        }
        System.out.println(count);
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
