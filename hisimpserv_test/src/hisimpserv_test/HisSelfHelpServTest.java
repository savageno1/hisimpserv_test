package hisimpserv_test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class HisSelfHelpServTest {
	// 接口地址
	public static String STR_URL = "http://192.168.1.28:8084/hisimpserv/selfhelp"; //本机测试
	// 数据类型
	public static String STR_DATATYPE = "JSON";

	public static void main(String[] args) {
		//		System.out.println("sssssssss");
		// 读取报告范例
		RLASample();
		// 写入结果范例
		//WLRSample();
		// 写入结果范例带Base64位图片
		//WLRSampleWithBase64();
		//		try {
		//			String aa = "{\"param\":\"{\"param_data\":{\"tmh\":\"TEST00010\"},\"opt_type\":\"RLA\"}\",\"choscode_acpt\":\"460106004\",\"check_code\":\"15805CA8C364EA76E2757634F36A4556\"}";
		//			JSONObject jo = new JSONObject(aa);
		//			System.out.println(jo);
		//		} catch (JSONException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
	}

	/**
	 * 取送检申请资料
	 */
	private static void RLASample() {
		try {
			for (int i = 0; i < 3; i++) {
				System.out.println("--------------------R001 数据测试开始：---------------------");
				//业务参数
				JSONObject joParamData = new JSONObject();
				joParamData.put("yyrq", "20171009");
				// 传入参数
				StringBuilder sbParam = new StringBuilder();
				sbParam.append("choscode=").append("350481006").append("&");
				sbParam.append("param=").append(new JSONObject().put("opt_type", "R001").put("param_data", joParamData));//RLA读取条码 及 条码信息
				//调用接口
				String strRtn = HttpRequest.SendPost(STR_URL, sbParam.toString());
				//解析返回值
				String strRtnConvert = strRtn;
				Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
				Matcher matcher = pattern.matcher(strRtnConvert);
				char ch;
				while (matcher.find()) {
					ch = (char) Integer.parseInt(matcher.group(2), 16);
					strRtnConvert = strRtnConvert.replace(matcher.group(1), ch + "");
				}
				JSONObject joRtn = new JSONObject(strRtnConvert);
				System.out.println(joRtn.getString("rtn_code"));
				System.out.println(joRtn.getString("rtn_msg"));
				JSONArray jaRtnData = joRtn.getJSONArray("rtn_data");
				System.out.println(jaRtnData);
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
