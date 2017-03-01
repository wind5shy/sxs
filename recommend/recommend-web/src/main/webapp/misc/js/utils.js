/*
 *统一的业务工具类
 */

(function($,w){
    var wBox;
    w.utils ={};

    $.extend(w.utils, {
        typeConf : {
            LINE:'xy',
            BAR:'xy',
            SCATTER:'xy',
            PIE:'kv',
            K:'k',
            FUNNEL:'kv',
            FORCE:'xyz',
            MAP:'map',
            TABLE:'',
            CHORD:'chord'
        },
        chartType:{
            LINE:1,
            BAR:2,
            SCATTER:3,
            PIE:5,
            K:4,
            FUNNEL:11,
            FORCE:8,
            MAP:9,
            TABLE:20,
            CHORD:7
        },
        fgetHtml : function(url, id, params){
            $.ajax({
                url:url,
                type:'POST',
                dataType:'html',
                data:params || {},
                success:function(data){
                    $('#'+id).html(data);
                }
            });
        },
        fgetHtmlsync : function(url, id, params){
            $.ajax({
                url:url,
                type:'POST',
                async:false,
                dataType:'html',
                data:params || {},
                success:function(data){
                    $('#'+id).html(data);
                }
            });
        },
        fReplaceAll : function (str, src, rep) {
            while(str.indexOf(src) != -1){
                str = str.replace(src, rep);
            }
            return str;
        },
        fgetParamsArray:function(json, limitParams){
            var params={
                filters:json
            };
            if(limitParams){
                params.sorters = limitParams;
            }
            return [params];
        }
    });
})(jQuery,window);