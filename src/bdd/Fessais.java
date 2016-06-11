package bdd;

import java.sql.Timestamp;

/**
 * Fessais est une classe décrivant un essais du journal d'un ticket.
 * @version Juin 2016
 * @author Thierry Baribaud
 */
public class Fessais {
    
  /**
   * Identifiant de l'essai au journal. 
   */
    private int enumabs;
    
  /**
   * Identifiant de l'appel (fcalls/f99calls). 
   */
    private int ecnum;
    
  /**
   * Date de l'essai au journal. 
   */
    private Timestamp Edate;
    
  /**
   * Heure de l'essai au journal. 
   */
    private String Etime;
    
  /**
   * Identifiant du client (furgent). 
   */
    private int eunum;
    
  /**
   * Identifiant de l'intervenant (ftoubib). 
   */
    private int etnum;
    
  /**
   * Identifiant de l'opérateur (foperat). 
   */
    private int eonum;
    
  /**
   * Code de l'essai au journal. 
   */
    private int eresult;
    
  /**
   * Durée de l'essai au journal exprimée en seconde. 
   */
    private int eduration;
    
  /**
   * Essai de test OUI=1, NON=0. 
   */
    private int etest;
    
  /**
   * Essai interne. 
   */
    private int einternal;
    
  /**
   * Ancienne référence à la table des message (fmessage). 
   */
    private int em3num;
    
  /**
   * Identifiant d'un groupe d'essais (fegid). 
   */
    private int egid;
    
  /**
   * Message sur l'essai. 
   */
    private String Emessage;
    
  /**
   * Variable temporaire. 
   */
    private int eptr;

    /**
     * @return the enumabs
     */
    public int getEnumabs() {
        return enumabs;
    }

    /**
     * @param enumabs the enumabs to set
     */
    public void setEnumabs(int enumabs) {
        this.enumabs = enumabs;
    }

    /**
     * @return the ecnum
     */
    public int getEcnum() {
        return ecnum;
    }

    /**
     * @param ecnum the ecnum to set
     */
    public void setEcnum(int ecnum) {
        this.ecnum = ecnum;
    }

    /**
     * @return the Edate
     */
    public Timestamp getEdate() {
        return Edate;
    }

    /**
     * @param Edate the Edate to set
     */
    public void setEdate(Timestamp Edate) {
        this.Edate = Edate;
    }

    /**
     * @return the Etime
     */
    public String getEtime() {
        return Etime;
    }

    /**
     * @param Etime the Etime to set
     */
    public void setEtime(String Etime) {
        this.Etime = (Etime != null)?Etime.trim():null;
    }

    /**
     * @return the eunum
     */
    public int getEunum() {
        return eunum;
    }

    /**
     * @param eunum the eunum to set
     */
    public void setEunum(int eunum) {
        this.eunum = eunum;
    }

    /**
     * @return the etnum
     */
    public int getEtnum() {
        return etnum;
    }

    /**
     * @param etnum the etnum to set
     */
    public void setEtnum(int etnum) {
        this.etnum = etnum;
    }

    /**
     * @return the eonum
     */
    public int getEonum() {
        return eonum;
    }

    /**
     * @param eonum the eonum to set
     */
    public void setEonum(int eonum) {
        this.eonum = eonum;
    }

    /**
     * @return the eresult
     */
    public int getEresult() {
        return eresult;
    }

    /**
     * @param eresult the eresult to set
     */
    public void setEresult(int eresult) {
        this.eresult = eresult;
    }

    /**
     * @return the eduration
     */
    public int getEduration() {
        return eduration;
    }

    /**
     * @param eduration the eduration to set
     */
    public void setEduration(int eduration) {
        this.eduration = eduration;
    }

    /**
     * @return the etest
     */
    public int getEtest() {
        return etest;
    }

    /**
     * @param etest the etest to set
     */
    public void setEtest(int etest) {
        this.etest = etest;
    }

    /**
     * @return the einternal
     */
    public int getEinternal() {
        return einternal;
    }

    /**
     * @param einternal the einternal to set
     */
    public void setEinternal(int einternal) {
        this.einternal = einternal;
    }

    /**
     * @return the em3num
     */
    public int getEm3num() {
        return em3num;
    }

    /**
     * @param em3num the em3num to set
     */
    public void setEm3num(int em3num) {
        this.em3num = em3num;
    }

    /**
     * @return the egid
     */
    public int getEgid() {
        return egid;
    }

    /**
     * @param egid the egid to set
     */
    public void setEgid(int egid) {
        this.egid = egid;
    }

    /**
     * @return the Emessage
     */
    public String getEmessage() {
        return Emessage;
    }

    /**
     * @param Emessage the Emessage to set
     */
    public void setEmessage(String Emessage) {
        this.Emessage = (Emessage != null)?Emessage.trim():null;
    }

    /**
     * @return the eptr
     */
    public int getEptr() {
        return eptr;
    }

    /**
     * @param eptr the eptr to set
     */
    public void setEptr(int eptr) {
        this.eptr = eptr;
    }
    
}
