package bdd;

import java.sql.*;

/**
 * Fagency est une classe décrivant une agence.
 * @version Juin 2016
 * @author Thierry Baribaud
 */
public class Fagency {

  /**
   * Identifiant de l'agence. 
   */
  private int a6num;

  /**
   * Identifiant du client (furgent). 
   */
  private int a6unum;

  /**
   * Appelation externe de l'agence (vu côté client). 
   */
  private String A6extname;

  /**
   * Nom de l'agence (vu côté Anstel). 
   */
  private String A6name;

  /**
   * Nom abrégé de l'agence. 
   */
  private String A6abbname;

  /**
   * Email de l'agence. 
   */
  private String A6email;

  /**
   * Adresse de l'agence. 
   */
  private String A6daddress;

  /**
   * Complément d'adresse de l'agence. 
   */
  private String A6daddress2;

  /**
   * Code postal. 
   */
  private String A6dposcode;

  /**
   * Ville. 
   */
  private String A6dcity;

  /**
   * Numéro de téléphone de l'agence. 
   */
  private String A6teloff;

  /**
   * Ligne directe de l'agence. 
   */
  private String A6teldir;

  /**
   * Télécopieur de l'agence. 
   */
  private String A6telfax;

  /**
   * Code d'activité de l'agence. 
   */
  private int a6active;

  /**
   * Date de début d'activité de l'agence. 
   */
  private Timestamp A6begactive;

  /**
   * Date de fin d'activité de l'agence. 
   */
  private Timestamp A6endactive;

  public int getA6num() {
    return a6num;
    }

  public int getA6unum() {
    return a6unum;
    }

  public Timestamp getA6begactive() {
    return A6begactive;
    }

  public String getA6extname() {
    return A6extname;
    }

  public String getA6name() {
    return A6name;
    }

  public String getA6abbname() {
    return A6abbname;
    }

  public String getA6email() {
    return A6email;
    }

  public String getA6daddress() {
    return A6daddress;
    }

  public String getA6daddress2() {
    return A6daddress2;
    }

  public String getA6dposcode() {
    return A6dposcode;
    }

  public String getA6dcity() {
    return A6dcity;
    }

  public String getA6teloff() {
    return A6teloff;
    }

  public String getA6teldir() {
    return A6teldir;
    }

  public String getA6telfax() {
    return A6telfax;
    }

  public int getA6active() {
    return a6active;
    }

  public Timestamp getA6endactive() {
    return A6endactive;
    }

  public void setA6num(int a6num) {
    this.a6num = a6num;
    }

  public void setA6unum(int a6unum) {
    this.a6unum = a6unum;
    }

  public void setA6begactive(Timestamp A6begactive) {
    this.A6begactive = A6begactive;
    }

  public void setA6name(String A6name) {
    this.A6name = (A6name != null)?A6name.trim():null;
    }

  public void setA6extname(String A6extname) {
    this.A6extname = (A6extname != null)?A6extname.trim():null;
    }

  public void setA6abbname(String A6abbname) {
    this.A6abbname = (A6abbname != null)?A6abbname.trim():null;
    }

  public void setA6email(String A6email) {
    this.A6email = (A6email != null)?A6email.trim():null;
    }

  public void setA6daddress(String A6daddress) {
    this.A6daddress = (A6daddress != null)?A6daddress.trim():null;
    }

  public void setA6daddress2(String A6daddress2) {
    this.A6daddress2 = (A6daddress2 != null)?A6daddress2.trim():null;
    }

  public void setA6dposcode(String A6dposcode) {
    this.A6dposcode = (A6dposcode != null)?A6dposcode.trim():null;
    }

  public void setA6dcity(String A6dcity) {
    this.A6dcity = (A6dcity != null)?A6dcity.trim():null;
    }

  public void setA6teloff(String A6teloff) {
    this.A6teloff = (A6teloff != null)?A6teloff.trim():null;
    }

  public void setA6teldir(String A6teldir) {
    this.A6teldir = (A6teldir != null)?A6teldir.trim():null;
    }

  public void setA6telfax(String A6telfax) {
    this.A6telfax = (A6telfax != null)?A6telfax.trim():null;
    }

  public void setA6active(int a6active) {
    this.a6active = a6active;
    }

  public void setA6endactive(Timestamp A6endactive) {
    this.A6endactive = A6endactive;
    }

  @Override
  public String toString() {
    return this.getClass().getName() +
           " : {a6num=" + a6num + 
           ", a6unum=" + a6unum + 
           ", A6extname=" + A6extname +
           ", A6name=" + A6name +
           ", A6abbname=" + A6abbname +
           ", A6email=" + A6email + 
           ", A6daddress=" + A6daddress + 
           ", A6daddress2=" + A6daddress2 + 
           ", A6dposcode=" + A6dposcode + 
           ", A6dcity=" + A6dcity + 
           ", A6teloff=" + A6teloff + 
           ", A6teldir=" + A6teldir + 
           ", A6telfax=" + A6telfax + 
           ", a6active=" + a6active +
           ", A6begactive=" + A6begactive +
           ", A6endactive=" + A6endactive +
           "}";
  }
}
