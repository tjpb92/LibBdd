package bdd;

/**
 * Furgent est une classe d�crivant un service d'urgence.
 *
 * @author Thierry Baribaud
 * @version 0.13
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
     * Nom abr�g� du service d'urgence.
     */
    private String Uabbname;

    /**
     * Mod�le de service d'urgence.
     * <ul>
     * <li>0 : ancien service d'urgence � partir de 1990 (utilisant
     * fmatrix),</li>
     * <li>1 : nouveau service d'urgence � partir de 1991 (utilisant
     * fsched),</li>
     * <li>5 : bailleur social � partir de 1995 (utilisant fsched).
     * </ul>
     */
    private int unewurg;

    /**
     * Niveau d'urgence pour ce service
     */
    private String UrgLevel;

    /**
     * Identifiant Performance Immo
     */
    private String Uuid;

    /**
     * @return unum l'identifiant du service d'urgence.
     */
    public int getUnum() {
        return unum;
    }

    /**
     * @param unum d�finit l'identifiant du service d'urgence.
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
     * @param Uname d�finit le nom du service d'urgence.
     */
    public void setUname(String Uname) {
        this.Uname = (Uname != null) ? Uname.trim() : null;
    }

    /**
     * @return Uabbname le nom abr�g� du service d'urgence.
     */
    public String getUabbname() {
        return Uabbname;
    }

    /**
     * @param Uabbname Uabbname d�finit le nom abr�g� du service d'urgence.
     */
    public void setUabbname(String Uabbname) {
        this.Uabbname = (Uabbname != null) ? Uabbname.trim() : null;
    }

    /**
     * @return unewurg le mod�le du service d'urgence.
     */
    public int getUnewurg() {
        return unewurg;
    }

    /**
     * @param unewurg d�finit le mod�le du service d'urgence.
     */
    public void setUnewurg(int unewurg) {
        this.unewurg = unewurg;
    }

    /**
     * @return le niveau d'urgence pour ce service
     */
    public String getUrgLevel() {
        return UrgLevel;
    }

    /**
     * @param UrgLevel d�finit le niveau d'urgence pour ce service
     */
    public void setUrgLevel(String UrgLevel) {
        this.UrgLevel = (UrgLevel != null) ? UrgLevel.trim() : null;
    }

    /**
     * @return l'identifiant Performance Immo
     */
    public String getUuid() {
        return Uuid;
    }

    /**
     * @param Uuid d�finit l'identifiant Performance Immo
     */
    public void setUuid(String Uuid) {
        this.Uuid = (Uuid != null) ? Uuid.trim() : null;
    }

    /**
     * Retourne le service d'urgence sous forme textuelle
     *
     * @return le service d'urgence sous forme textuelle
     */
    @Override
    public String toString() {
        return "Furgent:{unum=" + unum
                + ", Uuid=" + Uuid
                + ", Uname=" + Uname
                + ", Uabbname=" + Uabbname
                + ", unewurg=" + unewurg
                + "}";
    }

}
