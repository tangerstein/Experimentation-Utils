package org.continuity.experimentation.data;

/**
 * Data holder only for strings. Appends new string to the stored one.
 *
 * @author Henning Schulz
 *
 */
public class AppendingStringHolder extends AbstractDataHolder<String> {

	private StringBuilder stringBuilder = new StringBuilder();

	public AppendingStringHolder(String name) {
		super(name, String.class);
	}

	public AppendingStringHolder(String name, String initialString) {
		super(name, String.class);
		stringBuilder.append(initialString);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getWithoutNotification() {
		return stringBuilder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setWithoutNotification(String data) {
		stringBuilder.append(data);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidate() {
		super.invalidate();
		stringBuilder = new StringBuilder();
	}

}
