<%-- 
    Document   : index
    Created on : 2014.05.07., 16:49:58
    Author     : Zsolt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Two-Player Games</title>
    </head>
    <body>
        <table width="100%">
            <tr>
                <td>
                    <h1>Two-Player Games</h1>
                </td>
                <td width="10%" valign="top">
                    <a href="/kompgame-web/faces/user/Create.xhtml">Register</a><br />
                    <a href="/kompgame-web/faces/secure/gamelist.xhtml">Sign In</a>
                </td>
            </tr>
        </table>
        for Komponens alapú szoftverfejlesztés course on Eötvös Loránd University.</br>
        The goal was to create a program in which users can register, then log in, and play double games each other or against artificial intelligence.</br>
        Double play is a classic board game for us, in which:
        <ul>
            <li>Two people playing against each other.</li>
            <li>Players take turns, one after the other, As far as possible choose a finite number of steps.</li>
            <li>The game did not get the role of fortune, all is entirely up to the players actions.</li>
            <li>The game is guaranteed to come to an end, after a finite number of steps</li>
        </ul>
    </body>
</html>
