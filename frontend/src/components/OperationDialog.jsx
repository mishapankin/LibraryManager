import {Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField, Typography, Box} from "@mui/material";
import {postRequest, useFetch} from "../hooks.js";
import {useState} from "react";

const OperationDialog = ( {isOpen, setIsOpen, onEnd } ) => {
    const [newOperation, setNewOperation] = useState({
        reader_id: "",
        book_instance_id: ""
    });

    const [readers, setReaders] = useState([]);
    const [bookInstances, setBookInstances] = useState([]);

    useFetch("/api/get/reader/by_id?", { id: newOperation.reader_id }, {},
        (res) => setReaders(res),
        [newOperation.reader_id]);

    useFetch("/api/get/book_instances/by_id?", { id: newOperation.book_instance_id }, {},
        (res) => setBookInstances(res),
        [newOperation.book_instance_id]); 

    const postNewOperation = () =>
        postRequest("/api/post/operation", newOperation).then(() => {setIsOpen(false); onEnd()});

    return <Dialog open={isOpen}>
        <DialogTitle>Взятие книги</DialogTitle>
        <DialogContent sx={{width: "40rem", display: "flex", gap: 3, flexDirection: "row"}}>
            <Box sx={{display: "flex", gap: 3, flexDirection: "column", alignItems: "center"}}>
                <TextField
                    autoFocus
                    id="new_reader_name"
                    label="№ читательского билета"
                    margin="dense"
                    value={newOperation.reader_id}
                    onChange={e => setNewOperation({ ...newOperation, reader_id: e.target.value})}
                    required
                />
                <TextField
                    autoFocus
                    id="new_reader_address"
                    label="№ экземпляра"
                    margin="dense"
                    value={newOperation.book_instance_id}
                    onChange={e => setNewOperation({ ...newOperation, book_instance_id: e.target.value})}
                    required
                />
                
            </Box>
            <Typography>
                <b>Читатель: </b> {readers.length === 1 && readers[0]} <br/>
                <b>Автор: </b> {bookInstances.length === 1 && bookInstances[0].author} <br/>
                <b>Название: </b> {bookInstances.length === 1 && bookInstances[0].title}
            </Typography>
        </DialogContent>
        <DialogActions sx={{display: "flex", justifyContent: "space-evenly", paddingBottom: 3}}>
            <Button onClick={() => setIsOpen(false)}>Отменить</Button>
            <Button onClick={postNewOperation}
                    variant="contained">
                Добавить
            </Button>
        </DialogActions>
    </Dialog>;
};

export default OperationDialog;