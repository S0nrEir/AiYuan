package com.gc.ay.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Leong
 * @用途： 
 *      解决Activity/线程结束时，由于android缓存机制，造成线程没回收，缓存在内存，虽然线程不在运行但是内存不断增大影响程序性能，故写此类，
 *      通过线程池的关闭 ，来彻底销在内存中毁线程
 */
public class GcThread {
	private ExecutorService mExecutorService;
	private Runnable mRunnable;
	public GcRunnable mGcRunnable;
	public boolean isLooper;
	public int delay;
	private boolean isRunning;

	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * 循环执行
	 * 
	 * @param pRunnable
	 */
	public GcThread(GcRunnable pRunnable, int pDelay) {
		mGcRunnable = pRunnable;
		delay = pDelay;
		isLooper = true;
		mRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO 执行runnable
				isRunning = true;
				while (isLooper) {
					mGcRunnable.run();
					try {
						Thread.sleep(delay);
					} catch (Exception e) {
						// TODO
						e.printStackTrace();
					}
				}
				stopLooper();// 直到循环标=false才中断线程
			}
		};
		mExecutorService = Executors.newScheduledThreadPool(1);
	}

	/**
	 * 单次执行
	 * 
	 * @param pRunnable
	 */
	public GcThread(GcRunnable pRunnable) {
		mGcRunnable = pRunnable;
		mRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO
				isRunning = true;
				mGcRunnable.run();
				stop();// 执行完runnable 就关闭线程池
			}
		};
		createExcutor();
	}

	/**
	 * 创建线程池
	 */
	private void createExcutor() {
		mExecutorService = Executors.newScheduledThreadPool(1);
	}

	/**
	 * 开始
	 * 
	 * @see 对于单次执行线程，结束后无需重新实例化可再次执行
	 */
	public void start() {
		if (isRunning) {
			return;
		}
		if (mExecutorService.isShutdown()) {
			createExcutor();
		}
		mExecutorService.execute(mRunnable);
	}

	/**
	 * 停止
	 */
	public void stop() {
		if (!isRunning) {
			return;
		}
		if (isLooper) {// 若为循环线程，即使关闭线程池也没用，只能利用循环标志退出循环，再关闭线程池
			isLooper = false;
		} else {
			mExecutorService.shutdown();
			isRunning = false;
		}
	}

	public void shutDown() {
		isRunning = false;
		mExecutorService.shutdown();
	}

	private void stopLooper() {
		mExecutorService.shutdown();
		isRunning = false;
		isLooper = true;// 置回true，防止下次start时不能再次循环
	}

	public interface GcRunnable {

		public void run();
	}
}
