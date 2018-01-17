package tests.mybatis.maper;

import com.jryz.core.free.Pagination;
import tests.mybatis.bean.Order;
import tests.mybatis.bean.User;

import java.util.List;

public interface UserMaper {

    User getUserById(int id);

    List<Order> getUserOrders(int id);

    List<Order> getUserOrdersByPage(Pagination pagination);
}
