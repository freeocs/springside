#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.service.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.entity.security.Role;
import ${package}.entity.security.User;
import ${package}.service.security.UserManager;
import ${package}.unit.service.security.UserData;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * UserManager的集成测试用例.
 * 
 * 调用实际的DAO类进行操作,默认在操作后进行回滚.
 * 
 * @author calvin
 */
public class UserManagerTest extends SpringTxTestCase {

	@Autowired
	private UserManager userManager;

	@Test
	//如果需要真正插入数据库,将Rollback设为false
	//@Rollback(false) 
	public void crudUser() {
		//保存用户并验证.
		User entity = UserData.getRandomUser();
		userManager.saveUser(entity);
		//强制执行sql语句
		flush();
		assertNotNull(entity.getId());

		//删除用户并验证
		userManager.deleteUser(entity.getId());
		//强制执行sql语句
		flush();
	}

	@Test
	public void crudUserAndRole() {
		//保存带角色的用户并验证
		User entity = UserData.getRandomUser();
		Role role = UserData.getRandomRole();
		entity.getRoles().add(role);
		
		userManager.saveRole(role);
		userManager.saveUser(entity);
		flush();
		
		entity = userManager.getUser(entity.getId());
		assertEquals(1, entity.getRoles().size());

		//删除用户的角色并验证
		entity.getRoles().remove(role);
		flush();
		entity = userManager.getUser(entity.getId());
		assertEquals(0, entity.getRoles().size());
	}

	//期望抛出ConstraintViolationException的异常.
	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void savenUserNotUnique() {
		User entity = UserData.getRandomUser();
		entity.setLoginName("admin");
		userManager.saveUser(entity);
		flush();
	}
}