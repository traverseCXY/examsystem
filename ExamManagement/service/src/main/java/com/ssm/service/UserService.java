package com.ssm.service;

import com.ssm.entity.Group;
import com.ssm.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface UserService {

    User  getUserByUserNameAndPassword(User user);

    List<User>  getUserListByRoleId(int userId);

    /**
     * 获取
     *
     * @param userId
     * @return
     */
    public List<Group> getGroupListByUserId(int userId);

    int getUserCountByStudent();

    /**
     * 添加一个用户
     *
     * @param user
     * @return
     */
    public void addUser(User user);


    /**
     * 重置用户密码
     * @param userName
     * @param password
     */
    public void updateUserPwd(String userName,String password) throws Exception;

    /**
     * 更新user基本信息(包括更新password,fullname)
     *
     * @param user
     */
    public void updateUser(@Param("user") User user);
}
