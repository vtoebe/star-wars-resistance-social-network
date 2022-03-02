package br.com.letscode.stwars.mapper;




import org.dom4j.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MapperHelper {

    public static LocalDate transformToDate(String data) {
        if (data != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(data, formatter);
        }
        return null;
    }
}
