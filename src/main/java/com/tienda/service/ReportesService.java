package com.tienda.service;

import java.io.IOException;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface ReportesService {
    
    // Para emitir el reporte 
    public ResponseEntity<Resource> generarReporte(
            String reporte, // Nombre del reporte .jasper
            Map <String, Object> parametros,   // parametros del reporte
            String tipo //tipo de archivo (pdf,excel, csv)       
    ) throws IOException;

    
}
