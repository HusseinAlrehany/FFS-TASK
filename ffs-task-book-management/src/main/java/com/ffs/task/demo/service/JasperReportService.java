package com.ffs.task.demo.service;

import com.ffs.task.demo.dtos.BookDTO;
import com.ffs.task.demo.entities.Book;
import com.ffs.task.demo.repository.BookRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JasperReportService {

    @Autowired
    private BookRepository bookRepository;

    private String path = "D:\\JasperReports";

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        List<Book> books = bookRepository.findAll();

        //Load file
        File file = ResourceUtils.getFile("classpath:booksfilter.jrxml");
        //Compile the file
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        //getting the books and map them to the report
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(books);
        //Building the report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Hussein");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\books.html");
        }
        if(reportFormat.equalsIgnoreCase("pdf")){

            JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\books.pdf");


        }

        return "report generated in " + path;

    }
}
