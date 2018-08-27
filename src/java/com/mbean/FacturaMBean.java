
package com.mbean;

import com.bean.Comprobante;
import com.bean.ConceptoCFDI;
import com.bean.PagoBean;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bean.RetencionCFDI;
import com.bo.CanalizaPago;
import com.bo.CargarProformaBO;
import com.bo.ReadXML;
import com.conexion.ConexionBD;
import com.dao.FacturaDAO;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.primefaces.event.FileUploadEvent;

import org.primefaces.model.UploadedFile;


@Named("factura")
@ViewScoped
public class FacturaMBean implements Serializable {
    
    public FacturaMBean() {
    }
    
    private int id_proyecto;
    private int id_presup;
    private String proyecto;
    private String presupuesto;
    private String tipoPago;
    private BigDecimal importeEst;
    private BigDecimal importeAcum;
    private BigDecimal importeRest;
    private String origen;
    
    private BigDecimal amortEstim;
    private BigDecimal gtiaEstim;
    private BigDecimal amortAcum;
    private BigDecimal amortRest;
    
    
    private List<ProyectoSimple> listProy = new ArrayList<>();
    private ProyectoSimple ps;
    
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    private PresupuestoBean presB;
    
    private List<PagoBean> listPagos = new ArrayList<>();
    private PagoBean pb;
    
    private int id_pago;
    private BigDecimal importe;
    private BigDecimal amorAntcpo;
    private String emisor;
    private List<ConceptoCFDI> conceptos = new ArrayList<>();
    private String moneda;
    private String tipoComp;
    private String metodoPago;
    private String regimenEmisor;
    private String rfcEmisor;
    private String conceptoFact;
    private String tipo;
    private BigDecimal iva;
    private BigDecimal rtnRenta;
    private BigDecimal rtnFlete;
    private BigDecimal total;
    private float pctRetGtia;
    private float pctAmort;
    private BigDecimal retFdoGtia;
    private BigDecimal acumGtia;
    private BigDecimal retenRest;
    private String fechaFact;
    
    private String noFactura;
    private BigDecimal subtotal;
    
    private String dateNow;
    
    private boolean factura = false;
    
    private int solicitudPago;
    
    private UploadedFile upload;
    private Part file2;
    
    private File file;
    
    public void cargarXML(FileUploadEvent event) {
        
        upload = event.getFile();
        FacesContext msj = FacesContext.getCurrentInstance();
        
        
        ReadXML read = new ReadXML();
        Comprobante cfdi = read.leerXML(upload);
        System.out.println(cfdi.getComplemento().getTimbre().getUuid());
        if(validarUUID(cfdi)) {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El CFDI ya fue registrado."));
        }else {
            
            if(!cfdi.getReceptor().getRfc().equals("ACO071012857")){
                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                        "El RFC del receptor no es válido."));
            }else {
                updateCFDI(cfdi);
            }
            
        }
        
    }
    
    public boolean validarUUID(Comprobante cfdi) {
        
        
        ConexionBD c = new ConexionBD();
        FacturaDAO dao = new FacturaDAO(c.getConexion());
        boolean valid = dao.validarUID(cfdi);
        
        return valid;
        
    }
    
    public void updateCFDI(Comprobante cfdi) {
        
        setRfcEmisor(cfdi.getEmisor().getRfc());
        setEmisor(cfdi.getEmisor().getNombre());
        setConceptos(cfdi.getConceptos());
        setNoFactura(cfdi.getFolio());
        setMoneda(cfdi.getMoneda());
        setImporte(cfdi.getSubtotal());
        //System.out.println(cfdi.getSubtotal());
        setTipoComp(cfdi.getTipoDeComprobante());
        setMetodoPago(cfdi.getMetodoDePago());
        setRegimenEmisor(cfdi.getEmisor().getRegimenFiscal().getRegimen());
        for(RetencionCFDI ret:cfdi.getImpuestos().getRetenciones()) {
            if(ret.getImpuesto().equals("IVA")) {
                setIva(ret.getImporte());
            }
        }
        setTotal(cfdi.getTotal());
        setFechaFact(cfdi.getFecha());
        
        
    }
    
    public void registrarFactura() {
        //Si el importe de la factura + lo facturado es igual al importe origen, la amort del antcpo debe ser 
        //el monto por amortizar
        if((importeAcum.add(importe)).floatValue() >= importeEst.floatValue()) {
            
            //Validar el número de la factura para el proveedor 
            //Escribir factura, noFactura, importeFactura, antcpoFactura, contratista, origenFactura, tipoFactura, 
            //ivaFactura, rtnFlete, rtnRenta, rtnGarantia, totalFactura, idForaneo = id_pago
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            FacturaDAO fd = new FacturaDAO(con);

            if(fd.validarFactura(noFactura, emisor)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! ", "La factura ya fue registrada"));
            }else {
                PagoBean pago = new PagoBean();
                pago.setId_pago(id_pago);
                pago.setImporteEstimacion(importe);
                pago.setAnticipoAmort(amorAntcpo);

                pago.setRetFdoGtia(retFdoGtia);

                pago.setSubtotal(subtotal);
                pago.setIva(iva);
                pago.setRetFlete(rtnFlete);
                pago.setRetRenta(rtnRenta);
                pago.setImporteTotal(total);
                pago.setFecha(dateNow);
                pago.setContratista(emisor);
                pago.setTipo(tipo);
                pago.setNoFactura(noFactura);
                pago.setEstatusFact("PENDIENTE");
                pago.setOrigen(tipoPago);

                CanalizaPago canal = new CanalizaPago();
                setSolicitudPago(canal.ubicarPago(pago));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "La solicitud registrada es: "+solicitudPago));
            }
            
        }else {
            //Escribir factura, noFactura, importeFactura, antcpoFactura, contratista, origenFactura, tipoFactura, 
            //ivaFactura, rtnFlete, rtnRenta, rtnGarantia, totalFactura, idForaneo = id_pago
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            FacturaDAO fd = new FacturaDAO(con);
            
            if(fd.validarFactura(noFactura, emisor)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! ", "La factura ya fue registrada"));
            }else {
                PagoBean pago = new PagoBean();
                pago.setId_pago(id_pago);
                pago.setImporteEstimacion(importe);
                pago.setAnticipoAmort(amorAntcpo);

                pago.setRetFdoGtia(retFdoGtia);

                pago.setSubtotal(subtotal);
                pago.setIva(iva);
                pago.setRetFlete(rtnFlete);
                pago.setRetRenta(rtnRenta);
                pago.setImporteTotal(total);
                pago.setFecha(dateNow);
                pago.setContratista(emisor);
                pago.setTipo(tipo);
                pago.setNoFactura(noFactura);
                pago.setEstatusFact("PENDIENTE");
                pago.setOrigen(tipoPago);

                CanalizaPago canal = new CanalizaPago();
                setSolicitudPago(canal.ubicarPago(pago));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "La solicitud registrada es: "+solicitudPago));
            }    
        }
        
        
    }
    
    public void validarAmortizacion() {
        
        if(amorAntcpo.floatValue() > amortEstim.floatValue()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! ", "La amortización no puede ser mayor al total por amortizar."));
            actualizarImpFactura();
        }
        else {
            float ivaVar = 0.16f;
            
            
            
            retFdoGtia = (importe.multiply(BigDecimal.valueOf(pctRetGtia/100))).setScale(2, RoundingMode.DOWN);
            subtotal = (importe.subtract(amorAntcpo)).subtract(retFdoGtia);
            iva = (subtotal.multiply(BigDecimal.valueOf(ivaVar))).setScale(2, RoundingMode.UP);
            if(rtnRenta.floatValue() > 0.00) {
                setRtnRenta(((iva.divide(BigDecimal.valueOf(3))).multiply(BigDecimal.valueOf(2))).setScale(2, RoundingMode.UP));
            }
            if(rtnFlete.floatValue() > 0.00) {
                setRtnFlete((importe.multiply(BigDecimal.valueOf(.04))).setScale(2, RoundingMode.UP));
            }
            setTotal((((((importe.subtract(amorAntcpo)).subtract(retFdoGtia)).add(iva)).subtract(rtnRenta)).subtract(rtnFlete)).setScale(2, RoundingMode.UP));
            setFactura(true);
        }
    }
    
    
    
    public void actualizarImpFactura() {
        
        //System.out.println(importe+"\t"+importeRest);
        
        if(importe.floatValue() <= importeRest.floatValue()) {
            float ivaVar = 0.16f;
            
            
            amorAntcpo = (importe.multiply(BigDecimal.valueOf(pctAmort/100)));
            retFdoGtia = (importe.multiply(BigDecimal.valueOf(pctRetGtia/100)));
            subtotal = (importe.subtract(amorAntcpo)).subtract(retFdoGtia);
            //iva = (subtotal.multiply(BigDecimal.valueOf(ivaVar)));
            if(rtnRenta.floatValue() > 0.00) {
                float aux = iva.floatValue()/3;
                aux = aux*2;
                setRtnRenta(BigDecimal.valueOf(aux));
                //System.out.println(aux);
                //setRtnRenta(((iva.divide(BigDecimal.valueOf(3))).multiply(BigDecimal.valueOf(2))).setScale(2, RoundingMode.UP));
            }
            if(rtnFlete.floatValue() > 0.00) {
                float aux = iva.floatValue()*.04f;
                setRtnFlete(BigDecimal.valueOf(aux));
                //setRtnFlete((importe.multiply(BigDecimal.valueOf(.04))).setScale(2, RoundingMode.UP));
            }
            setTotal((((((importe.subtract(amorAntcpo)).subtract(retFdoGtia)).add(iva)).subtract(rtnRenta)).subtract(rtnFlete)));
            setFactura(true);
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Importe Incorrecto"));
            setImporte(importeRest);
            actualizarImpFactura();
        }
        
        
    }
    
    public void obtenerRestante() {
        //System.out.println(importeEst+"\t"+importeAcum);
        setImporteRest((importeEst.subtract(importeAcum)).setScale(2, RoundingMode.UP));
        
        
        setImporte(importeRest);
    }
    
    public void acumuladoFactura() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        FacturaDAO fd = new FacturaDAO(con);
        //setImporteAcum(fd.sumarAcumulados(id_pago, tipo));
        BigDecimal[] acumulados = fd.sumarAcumulados(id_pago, tipo);
        setImporteAcum(acumulados[0].setScale(2, RoundingMode.UP));
        setAmortAcum(acumulados[1].setScale(2, RoundingMode.UP));
        setAcumGtia(acumulados[2].setScale(2, RoundingMode.UP));
        
    }
    
    public void generarFecha() {
        
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        setDateNow(formato.format(fecha));
        
    }
    
    public void seleccionarPago() {
        
        if((tipoPago.equals("anticipo")) || (tipoPago.equals("estima"))) {
            setId_pago(pb.getId_pago());
            //setImporte(pb.getImporteEstimacion().setScale(2, RoundingMode.UP));
            setImporteAcum(pb.getFacturado());
            //System.out.println(pb.getFacturado());
            setImporteEst(pb.getImporteEstimacion().setScale(2, RoundingMode.UP));
            setAmortEstim(pb.getAnticipoAmort().setScale(2, RoundingMode.UP));
            setPctAmort(pb.getPctAmort());
            setPctRetGtia(pb.getFondoGtiaPct());
            //setAmorAntcpo(BigDecimal.valueOf(importe.floatValue()*getPctAmort()));
            setGtiaEstim(pb.getRetFdoGtia().setScale(2, RoundingMode.UP));
            //setEmisor(pb.getContratista());
            setTipo(pb.getTipo());
            //setIva(pb.getIva().setScale(2, RoundingMode.UP));
            setRtnFlete(pb.getRetFlete().setScale(2, RoundingMode.UP));
            setRtnRenta(pb.getRetRenta().setScale(2, RoundingMode.UP));
            //setTotal(pb.getImporteTotal().setScale(2, RoundingMode.UP));
            //setFactura(factura);
            //acumuladoFactura();
            //obtenerRestante();
            //generarFecha();
            //actualizarImpFactura();
            
        }
        
        if(tipoPago.equals("antOC")) {
            setId_pago(pb.getId_pago());
            //setImporte(pb.getImporteEstimacion().setScale(2, RoundingMode.UP));
            setImporteAcum(BigDecimal.ZERO);
            //System.out.println(pb.getFacturado());
            setImporteEst(pb.getImporteEstimacion().setScale(2, RoundingMode.UP));
            setAmortEstim(BigDecimal.ZERO);
            setGtiaEstim(BigDecimal.ZERO);
            //setEmisor(pb.getContratista());
            setTipo(pb.getTipo());
            //setIva(pb.getIva().setScale(2, RoundingMode.UP));
            setRtnFlete(BigDecimal.ZERO);
            setRtnRenta(BigDecimal.ZERO);
            //setTotal(pb.getImporteTotal().setScale(2, RoundingMode.UP));
            //acumuladoFactura();
            //obtenerRestante();
            //generarFecha();
            //actualizarImpFactura();
        }
        
        if(tipoPago.equals("compra")) {
            
            setId_pago(pb.getId_pago());
            //setImporte(pb.getImporteEstimacion().setScale(2, RoundingMode.UP));
            setImporteAcum(pb.getFacturado());
            //System.out.println(pb.getFacturado());
            setImporteEst(pb.getImporteEstimacion().setScale(2, RoundingMode.UP));
            ConexionBD c = new ConexionBD();
            Connection con = c.getConexion();
            FacturaDAO f = new FacturaDAO(con);
            setPctAmort(f.obtenerPctAmort(pb.getId_pago()));
            
            setGtiaEstim(BigDecimal.ZERO);
            setEmisor(pb.getContratista());
            setTipo(pb.getTipo());
            //setIva(pb.getIva().setScale(2, RoundingMode.UP));
            setRtnFlete(BigDecimal.ZERO);
            setRtnRenta(BigDecimal.ZERO);
            //setTotal(pb.getImporteTotal().setScale(2, RoundingMode.UP));
            //acumuladoFactura();
            //obtenerRestante();
            //generarFecha();
            //actualizarImpFactura();
        }
        
        
    }
    
    public void buscarPagos() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        FacturaDAO fd = new FacturaDAO(con);
        
        if(tipoPago.equals("anticipo")) {
            setListPagos(fd.listarAnticipos(id_proyecto, id_presup));
        }
        if(tipoPago.equals("estima")) {
            setListPagos(fd.listarAvancesEstimados(id_proyecto, id_presup));
        }
        if(tipoPago.equals("compra")) {
            //setListPagos(fd.listarAvancesEstimados(id_proyecto, id_presup));
            setListPagos(fd.listarOrdenesCompra(id_presup));
        }
        if(tipoPago.equals("antOC")) {
            
            setListPagos(fd.listarOrdCompra(id_proyecto, id_presup));
        }
        
    }
    
    public void obtenerPresupuesto() {
        setId_presup(presB.getId_presupuesto());
        setPresupuesto(presB.getPresupuesto());
        
    }
    
    public void buscarPresupuesto() {
        
        if(ps != null) {
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            FacturaDAO fd = new FacturaDAO(con);
            
            setListaPres(fd.listarPresupuesto(ps.getId_proyecto()));
            //System.out.println(listaPres.size());
        }
        
    }
    
    public void obtenerProyecto() {
        setId_proyecto(ps.getId_proyecto());
        setProyecto(ps.getProyecto());
        buscarPresupuesto();
    }
    
    public void consultarProyecto() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        FacturaDAO fd = new FacturaDAO(con);
        setListProy(fd.listarProyecto());
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public int getId_presup() {
        return id_presup;
    }

    public void setId_presup(int id_presup) {
        this.id_presup = id_presup;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    public List<ProyectoSimple> getListProy() {
        consultarProyecto();
        return listProy;
    }

    public void setListProy(List<ProyectoSimple> listProy) {
        this.listProy = listProy;
    }

    public ProyectoSimple getPs() {
        return ps;
    }

    public void setPs(ProyectoSimple ps) {
        this.ps = ps;
    }

    public ArrayList<PresupuestoBean> getListaPres() {
        return listaPres;
    }

    public void setListaPres(ArrayList<PresupuestoBean> listaPres) {
        this.listaPres = listaPres;
    }

    public PresupuestoBean getPresB() {
        return presB;
    }

    public void setPresB(PresupuestoBean presB) {
        this.presB = presB;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    

    

    public int getId_pago() {
        return id_pago;
    }

    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getAmorAntcpo() {
        return amorAntcpo;
    }

    public void setAmorAntcpo(BigDecimal amorAntcpo) {
        this.amorAntcpo = amorAntcpo;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getRtnRenta() {
        return rtnRenta;
    }

    public void setRtnRenta(BigDecimal rtnRenta) {
        this.rtnRenta = rtnRenta;
    }

    public BigDecimal getRtnFlete() {
        return rtnFlete;
    }

    public void setRtnFlete(BigDecimal rtnFlete) {
        this.rtnFlete = rtnFlete;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(String noFactura) {
        this.noFactura = noFactura;
    }

    public BigDecimal getImporteAcum() {
        return importeAcum;
    }

    public void setImporteAcum(BigDecimal importeAcum) {
        this.importeAcum = importeAcum;
    }

    public BigDecimal getImporteRest() {
        return importeRest;
    }

    public void setImporteRest(BigDecimal importeRest) {
        this.importeRest = importeRest;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public List<PagoBean> getListPagos() {
        return listPagos;
    }

    public void setListPagos(List<PagoBean> listPagos) {
        this.listPagos = listPagos;
    }

    public PagoBean getPb() {
        return pb;
    }

    public void setPb(PagoBean pb) {
        this.pb = pb;
    }

    public BigDecimal getAmortAcum() {
        return amortAcum;
    }

    public void setAmortAcum(BigDecimal amortAcum) {
        this.amortAcum = amortAcum;
    }

    public BigDecimal getAmortRest() {
        return amortRest;
    }

    public void setAmortRest(BigDecimal amortRest) {
        this.amortRest = amortRest;
    }

    public BigDecimal getImporteEst() {
        return importeEst;
    }

    public void setImporteEst(BigDecimal importeEst) {
        this.importeEst = importeEst;
    }

    public float getPctRetGtia() {
        return pctRetGtia;
    }

    public void setPctRetGtia(float pctRetGtia) {
        this.pctRetGtia = pctRetGtia;
    }

    public BigDecimal getRetFdoGtia() {
        return retFdoGtia;
    }

    public void setRetFdoGtia(BigDecimal retFdoGtia) {
        this.retFdoGtia = retFdoGtia;
    }

    public BigDecimal getRetenRest() {
        return retenRest;
    }

    public void setRetenRest(BigDecimal retenRest) {
        this.retenRest = retenRest;
    }

    public BigDecimal getAcumGtia() {
        return acumGtia;
    }

    public void setAcumGtia(BigDecimal acumGtia) {
        this.acumGtia = acumGtia;
    }

    public float getPctAmort() {
        return pctAmort;
    }

    public void setPctAmort(float pctAmort) {
        this.pctAmort = pctAmort;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getDateNow() {
        return dateNow;
    }

    public void setDateNow(String dateNow) {
        this.dateNow = dateNow;
    }

    public BigDecimal getAmortEstim() {
        return amortEstim;
    }

    public void setAmortEstim(BigDecimal amortEstim) {
        this.amortEstim = amortEstim;
    }

    public BigDecimal getGtiaEstim() {
        return gtiaEstim;
    }

    public void setGtiaEstim(BigDecimal gtiaEstim) {
        this.gtiaEstim = gtiaEstim;
    }

    public boolean isFactura() {
        return factura;
    }

    public void setFactura(boolean factura) {
        this.factura = factura;
    }

    public int getSolicitudPago() {
        return solicitudPago;
    }

    public void setSolicitudPago(int solicitudPago) {
        this.solicitudPago = solicitudPago;
    }

    /**
     * @return the conceptoFact
     */
    public String getConceptoFact() {
        return conceptoFact;
    }

    /**
     * @param conceptoFact the conceptoFact to set
     */
    public void setConceptoFact(String conceptoFact) {
        this.conceptoFact = conceptoFact;
    }

    /**
     * @return the upload
     */
    public UploadedFile getUpload() {
        return upload;
    }

    /**
     * @param upload the upload to set
     */
    public void setUpload(UploadedFile upload) {
        this.upload = upload;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the file2
     */
    public Part getFile2() {
        return file2;
    }

    /**
     * @param file2 the file2 to set
     */
    public void setFile2(Part file2) {
        this.file2 = file2;
    }

    /**
     * @return the rfcEmisor
     */
    public String getRfcEmisor() {
        return rfcEmisor;
    }

    /**
     * @param rfcEmisor the rfcEmisor to set
     */
    public void setRfcEmisor(String rfcEmisor) {
        this.rfcEmisor = rfcEmisor;
    }

    /**
     * @return the conceptos
     */
    public List<ConceptoCFDI> getConceptos() {
        return conceptos;
    }

    /**
     * @param conceptos the conceptos to set
     */
    public void setConceptos(List<ConceptoCFDI> conceptos) {
        this.conceptos = conceptos;
    }

    /**
     * @return the moneda
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     * @param moneda the moneda to set
     */
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    /**
     * @return the tipoComp
     */
    public String getTipoComp() {
        return tipoComp;
    }

    /**
     * @param tipoComp the tipoComp to set
     */
    public void setTipoComp(String tipoComp) {
        this.tipoComp = tipoComp;
    }

    /**
     * @return the metodoPago
     */
    public String getMetodoPago() {
        return metodoPago;
    }

    /**
     * @param metodoPago the metodoPago to set
     */
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    /**
     * @return the regimenEmisor
     */
    public String getRegimenEmisor() {
        return regimenEmisor;
    }

    /**
     * @param regimenEmisor the regimenEmisor to set
     */
    public void setRegimenEmisor(String regimenEmisor) {
        this.regimenEmisor = regimenEmisor;
    }

    /**
     * @return the fechaFact
     */
    public String getFechaFact() {
        return fechaFact;
    }

    /**
     * @param fechaFact the fechaFact to set
     */
    public void setFechaFact(String fechaFact) {
        this.fechaFact = fechaFact;
    }
    
}
