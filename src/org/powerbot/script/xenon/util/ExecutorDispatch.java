package org.powerbot.script.xenon.util;

import java.util.concurrent.Callable;

/**
 * An event dispatcher.
 *
 * @param <T>
 * @author Paris
 */
public interface ExecutorDispatch<T> {

	public void submit(Runnable task);

	public void submit(Runnable task, T result);

	public void submit(Callable<T> task);

	/**
	 * Submit a task to run on the Swing event queue.
	 *
	 * @param task a {@code Runnable} task
	 */
	public void submitSwing(Runnable task);
}
