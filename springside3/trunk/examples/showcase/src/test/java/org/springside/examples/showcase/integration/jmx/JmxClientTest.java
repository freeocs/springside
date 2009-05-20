package org.springside.examples.showcase.integration.jmx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.jmx.server.ServerConfigMBean;
import org.springside.modules.jmx.JmxClient;
import org.springside.modules.test.junit4.SpringContextTestCase;

/**
 * sprinside-jee中ClientMBeanProxyFactory的测试用例.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/jmx/applicationContext-jmx-server.xml" })
public class JmxClientTest extends SpringContextTestCase {

	private JmxClient jmxClient;
	private ServerConfigMBean serverConfigMbean;

	@Before
	public void setUp() throws Exception {
		System.out.println("aa");
		jmxClient = new JmxClient("service:jmx:rmi:///jndi/rmi://localhost:1099/showcase");
		serverConfigMbean = jmxClient.getMBeanProxy("org.springside.showcase:type=Configurator",
				ServerConfigMBean.class);
	}

	@After
	public void tearDown() throws Exception {
		jmxClient.close();
	}

	@Test
	public void getMBeanAttribute() {
		assertEquals("node1", serverConfigMbean.getNodeName());
	}

	@Test
	public void setMBeanAttribute() {
		serverConfigMbean.setNodeName("foo");
		assertEquals("foo", serverConfigMbean.getNodeName());
	}

	@Test
	public void getMBeanAttributeByReflection() {
		assertEquals(0L, jmxClient.getAttribute("org.hibernate:type=Statistics", "SessionOpenCount"));
	}

	@Test
	public void invokeMBeanMethodByReflection() {
		jmxClient.inoke("org.hibernate:type=Statistics", "logSummary");
	}
}