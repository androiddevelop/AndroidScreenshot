import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

/**
 * 手机截图
 * 
 * @author Yuedong Li
 * 
 */
public class PhoneGui extends JFrame {
	private static final long serialVersionUID = -5909492939837282220L;
	private ImagePanel imagePanel;
	private ScreenShot screenShot = new ScreenShot();

	public PhoneGui() {
		imagePanel = new ImagePanel();
		imagePanel.setSize(new Dimension(Config.WIDTH, Config.HEIGHT));
		this.add(imagePanel, BorderLayout.CENTER);
		imagePanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				performMouseAction(e, false);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				performMouseAction(e, true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){ //返回
					int x = 661 ;
					int y = 970 ;
					screenShot.clickScreen(x, y, false);
				}else if(e.getButton() == MouseEvent.BUTTON2){ //房子建
					int x = 388;
					int y = 970 ;
					screenShot.clickScreen(x, y, false);
				}
			}
		});

		refreshImage();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(Config.WIDTH + 2, Config.HEIGHT + 25));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void performMouseAction(MouseEvent e,boolean isFirstClick){
		if(e.getButton() == MouseEvent.BUTTON1){
			int x = (int) (e.getX() * Config.WIDTH_SCALE + 0.5);
			int y = (int) (e.getY() * Config.HEIGHT_SCALE + 0.5);
			screenShot.clickScreen(x, y, isFirstClick);
		}
	}

	/**
	 * 刷新图像
	 */
	private void refreshImage() {
		new Thread() {
			public void run() {
				while (true) {
					Image image = screenShot.getScreenshotBufferedImage();
					imagePanel.setBackgroundImage(image);
					imagePanel.updateUI();
				}

			}
		}.start();
	}

	public static void main(String[] args) {
		new PhoneGui();
	}
}