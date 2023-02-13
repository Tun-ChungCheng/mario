package db;

public class Player {
    private final int score;
    private final String name;
    private final String account;


    public Player(String name, String account) {
        this.name = name;
        this.account = account;
        this.score = 0;
    }

    public Player(String name, String account, int score) {
        this.name = name;
        this.account = account;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public int getScore() {
        return score;
    }

}
