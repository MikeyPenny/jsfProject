
package com.bo;

import com.bean.PagoBean;
import com.conexion.ConexionBD;
import com.dao.FacturaDAO;
import java.sql.Connection;


public class CanalizaPago {
    
    public int ubicarPago(PagoBean pb) {
        
        int id_solicitudRegistrada = 0;
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        FacturaDAO fd = new FacturaDAO(con);
        
        if(pb.getOrigen().equals("anticipo")) {
            id_solicitudRegistrada = fd.registrarFacturaAnticipo(pb);
        }
        if(pb.getOrigen().equals("estima")) {
            id_solicitudRegistrada = fd.resgistrarFacturaEstimacion(pb);
        }
        if(pb.getOrigen().equals("compra")) {
            id_solicitudRegistrada = fd.resgistrarFacturaOrdenCompra(pb);
        }
        if(pb.getOrigen().equals("antOC")) {
            id_solicitudRegistrada = fd.resgistrarFacturaOrdenCompra(pb);
        }
        
        return id_solicitudRegistrada;
        
    }
    
}
