package bdd;

/**
 * Furgent est une classe décrivant un service d'urgence.
 *
 * @version Juin 2016
 * @author Thierry Baribaud
 */
public class Furgent {

    /**
     * Identifiant unique du service d'urgence.
     */
    private int unum;
    
    /**
     * Nom du service d'urgence.
     */
    private String Uname;
    
    /**
     * Nom abrégé du service d'urgence.
     */
    private String Uabbname;
    
    /**
     * Modèle de service d'urgence.
     * <ul>
     * <li>0 : ancien service d'urgence à partir de 1990 (utilisant fmatrix),</li>
     * <li>1 : nouveau service d'urgence à partir de 1991 (utilisant fsched),</li>
     * <li>5 : bailleur social à partir de 1995 (utilisant fsched).
     * </ul>
     */
    private int unewurg;

    /**
     * @return unum l'identifiant du service d'urgence.
     */
    public int getUnum() {
        return unum;
    }

    /**
     * @param unum définit l'identifiant du service d'urgence.
     */
    public void setUnum(int unum) {
        this.unum = unum;
    }

    /**
     * @return Uname le nom du service d'urgence.
     */
    public String getUname() {
        return Uname;
    }

    /**
     * @param Uname définit le nom du service d'urgence.
     */
    public void setUname(String Uname) {
        this.Uname = (Uname != null) ? Uname.trim() : null;
    }

    /**
     * @return Uabbname le nom abrégé du service d'urgence.
     */
    public String getUabbname() {
        return Uabbname;
    }

    /**
     * @param Uabbname Uabbname définit le nom abrégé du service d'urgence.
     */
    public void setUabbname(String Uabbname) {
        this.Uabbname = (Uabbname != null) ? Uabbname.trim() : null;
    }

    /**
     * @return unewurg le modèle du service d'urgence.
     */
    public int getUnewurg() {
        return unewurg;
    }

    /**
     * @param unewurg définit le modèle du service d'urgence.
     */
    public void setUnewurg(int unewurg) {
        this.unewurg = unewurg;
    }

    @Override
    public String toString() {
        return this.getClass().getName()
                + " : {unum=" + unum
                + ", Uname=" + Uname
                + ", Uabbname=" + Uabbname
                + ", unewurg=" + unewurg
                + "}";
    }
}
