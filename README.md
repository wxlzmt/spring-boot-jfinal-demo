# spring-boot-jfinal-demo  

本项目是`spring boot` 和 `jfinal`的糅合版`demo`

先搭建`spring boot` 项目, 然后添加`jfinal`依赖  [https://github.com/jfinal/jfinal](https://github.com/jfinal/jfinal) , 最终改装成本项目.

## 本项目的特性    
- 运行环境由`spring boot`接管,项目产物是`jar`,已解决项目以`jar`形式运行导致的路径问题.
- `mvc`由`jfinal`接管.
	
## 注意    
- `jfinal` 和 `spring boot web` 是两个不同的`web`框架. 
- 在一个项目中混改融合两个框架可能会出现未知后遗症.

## 环境

`jdk1.8`  `apache-maven-3.5.0` 

## usage
项目下载之后,什么都不要改.    

在`windows`环境下, 运行`run-maven-clean-and-install.bat`打包.    
然后运行 `run-target-jar-dev.bat` 直接跑项目.
然后浏览器访问 `http://127.0.0.1:8080/` 或 `http://127.0.0.1:8080/t` ,亲测好使.

在`linux`环境下,将打包好的`spring-boot-jfinal-demo.jar` 和`script/run-demo-dev.sh`放在同一个目录下,运行启动脚本即可.





