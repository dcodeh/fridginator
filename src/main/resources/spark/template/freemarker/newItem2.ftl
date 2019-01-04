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
        </#if>
    
        <div class="page">
            <h1>New Item</h1>
            <h2>${itemName}</h2>

            <form action="./removePQ" method="POST">
                <#if hasPQs??>
                    <h3>Purchasable Quantities</h3>
                    <table>
                        <#list PQList as pq>
                            <tr>
                                <td>${pq.getQty()}</td>
                                <td>${pq.getUnit()}</td>
                                <td>${pq.getPrice()}</td>
                                <td><input type="image" id="${pq.getQty()}"
                                     src="/images/trash.png"></td>
                            </tr>
                        </#list>
                    </table>
                <#else>
                    <h3>PurchasableQuantities (none)</h3>
                </#if>
            </form>

            <form action="./createPQ" method="POST">
                <br/>
                <input name="qty" placeholder="Quantity" type="number"/>
                <p>${unit}</p>
                <br/>
                <p>$</p>
                <input name="price" placeholder="Price" type="number"/>
                <br/>
                <button type="submit">Add</button>
                <button type="submit">Done</button>
            </form>
        </div>
    </body>
</html>
