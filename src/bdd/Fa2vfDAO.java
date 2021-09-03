package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui d�crit les m�thodes pour acc�der � la table fa2vf avec JDBC.
 *
 * @author Thierry Baribaud
 * @version 0.32
 */
public class Fa2vfDAO extends PatternDAO {

    /**
     * Constructeur de la classe Fa2vf.
     *
     * @param connection une connexion � la base de donn�es courante.
     * @throws ClassNotFoundException en cas de classe non trouv�e.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public Fa2vfDAO(Connection connection)
            throws ClassNotFoundException, SQLException {

        this.connection = connection;

        setInvariableSelectStatement("select a12num, a12onum0, a12laguid,"
                + " a12evttype, a12data,"
                + " a12status, a12nberr, a12size,"
                + " a12credate, a12update"
                + " from fa2vf");

        setUpdateStatement("update fa2vf"
                + " set a12onum0=?, a12laguid=?,"
                + " a12evttype=?, a12data=?,"
                + " a12status=?, a12nberr=?, a12size=?,"
                + " a12credate=?, a12update=?"
                + " where a12num=?;");
//        setUpdatePreparedStatement();

        setInsertStatement("insert into fa2vf"
                + " (a12onum0, a12laguid, "
                + " a12evttype, a12data,"
                + " a12status, a12nberr, a12size,"
                + " a12credate, a12update)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?);");
//        setInsertPreparedStatement();

        setDeleteStatement("delete from fa2vf"
                + " where a12num=?;");
//        setDeletePreparedStatement();
    }

    /**
     * S�lectionne un �v�nement.
     *
     * @return l'�v�nement s�lectionn�.
     */
    @Override
    public Fa2vf select() {
        Fa2vf fa2vf = null;

        try {
            if (SelectResultSet.next()) {
                fa2vf = new Fa2vf();
                fa2vf.setA12num(SelectResultSet.getInt("a12num"));
                fa2vf.setA12onum0(SelectResultSet.getInt("a12onum0"));
                fa2vf.setA12laguid(SelectResultSet.getString("a12laguid"));
                fa2vf.setA12evttype(SelectResultSet.getInt("a12evttype"));
                fa2vf.setA12data(SelectResultSet.getString("a12data"));
                fa2vf.setA12status(SelectResultSet.getInt("a12status"));
                fa2vf.setA12nberr(SelectResultSet.getInt("a12nberr"));
                fa2vf.setA12size(SelectResultSet.getInt("a12size"));
                fa2vf.setA12credate(SelectResultSet.getTimestamp("a12credate"));
                fa2vf.setA12update(SelectResultSet.getTimestamp("a12update"));
            } else {
                System.out.println("Lecture de fa2vf termin�e");
            }
        } catch (SQLException exception) {
            System.out.println("Erreur en lecture de fa2vf "
                    + exception.getMessage());
        }
        return fa2vf;
    }

    /**
     * Met � jour un �v�nement.
     *
     * @param fa2vf agence � mettre � jour.
     */
    public void update(Fa2vf fa2vf) {
        try {
            UpdatePreparedStatement.setInt(1, fa2vf.getA12onum0());
            UpdatePreparedStatement.setString(2, fa2vf.getA12laguid());
            UpdatePreparedStatement.setInt(3, fa2vf.getA12evttype());
            UpdatePreparedStatement.setString(4, fa2vf.getA12data());
            UpdatePreparedStatement.setInt(5, fa2vf.getA12status());
            UpdatePreparedStatement.setInt(6, fa2vf.getA12nberr());
            UpdatePreparedStatement.setInt(7, fa2vf.getA12size());
            UpdatePreparedStatement.setTimestamp(8, fa2vf.getA12credate());
            UpdatePreparedStatement.setTimestamp(9, fa2vf.getA12update());
            UpdatePreparedStatement.setInt(10, fa2vf.getA12num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre � jour fa2vf");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise � jour de fa2vf "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime d�finitivement un �v�nement.
     *
     * @param a12num identifiant de l'�v�nement � supprimer.
     */
    @Override
    public void delete(int a12num) {
        try {
            DeletePreparedStatement.setInt(1, a12num);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de d�truire un �v�nement dans fa2vf");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'un �v�nement dans fa2vf "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute un �v�nement.
     *
     * @param fa2vf �v�nement � ajouter.
     */
    public void insert(Fa2vf fa2vf) {
//        ResultSet MyKeyResultSet;

        try {
            System.out.println("a12laguid=" + fa2vf.getA12laguid());
            InsertPreparedStatement.setInt(1, fa2vf.getA12onum0());
            InsertPreparedStatement.setString(2, fa2vf.getA12laguid());
            InsertPreparedStatement.setInt(3, fa2vf.getA12evttype());
            InsertPreparedStatement.setString(4, fa2vf.getA12data());
            InsertPreparedStatement.setInt(5, fa2vf.getA12status());
            InsertPreparedStatement.setInt(6, fa2vf.getA12nberr());
            InsertPreparedStatement.setInt(7, fa2vf.getA12size());
            InsertPreparedStatement.setTimestamp(8, fa2vf.getA12credate());
            InsertPreparedStatement.setTimestamp(9, fa2vf.getA12update());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un �v�nement dans fa2vf");
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFa2vf.setA12num((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                fa2vf.setA12num(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un �v�nement dans fa2vf "
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
        StringBuffer stmt;

        stmt = new StringBuffer(InvariableSelectStatement);
        stmt.append(" where a12num = ").append(id).append(";");
        setSelectStatement(stmt.toString());
    }

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe.
     *
     * @param laguid l'identifiant de groupe � utiliser pour le filtrage.
     */
    public void filterByGid(String laguid) {
        StringBuffer stmt;

        stmt = new StringBuffer(InvariableSelectStatement);
        stmt.append(" where a12laguid = \"").append(laguid).append("\";");
        setSelectStatement(stmt.toString());
    }

    /**
     * M�thode pour filter les r�sultats par status.
     *
     * @param status status � utiliser pour le filtrage.
     */
    public void filterByStatus(int status) {
        StringBuffer stmt;

        stmt = new StringBuffer(InvariableSelectStatement);
        stmt.append(" where a12status = ").append(status).append(";");
        setSelectStatement(stmt.toString());
    }

    @Override
    public void filterByGid(int gid) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void filterByCode(int gid, String code) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void filterByName(int gid, String name) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object object) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object object) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }
}
