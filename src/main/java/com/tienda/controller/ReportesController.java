package com.tienda.controller;

import com.tienda.service.ReportesService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reportes")
public class ReportesController {

    @Autowired
    private ReportesService reporteService;

    @GetMapping("/principal")
    public String listado(Model model) {
        return "/reportes/principal";
    }

    @GetMapping("/usuarios")
    public ResponseEntity<Resource> ReporteUsuarios(@RequestParam String tipo) throws IOException {
        var reporte = "usuarios";
        return reporteService.generarReporte(reporte, null, tipo);
    }

    @GetMapping("/productos")
    public ResponseEntity<Resource> ReporteProductos(@RequestParam String tipo) throws IOException {
        var reporte = "productos";
        return reporteService.generarReporte(reporte, null, tipo);
    }

    @GetMapping("/categorias")
    public ResponseEntity<Resource> ReporteCategorias(@RequestParam String tipo) throws IOException {
        var reporte = "categorias";
        return reporteService.generarReporte(reporte, null, tipo);
    }

}
