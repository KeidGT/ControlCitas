$(document).ready(function() {
    $.fn.initBootTable = function() {
        $(this).bootstrapTable('destroy');
        $(this).bootstrapTable().
        unbind('check.bs.table').on('check.bs.table', function (e, row) {
            consVisi([{name : 'codiVisi', value : row.id.trim()},{name : 'nombVisi', value : row.nomb.trim()}]);
            
        });
        return false;
    };
    $.fn.initBootTable2 = function() {
        $(this).bootstrapTable('destroy');
        $(this).bootstrapTable().
        unbind('check.bs.table').on('check.bs.table', function (e, row) {
            consAlum([{name : 'codiVisi', value : row.id.trim()},{name : 'nombVisi', value : row.nomb.trim()}]);
        });
        return false;
    };
    
    INIT_OBJE_VISI();
    INIT_OBJE_ALUM();
});
function INIT_OBJE_VISI()
{
    $("#TablVisi").initBootTable();
}
function INIT_OBJE_ALUM()
{
    $("#TablAlum").initBootTable2();
}