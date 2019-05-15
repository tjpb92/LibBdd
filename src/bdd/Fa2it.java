package bdd;

import java.sql.Timestamp;

/**
 * Classe décrivant la table fa2it
 *
 * @author Thierry Baribaud
 * @version 0.27
 */
public class Fa2it {

    private int a11num;

    /**
     * @param a11num
     */
    public void setA11num(int a11num) {
        this.a11num = a11num;
    }

    /**
     * @return a11num
     */
    public int getA11num() {
        return this.a11num;
    }

    private Timestamp a11credate;

    /**
     * @param a11credate
     */
    public void setA11credate(Timestamp a11credate) {
        this.a11credate = a11credate;
    }

    /**
     * @return a11credate
     */
    public Timestamp getA11credate() {
        return this.a11credate;
    }

    private int a11onum0;

    /**
     * @param a11onum0
     */
    public void setA11onum0(int a11onum0) {
        this.a11onum0 = a11onum0;
    }

    /**
     * @return a11onum0
     */
    public int getA11onum0() {
        return this.a11onum0;
    }

    private String a11laguid;

    /**
     * @param a11laguid
     */
    public void setA11laguid(String a11laguid) {
        this.a11laguid = (a11laguid != null) ? a11laguid.trim() : null;
    }

    /**
     * @return A11laguid
     */
    public String getA11laguid() {
        return this.a11laguid;
    }

    private int a11evttype;

    /**
     * @param a11evttype
     */
    public void setA11evttype(int a11evttype) {
        this.a11evttype = a11evttype;
    }

    /**
     * @return a11evttype
     */
    public int getA11evttype() {
        return this.a11evttype;
    }

    private String a11data;

    /**
     * @param a11data
     */
    public void setA11data(String a11data) {
        this.a11data = (a11data != null) ? a11data.trim() : null;
    }

    /**
     * @return A11data
     */
    public String getA11data() {
        return this.a11data;
    }

    private int a11status;

    /**
     * @param a11status
     */
    public void setA11status(int a11status) {
        this.a11status = a11status;
    }

    /**
     * @return a11status
     */
    public int getA11status() {
        return this.a11status;
    }

    private int a11nberr;

    /**
     * @param a11nberr
     */
    public void setA11nberr(int a11nberr) {
        this.a11nberr = a11nberr;
    }

    /**
     * @return a11nberr
     */
    public int getA11nberr() {
        return this.a11nberr;
    }

    private int a11size;

    /**
     * @param a11size
     */
    public void setA11size(int a11size) {
        this.a11size = a11size;
    }

    /**
     * @return a11size
     */
    public int getA11size() {
        return this.a11size;
    }

    private Timestamp a11update;

    /**
     * @param a11update
     */
    public void setA11update(Timestamp a11update) {
        this.a11update = a11update;
    }

    /**
     * @return a11update
     */
    public Timestamp getA11update() {
        return this.a11update;
    }

    @Override
    public String toString() {
        return "Fa2it{" + "a11num=" + a11num
                + ", a11credate=" + a11credate
                + ", a11onum0=" + a11onum0
                + ", a11laguid=" + a11laguid
                + ", a11evttype=" + a11evttype
                + ", a11data=" + a11data
                + ", a11status=" + a11status
                + ", a11nberr=" + a11nberr
                + ", a11size=" + a11size
                + ", a11update=" + a11update + '}';
    }

}
