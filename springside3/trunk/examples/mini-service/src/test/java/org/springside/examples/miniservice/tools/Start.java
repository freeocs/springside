package org.springside.examples.miniservice.tools;

import org.mortbay.jetty.Server;
import org.springside.modules.test.utils.JettyUtils;

/**
 * 使用Jetty运行调试Web应用,在Console输入回车停止服务.
 * 
 * @author calvin
 */
public class Start {

	public static final int PORT = 8080;
	public static final String CONTEXT = "/mini-service";
	public static final String BASE_URL = "http://localhost:8080/mini-service";

	public static void main(String[] args) throws Exception {
		Server server = JettyUtils.buildDebugServer(PORT, CONTEXT);
		server.start();
		
	    System.out.println("Hit Enter in console to stop server");
	    if (System.in.read() != 0) {
			System.out.println("Server stopping");
			server.stop();
			System.out.println("Server stopped");
		}
	}
}
