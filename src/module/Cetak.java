/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author users
 */
public class Cetak {
    public void generatePDF(JTable table, String laporan) throws IOException, DocumentException {
        
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            try {
                
                // OPEN FOLDER
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile.getAbsolutePath() + ".pdf"));
                document.open();
                
                // MEMBUAT JUDUL PARAGRAF
                String tanggal = new SimpleDateFormat("EEEE, dd MMMM Y KK:mm:ss").format(new java.util.Date());
                String textJudul = "HASIL LAPORAN " + laporan.toUpperCase() + "\n" + tanggal + "\n\n";
                Font title = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
                Paragraph paragraph = new Paragraph(textJudul, title);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);

                // MEMBUAT TABEL
                PdfPTable pdfTable = new PdfPTable(table.getColumnCount());

                for (int i = 0; i < table.getColumnCount(); i++) {
                    PdfPCell cell = new PdfPCell(new Phrase(table.getColumnName(i)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cell);
                }

                for (int i = 0; i < table.getRowCount(); i++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        pdfTable.addCell(table.getValueAt(i, j).toString());
                    }
                }

                document.add(pdfTable);
                JOptionPane.showMessageDialog(null, "Create PDF done.");
                document.close();

            } catch (DocumentException | FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    
}
