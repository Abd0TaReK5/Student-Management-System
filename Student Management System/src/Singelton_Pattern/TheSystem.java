package Singelton_Pattern;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//we should create diffrent interfaces for each method we want to implement to follow the single responsibility open closed principles !!
public class TheSystem implements table {
    private static TheSystem instance;  // Singleton instance
    
    // Private constructor to prevent instantiation
    private TheSystem() {}

    // Singleton instance request method
    public static TheSystem getInstance() {
        if (instance == null) {
            instance = new TheSystem();
        }
        return instance;
    }

    @Override
    public void populateTable(JTable table, String filterValue) {
        String query = "SELECT * FROM students";
        String orderBy = "";

        switch (filterValue) {
            case "name":
                orderBy = " ORDER BY name ASC";
                break;
            case "level":
                orderBy = " ORDER BY level ASC";
                break;
            case "course":
                orderBy = " ORDER BY course ASC";
                break;
            case "graduation":
                orderBy = " ORDER BY CASE graduation "
                    + "WHEN 'Undergraduate' THEN 1 "
                    + "WHEN 'Part-time' THEN 2 "
                    + "WHEN 'Graduate' THEN 3 "
                    + "ELSE 4 END DESC";
                break;
            default:
                break;
        }

        query += orderBy;

        try (Connection conn = DBconnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            DefaultTableModel model = new DefaultTableModel();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }

            table.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

   
    
    
    



