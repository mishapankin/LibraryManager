import {useMemo, useState} from "react";
import {useFetch} from "../hooks.js";
import { FilterAlt } from "@mui/icons-material";
import {Autocomplete, TextField, Box} from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import {useNavigate} from "react-router-dom";

const headers = [
    {field: "title", headerName: "Название", flex: 3, sortable: false},
    {field: "author", headerName: "Автор", flex: 3, sortable: false},
    {field: "total", headerName: "Всего", flex: 1, sortable: false},
    {field: "id", headerName:  "ISBN", flex: 2, sortable: false},
    {field: "publisher", headerName: "Издательство", flex: 2, sortable: false},
];

const Books = () => {
    const [data, setData] = useState({content: [], totalElements: 0});
    const [authorName, setAuthorName] = useState("");
    const [title, setTitle] = useState("");
    const [isbn, setISBN] = useState("");

    const [authors, setAuthors] = useState([]);
    const [isbns, setIsbns] = useState([]);
    const [titles, setTitles] = useState([]);

    const [page, setPage] = useState(0);
    const [pageSize, setPageSize] = useState(15);

    const navigate = useNavigate();

    const queryOptions = useMemo(
        () => ({
            author: authorName,
            title: title,
            isbn: isbn,
            page: page,
            pageSize: pageSize,
        }), [authorName, title, isbn, page, pageSize]
    )

    useFetch("/api/get/book_info?",
        queryOptions, {},
        (res) => setData(res), [authorName, title, isbn, page, pageSize]);

    useFetch("/api/get/titles", {}, {}, res => setTitles(res), []);
    useFetch("/api/get/isbns", {}, {}, res => setIsbns(res), []);
    useFetch("/api/get/authors", {}, {}, res => setAuthors(res), []);

    return (
        <Box>
            <Box sx={{display: "flex", p: 3, gap: 3, alignItems: "center"}}>
                <FilterAlt/>
                <Autocomplete
                    id="title_field"
                    style={{width: "15rem"}}
                    freeSolo
                    inputValue={title}
                    onInputChange={(e, v) => setTitle(v)}
                    options={titles}
                    renderInput={(params) => <TextField {...params} label="Название" />}
                />
                <Autocomplete
                    id="author_field"
                    style={{width: "15rem"}}
                    freeSolo
                    inputValue={authorName}
                    onInputChange={(e, v) => setAuthorName(v)}
                    options={authors}
                    renderInput={(params) => <TextField {...params} label="Автор" />}
                />
                <Autocomplete
                    id="isbn_field"
                    style={{width: "15rem"}}
                    freeSolo
                    inputValue={isbn}
                    onInputChange={(e, v) => setISBN(v)}
                    options={isbns}
                    renderInput={(params) => <TextField {...params} label="ISBN" />}
                />
            </Box>
            <DataGrid
                paginationMode="server"
                columns={headers}
                rows={data.content}
                density="compact"
                page={page}
                pageSize={pageSize}
                onPageChange={(p) => setPage(p)}
                onPageSizeChange={(p) => setPageSize(p)}
                rowsPerPageOptions={[15]}
                rowCount={data.totalElements}
                onRowClick={(p) => navigate(`/book_instances/${p.row.id}`)}
                sx={{height: "80vh"}}
                disableColumnMenu
            />
        </Box>
    );
};

export default Books;