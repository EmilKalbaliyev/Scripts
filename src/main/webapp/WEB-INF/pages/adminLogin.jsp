<%-- 
    Document   : login
    Created on : Jul 23, 2018, 11:35:55 PM
    Author     : Emil Kalbaliyev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="icon" type="image/ico" href="https://cdn2.iconfinder.com/data/icons/picons-basic-2/57/basic2-207_shell_unix_code-512.png">
        <title>Login</title>
        <script>
            window.onload = function () {
                var input = document.getElementById('myTextInput');
                input.focus();
                input.select();
            }
        </script>
        <style>

            body{
                padding-top:50px;
            }
            a{
                color:grey;
                text-decoration: none;

            }
            a.btn:hover{
                color:white;
                text-decoration: none;
            }

        </style>
    </head>
    <body>

        <c:url value='/adminlogincheck' var='url'/>
        <form:form action="${url}" method='POST' >
            <div class="container">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title" style="text-align:center">ADMIN</h3>
                            </div>
                            <div class="panel-body">
                                <fieldset>
                                    <div class="form-group">

                                        <c:if test="${not empty message}">
                                            <div class="alert alert-success" role="alert">
                                                ${message}
                                            </div>
                                        </c:if>
                                        <c:if test="${not empty error}">
                                            <div class="alert alert-danger" role="alert">
                                                ${error}
                                            </div>  
                                        </c:if>
                                    </div>
                                    <div class="form-group">
                                        <input id="myTextInput" class="form-control" placeholder="Username" name="username" type="text" required="">
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Password" name="password" type="password" value="" required="">
                                    </div>
                                    <input class="btn btn-lg btn-success btn-block" type="submit" value="Login">
                                </fieldset>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </body>
</html>
