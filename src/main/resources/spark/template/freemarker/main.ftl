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
            <center><img src="/images/favicon.png"></center>
            <h1>Fridginator</h1>

            <a href="./getItems">Items</a><br/>
            <a href="./getItems">View List</a><br/>
            <a href="./getItems">Edit Misc List</a><br/>
            <a href="./getItems">Settings</a><br/>
            <a href="./signOut">Sign Out</a><br/>

            <div class="info">
                <p>Version ${version} | ${release}</p>
            </div>
        </div>
    </body>
</html>
