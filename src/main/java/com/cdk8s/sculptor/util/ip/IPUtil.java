package com.cdk8s.sculptor.util.ip;

import cn.hutool.extra.servlet.ServletUtil;
import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.ExceptionUtil;
import com.cdk8s.sculptor.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public final class IPUtil {

	public static String getIp(HttpServletRequest request) {
		return ServletUtil.getClientIP(request);
	}


	/**
	 * 最终输出格式：中国-广东省-广州市-电信
	 */
	public static String getRegionInfoByIp(String ip) {
		String fileName = "ip2region.db";
		String fileResourcesPath = "ip2region/" + fileName;

		InputStream inputStream;
		try {
			inputStream = new ClassPathResource(fileResourcesPath).getInputStream();
		} catch (IOException e) {
			log.error("找不到 ip2region.db 文件，请阅读 /resources/ip2region/README.md 说明文件");
			throw new SystemException("找不到 ip2region.db 文件");
		}

		try {
			File file = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);
			FileUtils.copyInputStreamToFile(inputStream, file);

			if (!file.exists()) {
				log.error("ip2region.db file does not exist");
				return null;
			}

			DbConfig config = new DbConfig();
			DbSearcher searcher = new DbSearcher(config, file.getPath());
			Method method;
			method = searcher.getClass().getMethod("btreeSearch", String.class);
			DataBlock dataBlock;
			dataBlock = (DataBlock) method.invoke(searcher, ip);//默认结果格式：2140|中国|0|广东省|广州市|电信|16525
			// 对格式进行处理
			String dataBlockRegion = dataBlock.getRegion();

			if (StringUtil.containsIgnoreCase(dataBlockRegion, "内网IP")) {
				return "内网IP";
			}

			List<String> result = StringUtil.split(dataBlockRegion, "|");
			if (CollectionUtil.isEmpty(result)) {
				return null;
			}

			StringBuilder stringBuilder = new StringBuilder();
			for (String temp : result) {
				if (StringUtil.equalsIgnoreCase(temp, "0")) {
					continue;
				}
				stringBuilder.append(temp);
				stringBuilder.append("-");
			}

			return StringUtil.removeEnd(stringBuilder.toString(), "-");
		} catch (Exception e) {
			ExceptionUtil.getStackTraceAsString(e);
		}
		return null;
	}
}
