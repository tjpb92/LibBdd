package bdd;

/**
 * Ftype est une classe décrivant une raison d'appel.
 *
 * @version Juillet 2016
 * @author Thierry Baribaud
 */
public class Ftype {

    /**
     * Identifiant de la raison d'appel.
     */
    private int ttnum;

    /**
     * Identifiant du client (furgent).
     */
    private int ttunum;

    /**
     * Appelation externe de la raison d'appel (vu côté client).
     */
    private String Ttextname;

    /**
     * Nom de la raison d'appel (vu côté Anstel).
     */
    private String Ttypename;

    /**
     * Indice de la raison d'appel.
     */
    private int ttype;

    /**
     * @return ttnum l'identifiant de la raison d'appel.
     */
    public int getTtnum() {
        return ttnum;
    }

    /**
     * @return ttunum l'identifiant du client (furgent).
     */
    public int getTtunum() {
        return ttunum;
    }

    /**
     * @return Ttextname l'appellation client de la raison d'appel.
     */
    public String getTtextname() {
        return Ttextname;
    }

    /**
     * @return Ttypename le nom de la raison d'appel.
     */
    public String getTtypename() {
        return Ttypename;
    }

    /**
     * @return ttype l'indice de la raison d'appel.
     */
    public int getTtype() {
        return ttype;
    }

    /**
     * @param ttnum définit l'identifiant de la raison d'appel.
     */
    public void setTtnum(int ttnum) {
        this.ttnum = ttnum;
    }

    /**
     * @param ttunum définit l'identifiant du client (furgent).
     */
    public void setTtunum(int ttunum) {
        this.ttunum = ttunum;
    }

    /**
     * @param Ttypename définit le nom de la raison d'appel.
     */
    public void setTtypename(String Ttypename) {
        this.Ttypename = (Ttypename != null) ? Ttypename.trim() : null;
    }

    /**
     * @param Ttextname définit l'appellation client de la raison d'appel.
     */
    public void setTtextname(String Ttextname) {
        this.Ttextname = (Ttextname != null) ? Ttextname.trim() : null;
    }

    /**
     * @param ttype définit l'indice de la raison d'appel.
     */
    public void setTtype(int ttype) {
        this.ttype = ttype;
    }

    @Override
    public String toString() {
        return this.getClass().getName()
                + " : {ttnum=" + ttnum
                + ", ttunum=" + ttunum
                + ", Ttextname=" + Ttextname
                + ", Ttypename=" + Ttypename
                + ", ttype=" + ttype
                + "}";
    }
}
