package com.freemarker.run;

import com.jryz.freemarker.FreemarkerUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreemarkerTest {

    @Test
    public void test(){
        Map<String, Object> map = new HashMap<>();
 
        Group group = new Group();
        group.setName("IT");
         
        User user = new User();
        user.setId(001);
        user.setName("张三6666");
        user.setAge(12);
        user.setGroup(group);
         
        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user);
        users.add(user);
         
        map.put("user", user);
        //普通EL赋值
        //util.print("01.ftl", map );
        //判断
        //util.print("03.ftl", map, "03.html");
        //遍历
        //util.print("05.ftl", map);
        //子元素判断
        FreemarkerUtil.print("6.ftl", map);
        FreemarkerUtil.print("6.ftl", map, "D:\\many\\test.html");
    }
}