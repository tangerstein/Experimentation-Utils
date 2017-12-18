package org.continuity.experimentation.data;

/**
 * Abstract class providing management of write and read notifications.
 *
 * @author Henning Schulz
 *
 */
public abstract class AbstractDataHolder<T> implements IDataHolder<T> {

	private final String name;
	private Class<T> dataType;

	private boolean writeNotified = false;

	public AbstractDataHolder(String name, Class<T> dataType) {
		this.name = name;
		this.dataType = dataType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyWrite() {
		writeNotified = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyRead() throws IllegalStateException {
		if (!writeNotified) {
			throw new IllegalStateException(getClass().getSimpleName() + " '" + name + "' (holds " + dataType.getSimpleName() + ") is notified to be read before data has been written!");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " \"" + name + "\" holding " + dataType.getSimpleName();
	}

}
