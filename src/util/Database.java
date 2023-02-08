package util;

import java.sql.*;
import java.util.Properties;

public class Database {
    private static final String URI = "jdbc:mysql://";
    private static final String IP = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "mario";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String TABLE = "players";
    private static final String QUERY_ALL = "SELECT * FROM ";

    private Connection connection;

    public Database() throws SQLException {
        String url = URI + IP + ":" + PORT + "/" + DATABASE;

        Properties properties = new Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD);

        connection = DriverManager.getConnection(url, properties);
        if (connection != null) System.out.printf("Connect to SERVER %s:%s DATABASE %s successful%n", IP, PORT, DATABASE);

        PreparedStatement statement = connection.prepareStatement(QUERY_ALL + TABLE,
                                                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                                    ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
            System.out.println(resultSet.getString("id"));
            System.out.println(resultSet.getString("name"));
            System.out.println(resultSet.getString("score"));
        }

    }
}
