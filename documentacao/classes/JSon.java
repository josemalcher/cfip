package open.digytal.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JSon {
    static <T> T objeto(String string,Class classe) throws  Exception{
        ObjectMapper mapper = new ObjectMapper();
        //JSON from file to Object
        //User user = mapper.readValue(new File("c:\\user.json"), User.class);
        //JSON from String to Object
        Object objeto = mapper.readValue(string, classe);
        return (T) objeto;
    }
    static String string(Object objeto) throws Exception{
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(objeto);
        return json;
    }
}
