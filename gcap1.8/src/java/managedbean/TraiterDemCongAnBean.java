/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import com.sun.xml.xsom.impl.scd.Iterators;
import implementationdao.TraiterDemCongeDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import pojoandmapping.*;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Arnaud
 */
public class TraiterDemCongAnBean {
    private List<Conge> conge;
    private DataModel<Agtraitedemconge> agTraiteDemConge;
    private Agent selectedAgent=null;
    private Agtraitedemconge agTraiteDemCongSelect;
    FacesContext fc = FacesContext.getCurrentInstance();
    ConnexionManagedBean connexionBean = (ConnexionManagedBean) fc.getExternalContext().getSessionMap().get("connexionManagedBean");


    /**
     * Creates a new instance of TraiterDemCongAnBean
     */

    public TraiterDemCongAnBean() {
        conge = new ArrayList();
        agTraiteDemConge = new ListDataModel();
        agTraiteDemCongSelect=new Agtraitedemconge();
        selectedAgent=new Agent();
        
        
    }
    

    public Agent getSelectedAgent() {
        return selectedAgent;
    }

    public void setSelectedAgent(Agent selectedAgent) {
        this.selectedAgent = selectedAgent;
    }
   
    

    public Agtraitedemconge getAgTraiteDemCongSelect() {
        return agTraiteDemCongSelect;
    }

    public void setAgTraiteDemCongSelect(Agtraitedemconge agTraiteDemCongSelect) {
        this.agTraiteDemCongSelect = agTraiteDemCongSelect;
    }

    public List<Conge> getConge() {
        return conge;
    }

    public void setConge(List<Conge> conge) {
        this.conge = conge;
    }

    
    public DataModel<Agtraitedemconge> getAgTraiteDemConge() {
        return agTraiteDemConge;
    }

    public void setAgTraiteDemConge(DataModel<Agtraitedemconge> agTraiteDemConge) {
        this.agTraiteDemConge = agTraiteDemConge;
    }
    
 
    public  DataModel<Agtraitedemconge>returnDemATraite(){
        
       
        if (connexionBean.getAgent().getFonction().equalsIgnoreCase("Directeur de direction")){
         agTraiteDemConge=new TraiterDemCongeDao().returnDemATraiteParDD(connexionBean.getUser());
         System.out.println("Le directeur de direction est connecté et son nom est: "+connexionBean.getUser().getIdCompt());
         agTraiteDemCongSelect=this.getSelectedRow();
         }
        
            if(connexionBean.getAgent().getFonction().equalsIgnoreCase("DRH")) {
             agTraiteDemConge=new TraiterDemCongeDao().returnDemATraiteParDRH();
             agTraiteDemCongSelect=this.getSelectedRow();
             //this.obtenirDetailAgent();
             System.out.println("C'est Le DRH et sa fonction est "+ connexionBean.getAgent().getFonction());
             
             if (agTraiteDemConge!=null) System.out.println("Le congé a traité n'est pas NULL");
        }
            if(connexionBean.getAgent().getFonction().equalsIgnoreCase("DG")){
                agTraiteDemConge=new TraiterDemCongeDao().returnDemATraiteParDG();
                //this.obtenirDetailAgent();
                agTraiteDemCongSelect=this.getSelectedRow();
            }
        
        return agTraiteDemConge;
    }
    
  public String onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "Un commentaire sur une demande";
        }
        return null;
  }
  public void enregistreTraitement(){
   
       Agtraitedemconge agTraiteDemCongeSelected=new Agtraitedemconge();
       agTraiteDemCongeSelected=this.getSelectedRow();
       if(agTraiteDemCongeSelected.getAvis()==true){
           agTraiteDemCongeSelected.setCommentaire("Acceptation");
       }
       else {
           agTraiteDemCongeSelected.setCommentaire("Refus");
       }
       new TraiterDemCongeDao().miseAJourTraitement(agTraiteDemCongeSelected);
       FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, " Traitement effectué avec succès", "");
       FacesContext.getCurrentInstance().addMessage(null, msg);
       System.out.println("Effectué");
       
  }
 //Retourne les données de la line de DataTable selectionnée 
   public Agtraitedemconge getSelectedRow() {
       Agtraitedemconge agTraiteDemCongeSelected=new Agtraitedemconge();
       agTraiteDemCongeSelected = agTraiteDemConge.getRowData();
       //agTraiteDemCongSelect=agTraiteDemConge.getRowData();
       String matricAg=connexionBean.getAgent().getMatricAg();
       String numDemConge=agTraiteDemCongeSelected.getConge().getNumDemConge();
               
       AgtraitedemcongeId agTraiteDemCongeId=new AgtraitedemcongeId(matricAg, numDemConge);
       agTraiteDemCongeSelected.setId(agTraiteDemCongeId);
        return agTraiteDemCongeSelected;
    }
   
   public void obtenirDetailAgent(){
       
       Agtraitedemconge agTraiteDemCongeSelected=new Agtraitedemconge();
       agTraiteDemCongeSelected=this.getSelectedRow();
       //this.getSelectedRow();
       Service service=new Service();
       Direction direction=new Direction();
       
       String matricAg=agTraiteDemCongeSelected.getConge().getAgent().getMatricAg();
       String nomAg=agTraiteDemCongeSelected.getConge().getAgent().getNomAg();
       String prenAg=agTraiteDemCongeSelected.getConge().getAgent().getPrenAg();
       Date dateNaissance=agTraiteDemCongeSelected.getConge().getAgent().getDateNaissance();
       String profession=agTraiteDemCongeSelected.getConge().getAgent().getProfession();
       service=agTraiteDemCongeSelected.getConge().getAgent().getService();
       direction=agTraiteDemCongeSelected.getConge().getAgent().getService().getDirection();
       service.setDirection(direction);
       
       selectedAgent.setMatricAg(matricAg);
       selectedAgent.setNomAg(nomAg);
       selectedAgent.setPrenAg(prenAg);
       selectedAgent.setDateNaissance(dateNaissance);
       selectedAgent.setProfession(profession);
       selectedAgent.setService(service);
       System.out.println("Le nom de l'agent est: "+selectedAgent.getNomAg());
       //return agent;
   }
  
   
}
