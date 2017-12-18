package org.continuity.experimentation.element;

/**
 * Loop in the experiment chain.
 *
 * @author Henning Schulz
 *
 */
public class LoopElement implements IExperimentElement {

	private IExperimentElement loopStart;

	private IExperimentElement afterLoop;

	private final int numIterations;

	private int currentIteration = 0;

	boolean counting = false;

	boolean convertingToString = false;

	public LoopElement(int numIterations) {
		this.numIterations = numIterations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasAction() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IExperimentElement getNext() {
		currentIteration++;

		if (currentIteration <= numIterations) {
			return loopStart;
		} else {
			return afterLoop;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNextOrFail(IExperimentElement next) throws UnsupportedOperationException {
		setAfterLoop(next);
	}

	/**
	 * Sets {@link #loopStart}.
	 *
	 * @param loopStart
	 *            New value for {@link #loopStart}
	 */
	public void setLoopStart(IExperimentElement loopStart) {
		this.loopStart = loopStart;
	}

	/**
	 * Sets {@link #afterLoop}.
	 *
	 * @param afterLoop
	 *            New value for {@link #afterLoop}
	 */
	public void setAfterLoop(IExperimentElement afterLoop) {
		this.afterLoop = afterLoop;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double count() {
		if (counting) {
			counting = false;
			return 0;
		} else {
			counting = true;
			return (loopStart.count() * numIterations) + (afterLoop == null ? 0 : afterLoop.count());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return toString("");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(String newLinePrefix) {
		StringBuilder builder = new StringBuilder();

		if (!convertingToString) {
			convertingToString = true;
			builder.append("LOOP (");
			builder.append(numIterations);
			builder.append(" iterations):\n");
			builder.append(newLinePrefix);
			builder.append(loopStart.toString(newLinePrefix));
		} else {
			convertingToString = false;
			builder.append("END-LOOP\n--> ");
			builder.append(afterLoop.toString(newLinePrefix));
		}



		return builder.toString();
	}

}
