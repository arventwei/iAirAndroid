package com.txmcu.xiaoxin.config;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.app.Activity;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.txmcu.iair.activity.HomeManageActivity;
import com.txmcu.iair.activity.HomeModifyActivity;
import com.txmcu.iair.activity.MainActivity;
import com.txmcu.iair.adapter.City;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.adapter.MessageVo;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirConstants;
import com.txmcu.iair.common.iAirUtil;

/**
 * APP和服务器沟通的协议，采用HTTP POST + JSON格式交互。
 * 
 * @author Administrator
 *
 */
public class XinServerManager {

	static String TAG = "XinServerManager";

	public abstract interface onSuccess {

		// Method descriptor #4 ()V
		public abstract void run(JSONObject response) throws JSONException;
	}
	/**
	 * 创建一个新的HTTP连接
	 * @return
	 */
	static private AsyncHttpClient getHttpClient() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(10000);
		return client;
	}
	/**
	 * 请求家和城市列表，并更新主界面
	 */
	public static void requesthomelist(final Activity activity,final Boolean closeself) {
		requesthomelist(activity,closeself,null);
	}
	public static void requesthomelist(final Activity activity,final Boolean closeself,final onSuccess r) {
		
		final iAirApplication application = (iAirApplication)activity.getApplication();
		XinServerManager.getfirstpage_briefinfo(activity,application.getUserid(),new XinServerManager.onSuccess() {
			
			@Override
			public void run(JSONObject response) throws JSONException {
				iAirUtil.dismissDialog();
				application.homeList = XinServerManager.getHomeFromJson(activity,response.getJSONArray("home"));
				application.cityList = XinServerManager.getCityFromJson(response.getJSONArray("area"));
				
				// TODO Auto-generated method stub
				
				if (MainActivity.instance!=null) {
					MainActivity.instance.refreshlist();
				}
				if (HomeManageActivity.instance!=null) {
					HomeManageActivity.instance.refreshlist();
				}
				if (closeself) {
					activity.finish();
				}
				if (r!=null) {
					r.run(response);
				}
//				if (HomeModifyActivity.this!=null) {
//					HomeModifyActivity.this.finish();
//				}
				
			//	synchomebb();
				
			}
		});
	}

	/**
	 * 得到一个设备的详细信息，从服务器返回的数据中。
	 * @param jArr
	 * @return
	 */
	static public List<Device> getXiaoxinFromJson(JSONArray jArr) {
		List<Device> ret = new ArrayList<Device>();

		for (int i = 0; i < jArr.length(); i++) {

			Device device = new Device();
			JSONObject obj = null;
			try {
				obj = jArr.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			getSingleDeviceFromJson(device, obj);
			ret.add(device);
		}
		return ret;
	}

	/**
	 * 提取单个设备的详细信息，从服务器返回的数据中
	 * @param device
	 * @param obj
	 */
	public static void getSingleDeviceFromJson(Device device, JSONObject obj) {
		device.id = getJsonString(obj, "xiaoxinid");
		device.sn = getJsonString(obj, "sn");
		device.name = getJsonString(obj, "xiaoxinname");
		device.isVirtual = getJsonInt(obj, "virtual")==1;
		if (device.isVirtual) {
			device.vsn = device.sn;
		}
		device.share = getJsonInt(obj, "share")==1;
		device.refresh_interval = getJsonString(obj, "refresh_interval");
		device.status = getJsonString(obj, "status");
		device.temp = getJsonString(obj, "temp");
		device.humi = getJsonString(obj, "humi");
		device.pm25 = getJsonString(obj, "pm25");
		device.pa = getJsonString(obj, "pa");
		device.speed = getJsonInt(obj, "switch");
		device.switchOn = getJsonInt(obj, "switch");
		device.mode = getJsonString(obj, "mode");
		device.ontime = getJsonString(obj, "ontime");
		device.offtime = getJsonString(obj, "offtime");
		device.sortseq = getJsonInt(obj, "sortseq");
	}

	/**
	 * 得到家的列表信息，从服务器返回的数据中
	 * @param activity
	 * @param jArr
	 * @return
	 */
	static public List<Home> getHomeFromJson(Activity activity,JSONArray jArr) {
		List<Home> ret = new ArrayList<Home>();

		for (int i = 0; i < jArr.length(); i++) {

			Home home = new Home();
			JSONObject obj = null;
			try {
				obj = jArr.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			getSingleHomeFromJson(home, obj);

			try {
				JSONArray jArray = obj.getJSONArray("xiaoxin");
				home.xiaoxins = getXiaoxinFromJson(jArray);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				JSONArray jArray = obj.getJSONArray("notice");
				home.notices = getNoticeFromJson((iAirApplication)activity.getApplication(),jArray);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			ret.add(home);
		}
		return ret;
	}
/**
 * 得到单个家的信息，从服务器返回的数据中。
 * @param home
 * @param obj
 */
	public static void getSingleHomeFromJson(Home home, JSONObject obj) {
		home.homeid = getJsonString(obj, "homeid");
		home.homename = getJsonString(obj, "homename");
		home.share = getJsonInt(obj, "share")==1;
		home.refresh_interval = getJsonString(obj, "refresh_interval");
		home.status = getJsonString(obj, "status");
		home.temp = getJsonString(obj, "temp");
		home.humi = getJsonString(obj, "humi");
		home.pa = getJsonString(obj, "pa");
		home.pm25 = getJsonString(obj, "pm25");
		home.own  = getJsonString(obj, "own");
		home.ownernickname = getJsonString(obj, "ownernickname");
		home.sortseq = getJsonInt(obj, "sortseq");
	}
	/**
	 * 得到城市信息，从服务器返回的信息中
	 * @param jArr
	 * @return
	 */
	static public List<City> getCityFromJson(JSONArray jArr) {
		List<City> ret = new ArrayList<City>();

		for (int i = 0; i < jArr.length(); i++) {

			City city = new City("");
			JSONObject obj = null;
			try {
				obj = jArr.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			getSingleCityFromJson(city, obj);


			

			ret.add(city);
		}
		return ret;
	}
/**
 * 得到城市单个信息，从服务器返回的信息中
 * @param city
 * @param obj
 */
	public static void getSingleCityFromJson(City city, JSONObject obj) {
		city.areaId = getJsonString(obj, "area_id");
		city.name = getJsonString(obj, "area_name");
		city.temp = getJsonString(obj, "temp");
		city.temp_info = getJsonString(obj, "temp_info");
		city.humi = getJsonString(obj, "humi");
		city.wind_info = getJsonString(obj, "wind_info");
		city.wind_speed = getJsonString(obj, "wind_speed");
		city.today_car_limit = getJsonString(obj, "today_car_limit");
		city.tmr_car_limit = getJsonString(obj, "tmr_car_limit");
		city.aqi = getJsonString(obj, "aqi");
		city.pm25 = getJsonString(obj, "pm25");
		city.weather = getJsonString(obj, "weather");
		city.weather_level = getJsonString(obj, "weather_level");
		city.aqi_us = getJsonString(obj, "aqi_us");
		city.datestr = getJsonString(obj, "datestr");
		city.weekstr = getJsonString(obj, "weekstr");
		city.datecn = getJsonString(obj, "datecn");
		city.sortseq = getJsonInt(obj, "sortseq");
	}
/**
 * 得到公告信息列表，从服务器返回的信息中
 * @param application
 * @param jArr
 * @return
 */
	static public List<MessageVo> getNoticeFromJson(iAirApplication application,JSONArray jArr) {
		List<MessageVo> ret = new ArrayList<MessageVo>();

		for (int i = 0; i < jArr.length(); i++) {

			
			JSONObject obj = null;
			try {
				obj = jArr.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int dir = 0;
			String userid = getJsonString(obj, "userid");
			if (userid.equals(application.getUserid())) {
				dir =1;
			}
			MessageVo msg = new MessageVo(getJsonString(obj, "noticeid"),
					dir,
					getJsonString(obj,"content"),
					getJsonInt(obj,"time"),
					getJsonString(obj,"username")
					);
		


			

			ret.add(msg);
		}
		return ret;
	}

	/**
	 * 解析JSAON格式的浮点数
	 * @param obj
	 * @param key
	 * @return
	 */
	private static float getJsonDouble(JSONObject obj, String key) {
		try {
			float ret = (float) obj.getDouble(key);
			return ret;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
/**
 * 解析JSON格式的整数
 * @param obj
 * @param key
 * @return
 */
	private static int getJsonInt(JSONObject obj, String key) {
		try {
			int ret = (int) obj.getInt(key);
			return ret;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
/**
 * 解析JSON 格式的字符串
 * @param obj
 * @param key
 * @return
 */
	private static String getJsonString(JSONObject obj, String key) {
		try {
			String ret = obj.getString(key);
			return ret;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}


	/**
	 * 提交一个请求给服务器。这个是所有发送消息都调用的函数，
	 * 是最低层的调用函数。
	 * @param activity
	 * @param r
	 * @param post_params
	 * @param postUrlString
	 */
	private static void postHttpBase(final Activity activity,
			final onSuccess r, final RequestParams post_params,
			final String postUrlString) {

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {

					AsyncHttpClient client = getHttpClient();
					client.post(postUrlString, post_params,
							new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(String response) {
									System.out.println(response);
									//iAirUtil.toastMessage(activity, response);

									try {
										JSONObject jsonObject = new JSONObject(
												response);
										if (r != null) {
											r.run(jsonObject);
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();

									}

									// r.run(response);
								}
							});

				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			}
		});
	}

	// A1.1 【登录接口】
	// 1) 请求：http://112.124.58.144/android/login
	// 2) form数据：
	// oauth_name=xxx&oauth_access_token=xxx&oauth_openid=xxx&oauth_nickname=xxx
	// 3) 返回：
	// A. 成功：{"ret":"Ok","usertype":"new","userid":122}
	// B. 失败：{"ret":"Fail"}
	static public void login(final Activity activity, final String authType,
			final String token, final String openId, final String nickName,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("oauth_name", authType);
		post_params.put("oauth_access_token", token);
		post_params.put("oauth_openid", openId);
		post_params.put("oauth_nickname", nickName);
		// post_params.put("sn", sn);

		postHttpBase(activity, r, post_params, iAirConstants.login);

	}

	// A1.1.1 【请求家结构信息接口】如果usertype为new，获得家及家所属小新的结构数据
	// 1) 请求：http://112.124.58.144/android/gethome_structdata
	// 2) form数据：userid=xxx
	// 3) 返回：
	// A.成功：{"ret":"Ok","homecount":1,"home":[{"homeid":100,"homename":"北京的家","share":0,"refresh_interval":30,"status":0,"xiaoxincount":1,"xiaoxin":[{"xiaoxinid":201,"sn":"V_122_201403312053","xiaoxinname":"您的设备","virtual":1,"share":0,"refresh_interval":30,"status":0},{"xiaoxinid":202,......}]},{"homeid":101,......}]}
	// // 【说明】"share" 0:不共享，1：共享 ;
	// "refresh_interval":30(30秒刷新一次家数据)；"status":0(所属的所有小新都离线，一个小新都没有也是离线状态)，离线状态时，刷新设置不可用
	// 1：至少一个小新在线状态 // "virtual" 0:真实 1：虚拟；
	//
	// B.失败：{"ret":"Fail"}
	static public void gethome_structdata(final Activity activity,
			final String userid, final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		postHttpBase(activity, r, post_params, iAirConstants.gethome_structdata);
	}

	// 【注意】：1.1.2 和 1.1.3 可以在一个页面完成，”变更用户昵称，并设置所在城市“
	// A1.1.2 【更新用户昵称接口】更新用户昵称
	// 1) 请求：http://112.124.58.144/android/setuser_nickname
	// 2) form数据：userid=xxx&nickname=yyy
	// 3) 返回：
	// A.成功：{"ret":"Ok"}
	// B.失败：{"ret":"Fail"}
	static public void setuser_nickname(final Activity activity,
			final String userid, final String nickname, final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("nickname", nickname);
		postHttpBase(activity, r, post_params, iAirConstants.setuser_nickname);
	}

	// A1.1.3 【获得地区结构信息接口】获得城市列表结构数据
	// 1) 请求：http://112.124.58.144/android/getarea_structlist
	// 2) form数据：无
	// 3) 返回：
	// A.成功：{"ret":"Ok","areacount":87,"area":[{"area_id":"001","area_name":"北京","region_name":"北京","pinyin":"beijing"},{"area_id":"003","area_name":"石家庄","region_name":"河北","pinyin":"shijiazhuang"},{"area_id":002,......}]}
	// // 【说明】"area_id" 地区id； area_name：地区名称；region_name：省名称；pinyin：拼音
	// B.失败：{"ret":"Fail"}
	static public void getarea_structlist(final Activity activity,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		postHttpBase(activity, r, post_params, iAirConstants.getarea_structlist);
	}

	// A1.1.4
	// 【添加城市接口】（步骤：1.1.3选择一个地区之后，执行本步骤，并返回1.1.2页面）添加用户关注地区（在初期登录页面，可以只让用户选择一个地区（用户所在城市））
	// 1) 请求：http://112.124.58.144/android/binduser_area
	// 2) form数据：userid=xxx&areaid=yyy
	// 3) 返回：
	// A.成功：{"ret":"Ok"}
	// B.失败：{"ret":"Fail"}
	static public void binduser_area(final Activity activity,
			final String userid, final String areaid, final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("areaid", areaid);
		postHttpBase(activity, r, post_params, iAirConstants.binduser_area);
	}
//	A2.3 删除城市
//	A2.3.1 【解除绑定城市接口】在管理页面中，按下2秒钟（或点击右上角的小笔），进入编辑模式；按下某个城市图标右上角的小叉号，解除城市绑定
//	1) 请求：http://112.124.58.144/android/unbinduser_area
//	2) form数据：userid=xxx&areaid=yyy
//	3) 返回：
//	A.成功：{"ret":"Ok"}
//	B.失败：{"ret":"Fail"}
	static public void unbinduser_area(final Activity activity,
			final String userid, final String areaid, final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("areaid", areaid);
		postHttpBase(activity, r, post_params, iAirConstants.unbinduser_area);
	}

	// A1.1.5 【更新家基础信息接口】（该步骤为1.1.2的下一步）更新某个家的基础信息
	// 1) 请求：http://112.124.58.144/android/sethome_baseinfo
	// 2)
	// form数据：userid=xxx&homeid=xxx&homename=XXX&share=yyyy&refreshinterval=xxx
	// 3) 返回：
	// A.成功：{"ret":"Ok"}
	// B.失败：{"ret":"Fail"}
	static public void sethome_baseinfo(final Activity activity,
			final String userid, final String homeid, final String homename,
			final String share, final String refreshinterval, final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("homeid", homeid);
		post_params.put("homename", homename);
		post_params.put("share", share);
		post_params.put("refresh_interval", refreshinterval);
		postHttpBase(activity, r, post_params, iAirConstants.sethome_baseinfo);
	}

	// A1.1.6 【更新小新基础信息接口】（该步骤为1.1.5的下一步）更新小新的基础信息
	// 1) 请求：http://112.124.58.144/android/setxiaoxin_baseinfo
	// 2)
	// form数据：userid=xxx&xiaoxinid=xxx&xiaoxinname=XXX&share=yyyy&refreshinterval=xxx
	// 3) 返回：
	// A.成功：{"ret":"Ok"}
	// B.失败：{"ret":"Fail"}
	static public void setxiaoxin_baseinfo(final Activity activity,
			final String userid, final String xiaoxinid, final String xiaoxinname,
			final String share, final String refreshinterval, final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("xiaoxinid", xiaoxinid);
		post_params.put("xiaoxinname", xiaoxinname);
		post_params.put("share", share);
		post_params.put("refresh_interval", refreshinterval);
		postHttpBase(activity, r, post_params,
				iAirConstants.setxiaoxin_baseinfo);
	}

	// A1.2.1 【加载城市、家、告示简明信息接口】
	// 1) 请求：http://112.124.58.144/android/getfirstpage_briefinfo
	// 2) form数据：userid=xxx
	// 3) 返回：
	// A.成功：{"ret":"Ok","areacount":2,"area":[{"area_id":"001","area_name":"北京","temp":"25°","humi":"30","wind_info":"北风 微风","wind_speed":"1",
	// today_car_limit":"2|7","tmr_car_limit":"2|7","aqi":150, "pm25":90,"weather":"晴","weather_level":3},{"area_id":"003","area_name":"石家庄",…}],
	// "homecount":3,"home":[{"homeid":100,"homename":"北京的家",
	// "temp":"25°","humi":"30", "pm25":180, "pa":1,
	// "status":0,"notice_unread":5,
	// "noticecount"：1,"notice":[{"noticeid":11,"content":"sssss","userid":122,"time":"2014/3/8 14:12:10"},{"noticeid":12,......}]},{"homeid":101,......},{"homeid":102,......}]}
	// B.失败：{"ret":"Fail"}
	static public void getfirstpage_briefinfo(final Activity activity,
			final String userid,

			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);

		postHttpBase(activity, r, post_params,
				iAirConstants.getfirstpage_briefinfo);
	}
//	A6. 【获取家详细数据接口】家详细数据获取（家内所有小新的简明天气信息，告示栏的未读信息，告示栏的第一页信息）
//	1) 请求：http://112.124.58.144/android/gethome_detailweather
//	 2) form数据：userid=xx&homeid=xxx
//	 3) 返回：
//	A.成功：{"ret":"Ok","homeid":100,"homename":"北京的家", "temp":"25°","humi":"30", "pm25":180, "pa":1, "status":0,"own":1,"notice_unread":5, 
//	 "xiaoxincount"：2,"xiaoxin":[{"xiaoxinid":11,"xiaoxinname":"厨房","temp":"25°","humi":30,"pm25":158,"pa"=1,"status":0},{"xiaoxinid":12,......}]
//	 "noticecount"：3,"notice":[{"noticeid":11,"content":"sssss","userid":122,"time":"2014/3/8 14:12:10"},{"noticeid":12,......}]}
//	 B.失败：{"ret":"Fail"}
	static public void gethome_detailweather(final Activity activity,
			final String userid,
			final String homeid,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("homeid", homeid);
		postHttpBase(activity, r, post_params,
				iAirConstants.gethome_detailweather);
	}

//	 A7. 【获取小新详细数据接口】小新的详细数据获取
//	1) 请求：http://112.124.58.144/android/getxiaoxin_detailweather
//	 2) form数据：userid=xx&sn=xxx
//	 3) 返回：
//	A.成功：{"ret":"Ok","xiaoxinid":11,"xiaoxinname":"厨房","temp":"25°","humi":30,"pm25":158,"pa"=1,"status":0,"switch":1,"speed":5,"mode":2, "ontime":"18:00","offtime":"8:00"}
//	 B.失败：{"ret":"Fail"}
	static public void getxiaoxin_detailweather(final Activity activity,
			final String userid,
			final String sn,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("sn", sn);
		postHttpBase(activity, r, post_params,
				iAirConstants.getxiaoxin_detailweather);
	}
//	A1.1.7 【请求家本身的基础信息】关注其他人家的时候，获得指定家ID的基础信息
//	1) 请求：http://112.124.58.144/android/gethome_basedata 
//	2) form数据：userid=xxx&homeid=yyyy
//	3) 返回：
//	A.成功：{"ret":"Ok","homecount":1,"home":[{"homeid":100,"homename":"北京的家","share":0,"refresh_interval":30,"status":0,"own":1,"ownernickname":"fill西天取经"}]}
//	B.失败：{"ret":"Fail"}
	static public void gethome_basedata(final Activity activity,
			final String userid,
			final String homeid,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("homeid", homeid);
		postHttpBase(activity, r, post_params,
				iAirConstants.gethome_basedata);
	}

//	A3.2 【绑定已有的家接口】添加别人家的关注
//	1) 请求：http://112.124.58.144/android/binduser_home
//	2) form数据：userid=xxx&homeid=yyy&homenickname=yyy
//	3) 返回：
//	A.成功：{"ret":"Ok"}
//	B.失败：{"ret":"Fail"}
	static public void binduser_home(final Activity activity,
			final String userid,
			final String homeid,
			final String homenickname,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("homeid", homeid);
		post_params.put("homenickname", homenickname);
		postHttpBase(activity, r, post_params,
				iAirConstants.binduser_home);
	}
//	A3. 家 添加新家、添加别人家的关注、删除家
//	A3.1 【添加新家接口】添加新家
//	1) 请求：http://112.124.58.144/android/addhome
//	2) form数据：userid=xxx&homename=yyy&share=xxx&refreshinterval=yyy
//	3) 返回：
//	A.成功：{"ret":"Ok"}
//	B.失败：{"ret":"Fail"}
	static public void addhome(final Activity activity,
			final String userid,
			final String homename,
			final String share,
			final String refreshinterval,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("homename", homename);
		post_params.put("share", share);
		post_params.put("refresh_interval", refreshinterval);
		postHttpBase(activity, r, post_params,
				iAirConstants.addhome);
	}
//	A3.3 【解除绑定家接口】删除家绑定
//	1) 请求：http://112.124.58.144/android/unbinduser_home
//	2) form数据：userid=xxx&homeid=yyy
//	3) 返回：
//	A.成功：{"ret":"Ok"}
//	B.失败：{"ret":"Fail"}
	static public void unbinduser_home(final Activity activity,
			final String userid,
			final String homeid,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("homeid", homeid);
		postHttpBase(activity, r, post_params,
				iAirConstants.unbinduser_home);
	}
	//binduser_home
	
	//notice
//	A10.1. 【添加告示接口】添加自己告示
//	1) 请求：http://112.124.58.144/android/addhomenotice
//	2) form数据：userid=xx&homeid=xxx&content=XXX
//	3) 返回：
//	A.成功：{"ret":"Ok"}
//	B.失败：{"ret":"Fail"}
	static public void addhomenotice(final Activity activity,
			final String userid,
			final String homeid,
			final String content,
			
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("homeid", homeid);
		post_params.put("content", content);
		postHttpBase(activity, r, post_params,
				iAirConstants.addhomenotice);
	}
//	A1.1.8 【检查小新是否存在接口】（该步骤为1.1.5的下一步）更新小新的基础信息
//	1) 请求：http://112.124.58.144/android/checkxiaoxin_exist 
//	2) form数据：userid=xxx&sn=xxx
//	3) 返回：
//	A.成功：{"ret":"Ok"}
//	B.失败：{"ret":"Fail"}
	static public void checkxiaoxin_exist(final Activity activity,
			final String userid,
			final String sn,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("sn", sn);
		postHttpBase(activity, r, post_params,
				iAirConstants.checkxiaoxin_exist);
	}
	
//	A4.2 【解绑小新接口】删除小新绑定
//	1) 请求：http://112.124.58.144/android/unbindhome_xiaoxin
//	2) form数据：userid=xx&homeid=yyy&sn=xxxx
//	3) 返回：
//	A.成功：{"ret":"Ok"}
//	B.失败：{"ret":"Fail"}
	static public void unbindhome_xiaoxin(final Activity activity,
			final String userid,
			final String homeid,
			final String xiaoxinid,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("homeid", homeid);
		post_params.put("xiaoxinid", xiaoxinid);
		postHttpBase(activity, r, post_params,
				iAirConstants.unbindhome_xiaoxin);
	}
//	A8.1 【设置小新开关接口】切换开关
//	1) 请求：http://112.124.58.144/android/setxiaoxin_switch
//	2) form数据：userid=xx&sn=xxx&switch=xx
//	3) 返回：
//	A.成功：{"ret":"Ok"}
//	B.失败：{"ret":"Fail"}
	static public void setxiaoxin_switch(final Activity activity,
			final String userid,
			final String sn,
			final String swh,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("sn", sn);
		post_params.put("switch", swh);
		postHttpBase(activity, r, post_params,
				iAirConstants.setxiaoxin_switch);
	}
//	A8.2 【设置风速接口】设置风速
//	1) 请求：http://112.124.58.144/android/setxiaoxin_speed
//	2) form数据：userid=xx&sn=xxx&speed=xx
//	3) 返回：
//	A.成功：{"ret":"Ok"}
//	B.失败：{"ret":"Fail"}
	static public void setxiaoxin_speed(final Activity activity,
			final String userid,
			final String sn,
			final String speed,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("sn", sn);
		post_params.put("speed", speed);
		postHttpBase(activity, r, post_params,
				iAirConstants.setxiaoxin_speed);
	}
//	A2.1 【取得地区的天气简明信息接口】城市管理页面信息加载
//	1) 请求：http://112.124.58.144/android/getarealist_briefweather
//	2) form数据：userid=xxx
//	3) 返回：
//	A.成功：{"ret":"Ok","areacount":2,"area":[{"area_id":"001","area_name":"北京","temp_info":"5°~25°","weather":"雨","weather_level":"3", "sortseq":1},{"area_id":"003","area_name":"石家庄","temp_info":"3°~21°","weather":"多云转晴","weather_level":""}]}
//	B.失败：{"ret":"Fail"}
	static public void getarealist_briefweather(final Activity activity,
			final String userid,

			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);

		postHttpBase(activity, r, post_params,
				iAirConstants.getarealist_briefweather);
	} 
	
//	A5. 【获取城市详细数据接口】某一个城市详细数据的获取，包括 AQI，美国大使馆AQI，限行车牌号信息，等等信息
//	1) 请求：http://112.124.58.144/android/getarea_detailweather
//	2) form数据：userid=xx&areaid=xxx
//	3) 返回：
//	A.成功：{"ret":"Ok","areacount":1,"area":[{"area_id":"001","area_name":"北京","datestr":"4/1","weekstr":"周二","datecn":"三月初二","publish_time":"18:11","temp_info":"10~25°","humi":"30","wind_info":"北风 微风","wind_speed":"1", "today_car_limit":"1|6","tmr_car_limit":"2|7","aqi":150, "aqi_us":150, "pm25":90,"pm10":10,"no2":1,"so2":1,"o3":1,"co":1,"weather":"雨","weather_level":"3","temp":”22°"，"body_temp":”18°"，}]}
//	B.失败：{"ret":"Fail"}	
	static public void getarea_detailweather(final Activity activity,
			final String userid,
			final String areaid,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("areaid", areaid);
		postHttpBase(activity, r, post_params,
				iAirConstants.getarea_detailweather);
	} 
	
	static public void gethomenotice_bypage(final Activity activity,
			final String userid,
			final String homeid,
			final String pagesize,
			final String pageindex,
			final onSuccess r) {

		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("homeid", homeid);
		post_params.put("pagesize", pagesize);
		post_params.put("pageindex", pageindex);
		postHttpBase(activity, r, post_params,
				iAirConstants.gethomenotice_bypage);
	} 

	
}
