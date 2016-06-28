package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder à la table fcomplmt JDBC.
 *
 * @author Thierry Baribaud
 * @version Juin 2016
 */
public class FcomplmtDAO extends PatternDAO {

    /**
     * Class constructor.
     *
     * @param MyConnection an active connection to a database.
     * @throws ClassNotFoundException classe non trouvée.
     * @throws java.sql.SQLException erreur SQL.
     */
    public FcomplmtDAO(Connection MyConnection)
            throws ClassNotFoundException, SQLException {

        this.MyConnection = MyConnection;

        setInvariableSelectStatement("select c6num, c6int2, c6alpha1, c6alpha2, c6name, c6access,"
                + " c6city, c6tel, c6alpha3, c6alpha4,"
                + " c6alpha5, c6alpha6, c6alpha7,"
                + " c6int1, c6date, c6date1, c6int3, c6int4, c6onum,"
                + " c6corp, c6address, c6address2, c6poscode"
                + " from fcomplmt");
//        if (myC6num > 0) {
//            Stmt.append(" where c6num = ").append(myC6num);
//        }
//        Stmt.append(" order by c6num;");
//        setSelectStatement(Stmt.toString());
//        setSelectPreparedStatement();
////        System.out.println(Stmt);
//        setSelectResultSet();

        setUpdateStatement("update fcomplmt"
                + " set c6int2=?, c6alpha1=?, c6alpha2=?, c6name=?, c6access=?,"
                + " c6city=?, c6tel=?, c6alpha3=?, c6alpha4=?,"
                + " c6alpha5=?, c6alpha6=?, c6alpha7=?,"
                + " c6int1=?, c6date=?, c6date1=?, c6int3=?, c6int4=?, c6onum=?,"
                + " c6corp=?, c6address=?, c6address2=?, c6poscode=?"
                + " where c6num=?;");
//        setUpdatePreparedStatement();

        setInsertStatement("insert into fcomplmt"
                + " (c6int2, c6alpha1, c6alpha2, c6name, c6access,"
                + " c6city, c6tel, c6alpha3, c6alpha4,"
                + " c6alpha5, c6alpha6, c6alpha7,"
                + " c6int1, c6date, c6date1, c6int3, c6int4, c6onum,"
                + " c6corp, c6address, c6address2, c6poscode)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
                + ",?, ?, ?, ?, ?, ?, ?);");
//        setInsertPreparedStatement();

        setDeleteStatement("delete from fcomplmt where c6num=?;");
//        setDeletePreparedStatement();
    }

    /**
     * Selectionne un complément d'appel.
     *
     * @return le complément d'appel sélectionné.
     */
    @Override
    public Fcomplmt select() {
        Fcomplmt MyFcomplmt = null;

        try {
            if (SelectResultSet.next()) {
                MyFcomplmt = new Fcomplmt();
                MyFcomplmt.setC6num(SelectResultSet.getInt("c6num"));
                MyFcomplmt.setC6int2(SelectResultSet.getInt("c6int2"));
                MyFcomplmt.setC6alpha1(SelectResultSet.getString("c6alpha1"));
                MyFcomplmt.setC6alpha2(SelectResultSet.getString("c6alpha2"));
                MyFcomplmt.setC6name(SelectResultSet.getString("c6name"));
                MyFcomplmt.setC6access(SelectResultSet.getString("c6access"));
                MyFcomplmt.setC6city(SelectResultSet.getString("c6city"));
                MyFcomplmt.setC6tel(SelectResultSet.getString("c6tel"));
                MyFcomplmt.setC6alpha3(SelectResultSet.getString("c6alpha3"));
                MyFcomplmt.setC6alpha4(SelectResultSet.getString("c6alpha4"));
                MyFcomplmt.setC6alpha5(SelectResultSet.getString("c6alpha5"));
                MyFcomplmt.setC6alpha6(SelectResultSet.getString("c6alpha6"));
                MyFcomplmt.setC6alpha7(SelectResultSet.getString("c6alpha7"));
                MyFcomplmt.setC6int1(SelectResultSet.getInt("c6int1"));
                MyFcomplmt.setC6date(SelectResultSet.getTimestamp("c6date"));
                MyFcomplmt.setC6date1(SelectResultSet.getTimestamp("c6date1"));
                MyFcomplmt.setC6int3(SelectResultSet.getInt("c6int3"));
                MyFcomplmt.setC6int4(SelectResultSet.getInt("c6int4"));
                MyFcomplmt.setC6onum(SelectResultSet.getInt("c6onum"));
                MyFcomplmt.setC6corp(SelectResultSet.getString("c6corp"));
                MyFcomplmt.setC6address(SelectResultSet.getString("c6address"));
                MyFcomplmt.setC6address(SelectResultSet.getString("c6address2"));
                MyFcomplmt.setC6poscode(SelectResultSet.getString("c6poscode"));
            } else {
                System.out.println("Lecture de fcomplmt terminée");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de fcomplmt " + MyException.getMessage());
        }
        return MyFcomplmt;
    }

    /**
     * Met à jour un complément d'appel.
     *
     * @param MyFcomplmt complément d'appel à mettre à jour.
     */
    public void update(Fcomplmt MyFcomplmt) {
        try {
            UpdatePreparedStatement.setInt(1, MyFcomplmt.getC6int2());
            UpdatePreparedStatement.setString(2, MyFcomplmt.getC6alpha1());
            UpdatePreparedStatement.setString(3, MyFcomplmt.getC6alpha2());
            UpdatePreparedStatement.setString(4, MyFcomplmt.getC6name());
            UpdatePreparedStatement.setString(5, MyFcomplmt.getC6access());
            UpdatePreparedStatement.setString(6, MyFcomplmt.getC6city());
            UpdatePreparedStatement.setString(7, MyFcomplmt.getC6tel());
            UpdatePreparedStatement.setString(8, MyFcomplmt.getC6alpha3());
            UpdatePreparedStatement.setString(9, MyFcomplmt.getC6alpha4());
            UpdatePreparedStatement.setString(10, MyFcomplmt.getC6alpha5());
            UpdatePreparedStatement.setString(11, MyFcomplmt.getC6alpha6());
            UpdatePreparedStatement.setString(12, MyFcomplmt.getC6alpha7());
            UpdatePreparedStatement.setInt(13, MyFcomplmt.getC6int1());
            UpdatePreparedStatement.setTimestamp(14, MyFcomplmt.getC6date());
            UpdatePreparedStatement.setTimestamp(15, MyFcomplmt.getC6date1());
            UpdatePreparedStatement.setInt(16, MyFcomplmt.getC6int3());
            UpdatePreparedStatement.setInt(17, MyFcomplmt.getC6int4());
            UpdatePreparedStatement.setInt(18, MyFcomplmt.getC6onum());
            UpdatePreparedStatement.setString(19, MyFcomplmt.getC6corp());
            UpdatePreparedStatement.setString(20, MyFcomplmt.getC6address());
            UpdatePreparedStatement.setString(21, MyFcomplmt.getC6address2());
            UpdatePreparedStatement.setString(22, MyFcomplmt.getC6poscode());
            UpdatePreparedStatement.setInt(23, MyFcomplmt.getC6num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre à jour fcomplmt");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise à jour de fcomplmt "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime définitivement un complément d'appel.
     *
     * @param c6num identiant du complément d'appel à supprimer.
     */
    @Override
    public void delete(int c6num) {
        try {
            DeletePreparedStatement.setInt(1, c6num);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de détruire un complément d'appel dans fcomplmt");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la suppression d'un complément d'appel dans fcomplmt "
                    + MyException.getMessage());
        }
    }

    /**
     * Ajoute un complément d'appel dans la table fcomplmt.
     *
     * @param MyFcomplmt complément d'appel à ajouter à la table fcomplmt.
     */
    public void insert(Fcomplmt MyFcomplmt) {
//        ResultSet MyKeyResultSet;

        try {
            System.out.println("c6alpha2=" + MyFcomplmt.getC6alpha2());
            InsertPreparedStatement.setInt(1, MyFcomplmt.getC6int2());
            InsertPreparedStatement.setString(2, MyFcomplmt.getC6alpha1());
            InsertPreparedStatement.setString(3, MyFcomplmt.getC6alpha2());
            InsertPreparedStatement.setString(4, MyFcomplmt.getC6name());
            InsertPreparedStatement.setString(5, MyFcomplmt.getC6access());
            InsertPreparedStatement.setString(6, MyFcomplmt.getC6city());
            InsertPreparedStatement.setString(7, MyFcomplmt.getC6tel());
            InsertPreparedStatement.setString(8, MyFcomplmt.getC6alpha3());
            InsertPreparedStatement.setString(9, MyFcomplmt.getC6alpha4());
            InsertPreparedStatement.setString(10, MyFcomplmt.getC6alpha5());
            InsertPreparedStatement.setString(11, MyFcomplmt.getC6alpha6());
            InsertPreparedStatement.setString(12, MyFcomplmt.getC6alpha7());
            InsertPreparedStatement.setInt(13, MyFcomplmt.getC6int1());
            InsertPreparedStatement.setTimestamp(14, MyFcomplmt.getC6date());
            InsertPreparedStatement.setTimestamp(15, MyFcomplmt.getC6date1());
            InsertPreparedStatement.setInt(16, MyFcomplmt.getC6int3());
            InsertPreparedStatement.setInt(17, MyFcomplmt.getC6int4());
            InsertPreparedStatement.setInt(18, MyFcomplmt.getC6onum());
            InsertPreparedStatement.setString(19, MyFcomplmt.getC6corp());
            InsertPreparedStatement.setString(20, MyFcomplmt.getC6address());
            InsertPreparedStatement.setString(21, MyFcomplmt.getC6address2());
            InsertPreparedStatement.setString(22, MyFcomplmt.getC6poscode());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un complément d'appel dans fcomplmt");
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFcomplmt.setC6num((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                MyFcomplmt.setC6num(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un complément d'appel dans fcomplmt " + MyException.getMessage());
        }
    }

    /**
     * Méthode pour filter les résultats par identifiant.
     *
     * @param id l'identifiant à utiliser pour le filtrage.
     */
    @Override
    public void filterById(int id) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where c6num = ").append(id).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     */
    @Override
    public void filterByGid(int gid) {
        throw new UnsupportedOperationException("Non supporté actuellement");
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par code.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     * @param Code à utiliser pour le filtrage.
     */
    @Override
    public void filterByCode(int gid, String Code) {
        throw new UnsupportedOperationException("Non supporté actuellement");
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par nom.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     * @param Name à utiliser pour le filtrage.
     */
    @Override
    public void filterByName(int gid, String Name) {
        throw new UnsupportedOperationException("Non supporté actuellement");
    }

    @Override
    public void update(Object MyObject) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object MyObject) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }
}
