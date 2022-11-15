package com.app.web.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.app.web.entidad.Grados_curso;
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

public class Grados_cursoExporterPDF {

	private List<Grados_curso> listaGrados_cursos;

	public Grados_cursoExporterPDF(List<Grados_curso> listaGrados_cursos) {
		super();
		this.listaGrados_cursos = listaGrados_cursos;
	}

	private void escribirCabeceraDeLaTabla(PdfPTable tabla) {

		PdfPCell celda = new PdfPCell();
		celda.setBackgroundColor(Color.RED);
		celda.setPadding(3);

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);

		celda.setPhrase(new Phrase("ID", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Grado", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Curso", fuente));
		tabla.addCell(celda);

	}

	private void escribirDatosDeLaTabla(PdfPTable tabla) {
		for (Grados_curso Grados_curso : listaGrados_cursos) {
			tabla.addCell(String.valueOf(Grados_curso.getId()));
			tabla.addCell(Grados_curso.getGrado().getNombreg()+" Sección: "+Grados_curso.getGrado().getSeccion());
			tabla.addCell(Grados_curso.getCurso().getNombrec()+" Creditos: "+Grados_curso.getCurso().getCreditos());
		}

	}

	public void exportar(HttpServletResponse response) throws DocumentException, IOException {

		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		documento.open();
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.BLUE);
		fuente.setSize(18);

		Paragraph titulo = new Paragraph("Lista de asignación", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);

		PdfPTable tabla = new PdfPTable(3);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] { 1f, 3.5f, 3.5f});
		tabla.setWidthPercentage(110);

		escribirCabeceraDeLaTabla(tabla);
		escribirDatosDeLaTabla(tabla);
		documento.add(tabla);
		documento.close();

	}

}
