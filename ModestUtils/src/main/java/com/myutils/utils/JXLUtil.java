package com.myutils.utils;


import jxl.Workbook;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class JXLUtil {

	/**
	 * 写excel文件
	 *
	 */
	public static File writeExc(File filename, String[] titleList, String[] dataList) {
		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(filename);
			WritableSheet ws = wwb.createSheet("Excel导出", 0);// 创建sheet
			Label l;
			for (int i = 0; i < titleList.length; i++) {
				l = new Label(i, 0, transStrIso8859ToUtf8(titleList[i]), getTitle());
				ws.addCell(l);
				ws.setColumnView(i, 20);
				ws.setRowView(2, 400);
			}
			for (int x = 0; x < dataList.length; x++) {
				String[] cellData = dataList[x].split(",");
				for (int y = 0; y < cellData.length; y++) {
					l = new Label(y, x+1, transStrIso8859ToUtf8(cellData[y]), getNormolCell());
					ws.addCell(l);
				}
			}
			wwb.write();
		} catch (RowsExceededException e1) {
			e1.printStackTrace();
		} catch (WriteException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			// 关闭流
			if(wwb!= null){
				try {
					wwb.close();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//System.out.println("写入成功！ ");
		return filename;
	}

	/**
	 * 设置头的样式
	 *
	 * @return
	 */
	public static WritableCellFormat getHeader() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 24,
				WritableFont.BOLD);// 定义字体
		try {
			font.setColour(Colour.BLUE);// 蓝色字体
		} catch (WriteException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
			format.setBackground(Colour.YELLOW);// 黄色背景
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 设置标题样式
	 *
	 * @return
	 */
	public static WritableCellFormat getTitle() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 14, WritableFont.BOLD);
		try {
			font.setColour(Colour.BLACK);// 黑色字体
		} catch (WriteException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);

		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			format.setBackground(Colour.GRAY_25);
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 设置其他单元格样式
	 *
	 * @return
	 */
	public static WritableCellFormat getNormolCell() {// 12号字体,上下左右居中,带黑色边框
		WritableFont font = new WritableFont(WritableFont.TIMES, 12);
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}
	private static String transStrIso8859ToUtf8(String str) throws UnsupportedEncodingException{
		String rtnStr = new String(str.getBytes("iso-8859-1"),"utf-8");
		return  rtnStr;
	}
}
