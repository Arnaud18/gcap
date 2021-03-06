package pojoandmapping;
// Generated 23 juin 2014 16:20:08 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Service generated by hbm2java
 */
public class Service  implements java.io.Serializable {


     private String codeService;
     private Direction direction;
     private String nomService;
     private Integer nbAgentService;
     private Set agents = new HashSet(0);

    public Service() {
    }

	
    public Service(String codeService) {
        this.codeService = codeService;
    }
    public Service(String codeService, Direction direction, String nomService, Integer nbAgentService, Set agents) {
       this.codeService = codeService;
       this.direction = direction;
       this.nomService = nomService;
       this.nbAgentService = nbAgentService;
       this.agents = agents;
    }
   
    public String getCodeService() {
        return this.codeService;
    }
    
    public void setCodeService(String codeService) {
        this.codeService = codeService;
    }
    public Direction getDirection() {
        return this.direction;
    }
    
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public String getNomService() {
        return this.nomService;
    }
    
    public void setNomService(String nomService) {
        this.nomService = nomService;
    }
    public Integer getNbAgentService() {
        return this.nbAgentService;
    }
    
    public void setNbAgentService(Integer nbAgentService) {
        this.nbAgentService = nbAgentService;
    }
    public Set getAgents() {
        return this.agents;
    }
    
    public void setAgents(Set agents) {
        this.agents = agents;
    }




}


