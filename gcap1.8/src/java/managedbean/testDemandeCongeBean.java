/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import pojoandmapping.Conge;

/**
 *
 * @author Arnaud
 */
public class testDemandeCongeBean {
    
    
private Conge conge=null;
    /**
     * Creates a new instance of testDemandeCongeBean
     */
    public testDemandeCongeBean() {
        conge=new Conge();
    }

    public Conge getConge() {
        return conge;
    }

    public void setConge(Conge conge) {
        this.conge = conge;
    }
    public void nouvelleDemandeConge(){
        conge.setVilleJouissance("Bobo-Dioulasso");
        System.out.println("Le numéro de congé est: "+conge.getNumDemConge());
        System.out.println("La ville de jouissance: "+conge.getVilleJouissance());
    }
}
