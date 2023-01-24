package entity;


import lombok.Data;

@Data
public abstract class Entity {
    protected float width, height;
    public int x, y;

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width  = width;
        this.height = height;
    }
}
