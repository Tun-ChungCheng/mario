package db;

import util.BCrypt;

import java.sql.*;
import java.util.Properties;

public class Database {
    private static final String URI = "jdbc:mysql://";
    private static final String HOST = "localhost:3306";
    private static final String DATABASE = "mario";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String CHECK_ACCOUNT = "SELECT count(*) count FROM players WHERE account = ?";
    private static final String CREATE_ACCOUNT = "INSERT INTO players (name, account, password) VALUES (?, ?, ?)";
    private static final String LOG_IN = "SELECT * FROM players WHERE account = ?";
    private static final String UPDATE_SCORE = "UPDATE players SET score = ? WHERE account = ?";
    private static final String READ_TOP_10_PLAYERS = "SELECT * FROM players ORDER BY score DESC LIMIT 10";

    public Connection connection;

    private Player player;

    public Database() throws SQLException {
        Properties properties = new Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD);

        String url = URI + HOST + "/" + DATABASE;
        connection = DriverManager.getConnection(url, properties);
        if (connection != null)
            System.out.printf("Connect to SERVER %s DATABASE %s successful%n", HOST, DATABASE);
        assert connection != null;
    }

    public boolean logIn(String account, String password) {
        try (PreparedStatement statement = connection.prepareStatement(LOG_IN)) {
            statement.setString(1, account);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (BCrypt.checkpw(password, resultSet.getString("password"))) {
                    player = new Player( resultSet.getInt("id"),
                                         resultSet.getString("name"),
                                         resultSet.getString("account"),
                                         resultSet.getInt("score") );
                    return true;
                } else return false;
            } else return false;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean checkAccount(String account) {
        try (PreparedStatement statement = connection.prepareStatement(CHECK_ACCOUNT)) {
            statement.setString(1, account);

            if (account.equals("")) return false;
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("count") == 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createAccount(String name, String account, String password) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_ACCOUNT)) {
            statement.setString(1, name);
            statement.setString(2, account);
            statement.setString(3, BCrypt.hashpw(password, BCrypt.gensalt()));

            if (statement.executeUpdate() != 0) {
                player = new Player(name, account);
                return true;
            } else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateScore(int score) {
        if (player.getScore() >= score) {
            System.out.printf("Previous score %d greater equal current score %d.%n", player.getScore(), score);
            return;
        }
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SCORE)) {
            statement.setInt(1, score);
            statement.setString(2, player.getAccount());

            if (statement.executeUpdate() == 1) System.out.println("Update successful!");
            else System.out.println("Update fail!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object[][] getTopTenPlayers() {
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(READ_TOP_10_PLAYERS);
            Object[][] players = new Object[10][3];

            for(int rank = 1; resultSet.next();rank++) {
                players[rank - 1][0] = "No." + rank;
                players[rank - 1][1] = resultSet.getString("name");
                players[rank - 1][2] = resultSet.getString("score");
            }

            return players;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Player getPlayer() {
        return player;
    }
}
