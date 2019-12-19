package com.cdk8s.sculptor.util.code;

import com.cdk8s.sculptor.util.StringUtil;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.Charset;


@Slf4j
public final class Md5Util {

	@SuppressWarnings("deprecation")
	public static String md5ByGuava(final String sourceString) {
		if (StringUtil.isNotBlank(sourceString)) {
			return Hashing.md5().hashString(sourceString, Charset.forName(Charsets.UTF_8.toString())).toString();
		}
		return null;
	}

}
