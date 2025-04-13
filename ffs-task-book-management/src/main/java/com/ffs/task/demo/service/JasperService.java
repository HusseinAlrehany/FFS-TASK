package com.ffs.task.demo.service;
import com.ffs.task.demo.dtos.BookDTO;
import com.ffs.task.demo.dtos.ReportSuccessDTO;
import com.ffs.task.demo.entities.Type;
import com.ffs.task.demo.exception.NotFoundException;
import com.ffs.task.demo.exception.ReportGenerationException;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JasperService {


    private final BookService bookService;


    //getting the current working directory of the application(dynamic path)
    //to avoid specifying a static path
    private String path  = System.getProperty("user.dir");

    public ReportSuccessDTO exportReport(String format, String type, Long price, int authorId) {
        try {

            List<BookDTO> bookDTOList = bookService.filterBookByNameAndPrice(type,price, authorId);
            //load the file
            File file = ResourceUtils.getFile("classpath:bookreport.jrxml");
            //compile the file
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            //getting the booksDTO and map them to the report
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bookDTOList);
            //Building the report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Hussein");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            if(format.equalsIgnoreCase("html")){

                path = path + "\\books_"+new Date().getTime() +".html";
                JasperExportManager.exportReportToHtmlFile(jasperPrint, path);
            } else{
                path = path + "\\books_"+new Date().getTime() +".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, path);
            }

            ReportSuccessDTO reportSuccess = new ReportSuccessDTO();
            reportSuccess.setHttpStatus(HttpStatus.CREATED);
            reportSuccess.setReportPath(path);

            return reportSuccess;


        }
        catch (FileNotFoundException ex){
            throw new NotFoundException("Report Template Not Found");
        }catch(JRException ex){
            throw new ReportGenerationException("Error Generating the report");
        }

        }

    //RETURN PDF FILE AS BYTEARRAY INSTEAD OF IT'S PATH TO AVOID FRONT END NO ACCESS PROBLEM
   public ResponseEntity<byte[]> exportReportAsPDF(String format, String type, Long price, int authorId){
        try{

            List<BookDTO> bookDTOList = bookService.filterBookByNameAndPrice(type,price, authorId);
            //load the file
            File file = ResourceUtils.getFile("classpath:bookreport.jrxml");
            //compile the file
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            //getting the booksDTO and map them to the report
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bookDTOList);
            //Building the report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Hussein");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

                //HANDLE PDF FORMAT
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

                //set the http header
               HttpHeaders headers = new HttpHeaders();
               headers.setContentType(MediaType.APPLICATION_PDF);

            //return the PDF as response body
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"books_" + new Date().getTime() + ".pdf\"")
                    .body(outputStream.toByteArray());

        }
        catch(FileNotFoundException ex){
            throw new NotFoundException("Report Template Not Found");
        }catch(JRException ex){
            throw new ReportGenerationException("Error Generating the report");
        }
   }


}



