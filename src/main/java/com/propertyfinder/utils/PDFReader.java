package com.propertyfinder.utils;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.BadSecurityHandlerException;
import org.apache.pdfbox.pdmodel.encryption.DecryptionMaterial;
import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
import org.apache.pdfbox.util.PDFTextStripper;


public class PDFReader{
    public static void main(String args[]) throws FileNotFoundException, BadSecurityHandlerException, CryptographyException {
        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        InputStream file = new FileInputStream("C:/my.pdf");
        try {
            PDFParser parser = new PDFParser( file);
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            DecryptionMaterial decry = new StandardDecryptionMaterial("password");
            pdDoc.openProtection(decry);
            List allPages = pdDoc.getDocumentCatalog().getAllPages();
            PDPage pdfpage =  (PDPage)allPages.get(1);
            BufferedImage bufferedImage = pdfpage.convertToImage();
            int height  = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            int sectionIndex =1;
            int startY = sectionIndex*150;
            while(startY<height){
            	bufferedImage.getSubimage(0, startY, width, height);
            }
//            pdfStripper.setStartPage(1);
//            pdfStripper.setEndPage(5);
//            String parsedText = pdfStripper.getText(pdDoc);
//            System.out.println(parsedText);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
}