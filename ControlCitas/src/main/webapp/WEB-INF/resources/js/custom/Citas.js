$(document).ready(function() {
    $.fn.initBootTableAlum = function() {
        $(this).bootstrapTable('destroy');
        $(this).bootstrapTable().
        unbind('check.bs.table').on('check.bs.table', function (e, row) {
            consObje([{name : 'codiObjePara', value : row.id.trim()}]);
        });
        return false;
    };
    $('#ModaFormRegi').on('shown.bs.modal', function () {
        $("#TablAlum").initBootTableAlum();
        setMessage('MESS_INFO', 'Atención', 'ABRIENDO');
        setTimeout('setMessage("MESS_INFO", "Atención", "ABRIENDO")', 3000);
    });
    INIT_OBJE_CITA();
});
function INIT_OBJE_CITA(){
 $("#FormRegi\\:btonAgreVisi").confirmation({container: '#FormRegi'});
 
 
 
 //$("#TablAlum").initBootTableAlum();
}
