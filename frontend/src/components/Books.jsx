import {useState} from "react";
import {useFetch} from "../hooks.js";
import { FilterAlt } from "@mui/icons-material";
import {Autocomplete, TextField, Box} from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import {useNavigate} from "react-router-dom";

const headers = [
    {field: "title", headerName: "Название", flex: 3},
    {field: "author", headerName: "Автор", flex: 3},
    {field: "total", headerName: "Всего", flex: 1},
    // {field: "available", headerName: "Осталось", flex: 1},
    {field: "id", headerName:  "ISBN", flex: 2},
    {field: "publisher", headerName: "Издательство", flex: 2},
];

const Books = () => {
    const [data, setData] = useState([]);
    const [authorName, setAuthorName] = useState("");
    const [title, setTitle] = useState("");
    const [isbn, setISBN] = useState("");

    const navigate = useNavigate();

    useFetch("/api/get/book_info?" +
        new URLSearchParams({author: authorName, title: title, isbn: isbn}),
        {}, (res) => setData(res), [authorName, title, isbn]);

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
                    options={data.map(v => v.title)}
                    renderInput={(params) => <TextField {...params} label="Название" />}
                />
                <Autocomplete
                    id="author_field"
                    style={{width: "15rem"}}
                    freeSolo
                    inputValue={authorName}
                    onInputChange={(e, v) => setAuthorName(v)}
                    options={[...new Set(data.map(v => v.author))]}
                    renderInput={(params) => <TextField {...params} label="Автор" />}
                />
                <Autocomplete
                    id="isbn_field"
                    style={{width: "15rem"}}
                    freeSolo
                    inputValue={isbn}
                    onInputChange={(e, v) => setISBN(v)}
                    options={data.map(v => v.id)}
                    renderInput={(params) => <TextField {...params} label="ISBN" />}
                />
            </Box>
            <DataGrid
                columns={headers}
                rows={data}
                density="compact"
                onRowClick={(p) => navigate(`/book_instances/${p.row.id}`)}
                sx={{height: "80vh"}}
                disableColumnFilter
            />
        </Box>
    );
};

export default Books;