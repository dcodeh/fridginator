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
            <h1>My List</h1>

            <div class="info">
                <p>Inventory will not update until you hit Save!</p>
            </div>

            <form action="./list" method="POST">
                <#if miscItems??>
                    <h2>Misc. Items</h2>
                <#else>
                    <h2>Misc. Items (none)</h2>
                </#if>
                <div class="list">

                    <!-- repeated section for misc items -->
                    <#if miscItems??>
                        <#list miscItems as item>
                            <label class="container">${item.getLine()}
                                <#if item.getIsChecked()>
                                    <input type="checkbox" name="${item.getLine()}" checked="checked">
                                <#else>
                                    <input type="checkbox" name="${item.getLine()}">
                                </#if>
                                <span class="checkmark"></span>
                            </label>
                        </#list>
                    </#if>
                    
                </div>

                <#if sharedItems??>
                    <h2>Shared Items</h2>
                <#else>
                    <h2>Shared Items (none)</h2>
                </#if>
                <div class="list">

                    <!-- repeated section for shared items-->
                    <#if sharedItems??>
                        <#list sharedItems as item>
                            <label class="container">${item.getLine()}
                                <#if item.getIsChecked()>
                                    <input type="checkbox" name="${item.getName()}" checked="checked">
                                <#else>
                                    <input type="checkbox" name="${item.getName()}"> 
                                </#if>
                                <span class="checkmark"></span>
                            </label>
                        </#list>
                    </#if>

                </div>

                <!-- Bottom Buttons -->
                <button type="submit" name="save">Save</button>
                <button type="submit" name="edit">Edit Misc</button>
                <button type="submit" name="menu">Menu</button>
            </form>
        </div> <!-- end of page -->
    </body>
</html>
