<%-- 
    Document   : edit
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
        <link rel="icon" type="image/ico" href="https://cdn2.iconfinder.com/data/icons/picons-basic-2/57/basic2-207_shell_unix_code-512.png">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
        <title>Edit</title>
        <script>
            window.onload = function () {
                var input = document.getElementById('myTextInput');
                input.focus();
                input.select();
            }
        </script>
        <style>
            .table>tbody>tr>td, 
            .table>tfoot>tr>td,
            .table>thead>tr>td{
                text-align:center;
                vertical-align: middle;
            }
            .table>tbody>tr>th,
            .table>tfoot>tr>th, 
            .table>thead>tr>th{
                text-align:center;
            }
            .values{
                font-weight: bold
            }
        </style>

    </head>
    <body>

        <c:url value='/save' var='url'/>
        <form:form action="${url}" method='POST' >
            <div class="" style="margin-top:10px; margin-left: 35%;margin-right: 35%;">

                <table id="scripts" class="table table-striped table-bordered" >
                    <th colspan="2"> <h3 class="panel-title" style="text-align:center"><b>EDIT</b></h3></th>
                    <input id="id"  name="id" value="${script.id}" type="hidden" required="">
                    <tr><td class="values">Name:</td><td><input id="name" class="form-control" placeholder="Name" name="name" value="${script.name}" type="text" required=""></td></tr>
                    <tr><td class="values">Description:</td><td><textarea rows="3" class="form-control" id="note"  placeholder="Note" name="note"  type="text" required="">${script.note}</textarea></td></tr>
                    <tr><td class="values">Automatic Run Date:</td><td><input id="date" class="form-control" placeholder="Automatic Run Date:" name="date" value="${script.date}" type="text" required=""></td></tr>
                    <tr><td class="values">Comment:</td><td><textarea rows="3" id="comment" class="form-control" placeholder="Comment" name="comment" type="text" required="">${script.comment}</textarea ></td></tr>
                    <tr><td class="values">Duration:</td><td><input id="duration" class="form-control" placeholder="Duration" name="duration" value="${script.duration}" type="text" required=""></td></tr>
                    <tr><td class="values">Additional Parameter<br>(additionalParam)</td>
                        <td>
                            <input type="radio" name="additionalParam" value="1" style="margin-right:5px;"<c:if test="${script.additionalParam==1}">checked</c:if>>Yes
                            <input type="radio" name="additionalParam" value="0" style="margin-left:70px;margin-right:5px;" <c:if test="${script.additionalParam==0}">checked</c:if>> No
                        </td>
                    </tr>
                    <tr><td class="values">Script:</td><td><input id="script" class="form-control" placeholder="Script" name="script" value="${script.script}" type="text" required=""></td></tr>
                    <tr><td class="values">Check File Path:</td><td><input id="checkFile" class="form-control" placeholder="Check File Path" name="checkFile" value="${script.checkFile}" type="text" required=""></td></tr>
                    <tr><td class="values">Download File Path:</td><td><input id="downloadFilePath" class="form-control" placeholder="Name" name="downloadFilePath" value="${script.downloadFilePath}" type="text" required=""></td></tr>
                    <tr><td class="values">Can be downloaded</td>
                        <td>
                            <input type="radio" name="isDownloadable" value="1" style="margin-right:5px;"<c:if test="${script.isDownloadable==1}">checked</c:if>>Yes
                            <input type="radio" name="isDownloadable" value="0" style="margin-left:70px;margin-right:5px;" <c:if test="${script.isDownloadable==0}">checked</c:if>> No
                        </td>
                    </tr>

                    <tr><td class="values">Real Time Download</td>
                        <td>
                            <input type="radio" name="realTime" value="1" style="margin-right:5px;"<c:if test="${script.realTime==1}">checked</c:if>>Yes
                            <input type="radio" name="realTime" value="0" style="margin-left:70px;margin-right:5px;" <c:if test="${script.realTime==0}">checked</c:if>> No
                        </td>
                    </tr>

                    <tr><td class="values">Is process started?</td>
                       <td>
                            <input type="radio" name="status" value="1" style="margin-right:5px;"<c:if test="${script.status==1}">checked</c:if>>Yes
                            <input type="radio" name="status" value="0" style="margin-left:70px;margin-right:5px;" <c:if test="${script.status==0}">checked</c:if>>No
                        </td>
                    </tr>

                    <tr><td class="values">Count</td><td><input id="count" class="form-control" placeholder="Count" name="count" value="${script.count}" type="number" required=""></td></tr>
                    <tr><td class="values">Last Person</td><td><input id="lastPerson" class="form-control" placeholder="Last Person" name="lastPerson" value="${script.lastPerson}" type="text" required=""></td></tr>
                    <tr><td class="values">Last Time</td><td><input id="lastDate" class="form-control" placeholder="Last date" name="lastDate" value="${script.lastDate}" type="text" required=""></td></tr>
                    <tr><td colspan="2"><input class="btn  btn-success btn-block " type="submit" value="Save"></td></tr>
                    <tr><td colspan="2"><a href="../admin" class="btn btn-warning btn-block">Cancel</a></td></tr>

                </table>
            </div>

        </form:form>
    </body>
</html>
