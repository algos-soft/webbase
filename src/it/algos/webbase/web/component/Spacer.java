package it.algos.webbase.web.component;

import com.vaadin.ui.Label;

/**
 * An empty spacing component
 */
@SuppressWarnings("serial")
public class Spacer extends Label {

	/**
	 * @param width
	 *            the width in EM
	 * @param heigth
	 *            the height in EM
	 * @param unit
	 *            the unit (com.vaadin.server.Sizeable.Unit)
	 */
	public Spacer(int width, int heigth, Unit unit) {
		super();
		setWidth(width, unit);
		setHeight(heigth, unit);
	}
	
	/**
	 * @param size
	 *            the size in EM
	 */
	public Spacer(int size, Unit unit) {
		this(size, size, unit);
	}

	/**
	 * @param size
	 *            the size in EM
	 */
	public Spacer(int size) {
		this(size, Unit.PIXELS);
	}

	/**
	 */
	public Spacer() {
		this(20);
	}




}
