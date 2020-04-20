package com.ssm.service;

import com.github.pagehelper.Page;
import com.ssm.entity.Department;
import com.ssm.entity.Group;
import com.ssm.entity.Role;
import com.ssm.entity.User;
import java.util.HashMap;
import java.util.List;

/**
 * @author Ocelot
 * @date 2014年6月8日 下午5:52:55
 */
public interface UserService {

	/**
	 * 添加一个用户，并授权。
	 * 
	 * @param user
	 * @param authority
	 * @param groupId
	 * @param roleMap
	 * @return
	 */
	public int addUser(User user, String authority, int groupId, HashMap<String, Role> roleMap);


	/**
	 * 更新用户信息
	 * @param user
	 * @param oldPassword
	 */
	public void updateUser(User user, String oldPassword);
	
	/**
	 * 修改用户密码
	 * @param user
	 */
	public void updateUserPwd(User user, String oldPwd);

	/**
	 * 获取
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<Group> getGroupListByUserId(int userId, Page<Group> page);

	
	/**
	 * 获取所有的角色并生成字典
	 * 
	 * @return
	 */
	public HashMap<String, Role> getRoleMap();

	/**
	 * 更新用户状态
	 * 
	 * @param idList
	 * @param enabled
	 */
	public void changeUserStatus(List<Integer> idList, boolean enabled);

	
	/**
	 * 添加用户到分组
	 * 
	 * @param userId
	 * @param groupId
	 */
	public void addUserGroup(int userId, int groupId);

	/**
	 * 删除分组
	 * @param userId
	 * @param groupId
	 * @param managerId 只能删除自己管理的分组中的数据
	 */
	public void deleteUserGroup(int userId, int groupId, int managerId);
	
	/**
	 * 获取所有部门信息
	 * @return
	 */
	public List<Department> getDepList();
	
	/**
	 * 获取用户
	 * @param userName
	 * @return
	 */
	public User getUserByName(String userName);

	public User getUserByNameAndPassword(User user);


}
