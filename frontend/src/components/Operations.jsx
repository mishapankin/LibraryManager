import {Autocomplete, Box, TextField} from "@mui/material";
import {useMemo, useState} from "react";
import {useFetch} from "../hooks.js";
import {DataGrid} from "@mui/x-data-grid";
import {FilterAlt} from "@mui/icons-material";
import {useParams} from "react-router-dom";

const prettifyDate = (d) => {
    if (d.value) {
        let v = new Date(d.value);
        return v.toDateString();
    } else {
        return "-";
    }
}

const headers = [
    {field: "id", headerName: "№", flex: 1, sortable: false},
    {field: "isbn", headerName: "ISBN", flex: 2, sortable: false},
    {field: "date", headerName: "Дата взятия", flex: 2, valueFormatter: prettifyDate, sortable: false},
    {field: "dueDate", headerName: "До", flex: 2, valueFormatter: prettifyDate, sortable: false},
    {field: "returnDate", headerName: "Дата возвращения", flex: 2, valueFormatter: prettifyDate, sortable: false},
    {field: "name", headerName: "ФИО", flex: 3, sortable: false},
];

const Operations = () => {
    const { isbnInit } = useParams();

    const [isbn, setIsbn] = useState(isbnInit || "");
    const [reader_id, setReaderId] = useState("");

    const [page, setPage] = useState(0);
    const [pageSize, setPageSize] = useState(15);

    const [pageInfo, setPageInfo] = useState({content: [], totalElements: 0});

    const queryOptions = useMemo(
        () => ({page, pageSize,
            isbn,
            title: "",
            reader_id,
            reader_name: "",
            book_instance_id: "",
            not_returned: false}),
        [page, pageSize, isbn, reader_id],
    );

    useFetch("/api/get/operations?", queryOptions, {},
            t => setPageInfo(t),
        [page, pageSize, isbn, reader_id]);

    return <Box>
        <Box sx={{display: "flex", p: 3, gap: 3, alignItems: "center"}}>
            <FilterAlt/>
            <Autocomplete
                id="isbn_field"
                style={{width: "15rem"}}
                freeSolo
                inputValue={isbn}
                onInputChange={(e, v) => setIsbn(v)}
                options={[]}
                renderInput={(params) => <TextField {...params} label="ISBN" />}
            />
            <Autocomplete
                id="reader_id_field"
                style={{width: "15rem"}}
                freeSolo
                inputValue={reader_id}
                onInputChange={(e, v) => setReaderId(v)}
                options={[]}
                renderInput={(params) => <TextField {...params} label="№ читательского билета" />}
            />
        </Box>
        <DataGrid
            paginationMode="server"
            density="compact"
            page={page}
            pageSize={pageSize}
            onPageChange={(newPage) => setPage(newPage)}
            onPageSizeChange={(newPageSize) => setPageSize(newPageSize)}
            columns={headers}
            rowCount={pageInfo.totalElements}
            sx={{height: "80vh"}}
            disableColumnMenu
            rows={pageInfo.content}
        />
    </Box>
};

export default Operations;