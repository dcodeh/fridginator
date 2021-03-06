<!DOCTYPE html>
<!-- Copyright (c) 2018 David Cody Burrows...See LICENSE file for details -->
<!-- This page is the second in the sequence that the user interacts with to
     enter the information for a new item. The user enters
     purchasable quantities here. --!>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${title} | Fridginator</title>
        <link rel="stylesheet" type="text/css" href="/styles/style.css">
        <link rel="icon" href="/images/favicon.png">
    </head>
    
    <body>
    
        <#if message??>
            <div class="${messageType}">${message}</div>
            <br/>
        </#if>
    
        <div class="page">
            <h1>New Item</h1>
            <h2>${itemName}</h2>

            <div class="form">
                <#if PQList??>
                    <h3>Purchasable Quantities</h3>
                    <br/>
                    <table>
                        <#list PQList as pq>
                            <tr>
                                <form action="./removePQ" method="GET">
                                    <#if itemID??>
                                        <input type="hidden" name="itemID" value="${itemID}" />
                                    </#if>
                                    <td>${pq.getQty()}</td>
                                    <td>${unit}</td>
                                    <td>$${pq.getPrice()}</td>
                                    <td><input type="image" src="/images/delete.png"></td>
                                    <input type="hidden" name="pqID" value="${pq.getID()}" />
                                 </form>
                            </tr>
                        </#list>
                    </table>
                <#else>
                    <h3>Purchasable Quantities (none)</h3>
                </#if>
            </div>
            <br/>
            <form action="./createPQ" method="GET">
                <h3>New Purchasable Quantity</h3>
                <#if itemID??>
                    <input type="hidden" name="itemID" value="${itemID}" />
                </#if>
                <table>
                    <tr>
                        <td class="min"></td>
                        <td><input name="qty" placeholder="Quantity" type="number" step="0.01"/></td>
                        <td class="min">${unit}</td>
                    </tr>
                    <tr>
                        <td class="min">$</td>
                        <td><input name="price" placeholder="Price" type="number" step="0.01"/></td>
                        <td class="min"></td>
                    </tr>
                    <tr>
                        <td colspan="3"><button name="add" type="submit">Add</button></td>
                    </tr>
                    <tr>
                        <td colspan="3"><button name="done" type="submit">Done</button></td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>
