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
            <h1>Share ${itemName}</h1>

            <form action="./shareItem" method="POST">
                <input type="hidden" name="itemID" value="${itemID}" />
                Enter your expected weekly usage of this item. This is only
                an estimate.
                <br/>
                <input name="usage" placeholder="Exp. Usage" type="number" step="0.01"/>
                ${unit} per week
                <br/>
                <button type="submit" name="save">Save</button>
                <br/>
                <button type="submit" name="abort">Abort</button>
            </form>
        </div>
    </body>
</html>
