package org.continuity.experimentation.builder;

import org.continuity.experimentation.element.ConcurrentElement;
import org.continuity.experimentation.element.IExperimentElement;

/**
 * @author Henning Schulz
 *
 */
public class ConcurrentBuilder<C extends IExperimentBuilder> extends AbstractExperimentElementBuilder<C> implements IExperimentBuilder {

	private ConcurrentElement concurrentElement = new ConcurrentElement();

	public ConcurrentBuilder(C caller) {
		super(caller);
	}

	public ExperimentElementBuilder<ConcurrentBuilder<C>> thread() {
		return new ExperimentElementBuilder<>(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IExperimentElement getResult() {
		return concurrentElement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IExperimentElement getLast() {
		return concurrentElement.getJoin();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onReturn(IExperimentElement result, IExperimentElement last) {
		if (result != null) {
			concurrentElement.addThread(result);
			last.setNextOrFail(concurrentElement.getJoin());
		}
	}

}
