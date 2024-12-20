package Singelton_Pattern;
import java.sql.Connection;

/**
 *
 * @author TaReK
 */

public interface DBconnectionProxy {
    Connection getConnection();
    boolean hasPermission();
}
