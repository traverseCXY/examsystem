package com.ssm.service.impl;


import com.github.pagehelper.Page;
import com.ssm.dao.UserMapper;
import com.ssm.entity.Department;
import com.ssm.entity.Group;
import com.ssm.entity.Role;
import com.ssm.entity.User;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserMapper userMapper;

	@Override
	@Transactional
	public int addUser(User user, String authority, int groupId, HashMap<String, Role> roleMap) {
		try {
			int userId = -1;
			userMapper.insertUser(user);
			userId = user.getUserId();
			userMapper.grantUserRole(userId, roleMap.get(authority).getRoleId());
			if(user.getDepId() != 0)
				userMapper.addUser2Dep(userId, user.getDepId());
			return userId;
		} catch (Exception e) {
			String cause = e.getCause().getMessage();
			throw new RuntimeException(cause);
		}
	}
	
	
	@Override
	@Transactional
	public void updateUser(User user, String oldPassword) {
		// TODO Auto-generated method stub
		try {
			userMapper.updateUser(user, oldPassword);
			userMapper.deleteUser2Dep(user.getUserId());
			userMapper.addUser2Dep(user.getUserId(), 1);
		} catch (Exception e) {
			String cause = e.getCause().getMessage();
			throw new RuntimeException(cause);
		}
	}

	@Override
	public List<Group> getGroupListByUserId(int userId, Page<Group> page) {
		// TODO Auto-generated method stub
		return userMapper.getGroupListByUserId(userId, page);
	}

	@Override
	public HashMap<String, Role> getRoleMap() {
		// TODO Auto-generated method stub
		List<Role> roleList = userMapper.getRoleList();
		HashMap<String,Role> map = new HashMap<String,Role>();
		for(Role r : roleList){
			map.put(r.getAuthority(), r);
		}
		return map;
	}

	@Override
	public void changeUserStatus(List<Integer> idList,boolean enabled) {
		// TODO Auto-generated method stub
		userMapper.changeUserStatus(idList, enabled);
	}
	
	@Override
	public void addUserGroup(int userId, int groupId) {
		// TODO Auto-generated method stub
		userMapper.addUserGroup(userId, groupId);
	}


	@Override
	public void deleteUserGroup(int userId, int groupId, int managerId) {
		// TODO Auto-generated method stub
		userMapper.deleteUserGroup(userId, groupId, managerId);
	}


	@Override
	public List<Department> getDepList() {
		// TODO Auto-generated method stub
		return userMapper.getDepList();
	}


	@Override
	public User getUserByName(String userName) {
		// TODO Auto-generated method stub
		return userMapper.getUserByName(userName);
	}

	@Override
	public User getUserByNameAndPassword(User user) {
		return userMapper.getUserByNameAndPassword(user);
	}


	@Override
	public void updateUserPwd(User user, String oldPwd) {
		// TODO Auto-generated method stub
		userMapper.updateUser(user, oldPwd);
	}
}
