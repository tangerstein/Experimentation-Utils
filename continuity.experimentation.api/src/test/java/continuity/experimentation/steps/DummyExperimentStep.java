package continuity.experimentation.steps;

import org.continuity.experimentation.IExperimentAction;
import org.continuity.experimentation.data.IDataHolder;

/**
 * @author Henning Schulz
 *
 */
public class DummyExperimentStep implements IExperimentAction {

	private IDataHolder<String> inputHolder;
	private IDataHolder<String> outputHolder;

	private String message;

	/**
	 *
	 */
	public DummyExperimentStep(IDataHolder<String> inputHolder, IDataHolder<String> outputHolder, String message) {
		this.inputHolder = inputHolder;
		this.outputHolder = outputHolder;
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		System.out.println("Input: " + inputHolder.get());
		outputHolder.set(message);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Write \"" + message + "\" to " + outputHolder;
	}

}
