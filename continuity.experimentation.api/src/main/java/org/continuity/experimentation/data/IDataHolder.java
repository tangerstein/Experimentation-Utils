package org.continuity.experimentation.data;

/**
 * Common interface for holders of data to be exchanged between experiment actions.
 *
 * @author Henning Schulz
 *
 */
public interface IDataHolder<T> {

	/**
	 * To be called when the content of this holder has been set.
	 */
	void notifyWrite();

	/**
	 * To be called before the content of this holder is to be read.
	 *
	 * @throws IllegalStateException
	 *             If the content is not ready to be read.
	 */
	void notifyRead() throws IllegalStateException;

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

}
