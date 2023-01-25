package entity;


import java.awt.*;

public abstract class Entity extends Rectangle{
    protected Rectangle hitbox;

    public Entity(int x, int y, int width, int height) {
        super(x, y, width, height);
        hitbox = new Rectangle(x, y, width, height);
    }

    protected void drawHitbox(Graphics g) {
        g.setColor(Color.cyan);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    protected void updateHitbox() {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
