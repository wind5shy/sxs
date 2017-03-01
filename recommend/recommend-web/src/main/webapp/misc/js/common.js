/**
 * Created by zhang on 2014/11/27.
 * 公共方法
 */

(function(){
    window.DL = {
        fdo : function(url,params,callback){
            $.ajax({
                url:url,
                type:'POST',
                dataType:'json',
                data:params,
                success:function(data){
                    if(callback instanceof Function){
                        callback(data);
                    }
                }
            });
        },
        fgetHtmldo : function(url,params,id,callback){
            $.ajax({
                url:url,
                type:'POST',
                data:params,
                success:function(data){
                    $('#'+id).html(data);
                    if(callback instanceof Function){
                        callback();
                    }
                }
            });
        },
        navChange : function (index) {
            $('.summary li').removeClass('current');
            $('.summary li').eq(index).addClass('current');

            $('.report-step').hide();
            $('.report-step').eq(index-1).show();
        },

        editNavChange: function (index) {
            $('.summary li').removeClass('current');
            $('.summary li').eq(index).addClass('current');
            $('.report-step').hide();
            $('.report-step').eq(index).show();
        }




    };
})();