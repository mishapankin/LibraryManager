import {Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField, Typography} from "@mui/material";
import {postRequest} from "../hooks.js";
import {useState} from "react";

const UpdateReaderDialog = ( {isOpen, setIsOpen, onEnd, id } ) => {
    const [reader, setReader] = useState({name: "", address: "", phone: "", email: ""});

    const [error, setError] = useState({name: false, address: false, phone: false, email: false});

    const updateUser = () =>
        postRequest("/api/update/reader", { ...reader, id},
            (parsed, status) => {
                if (status !== 200) {
                    setError(parsed);
                } else {
                    setIsOpen(false);
                    onEnd(id);
                }
            });

    return <Dialog open={isOpen}>
        <DialogTitle>Обновить читателя</DialogTitle>
        <DialogContent sx={{display: "flex", gap: 3, flexDirection: "column"}}>
            <Typography>№ ЧБ: {id}</Typography>
            <TextField
                autoFocus
                id="new_reader_name"
                label="ФИО"
                margin="dense"
                value={reader.name}
                onChange={e => setReader({ ...reader, name: e.target.value})}
                required
                error={error.name}
            />
            <TextField
                autoFocus
                id="new_reader_address"
                label="Адрес"
                margin="dense"
                value={reader.address}
                onChange={e => setReader({ ...reader, address: e.target.value})}
                error={error.address}
            />
            <TextField
                autoFocus
                id="new_reader_email"
                label="E-mail"
                margin="dense"
                type="email"
                value={reader.email}
                onChange={e => setReader({ ...reader, email: e.target.value})}
                error={error.email}
            />
            <TextField
                autoFocus
                id="new_reader_phone"
                label="Номер телефона"
                margin="dense"
                type="tel"
                value={reader.phone}
                onChange={e => setReader({ ...reader, phone: e.target.value})}
                error={error.phone}
            />
        </DialogContent>
        <DialogActions sx={{display: "flex", justifyContent: "space-evenly", paddingBottom: 3}}>
            <Button onClick={() => setIsOpen(false)}>Отменить</Button>
            <Button onClick={updateUser}
                    variant="contained">
                Обновить
            </Button>
        </DialogActions>
    </Dialog>;
};

export default UpdateReaderDialog;