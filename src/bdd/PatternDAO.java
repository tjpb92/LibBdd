package bdd;

import java.sql.*;

/**
 * Classe qui décrit le pattern DAO permettant l'accès aux bases de données.
 * Cette classe remplace la classe PaternDAO.
 *
 * @author Thierry Baribaud
 * @version Juillet 2016
 */
public abstract class PatternDAO {

    /**
     * Connexion à la base de données.
     */
    protected Connection MyConnection;

    /**
     * Nombre de rangées affectées par une opération du type
     * insert/update/delete.
     */
    protected int nbAffectedRow;

    /**
     * Partie invariable de la requête SQL utilisée pour les sélections.
     */
    protected String InvariableSelectStatement;

    /**
     * Requête SQL utilisée pour les sélections.
     */
    protected String SelectStatement;

    /**
     * Requête SQL utilisée pour les mises à jour.
     */
    protected String UpdateStatement;

    /**
     * Requête SQL utilisé pour les insertions.
     */
    protected String InsertStatement;

    /**
     * Requête SQL utilisée pour les suppressions.
     */
    protected String DeleteStatement;

    /**
     * Requête SQL préparée pour sélectionner des enregistrements.
     */
    protected PreparedStatement SelectPreparedStatement;

    /**
     * Requête SQL préparée pour mettre à jour des enregistrements.
     */
    protected PreparedStatement UpdatePreparedStatement;

    /**
     * Requête SQL préparée pour ajouter des enregistrements.
     */
    protected PreparedStatement InsertPreparedStatement;

    /**
     * Requête SQL préparée pour supprimmer des enregistrements.
     */
    protected PreparedStatement DeletePreparedStatement;

    /**
     * ResultSet.
     */
    protected ResultSet SelectResultSet;

    /**
     * @param nbAffectedRow définit le nombre de rangées affectées par une 
     * opération du type insert/update/delete.
     */
    protected void setNbAffectedRow(int nbAffectedRow) {
        this.nbAffectedRow = nbAffectedRow;
    }

    /**
     * @param Statement définit la partie invariable de la requête SQL pour 
     * sélectionner des données.
     */
    public void setInvariableSelectStatement(String Statement) {
        this.InvariableSelectStatement = Statement;
    }

    /**
     * @param Statement définit la requête SQL pour sélectionner des données.
     */
    public void setSelectStatement(String Statement) {
        this.SelectStatement = Statement;
    }

    /**
     * @param Statement définit la requête SQL pour mettre à jour des données.
     */
    public void setUpdateStatement(String Statement) {
        this.UpdateStatement = Statement;
    }

    /**
     * @param Statement définit la requête SQL pour insérer des données.
     */
    public void setInsertStatement(String Statement) {
        this.InsertStatement = Statement;
    }

    /**
     * @param Statement définit la requête SQL pour supprimer des données.
     */
    public void setDeleteStatement(String Statement) {
        this.DeleteStatement = Statement;
    }

    /**
     * @return le nombre de rangées affectées par une opération du type
     * insert/update/delete.
     */
    public int getNbAffectedRow() {
        return nbAffectedRow;
    }

    /**
     * @return la partie invariable de la requête SQL pour sélectionner des 
     * enregistrements.
     */
    public String getInvariableSelectStatement() {
        return InvariableSelectStatement;
    }

    /**
     * @return la requête SQL pour sélectionner des enregistrements.
     */
    public String getSelectStatement() {
        return SelectStatement;
    }

    /**
     * @return la requête SQL pour mettre à jour des enregistrements.
     */
    public String getUpdateStatement() {
        return UpdateStatement;
    }

    /**
     * @return la requête SQL pour insérer des enregistrements.
     */
    public String getInsertStatement() {
        return InsertStatement;
    }

    /**
     * @return la requête SQL pour supprimer des enregistrements.
     */
    public String getDeleteStatement() {
        return DeleteStatement;
    }

    /**
     * Prepare la requête SQL pour sélectionner des données ainsi que le 
     * ResultSet associé.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setSelectPreparedStatement() throws SQLException {
        SelectPreparedStatement = MyConnection.prepareStatement(getSelectStatement());
        setSelectResultSet();
    }

    /**
     * Prepare la requête SQL pour mettre à jour des données.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setUpdatePreparedStatement() throws SQLException {
        UpdatePreparedStatement = MyConnection.prepareStatement(getUpdateStatement());
    }

    /**
     * Prepare la requête SQL pour insérer des données.
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
     * Prepare la requête SQL pour supprimer des données.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setDeletePreparedStatement() throws SQLException {
        DeletePreparedStatement = MyConnection.prepareStatement(getDeleteStatement());
    }

    /**
     * Associe les résultats d'une requête SQL à un ResultSet.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setSelectResultSet() throws SQLException {
        SelectResultSet = SelectPreparedStatement.executeQuery();
    }

    /**
     * Sélectionne un enregistrement.
     *
     * @return l'object sélectionné.
     */
    abstract public Object select();

    /**
     * Met à jour un enregistrement.
     *
     * @param MyObject object à mettre à jour.
     */
    abstract public void update(Object MyObject);

    /**
     * Supprime un enregistrement.
     *
     * @param MyId identifiant de l'objet.
     */
    abstract public void delete(int MyId);

    /**
     * Insère un enregistrement.
     *
     * @param MyObject Object à insérer.
     */
    abstract public void insert(Object MyObject);
//  abstract public void insertTb(Object MyObject);

    /**
     * Méthode pour fermer les ressources associées à la requête de sélection
     * des enregistrements.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeSelectPreparedStatement() throws SQLException {
        SelectResultSet.close();
        SelectPreparedStatement.close();
    }

    /**
     * Méthode pour fermer les ressources associées à la requête de mise à jour
     * des enregistrements.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeUpdatePreparedStatement() throws SQLException {
        UpdatePreparedStatement.close();
    }

    /**
     * Méthode pour fermer les ressources associées à la requête d'insertion des
     * enregistrements.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeInsertPreparedStatement() throws SQLException {
        InsertPreparedStatement.close();
    }

    /**
     * Méthode pour fermer les ressources associées à la requête de suppression
     * des enregistrements.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeDeletePreparedStatement() throws SQLException {
        DeletePreparedStatement.close();
    }

    /**
     * Méthode pour fermer toutes les ressources utilisées. Discutable, contenu
     * inhibé pour l'instant, TB, le 26/06/2016.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void close() throws SQLException {

//        closeSelectPreparedStatement();
//        closeUpdatePreparedStatement();
//        closeInsertPreparedStatement();
//        closeDeletePreparedStatement();
        throw new UnsupportedOperationException("Non supporté actuellement");
    }

    /**
     * Méthode pour filter les résultats par identifiant.
     *
     * @param id l'identifiant à utiliser pour le filtrage.
     */
    public abstract void filterById(int id);

    /**
     * Méthode pour filter les résultats par identifiant de groupe.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     */
    public abstract void filterByGid(int gid);

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par code.
     *
     * @param gid l'identifiant du groupe à utiliser pour le filtrage.
     * @param Code à utiliser pour le filtrage.
     */
    public abstract void filterByCode(int gid, String Code);

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par nom.
     *
     * @param gid l'identifiant du groupe à utiliser pour le filtrage.
     * @param Name à utiliser pour le filtrage.
     */
    public abstract void filterByName(int gid, String Name);
    
    /**
     * Méthode qui rajoute une clause ORDER BY à la requête SQL.
     * @param FieldsList liste des champs servant à trier les résultats de la requête.
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
