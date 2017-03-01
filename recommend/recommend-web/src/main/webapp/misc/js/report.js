/**
 * Created by zhang on 2014/12/11.
 */

(function () {
    window.Report = {
        dragValidate: function () {
            var flag = true, liSize;
            $('.drag table').each(function () {
                $(this).find('td').each(function (index, n) {
                    if (index == 0) {
                        liSize = $(n).find('li').size();
                    } else if ($(n).find('li').size() != liSize) {
                        showErrormsg('每个字段都需要填充，非选择项用空白字段填充');
                        flag = false;
                    }
                });
            });
            if (liSize == 0) {
                showErrormsg('请选择字段！');
                return false;
            }
            $('.drag table').each(function () {
                $(this).find('td:last').find('input').each(function () {
                    if ($(this).val().trim() == '') {
                        showErrormsg('请输入名称！');
                        flag = false;
                    }
                });
            });


            var chartType = $('#current-type').val();
            if (chartType == 'LINE' || chartType == 'BAR') {
                var msg = validateDefXY();
                if (msg) {
                    showErrormsg(msg);
                    flag = false;
                }
            }
            if (chartType == 'K') {
                var msg = validateDefK();
                if (msg) {
                    showErrormsg(msg);
                    flag = false;
                }
                if (liSize > 1) {
                    showErrormsg('K线图只能拖入一组数据！');
                    flag = false;
                }
            }
            if (chartType == 'PIE' || chartType == 'FUNNEL') {
                var msg = validateDefKV();
                if (msg) {
                    showErrormsg(msg);
                    flag = false;
                }
            }
            return flag;
        },
//        改造，加入传入的sql和数据源
        getReportParams: function (tableId,sql,sourceType) {
            var name = $('#name').val().trim(),
                description = $('#description').val();

            var randomSqlText=$('.random-sql textarea'),randomSql='';
            for(var i=0;i<randomSqlText.length;i++) {
                var temp = randomSqlText.eq(i).val();
                if (i < randomSqlText.length - 1) {
                    randomSql += temp + ';';
                }
                else {
                    randomSql += temp;
                }
            }

            id = $('#reportId').val(),
                type = $('#current-type').val(),
                conf = {
                    name: name,
                    type: type
                };
            var sqljson = getSqlParams(),
                dragjson = getDragParams();
             var limit;
            if(sourceType==1){
                limit=Report.getLimitInputParams('sort-params');
            }
            else if(sourceType==6){
                limit=Report.getRedisLimitInputParams('sort-params')
            }

              var  json = {
                    filters: sqljson,
                    names: dragjson,
                    sorters: limit
                };
//            if(type=="FORCE"){
//                delete json.sorters;
//            }
            var jsonArray = [json];
            if (type=='FORCE'||type=='CHORD') {
                conf.groups = getMutiTableGroups();
            } else {
                conf.groups = getGroups();
            }
            var params = {
                name: name,
                description: description,
                type: 1,
                sourceType:sourceType,
                opExpr: sql,
                randomExpr:randomSql,
                params: JSON.stringify(jsonArray),
                chartType: utils.chartType[type],
                chartConf: JSON.stringify(conf)
            };
            if (id != '') {
                params.id = id;
            }
            return params;
        },
        // 拖拽事件方法
        dragEvent: function (target, dropInput, configType) {
            dropInput = dropInput || 'drop-name';
            $('.left li').draggable({
                helper: 'clone',
                snap: target.join(','),
                revert: 'invalid'
            });
            var tempHtml = '<li class="temp" style="background-color:#8E8E8E">放在这里</li>';
            $('body').delegate('.a-remove', 'click', function () {
                $(this).closest('li').remove();

            });
            $(target[target.length - 1]).delegate('.a-remove', 'click', function () {
                $('#' + dropInput + ' li:last').remove();
            });
            for (var i = 0, length = target.length; i < length; i++) {
                (function (i) {
                    $(target[i]).droppable({
                        drop: function (event, ui) {
                            $('.temp').remove();
                            var name = ui.draggable.attr('name'),
                                table = ui.draggable.attr('table'),
                                type = ui.draggable.attr('type'),
                                _this = $(this);
                            if (configType == "map") {
                                _this.find('ul').append('<li  style="width:80px;" table="' + table + '" type="' + type + '"  name="' + name + '" ><span>' + ui.draggable.html() + '</span><a class="a-remove" href="javascript:void(0);">X</a></li>');
                            }
                            else {
                                _this.find('ul').append('<li   table="' + table + '"  type="' + type + '" name="' + name + '" ><span>' + ui.draggable.html() + '</span><a class="a-remove" href="javascript:void(0);">X</a></li>');
                            }

                            var liSize = _this.find('li').size();
                            /*当最后一个拖拽进入时，在行最後加input*/
                            if ($('#' + dropInput + ' li').size() < liSize) {
                                if(i==target.length-1){
                                    $('#' + dropInput).append('<li style="width:150px;"><input  style="display: inline"  table="' + table + '" type="text" /><span></span><a  href="javascript:void(0);">X</a></li>');
                                }
                            }
                        },
                        over: function (event, ui) {
                            if ($('.temp').size() == 0) {
                                $(this).find('ul').append(tempHtml);
                            }
                        },
                        out: function () {
                            $('.temp').remove();
                        }
                    });
                })(i);
            }
        },
        // 拖拽事件方法,处理多个table情况，如力导向图
        dragMutiTableEvent: function (target, dropInput, configType) {
            dropInput = dropInput || 'drop-name';
            $('.left li').draggable({
                helper: 'clone',
                snap: target.join(','),
                revert: 'invalid'
            });
            var tempHtml = '<li class="temp" style="background-color:#8E8E8E">放在这里</li>';
            $('body').delegate('.a-remove', 'click', function () {
                $(this).closest('li').remove();

            });
            $(target[target.length - 1]).delegate('.a-remove', 'click', function () {
                var dropContainer=$(this).siblings('.drop-container').find('ul').eq(0);
                dropContainer.find('li:last').remove();
//                $('#' + dropInput + ' li:last').remove();
            });
            for (var i = 0, length = target.length; i < length; i++) {
                (function (i) {
                    $(target[i]).droppable({
                        drop: function (event, ui) {
                            var dropContainer=$(this).siblings('.drop-container').find('ul').eq(0);
                            $('.temp').remove();
                            var name = ui.draggable.attr('name'),
                                table = ui.draggable.attr('table'),
                                type = ui.draggable.attr('type'),
                                _this = $(this);
                            if (configType == "map") {
                                _this.find('ul').append('<li  style="width:80px;" table="' + table + '" type="' + type + '"  name="' + name + '" ><span>' + ui.draggable.html() + '</span><a class="a-remove" href="javascript:void(0);">X</a></li>');
                            }
                            else {
                                _this.find('ul').append('<li   table="' + table + '"  type="' + type + '" name="' + name + '" ><span>' + ui.draggable.html() + '</span><a class="a-remove" href="javascript:void(0);">X</a></li>');
                            }
                        },
                        over: function (event, ui) {
                            if ($('.temp').size() == 0) {
                                $(this).find('ul').append(tempHtml);
                            }
                        },
                        out: function () {
                            $('.temp').remove();
                        }
                    });
                })(i);
            }
        },


        optionFormat: function () {
            $(this).attr('disabled', true);
            var option_source = $('#report-option-text').val().replace(/^\s+/, '');
            $('#report-option-text').val(js_beautify(option_source, 4, ' '))
            $(this).attr('disabled', false);
        },
        optionCompress: function (_this) {
            var formatOption = $(_this).siblings('textarea').val();
            var packer = new Packer;
            $(_this).siblings('textarea').val(packer.pack(formatOption, 0, 0));
        },
        inputValidate: function (domId) {
            var result = true;
            var trs = $('#' + domId).find('tbody tr');
            trs.each(function () {
                var tds = $(this).find('td');
                for (var i = 0; i < tds.length; i++) {
                    if (tds.eq(i).find('input').val() == '') {
                        alert('请输入limit参数！');
                        result = false;
                        return false;
                    }
                }
            });

            return result;
        },


        limitInputValidate: function (domId) {
            var result = true;
            var trs = $('#' + domId).find('tbody tr');
            trs.each(function () {
                var tds = $(this).find('td');
                for (var i = 0; i < tds.length; i++) {
                    if (tds.eq(i).find('input').val() == '') {
                        result = false;
                        return false;
                    }
                }
            });

            return result;
        },
        // 获取limit配置的参数和参数值
        getLimitInputParams: function (domId) {
            var result = [];
            var trs = $('#' + domId).find('tbody tr');
            var params;
            trs.each(function (index, n) {
                 params = {
                    index: index
                };
                var tds = $(this).find('td');
                for (var i = 0; i < tds.length; i++) {
                    var input = tds.eq(i).find('input');
                    params[input.attr('name')] = input.val();
                }
            });
            if(params!=undefined){
                var sqlLength= $('.sql textarea').size();
                console.log("sqllength:"+sqlLength);
                for(var i=0;i<sqlLength;i++){
                  var copy={};
                   copy.index=i;
                   copy.offset=params.offset;
                   copy.count=params.count;
                    result.push(copy);
                }
            }
            console.log(result);
            return result;
        },


        // 获取redislimit配置的参数和参数值
        getRedisLimitInputParams: function (domId) {
            var result = [];
            var trs = $('#' + domId).find('tbody tr');
            var params;
            trs.each(function (index, n) {
                params = {
                    index: index
                };
                var tds = $(this).find('td');
                for (var i = 0; i < tds.length; i++) {
                    var input = tds.eq(i).find('input');
                    params[input.attr('name')] = input.val();
                }
            });
            if(params!=undefined){
                var redisength= $('.redis').size();
                console.log("redissize:"+redisength);
                for(var i=0;i<redisength;i++){
                    var copy={};
                    copy.index=i;
                    copy.offset=params.offset;
                    copy.count=params.count;
                    result.push(copy);
                }
            }
            console.log(result);
            return result;
        }
    };


    function showErrormsg(msg) {
        $('#errormsg').html('<p>' + msg + '</p>');
        setTimeout(function () {
            $('#errormsg p').fadeOut();
        }, 2500);
    }
//    取得力导向图的参数，多个table情况
    function getMutiTableGroups(){
        var groups = [];
        var obj = {};
        obj.name='';
        obj.datas=[];
       $('.drop-target').each(function(index){
           var tableIndex=$(this).find('li').eq(0).attr('table');
           var  tableFiled=$(this).find('li').eq(0).attr('name');
           var  dataType=$(this).find('li').eq(0).attr('type');
           var   dimType=$(this).attr('type');
           if(tableFiled=='undefined'){
               obj.datas.push({
                   dimType: dimType
               });
           }
           else{
               obj.datas.push({
                   tableIndex:  tableIndex,
                   tableFiled: tableFiled,
                   dataType: dataType,
                   dimType: dimType
               });
           }
       })
        console.log(obj)
        groups.push(obj);
        return groups;


    }




















    function getGroups(tableId) {
        tableId = tableId || 'wctable';
        var groups = [],
            lenRow = $('#' + tableId + ' tbody td:first').find('li').length,
            lenColumn = $('#' + tableId + ' tbody td').length;

        var lenRow = getRowlength(tableId, lenColumn)

        for (var i = 0; i < lenRow; i++) {
            var obj = {};
            obj.name = $('#' + tableId + ' tbody td:last').find('input').eq(i).val();
            obj.datas = [];
//            var ulSize= $('#wctable tbody td ul').size();
//            for(var j=0;j<ulSize;j++){
//             var  table=$('#wctable tbody ').find('ul').eq(j).find('li').eq(i).attr('table');
//                if(table!=undefined){
//                    break;
//                }
//
//            }
            for (var j = 0; j < lenColumn - 1; j++) {
                var tableIndex=$('#' + tableId + ' tbody td').eq(j).find('li').eq(i).attr('table');
                var  tableFiled=$('#' + tableId + ' tbody td').eq(j).find('li').eq(i).attr('name');
                var  dataType=$('#' + tableId + ' tbody td').eq(j).find('li').eq(i).attr('type');
                var   dimType=$('#' + tableId + ' tbody td').eq(j).find('ul').attr('type');
                if(tableFiled=="undefined"){
                    obj.datas.push({
                        dimType: dimType
                    });
                }
                else{
                    obj.datas.push({
                        tableIndex:  $('#' + tableId + ' tbody td').eq(j).find('li').eq(i).attr('table'),
                        tableFiled: $('#' + tableId + ' tbody td').eq(j).find('li').eq(i).attr('name'),
                        dataType: $('#' + tableId + ' tbody td').eq(j).find('li').eq(i).attr('type'),
                        dimType: $('#' + tableId + ' tbody td').eq(j).find('ul').attr('type')
                    });
                }

            }
            console.log("push的y1table属性" + obj)
            console.log(obj)
            groups.push(obj);
        }
        return groups;

    }

    //        獲取最大行數
    function getRowlength(tableId, lenColumn) {
        var max = 0;
        for (var i = 0; i <= lenColumn; i++) {
            var rowlen = $('#' + tableId + ' tbody td').eq(i).find('li').size();
            if (rowlen > max) {
                max = rowlen;
            }
        }
        return max;
    }

    // 自定义方法
    function validateDragLi(_this, type, name, table) {
        var result = true,
            dropType = _this.find('li:first').attr('type');
        //要插入的li location
        var liLocation = _this.find('li').size();
        console.log('current lisie' + liLocation);
        var brother = _this.siblings();
        console.log("兄弟个数为" + brother.size());
        var dropTable

        for (var i = 0; i < brother.size(); i++) {
            if( brother.eq(i).find('li').eq(liLocation).attr('emptyFlag')!=undefined){
                continue;
            }
            var brotherTable = brother.eq(i).find('li').eq(liLocation).attr('table');
            if (brotherTable != undefined) {
                dropTable = brotherTable;
            }
        }

        var typeCheck = true;
        var tableCheck = true;

        if (dropType != undefined) {
            if (dropType != type) {
                showErrormsg('只能拖入相同类型的字段！');
                typeCheck = false;
            }
        }
        if(dropTable!=undefined){
            if(dropTable!=table){
                showErrormsg('每个sql的查询字段只能拖入进同一行！');
                tableCheck=false;
            }
        }
        return typeCheck&&tableCheck;
    }


})();