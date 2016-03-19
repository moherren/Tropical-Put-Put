package visibleObjects;

import java.awt.Graphics;

/**
 * Stand in graphics until the actual graphics is integrated
 * @author Peter
 *
 */
public interface TempGraphics {
	public void render(Graphics g);
}
