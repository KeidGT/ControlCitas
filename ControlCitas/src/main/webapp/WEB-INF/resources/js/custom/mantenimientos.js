$(document).ready(function() {
    $.fn.initBootTable = function() {
        $(this).bootstrapTable('destroy');
        $(this).bootstrapTable().
        unbind('check.bs.table').on('check.bs.table', function (e, row) {
            consObje([{name : 'codiObjePara', value : row.id.trim()}]);
        });
        return false;
    };
    
    INIT_OBJE();
});
function INIT_OBJE()
{
    $("#TablRegi").initBootTable();
    $("#FormRegi\\:btonElim").confirmation({container: '#FormRegi'});
    $(".select").selectpicker();
}