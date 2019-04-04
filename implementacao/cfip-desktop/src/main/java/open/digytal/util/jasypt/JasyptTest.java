package open.digytal.util.jasypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class JasyptTest {
	@Value("${url}")
    private String url;
	public String getUrl() {
		return url;
	}
}
