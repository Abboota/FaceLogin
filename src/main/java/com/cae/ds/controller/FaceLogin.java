package com.cae.ds.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.aip.face.AipFace;

/**
 * @Title: getFace.java
 * @Description:人脸登录模块
 * @Company 电子科技大学自动化研究所
 * @author 杜松
 * @date 2017年11月24日 下午7:35:59
 * @version V1.0
 */
@Controller
public class FaceLogin {

	@RequestMapping("/login")
	public String getLogin() {
		return "login";
	}

	@RequestMapping("/facelogin")
	@ResponseBody
	public String faceLogin(@RequestParam("img") String imgstr) throws JSONException {
		// 处理图片流编码
		byte[] imgbytes = Base64.getDecoder().decode(imgstr);

		// 人脸识别开放接口(个人)
		AipFace face = new AipFace("10415883", "ASO8Z2Q0ViKoARtkB9NXDM5p", "zjQefzc1Mr3cmigbiKot7EjinzD3ViGD");

		// 设置网络连接参数
		face.setConnectionTimeoutInMillis(60000);
		face.setSocketTimeoutInMillis(60000);

		// 设置请求参数用户top数
		HashMap<String, Object> options = new HashMap<String, Object>(1);
		options.put("user_top_num", 1);

		// 设置请求参数用户组
		List<String> groupId = new LinkedList<String>();
		groupId.add("test");

		// 用户特征鉴定
		JSONObject result = face.identifyUser(groupId, imgbytes, options);
		System.out.println(result);
		boolean registerResult = result.toString().contains("error_msg");
		if (registerResult) {
			return "fail";
		} else {
			return result.toString();
		}
	}

}