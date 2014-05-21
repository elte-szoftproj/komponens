/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
    if (window.WebSocket && false) {
        // Web sockets are supported by the browser 
    } else {
        // Web sockets are not supported, do AJAX polls
        
        var timeout = 1000;
        var $pollObject = $(".marker-poll");
                
        var poll = function () {
            $pollObject.trigger("click");
            setTimeout(poll, timeout);
        };
        
        poll();
    }
});
