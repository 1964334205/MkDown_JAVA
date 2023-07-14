package top.openfbi.mdnote.utils;

import java.util.List;

public class StringUtil {
    public static String jointString(List<String> stringList,String separator){
        // 拼接list为String，添加分隔符
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringList.size(); i++) {
            if(i != 0 ){
                stringBuilder.append(separator);
            }
            stringBuilder.append(stringList.get(i));
        }
        return stringBuilder.toString();
    }

    public static String cutString(String s,int cutLength){
        // 获取前cutLength个字符
        int contentLength = s.length();
        if (contentLength < cutLength) {
            cutLength = contentLength;
        }
        return s.substring(0, cutLength);
    }
}
