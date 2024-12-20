package Singelton_Pattern;
import java.sql.Connection;
/**
 *
 * @author TaReK
 */
public class Proxy implements DBconnectionProxy {
    private final DBconnection realDBConnection;
    private final String currentUserRole;

    public Proxy(String userRole) {
        this.currentUserRole = userRole;
        this.realDBConnection = DBconnection.getInstance(); // Real DBconnection instance
    }

    @Override
    public Connection getConnection() {
        if (hasPermissionToAccess()) {
            return realDBConnection.getConnection();  // Allow connection if admin
        } else {
            System.out.println("Access denied. Insufficient permissions.");
            return null;  // Deny connection if not admin
        }
    }
    // this method here is for proxy method and deal with it
    private boolean hasPermissionToAccess() {
        // Only allow access if the current user role is admin
        return "admin".equalsIgnoreCase(currentUserRole);
    }
    // this method here is for gui and deal with it
    @Override
    public boolean hasPermission() {
        return "admin".equalsIgnoreCase(currentUserRole);  // Return true if the user is admin
    }
}





