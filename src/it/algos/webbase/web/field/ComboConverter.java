package it.algos.webbase.web.field;

/**
 * Copyright 2009-2013 Oy Vaadin Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractSelect;
import org.vaadin.addons.lazyquerycontainer.LazyEntityContainer;

import java.util.Locale;

public class ComboConverter<T> implements Converter<Object, T> {

    private Class entityClass;
    private final AbstractSelect select;

    public ComboConverter(AbstractSelect select, Class entityClass) {
        this.select = select;
        this.entityClass=entityClass;
    }

    @SuppressWarnings("unchecked")
    private LazyEntityContainer<T> getContainer() {
        return (LazyEntityContainer<T>) select.getContainerDataSource();
    }

    @Override
    public T convertToModel(Object value, Class<? extends T> targetType,
                            Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        if (value != select.getNullSelectionItemId()) {
            int a = 1;
            return null;
            //return getContainer().getEntityProvider().getEntity(getContainer(), value);
        } else {
            return null;
        }
    }

    @Override
    public Object convertToPresentation(T value, Class<? extends Object> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        if (value != null) {
            int a = 1;
            return null;
//            return getContainer().getEntityProvider().getIdentifier(value);
        }
        return select.getNullSelectionItemId();
    }

    @Override
    public Class<T> getModelType() {
        return entityClass;
    }

    @Override
    public Class<Object> getPresentationType() {
        return Object.class;
    }

}
