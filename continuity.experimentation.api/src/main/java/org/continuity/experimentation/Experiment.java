package org.continuity.experimentation;

import org.continuity.experimentation.element.IExperimentElement;

/**
 * An Experiment holds a name and the first {@link IExperimentElement} to be executed. Each element
 * can, but need not, hold an {@link IExperimentAction}.
 *
 * @author Henning Schulz
 *
 */
public class Experiment {

	private final String name;

	private IExperimentElement first;

	public Experiment() {
		this("<unknown>");
	}

	public Experiment(String name) {
		this.name = name;
	}

	/**
	 * Executes the experiment.
	 *
	 */
	public void execute() {
		IExperimentElement current = first;

		while ((current != null) && !current.isEnd()) {
			if (current.hasAction()) {
				current.getAction().execute();
			}

			current = current.getNext();
		}
	}

	/**
	 * Sets {@link #first}.
	 *
	 * @param first
	 *            New value for {@link #first}
	 */
	public void setFirst(IExperimentElement first) {
		this.first = first;
	}

	/**
	 * Gets the number of experiment actions to be executed.
	 *
	 * @return The number of actions.
	 */
	public double getNumberOfActions() {
		return first.count();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Experiment \"" + name + "\" (" + getNumberOfActions() + " steps):\n" + first.toString();
	}

}
