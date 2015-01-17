package cn.androiddevelop.screenshot.ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import cn.androiddevelop.screenshot.Config;

/**
 * 显示图片面板
 * 
 * @author Yuedong Li
 *
 */
public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 5564818820574092960L;
	private Image image;

	public void setBackgroundImage(Image image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (null == image) {
			return;
		}
		g.drawImage(image, 0, 0,
				(int) (Config.WIDTH * Config.SCREEN_SCALE + 0.5),
				(int) (Config.HEIGHT * Config.SCREEN_SCALE + 0.5), this);
		g = null;
	}
}