package com.jsd.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 保存在手机里面的文件名
 * * @author sf
 * 2017/3/15
 */
public class SPUtils {

	public static final String FILE_NAME = "jsd_data";
	public static final String ACCOUNT_INFO = "Account_info";

	private static SharedPreferences saveInfo;
	private static Editor saveEditor;
	private static volatile SPUtils spUtils;
	private Context context;

	private SPUtils(Context context) {
		saveInfo = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		saveEditor = saveInfo.edit();
		this.context = context;
	}

	public static SPUtils getInstance(Context context) {
		if (spUtils == null) {
			synchronized (SPUtils.class) {
				if (spUtils == null) {
					spUtils = new SPUtils(context);
				}
			}
		}
		return spUtils;
	}

	public void putString(String key, String value) {
		saveEditor.putString(key, value);
		saveEditor.commit();
	}

	public String getString(String key) {
		return saveInfo.getString(key, "");
	}
	public String getString(String key,String def) {
		return saveInfo.getString(key, def);
	}
	public void putInt(String key, int value) {
		saveEditor.putInt(key, value);
		saveEditor.commit();
	}

	public int getInt(String key) {
		return saveInfo.getInt(key, 0);
	}

	public int getInt(String key, int defaultValue) {
		return saveInfo.getInt(key, defaultValue);
	}

	public void putBoolean(String key, boolean value) {
		saveEditor.putBoolean(key, value);
		saveEditor.commit();
	}

	public boolean getBoolean(String key) {
		return saveInfo.getBoolean(key, false);
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		return saveInfo.getBoolean(key, defaultValue);
	}
	
	/** 保存ArrayList */
	public boolean putArray(String keyName,List<String> values) {
		if (values == null || values.size() == 0)
			return false;
		int size = values.size();
		saveEditor.putInt(keyName + "_num", size); /* sKey is an array */

		for (int i = 0; i < size; i++) {
			saveEditor.remove(keyName + "_id_" + i);
			saveEditor.putString(keyName + "_id_" + i, values.get(i));
		}

		return saveEditor.commit();
	}
	
	/** 获取ArrayList */
	public List<String> getArray(String keyName) {
		List<String> sKey = new ArrayList<String>();
		int size = saveInfo.getInt(keyName + "_num", 0);

		for (int i = 0; i < size; i++) {
			sKey.add(saveInfo.getString(keyName + "_id_" + i, ""));
		}
		return sKey;
	}

	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param key
	 */
	public void remove(String key) {
		saveEditor.remove(key);
		saveEditor.commit();
	}

	/**
	 * 清除所有数据
	 * 
	 */
	public void clear() {
		saveEditor.clear();
		saveEditor.commit();
	}

	/**
	 * 查询某个key是否已经存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean contains(String key) {
		return saveInfo.contains(key);
	}

	/**
	 * 返回所有的键值对
	 * 
	 * @return
	 */
	public Map<String, ?> getAll() {
		return saveInfo.getAll();
	}

	/**
	 * 保存 账号信息
	 * */
	public void saveAccountKey(String mKey, String mValue) {
		SharedPreferences mSharePrefs = context.getSharedPreferences(
				ACCOUNT_INFO, Context.MODE_PRIVATE);
		Editor editor;
		if (mSharePrefs != null) {
			editor = mSharePrefs.edit();
			editor.putString(mKey, mValue);
			editor.commit();
		}
	}
	
	   public String getAccountKeyStringValue(String mKey, String mDefValue) {
	        String mStr = null;
	        SharedPreferences mSharePrefs = context.getSharedPreferences(ACCOUNT_INFO,Context.MODE_PRIVATE);
	        if (mSharePrefs != null) {
	            mStr = mSharePrefs.getString(mKey, mDefValue);
	        }
	        return mStr;
	    }
	   
		public void clearAccount(String key) {
			SharedPreferences mSharePrefs = context.getSharedPreferences(ACCOUNT_INFO,
					Context.MODE_PRIVATE);
			Editor editor;
			if (mSharePrefs != null) {
				editor = mSharePrefs.edit();
				editor.remove(key);
				editor.commit();
			}
		}
}