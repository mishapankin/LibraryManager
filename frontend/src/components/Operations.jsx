import {Autocomplete, Box, Button, Checkbox, FormControlLabel, TextField} from "@mui/material";
import {useMemo, useState} from "react";
import {postRequest, useFetch} from "../hooks.js";
import {DataGrid} from "@mui/x-data-grid";
import {FilterAlt} from "@mui/icons-material";
import {useParams} from "react-router-dom";
import OperationDialog from "./OperationDialog.jsx";

const Operations = () => {
    const { idInit } = useParams();
    const { bookInstanceIdInit } = useParams();

    const [rerenderVal, setRerenderVal] = useState();
    const [isbn, setIsbn] = useState("");
    const [readerId, setReaderId] = useState(idInit? `${idInit}!` : "");
    const [bookInstanceId, setBookInstanceId] = useState(bookInstanceIdInit? `${bookInstanceIdInit}!` : "")
    const [notReturned, setNotReturned] = useState(false);

    const [dialogOpened, setDialogOpened] = useState(false);

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

    const rerender = () => setRerenderVal({});

    const returnBook = (id) => {
        console.log(id);
        postRequest("/api/update/operation", {id: id}).then(rerender);
    };

    const prettifyDate = (d) => {
        let v = new Date(d.value);
        return v.toDateString();
    }
    
    const renderReturnButton = (d) => {
        if (d.value) {
            return prettifyDate(d);
        }
        return <Button onClick={() => returnBook(d.id)}>Вернуть</Button>;
    }

    const headers = [
        {field: "id", headerName: "№", flex: 1, sortable: false},
        {field: "reader_id", headerName: "№ ЧБ", flex: 1, sortable: false},
        {field: "book_instance_id", headerName: "№ ЭКЗ", flex: 1, sortable: false},
        {field: "date", headerName: "Дата взятия", flex: 2, valueFormatter: prettifyDate, sortable: false},
        {field: "dueDate", headerName: "До", flex: 2, valueFormatter: prettifyDate, sortable: false},
        {field: "returnDate", headerName: "Дата возвращения", flex: 2, renderCell: renderReturnButton, sortable: false},
        {field: "name", headerName: "ФИО", flex: 3, sortable: false},
        {field: "title", headerName: "Название", flex: 2, sortable: false},
    ];

    const onEnd = () => {
        setIsbn("");
        setReaderId("");
        setBookInstanceId("");
        setNotReturned(false);
    };
    

    useFetch("/api/get/operations?", queryOptions, {},
            t => setPageInfo(t),
        [page, pageSize, isbn, readerId, notReturned, bookInstanceId, rerenderVal]);

    return <Box>
        <Box sx={{display: "flex", p: 2, gap: 3, alignItems: "center"}}>
            <FilterAlt sx={{alignSelf: "center"}}/>
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
            <FormControlLabel control={<Checkbox checked={notReturned} onChange={v => setNotReturned(v.target.checked)} />} label="Не возвращенго" />
            <Button variant="outlined" onClick={() => setDialogOpened(true)} sx={{marginLeft: "auto"}}>Новая операция</Button>
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
            autoHeight
            disableColumnMenu
            rowsPerPageOptions={[15]}
            rows={pageInfo.content}
        />
        <OperationDialog isOpen={dialogOpened} setIsOpen={setDialogOpened}
                         onEnd={onEnd}
        />
    </Box>
};

export default Operations;