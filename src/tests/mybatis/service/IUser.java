package tests.mybatis.service;

import tests.mybatis.bean.User;

import java.util.List;

/**
 * 
 * @author yiibai
 *
 */
public interface IUser {

    //@Select("select * from user where id= #{id}")
    //public User getUserByID(int id);
    public List<User> getUserList();

    public void insertUser(User user);

    public void updateUser(User user);

    public void deleteUser(int userId);

    public User getUser(int id);
}