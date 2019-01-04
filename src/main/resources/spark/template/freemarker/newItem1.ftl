<!DOCTYPE html>
<!-- Copyright (c) 2018 David Cody Burrows...See LICENSE file for details -->
<!-- This page is the first in the sequence that the user interacts with to
     enter the information for a new item. --!>

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

            <form action="./newItem1" method="POST">
                Enter Information about the item.
                <br/>
                <input name="name" placeholder="Item Name" type="text"/>
                <br/>
                <input name="unit" placeholder="Unit" type="text"/>
                <br/>
                <label class="container">
                    <input type="checkbox" name="weekly" checked="false">
                    <span class="checkmark"></span>
                </label>
                <br/>
                <button type="submit">Next</button>
                <br/>
                <button type="submit">Abort</button>
            </form>
        </div>
    </body>
</html>
