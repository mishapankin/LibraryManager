import {Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import {postRequest} from "../hooks.js";
import {useState} from "react";

const OperationDialog = ( {isOpen, setIsOpen, onEnd } ) => {
    const [newOperation, setNewOperation] = useState({
        reader_id: "",
        book_instance_id: ""
    });

    const postNewOperation = () =>
        postRequest("/api/post/operation", newOperation).then(() => {setIsOpen(false); onEnd()});

    return <Dialog open={isOpen}>
        <DialogTitle>Взятие книги</DialogTitle>
        <DialogContent sx={{display: "flex", gap: 3, flexDirection: "column"}}>
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