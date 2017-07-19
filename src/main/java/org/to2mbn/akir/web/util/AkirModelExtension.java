package org.to2mbn.akir.web.util;

import java.nio.charset.Charset;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class AkirModelExtension {

	public String md5(String input) {
		return DigestUtils.md5DigestAsHex(input.getBytes(Charset.forName("utf-8")));
	}

}
