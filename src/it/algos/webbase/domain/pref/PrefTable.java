package it.algos.webbase.domain.pref;

import com.vaadin.data.Property;
import com.vaadin.ui.TableFieldFactory;
import it.algos.webbase.multiazienda.ETable;
import it.algos.webbase.web.module.ModulePop;

/**
 * Created by gac on 30 lug 2016.
 * .
 */
public class PrefTable extends ETable {

    /**
     * Costruttore
     *
     * @param module di riferimento (obbligatorio)
     */
    public PrefTable(ModulePop module) {
        super(module);
    }// end of constructor


    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
        String testoVisualizzato = "";

        if (colId.equals(Pref_.value.getName())) {
//            PrefType tipo;
//            Object value = property.getValue();
//            byte[] bytes = new byte[0];
//
//            if (value instanceof byte[]) {
//                bytes = (byte[]) value;
//            }// end of if cycle
//
//            Pref pref = Pref.find((long) rowId);
//
//            if (pref != null) {
//                tipo = pref.getTipo();
//                testoVisualizzato = tipo.bytesToObject(bytes).toString();
//            }// end of if cycle

//            return testoVisualizzato;
            return Pref.find((long) rowId).getValore().toString();
        } else {
            return super.formatPropertyValue(rowId, colId, property);
        }// end of if/else cycle

    }// end of method

}// end of class
