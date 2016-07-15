package bdd;

/**
 * Furgent est une classe d�crivant un op�rateur.
 *
 * @version Juillet 2016
 * @author Thierry Baribaud
 */
public class Foperat {

    /**
     * Identifiant unique de l'op�rateur.
     */
    private int onum;
    
    /**
     * Nom de l'op�rateur.
     */
    private String Oname;
    
    /**
     * Nom abr�g� de l'op�rateur.
     */
    private String Oabbname;
    
    /**
     * Num�ro de poste de l'op�rateur.
     * Les deux derniers digits seulement.
     */
    private int onumpabx;

    /**
     * @return onum l'identifiant de l'op�rateur.
     */
    public int getOnum() {
        return onum;
    }

    /**
     * @param onum d�finit l'identifiant de l'op�rateur.
     */
    public void setOnum(int onum) {
        this.onum = onum;
    }

    /**
     * @return Oname le nom de l'op�rateur.
     */
    public String getOname() {
        return Oname;
    }

    /**
     * @param Oname d�finit le nom de l'op�rateur.
     */
    public void setOname(String Oname) {
        this.Oname = (Oname != null) ? Oname.trim() : null;
    }

    /**
     * @return Oabbname le nom abr�g� de l'op�rateur.
     */
    public String getOabbname() {
        return Oabbname;
    }

    /**
     * @param Oabbname Oabbname d�finit le nom abr�g� de l'op�rateur.
     */
    public void setOabbname(String Oabbname) {
        this.Oabbname = (Oabbname != null) ? Oabbname.trim() : null;
    }

    /**
     * @return onumpabx le num�ro de poste de l'op�rateur.
     */
    public int getOnumpabx() {
        return onumpabx;
    }

    /**
     * @param onumpabx d�finit le num�ro de poste de l'op�rateur.
     */
    public void setOnumpabx(int onumpabx) {
        this.onumpabx = onumpabx;
    }

    @Override
    public String toString() {
        return this.getClass().getName()
                + " : {onum=" + onum
                + ", Oname=" + Oname
                + ", Oabbname=" + Oabbname
                + ", onumpabx=" + onumpabx
                + "}";
    }
}
