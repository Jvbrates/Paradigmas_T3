package negocio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
public class util {
    static Date timeNow(){
        long millis=System.currentTimeMillis();
        // creating a new object of the class Date
        return new Date(millis);
    }
}
