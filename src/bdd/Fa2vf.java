package bdd;

import java.sql.Timestamp;

/**
 * Classe décrivant la table fa2vf
 *
 * @author Thierry Baribaud
 * @version 0.32
 */
public class Fa2vf {

    private int a12num;

    /**
     * @param a12num
     */
    public void setA12num(int a12num) {
        this.a12num = a12num;
    }

    /**
     * @return a12num
     */
    public int getA12num() {
        return this.a12num;
    }

    private Timestamp a12credate;

    /**
     * @param a12credate
     */
    public void setA12credate(Timestamp a12credate) {
        this.a12credate = a12credate;
    }

    /**
     * @return a12credate
     */
    public Timestamp getA12credate() {
        return this.a12credate;
    }

    private int a12onum0;

    /**
     * @param a12onum0
     */
    public void setA12onum0(int a12onum0) {
        this.a12onum0 = a12onum0;
    }

    /**
     * @return a12onum0
     */
    public int getA12onum0() {
        return this.a12onum0;
    }

    private String a12laguid;

    /**
     * @param a12laguid
     */
    public void setA12laguid(String a12laguid) {
        this.a12laguid = (a12laguid != null) ? a12laguid.trim() : null;
    }

    /**
     * @return A12laguid
     */
    public String getA12laguid() {
        return this.a12laguid;
    }

    private int a12evttype;

    /**
     * @param a12evttype
     */
    public void setA12evttype(int a12evttype) {
        this.a12evttype = a12evttype;
    }

    /**
     * @return a12evttype
     */
    public int getA12evttype() {
        return this.a12evttype;
    }

    private String a12data;

    /**
     * @param a12data
     */
    public void setA12data(String a12data) {
        this.a12data = (a12data != null) ? a12data.trim() : null;
    }

    /**
     * @return A12data
     */
    public String getA12data() {
        return this.a12data;
    }

    private int a12status;

    /**
     * @param a12status
     */
    public void setA12status(int a12status) {
        this.a12status = a12status;
    }

    /**
     * @return a12status
     */
    public int getA12status() {
        return this.a12status;
    }

    private int a12nberr;

    /**
     * @param a12nberr
     */
    public void setA12nberr(int a12nberr) {
        this.a12nberr = a12nberr;
    }

    /**
     * @return a12nberr
     */
    public int getA12nberr() {
        return this.a12nberr;
    }

    private int a12size;

    /**
     * @param a12size
     */
    public void setA12size(int a12size) {
        this.a12size = a12size;
    }

    /**
     * @return a12size
     */
    public int getA12size() {
        return this.a12size;
    }

    private Timestamp a12update;

    /**
     * @param a12update
     */
    public void setA12update(Timestamp a12update) {
        this.a12update = a12update;
    }

    /**
     * @return a12update
     */
    public Timestamp getA12update() {
        return this.a12update;
    }

    @Override
    public String toString() {
        return "Fa2it{" + "a12num=" + a12num
                + ", a12credate=" + a12credate
                + ", a12onum0=" + a12onum0
                + ", a12laguid=" + a12laguid
                + ", a12evttype=" + a12evttype
                + ", a12data=" + a12data
                + ", a12status=" + a12status
                + ", a12nberr=" + a12nberr
                + ", a12size=" + a12size
                + ", a12update=" + a12update + '}';
    }

}
