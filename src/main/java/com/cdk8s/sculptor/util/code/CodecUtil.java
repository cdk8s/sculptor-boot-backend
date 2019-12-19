package com.cdk8s.sculptor.util.code;

import com.cdk8s.sculptor.util.ExceptionUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.google.common.io.BaseEncoding;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.net.URLDecoder;
import java.net.URLEncoder;

@Slf4j
public final class CodecUtil {

	public static String decodeURL(String source) {
		String target;
		try {
			target = URLDecoder.decode(source, "UTF-8");
		} catch (Exception e) {
			log.error("decode url failure", e);
			throw new RuntimeException(e);
		}
		return target;
	}

	public static String base64DecodeBySafe(final String string) {
		return new String(Base64.decodeBase64(string));
	}

}
