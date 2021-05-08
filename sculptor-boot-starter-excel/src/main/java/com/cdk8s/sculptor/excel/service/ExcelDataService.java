/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ExcelDataService.java
 * 项目名称：sculptor-boot-starter-excel
 * 项目描述：sculptor-boot-starter-excel
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.excel.service;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.code.CodecUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
public class ExcelDataService<T extends BaseRowModel> {


	//=====================================业务处理 start=====================================

	public void exportList(String fileName, String sheetName, Map<Integer, Integer> columnWidthMap, List<T> list, Class<T> clazz, HttpServletResponse response) {
		try {

			fileName = fileName + "_" + DatetimeUtil.formatDateTime(new Date(), "yyyyMMddHHmmss");

			response.setContentType("application/x-download");
			response.setCharacterEncoding("utf-8");
			fileName = CodecUtil.encodeURL(fileName);
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

			ServletOutputStream outputStream = response.getOutputStream();

			ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
			Sheet sheet1 = new Sheet(1, 0, clazz, sheetName, null);


			if (null != columnWidthMap) {
				sheet1.setColumnWidthMap(columnWidthMap);
			}


			writer.write(list, sheet1);
			writer.finish();

			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			log.error("导出 excel 失败，原因={}", e.getMessage());
		}
	}


	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================
	private TableStyle createTableStyle() {
		TableStyle tableStyle = new TableStyle();
		Font headFont = new Font();
		headFont.setBold(true);
		headFont.setFontHeightInPoints((short) 22);
		headFont.setFontName("楷体");
		tableStyle.setTableHeadFont(headFont);
		tableStyle.setTableHeadBackGroundColor(IndexedColors.RED);

		Font contentFont = new Font();
		contentFont.setBold(true);
		contentFont.setFontHeightInPoints((short) 22);
		contentFont.setFontName("黑体");
		tableStyle.setTableContentFont(contentFont);
		tableStyle.setTableContentBackGroundColor(IndexedColors.YELLOW);
		return tableStyle;
	}
	//=====================================私有方法  end=====================================

}
