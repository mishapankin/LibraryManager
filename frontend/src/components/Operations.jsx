import {Box} from "@mui/material";
import {useMemo, useState} from "react";
import {useFetch} from "../hooks.js";
import {DataGrid} from "@mui/x-data-grid";

const prettifyDate = (d) => {
    if (d.value) {
        let v = new Date(d.value);
        return v.toDateString();
    } else {
        return "-";
    }
}

const headers = [
    {field: "id", headerName: "№ Операции", flex: 1},
    {field: "isbn", headerName: "ISBN", flex: 1},
    {field: "date", headerName: "Дата взятия", flex: 1, valueFormatter: prettifyDate},
    {field: "dueDate", headerName: "До", flex: 1, valueFormatter: prettifyDate},
    {field: "returnDate", headerName: "Дата возвращения", flex: 1, valueFormatter: prettifyDate},
    {field: "name", headerName: "ФИО", flex: 2},
];

const Operations = () => {
    const [page, setPage] = useState(0);
    const [pageSize, setPageSize] = useState(15);

    const [pageInfo, setPageInfo] = useState({content: [], totalElements: 0});

    const queryOptions = useMemo(
        () => ({page, pageSize}),
        [page, pageSize],
    );

    useFetch("/api/get/operations?", queryOptions, {}, t => setPageInfo(t),[page, pageSize]);

    return <Box>
        <DataGrid
            paginationMode="server"
            page={page}
            pageSize={pageSize}
            onPageChange={(newPage) => setPage(newPage)}
            onPageSizeChange={(newPageSize) => setPageSize(newPageSize)}
            columns={headers}
            rowCount={pageInfo.totalElements}
            sx={{height: "80vh"}}
            disableColumnFilter
            rows={pageInfo.content}
        />
    </Box>
};

export default Operations;