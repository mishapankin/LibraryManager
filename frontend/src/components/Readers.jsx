import {useMemo, useState} from "react";
import {postRequest, useFetch} from "../hooks.js";
import {Edit, FilterAlt} from "@mui/icons-material";
import {
    Autocomplete,
    TextField,
    Box,
    Button,
} from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import ReaderDialog from "./ReaderDialog.jsx";
import {useNavigate} from "react-router-dom";
import UpdateReaderDialog from "./UpdateReader.jsx";



const Readers = () => {
    const [data, setData] = useState({content: [], totalElements: 0});
    const [id, setId] = useState("");
    const [name, setName] = useState("");

    const [dialogOpened, setDialogOpened] = useState(false);
    const openDialog = () => setDialogOpened(true);

    const [updateOpened, setUpdateOpened] = useState(false);
    const [updateId, setUpdateId] = useState(0);
    const openUpdate = () => setUpdateOpened(true);

    const [page, setPage] = useState(0);
    const [pageSize, setPageSize] = useState(15);

    const [readers, setReaders] = useState([]);
    const [ids, setIds] = useState([]);

    const navigate = useNavigate();

    const queryOptions = useMemo(
        () => ({
            id: id,
            name: name,
            page: page,
            pageSize: pageSize,
        }), [id, name, page, pageSize]
    );

    useFetch("/api/get/readers?",
        queryOptions,
        {}, (res) => setData(res), [id, name, dialogOpened, page, pageSize]);

    useFetch("/api/get/reader_ids", {}, {}, (r) => setIds(r), []);
    useFetch("/api/get/reader_names", {}, {}, (r) => setReaders(r), []);

    const headers = [
        {field: "id", headerName: "№ ЧБ", flex: 2, sortable: false},
        {field: "name", headerName: "ФИО", flex: 6, sortable: false},
        {field: "address", headerName: "Адрес", flex: 5, sortable: false},
        {field: "email", headerName: "E-mail", flex: 5, sortable: false},
        {field: "phone", headerName: "№ телефона", flex: 5, sortable: false},
        {field: "edit", headerName: "", flex: 1, sortable: false, renderCell: () => <Edit/>},
    ];

    return (
        <Box>
            <Box sx={{display: "flex", p: 2, gap: 3}}>
                <FilterAlt sx={{alignSelf: "center"}}/>
                <Autocomplete
                    id="id_field"
                    style={{width: "15rem"}}
                    freeSolo
                    inputValue={id}
                    onInputChange={(e, v) => setId(v)}
                    options={ids}
                    renderInput={(params) => <TextField {...params} label="№ читательского билета" />}
                />
                <Autocomplete
                    id="author_field"
                    style={{width: "15rem"}}
                    freeSolo
                    inputValue={name}
                    onInputChange={(e, v) => setName(v)}
                    options={readers}
                    renderInput={(params) => <TextField {...params} label="ФИО" />}
                />
                <Button variant="outlined" onClick={openDialog} sx={{marginLeft: "auto"}}>Добавить читателя</Button>
            </Box>
            <DataGrid
                paginationMode="server"
                columns={headers}
                rows={data.content}
                page={page}
                pageSize={pageSize}
                onPageChange={(p) => setPage(p)}
                onPageSizeChange={(p) => setPageSize(p)}
                rowsPerPageOptions={[15]}
                rowCount={data.totalElements}
                autoHeight
                density="compact"
                disableColumnMenu
                onCellClick={(c) => {
                    if (c.field !== "edit") {
                        navigate(`/operations/id=${c.id}`);
                    } else {
                        setUpdateId(c.id);
                        openUpdate();
                    }
                }}
            />
            <ReaderDialog isOpen={dialogOpened} setIsOpen={setDialogOpened}
                          onEnd={() => {setId(""); setName(""); setPage(0)}}
            />
            <UpdateReaderDialog isOpen={updateOpened} setIsOpen={setUpdateOpened}
                                onEnd={(id) => { setId(id + "!"); setName(""); setPage(0)}}
                                id={updateId}
            />
        </Box>
    );
};

export default Readers;