package vibeville.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.StdDateFormat;


public class ObjectWriterUtil {
    public static String getJsonObject(Object o) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter().with(new StdDateFormat());
        return ow.writeValueAsString(o);
    }

    private ObjectWriterUtil()
    {
        throw new IllegalStateException("ObjectWriterUtil class");
    }
}
