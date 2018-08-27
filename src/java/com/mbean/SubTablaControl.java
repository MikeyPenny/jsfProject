/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.Concepto;
import com.bean.CtaSubcontratoBean;
import com.bean.InsumoBean;
import com.bean.MaterialBean;
import com.bean.Partida;
import com.bean.PartidaCatalogo;
import com.bean.Unidad;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import com.dao.SubcontratoDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author Mickey
 */
@ManagedBean
@SessionScoped
public class SubTablaControl implements Serializable {

    private int id_proy;
    private int id_pres;

    private List<Partida> listPrtda = new ArrayList<Partida>();
    private Partida part;
    
    private List<InsumoBean> listaIns = new ArrayList<InsumoBean>();
    private InsumoBean insumo;
    
    private List<InsumoBean> listaInsTemp = new ArrayList<InsumoBean>();
    private InsumoBean insumoTemp;
    
    private BigDecimal totales;
    
    private int id_concepto;
    private String subPartida;
    private String cveSubPrtda;
    private int numConcepto;
    private String codConcepto;
    private String descripcion;
    private String unidad;
    private BigDecimal cantidad;
    private BigDecimal precioUnit;
    private BigDecimal importe;
    private String cadCant;
    private String cadPrecio;
    private String cadImp;
    private BigDecimal sumaImporte;
    
    private int id_insumo;
    private String cuenta;
    private String codInsumo;
    private String descripIns;
    private String unidadIns;
    private BigDecimal costoIns;
    private BigDecimal cantIns;
    private BigDecimal importeIns;
    
    private BigDecimal sumaIns;
    
    private List<PartidaCatalogo> lista = new ArrayList<PartidaCatalogo>();
    private PartidaCatalogo p;
    
    private String prtdaCrea;
    private String cvePrtdaCrea;
    
    private List<Unidad> listaUnid = new ArrayList<Unidad>();
    private Unidad un;
    
    private List<SelectItem> sel = new ArrayList<SelectItem>();
    
    private List<MaterialBean> listaMat = new ArrayList<MaterialBean>();
    private MaterialBean mb;
    
    private List<CtaSubcontratoBean> listSubs = new ArrayList<CtaSubcontratoBean>();
    private CtaSubcontratoBean csb;
    
    private String consecSubCtto;
    private String conttoDesc;
    private String subCttoCve;
        
    public SubTablaControl() {
     
    }
    
    
    
    public void actualizarInsSubCtto() {
        
        setCuenta("SUBCONTRATOS");
        setDescripIns(csb.getDefinicion());
        setSubCttoCve(csb.getClave());
        
    }
    
    public void resetarSubContto() {
        setCuenta(null);
        setDescripIns(null);
        setSubCttoCve(null);
        setConsecSubCtto(null);
        setUnidadIns(null);
        setCantIns(null);
        setCostoIns(null);
        setImporteIns(null);
    }
    
    public void generaCodSubContto() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO pres = new PresupuestoDAO(con);
        
        String proyCad = "" + getId_proy();
        codInsumo = getSubCttoCve();
        
        String consec = "";
        String consProy = "";
                
        for(int i = 0; i < (3 - proyCad.length()); i++) {
            consProy = consProy + '0';
        }
        
        consProy =  consProy + proyCad;
        codInsumo = codInsumo + consProy;
        
        for(int i = 0; i < (4 - consecSubCtto.length()); i++) {
            consec = consec + '0';
        }
        
        consec = consec + consecSubCtto;
        
        codInsumo = codInsumo + consec;
        
        if(pres.consultarConsecutivo(codInsumo)) {
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El consecutivo ya existe para el valor " + consecSubCtto));
            codInsumo = null;
        }
        
        
        
    }
    
    public void guardarConcepto() {
        
        Concepto concep = new Concepto();
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO pres = new PresupuestoDAO(con);
        int id_part = pres.consultarPartida(id_proy, id_pres, getCvePrtdaCrea());
        
        
        
        concep.setSubPartida(getSubPartida());
        concep.setCveSubPrtda(getCveSubPrtda());
        concep.setNumConcepto(String.valueOf(getNumConcepto()));
        concep.setCodConcepto(getCodConcepto());
        concep.setDescripcion(getDescripcion());
        concep.setUnidad(getUnidad());
        concep.setCantidad(getCantidad());
        concep.setpUnitario(getPrecioUnit());
        concep.setImporte(getImporte());
        if(id_part == 0) {
            
            concep.setId_partida(pres.escribirNuevaPartida(getPrtdaCrea(), getCvePrtdaCrea(), id_pres));
        }else {
            concep.setId_partida(id_part);
        }
        
        pres.escribirConcepto(concep, listaInsTemp);
        rellenarListas();
    }
    
    public void actualizarConcepto() {
        
        Concepto concepto = new Concepto();
        concepto.setCantidad(getCantidad());
        concepto.setDescripcion(getDescripcion());
        concepto.setNumConcepto(String.valueOf(getNumConcepto()));
        concepto.setImporte(getImporte());
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO pres = new PresupuestoDAO(con);
        pres.actualizarConcepto(concepto);
        rellenarListas();
        
    }
    
    public void listarCatSubs() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        SubcontratoDAO scd = new SubcontratoDAO(con);
        setListSubs(scd.consultarCatalogoSub());
        
    }
    
    public void resetearConcepto() {
        
        setNumConcepto(0);
        setPrtdaCrea(null);
        setSubPartida(null);
        setCveSubPrtda(null);
        setCodConcepto(null);
        setUnidad(null);
        setCantidad(null);
        setPrecioUnit(null);
        setImporte(null);
        setDescripcion(null);
        listaInsTemp.clear();
        setInsumoTemp(null);
        setSumaIns(null);
        
    }
        
    public void sustituirInsumo() {
        
        
        insumoTemp.setCuenta(getCuenta());
        insumoTemp.setCodInsumo(getCodInsumo());
        insumoTemp.setDescripIns(getDescripIns());
        insumoTemp.setUnidadIns(getUnidadIns());
        insumoTemp.setCostoIns(getCostoIns());
        insumoTemp.setCantIns(getCantIns());
        insumoTemp.setImporteIns(getImporteIns());
        insumoTemp.setCostoInsCtrl(getCostoIns());
        insumoTemp.setCantInsCtrl(getCantIns());
        insumoTemp.setImpInsCtrl(getImporteIns());
        
        for(InsumoBean aux: listaIns) {
            if(aux.getCodInsumo().equals(insumoTemp.getCodInsumo())) {
                int ind = listaIns.indexOf(aux);
                listaIns.set(ind, insumoTemp);
                break;
            }
        }
        sumarTotIns();
        actualizarMontosConcepto();
        
    }
    
    public void consultarConsecutivo() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO pres = new PresupuestoDAO(con);
        
        if(pres.consultaConsecutivo(numConcepto)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El número de concepto ya existe para el valor " + numConcepto));
            numConcepto = 0;
        }
        if(numConcepto == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El" + numConcepto + " no es valido para un número de concepto" ));
           
        }
                
    }
    
    
    public void cargarListaInsumos() {
        
        insumoTemp = new InsumoBean();
        insumoTemp.setCuenta(getCuenta());
        insumoTemp.setCodInsumo(getCodInsumo());
        insumoTemp.setDescripIns(getDescripIns());
        insumoTemp.setUnidadIns(getUnidadIns());
        insumoTemp.setCostoIns(getCostoIns());
        insumoTemp.setCantIns(getCantIns());
        insumoTemp.setImporteIns(getImporteIns());
        insumoTemp.setCostoInsCtrl(getCostoIns());
        insumoTemp.setCantInsCtrl(getCantIns());
        insumoTemp.setImpInsCtrl(getImporteIns());
        listaInsTemp.add(insumoTemp);
        
        sumarTotInsCreados();
        actualizarMontosConcepto();
    }
    
    public void configurarDatosInsumo() {
        
        setCuenta("MATERIALES");
        setUnidadIns(mb.getUnidad());
        setCodInsumo(mb.getClaveMat());
        setDescripIns(mb.getMaterial());
        
    }
    
    public void consultarMateriales() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO pres = new PresupuestoDAO(con);
        setListaMat(pres.listarMateriales());
        
    }
    
    public void listarUnidades() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO pres = new PresupuestoDAO(con);
        setListaUnid(pres.listaUnidades());
        
    }
    
        
    public void listarPartidas() {
        
        sel = new ArrayList<SelectItem>();
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO pres = new PresupuestoDAO(con);
        setLista(pres.listaPartida());
        for(PartidaCatalogo aux: lista) {
            sel.add(new SelectItem(aux, aux.getPartida().toLowerCase()));
        }
    }
    
    
    
    public void seleccionarPartida() {
        
        String[] array = prtdaCrea.split("-");
        prtdaCrea = array[0];
        cvePrtdaCrea = array[1];
        
        System.out.println(getPrtdaCrea());
        System.out.println(getCvePrtdaCrea());
    }
    
    
    
    public void generarConcepto() {
        
        String cadProy = "";
        
        if(id_proy < 10) {
            cadProy = "00" + id_proy;
        }
        if(id_proy >= 10 && id_proy < 100) {
            cadProy = "0" + id_proy;
        }
        if(id_proy >= 100) {
            cadProy = "" + id_proy;
        }
        
        String cad = "" + numConcepto;
        String consec = "";
        
        for(int i = 0; i < (4 - cad.length()); i++) {
            consec = consec + '0';
        }
        
        consec = consec + cad;
        cadProy = cadProy + cveSubPrtda.toUpperCase() + consec;
        
        setCodConcepto(cadProy);
        
    }
    
        
    public void actualizarMontosConcepto() {
        
        precioUnit = sumaIns;
        importe = cantidad.multiply(precioUnit);
        
    }
    
    public void actualizarImporte() {
        
        if(costoIns != null && cantIns != null) {
            importeIns = costoIns.multiply(cantIns);
        }
        
    }
    
    public void sumarTotInsCreados() {
        
        float val = 0.00f;
        
        for(InsumoBean ins:listaInsTemp) {
            val = val + ins.getImpInsCtrl().floatValue();
            setSumaIns(BigDecimal.valueOf(val));
            
        }
    }
    
    public void sumarTotIns() {
        
        float val = 0.00f;
        
        for(InsumoBean ins:listaIns) {
            val = val + ins.getImpInsCtrl().floatValue();
            setSumaIns(BigDecimal.valueOf(val));
            
        }
    }
    
        
    public void guardarCambioIns() {
        
        insumo.setId_insumo(getId_insumo());
        insumo.setUnidadIns(getUnidadIns());
        insumo.setCostoInsCtrl(getCostoIns());
        insumo.setCantInsCtrl(getCantIns());
        insumo.setImpInsCtrl(getImporteIns());
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO pres = new PresupuestoDAO(con);
        pres.editarInsumo(insumo);
        listarInsumos();
        actualizarMontosConcepto();
        pres.actualizarConcepto(precioUnit, importe, id_concepto);
        rellenarListas();
                
    }
    
    public void listarInsumos() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO pres = new PresupuestoDAO(con);
        setListaIns(pres.listarInsumos(getId_concepto()));
        sumarTotIns();
        
    }
    
    public void editarConcepto() {
        
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO pres = new PresupuestoDAO(con);
        actualizarConcepto(pres.buscarConcepto(getNumConcepto()));
        listarInsumos();
        
        
        //System.out.println(listaIns.size());      
    }
    
    public void resetearInsumo() {
        setId_insumo(0);
        setCuenta(null);
        setCodInsumo(null);
        setDescripIns(null);
        setUnidadIns(null);
        setCostoIns(null);
        setCantIns(null);
        setImporteIns(null);
    }
    
    public void actualizarInsumoCreado() {
        
        setId_insumo(insumoTemp.getId_insumo());
        setCuenta(insumoTemp.getCuenta());
        setCodInsumo(insumoTemp.getCodInsumo());
        setDescripIns(insumoTemp.getDescripIns());
        setUnidadIns(insumoTemp.getUnidadIns());
        setCostoIns(insumoTemp.getCostoInsCtrl());
        setCantIns(insumoTemp.getCantInsCtrl());
        setImporteIns(insumoTemp.getImpInsCtrl());
        
    }
    
    public void actualizarInsumo() {
        
          setId_insumo(insumo.getId_insumo());
          setCuenta(insumo.getCuenta());
          setCodInsumo(insumo.getCodInsumo());
          setDescripIns(insumo.getDescripIns());
          setUnidadIns(insumo.getUnidadIns());
          setCostoIns(insumo.getCostoInsCtrl());
          setCantIns(insumo.getCantInsCtrl());
          setImporteIns(insumo.getImpInsCtrl());

        
    }
    
    public void actualizarConcepto(Concepto c) {
    
        setId_concepto(c.getId_concepto());
        setSubPartida(c.getSubPartida());
        setCveSubPrtda(c.getCveSubPrtda());
        setNumConcepto(Integer.parseInt(c.getNumConcepto()));
        setCodConcepto(c.getCodConcepto());
        setDescripcion(c.getDescripcion());
        setUnidad(c.getUnidad());
        setCantidad(c.getCantidad());
        setPrecioUnit(c.getpUnitario());
        setImporte(c.getImporte());
        setCadCant(c.getCadCant());
        setCadPrecio(c.getCadPrecio());
        setCadImp(c.getCadImp());
                
    }
    
    public void rellenarListas() {
    
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO pDao = new PresupuestoDAO(con);
        setListPrtda(pDao.enlistar(id_proy, id_pres));
        
    }

    
    public String llenarListas(int id_proy, int id_pres) {
        
        this.id_proy = id_proy;
        this.id_pres = id_pres;
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO pDao = new PresupuestoDAO(con);
        setListPrtda(pDao.enlistar(id_proy, id_pres));
                
        return "vistaPres.xhtml";
        
    }

    /**
     * @return the listPrtda
     */
    public List<Partida> getListPrtda() {
        //llenarListas(id_proy, id_pres);
        return listPrtda;
    }

    /**
     * @param listPrtda the listPrtda to set
     */
    public void setListPrtda(List<Partida> listPrtda) {
        this.listPrtda = listPrtda;
    }

    /**
     * @return the part
     */
    public Partida getPart() {
        return part;
    }

    /**
     * @param part the part to set
     */
    public void setPart(Partida part) {
        this.part = part;
    }

    /**
     * @return the totales
     */
    public BigDecimal getTotales() {
        return totales;
    }

    /**
     * @param totales the totales to set
     */
    public void setTotales(BigDecimal totales) {
        this.totales = totales;
    }

    /**
     * @return the id_concepto
     */
    public int getId_concepto() {
        return id_concepto;
    }

    /**
     * @param id_concepto the id_concepto to set
     */
    public void setId_concepto(int id_concepto) {
        this.id_concepto = id_concepto;
    }

    /**
     * @return the subPartida
     */
    public String getSubPartida() {
        return subPartida;
    }

    /**
     * @param subPartida the subPartida to set
     */
    public void setSubPartida(String subPartida) {
        this.subPartida = subPartida;
    }

    /**
     * @return the cveSubPrtda
     */
    public String getCveSubPrtda() {
        return cveSubPrtda;
    }

    /**
     * @param cveSubPrtda the cveSubPrtda to set
     */
    public void setCveSubPrtda(String cveSubPrtda) {
        this.cveSubPrtda = cveSubPrtda;
    }

    /**
     * @return the numConcepto
     */
    public int getNumConcepto() {
        return numConcepto;
    }

    /**
     * @param numConcepto the numConcepto to set
     */
    public void setNumConcepto(int numConcepto) {
        this.numConcepto = numConcepto;
    }

    /**
     * @return the codConcepto
     */
    public String getCodConcepto() {
        return codConcepto;
    }

    /**
     * @param codConcepto the codConcepto to set
     */
    public void setCodConcepto(String codConcepto) {
        this.codConcepto = codConcepto;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the cantidad
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the pUnitario
     */
    public BigDecimal getPrecioUnit() {
        return precioUnit;
    }

    /**
     * @param pUnitario the pUnitario to set
     */
    public void setPrecioUnit(BigDecimal precioUnit) {
        this.precioUnit = precioUnit;
    }

    /**
     * @return the importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @return the cadCant
     */
    public String getCadCant() {
        return cadCant;
    }

    /**
     * @param cadCant the cadCant to set
     */
    public void setCadCant(String cadCant) {
        this.cadCant = cadCant;
    }

    /**
     * @return the cadPrecio
     */
    public String getCadPrecio() {
        return cadPrecio;
    }

    /**
     * @param cadPrecio the cadPrecio to set
     */
    public void setCadPrecio(String cadPrecio) {
        this.cadPrecio = cadPrecio;
    }

    /**
     * @return the cadImp
     */
    public String getCadImp() {
        return cadImp;
    }

    /**
     * @param cadImp the cadImp to set
     */
    public void setCadImp(String cadImp) {
        this.cadImp = cadImp;
    }

    /**
     * @return the sumaImporte
     */
    public BigDecimal getSumaImporte() {
        return sumaImporte;
    }

    /**
     * @param sumaImporte the sumaImporte to set
     */
    public void setSumaImporte(BigDecimal sumaImporte) {
        this.sumaImporte = sumaImporte;
    }

    /**
     * @return the listaIns
     */
    public List<InsumoBean> getListaIns() {
        return listaIns;
    }

    /**
     * @param listaIns the listaIns to set
     */
    public void setListaIns(List<InsumoBean> listaIns) {
        this.listaIns = listaIns;
    }

    /**
     * @return the insumo
     */
    public InsumoBean getInsumo() {
        return insumo;
    }

    /**
     * @param insumo the insumo to set
     */
    public void setInsumo(InsumoBean insumo) {
        this.insumo = insumo;
    }

    /**
     * @return the id_insumo
     */
    public int getId_insumo() {
        return id_insumo;
    }

    /**
     * @param id_insumo the id_insumo to set
     */
    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return the codInsumo
     */
    public String getCodInsumo() {
        return codInsumo;
    }

    /**
     * @param codInsumo the codInsumo to set
     */
    public void setCodInsumo(String codInsumo) {
        this.codInsumo = codInsumo;
    }

    /**
     * @return the descripIns
     */
    public String getDescripIns() {
        return descripIns;
    }

    /**
     * @param descripIns the descripIns to set
     */
    public void setDescripIns(String descripIns) {
        this.descripIns = descripIns;
    }

    /**
     * @return the unidadIns
     */
    public String getUnidadIns() {
        return unidadIns;
    }

    /**
     * @param unidadIns the unidadIns to set
     */
    public void setUnidadIns(String unidadIns) {
        this.unidadIns = unidadIns;
    }

    /**
     * @return the costoIns
     */
    public BigDecimal getCostoIns() {
        return costoIns;
    }

    /**
     * @param costoIns the costoIns to set
     */
    public void setCostoIns(BigDecimal costoIns) {
        this.costoIns = costoIns;
    }

    /**
     * @return the cantIns
     */
    public BigDecimal getCantIns() {
        return cantIns;
    }

    /**
     * @param cantIns the cantIns to set
     */
    public void setCantIns(BigDecimal cantIns) {
        this.cantIns = cantIns;
    }

    /**
     * @return the importeIns
     */
    public BigDecimal getImporteIns() {
        return importeIns;
    }

    /**
     * @param importeIns the importeIns to set
     */
    public void setImporteIns(BigDecimal importeIns) {
        this.importeIns = importeIns;
    }

    /**
     * @return the sumaIns
     */
    public BigDecimal getSumaIns() {
        return sumaIns;
    }

    /**
     * @param sumaIns the sumaIns to set
     */
    public void setSumaIns(BigDecimal sumaIns) {
        this.sumaIns = sumaIns;
    }

    /**
     * @return the id_proy
     */
    public int getId_proy() {
        return id_proy;
    }

    /**
     * @param id_proy the id_proy to set
     */
    public void setId_proy(int id_proy) {
        this.id_proy = id_proy;
    }

    /**
     * @return the id_pres
     */
    public int getId_pres() {
        return id_pres;
    }

    /**
     * @param id_pres the id_pres to set
     */
    public void setId_pres(int id_pres) {
        this.id_pres = id_pres;
    }

    /**
     * @return the lista
     */
    public List<PartidaCatalogo> getLista() {
        listarPartidas();
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List<PartidaCatalogo> lista) {
        this.lista = lista;
    }

    /**
     * @return the p
     */
    public PartidaCatalogo getP() {
        
        return p;
    }

    /**
     * @param p the p to set
     */
    public void setP(PartidaCatalogo p) {
        this.p = p;
    }

    /**
     * @return the prtdaCrea
     */
    public String getPrtdaCrea() {
        return prtdaCrea;
    }

    /**
     * @param prtdaCrea the prtdaCrea to set
     */
    public void setPrtdaCrea(String prtdaCrea) {
        this.prtdaCrea = prtdaCrea;
    }

    /**
     * @return the cvePrtdaCrea
     */
    public String getCvePrtdaCrea() {
        return cvePrtdaCrea;
    }

    /**
     * @param cvePrtdaCrea the cvePrtdaCrea to set
     */
    public void setCvePrtdaCrea(String cvePrtdaCrea) {
        this.cvePrtdaCrea = cvePrtdaCrea;
    }

    /**
     * @return the listaUnid
     */
    public List<Unidad> getListaUnid() {
        listarUnidades();
        return listaUnid;
    }

    /**
     * @param listaUnid the listaUnid to set
     */
    public void setListaUnid(List<Unidad> listaUnid) {
        this.listaUnid = listaUnid;
    }

    /**
     * @return the un
     */
    public Unidad getUn() {
        return un;
    }

    /**
     * @param un the un to set
     */
    public void setUn(Unidad un) {
        this.un = un;
    }

    /**
     * @return the sel
     */
    public List<SelectItem> getSel() {
        
        return sel;
    }

    /**
     * @param sel the sel to set
     */
    public void setSel(List<SelectItem> sel) {
        this.sel = sel;
    }

    /**
     * @return the listaMat
     */
    public List<MaterialBean> getListaMat() {
        consultarMateriales();
        return listaMat;
    }

    /**
     * @param listaMat the listaMat to set
     */
    public void setListaMat(List<MaterialBean> listaMat) {
        this.listaMat = listaMat;
    }

    /**
     * @return the mb
     */
    public MaterialBean getMb() {
        return mb;
    }

    /**
     * @param mb the mb to set
     */
    public void setMb(MaterialBean mb) {
        this.mb = mb;
    }

    /**
     * @return the insumoTemp
     */
    public InsumoBean getInsumoTemp() {
        return insumoTemp;
    }

    /**
     * @param insumoTemp the insumoTemp to set
     */
    public void setInsumoTemp(InsumoBean insumoTemp) {
        this.insumoTemp = insumoTemp;
    }

    /**
     * @return the listaInsTemp
     */
    public List<InsumoBean> getListaInsTemp() {
        return listaInsTemp;
    }

    /**
     * @param listaInsTemp the listaInsTemp to set
     */
    public void setListaInsTemp(List<InsumoBean> listaInsTemp) {
        this.listaInsTemp = listaInsTemp;
    }

    /**
     * @return the listSubs
     */
    public List<CtaSubcontratoBean> getListSubs() {
        listarCatSubs();
        return listSubs;
    }

    /**
     * @param listSubs the listSubs to set
     */
    public void setListSubs(List<CtaSubcontratoBean> listSubs) {
        this.listSubs = listSubs;
    }

    /**
     * @return the csb
     */
    public CtaSubcontratoBean getCsb() {
        return csb;
    }

    /**
     * @param csb the csb to set
     */
    public void setCsb(CtaSubcontratoBean csb) {
        this.csb = csb;
    }

    /**
     * @return the consecSubCtto
     */
    public String getConsecSubCtto() {
        return consecSubCtto;
    }

    /**
     * @param consecSubCtto the consecSubCtto to set
     */
    public void setConsecSubCtto(String consecSubCtto) {
        this.consecSubCtto = consecSubCtto;
    }

    /**
     * @return the conttoDesc
     */
    public String getConttoDesc() {
        return conttoDesc;
    }

    /**
     * @param conttoDesc the conttoDesc to set
     */
    public void setConttoDesc(String conttoDesc) {
        this.conttoDesc = conttoDesc;
    }

    /**
     * @return the subCttoCve
     */
    public String getSubCttoCve() {
        return subCttoCve;
    }

    /**
     * @param subCttoCve the subCttoCve to set
     */
    public void setSubCttoCve(String subCttoCve) {
        this.subCttoCve = subCttoCve;
    }
    
}
