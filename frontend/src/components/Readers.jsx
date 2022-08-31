import {useState} from "react";
import {postRequest, useFetch} from "../hooks.js";
import { FilterAlt } from "@mui/icons-material";
import {
    Autocomplete,
    TextField,
    Box,
    Button,
} from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import ReaderDialog from "./ReaderDialog.jsx";
import {useNavigate} from "react-router-dom";

const headers = [
    {field: "id", headerName: "№ читательского билета", flex: 1, sortable: false},
    {field: "name", headerName: "ФИО", flex: 5, sortable: false},
    {field: "address", headerName: "Адрес", flex: 5, sortable: false},
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

    const navigate = useNavigate();

    useFetch("/api/get/readers?",
        {id: id, name: name},
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
                <Button variant="outlined" onClick={openDialog} sx={{marginLeft: "auto"}}>Добавить читателя</Button>
            </Box>
            <DataGrid
                columns={headers}
                rows={data}
                sx={{height: "80vh"}}
                density="compact"
                disableColumnMenu
                onRowClick={(p) => navigate(`/operations/id=${p.row.id}`)}
            />
            <ReaderDialog newReader={newReader} setNewReader={setNewReader}
                          isOpen={dialogOpened} setIsOpen={setDialogOpened}
                          onEnd={() => {setId(""); setName("")}}
            />
        </Box>
    );
};

export default Books;