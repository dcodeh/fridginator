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
            <h1>Settings</h1>

            <form action="./updateSettings" method="POST">
                Change your password using the fields below. That's all you can do here at the moment ;)
                <br/>
                <input name="username" placeholder="Username" type="text" value="${username}"/>
                <br/>
                <input name="password" placeholder="Password" type="password" value="${password}"/>
                <br/>
                <input name="password2" placeholder="Retype Password" type="password"/>
                <br/>
                <button type="submit">Submit</button>
            </form>
        </div>
    </body>
</html>
