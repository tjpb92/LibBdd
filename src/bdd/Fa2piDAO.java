package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder à la table fa2pi avec JDBC.
 *
 * @author Thierry Baribaud
 * @version 0.29
 */
public class Fa2piDAO extends PatternDAO {

    /**
     * Constructeur de la classe Fa2pi.
     *
     * @param connection une connexion à la base de données courante.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public Fa2piDAO(Connection connection)
            throws ClassNotFoundException, SQLException {

        this.connection = connection;

        setInvariableSelectStatement("select a10num, a10unum, a10onum0, a10laguid, a10a6num, a10tnum,"
                + " a10onum, a10s3num, a10evttype, a10data,"
                + " a10status, a10nberr, a10size,"
                + " a10credate, a10update"
                + " from fa2pi");

//        if (myA10num > 0) {
//            Stmt.append(" where a10num = ").append(myA10num);
//        } else {
//            Stmt.append(" where a10num >0");
//        }
//        if (MyA10unum > 0) {
//            Stmt.append(" and a10unum = ").append(MyA10unum);
//        }
//        if (MyA10laguid != null) {
//            Stmt.append(" and a10laguid like \"").append(MyA10laguid).append("%\"");
//        }
//        Stmt.append(" order by a10num;");
//        setSelectStatement(Stmt.toString());
//        setSelectPreparedStatement();
//        setSelectResultSet();
        setUpdateStatement("update fa2pi"
                + " set a10unum=?, a10onum0=?, a10laguid=?, a10a6num=?, a10tnum=?,"
                + " a10onum=?, a10s3num=?, a10evttype=?, a10data=?,"
                + " a10status=?, a10nberr=?, a10size=?,"
                + " a10credate=?, a10update=?"
                + " where a10num=?;");
//        setUpdatePreparedStatement();

        setInsertStatement("insert into fa2pi"
                + " (a10unum, a10onum0, a10laguid, a10a6num, a10tnum,"
                + " a10onum, a10s3num, a10evttype, a10data,"
                + " a10status, a10nberr, a10size,"
                + " a10credate, a10update)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
//        setInsertPreparedStatement();

        setDeleteStatement("delete from fa2pi"
                + " where a10num=?;");
//        setDeletePreparedStatement();
    }

    /**
     * Sélectionne un événement.
     *
     * @return l'événement sélectionné.
     */
    @Override
    public Fa2pi select() {
        Fa2pi fa2pi = null;

        try {
            if (SelectResultSet.next()) {
                fa2pi = new Fa2pi();
                fa2pi.setA10num(SelectResultSet.getInt("a10num"));
                fa2pi.setA10unum(SelectResultSet.getInt("a10unum"));
                fa2pi.setA10onum0(SelectResultSet.getInt("a10onum0"));
                fa2pi.setA10laguid(SelectResultSet.getString("a10laguid"));
                fa2pi.setA10a6num(SelectResultSet.getInt("a10a6num"));
                fa2pi.setA10tnum(SelectResultSet.getInt("a10tnum"));
                fa2pi.setA10onum(SelectResultSet.getInt("a10onum"));
                fa2pi.setA10s3num(SelectResultSet.getInt("a10s3num"));
                fa2pi.setA10evttype(SelectResultSet.getInt("a10evttype"));
                fa2pi.setA10data(SelectResultSet.getString("a10data"));
                fa2pi.setA10status(SelectResultSet.getInt("a10status"));
                fa2pi.setA10nberr(SelectResultSet.getInt("a10nberr"));
                fa2pi.setA10size(SelectResultSet.getInt("a10size"));
                fa2pi.setA10credate(SelectResultSet.getTimestamp("a10credate"));
                fa2pi.setA10update(SelectResultSet.getTimestamp("a10update"));
            } else {
                System.out.println("Lecture de fa2pi terminée");
            }
        } catch (SQLException exception) {
            System.out.println("Erreur en lecture de fa2pi "
                    + exception.getMessage());
        }
        return fa2pi;
    }

    /**
     * Met à jour un événement.
     *
     * @param fa2pi agence à mettre à jour.
     */
    public void update(Fa2pi fa2pi) {
        try {
            UpdatePreparedStatement.setInt(1, fa2pi.getA10unum());
            UpdatePreparedStatement.setInt(2, fa2pi.getA10onum0());
            UpdatePreparedStatement.setString(3, fa2pi.getA10laguid());
            UpdatePreparedStatement.setInt(4, fa2pi.getA10a6num());
            UpdatePreparedStatement.setInt(5, fa2pi.getA10tnum());
            UpdatePreparedStatement.setInt(6, fa2pi.getA10onum());
            UpdatePreparedStatement.setInt(7, fa2pi.getA10s3num());
            UpdatePreparedStatement.setInt(8, fa2pi.getA10evttype());
            UpdatePreparedStatement.setString(9, fa2pi.getA10data());
            UpdatePreparedStatement.setInt(10, fa2pi.getA10status());
            UpdatePreparedStatement.setInt(11, fa2pi.getA10nberr());
            UpdatePreparedStatement.setInt(12, fa2pi.getA10size());
            UpdatePreparedStatement.setTimestamp(13, fa2pi.getA10credate());
            UpdatePreparedStatement.setTimestamp(14, fa2pi.getA10update());
            UpdatePreparedStatement.setInt(15, fa2pi.getA10num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre à jour fa2pi");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise à jour de fa2pi "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime définitivement un événement.
     *
     * @param a10num identifiant de l'événement à supprimer.
     */
    @Override
    public void delete(int a10num) {
        try {
            DeletePreparedStatement.setInt(1, a10num);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de détruire un événement dans fa2pi");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'un événement dans fa2pi "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute un événement.
     *
     * @param fa2pi événement à ajouter.
     */
    public void insert(Fa2pi fa2pi) {
//        ResultSet MyKeyResultSet;

        try {
            System.out.println("a10laguid=" + fa2pi.getA10laguid());
            InsertPreparedStatement.setInt(1, fa2pi.getA10unum());
            InsertPreparedStatement.setInt(2, fa2pi.getA10onum0());
            InsertPreparedStatement.setString(3, fa2pi.getA10laguid());
            InsertPreparedStatement.setInt(4, fa2pi.getA10a6num());
            InsertPreparedStatement.setInt(5, fa2pi.getA10tnum());
            InsertPreparedStatement.setInt(6, fa2pi.getA10onum());
            InsertPreparedStatement.setInt(7, fa2pi.getA10s3num());
            InsertPreparedStatement.setInt(8, fa2pi.getA10evttype());
            InsertPreparedStatement.setString(9, fa2pi.getA10data());
            InsertPreparedStatement.setInt(10, fa2pi.getA10status());
            InsertPreparedStatement.setInt(11, fa2pi.getA10nberr());
            InsertPreparedStatement.setInt(12, fa2pi.getA10size());
            InsertPreparedStatement.setTimestamp(13, fa2pi.getA10credate());
            InsertPreparedStatement.setTimestamp(14, fa2pi.getA10update());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un événement dans fa2pi");
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFa2pi.setA10num((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                fa2pi.setA10num(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un événement dans fa2pi "
                    + MyException.getMessage());
        }
    }

    /**
     * Méthode pour filter les résultats par identifiant.
     *
     * @param id l'identifiant à utiliser pour le filtrage.
     */
    @Override
    public void filterById(int id) {
        StringBuffer stmt;

        stmt = new StringBuffer(InvariableSelectStatement);
        stmt.append(" where a10num = ").append(id).append(";");
        setSelectStatement(stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe.
     *
     * @param laguid l'identifiant de groupe à utiliser pour le filtrage.
     */
    public void filterByGid(String laguid) {
        StringBuffer stmt;

        stmt = new StringBuffer(InvariableSelectStatement);
        stmt.append(" where a10laguid = \"").append(laguid).append("\";");
        setSelectStatement(stmt.toString());
    }
    
    /**
     * Méthode pour filter les résultats par status.
     *
     * @param status status à utiliser pour le filtrage.
     */
    public void filterByStatus(int status) {
        StringBuffer stmt;

        stmt = new StringBuffer(InvariableSelectStatement);
        stmt.append(" where a10status = ").append(status).append(";");
        setSelectStatement(stmt.toString());
    }

    @Override
    public void filterByGid(int gid) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void filterByCode(int gid, String code) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void filterByName(int gid, String name) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object object) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object object) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }
}
