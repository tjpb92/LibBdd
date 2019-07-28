package bdd;

/**
 * Classe décrivant la table fsite
 *
 * @author Thierry Baribaud
 * @version 0.28
 */
public class Fsite {

    private int s3num;

    /**
     * @param s3num
     */
    public void setS3num(int s3num) {
        this.s3num = s3num;
    }

    /**
     * @return s3num
     */
    public int getS3num() {
        return this.s3num;
    }

    private int s3onum;

    /**
     * @param s3onum
     */
    public void setS3onum(int s3onum) {
        this.s3onum = s3onum;
    }

    /**
     * @return s3onum
     */
    public int getS3onum() {
        return this.s3onum;
    }

    private String s3number;

    /**
     * @param s3number
     */
    public void setS3number(String s3number) {
        this.s3number = (s3number != null) ? s3number.trim() : null;
    }

    /**
     * @return S3number
     */
    public String getS3number() {
        return this.s3number;
    }

    private int s3unum;

    /**
     * @param s3unum
     */
    public void setS3unum(int s3unum) {
        this.s3unum = s3unum;
    }

    /**
     * @return s3unum
     */
    public int getS3unum() {
        return this.s3unum;
    }

    private String s3number2;

    /**
     * @param s3number2
     */
    public void setS3number2(String s3number2) {
        this.s3number2 = (s3number2 != null) ? s3number2.trim() : null;
    }

    /**
     * @return S3number2
     */
    public String getS3number2() {
        return this.s3number2;
    }

    private int s3type;

    /**
     * @param s3type
     */
    public void setS3type(int s3type) {
        this.s3type = s3type;
    }

    /**
     * @return s3type
     */
    public int getS3type() {
        return this.s3type;
    }

    private String s3name;

    /**
     * @param s3name
     */
    public void setS3name(String s3name) {
        this.s3name = (s3name != null) ? s3name.trim() : null;
    }

    /**
     * @return s3name
     */
    public String getS3name() {
        return this.s3name;
    }

    private String s3address;

    /**
     * @param s3address
     */
    public void setS3address(String s3address) {
        this.s3address = (s3address != null) ? s3address.trim() : null;
    }

    /**
     * @return s3address
     */
    public String getS3address() {
        return this.s3address;
    }

    private String s3address2;

    /**
     * @param s3address2
     */
    public void setS3address2(String s3address2) {
        this.s3address2 = (s3address2 != null) ? s3address2.trim() : null;
    }

    /**
     * @return s3address2
     */
    public String getS3address2() {
        return this.s3address2;
    }

    private String s3poscode;

    /**
     * @param s3poscode
     */
    public void setS3poscode(String s3poscode) {
        this.s3poscode = (s3poscode != null) ? s3poscode.trim() : null;
    }

    /**
     * @return s3poscode
     */
    public String getS3poscode() {
        return this.s3poscode;
    }

    private String s3city;

    /**
     * @param s3city
     */
    public void setS3city(String s3city) {
        this.s3city = (s3city != null) ? s3city.trim() : null;
    }

    /**
     * @return s3city
     */
    public String getS3city() {
        return this.s3city;
    }

    @Override
    public String toString() {
        return "Fsite{"
                + "s3num=" + s3num
                + ", s3unum=" + s3unum
                + ", s3type=" + s3type
                + ", s3number=" + s3number
                + ", s3number2=" + s3number2
                + ", s3name=" + s3name
                + ", s3address=" + s3address
                + ", s3address2=" + s3address2
                + ", s3poscode=" + s3poscode
                + ", s3city=" + s3city
                + ", s3onum=" + s3onum
                + '}';
    }

}
