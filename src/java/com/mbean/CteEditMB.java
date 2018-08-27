
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
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("edicion")
@ViewScoped
public class CteEditMB implements Serializable {
    
    private ClienteAltaBean clienteSel;
    
    private String razon;
    private String fechConst;
    private String nacionalidad;
    private String giro;
    private String rfcCliente;
    private int id_cliente;
    
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
    private Apoderado apodSel;
    private List<Banco> listBanco = new ArrayList<>();
    private Banco bancoSel;
    
    private List<Integer> bankDelete = new ArrayList<>();
    private List<Integer> apodDelete = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        EditaCteMB edit = context.getApplication().evaluateExpressionGet(context, "#{editCte}", EditaCteMB.class);
        
        setClienteSel(edit.getCte());
        
        //System.out.println(clienteSel.getRazon());
        
        seleccionarCliente();
        
    }
    
    public void seleccionarCliente() {
        
        setId_cliente(clienteSel.getId_cliente());
        setRazon(clienteSel.getRazon());
        setFechConst(clienteSel.getFechConst());
        setNacionalidad(clienteSel.getNacionalidad());
        setGiro(clienteSel.getGiro());
        setRfcCliente(clienteSel.getRfcCliente());
        setCalleFisc(clienteSel.getCalleFisc());
        setExtFisc(clienteSel.getExtFisc());
        setIntFisc(clienteSel.getIntFisc());
        setColFisc(clienteSel.getColFisc());
        setDelFisc(clienteSel.getDelFisc());
        setCpFisc(clienteSel.getCpFisc());
        setEntFedFisc(clienteSel.getEntFedFisc());
        setLadaFisc(clienteSel.getLadaFisc());
        setTelFisc(clienteSel.getTelFisc());
        setExtFisc(clienteSel.getExtFisc());
        setEmailFisc(clienteSel.getEmailFisc());
        setCalleSoc(clienteSel.getCalleSoc());
        setExtSoc(clienteSel.getExtSoc());
        setIntSoc(clienteSel.getIntSoc());
        setColSoc(clienteSel.getColSoc());
        setDelSoc(clienteSel.getDelSoc());
        setCpSoc(clienteSel.getCpSoc());
        setEntFedSoc(clienteSel.getEntFedSoc());
        setLadaSoc(clienteSel.getLadaSoc());
        setTelSoc(clienteSel.getTelSoc());
        setExSoc(clienteSel.getExSoc());
        setEmailSoc(clienteSel.getEmailSoc());
        setTestim(clienteSel.isTestim());
        setCedula(clienteSel.isCedula());
        setCompDom(clienteSel.isCompDom());
        setTestPodLegal(clienteSel.isTestPodLegal());
        setIdApod(clienteSel.isIdApod());
        setFm23(clienteSel.isFm23());
        setConstBenef(clienteSel.isConstBenef());
        setModif(clienteSel.isModif());
        setEspModif(clienteSel.getEspModif());
        
        ConexionBD c = new ConexionBD();
        ClienteDAO cd = new ClienteDAO(c.getConexion());
        setListBanco(cd.listarBancos(clienteSel.getId_cliente()));
        
        c = new ConexionBD();
        cd = new ClienteDAO(c.getConexion());
        setListApod(cd.listarApoderados(clienteSel.getId_cliente()));
        
    }
    
    
    public void editarCliente() {
        
        ClienteAltaBean client = new ClienteAltaBean();
        client.setId_cliente(getId_cliente());
        client.setRazon(getRazon());
        client.setFechConst(getFechConst());
        client.setNacionalidad(getNacionalidad());
        client.setGiro(getGiro());
        client.setRfcCliente(getRfcCliente());
        //System.out.println(getRfcCliente());
        client.setCalleFisc(getCalleFisc());
        client.setExFisc(getExFisc());
        client.setIntFisc(getIntFisc());
        client.setColFisc(getColFisc());
        client.setDelFisc(getDelFisc());
        client.setCpFisc(getCpFisc());
        client.setEntFedFisc(getEntFedFisc());
        client.setLadaFisc(getLadaFisc());
        System.out.println(getLadaFisc());
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
        
        /*ConexionBD c = new ConexionBD();
        ClienteDAO cd = new ClienteDAO(c.getConexion());
        cd.editarCliente(client, listApod, listBanco);*/
        
        EditBO ed = new EditBO();
        ed.editarCliente(client, listBanco, listApod, bankDelete, apodDelete);
        
        
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
    
    public void eliminarBanco() {
        //System.out.println("Eliminar...");
        bankDelete.add(bancoSel.getId_banco());
        
    }

    /**
     * @return the clienteSel
     */
    public ClienteAltaBean getClienteSel() {
        return clienteSel;
    }

    /**
     * @param clienteSel the clienteSel to set
     */
    public void setClienteSel(ClienteAltaBean clienteSel) {
        this.clienteSel = clienteSel;
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

    /**
     * @return the id_cliente
     */
    public int getId_cliente() {
        return id_cliente;
    }

    /**
     * @param id_cliente the id_cliente to set
     */
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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
     * @return the diaNacAPod
     */
    public String getDiaNacAPod() {
        return diaNacAPod;
    }

    /**
     * @param diaNacAPod the diaNacAPod to set
     */
    public void setDiaNacAPod(String diaNacAPod) {
        this.diaNacAPod = diaNacAPod;
    }

    /**
     * @return the mesNacApod
     */
    public String getMesNacApod() {
        return mesNacApod;
    }

    /**
     * @param mesNacApod the mesNacApod to set
     */
    public void setMesNacApod(String mesNacApod) {
        this.mesNacApod = mesNacApod;
    }

    /**
     * @return the yearNacApod
     */
    public String getYearNacApod() {
        return yearNacApod;
    }

    /**
     * @param yearNacApod the yearNacApod to set
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
     * @return the apePatBenef
     */
    public String getApePatBenef() {
        return apePatBenef;
    }

    /**
     * @param apePatBenef the apePatBenef to set
     */
    public void setApePatBenef(String apePatBenef) {
        this.apePatBenef = apePatBenef;
    }

    /**
     * @return the apeMatBenef
     */
    public String getApeMatBenef() {
        return apeMatBenef;
    }

    /**
     * @param apeMatBenef the apeMatBenef to set
     */
    public void setApeMatBenef(String apeMatBenef) {
        this.apeMatBenef = apeMatBenef;
    }

    /**
     * @return the diaNacBenef
     */
    public String getDiaNacBenef() {
        return diaNacBenef;
    }

    /**
     * @param diaNacBenef the diaNacBenef to set
     */
    public void setDiaNacBenef(String diaNacBenef) {
        this.diaNacBenef = diaNacBenef;
    }

    /**
     * @return the mesNacBenef
     */
    public String getMesNacBenef() {
        return mesNacBenef;
    }

    /**
     * @param mesNacBenef the mesNacBenef to set
     */
    public void setMesNacBenef(String mesNacBenef) {
        this.mesNacBenef = mesNacBenef;
    }

    /**
     * @return the yearNacBenef
     */
    public String getYearNacBenef() {
        return yearNacBenef;
    }

    /**
     * @param yearNacBenef the yearNacBenef to set
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
     * @return the apodSel
     */
    public Apoderado getApodSel() {
        return apodSel;
    }

    /**
     * @param apodSel the apodSel to set
     */
    public void setApodSel(Apoderado apodSel) {
        this.apodSel = apodSel;
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
     * @return the bancoSel
     */
    public Banco getBancoSel() {
        return bancoSel;
    }

    /**
     * @param bancoSel the bancoSel to set
     */
    public void setBancoSel(Banco bancoSel) {
        this.bancoSel = bancoSel;
    }

    /**
     * @return the bankDelete
     */
    public List<Integer> getBankDelete() {
        return bankDelete;
    }

    /**
     * @param bankDelete the bankDelete to set
     */
    public void setBankDelete(List<Integer> bankDelete) {
        this.bankDelete = bankDelete;
    }

    /**
     * @return the apodDelete
     */
    public List<Integer> getApodDelete() {
        return apodDelete;
    }

    /**
     * @param apodDelete the apodDelete to set
     */
    public void setApodDelete(List<Integer> apodDelete) {
        this.apodDelete = apodDelete;
    }
    
}
