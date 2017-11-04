# akir
Minecraft账号系统

本项目目前还在开发中。

## 构建

构建依赖：
 * JDK 8或以上
 * npm
 * Gradle

执行`gradle clean bootJar`，构建输出位于`build/libs`下。

## 运行
```
./akir.jar
```
第一次运行时会在当前目录释放配置文件`application.yaml`，
编辑其中配置再次运行即可。

如要作为`init.d`或`systemd`服务运行，见
[Spring Boot Reference Guide §60.2 Unix/Linux Services](https://docs.spring.io/spring-boot/docs/2.0.0.BUILD-SNAPSHOT/reference/htmlsingle/#deployment-service) 。

## License
akir is licensed under [GPLv3](./LICENSE).
