package com.ssm.dao;

import com.github.pagehelper.Page;
import com.ssm.entity.Department;
import com.ssm.entity.Group;
import com.ssm.entity.Role;
import com.ssm.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserMapper {

	/**
	 * 根据用户名称查询用户基本信息
	 * 
	 * @param username
	 * @return
	 */
	public User getUserByName(String username);

	/**
	 * 添加user并返回该记录的主键
	 * 
	 * @param user
	 * @return
	 */
	public int insertUser(User user);

	/**
	 * 更新user基本信息(包括更新password)
	 * 
	 * @param user
	 */
	public void updateUser(@Param("user") User user, @Param("oldpassword") String oldpassword);

	
	/**
	 * 给用户授权一种角色
	 * 
	 * 
	 * @param userId
	 *            roleId
	 */
	public void grantUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

	/**
	 * 获取
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<Group> getGroupListByUserId(@Param("userId") int userId, @Param("page") Page<Group> page);

	
	/**
	 * 为学员分配一个用户组
	 * 
	 * @param userId
	 * @param groupId
	 */
	public void addUserGroup(@Param("userId") int userId, @Param("groupId") int groupId);

	/**
	 * 更新分组
	 * 
	 * @param groupId
	 * @param groupName
	 */
	public void updateGroup(@Param("groupId") int groupId, @Param("groupName") String groupName);

	/**
	 * 获取所有的角色
	 * 
	 * @return
	 */
	public List<Role> getRoleList();

	/**
	 * 更新用户状态
	 * 
	 * @param idList
	 * @param enabled
	 */
	public void changeUserStatus(@Param("array") List<Integer> idList, @Param("enabled") boolean enabled);

		
	/**
	 * 删除分组
	 * @param userId
	 * @param groupId
	 * @param managerId 只能删除自己管理的分组中的数据
	 */
	public void deleteUserGroup(@Param("userId") int userId, @Param("groupId") int groupId, @Param("managerId") int managerId);

	/**
	 * 获取所有部门信息
	 * @return
	 */
	public List<Department> getDepList();

	/**
	 * 为用户添加一个部门
	 * @param userId
	 * @param depId
	 */
	public void addUser2Dep(@Param("userId") int userId, @Param("depId") int depId);
	
	/**
	 * 删除用户的部门信息
	 * @param userId
	 */
	public void deleteUser2Dep(int userId);

	User getUserByNameAndPassword(User user);
	
}
