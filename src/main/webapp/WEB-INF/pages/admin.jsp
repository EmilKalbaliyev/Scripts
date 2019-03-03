<%-- 
    Document   : admin
    Created on : Jul 23, 2018, 8:27:05 PM
    Author     : Emil Kalbaliyev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="300">
        <title>Admin</title>
        <link rel="icon" type="image/ico" href="https://cdn2.iconfinder.com/data/icons/picons-basic-2/57/basic2-207_shell_unix_code-512.png">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
        <style>
            .table{
                width: 100%;
            }
            .table>tbody>tr>td, 
            .table>tfoot>tr>td,
            .table>thead>tr>td{
                text-align:center;
                vertical-align: middle;
                max-width: 350px;
            }
            .table>tbody>tr>th,
            .table>tfoot>tr>th, 
            .table>thead>tr>th{
                text-align:center;
                max-width: 350px;
            }
        </style>
        <script>
            $(document).ready(function () {
                $('#scripts').DataTable({
                    fixedHeader: true
                });
            });
//                 $(document).ready(function () {
//                     $(".links").click(function (event) {
//                         alert("salam");
//                         var address = $(this).attr("href");
//                         var param = prompt("Please enter parameter", "Harry Potter");
//                         alert(address + "/" + param);
//                         window.location.replace(address + "/" + param);
//                         $("#forLinks").attr("href", address + "/" + param);
//                         $("#forLinks").trigger("click");
//                     });
//                 });
        </script> 
    </head>
    <body>

        <nav class="navbar navbar-default navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header"style="margin-left:4.5%">
                    <a class="navbar-brand" href="#" >NMOC Scripts - ADMIN</a>
                </div>

                <ul class="nav navbar-nav navbar-right"style="margin-right:4%;font-size:15px;">
                    <li><a href="adminlogout"><span class="glyphicon glyphicon-log-out"></span> <b>Logout</b></a></li>
                </ul>
            </div>
        </nav>
        <div class="" style="margin-top:70px; margin-left: 5%;margin-right: 5%;">
            <a href="add" class="btn btn-primary "><b> <i class="glyphicon glyphicon-plus"></i> Add New</b></a>
            <table id="scripts" class="table table-striped table-bordered" >
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Script Name</th>
                        <th>Description</th>
                        <th>Automatic Run Date</th>
                        <th>Comments</th>
                        <th>Duration</th>
                        <th>Is additional Parameter needed?</th>
                        <th>Script</th>
                        <th>Check File Path</th>
                        <th>Download File Path</th>
                        <th>Can be downloaded?</th>
                        <th>Real Time Download</th>
                        <th>Process start status</th>
                        <th>Count</th>
                        <th>Last run by</th>
                        <th colspan="2">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="script" items="${scripts}">
                        <tr>
                            <td><b>${script.id}</b></td>
                            <td><b>${script.name}</b></td>
                            <td style=" font-size: 12px;">${script.note}</td>
                            <td>${script.date}</td>
                            <td>${script.comment}</td>      
                            <td>${script.duration}</td>
                            <c:if test="${script.additionalParam==1}"><td>Yes</td></c:if>
                            <c:if test="${script.additionalParam==0}"><td>No</td></c:if>
                            <td>${script.script}</td>
                            <td>${script.checkFile}</td>
                            <td>${script.downloadFilePath}</td>
                            <c:if test="${script.isDownloadable==1}"><td>Yes</td></c:if>
                            <c:if test="${script.isDownloadable==0}"><td>No</td></c:if>
                            <c:if test="${script.realTime==1}"><td>Yes</td></c:if>
                            <c:if test="${script.realTime==0}"><td>No</td></c:if>
                            <c:if test="${script.status==1}"><td>Running</td></c:if>
                            <c:if test="${script.status==0}"><td>Not Running</td></c:if>
                            <td>${script.count}</td>
                            <td >${script.lastPerson} at ${script.lastDate}</td> 
                            <td style=" border-right: 0px solid ;"><a href="edit/${script.id}" class="btn btn-success"><i class="glyphicon glyphicon-edit"></i> </a></td>
                            <td><a href="delete/${script.id}" class="btn btn-danger"><i class="glyphicon glyphicon-trash"></i></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
