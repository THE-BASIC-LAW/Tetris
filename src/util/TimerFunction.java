package util;

public class TimerFunction {
	/**
	 * �����߳�˯��ʱ��
	 */
	public  static long getSleepTimeByLevel(int level){
		long sleep = (-40*level + 740);
		sleep = sleep>100? sleep : 100;
		return sleep;
	}
}
