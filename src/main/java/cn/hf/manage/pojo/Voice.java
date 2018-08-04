package cn.hf.manage.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CreateUser： YeShengGuang
 * CreateTime：2017年10月31日
 * FileExplain:语音
 */
@Table(name="h_voice")
public class Voice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="voice_name")
	private String voiceName;
	
	@Column(name="voice_text")
	private  String voiceText;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVoiceName() {
		return voiceName;
	}
	public void setVoiceName(String voiceName) {
		this.voiceName = voiceName;
	}
	public String getVoiceText() {
		return voiceText;
	}
	public void setVoiceText(String voiceText) {
		this.voiceText = voiceText;
	}
	@Override
	public String toString() {
		return "Voice [id=" + id + ", voiceName=" + voiceName + ", voiceText=" + voiceText + "]";
	}
}
