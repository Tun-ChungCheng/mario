package sprite;

import util.ClipsLoader;
import util.ImagesLoader;


public class Player extends Sprite {
    private static final int JUMP_SPEED = 5;

    private boolean up, right, down, left, jump;
    private boolean isFacingRight = true;
    private ClipsLoader clipsLoader;

    public Player(int x, int y, int width, int height,
                  ImagesLoader imagesLoader, ClipsLoader clipsLoader, String mapName) {
        super(x, y, width, height, imagesLoader, "idleRight", mapName);
        this.clipsLoader = clipsLoader;
    }

    public void update(){
        updateImage();
        updatePosition();
        updateJumpStatus();
        super.update();
    }

    private void updateImage() {
        if (isFacingRight) setImage("idleRight");
        if (!isFacingRight) setImage("idleLeft");

        if (jump && isFacingRight) setImage("jumpRight");
        if (jump && !isFacingRight) setImage("jumpLeft");

        if (!jump && right) setRunningImages("runningRight");
        if (!jump && left) setRunningImages("runningLeft");
    }

    private void setRunningImages(String name) {
        setImage(name);
        loopImage(20);
        if (name == "runningRight") isFacingRight = true;
        else isFacingRight = false;
    }

    private void updatePosition() {
        if (up && !isSolid(x, y - JUMP_SPEED, x + width,y + height)) {
            y -= JUMP_SPEED;
        }
        if (up && right && !isSolid(x + dx, y - JUMP_SPEED, x + width,y + height)) {
            x += dx; y -= JUMP_SPEED;
        }
        if (up && left && !isSolid(x - dx, y - JUMP_SPEED, x + width,y + height)) {
            x -= dx; y -= JUMP_SPEED;
        }
        if (right && !isSolid(x, y, x + width + dx,y + height)) {
            x += dx; isFacingRight = true;
        }
        if (left && !isSolid(x - dx, y, x + width,y + height)) {
            x -= dx; isFacingRight = false;
        }
    }

    private void updateJumpStatus() {
        if (isSolid(x, y, x + width,y + height + GRAVITY)) jump = false;
        else {
            jump = true;
        }
    }

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
}
