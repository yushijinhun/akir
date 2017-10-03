# akir
Minecraft账号系统

目前还在开发中。

## 构建

构建依赖：
 * jdk8+
 * npm
 * gradle

执行`gradle clean bootJar`，构建输出位于`build/libs`下。

## 运行
```
java -jar akir.jar
```
第一次运行时会在当前目录释放配置文件`application.yaml`，
编辑其中配置再次运行即可。

## License
akir is licensed under [GPLv3](./LICENSE).
