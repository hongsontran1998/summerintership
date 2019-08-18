package com.demo.controller;

import com.demo.repository.SpringBootHibernateDAO;
import com.demo.security.UserDetailsUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Controller
public class IndexController {

    @GetMapping("index")
    @ResponseBody
    public int add() {
        return 3 + 4;
    }

    @GetMapping("demo")
    public String demo(ModelMap modelMap) {
        int id = 1;
        String username = "";
        String password = "";
        String fullName = "";
        String email = "";
        String phoneNumber = "";
        String address = "";

//        User u = new User(id, username, password, fullName, email, phoneNumber, address);
//        System.out.println("i am tran huu hong son");
//        System.out.println(u);
//        System.out.println("u = " + u);
        System.out.println("IndexController.demo");
        try {
            UserDetailsUtil.getUserDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }

        modelMap.addAttribute("name", "tran huu hong son");
        return "index";

    }
    @GetMapping("demo2")
    public ModelAndView demo2() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("name", "tranhuuhongson");
        return modelAndView;
    }

    @Autowired
    SpringBootHibernateDAO testDAO;
    @GetMapping("demo3")
    public String demo3() {
        testDAO.testQueryOOP();
        return "view";
    }

    @RequestMapping(value = "/download2", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> download2(HttpServletRequest request) throws IOException {
        HttpHeaders responseHeader = new HttpHeaders();
        try {
            File file = ResourceUtils.getFile("classpath:static/abc.png");
            byte[] data = FileUtils.readFileToByteArray(file);
            // Set mimeType trả về
            responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // Thiết lập thông tin trả về
            responseHeader.set("Content-disposition", "attachment; filename=" + file.getName());
            responseHeader.setContentLength(data.length);
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<InputStreamResource>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
