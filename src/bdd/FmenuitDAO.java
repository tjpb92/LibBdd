package bdd;

import com.informix.jdbc.IfmxStatement;
import java.io.IOException;
import java.sql.*;
import utils.ApplicationProperties;
import utils.DBManager;
import utils.DBServer;
import utils.DBServerException;

/**
 * Classe qui d�crit les m�thodes pour acc�der � la table fmenuit avec JDBC.
 *
 * @author Thierry Baribaud
 * @version Juin 2016
 */
public class FmenuitDAO extends PatternDAO {

    /**
     * Constructeur de la classe FmenuitDAO.
     *
     * @param MyConnection connexion � la base de donn�es courante.
     * @throws ClassNotFoundException en cas de classe non trouv�e.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FmenuitDAO(Connection MyConnection)
            throws ClassNotFoundException, SQLException {

        this.MyConnection = MyConnection;

        setInvariableSelectStatement("select m6num, m6extname, m6name"
                + " from fmenuit");

//        if (myM6num > 0) {
//            Stmt.append(" where m6num = ").append(myM6num);
//        } else {
//            Stmt.append(" where m6num >0");
//        }
//        Stmt.append(" order by m6num;");
//        setSelectStatement(Stmt.toString());
//        setSelectPreparedStatement();
//        setSelectResultSet();

        setUpdateStatement("update fmenuit"
                + " set m6extname=?, m6name=?"
                + " where m6num=?;");
//        setUpdatePreparedStatement();

        setInsertStatement("insert into fmenuit"
                + " (m6extname, m6name)"
                + " values(?, ?);");
//        setInsertPreparedStatement();

        setDeleteStatement("delete from fmenuit"
                + " where m6num=?;");
//        setDeletePreparedStatement();
    }

    /**
     * Selectionne un item de menu.
     *
     * @return l'item de menu s�lectionn�.
     */
    @Override
    public Fmenuit select() {
        Fmenuit MyFmenuit = null;

        try {
            if (SelectResultSet.next()) {
                MyFmenuit = new Fmenuit();
                MyFmenuit.setM6num(SelectResultSet.getInt("m6num"));
                MyFmenuit.setM6extname(SelectResultSet.getString("m6extname"));
                MyFmenuit.setM6name(SelectResultSet.getString("m6name"));
            } else {
                System.out.println("Lecture de fmenuit termin�e");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de fmenuit "
                    + MyException.getMessage());
        }
        return MyFmenuit;
    }

    /**
     * Met � jour un item de menu.
     *
     * @param MyFmenuit item de menu � mettre � jour.
     */
    public void update(Fmenuit MyFmenuit) {
        try {
            UpdatePreparedStatement.setString(1, MyFmenuit.getM6extname());
            UpdatePreparedStatement.setString(2, MyFmenuit.getM6name());
            UpdatePreparedStatement.setInt(3, MyFmenuit.getM6num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre � jour fmenuit");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise � jour de fmenuit "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime d�finitivement un item de menu.
     *
     * @param m6num identiant de l'item � supprimer.
     */
    @Override
    public void delete(int m6num) {
        try {
            DeletePreparedStatement.setInt(1, m6num);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de d�truire un item dans fmenuit");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'un item dans fmenuit "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute un item de menu dans la table fmenuit.
     *
     * @param MyFmenuit item � ajouter � la table fmenuit.
     */
    public void insert(Fmenuit MyFmenuit) {
//        ResultSet MyKeyResultSet;

        try {
            System.out.println("m6name=" + MyFmenuit.getM6name());
            InsertPreparedStatement.setString(1, MyFmenuit.getM6extname());
            InsertPreparedStatement.setString(2, MyFmenuit.getM6name());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un item dans fmenuit");
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFmenuit.setM6num((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                MyFmenuit.setM6num(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un item dans fmenuit "
                    + MyException.getMessage());
        }
    }

    /**
     * M�thode pour filter les r�sultats par identifiant.
     *
     * @param id l'identifiant � utiliser pour le filtrage.
     */
    @Override
    public void filterById(int id) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where m6num = ").append(id).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe.
     *
     * @param gid l'identifiant de groupe � utiliser pour le filtrage.
     */
    @Override
    public void filterByGid(int gid) {
        throw new UnsupportedOperationException("Non support� actuellement"); 
    }

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe et par code.
     *
     * @param gid l'identifiant de groupe � utiliser pour le filtrage.
     * @param Code � utiliser pour le filtrage.
     */
    @Override
    public void filterByCode(int gid, String Code) {
        throw new UnsupportedOperationException("Non support� actuellement"); 
    }

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe et par nom.
     *
     * @param gid l'identifiant de groupe � utiliser pour le filtrage.
     * @param Name � utiliser pour le filtrage.
     */
    @Override
    public void filterByName(int gid, String Name) {
        throw new UnsupportedOperationException("Non support� actuellement"); 
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
