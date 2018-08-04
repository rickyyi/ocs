package cn.hf.manage.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class OutBoundInstance {
	
	private static Logger log = Logger.getLogger(OutBoundInstance.class);
	
	private static Map<String, Map<String, Object>> outboundMap = new ConcurrentHashMap<String, Map<String, Object>>();
	
	public static String getInstanceUUID(){
		return UUID.randomUUID().toString();
	}
	
	public static void initOutBound(String outboundUUID) {
		outboundMap.put(outboundUUID, new HashMap<>());
	}
	
	public static Map<String, Object> getOutBound(String outboundUUID) {
		return outboundMap.get(outboundUUID);
	}
	
	public static void putOutBoundKey(String outboundUUID, String outboundKey, Object outboundValue) {
		Map<String, Object> map = outboundMap.get(outboundUUID);
		if (map == null) {
			log.info("暂无该通话");
			return;
		}
		map.put(outboundKey, outboundValue);
	}
	
	public static void clearOutBound(String outboundUUID) {
		log.info("****************" + outboundUUID + "****************");
		getOutBound(outboundUUID).forEach((k, v)->{
			log.info("K" + k + ":" + "V" + v);
		});
		log.info("****************" + outboundUUID + "****************");
		outboundMap.remove(outboundUUID);
	}

}
