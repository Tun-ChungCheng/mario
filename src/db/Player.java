package db;

public class Player {
    private int id;
    private String name;
    private String account;
    private int score;

    public Player(String name, String account) {
        this(0, name, account, 0);
    }

    public Player(int id, String name, String account, int score) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
