
$(function (){
    $("#home").click(function (){
        location.reload(); 
    });
    
    $("#alta").click(function (){
        
        $("#contain").load("faces/views/altaProyecto.xhtml");
    });
    
    $("#list").click(function (){
        $("#contain").load("faces/views/editarProyecto.xhtml");
    });
    
    $("#pCrea").click(function (){
        $("#contain").load("faces/views/altaPrespto.xhtml"); 
    });
    
    $("#pVent").click(function (){
        $("#contain").load("faces/views/cargaPresVta.xhtml"); 
    });
    
    $("#pPro").click(function (){
        $("#contain").load("faces/views/cargaPres.xhtml"); 
    });
    
    $("#agrConc").click(function (){
        $("#contain").load("faces/views/creaConcepto.xhtml");
    });
    
    $("#vPresV").click(function (){
        $("#contain").load("faces/views/vista2.xhtml");
    });
    
    $("#vPresP").click(function (){
        $("#contain").load("faces/views/vista2.xhtml");
    });
    
    $("#expl").click(function (){
        $("#contain").load("faces/views/expInsumos.xhtml");
    });
    
    $("#preContrt").click(function (){
        $("#contain").load("faces/views/precontrato.xhtml");
    });
    
    $("#edPreCntrt").click(function (){
        $("#contain").load("faces/views/editarContto.xhtml");
    });
    
    $("#cntrto").click(function (){
        $("#contain").load("faces/views/contrato.xhtml");
    });
    
    $("#editCtto").click(function (){
        $("#contain").load("faces/views/editCtto.xhtml");
    });
    
    $("#avan").click(function (){
        $("#contain").load("faces/views/avance.xhtml");
    });
    
    $("#editAvan").click(function (){
        $("#contain").load("faces/views/editAvance.xhtml");
    });
    
    $("#estimac").click(function (){
        $("#contain").load("faces/views/estimacion.xhtml");
    });
    
    $("#revEstima").click(function (){
        $("#contain").load("faces/views/revEstim.xhtml");
    });
    
    $("#requis").click(function (){
        $("#contain").load("faces/views/solrequisicion.xhtml");
    });
    
    $("#autRequis").click(function (){
        $("#contain").load("faces/views/listadoRequisicion.xhtml");
    });
    
    $("#orCompr").click(function (){
        $("#contain").load("faces/views/solordencompra.xhtml");
    });
    
    $("#autOrComp").click(function (){
        $("#contain").load("faces/views/listadoOrdenCompra.xhtml");
    });
    
    $("#recepc").click(function (){
        $("#contain").load("faces/views/recepcion.xhtml");
    });
    
    $("#lRecepc").click(function (){
        $("#contain").load("faces/views/listarRecepciones.xhtml");
    });
    
    $("#devoluc").click(function (){
        $("#contain").load("faces/views/devolucion.xhtml");
    });
    
    $("#listDevoluc").click(function (){
        $("#contain").load("faces/views/listarDevoluciones.xhtml");
    });
    
    $("#vCons").click(function (){
        $("#contain").load("faces/views/valeconsumo.xhtml");
    });
    
    $("#lVCons").click(function (){
        $("#contain").load("faces/views/listarValeConsumo.xhtml");
    });
    
    $("#vDevol").click(function (){
        $("#contain").load("faces/views/valedevolucion.xhtml");
    });
    
    $("#inventa").click(function (){
        $("#contain").load("faces/views/inventario.xhtml");
    });
    
    $("#fReg").click(function (){
        $("#contain").load("faces/views/facturas.xhtml");
    });
    
    $("#fList").click(function (){
        $("#contain").load("faces/views/listaFacturas.xhtml");
    });
    
    $("#remesa").click(function (){
         $("#contain").load("faces/views/cuentasxPag.xhtml");
    });
    
    $("#repRem").click(function (){
        $("#contain").load("faces/views/reporteCXP.xhtml");
    });
       
    $("#cteReg").click(function (){
        $("#contain").load("faces/views/altaCte.xhtml");
    });
    
    $("#listCte").click(function (){
        $("#contain").load("faces/views/clientes.xhtml");
    });
    
    $("#estimaCte").click(function (){
        $("#contain").load("faces/views/estimaCliente.xhtml");
    });
    
    $("#cteFacts").click(function (){
        $("#contain").load("faces/views/facturaCte.xhtml");
    });
    
    $("#cteMovs").click(function (){
        $("#contain").load("faces/views/movimientos.xhtml");
    });
    
    $("#famil").click(function (){
        $("#contain").load("faces/views/catFamilia.xhtml");
    });
    
    $("#catMat").click(function (){
        $("#contain").load("faces/views/agregaInsumo.xhtml");
    });
    
    $("#editProv").click(function (){
        $("#contain").load("faces/views/listadoProveedores.xhtml");
    });
    
    $("#agregProv").click(function (){
        $("#contain").load("faces/views/nuevoProveedor.xhtml");
    });
    
    $("#editCncp").click(function (){
        $("#contain").load("faces/views/editaConcepto.xhtml");
    });
    
    $("#catProv").click(function (){
        $("#contain").load("faces/views/catProveedor.xhtml");
    });
    
});

