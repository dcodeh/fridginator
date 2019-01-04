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
            <h1>Items</h1>

            <form action="./item" method="POST">
                <#if sharedItems??>
                    <h2>Shared</h2>
                    <#list sharedItems as item>
                        <br/>
                        <table class="items">
                            <tr>
                                <td colspan="2"><b>${item.getName()}</b>
                                    </br>${item.getWeeklyUsageString()}</td>
                                <td class="min">
                                    <input type="image" name="edit_${item.getName()}" src="gear.png">
                                </td>
                                <td class="min">
                                    <input type="image" name="unshare_${item.getName()}" src="delete.png">
                                </td>
                            </tr>
                            <tr>
                                <td class="min">
                                    <#switch item.getQuantityforImage()>
                                        <#case "0">
                                            <image src="empty.png">
                                        <#break>
                                        <#case "1">
                                            <image src="quarter.png">
                                        <#break>
                                        <#case "2">
                                            <image src="half.png">
                                        <#break>
                                        <#case "3">
                                            <image src="threequarters.png">
                                        <#break>
                                        <#case "4">
                                            <image src="full.png">
                                        <#break>
                                    </#switch>
                                </td>
                                <td colspan="3">${item.getQuantityForText()} ${item.getUnit()} in fridge</td>
                            </tr>
                            <#if item.hasWarning()>
                                <tr>
                                    <td class="min"><img src="warning.png"></td>
                                    <td colspan="3">${item.getWarningMessage()}</td>
                                </tr>
                            </#if>
                        </table>
                    </#list>
                <#else>
                    <h2>Shared (none)</h2>
                </#if>

                <#if unsharedItems??>
                    <h2>Not Shared</h2>
                    <table class="items">
                        <#list unsharedItems as item>
                            <tr>
                                <td>${item.getName}</td>
                                <td class="min"><input type="image" name="edit_${item.getName}" src="gear.png"></td>
                                <td class="min"><input type="image" name="share_${item.getName}" src="add.png"></td>
                            </tr>
                        </#list>
                    </table>
                <#else>
                    <h2>Not Shared (none)</h2>
                </#if>
                <br/>
                <button type="submit">New Item</button>
                <br/>
                <button type="submit">Home</button>
            </form>
        </div>
    </body>
</html>
