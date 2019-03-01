# LXKtFrame

* 基础类

> common 文件夹下是继承基础类

* 抽象类

> entity文件夹下是eventbus 出现相关的类

* 请求相关

> http文件夹下是 请求相关的封装类

* 方法类

> utils 文件夹是 常用方法的集锦

* 组件库

> widget 文件夹是 封装的常用的组件库


* 依赖方式

``` python

allprojects{ 
    repositories{
         maven {url 'https://jitpack.io' 
         }
}

dependencies{ 
    implementation 'com.github.yundequanshi:LXKtFrame:V0.0.5-release' 
}

```