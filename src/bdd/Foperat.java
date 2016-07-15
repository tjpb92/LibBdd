package bdd;

/**
 * Furgent est une classe décrivant un opérateur.
 *
 * @version Juillet 2016
 * @author Thierry Baribaud
 */
public class Foperat {

    /**
     * Identifiant unique de l'opérateur.
     */
    private int onum;
    
    /**
     * Nom de l'opérateur.
     */
    private String Oname;
    
    /**
     * Nom abrégé de l'opérateur.
     */
    private String Oabbname;
    
    /**
     * Numéro de poste de l'opérateur.
     * Les deux derniers digits seulement.
     */
    private int onumpabx;

    /**
     * @return onum l'identifiant de l'opérateur.
     */
    public int getOnum() {
        return onum;
    }

    /**
     * @param onum définit l'identifiant de l'opérateur.
     */
    public void setOnum(int onum) {
        this.onum = onum;
    }

    /**
     * @return Oname le nom de l'opérateur.
     */
    public String getOname() {
        return Oname;
    }

    /**
     * @param Oname définit le nom de l'opérateur.
     */
    public void setOname(String Oname) {
        this.Oname = (Oname != null) ? Oname.trim() : null;
    }

    /**
     * @return Oabbname le nom abrégé de l'opérateur.
     */
    public String getOabbname() {
        return Oabbname;
    }

    /**
     * @param Oabbname Oabbname définit le nom abrégé de l'opérateur.
     */
    public void setOabbname(String Oabbname) {
        this.Oabbname = (Oabbname != null) ? Oabbname.trim() : null;
    }

    /**
     * @return onumpabx le numéro de poste de l'opérateur.
     */
    public int getOnumpabx() {
        return onumpabx;
    }

    /**
     * @param onumpabx définit le numéro de poste de l'opérateur.
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
