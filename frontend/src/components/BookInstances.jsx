import {Box, Chip, Typography} from "@mui/material";
import {DataGrid} from "@mui/x-data-grid";
import {useNavigate, useParams} from "react-router-dom";
import {useFetch} from "../hooks.js";
import {useState} from "react";

const headers = [
    { field: "id", headerName: "№ экземпляра", flex: 1, align: "center", headerAlign: "center", sortable: false},
    { field: "isReturned", headerName: "Статус", flex: 1, align: "center", headerAlign: "center", sortable: false,
        renderCell: (v) => (v.value?
            <Chip label="В библиотеке" color="success" size="small"/> :
            <Chip label="У читателя" color="warning" size="small"/>)
    },
];

const BookInstances = () => {
    const { isbn } = useParams();
    const [ids, setIds] = useState([]);

    const navigate = useNavigate();

    useFetch("/api/get/book_instances?",{"isbn": isbn}, {}, res => setIds(res), [isbn]);

    return <Box>
        <Box sx={{marginBottom: 3}}>
            <Typography variant="h3">ISBN: {isbn}</Typography>
        </Box>
        <DataGrid
            columns={headers}
            rows={ids}
            density="compact"
            sx={{height: "80vh"}}
            disableColumnMenu
            onRowClick={(p) => navigate(`/operations/book_id=${p.row.id}`)}
        />
    </Box>
};

export default BookInstances;