package com.ssm.dao;


import com.ssm.entity.Group;
import com.ssm.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户dao层
 */
public interface UserMapper {

    User getUserByUserNameAndPassword(User user);

    /**
     * 获取
     *
     * @param userId
     * @return
     */
    public List<Group> getGroupListByUserId(@Param("userId") int userId);

    int getUserCountByStudent();

    public List<User> getUserListByRoleId(int userId);

    void insertUser(User user);

    void updateUserPwd(String userName,String password);

    /**
     * 更新user基本信息
     *
     * @param user
     */
    public void updateUser(@Param("user") User user);

}
