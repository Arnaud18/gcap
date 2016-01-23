/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import implementationdao.TraiterDemCongeDao;
import javax.faces.context.FacesContext;
import pojoandmapping.Agtraitedemconge;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Arnaud
 */
public class NbDemAtraiteManagedBean {
 String nbDemATraite=null; 
 private DataModel<Agtraitedemconge> agTraiteDemConge;
    /**
     * Creates a new instance of NbDemAtraiteManagedBean
     */
    public NbDemAtraiteManagedBean() {
        nbDemATraite=new String();
        agTraiteDemConge = new ListDataModel();
    }
    public String getNbDemATraite() {
        return nbDemATraite;
    }

    public void setNbDemATraite(String nbDemATraite) {
        this.nbDemATraite = nbDemATraite;
    }
    public String returnNbDemATraite(){
        FacesContext fc = FacesContext.getCurrentInstance();
        ConnexionManagedBean connexionBean = (ConnexionManagedBean) fc.getExternalContext().getSessionMap().get("connexionManagedBean");
        System.out.println("La fonction est : "+connexionBean.getAgent().getFonction());
        if (connexionBean.getAgent().getFonction().equalsIgnoreCase("Directeur de direction")){
         agTraiteDemConge=new TraiterDemCongeDao().returnDemATraiteParDD(connexionBean.getUser());
         Integer nb=agTraiteDemConge.getRowCount();
         nbDemATraite=nb.toString();
    }
        if(connexionBean.getAgent().getFonction().equalsIgnoreCase("DRH")) {
             agTraiteDemConge=new TraiterDemCongeDao().returnDemATraiteParDRH();
             Integer nb=agTraiteDemConge.getRowCount();
             nbDemATraite=nb.toString();
             }
            
            if(connexionBean.getAgent().getFonction().equalsIgnoreCase("DG")){
                agTraiteDemConge=new TraiterDemCongeDao().returnDemATraiteParDG();
                Integer nb=agTraiteDemConge.getRowCount();
                nbDemATraite=nb.toString();
            }
            System.out.println("Le nombre de congé à traité est: "+nbDemATraite);
        return nbDemATraite;
        
}
}