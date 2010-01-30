package org.springside.examples.miniweb.unit.dao.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniweb.dao.account.RoleDao;
import org.springside.examples.miniweb.dao.account.UserDao;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;
import org.springside.examples.miniweb.unit.dao.BaseTxTestCase;
import org.springside.modules.test.utils.DataUtils;

/**
 * RoleDao的测试用例, 测试ORM映射及特殊的DAO操作.
 * 
 * @author calvin
 */
public class RoleDaoTest extends BaseTxTestCase {
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserDao userDao;

	/**
	 * 测试删除角色时删除用户-角色的中间表.
	 */
	@Test
	public void deleteRole() {
		//新增测试角色并与admin用户绑定.
		Role role = new Role();
		role.setName(DataUtils.randomName("Role"));
		roleDao.save(role);

		User user = userDao.get(1L);
		user.getRoleList().add(role);
		userDao.save(user);
		flush();

		int oldJoinTableCount = countRowsInTable("SS_USER_ROLE");
		int oldUserTableCount = countRowsInTable("SS_USER");

		//删除用户角色, 中间表将减少1条记录,而用户表应该不受影响.
		roleDao.delete(role.getId());
		flush();

		int newJoinTableCount = countRowsInTable("SS_USER_ROLE");
		int newUserTableCount = countRowsInTable("SS_USER");
		assertEquals(1, oldJoinTableCount - newJoinTableCount);
		assertEquals(0, oldUserTableCount - newUserTableCount);
	}
}
