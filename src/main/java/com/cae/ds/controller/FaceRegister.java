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
import com.cae.ds.entity.User;

/**
 * @Title: getFace.java
 * @Description:人脸登录模块
 * @Company 电子科技大学自动化研究所
 * @author 杜松
 * @date 2017年11月24日 下午7:35:59
 * @version V1.0
 */
@Controller
public class FaceRegister {

	@RequestMapping("/register")
	public String getRegister() {
		return "register";
	}

	/**
	 * 
	 * @Description:
	 * @param imgstr
	 * @param str
	 * @return
	 * @throws JSONException
	 * @author 杜松
	 * @date 2017年11月26日 下午2:41:42
	 */
	@RequestMapping("/faceregister")
	@ResponseBody
	public String faceRegister(@RequestParam("img") String imgstr, @RequestParam("user") String str)
			throws JSONException {

		// 获取用户信息
		User user = stringToUser(str);

		// 处理图片流编码
		byte[] imgbytes = Base64.getDecoder().decode(imgstr);

		// 人脸识别开放接口(个人)
		AipFace face = new AipFace("10415883", "ASO8Z2Q0ViKoARtkB9NXDM5p", "zjQefzc1Mr3cmigbiKot7EjinzD3ViGD");

		// 设置网络连接参数
		face.setConnectionTimeoutInMillis(60000);
		face.setSocketTimeoutInMillis(60000);

		// 设置请求参数
		String uid = user.getTelNumber();

		// 用户信息设置
		HashMap<String, String> options = new HashMap<String, String>(1);
		List<String> groupId = new LinkedList<String>();
		groupId.add("test");

		// 注册信息替换
		options.put("action_type", "replace");

		// 用户信息注册
		JSONObject result = face.addUser(uid, str, groupId, imgbytes, options);
		System.out.println(result);
		boolean registerResult = result.toString().contains("error_msg");
		if (registerResult) {
			return "注册失败！";
		} else {
			return "success";
		}

	}

	/**
	 * 
	 * @Description:将序列化的字符串转化为对象
	 * @param str
	 *            序列化后的字符串
	 * @return 用户对象
	 * @throws JSONException
	 * @author 杜松
	 * @date 2017年11月26日 下午4:50:44
	 */
	public User stringToUser(String str) throws JSONException {
		User user = new User();
		// 将序列化字符串转化为json格式
		str = "{\"" + str + "\"}";
		str = str.replace("=", "\":\"");
		str = str.replace("&", "\",\"");
		// json转化为对象
		JSONObject jsonObject = new JSONObject(str);
		user.setTelNumber(jsonObject.getString("telNumber"));
		user.setUserName(jsonObject.getString("userName"));
		user.setAge(jsonObject.getString("age"));
		user.setSex(jsonObject.getString("sex"));
		return user;

	}
}
