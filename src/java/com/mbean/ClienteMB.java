/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.Apoderado;
import com.bean.Banco;
import com.bean.ClienteAltaBean;
import com.bo.EditBO;
import com.conexion.ConexionBD;
import com.dao.ClienteDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;


@ManagedBean(name = "cliente")
@ViewScoped
public class ClienteMB implements Serializable {

    
    public ClienteMB() {
    }
    
    private String razon;
    private String fechConst;
    private String nacionalidad;
    private String giro;
    private String rfcCliente;
    
    private String calleFisc;
    private String extFisc;
    private String intFisc;
    private String colFisc;
    private String delFisc;
    private String cpFisc;
    private String entFedFisc;
    private String ladaFisc;
    private String telFisc;
    private String exFisc;
    private String emailFisc;
    
    private String calleSoc;
    private String extSoc;
    private String intSoc;
    private String colSoc;
    private String delSoc;
    private String cpSoc;
    private String entFedSoc;
    private String ladaSoc;
    private String telSoc;
    private String exSoc;
    private String emailSoc;
    
    
    private String nombApod;
    private String patApod;
    private String matApod;
    private String rfcApod;
    private String curpApod;
    private String diaNacAPod;
    private String mesNacApod;
    private String yearNacApod;
    
    private boolean testim;
    private boolean cedula;
    private boolean compDom;
    private boolean testPodLegal;
    private boolean idApod;
    private boolean fm23;
    private boolean constBenef;
    private boolean modif;
    
    private String espModif;
    
    private String nomBenef;
    private String apePatBenef;
    private String apeMatBenef;
    private String diaNacBenef;
    private String mesNacBenef;
    private String yearNacBenef;
    private String rfcBenef;
    private String curpBenef;
    
    private String banco;
    private String plaza;
    private String cta;
    private String clabe;
    private String metodo;
    
    private List<Apoderado> listApod = new ArrayList<>();
    
    private List<Banco> listBanco = new ArrayList<>();
    
    public void probarBoleanos() {
        System.out.println(isTestim());
    }
    
    public void agregarBanco() {
        
        Banco b = new Banco();
        b.setBanco(getBanco());
        b.setPlaza(getPlaza());
        b.setCta(getCta());
        b.setClabe(getClabe());
        b.setMetodo(getMetodo());
        b.setNomBenef(getNomBenef());
        b.setApePatBenef(getApePatBenef());
        b.setApeMatBenef(getApeMatBenef());
        b.setDiaNacBenef(getDiaNacBenef());
        b.setMesNacBenef(getMesNacBenef());
        b.setYearNacBenef(getYearNacBenef());
        b.setRfcBenef(getRfcBenef());
        b.setCurpBenef(getCurpBenef());
        
        listBanco.add(b);
        
        /*setBanco(null);
        setPlaza(null);
        setCta(null);
        setClabe(null);
        setMetodo(null);
        setNomBenef(null);
        setApePatBenef(null);
        setApeMatBenef(null);
        setDiaNacBenef(null);
        setMesNacBenef(null);
        setYearNacBenef(null);
        setRfcBenef(null);
        setCurpBenef(null);*/
        
    }
    
    public void cleanBanco() {
        
        setBanco(null);
        setPlaza(null);
        setCta(null);
        setClabe(null);
        setMetodo(null);
        setNomBenef(null);
        setApePatBenef(null);
        setApeMatBenef(null);
        setDiaNacBenef(null);
        setMesNacBenef(null);
        setYearNacBenef(null);
        setRfcBenef(null);
        setCurpBenef(null);
        
    }
    
    public void cleanFields() {
        
        System.out.println("Cleaning");
        setRazon(null);
        setFechConst(null);
        setNacionalidad(null);
        setGiro(null);

        setCalleFisc(null);
        setExtFisc(null);
        setIntFisc(null);
        setColFisc(null);
        setDelFisc(null);
        setCpFisc(null);
        setEntFedFisc(null);
        setLadaFisc(null);
        setTelFisc(null);
        setExFisc(null);
        setEmailFisc(null);

        setCalleSoc(null);
        setExtSoc(null);
        setIntSoc(null);
        setColSoc(null);
        setDelSoc(null);
        setCpSoc(null);
        setEntFedSoc(null);
        setLadaSoc(null);
        setTelSoc(null);
        setExSoc(null);
        setEmailSoc(null);


        setNombApod(null);
        setPatApod(null);
        setMatApod(null);
        setRfcApod(null);
        setCurpApod(null);
        setDiaNacAPod(null);
        setMesNacApod(null);
        setYearNacApod(null);

        setTestim(false);
        setCedula(false);
        setCompDom(false);
        setTestPodLegal(false);
        setIdApod(false);
        setFm23(false);
        setConstBenef(false);
        setModif(false);

        setEspModif(null);

        setNomBenef(null);
        setApePatBenef(null);
        setApeMatBenef(null);
        setDiaNacBenef(null);
        setMesNacBenef(null);
        setYearNacBenef(null);
        setRfcBenef(null);
        setCurpBenef(null);

        setBanco(null);
        setPlaza(null);
        setCta(null);
        setClabe(null);
        setMetodo(null);
        
        listApod.clear();
        listBanco.clear();
        
    }
    
    public void agregarApoderado() {
        Apoderado ap = new Apoderado();
        ap.setNombApod(getNombApod());
        ap.setPatApod(getPatApod());
        ap.setMatApod(getMatApod());
        ap.setRfcApod(getRfcApod());
        ap.setCurpApod(getCurpApod());
        ap.setdNacAPod(getDiaNacAPod());
        ap.setmNacApod(getMesNacApod());
        ap.setyNacApod(getYearNacApod());
        if(!ap.getdNacAPod().equals("")) {
            ap.setFechaNacApod(getDiaNacAPod()+"/"+getMesNacApod()+"/"+getYearNacApod());
        }else {
            ap.setFechaNacApod("");
        }
        
        
        listApod.add(ap);
        
        setNombApod(null);
        setPatApod(null);
        setMatApod(null);
        setRfcApod(null);
        setCurpApod(null);
        setDiaNacAPod(null);
        setMesNacApod(null);
        setYearNacApod(null);
        
    }
    
    public void crearCliente() {
        System.out.println("Registrando...");
        ClienteAltaBean client = new ClienteAltaBean();
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        if(getRazon().equals("") || getFechConst().equals("")) {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "La razón social o la Fecha de Cosntitución no pueden estar vaciós"));
            System.out.println("Vacío");
        }else {
            
            client.setRazon(getRazon());
            client.setFechConst(getFechConst());
            client.setNacionalidad(getNacionalidad());
            client.setGiro(getGiro());
            client.setRfcCliente(getRfcCliente());
            client.setCalleFisc(getCalleFisc());
            client.setExFisc(getExFisc());
            client.setIntFisc(getIntFisc());
            client.setColFisc(getColFisc());
            client.setDelFisc(getDelFisc());
            client.setCpFisc(getCpFisc());
            client.setEntFedFisc(getEntFedFisc());
            client.setLadaFisc(getLadaFisc());
            client.setTelFisc(getTelFisc());
            client.setExtFisc(getExtFisc());
            client.setEmailFisc(getEmailFisc());
            client.setCalleSoc(getCalleSoc());
            client.setExtSoc(getExtSoc());
            client.setIntSoc(getIntSoc());
            client.setColSoc(getColSoc());
            client.setDelSoc(getDelSoc());
            client.setCpSoc(getCpSoc());
            client.setEntFedSoc(getEntFedSoc());
            client.setLadaSoc(getLadaSoc());
            client.setTelSoc(getTelSoc());
            client.setExSoc(getExSoc());
            client.setEmailSoc(getEmailSoc());
            client.setTestim(isTestim());
            client.setCedula(isCedula());
            client.setCompDom(isCompDom());
            client.setTestPodLegal(isTestPodLegal());
            client.setIdApod(isIdApod());
            client.setFm23(isFm23());
            client.setConstBenef(isConstBenef());
            client.setModif(isModif());
            client.setEspModif(getEspModif());
            
            //System.out.println(getRfcCliente());
            ConexionBD c = new ConexionBD();
            ClienteDAO cd = new ClienteDAO(c.getConexion());
            int reg = cd.insertCliente(client, listBanco, listApod);
            
            if(reg > 0) {
                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "El cliente ha sido registrado"));
            }else {
                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrió un problema con su registro, favor de revisar los datos"));
            }
            
            
            
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
     * @return the razon
     */
    public String getRazon() {
        return razon;
    }

    /**
     * @param razon the razon to set
     */
    public void setRazon(String razon) {
        this.razon = razon;
    }

    /**
     * @return the fechConst
     */
    public String getFechConst() {
        return fechConst;
    }

    /**
     * @param fechConst the fechConst to set
     */
    public void setFechConst(String fechConst) {
        this.fechConst = fechConst;
    }

    /**
     * @return the nacionalidad
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * @param nacionalidad the nacionalidad to set
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * @return the giro
     */
    public String getGiro() {
        return giro;
    }

    /**
     * @param giro the giro to set
     */
    public void setGiro(String giro) {
        this.giro = giro;
    }

    /**
     * @return the calleFisc
     */
    public String getCalleFisc() {
        return calleFisc;
    }

    /**
     * @param calleFisc the calleFisc to set
     */
    public void setCalleFisc(String calleFisc) {
        this.calleFisc = calleFisc;
    }

    /**
     * @return the extFisc
     */
    public String getExtFisc() {
        return extFisc;
    }

    /**
     * @param extFisc the extFisc to set
     */
    public void setExtFisc(String extFisc) {
        this.extFisc = extFisc;
    }

    /**
     * @return the intFisc
     */
    public String getIntFisc() {
        return intFisc;
    }

    /**
     * @param intFisc the intFisc to set
     */
    public void setIntFisc(String intFisc) {
        this.intFisc = intFisc;
    }

    /**
     * @return the colFisc
     */
    public String getColFisc() {
        return colFisc;
    }

    /**
     * @param colFisc the colFisc to set
     */
    public void setColFisc(String colFisc) {
        this.colFisc = colFisc;
    }

    /**
     * @return the delFisc
     */
    public String getDelFisc() {
        return delFisc;
    }

    /**
     * @param delFisc the delFisc to set
     */
    public void setDelFisc(String delFisc) {
        this.delFisc = delFisc;
    }

    /**
     * @return the cpFisc
     */
    public String getCpFisc() {
        return cpFisc;
    }

    /**
     * @param cpFisc the cpFisc to set
     */
    public void setCpFisc(String cpFisc) {
        this.cpFisc = cpFisc;
    }

    /**
     * @return the entFedFisc
     */
    public String getEntFedFisc() {
        return entFedFisc;
    }

    /**
     * @param entFedFisc the entFedFisc to set
     */
    public void setEntFedFisc(String entFedFisc) {
        this.entFedFisc = entFedFisc;
    }

    /**
     * @return the ladaFisc
     */
    public String getLadaFisc() {
        return ladaFisc;
    }

    /**
     * @param ladaFisc the ladaFisc to set
     */
    public void setLadaFisc(String ladaFisc) {
        this.ladaFisc = ladaFisc;
    }

    /**
     * @return the telFisc
     */
    public String getTelFisc() {
        return telFisc;
    }

    /**
     * @param telFisc the telFisc to set
     */
    public void setTelFisc(String telFisc) {
        this.telFisc = telFisc;
    }

    /**
     * @return the exFisc
     */
    public String getExFisc() {
        return exFisc;
    }

    /**
     * @param exFisc the exFisc to set
     */
    public void setExFisc(String exFisc) {
        this.exFisc = exFisc;
    }

    /**
     * @return the emailFisc
     */
    public String getEmailFisc() {
        return emailFisc;
    }

    /**
     * @param emailFisc the emailFisc to set
     */
    public void setEmailFisc(String emailFisc) {
        this.emailFisc = emailFisc;
    }

    /**
     * @return the calleSoc
     */
    public String getCalleSoc() {
        return calleSoc;
    }

    /**
     * @param calleSoc the calleSoc to set
     */
    public void setCalleSoc(String calleSoc) {
        this.calleSoc = calleSoc;
    }

    /**
     * @return the extSoc
     */
    public String getExtSoc() {
        return extSoc;
    }

    /**
     * @param extSoc the extSoc to set
     */
    public void setExtSoc(String extSoc) {
        this.extSoc = extSoc;
    }

    /**
     * @return the intSoc
     */
    public String getIntSoc() {
        return intSoc;
    }

    /**
     * @param intSoc the intSoc to set
     */
    public void setIntSoc(String intSoc) {
        this.intSoc = intSoc;
    }

    /**
     * @return the colSoc
     */
    public String getColSoc() {
        return colSoc;
    }

    /**
     * @param colSoc the colSoc to set
     */
    public void setColSoc(String colSoc) {
        this.colSoc = colSoc;
    }

    /**
     * @return the delSoc
     */
    public String getDelSoc() {
        return delSoc;
    }

    /**
     * @param delSoc the delSoc to set
     */
    public void setDelSoc(String delSoc) {
        this.delSoc = delSoc;
    }

    /**
     * @return the cpSoc
     */
    public String getCpSoc() {
        return cpSoc;
    }

    /**
     * @param cpSoc the cpSoc to set
     */
    public void setCpSoc(String cpSoc) {
        this.cpSoc = cpSoc;
    }

    /**
     * @return the entFedSoc
     */
    public String getEntFedSoc() {
        return entFedSoc;
    }

    /**
     * @param entFedSoc the entFedSoc to set
     */
    public void setEntFedSoc(String entFedSoc) {
        this.entFedSoc = entFedSoc;
    }

    /**
     * @return the ladaSoc
     */
    public String getLadaSoc() {
        return ladaSoc;
    }

    /**
     * @param ladaSoc the ladaSoc to set
     */
    public void setLadaSoc(String ladaSoc) {
        this.ladaSoc = ladaSoc;
    }

    /**
     * @return the telSoc
     */
    public String getTelSoc() {
        return telSoc;
    }

    /**
     * @param telSoc the telSoc to set
     */
    public void setTelSoc(String telSoc) {
        this.telSoc = telSoc;
    }

    /**
     * @return the exSoc
     */
    public String getExSoc() {
        return exSoc;
    }

    /**
     * @param exSoc the exSoc to set
     */
    public void setExSoc(String exSoc) {
        this.exSoc = exSoc;
    }

    /**
     * @return the emailSoc
     */
    public String getEmailSoc() {
        return emailSoc;
    }

    /**
     * @param emailSoc the emailSoc to set
     */
    public void setEmailSoc(String emailSoc) {
        this.emailSoc = emailSoc;
    }

    /**
     * @return the nombApod
     */
    public String getNombApod() {
        return nombApod;
    }

    /**
     * @param nombApod the nombApod to set
     */
    public void setNombApod(String nombApod) {
        this.nombApod = nombApod;
    }

    /**
     * @return the patApod
     */
    public String getPatApod() {
        return patApod;
    }

    /**
     * @param patApod the patApod to set
     */
    public void setPatApod(String patApod) {
        this.patApod = patApod;
    }

    /**
     * @return the matApod
     */
    public String getMatApod() {
        return matApod;
    }

    /**
     * @param matApod the matApod to set
     */
    public void setMatApod(String matApod) {
        this.matApod = matApod;
    }

    /**
     * @return the rfcApod
     */
    public String getRfcApod() {
        return rfcApod;
    }

    /**
     * @param rfcApod the rfcApod to set
     */
    public void setRfcApod(String rfcApod) {
        this.rfcApod = rfcApod;
    }

    /**
     * @return the curpApod
     */
    public String getCurpApod() {
        return curpApod;
    }

    /**
     * @param curpApod the curpApod to set
     */
    public void setCurpApod(String curpApod) {
        this.curpApod = curpApod;
    }

    /**
     * @return the dNacAPod
     */
    public String getDiaNacAPod() {
        return diaNacAPod;
    }

    /**
     * @param dNacAPod the dNacAPod to set
     */
    public void setDiaNacAPod(String diaNacAPod) {
        this.diaNacAPod = diaNacAPod;
    }

    /**
     * @return the mNacApod
     */
    public String getMesNacApod() {
        return mesNacApod;
    }

    /**
     * @param mNacApod the mNacApod to set
     */
    public void setMesNacApod(String mesNacApod) {
        this.mesNacApod = mesNacApod;
    }

    /**
     * @return the yNacApod
     */
    public String getYearNacApod() {
        return yearNacApod;
    }

    /**
     * @param yNacApod the yNacApod to set
     */
    public void setYearNacApod(String yearNacApod) {
        this.yearNacApod = yearNacApod;
    }

    /**
     * @return the testim
     */
    public boolean isTestim() {
        return testim;
    }

    /**
     * @param testim the testim to set
     */
    public void setTestim(boolean testim) {
        this.testim = testim;
    }

    /**
     * @return the cedula
     */
    public boolean isCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(boolean cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the compDom
     */
    public boolean isCompDom() {
        return compDom;
    }

    /**
     * @param compDom the compDom to set
     */
    public void setCompDom(boolean compDom) {
        this.compDom = compDom;
    }

    /**
     * @return the testPodLegal
     */
    public boolean isTestPodLegal() {
        return testPodLegal;
    }

    /**
     * @param testPodLegal the testPodLegal to set
     */
    public void setTestPodLegal(boolean testPodLegal) {
        this.testPodLegal = testPodLegal;
    }

    /**
     * @return the idApod
     */
    public boolean isIdApod() {
        return idApod;
    }

    /**
     * @param idApod the idApod to set
     */
    public void setIdApod(boolean idApod) {
        this.idApod = idApod;
    }

    /**
     * @return the fm23
     */
    public boolean isFm23() {
        return fm23;
    }

    /**
     * @param fm23 the fm23 to set
     */
    public void setFm23(boolean fm23) {
        this.fm23 = fm23;
    }

    /**
     * @return the constBenef
     */
    public boolean isConstBenef() {
        return constBenef;
    }

    /**
     * @param constBenef the constBenef to set
     */
    public void setConstBenef(boolean constBenef) {
        this.constBenef = constBenef;
    }

    /**
     * @return the modif
     */
    public boolean isModif() {
        return modif;
    }

    /**
     * @param modif the modif to set
     */
    public void setModif(boolean modif) {
        this.modif = modif;
    }

    /**
     * @return the espModif
     */
    public String getEspModif() {
        return espModif;
    }

    /**
     * @param espModif the espModif to set
     */
    public void setEspModif(String espModif) {
        this.espModif = espModif;
    }

    /**
     * @return the nomBenef
     */
    public String getNomBenef() {
        return nomBenef;
    }

    /**
     * @param nomBenef the nomBenef to set
     */
    public void setNomBenef(String nomBenef) {
        this.nomBenef = nomBenef;
    }

    /**
     * @return the aPBenef
     */
    public String getApePatBenef() {
        return apePatBenef;
    }

    /**
     * @param aPBenef the aPBenef to set
     */
    public void setApePatBenef(String apePatBenef) {
        this.apePatBenef = apePatBenef;
    }

    /**
     * @return the aMBenef
     */
    public String getApeMatBenef() {
        return apeMatBenef;
    }

    /**
     * @param aMBenef the aMBenef to set
     */
    public void setApeMatBenef(String apeMatBenef) {
        this.apeMatBenef = apeMatBenef;
    }

    /**
     * @return the dNacBenef
     */
    public String getDiaNacBenef() {
        return diaNacBenef;
    }

    /**
     * @param dNacBenef the dNacBenef to set
     */
    public void setDiaNacBenef(String diaNacBenef) {
        this.diaNacBenef = diaNacBenef;
    }

    /**
     * @return the mNacBenef
     */
    public String getMesNacBenef() {
        return mesNacBenef;
    }

    /**
     * @param mNacBenef the mNacBenef to set
     */
    public void setMesNacBenef(String mesNacBenef) {
        this.mesNacBenef = mesNacBenef;
    }

    /**
     * @return the yNacBenef
     */
    public String getYearNacBenef() {
        return yearNacBenef;
    }

    /**
     * @param yNacBenef the yNacBenef to set
     */
    public void setYearNacBenef(String yearNacBenef) {
        this.yearNacBenef = yearNacBenef;
    }

    /**
     * @return the rfcBenef
     */
    public String getRfcBenef() {
        return rfcBenef;
    }

    /**
     * @param rfcBenef the rfcBenef to set
     */
    public void setRfcBenef(String rfcBenef) {
        this.rfcBenef = rfcBenef;
    }

    /**
     * @return the curpBenef
     */
    public String getCurpBenef() {
        return curpBenef;
    }

    /**
     * @param curpBenef the curpBenef to set
     */
    public void setCurpBenef(String curpBenef) {
        this.curpBenef = curpBenef;
    }

    /**
     * @return the banco
     */
    public String getBanco() {
        return banco;
    }

    /**
     * @param banco the banco to set
     */
    public void setBanco(String banco) {
        this.banco = banco;
    }

    /**
     * @return the plaza
     */
    public String getPlaza() {
        return plaza;
    }

    /**
     * @param plaza the plaza to set
     */
    public void setPlaza(String plaza) {
        this.plaza = plaza;
    }

    /**
     * @return the cta
     */
    public String getCta() {
        return cta;
    }

    /**
     * @param cta the cta to set
     */
    public void setCta(String cta) {
        this.cta = cta;
    }

    /**
     * @return the clabe
     */
    public String getClabe() {
        return clabe;
    }

    /**
     * @param clabe the clabe to set
     */
    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    /**
     * @return the metodo
     */
    public String getMetodo() {
        return metodo;
    }

    /**
     * @param metodo the metodo to set
     */
    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    /**
     * @return the listApod
     */
    public List<Apoderado> getListApod() {
        return listApod;
    }

    /**
     * @param listApod the listApod to set
     */
    public void setListApod(List<Apoderado> listApod) {
        this.listApod = listApod;
    }

    /**
     * @return the listBanco
     */
    public List<Banco> getListBanco() {
        return listBanco;
    }

    /**
     * @param listBanco the listBanco to set
     */
    public void setListBanco(List<Banco> listBanco) {
        this.listBanco = listBanco;
    }

    /**
     * @return the rfcCliente
     */
    public String getRfcCliente() {
        return rfcCliente;
    }

    /**
     * @param rfcCliente the rfcCliente to set
     */
    public void setRfcCliente(String rfcCliente) {
        this.rfcCliente = rfcCliente;
    }
    
}
