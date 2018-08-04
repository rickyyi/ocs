package cn.hf.manage.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import cn.hf.manage.pojo.CallTask;
import cn.hf.manage.pojo.CallTask2;
import cn.hf.manage.pojo.EasyUIResult;
import cn.hf.manage.service.CallTaskService;

/**
 * 说明：呼叫任务控制层
 * @author XiJie
 * @version 1.0
 * @date 2017年10月23日
 */
@Controller
@RequestMapping("/callTask")
public class CallTaskController {

	// 属性注入呼叫业务业务层对象
	@Autowired
	private CallTaskService callTaskService;
	
	
	/**
	 * 说明：批量导入模板下载
	 * @return
	 * @author XiJie
	 * @time：2017年10月30日 下午3:33:37
	 */
	@RequestMapping(value="/templetDownload")
	public String batchTempletDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			// 2. 通过工作簿创建一个工作表
			XSSFSheet sheet = xssfWorkbook.createSheet();
			// 3. 通过工作表创建第一行，设置头行标题
			XSSFRow xssfRow = sheet.createRow(0);
			// 4. 通过第一行来创建每一格，并且给每格添加内容
			xssfRow.createCell(0).setCellValue("号码");
			xssfRow.createCell(1).setCellValue("备注");
			// 5. 给表格添加数据
			XSSFRow xssfRow2 = sheet.createRow(1);
			xssfRow2.createCell(0).setCellValue("15895085373");
			xssfRow2.createCell(1).setCellValue("备注的文本");
			xssfWorkbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		String filename = "批量号码导入模板";
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String((filename + ".xlsx").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}
	
	
	/**
	 * 说明：保存呼叫任务
	 * @return
	 * @author XiJie
	 * @time：2017年10月23日 下午4:19:44
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> saveCallTask(CallTask callTask){
		try {
			// 调用业务层方法进行保存
			boolean flag = callTaskService.saveCallTask(callTask);
			// 对结果进行判断
			if (flag) {
				// 如果保存成功   返回201 
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 出现异常 返回  500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		// 否则返回  500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	
	/**
	 * 说明：分页+条件查询
	 * @param callTask
	 * @return
	 * @author XiJie
	 * @time：2017年10月23日 下午4:55:54
	 */
	@RequestMapping(value="/query", method=RequestMethod.POST)
	public ResponseEntity<EasyUIResult> queryCallTask(CallTask2 callTask2,
			@RequestParam(defaultValue="10",value="page") Integer pageNum,
			@RequestParam(defaultValue="1",value="rows") Integer pageSize){
		try {
			EasyUIResult easyUIResult = null;
			if (callTask2.getStartCreateTime() != null || callTask2.getEndCreateTime() != null) {
				// 调用业务层方法进行查询
				PageInfo<CallTask> pageInfo = callTaskService.queryCallTaskByCondition2(callTask2, pageNum, pageSize);
				// 对数据进行封装
				easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
			} else {
				// 调用业务层方法进行查询
				PageInfo<CallTask> pageInfo = callTaskService.queryCallTaskByCondition(callTask2, pageNum, pageSize);
				// 对数据进行封装
				easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
			}
			// 如果返回的结果不为空 
			return ResponseEntity.status(HttpStatus.OK).body(easyUIResult);
		} catch (Exception e) {
			e.printStackTrace();
			// 出现异常 返回  500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/**
	 * 说明：根据id来查询对象
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 上午9:39:16
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<CallTask> queryCallTaskById(@RequestParam("id")Integer id){
		try {
			// 1. 根据id到业务层查询对应得对象
			CallTask callTask = callTaskService.queryCallTaskById(id);
			// 2. 根据返回的结果进行判断是否是否为空
			if (callTask != null) {
				// 2.1 如果不为空   返回实体对象   200
				return ResponseEntity.status(HttpStatus.OK).body(callTask);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		// 2.2 如果为空返回null
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	/**
	 * 说明：修改实体类
	 * @param callTask
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 上午10:33:26
	 */
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Void> updateCallTask(CallTask callTask){
		try {
			boolean flag = callTaskService.updateCallTask(callTask);
			if (flag) {
				// 返回200
				return ResponseEntity.status(HttpStatus.OK).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 返回500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		// 返回400
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	
	/**
	 * 说明：根据id单个删除对应的对象
	 * @param id
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 上午11:25:30
	 */
	@RequestMapping(value="/single", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCallTaskById(@RequestParam("id")Integer id){
		try {
			boolean flag = callTaskService.deleteCallTaskById(id);
			if (flag) {
				// 返回200
				return ResponseEntity.status(HttpStatus.OK).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 返回500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		// 返回400
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	/**
	 * 说明：根据id批量删除对应的对象
	 * @param id
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 上午11:25:30
	 */
	@RequestMapping(value="/batch", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCallTaskByIds(@RequestParam("ids")String ids){
		try {
			boolean flag = callTaskService.deleteCallTaskByIds(ids);
			if (flag) {
				// 返回200
				return ResponseEntity.status(HttpStatus.OK).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 返回500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		// 返回400
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	};
	
	@RequestMapping(value="/pause", method=RequestMethod.PUT)
	public ResponseEntity<Void> pauseCallTaskByIds(@RequestParam("ids")String ids){
		try {
			boolean flag = callTaskService.pauseCallTaskByIds(ids);
			if (flag) {
				// 返回200
				return ResponseEntity.status(HttpStatus.OK).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 返回500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		// 返回400
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	
	
	 /**
	 * 说明：批量导入数据
	 * @return
	 * @author XiJie
	 * @time：2017年10月25日 下午1:16:17
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	@RequestMapping(value="/importData")
	public ResponseEntity<Map<String, Object>> batchImportData(@RequestParam("file") MultipartFile file,
			CallTask callTask){
		Map<String, Object> map = new HashMap<String, Object>();
		if(file != null){
			// 创建一个list集合
			List<String> list = new ArrayList<String>();
			try {
				// 1. 创建一个工作簿  关联到要上传的excel文件
				InputStream inputStream = file.getInputStream();
				XSSFWorkbook hssfWorkbook = new XSSFWorkbook(inputStream);
				// 2. 打开sheet表，获取表中所有行数据
				XSSFSheet sheet = hssfWorkbook.getSheetAt(0);
				// 3. 遍历sheet表  得到每一行数据
				for (Row row : sheet) {
					// 3.1    如果是第一行  就跳过
					if (row.getRowNum() == 0) {
						continue;
					}
					
					if(row.getCell(0) != null){
						// 3.2    如果第一个没有数据  就跳过
						// 设置类型
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						String value = row.getCell(0).getStringCellValue();
						if (StringUtils.isNotBlank(value)) {
							// 3.2.1    将第一格数据添加到集合中
							list.add(row.getCell(0).getStringCellValue());
						}
					}
				}
				
				// 4. 调用业务层进行数据的保存
				boolean flag = callTaskService.saveBatchImportData(callTask , list);
				if (flag) {
					map.put("result", true);
					return  ResponseEntity.status(HttpStatus.CREATED).body(map);
				}
				map.put("result", false);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("result", false);
				return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
			}
		}
		return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	 }
	
	
}
