AndroidScreenshot
=================

![github](http://115.29.248.115/github/phone.png)

解决问题
-------------
在PC上展示手机屏幕,提供点击滑动操作.之前有一个[项目](http://code.google.com/p/androidscreencast),
大家可以参考一下.

使用说明
------------
* 运行 java -jar Phone.jar即可
* 鼠标左键可以模拟点击以及拖拽
* 鼠标右键模拟点击HOME键
* 不同手机在存在鼠标事件时存在一定的差异,本项目在MOTO DEFY上测试,理论上MOTO手机都相同

实现原理:
------------
使用ddmlib包获取手机的截图或不断更新,同时将鼠标事件传递到手机
鼠标事件相关问题,大家有问题可以给我发送邮件> androiddevelop@qq.com