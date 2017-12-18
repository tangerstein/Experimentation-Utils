package org.continuity.experimentation.data;

/**
 * Holder that simply stores the content and replaces it if {@link #set(Object)} is called a second
 * time.
 *
 * @author Henning Schulz
 *
 */
public class SimpleDataHolder<T> extends AbstractDataHolder<T> {

	private T data;

	public SimpleDataHolder(String name, Class<T> dataType) {
		super(name, dataType);
	}

	@SuppressWarnings("unchecked")
	public SimpleDataHolder(String name, T initialData) {
		super(name, (Class<T>) initialData.getClass());
		this.data = initialData;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Overwrites already stored data.
	 */
	@Override
	public void set(T data) {
		this.data = data;
	}

	@Override
	public T get() {
		return this.data;
	}

}
