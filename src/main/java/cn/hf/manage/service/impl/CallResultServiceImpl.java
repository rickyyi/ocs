package cn.hf.manage.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

import cn.hf.manage.form.CallResultForm;
import cn.hf.manage.mapper.AutoVoiceMapper;
import cn.hf.manage.mapper.CallResultMapper;
import cn.hf.manage.mapper.CallTaskMapper;
import cn.hf.manage.mapper.TextMapper;
import cn.hf.manage.mapper.VoiceMapper;
import cn.hf.manage.pojo.AutoVoice;
import cn.hf.manage.pojo.CallResult;
import cn.hf.manage.pojo.CallResultInfo;
import cn.hf.manage.pojo.CallTask;
import cn.hf.manage.pojo.Text;
import cn.hf.manage.pojo.Voice;
import cn.hf.manage.service.CallResultService;

/**
 * CreateUser： YeShengGuang CreateTime：2017年10月23日 FileExplain:呼叫结果功能业务层实现
 */
@Service
public class CallResultServiceImpl implements CallResultService {

	// 通话记录语音文件存放文件夹地址
	@Value("${VOICEPATH}")
	private String VOICEPATH;
	// 通话记录JSON数据文件地址
	@Value("${VOICETEXT}")
	private String VOICETEXT;
	//语音文件、文本格式类型
	@Value("${VOICETYPE}")
	private String VOICETYPE;
	@Value("${VOICETEXTTYPE}")
	private String VOICETEXTTYPE;
	//机器人开场白语音文件
	@Value("${PROLOGUE}")
	private String PROLOGUE;
	// Linux命令
	@Value("${NEWFILEPATH}")
	private String NEWFILEPATH;
	@Value("${SSHPORT}")
	private String SSHPORT;
	@Value("${TARGETPATH}")
	private String TARGETPATH;

	// 呼叫结果
	@Autowired
	private CallResultMapper mapper;

	// 命中标签
	@Autowired
	private TextMapper textMapper;

	// 呼叫任务
	@Autowired
	private CallTaskMapper callTaskMapper;

	// 语音文件
	@Autowired
	private AutoVoiceMapper autoVoiceMapper;

	// 语音数据
	@Autowired
	private VoiceMapper voiceMapper;

	// 日志对象
	Logger log = LoggerFactory.getLogger(CallResultServiceImpl.class);

	@Override
	public PageInfo<CallResult> queryCallResult(CallResult call, Integer pageNum, Integer pageSize) {
		// 使用分页助手进行分页并获得呼叫结果集数据
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<>(mapper.select(call == null ? new CallResult() : call));
	}

	@Override
	public PageInfo<CallResult> queryCallResultWhere(CallResultForm call) {
		// 使用分页助手进行分页并获得呼叫结果集数据
		PageHelper.startPage(call.getPageNum() + 1, call.getPageSize());
		Example e = new Example(CallResult.class);
		Criteria cri = e.createCriteria();
		// 追加查询条件
		if (!StringUtils.isEmpty(call.getTime_1()))
			cri.andGreaterThan("callTime", call.getTime_1());
		if (!StringUtils.isEmpty(call.getTime_2()))
			cri.andLessThan("callTime", call.getTime_2());
		if (!StringUtils.isEmpty(call.getDuration_1()))
			cri.andGreaterThan("communicateTime", call.getDuration_1());
		if (!StringUtils.isEmpty(call.getDuration_2()))
			cri.andLessThan("communicateTime", call.getDuration_2());
		if (!StringUtils.isEmpty(call.getCallLabel()))
			cri.andLike("callLabel", call.getCallLabel());
		if (!StringUtils.isEmpty(call.getCallId()))
			cri.andLike("callId", call.getCallId());
		if (!StringUtils.isEmpty(call.getTaskStatus()))
			cri.andEqualTo("taskStatus", call.getTaskStatus());
		if (!StringUtils.isEmpty(call.getCallResult()))
			cri.andEqualTo("callResult", call.getCallResult());
		return new PageInfo<>(mapper.selectByExample(e));
	}

	@Override
	public boolean saveCallResult(CallResult call) {
		return mapper.insert(call) > 0;
	}

	@Override
	public String getCallResult(String callid) {
		// 存储呼叫结果详情信息
		CallResultInfo call = new CallResultInfo();
		// 查找通话记录是否已经存为文本
		File file = new File(VOICETEXT + callid + VOICETEXTTYPE);
		if (!file.exists()) {
			// 到数据库查询通话记录
			getCallResultInfo(call, callid);
			// 将通话记录保存到本地
			saveCallResultInfo(file, call);
			//下载相对应的语音文件
			getVoiceFile(callid);
		} else {
			// 读取本地录音文件并封装到实体类
			call = getCallResultInfo(file);
		}
		return new Gson().toJson(call);
	}

	@Override
	public boolean delCallResult(CallResult call) {
		return mapper.delete(call) > 0;
	}

	@Override
	public boolean updateCallResult(String jsonStr) {
		boolean bol = false;
		
		// json不为空则把数据赋值到呼叫结果实体中
		if (!StringUtils.isEmpty(jsonStr)) {
			JSONArray jsons = new JSONObject(jsonStr).getJSONArray("callresult");
			String statusValue = "计划完成";
			for (int i = 0; i < jsons.length(); i++) {
				JSONObject jsonCall = jsons.getJSONObject(i);
				Integer taskId = jsonCall.getInt("taskid");
				CallResult call = new CallResult();
				call.setId(taskId);
				call.setTaskStatus(statusValue);
				String callbegin = jsonCall.has("callbegin")?jsonCall.getString("callbegin"):null;
				String callend = jsonCall.has("callend")?jsonCall.getString("callend"):null;
				String answertime = jsonCall.has("answertime")?jsonCall.getString("answertime"):null;
				call.setCallId(jsonCall.getString("callid"));
				//call.setCallResult(Integer.parseInt(jsonCall.getString("callstate")) == 0 ? "接通" : "未接通");
				if (!StringUtils.isEmpty(callbegin))
					call.setCallTime(Timestamp.valueOf(callbegin));
				if (!StringUtils.isEmpty(callend))
					call.setEndTime(Timestamp.valueOf(callend));
				if (!StringUtils.isEmpty(answertime))
					call.setAnswertime(Timestamp.valueOf(answertime));
				if (!StringUtils.isEmpty(callbegin) && !StringUtils.isEmpty(callend)) {
					Integer communicateTime = Integer
							.parseInt(((call.getEndTime().getTime() - call.getCallTime().getTime()) / 1000) + "");
					call.setCommunicateTime(communicateTime + "");
					// 设置意向
					//call.setPurpose(getPurpose(communicateTime, jsonCall.getString("callid")));
				}
				// 更新数据
				if (mapper.updateByPrimaryKeySelective(call) > 0 && updateCallTask(taskId, statusValue))
					bol = true;
				else
					bol = false;
			}
		}
		return bol;
	}

	/**
	 * CreateUser：YeShengGuang FileExplain：设置呼叫任务的状态
	 * 
	 * @param taskId
	 * @param statusValue
	 * @return
	 */
	private boolean updateCallTask(Integer taskId, String statusValue) {
		CallTask task = new CallTask();
		task.setId(taskId);
		task.setPreState(statusValue);
		return callTaskMapper.updateByPrimaryKeySelective(task) > 0;
	}

	/**
	 * CreateUser：YeShengGuang FileExplain：获取意向标签
	 * 
	 * @param communicateTime
	 * @param callId
	 * @return
	 */
	private String getPurpose(Integer communicateTime, String callId) {
		String label = null;
		if (!StringUtils.isEmpty(callId)) {
			// 存储语音名
			List<Object> voiceNames = new ArrayList<>();
			Example example = new Example(Text.class);
			Criteria and = example.createCriteria();
			and.andEqualTo("callid", callId);
			and.andIsNotNull("masterLabel");
			and.andIsNotNull("assistantLabel");
			textMapper.selectByExample(example).forEach(text -> {
				if (!voiceNames.contains(text.getCause()))
					voiceNames.add(text.getCause());
			});
			// 计算意向标签
			List<AutoVoice> autoVoices = getAutoVoice(voiceNames);
			// 是否有拒绝调戏等行为、命中的标签数量、是否有意向
			for (AutoVoice autoVoice : autoVoices) {
				if (autoVoice.getIntention() != null) {
					if (autoVoice.getIntention().toUpperCase().equals("A")) {
						label = "A";
						break;
					} else if (autoVoice.getIntention().toUpperCase().equals("B")) {
						label = "B";
						break;
					} else if (autoVoice.getIntention().toUpperCase().equals("C")) {
						label = "C";
						break;
					}
				}
			}
		}
		return label == null ? "D" : label;
	}

	/**
	 * CreateUser：YeShengGuang FileExplain：根据callid查询记录
	 * 
	 * @param callid
	 * @return
	 */
	private List<Text> getText(String callid) {
		Example example = new Example(Text.class);
		example.setOrderByClause("id asc");
		example.createCriteria().andEqualTo("callid", callid);
		return textMapper.selectByExample(example);
	}

	/**
	 * CreateUser：YeShengGuang FileExplain：根据语音名集合获取语音意向
	 * 
	 * @param voiceNames
	 * @return
	 */
	private List<AutoVoice> getAutoVoice(List<Object> voiceNames) {
		Example example = new Example(AutoVoice.class);
		example.createCriteria().andIn("name", voiceNames);
		return autoVoiceMapper.selectByExample(example);
	}

	/**
	 * CreateUser：YeShengGuang FileExplain：根据语音名集合获取语音话术
	 * 
	 * @param voiceNames
	 * @return
	 */
	private List<Voice> getVoice(List<String> voiceNames) {
		List<Voice> voices = new ArrayList<>();
		for (String name : voiceNames) {
			Voice voice = new Voice();
			voice.setVoiceName(name);
			// 获取语音文件文本
			List<Voice> li = voiceMapper.select(voice);
			if (li != null && li.size() > 0)
				voices.add(li.get(0));
			else
				voices.add(new Voice());
		}
		return voices;
	}

	/**
	 * CreateUser：YeShengGuang FileExplain：读取本地文件通话记录，封装到实体类中返回
	 * 
	 * @param call
	 * @param file
	 * @return
	 */
	private CallResultInfo getCallResultInfo(File file) {
		BufferedReader read = null;
		CallResultInfo call = null;
		try {
			// 读取文件
			read = new BufferedReader(new FileReader(file));
			String json = "";
			do {
				json += read.readLine();
			} while (read.read() != -1);
			// 对json进行过滤
			json = json.replace("\r", "");
			json = json.replace("\n", "");
			// 封装json到实体中
			ObjectMapper objectMapper = new ObjectMapper();
			call = objectMapper.readValue(json, CallResultInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("读取通话记录文件出错");
		} finally {
			try {
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("关闭流错误");
			}
		}
		return call;
	}

	/**
	 * CreateUser：YeShengGuang FileExplain：到数据库查询通话记录数据
	 * 
	 * @param call
	 * @param callid
	 */
	private void getCallResultInfo(CallResultInfo call, String callid) {
		List<Map<String, String>> liMap = new ArrayList<>();
		List<String> voiceNames = new ArrayList<>();
		voiceNames.add(PROLOGUE);
		// 获得通话记录
		for (Text text : getText(callid)) {
			HashMap<String, String> map = new HashMap<>();
			map.put("clientText", text.getName());
			liMap.add(map);
			voiceNames.add(text.getCause());
		}
		call.setClient(liMap);
		// 实例化存储机器人语音文本
		liMap = new ArrayList<>();
		List<Voice> voices = getVoice(voiceNames);
		for (Voice voice : voices) {
			HashMap<String, String> map = new HashMap<>();
			map.put("serviceText", voice.getVoiceText());
			liMap.add(map);
		}
		call.setService(liMap);
		// 获取全程通话语音
		call.setVoicePath(VOICEPATH + callid + VOICETYPE);
	}

	/**
	 * CreateUser：YeShengGuang FileExplain：将通话记录保存到本地
	 * 
	 * @param file
	 */
	private void saveCallResultInfo(File file, CallResultInfo call) {
		BufferedWriter write = null;
		try {
			// 写入文件对象
			write = new BufferedWriter(new FileWriter(file));
			write.write(new Gson().toJson(call));
		} catch (IOException e) {
			e.printStackTrace();
			log.error("写入通话记录到本地出错");
		} finally {
			try {
				write.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("关闭流错误");
			}
		}
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

	@Override
	public boolean updateCallResultByTaskId(Integer taskId, CallResult callResult) {
		//mapper.updateCallResultByTaskId(taskId, callResult);
		CallResult callResultFind = new CallResult();
		callResultFind.setTaskId(taskId);
		CallResult result = mapper.selectOne(callResultFind);
		mapper.updateByPrimaryKeySelective(callResult);
		return true;
	}

	@Override
	public boolean updateCallResultByCallId(Integer callId, CallResult callResult) {
		mapper.updateCallResultByCallId(callId+"", callResult);
		return true;
	}
}