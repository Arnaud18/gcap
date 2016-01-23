/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import hibernateUtil.HibernateUtil;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import pojoandmapping.Agtraitedemconge;
import implementationdao.TraiterDemCongeDao;
import org.hibernate.Query;
import org.hibernate.Session;
import pojoandmapping.ArchiveConge;

/**
 *
 * @author Arnaud
 */
public class ReponseTraitementManagedBean {
    private DataModel<Agtraitedemconge> agTraiteDemConge;
    FacesContext fc = FacesContext.getCurrentInstance();
    private Session session=null;
    ConnexionManagedBean connexionBean = (ConnexionManagedBean) fc.getExternalContext().getSessionMap().get("connexionManagedBean");
    /**
     * Creates a new instance of ReponseTraitementManagedBean
     */
    public ReponseTraitementManagedBean() {
        agTraiteDemConge = new ListDataModel();
    }

    public DataModel<Agtraitedemconge> getAgTraiteDemConge() {
        return agTraiteDemConge;
    }

    public void setAgTraiteDemConge(DataModel<Agtraitedemconge> agTraiteDemConge) {
        this.agTraiteDemConge = agTraiteDemConge;
    }
    
    public DataModel<Agtraitedemconge> returnReponseDeTraitement(){
        agTraiteDemConge=new TraiterDemCongeDao().returnReponseDeTraitement(connexionBean.getUser());
        
        return agTraiteDemConge;
    }
    
    public int returnNbNotification(){
        int nbNotification=0;
        this.returnReponseDeTraitement();
        nbNotification=agTraiteDemConge.getRowCount();
        return nbNotification;
    }
    
    public String enregistreDemVu(){
     ArchiveConge archiveConge=new ArchiveConge();
     archiveConge.setNumDemConge(agTraiteDemConge.getRowData().getConge().getNumDemConge());
        new TraiterDemCongeDao().enregistreDemVu(archiveConge);
        
         return "reponse_traitement";
        
}
    public String returnColor(String commentaire){
        if (commentaire.compareTo("Acceptation")==0)
        return "green";
        else return "red";
    }
}