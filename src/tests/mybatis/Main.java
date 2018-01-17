package tests.mybatis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tests.mybatis.bean.Order;
import tests.mybatis.bean.User;
import tests.mybatis.maper.UserMaper;

import java.util.List;


/**
 * Description
 * @author yiibai
 * @date 2015-4-12
 * @copyright http://www.yiibai.com
 * @email yiibai.com@gmai.com
 * @version 1.0
 */

public class Main {

	private static ApplicationContext ctx;

	static {
        ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
	}

	public static void main(String[] args) {
		UserMaper userMaper = (UserMaper) ctx.getBean("userMaper");
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

	}

}