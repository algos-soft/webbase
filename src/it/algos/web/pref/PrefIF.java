package it.algos.web.pref;

import it.algos.web.pref.AbsPref.PrefType;

/**
 * Interface for Enums containing preferences.
 * <p>
 * All the preference enums should adhere to this interface.
 * */
public interface PrefIF {

	public String getCode();

	public PrefType getType();

	public Object getDefaultValue();

}
