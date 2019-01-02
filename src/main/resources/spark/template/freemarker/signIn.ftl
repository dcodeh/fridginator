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
            <h1>Fridginator</h1>

            <form action="./signIn" method="POST">
                Enter your credentials to log in.
                <br/>
                <input name="username" placeholder="Username" type="text"/>
                <br/>
                <input name="password" placeholder="Password" type="password"/>
                <br/>
                <button type="submit">Log In</button>
            </form>
            
            <br>
            <a href="/signUp">New User</a>

            <div class="info">
                <p>Version ${version} | ${release}</p>
            </div>
        </div>
    </body>
</html>
