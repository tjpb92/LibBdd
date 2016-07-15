package bdd;

import java.sql.*;

/**
 * Classe qui d�crit le pattern DAO permettant l'acc�s aux bases de donn�es.
 * Cette classe remplace la classe PaternDAO.
 *
 * @author Thierry Baribaud
 * @version Juillet 2016
 */
public abstract class PatternDAO {

    /**
     * Connexion � la base de donn�es.
     */
    protected Connection MyConnection;

    /**
     * Nombre de rang�es affect�es par une op�ration du type
     * insert/update/delete.
     */
    protected int nbAffectedRow;

    /**
     * Partie invariable de la requ�te SQL utilis�e pour les s�lections.
     */
    protected String InvariableSelectStatement;

    /**
     * Requ�te SQL utilis�e pour les s�lections.
     */
    protected String SelectStatement;

    /**
     * Requ�te SQL utilis�e pour les mises � jour.
     */
    protected String UpdateStatement;

    /**
     * Requ�te SQL utilis� pour les insertions.
     */
    protected String InsertStatement;

    /**
     * Requ�te SQL utilis�e pour les suppressions.
     */
    protected String DeleteStatement;

    /**
     * Requ�te SQL pr�par�e pour s�lectionner des enregistrements.
     */
    protected PreparedStatement SelectPreparedStatement;

    /**
     * Requ�te SQL pr�par�e pour mettre � jour des enregistrements.
     */
    protected PreparedStatement UpdatePreparedStatement;

    /**
     * Requ�te SQL pr�par�e pour ajouter des enregistrements.
     */
    protected PreparedStatement InsertPreparedStatement;

    /**
     * Requ�te SQL pr�par�e pour supprimmer des enregistrements.
     */
    protected PreparedStatement DeletePreparedStatement;

    /**
     * ResultSet.
     */
    protected ResultSet SelectResultSet;

    /**
     * @param nbAffectedRow d�finit le nombre de rang�es affect�es par une 
     * op�ration du type insert/update/delete.
     */
    protected void setNbAffectedRow(int nbAffectedRow) {
        this.nbAffectedRow = nbAffectedRow;
    }

    /**
     * @param Statement d�finit la partie invariable de la requ�te SQL pour 
     * s�lectionner des donn�es.
     */
    public void setInvariableSelectStatement(String Statement) {
        this.InvariableSelectStatement = Statement;
    }

    /**
     * @param Statement d�finit la requ�te SQL pour s�lectionner des donn�es.
     */
    public void setSelectStatement(String Statement) {
        this.SelectStatement = Statement;
    }

    /**
     * @param Statement d�finit la requ�te SQL pour mettre � jour des donn�es.
     */
    public void setUpdateStatement(String Statement) {
        this.UpdateStatement = Statement;
    }

    /**
     * @param Statement d�finit la requ�te SQL pour ins�rer des donn�es.
     */
    public void setInsertStatement(String Statement) {
        this.InsertStatement = Statement;
    }

    /**
     * @param Statement d�finit la requ�te SQL pour supprimer des donn�es.
     */
    public void setDeleteStatement(String Statement) {
        this.DeleteStatement = Statement;
    }

    /**
     * @return le nombre de rang�es affect�es par une op�ration du type
     * insert/update/delete.
     */
    public int getNbAffectedRow() {
        return nbAffectedRow;
    }

    /**
     * @return la partie invariable de la requ�te SQL pour s�lectionner des 
     * enregistrements.
     */
    public String getInvariableSelectStatement() {
        return InvariableSelectStatement;
    }

    /**
     * @return la requ�te SQL pour s�lectionner des enregistrements.
     */
    public String getSelectStatement() {
        return SelectStatement;
    }

    /**
     * @return la requ�te SQL pour mettre � jour des enregistrements.
     */
    public String getUpdateStatement() {
        return UpdateStatement;
    }

    /**
     * @return la requ�te SQL pour ins�rer des enregistrements.
     */
    public String getInsertStatement() {
        return InsertStatement;
    }

    /**
     * @return la requ�te SQL pour supprimer des enregistrements.
     */
    public String getDeleteStatement() {
        return DeleteStatement;
    }

    /**
     * Prepare la requ�te SQL pour s�lectionner des donn�es ainsi que le 
     * ResultSet associ�.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setSelectPreparedStatement() throws SQLException {
        SelectPreparedStatement = MyConnection.prepareStatement(getSelectStatement());
        setSelectResultSet();
    }

    /**
     * Prepare la requ�te SQL pour mettre � jour des donn�es.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setUpdatePreparedStatement() throws SQLException {
        UpdatePreparedStatement = MyConnection.prepareStatement(getUpdateStatement());
    }

    /**
     * Prepare la requ�te SQL pour ins�rer des donn�es.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setInsertPreparedStatement() throws SQLException {
//        System.out.println("  InsertStatement=" + getInsertStatement());
        InsertPreparedStatement = MyConnection.prepareStatement(getInsertStatement());

//  Does not work with Informix IDS2000
//  public void setInsertPreparedStatement() throws SQLException {
//    InsertPreparedStatement = MyConnection.prepareStatement(getInsertStatement(),
//      PreparedStatement.RETURN_GENERATED_KEYS);
    }

    /**
     * Prepare la requ�te SQL pour supprimer des donn�es.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setDeletePreparedStatement() throws SQLException {
        DeletePreparedStatement = MyConnection.prepareStatement(getDeleteStatement());
    }

    /**
     * Associe les r�sultats d'une requ�te SQL � un ResultSet.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setSelectResultSet() throws SQLException {
        SelectResultSet = SelectPreparedStatement.executeQuery();
    }

    /**
     * S�lectionne un enregistrement.
     *
     * @return l'object s�lectionn�.
     */
    abstract public Object select();

    /**
     * Met � jour un enregistrement.
     *
     * @param MyObject object � mettre � jour.
     */
    abstract public void update(Object MyObject);

    /**
     * Supprime un enregistrement.
     *
     * @param MyId identifiant de l'objet.
     */
    abstract public void delete(int MyId);

    /**
     * Ins�re un enregistrement.
     *
     * @param MyObject Object � ins�rer.
     */
    abstract public void insert(Object MyObject);
//  abstract public void insertTb(Object MyObject);

    /**
     * M�thode pour fermer les ressources associ�es � la requ�te de s�lection
     * des enregistrements.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeSelectPreparedStatement() throws SQLException {
        SelectResultSet.close();
        SelectPreparedStatement.close();
    }

    /**
     * M�thode pour fermer les ressources associ�es � la requ�te de mise � jour
     * des enregistrements.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeUpdatePreparedStatement() throws SQLException {
        UpdatePreparedStatement.close();
    }

    /**
     * M�thode pour fermer les ressources associ�es � la requ�te d'insertion des
     * enregistrements.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeInsertPreparedStatement() throws SQLException {
        InsertPreparedStatement.close();
    }

    /**
     * M�thode pour fermer les ressources associ�es � la requ�te de suppression
     * des enregistrements.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeDeletePreparedStatement() throws SQLException {
        DeletePreparedStatement.close();
    }

    /**
     * M�thode pour fermer toutes les ressources utilis�es. Discutable, contenu
     * inhib� pour l'instant, TB, le 26/06/2016.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void close() throws SQLException {

//        closeSelectPreparedStatement();
//        closeUpdatePreparedStatement();
//        closeInsertPreparedStatement();
//        closeDeletePreparedStatement();
        throw new UnsupportedOperationException("Non support� actuellement");
    }

    /**
     * M�thode pour filter les r�sultats par identifiant.
     *
     * @param id l'identifiant � utiliser pour le filtrage.
     */
    public abstract void filterById(int id);

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe.
     *
     * @param gid l'identifiant de groupe � utiliser pour le filtrage.
     */
    public abstract void filterByGid(int gid);

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe et par code.
     *
     * @param gid l'identifiant du groupe � utiliser pour le filtrage.
     * @param Code � utiliser pour le filtrage.
     */
    public abstract void filterByCode(int gid, String Code);

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe et par nom.
     *
     * @param gid l'identifiant du groupe � utiliser pour le filtrage.
     * @param Name � utiliser pour le filtrage.
     */
    public abstract void filterByName(int gid, String Name);
    
    /**
     * M�thode qui rajoute une clause ORDER BY � la requ�te SQL.
     * @param FieldsList liste des champs servant � trier les r�sultats de la requ�te.
     */
    public void orderBy(String FieldsList) {
        StringBuffer Stmt;
        int len;
        String OrderByClause;
        
        OrderByClause = " order by " + FieldsList;
        if (SelectStatement != null) {
            Stmt = new StringBuffer(SelectStatement);
        }
        else {
            Stmt = new StringBuffer(InvariableSelectStatement);
        }
        len = Stmt.length();
        System.out.println("Stmt=" + Stmt + ", len=" + len);
        if (Stmt.charAt(len - 1) == ';') {
            Stmt.insert(len - 1, OrderByClause);
        }
        else {
            Stmt.append(OrderByClause).append(";");
        }
        setSelectStatement(Stmt.toString());
    }
}
