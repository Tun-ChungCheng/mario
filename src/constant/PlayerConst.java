package constant;

public interface PlayerConst {
    // ========================================
    // Action
    // ========================================
    public static final int DIE = 0;
    public static final int JUMP_LEFT = 1;
    public static final int TURN_LEFT = 2;
    public static final int RUN_LEFT = 3;
    public static final int IDLE_LEFT = 4;
    public static final int JUMP_RIGHT = 5;
    public static final int TURN_RIGHT = 6;
    public static final int RUN_RIGHT = 7;
    public static final int IDLE_RIGHT = 8;

    // ========================================
    // Direction
    // ========================================
    public static final int DIR_LEFT = 9;
    public static final int DIR_RIGHT = 10;

    // ========================================
    // Velocity
    // ========================================
    public static final int DELTA_X = 2;
    public static final int DELTA_Y = 2;

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
