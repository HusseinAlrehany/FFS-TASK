package com.ffs.task.demo.service;
import com.ffs.task.demo.dtos.BookDTO;
import com.ffs.task.demo.dtos.ReportSuccessDTO;
import com.ffs.task.demo.entities.Type;
import com.ffs.task.demo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
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

    private String path  = System.getProperty("user.dir");

    public ReportSuccessDTO exportReport(String format, Type type, Long price, int authorId) throws JRException, FileNotFoundException{

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


    }



