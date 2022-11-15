package com.app.web.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.app.web.entidad.Estudiante;

public class EstudianteExporterExcel {

	private XSSFWorkbook libro;
	private XSSFSheet hoja;

	private List<Estudiante> listaEstudiantes;

	public EstudianteExporterExcel(List<Estudiante> listaEstudiantes) {
		this.listaEstudiantes = listaEstudiantes;
		libro= new XSSFWorkbook();
		hoja = libro.createSheet("Estudiantes");
		
	}
	
	private void escribirCabeceraDeTabla() {
		Row fila = hoja.createRow(0);
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setBold(true);
		fuente.setFontHeight(16);
		estilo.setFont(fuente);
		
		Cell celda = fila.createCell(0);
		celda.setCellValue("ID");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(1);
		celda.setCellValue("Nombre");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(2);
		celda.setCellValue("Apellido");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(3);
		celda.setCellValue("Email");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(4);
		celda.setCellValue("Grado");
		celda.setCellStyle(estilo);
		
	}
	
	private void escribirDatosDeLaTabla() {
		int numeroFilas=1;
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for (Estudiante estudiante: listaEstudiantes) {
			Row fila = hoja.createRow(numeroFilas ++);
			
			Cell celda =fila.createCell(0);
			celda.setCellValue(estudiante.getId());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda =fila.createCell(1);
			celda.setCellValue(estudiante.getNombre());
			hoja.autoSizeColumn(1);
			celda.setCellStyle(estilo);
			
			celda =fila.createCell(2);
			celda.setCellValue(estudiante.getApellido());
			hoja.autoSizeColumn(2);
			celda.setCellStyle(estilo);
			
			celda =fila.createCell(3);
			celda.setCellValue(estudiante.getEmail());
			hoja.autoSizeColumn(3);
			celda.setCellStyle(estilo);
			
			celda =fila.createCell(4);
			celda.setCellValue(estudiante.getGrado().getNombreg()+" Seccion: "+estudiante.getGrado().getSeccion());
			hoja.autoSizeColumn(4);
			celda.setCellStyle(estilo);
			
			
		}
			
		
	}
	
	public void exportar(HttpServletResponse response) throws IOException {
		
		escribirCabeceraDeTabla();
		escribirDatosDeLaTabla();
		
		ServletOutputStream outPutStream = response.getOutputStream();
		libro.write(outPutStream);
		libro.close();
		outPutStream.close();
		
	}

}
