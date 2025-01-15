package com.example.library.utils;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @RequestMapping
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = Integer.valueOf(status.toString());
        StringBuilder htmlResponse = new StringBuilder();

        htmlResponse.append("<!DOCTYPE html>")
                    .append("<html>")
                    .append("<head><title>Error</title></head>")
                    .append("<body>");

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            htmlResponse.append("<h1>404 - Resource not found</h1>")
                        .append("<p>The requested resource could not be found.</p>");
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            htmlResponse.append("<h1>500 - Internal Server Error</h1>")
                        .append("<p>There was an internal server error. Could be a resource not found.</p>");
        } else {
            htmlResponse.append("<h1>400 - Bad Request</h1>")
                        .append("<p>The request could not be understood or was missing required parameters.</p>");
        }
        htmlResponse.append("<button type=\"button\" onclick=\"location.href='/v1/books/'\">Go to Books</button>")
                    .append("</body>")
                    .append("</html>");

        return htmlResponse.toString();
    }

    @Deprecated
    public String getErrorPath() {
        return null;
    }
}
