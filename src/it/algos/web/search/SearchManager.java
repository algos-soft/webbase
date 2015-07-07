package it.algos.web.search;

import com.vaadin.ui.*;
import com.vaadin.ui.DateField;
import it.algos.web.dialog.ConfirmDialog;
import it.algos.web.dialog.DialogToolbar;
import it.algos.web.entity.BaseEntity;
import it.algos.web.field.*;
import it.algos.web.field.TextField;
import it.algos.web.form.AFormLayout;
import it.algos.web.lib.Lib;
import it.algos.web.module.ModulePop;
import it.algos.web.query.AQuery;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.persistence.metamodel.Attribute;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.SimpleStringFilter;

@SuppressWarnings("serial")
public class SearchManager extends ConfirmDialog {

	private ModulePop module;
	@SuppressWarnings("rawtypes")
	private LinkedHashMap<String, Field> fieldMap;

	/**
	 * Constructor
	 */
	public SearchManager() {
		this(null);
	}// end of constructor

	/**
	 * Constructor
	 * 
	 * @param module
	 *            the reference module
	 */
	public SearchManager(ModulePop module) {
		super(null);
		this.module = module;

		init();
	}// end of constructor

	/**
	 * Initialization
	 */
	@SuppressWarnings("rawtypes")
	protected void init() {
		this.setFieldMap(new LinkedHashMap<String, Field>());

		if (module != null) {
			setCaption(module.getTitoloSearch());
		}// end of if cycle

		// creates the form layout
		FormLayout layout = createFormLayout();

		// create the fields
		createFields(layout);

		// aggiunge i campi al layout
		addComponent(layout);
	}// end of method
	
	
	/**
	 * Creates the FormLayout used to display the fields
	 * <p>
	 * @return the FormLayout
	 * */
	protected FormLayout createFormLayout(){
		FormLayout layout = new AFormLayout();
		layout.setSpacing(true);
		return layout;
	}
	
	

	/**
	 * The component shown in the toolbar area.
	 */
	protected DialogToolbar createToolbarComponent() {
		return new SearchManagerToolbar();
	}// end of method

	/**
	 * Returns the toolbar casted as SearchManagerToolbar
	 * to access search options
	 */
	protected SearchManagerToolbar getSearchToolbar(){
		return(SearchManagerToolbar)getToolbar();
	}

	/**
	 * Return a JPA container composite Filter corresponding to the current search contitions
	 * 
	 * @return the JPAContainer Filter
	 */
	public Filter getFilter() {
		Filter outFilter = null;

		// create composite filter
		ArrayList<Filter> filters = createFilters();
		if (filters != null) {

			// removes null filters
			ArrayList<Filter> validFilters = new ArrayList<Filter>();
			for (Filter filter : filters) {
				if (filter != null) {
					validFilters.add(filter);
				}
			}

			// concatenates the filters with the And clause
			if (validFilters.size() > 0) {
				Filter[] aFilters = validFilters.toArray(new Filter[0]);
				outFilter = new And(aFilters);
			}
		}

		return outFilter;
	}// end of method

	/**
	 * Creates and adds the filters for each search field. Invoked before performing the search.
	 * 
	 * @return an array of filters which will be concatenated with the And clause
	 */
	public ArrayList<Filter> createFilters() {
		// ArrayList<Filter> filters = new ArrayList<Filter>();
		// filters.add(createStringFilter(new TextField("Abcd"), Sala_.nome, SearchType.CONTAINS));
		// filters.add(createStringFilter(new TextField("pippox"), Sala_.capienza, SearchType.MATCHES));
		// return filters;
		return null;
	}// end of method

	/**
	 * Create a filter for String searches.
	 * 
	 * @param attr
	 *            the StaticMetamodel attribute
	 */
	protected Filter createStringFilter(Attribute attr) {
		return createStringFilter(attr, SearchType.STARTS_WITH);
	}// end of method

	/**
	 * Create a filter for String searches using the STARTS_WITH search type.
	 * 
	 * @param field
	 *            the field containing the String value
	 * @param attr
	 *            the StaticMetamodel attribute
	 */
	protected Filter createStringFilter(Field field, Attribute attr) {
		return createStringFilter(field, attr, SearchType.STARTS_WITH);
	}// end of method

	/**
	 * Create a filter for String searches.
	 * 
	 * @param attr
	 *            the StaticMetamodel attribute
	 * @param type
	 *            the search type
	 */
	protected Filter createStringFilter(Attribute attr, SearchType type) {
		String key = attr.getName();
		Field field = getFieldMap().get(key);

		return createStringFilter(field, attr, type);
	}// end of method

	/**
	 * Create a filter for String searches.
	 * 
	 * @param field
	 *            the field containing the value
	 * @param attr
	 *            the StaticMetamodel attribute
	 * @param type
	 *            the search type
	 */
	protected Filter createStringFilter(Field field, Attribute attr, SearchType type) {
		Filter filter = null;
		Object value = field.getValue();
		if (value != null) {
			if (!value.equals("")) {
				switch (type) {
				case STARTS_WITH:
					filter = new SimpleStringFilter(attr.getName(), value.toString(), true, true);
					break;
				case CONTAINS:
					filter = new SimpleStringFilter(attr.getName(), value.toString(), true, false);
					break;
				case MATCHES:
					filter = new Compare.Equal(attr.getName(), value.toString());
					break;
				default:
					break;
				}// end of switch cycle
			}// end of if cycle
		}// end of if cycle

		return filter;
	}// end of method


	/**
	 * Create a filter for Boolean searches.
	 *
	 * @param attr
	 *            the StaticMetamodel attribute
	 */
	protected Filter createBoolFilter(Attribute attr) {
		String key = attr.getName();
		Field field = getFieldMap().get(key);
		return createBoolFilter(field, attr);
	}// end of method


	/**
	 * Create a filter for Boolean searches.
	 *
	 * @param attr
	 *            the StaticMetamodel attribute
	 *            @return the filter or null if no checkboxes selected
	 */
	protected Filter createBoolFilter(Field field, Attribute attr) {
		Filter filter = null;
		Object value = field.getValue();
		if(value!=null){
			Boolean bool = Lib.getBool(value);
			filter = new Compare.Equal(attr.getName(), bool);
		}
		return filter;
	}// end of method


	/**
	 * Create a filter for Integer searches.
	 * 
	 * @param field
	 *            the field containing the Integer value
	 * @param attr
	 *            the StaticMetamodel attribute
	 */
	protected Filter createIntegerFilter(Field field, Attribute attr) {
		Filter filter = null;
		Object value = field.getValue();
		int intVal = Lib.getInt(value);
		if (intVal != 0) {
			filter = new Compare.Equal(attr.getName(), Lib.getInt(value));
		}
		return filter;
	}// end of method

	/**
	 * Create a filter for Combo Popup searches.
	 * 
	 * @param attr
	 *            the StaticMetamodel attribute
	 */
	protected Filter createBeanFilter(Attribute attr) {
		String key = attr.getName();
		RelatedComboField field = (RelatedComboField) getFieldMap().get(key);

		return createBeanFilter(field, attr);
	}// end of method

	/**
	 * Create a filter for Combo Popup searches.
	 * 
	 * @param field
	 *            the RelatedComboField field
	 * @param attr
	 *            the StaticMetamodel attribute
	 */
	protected Filter createBeanFilter(RelatedComboField field, Attribute attr) {
		return createBeanFilter(field, attr.getName());
	}// end of method



	/**
	 * Create a filter for Combo Popup searches.
	 *
	 * @param field
	 *            the RelatedComboField
	 * @param attrName
	 *            the attribute name
	 */
	protected Filter createBeanFilter(RelatedComboField field, String attrName) {
		Filter filter = null;
		Object value = field.getValue();
		if (value != null) {
			BaseEntity bean = AQuery.queryById(field.getEntityClass(), (long) value);
			filter = new Compare.Equal(attrName, bean);
		}
		return filter;
	}// end of method


	/**
	 * Searches from the value retrieved from an Array.
	 * <p>
	 * It is assumed that the object returned by getValue() has a method named "getId()" 
	 * which returns an Integer. If not, null is returned.
	 */
	protected Filter createArrayFilter(ArrayComboField field, Attribute attr) {
		Filter filter = null;
		Object value = field.getValue();

		if (value != null) {
			Class clazz = value.getClass();
			Method method;
			try {
				method = clazz.getMethod("getId");
				Object result = method.invoke(value);
				int key = Lib.getInt(result);
				if (key != 0) {
					filter = new Compare.Equal(attr.getName(), key);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return filter;
	}// end of method

	/**
	 * Returns the combine search option selected, or null if none selected.
	 * 
	 * @return the combine search option
	 */
	public CombineSearchOptions getCombineOption() {
		return getSearchToolbar().getCombineOption();
	}// end of method

	/**
	 * Crea e aggiunge i campi.
	 * <p>
	 * Implementazione di default nella superclasse. <br>
	 * I campi vengono recuperati dal Modello. <br>
	 * I campi vengono creati del tipo grafico previsto nella Entity. <br>
	 * Se si vuole aggiungere un campo (solo nel search e non nel Modello), usare il metodo sovrascritto nella
	 * sottoclasse richiamando prima il metodo della superclasse.
	 * <p>
	 * Per sovrascrivere completamente questo metodo, creare i campi, aggiungerli alla mappa con addField() e
	 * aggiungerli al layout passato nei parametri.
	 * <code>
	 * Field field = new TextField("cognome");
	 * addField(Insegnante_.cognome.getName(), field);
	 * layout.addComponent(field);
	 * </code>
	 */
	@SuppressWarnings("rawtypes")
	protected void createFields(FormLayout layout) {
		Attribute<?, ?>[] attributes = null;
		Field field = null;

		if (module != null) {
			attributes = module.getFieldsSearch();
			if (attributes != null) {
				// create a field for each attribute
				// and add it to the UI
				for (Attribute<?, ?> attr : attributes) {
					field = creaField(attr);
					if (field != null) {
						addField(attr.getName(), field);
						layout.addComponent(field);
					}
				}
			}
		}

	}// end of method


	/**
	 * Crea un campo del tipo corrispondente all'attributo dato.
	 * I campi vengono creati del tipo grafico previsto nella Entity.
	 */
	@SuppressWarnings("rawtypes")
	protected Field creaField(Attribute attr) {
		Field field = null;
		Class clazz = null;
		String caption = "";

		if (attr != null) {
			clazz = attr.getJavaType();

			if (attr.isAssociation()) {
				field = createCombo(clazz);
			}// end of if cycle

			if (clazz.equals(String.class)) {
				field = new TextField();
			}// end of if cycle

			if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
				field = new YesNoField();
			}// end of if cycle

			if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
				field = new IntegerField();
			}// end of if cycle

			if (clazz.equals(BigDecimal.class)) {
				field = new DecimalField();
			}// end of if cycle

			if (clazz.equals(Date.class)) {
				field = new DateField();
			}// end of if cycle

			if (field == null && !clazz.isEnum()) {
				field = new TextField();
			}// end of if cycle
		}// end of if cycle

		caption = DefaultFieldFactory.createCaptionByPropertyId(attr.getName());
		if (field != null) {
			field.setCaption(caption);
		}// end of if cycle

		return field;
	}// end of method


	/**
	 * Creates a Combo field to search in associated fields.
	 * @param clazz the related class
	 * @return the combo field
	 */
	protected RelatedComboField createCombo(Class clazz){
		RelatedComboField field = new RelatedComboField(clazz);
		return field;
	}

	/**
	 * Aggiunge il campo alla mappa
	 * 
	 * @param key
	 *            - the key
	 * @param field
	 *            - the field
	 */
	@SuppressWarnings("rawtypes")
	protected void addField(String key, Field field) {
		LinkedHashMap<String, Field> fieldMap = getFieldMap();

		if (key != null && field != null && fieldMap != null) {
			fieldMap.put(key, field);
		}// end of if cycle
	}// end of method

	/**
	 * @return the fieldMap
	 */
	@SuppressWarnings("rawtypes")
	public LinkedHashMap<String, Field> getFieldMap() {
		return fieldMap;
	}

	/**
	 * @param fieldMap
	 *            the fieldMap to set
	 */
	@SuppressWarnings("rawtypes")
	public void setFieldMap(LinkedHashMap<String, Field> fieldMap) {
		this.fieldMap = fieldMap;
	}

	/**
	 * Retrieve a filed from the map by attribute
	 * @param attr the attribute
	 * @return the field
	 */
	public Field getField(Attribute attr){
		return getFieldMap().get(attr.getName());
	}

	@Override
	protected void onConfirm() {

		super.onConfirm();
	}

	/**
	 * Enum for search types
	 */
	public enum SearchType {
		STARTS_WITH, CONTAINS, MATCHES
	}

	/**
	 * Enum of available combine search options in the SearchManager dialog
	 */
	public enum CombineSearchOptions {
		addToList("Aggiungi alla lista"), removeFromList("Rimuovi dalla lista"), searchInList("Cerca nella lista");

		private String text;

		private CombineSearchOptions(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

	};

}// end of class
