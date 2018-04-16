/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Administrator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Samango
 */

@XmlRootElement
@XmlType(name = "updateAdminReq", propOrder = {
    "admin"
})
public class UpdateAdminReq {

    private Administrator admin;
    
    public UpdateAdminReq() {
    }

    public UpdateAdminReq(Administrator admin) {
        this.admin = admin;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }
    
    
}
