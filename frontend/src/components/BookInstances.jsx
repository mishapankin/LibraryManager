import {Box, Chip, Typography} from "@mui/material";
import {DataGrid} from "@mui/x-data-grid";
import {useParams} from "react-router-dom";
import {useFetch} from "../hooks.js";
import {useState} from "react";

const headers = [
    { field: "id", headerName: "№ экземпляра", flex: 1, align: "center", headerAlign: "center"},
    { field: "isReturned", headerName: "Статус", flex: 1, align: "center", headerAlign: "center",
        renderCell: (v) => (v.value?
            <Chip label="В библиотеке" color="success" size="small"/> :
            <Chip label="У читателя" color="warning" size="small"/>)
    },
];

const BookInstances = () => {
    const { isbn } = useParams();
    const [ids, setIds] = useState([]);

    useFetch("/api/get/book_instances?" + new URLSearchParams({"isbn": isbn}), {}, res => setIds(res), [isbn]);

    return <Box>
        <Box sx={{marginBottom: 3}}>
            <Typography variant="h3">ISBN: {isbn}</Typography>
        </Box>
        <DataGrid
            columns={headers}
            rows={ids}
            density="compact"
            sx={{height: "80vh"}}
            disableColumnFilter
        />
    </Box>
};

export default BookInstances;