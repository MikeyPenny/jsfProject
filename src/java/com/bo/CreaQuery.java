/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.bean.Concepts;
import com.bean.FacturaBean;
import com.bean.SelectBean;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import com.dao.RemesaDAO;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class CreaQuery {
    
    public List<FacturaBean> crearSelect(SelectBean datos) {
        
        List<FacturaBean> facturas = new ArrayList<>();
        
        
        String query1 = "SELECT  DISTINCT A.id_factura, A.noFactura, A.importeFactura, A.antcpoFactura, A.contratista, "
                    + "A.origenFactura, A.tipoFactura, A.ivaFactura, " 
                    + "A.rtnFlete, A.rtnRenta, A.rtnGarantia, A.totalFactura, A.estatusFact, A.fechaFactura, "
                    + "A.id_foraneo, A.id_solicitud from factura AS A " 
                    + "inner join avance as B on B.id_avance = A.id_foraneo AND A.origenFactura = 'estima' " 
                    + "inner join avance_insumocontto as C on C.id_avance = B.id_avance " 
                    + "inner join contrato_insumo as D on D.idcontrato_insumo = C.idcontrato_insumo " 
                    + "inner join insumo as E on E.id_insumo = D.id_insumo " 
                    + "inner join concepto as F on F.id_concepto = E.id_concepto " 
                    + "inner join partida as G on G.id_partida = F.id_partida " 
                    + "inner join presupuesto as H on H.id_presupuesto = G.id_presupuesto " 
                    + "inner join proyecto as K on K.id_proyecto="+datos.getId_proyecto();
                    
                    //System.out.println(datos.getBeneficiario());
        
        if(!datos.getBeneficiario().equals("")) {
            //System.out.println(datos.getBeneficiario());
            query1 = query1 + " AND A.contratista='"+datos.getBeneficiario()+"'";
        }
        if(!datos.getFechaDe().equals("")) {
            query1 = query1 + " AND A.fechaFactura>='"+datos.getFechaDe()+"'";
        }
        if(!datos.getFechaA().equals("")) {
            query1 = query1 + " AND A.fechaFactura<='"+datos.getFechaA()+"'";
        }
        if(!datos.getFactura().equals("")) {
            query1 = query1 + " AND A.noFactura='"+datos.getFactura()+"'";
        }
        if(!datos.getEstatusFactura().equals("")) {
            query1 = query1 + " AND A.estatusFact='"+datos.getEstatusFactura()+"'";
        }
        if(!datos.getCuenta().equals("")) {
            query1 = query1 + " AND A.tipoFactura='"+datos.getCuenta()+"'";
        }
        if(datos.getId_solpago()!= 0) {
            query1 = query1 + " AND A.id_solicitud="+datos.getId_solpago();
        }
        
        String query2 = "SELECT  DISTINCT A.id_factura, A.noFactura, A.importeFactura, A.antcpoFactura, A.contratista, "
                    + "A.origenFactura, A.tipoFactura, A.ivaFactura, " 
                    + "A.rtnFlete, A.rtnRenta, A.rtnGarantia, A.totalFactura, A.estatusFact, A.fechaFactura, "
                    + "A.id_foraneo, A.id_solicitud from factura AS A " 
                    + "inner join anticipo as I on I.id_contrato = A.id_foraneo AND A.origenFactura = 'anticipo' " 
                    + "inner join contrato as J on J.id_contrato = I.id_contrato " 
                    + "inner join contrato_insumo as L on L.id_contrato = J.id_contrato " 
                    + "inner join insumo as M on M.id_insumo = L.id_insumo " 
                    + "inner join concepto as N on N.id_concepto = M.id_concepto " 
                    + "inner join partida as O on O.id_partida = N.id_partida " 
                    + "inner join presupuesto as P on P.id_presupuesto = O.id_presupuesto " 
                    + "inner join proyecto as K on K.id_proyecto="+datos.getId_proyecto();
        
        if(!datos.getBeneficiario().equals("")) {
            //System.out.println(datos.getBeneficiario());
            query2 = query2 + " AND A.contratista='"+datos.getBeneficiario()+"'";
        }
        if(!datos.getFechaDe().equals("")) {
            query2 = query2 + " AND A.fechaFactura>='"+datos.getFechaDe()+"'";
        }
        if(!datos.getFechaA().equals("")) {
            query2 = query2 + " AND A.fechaFactura<='"+datos.getFechaA()+"'";
        }
        if(!datos.getFactura().equals("")) {
            query2 = query2 + " AND A.noFactura='"+datos.getFactura()+"'";
        }
        if(!datos.getEstatusFactura().equals("")) {
            query2 = query2 + " AND A.estatusFact='"+datos.getEstatusFactura()+"'";
        }
        if(!datos.getCuenta().equals("")) {
            query2 = query2 + " AND A.tipoFactura='"+datos.getCuenta()+"'";
        }
        if(datos.getId_solpago()!= 0) {
            query2 = query2 + " AND A.id_solicitud="+datos.getId_solpago();
        }
        
        String query3 = "SELECT  DISTINCT A.id_factura, A.noFactura, A.importeFactura, A.antcpoFactura, A.contratista, "
                    + "A.origenFactura, A.tipoFactura, A.ivaFactura, " 
                    + "A.rtnFlete, A.rtnRenta, A.rtnGarantia, A.totalFactura, A.estatusFact, A.fechaFactura, "
                    + "A.id_foraneo, A.id_solicitud from factura AS A " 
                    + "inner join orden_compra as I on I.idorden_compra = A.id_foraneo AND A.origenFactura = 'compra' " 
                    + "inner join requisicion as J on J.id_requisicion = I.id_requisicion " 
                    + "inner join presupuesto as L on L.id_presupuesto = J.id_presupuesto " 
                    + "inner join proyecto as M on M.id_proyecto = L.id_proyecto " 
                    + "and M.id_proyecto="+datos.getId_proyecto();
        
        if(!datos.getBeneficiario().equals("")) {
            //System.out.println(datos.getBeneficiario());
            query3 = query3 + " AND A.contratista='"+datos.getBeneficiario()+"'";
        }
        if(!datos.getFechaDe().equals("")) {
            query3 = query3 + " AND A.fechaFactura>='"+datos.getFechaDe()+"'";
        }
        if(!datos.getFechaA().equals("")) {
            query3 = query3 + " AND A.fechaFactura<='"+datos.getFechaA()+"'";
        }
        if(!datos.getFactura().equals("")) {
            query3 = query3 + " AND A.noFactura='"+datos.getFactura()+"'";
        }
        if(!datos.getEstatusFactura().equals("")) {
            query3 = query3 + " AND A.estatusFact='"+datos.getEstatusFactura()+"'";
        }
        if(!datos.getCuenta().equals("")) {
            query3 = query3 + " AND A.tipoFactura='"+datos.getCuenta()+"'";
        }
        if(datos.getId_solpago()!= 0) {
            query3 = query3 + " AND A.id_solicitud="+datos.getId_solpago();
        }
        
        String query4 = "SELECT  DISTINCT A.id_factura, A.noFactura, A.importeFactura, A.antcpoFactura, A.contratista, "
                    + "A.origenFactura, A.tipoFactura, A.ivaFactura, " 
                    + "A.rtnFlete, A.rtnRenta, A.rtnGarantia, A.totalFactura, A.estatusFact, A.fechaFactura, "
                    + "A.id_foraneo, A.id_solicitud from factura AS A " 
                    + "inner join orden_compra as I on I.idorden_compra = A.id_foraneo AND A.origenFactura = 'antOC' " 
                    + "inner join requisicion as J on J.id_requisicion = I.id_requisicion " 
                    + "inner join presupuesto as L on L.id_presupuesto = J.id_presupuesto " 
                    + "inner join proyecto as M on M.id_proyecto = L.id_proyecto " 
                    + "and M.id_proyecto="+datos.getId_proyecto();
        
        if(!datos.getBeneficiario().equals("")) {
            //System.out.println(datos.getBeneficiario());
            query4 = query4 + " AND A.contratista='"+datos.getBeneficiario()+"'";
        }
        if(!datos.getFechaDe().equals("")) {
            query4 = query4 + " AND A.fechaFactura>='"+datos.getFechaDe()+"'";
        }
        if(!datos.getFechaA().equals("")) {
            query4 = query4 + " AND A.fechaFactura<='"+datos.getFechaA()+"'";
        }
        if(!datos.getFactura().equals("")) {
            query4 = query4 + " AND A.noFactura='"+datos.getFactura()+"'";
        }
        if(!datos.getEstatusFactura().equals("")) {
            query4 = query4 + " AND A.estatusFact='"+datos.getEstatusFactura()+"'";
        }
        if(!datos.getCuenta().equals("")) {
            query4 = query4 + " AND A.tipoFactura='"+datos.getCuenta()+"'";
        }
        if(datos.getId_solpago()!= 0) {
            query4 = query4 + " AND A.id_solicitud="+datos.getId_solpago();
        }
        
        Connection con;
        ConexionBD c = new ConexionBD();
        con = c.getConexion();
        RemesaDAO rem = new RemesaDAO(con);
        facturas = rem.filtrarBusqueda(query1, query2, query3, query4);
        
        
        return facturas;
    }
    
    public List<Concepts> selectConceptos(int id_proy, int id_pres, int id_part, int id_sub) {
    
        List<Concepts> list;
        
        String sql = "SELECT * FROM concepto AS A "
                    + "INNER JOIN partida AS B ON B.id_partida = A.id_partida "
                    + "INNER JOIN presupuesto AS C ON C.id_presupuesto = B.id_presupuesto "
                    + "INNER JOIN proyecto AS D ON  D.id_proyecto ="+id_proy+  " AND C.id_presupuesto ="+id_pres;
        
        
        if(id_part != 0 && id_sub == 0) {
            sql = "SELECT * FROM concepto AS A "
                    + "INNER JOIN partida AS B ON B.id_partida = A.id_partida "
                    + "INNER JOIN presupuesto AS C ON C.id_presupuesto = B.id_presupuesto "
                    + "INNER JOIN proyecto AS D ON  D.id_proyecto ="+id_proy+  " AND C.id_presupuesto ="+id_pres;
        }
        
        if(id_sub != 0) {
            sql = sql + " and B.id_partida ="+id_sub;
            
        }
        
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        list = dao.listaFiltradaDeConcepto(sql);
        
        return list;
        
    }
    
}
