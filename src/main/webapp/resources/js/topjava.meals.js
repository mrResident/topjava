function filterTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableWithData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableWithData);
}

// $(document).ready(function () {
$(function () {
    makeEditable({
        ajaxUrl: "ajax/profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        }),
        updateTable: filterTable
    });
});