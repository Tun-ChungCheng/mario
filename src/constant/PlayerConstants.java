package constant;

public interface PlayerConstants {

    public static final int DIE = 0;
    public static final int JUMP_LEFT = 1;
    public static final int TURN_LEFT = 2;
    public static final int RUN_LEFT = 3;
    public static final int IDLE_LEFT = 4;
    public static final int JUMP_RIGHT = 5;
    public static final int TURN_RIGHT = 6;
    public static final int RUN_RIGHT = 7;
    public static final int IDLE_RIGHT = 8;
    public static final int DIR_LEFT = 9;
    public static final int DIR_RIGHT = 10;
    public static final int DELTA_X = 3;
    public static final int DELTA_Y = 3;

    public static int GetSpriteActionAmount(int action) {
        switch (action) {
            case RUN_LEFT:
            case RUN_RIGHT:
                return 3;
            default:
            return 1;
        }
    }
}
