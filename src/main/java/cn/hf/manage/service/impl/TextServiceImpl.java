package cn.hf.manage.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.hf.manage.mapper.TextMapper;
import cn.hf.manage.pojo.ReturnText;
import cn.hf.manage.pojo.Text;
import cn.hf.manage.service.TextService;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 说明：有关没有匹配上的文本的业务层实现类
 * @author XiJie
 * @version 1.0
 * @date 2017年10月17日
 */
@Service("textService")
public class TextServiceImpl implements TextService {

	@Autowired
	private TextMapper textMapper;
	
	//语音文件、文本格式类型
	@Value("${VOICETYPE}")
	private String VOICETYPE;
	// Linux命令
	@Value("${NEWFILEPATH}")
	private String NEWFILEPATH;
	@Value("${SSHPORT}")
	private String SSHPORT;
	@Value("${TARGETPATH}")
	private String TARGETPATH;
	
	// 日志对象
	Logger log = LoggerFactory.getLogger(CallResultServiceImpl.class);

	/**
	 * 说明：保存没有匹配上的文本
	 * @param text
	 * @param callid
	 * @author XiJie
	 * @time：2017年10月17日 上午10:08:34
	 */
	@Override
	public void saveText(String text, String callid, String cause) {
		// 创建实体类对象
		Text record = new Text();
		// 保存数据
		// 如果text为空
		if (StringUtils.isEmpty(text)) {
			text = "sentence 传入的数据方式有误";
		}
		record.setName(text);
		
		// 对callid进行非空判断
		if (StringUtils.isEmpty(callid)) {
			callid = "callid 传入的数据方式有误";
		}
		record.setCallid(callid);
		
		// 保存case
		record.setCause(cause);
		
		// 创建当前时间
		long time = System.currentTimeMillis();
		Timestamp createTime = new Timestamp(time);
		record.setCreateTime(createTime );
		
		// 调用数据层保存的方法
		textMapper.insertSelective(record);
	}

	/**
	 * 说明：保存
	 * @param text
	 * @author XiJie
	 * @time：2017年10月26日 上午10:12:52
	 */
	@Override
	public void saveText(Text text) {
		textMapper.insertSelective(text);
	}

	/**
	 * 说明：保存
	 * @param sentence
	 * @param callid
	 * @param cause
	 * @param labels
	 * @author XiJie
	 * @time：2017年10月30日 下午2:32:10
	 */
	@Override
	public void saveText(String sentence, String callid, String cause, List<String> labels) {
		List<ReturnText> returnList = new ArrayList<>();
		for (String str : labels) {
			String[] labelArr = str.split("\\|");
			ReturnText returnText = new ReturnText();
			returnText.setMasterLabel(labelArr[0]);
			returnText.setAssistantLabel(labelArr[1]);
			returnList.add(returnText);
		}
		
		// 添加查询条件
		for (ReturnText returnText : returnList) {
			// 封装数据
			Text text = new Text();
			// 1. 如果name为空
			if (StringUtils.isEmpty(sentence)) {
				sentence = "sentence 传入的数据方式有误";
			}
			text.setName(sentence);
			
			// 2. 对callid进行非空判断
			if (StringUtils.isEmpty(callid)) {
				callid = "callid 传入的数据方式有误";
			}
			text.setCallid(callid);
			
			// 3. 对导致的原因cause进行保存
			text.setCause(cause);
			// 4. 对主标签进行保存
			text.setMasterLabel(returnText.getMasterLabel());
			// 5. 对副标签进行保存
			text.setAssistantLabel(returnText.getAssistantLabel());
			// 6. 创建当前时间
			long time = System.currentTimeMillis();
			Timestamp createTime = new Timestamp(time);
			text.setCreateTime(createTime);
			// 7.进行数据库保存
			textMapper.insertSelective(text);
		}
		
	}

	/**
	 * 说明：保存
	 * @param sentence
	 * @param callid
	 * @param cause
	 * @param masterLabel
	 * @param assistantLabel
	 * @author XiJie
	 * @time：2017年10月30日 下午3:04:42
	 */
	@Override
	public void saveText(String sentence, String callid, String cause, String masterLabel, String assistantLabel) {
		// 封装数据
		Text text = new Text();
		// 1. 如果name为空
		if (StringUtils.isEmpty(sentence)) {
			sentence = "sentence 传入的数据方式有误";
		}
		text.setName(sentence);
		
		// 2. 对callid进行非空判断
		if (StringUtils.isEmpty(callid)) {
			callid = "callid 传入的数据方式有误";
		}
		text.setCallid(callid);
		
		// 3. 对导致的原因cause进行保存
		text.setCause(cause);
		// 4. 对主标签进行保存
		text.setMasterLabel(masterLabel);
		// 5. 对副标签进行保存
		text.setAssistantLabel(assistantLabel);
		// 6. 创建当前时间
		long time = System.currentTimeMillis();
		Timestamp createTime = new Timestamp(time);
		text.setCreateTime(createTime);
		// 7. 进行数据库保存
		textMapper.insertSelective(text);
	}

	/**
	 * 说明：根据callid和主副标签来查询对应的文本
	 * @param callid
	 * @return
	 * @author XiJie
	 * @time：2017年11月1日 上午10:33:46
	 */
	@Override
	public int selectTextByCallIdAndMasterLabelAndAssistantLabel(String callid) {
		// 创建Example对象
		Example example = new Example(Text.class);
		// 通过Example来创建查询对象
		Criteria criteria = example.createCriteria();
		// 添加查询条件
		criteria.andEqualTo("callid", callid);
		criteria.andIsNull("masterLabel");
		criteria.andIsNull("assistantLabel");
		// 执行查询
		int count = textMapper.selectCountByExample(example);
		return count;
	}

	/**
	 * 说明：根据callid和主副标签来查询对应的文本
	 * @param string
	 * @param masterLabel
	 * @param assistantLabel
	 * @return
	 * @author XiJie
	 * @time：2017年11月1日 上午11:00:51
	 */
	@Override
	public int selectTextByCallIdAndMasterLabelAndAssistantLabel2(String callid, String masterLabel) {
		// 创建Example对象
		Example example = new Example(Text.class);
		// 通过Example来创建查询对象
		Criteria criteria = example.createCriteria();
		// 添加查询条件
		criteria.andEqualTo("callid", callid);
		criteria.andEqualTo("masterLabel", masterLabel);
		// 执行查询
		int count = textMapper.selectCountByExample(example);
		return count;
	}

	@Override
	public PageInfo<Text> queryTextByCondition(Text text, Integer pageNum,
			Integer pageSize) {
		// 开启分页助手
		PageHelper.startPage(pageNum, pageSize);
		// 调用数据数据层进行查询
		List<Text> list = textMapper.select(text);
		//下载语音
		list.forEach(item->{
			if(!new File(TARGETPATH+item.getCallid()+VOICETYPE).exists())
				getVoiceFile(item.getCallid());
		});
		// 封装分页信息
		PageInfo<Text> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public boolean updateText(Text text) {
		boolean flag = textMapper.updateByPrimaryKeySelective(text) == 1;
		return flag;
	}
	
	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：下载语音文件到本地服务器中
	 * @param callid
	 * @throws IOException 
	 */
	private void getVoiceFile(String callid){
		//创建本地进程
		try {
			String command = "scp -P "+SSHPORT+" "+NEWFILEPATH+callid+VOICETYPE+" "+TARGETPATH;
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("下载文件到本地出错");
		}
	}
}
