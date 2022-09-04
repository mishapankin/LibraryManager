import {Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import {postRequest} from "../hooks.js";
import {useState} from "react";

const ReaderDialog = ( {isOpen, setIsOpen, onEnd } ) => {
    const [newReader, setNewReader] = useState({name: "", address: "", phone: "", email: ""});

    const [error, setError] = useState(false);

    const createNewUser = () =>
        postRequest("/api/post/reader", newReader)
            .then(() => {setIsOpen(false); onEnd()});

    return <Dialog open={isOpen}>
        <DialogTitle>Добавить нового читателя</DialogTitle>
        <DialogContent sx={{display: "flex", gap: 3, flexDirection: "column"}}>
            <TextField
                autoFocus
                id="new_reader_name"
                label="ФИО"
                margin="dense"
                value={newReader.name}
                onChange={e => setNewReader({ ...newReader, name: e.target.value})}
                required
                error={true}
            />
            <TextField
                autoFocus
                id="new_reader_address"
                label="Адрес"
                margin="dense"
                value={newReader.address}
                onChange={e => setNewReader({ ...newReader, address: e.target.value})}
                error={true}
            />
            <TextField
                autoFocus
                id="new_reader_email"
                label="E-mail"
                margin="dense"
                type="email"
                value={newReader.email}
                onChange={e => setNewReader({ ...newReader, email: e.target.value})}
                error={true}
            />
            <TextField
                autoFocus
                id="new_reader_phone"
                label="Номер телефона"
                margin="dense"
                type="tel"
                value={newReader.phone}
                onChange={e => setNewReader({ ...newReader, phone: e.target.value})}
                error={true}
            />
        </DialogContent>
        <DialogActions sx={{display: "flex", justifyContent: "space-evenly", paddingBottom: 3}}>
            <Button onClick={() => setIsOpen(false)}>Отменить</Button>
            <Button onClick={createNewUser}
                    variant="contained">
                Добавить
            </Button>
        </DialogActions>
    </Dialog>;
};

export default ReaderDialog;