/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedbean;
import implementationdao.AuthentificationDao;
import implementationdao.DemCongAnnDao;
import implementationdao.TraiterDemCongeDao;
import pojoandmapping.*;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * @author Arnaud
 */
public class ConnexionManagedBean implements Serializable {
    
    private Authentification user;  
    private String message=null;
    private Agent agent=null;
    private Conge conge=null;
    private String numDemCong=null;
    private String nbDemATraite=null;
    int nbEssai=0;
    int nbreJrCong=0;
        /**
     * @return the user
     */
    public Authentification getUser() {
        return user;
    }

    public int getNbreJrCong() {
        return nbreJrCong;
    }

    public void setNbreJrCong(int nbreJrCong) {
        this.nbreJrCong = nbreJrCong;
    }

    

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Authentification user) {
        this.user = user;
    }
    public String getNumDemCong() {
        return numDemCong;
    }

    public void setNumDemCong(String numDemCong) {
        this.numDemCong = numDemCong;
    }

    public String getNbDemATraite() {
        return nbDemATraite;
    }

    public void setNbDemATraite(String nbDemATraite) {
        this.nbDemATraite = nbDemATraite;
    }

    
    public ConnexionManagedBean() {
        user=new Authentification();
        conge=new Conge();
        agent=new Agent();
    }

public String getMessage(){
    return message;
}
public void setMessage(String message) {
this.message =message;
}

public void checkErrors(ComponentSystemEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if ("true".equals((String)request.getParameter("failed"))) {
            /* GET parameter "failed" has been sent in the HTTP request... */
            context.addMessage(null, new FacesMessage("Login failed!"));
        }
        else if (request.getRequestedSessionId()!=null && !request.isRequestedSessionIdValid()) {
            /* The user session has timed out... */
            context.addMessage(null, new FacesMessage("Votre session a expiré!"));
        }
    }

    

    
    public String connecter() throws IOException  {
    boolean connecte;
            System.out.println("Je suis connecté");
            //user.setPassword(md5(user.getPassword()));
            System.out.println("Mon login 1 est :"+user.getIdCompt());
        if(new AuthentificationDao().findUserByLoginPassword(user.getIdCompt(),user.getPassword())!=null){
        user=new AuthentificationDao().findUserByLoginPassword(user.getIdCompt(),user.getPassword());
        //this.findNumConge();
        this.returnNombreDeJourCong();
        agent=new AuthentificationDao().findAgentByMatricule(user.getIdCompt());
        this.returnNbDemATraite();
        System.out.println("Le nom de l'agent est :"+agent.getNomAg());
        nbEssai = 0;
        message="Connexion réussie";
        
        if (user.getTypeCompte().equalsIgnoreCase("manager")){
            if(agent.getFonction().equalsIgnoreCase("Chef de service") || agent.getFonction().equalsIgnoreCase("Directeur de direction")) 
            return "CSouDD";
            else if(agent.getFonction().equalsIgnoreCase("DRH")) return "manager";
            else return "DG";
        }
        else  return "agent";
    }
    else {
            
            System.out.println("Impossible de se connecter login et mot de passe: "+user.getIdCompt()+" "+user.getPassword());
       nbEssai++;
       System.out.println("essai " + nbEssai);
       
       if(nbEssai==3){
           new AuthentificationDao().bloqueCompte(user);
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Compte bloqué!",""));    
       }
       if (nbEssai==2) {
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Attention! 1 essai restant",""));    
       }
       if(nbEssai==1){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Login ou password incorrect!",""));
       }
       return "invalide";             
    }
    
        
    }
        
    
    public void addInfo(ActionEvent actionEvent) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sample info message", "PrimeFaces rocks!"));  
    }  
  
    public void addWarn(ActionEvent actionEvent) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Sample warn message", "Watch out for PrimeFaces!"));  
    }  
  
    public void addError(ActionEvent actionEvent) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mot de passe incorrect", "PrimeFaces makes no mistakes"));  
    }  
  
    public void addFatal(ActionEvent actionEvent) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Sample fatal message", "Fatal Error in System"));  
    }
    
    
    //cryptage en md5 du mot de passe
    public String md5(String password) {
        byte[] uniqueKey = password.getBytes();
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        } catch (NoSuchAlgorithmException e) {
            // throw new Error("No MD5 support in this VM.");
            e.printStackTrace();
        }
    StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            } else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }
        System.out.println("taille " + hashString.length());
        return hashString.toString();
    }
    public void findNumConge(){
        String dernierNumCong=null;
        String inter1=null;
        String inter2=null;
        String tiret="-";
        int numDemEntier=0;
        String anneeEnCours=null;
        
        //Recupération de la date en cours
        Calendar calendar = Calendar.getInstance();
        int annee = calendar.get(Calendar.YEAR);
        int mois= calendar.get(Calendar.MONTH);
        int jour= calendar.get(Calendar.DAY_OF_MONTH);
        String numCong=null;
        conge=new AuthentificationDao().returnNewNumCong();
        dernierNumCong=conge.getNumDemConge();
        dernierNumCong=dernierNumCong.substring(5);
        numDemEntier=Integer.parseInt(dernierNumCong);
        System.out.println("Le dernier numéro de demande en entier: "+numDemEntier);
        numDemEntier=numDemEntier+1;
        if(mois==1 && jour==1) numDemEntier=0;
        inter1=String.valueOf(numDemEntier);
        anneeEnCours=String.valueOf(annee);
        System.out.println("Le prochain numéro de demande en string: "+inter1);
        inter2=anneeEnCours.concat(tiret);
        System.out.println("La concaténation avec tiret: "+inter2);
        numDemCong=inter2.concat(inter1);
        
    }
    
    
    public void returnNbDemATraite(){
        DataModel<Agtraitedemconge> agTraiteDemConge;
        agTraiteDemConge=new ListDataModel();
        FacesContext fc = FacesContext.getCurrentInstance();
        if (agent.getFonction().equalsIgnoreCase("Directeur de direction")){
         agTraiteDemConge=new TraiterDemCongeDao().returnDemATraiteParDD(user);
         Integer nb=agTraiteDemConge.getRowCount();
         nbDemATraite=nb.toString();
    }
        if(agent.getFonction().equalsIgnoreCase("DRH")) {
             agTraiteDemConge=new TraiterDemCongeDao().returnDemATraiteParDRH();
             Integer nb=agTraiteDemConge.getRowCount();
             nbDemATraite=nb.toString();
             }
            
            if(agent.getFonction().equalsIgnoreCase("DG")){
                agTraiteDemConge=new TraiterDemCongeDao().returnDemATraiteParDG();
                Integer nb=agTraiteDemConge.getRowCount();
                nbDemATraite=nb.toString();
            }
            
        
        
}
    public void returnNbNotificationDemTraite(){
        
    }

    public String deconnecter() {
try {
ExternalContext ExtContext =FacesContext.getCurrentInstance().getExternalContext();
ExtContext.getSessionMap().clear();
} catch(Exception ex) {
ex.printStackTrace();
}
return "/connexion.xhtml?faces-redirect=true";
}
    
    
  public void returnNombreDeJourCong(){
        
        //Declaration de variables
        
        
        String matricAg=null;
        
        FacesContext fc = FacesContext.getCurrentInstance();
        ConnexionManagedBean connexionBean = (ConnexionManagedBean)fc.getExternalContext().getSessionMap().get("connexionManagedBean");
        user=connexionBean.getUser();
        matricAg=user.getIdCompt();
        nbreJrCong= new DemCongAnnDao().returnNombreDeJourCong(user);
        
    }
  
}
