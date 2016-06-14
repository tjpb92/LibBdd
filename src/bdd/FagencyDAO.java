package bdd;

import java.io.IOException;
import java.sql.*;
import utils.ApplicationProperties;
import utils.DBManager;
import utils.DBServer;
import utils.DBServerException;

/**
 * Classe qui décrit les méthodes pour accéder à la table fagency au travers de
 * JDBC.
 *
 * @author Thierry Baribaud
 * @version Juin 2015
 */
public class FagencyDAO extends PaternDAO {

    /**
     * Constructeur principal de la classe Fagency.
     *
     * @param MyConnection une connexion à la base de données courante.
     * @param myA6num identifiant de l'agence.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FagencyDAO(Connection MyConnection, int myA6num)
            throws ClassNotFoundException, SQLException {

        this(MyConnection, myA6num, 0, null);
    }

    /**
     * Constructeur secondaire de la classe Fagency.
     *
     * @param MyConnection une connexion à la base de données courante.
     * @param MyA6unum identifiant du client.
     * @param MyA6name nom de l'agence permettant un filtrage.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FagencyDAO(Connection MyConnection, int MyA6unum, String MyA6name)
            throws ClassNotFoundException, SQLException {

        this(MyConnection, 0, MyA6unum, MyA6name);
    }

    /**
     * Constructeur tertiaire de la classe Fagency.
     *
     * @param MyConnection une connexion à la base de données courante.
     * @param myA6num identifiant de l'agence.
     * @param MyA6unum identifiant du client.
     * @param MyA6name nom de l'agence permettant un filtrage.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FagencyDAO(Connection MyConnection, int myA6num, int MyA6unum, String MyA6name)
            throws ClassNotFoundException, SQLException {

        StringBuffer Stmt;

        this.MyConnection = MyConnection;

        Stmt = new StringBuffer("select a6num, a6unum, a6extname, a6name, a6abbname, a6email,"
                + " a6daddress, a6daddress2, a6dposcode, a6dcity,"
                + " a6teloff, a6teldir, a6telfax,"
                + " a6active, a6begactive, a6endactive"
                + " from fagency");

        if (myA6num > 0) {
            Stmt.append(" where a6num = ").append(myA6num);
        } else {
            Stmt.append(" where a6num >0");
        }
        if (MyA6unum > 0) {
            Stmt.append(" and a6unum = ").append(MyA6unum);
        }
        if (MyA6name != null) {
            Stmt.append(" and a6name like \"").append(MyA6name).append("%\"");
        }
        Stmt.append(" order by a6num;");
        setReadStatement(Stmt.toString());
        setReadPreparedStatement();
        setReadResultSet();

        setUpdateStatement("update fagency"
                + " set a6unum=?, a6extname=?, a6name=?, a6abbname=?, a6email=?,"
                + " a6daddress=?, a6daddress2=?, a6dposcode=?, a6dcity=?,"
                + " a6teloff=?, a6teldir=?, a6telfax=?,"
                + " a6active=?, a6begactive=?, a6endactive=?"
                + " where a6num=?;");
        setUpdatePreparedStatement();

        setInsertStatement("insert into fagency"
                + " (a6unum, a6extname, a6name, a6abbname, a6email,"
                + " a6daddress, a6daddress2, a6dposcode, a6dcity,"
                + " a6teloff, a6teldir, a6telfax,"
                + " a6active, a6begactive, a6endactive)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
        setInsertPreparedStatement();

        setDeleteStatement("delete from fagency"
                + " where a6num=?;");
        setDeletePreparedStatement();
    }

    /**
     * Sélectionne une agence.
     *
     * @return l'agence sélectionnée.
     */
    @Override
    public Fagency select() {
        Fagency MyFagency = null;

        try {
            if (ReadResultSet.next()) {
                MyFagency = new Fagency();
                MyFagency.setA6num(ReadResultSet.getInt("a6num"));
                MyFagency.setA6unum(ReadResultSet.getInt("a6unum"));
                MyFagency.setA6extname(ReadResultSet.getString("a6extname"));
                MyFagency.setA6name(ReadResultSet.getString("a6name"));
                MyFagency.setA6abbname(ReadResultSet.getString("a6abbname"));
                MyFagency.setA6email(ReadResultSet.getString("a6email"));
                MyFagency.setA6daddress(ReadResultSet.getString("a6daddress"));
                MyFagency.setA6daddress2(ReadResultSet.getString("a6daddress2"));
                MyFagency.setA6dposcode(ReadResultSet.getString("a6dposcode"));
                MyFagency.setA6dcity(ReadResultSet.getString("a6dcity"));
                MyFagency.setA6teloff(ReadResultSet.getString("a6teloff"));
                MyFagency.setA6teldir(ReadResultSet.getString("a6teldir"));
                MyFagency.setA6telfax(ReadResultSet.getString("a6telfax"));
                MyFagency.setA6active(ReadResultSet.getInt("a6active"));
                MyFagency.setA6begactive(ReadResultSet.getTimestamp("a6begactive"));
                MyFagency.setA6endactive(ReadResultSet.getTimestamp("a6endactive"));
            } else {
                System.out.println("Lecture de fagency terminée");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de fagency "
                    + MyException.getMessage());
        }
        return MyFagency;
    }

    /**
     * Met à jour une agence.
     *
     * @param MyFagency agence à mettre à jour.
     */
    public void update(Fagency MyFagency) {
        try {
            UpdatePreparedStatement.setInt(1, MyFagency.getA6unum());
            UpdatePreparedStatement.setString(2, MyFagency.getA6extname());
            UpdatePreparedStatement.setString(3, MyFagency.getA6name());
            UpdatePreparedStatement.setString(4, MyFagency.getA6abbname());
            UpdatePreparedStatement.setString(5, MyFagency.getA6email());
            UpdatePreparedStatement.setString(6, MyFagency.getA6daddress());
            UpdatePreparedStatement.setString(7, MyFagency.getA6daddress2());
            UpdatePreparedStatement.setString(8, MyFagency.getA6dposcode());
            UpdatePreparedStatement.setString(9, MyFagency.getA6dcity());
            UpdatePreparedStatement.setString(10, MyFagency.getA6teloff());
            UpdatePreparedStatement.setString(11, MyFagency.getA6teldir());
            UpdatePreparedStatement.setString(12, MyFagency.getA6telfax());
            UpdatePreparedStatement.setInt(13, MyFagency.getA6active());
            UpdatePreparedStatement.setTimestamp(14, MyFagency.getA6begactive());
            UpdatePreparedStatement.setTimestamp(15, MyFagency.getA6endactive());
            UpdatePreparedStatement.setInt(16, MyFagency.getA6num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre à jour fagency");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise à jour de fagency "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime définitivement une agence.
     *
     * @param a6num identifiant de l'agence à supprimer.
     */
    @Override
    public void delete(int a6num) {
        try {
            DeletePreparedStatement.setInt(1, a6num);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de détruire une agence dans fagency");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'une agence dans fagency "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute une agence.
     *
     * @param MyFagency agence à ajouter.
     */
    public void insert(Fagency MyFagency) {
        ResultSet MyKeyResultSet = null;

        try {
            System.out.println("a6name=" + MyFagency.getA6name());
            InsertPreparedStatement.setInt(1, MyFagency.getA6unum());
            InsertPreparedStatement.setString(2, MyFagency.getA6extname());
            InsertPreparedStatement.setString(3, MyFagency.getA6name());
            InsertPreparedStatement.setString(4, MyFagency.getA6abbname());
            InsertPreparedStatement.setString(5, MyFagency.getA6email());
            InsertPreparedStatement.setString(6, MyFagency.getA6daddress());
            InsertPreparedStatement.setString(7, MyFagency.getA6daddress2());
            InsertPreparedStatement.setString(8, MyFagency.getA6dposcode());
            InsertPreparedStatement.setString(9, MyFagency.getA6dcity());
            InsertPreparedStatement.setString(10, MyFagency.getA6teloff());
            InsertPreparedStatement.setString(11, MyFagency.getA6teldir());
            InsertPreparedStatement.setString(12, MyFagency.getA6telfax());
            InsertPreparedStatement.setInt(13, MyFagency.getA6active());
            InsertPreparedStatement.setTimestamp(14, MyFagency.getA6begactive());
            InsertPreparedStatement.setTimestamp(15, MyFagency.getA6endactive());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter une agence dans fagency");
            } else {
                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
                if (MyKeyResultSet.next()) {
                    MyFagency.setA6num((int) MyKeyResultSet.getInt(1));
                }
            }
            MyKeyResultSet.close();
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'une agence dans fagency "
                    + MyException.getMessage());
        }
    }

    /**
     * Main method to test FagencyDAO class.
     *
     * @param Args command line arguments. First argument must be the type of
     * Database server : dev/pre-prod/prod. Second argument must be the name of
     * the application properties file to load.
     */
    public static void main(String[] Args) {
        ApplicationProperties MyApplicationProperties;
        DBServer MyDBServer;
        DBManager MyDBManager;
        FagencyDAO MyFagencyDAO;
        Fagency MyFagency1;
        Fagency MyFagency;
        long i;
        //String MyA6name = "france";
        String MyA6name = null;
        int MyA6unum = 552;

        if (Args.length != 2) {
            System.out.println("Usage : java FagencyDAO server-type filename");
            System.exit(0);
        }

        try {
            MyApplicationProperties = new ApplicationProperties(Args[1]);

            MyDBServer = new DBServer(Args[0], MyApplicationProperties);
            MyDBManager = new DBManager(MyDBServer);

// Essai insertion
            MyFagencyDAO = new FagencyDAO(MyDBManager.getConnection(), MyA6unum, MyA6name);
            MyFagency1 = new Fagency();
            MyFagency1.setA6unum(99999);
            MyFagency1.setA6extname("terra incognita");
            MyFagency1.setA6name("utopia");
            MyFagency1.setA6abbname("UTOPIA");
            MyFagency1.setA6email("utopia@gmail.com");
            MyFagency1.setA6daddress("12, rue des rÃ¨ves");
            MyFagency1.setA6daddress2("bÃ¢timent B");
            MyFagency1.setA6dposcode("92400");
            MyFagency1.setA6dcity("UTOPIA CITY");
            MyFagency1.setA6teloff("01.01.01.01.01");
            MyFagency1.setA6teldir("02.02.02.02.02");
            MyFagency1.setA6telfax("03.03.03.03.03");
            MyFagency1.setA6active(1);
            MyFagency1.setA6begactive(new Timestamp(new java.util.Date().getTime()));
            MyFagency1.setA6endactive(Timestamp.valueOf("2050-12-31 23:59:59.0"));
            System.out.println("Fagency(before insert)=" + MyFagency1);
            MyFagencyDAO.insert(MyFagency1);
            System.out.println("Fagency(after insert)=" + MyFagency1);
            System.out.println("Affected row(s)=" + MyFagencyDAO.getNbAffectedRow());

// Essai mise ÃƒÂ  jour
            MyFagency1.setA6email(MyFagency1.getA6email() + ",utopia@free.fr");
            MyFagencyDAO.update(MyFagency1);
            System.out.println("Fagency(after update)=" + MyFagency1);
            System.out.println("Affected row(s)=" + MyFagencyDAO.getNbAffectedRow());
            MyFagencyDAO.close();

// Essai lecture
            MyFagencyDAO = new FagencyDAO(MyDBManager.getConnection(), MyA6unum, MyA6name);
            i = 0;
            while ((MyFagency = MyFagencyDAO.select()) != null) {
                i++;
                System.out.println("Fagency(" + i + ")=" + MyFagency);
                System.out.println("  getA6num()=" + MyFagency.getA6num());
                System.out.println("  getA6unum()=" + MyFagency.getA6unum());
                System.out.println("  getA6extname()=" + MyFagency.getA6extname());
                System.out.println("  getA6name()=" + MyFagency.getA6name());
                System.out.println("  getA6abbname()=" + MyFagency.getA6abbname());
                System.out.println("  getA6email()=" + MyFagency.getA6email());
                System.out.println("  getA6daddress()=" + MyFagency.getA6daddress());
                System.out.println("  getA6daddress2()=" + MyFagency.getA6daddress2());
                System.out.println("  getA6dposcode()=" + MyFagency.getA6dposcode());
                System.out.println("  getA6dcity()=" + MyFagency.getA6dcity());
                System.out.println("  getA6teloff()=" + MyFagency.getA6teloff());
                System.out.println("  getA6teldir()=" + MyFagency.getA6teldir());
                System.out.println("  getA6telfax()=" + MyFagency.getA6telfax());
                System.out.println("  getA6active()=" + MyFagency.getA6active());
                System.out.println("  getA6begactive()=" + MyFagency.getA6begactive());
                System.out.println("  getA6endactive()=" + MyFagency.getA6endactive());
            }

// Essai suppression
            System.out.println("Deleting : " + MyFagency1);
            MyFagencyDAO.delete(MyFagency1.getA6num());
            System.out.println("Affected row(s)=" + MyFagencyDAO.getNbAffectedRow());

        } catch (IOException | DBServerException | ClassNotFoundException | SQLException MyException) {
            System.out.println("Problem while creating FagencyDAO " + MyException);
        }
    }

    @Override
    public void update(Object MyObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object MyObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
