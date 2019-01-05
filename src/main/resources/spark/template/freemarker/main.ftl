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
        </#if>
    
        <div class="page">
            <center><img src="/images/favicon.png"></center>
            <h1>Fridginator</h1>

            <form action="./signIn" method="POST">
                <br/>
                <button name="items" type="submit">Items</button>
                <br/>
                <button name="viewList" type="submit">View List</button>
                <br/>
                <button name="editList" type="submit">Edit Misc List</button>
                <br/>
                <button name="settings" type="submit">Settings</button>
                <br/>
                <button name="Sign Out" type="submit">Sign Out</button>
            </form>

            <div class="info">
                <p>Version ${version} | ${release}</p>
            </div>
        </div>
    </body>
</html>
