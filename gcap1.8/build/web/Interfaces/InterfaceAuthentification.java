/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;
import pojoandmapping.*;
import java.util.List;


/**
 *
 * @author Arnaud
 */
public interface InterfaceAuthentification {
    public void save(Object inst);
    public void modify(Object inst);
    public void delete(Object inst);
    public List findAll();
    public Authentification findByLoginAndPassword(String login, String pass);
}
