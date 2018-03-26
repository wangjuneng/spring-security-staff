package org.springframework.security.web.csrf;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TestFilterMap {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");
        
        String result = "";
        
        result = map.entrySet().stream().filter(item -> "four".equals(item.getValue()))
            .map(item-> item.getValue()).collect(Collectors.joining());
        
        System.out.println(result);
    }
}
