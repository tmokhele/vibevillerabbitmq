package vibeville.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;


public class ObjectWriterUtil {
    public static String getJsonObject(Object o) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter().with(new ISO8601DateFormat());
        return ow.writeValueAsString(o);
    }
}
