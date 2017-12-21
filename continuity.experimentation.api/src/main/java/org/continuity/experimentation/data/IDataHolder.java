package org.continuity.experimentation.data;

/**
 * Common interface for holders of data to be exchanged between experiment actions.
 *
 * @author Henning Schulz
 *
 */
public interface IDataHolder<T> {

	/**
	 * Sets the content.
	 *
	 * @param data
	 *            Content to be set.
	 */
	void set(T data);

	/**
	 * Reads the content.
	 *
	 * @return Contained content.
	 */
	T get();

	/**
	 * Returns whether the data already has been set.
	 * 
	 * @return {@code true} if the data has been set or {@code false} otherwise.
	 */
	boolean isSet();

	/**
	 * Invalidates the stored data.
	 */
	void invalidate();

}
