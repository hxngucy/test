package com.cmos.ngbusicontrol.service.impl.busi.busi4429;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;


public class Test {

	public static void main(String[] args) {
		String jsonString = "{"+
			    "\"bean\": {},"+
			    "\"beans\": [],"+
			    "\"object\": {"+
			        "\"body.outData.outList\": [{"+
			                "\"acceptphone\":"+ "\"13596494264\","+
			                "\"callerno\":"+ "\"13596494264\","+
			                "\"logdate\":"+ "\"2018-29-01 10:37:42\","+
			                "\"operatetrace\":"+ "\"112\","+
			                "\"autoservicename\":"+ "\"套餐及上网流量查询\","+
			                "\"opresultdsc\":"+ "\"发送短信成功\"}]"+
			    "},"+
			    "\"rtnCode\":"+ "\"0\","+
			    "\"rtnMsg\":"+ "\"成功\","+
			"}";
		
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		String strObject = jsonObject.getString("object");
		JSONObject arrayObject = JSONObject.fromObject(strObject);
		String outList = arrayObject.getString("body.outData.outList");
		JSONArray array = JSONArray.fromObject(outList);
		List<Map<String,Object>> list = new ArrayList<>();
		for(int i=0;i<array.size();i++){
			Map<String,Object> map = new HashMap<>();
			JSONObject jObject = array.getJSONObject(i);
			map.put("acceptphone", jObject.getString("acceptphone"));
			map.put("callerno", jObject.getString("callerno"));
			map.put("logdate", jObject.getString("logdate"));
			map.put("operatetrace", jObject.getString("operatetrace"));
			map.put("autoservicename", jObject.getString("autoservicename"));
			map.put("opresultdsc", jObject.getString("opresultdsc"));
			list.add(map);
		}
		System.out.println(list.size());
		
		
		
		
		
		
		
		
//		JSONObject jsonObject = JSONObject.parseObject(jsonString); 
//		String strObject = jsonObject.getString("object");
//		JSONObject object = JSONObject.parseObject(strObject);
//		
//		String outList = object.getString("body.outData.outList");
//		JSONArray array = JSONArray.parseArray(outList);
//		List<Map<String,Object>> list = new ArrayList<>();
//		for(int i=0;i<array.size();i++){
//			Map<String,Object> map = new HashMap<>();
//			JSONObject jObject = array.getJSONObject(i);
//			map.put("acceptphone", jObject.getString("acceptphone"));
//			map.put("callerno", jObject.getString("callerno"));
//			map.put("logdate", jObject.getString("logdate"));
//			map.put("operatetrace", jObject.getString("operatetrace"));
//			map.put("autoservicename", jObject.getString("autoservicename"));
//			map.put("opresultdsc", jObject.getString("opresultdsc"));
//			list.add(map);
//		}
//		System.out.println(list.size());
//		String sss = "OPRESULTDSC";
//		System.out.println(sss.toLowerCase());
	}
	
		
}
