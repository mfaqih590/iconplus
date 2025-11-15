package com.example.demo.service;

import com.example.demo.dto.SummaryResponse;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    public byte[] generateBestSellerReport() {
        List<Object[]> rows = orderItemRepository.findTop3BestSeller();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Top 3 Buku Terlaris").setBold().setFontSize(18));

        float[] widths = {200F, 150F, 100F};
        Table table = new Table(widths);

        table.addHeaderCell("Title");
        table.addHeaderCell("Author");
        table.addHeaderCell("Total Sold");

        for (Object[] r : rows) {
            table.addCell(r[0].toString());
            table.addCell(r[1].toString());
            table.addCell(r[2].toString());
        }

        document.add(table);
        document.close();

        return baos.toByteArray();
    }

    public byte[] generateSummaryReport() {
        Long totalSold = orderItemRepository.getTotalQuantity();
        BigDecimal totalRevenue = orderRepository.getTotalRevenue();

        SummaryResponse dto = new SummaryResponse(
                totalRevenue == null ? BigDecimal.ZERO : totalRevenue,
                totalSold == null ? 0L : totalSold
        );
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Sales Report")
                .setFontSize(18)
                .setBold()
        );
        float[] widths = {200F, 200F};
        Table table = new Table(widths);

        table.addHeaderCell("Total Revenue (Omzet)");
        table.addHeaderCell("Total Buku Terjual");

        table.addCell(dto.getTotalRevenue().toString());
        table.addCell(dto.getTotalSold().toString());

        document.add(table);
        document.close();

        return baos.toByteArray();
    }

    public byte[] generatePricesBooks() {
        List<Object[]> rows = bookRepository.getPrices();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Harga Max, Min, Avg").setBold().setFontSize(18));

        float[] widths = {200F, 150F, 100F};
        Table table = new Table(widths);

        table.addHeaderCell("Max Price");
        table.addHeaderCell("Min Price");
        table.addHeaderCell("Avg Price");

        for (Object[] r : rows) {
            table.addCell(r[0].toString());
            table.addCell(r[1].toString());
            table.addCell(r[2].toString());
        }

        document.add(table);
        document.close();

        return baos.toByteArray();
    }
}
