package com.example.administrator.wangshuobaselib;

import android.app.Activity;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

/**
 * 维护应用Activity栈，用来控制栈的大小，超出则finish进吧页之后的若干个页面，至小于限制为止 上限值在TbadkApp中设置，为0则不限制
 * 注意，这个行为在SDK中应该有所不同。
 */
public final class RRHActivityStack {
	private static ArrayList<SoftReference<Activity>> sActivityStack;
	private static RRHActivityStack sInstance;

	// Activity栈的MaxSize，为0表示不限制
	private int mActivityStackMaxSize = 0;

	private RRHActivityStack() {
		if (sActivityStack == null) {
			sActivityStack = new ArrayList<SoftReference<Activity>>(20);
		}
		// BdLog.addLogPackage("com.mofamulu.adp.base.BdActivityStack");
	}

	public static RRHActivityStack getInst() {
		if (sInstance == null) {
			sInstance = new RRHActivityStack();
		}
		return sInstance;
	}

	public int getSize() {
		return sActivityStack.size();
	}

	public void pushActivity(Activity activity) {
		// BdLog.d("Activity pushed: " + activity);
		if (activity != null) {
			sActivityStack.add(new SoftReference<Activity>(activity));
			checkAndMaintainActivityStack(mActivityStackMaxSize, true);
		}
	}

	public Activity popActivity() {
		int size = sActivityStack.size();
		if (size == 0) {
			return null;
		}
		SoftReference<Activity> sr = sActivityStack.remove(size - 1);
		if (sr == null) {
			return null;
		}
		Activity activity = sr.get();
		// BdLog.d("Activity popped: " + activity);
		return activity;
	}

	public Activity popActivity(int index) {
		int size = sActivityStack.size();
		if (size == 0) {
			return null;
		}
		if (index < 0 || index >= size) {
			return null;
		}
		SoftReference<Activity> sr = sActivityStack.remove(index);
		if (sr == null) {
			return null;
		}
		Activity activity = sr.get();
		// BdLog.d("Activity popped: " + activity);
		return activity;
	}

	public void popActivity(Activity activity) {
		if (activity != null) {
			int size = sActivityStack.size();
			if (size == 0) {
				return;
			}
			for (int i = size - 1; i >= 0; i--) {
				SoftReference<Activity> sr = sActivityStack.get(i);
				if (sr == null) {
					continue;
				}
				Activity item = sr.get();
				if (activity.equals(item)) {
					sActivityStack.remove(i);
					// BdLog.d("Activity popped: " + activity);
					return;
				}
			}
		}
	}

	public Activity currentActivity() {
		int size = sActivityStack.size();
		if (size == 0) {
			return null;
		}
		SoftReference<Activity> sr = sActivityStack.get(size - 1);
		if (sr == null) {
			return null;
		}
		Activity activity = sr.get();
		return activity;
	}

	/**
	 * 出去透明窗口等临时activity以外的当前Activity
	 */
	public Activity currentLiveActivity() {
		int size = sActivityStack.size();
		if (size == 0) {
			return null;
		}

		for (int i = size - 1; i >= 0; i--) {
			SoftReference<Activity> sr = sActivityStack.get(i);
			if (sr == null) {
				continue;
			}

			Activity activity = sr.get();

			if (activity == null) {
				continue;
			}

			if (activity.isFinishing()) {
				continue;
			}

			// 手势密码锁屏页面跳过
//			if (activity instanceof RRHBaseFragmentActivity
//					&& ((RRHBaseFragmentActivity) activity).isGestureLoginActivity()) {
//				continue;
//			}

			return activity;
		}

		return null;
	}

	public void setActivityStackMaxSize(int size) {
		// Not allow to be less than 10.
		if (size < 10 && size != 0) {
			return;
		}
		mActivityStackMaxSize = size;
	}

	public void releaseAllPossibleAcitivities() {
		this.checkAndMaintainActivityStack(3, true);
	}

	public void releaseAllWithoutCurrent(Activity activityToKeep) {
		if (sActivityStack == null) {
			return;
		}

		int total = RRHActivityStack.getInst().getSize();
		for (int i = 0; i < total; i++) {
			SoftReference<Activity> ref = sActivityStack.get(i);
			if (ref == null) {
				continue;
			}

			Activity a = ref.get();
			if (a == null) {
				continue;
			}

			if (!activityToKeep.getClass().isAssignableFrom(a.getClass())) {
				a.finish();
			}
		}

		pushActivity(activityToKeep);
	}

	public int getActivityStackMaxSize() {
		return mActivityStackMaxSize;
	}

	private void checkAndMaintainActivityStack(int activityStackMaxSize, boolean keepMaintab) {
		if (activityStackMaxSize == 0) {
			return;
		}

		int currentSize = RRHActivityStack.getInst().getSize();
		while (currentSize > activityStackMaxSize) {
			currentSize--;
			// 退出登录到FirstActivity需要finish所有
			Activity act = RRHActivityStack.getInst().popActivity(keepMaintab ? 1 : 0);
			if (act != null) {
				act.finish();
			}
		}
	}

	public boolean popList(Activity activity, String[] activityArray) {

		if (activity != null) {
			int size = sActivityStack.size();
			if (size == 0) {
				return false;
			}
			int index = -1;
			for (int i = size - 1; i >= 0; i--) {
				SoftReference<Activity> sr = sActivityStack.get(i);
				if (sr == null) {
					continue;
				}
				Activity item = sr.get();
				if (activity.equals(item)) {
					index = i;
				}
			}
			if (index != -1) {
				ArrayList<SoftReference<Activity>> activityList = new ArrayList<SoftReference<Activity>>(3);
				for (int j = 0; j < activityArray.length; j++) {
					SoftReference<Activity> sr = sActivityStack.get(index - j);
					if (sr == null){
						break;
					}
					Activity item = sr.get();
					if (item == null){
						break;
					}
					if (index - j > 0 && item.getClass().getName().equals(activityArray[j])) {
						activityList.add(sr);
					} else {
						return false;
					}
				}
				for (SoftReference<Activity> a: activityList){
					if (a == null) {
						continue;
					}
					if (a.get() == null){
						continue;
					}
					a.get().finish();
				}
				return true;

			}
		}
		return false;
	}

}
