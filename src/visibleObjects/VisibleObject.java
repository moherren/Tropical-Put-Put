package visibleObjects;

import java.awt.Graphics;
import graphics.Render2D;

public interface VisibleObject {
public void render(Render2D r);
public void render(Graphics g);
}
