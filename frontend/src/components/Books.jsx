import {useState} from "react";
import {useFetch} from "../hooks.js";
import { FilterAlt } from "@mui/icons-material";
import {Autocomplete, TextField, Box} from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';

const headers = [
    {field: "title", headerName: "Название", flex: 3},
    {field: "author", headerName: "Автор", flex: 3},
    // {field: "total", headerName: "Всего", flex: 1},
    // {field: "available", headerName: "Осталось", flex: 1},
    {field: "id", headerName:  "ISBN", flex: 2},
    {field: "publisher", headerName: "Издательство", flex: 2},
];

const createData = (row) => ({
    title: row.title, 
    author: row.author.name,
    // total: row.total,
    // available: row.available,
    id: row.isbn,
    publisher: row.publisher.name,
});

const Books = () => {
    const [data, setData] = useState([]);
    const [authorName, setAuthorName] = useState("");
    const [title, setTitle] = useState("");
    const [isbn, setISBN] = useState("");

    const [authorList, setAuthorList] = useState([]);

    useFetch("/api/get/books?" +
        new URLSearchParams({author: authorName, title: title, isbn: isbn}),
        {}, (res) => setData(res.map(createData)), [authorName, title, isbn]);

    useFetch("/api/get/authors", {}, (res) => setAuthorList(res), []);

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
                    options={[]}
                    renderInput={(params) => <TextField {...params} label="Название" />}
                />
                <Autocomplete
                    id="author_field"
                    style={{width: "15rem"}}
                    freeSolo
                    inputValue={authorName}
                    onInputChange={(e, v) => setAuthorName(v)}
                    options={authorList}
                    renderInput={(params) => <TextField {...params} label="Автор" />}
                />
                <Autocomplete
                    id="isbn_field"
                    style={{width: "15rem"}}
                    freeSolo
                    inputValue={isbn}
                    onInputChange={(e, v) => setISBN(v)}
                    options={[]}
                    renderInput={(params) => <TextField {...params} label="ISBN" />}
                />
            </Box>
            <DataGrid
                columns={headers}
                rows={data}
                sx={{height: "80vh"}}
                disableColumnFilter
            />
        </Box>
    );
};

export default Books;