package it.algos.webbase.web.lib;

import java.lang.annotation.Annotation;

import javax.persistence.metamodel.Attribute;

import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;

public abstract class LibBean {

	public static Annotation[] getAnnotations(java.lang.reflect.Field field) {
		Annotation[] annotations = new Annotation[0];
		return annotations;
	}// end of static method

	public static Annotation[] getAnnotations(Object bean, Attribute attribute) {
		Annotation[] annotations = new Annotation[0];
		Class type = attribute.getJavaType();
		bean.getClass().getDeclaredAnnotations();
		java.lang.reflect.Field[] fields = bean.getClass().getFields();
		for (java.lang.reflect.Field field : fields) {
			field.getDeclaredAnnotations();
		}
		return annotations;
	}// end of static method

//	public static Annotation getAnnotation(Attribute attribute) {
//		Annotation annotation = null;
//		Annotation[] annotations = getAnnotations(attribute);
//		return annotation;
//	}

	/**
	 * Create field from property name
	 */
	public static Field createField(Attribute attribute) {
		DefaultFieldFactory factory = DefaultFieldFactory.get();
		Class javaType = attribute.getJavaType();
		Field field = factory.createFieldByPropertyType(javaType);
		return field;
	}// end of static method

	// /**
	// * Create field from attribute
	// */
	// public static Field createField(Attribute attribute){
	// return null;
	// }

}// end of abstract static class
