package kniznica;

/*
Vytvorte knižnicu (Kniznica), kde bude môcť používateľ zadať knihy (názov, autor, rok vydania) cez konzolu kým nezadá slovo koniec. Tie na konci vypíšeme. Opakovanie na ArrayList.
 */

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class KniznicaService {
    ArrayList<Kniha> kniznica;

    public KniznicaService() {
        kniznica = new ArrayList<>();
    }

    public void fillKniznica() {
        Kniha kniha1 = new Kniha("Java", "Jan Zitniak", 1992, 2.05);
        Kniha kniha2 = new Kniha("Ja, robot", "Isaac Asimov", 1998, 31.14);
        Kniha kniha3 = new Kniha("Python", "John Fasey", 2003, 5.4);

        kniznica.add(kniha1);
        kniznica.add(kniha2);
        kniznica.add(kniha3);
    }

    public void startMenu() {
        Scanner sc = new Scanner(System.in);
        String input = getMenuInput(sc);
        while (!input.equalsIgnoreCase("koniec")) {
            switch (input){
                case "1" -> addBook();
                case "2" -> showAllBooks();
                case "3" -> showBookIndex();
                case "4" -> removeBook();
                case "5" -> showCountOfBooks();
                case "6" -> {
                    ArrayList<Kniha> fountBooks = findByName();
                    System.out.println("Najdene knihy su nasledovne: \n" + fountBooks);
                }
                case "7" -> kniznica = loadFile();
                case "8" -> saveFile();
                case "9" -> {
                    editBookByIndex();
                    System.out.println("Kniha bola zmenena");
                }
                case "10" -> {
                    removeAllBooks();
                    System.out.println("Vsetky knihy boli odstranene");
                }
                case "11" -> {
                    printToPDF();
                    System.out.println("kniznica bola exportovana ako kniznica.pdf");
                }
                case "12" -> {
                    printToDocx();
                    System.out.println("kniznica bola exportovana ako kniznica.docx");
                }
                default -> System.out.println("Nezadal si spravny vstup");
            }
            System.out.println();
            input = getMenuInput(sc);
        }
    }

    public String getMenuInput(Scanner sc){
        System.out.println("""
                [1]  Zadaj novú knihu
                [2]  Zobraz všetky knihy
                [3]  Zobraz konkrétnu knihu (podľa indexu)
                [4]  Vymaž konkrétnu knihu (podľa indexu)
                [5]  Zobraz počet všetkých kníh
                [6]  Vyhľadaj knihu podľa názvu
                [7]  Načítaj zoznam kníh zo súboru (zadaj názov súboru)
                [8]  Ulož zoznam kníh do súboru (zadaj názov súboru)
                [9]  Uprav knihu
                [10] Vymaž všetky knihy
                [11] Export do PDF
                [12] Export do DOCX
                [koniec] Ukonci program""");
        System.out.print("Zadaj vstup: ");
        return sc.nextLine();
    }

    public void printToDocx() {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        String text;
        for (int i = 0; i < kniznica.size(); i++) {
            text = kniznica.get(i).toStringWithIndex(i + 1);
            run.setText(text);
            run.addBreak(); // Add line break after each book
        }

        try (FileOutputStream out = new FileOutputStream("kniznica.docx")) {
            document.write(out);
            document.close();
            System.out.println("DOCX file created successfully!");
        } catch (IOException e) {
            System.out.println("Error creating DOCX file: " + e.getMessage());
        }
    }

    public void printToPDF() {
        Document document = new Document();

        try {
            // Use a font that supports Slovak special characters (UTF-8 encoding)
            BaseFont baseFont = BaseFont.createFont("AbhayaLibre-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 12);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("kniznica.pdf"));
            document.open();

            StringBuilder text = new StringBuilder();
            for (int i = 0; i < kniznica.size(); i++) {
                text.append(kniznica.get(i).toStringWithIndex(i + 1)).append("\n");
            }

            Paragraph paragraph = new Paragraph(text.toString(), font);
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

    public void editBookByIndex() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Zadaj index knihy, ktoru chces upravit: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        System.out.print("Zadaj nazov: ");
        kniznica.get(index).setNazov(sc.nextLine());

        System.out.print("Zadaj autora: ");
        kniznica.get(index).setAutor(sc.nextLine());

        System.out.print("Zadaj rok vydania: ");
        kniznica.get(index).setRokVydania(sc.nextInt());

        System.out.print("Zadaj cenu: ");
        kniznica.get(index).setCena(sc.nextDouble());
    }

    public void removeAllBooks() {
        kniznica = new ArrayList<>();
    }

    public ArrayList<Kniha> findByName() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Kniha> fountBooks = new ArrayList<>();
        System.out.print("Zadaj nazov alebo cast nazvu hladanej knihy: ");
        String hladanyNazov = sc.nextLine();

        for (Kniha kniha:kniznica) {
            if (kniha.getNazov().contains(hladanyNazov)) fountBooks.add(kniha);
        }
        return fountBooks;
    }

    public void addBook(){
        Scanner sc = new Scanner(System.in);
        Kniha kniha = new Kniha();
        System.out.print("Nazov: ");
        kniha.setNazov(sc.nextLine());

        System.out.print("Autor: ");
        kniha.setAutor(sc.nextLine());

        System.out.print("Rok vydania: ");
        kniha.setRokVydania(sc.nextInt());
        sc.nextLine();

        System.out.print("Cena: ");
        kniha.setCena(sc.nextDouble());
        sc.nextLine();

        kniznica.add(kniha);
    }

    public void showAllBooks() {
        for (Kniha kniha:kniznica) {
            System.out.println(kniha);
        }
    }

    public void showBookIndex() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Zadaj index knihy, ktoru chces zobrazit: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();
        System.out.println(kniznica.get(index));
    }

    public void removeBook() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Zadaj index knihy na vymazanie: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();
        kniznica.remove(index);
    }

    public void showCountOfBooks() {
        System.out.println("Celkovy pocet knih je " + kniznica.size());
    }

    public void saveFile() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Zadaj názov súboru: ");
        String nazovSuboru = sc.nextLine();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(nazovSuboru); // vytvorime subor s nazvom kniha.ser
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream); // vytvorime Object stream pre ukladanie objektov
            objectOutputStream.writeObject(kniznica); // zapiseme objekt
            objectOutputStream.flush(); // realne uskutocnime operaciu zapisu
            objectOutputStream.close(); // zatvorime object output stream
            fileOutputStream.close(); // zatvorime file output stream, cize subor*/
            System.out.print("Súbor je uložený!");
        } catch (IOException e) {
            System.out.println("Nepodaril sa vytvoriť súbor, resp. ho uložiť!");
            e.printStackTrace();
        }
    }

    public ArrayList<Kniha> loadFile() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Zadaj názov súboru: ");
        String nazovSuboru = sc.nextLine();
        try {
            FileInputStream fileInputStream = new FileInputStream(nazovSuboru);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            kniznica = (ArrayList) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            System.out.println("Súbor je načítaný!");
        } catch (IOException e) {
            System.out.println("Nepodaril sa otvoriť súbor!");
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Súbor sa podaril načítať, ale nie je kompatibilný z ArrayList!");
            //e.printStackTrace();
        }
        return kniznica;
    }
}
