package bdd;

/**
 * Fmenuit est une classe décrivant un item d'un menu.
 *
 * @version Juin 2016
 * @author Thierry Baribaud
 */
public class Fmenuit {

    /**
     * Identifiant de l'item d'un menu.
     */
    private int m6num;

    /**
     * Nom de l'item d'un menu (vu côté Anstel).
     */
    private String M6name;

    /**
     * Appelation externe de l'item d'un menu (vu côté client).
     */
    private String M6extname;

    /**
     * @return m6num l'identifiant de l'item du menu.
     */
    public int getM6num() {
        return m6num;
    }

    /**
     * @param m6num définit l'identifiant de l'item du menu.
     */
    public void setM6num(int m6num) {
        this.m6num = m6num;
    }

    /**
     * @return M6name le nom de l'item du menu.
     */
    public String getM6name() {
        return M6name;
    }

    /**
     * @param M6name définit le nom de l'item du menu.
     */
    public void setM6name(String M6name) {
        this.M6name = (M6name != null) ? M6name.trim() : null;
    }

    /**
     * @return M6extname l'appellation client de l'item du menu.
     */
    public String getM6extname() {
        return M6extname;
    }

    /**
     * @param M6extname M6extname définit l'appellation client de l'item du
     * menu.
     */
    public void setM6extname(String M6extname) {
        this.M6extname = (M6extname != null) ? M6extname.trim() : null;
    }

    @Override
    public String toString() {
        return this.getClass().getName()
                + " : {m6num=" + m6num
                + ", M6extname=" + M6extname
                + ", M6name=" + M6name
                + "}";
    }
}
