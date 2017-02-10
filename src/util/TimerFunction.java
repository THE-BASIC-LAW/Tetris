package util;

public class TimerFunction {
	/**
	 * 计算线程睡眠时间
	 */
	public  static long getSleepTimeByLevel(int level){
		long sleep = (-40*level + 740);
		sleep = sleep>100? sleep : 100;
		return sleep;
	}
}
