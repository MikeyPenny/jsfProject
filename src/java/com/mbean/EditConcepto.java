/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.Catalogo;
import com.bean.Concepts;
import com.bean.InsumoBean;
import com.bean.MaterialBean;
import com.bean.PartidaBean;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bean.Unidad;
import com.bo.CreaQuery;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("editarCon")
@ViewScoped
public class EditConcepto implements Serializable {
    
    private ArrayList<ProyectoSimple> listaProy = new ArrayList<>();
    private ProyectoSimple proySel;
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    private PresupuestoBean presB;
    
    private int id_proyecto;
    private String presupuesto;
    private int id_presupuesto;
    private String proyecto;
    
    private Concepts concept;
    private List<Concepts> list = new ArrayList<>();
    private Concepts concpEdit;
    private List<Concepts> filtCon;
    
    private String codConcepto;
    private String descConc;
    private String unidad;
    private String unidConcepto;
    private BigDecimal cantidad;
    private BigDecimal presUnit;
    private BigDecimal importe;
    
    private List<Unidad> listUnid;
    
    private boolean btnInsumo;
    private boolean btnMaterial;
    private boolean btnSubctto;
    private boolean btnRegCon;
    private boolean actPresUnit;
    private boolean actCveMat;
    
    private String cveMat;
    private String cveMatAux;
    private String material;
    private String unidMat;
    private String codSubCtto;
    private BigDecimal cantIns;
    private BigDecimal presUnitIns;
    private BigDecimal impIns;
    private List<MaterialBean> filtroMat;
    private List<MaterialBean> listMat;
    private List<Catalogo> catalogo = new ArrayList<>();
    private String tipoIns;
    private List<InsumoBean> listaIns;
    private List<InsumoBean> listaAdd = new ArrayList<>();
    private List<InsumoBean> listaEdit = new ArrayList<>();
    private List<InsumoBean> listaElim = new ArrayList<>();
    private InsumoBean insElim;
    private String familia;
    private String cveInsumo;
    private String descIns;
    
    private MaterialBean matSel;
    private Catalogo cat;
    private String claveFam;
    private String capConsec;
    
    private boolean cambio;
    
    private List<PartidaBean> listaPB;
    private PartidaBean partSel;
    private List<PartidaBean> listSubPrt;
    private PartidaBean subPrtSel;
    private int id_partida;
    private String codPrtda;
    private int id_subpartida;
    private String subPrtda;
    private String cuenta;
    
    
    @PostConstruct
    public void init() {
        buscarProyecto();
        listarUnidades();
        setBtnRegCon(true);
        setCambio(false);
    }
    
    public void listarUnidades() {
        
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        setListUnid(dao.listaUnidades());
        c.closeConexion();
        
    }
    
    public void buscarProyecto() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO presD = new PresupuestoDAO(con);
        setListaProy(presD.listarProyecto());      
    }
    
    public void obtenerProyecto() {
        setId_proyecto(proySel.getId_proyecto());
        setProyecto(proySel.getProyecto());
        buscarPresupuesto();
    }
    
    public void buscarPresupuesto() {
        
        if(proySel != null) {
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            PresupuestoDAO presD = new PresupuestoDAO(con);
            
            setListaPres(presD.listarPresupuesto(proySel.getId_proyecto(), 2));
            //System.out.println(listaPres.size());
            
        }
        
    }
    
    public void buscarPartida() {
        ConexionBD c  =  new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        setListaPB(pd.listarPartidaBean(id_proyecto, id_presupuesto));
        
    }
    
    public void obtenerPresupuesto() {
        setId_presupuesto(presB.getId_presupuesto());
        setPresupuesto(presB.getPresupuesto());
        buscarPartida();
    }
    
    public void obtenerPartida() {
        setCodPrtda(partSel.getNivel());
        setId_partida(partSel.getId_partida());
        listarSubPartidas();
    }
    
    public void listarSubPartidas() {
        ConexionBD c  =  new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        setListSubPrt(pd.listarSubPartidas(id_proyecto, id_presupuesto, partSel.getId_partida()));
    }
    
    public void obtenerSubPartida() {
        setSubPrtda(subPrtSel.getNivel());
        setId_subpartida(subPrtSel.getId_partida());
        System.out.println(subPrtSel.getId_partida());
    }
    
    public void listarConceptos () {
        
        
        /*Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO presD = new PresupuestoDAO(con);
        setList(presD.listBudget(id_proyecto, id_presupuesto));*/
        CreaQuery query = new CreaQuery();
        setList(query.selectConceptos(id_proyecto, id_presupuesto, id_partida, id_subpartida));
        
        //System.out.println(id_partida);
        //System.out.println(id_proyecto +" "+ id_presupuesto);
        /*try {
            con.close();
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }*/
        
        
        
    }
    
    public void seleccionarConcepto() {
        
        listaIns = new ArrayList<>();
        
        setCodConcepto(concept.getCodConcepto());
        setDescConc(concept.getDescripcion());
        setUnidConcepto(concept.getUnidad());
        setCantidad(concept.getCantidad());
        setPresUnit(concept.getpUnitario());
        setImporte(concept.getImporte());
        setListaIns(concept.getInsumos());
        System.out.println(listaIns.size());
        
    }
    
    
    
    public void actualizaImporte() {
        
        if(getCantidad()!= null && getPresUnit() != null) {
            setImporte(getCantidad().multiply(getPresUnit()));
            setCambio(true);
            setBtnRegCon(false);
        }else {
            setImporte(BigDecimal.ZERO);
        }
        
        
        
    }
    
    public void cambiarUnidad() {
        setCambio(true);
        setBtnRegCon(false);
    }
    
    public void crearInsumo() {
            
            setBtnMaterial(false);
            setBtnSubctto(false);
            setCveMat(null);
            setCodSubCtto(null);
            setMaterial(null);
            setUnidMat(null);
            setCantIns(null);
            setPresUnitIns(null);
            setImpIns(null);
            setFiltroMat(null);
            listMat = new ArrayList<>();
            catalogo = new ArrayList<>();
            setActCveMat(false);
            setActPresUnit(false);
        
    }
    
    public void consultarMateriales() {
        
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        setListMat(dao.listarMateriales());
        
        
    }
    
    public void listarCatSc() {
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        setCatalogo(dao.listarCatalogoConceptos());
        setActCveMat(false);
        setActPresUnit(false);
        setMaterial(null);
        setPresUnitIns(null);
        setImpIns(null);
        setCantIns(null);
        setUnidMat(null);
        setCodSubCtto(null);
        setCveMat(null);
        
    }
    
    public void validarCodigo() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        
        
        if(cveMat.length() != 12) {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La estructura del código debe ser CVE-Fam-XXXX, revisar la longitud a 12 caracteres"));
            setCveMat(cveMatAux);
        }
        if(cveMat.charAt(3) != '-') {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La estructura del código debe ser CVE-Fam-XXXX, la longitud de la CVE no debe ser mayor ni menor a 3 caracteres y debe ser seguida por un guión"));
            setCveMat(cveMatAux);
        }
        if(cveMat.charAt(7) != '-') {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La estructura del código debe ser CVE-Fam-XXXX, la longitud de la Fam no debe ser mayor ni menor a 3 caracteres y debe ser seguida por un guión"));
            setCveMat(cveMatAux);
        }
        
        editarCveSubCtto();
        
        
    }
    
    public void editarCveSubCtto() {
        
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        InsumoBean ins = dao.verifDataSC(id_presupuesto, getCveMat());
        if(ins.getCostoIns() != null) {
            setCveMat(ins.getCodInsumo());
            setPresUnitIns(ins.getCostoIns());
            setMaterial(ins.getDescripIns());
            setUnidMat(ins.getUnidadIns());
            setCuenta(ins.getCuenta());
            setActCveMat(true);
            setActPresUnit(true);
            
            //System.out.println("OK");
            
        }
        
        //System.out.println("SIn coincidencias");
        
    }
    
    public void actualizarImpInsumo() {
        if(getCantIns()!= null && getPresUnitIns()!= null) {
            impIns = getCantIns().multiply(getPresUnitIns());
        }else {
            impIns = BigDecimal.ZERO;
        }
    }
    
    public void agregarInsumo() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        if(!cveMat.equals("") && !material.equals("") && !unidMat.equals("") && cantIns != null 
                && presUnitIns != null) {
            InsumoBean ib = new InsumoBean();
            ib.setCuenta(getCuenta());
            ib.setCodInsumo(getCveMat());
            ib.setDescripIns(getMaterial());
            ib.setUnidadIns(getUnidMat());
            ib.setCantIns(getCantIns());
            ib.setCostoIns(getPresUnitIns());
            ib.setImporteIns(getImpIns());
            ib.setCantInsCtrl(getCantIns());
            ib.setCostoInsCtrl(getPresUnitIns());
            ib.setImpInsCtrl(getImpIns());
            listaIns.add(ib);
            setListaIns(listaIns);
            sumarListaInsumos();
            limpiarCamposInsumo();
            setBtnRegCon(false);
            listaAdd.add(ib);
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                    "No se puede agregar un insumo con datos incompletos"));
            setBtnRegCon(true);
        }
        
        
            
    }
    
    public void sumarListaInsumos() {
        BigDecimal suma = BigDecimal.ZERO;
        
        if(getPresUnit()== null) {
            setPresUnit(BigDecimal.ZERO);
        }
        
        System.out.println(listaIns.size());
        
        if(listaIns.size()>0) {
            for(InsumoBean aux: listaIns) {

                suma = suma.add(aux.getImporteIns());
                setPresUnit(suma);
            }
        }else {
            setPresUnit(suma);
        }
            
        
        actualizaImporte();
        
    }
    
    public void limpiarCamposInsumo() {
        setTipoIns(null);
        setFamilia(null);
        setCveInsumo(null);
        setDescIns(null);
        setUnidad(null);
        setCantIns(null);
        setPresUnitIns(null);
        setImpIns(null);
    }
    
    public void eliminaInsumo() {
        
        listaIns.remove(insElim); 
        sumarListaInsumos();
        listaElim.add(insElim);
        setBtnRegCon(false);
        
    }
    
    public void editarInsumo() {
        
        setCveMat(insElim.getCodInsumo());
        if(cveMat.charAt(0) == '7') {
            ConexionBD c = new ConexionBD();
            PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
            setCodSubCtto(dao.buscartipoSubCtto(cveMat.substring(0, 3)));
            //System.out.println(cveMat.substring(0, 3));
        }else {
            setCodSubCtto("");
        }
        setMaterial(insElim.getDescripIns());
        setUnidMat(insElim.getUnidadIns());
        setCantIns(insElim.getCantIns());
        setPresUnitIns(insElim.getCostoIns());
        setImpIns(insElim.getImporteIns());
        //System.out.println(cveMat.charAt(0));
        
    }
    
    public void agregarCambioInsumo() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        if(!cveMat.equals("") && !material.equals("") && !unidMat.equals("") && cantIns != null 
                && presUnitIns != null) {
            
            InsumoBean ib = new InsumoBean();
            ib.setId_insumo(insElim.getId_insumo());
            ib.setCuenta(insElim.getCuenta());
            ib.setCodInsumo(getCveMat());
            ib.setDescripIns(getMaterial());
            ib.setUnidadIns(getUnidMat());
            ib.setCantIns(getCantIns());
            ib.setCostoIns(getPresUnitIns());
            ib.setImporteIns(getImpIns());
            ib.setCantInsCtrl(getCantIns());
            ib.setCostoInsCtrl(getPresUnitIns());
            ib.setImpInsCtrl(getImpIns());
            listaIns.set(listaIns.indexOf(insElim), ib);
            listaEdit.add(ib);
            sumarListaInsumos();
            limpiarCamposInsumo();
            setBtnRegCon(false);
            
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                    "No se puede agregar un insumo con datos incompletos"));
            setBtnRegCon(true);
        }
            
        
    }
    
    public void seleccionarMaterial() {
        
        setTipoIns("MATERIALES");
        setCuenta(matSel.getCuenta());
        setCveMat(matSel.getClaveMat());
        setMaterial(matSel.getMaterial());
        setUnidMat(matSel.getUnidad());
        setActCveMat(true);
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        InsumoBean ins = dao.verifClaveIns(matSel.getClaveMat(), id_presupuesto);
        if(ins.getCostoInsCtrl() != null) {
            setPresUnitIns(ins.getCostoInsCtrl());
            setActPresUnit(true);
            
        }
        setBtnSubctto(true);
        
    }
    
    public void seleccionarSC() {
        
        setTipoIns("SUBCONTRATOS");
        setCveMat(cat.getClave()+"-"+getClaveFam()+"-"+getCapConsec());
        setCveMatAux(cat.getClave()+"-"+getClaveFam()+"-"+getCapConsec());
        setCodSubCtto(cat.getDefinicion());
        
        setBtnMaterial(true);
    }
    
    public void editarConcepto() {
        
        
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        if(listaAdd.size() > 0 || listaEdit.size() > 0 || listaElim.size() > 0 || cambio) {
            
            Concepts ccp = new Concepts();
            ccp.setId_concepto(concept.getId_concepto());
            ccp.setCodConcepto(getCodConcepto());
            ccp.setDescripcion(getDescConc());
            ccp.setUnidad(getUnidConcepto());
            ccp.setCantidad(getCantidad());
            ccp.setpUnitario(getPresUnit());
            ccp.setImporte(getImporte());

            ConexionBD c = new ConexionBD();
            PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
            if(dao.editarConcepto(ccp, listaAdd, listaEdit, listaElim)) {
                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", 
                        "Se editó la información del concepto con éxito!!"));
            }else {
                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                        "No se editó la información del concepto, "
                        + "REVISE SI YA TIENE CONTRATOS O REQUISICIONES ALGUNO DE LOS INSUMOS!!"));
            }
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                        "No hay datos que editar!!"));
        }
        
        refresh();
        
    }
    
    public void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context
         .getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse(); 
    }

    /**
     * @return the listaProy
     */
    public ArrayList<ProyectoSimple> getListaProy() {
        return listaProy;
    }

    /**
     * @param listaProy the listaProy to set
     */
    public void setListaProy(ArrayList<ProyectoSimple> listaProy) {
        this.listaProy = listaProy;
    }

    /**
     * @return the id_proyecto
     */
    public int getId_proyecto() {
        return id_proyecto;
    }

    /**
     * @param id_proyecto the id_proyecto to set
     */
    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    /**
     * @return the presupuesto
     */
    public String getPresupuesto() {
        return presupuesto;
    }

    /**
     * @param presupuesto the presupuesto to set
     */
    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    /**
     * @return the id_presupuesto
     */
    public int getId_presupuesto() {
        return id_presupuesto;
    }

    /**
     * @param id_presupuesto the id_presupuesto to set
     */
    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    /**
     * @return the proyecto
     */
    public String getProyecto() {
        return proyecto;
    }

    /**
     * @param proyecto the proyecto to set
     */
    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * @return the proySel
     */
    public ProyectoSimple getProySel() {
        return proySel;
    }

    /**
     * @param proySel the proySel to set
     */
    public void setProySel(ProyectoSimple proySel) {
        this.proySel = proySel;
    }

    /**
     * @return the listaPres
     */
    public ArrayList<PresupuestoBean> getListaPres() {
        return listaPres;
    }

    /**
     * @param listaPres the listaPres to set
     */
    public void setListaPres(ArrayList<PresupuestoBean> listaPres) {
        this.listaPres = listaPres;
    }

    /**
     * @return the presB
     */
    public PresupuestoBean getPresB() {
        return presB;
    }

    /**
     * @param presB the presB to set
     */
    public void setPresB(PresupuestoBean presB) {
        this.presB = presB;
    }

    /**
     * @return the concept
     */
    public Concepts getConcept() {
        return concept;
    }

    /**
     * @param concept the concept to set
     */
    public void setConcept(Concepts concept) {
        this.concept = concept;
    }

    /**
     * @return the list
     */
    public List<Concepts> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<Concepts> list) {
        this.list = list;
    }

    /**
     * @return the concpEdit
     */
    public Concepts getConcpEdit() {
        return concpEdit;
    }

    /**
     * @param concpEdit the concpEdit to set
     */
    public void setConcpEdit(Concepts concpEdit) {
        this.concpEdit = concpEdit;
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
     * @return the descConc
     */
    public String getDescConc() {
        return descConc;
    }

    /**
     * @param descConc the descConc to set
     */
    public void setDescConc(String descConc) {
        this.descConc = descConc;
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
     * @return the unidConcepto
     */
    public String getUnidConcepto() {
        return unidConcepto;
    }

    /**
     * @param unidConcepto the unidConcepto to set
     */
    public void setUnidConcepto(String unidConcepto) {
        this.unidConcepto = unidConcepto;
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
     * @return the presUnit
     */
    public BigDecimal getPresUnit() {
        return presUnit;
    }

    /**
     * @param presUnit the presUnit to set
     */
    public void setPresUnit(BigDecimal presUnit) {
        this.presUnit = presUnit;
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
     * @return the listUnid
     */
    public List<Unidad> getListUnid() {
        return listUnid;
    }

    /**
     * @param listUnid the listUnid to set
     */
    public void setListUnid(List<Unidad> listUnid) {
        this.listUnid = listUnid;
    }

    /**
     * @return the btnInsumo
     */
    public boolean isBtnInsumo() {
        return btnInsumo;
    }

    /**
     * @param btnInsumo the btnInsumo to set
     */
    public void setBtnInsumo(boolean btnInsumo) {
        this.btnInsumo = btnInsumo;
    }

    /**
     * @return the btnMaterial
     */
    public boolean isBtnMaterial() {
        return btnMaterial;
    }

    /**
     * @param btnMaterial the btnMaterial to set
     */
    public void setBtnMaterial(boolean btnMaterial) {
        this.btnMaterial = btnMaterial;
    }

    /**
     * @return the btnSubctto
     */
    public boolean isBtnSubctto() {
        return btnSubctto;
    }

    /**
     * @param btnSubctto the btnSubctto to set
     */
    public void setBtnSubctto(boolean btnSubctto) {
        this.btnSubctto = btnSubctto;
    }

    /**
     * @return the btnRegCon
     */
    public boolean isBtnRegCon() {
        return btnRegCon;
    }

    /**
     * @param btnRegCon the btnRegCon to set
     */
    public void setBtnRegCon(boolean btnRegCon) {
        this.btnRegCon = btnRegCon;
    }

    /**
     * @return the actPresUnit
     */
    public boolean isActPresUnit() {
        return actPresUnit;
    }

    /**
     * @param actPresUnit the actPresUnit to set
     */
    public void setActPresUnit(boolean actPresUnit) {
        this.actPresUnit = actPresUnit;
    }

    /**
     * @return the actCveMat
     */
    public boolean isActCveMat() {
        return actCveMat;
    }

    /**
     * @param actCveMat the actCveMat to set
     */
    public void setActCveMat(boolean actCveMat) {
        this.actCveMat = actCveMat;
    }

    /**
     * @return the cveMat
     */
    public String getCveMat() {
        return cveMat;
    }

    /**
     * @param cveMat the cveMat to set
     */
    public void setCveMat(String cveMat) {
        this.cveMat = cveMat;
    }

    /**
     * @return the cveMatAux
     */
    public String getCveMatAux() {
        return cveMatAux;
    }

    /**
     * @param cveMatAux the cveMatAux to set
     */
    public void setCveMatAux(String cveMatAux) {
        this.cveMatAux = cveMatAux;
    }

    /**
     * @return the material
     */
    public String getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * @return the unidMat
     */
    public String getUnidMat() {
        return unidMat;
    }

    /**
     * @param unidMat the unidMat to set
     */
    public void setUnidMat(String unidMat) {
        this.unidMat = unidMat;
    }

    /**
     * @return the codSubCtto
     */
    public String getCodSubCtto() {
        return codSubCtto;
    }

    /**
     * @param codSubCtto the codSubCtto to set
     */
    public void setCodSubCtto(String codSubCtto) {
        this.codSubCtto = codSubCtto;
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
     * @return the presUnitIns
     */
    public BigDecimal getPresUnitIns() {
        return presUnitIns;
    }

    /**
     * @param presUnitIns the presUnitIns to set
     */
    public void setPresUnitIns(BigDecimal presUnitIns) {
        this.presUnitIns = presUnitIns;
    }

    /**
     * @return the impIns
     */
    public BigDecimal getImpIns() {
        return impIns;
    }

    /**
     * @param impIns the impIns to set
     */
    public void setImpIns(BigDecimal impIns) {
        this.impIns = impIns;
    }

    /**
     * @return the filtroMat
     */
    public List<MaterialBean> getFiltroMat() {
        return filtroMat;
    }

    /**
     * @param filtroMat the filtroMat to set
     */
    public void setFiltroMat(List<MaterialBean> filtroMat) {
        this.filtroMat = filtroMat;
    }

    /**
     * @return the listMat
     */
    public List<MaterialBean> getListMat() {
        return listMat;
    }

    /**
     * @param listMat the listMat to set
     */
    public void setListMat(List<MaterialBean> listMat) {
        this.listMat = listMat;
    }

    /**
     * @return the catalogo
     */
    public List<Catalogo> getCatalogo() {
        return catalogo;
    }

    /**
     * @param catalogo the catalogo to set
     */
    public void setCatalogo(List<Catalogo> catalogo) {
        this.catalogo = catalogo;
    }

    /**
     * @return the tipoIns
     */
    public String getTipoIns() {
        return tipoIns;
    }

    /**
     * @param tipoIns the tipoIns to set
     */
    public void setTipoIns(String tipoIns) {
        this.tipoIns = tipoIns;
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
     * @return the insElim
     */
    public InsumoBean getInsElim() {
        return insElim;
    }

    /**
     * @param insElim the insElim to set
     */
    public void setInsElim(InsumoBean insElim) {
        this.insElim = insElim;
    }

    /**
     * @return the familia
     */
    public String getFamilia() {
        return familia;
    }

    /**
     * @param familia the familia to set
     */
    public void setFamilia(String familia) {
        this.familia = familia;
    }

    /**
     * @return the cveInsumo
     */
    public String getCveInsumo() {
        return cveInsumo;
    }

    /**
     * @param cveInsumo the cveInsumo to set
     */
    public void setCveInsumo(String cveInsumo) {
        this.cveInsumo = cveInsumo;
    }

    /**
     * @return the descIns
     */
    public String getDescIns() {
        return descIns;
    }

    /**
     * @param descIns the descIns to set
     */
    public void setDescIns(String descIns) {
        this.descIns = descIns;
    }

    /**
     * @return the matSel
     */
    public MaterialBean getMatSel() {
        return matSel;
    }

    /**
     * @param matSel the matSel to set
     */
    public void setMatSel(MaterialBean matSel) {
        this.matSel = matSel;
    }

    /**
     * @return the cat
     */
    public Catalogo getCat() {
        return cat;
    }

    /**
     * @param cat the cat to set
     */
    public void setCat(Catalogo cat) {
        this.cat = cat;
    }

    /**
     * @return the claveFam
     */
    public String getClaveFam() {
        return claveFam;
    }

    /**
     * @param claveFam the claveFam to set
     */
    public void setClaveFam(String claveFam) {
        this.claveFam = claveFam;
    }

    /**
     * @return the capConsec
     */
    public String getCapConsec() {
        return capConsec;
    }

    /**
     * @param capConsec the capConsec to set
     */
    public void setCapConsec(String capConsec) {
        this.capConsec = capConsec;
    }

    /**
     * @return the listaAdd
     */
    public List<InsumoBean> getListaAdd() {
        return listaAdd;
    }

    /**
     * @param listaAdd the listaAdd to set
     */
    public void setListaAdd(List<InsumoBean> listaAdd) {
        this.listaAdd = listaAdd;
    }

    /**
     * @return the listaEdit
     */
    public List<InsumoBean> getListaEdit() {
        return listaEdit;
    }

    /**
     * @param listaEdit the listaEdit to set
     */
    public void setListaEdit(List<InsumoBean> listaEdit) {
        this.listaEdit = listaEdit;
    }

    /**
     * @return the listaElim
     */
    public List<InsumoBean> getListaElim() {
        return listaElim;
    }

    /**
     * @param listaElim the listaElim to set
     */
    public void setListaElim(List<InsumoBean> listaElim) {
        this.listaElim = listaElim;
    }

    /**
     * @return the cambio
     */
    public boolean isCambio() {
        return cambio;
    }

    /**
     * @param cambio the cambio to set
     */
    public void setCambio(boolean cambio) {
        this.cambio = cambio;
    }

    /**
     * @return the listaPB
     */
    public List<PartidaBean> getListaPB() {
        return listaPB;
    }

    /**
     * @param listaPB the listaPB to set
     */
    public void setListaPB(List<PartidaBean> listaPB) {
        this.listaPB = listaPB;
    }

    /**
     * @return the partSel
     */
    public PartidaBean getPartSel() {
        return partSel;
    }

    /**
     * @param partSel the partSel to set
     */
    public void setPartSel(PartidaBean partSel) {
        this.partSel = partSel;
    }

    /**
     * @return the listSubPrt
     */
    public List<PartidaBean> getListSubPrt() {
        return listSubPrt;
    }

    /**
     * @param listSubPrt the listSubPrt to set
     */
    public void setListSubPrt(List<PartidaBean> listSubPrt) {
        this.listSubPrt = listSubPrt;
    }

    /**
     * @return the subPrtSel
     */
    public PartidaBean getSubPrtSel() {
        return subPrtSel;
    }

    /**
     * @param subPrtSel the subPrtSel to set
     */
    public void setSubPrtSel(PartidaBean subPrtSel) {
        this.subPrtSel = subPrtSel;
    }

    /**
     * @return the codPrtda
     */
    public String getCodPrtda() {
        return codPrtda;
    }

    /**
     * @param codPrtda the codPrtda to set
     */
    public void setCodPrtda(String codPrtda) {
        this.codPrtda = codPrtda;
    }

    /**
     * @return the subPrtda
     */
    public String getSubPrtda() {
        return subPrtda;
    }

    /**
     * @param subPrtda the subPrtda to set
     */
    public void setSubPrtda(String subPrtda) {
        this.subPrtda = subPrtda;
    }

    /**
     * @return the filtCon
     */
    public List<Concepts> getFiltCon() {
        return filtCon;
    }

    /**
     * @param filtCon the filtCon to set
     */
    public void setFiltCon(List<Concepts> filtCon) {
        this.filtCon = filtCon;
    }

    /**
     * @return the id_subpartida
     */
    public int getId_subpartida() {
        return id_subpartida;
    }

    /**
     * @param id_subpartida the id_subpartida to set
     */
    public void setId_subpartida(int id_subpartida) {
        this.id_subpartida = id_subpartida;
    }

    /**
     * @return the id_partida
     */
    public int getId_partida() {
        return id_partida;
    }

    /**
     * @param id_partida the id_partida to set
     */
    public void setId_partida(int id_partida) {
        this.id_partida = id_partida;
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
    
}
