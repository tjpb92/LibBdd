package bdd;

/**
 * Ftype est une classe d�crivant une raison d'appel.
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
     * Appelation externe de la raison d'appel (vu c�t� client).
     */
    private String Ttextname;

    /**
     * Nom de la raison d'appel (vu c�t� Anstel).
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
     * @param ttnum d�finit l'identifiant de la raison d'appel.
     */
    public void setTtnum(int ttnum) {
        this.ttnum = ttnum;
    }

    /**
     * @param ttunum d�finit l'identifiant du client (furgent).
     */
    public void setTtunum(int ttunum) {
        this.ttunum = ttunum;
    }

    /**
     * @param Ttypename d�finit le nom de la raison d'appel.
     */
    public void setTtypename(String Ttypename) {
        this.Ttypename = (Ttypename != null) ? Ttypename.trim() : null;
    }

    /**
     * @param Ttextname d�finit l'appellation client de la raison d'appel.
     */
    public void setTtextname(String Ttextname) {
        this.Ttextname = (Ttextname != null) ? Ttextname.trim() : null;
    }

    /**
     * @param ttype d�finit l'indice de la raison d'appel.
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
