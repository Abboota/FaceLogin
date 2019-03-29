package com.cae.ds.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.aip.face.AipFace;

/**
 * @Title: UploadDetect.java
 * @Description:
 * @Company 电子科技大学自动化研究所
 * @author 杜松
 * @date 2017年11月22日 下午8:41:21
 * @version V1.0
 */
@RestController
public class FaceDetect {
	/**
	 * @Description:上传图片流识别人脸
	 * @param imgstr
	 *            上传的图片文件流
	 * @return 返回识别的结果
	 * @throws IOException
	 * @author 杜松
	 * @throws JSONException 
	 * @date 2017年11月26日 下午3:38:37
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/facedetect")
	@ResponseBody
	public String faceDetect(@RequestParam("img") String imgstr) throws IOException, JSONException {
		// 处理图片流编码
		byte[] imgbytes = Base64.getDecoder().decode(imgstr);

		// 人脸识别开放接口(个人)
		AipFace face = new AipFace("10415883", "ASO8Z2Q0ViKoARtkB9NXDM5p", "zjQefzc1Mr3cmigbiKot7EjinzD3ViGD");

		// 设置网络连接参数
		face.setConnectionTimeoutInMillis(60000);
		face.setSocketTimeoutInMillis(60000);

		// 设置请求参数
		HashMap options = new HashMap();
		options.put("face_fields", "age,beauty,expression,gender,glasses,race,qualities");
		options.put("max_face_num", 1);

		// 将上传的图片文件流进行识别
		JSONObject result = face.detect(imgbytes, options);
					
		return result.toString();
	}
}
