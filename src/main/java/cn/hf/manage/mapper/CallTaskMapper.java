package cn.hf.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;

import cn.hf.manage.pojo.CallTask;

/**
 * 说明：呼叫任务数据层接口
 * @author XiJie
 * @version 1.0
 * @date 2017年10月23日
 */
public interface CallTaskMapper extends Mapper<CallTask>{
	int batchUpdate(@Param(value="callTask") CallTask callTask, @Param(value="ids") List<Integer> ids);
}
