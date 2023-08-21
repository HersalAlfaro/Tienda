package com.tienda.service.impl;


import com.tienda.service.ReportesService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReportesServiceImpl implements ReportesService {

   @Autowired
    DataSource dataSource;

    @Override
    public ResponseEntity<Resource> generarReporte(
            String reporte,
            Map<String, Object> parametros,
            String tipo) throws IOException {

        try {
            String estilo; // Si el reporte se debe mostrar o si se debe descargar.

            if ("vPdf".equals(tipo)) {
                estilo = "inline; ";
            } else {
                estilo = "attachment; ";
            }

            // La ruta del reporte (dentro del default packege)
            String reportePath = "reportes";

            // La salida que da el reporte ya generado
            ByteArrayOutputStream salida = new ByteArrayOutputStream();

            // Se define el lugar y acceso al archivo .jasper
            ClassPathResource fuente = new ClassPathResource(
                    reportePath
                    + File.separator
                    + reporte
                    + ".jasper");

            // Un objeto que leea el reporte.
            InputStream elReporte = fuente.getInputStream();

            // Se crea el reporte como tal
            var reporteJasper = JasperFillManager.fillReport(
                    elReporte,
                    parametros,
                    dataSource.getConnection());

            // Tipos de salida del reporte
            MediaType mediaType = null;
            String archivoSalida = "";
            byte[] data;

            if (tipo != null) {
                switch (tipo) {
                    case "Pdf", "vPdf" -> {
                        JasperExportManager.exportReportToPdfStream(reporteJasper, salida);
                        mediaType = MediaType.APPLICATION_PDF;
                        archivoSalida = reporte + ".pdf";
                    }
                    case "Xls" -> {

                    }
                    case "Csv" -> {

                    }
                }
            }
            
            data = salida.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Disposition", estilo + "filename=\"" + archivoSalida + "\"");
            
            
            return  ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(data.length)
                    .contentType(mediaType)
                    .body(new InputStreamResource(new ByteArrayInputStream(data)));
   

        } catch (SQLException | JRException ex) {
            ex.printStackTrace();
            return null;
        }

    }

}
