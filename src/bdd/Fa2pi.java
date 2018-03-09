package bdd;

import java.sql.Timestamp;

/**
 * Classe décrivant la table fa2pi
 *
 * @author Thierry Baribaud
 * @version 0.20
 */
public class Fa2pi {

    private int a10num;

    /**
     * @param a10num
     */
    public void setA10num(int a10num) {
        this.a10num = a10num;
    }

    /**
     * @return a10num
     */
    public int getA10num() {
        return this.a10num;
    }

    private Timestamp a10credate;

    /**
     * @param a10credate
     */
    public void setA10credate(Timestamp a10credate) {
        this.a10credate = a10credate;
    }

    /**
     * @return a10credate
     */
    public Timestamp getA10credate() {
        return this.a10credate;
    }

    private int a10onum0;

    /**
     * @param a10onum0
     */
    public void setA10onum0(int a10onum0) {
        this.a10onum0 = a10onum0;
    }

    /**
     * @return a10onum0
     */
    public int getA10onum0() {
        return this.a10onum0;
    }

    private String a10laguid;

    /**
     * @param a10laguid
     */
    public void setA10laguid(String a10laguid) {
        this.a10laguid = (a10laguid != null) ? a10laguid.trim() : null;
    }

    /**
     * @return A10laguid
     */
    public String getA10laguid() {
        return this.a10laguid;
    }

    private int a10unum;

    /**
     * @param a10unum
     */
    public void setA10unum(int a10unum) {
        this.a10unum = a10unum;
    }

    /**
     * @return a10unum
     */
    public int getA10unum() {
        return this.a10unum;
    }

    private int a10a6num;

    /**
     * @param a10a6num
     */
    public void setA10a6num(int a10a6num) {
        this.a10a6num = a10a6num;
    }

    /**
     * @return a10a6num
     */
    public int getA10a6num() {
        return this.a10a6num;
    }

    private int a10tnum;

    /**
     * @param a10tnum
     */
    public void setA10tnum(int a10tnum) {
        this.a10tnum = a10tnum;
    }

    /**
     * @return a10tnum
     */
    public int getA10tnum() {
        return this.a10tnum;
    }

    private int a10onum;

    /**
     * @param a10onum
     */
    public void setA10onum(int a10onum) {
        this.a10onum = a10onum;
    }

    /**
     * @return a10onum
     */
    public int getA10onum() {
        return this.a10onum;
    }

    private int a10s3num;

    /**
     * @param a10s3num
     */
    public void setA10s3num(int a10s3num) {
        this.a10s3num = a10s3num;
    }

    /**
     * @return a10s3num
     */
    public int getA10s3num() {
        return this.a10s3num;
    }

    private int a10evttype;

    /**
     * @param a10evttype
     */
    public void setA10evttype(int a10evttype) {
        this.a10evttype = a10evttype;
    }

    /**
     * @return a10evttype
     */
    public int getA10evttype() {
        return this.a10evttype;
    }

    private String a10data;

    /**
     * @param a10data
     */
    public void setA10data(String a10data) {
        this.a10data = (a10data != null) ? a10data.trim() : null;
    }

    /**
     * @return A10data
     */
    public String getA10data() {
        return this.a10data;
    }

    private int a10status;

    /**
     * @param a10status
     */
    public void setA10status(int a10status) {
        this.a10status = a10status;
    }

    /**
     * @return a10status
     */
    public int getA10status() {
        return this.a10status;
    }

    private int a10nberr;

    /**
     * @param a10nberr
     */
    public void setA10nberr(int a10nberr) {
        this.a10nberr = a10nberr;
    }

    /**
     * @return a10nberr
     */
    public int getA10nberr() {
        return this.a10nberr;
    }

    private int a10size;

    /**
     * @param a10size
     */
    public void setA10size(int a10size) {
        this.a10size = a10size;
    }

    /**
     * @return a10size
     */
    public int getA10size() {
        return this.a10size;
    }

    private Timestamp a10update;

    /**
     * @param a10update
     */
    public void setA10update(Timestamp a10update) {
        this.a10update = a10update;
    }

    /**
     * @return a10update
     */
    public Timestamp getA10update() {
        return this.a10update;
    }

    @Override
    public String toString() {
        return "Fa2pi{" + "a10num=" + a10num
                + ", a10credate=" + a10credate
                + ", a10onum0=" + a10onum0
                + ", a10laguid=" + a10laguid
                + ", a10unum=" + a10unum
                + ", a10a6num=" + a10a6num
                + ", a10tnum=" + a10tnum
                + ", a10onum=" + a10onum
                + ", a10s3num=" + a10s3num
                + ", a10evttype=" + a10evttype
                + ", a10data=" + a10data
                + ", a10status=" + a10status
                + ", a10nberr=" + a10nberr
                + ", a10size=" + a10size
                + ", a10update=" + a10update + '}';
    }

}
