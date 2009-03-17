@echo off
if exist "..\tools\maven" goto begin
echo [INFO] tools\maven目录不存在，请下载all-in-one版本
goto end
:begin
set MAVEN_BAT="%cd%\..\tools\maven\maven-2.0.9\bin\mvn.bat"
cd ..\

echo [Step 1] 执行tools/maven/nexus/nexus-webapp-1.2.1/bin/jsw/windows-x86-32/Nexus.bat,启动Nexus Maven私服
start tools\maven\nexus-1.2.1\nexus-webapp-1.2.1\bin\jsw\windows-x86-32\Nexus.bat

echo [Step 2] 执行servers/derby/start-db.bat 以Standalone形式启动Derby数据库
cd servers\derby
start start-db.bat
cd ..\..\

echo [Step 3] 执行servers/tomcat/apache-tomcat-6.0.18/bin/startup.bat 启动Tomcat服务器
cd  servers\tomcat\apache-tomcat-6.0.18\bin\
start startup.bat
cd ..\..\..\..\

echo 暂停20秒等待nexus启动
ping -n 21 127.0.0.1>null

echo [Step 4] 安装SpringSide3 modules 和archetypes到 本地Maven仓库.
call %MAVEN_BAT% dependency:copy-dependencies -DoutputDirectory=lib
call %MAVEN_BAT% clean install 
 
echo [Step 5] 初始化Jar包，编译、测试、打包、部署mini-web项目到tomcat，访问路径为http://localhost:8080/mini-web
cd examples\mini-web
call %MAVEN_BAT% dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime
call %MAVEN_BAT% dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib  -DincludeScope=runtime
call %MAVEN_BAT% tomcat:undeploy
call %MAVEN_BAT% clean compile war:exploded tomcat:exploded -Pinitdb
cd ..\..\

echo [Step 6] 初始化Jar包，编译、测试、打包、部署mini-service项目到tomcat，http://localhost:8080/mini-service
cd examples\mini-service
call %MAVEN_BAT% dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime
call %MAVEN_BAT% dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib  -DincludeScope=runtime
call %MAVEN_BAT% tomcat:undeploy
call %MAVEN_BAT% clean compile war:exploded tomcat:exploded
cd ..\..\

echo [Step 7] 初始化Jar包，编译、测试、打包、部署showcase项目到tomcat，http://localhost:8080/showcase
cd examples\showcase
call %MAVEN_BAT% dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime
call %MAVEN_BAT% dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib  -DincludeScope=runtime
call %MAVEN_BAT% tomcat:undeploy
call %MAVEN_BAT% clean compile war:exploded tomcat:exploded -Pinitdb
cd ..\..\

echo [Step 8] 启动IE浏览上述三个项目 .
explorer http://localhost:8080/mini-web
explorer http://localhost:8080/mini-service
explorer http://localhost:8080/showcase

echo [INFO] SpringSide3.0 快速启动完毕.
:end
pause


