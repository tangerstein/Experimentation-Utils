package org.continuity.experimentation.action;

import java.util.List;
import java.util.Random;

import org.continuity.experimentation.IExperimentAction;
import org.continuity.experimentation.data.IDataHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Selects a random element from an input list and stores it to an output data holder.
 *
 * @author Henning Schulz
 *
 */
public class RandomSelection<T> implements IExperimentAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(RandomSelection.class);

	private final IDataHolder<List<T>> inputHolder;
	private final IDataHolder<T> outputHolder;

	private final Random random = new Random();

	/**
	 * @param inputHolder
	 * @param outputHolder
	 */
	public RandomSelection(IDataHolder<List<T>> inputHolder, IDataHolder<T> outputHolder) {
		this.inputHolder = inputHolder;
		this.outputHolder = outputHolder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		int size = inputHolder.get().size();
		T selected = inputHolder.get().get(random.nextInt(size));
		outputHolder.set(selected);

		LOGGER.info("Selected {} from {} elements.", selected, size);
	}

}
