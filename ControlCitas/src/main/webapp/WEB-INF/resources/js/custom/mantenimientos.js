$(document).ready(function() {
    $.fn.initBootTable = function() {
        $(this).bootstrapTable('destroy');
        $(this).bootstrapTable().
        unbind('check.bs.table').on('check.bs.table', function (e, row) {
            consObje([{name : 'codiObjePara', value : row.id.trim()}]);
        });
        return false;
    };
    //Eliminar
    $.fn.funcElimRegi = function() {
        $(this).confirmation(
        {
            popout: true,
            onConfirm: function() {
                elimRegi();
                $('[data-toggle="confirmation-popout"]').confirmation('hide');
                return false;
            },
            onCancel: function()
            {
                $('[data-toggle="confirmation-popout"]').confirmation('hide');
                return false;
            }
        });
        return false;
    };
    
    INIT_OBJE();
});

function INIT_OBJE()
{
    $("#TablRegi").initBootTable();
    $("#FormRegi\\:btonElim").funcElimRegi();
}