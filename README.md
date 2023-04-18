# FreeTime BackStage
这是毕业设计APP——FreeTime的后端系统。主要实现了三大模块功能
1. 基本课室管理系统接口
2. 人脸识别系统接口
3. AI小工具接口

同时，本项目中配备了管理系统前端页面的展示，文件夹为`backstageVue`，可以自行查看

用到的依赖请查看`pom.xml`文件
## 系统介绍
本系统从数据库设计开始，分析系统架构，自主设计了一个人脸识别系统，并将其应用在课堂签到中。
实现了课堂签到可类似于上班签到打卡的签到方式。根据第一性原理，
人脸签到需要一张人脸，有效避免了代签、漏签、假签，也省去了传统签到的手动特性。

人脸识别系统用到的开源模型为`SCRFD`+`CosFace`。其中，`SCRFD`参考了大佬**nihui**的`NCNN`手机端部署。

此外，通过部署超分辨率模型`ESRGAN`、风格转化`AnimalGANv2`、图像上色`siggraph`，实现了AI小工具的应用落地，可以在APP中进行使用
## 一些系统文件介绍

在`src/main/mysql_sqlfile`文件夹中，存储了系统的数据库文件，可以直接搭配`navicat`使用

人脸识别系统的部署代码放在`src/main/java/com/postdesign/detectsystem/service/serviceImpl/algorithmImpl`文件夹中，采用的是JAVA深度学习框架`DJL`

模型部署的`Translators`放在`src/main/java/com/postdesign/detectsystem/translators`中，这是用于数据预处理的类

系统整体架构采用MVC，拦截器配置在`configure`文件夹中，具体实现可以查看`src/main/java/com/postdesign/detectsystem`下的代码
