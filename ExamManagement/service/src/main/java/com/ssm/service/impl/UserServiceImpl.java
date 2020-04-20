package com.ssm.service.impl;

import com.ssm.dao.UserMapper;
import com.ssm.entity.Group;
import com.ssm.entity.StandardPasswordEncoderForSha1;
import com.ssm.entity.User;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserNameAndPassword(User user) {
        return userMapper.getUserByUserNameAndPassword(user);
    }

    @Override
    public List<User> getUserListByRoleId(int userId) {
        return userMapper.getUserListByRoleId(userId);
    }

    @Override
    public List<Group> getGroupListByUserId(int userId) {
        // TODO Auto-generated method stub
        return userMapper.getGroupListByUserId(userId);
    }

    @Override
    public int getUserCountByStudent() {
        return userMapper.getUserCountByStudent();
    }

    @Override
    public void addUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public void updateUserPwd(String userName, String password) throws Exception {
        PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
        password = passwordEncoder.encode(password + "{" + userName.toLowerCase() + "}");
        userMapper.updateUserPwd(userName,password);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}
