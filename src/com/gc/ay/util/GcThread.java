package com.gc.ay.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Leong
 * @��;�� 
 *      ���Activity/�߳̽���ʱ������android������ƣ�����߳�û���գ��������ڴ棬��Ȼ�̲߳������е����ڴ治������Ӱ��������ܣ���д���࣬
 *      ͨ���̳߳صĹر� �������������ڴ��л��߳�
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
	 * ѭ��ִ��
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
				// TODO ִ��runnable
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
				stopLooper();// ֱ��ѭ����=false���ж��߳�
			}
		};
		mExecutorService = Executors.newScheduledThreadPool(1);
	}

	/**
	 * ����ִ��
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
				stop();// ִ����runnable �͹ر��̳߳�
			}
		};
		createExcutor();
	}

	/**
	 * �����̳߳�
	 */
	private void createExcutor() {
		mExecutorService = Executors.newScheduledThreadPool(1);
	}

	/**
	 * ��ʼ
	 * 
	 * @see ���ڵ���ִ���̣߳���������������ʵ�������ٴ�ִ��
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
	 * ֹͣ
	 */
	public void stop() {
		if (!isRunning) {
			return;
		}
		if (isLooper) {// ��Ϊѭ���̣߳���ʹ�ر��̳߳�Ҳû�ã�ֻ������ѭ����־�˳�ѭ�����ٹر��̳߳�
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
		isLooper = true;// �û�true����ֹ�´�startʱ�����ٴ�ѭ��
	}

	public interface GcRunnable {

		public void run();
	}
}
