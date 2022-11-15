package com.app.web.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.app.web.entidad.Estudiante;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class EstudianteExporterPDF {

	private List<Estudiante> listaEstudiantes;

	public EstudianteExporterPDF(List<Estudiante> listaEstudiantes) {
		super();
		this.listaEstudiantes = listaEstudiantes;
	}

	private void escribirCabeceraDeLaTabla(PdfPTable tabla) {

		PdfPCell celda = new PdfPCell();
		celda.setBackgroundColor(Color.RED);
		celda.setPadding(5);

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);

		celda.setPhrase(new Phrase("ID", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Nombre", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Apellido", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Email", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Grado", fuente));
		tabla.addCell(celda);

	}

	private void escribirDatosDeLaTabla(PdfPTable tabla) {
		for (Estudiante estudiante : listaEstudiantes) {
			tabla.addCell(String.valueOf(estudiante.getId()));
			tabla.addCell(estudiante.getNombre());
			tabla.addCell(estudiante.getApellido());
			tabla.addCell(estudiante.getEmail());
			tabla.addCell(estudiante.getGrado().getNombreg() + " Seccion: " + estudiante.getGrado().getSeccion());
		}

	}

	public void exportar(HttpServletResponse response) throws DocumentException, IOException {

		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		documento.open();
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.BLUE);
		fuente.setSize(18);

		Paragraph titulo = new Paragraph("Lista de estudiantes", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);

		PdfPTable tabla = new PdfPTable(5);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] { 1f, 2.3f, 2.3f, 3.3f, 2.3f });
		tabla.setWidthPercentage(110);

		escribirCabeceraDeLaTabla(tabla);
		escribirDatosDeLaTabla(tabla);
		documento.add(tabla);
		documento.close();

	}

}
