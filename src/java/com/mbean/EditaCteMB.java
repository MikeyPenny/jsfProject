
package com.mbean;

import com.bean.ClienteAltaBean;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("editCte")
@RequestScoped
public class EditaCteMB implements Serializable {
    
    private ClienteAltaBean cte;
    
    
    

    /**
     * @return the cte
     */
    public ClienteAltaBean getCte() {
        return cte;
    }

    /**
     * @param cte the cte to set
     */
    public void setCte(ClienteAltaBean cte) {
        this.cte = cte;
    }
    
    
    
}
