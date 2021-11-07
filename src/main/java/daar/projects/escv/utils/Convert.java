package daar.projects.escv.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class Convert
{
    public static String convertPdfToTxt(File file) {

        String text = "";
        try (PDDocument document = PDDocument.load(file))
        {
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();
                text = tStripper.getText(document);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static String convertWordToTxt(File file) {
        XWPFDocument docx = null;
        try {
            docx = new XWPFDocument(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        XWPFWordExtractor wex = new XWPFWordExtractor(docx);
        return wex.getText();
    }
}
