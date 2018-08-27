/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.bean.Concepto;
import com.bean.InsumoBean;
import com.bean.PartidaBean;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;


public class CargarProformaBO {
    
    public int cargarArchivo(UploadedFile upload, int id_pres) {
        
        int resultado = 0;
        
        //System.out.println("Cargando....");
        PartidaBean pb;
        List<PartidaBean> list = new ArrayList();
        
        Concepto concepto;
        List<Concepto> listConc = new ArrayList();
        
        InsumoBean insumo;
        List<InsumoBean> listIns = new ArrayList();
        
        try {
            
            InputStream input = upload.getInputstream();
            //FileInputStream archivo = (FileInputStream)file.getInputStream();
            
            
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            
            while(rowIterator.hasNext()) {
                
                row = rowIterator.next();
                
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell celda;
                
                while(cellIterator.hasNext()) {
                    celda = cellIterator.next();
                    
                    
                    switch(celda.getCellType()) {
                        
                                                
                        case Cell.CELL_TYPE_STRING:
                            pb = new PartidaBean();
                            pb.setDescripcion(celda.getStringCellValue());
                            celda = cellIterator.next();
                            pb.setNivel(celda.getStringCellValue());
                            celda = cellIterator.next();
                            pb.setEsSubNivel(celda.getStringCellValue());
                            list.add(pb);
                        break;
                        
                        
                        
                    }
                    
                }
                
            }
            
            list.remove(0);
            
            /*for(PartidaBean aux:list) {
                System.out.println(aux.getNivel()+"\t"+aux.getEsSubNivel()+"\t"+aux.getDescripcion());
            }*/
            
            sheet = workbook.getSheetAt(1);
            rowIterator = sheet.iterator();
            
            while(rowIterator.hasNext()) {
                concepto = new Concepto();
                row = rowIterator.next();
                
                if(row.getRowNum() > 2) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell celda;

                    while(cellIterator.hasNext()) {
                        celda = cellIterator.next();
                        concepto.setNumConcepto(String.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        concepto.setCodConcepto(celda.getStringCellValue());
                        celda = cellIterator.next();
                        concepto.setDescripcion(celda.getStringCellValue());
                        celda = cellIterator.next();
                        concepto.setUnidad(celda.getStringCellValue());
                        celda = cellIterator.next();
                        concepto.setCantidad(BigDecimal.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        concepto.setpUnitario(BigDecimal.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        concepto.setImporte(BigDecimal.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        concepto.setPartida(celda.getStringCellValue());
                        listConc.add(concepto);

                    }
                }
            }
            
            //System.out.println(listConc.size());
            
            sheet = workbook.getSheetAt(2);
            rowIterator = sheet.iterator();
            
            while(rowIterator.hasNext()) {
                insumo =  new InsumoBean();
                row = rowIterator.next();
                
                if(row.getRowNum() > 1) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell celda;
                    
                    while(cellIterator.hasNext()) {
                        celda = cellIterator.next();
                        insumo.setCuenta(celda.getStringCellValue());
                        celda = cellIterator.next();
                        insumo.setNoConcepto(String.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        insumo.setCodInsumo(celda.getStringCellValue());
                        celda = cellIterator.next();
                        insumo.setDescripIns(celda.getStringCellValue());
                        celda = cellIterator.next();
                        insumo.setUnidadIns(celda.getStringCellValue());
                        celda = cellIterator.next();
                        insumo.setCantIns(BigDecimal.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        insumo.setCostoIns(BigDecimal.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        insumo.setImporteIns(BigDecimal.valueOf(celda.getNumericCellValue()));
                        listIns.add(insumo);
                    }
                    
                }
                
            }
            
            
            
            sheet = workbook.getSheetAt(3);
            rowIterator = sheet.iterator();
            
            while(rowIterator.hasNext()) {
                insumo =  new InsumoBean();
                row = rowIterator.next();
                
                if(row.getRowNum() > 1) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell celda;
                    
                    while(cellIterator.hasNext()) {
                        celda = cellIterator.next();
                        insumo.setCuenta(celda.getStringCellValue());
                        celda = cellIterator.next();
                        insumo.setNoConcepto(String.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        insumo.setCodInsumo(celda.getStringCellValue());
                        celda = cellIterator.next();
                        insumo.setDescripIns(celda.getStringCellValue());
                        celda = cellIterator.next();
                        insumo.setUnidadIns(celda.getStringCellValue());
                        celda = cellIterator.next();
                        insumo.setCantIns(BigDecimal.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        insumo.setCostoIns(BigDecimal.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        insumo.setImporteIns(BigDecimal.valueOf(celda.getNumericCellValue()));
                        listIns.add(insumo);
                    }
                    
                }
                
            }
            
            ConexionBD c = new ConexionBD();
            Connection con =  c.getConexion();
            PresupuestoDAO pd = new PresupuestoDAO(con);
            resultado = pd.agregarPresupuestoAuto(list, listConc, listIns, id_pres);
            
            workbook.close();
            
        }catch(IOException ie) {
            System.out.println(ie.getMessage());
        }
        
        return resultado;
    }
    
    public int cargarArchivoVta(UploadedFile upload, int id_pres) {
        
        int resultado = 0;
        
        //System.out.println("Cargando....");
        PartidaBean pb;
        List<PartidaBean> list = new ArrayList();
        
        Concepto concepto;
        List<Concepto> listConc = new ArrayList();
        
        try {
            
            InputStream input = upload.getInputstream();
            //FileInputStream archivo = (FileInputStream)file.getInputStream();
            
            
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            
            while(rowIterator.hasNext()) {
                
                row = rowIterator.next();
                
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell celda;
                
                while(cellIterator.hasNext()) {
                    celda = cellIterator.next();
                    
                    
                    switch(celda.getCellType()) {
                        
                                                
                        case Cell.CELL_TYPE_STRING:
                            pb = new PartidaBean();
                            pb.setDescripcion(celda.getStringCellValue());
                            celda = cellIterator.next();
                            pb.setNivel(celda.getStringCellValue());
                            celda = cellIterator.next();
                            pb.setEsSubNivel(celda.getStringCellValue());
                            list.add(pb);
                        break;
                        
                        
                        
                    }
                    
                }
                
            }
            
            list.remove(0);
            System.out.println(list.size());
            /*for(PartidaBean aux:list) {
                System.out.println(aux.getNivel()+"\t"+aux.getEsSubNivel()+"\t"+aux.getDescripcion());
            }*/
            
            sheet = workbook.getSheetAt(1);
            rowIterator = sheet.iterator();
            
            while(rowIterator.hasNext()) {
                concepto = new Concepto();
                row = rowIterator.next();
                
                if(row.getRowNum() > 2) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell celda;

                    while(cellIterator.hasNext()) {
                        celda = cellIterator.next();
                        concepto.setNumConcepto(String.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        concepto.setCodConcepto(celda.getStringCellValue());
                        celda = cellIterator.next();
                        concepto.setDescripcion(celda.getStringCellValue());
                        celda = cellIterator.next();
                        concepto.setUnidad(celda.getStringCellValue());
                        celda = cellIterator.next();
                        concepto.setCantidad(BigDecimal.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        concepto.setpUnitario(BigDecimal.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        concepto.setImporte(BigDecimal.valueOf(celda.getNumericCellValue()));
                        celda = cellIterator.next();
                        concepto.setPartida(celda.getStringCellValue());
                        listConc.add(concepto);

                    }
                }
            }
            
            System.out.println(listConc.size());
            
            ConexionBD c = new ConexionBD();
            Connection con =  c.getConexion();
            PresupuestoDAO pd = new PresupuestoDAO(con);
            resultado = pd.agregarPresupuestoAutoVta(list, listConc, id_pres);
            
            workbook.close();
            
        }catch(IOException ie) {
            System.out.println(ie.getMessage());
        }
        
        return resultado;
    }
    
}
