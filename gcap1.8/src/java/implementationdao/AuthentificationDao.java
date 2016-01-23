/*
 * To change this license header, choose License Headers in Project 
Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implementationdao;
import pojoandmapping.*;
import hibernateUtil.HibernateUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.stat.SessionStatistics;

/**
 *
 * @author Arnaud
 */
public  class AuthentificationDao {
    private Authentification authentification;
    private Session session=null;
    private Agent agent=null;
    private Conge conge=null;

    public void Authentification(){ 
       authentification=new Authentification();
       agent=new Agent();
       conge=new Conge();
    }
  
public Authentification findUserByLoginPassword(String login,String pass){
    boolean bloque=false;
    session=HibernateUtil.getSessionFactory().openSession();
try { 
    session.beginTransaction();
    Query query = session.createQuery("from Authentification where idCompt =:login and password=:pass and bloque =:bloque");
    query.setParameter("login", login);
    query.setParameter("pass",pass);
    query.setParameter("bloque",bloque);
    authentification = (Authentification) query.uniqueResult();
    session.getTransaction().commit();
session.close();
return authentification;
} catch(Exception e){
    e.printStackTrace();
    return null;
}   
}  
public void bloqueCompte(Authentification user){
    Boolean bloque=true;
    session=HibernateUtil.getSessionFactory().openSession();
    try {
        user.setBloque(bloque);
        user.setIdCompt(user.getIdCompt());
        user.setPassword(user.getPassword());
        user.setAgent(user.getAgent());
        session.beginTransaction();
        session.update(user);
        System.out.println("Votre compte a été bloque veuillez contacter l'administrateur");
        session.getTransaction().commit();
        session.close();
        }
    catch(Exception e){
      System.out.println("Impossible de bloquer le compte");
    e.printStackTrace();
    
}
}
    
/*public Agent getUserConnecte(String login){
    Configuration config = new Configuration();
config.addClass(Authentification.class);
SessionFactory sessionFactory = config.buildSessionFactory();
Session session = sessionFactory.openSession();
Criteria criteria = session.createCriteria(Agent.class);
Criterion critere_1 = Restrictions.eq("idCompt", login);
criteria.add(critere_1);
List user = criteria.list();
Iterator it = user.iterator();
while(it.hasNext()) {
    agent=(Agent) it.next();
}
session.close();
sessionFactory.close();
 return agent;   
*/

public Conge returnNewNumCong()
 {
     String lastNumConge=null;
     String anneeEnCours=null;
     int nbDem=0;
     String newNumConge=null;
    try 
    {
    session=HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    Query query = session.createQuery("from Conge as Cong where Cong.numDemConge=(select max(a.numDemConge) from Conge as a)");
    conge = (Conge) query.uniqueResult();
    return conge;
    
}
 catch(Exception e){
     System.out.println("Echec de la requête!");
     e.printStackTrace();
    return null;
     
}
 } 

public Agent findAgentByMatricule(String matricAg){
        try 
    {
    session=HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    Query query = session.createQuery("from Agent where matricAg=:matricAg");
    query.setParameter("matricAg", matricAg);
    agent=(Agent)query.uniqueResult();
    return agent;
    }
    catch(Exception e){
     System.out.println("Echec de la requête!");
     e.printStackTrace();
    return null;
}

}
}