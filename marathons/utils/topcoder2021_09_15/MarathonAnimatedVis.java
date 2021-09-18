package marathons.utils.topcoder2021_09_15;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Base class for Topcoder Marathon testers with animation, i.e.
 * intermediate states are displayed in the visualizer.
 * <p>
 * Updates:
 * 2021/02/03 - Keep the delay setting after end(), to allow pauses after the solution is over,
 * in a manual mode or some kind of animation of the final state.
 */
public abstract class MarathonAnimatedVis extends MarathonVis {
	private int delay = -1;
	private final Object pauseLock = new Object();
	private boolean listenerInit;
	private boolean paused;
	private boolean keyPressed;

	public void setParameters(Parameters parameters) {
		super.setParameters(parameters);
		if (parameters.isDefined(Parameters.delay)) delay = parameters.getIntValue(Parameters.delay);
		if (parameters.isDefined(Parameters.startPaused)) paused = true;
	}

	protected final boolean hasDelay() {
		return delay > 0;
	}

	protected final int getDelay() {
		return delay;
	}

	protected void end() {
		synchronized (pauseLock) {
			keyPressed = true;
			paused = false;
			pauseLock.notifyAll();
		}
		super.end();
	}

	protected final void setDefaultDelay(int defaultDelay) {
		if (delay == -1) delay = defaultDelay;
	}

	protected void updateDelay() {
		if (!hasVis()) return;
		update();
		if (!hasDelay()) return;
		synchronized (updateLock) {
			if (!listenerInit) {
				listenerInit = true;
				frame.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						synchronized (pauseLock) {
							if (e.getKeyChar() == ' ') paused = !paused;
							keyPressed = true;
							pauseLock.notifyAll();
						}
					}
				});
			}
		}
		synchronized (pauseLock) {
			if (paused) {
				keyPressed = false;
				while (!keyPressed) {
					try {
						pauseLock.wait();
					} catch (InterruptedException e) {
					}
				}
			}
		}
		if (!paused) {
			try {
				Thread.sleep(delay);
			} catch (Exception e) {
			}
		}
	}
}
