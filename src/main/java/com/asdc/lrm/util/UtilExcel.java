package com.asdc.lrm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import jxl.CellType;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts2.ServletActionContext;

/**
 * ExcelUtil 工具类
 */
public class UtilExcel {
	/**
	 * 将String转码——导出时的报表名字
	 * 
	 * @param name
	 * @return
	 */
	public static String transcodingForExportReportName(String name) {
		String result = "";
		try {
			result = new String(name.getBytes("ISO8859-1"), "UTF-8") + ".xls";
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		return result;
	}
	
	public static String writeExcel(List<String[]> list, String title) {
		if (null == title)
			title = "";
		
		File file = null;
		try {
			String path = UtilFile.createExcelDir();
			String uploadPath = ServletActionContext.getServletContext().getRealPath("")+UtilFile.separator+path+title+".xls";
			file = new File(uploadPath);
			file.deleteOnExit();
			WritableWorkbook book = Workbook.createWorkbook(file);
			writeBook(list, title, book);
			return uploadPath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void writeBook(List<String[]> list, String title,
			WritableWorkbook book) throws WriteException,
			RowsExceededException, IOException {
		
		WritableSheet sheet = book.createSheet(title, 0);
		sheet.getSettings().setDefaultColumnWidth(30); // 设置列的默认宽度
		
		Label label = new Label(1, 0, title, getTitleCellFormat());
		sheet.addCell(label);
		
		String[] titleStr = (String[]) list.get(0);
		// 合并单元格并写入标题
		mergeCellsAndInsertData(sheet, 0, 0, titleStr.length-1, 1, title, getTitleCellFormat());
		
		// 写入列名行
		UtilExcel.insertRowData(sheet, new Integer(2), titleStr, getTitleDataCellFormat(CellType.STRING_FORMULA));
		
		// 写入数据
		if(list.size() > 1){
			for (int i = 1; i < list.size(); i++) {
				try {
					String[] data = (String[]) list.get(i);
					UtilExcel.insertRowData(sheet, new Integer(i + 2), data, getDataCellFormat(CellType.STRING_FORMULA));
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
			}
		}else{
			mergeCellsAndInsertData(sheet, 0, 3, titleStr.length-1, 4, "No "+title, getDataCellFormat(CellType.STRING_FORMULA));
		}
		
		book.write();
		book.close();
	}
	
	/**
	 * 得到数据表头格式
	 * 
	 * @return
	 */
	public static WritableCellFormat getTitleCellFormat() {
		WritableCellFormat wcf = null;
		try {
			//字体样式
			WritableFont wf = new WritableFont(WritableFont.TIMES, 20, WritableFont.NO_BOLD, false);//最后一个为是否italic
			//wf.setColour(Colour.RED);
			wcf = new WritableCellFormat(wf);
			//对齐方式
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			//边框
			wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
			//背景色
			//wcf.setBackground(Colour.GREY_25_PERCENT);
			wcf.setWrap(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcf;
	}

	/**
	 * 合并单元格，并插入数据
	 * 
	 * @param sheet
	 * @param col_start 列开始
	 * @param row_start 行开始
	 * @param col_end 列结束
	 * @param row_end 行结束
	 * @param data
	 * @param format
	 */
	public static void mergeCellsAndInsertData(WritableSheet sheet,
			Integer col_start, Integer row_start, Integer col_end,
			Integer row_end, Object data, WritableCellFormat format) {
		try {
			sheet.mergeCells(col_start.intValue(), row_start.intValue(), col_end.intValue(), row_end.intValue());//左上角到右下角
			insertOneCellData(sheet, col_start, row_start, data, format);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 插入单元格数据
	 * 
	 * @param sheet
	 * @param col
	 * @param row
	 * @param data
	 */
	public static void insertOneCellData(WritableSheet sheet, Integer col, Integer row, Object data, WritableCellFormat format) {
		try {
			if (data instanceof Double) {
				jxl.write.Number labelNF = new jxl.write.Number(col.intValue(), row.intValue(), ((Double) data).doubleValue(), format);
				sheet.addCell(labelNF);
			} else if (data instanceof Boolean) {
				jxl.write.Boolean labelB = new jxl.write.Boolean(col.intValue(), row.intValue(), ((Boolean) data).booleanValue(), format);
				sheet.addCell(labelB);
			} else if (data instanceof Date) {
				jxl.write.DateTime labelDT = new jxl.write.DateTime(col.intValue(), row.intValue(), ((Date) data), format);
				sheet.addCell(labelDT);
				setCellComments(labelDT, "这是个创建表的日期说明！");
			} else {
				Label label = new Label(col.intValue(), row.intValue(), data.toString(), format);
				sheet.addCell(label);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 得到报表列名格式
	 * 
	 * @param type
	 * @return
	 */
	public static WritableCellFormat getTitleDataCellFormat(CellType type) {
		WritableCellFormat wcf = getDataCellFormat(type);
		try {
			wcf.setBackground(Colour.WHITE);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcf;
	}

	/**
	 * 得到数据格式
	 * 
	 * @return
	 */
	public static WritableCellFormat getDataCellFormat(CellType type) {
		WritableCellFormat wcf = null;
		try {
			//字体样式
			if (type == CellType.NUMBER || type == CellType.NUMBER_FORMULA) {//数字
				NumberFormat nf = new NumberFormat("#.00");
				wcf = new WritableCellFormat(nf);
			} else if (type == CellType.DATE || type == CellType.DATE_FORMULA) {//日期
				jxl.write.DateFormat df = new jxl.write.DateFormat("yyyy-MM-dd");
				wcf = new jxl.write.WritableCellFormat(df);
			} else {
				WritableFont wf = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD, false);//最后一个为是否italic
				wcf = new WritableCellFormat(wf);
			}
			//对齐方式
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			//边框
			wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
			wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
			//背景色
			wcf.setBackground(Colour.WHITE);
			//自动换行
			wcf.setWrap(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcf;
	}

	/**
	 * 给单元格加注释
	 * 
	 * @param label
	 * @param comments
	 */
	public static void setCellComments(Object label, String comments) {
		WritableCellFeatures cellFeatures = new WritableCellFeatures();
		cellFeatures.setComment(comments);
		if (label instanceof jxl.write.Number) {
			jxl.write.Number num = (jxl.write.Number) label;
			num.setCellFeatures(cellFeatures);
		} else if (label instanceof jxl.write.Boolean) {
			jxl.write.Boolean bool = (jxl.write.Boolean) label;
			bool.setCellFeatures(cellFeatures);
		} else if (label instanceof jxl.write.DateTime) {
			jxl.write.DateTime dt = (jxl.write.DateTime) label;
			dt.setCellFeatures(cellFeatures);
		} else {
			Label _label = (Label) label;
			_label.setCellFeatures(cellFeatures);
		}
	}

	/**
	 * 插入一行数据
	 * 
	 * @param sheet
	 *            工作表
	 * @param row
	 *            行号
	 * @param content
	 *            内容
	 * @param format
	 *            风格
	 */
	public static void insertRowData(WritableSheet sheet, Integer row, String[] dataArr, WritableCellFormat format) {
		try {
			Label label;
			for (int i = 0; i < dataArr.length; i++) {
				label = new Label(i, row.intValue(), dataArr[i], format);
				sheet.addCell(label);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * 生成Excel输入流
	 * @param list
	 * @param title
	 * @return
	 */
	public static InputStream generateExcelDataInputStream(List<String[]> list,String title){
		if(list == null || list.size() == 0) return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(baos);
			writeBook(list, title, workbook);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(baos.toByteArray());
	}
}