import {useState} from "react";
import {postRequest, useFetch} from "../hooks.js";
import { FilterAlt } from "@mui/icons-material";
import {
    Autocomplete,
    TextField,
    Box,
    SpeedDial,
    SpeedDialIcon,
    Button,
    Dialog,
    DialogTitle,
    DialogContent, DialogActions
} from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import ReaderDialog from "./ReaderDialog.jsx";

const headers = [
    {field: "id", headerName: "№ читательского билета", flex: 1},
    {field: "name", headerName: "ФИО", flex: 1},
    {field: "address", headerName: "Адрес", flex: 1},
];

const createData = (row) => ({
    id: row.id,
    name: row.name,
    address: row.address,
});

const Books = () => {
    const [data, setData] = useState([]);
    const [id, setId] = useState("");
    const [name, setName] = useState("");

    const [dialogOpened, setDialogOpened] = useState(false);
    const openDialog = () => setDialogOpened(true);
    const closeDialog = () => setDialogOpened(false);

    const [newReader, setNewReader] = useState({name: "", address: ""});

    useFetch("/api/get/readers?" +
        new URLSearchParams({id: id, name: name}),
        {}, (res) => setData(res.map(createData)), [id, name, dialogOpened]);

    return (
        <Box>
            <Box sx={{display: "flex", p: 3, gap: 3, alignItems: "center"}}>
                <FilterAlt/>
                <Autocomplete
                    id="id_field"
                    style={{width: "15rem"}}
                    freeSolo
                    inputValue={id}
                    onInputChange={(e, v) => setId(v)}
                    options={[]}
                    renderInput={(params) => <TextField {...params} label="№ читательского билета" />}
                />
                <Autocomplete
                    id="author_field"
                    style={{width: "15rem"}}
                    freeSolo
                    inputValue={name}
                    onInputChange={(e, v) => setName(v)}
                    options={[]}
                    renderInput={(params) => <TextField {...params} label="ФИО" />}
                />
                <Button variant="outlined" onClick={openDialog}>Добавить читателя</Button>
            </Box>
            <DataGrid
                columns={headers}
                rows={data}
                sx={{height: "80vh"}}
                density="compact"
                disableColumnFilter
            />
            <ReaderDialog newReader={newReader} setNewReader={setNewReader}
                          isOpen={dialogOpened} setIsOpen={setDialogOpened}/>
        </Box>
    );
};

export default Books;