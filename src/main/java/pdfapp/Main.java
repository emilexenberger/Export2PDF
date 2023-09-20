package pdfapp;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Document document = new Document();

        try {
            // Use a font that supports Slovak special characters (UTF-8 encoding)
            BaseFont baseFont = BaseFont.createFont("AbhayaLibre-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 12);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));
            document.open();
            Paragraph paragraph = new Paragraph("A Hello World PDF document.ľščťžýáíé", font);
            document.add(paragraph);
            document.close();
            writer.close();
        } catch (DocumentException e) {
            System.out.println("Nastal problém s vytváraním dokumentu");
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("Problém so súborom!");
        } catch (IOException e) {
            System.out.println("Neznama I/O chyba");
        }
    }
}
