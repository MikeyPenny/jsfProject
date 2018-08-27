
package com.bo;

import com.bean.Comprobante;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.primefaces.model.UploadedFile;


public class ReadXML {
    
    public Comprobante leerXML(UploadedFile file) {
        
        Comprobante cfdi = new Comprobante();
        
        try {
            JAXBContext context = JAXBContext.newInstance(Comprobante.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            cfdi = (Comprobante)unmarshaller.unmarshal(file.getInputstream());
            
            
            //System.out.println(cfdi.getComplemento().getTimbre().getUuid());
            /*for(ConceptoCFDI c:cfdi.getConceptos()) {
                System.out.println(c.getImporte());
            }*/
            
        }catch(JAXBException|IOException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
        
        return cfdi;
            
    }
    
}
