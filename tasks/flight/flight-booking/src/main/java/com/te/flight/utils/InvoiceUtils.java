package com.te.flight.utils;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.te.flight.entity.Flight;
import com.te.flight.entity.User;

import lombok.extern.log4j.Log4j;

public class InvoiceUtils {

	private InvoiceUtils() {
	}

	public static void genrateInvoice(User user, Flight flight) throws FileNotFoundException {

		String path = "G:\\Workspace\\tasks\\Invoices\\" + user.getUsername() + "Invoice.pdf";
		PdfWriter pdfWriter = new PdfWriter(path);
		PdfDocument pdfDocument = new PdfDocument(pdfWriter);
		pdfDocument.setDefaultPageSize(PageSize.A4);
		Document document = new Document(pdfDocument);

		float[] headerColWidth = { 450f, 250f };
		Table headerTable = new Table(headerColWidth);
		headerTable.addCell(new Cell().add("Welcome to Air India \n Invoice For Booking a flight \n \n Passenger Name :"
				+ user.getUsername() + "\n Phone no. : " + user.getUserPhone()));
		headerTable.addCell(new Cell().add("Invoice Date : " + LocalDate.now() + "\n Time : "
				+ LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + "\n Invoice No. : AI1001CHH2314"));
		document.add(headerTable);

		float[] msgColWidth = { 700f };
		Table msgTable = new Table(msgColWidth);
		msgTable.addCell(new Cell()
				.add("\nDear " + user.getUsername() + ", \n your Booking is confirmed flight details is as follows.\n")
				.setBorder(Border.NO_BORDER));
		document.add(msgTable);

		float[] particularColWidth = { 450f, 250f };
		Table particularTable = new Table(particularColWidth);
		particularTable.addCell(new Cell().add("Journey Particulars").setBold().setTextAlignment(TextAlignment.CENTER));
		particularTable.addCell(new Cell().add("Price details").setBold().setTextAlignment(TextAlignment.CENTER));
		particularTable.addCell(new Cell().add(GeneralUtils.journeyDetailsGenerator(flight)));
		particularTable
				.addCell(new Cell()
						.add("Base fare : " + BigDecimal.valueOf(flight.getPrice().floatValue() * 100 / 118)
								.round(new MathContext(2)) + "\nSGST : + 9%\nCGST : + 9%")
						.setTextAlignment(TextAlignment.RIGHT));
		particularTable.addCell(new Cell().add("Total").setBold().setTextAlignment(TextAlignment.RIGHT));
		particularTable
				.addCell(new Cell().add(flight.getPrice().toString()).setBold().setTextAlignment(TextAlignment.RIGHT));
		document.add(particularTable);

		float[] footerColWidth = { 750f };
		Table footerTable = new Table(footerColWidth);
		footerTable.addCell(
				new Cell().add("\nThank You For Travelling With Air India\n\n").add("Wish you a very Happy Journey\n")
						.setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
		document.add(footerTable);

		document.close();
		System.out.println("Invoice Created");
	}

}
