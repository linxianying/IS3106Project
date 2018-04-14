/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Administrator;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Samango
 */

@XmlType(name = "retrieveAdminRsp", propOrder = {
    "admin"
})
public class RetrieveAdminRsp {
    private Administrator admin;

    public RetrieveAdminRsp(Administrator admin) {
        this.admin = admin;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }
    
    
}
