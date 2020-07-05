package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder à la table fcomplmt JDBC.
 *
 * @author Thierry Baribaud
 * @version 0.31
 */
public class FcomplmtDAO extends PatternDAO {

    /**
     * Class constructor.
     *
     * @param connection an active connection to a database.
     * @throws ClassNotFoundException classe non trouvée.
     * @throws java.sql.SQLException erreur SQL.
     */
    public FcomplmtDAO(Connection connection)
            throws ClassNotFoundException, SQLException {

        this.connection = connection;

        setInvariableSelectStatement("select c6num, c6int2, c6alpha1, c6alpha2, c6name, c6access,"
                + " c6city, c6tel, c6alpha3, c6alpha4,"
                + " c6alpha5, c6alpha6, c6alpha7, c6alpha8, c6alpha9, c6alpha10,"
                + " c6int1, c6date, c6date1, c6int3, c6int4, c6onum,"
                + " c6corp, c6address, c6address2, c6poscode"
                + " from fcomplmt");
//        if (c6num > 0) {
//            Stmt.append(" where c6num = ").append(c6num);
//        }
//        Stmt.append(" order by c6num;");
//        setSelectStatement(Stmt.toString());
//        setSelectPreparedStatement();
////        System.out.println(Stmt);
//        setSelectResultSet();

        setUpdateStatement("update fcomplmt"
                + " set c6int2=?, c6alpha1=?, c6alpha2=?, c6name=?, c6access=?,"
                + " c6city=?, c6tel=?, c6alpha3=?, c6alpha4=?,"
                + " c6alpha5=?, c6alpha6=?, c6alpha7=?, c6alpha8=?, c6alpha9=?, c6alpha10=?,"
                + " c6int1=?, c6date=?, c6date1=?, c6int3=?, c6int4=?, c6onum=?,"
                + " c6corp=?, c6address=?, c6address2=?, c6poscode=?"
                + " where c6num=?;");
//        setUpdatePreparedStatement();

        setInsertStatement("insert into fcomplmt"
                + " (c6int2, c6alpha1, c6alpha2, c6name, c6access,"
                + " c6city, c6tel, c6alpha3, c6alpha4,"
                + " c6alpha5, c6alpha6, c6alpha7, c6alpha8, c6alpha9, c7alpha10,"
                + " c6int1, c6date, c6date1, c6int3, c6int4, c6onum,"
                + " c6corp, c6address, c6address2, c6poscode)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
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
        Fcomplmt fcomplmt = null;

        try {
            if (SelectResultSet.next()) {
                fcomplmt = new Fcomplmt();
                fcomplmt.setC6num(SelectResultSet.getInt("c6num"));
                fcomplmt.setC6int2(SelectResultSet.getInt("c6int2"));
                fcomplmt.setC6alpha1(SelectResultSet.getString("c6alpha1"));
                fcomplmt.setC6alpha2(SelectResultSet.getString("c6alpha2"));
                fcomplmt.setC6name(SelectResultSet.getString("c6name"));
                fcomplmt.setC6access(SelectResultSet.getString("c6access"));
                fcomplmt.setC6city(SelectResultSet.getString("c6city"));
                fcomplmt.setC6tel(SelectResultSet.getString("c6tel"));
                fcomplmt.setC6alpha3(SelectResultSet.getString("c6alpha3"));
                fcomplmt.setC6alpha4(SelectResultSet.getString("c6alpha4"));
                fcomplmt.setC6alpha5(SelectResultSet.getString("c6alpha5"));
                fcomplmt.setC6alpha6(SelectResultSet.getString("c6alpha6"));
                fcomplmt.setC6alpha7(SelectResultSet.getString("c6alpha7"));
                fcomplmt.setC6alpha8(SelectResultSet.getString("c6alpha8"));
                fcomplmt.setC6alpha9(SelectResultSet.getString("c6alpha9"));
                fcomplmt.setC6alpha10(SelectResultSet.getString("c6alpha10"));
                fcomplmt.setC6int1(SelectResultSet.getInt("c6int1"));
                fcomplmt.setC6date(SelectResultSet.getTimestamp("c6date"));
                fcomplmt.setC6date1(SelectResultSet.getTimestamp("c6date1"));
                fcomplmt.setC6int3(SelectResultSet.getInt("c6int3"));
                fcomplmt.setC6int4(SelectResultSet.getInt("c6int4"));
                fcomplmt.setC6onum(SelectResultSet.getInt("c6onum"));
                fcomplmt.setC6corp(SelectResultSet.getString("c6corp"));
                fcomplmt.setC6address(SelectResultSet.getString("c6address"));
                fcomplmt.setC6address(SelectResultSet.getString("c6address2"));
                fcomplmt.setC6poscode(SelectResultSet.getString("c6poscode"));
            } else {
                System.out.println("Lecture de fcomplmt terminée");
            }
        } catch (SQLException exception) {
            System.out.println("Erreur en lecture de fcomplmt " + exception.getMessage());
        }
        return fcomplmt;
    }

    /**
     * Met à jour un complément d'appel.
     *
     * @param fcomplmt complément d'appel à mettre à jour.
     */
    public void update(Fcomplmt fcomplmt) {
        try {
            UpdatePreparedStatement.setInt(1, fcomplmt.getC6int2());
            UpdatePreparedStatement.setString(2, fcomplmt.getC6alpha1());
            UpdatePreparedStatement.setString(3, fcomplmt.getC6alpha2());
            UpdatePreparedStatement.setString(4, fcomplmt.getC6name());
            UpdatePreparedStatement.setString(5, fcomplmt.getC6access());
            UpdatePreparedStatement.setString(6, fcomplmt.getC6city());
            UpdatePreparedStatement.setString(7, fcomplmt.getC6tel());
            UpdatePreparedStatement.setString(8, fcomplmt.getC6alpha3());
            UpdatePreparedStatement.setString(9, fcomplmt.getC6alpha4());
            UpdatePreparedStatement.setString(10, fcomplmt.getC6alpha5());
            UpdatePreparedStatement.setString(11, fcomplmt.getC6alpha6());
            UpdatePreparedStatement.setString(12, fcomplmt.getC6alpha7());
            UpdatePreparedStatement.setString(13, fcomplmt.getC6alpha8());
            UpdatePreparedStatement.setString(14, fcomplmt.getC6alpha9());
            UpdatePreparedStatement.setString(15, fcomplmt.getC6alpha10());
            UpdatePreparedStatement.setInt(16, fcomplmt.getC6int1());
            UpdatePreparedStatement.setTimestamp(17, fcomplmt.getC6date());
            UpdatePreparedStatement.setTimestamp(18, fcomplmt.getC6date1());
            UpdatePreparedStatement.setInt(19, fcomplmt.getC6int3());
            UpdatePreparedStatement.setInt(20, fcomplmt.getC6int4());
            UpdatePreparedStatement.setInt(21, fcomplmt.getC6onum());
            UpdatePreparedStatement.setString(22, fcomplmt.getC6corp());
            UpdatePreparedStatement.setString(23, fcomplmt.getC6address());
            UpdatePreparedStatement.setString(24, fcomplmt.getC6address2());
            UpdatePreparedStatement.setString(25, fcomplmt.getC6poscode());
            UpdatePreparedStatement.setInt(26, fcomplmt.getC6num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre à jour fcomplmt");
            }
        } catch (SQLException exception) {
            System.out.println("Erreur lors de la mise à jour de fcomplmt "
                    + exception.getMessage());
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
        } catch (SQLException exception) {
            System.out.println("Erreur lors de la suppression d'un complément d'appel dans fcomplmt "
                    + exception.getMessage());
        }
    }

    /**
     * Ajoute un complément d'appel dans la table fcomplmt.
     *
     * @param fcomplmt complément d'appel à ajouter à la table fcomplmt.
     */
    public void insert(Fcomplmt fcomplmt) {
//        ResultSet keyResultSet;

        try {
            System.out.println("c6alpha2=" + fcomplmt.getC6alpha2());
            InsertPreparedStatement.setInt(1, fcomplmt.getC6int2());
            InsertPreparedStatement.setString(2, fcomplmt.getC6alpha1());
            InsertPreparedStatement.setString(3, fcomplmt.getC6alpha2());
            InsertPreparedStatement.setString(4, fcomplmt.getC6name());
            InsertPreparedStatement.setString(5, fcomplmt.getC6access());
            InsertPreparedStatement.setString(6, fcomplmt.getC6city());
            InsertPreparedStatement.setString(7, fcomplmt.getC6tel());
            InsertPreparedStatement.setString(8, fcomplmt.getC6alpha3());
            InsertPreparedStatement.setString(9, fcomplmt.getC6alpha4());
            InsertPreparedStatement.setString(10, fcomplmt.getC6alpha5());
            InsertPreparedStatement.setString(11, fcomplmt.getC6alpha6());
            InsertPreparedStatement.setString(12, fcomplmt.getC6alpha7());
            InsertPreparedStatement.setString(13, fcomplmt.getC6alpha8());
            InsertPreparedStatement.setString(14, fcomplmt.getC6alpha9());
            InsertPreparedStatement.setString(15, fcomplmt.getC6alpha10());
            InsertPreparedStatement.setInt(16, fcomplmt.getC6int1());
            InsertPreparedStatement.setTimestamp(17, fcomplmt.getC6date());
            InsertPreparedStatement.setTimestamp(18, fcomplmt.getC6date1());
            InsertPreparedStatement.setInt(19, fcomplmt.getC6int3());
            InsertPreparedStatement.setInt(20, fcomplmt.getC6int4());
            InsertPreparedStatement.setInt(21, fcomplmt.getC6onum());
            InsertPreparedStatement.setString(22, fcomplmt.getC6corp());
            InsertPreparedStatement.setString(23, fcomplmt.getC6address());
            InsertPreparedStatement.setString(24, fcomplmt.getC6address2());
            InsertPreparedStatement.setString(25, fcomplmt.getC6poscode());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un complément d'appel dans fcomplmt");
            } else {
//                Does not work with Informix IDS2000
//                keyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (keyResultSet.next()) {
//                    fcomplmt.setC6num((int) keyResultSet.getInt(1));
//                }
//                keyResultSet.close();
                fcomplmt.setC6num(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException exception) {
            System.out.println("Erreur lors de l'insertion d'un complément d'appel dans fcomplmt " + exception.getMessage());
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
    public void update(Object object) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object object) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }
}
