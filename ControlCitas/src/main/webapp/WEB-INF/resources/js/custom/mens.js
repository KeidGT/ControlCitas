function setMessage(tipo, title, msg) 
{
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": true,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "30000",
        "hideDuration": "10000",
        "timeOut": "30000",
        "extendedTimeOut": "10000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    if (tipo === 'MESS_WARN')
    {
        toastr.warning(msg, title);
    }
    else if (tipo === 'MESS_ERRO')
    {
        toastr.error(msg, title);
    }
    else if (tipo === 'MESS_SUCC')
    {
        toastr.success(msg, title);
    }
    else if (tipo === 'MESS_INFO')
    {
        toastr.info(msg, title);
    }
}