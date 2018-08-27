/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.bean.AcumuladosBean;
import com.bean.InsumoContrat;
import com.mbean.DetalleOrdenCompraMB;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Cantidades {
    
    public List<InsumoContrat> ajustarCantidades(List<InsumoContrat> lista) {
        
        List<InsumoContrat> listIns = new ArrayList<>();
        
        for(InsumoContrat aux: lista) {
            aux.setCantCtrl(aux.getCantCtrl().add(aux.getCantContrato()));
            //aux.setCantContrato(null);
            System.out.println("Cant Ctrl Ajus "+aux.getCantCtrl());
            aux.setImporteCont(null);
            listIns.add(aux);
        }
        
        return listIns;
    }
    
     public BigDecimal calcularEstimado(List<InsumoContrat> lista) {
         
         BigDecimal sumaEstimado = BigDecimal.ZERO;
         
         for(InsumoContrat aux: lista) {
             sumaEstimado = sumaEstimado.add(aux.getPresUnit().multiply(aux.getSumaAvance()));
             
         }
         
         return sumaEstimado;
     }
     
     public BigDecimal calcularPorEstimar(BigDecimal importe, BigDecimal estimado) {
         BigDecimal porEstimar = importe.subtract(estimado);
         if(porEstimar.floatValue() == 0.0) {
             porEstimar = BigDecimal.ZERO;
         }
         return porEstimar;
     }
     
     public BigDecimal sumarAvance(List<InsumoContrat> lista) {
         BigDecimal sumaAvance = BigDecimal.ZERO;
         
         for(InsumoContrat aux: lista) {
             if(aux.getAvance() != null) {
                 sumaAvance =  sumaAvance.add(aux.getPresUnit().multiply(aux.getAvance()));
             }
         }
         
         return sumaAvance;
     }
     
     public List<InsumoContrat> configurarImportesAvances(List<InsumoContrat> listSelec) {
         
         List<InsumoContrat> lstAvnImp = new ArrayList<InsumoContrat>();
         
         for(InsumoContrat aux: listSelec) {
             aux.setImporteAvnce(aux.getPresUnit().multiply(aux.getAvance()));
             lstAvnImp.add(aux);
         }
                 
         return lstAvnImp;
     }
     
     public AcumuladosBean evitarNulos(AcumuladosBean acum) {
         
         if(acum.getAcumImporte() == null) {
             acum.setAcumImporte(BigDecimal.ZERO);
         }
         if(acum.getAcumAmort()== null) {
             acum.setAcumAmort(BigDecimal.ZERO);
         }
         if(acum.getAcumGtia()== null) {
             acum.setAcumGtia(BigDecimal.ZERO);
         }
         
         return acum;
     }
     
     public BigDecimal calcularRenta(BigDecimal importe) {
        
        BigDecimal ivaAntcpo = importe.multiply(BigDecimal.valueOf(.16));
        BigDecimal rtnRenta = BigDecimal.ZERO;
        float div = ivaAntcpo.floatValue()/3;
        float mult = div * 2;
        rtnRenta = BigDecimal.valueOf(mult);
        
        return rtnRenta;
     }
     
     public BigDecimal calcularFlete(BigDecimal importe) {
        
        BigDecimal ivaAntcpo = importe.multiply(BigDecimal.valueOf(.16));
        BigDecimal rtnFlete = BigDecimal.ZERO;
        rtnFlete = ivaAntcpo.multiply(BigDecimal.valueOf(.04));
        
        return rtnFlete;
     }
     
     public BigDecimal sumarDetalleOrdenCompra(List<DetalleOrdenCompraMB> detalle) {
         
         BigDecimal suma = BigDecimal.ZERO;
         
         for(DetalleOrdenCompraMB aux: detalle) {
             suma = suma.add(aux.getCantidadSolicitada().multiply(aux.getPrecioUnitario()));
         }
         
         return suma;
     }
     
     public List<InsumoContrat> filtrarCompletos() {
         
         List<InsumoContrat> list = new ArrayList<>();
         
         
         
         return list;
         
     }
    
}
