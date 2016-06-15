package bdd;

/**
 * Factivity est une classe d�crivant une activit� d'un intervenant.
 *
 * @version Juin 2016
 * @author Thierry Baribaud
 */
public class Factivity {

    private int a4num;
    private String A4name;
    private String A4abbname;

    /**
     * @return a4num l'identifiant d'une activit�.
     */
    public int getA4num() {
        return a4num;
    }

    /**
     * @param a4num d�finit l'identifiant d'une activit�.
     */
    public void setA4num(int a4num) {
        this.a4num = a4num;
    }

    /**
     * @return A4name le nom d'une activit�.
     */
    public String getA4name() {
        return A4name;
    }

    /**
     * @param A4name d�finit le nom d'une activit�.
     */
    public void setA4name(String A4name) {
        this.A4name = (A4name != null) ? A4name.trim() : null;
    }

    /**
     * @return A4abbname le nom abr�g� d'une activit�.
     */
    public String getA4abbname() {
        return A4abbname;
    }

    /**
     * @param A4abbname A4abbname d�finit le nom abr�g� d'une activit�.
     */
    public void setA4abbname(String A4abbname) {
        this.A4abbname = (A4abbname != null) ? A4abbname.trim() : null;
    }

}
