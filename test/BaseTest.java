import it.algos.webbase.domain.company.BaseCompany;

import javax.persistence.EntityManager;
import java.util.HashMap;

/**
 * Created by gac on 12 set 2015.
 * .
 */
public abstract class BaseTest {

    // alcuni parametri utilizzati
    protected int numSorgente = 0;
    protected int numPrevisto = 0;
    protected int numOttenuto = 0;
    protected boolean boolPrevisto = false;
    protected boolean boolOttenuto = false;
    protected String sorgente = "";
    protected String previsto = "";
    protected String ottenuto = "";
    protected String tag = "";
    protected HashMap mappaTxt;
    protected HashMap mappaObj;
    protected HashMap mappaDB;
    protected String contenuto;
    protected BaseCompany company;

}// end of abstract class