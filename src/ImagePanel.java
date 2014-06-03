import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * 显示图片面板
 * @author Yuedong Li
 *
 */
public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 5564818820574092960L;
	private Image image ;

	public void setBackgroundImage(Image image){
		this.image =image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (null == image) {  
			return;  
		}  
		g.drawImage(image, 0, 0, Config.WIDTH, Config.HEIGHT,  
				this);  
		g = null;  
	}
}
