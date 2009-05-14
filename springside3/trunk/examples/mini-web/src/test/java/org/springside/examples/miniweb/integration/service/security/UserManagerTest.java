package org.springside.examples.miniweb.integration.service.security;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;
import org.springside.examples.miniweb.service.security.UserManager;
import org.springside.modules.test.junit4.SpringTransactionalTestCase;

/**
 * UserManager的集成测试用例.
 * 
 * 调用实际的DAO类进行操作,默认在操作后进行回滚.
 * 
 * @author calvin
 */
public class UserManagerTest extends SpringTransactionalTestCase {

	@Autowired
	private UserManager	userManager;

	@Test
	public void crudUser() {
		//保存用户并验证.
		User entity = new User();
		// 因为LoginName要求唯一性，因此添加random字段。
		entity.setLoginName("tester" + randomString());
		entity.setName("foo");
		entity.setEmail("foo@bar.com");
		entity.setPassword("foo");
		userManager.save(entity);
		flush();
		assertNotNull(entity.getId());

		//删除用户并验证
		userManager.delete(entity.getId());
		flush();
	}

	@Test
	public void crudUserAndRole() {
		//保存带角色的用户并验证
		User entity = new User();
		entity.setLoginName("tester" + randomString());

		Role role = new Role();
		role.setId(1L);
		entity.getRoles().add(role);
		userManager.save(entity);
		flush();
		entity = userManager.get(entity.getId());
		assertEquals(1, entity.getRoles().size());

		//删除用户的角色并验证
		entity.getRoles().remove(role);
		flush();
		entity = userManager.get(entity.getId());
		assertEquals(0, entity.getRoles().size());
	}

	//期望抛出ConstraintViolationException的异常.
	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void savenUserNotUnique() {
		User entity = new User();
		entity.setLoginName("admin");
		userManager.save(entity);
		flush();
	}
}