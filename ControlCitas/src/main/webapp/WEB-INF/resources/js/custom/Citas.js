$(document).ready(function() {
    $.fn.initBootTableAlum = function() {
        $(this).bootstrapTable('destroy');
        $(this).bootstrapTable().
        unbind('check.bs.table').on('check.bs.table', function (e, row) {
            consObje([{name : 'codiObjeAlum', value : row.id.trim()}]);
        });
        return false;
    };
    $.fn.initBootTableVisi = function() {
        $(this).bootstrapTable('destroy');
        $(this).bootstrapTable().
        unbind('check.bs.table').on('check.bs.table', function (e, row) {
            consObje([{name : 'codiObjeVisi', value : row.id.trim()}]);
        });
        return false;
    };
    $('#ModaFormRegi').on('shown.bs.modal', function () {
        INIT_OBJE_TABL();
        //setMessage('MESS_INFO', 'Atención', 'ABRIENDO');
        //setTimeout('funcion()', 3000);
    });
    $('#ModaFormRegi').on('show.bs.modal', function () {
        INIT_OBJE_TABL();
        //setMessage('MESS_INFO', 'Atención', 'ABRIENDO');
        //setTimeout('funcion()', 3000);
    });
    INIT_OBJE_CITA();
});
function INIT_OBJE_CITA(){
    $("#FormRegi\\:btonAgreVisi").confirmation({container: '#FormRegi'});
    
 //$("#TablAlum").initBootTableAlum();
}
function INIT_OBJE_TABL(){
    $("#TablAlum").initBootTableAlum();
    $("#TablVisiCita").initBootTableVisi();
    setMessage('MESS_INFO', 'Atención', 'Inicializando...');
}