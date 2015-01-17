AndroidScreenshot
=================

![github](http://androiddevelop.cn/github/phone.png)

解决问题
-------------
在PC上展示手机屏幕,提供点击滑动操作.之前有一个[项目](http://code.google.com/p/androidscreencast),
大家可以参考一下.

使用说明
------------
* 运行MainGui类即可
* 鼠标左键可以模拟点击以及拖拽
* 按键ESC模拟点击返回，H键模拟点击HOME键
* 不同手机在存在鼠标事件时存在一定的差异,本项目是在Genymotion模拟器上测试

调整方式
--------------
####左上角点击
* /dev/input/event7: 0001 014a 00000001
* /dev/input/event7: 0003 003a 00000001
* /dev/input/event7: 0003 0035 00000040
* /dev/input/event7: 0003 0036 00000115
* /dev/input/event7: 0000 0002 00000000
* /dev/input/event7: 0000 0000 00000000
* /dev/input/event7: 0001 014a 00000000
* /dev/input/event7: 0003 003a 00000000
* /dev/input/event7: 0003 0035 00000040
* /dev/input/event7: 0003 0036 00000115
* /dev/input/event7: 0000 0002 00000000
* /dev/input/event7: 0000 0000 00000000

--------------------------------------------
####右上角点击
* /dev/input/event7: 0001 014a 00000001
* /dev/input/event7: 0003 003a 00000001
* /dev/input/event7: 0003 0035 000002f7
* /dev/input/event7: 0003 0036 00000496
* /dev/input/event7: 0000 0002 00000000
* /dev/input/event7: 0000 0000 00000000
* /dev/input/event7: 0001 014a 00000000
* /dev/input/event7: 0003 003a 00000000
* /dev/input/event7: 0003 0035 000002f7
* /dev/input/event7: 0003 0036 00000496
* /dev/input/event7: 0000 0002 00000000
* /dev/input/event7: 0000 0000 00000000

+ 每一次点击是一个touch-down与touch-up的操作，每6行为一个操作，中间2行对应坐标。
+ 如需适配自己设备，请再所需设备中使用**adb shell  getevent**进行操作，替换cn.androiddevelop.screenshot.util.ScreenShot.java文件中对应的参数即可。
+ 有些真机如moto，厂商已经屏幕坐标系格式化为1000*1000，具体可以通过点击屏幕右下角，查看坐标进行转换，坐标比例进行转换，请修改cn.androiddevelop.screenshot.Config.java中比例

实现原理:
------------
使用ddmlib包获取手机的截图或不断更新,同时将鼠标事件传递到手机
鼠标事件相关问题,大家有问题可以给我发送邮件> androiddevelop@qq.com