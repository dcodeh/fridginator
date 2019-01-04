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
            <h1>Sign Up</h1>

            <form action="./signUp" method="POST">
                Enter your account information. All fields are required.
                <br/>
                <input name="username" placeholder="Username" type="text"/>
                <br/>
                <input name="password" placeholder="Password" type="password"/>
                <br/>
                <input name="password2" placeholder="Retype Password" type="password"/>
                <br/>
                <button type="submit">Submit</button>
            </form>

            <div class="info">
                <p>You will not be able to create a user account unless you
                   know the secret code. It's on the fridge
                   at the Pink House</p>
            </div>
        </div>
    </body>
</html>
