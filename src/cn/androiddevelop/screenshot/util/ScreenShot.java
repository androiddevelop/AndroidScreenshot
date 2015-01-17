package cn.androiddevelop.screenshot.util;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cn.androiddevelop.screenshot.Config;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;

/**
 * 实现设备截图
 * 
 * @author YD
 *
 */
public class ScreenShot {

	public IDevice device;

	/**
	 * 构造函数，默认获取第一个设备
	 */
	public ScreenShot() {
		AndroidDebugBridge.init(false);
		device = this.getDevice(0);
	}

	/**
	 * 构造函数，指定设备序号
	 * 
	 * @param deviceIndex
	 *            设备序号
	 */
	public ScreenShot(int deviceIndex) {
		AndroidDebugBridge.init(false);
		device = this.getDevice(deviceIndex);
	}

	/**
	 * 直接抓取屏幕数据
	 * 
	 * @return 屏幕数据
	 */
	public RawImage getScreenShot() {
		RawImage rawScreen = null;
		if (device != null) {
			try {
				rawScreen = device.getScreenshot();
			} catch (TimeoutException e) {
				e.printStackTrace();
			} catch (AdbCommandRejectedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.print("没有找到设备");
		}
		return rawScreen;
	}

	/**
	 * 获取图片byte[]数据
	 * 
	 * @return 图片byte[]数据
	 */
	public byte[] getScreenShotByteData() {
		RawImage rawScreen = getScreenShot();
		if (rawScreen != null) {
			return rawScreen.data;
		}
		return null;
	}

	/**
	 * 返回截图
	 * 
	 * @return
	 */
	public BufferedImage getScreenshotBufferedImage() {
		RawImage rawScreen = getScreenShot();
		BufferedImage image = null;
		if (rawScreen != null) {
			Boolean landscape = false;
			int width2 = landscape ? rawScreen.height : rawScreen.width;
			int height2 = landscape ? rawScreen.width : rawScreen.height;
			image = new BufferedImage(width2, height2,
					BufferedImage.TYPE_INT_RGB);
			if (image.getHeight() != height2 || image.getWidth() != width2) {
				image = new BufferedImage(width2, height2,
						BufferedImage.TYPE_INT_RGB);
			}
			int index = 0;
			int indexInc = rawScreen.bpp >> 3;
			for (int y = 0; y < rawScreen.height; y++) {
				for (int x = 0; x < rawScreen.width; x++, index += indexInc) {
					int value = rawScreen.getARGB(index);
					if (landscape)
						image.setRGB(y, rawScreen.width - x - 1, value);
					else
						image.setRGB(x, y, value);
				}
			}
		}
		return image;
	}

	/**
	 * 抓取图片并保存到指定路径
	 * 
	 * @param path
	 *            文件路径
	 * @param fileName
	 *            文件名
	 */
	public void getScreenShot(String path, String fileName) {
		BufferedImage image = getScreenshotBufferedImage();
		try {
			if (null != image)
				ImageIO.write((RenderedImage) image, "PNG", new File(path + "/"
						+ fileName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取得到device对象
	 * 
	 * @param index
	 *            设备序号
	 * @return 指定设备device对象
	 */
	private IDevice getDevice(int index) {
		IDevice device = null;
		// 如果代码有问题请查看API，修改此处的参数值试一下
		// AndroidDebugBridge bridge = AndroidDebugBridge.createBridge("adb",
		// true);
		AndroidDebugBridge bridge = AndroidDebugBridge.createBridge();
		waitDevicesList(bridge);
		IDevice devices[] = bridge.getDevices();

		for (int i = 0; i < devices.length; i++) {
			System.out.println(devices[i].toString());
		}

		if (devices.length < index) {
			System.err.print("没有检测到第" + index + "个设备");
		} else {
			if (devices.length - 1 >= index) {
				device = devices[index];
			} else {
				device = devices[0];
			}
		}
		return device;
	}

	/**
	 * 等待查找device
	 * 
	 * @param bridge
	 */
	private void waitDevicesList(AndroidDebugBridge bridge) {
		int count = 0;
		while (bridge.hasInitialDeviceList() == false) {
			try {
				Thread.sleep(500);
				count++;
			} catch (InterruptedException e) {
			}
			if (count > 60) {
				System.err.print("等待获取设备超时");
				break;
			}
		}
	}

	/**
	 * 点击屏幕
	 * 
	 * @param x
	 * @param y
	 * @param isPressDown
	 *            是否在拖动
	 */
	public void clickScreen(int x, int y, boolean isPressDown) {
		try {
			if (isPressDown) {
				device.executeShellCommand(
						"sendevent /dev/input/event7 1 330 1",
						new ShellReceiver(System.out));
				device.executeShellCommand(
						"sendevent /dev/input/event7 3 58 1",
						new ShellReceiver(System.out));
				device.executeShellCommand("sendevent /dev/input/event7 3 53 "
						+ x * Config.WIDTH_SCALE / Config.SCREEN_SCALE,
						new ShellReceiver(System.out));
				device.executeShellCommand("sendevent /dev/input/event7 3 54 "
						+ y * Config.HEIGHT_SCALE / Config.SCREEN_SCALE,
						new ShellReceiver(System.out));
				device.executeShellCommand("sendevent /dev/input/event7 0 2 0",
						new ShellReceiver(System.out));
				device.executeShellCommand("sendevent /dev/input/event7 0 0 0",
						new ShellReceiver(System.out));

			} else {

				device.executeShellCommand(
						"sendevent /dev/input/event7 1 330 0",
						new ShellReceiver(System.out));
				device.executeShellCommand(
						"sendevent /dev/input/event7 3 58 0",
						new ShellReceiver(System.out));
				device.executeShellCommand("sendevent /dev/input/event7 3 53 "
						+ x * Config.WIDTH_SCALE / Config.SCREEN_SCALE,
						new ShellReceiver(System.out));
				device.executeShellCommand("sendevent /dev/input/event7 3 54 "
						+ y * Config.HEIGHT_SCALE / Config.SCREEN_SCALE,
						new ShellReceiver(System.out));
				device.executeShellCommand("sendevent /dev/input/event7 0 2 0",
						new ShellReceiver(System.out));
				device.executeShellCommand("sendevent /dev/input/event7 0 0 0",
						new ShellReceiver(System.out));
			}
		} catch (TimeoutException | AdbCommandRejectedException
				| ShellCommandUnresponsiveException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拖动
	 * 
	 * @param x
	 * @param y
	 */
	public void drag(int x, int y) {
		try {
			device.executeShellCommand("sendevent /dev/input/event7 3 53 " + x
					* Config.WIDTH_SCALE / Config.SCREEN_SCALE,
					new ShellReceiver(System.out));
			device.executeShellCommand("sendevent /dev/input/event7 3 54 " + y
					* Config.HEIGHT_SCALE / Config.SCREEN_SCALE,
					new ShellReceiver(System.out));
			device.executeShellCommand("sendevent /dev/input/event7 0 2 0",
					new ShellReceiver(System.out));
			device.executeShellCommand("sendevent /dev/input/event7 0 0 0",
					new ShellReceiver(System.out));
		} catch (TimeoutException | AdbCommandRejectedException
				| ShellCommandUnresponsiveException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输入字符
	 * 
	 * @param keycode
	 */
	public void inputChar(int keycode) {
		try {
			device.executeShellCommand("sendevent /dev/input/event7 1  "
					+ keycode + " 1", new ShellReceiver(System.out));
			device.executeShellCommand("sendevent /dev/input/event7 0 0 0",
					new ShellReceiver(System.out));

			device.executeShellCommand("sendevent /dev/input/event7 1  "
					+ keycode + " 0", new ShellReceiver(System.out));
			device.executeShellCommand("sendevent /dev/input/event7 0 0 0",
					new ShellReceiver(System.out));
		} catch (TimeoutException | AdbCommandRejectedException
				| ShellCommandUnresponsiveException | IOException e) {
			e.printStackTrace();
		}
	}
}