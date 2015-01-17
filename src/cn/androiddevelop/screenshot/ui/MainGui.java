package cn.androiddevelop.screenshot.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import cn.androiddevelop.screenshot.Config;
import cn.androiddevelop.screenshot.util.ScreenShot;

/**
 * 入口
 * 
 * @author Yuedong Li
 * 
 */
public class MainGui extends JFrame {
	private static final long serialVersionUID = 1L;
	private ImagePanel imagePanel;
	private ScreenShot screenShot = new ScreenShot();


	public MainGui() {
		imagePanel = new ImagePanel();
		this.add(imagePanel, BorderLayout.CENTER);
		imagePanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				performMouseAction(e, false);
				System.out.println("mouse released");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				performMouseAction(e, true);
				System.out.println("mouse pressed");
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		imagePanel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println("mouse moved");
				screenShot.drag(x, y);

			}
		});

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // 返回
					System.out.println("press esc key");
					screenShot.inputChar(1);
				} else if (e.getKeyCode() == KeyEvent.VK_H) { // 主键
					System.out.println("press home key");
					screenShot.inputChar(35);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		refreshImage();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 加2和25是留边框
		this.setSize(new Dimension(
				(int) (Config.WIDTH * Config.SCREEN_SCALE + 0.5) + 2,
				(int) (Config.HEIGHT * Config.SCREEN_SCALE + 0.5) + 25));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * 完成鼠标点击操作
	 * 
	 * @param e
	 * @param isPressDown
	 */
	private void performMouseAction(MouseEvent e, boolean isPressDown) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int x = e.getX();
			int y = e.getY();
			screenShot.clickScreen(x, y, isPressDown);
		}
	}

	/**
	 * 实时刷新图像
	 */
	private void refreshImage() {
		new Thread() {
			public void run() {
				while (true) {
					Image image = screenShot.getScreenshotBufferedImage();
					imagePanel.setBackgroundImage(image);
					// 可以加入刷新等待时间
					imagePanel.updateUI();
				}

			}
		}.start();
	}

	public static void main(String[] args) {
		new MainGui();
	}
}