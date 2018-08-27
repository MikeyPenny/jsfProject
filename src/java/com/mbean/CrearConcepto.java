/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.Catalogo;
import com.bean.Concepto;
import com.bean.Concepts;
import com.bean.FamConcepto;
import com.bean.FamiliaMat;
import com.bean.InsumoBean;
import com.bean.MaterialBean;
import com.bean.Nivel;
import com.bean.PartidaBean;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bean.Unidad;
import com.bo.CrearCodigo;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.regex.Pattern;




@Named("crearConcepto")
@ViewScoped
public class CrearConcepto implements Serializable {

    private ArrayList<ProyectoSimple> listaProy = new ArrayList<>();
    private ProyectoSimple proySel;
    private String proyecto;
    private int id_proyecto;
    private String cod_proy;
    
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    private PresupuestoBean presB;
    private int id_presupuesto;
    private String presupuesto;
    
    private String codPrtda;
    private String subPrtda;
    private String tipoConcepto;
    private String codConcepto;
    private String descConc;
    
    private String unidad;
    private String unidConcepto;
    private BigDecimal cantidad;
    private BigDecimal presUnit;
    private BigDecimal importe;
    
    private String tipoIns;
    private String familia;
    private List<String> familias;
    
    private List<FamiliaMat> listaFam;
    private FamiliaMat selecFam;
    
    private List<InsumoBean> listaIns;
    private InsumoBean insElim;
    
    private List<Unidad> listUnid;
    
    private String descIns;
    private String unitSel;
    private BigDecimal cantIns;
    private BigDecimal presUnitIns;
    private BigDecimal impIns;
    
    private List<Catalogo> catalogo = new ArrayList<>();
    private Catalogo cat;
    private String codSubCtto;
    
    private List<String> catString;
    
    private List<String> listSubFam;
    
    private String cveInsumo;
    
    private String consecutivo;
    
    private List<Concepto> listConcep;
    
    private List<FamConcepto> listFamConc;
    private List<FamConcepto> listadoFamilias;
    private String famConcepto;
    private String claveFam;
    private List<String> listaStFamConc;
    
    private List<PartidaBean> listaPB;
    private PartidaBean partSel;
    
    private List<PartidaBean> listSubPrt;
    private PartidaBean subPrtSel;
    private PartidaBean partElim;
    
    private String creaPrtCve;
    private String creaPrtDesc;
    
    private String creaSubPrtCve;
    private String creaSubPrtDesc;
    
    private String cveNivel;
    private String nivel;
    private int id_nivel;
    
    private List<Nivel> listNvl;
    private Nivel selNvl;
    
    private String capConsec;
    
    private List<MaterialBean> listMat;
    private MaterialBean matSel;
    private List<MaterialBean> filtroMat;
    private String cveMat;
    private String cveMatAux;
    private String material;
    private String unidMat;
    
    private boolean btnInsumo;
    private boolean btnMaterial;
    private boolean btnSubctto;
    private boolean btnFam;
    private boolean btnPart;
    private boolean btnSubPrt;
    private boolean btnRegCon;
    private boolean actPresUnit;
    private boolean actCveMat;
    private boolean btnElimIns;
    private boolean btnEditIns;
    private int deletePart;
    private int deleteSubPart;
    
    
    private String famCons;
    
    public void eliminaInsumo() {
        
        listaIns.remove(insElim); 
        sumarListaInsumos();
        
        
    }
    
    public void eliminarPartida() {
        
        
        
        //System.out.println("elim partida");
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        deletePart = dao.eliminaPartida(id_proyecto, partElim);
        
        buscarPartida();
        
    }
    
    public void actualizarMsjElimPart() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        System.out.println("del= "+deletePart);
        if(deletePart <= 0) {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                    "La partida no puede ser eliminada"));
            System.out.println("Mensaje");
        }
    
    }
    
    public void eliminarSubPartida() {
        
        
        //System.out.println("elim partida");
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        deleteSubPart = dao.eliminaSubPartida(id_proyecto, partElim);
        
        listarSubPartidas();
        
        
    }
    
    public void actualizarMsjElimSubPart() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        
        if(deleteSubPart <= 0) {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                    "La subpartida no puede ser eliminada"));
            
        }
    
    }
    
    public void resetCreaPart() {
        
        setCreaPrtCve(null);
        setCreaPrtDesc(null);
        
    }
    
    public void resetCreaSubPart() {
        
        setCreaSubPrtCve(null);
        setCreaSubPrtDesc(null);
        
    }
    
    public void consultarFamilias() {
        
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        setListadoFamilias(dao.listarFamiliasConceptos(id_proyecto));
        
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
        
        //System.out.println(getCveMat());
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        InsumoBean ins = dao.verifDataSC(id_presupuesto, getCveMat());
        if(ins.getCostoIns() != null) {
            setCveMat(ins.getCodInsumo());
            setPresUnitIns(ins.getCostoIns());
            setMaterial(ins.getDescripIns());
            setUnidMat(ins.getUnidadIns());
            
            setActCveMat(true);
            setActPresUnit(true);
            
        }
        
    }
    
    public void seleccionarSC() {
        
        setTipoIns("SUBCONTRATOS");
        setCveMat(cat.getClave()+"-"+getClaveFam()+"-"+getCapConsec());
        setCveMatAux(cat.getClave()+"-"+getClaveFam()+"-"+getCapConsec());
        setCodSubCtto(cat.getDefinicion());
        
        setBtnMaterial(true);
    }
    
    
    
    public void consultarMateriales() {
        
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        setListMat(dao.listarMateriales());
        System.out.println(listMat.size());
        
    }
    
    public void seleccionarMaterial() {
        
        setTipoIns("MATERIALES");
        setCveMat(matSel.getClaveMat());
        setMaterial(matSel.getMaterial());
        setUnidMat(matSel.getUnidad());
        setActCveMat(true);
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        InsumoBean ins = dao.verifClaveIns(matSel.getClaveMat(), id_presupuesto);
        if(ins.getCostoIns()!= null) {
            setPresUnitIns(ins.getCostoIns());
            setActPresUnit(true);
            
        }
        setBtnSubctto(true);
        
    }
    
    public void sumarListaInsumos() {
        BigDecimal suma = BigDecimal.ZERO;
        
        if(getPresUnit()== null) {
            setPresUnit(BigDecimal.ZERO);
        }
        
        //System.out.println(listaIns.size());
        
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
    
    public void actualizarImpInsumo() {
        if(getCantIns()!= null && getPresUnitIns()!= null) {
            impIns = getCantIns().multiply(getPresUnitIns());
        }else {
            impIns = BigDecimal.ZERO;
        }
    }
    
    public void actualizaImporte() {
        
        if(getCantidad()!= null && getPresUnit() != null) {
            importe = getCantidad().multiply(getPresUnit());
        }else {
            importe = BigDecimal.ZERO;
        }
        
        
        
    }
    
    public CrearConcepto() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        listUnid = new ArrayList<>();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        listUnid = pd.listaUnidades();
        //System.out.println("Lista unidades");
        tipoIns = "";
        listaIns =  new ArrayList<>();
        btnFam = true;
        c.closeConexion();
    }
    
    public void guardarConcepto() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        //System.out.println(cantidad);
        //System.out.println("unidad"+unidConcepto);
        //System.out.println(descConc);
        
        if(!descConc.equals("") && !unidConcepto.equals("") && cantidad != null) {
            CrearCodigo crea = new CrearCodigo();
            Concepto conc = new Concepto();
            conc.setNumConcepto(crea.crearConsecutivo(id_proyecto, id_proyecto));
            conc.setCodConcepto(getCodConcepto());
            conc.setDescripcion(getDescConc());
            conc.setUnidad(getUnidConcepto());
            conc.setCantidad(getCantidad());
            conc.setpUnitario(getPresUnit());
            conc.setImporte(getImporte());
            conc.setId_partida(subPrtSel.getId_partida());
            conc.setPartida(subPrtSel.getNivel());

            ConexionBD c = new ConexionBD();
            Connection con = c.getConexion();
            PresupuestoDAO pd = new PresupuestoDAO(con);
            int reg = pd.guardarConceptoNuevo(conc, listaIns);

            

            if(reg > 0) {
                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "El concepto se registró con éxito"));
            }else {
                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El concepto no se registró!"));
            }

            limpiarCampos();
            limpiarCamposInsumo();

            refresh();
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                    "El concepto no se registró, faltan datos, revise descripción, unidad y cantidad."));
        }
        
            
        
    }
    
    @PostConstruct
    public void limpiarCampos() {
        setId_proyecto(0);
        setProyecto(null);
        setId_presupuesto(0);
        setPresupuesto(null);
        setCodPrtda(null);
        setSubPrtda(null);
        setFamConcepto(null);
        setCodConcepto(null);
        setDescConc(null);
        setUnidConcepto(null);
        setCantidad(null);
        setPresUnit(null);
        setImporte(null);
        listaIns.clear();
        setTipoIns(null);
        setFamilia(null);
        setCveInsumo(null);
        setDescIns(null);
        setUnidad(null);
        setCantIns(null);
        setPresUnitIns(null);
        setImpIns(null);
        setBtnInsumo(true);
        setBtnPart(true);
        setBtnSubPrt(true);
        setBtnRegCon(true);
        setActPresUnit(false);
        setActCveMat(false);
        setBtnElimIns(false);
        setBtnEditIns(false);
        //System.out.println("Limpio");
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
    
    public void guardarNuevaSubPrtda() {
        PartidaBean pb = new PartidaBean();
        pb.setNivel(getCreaSubPrtCve());
        pb.setEsSubNivelDe(partSel.getId_partida());
        pb.setDescripcion(getCreaSubPrtDesc());
        pb.setId_presupuesto(getId_presupuesto());
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        pd.guardarNuevaSubPrtda(pb);
        listarSubPartidas();
    }
    
    public void guardarNivel() {
        Nivel nvl = new Nivel();
        nvl.setNivel(getCveNivel());
        nvl.setEsSubNivelDe(0);
        nvl.setDescripcion(getNivel());
        nvl.setId_presupuesto(getId_presupuesto());
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        pd.guardarNuevoNivel(nvl);
        listarNivel();
    }
    
    public void listarNivel() {
        
        ConexionBD c  =  new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        setListNvl(pd.listarNiveles(id_proyecto, id_presupuesto));
    }
    
    public void obtenerNivel() {
        setCveNivel(selNvl.getNivel());
        setNivel(selNvl.getDescripcion());
        setId_nivel(selNvl.getId_partida());
        System.out.println(id_nivel);
        buscarPartida();
    }
    
    public void guardarNuevaPartida() {
        
        PartidaBean pb = new PartidaBean();
        pb.setNivel(getCreaPrtCve());
        pb.setEsSubNivelDe(0);
        pb.setDescripcion(getCreaPrtDesc());
        pb.setId_presupuesto(getId_presupuesto());
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        pd.guardarNuevaPartida(pb);
        buscarPartida();
    }
    
    public void buscarPartida() {
        ConexionBD c  =  new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        setListaPB(pd.listarPartidaBean(id_proyecto, id_presupuesto));
        
    }
    
    public void obtenerPartida() {
        setCodPrtda(partSel.getNivel());
        setBtnSubPrt(false);
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
    }
    
    public List<String> listarFamConc(List<FamConcepto> lista) {
        
        List<String> listAux = new ArrayList<>();
        
        for(FamConcepto aux:lista) {
            listAux.add(aux.getClaveFam()+"-"+aux.getSunFamilia());
        }
        
        return listAux;
    }
    
    public void crearCodigo() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        CrearCodigo cc = new CrearCodigo();
        //System.out.println(famConcepto);
        //System.out.println(id_proyecto);
        //System.out.println(id_presupuesto);
        
        setCodConcepto(cc.codificarConcepto(cod_proy, claveFam, id_presupuesto, capConsec));
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        Concepts concepto = dao.buscarDuplicados(getCodConcepto());
        if(concepto.getDescripcion() != null) {
            
            setDescConc(concepto.getDescripcion());
            setUnidConcepto(concepto.getUnidad());
            setPresUnit(concepto.getpUnitario());
            
            setListaIns(concepto.getInsumos());
            
            sumarListaInsumos();
            
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info","Este Código de Concepto ya existe!"));
            
        }else {
            setBtnInsumo(false);
        }
        
        
        
    }
    
    public void homologarConcepto() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        setFamCons(famCons.toUpperCase());
        
        
        CrearCodigo cc = new CrearCodigo();
        
        Pattern pat = Pattern.compile("([a-zA-Z0-9]{3}+)-([a-zA-Z0-9]{4}+)");
        Matcher match = pat.matcher(famCons);
        if(match.matches()) {
            //System.out.println(famCons);
            setCodConcepto(cod_proy+"-"+famCons);
            //System.out.println(codConcepto);
            ConexionBD c  =  new ConexionBD();
            PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
            Concepts cp = dao.buscarDuplicados(codConcepto);
            if(cp.getDescripcion() != null) {
            
                setDescConc(cp.getDescripcion());
                setUnidConcepto(cp.getUnidad());
                setPresUnit(cp.getpUnitario());

                setListaIns(cp.getInsumos());

                sumarListaInsumos();
                setBtnElimIns(true);
                setBtnEditIns(true);
                setBtnRegCon(false);
                setBtnInsumo(true);

                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info","Este Código de Concepto ya existe!"));

            }else {
                setBtnInsumo(false);
            }
            
            
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!","La estructura del código "
                    + "de concepto no es válida"));
            setFamCons("");
        }
        
        
        
        /*ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        Concepts concepto = dao.buscarDuplicados(getCodConcepto());
        
        
        
        if(concepto.getDescripcion() != null) {
            
            setDescConc(concepto.getDescripcion());
            setUnidConcepto(concepto.getUnidad());
            setPresUnit(concepto.getpUnitario());
            
            setListaIns(concepto.getInsumos());
            
            sumarListaInsumos();
            
        }else {
            FacesContext msj = FacesContext.getCurrentInstance();
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Este Código de Concepto no existe enel presupuesto!"));
        }*/
        
        
    }
    
    public void probarSeleccion() {
        System.out.println(claveFam);
    }
    
    public void agregarInsumo() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        if(!cveMat.equals("") && !material.equals("") && !unidMat.equals("") && cantIns != null 
                && presUnitIns != null) {
            InsumoBean ib = new InsumoBean();
            ib.setCuenta(getTipoIns());
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
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                    "No se puede agregar un insumo con datos incompletos"));
            setBtnRegCon(true);
        }
        
        
            
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
    
    public void listarConceptosConsecutivos() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        setListConcep(pd.conceptosConsecutivos(id_proyecto, id_presupuesto));
    }
    
    public void filtrarSubFamilias() {
        
        if(tipoIns.equals("sc")) {
            List<String> catSubc = new ArrayList<>();
            int ind2 = familia.indexOf("-");
            String sub;
            String id = ""+getId_proyecto();
            String idComp = "";
            for(int i = id.length(); i<3; i++) {
                idComp = idComp + "0";
            }
            idComp = idComp + id;
            sub = familia.substring(0, ind2)+"-"+idComp;
            ConexionBD c = new ConexionBD();
            Connection con = c.getConexion();
            PresupuestoDAO pd = new PresupuestoDAO(con);
            sub = sub+"-"+pd.buscarConsecutivoSC(familia.substring(0, ind2));
            catSubc.add(sub);
            setListSubFam(catSubc);
        }
        if(tipoIns.equals("mat")) {
            int ind2 = familia.indexOf("-");
            String cve = familia.substring(0, ind2);
            ConexionBD c = new ConexionBD();
            Connection con = c.getConexion();
            PresupuestoDAO pd = new PresupuestoDAO(con);
            setCatalogo(pd.listarClaves(cve));
            convertirCatSubFam(getCatalogo());
        }
    }
    
    public void convertirCatSubFam(List<Catalogo> lista) {
        
        List<String> listAux = new ArrayList<>();
        
        for(Catalogo aux: lista) {
            listAux.add(aux.getClave()+"-"+aux.getDefinicion());
        }
        
        setListSubFam(listAux);
    }
    
    public void filtrarFamiliaIns() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        if(tipoIns.equals("sc")) {
            
            setCatalogo(pd.listarCatalogo(1));
            
        }
        if(tipoIns.equals("mat")) {
            
            setCatalogo(pd.listarCatalogo(2));
        }
        convertirCatalogo(getCatalogo());
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
    
    public void convertirCatalogo(List<Catalogo> lista) {
        
        List<String> listaCatString = new ArrayList<>();
        
        for(Catalogo aux:lista) {
            listaCatString.add(aux.getClave()+"-"+aux.getDefinicion());
        }
        
        setCatString(listaCatString);
        
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
        setCod_proy(proySel.getCod_proy());
        buscarPresupuesto();
        listarFamilias();
        setBtnFam(false);
        //setBtnPart(false);
    }
    
    public void listarFamilias() {
        
        ConexionBD c = new ConexionBD();
        PresupuestoDAO pd = new PresupuestoDAO(c.getConexion());
        setListFamConc(pd.listarFamiliasConceptos(id_proyecto));
        //setListaStFamConc(listarFamConc(pd.listarFamiliasConceptos(getId_proyecto())));
        
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
    
    public void obtenerPresupuesto() {
        setId_presupuesto(presB.getId_presupuesto());
        setPresupuesto(presB.getPresupuesto());
        buscarPartida();
        setBtnPart(false);
        
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
            sumarListaInsumos();
            limpiarCamposInsumo();
            setBtnRegCon(false);
            
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                    "No se puede agregar un insumo con datos incompletos"));
            setBtnRegCon(true);
        }
            
        
    }

    /**
     * @return the listaProy
     */
    public ArrayList<ProyectoSimple> getListaProy() {
        buscarProyecto();
        return listaProy;
    }

    /**
     * @param listaProy the listaProy to set
     */
    public void setListaProy(ArrayList<ProyectoSimple> listaProy) {
        this.listaProy = listaProy;
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
     * @return the tipoConcepto
     */
    public String getTipoConcepto() {
        return tipoConcepto;
    }

    /**
     * @param tipoConcepto the tipoConcepto to set
     */
    public void setTipoConcepto(String tipoConcepto) {
        this.tipoConcepto = tipoConcepto;
    }

    /**
     * @return the codSubPrtda
     */
    public String getCodPrtda() {
        return codPrtda;
    }

    /**
     * @param codSubPrtda the codSubPrtda to set
     */
    public void setCodPrtda(String codPrtda) {
        this.codPrtda = codPrtda;
    }

    /**
     * @return the codPrtda
     */
    public String getSubPrtda() {
        return subPrtda;
    }

    /**
     * @param codPrtda the codPrtda to set
     */
    public void setSubPrtda(String subPrtda) {
        this.subPrtda = subPrtda;
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
     * @return the unitSel
     */
    public String getUnitSel() {
        return unitSel;
    }

    /**
     * @param unitSel the unitSel to set
     */
    public void setUnitSel(String unitSel) {
        this.unitSel = unitSel;
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
     * @return the familias
     */
    public List<String> getFamilias() {
        return familias;
    }

    /**
     * @param familias the familias to set
     */
    public void setFamilias(List<String> familias) {
        this.familias = familias;
    }

    /**
     * @return the listaFam
     */
    public List<FamiliaMat> getListaFam() {
        return listaFam;
    }

    /**
     * @param listaFam the listaFam to set
     */
    public void setListaFam(List<FamiliaMat> listaFam) {
        this.listaFam = listaFam;
    }

    /**
     * @return the selecFam
     */
    public FamiliaMat getSelecFam() {
        return selecFam;
    }

    /**
     * @param selecFam the selecFam to set
     */
    public void setSelecFam(FamiliaMat selecFam) {
        this.selecFam = selecFam;
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
     * @return the catString
     */
    public List<String> getCatString() {
        return catString;
    }

    /**
     * @param catString the catString to set
     */
    public void setCatString(List<String> catString) {
        this.catString = catString;
    }

    /**
     * @return the listSubFam
     */
    public List<String> getListSubFam() {
        return listSubFam;
    }

    /**
     * @param listSubFam the listSubFam to set
     */
    public void setListSubFam(List<String> listSubFam) {
        this.listSubFam = listSubFam;
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
     * @return the consecutivo
     */
    public String getConsecutivo() {
        return consecutivo;
    }

    /**
     * @param consecutivo the consecutivo to set
     */
    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    /**
     * @return the listConcep
     */
    public List<Concepto> getListConcep() {
        return listConcep;
    }

    /**
     * @param listConcep the listConcep to set
     */
    public void setListConcep(List<Concepto> listConcep) {
        this.listConcep = listConcep;
    }

    /**
     * @return the famConcepto
     */
    public String getFamConcepto() {
        return famConcepto;
    }

    /**
     * @param famConcepto the famConcepto to set
     */
    public void setFamConcepto(String famConcepto) {
        this.famConcepto = famConcepto;
    }

    /**
     * @return the listFamConc
     */
    public List<FamConcepto> getListFamConc() {
        return listFamConc;
    }

    /**
     * @param listFamConc the listFamConc to set
     */
    public void setListFamConc(List<FamConcepto> listFamConc) {
        this.listFamConc = listFamConc;
    }

    /**
     * @return the listaStFamConc
     */
    public List<String> getListaStFamConc() {
        return listaStFamConc;
    }

    /**
     * @param listaStFamConc the listaStFamConc to set
     */
    public void setListaStFamConc(List<String> listaStFamConc) {
        this.listaStFamConc = listaStFamConc;
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
     * @return the creaPrtCve
     */
    public String getCreaPrtCve() {
        return creaPrtCve;
    }

    /**
     * @param creaPrtCve the creaPrtCve to set
     */
    public void setCreaPrtCve(String creaPrtCve) {
        this.creaPrtCve = creaPrtCve;
    }

    /**
     * @return the creaPrtDesc
     */
    public String getCreaPrtDesc() {
        return creaPrtDesc;
    }

    /**
     * @param creaPrtDesc the creaPrtDesc to set
     */
    public void setCreaPrtDesc(String creaPrtDesc) {
        this.creaPrtDesc = creaPrtDesc;
    }

    /**
     * @return the creaSubPrtCve
     */
    public String getCreaSubPrtCve() {
        return creaSubPrtCve;
    }

    /**
     * @param creaSubPrtCve the creaSubPrtCve to set
     */
    public void setCreaSubPrtCve(String creaSubPrtCve) {
        this.creaSubPrtCve = creaSubPrtCve;
    }

    /**
     * @return the creaSubPrtDesc
     */
    public String getCreaSubPrtDesc() {
        return creaSubPrtDesc;
    }

    /**
     * @param creaSubPrtDesc the creaSubPrtDesc to set
     */
    public void setCreaSubPrtDesc(String creaSubPrtDesc) {
        this.creaSubPrtDesc = creaSubPrtDesc;
    }

    /**
     * @return the cveNivel
     */
    public String getCveNivel() {
        return cveNivel;
    }

    /**
     * @param cveNivel the cveNivel to set
     */
    public void setCveNivel(String cveNivel) {
        this.cveNivel = cveNivel;
    }

    /**
     * @return the nivel
     */
    public String getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the id_nivel
     */
    public int getId_nivel() {
        return id_nivel;
    }

    /**
     * @param id_nivel the id_nivel to set
     */
    public void setId_nivel(int id_nivel) {
        this.id_nivel = id_nivel;
    }

    /**
     * @return the listNvl
     */
    public List<Nivel> getListNvl() {
        return listNvl;
    }

    /**
     * @param listNvl the listNvl to set
     */
    public void setListNvl(List<Nivel> listNvl) {
        this.listNvl = listNvl;
    }

    /**
     * @return the selNvl
     */
    public Nivel getSelNvl() {
        return selNvl;
    }

    /**
     * @param selNvl the selNvl to set
     */
    public void setSelNvl(Nivel selNvl) {
        this.selNvl = selNvl;
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
     * @return the btnFam
     */
    public boolean isBtnFam() {
        return btnFam;
    }

    /**
     * @param btnFam the btnFam to set
     */
    public void setBtnFam(boolean btnFam) {
        this.btnFam = btnFam;
    }

    /**
     * @return the listadoFamilias
     */
    public List<FamConcepto> getListadoFamilias() {
        return listadoFamilias;
    }

    /**
     * @param listadoFamilias the listadoFamilias to set
     */
    public void setListadoFamilias(List<FamConcepto> listadoFamilias) {
        this.listadoFamilias = listadoFamilias;
    }

    /**
     * @return the cod_proy
     */
    public String getCod_proy() {
        return cod_proy;
    }

    /**
     * @param cod_proy the cod_proy to set
     */
    public void setCod_proy(String cod_proy) {
        this.cod_proy = cod_proy;
    }

    /**
     * @return the partElim
     */
    public PartidaBean getPartElim() {
        return partElim;
    }

    /**
     * @param partElim the partElim to set
     */
    public void setPartElim(PartidaBean partElim) {
        this.partElim = partElim;
    }

    /**
     * @return the btnPart
     */
    public boolean isBtnPart() {
        return btnPart;
    }

    /**
     * @param btnPart the btnPart to set
     */
    public void setBtnPart(boolean btnPart) {
        this.btnPart = btnPart;
    }

    /**
     * @return the btnSubPrt
     */
    public boolean isBtnSubPrt() {
        return btnSubPrt;
    }

    /**
     * @param btnSubPrt the btnSubPrt to set
     */
    public void setBtnSubPrt(boolean btnSubPrt) {
        this.btnSubPrt = btnSubPrt;
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
     * @return the deletePart
     */
    public int getDeletePart() {
        return deletePart;
    }

    /**
     * @param deletePart the deletePart to set
     */
    public void setDeletePart(int deletePart) {
        this.deletePart = deletePart;
    }

    /**
     * @return the deleteSubPart
     */
    public int getDeleteSubPart() {
        return deleteSubPart;
    }

    /**
     * @param deleteSubPart the deleteSubPart to set
     */
    public void setDeleteSubPart(int deleteSubPart) {
        this.deleteSubPart = deleteSubPart;
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
     * @return the famCons
     */
    public String getFamCons() {
        return famCons;
    }

    /**
     * @param famCons the famCons to set
     */
    public void setFamCons(String famCons) {
        this.famCons = famCons;
    }

    /**
     * @return the btnElimIns
     */
    public boolean isBtnElimIns() {
        return btnElimIns;
    }

    /**
     * @param btnElimIns the btnElimIns to set
     */
    public void setBtnElimIns(boolean btnElimIns) {
        this.btnElimIns = btnElimIns;
    }

    /**
     * @return the btnEditIns
     */
    public boolean isBtnEditIns() {
        return btnEditIns;
    }

    /**
     * @param btnEditIns the btnEditIns to set
     */
    public void setBtnEditIns(boolean btnEditIns) {
        this.btnEditIns = btnEditIns;
    }
}
