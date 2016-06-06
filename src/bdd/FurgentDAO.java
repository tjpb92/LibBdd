package bdd;

import bdd.PaternDAO;
import java.io.IOException;
import java.sql.*;
import utils.ApplicationProperties;
import utils.DBManager;
import utils.DBServer;
import utils.DBServerException;

/**
 * Classe qui d�crit les m�thodes pour acc�der � la table furgent avec JDBC.
 *
 * @author Thierry Baribaud.
 * @version Mai 2015.
 */
public class FurgentDAO extends PaternDAO {

    /**
     * Constructeur de la classe FurgentDAO.
     *
     * @param MyConnection connexion � la base de donn�es courante.
     * @param myUnum identifiant interne du service d'urgence,
     * @throws ClassNotFoundException en cas de classse non trouv�e.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FurgentDAO(Connection MyConnection, int myUnum)
            throws ClassNotFoundException, SQLException {

        StringBuffer Stmt;

        this.MyConnection = MyConnection;

        Stmt = new StringBuffer("select unum, uabbname, uname"
                + " from furgent");

        if (myUnum > 0) {
            Stmt.append(" where unum = ").append(myUnum);
        } else {
            Stmt.append(" where unum >0");
        }
        Stmt.append(" order by unum;");
        setReadStatement(Stmt.toString());
        setReadPreparedStatement();
        setReadResultSet();

        setUpdateStatement("update furgent"
                + " set uabbname=?, uname=?"
                + " where unum=?;");
        setUpdatePreparedStatement();

        setInsertStatement("insert into furgent"
                + " (uabbname, uname)"
                + " values(?, ?);");
        setInsertPreparedStatement();

        setDeleteStatement("delete from furgent"
                + " where unum=?;");
        setDeletePreparedStatement();
    }

    /**
     * Selectionne un service d'urgence.
     *
     * @return un service d'urgence.
     */
    @Override
    public Furgent select() {
        Furgent MyFurgent = null;

        try {
            if (ReadResultSet.next()) {
                MyFurgent = new Furgent();
                MyFurgent.setUnum(ReadResultSet.getInt("unum"));
                MyFurgent.setUabbname(ReadResultSet.getString("uabbname"));
                MyFurgent.setUname(ReadResultSet.getString("uname"));
            } else {
                System.out.println("Lecture de furgent termin�e");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de furgent "
                    + MyException.getMessage());
        }
        return MyFurgent;
    }

    /**
     * Met � jour un service d'urgence.
     *
     * @param MyFurgent service d'urgence � mettre � jour.
     */
    public void update(Furgent MyFurgent) {
        try {
            UpdatePreparedStatement.setString(1, MyFurgent.getUabbname());
            UpdatePreparedStatement.setString(2, MyFurgent.getUname());
            UpdatePreparedStatement.setInt(3, MyFurgent.getUnum());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre � jour furgent");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise � jour de furgent "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime d�finitivement un service d'urgence.
     *
     * @param unum identiant du service d'urgence � supprimer.
     */
    @Override
    public void delete(int unum) {
        try {
            DeletePreparedStatement.setInt(1, unum);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de d�truire un service d'urgence dans furgent");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'un service d'urgence dans furgent "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute un service d'urgence dans la table furgent.
     *
     * @param MyFurgent service d'urgence � ajouter � la table furgent.
     */
    public void insert(Furgent MyFurgent) {
        ResultSet MyKeyResultSet = null;

        try {
            System.out.println("uname=" + MyFurgent.getUname());
            InsertPreparedStatement.setString(1, MyFurgent.getUabbname());
            InsertPreparedStatement.setString(2, MyFurgent.getUname());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un service d'urgence dans furgent");
            } else {
                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
                if (MyKeyResultSet.next()) {
                    MyFurgent.setUnum((int) MyKeyResultSet.getInt(1));
                }
            }
            MyKeyResultSet.close();
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un service d'urgence dans furgent "
                    + MyException.getMessage());
        }
    }

    /**
     * <p>
     * Programme principal pour tester la classe FurgentDAO.</p>
     * <ul><li>Le premier param�tre d�signe le serveur de base de donn�es auquel
     * se connecter : dev/pre-prod/prod (obligatoire). </li>
     * <li>Le second param�tre est le nom du fichier des propri�t�s � charger
     * (obligatoire).</li></ul>
     *
     * @param Args param�tres en ligne de commande.
     */
    public static void main(String[] Args) {
        ApplicationProperties MyApplicationProperties;
        DBServer MyDBServer;
        DBManager MyDBManager;
        FurgentDAO MyFurgentDAO;
        Furgent MyFurgent1;
        Furgent MyFurgent;
        long i;
        int myUnum = 552;

        if (Args.length != 2) {
            System.out.println("Usage : java FurgentDAO server-type filename");
            System.exit(0);
        }

        try {
            MyApplicationProperties = new ApplicationProperties(Args[1]);

            MyDBServer = new DBServer(Args[0], MyApplicationProperties);
            MyDBManager = new DBManager(MyDBServer);

// Essai insertion
            MyFurgentDAO = new FurgentDAO(MyDBManager.getConnection(), myUnum);
            MyFurgent1 = new Furgent();
            MyFurgent1.setUabbname("NEXITY");
            MyFurgent1.setUname("nexity");
            System.out.println("Furgent(avant insertion)=" + MyFurgent1);
            MyFurgentDAO.insert(MyFurgent1);
            System.out.println("Furgent(apr�s insertion)=" + MyFurgent1);
            System.out.println("Rang�e(s) affect�e(s)=" + MyFurgentDAO.getNbAffectedRow());

// Essai mise � jour
            MyFurgent1.setUname(MyFurgent1.getUname() + " tertiaire");
            MyFurgentDAO.update(MyFurgent1);
            System.out.println("Furgent(apr�s mise � jour)=" + MyFurgent1);
            System.out.println("Rang�e(s) affect�e(s)=" + MyFurgentDAO.getNbAffectedRow());
            MyFurgentDAO.close();

// Essai lecture
            MyFurgentDAO = new FurgentDAO(MyDBManager.getConnection(), myUnum);
            i = 0;
            while ((MyFurgent = MyFurgentDAO.select()) != null) {
                i++;
                System.out.println("Furgent(" + i + ")=" + MyFurgent);
                System.out.println("  getUnum()=" + MyFurgent.getUnum());
                System.out.println("  getUabbname()=" + MyFurgent.getUabbname());
                System.out.println("  getUname()=" + MyFurgent.getUname());
            }

// Essai suppression
            System.out.println("Suppression de : " + MyFurgent1);
            MyFurgentDAO.delete(MyFurgent1.getUnum());
            System.out.println("Rang�e(s) affect�e(s)=" + MyFurgentDAO.getNbAffectedRow());

        } catch (IOException MyException) {
            System.out.println("Erreur en lecture du fichier des propri�t�s " + MyException);
        } catch (DBServerException MyException) {
            System.out.println("Erreur avec le serveur de base de donn�es " + MyException);
        } catch (ClassNotFoundException MyException) {
            System.out.println("Erreur classe non trouv�e " + MyException);
        } catch (SQLException MyException) {
            System.out.println("Erreur SQL rencontr�e " + MyException);
        }
    }

    @Override
    public void update(Object MyObject) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object MyObject) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }
}
