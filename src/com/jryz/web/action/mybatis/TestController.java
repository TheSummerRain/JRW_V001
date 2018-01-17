package com.jryz.web.action.mybatis;

import com.jryz.core.free.Pagination;
import com.jryz.model.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tests.mybatis.bean.Order;
import tests.mybatis.bean.User;
import tests.mybatis.maper.UserMaper;

import java.util.List;

@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    private UserMaper userMaper;

    @RequestMapping("test")
    @ResponseBody
    public ApiResult test(Pagination pagination){
        // 测试id=1的用户查询，可根据数据库中的情况修改.
        User user = userMaper.getUserById(1);
        System.out.println("获取用户 ID=1 的用户名："+user.getUsername());

        // 得到文章列表测试
        System.out.println("得到用户id为1的所有订单列表:");
        System.out.println("=============================================");
        List<Order> orders = userMaper.getUserOrders(1);

        for (Order order : orders) {
            System.out.println("订单号："+order.getOrderNo() + "，订单金额：" + order.getMoney() + " " + order.getUser().getUsername());
        }

        ApiResult re = new ApiResult();
        userMaper.getUserOrdersByPage(pagination);
        re.setData(pagination);
        return re;
    }
}
