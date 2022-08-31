import {Autocomplete, Box, Checkbox, FormControlLabel, TextField} from "@mui/material";
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
    {field: "reader_id", headerName: "№ билета", flex: 1, sortable: false},
];

const Operations = () => {
    const { idInit } = useParams();
    const { bookInstanceIdInit } = useParams();

    const [isbn, setIsbn] = useState("");
    const [readerId, setReaderId] = useState(idInit || "");
    const [bookInstanceId, setBookInstanceId] = useState(bookInstanceIdInit || "")
    const [notReturned, setNotReturned] = useState(false);

    const [page, setPage] = useState(0);
    const [pageSize, setPageSize] = useState(15);

    const [pageInfo, setPageInfo] = useState({content: [], totalElements: 0});

    const queryOptions = useMemo(
        () => ({page, pageSize,
            isbn: isbn,
            title: "",
            reader_id: readerId,
            reader_name: "",
            book_instance_id: bookInstanceId,
            not_returned: notReturned}),
        [page, pageSize, isbn, readerId, notReturned, bookInstanceId],
    );

    useFetch("/api/get/operations?", queryOptions, {},
            t => setPageInfo(t),
        [page, pageSize, isbn, readerId, notReturned, bookInstanceId]);

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
                inputValue={readerId}
                onInputChange={(e, v) => setReaderId(v)}
                options={[]}
                renderInput={(params) => <TextField {...params} label="№ читательского билета" />}
            />
            <Autocomplete
                id="book_instance_id_field"
                style={{width: "15rem"}}
                freeSolo
                inputValue={bookInstanceId}
                onInputChange={(e, v) => setBookInstanceId(v)}
                options={[]}
                renderInput={(params) => <TextField {...params} label="№ экземпляра" />}
            />
            <FormControlLabel control={<Checkbox defaultChecked checked={notReturned} onChange={v => setNotReturned(v.target.checked)} />} label="Не возвращенго" />
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