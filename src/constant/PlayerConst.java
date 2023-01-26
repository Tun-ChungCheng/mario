package constant;

public interface PlayerConst {
    // ========================================
    // Action
    // ========================================
    int DIE = 0;
    int JUMP_LEFT = 1;
    int TURN_LEFT = 2;
    int RUN_LEFT = 3;
    int IDLE_LEFT = 4;
    int JUMP_RIGHT = 5;
    int TURN_RIGHT = 6;
    int RUN_RIGHT = 7;
    int IDLE_RIGHT = 8;

    // ========================================
    // Direction
    // ========================================
    int DIR_LEFT = 9;
    int DIR_RIGHT = 10;

    // ========================================
    // Velocity
    // ========================================
    int DELTA_X = 1;
    int DELTA_Y = 2;
    int GRAVITY = 1;
    int JUMP_SPEED = 3;

    static int GetSpriteActionAmount(int action) {
        return switch (action) {
            case RUN_LEFT, RUN_RIGHT -> 3;
            default -> 1;
        };
    }
}
