<!DOCTYPE html>
<!-- Copyright (c) 2018 David Cody Burrows...See LICENSE file for details -->

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
            <h1>Update Quantity</h1>
            <h2>${itemName}</h2>

            <form action="./updateInventory" method="POST">
                <input type="hidden" name="itemID" value="${itemID}" />
                Enter the amount of this item currently in the fridge (or wherever it's kept).
                <br/>
                <input name="qty" value="${estQty}" type="number" step="0.01"/>
                ${unit}
                <br/>
                <button type="submit" name="save">Save</button>
                <br/>
                <button type="submit" name="abort">Abort</button>
            </form>
        </div>
    </body>
</html>
