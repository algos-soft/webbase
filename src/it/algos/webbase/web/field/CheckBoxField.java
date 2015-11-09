package it.algos.webbase.web.field;

import com.vaadin.ui.CheckBox;

@SuppressWarnings("serial")
public class CheckBoxField extends CheckBox implements FieldInterface<Boolean> {

    /**
     * Creates a new checkbox.
     */
    public CheckBoxField() {
        super();
        init();
    }// end of constructor

    /**
     * Creates a new checkbox with a set caption.
     *
     * @param caption the Checkbox caption.
     */
    public CheckBoxField(String caption) {
        super(caption);
        init();
    }// end of constructor

    /**
     * Creates a new checkbox with a caption and a set initial state.
     *
     * @param caption      the caption of the checkbox
     * @param initialState the initial state of the checkbox
     */
    public CheckBoxField(String caption, boolean initialState) {
        this(caption);
        setValue(initialState);
    }// end of constructor

    private void init() {
        initField();
    }// end of method

    public void initField() {
        FieldUtil.initField(this);
    }// end of method

    public void setAlignment(FieldAlignment alignment) {
    }// end of method

}// end of field class
